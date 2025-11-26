/*==============================================================================
   ORCHESTRATOR DEMO - Individual Strategy Testing

   PURPOSE:
     Interactive demo menu for testing individual orchestrator strategies.
     Each orchestrator is a complete automated trading system that can run
     independently with automatic MT5 connection and execution.

   AVAILABLE ORCHESTRATORS:
     [1] Scalping Orchestrator
         • Quick in/out trades | SL: 8pts, TP: 15pts
         • Risk: $20 per trade | Duration: ~10-30 seconds

     [2] Trend Following Orchestrator
         • Ride trends with trailing stops | SL: 80pts, TP: 160pts
         • Risk: $50 per trade | Trailing: 40pts | Duration: ~1-3 minutes

     [3] Hedging Orchestrator
         • Open position with auto hedge trigger | SL: 100pts, TP: 150pts
         • Risk: $30 per trade | Hedge at: 50pts | Duration: ~1-2 minutes

     [4] Breakout Orchestrator
         • Pending orders for breakouts | SL: 50pts, TP: 100pts
         • Risk: $40 per trade | Breakout: 30pts | Duration: ~1-3 minutes

     [5] Martingale Orchestrator
         • Progressive volume doubling | SL/TP: 20pts each
         • Base: 0.01 lots | Max: 5 trades | Duration: ~1-2 minutes
         • ⚠️ WARNING: High risk strategy!

     [0] Run All Five Sequentially
         • Tests all orchestrators back-to-back
         • Provides final account summary
         • Total duration: ~5-10 minutes

   FEATURES:
     ✓ Auto-connects to MT5 from appsettings.json
     ✓ Command-line argument support for automation
     ✓ Individual orchestrator testing
     ✓ Full demo mode for all 5 strategies

   USAGE:
     run.bat 10 [choice]
     Examples:
       run.bat 10 1    - Run Scalping only
       run.bat 10 0    - Run all 5 orchestrators

   OR directly via Maven:
     mvnd compile exec:java -Dexec.mainClass="examples.orchestrators.OrchestratorDemo" -Dexec.args="1"
==============================================================================*/

package examples.orchestrators;

import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.MT5Service;
import io.metarpc.mt5.MT5Sugar;
import orchestrators.*;

import java.io.InputStream;

public class OrchestratorDemo {

    public static void main(String[] args) {
        try {
            System.out.println("\n+============================================================+");
            System.out.println("|  ORCHESTRATOR DEMO - Strategy Automation                 |");
            System.out.println("+============================================================+\n");

            // Load credentials
            InputStream is = OrchestratorDemo.class.getClassLoader().getResourceAsStream("appsettings.json");
            if (is == null) {
                is = new java.io.FileInputStream("appsettings.json");
            }
            String json = new String(is.readAllBytes());
            is.close();

            // Parse JSON
            String fxProDemoSection = json.split("\"FxProDemo\":\\s*\\{")[1].split("\\}")[0];
            long user = Long.parseLong(fxProDemoSection.split("\"user\":\\s*")[1].split(",")[0].trim());
            String password = fxProDemoSection.split("\"password\":\\s*\"")[1].split("\"")[0];
            String grpcServer = fxProDemoSection.split("\"grpcServer\":\\s*\"")[1].split("\"")[0];

            System.out.println("Configuration loaded: user=" + user);
            System.out.println();

            // Connect
            MT5Account account = new MT5Account(user, password, grpcServer, null);
            MT5Service service = new MT5Service(account);
            MT5Sugar sugar = new MT5Sugar(service);

            System.out.println("► Connecting to MT5...");
            account.connectByServerName("FxPro-MT5 Demo", "EURUSD");
            System.out.println("✓ Connected\n");

            // Get choice from command line args or show menu
            String choice;

            if (args.length > 0) {
                // Use command line argument
                choice = args[0];
                System.out.println("Running orchestrator: " + choice);
                System.out.println();
            } else {
                // Show menu and wait for input
                System.out.println("+============================================================+");
                System.out.println("|  SELECT ORCHESTRATOR TO TEST                             |");
                System.out.println("+============================================================+");
                System.out.println();
                System.out.println("  [1] Scalping Orchestrator");
                System.out.println("      - Quick in/out trades");
                System.out.println("      - Tight SL/TP (8/15 points)");
                System.out.println("      - Risk: $20 per trade");
                System.out.println();
                System.out.println("  [2] Trend Following Orchestrator");
                System.out.println("      - Follow the trend");
                System.out.println("      - Wider SL/TP (80/160 points)");
                System.out.println("      - Trailing stop at 40 points");
                System.out.println("      - Risk: $50 per trade");
                System.out.println();
                System.out.println("  [3] Hedging Orchestrator");
                System.out.println("      - Open primary position");
                System.out.println("      - Hedge if loss reaches 50 points");
                System.out.println("      - Locks position to prevent further loss");
                System.out.println("      - Risk: $30 per trade");
                System.out.println();
                System.out.println("  [4] Breakout Orchestrator");
                System.out.println("      - Place BUY STOP and SELL STOP pending orders");
                System.out.println("      - Wait for price breakout");
                System.out.println("      - Cancel opposite order when triggered");
                System.out.println("      - Risk: $40 per trade");
                System.out.println();
                System.out.println("  [5] Martingale Orchestrator");
                System.out.println("      - Double volume after each loss");
                System.out.println("      - Reset to base volume after win");
                System.out.println("      - Max 5 trades");
                System.out.println("      - WARNING: High risk strategy!");
                System.out.println();
                System.out.println("  [0] Run all five orchestrators (demo mode)");
                System.out.println();
                System.out.println("+============================================================+");
                System.out.println();
                System.out.println("USAGE: run.bat 10 [choice]");
                System.out.println("  Examples:");
                System.out.println("    run.bat 10 1    - Run Scalping");
                System.out.println("    run.bat 10 2    - Run Trend Following");
                System.out.println("    run.bat 10 0    - Run all 5 orchestrators");
                System.out.println();
                return; // Exit since we can't read input in batch mode
            }

            switch (choice) {
                case "1":
                    runScalping(sugar);
                    break;

                case "2":
                    runTrendFollowing(sugar);
                    break;

                case "3":
                    runHedging(sugar);
                    break;

                case "4":
                    runBreakout(sugar);
                    break;

                case "5":
                    runMartingale(sugar);
                    break;

                case "0":
                    runAllDemo(sugar);
                    break;

                default:
                    System.out.println("Invalid choice. Exiting.");
            }

            account.disconnect();

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

    // ========================================================================
    // ORCHESTRATOR RUNNERS
    // ========================================================================

    private static void runScalping(MT5Sugar sugar) throws Exception {
        ScalpingOrchestrator scalper = new ScalpingOrchestrator(sugar);
        scalper.setSymbol("EURUSD");
        scalper.setRiskAmount(20.0);
        scalper.setStopLossPoints(8);
        scalper.setTakeProfitPoints(15);
        scalper.setMaxPositions(1);

        scalper.execute();
    }

    private static void runTrendFollowing(MT5Sugar sugar) throws Exception {
        TrendFollowingOrchestrator trend = new TrendFollowingOrchestrator(sugar);
        trend.setSymbol("EURUSD");
        trend.setRiskAmount(50.0);
        trend.setStopLossPoints(80);
        trend.setTakeProfitPoints(160);
        trend.setTrailingStopPoints(40);

        trend.execute();
    }

    private static void runHedging(MT5Sugar sugar) throws Exception {
        HedgingOrchestrator hedge = new HedgingOrchestrator(sugar);
        hedge.setSymbol("EURUSD");
        hedge.setRiskAmount(30.0);
        hedge.setStopLossPoints(100);
        hedge.setTakeProfitPoints(150);
        hedge.setHedgeTriggerPoints(50);

        hedge.execute(true); // true = BUY first
    }

    private static void runBreakout(MT5Sugar sugar) throws Exception {
        BreakoutOrchestrator breakout = new BreakoutOrchestrator(sugar);
        breakout.setSymbol("EURUSD");
        breakout.setRiskAmount(40.0);
        breakout.setBreakoutDistance(30);
        breakout.setStopLossPoints(50);
        breakout.setTakeProfitPoints(100);

        breakout.execute();
    }

    private static void runMartingale(MT5Sugar sugar) throws Exception {
        MartingaleOrchestrator martingale = new MartingaleOrchestrator(sugar);
        martingale.setSymbol("EURUSD");
        martingale.setBaseVolume(0.01);
        martingale.setStopLossPoints(20);
        martingale.setTakeProfitPoints(20);
        martingale.setMaxTrades(5);

        martingale.execute();
    }

    private static void runAllDemo(MT5Sugar sugar) throws Exception {
        System.out.println("+============================================================+");
        System.out.println("|  RUNNING ALL ORCHESTRATORS IN DEMO MODE                  |");
        System.out.println("+============================================================+\n");

        System.out.println("------------------------------------------------------------");
        System.out.println(" [1/5] SCALPING ORCHESTRATOR");
        System.out.println("------------------------------------------------------------\n");
        runScalping(sugar);

        System.out.println("\n------------------------------------------------------------");
        System.out.println(" [2/5] TREND FOLLOWING ORCHESTRATOR");
        System.out.println("------------------------------------------------------------\n");
        runTrendFollowing(sugar);

        System.out.println("\n------------------------------------------------------------");
        System.out.println(" [3/5] HEDGING ORCHESTRATOR");
        System.out.println("------------------------------------------------------------\n");
        runHedging(sugar);

        System.out.println("\n------------------------------------------------------------");
        System.out.println(" [4/5] BREAKOUT ORCHESTRATOR");
        System.out.println("------------------------------------------------------------\n");
        runBreakout(sugar);

        System.out.println("\n------------------------------------------------------------");
        System.out.println(" [5/5] MARTINGALE ORCHESTRATOR");
        System.out.println("------------------------------------------------------------\n");
        runMartingale(sugar);

        System.out.println("\n+============================================================+");
        System.out.println("|  >> ALL 5 ORCHESTRATORS COMPLETED                           |");
        System.out.println("+============================================================+");

        // Final account status
        double finalBalance = sugar.getBalance();
        double finalEquity = sugar.getEquity();
        int positions = sugar.getPositionCount();

        System.out.println();
        System.out.println("  Final Account Status:");
        System.out.println("    Balance: $" + String.format("%.2f", finalBalance));
        System.out.println("    Equity: $" + String.format("%.2f", finalEquity));
        System.out.println("    Open positions: " + positions);
        System.out.println();
    }
}

// .\run.bat 10