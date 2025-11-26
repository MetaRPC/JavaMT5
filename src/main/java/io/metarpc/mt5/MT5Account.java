package io.metarpc.mt5;

import io.grpc.*;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MT5 Account API Class
 *
 * High-level Java client for MetaTrader 5 terminal via gRPC.
 * Provides convenient methods for trading, market data, and account management.
 */
public class MT5Account {

    //==============================================
    // region FIELDS
    //==============================================


    // Connection parameters
    private final long user;
    private final String password;
    private String host;
    private int port;
    private String serverName;
    private String baseChartSymbol;
    private int connectTimeoutSeconds;
    private UUID id;

    // gRPC configuration
    private final String grpcServer;
    private final ManagedChannel grpcChannel;

    // gRPC client stubs (blocking)
    public final ConnectionGrpc.ConnectionBlockingStub connectionClient;
    public final AccountHelperGrpc.AccountHelperBlockingStub accountClient;
    public final AccountInformationGrpc.AccountInformationBlockingStub accountInformationClient;
    public final TradingHelperGrpc.TradingHelperBlockingStub tradeClient;
    public final TradeFunctionsGrpc.TradeFunctionsBlockingStub tradeFunctionsClient;
    public final MarketInfoGrpc.MarketInfoBlockingStub marketInfoClient;
    public final ChartsGrpc.ChartsBlockingStub chartsClient;
    public final InternalChartsGrpc.InternalChartsBlockingStub internalChartsClient;
    public final HealthGrpc.HealthBlockingStub healthClient;

    // gRPC client stubs (async/streaming)
    public final SubscriptionServiceGrpc.SubscriptionServiceStub subscriptionClient;


    //endregion


    //==============================================
    // region CONSTRUCTORS
    //==============================================

    /**
     * Create MT5 account connection
     *
     * @param user MT5 account number
     * @param password MT5 account password
     * @param grpcServer gRPC server address (default: "grpc.mt5.mrpc.pro:443")
     * @param id Optional instance ID (generated if null)
     */
    public MT5Account(long user, String password, String grpcServer, UUID id) {
        this.user = user;
        this.password = password;
        this.grpcServer = grpcServer != null ? grpcServer : "grpc.mt5.mrpc.pro:443";
        this.id = id != null ? id : UUID.randomUUID();

        // Create gRPC channel with SSL/TLS
        this.grpcChannel = NettyChannelBuilder
                .forTarget(this.grpcServer)
                .useTransportSecurity() // Use TLS instead of plaintext
                .maxInboundMessageSize(100 * 1024 * 1024) // 100 MB
                .build();

        // Initialize blocking stubs
        this.connectionClient = ConnectionGrpc.newBlockingStub(grpcChannel);
        this.accountClient = AccountHelperGrpc.newBlockingStub(grpcChannel);
        this.accountInformationClient = AccountInformationGrpc.newBlockingStub(grpcChannel);
        this.tradeClient = TradingHelperGrpc.newBlockingStub(grpcChannel);
        this.tradeFunctionsClient = TradeFunctionsGrpc.newBlockingStub(grpcChannel);
        this.marketInfoClient = MarketInfoGrpc.newBlockingStub(grpcChannel);
        this.chartsClient = ChartsGrpc.newBlockingStub(grpcChannel);
        this.internalChartsClient = InternalChartsGrpc.newBlockingStub(grpcChannel);
        this.healthClient = HealthGrpc.newBlockingStub(grpcChannel);

        // Initialize async/streaming stub
        this.subscriptionClient = SubscriptionServiceGrpc.newStub(grpcChannel);
    }

    /**
     * Simplified constructor (uses default gRPC server)
     */
    public MT5Account(long user, String password) {
        this(user, password, null, null);
    }


    //endregion

    //==============================================
    // region HELPER METHODS
    //==============================================


    /**
     * Create metadata headers with authentication info
     */
    private Metadata getMetadataHeaders() {
        Metadata headers = new Metadata();
        // Use "id" header as per C# implementation
        Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);

        headers.put(idKey, id.toString());

        return headers;
    }

    /**
     * Check if connected
     */
    public boolean isConnected() {
        return host != null || serverName != null;
    }

    /**
     * Attach to existing terminal instance
     */
    public void attachByInstanceId(UUID instanceId) {
        this.id = instanceId;
    }


    //endregion


    //===============================================================
    // region PROTECTED HELPER METHODS (Automatic Reconnection)
    //===============================================================

    /**
     * Functional interface for gRPC calls that return a response with error field
     */
    @FunctionalInterface
    private interface GrpcCall<T> {
        T execute(Metadata headers) throws StatusRuntimeException;
    }

    /**
     * Functional interface to extract error from response
     * Returns null if no error, or Error object if response contains error
     */
    @FunctionalInterface
    private interface ErrorExtractor<T> {
        MrpcMt5Error.Error getError(T response);
    }

    /**
     * Execute gRPC call with automatic reconnection on failure.
     * This method will attempt to reconnect if the terminal connection is lost.
     * Used for all unary (request-response) RPC calls.
     *
     * @param call The gRPC call to execute
     * @param errorExtractor Function to check if response has error
     * @return Response from gRPC call
     * @throws ApiExceptionMT5 if call fails or returns error
     */
    private <T> T executeWithReconnect(
            GrpcCall<T> call,
            ErrorExtractor<T> errorExtractor) throws ApiExceptionMT5 {

        if (id == null) {
            throw new ApiExceptionMT5("Please call connect() method first");
        }

        int maxRetries = 3;
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            Metadata headers = getMetadataHeaders();
            T response;

            try {
                response = call.execute(headers);
            } catch (StatusRuntimeException e) {
                if (e.getStatus().getCode() == io.grpc.Status.Code.UNAVAILABLE) {
                    // Server unavailable - wait and reconnect
                    try {
                        Thread.sleep(500);
                        reconnect();
                        continue; // Retry after reconnect
                    } catch (Exception reconnectEx) {
                        if (attempt == maxRetries - 1) {
                            throw new ApiExceptionMT5("gRPC unavailable and reconnection failed: " + reconnectEx.getMessage());
                        }
                        continue;
                    }
                } else {
                    throw new ApiExceptionMT5("gRPC call failed: " + e.getMessage());
                }
            }

            // Check if response has terminal error that requires reconnect
            MrpcMt5Error.Error error = errorExtractor.getError(response);
            if (error != null) {
                String errorCode = error.getErrorCode();
                if ("TERMINAL_INSTANCE_NOT_FOUND".equals(errorCode) ||
                    "TERMINAL_REGISTRY_TERMINAL_NOT_FOUND".equals(errorCode)) {
                    // Terminal instance lost - wait and reconnect
                    try {
                        Thread.sleep(500);
                        reconnect();
                        continue; // Retry after reconnect
                    } catch (Exception reconnectEx) {
                        if (attempt == maxRetries - 1) {
                            throw new ApiExceptionMT5("Terminal instance lost and reconnection failed: " + reconnectEx.getMessage());
                        }
                        continue;
                    }
                } else {
                    // Other API error - throw immediately
                    throw new ApiExceptionMT5(error);
                }
            }

            // Success - return response
            return response;
        }

        throw new ApiExceptionMT5("Operation failed after " + maxRetries + " attempts");
    }

    /**
     * Execute streaming gRPC call with automatic reconnection.
     * For server-streaming RPCs (subscriptions).
     * Used for all streaming (server→client) RPC calls.
     *
     * @param responseObserver Observer to receive streaming responses
     * @param streamCall The streaming call to execute
     * @throws ApiExceptionMT5 if stream fails
     */
    private <T> void executeStreamWithReconnect(
            StreamObserver<T> responseObserver,
            java.util.function.Consumer<Metadata> streamCall) throws ApiExceptionMT5 {

        if (id == null) {
            throw new ApiExceptionMT5("Please call connect() method first");
        }

        Metadata headers = getMetadataHeaders();

        try {
            streamCall.accept(headers);
        } catch (StatusRuntimeException e) {
            // Try reconnect and retry once
            try {
                reconnect();
                headers = getMetadataHeaders();
                streamCall.accept(headers);
            } catch (Exception reconnectEx) {
                throw new ApiExceptionMT5("Stream call failed and reconnection failed: " + e.getMessage());
            }
        }
    }


    //endregion


    //==============================================
    // region CONNECTION METHODS
    //==============================================


    /**
     * Connect to MT5 terminal by host and port
     *
     * @param host MT5 server host (IP or domain)
     * @param port MT5 server port (default 443)
     * @param baseChartSymbol Base chart symbol (e.g. "EURUSD")
     * @param waitForTerminalIsAlive Wait for terminal to be ready
     * @param timeoutSeconds Timeout in seconds
     * @return Connection response with terminal instance GUID
     * @throws ApiExceptionMT5 if connection fails
     */
    public Mt5TermApiConnection.ConnectReply connect(
            String host,
            int port,
            String baseChartSymbol,
            boolean waitForTerminalIsAlive,
            int timeoutSeconds) throws ApiExceptionMT5 {

        Mt5TermApiConnection.ConnectRequest request = Mt5TermApiConnection.ConnectRequest.newBuilder()
                .setUser(user)
                .setPassword(password)
                .setHost(host)
                .setPort(port)
                .setBaseChartSymbol(baseChartSymbol)
                .setWaitForTerminalIsAlive(waitForTerminalIsAlive)
                .setTerminalReadinessWaitingTimeoutSeconds(timeoutSeconds)
                .build();

        // Only send headers if we have an existing connection ID
        Mt5TermApiConnection.ConnectReply response;
        if (id != null) {
            Metadata headers = getMetadataHeaders();
            response = connectionClient
                    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                    .connect(request);
        } else {
            response = connectionClient.connect(request);
        }

        if (response.hasError()) {
            throw new ApiExceptionMT5(response.getError());
        }

        // Save connection parameters
        this.host = host;
        this.port = port;
        this.baseChartSymbol = baseChartSymbol;
        this.connectTimeoutSeconds = timeoutSeconds;
        this.id = UUID.fromString(response.getData().getTerminalInstanceGuid());

        return response;
    }

    /**
     * Simplified connect method with default parameters
     */
    public Mt5TermApiConnection.ConnectReply connect(String host, int port, String baseChartSymbol) throws ApiExceptionMT5 {
        return connect(host, port, baseChartSymbol, true, 30);
    }

    /**
     * Connect to MT5 terminal by server name (MT cluster name)
     *
     * @param serverName MT5 server name from terminal
     * @param baseChartSymbol Base chart symbol (e.g. "EURUSD")
     * @param timeoutSeconds Timeout in seconds
     * @return Connection response with terminal instance GUID
     * @throws ApiExceptionMT5 if connection fails
     */
    public Mt5TermApiConnection.ConnectExReply connectByServerName(
            String serverName,
            String baseChartSymbol,
            int timeoutSeconds) throws ApiExceptionMT5 {

        Mt5TermApiConnection.ConnectExRequest request = Mt5TermApiConnection.ConnectExRequest.newBuilder()
                .setUser(user)
                .setPassword(password)
                .setMtClusterName(serverName)
                .setBaseChartSymbol(baseChartSymbol)
                .setTerminalReadinessWaitingTimeoutSeconds(timeoutSeconds)
                .build();

        // Only send headers if we have an existing connection ID
        Mt5TermApiConnection.ConnectExReply response;
        if (id != null) {
            Metadata headers = getMetadataHeaders();
            response = connectionClient
                    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                    .connectEx(request);
        } else {
            response = connectionClient.connectEx(request);
        }

        if (response.hasError()) {
            throw new ApiExceptionMT5(response.getError());
        }

        // Save connection parameters
        this.serverName = serverName;
        this.baseChartSymbol = baseChartSymbol;
        this.connectTimeoutSeconds = timeoutSeconds;
        this.id = UUID.fromString(response.getData().getTerminalInstanceGuid());

        return response;
    }

    /**
     * Simplified connectByServerName with default timeout
     */
    public Mt5TermApiConnection.ConnectExReply connectByServerName(String serverName, String baseChartSymbol) throws ApiExceptionMT5 {
        return connectByServerName(serverName, baseChartSymbol, 30);
    }

    /**
     * Reconnect to MT5 terminal (recreate terminal instance)
     * Used internally by executeWithReconnect
     *
     * @throws ApiExceptionMT5 if reconnection fails
     */
    private void reconnect() throws ApiExceptionMT5 {
        if (serverName != null) {
            // Reconnect by server name
            connectByServerName(serverName, baseChartSymbol, connectTimeoutSeconds);
        } else if (host != null) {
            // Reconnect by host/port
            connect(host, port, baseChartSymbol, true, connectTimeoutSeconds);
        } else {
            throw new ApiExceptionMT5("Cannot reconnect: no connection parameters saved");
        }
    }

    /**
     * Check if terminal connection is alive
     *
     * @return Check connection response
     * @throws ApiExceptionMT5 if check fails
     */
    public Mt5TermApiConnection.CheckConnectReply checkConnect() throws ApiExceptionMT5 {
        Mt5TermApiConnection.CheckConnectRequest request =
                Mt5TermApiConnection.CheckConnectRequest.newBuilder().build();

        Metadata headers = getMetadataHeaders();

        Mt5TermApiConnection.CheckConnectReply response = connectionClient
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                .checkConnect(request);

        if (response.hasError()) {
            throw new ApiExceptionMT5(response.getError());
        }

        return response;
    }

    /**
     * Disconnect from MT5 terminal
     *
     * @return Disconnect response
     * @throws ApiExceptionMT5 if disconnect fails
     */
    public Mt5TermApiConnection.DisconnectReply disconnect() throws ApiExceptionMT5 {
        Mt5TermApiConnection.DisconnectRequest request =
                Mt5TermApiConnection.DisconnectRequest.newBuilder().build();

        Metadata headers = getMetadataHeaders();

        Mt5TermApiConnection.DisconnectReply response = connectionClient
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                .disconnect(request);

        if (response.hasError()) {
            throw new ApiExceptionMT5(response.getError());
        }

        // Clear connection parameters
        this.host = null;
        this.port = 0;
        this.serverName = null;

        return response;
    }

    /**
     * Connect to MT5 terminal through proxy server
     *
     * @param host MT5 server host
     * @param port MT5 server port
     * @param proxyUser Proxy username
     * @param proxyPassword Proxy password
     * @param proxyHost Proxy server host
     * @param proxyPort Proxy server port
     * @param proxyType Proxy type (Socks5, Socks4, Https, None)
     * @param baseChartSymbol Base chart symbol
     * @param waitForTerminalIsAlive Wait for terminal readiness
     * @param timeoutSeconds Terminal readiness timeout
     * @return Connection response with terminal GUID
     * @throws ApiExceptionMT5 if connection fails
     */
    public Mt5TermApiConnection.ConnectProxyReply connectProxy(
            String host,
            int port,
            String proxyUser,
            String proxyPassword,
            String proxyHost,
            int proxyPort,
            Mt5TermApiConnection.ProxyTypes proxyType,
            String baseChartSymbol,
            boolean waitForTerminalIsAlive,
            int timeoutSeconds) throws ApiExceptionMT5 {

        Mt5TermApiConnection.ConnectProxyRequest.Builder builder =
                Mt5TermApiConnection.ConnectProxyRequest.newBuilder()
                        .setUser(this.user)
                        .setPassword(this.password)
                        .setHost(host)
                        .setPort(port)
                        .setProxyUser(proxyUser)
                        .setProxyPassword(proxyPassword)
                        .setProxyHost(proxyHost)
                        .setProxyPort(proxyPort)
                        .setProxyType(proxyType);

        if (baseChartSymbol != null) {
            builder.setBaseChartSymbol(baseChartSymbol);
        }
        builder.setWaitForTerminalIsAlive(waitForTerminalIsAlive);
        builder.setTerminalReadinessWaitingTimeoutSeconds(timeoutSeconds);

        Mt5TermApiConnection.ConnectProxyRequest request = builder.build();
        Metadata headers = getMetadataHeaders();

        Mt5TermApiConnection.ConnectProxyReply response = connectionClient
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                .connectProxy(request);

        if (response.hasError()) {
            throw new ApiExceptionMT5(response.getError());
        }

        this.host = host;
        this.port = port;
        this.baseChartSymbol = baseChartSymbol;
        this.connectTimeoutSeconds = timeoutSeconds;
        this.serverName = null;

        return response;
    }

    /**
     * Reconnect to terminal (recreate terminal instance if needed)
     *
     * @param forceReconnection Force reconnection even if terminal is alive
     * @param timeoutSeconds Terminal readiness timeout
     * @return Reconnect response with terminal info
     * @throws ApiExceptionMT5 if reconnection fails
     */
    public Mt5TermApiConnection.ReconnectReply reconnect(
            boolean forceReconnection,
            int timeoutSeconds) throws ApiExceptionMT5 {

        Mt5TermApiConnection.ReconnectRequest request =
                Mt5TermApiConnection.ReconnectRequest.newBuilder()
                        .setForceReconnection(forceReconnection)
                        .setTerminalReadinessWaitingTimeoutSeconds(timeoutSeconds)
                        .build();

        Metadata headers = getMetadataHeaders();

        Mt5TermApiConnection.ReconnectReply response = connectionClient
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                .reconnect(request);

        if (response.hasError()) {
            throw new ApiExceptionMT5(response.getError());
        }

        return response;
    }


    //endregion


    //==============================================
    // region ACCOUNT INFORMATION (4 methods)
    //==============================================


    /**
     * Gets the complete summary of the trading account in a single call.
     * Returns all essential account information including balance, equity, margin, profit, leverage, and currency.
     * This is the recommended method for retrieving account data as it minimizes network calls.
     *
     * @return Account summary containing all account properties
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.AccountSummaryReply accountSummary() throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountHelper.AccountSummaryRequest request =
                            Mt5TermApiAccountHelper.AccountSummaryRequest.newBuilder().build();
                    return accountClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .accountSummary(request);
                },
                 response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Retrieves a specific double-precision property of the trading account.
     * Use this to get individual numeric values such as BALANCE, EQUITY, MARGIN, PROFIT, etc.
     *
     * @param propertyType The specific property to retrieve (e.g., ACCOUNT_BALANCE, ACCOUNT_EQUITY)
     * @return Response containing the requested double value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountInformation.AccountInfoDoubleReply accountInfoDouble(
            Mt5TermApiAccountInformation.AccountInfoDoublePropertyType propertyType) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountInformation.AccountInfoDoubleRequest request =
                            Mt5TermApiAccountInformation.AccountInfoDoubleRequest.newBuilder()
                                    .setPropertyId(propertyType)
                                    .build();
                    return accountInformationClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .accountInfoDouble(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Retrieves a specific integer property of the trading account.
     * Use this to get values such as LOGIN, LEVERAGE, TRADE_MODE, LIMIT_ORDERS, etc.
     *
     * @param propertyType The specific property to retrieve (e.g., ACCOUNT_LOGIN, ACCOUNT_LEVERAGE)
     * @return Response containing the requested integer value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountInformation.AccountInfoIntegerReply accountInfoInteger(
            Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType propertyType) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountInformation.AccountInfoIntegerRequest request =
                            Mt5TermApiAccountInformation.AccountInfoIntegerRequest.newBuilder()
                                    .setPropertyId(propertyType)
                                    .build();
                    return accountInformationClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .accountInfoInteger(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Retrieves a specific string property of the trading account.
     * Use this to get textual information such as account NAME, SERVER, CURRENCY, or COMPANY.
     *
     * @param propertyType The specific property to retrieve (e.g., ACCOUNT_NAME, ACCOUNT_SERVER)
     * @return Response containing the requested string value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountInformation.AccountInfoStringReply accountInfoString(
            Mt5TermApiAccountInformation.AccountInfoStringPropertyType propertyType) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountInformation.AccountInfoStringRequest request =
                            Mt5TermApiAccountInformation.AccountInfoStringRequest.newBuilder()
                                    .setPropertyId(propertyType)
                                    .build();
                    return accountInformationClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .accountInfoString(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }


    //endregion


    //=============================================================
    // region SYMBOL INFORMATION & OPERATIONS (8 methods)
    //=============================================================


    /**
     * Gets the current market quote (Bid/Ask prices) for a trading symbol.
     * This is a convenience method that internally calls symbolInfoTick().
     *
     * @param symbol Trading symbol name (e.g., "EURUSD", "GBPUSD")
     * @return Current tick data including bid, ask, last price, and volume
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoTickRequestReply quote(String symbol) throws ApiExceptionMT5 {
        return symbolInfoTick(symbol);
    }

    /**
     * Get quotes for multiple symbols
     * Note: Calls symbolInfoTick for each symbol sequentially
     *
     * @param symbols Array of symbol names
     * @return Array of symbol info ticks
     * @throws ApiExceptionMT5 if any call fails
     */
    public Mt5TermApiMarketInfo.SymbolInfoTickRequestReply[] quoteMany(String[] symbols) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.SymbolInfoTickRequestReply[] results =
                new Mt5TermApiMarketInfo.SymbolInfoTickRequestReply[symbols.length];
        for (int i = 0; i < symbols.length; i++) {
            results[i] = symbolInfoTick(symbols[i]);
        }
        return results;
    }

    /**
     * Gets the total count of available symbols on the MT5 server.
     * Returns either all symbols known to the server or only those currently shown in the MarketWatch window.
     * Use this to determine how many symbols are available before requesting detailed symbol information.
     *
     * @param selectedOnly If true, returns only symbols visible in MarketWatch; if false, returns all available symbols
     * @return Total number of symbols matching the filter criteria
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolsTotalReply symbolsTotal(boolean selectedOnly) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolsTotalRequest request =
                            Mt5TermApiMarketInfo.SymbolsTotalRequest.newBuilder()
                                    .setMode(selectedOnly)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolsTotal(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets the latest tick data for a specified symbol.
     * Returns real-time market information including current bid/ask prices, last trade price, volume, and timestamp.
     * This is the primary method for retrieving current market prices for trading decisions.
     *
     * @param symbol Symbol name (e.g., "EURUSD", "GBPUSD")
     * @return Latest tick data with bid/ask prices, last price, volume, and time
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoTickRequestReply symbolInfoTick(String symbol) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolInfoTickRequest request =
                            Mt5TermApiMarketInfo.SymbolInfoTickRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolInfoTick(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Selects or deselects a symbol in the Market Watch window.
     * Symbols must be selected in Market Watch to receive price updates and place trades.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param select True to select symbol, false to remove from Market Watch
     * @return Reply with success status
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolSelectReply symbolSelect(String symbol, boolean select) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolSelectRequest request =
                            Mt5TermApiMarketInfo.SymbolSelectRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setSelect(select)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolSelect(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets a double property value for a specified symbol.
     * Used to retrieve numeric properties like point size, spread, volume limits, etc.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param property Property type (SYMBOL_POINT, SYMBOL_VOLUME_MIN, etc.)
     * @return Reply with the property value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoDoubleReply symbolInfoDouble(
            String symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty property) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolInfoDoubleRequest request =
                            Mt5TermApiMarketInfo.SymbolInfoDoubleRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setType(property)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolInfoDouble(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets an integer property value for a specified symbol.
     * Used to retrieve integer properties like digits, spread in points, trade mode, etc.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param property Property type (SYMBOL_DIGITS, SYMBOL_SPREAD, etc.)
     * @return Reply with the property value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoIntegerReply symbolInfoInteger(
            String symbol,
            Mt5TermApiMarketInfo.SymbolInfoIntegerProperty property) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolInfoIntegerRequest request =
                            Mt5TermApiMarketInfo.SymbolInfoIntegerRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setType(property)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolInfoInteger(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets a string property value for a specified symbol.
     * Used to retrieve string properties like base currency, description, path, etc.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param property Property type (SYMBOL_CURRENCY_BASE, SYMBOL_DESCRIPTION, etc.)
     * @return Reply with the property value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoStringReply symbolInfoString(
            String symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty property) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolInfoStringRequest request =
                            Mt5TermApiMarketInfo.SymbolInfoStringRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setType(property)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolInfoString(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Checks if a symbol exists on the MT5 server.
     * Returns whether the specified symbol name is available for trading.
     *
     * @param symbolName Symbol name to check (e.g., "EURUSD")
     * @return Reply with existence status
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolExistReply symbolExist(String symbolName) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolExistRequest request =
                            Mt5TermApiMarketInfo.SymbolExistRequest.newBuilder()
                                    .setName(symbolName)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolExist(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets the symbol name by its position in the symbols list.
     * Returns the name of the symbol at the specified index.
     *
     * @param index Position in the symbols list (0-based index)
     * @param selectedOnly If true, searches only in Market Watch; if false, searches all symbols
     * @return Reply with symbol name
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolNameReply symbolName(int index, boolean selectedOnly) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolNameRequest request =
                            Mt5TermApiMarketInfo.SymbolNameRequest.newBuilder()
                                    .setIndex(index)
                                    .setSelected(selectedOnly)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolName(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Checks if symbol data is synchronized with the trade server.
     * Returns whether the symbol's price and market data is currently up to date.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Reply with synchronization status
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolIsSynchronizedReply symbolIsSynchronized(String symbol) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest request =
                            Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolIsSynchronized(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets the margin rate required for opening positions on a specified symbol.
     * Returns the margin multiplier applied for buy and sell orders, which varies based on order type.
     * Use this to calculate the exact margin requirement before placing orders.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param orderType Type of order (BUY, SELL, etc.)
     * @return Margin rate information including initial and maintenance margin multipliers
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoMarginRateReply symbolInfoMarginRate(
            String symbol,
            Mt5TermApiMarketInfo.ENUM_ORDER_TYPE orderType) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest request =
                            Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setOrderType(orderType)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolInfoMarginRate(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets the quote (pricing) session schedule for a symbol on a specific day.
     * Returns the start and end times when price quotes are available for the symbol.
     * Use this to determine when you can expect to receive price updates for market data.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param dayOfWeek Day of the week (SUNDAY=0, MONDAY=1, ..., SATURDAY=6)
     * @param sessionIndex Session index (0 for first session, 1 for second session if multiple sessions exist)
     * @return Quote session times including start and end times in seconds since midnight
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply symbolInfoSessionQuote(
            String symbol,
            Mt5TermApiMarketInfo.DayOfWeek dayOfWeek,
            int sessionIndex) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest request =
                            Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setDayOfWeek(dayOfWeek)
                                    .setSessionIndex(sessionIndex)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolInfoSessionQuote(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets the trading session schedule for a symbol on a specific day.
     * Returns the start and end times when trading operations are allowed for the symbol.
     * Use this to determine when you can open/close positions and place orders.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param dayOfWeek Day of the week (SUNDAY=0, MONDAY=1, ..., SATURDAY=6)
     * @param sessionIndex Session index (0 for first session, 1 for second session if multiple sessions exist)
     * @return Trade session times including start and end times in seconds since midnight
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply symbolInfoSessionTrade(
            String symbol,
            Mt5TermApiMarketInfo.DayOfWeek dayOfWeek,
            int sessionIndex) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest request =
                            Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setDayOfWeek(dayOfWeek)
                                    .setSessionIndex(sessionIndex)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolInfoSessionTrade(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets comprehensive parameter details for one or more symbols with pagination support.
     * Returns extensive symbol information including contract specifications, trading conditions, and current state.
     * Use this when you need complete symbol details beyond basic tick data, especially for multiple symbols.
     *
     * @param symbolName Symbol name to filter (null to retrieve all symbols)
     * @param sortType Sort order for results (by name, volume, etc.)
     * @param pageNumber Page number for pagination (0-based index)
     * @param itemsPerPage Number of symbols per page (0 to retrieve all without pagination)
     * @return Detailed symbol parameters including specs, margins, session times, and current state
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.SymbolParamsManyReply symbolParamsMany(
            String symbolName,
            Mt5TermApiAccountHelper.AH_SYMBOL_PARAMS_MANY_SORT_TYPE sortType,
            int pageNumber,
            int itemsPerPage) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountHelper.SymbolParamsManyRequest.Builder builder =
                            Mt5TermApiAccountHelper.SymbolParamsManyRequest.newBuilder();
                    if (symbolName != null) {
                        builder.setSymbolName(symbolName);
                    }
                    if (sortType != null) {
                        builder.setSortType(sortType);
                    }
                    builder.setPageNumber(pageNumber);
                    builder.setItemsPerPage(itemsPerPage);

                    Mt5TermApiAccountHelper.SymbolParamsManyRequest request = builder.build();
                    return accountClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .symbolParamsMany(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }


    //endregion


    //=============================================================
    // region POSITIONS & ORDERS INFORMATION (5 methods)
    //=============================================================


    /**
     * Gets the total count of currently open positions on the account.
     * Returns a simple count of all active positions regardless of symbol.
     * Use this for quick checks of position count before retrieving detailed position information.
     *
     * @return Total number of open positions
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.PositionsTotalReply positionsTotal() throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    com.google.protobuf.Empty request = com.google.protobuf.Empty.newBuilder().build();
                    return tradeFunctionsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .positionsTotal(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets complete details for all currently open positions and pending orders.
     * Returns comprehensive information including symbols, volumes, prices, profits, and timestamps.
     * Use this to retrieve the full state of all active trading operations with sorting options.
     *
     * @param sortType Sort order for results (by symbol, open time, profit, etc.)
     * @return Complete list of open positions and pending orders with all details
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.OpenedOrdersReply openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE sortType) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountHelper.OpenedOrdersRequest request =
                            Mt5TermApiAccountHelper.OpenedOrdersRequest.newBuilder()
                                    .setInputSortMode(sortType)
                                    .build();
                    return accountClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .openedOrders(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets only the ticket numbers of all open positions and pending orders.
     * This lightweight method returns just the ticket IDs without full position details.
     * Use this when you only need to track which positions exist or for efficient position monitoring.
     *
     * @return List of ticket numbers for all open positions and pending orders
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.OpenedOrdersTicketsReply openedOrdersTickets() throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest request =
                            Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest.newBuilder().build();
                    return accountClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .openedOrdersTickets(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets historical order data within a specified time range with pagination support.
     * Returns all orders (executed, canceled, rejected) that occurred between the given timestamps.
     * Use this to analyze past trading activity, generate reports, or track order execution history.
     *
     * @param from Start time for the query (server time)
     * @param to End time for the query (server time)
     * @param sortType Sort order for results (by time, symbol, etc.)
     * @param pageNumber Page number for pagination (0-based index)
     * @param itemsPerPage Number of orders per page (0 to retrieve all without pagination)
     * @return Historical order data with pagination metadata
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.OrderHistoryReply orderHistory(
            com.google.protobuf.Timestamp from,
            com.google.protobuf.Timestamp to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE sortType,
            int pageNumber,
            int itemsPerPage) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountHelper.OrderHistoryRequest request =
                            Mt5TermApiAccountHelper.OrderHistoryRequest.newBuilder()
                                    .setInputFrom(from)
                                    .setInputTo(to)
                                    .setInputSortMode(sortType)
                                    .setPageNumber(pageNumber)
                                    .setItemsPerPage(itemsPerPage)
                                    .build();
                    return accountClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .orderHistory(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets historical position data with optional time filtering and pagination.
     * Returns all closed positions with complete details including entry/exit prices, profits, and durations.
     * Use this for performance analysis, reporting, or tracking past trading results.
     *
     * @param sortType Sort order for results (by open time, close time, profit, etc.)
     * @param positionOpenTimeFrom Optional start time filter for position open time (null for no filter)
     * @param positionOpenTimeTo Optional end time filter for position open time (null for no filter)
     * @param pageNumber Optional page number for pagination (null for first page, 0-based index)
     * @param itemsPerPage Optional number of positions per page (null or 0 to retrieve all)
     * @return Historical position data with complete trading statistics
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.PositionsHistoryReply positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE sortType,
            com.google.protobuf.Timestamp positionOpenTimeFrom,
            com.google.protobuf.Timestamp positionOpenTimeTo,
            Integer pageNumber,
            Integer itemsPerPage) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountHelper.PositionsHistoryRequest.Builder builder =
                            Mt5TermApiAccountHelper.PositionsHistoryRequest.newBuilder()
                                    .setSortType(sortType);

                    if (positionOpenTimeFrom != null) {
                        builder.setPositionOpenTimeFrom(positionOpenTimeFrom);
                    }
                    if (positionOpenTimeTo != null) {
                        builder.setPositionOpenTimeTo(positionOpenTimeTo);
                    }
                    if (pageNumber != null) {
                        builder.setPageNumber(pageNumber);
                    }
                    if (itemsPerPage != null) {
                        builder.setItemsPerPage(itemsPerPage);
                    }

                    Mt5TermApiAccountHelper.PositionsHistoryRequest request = builder.build();
                    return accountClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .positionsHistory(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }


    //endregion

    //=============================================================
    //region MARKET DEPTH (DOM) (3 methods)
    //=============================================================


    /**
     * Subscribes to Market Depth (DOM/Level II) updates for a specified symbol.
     * After subscription, you can retrieve current order book data showing pending buy and sell orders.
     * Use this to access liquidity information and see the market depth before placing large orders.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Subscription confirmation response
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.MarketBookAddReply marketBookAdd(String symbol) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.MarketBookAddRequest request =
                            Mt5TermApiMarketInfo.MarketBookAddRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .marketBookAdd(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Gets the current Market Depth (order book) data for a subscribed symbol.
     * Returns pending buy and sell orders with prices and volumes from the order book.
     * Use this to analyze liquidity, identify support/resistance levels, or optimize order placement.
     *
     * @param symbol Symbol name (must be subscribed via marketBookAdd first)
     * @return Market book data containing arrays of buy and sell orders with prices and volumes
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.MarketBookGetReply marketBookGet(String symbol) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.MarketBookGetRequest request =
                            Mt5TermApiMarketInfo.MarketBookGetRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .marketBookGet(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Unsubscribes from Market Depth updates for a specified symbol.
     * Stops receiving order book data and releases associated resources.
     * Use this when you no longer need DOM data for a symbol to free up resources.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Unsubscription confirmation response
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.MarketBookReleaseReply marketBookRelease(String symbol) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiMarketInfo.MarketBookReleaseRequest request =
                            Mt5TermApiMarketInfo.MarketBookReleaseRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .build();
                    return marketInfoClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .marketBookRelease(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }


    //endregion


    //=============================================================
    //region MARKET DATA & HISTORY (1 method)
    //=============================================================


    /**
     * Get tick value and size information for multiple symbols
     * Returns tick values, contract size, and tick size for trading calculations
     *
     * @param symbolNames Array of symbol names
     * @return Tick value and size data for all requested symbols
     * @throws ApiExceptionMT5 if call fails
     */
    public Mt5TermApiAccountHelper.TickValueWithSizeReply tickValueWithSize(String[] symbolNames) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiAccountHelper.TickValueWithSizeRequest request =
                            Mt5TermApiAccountHelper.TickValueWithSizeRequest.newBuilder()
                                    .addAllSymbolNames(java.util.Arrays.asList(symbolNames))
                                    .build();
                    return accountClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .tickValueWithSize(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }


    //endregion


    //=============================================================
    //region TRADING OPERATIONS (3 methods)
    //=============================================================


    /**
     * Sends a trading order to the MT5 server (market or pending order).
     * Use this method to open new positions or place pending orders with specified parameters
     * including symbol, volume, price, stop loss, and take profit levels.
     *
     * @param request Complete order request containing symbol, order type, volume, prices, and other parameters
     * @return Response with the order/deal ticket number if successful
     * @throws ApiExceptionMT5 if the order fails or connection is lost
     */
    public Mt5TermApiTradingHelper.OrderSendReply orderSend(
            Mt5TermApiTradingHelper.OrderSendRequest request) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> tradeClient
                        .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                        .orderSend(request),
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Modifies an existing order or position parameters.
     * Use this to update stop loss, take profit, price, or other parameters of an open position or pending order.
     *
     * @param request Modification request with order ticket and new parameters to update
     * @return Response indicating success or failure of the modification
     * @throws ApiExceptionMT5 if the modification fails or connection is lost
     */
    public Mt5TermApiTradingHelper.OrderModifyReply orderModify(
            Mt5TermApiTradingHelper.OrderModifyRequest request) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> tradeClient
                        .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                        .orderModify(request),
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Closes an open position or deletes a pending order.
     * For positions, you can specify partial closure by providing a volume less than the total position size.
     *
     * @param ticket The ticket number of the order or position to close
     * @param volume Volume to close in lots (use position's full volume to close completely)
     * @param slippage Maximum acceptable price slippage in points
     * @return Response indicating success or failure of the closure
     * @throws ApiExceptionMT5 if the closure fails or connection is lost
     */
    public Mt5TermApiTradingHelper.OrderCloseReply orderClose(
            long ticket,
            double volume,
            int slippage) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiTradingHelper.OrderCloseRequest request =
                            Mt5TermApiTradingHelper.OrderCloseRequest.newBuilder()
                                    .setTicket(ticket)
                                    .setVolume(volume)
                                    .setSlippage(slippage)
                                    .build();
                    return tradeClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .orderClose(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }


    //endregion


    //=============================================================
    //region TRADING CALCULATIONS (3 methods)
    //=============================================================


    /**
     * Calculates the margin required to open a position with specified parameters.
     * Returns the amount of funds needed in account currency to maintain the position.
     * Use this before placing orders to verify sufficient margin and avoid margin call risks.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param orderType Order type (BUY or SELL)
     * @param volume Position volume in lots
     * @param openPrice Expected open price for the position
     * @return Calculated margin requirement in account currency
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.OrderCalcMarginReply orderCalcMargin(
            String symbol,
            Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF orderType,
            double volume,
            double openPrice) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiTradeFunctions.OrderCalcMarginRequest request =
                            Mt5TermApiTradeFunctions.OrderCalcMarginRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setOrderType(orderType)
                                    .setVolume(volume)
                                    .setOpenPrice(openPrice)
                                    .build();
                    return tradeFunctionsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .orderCalcMargin(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Calculates the potential profit or loss for a trading operation.
     * Returns the P&L in account currency for opening at one price and closing at another.
     * Use this to evaluate potential trades, set profit targets, or calculate risk/reward ratios.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param orderType Order type (BUY or SELL)
     * @param volume Position volume in lots
     * @param openPrice Entry price for the position
     * @param closePrice Exit price for the position
     * @return Calculated profit/loss in account currency
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.OrderCalcProfitReply orderCalcProfit(
            String symbol,
            Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF orderType,
            double volume,
            double openPrice,
            double closePrice) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiTradeFunctions.OrderCalcProfitRequest request =
                            Mt5TermApiTradeFunctions.OrderCalcProfitRequest.newBuilder()
                                    .setSymbol(symbol)
                                    .setOrderType(orderType)
                                    .setVolume(volume)
                                    .setOpenPrice(openPrice)
                                    .setClosePrice(closePrice)
                                    .build();
                    return tradeFunctionsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .orderCalcProfit(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Validates a trading request and checks if there are sufficient funds to execute it.
     * Returns detailed calculations including margin requirements, expected profit, and resulting balance.
     * Use this to verify order validity before sending, preventing rejected orders due to insufficient funds.
     *
     * @param tradeRequest Complete trade request with symbol, volume, type, price, and other parameters
     * @return Validation result with margin, profit, balance calculations, and any error codes
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.OrderCheckReply orderCheck(
            Mt5TermApiTradeFunctions.MrpcMqlTradeRequest tradeRequest) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiTradeFunctions.OrderCheckRequest request =
                            Mt5TermApiTradeFunctions.OrderCheckRequest.newBuilder()
                                    .setMqlTradeRequest(tradeRequest)
                                    .build();
                    return tradeFunctionsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .orderCheck(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }


    //endregion


    //=============================================================
    //region CHARTS MANAGEMENT (4 methods)
    //=============================================================


    /**
     * Opens a chart in the MT5 terminal with an Expert Advisor (EA) attached.
     * Creates a new chart for the specified symbol with the given timeframe and automatically attaches an EA with parameters.
     * Use this to programmatically deploy trading robots on specific symbols and timeframes.
     *
     * @param symbolName Symbol name for the chart (e.g., "EURUSD")
     * @param eaFileName Name of the Expert Advisor file (without .ex5 extension)
     * @param chartPeriod Timeframe for the chart (M1, M5, H1, D1, etc.)
     * @param eaParameters List of parameters to pass to the EA (index, type, and value for each parameter)
     * @return Chart ID and EA attachment status
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiCharts.OpenTerminalChartWithEaReply openTerminalChartWithEa(
            String symbolName,
            String eaFileName,
            Mt5TermApiCharts.EnumOpenTerminalChartWithEaChatPeriod chartPeriod,
            java.util.List<Mt5TermApiCharts.OpenTerminalChartWithEaParameter> eaParameters) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiCharts.OpenTerminalChartWithEaRequest request =
                            Mt5TermApiCharts.OpenTerminalChartWithEaRequest.newBuilder()
                                    .setSymbolName(symbolName)
                                    .setEaFileName(eaFileName)
                                    .setChartPeriod(chartPeriod)
                                    .addAllEaParameters(eaParameters)
                                    .build();
                    return chartsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .openTerminalChartWithEa(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Retrieves the input parameters defined in an Expert Advisor.
     * Returns metadata about all configurable parameters including their names and types.
     * Use this to discover which parameters an EA accepts before attaching it to a chart.
     *
     * @param eaFileName Name of the Expert Advisor file (without .ex5 extension)
     * @return EA parameter information including names, types, and whether the EA was found
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiCharts.GetEaParamsReply getEaParams(String eaFileName) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiCharts.GetEaParamsRequest request =
                            Mt5TermApiCharts.GetEaParamsRequest.newBuilder()
                                    .setEaFileName(eaFileName)
                                    .build();
                    return chartsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .getEaParams(request);
                },
                response -> response.hasError() ? response.getError() : null
        );
    }

    /**
     * Opens an internal chart for a symbol with a specified expert mode for receiving trading events.
     * Creates a background chart that can trigger callbacks based on ticks, trades, or profit changes.
     * Use this for event-driven trading logic without displaying a visible chart in the terminal.
     *
     * @param symbolName Symbol name for the chart (e.g., "EURUSD")
     * @param expertMode Event mode (ON_TICK, ON_TRADE, ON_ORDER_PROFIT, ON_OPENED_ORDERS_TICKETS, ON_TRADE_TRANSACTION)
     * @param timerPeriod Optional timer period in milliseconds for periodic events (null if not needed)
     * @param ignoreEmptyData Optional flag to skip events when data hasn't changed (null for default behavior)
     * @return Chart ID for the opened internal chart
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiInternalCharts.OpenChartForSymbolReply openChartForSymbol(
            String symbolName,
            Mt5TermApiInternalCharts.ChartExpertMode expertMode,
            Integer timerPeriod,
            Boolean ignoreEmptyData) throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiInternalCharts.OpenChartForSymbolRequest.Builder builder =
                            Mt5TermApiInternalCharts.OpenChartForSymbolRequest.newBuilder()
                                    .setSymbolName(symbolName)
                                    .setExpertMode(expertMode);
                    if (timerPeriod != null) {
                        builder.setTimerPeriod(timerPeriod);
                    }
                    if (ignoreEmptyData != null) {
                        builder.setIgnoreEmptyData(ignoreEmptyData);
                    }
                    return internalChartsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .openChartForSymbol(builder.build());
                },
                reply -> null
        );
    }

    /**
     * Closes a previously opened internal chart.
     * Removes the chart and stops all associated event callbacks.
     * Use this to clean up resources when you no longer need events from a specific symbol.
     *
     * @param mqlChartId Chart ID returned from openChartForSymbol
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public void closeChartForSymbol(long mqlChartId) throws ApiExceptionMT5 {
        executeWithReconnect(
                headers -> {
                    Mt5TermApiInternalCharts.CloseChartForSymbolRequest request =
                            Mt5TermApiInternalCharts.CloseChartForSymbolRequest.newBuilder()
                                    .setMqlChartId(mqlChartId)
                                    .build();
                    return internalChartsClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .closeChartForSymbol(request);
                },
                reply -> null
        );
    }


    //endregion


    //=============================================================
    // region HEALTH & MONITORING (2 methods)
    //=============================================================


    /**
     * Performs a health check on the MT5 terminal connection.
     * Returns connection status and current server time to verify the terminal is alive and responsive.
     * Use this periodically to monitor connection health and detect disconnections early.
     *
     * @return Health check response with connection status and server time in seconds
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiHealthCheck.HealthCheckReply healthCheck() throws ApiExceptionMT5 {
        return executeWithReconnect(
                headers -> {
                    Mt5TermApiHealthCheck.HealthCheckRequest request =
                            Mt5TermApiHealthCheck.HealthCheckRequest.newBuilder().build();
                    return healthClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .check(request);
                },
                reply -> null
        );
    }

    /**
     * Stops the MT5 terminal from listening for incoming gRPC requests.
     * Gracefully shuts down the terminal's API server while keeping the terminal running.
     * Use this when you want to terminate API access but leave the terminal operational.
     *
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public void stopListening() throws ApiExceptionMT5 {
        executeWithReconnect(
                headers -> {
                    Mt5TermApiHealthCheck.StopListeningRequest request =
                            Mt5TermApiHealthCheck.StopListeningRequest.newBuilder().build();
                    return healthClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .stopListening(request);
                },
                reply -> null
        );
    }


    //endregion


    //=============================================================
    //region REAL-TIME SUBSCRIPTIONS (5 methods)
    //=============================================================


    /**
     * Subscribes to real-time tick updates for one or more symbols.
     * Receives a continuous stream of price updates (bid, ask, last, volume) whenever prices change.
     * Use this for real-time price monitoring, tick-based trading strategies, or market data feeds.
     *
     * @param symbolNames Array of symbol names to monitor (e.g., ["EURUSD", "GBPUSD"])
     * @param responseObserver Observer to receive streaming tick updates
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onSymbolTick(String[] symbolNames, StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> responseObserver) throws ApiExceptionMT5 {
        executeStreamWithReconnect(
                responseObserver,
                headers -> {
                    Mt5TermApiSubscriptions.OnSymbolTickRequest request =
                            Mt5TermApiSubscriptions.OnSymbolTickRequest.newBuilder()
                                    .addAllSymbolNames(java.util.Arrays.asList(symbolNames))
                                    .build();
                    subscriptionClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .onSymbolTick(request, responseObserver);
                }
        );
    }

    /**
     * Subscribes to trade events whenever a trading operation occurs.
     * Receives notifications when orders are opened, closed, modified, or deleted.
     * Use this to track all trading activity in real-time and react to order execution events.
     *
     * @param responseObserver Observer to receive streaming trade event notifications
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onTrade(StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> responseObserver) throws ApiExceptionMT5 {
        executeStreamWithReconnect(
                responseObserver,
                headers -> {
                    Mt5TermApiSubscriptions.OnTradeRequest request =
                            Mt5TermApiSubscriptions.OnTradeRequest.newBuilder().build();
                    subscriptionClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .onTrade(request, responseObserver);
                }
        );
    }

    /**
     * Subscribes to periodic updates of position profit/loss values.
     * Receives profit updates at regular intervals for all open positions.
     * Use this to monitor unrealized P&L in real-time and implement profit-based exit strategies.
     *
     * @param timerPeriodMilliseconds Update interval in milliseconds
     * @param ignoreEmptyData If true, skips updates when profit values haven't changed
     * @param responseObserver Observer to receive streaming position profit updates
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onPositionProfit(int timerPeriodMilliseconds, boolean ignoreEmptyData, StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> responseObserver) throws ApiExceptionMT5 {
        executeStreamWithReconnect(
                responseObserver,
                headers -> {
                    Mt5TermApiSubscriptions.OnPositionProfitRequest request =
                            Mt5TermApiSubscriptions.OnPositionProfitRequest.newBuilder()
                                    .setTimerPeriodMilliseconds(timerPeriodMilliseconds)
                                    .setIgnoreEmptyData(ignoreEmptyData)
                                    .build();
                    subscriptionClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .onPositionProfit(request, responseObserver);
                }
        );
    }

    /**
     * Subscribes to periodic updates of position and pending order ticket numbers.
     * Receives lists of currently open position tickets and pending order tickets at regular intervals.
     * Use this to efficiently track which positions/orders exist without retrieving full details.
     *
     * @param timerPeriodMilliseconds Update interval in milliseconds
     * @param responseObserver Observer to receive streaming ticket lists
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onPositionsAndPendingOrdersTickets(int timerPeriodMilliseconds, StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> responseObserver) throws ApiExceptionMT5 {
        executeStreamWithReconnect(
                responseObserver,
                headers -> {
                    Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest request =
                            Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest.newBuilder()
                                    .setTimerPeriodMilliseconds(timerPeriodMilliseconds)
                                    .build();
                    subscriptionClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .onPositionsAndPendingOrdersTickets(request, responseObserver);
                }
        );
    }

    /**
     * Subscribes to detailed trade transaction events.
     * Receives comprehensive information about every trade operation including request, result, and execution details.
     * Use this for detailed trade auditing, debugging order execution, or building advanced trading analytics.
     *
     * @param responseObserver Observer to receive streaming trade transaction details
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onTradeTransaction(StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> responseObserver) throws ApiExceptionMT5 {
        executeStreamWithReconnect(
                responseObserver,
                headers -> {
                    Mt5TermApiSubscriptions.OnTradeTransactionRequest request =
                            Mt5TermApiSubscriptions.OnTradeTransactionRequest.newBuilder().build();
                    subscriptionClient
                            .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
                            .onTradeTransaction(request, responseObserver);
                }
        );
    }


    //endregion

    //=============================================================
    //region API METHODS OVERVIEW
    //=============================================================

    /*
     * CONNECTION (6 methods):
     *   - connect()              : Connect to MT5 terminal by host/port
     *   - connectByServerName()  : Connect by MT5 server cluster name
     *   - connectProxy()         : Connect through proxy server (SOCKS5, SOCKS4, HTTPS)
     *   - reconnect()            : Reconnect/recreate terminal instance
     *   - checkConnect()         : Verify connection is alive
     *   - disconnect()           : Disconnect from terminal
     *
     * ACCOUNT INFORMATION (4 methods):
     *   - accountSummary()       : Get complete account info (balance, equity, margin, etc.)
     *   - accountInfoDouble()    : Get specific double property (BALANCE, EQUITY, etc.)
     *   - accountInfoInteger()   : Get specific integer property (LOGIN, LEVERAGE, etc.)
     *   - accountInfoString()    : Get specific string property (NAME, SERVER, CURRENCY)
     *
     * SYMBOL INFORMATION (12 methods):
     *   - quote()                : Get current bid/ask prices for symbol
     *   - quoteMany()            : Get quotes for multiple symbols
     *   - symbolsTotal()         : Get count of available symbols
     *   - symbolInfoTick()       : Get latest tick data with prices and volume
     *   - symbolSelect()         : Select/deselect symbol in Market Watch
     *   - symbolInfoDouble()     : Get symbol double property (POINT, VOLUME_MIN, etc.)
     *   - symbolInfoInteger()    : Get symbol integer property (DIGITS, SPREAD, etc.)
     *   - symbolInfoString()     : Get symbol string property (CURRENCY_BASE, DESCRIPTION, etc.)
     *   - symbolExist()          : Check if symbol exists on server
     *   - symbolName()           : Get symbol name by index
     *   - symbolIsSynchronized() : Check if symbol data is synchronized
     *   - symbolInfoMarginRate() : Get margin requirements for symbol
     *   - symbolInfoSessionQuote(): Get quote session schedule
     *   - symbolInfoSessionTrade(): Get trading session schedule
     *   - symbolParamsMany()     : Get detailed symbol specs with pagination
     *
     * POSITIONS & ORDERS (5 methods):
     *   - positionsTotal()       : Get count of open positions
     *   - openedOrders()         : Get all open positions/orders with details
     *   - openedOrdersTickets()  : Get ticket numbers only (lightweight)
     *   - orderHistory()         : Get historical orders with pagination
     *   - positionsHistory()     : Get closed positions history
     *
     * MARKET DEPTH (3 methods):
     *   - marketBookAdd()        : Subscribe to market depth (DOM)
     *   - marketBookGet()        : Get current order book data
     *   - marketBookRelease()    : Unsubscribe from market depth
     *
     * MARKET DATA (1 method):
     *   - tickValueWithSize()    : Get tick value/size for calculations
     *
     * TRADING OPERATIONS (3 methods):
     *   - orderSend()            : Send market or pending order
     *   - orderModify()          : Modify existing order/position
     *   - orderClose()           : Close position or delete pending order
     *
     * TRADING CALCULATIONS (3 methods):
     *   - orderCalcMargin()      : Calculate required margin for order
     *   - orderCalcProfit()      : Calculate potential profit/loss
     *   - orderCheck()           : Validate order and check funds
     *
     * CHARTS MANAGEMENT (4 methods):
     *   - openTerminalChartWithEa() : Open chart with Expert Advisor
     *   - getEaParams()          : Get EA input parameters
     *   - openChartForSymbol()   : Open internal chart for events
     *   - closeChartForSymbol()  : Close internal chart
     *
     * HEALTH & MONITORING (2 methods):
     *   - healthCheck()          : Check terminal connection status
     *   - stopListening()        : Stop terminal API server
     *
     * REAL-TIME SUBSCRIPTIONS (5 methods):
     *   - onSymbolTick()         : Stream real-time tick updates
     *   - onTrade()              : Stream trade events
     *   - onPositionProfit()     : Stream position profit updates
     *   - onPositionsAndPendingOrdersTickets() : Stream ticket lists
     *   - onTradeTransaction()   : Stream detailed trade transactions
     *
     * Total: 48 trading methods
     */
    
    //endregion

    
    //=============================================================
    //region UTILITY METHODS
    //=============================================================

    /**
     * Close gRPC channel and release resources
     */
    public void close() {
        try {
            grpcChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            grpcChannel.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // GETTERS

    public long getUser() { return user; }
    public String getPassword() { return password; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getServerName() { return serverName; }
    public String getBaseChartSymbol() { return baseChartSymbol; }
    public UUID getId() { return id; }
    public String getGrpcServer() { return grpcServer; }
}
    //endregion
