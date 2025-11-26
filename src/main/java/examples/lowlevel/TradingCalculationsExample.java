/*══════════════════════════════════════════════════════════════════════════════
 FILE: TradingCalculationsExample.java — LOW-LEVEL API: TRADING & CALCULATIONS

 LEVEL: 1 (Low-Level API)
 DIFFICULTY: Intermediate

 PURPOSE:
   Demonstrates pre-trade calculations and basic trading operations.
   Learn how to calculate margin, profit, validate orders BEFORE placing them.
   This is CRITICAL for risk management - never trade without calculations!
   Also shows basic trading operations: send, modify, close orders.

 WHAT YOU'LL LEARN:
   • How to calculate required margin before opening position
   • How to calculate potential profit/loss for trade
   • How to validate order parameters before sending
   • How to calculate tick value for different symbols
   • How to place, modify, and close orders programmatically

 📚 WHAT THIS DEMO COVERS (7 Sections):

   1. MARGIN CALCULATIONS
      • orderCalcMargin() - Calculate required margin for order
      • Essential before opening position to avoid margin call
      • Different for Buy and Sell orders

   2. PROFIT CALCULATIONS
      • orderCalcProfit() - Calculate potential profit/loss
      • Estimate P&L before opening position
      • Critical for risk/reward ratio analysis

   3. ORDER VALIDATION
      • orderCheck() - Validate order before sending
      • Checks: sufficient margin, valid parameters, symbol tradeable
      • ALWAYS validate before orderSend()!

   4. TICK VALUE CALCULATIONS
      • tickValueWithSize() - Get tick value for symbols
      • Understand how much 1 pip movement equals in account currency
      • Different for each symbol and lot size

   5. SEND ORDER
      • orderSend() - Place new order/open position
      • Market orders (instant execution)
      • Pending orders (limit, stop)

   6. MODIFY ORDER
      • orderModify() - Modify existing order
      • Change Stop-Loss, Take-Profit
      • Cannot modify price of market order

   7. CLOSE ORDER
      • orderClose() - Close position
      • Full or partial close
      • Returns profit/loss

 USAGE:
   run.bat 2  or  .\run.bat 2       # Via run.bat (recommended)
   mvnd exec:java -Dexec.args="2"   # Via Maven directly

 PREREQUISITES:
   • MT5 terminal installed and running
   • MetaRPC gRPC gateway (plugin) running in MT5 terminal
   • Valid MT5 account credentials in appsettings.json
   • Recommended: Complete [1] Market Data first
   • DEMO ACCOUNT strongly recommended for testing!

 NEXT STEPS AFTER THIS DEMO:
   • Try [3] Streaming - monitor trades in real-time
   • Try [5] Trading Service - simplified trading API
   • Try [7] Simple Trading - high-level trading scenarios
   • Try [8] Risk Management - professional risk control

 WARNING:
   This example demonstrates actual trading operations!
   Sections 5-7 (send/modify/close) will place real orders on your account.
   Use DEMO ACCOUNT for testing!
══════════════════════════════════════════════════════════════════════════════*/

package examples.lowlevel;

import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class TradingCalculationsExample {

    public static void main(String[] args) {
        // Set UTF-8 encoding for console output
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        printBanner();

        MT5Account account = null;

        try {
            // Load configuration
            JsonObject config = loadConfig();
            long user = config.get("user").getAsLong();
            String password = config.get("password").getAsString();
            String grpcServer = config.get("grpcServer").getAsString();
            String serverName = config.get("serverName").getAsString();
            String baseSymbol = config.get("baseSymbol").getAsString();

            System.out.println("Configuration loaded: user=" + user);
            System.out.println();

            // Connect
            account = new MT5Account(user, password, grpcServer, null);
            account.connectByServerName(serverName, baseSymbol, 30);
            System.out.println("Connected to MT5");
            System.out.println();

            // Register shutdown hook for Ctrl+C handling
            final MT5Account finalAccount = account;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n[Ctrl+C detected] Shutting down gracefully...");
                try {
                    if (finalAccount != null) {
                        finalAccount.disconnect();
                        finalAccount.close();
                        System.out.println("[Shutdown hook] Disconnected successfully");
                    }
                } catch (Exception e) {
                    // Silently ignore errors during emergency shutdown
                }
            }));

            // Run all trading calculation demos
            runMarginCalculations(account, baseSymbol);
            runProfitCalculations(account, baseSymbol);
            runOrderValidation(account, baseSymbol);
            runTickValueCalculations(account, new String[]{baseSymbol, "GBPUSD", "USDJPY"});

            // Run trading operations demos (WARNING: Real orders will be sent!)
            System.out.println();
            System.out.println("------------------------------------------------------------------");
            System.out.println("  TRADING OPERATIONS SECTION");
            System.out.println("  WARNING: These methods will send REAL orders to MT5!");
            System.out.println("  Make sure you are using a DEMO account!");
            System.out.println("------------------------------------------------------------------");
            System.out.println();

            runOrderSend(account, baseSymbol);
            runOrderModify(account, baseSymbol);
            runOrderClose(account, baseSymbol);

            System.out.println();
            System.out.println("+------------------------------------------------------------------+");
            System.out.println("|  ALL TRADING CALCULATION DEMOS COMPLETED                         |");
            System.out.println("+------------------------------------------------------------------+");
            System.out.println();
            System.out.println("Closing in 3 seconds...");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (account != null) {
                try {
                    System.out.println("\n► Disconnecting from MT5...");
                    account.disconnect();
                    account.close();
                    System.out.println("► Disconnected successfully");
                } catch (Exception e) {
                    // Silently ignore all disconnect errors (file locks, etc.)
                    // They are harmless during shutdown
                }
            }
        }
    }

    private static JsonObject loadConfig() throws Exception {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(new FileReader("appsettings.json"), JsonObject.class);
        String defaultConn = root.get("DefaultConnection").getAsString();
        return root.getAsJsonObject("MT5Connections").getAsJsonObject(defaultConn);
    }

    // ══════════════════════════════════════════════════════════════
    // 1. MARGIN CALCULATIONS
    //    Calculate how much margin is required to open a position.
    //    CRITICAL: Always check margin before opening position to
    //    avoid margin call and forced position closure.
    // ══════════════════════════════════════════════════════════════

    private static void runMarginCalculations(MT5Account acc, String symbol) throws ApiExceptionMT5 {
        printSection("1. MARGIN CALCULATIONS");

        System.out.println("  Margin is the amount blocked by broker to open a position");
        System.out.println("  Helps determine if you have enough funds to open the position");
        System.out.println();

        // Get current price first
        Mt5TermApiMarketInfo.SymbolInfoTickRequestReply tick = acc.quote(symbol);
        double currentPrice = tick.getData().getAsk();

        System.out.println("  [1.1] orderCalcMargin() - Calculate required margin:");
        System.out.println("        Symbol: " + symbol);
        System.out.println("        Current Ask: " + String.format("%.5f", currentPrice));
        System.out.println();

        // Test different lot sizes
        double[] lotSizes = {0.01, 0.1, 1.0};

        for (double lotSize : lotSizes) {
            try {
                Mt5TermApiTradeFunctions.OrderCalcMarginReply marginReply = acc.orderCalcMargin(
                    symbol,
                    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                    lotSize,
                    currentPrice
                );

                double margin = marginReply.getData().getMargin();
                System.out.println("        Lot: " + lotSize + " => Required Margin: " +
                    String.format("%.2f", margin) + " (account currency)");

            } catch (ApiExceptionMT5 e) {
                System.out.println("        Lot: " + lotSize + " => Error: " + e.getMessage());
            }
        }
        System.out.println();

        // Calculate margin for SELL order
        try {
            Mt5TermApiTradeFunctions.OrderCalcMarginReply sellMarginReply = acc.orderCalcMargin(
                symbol,
                Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL,
                0.1,
                tick.getData().getBid()
            );

            System.out.println("        SELL order (0.1 lot) margin: " +
                String.format("%.2f", sellMarginReply.getData().getMargin()));
        } catch (ApiExceptionMT5 e) {
            System.out.println("        SELL margin calculation failed: " + e.getMessage());
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 2. PROFIT CALCULATIONS
    //    Calculate potential profit or loss for a trade BEFORE
    //    opening position. Essential for risk/reward analysis and
    //    position sizing decisions.
    // ══════════════════════════════════════════════════════════════

    private static void runProfitCalculations(MT5Account acc, String symbol) throws ApiExceptionMT5 {
        printSection("2. PROFIT CALCULATIONS");

        System.out.println("  Calculate potential profit/loss for a trade");
        System.out.println("  Helps evaluate risk/reward ratio before opening position");
        System.out.println();

        // Get current prices
        Mt5TermApiMarketInfo.SymbolInfoTickRequestReply tick = acc.quote(symbol);
        double openPrice = tick.getData().getAsk();
        double closePrice = openPrice + 0.0010; // +10 pips profit target

        System.out.println("  [2.1] orderCalcProfit() - Calculate profit:");
        System.out.println("        Symbol: " + symbol);
        System.out.println("        Open Price (Ask): " + String.format("%.5f", openPrice));
        System.out.println("        Close Price (+10 pips): " + String.format("%.5f", closePrice));
        System.out.println();

        // Calculate profit for BUY order
        try {
            Mt5TermApiTradeFunctions.OrderCalcProfitReply profitReply = acc.orderCalcProfit(
                symbol,
                Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                0.1,
                openPrice,
                closePrice
            );

            double profit = profitReply.getData().getProfit();
            System.out.println("        BUY 0.1 lot, +10 pips profit: " +
                String.format("%.2f", profit) + " (account currency)");

        } catch (ApiExceptionMT5 e) {
            System.out.println("        BUY profit calculation failed: " + e.getMessage());
        }

        // Calculate loss for BUY order (price goes down)
        double lossPrice = openPrice - 0.0010; // -10 pips
        try {
            Mt5TermApiTradeFunctions.OrderCalcProfitReply lossReply = acc.orderCalcProfit(
                symbol,
                Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                0.1,
                openPrice,
                lossPrice
            );

            double loss = lossReply.getData().getProfit();
            System.out.println("        BUY 0.1 lot, -10 pips loss: " +
                String.format("%.2f", loss) + " (account currency)");

        } catch (ApiExceptionMT5 e) {
            System.out.println("        Loss calculation failed: " + e.getMessage());
        }

        // Calculate profit for SELL order
        double sellOpenPrice = tick.getData().getBid();
        double sellClosePrice = sellOpenPrice - 0.0010; // -10 pips = profit for SELL
        try {
            Mt5TermApiTradeFunctions.OrderCalcProfitReply sellProfitReply = acc.orderCalcProfit(
                symbol,
                Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL,
                0.1,
                sellOpenPrice,
                sellClosePrice
            );

            double sellProfit = sellProfitReply.getData().getProfit();
            System.out.println("        SELL 0.1 lot, -10 pips (profit for sell): " +
                String.format("%.2f", sellProfit) + " (account currency)");

        } catch (ApiExceptionMT5 e) {
            System.out.println("        SELL profit calculation failed: " + e.getMessage());
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 3. ORDER VALIDATION
    //    Validate order parameters before sending to broker.
    //    Checks: margin sufficiency, valid lot size, symbol tradeable.
    //    ALWAYS use orderCheck() before orderSend() in production!
    // ══════════════════════════════════════════════════════════════

    private static void runOrderValidation(MT5Account acc, String symbol) throws ApiExceptionMT5 {
        printSection("3. ORDER VALIDATION");

        System.out.println("  orderCheck() - validate order correctness BEFORE sending");
        System.out.println("  Helps detect errors before opening position");
        System.out.println();

        // Get current price
        Mt5TermApiMarketInfo.SymbolInfoTickRequestReply tick = acc.quote(symbol);
        double currentPrice = tick.getData().getAsk();

        System.out.println("  [3.1] orderCheck() - Validate market order:");
        System.out.println("        Type: BUY");
        System.out.println("        Symbol: " + symbol);
        System.out.println("        Volume: 0.1 lot");
        System.out.println("        Price: " + String.format("%.5f", currentPrice));
        System.out.println();

        // Build order request
        Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.Builder orderBuilder =
            Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
                .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
                .setSymbol(symbol)
                .setVolume(0.1)
                .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
                .setPrice(currentPrice)
                .setDeviation(10)
                .setComment("Test validation");

        try {
            Mt5TermApiTradeFunctions.OrderCheckReply checkReply = acc.orderCheck(orderBuilder.build());

            if (checkReply.getData().hasMqlTradeCheckResult()) {
                Mt5TermApiTradeFunctions.MrpcMqlTradeCheckResult result = checkReply.getData().getMqlTradeCheckResult();

                System.out.println("        Validation Result:");
                System.out.println("          Retcode: " + result.getReturnedCode());
                System.out.println("          Balance after: " + String.format("%.2f", result.getBalanceAfterDeal()));
                System.out.println("          Equity after: " + String.format("%.2f", result.getEquityAfterDeal()));
                System.out.println("          Profit: " + String.format("%.2f", result.getProfit()));
                System.out.println("          Margin: " + String.format("%.2f", result.getMargin()));
                System.out.println("          Margin free: " + String.format("%.2f", result.getFreeMargin()));
                System.out.println("          Margin level: " + String.format("%.2f%%", result.getMarginLevel()));
                System.out.println("          Comment: " + result.getComment());
            }

        } catch (ApiExceptionMT5 e) {
            System.out.println("        Order validation failed: " + e.getMessage());
        }

        System.out.println();

        // Test invalid order (too large volume)
        System.out.println("  [3.2] orderCheck() - Test INVALID order (too large volume):");

        Mt5TermApiTradeFunctions.MrpcMqlTradeRequest invalidOrder =
            Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
                .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
                .setSymbol(symbol)
                .setVolume(1000.0) // Unrealistic volume
                .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
                .setPrice(currentPrice)
                .setComment("Invalid test")
                .build();

        try {
            Mt5TermApiTradeFunctions.OrderCheckReply invalidCheck = acc.orderCheck(invalidOrder);

            if (invalidCheck.getData().hasMqlTradeCheckResult()) {
                Mt5TermApiTradeFunctions.MrpcMqlTradeCheckResult result = invalidCheck.getData().getMqlTradeCheckResult();
                System.out.println("        Retcode: " + result.getReturnedCode());
                System.out.println("        Comment: " + result.getComment());
            }

        } catch (ApiExceptionMT5 e) {
            System.out.println("        Expected error: " + e.getMessage());
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 4. TICK VALUE CALCULATIONS
    //    Calculate monetary value of 1 tick (1 pip) movement.
    //    Different for each symbol and lot size. Essential for
    //    understanding profit/loss per pip.
    // ══════════════════════════════════════════════════════════════

    private static void runTickValueCalculations(MT5Account acc, String[] symbols) throws ApiExceptionMT5 {
        printSection("4. TICK VALUE CALCULATIONS");

        System.out.println("  tickValueWithSize() - tick value for profit calculations");
        System.out.println("  Shows how much one tick is worth in account currency");
        System.out.println();

        System.out.println("  [4.1] tickValueWithSize() - Get tick values:");

        try {
            Mt5TermApiAccountHelper.TickValueWithSizeReply tickReply =
                acc.tickValueWithSize(symbols);

            if (tickReply.getData().getSymbolTickSizeInfosCount() > 0) {
                System.out.println();
                System.out.println("        Symbol             | Tick Value  | Tick Size");
                System.out.println("        -------------------|-------------|------------");

                for (int i = 0; i < tickReply.getData().getSymbolTickSizeInfosCount(); i++) {
                    Mt5TermApiAccountHelper.TickSizeSymbol tickValue =
                        tickReply.getData().getSymbolTickSizeInfos(i);

                    System.out.println(String.format("        %-18s | %11.5f | %.5f",
                        tickValue.getName(),
                        tickValue.getTradeTickValue(),
                        tickValue.getTradeTickSize()
                    ));
                }
            } else {
                System.out.println("        No tick values returned");
            }

        } catch (ApiExceptionMT5 e) {
            System.out.println("        Tick value calculation failed: " + e.getMessage());
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 5. SEND ORDER (TRADING OPERATION)
    //
    //    WARNING: This places REAL orders on your MT5 account!
    //
    //    This section demonstrates orderSend() - the core method for
    //    placing market orders. It shows:
    //    • How to construct an OrderSendRequest with all parameters
    //    • Setting Stop-Loss and Take-Profit levels
    //    • Handling slippage for market orders
    //    • Processing OrderSendReply to get ticket numbers
    //
    //    Two orders are placed:
    //    1. BUY order at Ask price (SL = -50 pips, TP = +100 pips)
    //    2. SELL order at Bid price (SL = +50 pips, TP = -100 pips)
    //
    //    BEST PRACTICES:
    //    • Always validate order parameters before sending
    //    • Always set Stop-Loss to protect capital
    //    • Use try-catch to handle order failures
    //    • Check hasData() before accessing order result
    //    • Save ticket numbers for later modification/closing
    // ══════════════════════════════════════════════════════════════

    private static void runOrderSend(MT5Account acc, String symbol) throws ApiExceptionMT5 {
        printSection("5. SEND ORDER - PLACE NEW ORDER");

        System.out.println("  WARNING: This will send REAL orders to MT5!");
        System.out.println("  Make sure you are using a DEMO account");
        System.out.println();

        Mt5TermApiMarketInfo.SymbolInfoTickRequestReply tick = acc.quote(symbol);
        double currentAsk = tick.getData().getAsk();
        double currentBid = tick.getData().getBid();

        System.out.println("  [5.1] orderSend() - Send BUY market order:");
        System.out.println("        Symbol: " + symbol);
        System.out.println("        Price (Ask): " + String.format("%.5f", currentAsk));
        System.out.println("        Volume: 0.01 lot");
        System.out.println();

        Mt5TermApiTradingHelper.OrderSendRequest buyRequest =
            Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
                .setVolume(0.01)
                .setPrice(currentAsk)
                .setSlippage(10)
                .setStopLoss(currentAsk - 0.0050)
                .setTakeProfit(currentAsk + 0.0100)
                .setComment("Java MT5 BUY test")
                .build();

        try {
            Mt5TermApiTradingHelper.OrderSendReply sendReply = acc.orderSend(buyRequest);

            if (sendReply.hasData()) {
                Mt5TermApiTradingHelper.OrderSendData result = sendReply.getData();
                System.out.println("        [OK] Order sent successfully!");
                System.out.println("        Order ticket: " + result.getOrder());
                System.out.println("        Deal ticket: " + result.getDeal());
                System.out.println("        Volume: " + result.getVolume());
                System.out.println("        Price: " + String.format("%.5f", result.getPrice()));
            }

        } catch (ApiExceptionMT5 e) {
            System.out.println("        [X] Order failed: " + e.getMessage());
        }

        System.out.println();

        System.out.println("  [5.2] orderSend() - Send SELL market order:");
        System.out.println("        Symbol: " + symbol);
        System.out.println("        Price (Bid): " + String.format("%.5f", currentBid));
        System.out.println("        Volume: 0.01 lot");
        System.out.println();

        Mt5TermApiTradingHelper.OrderSendRequest sellRequest =
            Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL)
                .setVolume(0.01)
                .setPrice(currentBid)
                .setSlippage(10)
                .setStopLoss(currentBid + 0.0050)
                .setTakeProfit(currentBid - 0.0100)
                .setComment("Java MT5 SELL test")
                .build();

        try {
            Mt5TermApiTradingHelper.OrderSendReply sellSendReply = acc.orderSend(sellRequest);

            if (sellSendReply.hasData()) {
                Mt5TermApiTradingHelper.OrderSendData result = sellSendReply.getData();
                System.out.println("        [OK] Order sent successfully!");
                System.out.println("        Order ticket: " + result.getOrder());
                System.out.println("        Deal ticket: " + result.getDeal());
                System.out.println("        Volume: " + result.getVolume());
                System.out.println("        Price: " + String.format("%.5f", result.getPrice()));
            }

        } catch (ApiExceptionMT5 e) {
            System.out.println("        [X] Order failed: " + e.getMessage());
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 6. MODIFY ORDER (TRADING OPERATION)
    //
    //    WARNING: This modifies REAL positions on your MT5 account!
    //
    //    This section demonstrates orderModify() - updating Stop-Loss
    //    and Take-Profit levels of existing positions. It shows:
    //    • How to get list of open positions using openedOrders()
    //    • How to construct OrderModifyRequest with ticket number
    //    • Setting new SL/TP levels relative to current price
    //    • Handling modification failures
    //
    //    USE CASES:
    //    • Moving Stop-Loss to break-even after price moves in profit
    //    • Implementing trailing stop (move SL as price moves)
    //    • Adjusting Take-Profit based on market conditions
    //    • Tightening risk parameters
    //
    //    NOTE: You can only modify positions that are already open.
    //    If no positions exist, this section will be skipped.
    // ══════════════════════════════════════════════════════════════

    private static void runOrderModify(MT5Account acc, String symbol) throws ApiExceptionMT5 {
        printSection("6. MODIFY ORDER - UPDATE SL/TP");

        System.out.println("  WARNING: This will modify REAL positions in MT5!");
        System.out.println();

        Mt5TermApiAccountHelper.OpenedOrdersReply ordersReply =
            acc.openedOrders(Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC);

        if (ordersReply.getData().getPositionInfosCount() == 0) {
            System.out.println("  [!] No open positions to modify");
            System.out.println("  Please open a position first using orderSend()");
            System.out.println();
            return;
        }

        Mt5TermApiAccountHelper.PositionInfo position = ordersReply.getData().getPositionInfos(0);
        long ticket = position.getTicket();
        double currentPrice = position.getPriceCurrent();
        String positionType = position.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY ? "BUY" : "SELL";

        System.out.println("  [6.1] orderModify() - Modify position SL/TP:");
        System.out.println("        Ticket: " + ticket);
        System.out.println("        Type: " + positionType);
        System.out.println("        Current price: " + String.format("%.5f", currentPrice));
        System.out.println();

        double newSL, newTP;
        if (position.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY) {
            newSL = currentPrice - 0.0030;
            newTP = currentPrice + 0.0080;
        } else {
            newSL = currentPrice + 0.0030;
            newTP = currentPrice - 0.0080;
        }

        Mt5TermApiTradingHelper.OrderModifyRequest modifyRequest =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket)
                .setStopLoss(newSL)
                .setTakeProfit(newTP)
                .build();

        try {
            Mt5TermApiTradingHelper.OrderModifyReply modifyReply = acc.orderModify(modifyRequest);

            if (modifyReply.hasData()) {
                System.out.println("        [OK] Position modified successfully!");
                System.out.println("        New Stop Loss: " + String.format("%.5f", newSL));
                System.out.println("        New Take Profit: " + String.format("%.5f", newTP));
            }

        } catch (ApiExceptionMT5 e) {
            System.out.println("        [X] Modification failed: " + e.getMessage());
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 7. CLOSE ORDER (TRADING OPERATION)
    //
    //    WARNING: This closes REAL positions on your MT5 account!
    //
    //    This section demonstrates orderClose() - closing open positions
    //    at current market price. It shows:
    //    • How to get list of open positions
    //    • How to close position by ticket number and volume
    //    • Handling slippage parameter for market close
    //    • Processing OrderCloseReply with return codes
    //
    //    PARAMETERS:
    //    • ticket: Position ticket number to close
    //    • volume: Amount to close (partial or full position)
    //    • slippage: Maximum acceptable price deviation (in points)
    //
    //    USE CASES:
    //    • Emergency exit from losing position
    //    • Taking profit manually before TP is hit
    //    • Partial position closing (scale out)
    //    • End-of-day position cleanup
    //
    //    NOTE: Closing at market price means you pay the spread.
    //    BUY positions close at Bid, SELL positions close at Ask.
    // ══════════════════════════════════════════════════════════════

    private static void runOrderClose(MT5Account acc, String symbol) throws ApiExceptionMT5 {
        printSection("7. CLOSE ORDER - CLOSE POSITION");

        System.out.println("  WARNING: This will close REAL positions in MT5!");
        System.out.println();

        Mt5TermApiAccountHelper.OpenedOrdersReply ordersReply =
            acc.openedOrders(Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC);

        if (ordersReply.getData().getPositionInfosCount() == 0) {
            System.out.println("  [!] No open positions to close");
            System.out.println("  Please open a position first using orderSend()");
            System.out.println();
            return;
        }

        Mt5TermApiAccountHelper.PositionInfo position = ordersReply.getData().getPositionInfos(0);
        long ticket = position.getTicket();
        double volume = position.getVolume();
        String positionType = position.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY ? "BUY" : "SELL";

        System.out.println("  [7.1] orderClose() - Close position:");
        System.out.println("        Ticket: " + ticket);
        System.out.println("        Type: " + positionType);
        System.out.println("        Volume: " + volume);
        System.out.println();

        try {
            Mt5TermApiTradingHelper.OrderCloseReply closeReply = acc.orderClose(ticket, volume, 10);

            if (closeReply.hasData()) {
                Mt5TermApiTradingHelper.OrderCloseData result = closeReply.getData();
                System.out.println("        [OK] Position closed successfully!");
                System.out.println("        Return code: " + result.getReturnedCode());
                System.out.println("        Close mode: " + result.getCloseMode());
                System.out.println("        Description: " + result.getReturnedCodeDescription());
            }

        } catch (ApiExceptionMT5 e) {
            System.out.println("        [X] Close failed: " + e.getMessage());
        }

        System.out.println();
    }

    private static void printBanner() {
        System.out.println("+------------------------------------------------------------------+");
        System.out.println("|         TRADING CALCULATIONS & OPERATIONS DEMO                   |");
        System.out.println("|  Margin, Profit, Validation, Send, Modify, Close                |");
        System.out.println("+------------------------------------------------------------------+");
        System.out.println();
    }

    private static void printSection(String title) {
        System.out.println("------------------------------------------------------------------");
        System.out.println(title);
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }
}

/*══════════════════════════════════════════════════════════════════════════════

                         HOW TO RUN THIS EXAMPLE

══════════════════════════════════════════════════════════════════════════════

METHOD 1: Via run.bat (Recommended - fast)
────────────────────────────────────
  run.bat 2  or  .\run.bat 2      → Run this example directly

METHOD 2: Via run-clean.bat (If run.bat fails with compilation errors)
────────────────────────────────────
  run-clean.bat 2  or  .\run-clean.bat 2
  → Stops daemon, removes target/, recompiles from scratch
  → Use this if you see "Unresolved compilation problem" errors

METHOD 3: Via Maven
────────────────────────────────────
  mvnd compile exec:java -Dexec.args="2"
  mvnd compile exec:java -Dexec.mainClass="examples.lowlevel.TradingCalculationsExample"

METHOD 3: Direct Java execution
────────────────────────────────────
  mvnd compile                                    → Compile first
  java -cp "target/classes;target/dependency/*" examples.lowlevel.TradingCalculationsExample


══════════════════════════════════════════════════════════════════════════════

                            EXPECTED OUTPUT

══════════════════════════════════════════════════════════════════════════════

You should see 7 main sections:

[1] MARGIN CHECK
    → Shows margin required for 0.10 lot trade
    → Example: "Required margin: $130.50"

[2] PROFIT CALCULATION
    → Shows profit for 0.10 lot with 50 pip movement
    → Example: "Profit (long): $50.00, Profit (short): $50.00"

[3] ORDER VALIDATION
    → Validates order parameters before sending
    → Shows which parameters are valid/invalid
    → Example: "Valid volume range: 0.01 - 100.00 lot"

[4] TICK VALUE & CONTRACT SIZE
    → Shows value of 1 tick and contract specifications
    → Example: "Tick value: $1.00, Contract size: 100000"

[5] SEND ORDER (TRADING OPERATION)
    → Sends 2 orders: BUY and SELL (0.01 lot each)
    → Returns order tickets and deal tickets
    → Example: "Order ticket: 123456789, Deal ticket: 987654321"

[6] MODIFY ORDER (TRADING OPERATION)
    → Modifies SL/TP of first open position
    → Example: "New Stop Loss: 1.08500, New Take Profit: 1.09200"

[7] CLOSE ORDER (TRADING OPERATION)
    → Closes first open position
    → Example: "Position closed successfully! Return code: 10009"


══════════════════════════════════════════════════════════════════════════════

                            TROUBLESHOOTING

══════════════════════════════════════════════════════════════════════════════

PROBLEM: "Connection failed" or "Failed to create MT5 connection"
SOLUTION:
  1. Check MT5 terminal is running
  2. Check MetaRPC gRPC gateway (plugin) is installed in MT5 terminal
  3. Check appsettings.json has correct:
     • grpcServer: "mt5win.mrpc.pro" (or your server)
     • user: your MT5 account number
     • password: your MT5 password
     • serverName: "FxPro-MT5 Demo" (or your broker)
  4. Check firewall is not blocking gRPC connection
  5. Check internet connection

PROBLEM: "Order failed: Not enough money"
SOLUTION:
  • Check account balance (must be > required margin)
  • Reduce order volume (try 0.01 lot instead of 0.10)
  • Close some existing positions to free up margin
  • Deposit more funds (if using real account)

PROBLEM: "Order failed: Invalid volume"
SOLUTION:
  • Check minimum volume for symbol (usually 0.01)
  • Check maximum volume for symbol
  • Check volume step (must be multiple of 0.01)
  • Some symbols require minimum 0.1 lot

PROBLEM: "Order failed: Trade is disabled"
SOLUTION:
  • Check market is open (Forex closed on weekends)
  • Check symbol is enabled for trading
  • Some brokers disable trading on demo accounts after period

PROBLEM: "Order failed: Invalid stops"
SOLUTION:
  • Stop-Loss too close to current price (check stops level)
  • Take-Profit too close to current price
  • Most brokers require minimum distance (e.g., 10-50 points)
  • Check SYMBOL_TRADE_STOPS_LEVEL in symbol info

PROBLEM: "No open positions to modify/close"
SOLUTION:
  • This is expected if you don't have open positions
  • Run section 5 (SEND ORDER) first to open positions
  • Then sections 6 and 7 will work
  • Or open position manually in MT5 terminal

PROBLEM: "Modification failed: Invalid ticket"
SOLUTION:
  • Position was already closed
  • Ticket number is incorrect
  • Position belongs to different account
  • Check position still exists with openedOrders()

PROBLEM: Results show "No data" or empty responses
SOLUTION:
  • Check hasData() returned true
  • Check error code in response (if available)
  • Some brokers return empty data on certain operations
  • Check MT5 terminal Journal for error messages

══════════════════════════════════════════════════════════════════════════════

                     CRITICAL TRADING WARNINGS

══════════════════════════════════════════════════════════════════════════════

⚠️  WARNING #1: REAL MONEY AT RISK
    Sections 5-7 (send/modify/close) execute REAL trading operations.
    If using LIVE account, this means REAL MONEY is at risk!
    → ALWAYS use DEMO account for testing
    → NEVER test new code on live account
    → ALWAYS start with minimum volume (0.01 lot)

⚠️  WARNING #2: MARKET RISK
    Forex trading involves substantial risk of loss.
    Past performance does not guarantee future results.
    → NEVER trade with money you can't afford to lose
    → ALWAYS understand the risks before trading
    → CONSIDER consulting a financial advisor

⚠️  WARNING #3: SLIPPAGE
    Market orders execute at current price + slippage.
    During high volatility, actual price can differ significantly.
    → ALWAYS set reasonable slippage (10-50 points)
    → NEVER use large slippage (you'll get bad fills)
    → CONSIDER using limit orders in volatile markets


══════════════════════════════════════════════════════════════════════════════

                            NEXT STEPS

══════════════════════════════════════════════════════════════════════════════

After understanding this example, try:

1. STREAMING (Example 3 - Level 1)
   → Real-time tick data and position updates
   → Learn reactive programming with StreamObserver
   → File: examples/lowlevel/StreamingExample.java
   → Run: run.bat 3

2. TRADING SERVICE (Example 5 - Level 2)
   → Simplified API wrapper for trading operations
   → Same functionality, cleaner code
   → File: examples/services/TradingServiceExample.java
   → Run: run.bat 5

3. RISK MANAGEMENT (Example 8 - Level 3)
   → Professional risk control scenarios
   → Stop-Loss, Take-Profit, Trailing Stop, Break-Even
   → Position sizing based on risk percentage
   → File: examples/sugar/RiskManagementScenario.java
   → Run: run.bat 8

4. ORCHESTRATORS (Example 10 - Level 4)
   → Full-featured trading strategies
   → Trend Following, Scalping, Hedging, Breakout, Martingale
   → Complete capital management systems
   → File: examples/orchestrators/OrchestratorDemo.java
   → Run: run.bat 10
   
══════════════════════════════════════════════════════════════════════════════ */