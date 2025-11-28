/*==============================================================================
 FILE: Program.java — MAIN ENTRY POINT FOR JAVAMT5 EXAMPLES

 ══════════════════════════════════════════════════════════════════════════════
 WHAT IS THIS FILE?
 ══════════════════════════════════════════════════════════════════════════════

 Program.java is the central launcher for all JavaMT5 examples (commands 1-11).

 You don't need to understand Java to use this project!
 Just run commands like: run.bat 7

 This file automatically:
   • Parses your run.bat command arguments
   • Loads your MT5 credentials from appsettings.json
   • Connects to MetaTrader 5 terminal via gRPC
   • Launches the requested example/orchestrator/preset
   • Handles resource cleanup (disconnect + close)

 ══════════════════════════════════════════════════════════════════════════════
 WHY USE run.bat? — YOUR PROJECT LIFECYCLE MANAGER
 ══════════════════════════════════════════════════════════════════════════════

 run.bat manages the entire build-and-run cycle so you don't have to:

 ✓ DOWNLOADS DEPENDENCIES (once, automatically)
   • MetaRPC library (pre-compiled gRPC classes) from JitPack
   • Gson for JSON parsing
   • All transitive dependencies (gRPC, Protobuf, etc.)

 ✓ COMPILES YOUR CODE (fast Maven daemon - mvnd)
   • Incremental compilation (only changed files)
   • Daemon stays running between builds (saves 2-3 seconds)
   • Multi-threaded compilation

 ✓ RUNS YOUR EXAMPLE (mvnd exec:java)
   • Passes arguments to Program.java
   • Sets up classpath automatically
   • Streams output in real-time

 Compare:
   run.bat 7                                                        ← Type this! ✓
   mvnd compile exec:java -Dexec.args="7" -Dexec.mainClass=Program  ← Not this! ✗

 ══════════════════════════════════════════════════════════════════════════════
 AVAILABLE COMMANDS (copy-paste these into your terminal)
 ══════════════════════════════════════════════════════════════════════════════

 NOTE: If commands don't work, try adding .\ prefix:
   run.bat 1   →  If doesn't work, try: .\run.bat 1
   This is required in some terminals (PowerShell, Git Bash)

 ┌─────────────────────────────────────────────────────────────────────────┐
 │ LEVEL 1: LOW-LEVEL (MT5Account - Direct gRPC)                           │
 └─────────────────────────────────────────────────────────────────────────┘
   run.bat 1  →  MarketDataExample.java
                 Account info, symbols, quotes, order book

   run.bat 2  →  TradingCalculationsExample.java
                 Margin, profit calculations, order validation

   run.bat 3  →  StreamingExample.java
                 Real-time tick/trade/position streaming

 ┌─────────────────────────────────────────────────────────────────────────┐
 │ LEVEL 2: SERVICE (MT5Service - Simplified Wrappers)                     │
 └─────────────────────────────────────────────────────────────────────────┘
   run.bat 4  →  MarketDataServiceExample.java
                 Easier market data access with wrappers

   run.bat 5  →  TradingServiceExample.java
                 Simplified trading operations

   run.bat 6  →  StreamingServiceExample.java
                 Simplified real-time data streams

 ┌─────────────────────────────────────────────────────────────────────────┐
 │ LEVEL 3: SUGAR (MT5Sugar - One-Line Convenience API)                    │
 └─────────────────────────────────────────────────────────────────────────┘
   run.bat 7  →  SimpleTradingScenario.java
                 Basic buy/sell in 1 line: sugar.buyMarket()

   run.bat 8  →  RiskManagementScenario.java
                 Auto volume calculation by risk amount

   run.bat 9  →  GridTradingScenario.java
                 Multiple pending orders at price levels

 ┌─────────────────────────────────────────────────────────────────────────┐
 │ LEVEL 4: ORCHESTRATORS (Automated Trading Strategies)                   │
 └─────────────────────────────────────────────────────────────────────────┘
   run.bat 10      →  Show orchestrator menu
   run.bat 10 1    →  Scalping (8pt SL, 15pt TP, $20 risk) (or .\run.bat 10 1)
   run.bat 10 2    →  Trend Following (80pt SL, 160pt TP, trailing)
   run.bat 10 3    →  Hedging (auto-hedge at 50pt loss)
   run.bat 10 4    →  Breakout (pending BUY/SELL STOP orders)
   run.bat 10 5    →  Martingale (⚠️ high risk - doubles volume)
   run.bat 10 0    →  Run ALL 5 orchestrators sequentially

 ┌─────────────────────────────────────────────────────────────────────────┐
 │ LEVEL 5: PRESETS (Multi-Strategy Trading Systems)                       │
 └─────────────────────────────────────────────────────────────────────────┘
   run.bat 11      →  Show preset menu
   run.bat 11 1    →  Aggressive Growth (Scalping+Trend+Breakout)
   run.bat 11 2    →  Defensive (Hedging+Scalping+Martingale)
   run.bat 11 0    →  Run BOTH presets with P/L comparison

 ══════════════════════════════════════════════════════════════════════════════
 SPECIAL COMMANDS
 ══════════════════════════════════════════════════════════════════════════════
   run.bat          →  Show help menu
   run.bat stop     →  Stop Maven daemon

 ══════════════════════════════════════════════════════════════════════════════
 RESOURCE CLEANUP & TROUBLESHOOTING
 ══════════════════════════════════════════════════════════════════════════════

 WHY BOTH disconnect() AND close() ARE NEEDED:

   When you finish working with MT5Account (orchestrators/presets), you MUST:
     1. account.disconnect()  →  Cancel subscriptions, close MT5 connection
     2. account.close()       →  Free gRPC resources (channels, threads)

   ⚠️ Calling ONLY disconnect() without close() will leak resources!
   ⚠️ Calling ONLY close() without disconnect() may cause errors!

   Correct order:
     try {
         // Your trading code...
     } finally {
         account.disconnect();  // ← First: close MT5 connection
         account.close();       // ← Then: free gRPC resources
     }

   NOTE: Sugar examples (7-9) are standalone and manage their own lifecycle.
         Only orchestrators (10) and presets (11) use Program.java cleanup.

 IF run.bat HANGS OR DOESN'T WORK:

   Sometimes run.bat processes don't terminate properly. Symptoms:
     • Script compiles but doesn't run
     • Java processes stay running
     • Can't start new examples

   Solution - Kill all Java/Maven processes:
     taskkill /F /IM java.exe         ← Kill all Java processes
     taskkill /F /IM mvnd.exe         ← Kill Maven daemon
     run.bat stop (or .\run.bat stop) ← Stop Maven daemon (alternative)

   Then try again:
     run.bat 10 1                     ← Should work now!

   ─────────────────────────────────────────────────────────────────────────

   If you get compilation errors ("Unresolved compilation problem"):

   ⚠️ Java is finicky! Sometimes Maven caches corrupted files.
      Solution: Delete target folder - next run.bat will rebuild everything.

   Windows CMD:
     rmdir /s /q target               ← Delete target folder manually
     .\run.bat 11 2                   ← Rebuilds from scratch automatically

   Windows PowerShell:
     Remove-Item -Recurse -Force target
     .\run.bat 11 2

   Git Bash / Linux:
     rm -rf target
     ./run.bat 11 2

   This is Java's "turn it off and on again" - works 99% of the time!

 ══════════════════════════════════════════════════════════════════════════════
 RECOMMENDED LEARNING PATH
 ══════════════════════════════════════════════════════════════════════════════

   Start Here → 1→2→3 (low-level) → 4→5→6 (service) → 7→8→9 (sugar) → 10 → 11

   1-3: Understand HOW MT5 gRPC works (harder but powerful)
   4-6: Learn simplified wrappers (easier to use)
   7-9: Master one-line Sugar API (easiest!)
   10:  Build automated strategies
   11:  Combine multiple strategies

 ══════════════════════════════════════════════════════════════════════════════
 FILE STRUCTURE (for developers who want to understand the code)
 ══════════════════════════════════════════════════════════════════════════════

   main()                              Entry point, parses run.bat arguments
   runDemo()                           Launches examples 1-9 via reflection

   LEVEL 4 - ORCHESTRATORS:
     runOrchestrator()                 Menu + MT5 connection for orchestrators
     runScalping/Trend/Hedging/etc()   Individual strategy implementations
     runAllOrchestrators()             Demo mode - run all 5 sequentially

   LEVEL 5 - PRESETS:
     runPreset()                       Menu + MT5 connection for presets
     runAggressiveGrowth/Defensive()   Multi-strategy preset implementations
     runBothPresets()                  Demo mode - run both with comparison

   showUsage()                         Display help menu (run.bat with no args)

==============================================================================*/

import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.MT5Service;
import io.metarpc.mt5.MT5Sugar;
import orchestrators.*;
import presets.*;

import java.io.InputStream;

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
                    runOrchestrator(subArgs);
                    break;
                case "11":
                    runPreset(subArgs);
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

    // ══════════════════════════════════════════════════════════════════════════
    // HELPER METHOD - Reflection runner for examples 1-9
    // ══════════════════════════════════════════════════════════════════════════
    private static void runDemo(String className, String[] args) throws Exception {
        Class<?> clazz = Class.forName(className);
        java.lang.reflect.Method mainMethod = clazz.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object) args);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // LEVEL 4: ORCHESTRATORS (run.bat 10 <choice>)
    // ══════════════════════════════════════════════════════════════════════════
    private static void runOrchestrator(String[] args) throws Exception {
        System.out.println("\n+============================================================+");
        System.out.println("|  ORCHESTRATOR DEMO - Strategy Automation                 |");
        System.out.println("+============================================================+\n");

        // Load credentials
        InputStream is = Program.class.getClassLoader().getResourceAsStream("appsettings.json");
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

        // Get choice from command line args
        if (args.length == 0) {
            // No argument provided - show usage menu
            System.out.println("+============================================================+");
            System.out.println("|  AVAILABLE ORCHESTRATORS                                 |");
            System.out.println("+============================================================+");
            System.out.println();
            System.out.println("  [1] Scalping Orchestrator");
            System.out.println("      • Quick in/out trades | SL: 8pts, TP: 15pts");
            System.out.println("      • Risk: $20 per trade");
            System.out.println();
            System.out.println("  [2] Trend Following Orchestrator");
            System.out.println("      • Ride trends | SL: 80pts, TP: 160pts");
            System.out.println("      • Trailing stop: 40pts | Risk: $50 per trade");
            System.out.println();
            System.out.println("  [3] Hedging Orchestrator");
            System.out.println("      • Auto hedge at 50pts loss");
            System.out.println("      • SL: 100pts, TP: 150pts | Risk: $30 per trade");
            System.out.println();
            System.out.println("  [4] Breakout Orchestrator");
            System.out.println("      • BUY/SELL STOP pending orders");
            System.out.println("      • Breakout: 30pts | SL: 50pts, TP: 100pts");
            System.out.println("      • Risk: $40 per trade");
            System.out.println();
            System.out.println("  [5] Martingale Orchestrator");
            System.out.println("      • Double volume after loss");
            System.out.println("      • Base: 0.01 lots | Max: 5 trades");
            System.out.println("      • ⚠️ WARNING: High risk strategy!");
            System.out.println();
            System.out.println("  [0] Run all five orchestrators sequentially");
            System.out.println();
            System.out.println("+============================================================+");
            System.out.println("|  USAGE                                                   |");
            System.out.println("+============================================================+");
            System.out.println();
            System.out.println("  run.bat 10 <choice>");
            System.out.println();
            System.out.println("  Examples:");
            System.out.println("    run.bat 10 1    - Run Scalping Orchestrator");
            System.out.println("    run.bat 10 2    - Run Trend Following Orchestrator");
            System.out.println("    run.bat 10 3    - Run Hedging Orchestrator");
            System.out.println("    run.bat 10 4    - Run Breakout Orchestrator");
            System.out.println("    run.bat 10 5    - Run Martingale Orchestrator");
            System.out.println("    run.bat 10 0    - Run all 5 orchestrators sequentially");
            System.out.println();
            System.out.println("+============================================================+");
            account.disconnect();
            account.close();
            return;
        }

        String choice = args[0];
        System.out.println("Running orchestrator: " + choice);
        System.out.println();

        try {
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
                    runAllOrchestrators(sugar);
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
            }
        } finally {
            account.disconnect();
            account.close();
        }

        System.out.println("\nPress Enter to exit...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // LEVEL 5: PRESETS (run.bat 11 <choice>)
    // ══════════════════════════════════════════════════════════════════════════
    private static void runPreset(String[] args) throws Exception {
        System.out.println("\n+============================================================+");
        System.out.println("|  PRESET DEMO - Trading Scenarios                         |");
        System.out.println("+============================================================+\n");

        // Load credentials
        InputStream is = Program.class.getClassLoader().getResourceAsStream("appsettings.json");
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

        // Get choice
        if (args.length == 0) {
            System.out.println("+============================================================+");
            System.out.println("|  SELECT PRESET TO RUN                                      |");
            System.out.println("+============================================================+");
            System.out.println();
            System.out.println("  [1] Aggressive Growth Preset");
            System.out.println("      • Multi-Orchestrator Strategy");
            System.out.println("      • Phase 1: Scalping (warm-up)");
            System.out.println("      • Phase 2: Trend Following OR Hedging (adaptive)");
            System.out.println("      • Phase 3: Breakout (final entry)");
            System.out.println("      • Max risk: $110 | Min account: $5000");
            System.out.println();
            System.out.println("  [2] Defensive Preset");
            System.out.println("      • Conservative Multi-Orchestrator");
            System.out.println("      • Phase 1: Hedging (protection)");
            System.out.println("      • Phase 2: Scalping x2 (conservative wins)");
            System.out.println("      • Phase 3: Martingale (conditional)");
            System.out.println("      • Max risk: $70 | Min account: $2000");
            System.out.println();
            System.out.println("  [0] Run both presets sequentially");
            System.out.println();
            System.out.println("+============================================================+");
            System.out.println("|  USAGE                                                   |");
            System.out.println("+============================================================+");
            System.out.println();
            System.out.println("  run.bat 11 <choice>");
            System.out.println();
            System.out.println("  Examples:");
            System.out.println("    run.bat 11 1    - Run Aggressive Growth Preset");
            System.out.println("    run.bat 11 2    - Run Defensive Preset");
            System.out.println("    run.bat 11 0    - Run both presets");
            System.out.println();
            System.out.println("+============================================================+");
            account.disconnect();
            account.close();
            return;
        }

        String choice = args[0];
        System.out.println("Running preset: " + choice);
        System.out.println();

        try {
            switch (choice) {
                case "1":
                    runAggressiveGrowth(sugar);
                    break;
                case "2":
                    runDefensive(sugar);
                    break;
                case "0":
                    runBothPresets(sugar);
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
            }
        } finally {
            account.disconnect();
            account.close();
        }

        System.out.println("\nPress Enter to exit...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // ORCHESTRATOR IMPLEMENTATIONS
    // ══════════════════════════════════════════════════════════════════════════

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

    private static void runAllOrchestrators(MT5Sugar sugar) throws Exception {
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
        System.out.println("|  >> ALL 5 ORCHESTRATORS COMPLETED                        |");
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

    // ══════════════════════════════════════════════════════════════════════════
    // PRESET IMPLEMENTATIONS
    // ══════════════════════════════════════════════════════════════════════════

    private static void runAggressiveGrowth(MT5Sugar sugar) throws Exception {
        AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
        preset.execute();
    }

    private static void runDefensive(MT5Sugar sugar) throws Exception {
        DefensivePreset preset = new DefensivePreset(sugar);
        preset.execute();
    }

    private static void runBothPresets(MT5Sugar sugar) throws Exception {
        System.out.println("+============================================================+");
        System.out.println("|  RUNNING BOTH PRESETS IN DEMO MODE                       |");
        System.out.println("+============================================================+\n");

        double startBalance = sugar.getBalance();

        System.out.println("------------------------------------------------------------");
        System.out.println(" [1/2] AGGRESSIVE GROWTH PRESET");
        System.out.println("------------------------------------------------------------\n");
        runAggressiveGrowth(sugar);
        double afterPreset1 = sugar.getBalance();

        System.out.println("\n------------------------------------------------------------");
        System.out.println(" [2/2] DEFENSIVE PRESET");
        System.out.println("------------------------------------------------------------\n");
        runDefensive(sugar);
        double afterPreset2 = sugar.getBalance();

        System.out.println("\n+============================================================+");
        System.out.println("|  >> BOTH PRESETS COMPLETED                               |");
        System.out.println("+============================================================+");
        System.out.println();
        System.out.println("  Performance Summary:");
        System.out.println("    Starting Balance: $" + String.format("%.2f", startBalance));
        System.out.println("    After Aggressive: $" + String.format("%.2f", afterPreset1) +
                         " (" + String.format("%+.2f", afterPreset1 - startBalance) + ")");
        System.out.println("    After Defensive:  $" + String.format("%.2f", afterPreset2) +
                         " (" + String.format("%+.2f", afterPreset2 - afterPreset1) + ")");
        System.out.println("    Total P/L: $" + String.format("%+.2f", afterPreset2 - startBalance));
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════════════════
    // USAGE MENU
    // ══════════════════════════════════════════════════════════════════════════

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
