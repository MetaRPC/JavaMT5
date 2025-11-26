package io.metarpc.mt5;

import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;

/**
 * MT5 Sugar - CONVENIENCE LAYER
 *
 * <p>This class provides high-level convenience methods with simplified, intuitive APIs
 * for common trading operations. It's the top tier in our three-tier architecture:</p>
 *
 * <h2>Architecture Tiers:</h2>
 * <ul>
 *   <li><b>MT5Account</b> (LOW-LEVEL) - Direct proto method calls with Request/Response objects</li>
 *   <li><b>MT5Service</b> (WRAPPERS) - Wrapper methods that return data directly</li>
 *   <li><b>MT5Sugar</b> (CONVENIENCE) - High-level sugar methods ← YOU ARE HERE</li>
 * </ul>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *   <li><b>Simple Trading</b> - buyMarket(), sellMarket(), closePosition() with minimal parameters</li>
 *   <li><b>Risk Management</b> - buyByRisk(), calculateVolume() for position sizing</li>
 *   <li><b>Smart Helpers</b> - normalizePrice(), normalizeVolume(), getSpread()</li>
 *   <li><b>Batch Operations</b> - closeAll(), closeAllBuy(), closeAllSell()</li>
 * </ul>
 *
 * <h2>Example Usage:</h2>
 * <pre>{@code
 * // Create full stack
 * MT5Account account = new MT5Account(12345678, "password");
 * MT5Service service = new MT5Service(account);
 * MT5Sugar sugar = new MT5Sugar(service);
 *
 * // Connect
 * account.connect("localhost", 5555, "EURUSD", true, 30);
 *
 * // Simple market order with SL/TP
 * long ticket = sugar.buyMarket("EURUSD", 0.1, 1.08000, 1.09000);
 *
 * // Order with risk management (auto-calculate volume)
 * long ticket2 = sugar.buyByRisk("EURUSD", 100, 50.0); // 100 points SL, $50 risk
 *
 * // Close all positions
 * int closed = sugar.closeAll("EURUSD");
 * }</pre>
 *
 */
public class MT5Sugar {

    private final MT5Service service;

    public MT5Sugar(MT5Service service) {
        if (service == null) {
            throw new NullPointerException("MT5Service cannot be null");
        }
        this.service = service;
    }

    public MT5Service getService() {
        return service;
    }

    public MT5Account getAccount() {
        return service.getAccount();
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // region SYMBOL HELPERS
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Ensures symbol is selected in Market Watch.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @throws ApiExceptionMT5 if operation fails
     */
    public void ensureSymbolSelected(String symbol) throws ApiExceptionMT5 {
        service.symbolSelect(symbol, true);
    }

    /**
     * Gets point size for symbol.
     *
     * @param symbol Symbol name
     * @return Point size (e.g., 0.00001 for EURUSD)
     * @throws ApiExceptionMT5 if request fails
     */
    public double getPoint(String symbol) throws ApiExceptionMT5 {
        return service.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT).getValue();
    }

    /**
     * Gets number of digits after decimal point.
     *
     * @param symbol Symbol name
     * @return Number of digits (e.g., 5 for EURUSD)
     * @throws ApiExceptionMT5 if request fails
     */
    public int getDigits(String symbol) throws ApiExceptionMT5 {
        return (int) service.symbolInfoInteger(symbol, Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS).getValue();
    }

    /**
     * Gets current spread in points.
     *
     * @param symbol Symbol name
     * @return Spread in points
     * @throws ApiExceptionMT5 if request fails
     */
    public int getSpread(String symbol) throws ApiExceptionMT5 {
        return (int) service.symbolInfoInteger(symbol, Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD).getValue();
    }

    /**
     * Normalizes price to symbol's digits.
     *
     * @param symbol Symbol name
     * @param price Price to normalize
     * @return Normalized price
     * @throws ApiExceptionMT5 if request fails
     */
    public double normalizePrice(String symbol, double price) throws ApiExceptionMT5 {
        int digits = getDigits(symbol);
        double multiplier = Math.pow(10, digits);
        return Math.round(price * multiplier) / multiplier;
    }

    /**
     * Normalizes volume to symbol's volume step.
     *
     * @param symbol Symbol name
     * @param volume Volume to normalize
     * @return Normalized volume
     * @throws ApiExceptionMT5 if request fails
     */
    public double normalizeVolume(String symbol, double volume) throws ApiExceptionMT5 {
        double volumeMin = service.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MIN).getValue();
        double volumeMax = service.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MAX).getValue();
        double volumeStep = service.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_STEP).getValue();

        // Clamp to min/max
        volume = Math.max(volumeMin, Math.min(volumeMax, volume));

        // Round to step
        double steps = Math.round(volume / volumeStep);
        return steps * volumeStep;
    }

    /**
     * Converts points to price for given symbol and direction.
     *
     * @param symbol Symbol name
     * @param points Points offset
     * @param isBuy true for buy (add points), false for sell (subtract points)
     * @return Price with offset applied
     * @throws ApiExceptionMT5 if request fails
     */
    public double pointsToPrice(String symbol, double points, boolean isBuy) throws ApiExceptionMT5 {
        double point = getPoint(symbol);
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);

        double basePrice = isBuy ? tick.getAsk() : tick.getBid();
        double offset = points * point;

        return isBuy ? basePrice + offset : basePrice - offset;
    }

    /**
     * Gets current Bid price for symbol.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Current Bid price
     * @throws ApiExceptionMT5 if request fails
     */
    public double getBid(String symbol) throws ApiExceptionMT5 {
        return service.quote(symbol).getBid();
    }

    /**
     * Gets current Ask price for symbol.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Current Ask price
     * @throws ApiExceptionMT5 if request fails
     */
    public double getAsk(String symbol) throws ApiExceptionMT5 {
        return service.quote(symbol).getAsk();
    }

    /**
     * Gets current spread in price units (Ask - Bid).
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Spread in price units
     * @throws ApiExceptionMT5 if request fails
     */
    public double getSpreadPrice(String symbol) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.quote(symbol);
        return tick.getAsk() - tick.getBid();
    }

    //endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region MARKET ORDERS - SIMPLIFIED
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Opens BUY market order with optional SL/TP.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param volume Volume in lots (e.g., 0.1)
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long buyMarket(String symbol, double volume, Double stopLoss, Double takeProfit) throws ApiExceptionMT5 {
        return buyMarket(symbol, volume, stopLoss, takeProfit, "");
    }

    /**
     * Opens BUY market order with optional SL/TP and comment.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @param comment Order comment
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long buyMarket(String symbol, double volume, Double stopLoss, Double takeProfit, String comment) throws ApiExceptionMT5 {
        ensureSymbolSelected(symbol);
        volume = normalizeVolume(symbol, volume);

        Mt5TermApiTradingHelper.OrderSendRequest request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
                .setVolume(volume)
                .setStopLoss(stopLoss != null ? stopLoss : 0.0)
                .setTakeProfit(takeProfit != null ? takeProfit : 0.0)
                .setComment(comment != null ? comment : "")
                .build();

        Mt5TermApiTradingHelper.OrderSendData result = service.orderSend(request);

        if (result.getReturnedCode() != 10009) { // TRADE_RETCODE_DONE
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Order failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }

        return result.getOrder();
    }

    /**
     * Opens SELL market order with optional SL/TP.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long sellMarket(String symbol, double volume, Double stopLoss, Double takeProfit) throws ApiExceptionMT5 {
        return sellMarket(symbol, volume, stopLoss, takeProfit, "");
    }

    /**
     * Opens SELL market order with optional SL/TP and comment.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @param comment Order comment
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long sellMarket(String symbol, double volume, Double stopLoss, Double takeProfit, String comment) throws ApiExceptionMT5 {
        ensureSymbolSelected(symbol);
        volume = normalizeVolume(symbol, volume);

        Mt5TermApiTradingHelper.OrderSendRequest request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL)
                .setVolume(volume)
                .setStopLoss(stopLoss != null ? stopLoss : 0.0)
                .setTakeProfit(takeProfit != null ? takeProfit : 0.0)
                .setComment(comment != null ? comment : "")
                .build();

        Mt5TermApiTradingHelper.OrderSendData result = service.orderSend(request);

        if (result.getReturnedCode() != 10009) { // TRADE_RETCODE_DONE
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Order failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }

        return result.getOrder();
    }


    //endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region PENDING ORDERS - SIMPLIFIED
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Places BUY LIMIT pending order.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param price Entry price
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long buyLimit(String symbol, double volume, double price, Double stopLoss, Double takeProfit) throws ApiExceptionMT5 {
        ensureSymbolSelected(symbol);
        volume = normalizeVolume(symbol, volume);
        price = normalizePrice(symbol, price);

        Mt5TermApiTradingHelper.OrderSendRequest request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY_LIMIT)
                .setVolume(volume)
                .setPrice(price)
                .setStopLoss(stopLoss != null ? stopLoss : 0.0)
                .setTakeProfit(takeProfit != null ? takeProfit : 0.0)
                .build();

        Mt5TermApiTradingHelper.OrderSendData result = service.orderSend(request);

        if (result.getReturnedCode() != 10009) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Order failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }

        return result.getOrder();
    }

    /**
     * Places SELL LIMIT pending order.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param price Entry price
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long sellLimit(String symbol, double volume, double price, Double stopLoss, Double takeProfit) throws ApiExceptionMT5 {
        ensureSymbolSelected(symbol);
        volume = normalizeVolume(symbol, volume);
        price = normalizePrice(symbol, price);

        Mt5TermApiTradingHelper.OrderSendRequest request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL_LIMIT)
                .setVolume(volume)
                .setPrice(price)
                .setStopLoss(stopLoss != null ? stopLoss : 0.0)
                .setTakeProfit(takeProfit != null ? takeProfit : 0.0)
                .build();

        Mt5TermApiTradingHelper.OrderSendData result = service.orderSend(request);

        if (result.getReturnedCode() != 10009) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Order failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }

        return result.getOrder();
    }

    /**
     * Places BUY STOP pending order.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param price Entry price
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long buyStop(String symbol, double volume, double price, Double stopLoss, Double takeProfit) throws ApiExceptionMT5 {
        ensureSymbolSelected(symbol);
        volume = normalizeVolume(symbol, volume);
        price = normalizePrice(symbol, price);

        Mt5TermApiTradingHelper.OrderSendRequest request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY_STOP)
                .setVolume(volume)
                .setPrice(price)
                .setStopLoss(stopLoss != null ? stopLoss : 0.0)
                .setTakeProfit(takeProfit != null ? takeProfit : 0.0)
                .build();

        Mt5TermApiTradingHelper.OrderSendData result = service.orderSend(request);

        if (result.getReturnedCode() != 10009) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Order failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }

        return result.getOrder();
    }

    /**
     * Places SELL STOP pending order.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param price Entry price
     * @param stopLoss Stop Loss price (0 or null = no SL)
     * @param takeProfit Take Profit price (0 or null = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long sellStop(String symbol, double volume, double price, Double stopLoss, Double takeProfit) throws ApiExceptionMT5 {
        ensureSymbolSelected(symbol);
        volume = normalizeVolume(symbol, volume);
        price = normalizePrice(symbol, price);

        Mt5TermApiTradingHelper.OrderSendRequest request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL_STOP)
                .setVolume(volume)
                .setPrice(price)
                .setStopLoss(stopLoss != null ? stopLoss : 0.0)
                .setTakeProfit(takeProfit != null ? takeProfit : 0.0)
                .build();

        Mt5TermApiTradingHelper.OrderSendData result = service.orderSend(request);

        if (result.getReturnedCode() != 10009) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Order failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }

        return result.getOrder();
    }


    //endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region POSITION MANAGEMENT
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Modifies Stop Loss and Take Profit for existing position.
     *
     * @param ticket Order ticket
     * @param stopLoss New Stop Loss price (null = keep current)
     * @param takeProfit New Take Profit price (null = keep current)
     * @throws ApiExceptionMT5 if modification fails
     */
    public void modifyPosition(long ticket, Double stopLoss, Double takeProfit) throws ApiExceptionMT5 {
        if (stopLoss == null && takeProfit == null) {
            throw new IllegalArgumentException("At least one of stopLoss or takeProfit must be provided");
        }

        Mt5TermApiTradingHelper.OrderModifyRequest.Builder builder = Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket);

        if (stopLoss != null) {
            builder.setStopLoss(stopLoss);
        }
        if (takeProfit != null) {
            builder.setTakeProfit(takeProfit);
        }

        Mt5TermApiTradingHelper.OrderModifyData result = service.orderModify(builder.build());

        if (result.getReturnedCode() != 10009) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Modify failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }
    }

    /**
     * Closes position by ticket.
     *
     * @param ticket Order ticket to close
     * @throws ApiExceptionMT5 if close fails
     */
    public void closePosition(long ticket) throws ApiExceptionMT5 {
        closePosition(ticket, null);
    }

    /**
     * Closes position by ticket with optional partial volume.
     *
     * @param ticket Order ticket to close
     * @param volume Volume to close (null = close all)
     * @throws ApiExceptionMT5 if close fails
     */
    public void closePosition(long ticket, Double volume) throws ApiExceptionMT5 {
        Mt5TermApiTradingHelper.OrderCloseRequest request = Mt5TermApiTradingHelper.OrderCloseRequest.newBuilder()
                .setTicket(ticket)
                .setVolume(volume != null ? volume : 0.0)
                .setSlippage(10)
                .build();

        Mt5TermApiTradingHelper.OrderCloseData result = service.orderClose(request);

        if (result.getReturnedCode() != 10009) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Close failed: " + result.getReturnedCodeDescription())
                    .setMqlErrorTradeIntCode(result.getReturnedCode())
                    .build();
            throw new ApiExceptionMT5(error);
        }
    }

    /**
     * Closes all open positions for specified symbol.
     *
     * @param symbol Symbol name (null = all symbols)
     * @return Number of positions closed
     * @throws ApiExceptionMT5 if operation fails
     */
    public int closeAll(String symbol) throws ApiExceptionMT5 {
        return closeAll(symbol, null);
    }

    /**
     * Closes all open positions for specified symbol and direction.
     *
     * @param symbol Symbol name (null = all symbols)
     * @param isBuy true = close only BUY positions, false = close only SELL positions, null = close all
     * @return Number of positions closed
     * @throws ApiExceptionMT5 if operation fails
     */
    public int closeAll(String symbol, Boolean isBuy) throws ApiExceptionMT5 {
        Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
                Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
        );

        int closed = 0;

        for (Mt5TermApiAccountHelper.PositionInfo position : opened.getPositionInfosList()) {
            // Filter by symbol
            if (symbol != null && !position.getSymbol().equalsIgnoreCase(symbol)) {
                continue;
            }

            // Filter by direction
            if (isBuy != null) {
                boolean positionIsBuy = position.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY;
                if (positionIsBuy != isBuy) {
                    continue;
                }
            }

            try {
                closePosition(position.getTicket());
                closed++;
            } catch (ApiExceptionMT5 e) {
                // Continue closing others even if one fails
                System.err.println("Failed to close position " + position.getTicket() + ": " + e.getMessage());
            }
        }

        return closed;
    }

    /**
     * Closes all BUY positions for symbol.
     *
     * @param symbol Symbol name (null = all symbols)
     * @return Number of positions closed
     * @throws ApiExceptionMT5 if operation fails
     */
    public int closeAllBuy(String symbol) throws ApiExceptionMT5 {
        return closeAll(symbol, true);
    }

    /**
     * Closes all SELL positions for symbol.
     *
     * @param symbol Symbol name (null = all symbols)
     * @return Number of positions closed
     * @throws ApiExceptionMT5 if operation fails
     */
    public int closeAllSell(String symbol) throws ApiExceptionMT5 {
        return closeAll(symbol, false);
    }


    // endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region RISK MANAGEMENT
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Calculates volume based on risk in account currency.
     *
     * @param symbol Symbol name
     * @param stopLossPoints Stop loss distance in points
     * @param riskAmount Risk amount in account currency
     * @return Calculated volume in lots
     * @throws ApiExceptionMT5 if calculation fails
     */
    public double calculateVolume(String symbol, double stopLossPoints, double riskAmount) throws ApiExceptionMT5 {
        // Get tick value
        Mt5TermApiAccountHelper.TickValueWithSizeData tickData = service.tickValueWithSize(new String[]{symbol});

        if (tickData.getSymbolTickSizeInfosCount() == 0) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("Failed to get tick value for " + symbol)
                    .build();
            throw new ApiExceptionMT5(error);
        }

        double tickValue = tickData.getSymbolTickSizeInfos(0).getTradeTickValue();
        double tickSize = tickData.getSymbolTickSizeInfos(0).getTradeTickSize();
        double point = getPoint(symbol);

        // Calculate value per lot per point
        double valuePerPoint = (tickValue / tickSize) * point;

        // Calculate volume
        double volume = riskAmount / (stopLossPoints * valuePerPoint);

        return normalizeVolume(symbol, volume);
    }

    /**
     * Opens BUY market order with automatic volume calculation based on risk.
     *
     * @param symbol Symbol name
     * @param stopLossPoints Stop loss distance in points
     * @param riskAmount Risk amount in account currency
     * @param takeProfitPoints Take profit distance in points (0 = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long buyByRisk(String symbol, double stopLossPoints, double riskAmount, double takeProfitPoints) throws ApiExceptionMT5 {
        double volume = calculateVolume(symbol, stopLossPoints, riskAmount);

        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double point = getPoint(symbol);

        double stopLoss = tick.getAsk() - (stopLossPoints * point);
        double takeProfit = takeProfitPoints > 0 ? tick.getAsk() + (takeProfitPoints * point) : 0.0;

        return buyMarket(symbol, volume, stopLoss, takeProfit);
    }

    /**
     * Opens SELL market order with automatic volume calculation based on risk.
     *
     * @param symbol Symbol name
     * @param stopLossPoints Stop loss distance in points
     * @param riskAmount Risk amount in account currency
     * @param takeProfitPoints Take profit distance in points (0 = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long sellByRisk(String symbol, double stopLossPoints, double riskAmount, double takeProfitPoints) throws ApiExceptionMT5 {
        double volume = calculateVolume(symbol, stopLossPoints, riskAmount);

        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double point = getPoint(symbol);

        double stopLoss = tick.getBid() + (stopLossPoints * point);
        double takeProfit = takeProfitPoints > 0 ? tick.getBid() - (takeProfitPoints * point) : 0.0;

        return sellMarket(symbol, volume, stopLoss, takeProfit);
    }


    // endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region PENDING ORDERS WITH POINTS OFFSET
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Places BUY LIMIT order using points offset from current Ask price.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param pointsOffset Points offset from current Ask (negative = below price)
     * @param stopLossPoints Stop loss distance in points (0 = no SL)
     * @param takeProfitPoints Take profit distance in points (0 = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long buyLimitPoints(String symbol, double volume, double pointsOffset,
                               double stopLossPoints, double takeProfitPoints) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double point = getPoint(symbol);

        double price = tick.getAsk() + (pointsOffset * point);
        double stopLoss = stopLossPoints > 0 ? price - (stopLossPoints * point) : 0.0;
        double takeProfit = takeProfitPoints > 0 ? price + (takeProfitPoints * point) : 0.0;

        return buyLimit(symbol, volume, price, stopLoss, takeProfit);
    }

    /**
     * Places SELL LIMIT order using points offset from current Bid price.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param pointsOffset Points offset from current Bid (positive = above price)
     * @param stopLossPoints Stop loss distance in points (0 = no SL)
     * @param takeProfitPoints Take profit distance in points (0 = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long sellLimitPoints(String symbol, double volume, double pointsOffset,
                                double stopLossPoints, double takeProfitPoints) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double point = getPoint(symbol);

        double price = tick.getBid() + (pointsOffset * point);
        double stopLoss = stopLossPoints > 0 ? price + (stopLossPoints * point) : 0.0;
        double takeProfit = takeProfitPoints > 0 ? price - (takeProfitPoints * point) : 0.0;

        return sellLimit(symbol, volume, price, stopLoss, takeProfit);
    }

    /**
     * Places BUY STOP order using points offset from current Ask price.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param pointsOffset Points offset from current Ask (positive = above price)
     * @param stopLossPoints Stop loss distance in points (0 = no SL)
     * @param takeProfitPoints Take profit distance in points (0 = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long buyStopPoints(String symbol, double volume, double pointsOffset,
                              double stopLossPoints, double takeProfitPoints) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double point = getPoint(symbol);

        double price = tick.getAsk() + (pointsOffset * point);
        double stopLoss = stopLossPoints > 0 ? price - (stopLossPoints * point) : 0.0;
        double takeProfit = takeProfitPoints > 0 ? price + (takeProfitPoints * point) : 0.0;

        return buyStop(symbol, volume, price, stopLoss, takeProfit);
    }

    /**
     * Places SELL STOP order using points offset from current Bid price.
     *
     * @param symbol Symbol name
     * @param volume Volume in lots
     * @param pointsOffset Points offset from current Bid (negative = below price)
     * @param stopLossPoints Stop loss distance in points (0 = no SL)
     * @param takeProfitPoints Take profit distance in points (0 = no TP)
     * @return Order ticket number
     * @throws ApiExceptionMT5 if order fails
     */
    public long sellStopPoints(String symbol, double volume, double pointsOffset,
                               double stopLossPoints, double takeProfitPoints) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double point = getPoint(symbol);

        double price = tick.getBid() + (pointsOffset * point);
        double stopLoss = stopLossPoints > 0 ? price + (stopLossPoints * point) : 0.0;
        double takeProfit = takeProfitPoints > 0 ? price - (takeProfitPoints * point) : 0.0;

        return sellStop(symbol, volume, price, stopLoss, takeProfit);
    }


    //endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region ADVANCED BATCH OPERATIONS
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Closes only market positions (not pending orders).
     *
     * @param symbol Symbol name (null = all symbols)
     * @param isBuy true = close only BUY, false = close only SELL, null = close all
     * @return Number of positions closed
     * @throws ApiExceptionMT5 if operation fails
     */
    public int closeAllPositions(String symbol, Boolean isBuy) throws ApiExceptionMT5 {
        Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
                Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
        );

        int closed = 0;

        // Close only positions (not pending orders)
        for (Mt5TermApiAccountHelper.PositionInfo position : opened.getPositionInfosList()) {
            if (symbol != null && !position.getSymbol().equalsIgnoreCase(symbol)) {
                continue;
            }

            if (isBuy != null) {
                boolean positionIsBuy = position.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY;
                if (positionIsBuy != isBuy) {
                    continue;
                }
            }

            try {
                closePosition(position.getTicket());
                closed++;
            } catch (ApiExceptionMT5 e) {
                System.err.println("Failed to close position " + position.getTicket() + ": " + e.getMessage());
            }
        }

        return closed;
    }

    /**
     * Cancels all pending orders (not market positions).
     *
     * @param symbol Symbol name (null = all symbols)
     * @param isBuy true = cancel only BUY orders, false = cancel only SELL orders, null = cancel all
     * @return Number of orders cancelled
     * @throws ApiExceptionMT5 if operation fails
     */
    public int closeAllPending(String symbol, Boolean isBuy) throws ApiExceptionMT5 {
        Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
                Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
        );

        int cancelled = 0;

        // Cancel only pending orders
        for (Mt5TermApiAccountHelper.OpenedOrderInfo order : opened.getOpenedOrdersList()) {
            if (symbol != null && !order.getSymbol().equalsIgnoreCase(symbol)) {
                continue;
            }

            // Check if it's a pending order (not market order)
            int orderTypeValue = order.getType().getNumber();
            boolean isPending = orderTypeValue >= 2 && orderTypeValue <= 7; // LIMIT/STOP orders

            if (!isPending) {
                continue;
            }

            if (isBuy != null) {
                // BUY_LIMIT=2, BUY_STOP=4, BUY_STOP_LIMIT=6
                boolean orderIsBuy = (orderTypeValue == 2 || orderTypeValue == 4 || orderTypeValue == 6);
                if (orderIsBuy != isBuy) {
                    continue;
                }
            }

            try {
                Mt5TermApiTradingHelper.OrderCloseRequest request = Mt5TermApiTradingHelper.OrderCloseRequest.newBuilder()
                        .setTicket(order.getTicket())
                        .setVolume(0)
                        .setSlippage(0)
                        .build();

                service.orderClose(request);
                cancelled++;
            } catch (ApiExceptionMT5 e) {
                System.err.println("Failed to cancel order " + order.getTicket() + ": " + e.getMessage());
            }
        }

        return cancelled;
    }

    /**
     * Cancels all pending orders (alias for closeAllPending).
     *
     * @param symbol Symbol name (null = all symbols)
     * @param isBuy true = cancel only BUY, false = cancel only SELL, null = cancel all
     * @return Number of orders cancelled
     * @throws ApiExceptionMT5 if operation fails
     */
    public int cancelAll(String symbol, Boolean isBuy) throws ApiExceptionMT5 {
        return closeAllPending(symbol, isBuy);
    }


    // endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region ADVANCED HELPERS
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Gets volume limits for symbol (min, max, step).
     *
     * @param symbol Symbol name
     * @return Array with [min, max, step]
     * @throws ApiExceptionMT5 if request fails
     */
    public double[] getVolumeLimits(String symbol) throws ApiExceptionMT5 {
        double volumeMin = service.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MIN).getValue();
        double volumeMax = service.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MAX).getValue();
        double volumeStep = service.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_STEP).getValue();

        return new double[]{volumeMin, volumeMax, volumeStep};
    }

    /**
     * Converts points to pips for symbol.
     * For most pairs: 1 pip = 10 points (5-digit broker)
     * For JPY pairs: 1 pip = 1 point (3-digit broker)
     *
     * @param symbol Symbol name
     * @param points Points to convert
     * @return Pips value
     * @throws ApiExceptionMT5 if request fails
     */
    public double pointsToPips(String symbol, double points) throws ApiExceptionMT5 {
        int digits = getDigits(symbol);

        // JPY pairs (3 digits) or metals (2-3 digits): 1 pip = 1 point
        if (digits <= 3) {
            return points;
        }

        // Standard pairs (5 digits): 1 pip = 10 points
        return points / 10.0;
    }

    /**
     * Calculates price from current market price with points offset.
     *
     * @param symbol Symbol name
     * @param isBuy true for BUY (use Ask), false for SELL (use Bid)
     * @param pointsOffset Points offset (positive = up, negative = down)
     * @return Calculated price
     * @throws ApiExceptionMT5 if request fails
     */
    public double priceFromOffsetPoints(String symbol, boolean isBuy, double pointsOffset) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double point = getPoint(symbol);

        double basePrice = isBuy ? tick.getAsk() : tick.getBid();
        return basePrice + (pointsOffset * point);
    }

    /**
     * Gets tick value and tick size for symbol in one call.
     *
     * @param symbol Symbol name
     * @return Array with [tickValue, tickSize]
     * @throws ApiExceptionMT5 if request fails
     */
    public double[] getTickValueAndSize(String symbol) throws ApiExceptionMT5 {
        Mt5TermApiAccountHelper.TickValueWithSizeData data = service.tickValueWithSize(new String[]{symbol});

        if (data.getSymbolTickSizeInfosCount() == 0) {
            mt5_term_api.MrpcMt5Error.Error error = mt5_term_api.MrpcMt5Error.Error.newBuilder()
                    .setErrorMessage("No tick data available for " + symbol)
                    .build();
            throw new ApiExceptionMT5(error);
        }

        double tickValue = data.getSymbolTickSizeInfos(0).getTradeTickValue();
        double tickSize = data.getSymbolTickSizeInfos(0).getTradeTickSize();

        return new double[]{tickValue, tickSize};
    }


    // endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region ACCOUNT & POSITION HELPERS
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Gets account balance.
     *
     * @return Account balance
     * @throws ApiExceptionMT5 if request fails
     */
    public double getBalance() throws ApiExceptionMT5 {
        return service.getBalance();
    }

    /**
     * Gets account equity.
     *
     * @return Account equity
     * @throws ApiExceptionMT5 if request fails
     */
    public double getEquity() throws ApiExceptionMT5 {
        return service.getEquity();
    }

    /**
     * Gets used margin.
     *
     * @return Used margin
     * @throws ApiExceptionMT5 if request fails
     */
    public double getMargin() throws ApiExceptionMT5 {
        return service.getMargin();
    }

    /**
     * Gets free margin.
     *
     * @return Free margin
     * @throws ApiExceptionMT5 if request fails
     */
    public double getFreeMargin() throws ApiExceptionMT5 {
        return service.getFreeMargin();
    }

    /**
     * Gets current profit/loss.
     *
     * @return Current profit/loss
     * @throws ApiExceptionMT5 if request fails
     */
    public double getProfit() throws ApiExceptionMT5 {
        return service.getProfit();
    }

    /**
     * Checks if there are any open positions.
     *
     * @return true if there are open positions, false otherwise
     * @throws ApiExceptionMT5 if request fails
     */
    public boolean hasOpenPositions() throws ApiExceptionMT5 {
        return service.positionsTotal().getTotalPositions() > 0;
    }

    /**
     * Gets count of open positions.
     *
     * @return Number of open positions
     * @throws ApiExceptionMT5 if request fails
     */
    public int getPositionCount() throws ApiExceptionMT5 {
        return service.positionsTotal().getTotalPositions();
    }

    /**
     * Normalizes price according to symbol's digits (alias for normalizePrice).
     *
     * @param symbol Symbol name
     * @param price Price to normalize
     * @return Normalized price
     * @throws ApiExceptionMT5 if request fails
     */
    public double normalizePriceDigits(String symbol, double price) throws ApiExceptionMT5 {
        return normalizePrice(symbol, price);
    }

    /**
     * Normalizes lots according to symbol's volume step (alias for normalizeVolume).
     *
     * @param symbol Symbol name
     * @param lots Lots to normalize
     * @return Normalized lots
     * @throws ApiExceptionMT5 if request fails
     */
    public double normalizeLots(String symbol, double lots) throws ApiExceptionMT5 {
        return normalizeVolume(symbol, lots);
    }

    /**
     * Creates protobuf Timestamp from epoch seconds.
     *
     * @param epochSeconds Seconds since Unix epoch
     * @return Protobuf Timestamp
     */
    public com.google.protobuf.Timestamp createTimestamp(long epochSeconds) {
        return com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(epochSeconds)
                .build();
    }

    /**
     * Creates protobuf Timestamp from milliseconds.
     *
     * @param epochMillis Milliseconds since Unix epoch
     * @return Protobuf Timestamp
     */
    public com.google.protobuf.Timestamp createTimestampFromMillis(long epochMillis) {
        return com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(epochMillis / 1000)
                .setNanos((int) ((epochMillis % 1000) * 1_000_000))
                .build();
    }

    /**
     * Creates protobuf Timestamp for current time.
     *
     * @return Protobuf Timestamp for now
     */
    public com.google.protobuf.Timestamp createTimestampNow() {
        return createTimestampFromMillis(System.currentTimeMillis());
    }


    // endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region SNAPSHOT HELPERS
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Account snapshot - all key account metrics in one object.
     */
    public static class AccountSnapshot {
        public final long login;
        public final double balance;
        public final double equity;
        public final double margin;
        public final double freeMargin;
        public final double marginLevel;
        public final double profit;
        public final long leverage;
        public final String currency;
        public final String company;

        public AccountSnapshot(long login, double balance, double equity, double margin,
                               double freeMargin, double marginLevel, double profit,
                               long leverage, String currency, String company) {
            this.login = login;
            this.balance = balance;
            this.equity = equity;
            this.margin = margin;
            this.freeMargin = freeMargin;
            this.marginLevel = marginLevel;
            this.profit = profit;
            this.leverage = leverage;
            this.currency = currency;
            this.company = company;
        }

        @Override
        public String toString() {
            return String.format("Account[login=%d, balance=%.2f, equity=%.2f, margin=%.2f, free=%.2f, level=%.2f%%, profit=%.2f, leverage=%d, currency=%s]",
                    login, balance, equity, margin, freeMargin, marginLevel, profit, leverage, currency);
        }
    }

    /**
     * Symbol snapshot - all key symbol metrics in one object.
     */
    public static class SymbolSnapshot {
        public final String name;
        public final double bid;
        public final double ask;
        public final double point;
        public final int digits;
        public final int spread;
        public final double volumeMin;
        public final double volumeMax;
        public final double volumeStep;
        public final double tickValue;
        public final double tickSize;

        public SymbolSnapshot(String name, double bid, double ask, double point, int digits,
                              int spread, double volumeMin, double volumeMax, double volumeStep,
                              double tickValue, double tickSize) {
            this.name = name;
            this.bid = bid;
            this.ask = ask;
            this.point = point;
            this.digits = digits;
            this.spread = spread;
            this.volumeMin = volumeMin;
            this.volumeMax = volumeMax;
            this.volumeStep = volumeStep;
            this.tickValue = tickValue;
            this.tickSize = tickSize;
        }

        @Override
        public String toString() {
            return String.format("Symbol[%s, bid=%.5f, ask=%.5f, spread=%d, point=%.5f, digits=%d, vol=[%.2f-%.2f step %.2f]]",
                    name, bid, ask, spread, point, digits, volumeMin, volumeMax, volumeStep);
        }
    }

    /**
     * Gets complete account snapshot with all key metrics in one call.
     *
     * @return AccountSnapshot object
     * @throws ApiExceptionMT5 if request fails
     */
    public AccountSnapshot getAccountSnapshot() throws ApiExceptionMT5 {
        Mt5TermApiAccountHelper.AccountSummaryData summary = service.accountSummary();

        return new AccountSnapshot(
                summary.getAccountLogin(),
                summary.getAccountBalance(),
                summary.getAccountEquity(),
                service.accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN),
                service.accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN_FREE),
                service.accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN_LEVEL),
                service.accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_PROFIT),
                summary.getAccountLeverage(),
                summary.getAccountCurrency(),
                summary.getAccountCompanyName()
        );
    }

    /**
     * Gets complete symbol snapshot with all key metrics in one call.
     *
     * @param symbol Symbol name
     * @return SymbolSnapshot object
     * @throws ApiExceptionMT5 if request fails
     */
    public SymbolSnapshot getSymbolSnapshot(String symbol) throws ApiExceptionMT5 {
        Mt5TermApiMarketInfo.MrpcMqlTick tick = service.symbolInfoTick(symbol);
        double[] tickData = getTickValueAndSize(symbol);
        double[] volumeLimits = getVolumeLimits(symbol);

        return new SymbolSnapshot(
                symbol,
                tick.getBid(),
                tick.getAsk(),
                getPoint(symbol),
                getDigits(symbol),
                getSpread(symbol),
                volumeLimits[0], // min
                volumeLimits[1], // max
                volumeLimits[2], // step
                tickData[0],     // tickValue
                tickData[1]      // tickSize
        );
    }


    // endregion

    // ═══════════════════════════════════════════════════════════════════════════════
    // region HISTORY HELPERS
    // ═══════════════════════════════════════════════════════════════════════════════

    /**
     * Gets orders history for last N days.
     *
     * @param days Number of days back
     * @param symbol Symbol filter (null = all symbols)
     * @return Orders history data
     * @throws ApiExceptionMT5 if request fails
     */
    public Mt5TermApiAccountHelper.OrdersHistoryData getOrdersHistoryLastDays(int days, String symbol) throws ApiExceptionMT5 {
        long nowSeconds = System.currentTimeMillis() / 1000;
        long fromSeconds = nowSeconds - (days * 24 * 60 * 60);

        com.google.protobuf.Timestamp from = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(fromSeconds)
                .build();

        com.google.protobuf.Timestamp to = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(nowSeconds)
                .build();

        return service.orderHistory(
                from,
                to,
                Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
                0,
                0
        );
    }

    /**
     * Gets positions history with pagination.
     *
     * @param page Page number (0-based)
     * @param itemsPerPage Items per page
     * @return Positions history data
     * @throws ApiExceptionMT5 if request fails
     */
    public Mt5TermApiAccountHelper.PositionsHistoryData getPositionsHistoryPaged(int page, int itemsPerPage) throws ApiExceptionMT5 {
        return service.positionsHistory(
                Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC,
                null,
                null,
                page,
                itemsPerPage
        );
    }
}

// endregion