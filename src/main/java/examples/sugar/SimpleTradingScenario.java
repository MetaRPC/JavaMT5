/*══════════════════════════════════════════════════════════════════════════════
 FILE: SimpleTradingScenario.java — SUGAR API: SIMPLE TRADING (EASIEST START)

 LEVEL: 3 (Sugar API)
 DIFFICULTY: Beginner

 PURPOSE:
   The SIMPLEST complete trading example using MT5Sugar!
   Perfect for beginners - shows full workflow:
   • buyMarket() - open BUY position with SL/TP in one call
   • modifyPosition() - change SL/TP of open position
   • closePosition() - close by ticket number
   • getBid/getAsk/getSpread() - direct market data access

 🎯 WHY USE SUGAR API FOR SIMPLE TRADING?

   SERVICE API (Manual):                   SUGAR API (This Example):
   ────────────────────────────────────────────────────────────────────────
   // Get price                            long ticket =
   tick = service.quote(symbol);            sugar.buyMarket(
   price = tick.getAsk();                     symbol, 0.01, sl, tp,
   // Build OrderSendRequest               // Done! 1 line to open!
   request.setVolume(0.01)
   request.setPrice(price)
   request.setSl(sl).setTp(tp)
   ticket = service.orderSend(req)
     .getOrder();

 📚 WHAT YOU'LL LEARN:
   • buyMarket/sellMarket - open positions in 1 line
   • modifyPosition() - update SL/TP
   • closePosition() - close by ticket
   • normalizePrice() - ensure price has correct digits
   • getBid/getAsk/getSpread/getPoint() - direct getters

 COMPLETE TRADING WORKFLOW:
   1. Get market info (bid/ask/spread)
   2. Open position with SL/TP
   3. Modify position (tighten SL, move to breakeven, etc.)
   4. Check position status
   5. Close position

 USAGE:
   run.bat 7  (or .\run.bat 7)                             # Via run.bat
   mvnd exec:java -Dexec.args="7"           # Via Maven
══════════════════════════════════════════════════════════════════════════════*/

package examples.sugar;

import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.MT5Service;
import pro.mrpc.mt5.MT5Sugar;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;

import java.io.InputStream;

public class SimpleTradingScenario {

    public static void main(String[] args) {
        // Set UTF-8 encoding
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        System.out.println("\n+============================================================+");
        System.out.println("|  SCENARIO 1: SIMPLE TRADING WITH MODIFICATION            |");
        System.out.println("+============================================================+\n");

        try {
            // Load credentials
            InputStream is = SimpleTradingScenario.class.getClassLoader().getResourceAsStream("appsettings.json");
            if (is == null) {
                is = new java.io.FileInputStream("appsettings.json");
            }
            String json = new String(is.readAllBytes());
            is.close();

            // Parse nested JSON structure: MT5Connections.FxProDemo.*
            String fxProDemoSection = json.split("\"FxProDemo\":\\s*\\{")[1].split("\\}")[0];

            long user = Long.parseLong(fxProDemoSection.split("\"user\":\\s*")[1].split(",")[0].trim());
            String password = fxProDemoSection.split("\"password\":\\s*\"")[1].split("\"")[0];
            String grpcServer = fxProDemoSection.contains("\"grpcServer\"")
                ? fxProDemoSection.split("\"grpcServer\":\\s*\"")[1].split("\"")[0]
                : null;

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
            // STEP 1: GET SYMBOL INFORMATION
            //
            // SUGAR BENEFIT: Direct getters - no API calls needed!
            //   • getBid(symbol)    → double (not service.quote().getBid())
            //   • getAsk(symbol)    → double
            //   • getSpread(symbol) → int
            //   • getPoint(symbol)  → double
            // ══════════════════════════════════════════════════════════════

            section("STEP 1: Symbol Information");

            double bid = sugar.getBid(symbol);
            double ask = sugar.getAsk(symbol);
            int spread = sugar.getSpread(symbol);
            int digits = sugar.getDigits(symbol);
            double point = sugar.getPoint(symbol);

            System.out.println("  Symbol: " + symbol);
            System.out.println("  Bid: " + String.format("%." + digits + "f", bid));
            System.out.println("  Ask: " + String.format("%." + digits + "f", ask));
            System.out.println("  Spread: " + spread + " points");
            System.out.println("  Digits: " + digits);
            System.out.println("  Point: " + point);
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 2: OPEN BUY POSITION WITH SL/TP
            //
            // SUGAR BENEFIT: buyMarket(symbol, volume, sl, tp, comment)
            //   → Opens BUY position in 1 line!
            //   → Automatically gets Ask price
            //   → Sets SL/TP in same call
            //   → Returns ticket number directly
            // ══════════════════════════════════════════════════════════════

            section("STEP 2: Open BUY Position");

            double volume = 0.01;
            double stopLoss = sugar.normalizePrice(symbol, ask - 50 * point);  // 50 points SL
            double takeProfit = sugar.normalizePrice(symbol, ask + 100 * point); // 100 points TP

            System.out.println("  Opening BUY " + volume + " lot " + symbol);
            System.out.println("  Entry: " + String.format("%." + digits + "f", ask));
            System.out.println("  Stop Loss: " + String.format("%." + digits + "f", stopLoss) + " (-50 points)");
            System.out.println("  Take Profit: " + String.format("%." + digits + "f", takeProfit) + " (+100 points)");
            System.out.println();

            long ticket = sugar.buyMarket(symbol, volume, stopLoss, takeProfit, "Sugar Demo");

            System.out.println("  ✓ Position opened!");
            System.out.println("  Ticket: " + ticket);
            System.out.println();

            // Wait a moment
            Thread.sleep(1000);

            // ══════════════════════════════════════════════════════════════
            // STEP 3: MODIFY SL CLOSER TO ENTRY (DEMO)
            //
            // SUGAR BENEFIT: modifyPosition(ticket, newSL, newTP)
            //   → Pass null for TP to keep current value
            //   → No need to fetch current position first
            //   → Simple 1-line modification
            // ══════════════════════════════════════════════════════════════

            section("STEP 3: Modify Stop Loss");

            double currentBid = sugar.getBid(symbol);
            double newSL = sugar.normalizePrice(symbol, ask - 30 * point); // Tighter SL: -30 points instead of -50

            System.out.println("  Current Bid: " + String.format("%." + digits + "f", currentBid));
            System.out.println("  Original SL: " + String.format("%." + digits + "f", stopLoss) + " (-50 points)");
            System.out.println("  New SL: " + String.format("%." + digits + "f", newSL) + " (-30 points)");
            System.out.println();

            sugar.modifyPosition(ticket, newSL, null); // Keep current TP

            System.out.println("  ✓ Position modified!");
            System.out.println("  SL tightened from -50 to -30 points");
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 4: CHECK CURRENT POSITIONS
            //
            // SUGAR BENEFIT: Simple boolean/int getters
            //   • hasOpenPositions() → boolean
            //   • getPositionCount() → int
            //   • getProfit()        → double (total P/L)
            // ══════════════════════════════════════════════════════════════

            section("STEP 4: Check Positions");

            boolean hasPositions = sugar.hasOpenPositions();
            int positionCount = sugar.getPositionCount();
            double currentProfit = sugar.getProfit();

            System.out.println("  Has open positions: " + (hasPositions ? "Yes" : "No"));
            System.out.println("  Position count: " + positionCount);
            System.out.println("  Current P/L: $" + String.format("%.2f", currentProfit));

            // ══════════════════════════════════════════════════════════════
            // STEP 5: CLOSE POSITION
            //
            // SUGAR BENEFIT: closePosition(ticket)
            //   → Closes position by ticket in 1 line
            //   → Automatically handles Bid/Ask for BUY/SELL
            //   → No need to build close request manually
            // ══════════════════════════════════════════════════════════════

            section("STEP 5: Close Position");

            System.out.println("  Closing position #" + ticket + "...");

            sugar.closePosition(ticket);

            System.out.println("  ✓ Position closed!");
            System.out.println();

            // Final check
            positionCount = sugar.getPositionCount();
            System.out.println("  Remaining positions: " + positionCount);

            account.disconnect();

            System.out.println("+============================================================+");
            System.out.println("|  >> SCENARIO COMPLETED SUCCESSFULLY                      |");
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

Simple Trading made EASY with MT5Sugar:

┌─────────────────────────────────────────────────────────────────────────────┐
│ OPERATION            │ SERVICE API              │ SUGAR API                 │
├─────────────────────────────────────────────────────────────────────────────┤
│ Open BUY position    │ // Get price             │ long ticket =             │
│                      │ tick = service.quote()   │   sugar.buyMarket(        │
│                      │ price = tick.getAsk()    │     symbol, 0.01, sl, tp, │
│                      │ // Build request...      │     "comment");           │
│                      │ request.setVolume(0.01)  │ // Done! 1 line!          │
│                      │ request.setPrice(price)  │                           │
│                      │ request.setSl/setTp...   │                           │
│                      │ ticket = service         │                           │
│                      │   .orderSend(req)        │                           │
│                      │   .getOrder()            │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Modify position      │ // Build modify request  │ sugar.modifyPosition(     │
│                      │ request.setTicket(ticket)│   ticket, newSL, null);   │
│                      │ request.setNewSl(newSL)  │ // Done! 1 line!          │
│                      │ service.orderModify(req) │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Close position       │ // Build close request   │ sugar.closePosition(      │
│                      │ service.orderClose(      │   ticket);                │
│                      │   ticket, volume, slip)  │ // Done! 1 line!          │
└─────────────────────────────────────────────────────────────────────────────┘

KEY SUGAR API METHODS USED:
  ✓ buyMarket()       - open BUY position with SL/TP in 1 call
  ✓ sellMarket()      - open SELL position with SL/TP in 1 call
  ✓ modifyPosition()  - update SL/TP of open position
  ✓ closePosition()   - close position by ticket
  ✓ normalizePrice()  - ensure price has correct digits
  ✓ getBid/getAsk()   - direct price access (no API calls)
  ✓ getSpread/getPoint/getDigits() - direct symbol info

WHEN TO USE SUGAR API:
  → For simple trading scenarios (open, modify, close)
  → When you want readable, maintainable code
  → For quick prototyping of trading ideas
  → When Service API feels too verbose


══════════════════════════════════════════════════════════════════════════════

                               HOW TO RUN

══════════════════════════════════════════════════════════════════════════════

1. Via run.bat (Recommended - fast):
   run.bat 7  or  .\run.bat 7

2. Via run-clean.bat (If run.bat fails with compilation errors):
   run-clean.bat 7  or  .\run-clean.bat 7
   → Stops daemon, removes target/, recompiles from scratch
   → Use this if you see "Unresolved compilation problem" errors

3. Via Maven:
   mvnd compile exec:java -Dexec.args="7"


══════════════════════════════════════════════════════════════════════════════

                              NEXT STEPS

══════════════════════════════════════════════════════════════════════════════

1. Example 8 (Risk Management Scenario)
   → Fixed $ risk per trade with auto volume calculation
   → buyByRisk(), calculateVolume()
   → run.bat 8

2. Example 9 (Grid Trading Scenario)
   → Multiple pending orders at price levels
   → buyLimitPoints(), sellLimitPoints(), cancelAll()
   → run.bat 9

3. Example 10 (Orchestrators - Advanced)
   → Full-featured trading strategies
   → Trend Following, Scalping with built-in risk control
   → run.bat 10

══════════════════════════════════════════════════════════════════════════════*/