/*==============================================================================
 FILE: Program.java — MAIN ENTRY POINT

 PURPOSE:
   Launcher for JavaMT5 examples demonstrating MT5 trading automation.

 USAGE:
   run.bat <number>              - Run example
   run.bat <number> <choice>     - Run with sub-choice (orchestrators/presets)

 LEARNING PATH:
   This project was created to learn MT5 low-level (gRPC) methods first.
   Then explore wrappers, sugar API, orchestrators, and presets.

   Recommended order:
   1. Low-level (MT5Account)  - Learn gRPC protocol [1-3]
   2. Service (MT5Service)    - Learn wrappers [4-6]
   3. Sugar (MT5Sugar)        - Learn convenience API [7-9]
   4. Orchestrators           - Learn strategies [10]
   5. Presets                 - Learn multi-strategy systems [11]

==============================================================================*/

public class Program {

    public static void main(String[] args) {
        // Check if arguments provided
        if (args.length == 0) {
            showUsage();
            return;
        }

        // Extract main mode and sub-arguments
        String mode = args[0].toLowerCase();
        String[] subArgs = new String[0];
        if (args.length > 1) {
            subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        }

        // Run selected example
        try {
            switch (mode) {
                // LEVEL 1: LOW-LEVEL (MT5Account - gRPC)
                case "1":
                    runDemo("examples.lowlevel.MarketDataExample", subArgs);
                    break;
                case "2":
                    runDemo("examples.lowlevel.TradingCalculationsExample", subArgs);
                    break;
                case "3":
                    runDemo("examples.lowlevel.StreamingExample", subArgs);
                    break;

                // LEVEL 2: SERVICE (MT5Service - Wrappers)
                case "4":
                    runDemo("examples.services.MarketDataServiceExample", subArgs);
                    break;
                case "5":
                    runDemo("examples.services.TradingServiceExample", subArgs);
                    break;
                case "6":
                    runDemo("examples.services.StreamingServiceExample", subArgs);
                    break;

                // LEVEL 3: SUGAR (MT5Sugar - Convenience)
                case "7":
                    runDemo("examples.sugar.SimpleTradingScenario", subArgs);
                    break;
                case "8":
                    runDemo("examples.sugar.RiskManagementScenario", subArgs);
                    break;
                case "9":
                    runDemo("examples.sugar.GridTradingScenario", subArgs);
                    break;

                // LEVEL 4: ADVANCED (Orchestrators & Presets)
                case "10":
                    runDemo("examples.orchestrators.OrchestratorDemo", subArgs);
                    break;
                case "11":
                    runDemo("examples.presets.PresetDemo", subArgs);
                    break;

                default:
                    System.out.println("\n❌ Invalid option: " + mode);
                    System.out.println();
                    showUsage();
                    System.exit(1);
            }

        } catch (Exception ex) {
            System.out.println("\n❌ ERROR: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void runDemo(String className, String[] args) throws Exception {
        Class<?> clazz = Class.forName(className);
        java.lang.reflect.Method mainMethod = clazz.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object) args);
    }

    private static void showUsage() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   JavaMT5 - Examples Launcher                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Copy and paste these commands into your terminal:");
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────┐");
        System.out.println("│ LEVEL 1: LOW-LEVEL (MT5Account - gRPC Protocol)                 │");
        System.out.println("└──────────────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("  run.bat 1" );  // or Specifying the full path - .\run.bat 1
        System.out.println("    → MarketDataExample.java");
        System.out.println("    → Account info, symbols, history, DOM");
        System.out.println();
        System.out.println("  run.bat 2");
        System.out.println("    → TradingCalculationsExample.java");
        System.out.println("    → Margin, profit, validation");
        System.out.println();
        System.out.println("  run.bat 3");
        System.out.println("    → StreamingExample.java");
        System.out.println("    → Real-time ticks, trades, positions");
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────┐");
        System.out.println("│ LEVEL 2: SERVICE (MT5Service - Wrappers)                         │");
        System.out.println("└──────────────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("  run.bat 4");
        System.out.println("    → MarketDataServiceExample.java");
        System.out.println("    → Simplified market data access");
        System.out.println();
        System.out.println("  run.bat 5");
        System.out.println("    → TradingServiceExample.java");
        System.out.println("    → Simplified trading operations");
        System.out.println();
        System.out.println("  run.bat 6");
        System.out.println("    → StreamingServiceExample.java");
        System.out.println("    → Simplified real-time streams");
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────┐");
        System.out.println("│ LEVEL 3: SUGAR (MT5Sugar - Convenience API)                      │");
        System.out.println("└──────────────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("  run.bat 7");
        System.out.println("    → SimpleTradingScenario.java");
        System.out.println("    → Basic buy/sell operations");
        System.out.println();
        System.out.println("  run.bat 8");
        System.out.println("    → RiskManagementScenario.java");
        System.out.println("    → Stop-loss, take-profit, trailing");
        System.out.println();
        System.out.println("  run.bat 9");
        System.out.println("    → GridTradingScenario.java");
        System.out.println("    → Grid strategy implementation");
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────┐");
        System.out.println("│ LEVEL 4: ADVANCED (Strategies)                                   │");
        System.out.println("└──────────────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("  run.bat 10 1");
        System.out.println("    → ScalpingOrchestrator.java");
        System.out.println();
        System.out.println("  run.bat 10 2");
        System.out.println("    → TrendFollowingOrchestrator.java");
        System.out.println();
        System.out.println("  run.bat 10 3");
        System.out.println("    → HedgingOrchestrator.java");
        System.out.println();
        System.out.println("  run.bat 10 4");
        System.out.println("    → BreakoutOrchestrator.java");
        System.out.println();
        System.out.println("  run.bat 10 5");
        System.out.println("    → MartingaleOrchestrator.java");
        System.out.println();
        System.out.println("  run.bat 10 0");
        System.out.println("    → Run all 5 orchestrators");
        System.out.println();
        System.out.println("  run.bat 11 1");
        System.out.println("    → AggressiveGrowthPreset.java");
        System.out.println();
        System.out.println("  run.bat 11 2");
        System.out.println("    → DefensivePreset.java");
        System.out.println();
        System.out.println("  run.bat 11 0");
        System.out.println("    → Run both presets");
        System.out.println();
        System.out.println("──────────────────────────────────────────────────────────────────");
        System.out.println("LEARNING PATH:");
        System.out.println("  1→3 (low-level) → 4→6 (wrappers) → 7→9 (sugar) → 10 → 11");
        System.out.println();
        System.out.println("NOTE: All examples use settings from appsettings.json");
        System.out.println();
    }
}

/*
==============================================================================
 TROUBLESHOOTING: Protobuf Compilation Errors
==============================================================================

If you encounter errors like:
  "java.lang.Error: Unresolved compilation problem"
  "at mt5_term_api.MarketInfoGrpc.newBlockingStub(...)"

This means Maven daemon is using cached/corrupted protobuf-generated classes.

SOLUTION:
---------
1. Stop all Maven daemons:
   mvnd --stop

2. Clean compile the project:
   mvnd clean compile

3. Run your example again:
   run.bat <number>

WHY THIS HAPPENS:
-----------------
The Maven daemon (mvnd) caches compiled classes in memory for faster builds.
Sometimes when protobuf files are regenerated, the daemon holds old/broken
versions of generated gRPC stub classes (like MarketInfoGrpc.java), causing
"Unresolved compilation problem" errors at runtime.

Stopping the daemon forces a fresh compilation of all protobuf-generated code.

ALTERNATIVE (if mvnd --stop doesn't work):
------------------------------------------
1. Manually delete the target directory:
   rm -rf target
   or
   Remove-Item -Path target -Recurse -Force  (PowerShell)

2. Compile again:
   mvnd compile

==============================================================================
 TROUBLESHOOTING: Programs hang on exit / Streams don't stop
==============================================================================

If your program doesn't exit cleanly or hangs after "Closing in X seconds...":

ROOT CAUSE:
-----------
gRPC streaming subscriptions (onSymbolTick, onTrade, etc.) continue running
in background threads even after your main code finishes.

SOLUTION:
---------
Always call BOTH disconnect() and close() in the correct order:

} finally {
    if (service != null) {
        // Step 1: Disconnect from MT5 - CANCELS EVENT SUBSCRIPTIONS
        service.disconnect();

        // Step 2: Close gRPC channel - FREES JVM RESOURCES
        service.getAccount().close();
    }
    // Step 3: Force exit to ensure cleanup
    System.exit(0);
}

For MT5Account (low-level):
    account.disconnect();  // Cancel subscriptions
    account.close();       // Free resources

WHAT EACH METHOD DOES:
-----------------------
• disconnect()    - Disconnects from MT5 terminal, cancels all subscriptions on server side
• close()         - Closes gRPC channel, frees threads and memory in JVM
• System.exit(0)  - Forces JVM termination (in case of hanging threads)

IMPORTANT: Order matters! First disconnect, then close.

See also: docs/GRPC_CHANNEL_AND_STREAM_MANAGEMENT_EN.md

==============================================================================
*/
