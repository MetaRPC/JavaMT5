/*==============================================================================
   PRESET DEMO - Multi-Orchestrator Strategy Launcher

   PURPOSE:
     Interactive demo menu for running preset trading strategies.
     Presets combine multiple orchestrators into complete trading sessions
     with automatic MT5 connection, execution, and reporting.

   AVAILABLE PRESETS:
     [1] Aggressive Growth Preset
         • Scalping → Trend/Hedge (adaptive) → Breakout
         • Max risk: $110 | Min account: $5000
         • Duration: ~1-2 minutes

     [2] Defensive Preset
         • Hedging → Scalping x2 → Martingale (conditional)
         • Max risk: $70 | Min account: $2000
         • Duration: ~1-2 minutes

     [0] Run Both Sequentially
         • Executes both presets back-to-back
         • Provides comparison summary
         • Total duration: ~3-5 minutes

   FEATURES:
     ✓ Auto-connects to MT5 from appsettings.json
     ✓ Interactive menu for preset selection
     ✓ Detailed P/L tracking per preset
     ✓ Comparison mode for analyzing strategy performance

   USAGE:
     run.bat 11
     Then select preset: 1, 2, or 0

   OR directly via Maven:
     mvnd compile exec:java -Dexec.mainClass="examples.presets.PresetDemo"
==============================================================================*/

package examples.presets;

import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.MT5Service;
import io.metarpc.mt5.MT5Sugar;
import presets.AggressiveGrowthPreset;
import presets.DefensivePreset;

import java.io.InputStream;

public class PresetDemo {

    public static void main(String[] args) {
        try {
            System.out.println("\n+============================================================+");
            System.out.println("|  PRESET DEMO - Trading Scenarios                         |");
            System.out.println("+============================================================+\n");

            // Load credentials
            InputStream is = PresetDemo.class.getClassLoader().getResourceAsStream("appsettings.json");
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

            // Show menu
            // Check if choice was passed as command line argument
            String choice;
            if (args.length > 0) {
                choice = args[0];
                System.out.println("Running preset: " + choice);
                System.out.println();
            } else {
                // Show menu and exit (can't read input reliably in batch mode)
                System.out.println("+============================================================+");
                System.out.println("|  SELECT PRESET TO RUN                                      |");
                System.out.println("+============================================================+");
                System.out.println();
                System.out.println("  [1] Aggressive Growth Preset");
                System.out.println("      - Multi-Orchestrator Strategy");
                System.out.println("      - Phase 1: Scalping (warm-up)");
                System.out.println("      - Phase 2: Trend Following OR Hedging (adaptive)");
                System.out.println("      - Phase 3: Breakout (final entry)");
                System.out.println("      - Duration: ~1-2 minutes");
                System.out.println();
                System.out.println("  [2] Defensive Preset");
                System.out.println("      - Conservative Multi-Strategy");
                System.out.println("      - Phase 1: Hedging (protection first)");
                System.out.println("      - Phase 2: Scalping x2 (low risk)");
                System.out.println("      - Phase 3: Martingale (conditional, if profit > $10)");
                System.out.println("      - Duration: ~1-2 minutes");
                System.out.println();
                System.out.println("  [0] Run both presets sequentially");
                System.out.println();
                System.out.println("+============================================================+");
                System.out.println();
                System.out.println("USAGE: run.bat 11 [choice]");
                System.out.println("  Examples:");
                System.out.println("    run.bat 11 1    - Run Aggressive Growth");
                System.out.println("    run.bat 11 2    - Run Defensive");
                System.out.println("    run.bat 11 0    - Run both presets");
                System.out.println();
                return; // Exit since we can't read input in batch mode
            }

            switch (choice) {
                case "1":
                    runAggressiveGrowth(sugar);
                    break;

                case "2":
                    runDefensive(sugar);
                    break;

                case "0":
                    runAllPresets(sugar);
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
    // PRESET RUNNERS
    // ========================================================================

    private static void runAggressiveGrowth(MT5Sugar sugar) throws Exception {
        AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
        preset.setSymbol("EURUSD");
        preset.execute();
    }

    private static void runDefensive(MT5Sugar sugar) throws Exception {
        DefensivePreset preset = new DefensivePreset(sugar);
        preset.setSymbol("EURUSD");
        preset.execute();
    }

    private static void runAllPresets(MT5Sugar sugar) throws Exception {
        System.out.println("+============================================================+");
        System.out.println("|  RUNNING ALL PRESETS SEQUENTIALLY                          |");
        System.out.println("+============================================================+\n");

        double initialBalance = sugar.getBalance();

        // Preset 1
        System.out.println("------------------------------------------------------------");
        System.out.println(" [1/2] AGGRESSIVE GROWTH PRESET");
        System.out.println("------------------------------------------------------------\n");
        runAggressiveGrowth(sugar);

        double balanceAfterAggressive = sugar.getBalance();
        double aggressiveProfit = balanceAfterAggressive - initialBalance;

        System.out.println("  >> Aggressive Growth P/L: " + (aggressiveProfit >= 0 ? "+" : "") +
                         String.format("%.2f", aggressiveProfit));
        System.out.println();

        // Wait between presets
        System.out.println("  Waiting 5 seconds before next preset...");
        Thread.sleep(5000);
        System.out.println();

        // Preset 2
        System.out.println("------------------------------------------------------------");
        System.out.println(" [2/2] DEFENSIVE PRESET");
        System.out.println("------------------------------------------------------------\n");
        runDefensive(sugar);

        double finalBalance = sugar.getBalance();
        double defensiveProfit = finalBalance - balanceAfterAggressive;
        double totalProfit = finalBalance - initialBalance;

        // Final summary
        System.out.println("\n+============================================================+");
        System.out.println("|  >> ALL PRESETS COMPLETED                                   |");
        System.out.println("+============================================================+");
        System.out.println();
        System.out.println("  OVERALL SUMMARY:");
        System.out.println("  ----------------");
        System.out.println("    Starting balance: $" + String.format("%.2f", initialBalance));
        System.out.println();
        System.out.println("    Aggressive Growth P/L: " + (aggressiveProfit >= 0 ? "+" : "") +
                         String.format("%.2f", aggressiveProfit));
        System.out.println("    Defensive P/L: " + (defensiveProfit >= 0 ? "+" : "") +
                         String.format("%.2f", defensiveProfit));
        System.out.println();
        System.out.println("    Final balance: $" + String.format("%.2f", finalBalance));
        System.out.println("    Total P/L: " + (totalProfit >= 0 ? "+" : "") +
                         String.format("%.2f", totalProfit));
        System.out.println();

        int positions = sugar.getPositionCount();
        System.out.println("    Open positions: " + positions);

        System.out.println("+============================================================+");
    }
}
