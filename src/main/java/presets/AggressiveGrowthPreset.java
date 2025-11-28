/*══════════════════════════════════════════════════════════════════════════════
 FILE: AggressiveGrowthPreset.java — MULTI-ORCHESTRATOR TRADING STRATEGY

 LEVEL: 5 (Advanced Preset)
 DIFFICULTY: Advanced
 RISK LEVEL: ⚠️ AGGRESSIVE (Multiple simultaneous strategies)

 PURPOSE:
   Demonstrates how to combine multiple trading orchestrators into a single
   adaptive strategy that adjusts to market conditions in real-time.

   This preset runs 3-4 different orchestrators in sequence:
   1. SCALPING - Quick market test (warm-up trade)
   2. TREND FOLLOWING or HEDGING - Adaptive decision based on scalping result
   3. BREAKOUT - Final opportunity capture
   4. Comprehensive performance report

 🎯 WHY USE MULTI-ORCHESTRATOR PRESET?

   SINGLE STRATEGY:                   AGGRESSIVE GROWTH PRESET:
   ══════════════════════════════════════════════════════════════════════
   One approach only                  3-4 strategies working together
   Fixed behavior                     Adaptive to market conditions
   Single risk exposure               Diversified approach
   Limited opportunities              Multiple entry opportunities
   No adaptation                      Learns from each phase

 📚 WHAT YOU'LL LEARN:
   • How to chain multiple orchestrators together
   • Adaptive strategy selection based on performance
   • Multi-phase trading sessions
   • Performance tracking across strategies
   • Risk management across multiple approaches

 ⚠️ RISK WARNING:
   This preset executes MULTIPLE strategies sequentially, which means:
   • Higher total capital exposure
   • More trades = more commission costs
   • Complexity increases risk of losses
   • Best for experienced traders only

 ORCHESTRATORS USED:
   1. ScalpingOrchestrator - Quick in/out for market test
   2. TrendFollowingOrchestrator - Capture momentum (if scalping profitable)
   3. HedgingOrchestrator - Protect capital (if scalping unprofitable)
   4. BreakoutOrchestrator - Final opportunity entry

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                               HOW TO RUN                                 ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  From run.bat:
    run.bat 11 1                           # Direct launch (or .\run.bat 11 1)
    mvnd exec:java -Dexec.args="11 1"      # Via Maven

  From Maven:
    mvnd compile exec:java -Dexec.mainClass="presets.PresetDemo"

  Or directly in your code:
    MT5Sugar sugar = new MT5Sugar(account);
    AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
    preset.execute();   

══════════════════════════════════════════════════════════════════════════════*/

package presets;

import io.metarpc.mt5.MT5Sugar;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import orchestrators.*;

public class AggressiveGrowthPreset {

    private final MT5Sugar sugar;
    private String symbol = "EURUSD";
    private double initialBalance;

    /**
     * Creates new Aggressive Growth Preset instance
     *
     * @param sugar MT5Sugar instance for trading operations
     */
    public AggressiveGrowthPreset(MT5Sugar sugar) {
        this.sugar = sugar;
    }

    /**
     * Sets the trading symbol for all orchestrators
     *
     * @param symbol Symbol to trade (e.g., "EURUSD", "GBPUSD")
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Executes the multi-orchestrator trading session
     *
     * This method runs a sophisticated 3-phase trading strategy:
     *
     * PHASE 1: SCALPING (Market Test)
     *   • Quick BUY trade with tight stops (10/20 points)
     *   • Risk: $20
     *   • Purpose: Test market conditions
     *
     * PHASE 2: ADAPTIVE DECISION
     *   • IF scalping profit > $5 → Run TREND FOLLOWING
     *     - Capture momentum with $50 risk
     *     - Wider stops (60/120 points)
     *     - Trailing stop at 30 points
     *   • ELSE → Run HEDGING
     *     - Protect capital with $30 risk
     *     - Hedge trigger at 40 points
     *
     * PHASE 3: BREAKOUT (Final Opportunity)
     *   • Set pending orders 25 points from current price
     *   • Risk: $40
     *   • SL: 40 points, TP: 80 points
     *
     * PHASE 4: PERFORMANCE REPORT
     *   • Shows P/L for each orchestrator
     *   • Calculates success rate
     *   • Displays total session profit/loss
     *
     * @throws ApiExceptionMT5 if any trading operation fails
     * @throws InterruptedException if execution is interrupted
     */
    public void execute() throws ApiExceptionMT5, InterruptedException {
        printHeader();

        initialBalance = sugar.getBalance();
        System.out.println("  Starting balance: $" + String.format("%.2f", initialBalance));
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // PHASE 1: SCALPING - Market warm-up
        //
        // ORCHESTRATOR BENEFIT:
        //   Instead of writing manual scalping logic, we use
        //   ScalpingOrchestrator which handles:
        //   • Auto volume calculation from risk amount
        //   • Tight SL/TP placement (10/20 points)
        //   • Quick execution and monitoring
        //   • Automatic position management
        //
        // PURPOSE:
        //   Quick market test to gauge current conditions
        //   If profitable → momentum exists (run trend following)
        //   If unprofitable → choppy market (run hedging)
        // ═══════════════════════════════════════════════════════════════
        System.out.println("+============================================================+");
        System.out.println("|  PHASE 1: SCALPING ORCHESTRATOR (Market Test)              |");
        System.out.println("+============================================================+");
        System.out.println();

        double balanceAfterScalping = 0;

        try {
            ScalpingOrchestrator scalper = new ScalpingOrchestrator(sugar);
            scalper.setSymbol(symbol);
            scalper.setRiskAmount(20.0);
            scalper.setStopLossPoints(10);
            scalper.setTakeProfitPoints(20);
            scalper.execute();

            balanceAfterScalping = sugar.getBalance();
        } catch (ApiExceptionMT5 e) {
            System.err.println("  X Scalping failed: " + e.getMessage());
            balanceAfterScalping = sugar.getBalance();
        }

        double scalpingProfit = balanceAfterScalping - initialBalance;
        System.out.println("  >> Scalping result: " + (scalpingProfit >= 0 ? "+" : "") +
                         String.format("%.2f", scalpingProfit));
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // PHASE 2: ADAPTIVE DECISION - Trend Following OR Hedging
        //
        // PRESET INTELLIGENCE:
        //   This is where preset shines! Based on Phase 1 result,
        //   we intelligently choose the next strategy:
        //
        //   IF scalping profitable ($5+):
        //     → Market has momentum
        //     → Launch TREND FOLLOWING orchestrator
        //     → Capture the movement with trailing stops
        //
        //   ELSE (scalping unprofitable):
        //     → Market is choppy/ranging
        //     → Launch HEDGING orchestrator
        //     → Protect capital with opposite positions
        //
        // ORCHESTRATOR BENEFIT:
        //   • TrendFollowingOrchestrator: Auto trailing stops, wider TP
        //   • HedgingOrchestrator: Auto opposite position management
        //   • No need to write complex logic twice!
        // ═══════════════════════════════════════════════════════════════
        System.out.println("+============================================================+");
        System.out.println("|  PHASE 2: ADAPTIVE DECISION                                |");
        System.out.println("+============================================================+");
        System.out.println();

        double balanceAfterPhase2 = 0;

        if (scalpingProfit > 5.0) {
            // Good start - follow the trend!
            System.out.println("  >> DECISION: Scalping profitable ($" +
                             String.format("%.2f", scalpingProfit) + ")");
            System.out.println("     Launching TREND FOLLOWING to catch momentum!");
            System.out.println();

            try {
                TrendFollowingOrchestrator trend = new TrendFollowingOrchestrator(sugar);
                trend.setSymbol(symbol);
                trend.setRiskAmount(50.0);
                trend.setStopLossPoints(60);
                trend.setTakeProfitPoints(120);
                trend.setTrailingStopPoints(30);
                trend.execute();

                balanceAfterPhase2 = sugar.getBalance();
            } catch (ApiExceptionMT5 e) {
                System.err.println("  X Trend following failed: " + e.getMessage());
                balanceAfterPhase2 = sugar.getBalance();
            }

        } else {
            // Bad start - activate hedging!
            System.out.println("  >> DECISION: Scalping unprofitable ($" +
                             String.format("%.2f", scalpingProfit) + ")");
            System.out.println("     Launching HEDGING to protect capital!");
            System.out.println();

            try {
                HedgingOrchestrator hedge = new HedgingOrchestrator(sugar);
                hedge.setSymbol(symbol);
                hedge.setRiskAmount(30.0);
                hedge.setStopLossPoints(80);
                hedge.setTakeProfitPoints(120);
                hedge.setHedgeTriggerPoints(40);
                hedge.execute(true); // BUY first

                balanceAfterPhase2 = sugar.getBalance();
            } catch (ApiExceptionMT5 e) {
                System.err.println("  X Hedging failed: " + e.getMessage());
                balanceAfterPhase2 = sugar.getBalance();
            }
        }

        double phase2Profit = balanceAfterPhase2 - balanceAfterScalping;
        System.out.println("  >> Phase 2 result: " + (phase2Profit >= 0 ? "+" : "") +
                         String.format("%.2f", phase2Profit));
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // PHASE 3: BREAKOUT - Final opportunity
        //
        // ORCHESTRATOR BENEFIT:
        //   BreakoutOrchestrator places pending orders both above AND
        //   below current price, waiting for breakout in either direction.
        //
        //   Manual alternative would require:
        //   • Get current price
        //   • Calculate BUY STOP and SELL STOP levels
        //   • Place two pending orders
        //   • Monitor for execution
        //   • Cancel unfilled order
        //   • Manage executed position
        //
        //   With BreakoutOrchestrator: Just call .execute()!
        //
        // PURPOSE:
        //   Capture final opportunity regardless of direction
        //   Works in both trending and ranging markets
        // ═══════════════════════════════════════════════════════════════
        System.out.println("+============================================================+");
        System.out.println("  PHASE 3: BREAKOUT ORCHESTRATOR (Final Entry)              ");
        System.out.println("+============================================================+");
        System.out.println();

        System.out.println("  Setting up breakout trap for final opportunity...");
        System.out.println();

        double finalBalance = 0;

        try {
            BreakoutOrchestrator breakout = new BreakoutOrchestrator(sugar);
            breakout.setSymbol(symbol);
            breakout.setRiskAmount(40.0);
            breakout.setBreakoutDistance(25);
            breakout.setStopLossPoints(40);
            breakout.setTakeProfitPoints(80);
            breakout.execute();

            finalBalance = sugar.getBalance();
        } catch (ApiExceptionMT5 e) {
            System.err.println("  X Breakout failed: " + e.getMessage());
            finalBalance = sugar.getBalance();
        }

        double breakoutProfit = finalBalance - balanceAfterPhase2;
        System.out.println("  >> Breakout result: " + (breakoutProfit >= 0 ? "+" : "") +
                         String.format("%.2f", breakoutProfit));
        System.out.println();

        // ====================================================================
        // FINAL REPORT
        // ====================================================================
        printFinalReport(scalpingProfit, phase2Profit, breakoutProfit, finalBalance);
    }

    private void printHeader() {
        System.out.println("\n+============================================================+");
        System.out.println("  AGGRESSIVE GROWTH PRESET - Multi-Orchestrator              ");
        System.out.println("+============================================================+");
        System.out.println();
        System.out.println("  ORCHESTRATORS IN THIS PRESET:");
        System.out.println("  1. Scalping Orchestrator (warm-up)");
        System.out.println("  2. Trend Following OR Hedging (adaptive)");
        System.out.println("  3. Breakout Orchestrator (final entry)");
        System.out.println();
        System.out.println("  This is a full ORCHESTRA playing together!");
        System.out.println();
    }

    private void printFinalReport(double scalpProfit, double phase2Profit,
                                  double breakoutProfit, double finalBalance) {
        double totalProfit = finalBalance - initialBalance;

        System.out.println("+============================================================+");
        System.out.println("  >> AGGRESSIVE GROWTH SESSION COMPLETED                    ");
        System.out.println("+============================================================+");
        System.out.println();
        System.out.println("  ORCHESTRATORS PERFORMANCE:");
        System.out.println("  --------------------------");
        System.out.println("    1. Scalping:     " + (scalpProfit >= 0 ? "+" : "") +
                         String.format("%.2f", scalpProfit));
        System.out.println("    2. Trend/Hedge:  " + (phase2Profit >= 0 ? "+" : "") +
                         String.format("%.2f", phase2Profit));
        System.out.println("    3. Breakout:     " + (breakoutProfit >= 0 ? "+" : "") +
                         String.format("%.2f", breakoutProfit));
        System.out.println("  --------------------------");
        System.out.println("    TOTAL P/L:       " + (totalProfit >= 0 ? "+" : "") +
                         String.format("%.2f", totalProfit));
        System.out.println();
        System.out.println("  FINANCIAL SUMMARY:");
        System.out.println("  --------------------------");
        System.out.println("    Starting: $" + String.format("%.2f", initialBalance));
        System.out.println("    Ending:   $" + String.format("%.2f", finalBalance));
        System.out.println();

        // Analysis
        int profitableOrchestrators = 0;
        if (scalpProfit > 0) profitableOrchestrators++;
        if (phase2Profit > 0) profitableOrchestrators++;
        if (breakoutProfit > 0) profitableOrchestrators++;

        System.out.println("  ORCHESTRATOR ANALYSIS:");
        System.out.println("    Profitable: " + profitableOrchestrators + "/3");

        if (profitableOrchestrators == 3) {
            System.out.println("    >> PERFECT SYMPHONY! All orchestrators in harmony!");
        } else if (profitableOrchestrators >= 2) {
            System.out.println("    >> GOOD PERFORMANCE! Majority successful!");
        } else {
            System.out.println("    >> CHALLENGING SESSION. Market conditions difficult.");
        }

        System.out.println("+============================================================+\n");
    }
}

/*═════════════════════════════════════════════════════════════════════════

                  🎼 AGGRESSIVE GROWTH PRESET - END DOCUMENTATION 🎼

═══════════════════════════════════════════════════════════════════════════

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                   WHY USE MULTI-ORCHESTRATOR PRESET?                     ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  MANUAL MULTI-STRATEGY               🎼 AGGRESSIVE GROWTH PRESET
  ══════════════════════════════════════════════════════════════════════

  Strategy Setup:
  ├─ Write scalping logic manually   ├─ new ScalpingOrchestrator(sugar)
  ├─ Write trend logic manually      ├─ new TrendFollowingOrchestrator(sugar)
  ├─ Write hedging logic manually    ├─ new HedgingOrchestrator(sugar)
  └─ Write breakout logic manually   └─ new BreakoutOrchestrator(sugar)
                                        → 4 lines instead of 400+!

  Execution Flow:
  ├─ Run scalping, track result      ├─ Auto balance tracking
  ├─ IF profitable → run trend       ├─ Adaptive decision logic built-in
  ├─ ELSE → run hedging              ├─ Just call .execute()!
  └─ Run breakout for final entry    └─ All orchestrators auto-configured

  Error Handling:
  ├─ Try-catch for each strategy     ├─ Built-in error handling
  ├─ Continue on failure             ├─ Session continues automatically
  └─ Manual balance tracking         └─ Auto P/L tracking per strategy

  Reporting:
  ├─ Calculate P/L manually          ├─ Auto performance analysis
  ├─ Track wins/losses yourself      ├─ Success rate calculation
  └─ Format output yourself          └─ Professional formatted reports

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         STRATEGY FLOW DIAGRAM                            ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 1: SCALPING ORCHESTRATOR                                 │
  │  Risk: $20 | SL: 10 pts | TP: 20 pts                            │
  │  Purpose: Quick market test                                     │
  └─────────────┬───────────────────────────────────────────────────┘
                │
                ├───> Result tracked in: scalpingProfit
                │
                v
  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 2: ADAPTIVE DECISION                                     │
  │                                                                 │
  │  IF scalpingProfit > $5:                                        │
  │    ├─> Market has momentum                                      │
  │    └─> Launch TREND FOLLOWING ORCHESTRATOR                      │
  │        Risk: $50 | SL: 60 | TP: 120 | Trailing: 30              │
  │                                                                 │
  │  ELSE:                                                          │
  │    ├─> Market is choppy                                         │
  │    └─> Launch HEDGING ORCHESTRATOR                              │
  │        Risk: $30 | SL: 80 | TP: 120 | Hedge: 40                 │
  └─────────────┬───────────────────────────────────────────────────┘
                │
                ├───> Result tracked in: phase2Profit
                │
                v
  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 3: BREAKOUT ORCHESTRATOR                                 │
  │  Risk: $40 | Breakout: 25 pts | SL: 40 | TP: 80                 │
  │  Purpose: Capture final opportunity (any direction)             │
  └─────────────┬───────────────────────────────────────────────────┘
                │
                ├───> Result tracked in: breakoutProfit
                │
                v
  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 4: PERFORMANCE REPORT                                    │
  │  • P/L per orchestrator                                         │
  │  • Total session profit/loss                                    │
  │  • Success rate analysis                                        │
  │  • "Symphony" quality rating                                    │
  └─────────────────────────────────────────────────────────────────┘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         PRESET BENEFITS                                  ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  1. ADAPTIVE INTELLIGENCE:
     • Scalping tests market conditions first
     • Trend Following launches if momentum detected
     • Hedging activates if market is choppy
     • Strategy adapts to real-time conditions

  2. DIVERSIFICATION:
     • 3-4 different strategies in one session
     • Not all eggs in one basket
     • If one fails, others may succeed
     • Reduces single-strategy risk

  3. ORCHESTRATOR REUSE:
     • Each orchestrator is battle-tested
     • No need to rewrite complex logic
     • Just instantiate and configure
     • All error handling built-in

  4. COMPREHENSIVE TRACKING:
     • P/L tracked per orchestrator
     • Easy to see which strategies work
     • Performance analysis built-in
     • Professional reporting

  5. EDUCATIONAL VALUE:
     • Shows how to chain orchestrators
     • Demonstrates adaptive strategy selection
     • Example of multi-phase trading
     • Template for custom presets

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                    ⚠️ RISK MANAGEMENT WARNING ⚠️                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  TOTAL CAPITAL EXPOSURE:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Phase 1 (Scalping):        $20 risk
    Phase 2 (Trend/Hedge):     $50 or $30 risk
    Phase 3 (Breakout):        $40 risk
    ─────────────────────────────────────────────────────────────
    MAXIMUM EXPOSURE:          $110 total risk

  WORST CASE SCENARIO:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    If ALL three orchestrators hit SL:
    Loss = -$20 (scalping) -$50 (trend) -$40 (breakout)
         = -$110 total loss

    For $5000 account: -$110 = 2.2% drawdown
    For $10000 account: -$110 = 1.1% drawdown

  RECOMMENDATIONS:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  ✓ Minimum account: $5000 (for 2.2% max risk)
  ✓ Recommended: $10000+ (for 1.1% max risk)
  ✗ Do NOT use on accounts < $5000
  ⚠️ Do NOT run multiple preset sessions simultaneously
  ⚠️ Allow time between sessions for analysis

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                        USAGE EXAMPLES                                    ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  BASIC USAGE (Default settings):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    MT5Sugar sugar = new MT5Sugar(account);

    AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
    preset.execute();  // Runs on EURUSD by default

  CUSTOM SYMBOL:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
    preset.setSymbol("GBPUSD");  // Trade on GBP/USD
    preset.execute();

  WITH FULL MT5 CONNECTION:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // Read connection settings
    Gson gson = new Gson();
    JsonObject config = gson.fromJson(
        new FileReader("appsettings.json"),
        JsonObject.class
    );

    // Connect to MT5
    MT5Account account = new MT5Account(
        config.get("hostname").getAsString(),
        config.get("port").getAsInt()
    );

    // Create Sugar API
    MT5Sugar sugar = new MT5Sugar(account);

    // Run preset
    AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
    preset.setSymbol("USDJPY");
    preset.execute();

    // Disconnect
    account.disconnect();

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                       SESSION EXAMPLE OUTPUT                             ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  Starting Balance: $10000.00

  +============================================================+
  |  PHASE 1: SCALPING ORCHESTRATOR (Market Test)           |
  +============================================================+
  Entry: BUY 0.20 lots at 1.1000
  SL: 1.0990 (10 pts) | TP: 1.1020 (20 pts)
  Result: TP hit → +$18.50
  >> Scalping result: +$18.50

  +============================================================+
  |  PHASE 2: ADAPTIVE DECISION                              |
  +============================================================+
  >> DECISION: Scalping profitable ($18.50)
     Launching TREND FOLLOWING to catch momentum!

  Entry: BUY 0.08 lots at 1.1005
  SL: 1.0945 (60 pts) | TP: 1.1125 (120 pts)
  Trailing: Activated at 1.1035 (+30 pts)
  Result: TP hit → +$96.00
  >> Phase 2 result: +$96.00

  +============================================================+
  |  PHASE 3: BREAKOUT ORCHESTRATOR (Final Entry)           |
  +============================================================+
  Pending: BUY STOP at 1.1130 | SELL STOP at 1.1080
  Executed: SELL at 1.1080
  SL: 1.1120 (40 pts) | TP: 1.1000 (80 pts)
  Result: TP hit → +$64.00
  >> Breakout result: +$64.00

  +============================================================+
  |  >> AGGRESSIVE GROWTH SESSION COMPLETED                  |
  +============================================================+

  ORCHESTRATORS PERFORMANCE:
  --------------------------
    1. Scalping:     +$18.50
    2. Trend/Hedge:  +$96.00
    3. Breakout:     +$64.00
  --------------------------
    TOTAL P/L:       +$178.50

  FINANCIAL SUMMARY:
  --------------------------
    Starting: $10000.00
    Ending:   $10178.50

  ORCHESTRATOR ANALYSIS:
    Profitable: 3/3
    >> PERFECT SYMPHONY! All orchestrators in harmony!

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                          CUSTOMIZATION IDEAS                             ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  CREATE YOUR OWN PRESET:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

  1. CONSERVATIVE GROWTH PRESET:
     • Scalping: $10 risk (5/10 pts)
     • Trend: $25 risk (80/160 pts)
     • NO Breakout phase (too aggressive)
     • Total max risk: $35

  2. BALANCED GROWTH PRESET:
     • Martingale: $20 risk (start small)
     • Hedging: $30 risk (protect)
     • Scalping: $15 risk (final opportunity)
     • Total max risk: $65

  3. MOMENTUM HUNTER PRESET:
     • Trend Following: $50 risk
     • IF profitable → Breakout: $40 risk
     • IF unprofitable → Stop (no revenge trading)
     • Total max risk: $90

  4. VOLATILITY PRESET:
     • Breakout: $60 risk (wide stops)
     • Hedging: $40 risk (both directions)
     • Total max risk: $100

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                            BEST PRACTICES                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  ACCOUNT REQUIREMENTS:
  ✓ Minimum: $5000 (2.2% max risk)
  ✓ Recommended: $10000+ (1.1% max risk)
  ✓ Demo account first: Practice for 1-2 weeks

  TIMING:
  ✓ Run during high liquidity (London/NY overlap)
  ✓ Avoid major news events
  ✓ One session per day maximum
  ✗ Do NOT run multiple sessions simultaneously

  RISK MANAGEMENT:
  ✓ Never risk more than 2-3% total per session
  ✓ Keep detailed logs of each session
  ✓ If 3 consecutive losing sessions → pause and analyze
  ✗ Do NOT increase risk after losses (revenge trading)

  ANALYSIS:
  ✓ Track which orchestrator performs best
  ✓ Note market conditions during session
  ✓ Adjust orchestrator parameters based on results
  ✓ Consider removing consistently unprofitable orchestrators

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                NEXT STEPS                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  1. Practice on demo account:
     • Run 10-20 sessions
     • Track results in spreadsheet
     • Identify best/worst orchestrators
     • Adjust parameters

  2. Analyze performance:
     • Which orchestrator has highest win rate?
     • Does adaptive decision work well?
     • Should you adjust decision threshold ($5)?
     • Consider removing underperforming strategies

  3. Build custom presets:
     • Copy AggressiveGrowthPreset.java
     • Rename to MyCustomPreset.java
     • Mix different orchestrators
     • Test extensively on demo

  4. Explore other presets:
     • ConservativeGrowthPreset (lower risk)
     • BalancedGrowthPreset (medium risk)
     • Create your own combinations!

══════════════════════════════════════════════════════════════════════════════*/
