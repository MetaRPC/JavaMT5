/*══════════════════════════════════════════════════════════════════════════════
 FILE: GridTradingScenario.java — SUGAR API: GRID TRADING (READY SCENARIO)

 LEVEL: 3 (Sugar API)
 DIFFICULTY: Beginner

 PURPOSE:
   Pre-built Grid Trading scenario using MT5Sugar!
   This is the EASIEST way to implement grid trading strategy:
   • buyLimitPoints() - place BUY LIMIT in points offset
   • sellLimitPoints() - place SELL LIMIT in points offset
   • cancelAll() - cancel all pending orders for symbol
   • closeAll() - close all positions for symbol
   • getSymbolSnapshot() - all symbol info in one call

 🎯 WHY USE SUGAR API FOR GRID TRADING?

   SERVICE API (Manual):                   SUGAR API (This Example):
   ────────────────────────────────────────────────────────────────────────
   // Calculate price                     long ticket =
   tick = service.quote(symbol);            sugar.buyLimitPoints(
   price = tick.getBid() - 100*point;         symbol, 0.01, -100, 50, 100);
   // Build OrderSendRequest...            // Done! 1 line instead of 10+
   // Set all parameters...
   // Send order...
   ticket = service.orderSend(request)
     .getOrder();

 📚 WHAT YOU'LL LEARN:
   • Grid trading strategy: multiple pending orders at price levels
   • buyLimitPoints/sellLimitPoints - place orders with point offsets
   • cancelAll() - batch cancel all pending orders
   • closeAll() - batch close all positions
   • getSymbolSnapshot() - complete symbol info in one object

 GRID STRATEGY:
   Places BUY LIMIT orders BELOW current price (triggered when price drops)
   Places SELL LIMIT orders ABOVE current price (triggered when price rises)
   Result: catches price movement in both directions

 USAGE:
   run.bat 9  (or .\run.bat 9)                # Via run.bat
   mvnd exec:java -Dexec.args="9"           # Via Maven
══════════════════════════════════════════════════════════════════════════════*/

package examples.sugar;

import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.MT5Service;
import io.metarpc.mt5.MT5Sugar;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GridTradingScenario {

    public static void main(String[] args) {
        // Set UTF-8 encoding
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        System.out.println("\n+============================================================+");
        System.out.println("  SCENARIO 3: GRID TRADING (PENDING ORDERS)              ");
        System.out.println("+============================================================+\n");

        try {
            // Load credentials
            InputStream is = GridTradingScenario.class.getClassLoader().getResourceAsStream("appsettings.json");
            if (is == null) {
                is = new java.io.FileInputStream("appsettings.json");
            }
            String json = new String(is.readAllBytes());
            is.close();

            // Parse nested JSON structure: MT5Connections.FxProDemo.*
            String fxProDemoSection = json.split("\"FxProDemo\":\\s*\\{")[1].split("\\}")[0];

            long user = Long.parseLong(fxProDemoSection.split("\"user\":\\s*")[1].split(",")[0].trim());
            String password = fxProDemoSection.split("\"password\":\\s*\"")[1].split("\"")[0];
            String grpcServer = fxProDemoSection.split("\"grpcServer\":\\s*\"")[1].split("\"")[0];

            System.out.println("Configuration loaded: user=" + user);
            System.out.println();

            // Create full stack: Account → Service → Sugar
            MT5Account account = new MT5Account(user, password, grpcServer, null);
            MT5Service service = new MT5Service(account);
            MT5Sugar sugar = new MT5Sugar(service);

            // Connect
            System.out.println("► Connecting to MT5...");
            account.connectByServerName("FxPro-MT5 Demo", "EURUSD");
            System.out.println("✓ Connected\n");

            String symbol = "EURUSD";

            // ══════════════════════════════════════════════════════════════
            // STEP 1: GET SYMBOL SNAPSHOT
            //
            // SUGAR BENEFIT: getSymbolSnapshot() returns all symbol info
            //                in one convenient object instead of multiple calls
            // ══════════════════════════════════════════════════════════════

            section("STEP 1: Symbol Snapshot");

            MT5Sugar.SymbolSnapshot symbolSnapshot = sugar.getSymbolSnapshot(symbol);

            System.out.println("  Symbol: " + symbolSnapshot.name);
            System.out.println("  Bid: " + String.format("%.5f", symbolSnapshot.bid));
            System.out.println("  Ask: " + String.format("%.5f", symbolSnapshot.ask));
            System.out.println("  Spread: " + symbolSnapshot.spread + " points");
            System.out.println("  Point: " + symbolSnapshot.point);
            System.out.println("  Digits: " + symbolSnapshot.digits);
            System.out.println("  Volume: [" + symbolSnapshot.volumeMin + " - " + symbolSnapshot.volumeMax + "] step " + symbolSnapshot.volumeStep);
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 2: PLACE BUY LIMIT GRID BELOW CURRENT PRICE
            //
            // SUGAR BENEFIT: buyLimitPoints(symbol, volume, offset, sl, tp)
            //                → 1 line instead of 10+ lines with Service API
            //                Automatically calculates price from offset!
            // ══════════════════════════════════════════════════════════════

            section("STEP 2: Place BUY LIMIT Grid");

            int gridLevels = 2;  // Smaller grid for demo
            double gridStepPoints = 30; // 30 points between levels (tighter)
            double volume = 0.01;
            double stopLossPoints = 20;
            double takeProfitPoints = 40;

            List<Long> buyOrders = new ArrayList<>();

            System.out.println("  Placing " + gridLevels + " BUY LIMIT orders below current price");
            System.out.println("  Grid step: " + gridStepPoints + " points");
            System.out.println("  Volume: " + volume + " lots each");
            System.out.println();

            for (int i = 1; i <= gridLevels; i++) {
                double pointsOffset = -i * gridStepPoints; // Negative = below current price

                long ticket = sugar.buyLimitPoints(symbol, volume, pointsOffset, stopLossPoints, takeProfitPoints);
                buyOrders.add(ticket);

                double orderPrice = sugar.priceFromOffsetPoints(symbol, true, pointsOffset);
                System.out.println("    [" + i + "] BUY LIMIT at " + String.format("%.5f", orderPrice) +
                                   " (" + pointsOffset + " points) → Ticket: " + ticket);
            }

            System.out.println();
            System.out.println("  ✓ " + buyOrders.size() + " BUY LIMIT orders placed");
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 3: PLACE SELL LIMIT GRID ABOVE CURRENT PRICE
            //
            // SUGAR BENEFIT: sellLimitPoints() - same simplicity for SELL side
            // ══════════════════════════════════════════════════════════════

            section("STEP 3: Place SELL LIMIT Grid");

            List<Long> sellOrders = new ArrayList<>();

            System.out.println("  Placing " + gridLevels + " SELL LIMIT orders above current price");
            System.out.println("  Grid step: " + gridStepPoints + " points");
            System.out.println("  Volume: " + volume + " lots each");
            System.out.println();

            for (int i = 1; i <= gridLevels; i++) {
                double pointsOffset = i * gridStepPoints; // Positive = above current price

                long ticket = sugar.sellLimitPoints(symbol, volume, pointsOffset, stopLossPoints, takeProfitPoints);
                sellOrders.add(ticket);

                double orderPrice = sugar.priceFromOffsetPoints(symbol, false, pointsOffset);
                System.out.println("    [" + i + "] SELL LIMIT at " + String.format("%.5f", orderPrice) +
                                   " (+" + pointsOffset + " points) → Ticket: " + ticket);
            }

            System.out.println();
            System.out.println("  ✓ " + sellOrders.size() + " SELL LIMIT orders placed");
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 4: CHECK PENDING ORDERS
            //
            // SUGAR BENEFIT: getPositionCount() - direct int instead of API calls
            // ══════════════════════════════════════════════════════════════

            section("STEP 4: Check Grid Status");

            int totalOrders = buyOrders.size() + sellOrders.size();
            int currentPositions = sugar.getPositionCount();

            System.out.println("  Total pending orders placed: " + totalOrders);
            System.out.println("  BUY LIMIT orders: " + buyOrders.size());
            System.out.println("  SELL LIMIT orders: " + sellOrders.size());
            System.out.println("  Current positions: " + currentPositions);
            System.out.println();

            System.out.println("  Grid range:");
            double lowestBuyPrice = sugar.priceFromOffsetPoints(symbol, true, -gridLevels * gridStepPoints);
            double highestSellPrice = sugar.priceFromOffsetPoints(symbol, false, gridLevels * gridStepPoints);
            double currentBid = sugar.getBid(symbol);
            double currentAsk = sugar.getAsk(symbol);

            System.out.println("    Lowest BUY:  " + String.format("%.5f", lowestBuyPrice));
            System.out.println("    Current Bid: " + String.format("%.5f", currentBid));
            System.out.println("    Current Ask: " + String.format("%.5f", currentAsk));
            System.out.println("    Highest SELL: " + String.format("%.5f", highestSellPrice));
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // NEW STEP: MONITOR GRID FOR 15 SECONDS
            // ══════════════════════════════════════════════════════════════

            section("MONITORING GRID (15 seconds)");

            System.out.println("  Grid is now ACTIVE and monitoring price movement...");
            System.out.println("  Orders will trigger when price reaches their levels");
            System.out.println();

            int monitorDuration = 15; // seconds
            for (int sec = 1; sec <= monitorDuration; sec++) {
                Thread.sleep(1000);

                currentBid = sugar.getBid(symbol);
                currentAsk = sugar.getAsk(symbol);
                int positions = sugar.getPositionCount();
                double balance = sugar.getBalance();
                double equity = sugar.getEquity();

                System.out.println(String.format("  [%02d/%02ds] Bid: %.5f | Ask: %.5f | Positions: %d | Balance: $%.2f | Equity: $%.2f",
                    sec, monitorDuration, currentBid, currentAsk, positions, balance, equity));

                // Show if any orders triggered
                if (positions > currentPositions) {
                    System.out.println("         ⚡ NEW POSITION OPENED! Grid order triggered!");
                    currentPositions = positions;
                }
            }

            System.out.println();
            System.out.println("  ✓ Monitoring completed");
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 5: CANCEL ALL PENDING ORDERS
            //
            // SUGAR BENEFIT: cancelAll(symbol, null) - cancels ALL pending
            //                orders for symbol in one call!
            // ══════════════════════════════════════════════════════════════

            section("STEP 5: Cleanup - Cancel Pending Orders");

            System.out.println("  Cancelling all pending orders for " + symbol + "...");
            System.out.println();

            int cancelledCount = sugar.cancelAll(symbol, null);

            System.out.println("  ✓ Cancelled orders: " + cancelledCount);
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 6: CLOSE ANY OPENED POSITIONS
            //
            // SUGAR BENEFIT: closeAll(symbol) - closes ALL positions for
            //                symbol in one call! No loops, no ticket tracking.
            // ══════════════════════════════════════════════════════════════

            section("STEP 6: Cleanup - Close All Positions");

            int remainingPositions = sugar.getPositionCount();

            if (remainingPositions > 0) {
                System.out.println("  Found " + remainingPositions + " open position(s)");
                System.out.println("  (Some pending orders may have triggered)");
                System.out.println();
                System.out.println("  Closing all positions for " + symbol + "...");
                System.out.println();

                int closedPositions = sugar.closeAll(symbol);

                System.out.println("  ✓ Closed positions: " + closedPositions);
            } else {
                System.out.println("  No open positions (pending orders were cancelled before triggering)");
            }

            System.out.println();

            // Final summary
            remainingPositions = sugar.getPositionCount();
            double finalBalance = sugar.getBalance();
            double finalEquity = sugar.getEquity();

            System.out.println("  Final status:");
            System.out.println("    Positions: " + remainingPositions);
            System.out.println("    Balance: $" + String.format("%.2f", finalBalance));
            System.out.println("    Equity: $" + String.format("%.2f", finalEquity));
            System.out.println();

            account.disconnect();

            System.out.println("+============================================================+");
            System.out.println("|  >> GRID TRADING DEMO COMPLETED                            |");
            System.out.println("|                                                            |");
            System.out.println("|  What happened:                                            |");
            System.out.println("|  1. Created " + (gridLevels * 2) + " pending orders (" + gridLevels + " BUY + " + gridLevels + " SELL)              |");
            System.out.println("|  2. Monitored grid for " + monitorDuration + " seconds (watch for triggers)      |");
            System.out.println("|  3. Cleaned up: cancelled orders + closed positions        |");
            System.out.println("|                                                            |");
            System.out.println("|  Key Takeaway: Grid trading automates entries at           |");
            System.out.println("|  multiple price levels to catch market movement            |");
            System.out.println("+============================================================+\n");

        } catch (ApiExceptionMT5 e) {
            System.err.println("\n✗ MT5 Error: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\n✗ Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nPress Enter to exit...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

    private static void section(String title) {
        System.out.println("------------------------------------------------------------");
        System.out.println(" " + title);
        System.out.println("------------------------------------------------------------");
        System.out.println();
    }
}

/*══════════════════════════════════════════════════════════════════════════════

                    SUGAR API: SIMPLEST TRADING INTERFACE

══════════════════════════════════════════════════════════════════════════════

Grid Trading made EASY with MT5Sugar:

┌─────────────────────────────────────────────────────────────────────────────┐
│ OPERATION            │ SERVICE API              │ SUGAR API                 │
├─────────────────────────────────────────────────────────────────────────────┤
│ Place BUY LIMIT      │ // Get price             │ sugar.buyLimitPoints(     │
│                      │ tick = service.quote()   │   symbol, 0.01, -100,     │
│                      │ price = bid - 100*point  │   50, 100);               │
│                      │ // Build request...      │ // Done! 1 line!          │
│                      │ request.setPrice(price)  │                           │
│                      │ request.setSl/setTp...   │                           │
│                      │ service.orderSend(req)   │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Cancel all orders    │ // Get all orders        │ sugar.cancelAll(symbol,   │
│                      │ orders = service         │   null);                  │
│                      │   .openedOrders()        │ // Done! 1 line!          │
│                      │ // Loop through each     │                           │
│                      │ for (order : orders) {   │                           │
│                      │   service.orderDelete()  │                           │
│                      │ }                        │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Close all positions  │ // Get all positions     │ sugar.closeAll(symbol);   │
│                      │ positions = service      │ // Done! 1 line!          │
│                      │   .openedOrders()        │                           │
│                      │ // Loop through each     │                           │
│                      │ for (pos : positions) {  │                           │
│                      │   service.orderClose()   │                           │
│                      │ }                        │                           │
└─────────────────────────────────────────────────────────────────────────────┘

KEY SUGAR API METHODS USED:
  ✓ buyLimitPoints()  - place BUY LIMIT with point offset
  ✓ sellLimitPoints() - place SELL LIMIT with point offset
  ✓ cancelAll()       - cancel all pending orders (1 call)
  ✓ closeAll()        - close all positions (1 call)
  ✓ getSymbolSnapshot() - all symbol info in one object
  ✓ getBid/getAsk()   - direct price access
  ✓ getBalance/getEquity() - direct account info

WHEN TO USE SUGAR API:
  → For rapid prototyping of trading strategies
  → For simple trading bots without complex logic
  → When you want readable, maintainable code
  → For common scenarios (grid, breakout, simple entry/exit)


══════════════════════════════════════════════════════════════════════════════

                               HOW TO RUN

══════════════════════════════════════════════════════════════════════════════

1. Via run.bat (Recommended - fast):

   run.bat 9  or  .\run.bat 9
   

2. Via run-clean.bat (If run.bat fails with compilation errors):

   run-clean.bat 9  or  .\run-clean.bat 9

   → Stops daemon, removes target/, recompiles from scratch
   → Use this if you see "Unresolved compilation problem" errors

3. Via Maven:
   mvnd compile exec:java -Dexec.args="9"


══════════════════════════════════════════════════════════════════════════════

                              NEXT STEPS

══════════════════════════════════════════════════════════════════════════════

1. Example 7 (Simple Trading Scenario)
   → Basic buy/sell with Sugar API
   → openBuy(), openSell(), closeAll()
   → run.bat 7

2. Example 8 (Risk Management Scenario)
   → Stop-Loss, Take-Profit, Trailing Stop
   → Professional risk control
   → run.bat 8

3. Example 10 (Orchestrators - Advanced)
   → Full-featured trading strategies with Sugar API
   → Trend Following, Scalping, Hedging
   → run.bat 10

══════════════════════════════════════════════════════════════════════════════*/
