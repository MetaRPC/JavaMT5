/*══════════════════════════════════════════════════════════════════════════════
 FILE: TradingServiceExample.java — SERVICE API: TRADING WITH WRAPPERS

 LEVEL: 2 (Service API)
 DIFFICULTY: Beginner to Intermediate

 PURPOSE:
   Same trading functionality as Example 2 (Low-Level), but with Service API!
   This example demonstrates MT5Service trading methods that:
   • Auto-extract .getData() from trading calculations
   • Return double directly (orderCalcMargin, orderCalcProfit)
   • Return Data objects (orderCheck, orderSend, orderModify, orderClose)
   • Reduce boilerplate for trading operations

 🎯 WHY USE SERVICE API FOR TRADING?

   LOW-LEVEL (Example 2):                    SERVICE API (This Example):
   ────────────────────────────────────────────────────────────────────────
   Reply reply = acc.orderCalcMargin(...);   double margin =
   double margin = reply.getData()             service.orderCalcMargin(...);
                      .getMargin();           // Direct double!

   OrderSendReply reply = acc.orderSend();   OrderSendData result =
   OrderSendData data = reply.getData();       service.orderSend(request);
   long ticket = data.getOrder();            long ticket = result.getOrder();

 📚 WHAT YOU'LL LEARN:
   • Simplified trading calculations (margin, profit, validation)
   • Direct return values instead of Reply wrappers
   • Same orderSend/Modify/Close but cleaner

 USAGE:
   run.bat 5  or  .\run.bat 5               # Via run.bat (recommended)
   mvnd exec:java -Dexec.args="5"           # Via Maven

══════════════════════════════════════════════════════════════════════════════*/

package examples.services;

import pro.mrpc.mt5.MT5Service;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class TradingServiceExample {

    public static void main(String[] args) {
        // Set UTF-8 encoding for console output
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        System.out.println("\n------------------------------------------------------------------");
        System.out.println("MT5 SERVICE - TRADING EXAMPLE");
        System.out.println("------------------------------------------------------------------\n");

        MT5Service service = null;
        try {
            // ═══════════════════════════════════════════════════════════
            // INITIALIZATION
            // ═══════════════════════════════════════════════════════════

            JsonObject config = loadConfig();
            long user = config.get("user").getAsLong();
            String password = config.get("password").getAsString();
            String grpcServer = config.has("grpcServer") ? config.get("grpcServer").getAsString() : null;
            String serverName = config.get("serverName").getAsString();
            String baseSymbol = config.get("baseSymbol").getAsString();

            System.out.println("► Initializing MT5Service...");
            service = new MT5Service(user, password, grpcServer, null);

            System.out.println("► Connecting to " + serverName + "...");
            service.connectByServerName(serverName, baseSymbol, 30);
            System.out.println("✓ Connected successfully\n");

            // Register shutdown hook for Ctrl+C handling
            final MT5Service finalService = service;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n[Ctrl+C detected] Shutting down gracefully...");
                try {
                    if (finalService != null) {
                        finalService.disconnect();
                        finalService.getAccount().close();
                        System.out.println("[Shutdown hook] Disconnected successfully");
                    }
                } catch (Exception e) {
                    // Silently ignore errors during emergency shutdown
                }
            }));

            // ══════════════════════════════════════════════════════════════
            // SECTION 1: TRADING CALCULATIONS
            //
            // WRAPPER BENEFIT: Returns double/Data directly
            //   • service.orderCalcMargin()  → double (not Reply.getData().getMargin())
            //   • service.orderCalcProfit()  → double (not Reply.getData().getProfit())
            //   • service.orderCheck()       → Data  (not Reply.getData())
            // ══════════════════════════════════════════════════════════════

            section("1. TRADING CALCULATIONS");

            String symbol = baseSymbol;
            double volume = 0.01;  // Micro lot for demo

            // Get current price for calculations
            Mt5TermApiMarketInfo.MrpcMqlTick tick = service.quote(symbol);
            double currentPrice = tick.getAsk();

            demo("orderCalcMargin(symbol, type, volume, price)", "Calculate required margin");
            double marginBuy = service.orderCalcMargin(
                symbol,
                Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                volume,
                currentPrice
            );
            result("Margin for BUY", String.format("$%.2f", marginBuy));

            double marginSell = service.orderCalcMargin(
                symbol,
                Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL,
                volume,
                currentPrice
            );
            result("Margin for SELL", String.format("$%.2f", marginSell));

            demo("orderCalcProfit(symbol, type, volume, openPrice, closePrice)", "Calculate potential profit");
            double openPrice = currentPrice;
            double closePrice = currentPrice + 0.0010;  // +10 pips for EURUSD

            double profit = service.orderCalcProfit(
                symbol,
                Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                volume,
                openPrice,
                closePrice
            );
            result("Profit (+10 pips)", String.format("$%.2f", profit));

            demo("orderCheck(tradeRequest)", "Validate order before sending");
            try {
                Mt5TermApiTradeFunctions.MrpcMqlTradeRequest checkRequest =
                    Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
                        .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
                        .setSymbol(symbol)
                        .setVolume(volume)
                        .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
                        .setPrice(currentPrice)
                        .setDeviation(50)
                        .setTypeFilling(Mt5TermApiTradeFunctions.MRPC_ENUM_ORDER_TYPE_FILLING.ORDER_FILLING_FOK)
                        .setTypeTime(Mt5TermApiTradeFunctions.MRPC_ENUM_ORDER_TYPE_TIME.ORDER_TIME_GTC)
                        .setExpertAdvisorMagicNumber(123456)
                        .setComment("Service layer test order")
                        .build();

                Mt5TermApiTradeFunctions.OrderCheckData checkResult = service.orderCheck(checkRequest);
                if (checkResult.hasMqlTradeCheckResult()) {
                    Mt5TermApiTradeFunctions.MrpcMqlTradeCheckResult checkRes = checkResult.getMqlTradeCheckResult();
                    result("Validation", checkRes.getReturnedCode() == 0 ? "✓ Valid" : "✗ Invalid");
                    result("Return Code", String.valueOf(checkRes.getReturnedCode()));
                    result("Margin Required", String.format("$%.2f", checkRes.getMargin()));
                    result("Comment", checkRes.getComment());
                }
            } catch (ApiExceptionMT5 e) {
                result("Order Check", "Not available (server error: " + e.getErrorCode() + ")");
            }

            // ══════════════════════════════════════════════════════════════
            // SECTION 2: ORDER OPERATIONS (DEMO WITH REAL DATA - NOT EXECUTED)
            //
            // WRAPPER BENEFIT: Same as calculations - returns Data directly
            //   • service.orderSend()   → OrderSendData   (not Reply.getData())
            //   • service.orderModify() → OrderModifyData (not Reply.getData())
            //   • service.orderClose()  → OrderCloseData  (not Reply.getData())
            //
            // NOTE: This section builds real request objects with current market data
            //       but does NOT execute them (safe demo)
            // ══════════════════════════════════════════════════════════════

            section("2. ORDER OPERATIONS (Demo with REAL market data)");

            // ────────────────────────────────────────────────────────────────
            // 2.1 ORDER SEND - Build real request with current price
            // ────────────────────────────────────────────────────────────────
            demo("orderSend(request)", "Building BUY order with real market data");

            // Calculate real SL/TP based on current price
            double stopLossPrice = currentPrice - 0.0050;   // -50 pips
            double takeProfitPrice = currentPrice + 0.0100; // +100 pips (1:2 R/R)

            // Build REAL request object (but won't execute it)
            Mt5TermApiTradingHelper.OrderSendRequest orderRequest =
                Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                    .setSymbol(symbol)
                    .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
                    .setVolume(0.01)
                    .setPrice(currentPrice)
                    .setSlippage(50)
                    .setStopLoss(stopLossPrice)
                    .setTakeProfit(takeProfitPrice)
                    .setComment("Demo order - not executed")
                    .build();

            System.out.println("    Request built with REAL market data:");
            System.out.println("    ----------------------------------------------------------");
            System.out.println("      Symbol:        " + orderRequest.getSymbol());
            System.out.println("      Type:          BUY");
            System.out.println("      Volume:        " + orderRequest.getVolume() + " lots");
            System.out.println("      Price:         " + String.format("%.5f", orderRequest.getPrice()));
            System.out.println("      Stop Loss:     " + String.format("%.5f", orderRequest.getStopLoss()) + " (-50 pips)");
            System.out.println("      Take Profit:   " + String.format("%.5f", orderRequest.getTakeProfit()) + " (+100 pips)");
            System.out.println("      Risk/Reward:   1:2");
            System.out.println("      Slippage:      " + orderRequest.getSlippage() + " points");
            System.out.println("    ----------------------------------------------------------");
            result("Request", "✓ Built successfully (NOT executed)");

            // ────────────────────────────────────────────────────────────────
            // 2.2 ORDER MODIFY - Build real modify request
            // ────────────────────────────────────────────────────────────────
            demo("orderModify(request)", "Building modify request with tighter stops");

            // Simulate tighter stops (move SL closer, TP further)
            long demoTicket = 123456789L;  // Simulated ticket
            double newStopLoss = currentPrice - 0.0030;    // -30 pips (tighter)
            double newTakeProfit = currentPrice + 0.0150;  // +150 pips (wider)

            Mt5TermApiTradingHelper.OrderModifyRequest modifyRequest =
                Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                    .setTicket(demoTicket)
                    .setStopLoss(newStopLoss)
                    .setTakeProfit(newTakeProfit)
                    .build();

            System.out.println("    Modify request built:");
            System.out.println("    ----------------------------------------------------------");
            System.out.println("      Ticket:        " + modifyRequest.getTicket() + " (simulated)");
            System.out.println("      New SL:        " + String.format("%.5f", modifyRequest.getStopLoss()) + " (-30 pips)");
            System.out.println("      New TP:        " + String.format("%.5f", modifyRequest.getTakeProfit()) + " (+150 pips)");
            System.out.println("      New R/R:       1:5 (improved!)");
            System.out.println("      Strategy:      Tighter SL, wider TP");
            System.out.println("    ----------------------------------------------------------");
            result("Request", "✓ Built successfully (NOT executed)");

            // ────────────────────────────────────────────────────────────────
            // 2.3 ORDER CLOSE - Show close parameters
            // ────────────────────────────────────────────────────────────────
            demo("orderClose(ticket, volume, slippage)", "Closing position parameters");

            double volumeToClose = 0.01;
            long slippage = 50;

            System.out.println("    Close parameters:");
            System.out.println("    ----------------------------------------------------------");
            System.out.println("      Ticket:        " + demoTicket + " (simulated)");
            System.out.println("      Volume:        " + volumeToClose + " lots");
            System.out.println("      Slippage:      " + slippage + " points max");
            System.out.println("    ----------------------------------------------------------");
            System.out.println();
            System.out.println("    Example code:");
            System.out.println("    ----------------------------------------------------------");
            System.out.println("      OrderCloseData result = service.orderClose(");
            System.out.println("          " + demoTicket + "L,     // ticket");
            System.out.println("          " + volumeToClose + ",           // volume");
            System.out.println("          " + slippage + "              // max slippage");
            System.out.println("      );");
            System.out.println("    ----------------------------------------------------------");
            result("Request", "✓ Parameters ready (NOT executed)");

            // ═══════════════════════════════════════════════════════════

            System.out.println("\n------------------------------------------------------------------");
            System.out.println("All trading operations demonstrated successfully");
            System.out.println();
            System.out.println("NOTE: Order send/modify/close are NOT executed");
            System.out.println("This is a safe demo showing API usage only");
            System.out.println("------------------------------------------------------------------\n");
            System.out.println("Closing in 3 seconds...");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("\n✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (service != null) {
                try {
                    System.out.println("► Disconnecting from MT5...");
                    service.disconnect();
                    service.getAccount().close();
                    System.out.println("► Disconnected successfully");
                } catch (Exception e) {
                    // Silently ignore all disconnect errors (file locks, etc.)
                    // They are harmless during shutdown
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ═══════════════════════════════════════════════════════════════════

    private static JsonObject loadConfig() throws Exception {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(new FileReader("appsettings.json"), JsonObject.class);
        String defaultConn = root.get("DefaultConnection").getAsString();
        return root.getAsJsonObject("MT5Connections").getAsJsonObject(defaultConn);
    }

    private static void section(String title) {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println(" " + title);
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    private static void demo(String method, String description) {
        System.out.println("  ▸ " + method);
        System.out.println("    " + description);
    }

    private static void result(String label, String value) {
        System.out.println("    → " + label + ": " + value);
    }
}

/*════════════════════════════════════════════════════════════════════════════

                      KEY DIFFERENCES FROM LOW-LEVEL API

══════════════════════════════════════════════════════════════════════════════

┌─────────────────────────────────────────────────────────────────────────────┐
│ OPERATION            │ LOW-LEVEL (Example 2)       │ SERVICE (Example 5)    │
├─────────────────────────────────────────────────────────────────────────────┤
│ Calculate Margin     │ Reply reply =               │ double margin =        │
│                      │   acc.orderCalcMargin(...)  │   service              │
│                      │ double margin = reply       │     .orderCalcMargin() │
│                      │   .getData().getMargin()    │ // Direct double!      │
├─────────────────────────────────────────────────────────────────────────────┤
│ Calculate Profit     │ Reply reply =               │ double profit =        │
│                      │   acc.orderCalcProfit(...)  │   service              │
│                      │ double profit = reply       │     .orderCalcProfit() │
│                      │   .getData().getProfit()    │ // Direct double!      │
├─────────────────────────────────────────────────────────────────────────────┤
│ Check Order          │ Reply reply =               │ Data data =            │
│                      │   acc.orderCheck(request)   │   service               │
│                      │ Data data = reply           │     .orderCheck(request)│
│                      │   .getData()                │ // Auto .getData()      │
├─────────────────────────────────────────────────────────────────────────────┤
│ Send Order           │ OrderSendReply reply =      │ OrderSendData data =   │
│                      │   acc.orderSend(request)    │   service              │
│                      │ OrderSendData data =        │     .orderSend(request)│
│                      │   reply.getData()           │ // Already extracted!  │
│                      │ long ticket = data          │ long ticket = data     │
│                      │   .getOrder()               │   .getOrder()          │
└─────────────────────────────────────────────────────────────────────────────┘

BENEFITS:
  ✓ Calculation methods return double directly (orderCalcMargin, orderCalcProfit)
  ✓ No need to extract .getData() from Reply objects
  ✓ Trading operations return Data objects directly
  ✓ Same underlying functionality, less boilerplate
  ✓ Cleaner code for trading algorithms

WHEN TO USE:
  → Use SERVICE API (this) for most trading bots and algorithms
  → Use LOW-LEVEL API (Example 2) when you need access to Reply metadata


══════════════════════════════════════════════════════════════════════════════

                               HOW TO RUN

══════════════════════════════════════════════════════════════════════════════

1. Via run.bat (Recommended - fast):
   run.bat 5  or  .\run.bat 5

2. Via run-clean.bat (If run.bat fails with compilation errors):
   run-clean.bat 5  or  .\run-clean.bat 5
   → Stops daemon, removes target/, recompiles from scratch
   → Use this if you see "Unresolved compilation problem" errors

3. Via Maven:
   mvnd compile exec:java -Dexec.args="5"

══════════════════════════════════════════════════════════════════════════════*/