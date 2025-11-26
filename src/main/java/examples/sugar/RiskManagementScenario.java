/*══════════════════════════════════════════════════════════════════════════════
 FILE: RiskManagementScenario.java — SUGAR API: RISK MANAGEMENT (PROFESSIONAL)

 LEVEL: 3 (Sugar API)
 DIFFICULTY: Beginner to Intermediate

 PURPOSE:
   Professional risk management using MT5Sugar!
   This demonstrates the SAFEST way to trade - fixed risk per trade:
   • buyByRisk() - auto-calculate volume based on $ risk
   • calculateVolume() - get exact volume for risk amount
   • getAccountSnapshot() - complete account info in one call
   • closeAllBuy/closeAllSell() - directional position closing

 🎯 WHY USE SUGAR API FOR RISK MANAGEMENT?

   SERVICE API (Manual):                   SUGAR API (This Example):
   ────────────────────────────────────────────────────────────────────────
   // Calculate volume manually           long ticket = sugar.buyByRisk(
   tick = service.quote(symbol);            symbol, 50, 50.0, 100);
   tickValue = service.symbolInfo...       // Done! Auto-calculates volume
   volume = riskAmount / (sl * tickVal);   // to risk exactly $50!
   // Build OrderSendRequest...
   // Set volume, sl, tp...
   ticket = service.orderSend(req)
     .getOrder();

 📚 WHAT YOU'LL LEARN:
   • Fixed risk trading: always risk same $ amount per trade
   • buyByRisk/sellByRisk - auto position sizing
   • calculateVolume() - manual volume calculation for risk
   • getAccountSnapshot() - account metrics in one object
   • Risk/Reward ratio: TP should be > SL (e.g., 2:1)

 RISK MANAGEMENT PRINCIPLE:
   Risk same $ amount on every trade (e.g., $50)
   → Volume adjusts automatically based on SL distance
   → Wider SL = smaller volume, Tighter SL = larger volume
   → Result: consistent risk regardless of SL placement

 USAGE:
   run.bat 8                                # Via run.bat
   mvnd exec:java -Dexec.args="8"           # Via Maven
══════════════════════════════════════════════════════════════════════════════*/

package examples.sugar;

import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.MT5Service;
import io.metarpc.mt5.MT5Sugar;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;

import java.io.InputStream;

public class RiskManagementScenario {

    public static void main(String[] args) {
        // Set UTF-8 encoding
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        System.out.println("\n+============================================================+");
        System.out.println("|  SCENARIO 2: RISK MANAGEMENT TRADING                     |");
        System.out.println("+============================================================+\n");

        try {
            // Load credentials
            InputStream is = RiskManagementScenario.class.getClassLoader().getResourceAsStream("appsettings.json");
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
            // STEP 1: GET ACCOUNT SNAPSHOT
            //
            // SUGAR BENEFIT: getAccountSnapshot() returns complete account
            //                info in one object - balance, equity, margin, etc.
            // ══════════════════════════════════════════════════════════════

            section("STEP 1: Account Snapshot");

            MT5Sugar.AccountSnapshot snapshot = sugar.getAccountSnapshot();

            System.out.println("  Login: " + snapshot.login);
            System.out.println("  Balance: $" + String.format("%.2f", snapshot.balance));
            System.out.println("  Equity: $" + String.format("%.2f", snapshot.equity));
            System.out.println("  Margin Used: $" + String.format("%.2f", snapshot.margin));
            System.out.println("  Free Margin: $" + String.format("%.2f", snapshot.freeMargin));
            System.out.println("  Margin Level: " + String.format("%.2f%%", snapshot.marginLevel));
            System.out.println("  Current P/L: $" + String.format("%.2f", snapshot.profit));
            System.out.println("  Leverage: 1:" + snapshot.leverage);
            System.out.println("  Currency: " + snapshot.currency);
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 2: CALCULATE VOLUME BY RISK
            //
            // SUGAR BENEFIT: calculateVolume(symbol, slPoints, riskAmount)
            //                → Automatically calculates exact volume to risk
            //                  specified $ amount with given SL distance
            // ══════════════════════════════════════════════════════════════

            section("STEP 2: Calculate Volume by Risk");

            double riskAmount = 50.0;  // Risk $50 per trade
            double stopLossPoints = 50; // 50 points SL

            double calculatedVolume = sugar.calculateVolume(symbol, stopLossPoints, riskAmount);

            System.out.println("  Risk per trade: $" + String.format("%.2f", riskAmount));
            System.out.println("  Stop Loss: " + stopLossPoints + " points");
            System.out.println("  Calculated volume: " + String.format("%.2f", calculatedVolume) + " lots");
            System.out.println();
            System.out.println("  → If SL hits, loss = $" + String.format("%.2f", riskAmount));
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 3: OPEN FIRST POSITION WITH RISK-BASED VOLUME
            //
            // SUGAR BENEFIT: buyByRisk(symbol, slPoints, riskAmount, tpPoints)
            //                → Opens position with auto-calculated volume
            //                → If SL hits, you lose exactly $riskAmount!
            // ══════════════════════════════════════════════════════════════

            section("STEP 3: Open First Position (Risk-Based)");

            double takeProfitPoints = 100; // 100 points TP (2:1 risk/reward)

            System.out.println("  Opening BUY " + symbol + " with auto-volume");
            System.out.println("  Risk: $" + String.format("%.2f", riskAmount));
            System.out.println("  Stop Loss: " + stopLossPoints + " points");
            System.out.println("  Take Profit: " + takeProfitPoints + " points");
            System.out.println("  Risk/Reward Ratio: 1:" + (takeProfitPoints / stopLossPoints));
            System.out.println();

            long ticket1 = sugar.buyByRisk(symbol, stopLossPoints, riskAmount, takeProfitPoints);

            System.out.println("  ✓ Position opened!");
            System.out.println("  Ticket: " + ticket1);
            System.out.println("  Volume: " + String.format("%.2f", calculatedVolume) + " lots");
            System.out.println();

            // Wait a moment
            Thread.sleep(500);

            // ══════════════════════════════════════════════════════════════
            // STEP 4: CHECK MARGIN USAGE
            //
            // SUGAR BENEFIT: Direct getters - getMargin(), getFreeMargin()
            //                No need for multiple API calls
            // ══════════════════════════════════════════════════════════════

            section("STEP 4: Check Margin Usage");

            double usedMargin = sugar.getMargin();
            double freeMargin = sugar.getFreeMargin();
            double currentEquity = sugar.getEquity();

            System.out.println("  Equity: $" + String.format("%.2f", currentEquity));
            System.out.println("  Margin Used: $" + String.format("%.2f", usedMargin));
            System.out.println("  Free Margin: $" + String.format("%.2f", freeMargin));
            System.out.println("  Positions: " + sugar.getPositionCount());

            // ══════════════════════════════════════════════════════════════
            // STEP 5: OPEN SECOND POSITION
            //
            // Same buyByRisk() call - consistent risk on every trade!
            // ══════════════════════════════════════════════════════════════

            section("STEP 5: Open Second Position");

            System.out.println("  Opening second BUY position...");
            System.out.println("  Same risk parameters: $" + String.format("%.2f", riskAmount));
            System.out.println();

            long ticket2 = sugar.buyByRisk(symbol, stopLossPoints, riskAmount, takeProfitPoints);

            System.out.println("  ✓ Second position opened!");
            System.out.println("  Ticket: " + ticket2);
            System.out.println();

            // Wait a moment
            Thread.sleep(500);

            // Check total positions
            int totalPositions = sugar.getPositionCount();
            double totalMargin = sugar.getMargin();
            double totalProfit = sugar.getProfit();

            System.out.println("  Total positions: " + totalPositions);
            System.out.println("  Total margin: $" + String.format("%.2f", totalMargin));
            System.out.println("  Total P/L: $" + String.format("%.2f", totalProfit));
            System.out.println();

            // ══════════════════════════════════════════════════════════════
            // STEP 6: CLOSE ALL BUY POSITIONS
            //
            // SUGAR BENEFIT: closeAllBuy(symbol) - closes only BUY positions
            //                Also available: closeAllSell(symbol)
            // ══════════════════════════════════════════════════════════════

            section("STEP 6: Close All BUY Positions");

            System.out.println("  Closing all BUY positions for " + symbol + "...");

            int closedCount = sugar.closeAllBuy(symbol);

            System.out.println("  ✓ Closed positions: " + closedCount);
            System.out.println();

            // Final check
            int remainingPositions = sugar.getPositionCount();
            double finalEquity = sugar.getEquity();
            double finalBalance = sugar.getBalance();
            double tradingResult = finalBalance - snapshot.balance;

            System.out.println("  Remaining positions: " + remainingPositions);
            System.out.println("  Final equity: $" + String.format("%.2f", finalEquity));
            System.out.println("  Final balance: $" + String.format("%.2f", finalBalance));
            System.out.println("  Trading result: " + (tradingResult >= 0 ? "+" : "") + String.format("%.2f", tradingResult));

            account.disconnect();

            System.out.println("+============================================================+");
            System.out.println("|  >> SCENARIO COMPLETED SUCCESSFULLY                      |");
            System.out.println("|                                                            |");
            System.out.println("|  Key Takeaway: Fixed risk per trade = consistent          |");
            System.out.println("|  position sizing regardless of SL distance                |");
            System.out.println("+============================================================+\n");

        } catch (ApiExceptionMT5 e) {
            System.err.println("\n✗ MT5 Error: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\n✗ Error: " + e.getMessage());
            e.printStackTrace();
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

                    SUGAR API: PROFESSIONAL RISK MANAGEMENT

══════════════════════════════════════════════════════════════════════════════

Fixed Risk Trading made EASY with MT5Sugar:

┌─────────────────────────────────────────────────────────────────────────────┐
│ OPERATION            │ SERVICE API                │ SUGAR API               │
├─────────────────────────────────────────────────────────────────────────────┤
│ Calculate volume     │ // Get tick value          │ double volume =         │
│ for fixed risk       │ tickValue = service        │   sugar.calculateVolume │
│                      │   .symbolInfo...()         │   (symbol, 50, 50.0);   │
│                      │ // Manual calculation      │ // Done! Auto-calc!     │
│                      │ volume = riskAmount /      │                         │
│                      │   (slPoints * tickValue)   │                         │
├─────────────────────────────────────────────────────────────────────────────┤
│ Open position        │ // Calculate volume first  │ long ticket =           │
│ with fixed risk      │ volume = ...               │   sugar.buyByRisk(      │
│                      │ // Build request           │     symbol, 50, 50.0,   │
│                      │ request.setVolume(volume)  │     100);               │
│                      │ request.setSl/setTp...     │ // All in 1 line!       │
│                      │ service.orderSend(request) │                         │
├─────────────────────────────────────────────────────────────────────────────┤
│ Close BUY positions  │ // Get all positions       │ sugar.closeAllBuy(      │
│ only                 │ positions = service        │   symbol);              │
│                      │   .openedOrders()          │ // Done! 1 line!        │
│                      │ // Loop & filter BUY       │                         │
│                      │ for (pos : positions) {    │                         │
│                      │   if (pos.getType() == BUY)│                         │
│                      │     service.orderClose()   │                         │
│                      │ }                          │                         │
└─────────────────────────────────────────────────────────────────────────────┘

KEY SUGAR API METHODS USED:
  ✓ buyByRisk()       - open BUY with auto-calculated volume for risk
  ✓ sellByRisk()      - open SELL with auto-calculated volume for risk
  ✓ calculateVolume() - manually get volume for risk amount
  ✓ getAccountSnapshot() - complete account info in one object
  ✓ closeAllBuy()     - close only BUY positions
  ✓ closeAllSell()    - close only SELL positions
  ✓ getMargin/getFreeMargin() - direct margin access

RISK MANAGEMENT BENEFITS:
  ✓ Fixed $ risk per trade (e.g., always risk $50)
  ✓ Volume auto-adjusts based on SL distance
  ✓ Consistent risk regardless of market volatility
  ✓ Easy to calculate maximum drawdown
  ✓ Professional money management built-in

WHEN TO USE:
  → For professional trading with strict risk control
  → When you want consistent position sizing
  → For automated trading strategies
  → To avoid over-leveraging


══════════════════════════════════════════════════════════════════════════════

                               HOW TO RUN

══════════════════════════════════════════════════════════════════════════════

1. Via run.bat (Recommended - fast):
   run.bat 8  or  .\run.bat 8

2. Via run-clean.bat (If run.bat fails with compilation errors):
   run-clean.bat 8  or  .\run-clean.bat 8
   → Stops daemon, removes target/, recompiles from scratch
   → Use this if you see "Unresolved compilation problem" errors

3. Via Maven:
   mvnd compile exec:java -Dexec.args="8"


══════════════════════════════════════════════════════════════════════════════

                              NEXT STEPS

══════════════════════════════════════════════════════════════════════════════

1. Example 7 (Simple Trading Scenario)
   → Basic buy/sell without risk calculation
   → openBuy(), openSell() with fixed volume
   → run.bat 7

2. Example 9 (Grid Trading Scenario)
   → Multiple pending orders strategy
   → buyLimitPoints(), sellLimitPoints()
   → run.bat 9

3. Example 10 (Orchestrators - Advanced)
   → Full strategies with risk management
   → Trend Following, Scalping with built-in risk control
   → run.bat 10

══════════════════════════════════════════════════════════════════════════════*/