package io.metarpc.mt5;

import io.grpc.stub.StreamObserver;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;

import java.util.UUID;

/**
 * MT5 Service - High-level wrapper over MT5Account
 *
 * Provides a clean service layer with convenient methods that extract data from Reply objects.
 * This is the recommended API for most applications - it hides gRPC complexity and provides simple return values.
 *
 * Architecture:
 * - MT5Service (this class) - High-level convenience layer
 * - MT5Account - Low-level gRPC client with automatic reconnection
 * - gRPC stubs - Direct protocol buffer communication
 */
public class MT5Service {

    private final MT5Account account;

    /**
     * Create MT5 service with existing account
     *
     * @param account Configured MT5Account instance
     */
    public MT5Service(MT5Account account) {
        this.account = account;
    }

    /**
     * Create MT5 service with credentials (uses default gRPC server)
     *
     * @param user MT5 account number
     * @param password MT5 account password
     */
    public MT5Service(long user, String password) {
        this.account = new MT5Account(user, password);
    }

    /**
     * Create MT5 service with full configuration
     *
     * @param user MT5 account number
     * @param password MT5 account password
     * @param grpcServer gRPC server address
     * @param id Instance ID (null for auto-generation)
     */
    public MT5Service(long user, String password, String grpcServer, UUID id) {
        this.account = new MT5Account(user, password, grpcServer, id);
    }

    /**
     * Get underlying MT5Account for advanced operations
     */
    public MT5Account getAccount() {
        return account;
    }


    //==============================================
    // region CONNECTION METHODS
    //==============================================

    
    /**
     * Connect to MT5 terminal by host and port
     */
    public Mt5TermApiConnection.ConnectData connect(
            String host,
            int port,
            String baseChartSymbol,
            boolean waitForTerminalIsAlive,
            int timeoutSeconds) throws ApiExceptionMT5 {
        return account.connect(host, port, baseChartSymbol, waitForTerminalIsAlive, timeoutSeconds).getData();
    }

    /**
     * Connect with default parameters
     */
    public Mt5TermApiConnection.ConnectData connect(String host, int port, String baseChartSymbol) throws ApiExceptionMT5 {
        return account.connect(host, port, baseChartSymbol).getData();
    }

    /**
     * Connect by server name
     */
    public Mt5TermApiConnection.ConnectData connectByServerName(
            String serverName,
            String baseChartSymbol,
            int timeoutSeconds) throws ApiExceptionMT5 {
        return account.connectByServerName(serverName, baseChartSymbol, timeoutSeconds).getData();
    }

    /**
     * Connect by server name with default timeout
     */
    public Mt5TermApiConnection.ConnectData connectByServerName(String serverName, String baseChartSymbol) throws ApiExceptionMT5 {
        return account.connectByServerName(serverName, baseChartSymbol).getData();
    }

    /**
     * Check if connection is alive
     */
    public boolean isConnected() throws ApiExceptionMT5 {
        return account.checkConnect().getData().getHealthCheck().getIsAlive();
    }

    /**
     * Disconnect from terminal
     */
    public void disconnect() throws ApiExceptionMT5 {
        account.disconnect();
    }

    /**
     * Connect through proxy server
     */
    public Mt5TermApiConnection.ConnectProxyData connectProxy(
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
        return account.connectProxy(host, port, proxyUser, proxyPassword, proxyHost, proxyPort,
                proxyType, baseChartSymbol, waitForTerminalIsAlive, timeoutSeconds).getData();
    }

    /**
     * Reconnect terminal instance
     */
    public Mt5TermApiConnection.ReconnectData reconnect(
            boolean forceReconnection,
            int timeoutSeconds) throws ApiExceptionMT5 {
        return account.reconnect(forceReconnection, timeoutSeconds).getData();
    }

    //endregion


    //==============================================
    // region ACCOUNT INFORMATION
    //==============================================


    /**
     * Get complete account summary
     */
    public Mt5TermApiAccountHelper.AccountSummaryData accountSummary() throws ApiExceptionMT5 {
        return account.accountSummary().getData();
    }

    /**
     * Get specific double property
     */
    public double accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType propertyType) throws ApiExceptionMT5 {
        return account.accountInfoDouble(propertyType).getData().getRequestedValue();
    }

    /**
     * Get specific integer property
     */
    public long accountInfoInteger(Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType propertyType) throws ApiExceptionMT5 {
        return account.accountInfoInteger(propertyType).getData().getRequestedValue();
    }

    /**
     * Get specific string property
     */
    public String accountInfoString(Mt5TermApiAccountInformation.AccountInfoStringPropertyType propertyType) throws ApiExceptionMT5 {
        return account.accountInfoString(propertyType).getData().getRequestedValue();
    }

    // Convenience methods for common account properties
    public double getBalance() throws ApiExceptionMT5 {
        return accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_BALANCE);
    }

    public double getEquity() throws ApiExceptionMT5 {
        return accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_EQUITY);
    }

    public double getMargin() throws ApiExceptionMT5 {
        return accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN);
    }

    public double getFreeMargin() throws ApiExceptionMT5 {
        return accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN_FREE);
    }

    public double getProfit() throws ApiExceptionMT5 {
        return accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_PROFIT);
    }

    public long getLogin() throws ApiExceptionMT5 {
        return accountInfoInteger(Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType.ACCOUNT_LOGIN);
    }

    public long getLeverage() throws ApiExceptionMT5 {
        return accountInfoInteger(Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType.ACCOUNT_LEVERAGE);
    }

    public String getAccountName() throws ApiExceptionMT5 {
        return accountInfoString(Mt5TermApiAccountInformation.AccountInfoStringPropertyType.ACCOUNT_NAME);
    }

    public String getServerName() throws ApiExceptionMT5 {
        return accountInfoString(Mt5TermApiAccountInformation.AccountInfoStringPropertyType.ACCOUNT_SERVER);
    }

    public String getCurrency() throws ApiExceptionMT5 {
        return accountInfoString(Mt5TermApiAccountInformation.AccountInfoStringPropertyType.ACCOUNT_CURRENCY);
    }

    //endregion


    //==============================================
    // region SYMBOL INFORMATION
    //==============================================


    /**
     * Get current quote for symbol
     */
    public Mt5TermApiMarketInfo.MrpcMqlTick quote(String symbol) throws ApiExceptionMT5 {
        return account.quote(symbol).getData();
    }

    /**
     * Get quotes for multiple symbols
     */
    public Mt5TermApiMarketInfo.SymbolInfoTickRequestReply[] quoteMany(String[] symbols) throws ApiExceptionMT5 {
        return account.quoteMany(symbols);
    }

    /**
     * Get total count of symbols
     */
    public int symbolsTotal(boolean selectedOnly) throws ApiExceptionMT5 {
        return account.symbolsTotal(selectedOnly).getData().getTotal();
    }

    /**
     * Get symbol tick data
     */
    public Mt5TermApiMarketInfo.MrpcMqlTick symbolInfoTick(String symbol) throws ApiExceptionMT5 {
        return account.symbolInfoTick(symbol).getData();
    }

    /**
     * Select symbol in Market Watch
     */
    public boolean symbolSelect(String symbol, boolean select) throws ApiExceptionMT5 {
        return account.symbolSelect(symbol, select).getData().getSuccess();
    }

    /**
     * Get symbol double property
     */
    public Mt5TermApiMarketInfo.SymbolInfoDoubleData symbolInfoDouble(
            String symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty property) throws ApiExceptionMT5 {
        return account.symbolInfoDouble(symbol, property).getData();
    }

    /**
     * Get symbol integer property
     */
    public Mt5TermApiMarketInfo.SymbolInfoIntegerData symbolInfoInteger(
            String symbol,
            Mt5TermApiMarketInfo.SymbolInfoIntegerProperty property) throws ApiExceptionMT5 {
        return account.symbolInfoInteger(symbol, property).getData();
    }

    /**
     * Get symbol string property
     */
    public Mt5TermApiMarketInfo.SymbolInfoStringData symbolInfoString(
            String symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty property) throws ApiExceptionMT5 {
        return account.symbolInfoString(symbol, property).getData();
    }

    /**
     * Check if symbol exists
     */
    public boolean symbolExist(String symbolName) throws ApiExceptionMT5 {
        return account.symbolExist(symbolName).getData().getExists();
    }

    /**
     * Get symbol name by index
     */
    public String symbolName(int index, boolean selectedOnly) throws ApiExceptionMT5 {
        return account.symbolName(index, selectedOnly).getData().getName();
    }

    /**
     * Check if symbol is synchronized
     */
    public boolean symbolIsSynchronized(String symbol) throws ApiExceptionMT5 {
        return account.symbolIsSynchronized(symbol).getData().getSynchronized();
    }

    /**
     * Get symbol margin rate
     */
    public Mt5TermApiMarketInfo.SymbolInfoMarginRateData symbolInfoMarginRate(
            String symbol,
            Mt5TermApiMarketInfo.ENUM_ORDER_TYPE orderType) throws ApiExceptionMT5 {
        return account.symbolInfoMarginRate(symbol, orderType).getData();
    }

    /**
     * Get quote session schedule
     */
    public Mt5TermApiMarketInfo.SymbolInfoSessionQuoteData symbolInfoSessionQuote(
            String symbol,
            Mt5TermApiMarketInfo.DayOfWeek dayOfWeek,
            int sessionIndex) throws ApiExceptionMT5 {
        return account.symbolInfoSessionQuote(symbol, dayOfWeek, sessionIndex).getData();
    }

    /**
     * Get trade session schedule
     */
    public Mt5TermApiMarketInfo.SymbolInfoSessionTradeData symbolInfoSessionTrade(
            String symbol,
            Mt5TermApiMarketInfo.DayOfWeek dayOfWeek,
            int sessionIndex) throws ApiExceptionMT5 {
        return account.symbolInfoSessionTrade(symbol, dayOfWeek, sessionIndex).getData();
    }

    /**
     * Get detailed symbol parameters
     */
    public Mt5TermApiAccountHelper.SymbolParamsManyData symbolParamsMany(
            String symbolName,
            Mt5TermApiAccountHelper.AH_SYMBOL_PARAMS_MANY_SORT_TYPE sortType,
            int pageNumber,
            int itemsPerPage) throws ApiExceptionMT5 {
        return account.symbolParamsMany(symbolName, sortType, pageNumber, itemsPerPage).getData();
    }

    //endregion


    //==============================================
    // region POSITIONS & ORDERS
    //==============================================


    /**
     * Get total count of open positions
     */
    public Mt5TermApiTradeFunctions.PositionsTotalData positionsTotal() throws ApiExceptionMT5 {
        return account.positionsTotal().getData();
    }

    /**
     * Get all opened orders and positions
     */
    public Mt5TermApiAccountHelper.OpenedOrdersData openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE sortType) throws ApiExceptionMT5 {
        return account.openedOrders(sortType).getData();
    }

    /**
     * Get opened orders with default sorting
     */
    public Mt5TermApiAccountHelper.OpenedOrdersData openedOrders() throws ApiExceptionMT5 {
        return openedOrders(Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC);
    }

    /**
     * Get ticket numbers only
     */
    public Mt5TermApiAccountHelper.OpenedOrdersTicketsData openedOrdersTickets() throws ApiExceptionMT5 {
        return account.openedOrdersTickets().getData();
    }

    /**
     * Get order history
     */
    public Mt5TermApiAccountHelper.OrdersHistoryData orderHistory(
            com.google.protobuf.Timestamp from,
            com.google.protobuf.Timestamp to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE sortType,
            int pageNumber,
            int itemsPerPage) throws ApiExceptionMT5 {
        return account.orderHistory(from, to, sortType, pageNumber, itemsPerPage).getData();
    }

    /**
     * Get positions history
     */
    public Mt5TermApiAccountHelper.PositionsHistoryData positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE sortType,
            com.google.protobuf.Timestamp positionOpenTimeFrom,
            com.google.protobuf.Timestamp positionOpenTimeTo,
            Integer pageNumber,
            Integer itemsPerPage) throws ApiExceptionMT5 {
        return account.positionsHistory(sortType, positionOpenTimeFrom, positionOpenTimeTo, pageNumber, itemsPerPage).getData();
    }

    //endregion


    //==============================================
    // region MARKET DEPTH
    //==============================================


    /**
     * Subscribe to market depth
     */
    public void marketBookAdd(String symbol) throws ApiExceptionMT5 {
        account.marketBookAdd(symbol);
    }

    /**
     * Get market depth data
     */
    public Mt5TermApiMarketInfo.MarketBookGetData marketBookGet(String symbol) throws ApiExceptionMT5 {
        return account.marketBookGet(symbol).getData();
    }

    /**
     * Unsubscribe from market depth
     */
    public void marketBookRelease(String symbol) throws ApiExceptionMT5 {
        account.marketBookRelease(symbol);
    }

    //endregion


    //==============================================
    // region MARKET DATA
    //=============================================


    /**
     * Get tick value and size
     */
    public Mt5TermApiAccountHelper.TickValueWithSizeData tickValueWithSize(String[] symbolNames) throws ApiExceptionMT5 {
        return account.tickValueWithSize(symbolNames).getData();
    }

    //endregion


    //==============================================
    // region TRADING OPERATIONS
    //==============================================


    /**
     * Send order
     */
    public Mt5TermApiTradingHelper.OrderSendData orderSend(
            Mt5TermApiTradingHelper.OrderSendRequest request) throws ApiExceptionMT5 {
        return account.orderSend(request).getData();
    }

    /**
     * Modify order
     */
    public Mt5TermApiTradingHelper.OrderModifyData orderModify(
            Mt5TermApiTradingHelper.OrderModifyRequest request) throws ApiExceptionMT5 {
        return account.orderModify(request).getData();
    }

    /**
     * Close order (by parameters)
     */
    public Mt5TermApiTradingHelper.OrderCloseData orderClose(
            long ticket,
            double volume,
            int slippage) throws ApiExceptionMT5 {
        return account.orderClose(ticket, volume, slippage).getData();
    }

    /**
     * Close order (by request)
     */
    public Mt5TermApiTradingHelper.OrderCloseData orderClose(
            Mt5TermApiTradingHelper.OrderCloseRequest request) throws ApiExceptionMT5 {
        return account.orderClose(
                request.getTicket(),
                request.getVolume(),
                request.getSlippage()
        ).getData();
    }

    //endregion


    //==============================================
    // region TRADING CALCULATIONS
    //==============================================


    /**
     * Calculate margin
     */
    public double orderCalcMargin(
            String symbol,
            Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF orderType,
            double volume,
            double openPrice) throws ApiExceptionMT5 {
        return account.orderCalcMargin(symbol, orderType, volume, openPrice).getData().getMargin();
    }

    /**
     * Calculate profit
     */
    public double orderCalcProfit(
            String symbol,
            Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF orderType,
            double volume,
            double openPrice,
            double closePrice) throws ApiExceptionMT5 {
        return account.orderCalcProfit(symbol, orderType, volume, openPrice, closePrice).getData().getProfit();
    }

    /**
     * Check order
     */
    public Mt5TermApiTradeFunctions.OrderCheckData orderCheck(
            Mt5TermApiTradeFunctions.MrpcMqlTradeRequest tradeRequest) throws ApiExceptionMT5 {
        return account.orderCheck(tradeRequest).getData();
    }

    //endregion


    //==============================================
    // region CHARTS MANAGEMENT
    //==============================================


    /**
     * Open terminal chart with EA
     */
    public long openTerminalChartWithEa(
            String symbolName,
            String eaFileName,
            Mt5TermApiCharts.EnumOpenTerminalChartWithEaChatPeriod chartPeriod,
            java.util.List<Mt5TermApiCharts.OpenTerminalChartWithEaParameter> eaParameters) throws ApiExceptionMT5 {
        return account.openTerminalChartWithEa(symbolName, eaFileName, chartPeriod, eaParameters).getData().getChartId();
    }

    /**
     * Get EA parameters
     */
    public Mt5TermApiCharts.GetEaParamsData getEaParams(String eaFileName) throws ApiExceptionMT5 {
        return account.getEaParams(eaFileName).getData();
    }

    /**
     * Open internal chart for symbol
     */
    public long openChartForSymbol(
            String symbolName,
            Mt5TermApiInternalCharts.ChartExpertMode expertMode,
            Integer timerPeriod,
            Boolean ignoreEmptyData) throws ApiExceptionMT5 {
        return account.openChartForSymbol(symbolName, expertMode, timerPeriod, ignoreEmptyData).getMqlChartId();
    }

    /**
     * Close chart for symbol
     */
    public void closeChartForSymbol(long mqlChartId) throws ApiExceptionMT5 {
        account.closeChartForSymbol(mqlChartId);
    }

    //endregion


    //==============================================
    // region HEALTH & MONITORING
    //==============================================


    /**
     * Health check - returns full reply with server info
     */
    public Mt5TermApiHealthCheck.HealthCheckReply healthCheck() throws ApiExceptionMT5 {
        return account.healthCheck();
    }

    /**
     * Get terminal server time
     */
    public long getServerTime() throws ApiExceptionMT5 {
        return account.healthCheck().getServerTimeSeconds();
    }

    /**
     * Stop listening
     */
    public void stopListening() throws ApiExceptionMT5 {
        account.stopListening();
    }

    //endregion


    //==============================================
    // region REAL-TIME SUBSCRIPTIONS
    //==============================================


    /**
     * Subscribe to symbol ticks
     */
    public void onSymbolTick(String[] symbolNames, StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> responseObserver) throws ApiExceptionMT5 {
        account.onSymbolTick(symbolNames, responseObserver);
    }

    /**
     * Subscribe to trade events
     */
    public void onTrade(StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> responseObserver) throws ApiExceptionMT5 {
        account.onTrade(responseObserver);
    }

    /**
     * Subscribe to position profit updates
     */
    public void onPositionProfit(int timerPeriodMilliseconds, boolean ignoreEmptyData, StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> responseObserver) throws ApiExceptionMT5 {
        account.onPositionProfit(timerPeriodMilliseconds, ignoreEmptyData, responseObserver);
    }

    /**
     * Subscribe to position/order tickets
     */
    public void onPositionsAndPendingOrdersTickets(int timerPeriodMilliseconds, StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> responseObserver) throws ApiExceptionMT5 {
        account.onPositionsAndPendingOrdersTickets(timerPeriodMilliseconds, responseObserver);
    }

    /**
     * Subscribe to trade transactions
     */
    public void onTradeTransaction(StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> responseObserver) throws ApiExceptionMT5 {
        account.onTradeTransaction(responseObserver);
    }


    //endregion


    //==============================================
    // region UTILITY METHODS
    //==============================================

    /**
     * Close gRPC channel and release resources
     */
    public void close() {
        account.close();
    }

    //endregion
}
