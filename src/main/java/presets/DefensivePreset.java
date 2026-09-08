/*══════════════════════════════════════════════════════════════════════════════
 FILE: DefensivePreset.java — DEFENSIVE MULTI-ORCHESTRATOR STRATEGY

 LEVEL: 5 (Advanced Preset)
 DIFFICULTY: Beginner-Intermediate
 RISK LEVEL: 🛡️ CONSERVATIVE (Capital preservation focus)

 PURPOSE:
   Demonstrates a conservative multi-orchestrator approach that prioritizes
   capital protection over aggressive growth. Unlike aggressive presets,
   this strategy:

   1. STARTS with protection (Hedging first, not scalping)
   2. USES lower risk amounts across all orchestrators
   3. WAITS between phases for market cooldown
   4. CONDITIONALLY executes final phase only if profitable

   This preset runs 3 orchestrators in sequence:
   1. HEDGING - Immediate protection with low risk ($20)
   2. SCALPING x2 - Conservative entries with tight stops ($15 each)
   3. MARTINGALE (conditional) - Only if total profit > $10

 🎯 WHY USE DEFENSIVE PRESET?

   MANUAL DEFENSIVE TRADING:           DEFENSIVE PRESET:
   ══════════════════════════════════════════════════════════════════════
   Manual hedge setup                  Auto-hedging from start
   One strategy at a time              3 orchestrators working together
   No profit-based decisions           Smart conditional execution
   Fixed approach                      Adaptive to profitability
   High stress monitoring              Automated with delays
   ══════════════════════════════════════════════════════════════════════

 📚 WHAT YOU'LL LEARN:

   ✓ How to build protection-first trading strategies
   ✓ How to use conditional orchestrator execution based on profitability
   ✓ How to chain orchestrators with time delays for market cooldown
   ✓ How to balance multiple low-risk approaches
   ✓ How to track performance across multiple strategy phases
   ✓ How to implement smart decision-making in automated trading

 ⚠️ RISK WARNING:

   Even defensive strategies carry risks:
   - Multiple orchestrators = multiple exposure points
   - Hedging reduces but doesn't eliminate risk
   - Low risk per trade still accumulates across phases
   - Market gaps can exceed stop-loss protection
   - Conditional logic may not suit all market conditions

   ALWAYS test on demo accounts first!
   NEVER risk more than you can afford to lose!

 🎼 ORCHESTRATORS USED:

   1. HedgingOrchestrator   - Opens position with immediate hedge protection
   2. ScalpingOrchestrator  - Quick conservative trades (executed twice)
   3. MartingaleOrchestrator - Progressive volume recovery (conditional)

 USAGE:
   DefensivePreset preset = new DefensivePreset(sugar);
   preset.setSymbol("EURUSD");  // optional, defaults to EURUSD
   preset.execute();

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                               HOW TO RUN                                 ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  From run.bat:
    run.bat 11 2                           # Direct launch (or .\run.bat 11 2)
    mvnd exec:java -Dexec.args="11 2"      # Via Maven

  From Maven:
    mvnd compile exec:java -Dexec.mainClass="presets.PresetDemo"

  Or directly in your code:
    MT5Sugar sugar = new MT5Sugar(account);
    DefensivePreset preset = new DefensivePreset(sugar);
    preset.execute();   
══════════════════════════════════════════════════════════════════════════════*/

package presets;

import pro.mrpc.mt5.MT5Sugar;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import orchestrators.*;

public class DefensivePreset {

    private final MT5Sugar sugar;
    private String symbol = "EURUSD";
    private double initialBalance;

    /**
     * Creates a new Defensive Preset with protection-first approach.
     *
     * This preset is designed for traders who prioritize capital preservation
     * over aggressive growth. It combines hedging, scalping, and conditional
     * martingale strategies with lower risk parameters.
     *
     * @param sugar MT5Sugar instance for high-level trading operations
     */
    public DefensivePreset(MT5Sugar sugar) {
        this.sugar = sugar;
    }

    /**
     * Sets the trading symbol for all orchestrators in this preset.
     *
     * @param symbol Trading symbol (default: "EURUSD")
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Executes the complete defensive multi-orchestrator strategy.
     *
     * STRATEGY FLOW:
     *
     * PHASE 1: HEDGING ORCHESTRATOR (Protection First)
     *   • Opens position with immediate hedge
     *   • Risk: $20 (low exposure)
     *   • SL: 100 points, TP: 150 points
     *   • Quick hedge trigger: 30 points
     *   • Goal: Start with maximum protection
     *
     * PHASE 2: SCALPING ORCHESTRATOR x2 (Conservative Growth)
     *   • 2 separate scalping trades
     *   • Risk: $15 each (very low)
     *   • SL: 8 points, TP: 15 points (tight)
     *   • 8-second delay between trades
     *   • Goal: Careful profit accumulation
     *
     * PHASE 3: MARTINGALE ORCHESTRATOR (Conditional)
     *   • Only executes if total profit > $10
     *   • Base volume: 0.01 lots
     *   • SL/TP: 15 points each
     *   • Max trades: 3 (limited exposure)
     *   • Goal: Capitalize on good performance
     *
     * The preset tracks profitability after each phase and makes intelligent
     * decisions about whether to proceed with higher-risk strategies.
     *
     * @throws ApiExceptionMT5 if any MT5 operation fails
     * @throws InterruptedException if sleep between phases is interrupted
     */
    public void execute() throws ApiExceptionMT5, InterruptedException {
        printHeader();

        initialBalance = sugar.getBalance();
        System.out.println("  Starting balance: $" + String.format("%.2f", initialBalance));
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // PHASE 1: HEDGING - Protection First Philosophy
        //
        // DEFENSIVE STRATEGY BENEFIT:
        //   Unlike aggressive presets that start with scalping to "test"
        //   the market, defensive approach PROTECTS from the start.
        //
        //   HedgingOrchestrator opens initial position and immediately
        //   places hedge (opposite position) if price moves against us.
        //
        // ORCHESTRATOR BENEFIT:
        //   • Auto opposite position placement at trigger level
        //   • Both positions managed simultaneously
        //   • Limited loss even in volatile markets
        //   • No manual monitoring needed
        //
        // RISK PARAMETERS:
        //   • Only $20 risk (vs $50 in aggressive preset)
        //   • Quick hedge trigger at 30 points (early protection)
        //   • Conservative SL: 100 pts, TP: 150 pts
        //
        // PURPOSE:
        //   Start session with capital preservation mindset
        // ═══════════════════════════════════════════════════════════════
        System.out.println("+============================================================+");
        System.out.println("|  PHASE 1: HEDGING ORCHESTRATOR (Defensive Start)        |");
        System.out.println("+============================================================+");
        System.out.println();

        System.out.println("  Starting with hedging for maximum protection...");
        System.out.println();

        double balanceAfterHedge = 0;

        try {
            HedgingOrchestrator hedge = new HedgingOrchestrator(sugar);
            hedge.setSymbol(symbol);
            hedge.setRiskAmount(20.0);  // Low risk
            hedge.setStopLossPoints(100);
            hedge.setTakeProfitPoints(150);
            hedge.setHedgeTriggerPoints(30);  // Quick hedge trigger
            hedge.execute(true); // BUY first

            balanceAfterHedge = sugar.getBalance();
        } catch (ApiExceptionMT5 e) {
            System.err.println("  X Hedging failed: " + e.getMessage());
            balanceAfterHedge = sugar.getBalance();
        }

        double hedgeProfit = balanceAfterHedge - initialBalance;
        System.out.println("  >> Hedging result: " + (hedgeProfit >= 0 ? "+" : "") +
                         String.format("%.2f", hedgeProfit));
        System.out.println();

        // Wait before next phase
        System.out.println("  Waiting 5 seconds before next phase...");
        Thread.sleep(5000);
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // PHASE 2: SCALPING x2 - Careful Profit Accumulation
        //
        // DEFENSIVE STRATEGY BENEFIT:
        //   After protecting capital with hedging, we now attempt
        //   SMALL, CONSERVATIVE profits with tight stops.
        //
        //   Running scalping TWICE with delays allows us to:
        //   • Take advantage of two separate opportunities
        //   • Give market time to settle (8s delay between trades)
        //   • Keep risk very low ($15 each trade)
        //   • Accumulate small wins safely
        //
        // ORCHESTRATOR BENEFIT:
        //   • ScalpingOrchestrator handles tight SL/TP (8/15 pts)
        //   • Auto volume calculation from $15 risk
        //   • Quick execution and monitoring
        //   • No need to write manual scalping logic twice
        //
        // RISK COMPARISON:
        //   • Aggressive preset: $20 scalping risk
        //   • Defensive preset: $15 scalping risk (25% lower!)
        //   • Two trades but separate, not simultaneous
        //
        // PURPOSE:
        //   Build profit gradually without excessive risk exposure
        // ═══════════════════════════════════════════════════════════════
        System.out.println("+============================================================+");
        System.out.println("|  PHASE 2: SCALPING ORCHESTRATOR (Conservative)          |");
        System.out.println("+============================================================+");
        System.out.println();

        System.out.println("  Executing 2 conservative scalping trades...");
        System.out.println();

        double balanceAfterScalping = balanceAfterHedge;

        for (int i = 1; i <= 2; i++) {
            System.out.println("  -- Scalping trade " + i + "/2 --");

            try {
                ScalpingOrchestrator scalper = new ScalpingOrchestrator(sugar);
                scalper.setSymbol(symbol);
                scalper.setRiskAmount(15.0);  // Very low risk
                scalper.setStopLossPoints(8);
                scalper.setTakeProfitPoints(15);
                scalper.execute();

                balanceAfterScalping = sugar.getBalance();
            } catch (ApiExceptionMT5 e) {
                System.err.println("  X Scalping trade " + i + " failed: " + e.getMessage());
            }

            if (i < 2) {
                System.out.println("  Waiting 8 seconds...");
                Thread.sleep(8000);
                System.out.println();
            }
        }

        double scalpingProfit = balanceAfterScalping - balanceAfterHedge;
        System.out.println();
        System.out.println("  >> Scalping (2 trades) result: " + (scalpingProfit >= 0 ? "+" : "") +
                         String.format("%.2f", scalpingProfit));
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // PHASE 3: CONDITIONAL MARTINGALE - Smart Risk Decision
        //
        // DEFENSIVE STRATEGY INTELLIGENCE:
        //   This is the KEY difference from aggressive presets!
        //
        //   We ONLY run Martingale (progressive volume strategy) if:
        //   → Total profit from Phase 1 + Phase 2 > $10
        //
        //   IF profitable:
        //     • We have cushion to absorb potential losses
        //     • Risk Martingale to GROW existing profit
        //     • Limited to 3 trades max (not unlimited!)
        //
        //   IF NOT profitable:
        //     • SKIP this phase entirely
        //     • Preserve remaining capital
        //     • Exit session defensively
        //
        // ORCHESTRATOR BENEFIT:
        //   • MartingaleOrchestrator handles progressive volume doubling
        //   • Auto max trades limit (3 trades only)
        //   • Built-in loss recovery logic
        //   • No need to write complex martingale math
        //
        // RISK COMPARISON:
        //   • Aggressive preset: Always runs final phase
        //   • Defensive preset: Conditional execution based on profit
        //
        // PURPOSE:
        //   Only take additional risk when we can afford it
        // ═══════════════════════════════════════════════════════════════
        System.out.println("+============================================================+");
        System.out.println("|  PHASE 3: CONDITIONAL MARTINGALE                         |");
        System.out.println("+============================================================+");
        System.out.println();

        double totalProfit = balanceAfterScalping - initialBalance;

        if (totalProfit > 10.0) {
            // We're in good profit - try martingale
            System.out.println("  >> DECISION: Total profit $" + String.format("%.2f", totalProfit));
            System.out.println("     Trying MARTINGALE to maximize gains!");
            System.out.println();

            try {
                MartingaleOrchestrator martingale = new MartingaleOrchestrator(sugar);
                martingale.setSymbol(symbol);
                martingale.setBaseVolume(0.01);
                martingale.setStopLossPoints(15);
                martingale.setTakeProfitPoints(15);
                martingale.setMaxTrades(3);  // Limited trades
                martingale.execute();

            } catch (ApiExceptionMT5 e) {
                System.err.println("  X Martingale failed: " + e.getMessage());
            }

        } else {
            // Not enough profit - skip martingale
            System.out.println("  >> DECISION: Total profit only $" + String.format("%.2f", totalProfit));
            System.out.println("     Skipping MARTINGALE (requires > $10 profit)");
            System.out.println("     Staying defensive.");
            System.out.println();
        }

        double finalBalance = sugar.getBalance();
        double martingaleProfit = finalBalance - balanceAfterScalping;

        if (totalProfit > 10.0) {
            System.out.println("  >> Martingale result: " + (martingaleProfit >= 0 ? "+" : "") +
                             String.format("%.2f", martingaleProfit));
            System.out.println();
        }

        // ====================================================================
        // FINAL REPORT
        // ====================================================================
        printFinalReport(hedgeProfit, scalpingProfit, martingaleProfit, finalBalance);
    }

    private void printHeader() {
        System.out.println("\n+============================================================+");
        System.out.println("|  DEFENSIVE PRESET - Conservative Multi-Strategy          |");
        System.out.println("+============================================================+");
        System.out.println();
        System.out.println("  ORCHESTRATORS IN THIS PRESET:");
        System.out.println("  1. Hedging Orchestrator (protection first)");
        System.out.println("  2. Scalping Orchestrator x2 (low risk)");
        System.out.println("  3. Martingale Orchestrator (conditional)");
        System.out.println();
        System.out.println("  Focus: Capital preservation with cautious growth");
        System.out.println();
    }

    private void printFinalReport(double hedgeProfit, double scalpProfit,
                                  double martingaleProfit, double finalBalance) {
        double totalProfit = finalBalance - initialBalance;

        System.out.println("+============================================================+");
        System.out.println("|  >> DEFENSIVE SESSION COMPLETED                          |");
        System.out.println("+============================================================+");
        System.out.println();
        System.out.println("  ORCHESTRATORS PERFORMANCE:");
        System.out.println("  --------------------------");
        System.out.println("    1. Hedging:      " + (hedgeProfit >= 0 ? "+" : "") +
                         String.format("%.2f", hedgeProfit));
        System.out.println("    2. Scalping x2:  " + (scalpProfit >= 0 ? "+" : "") +
                         String.format("%.2f", scalpProfit));
        System.out.println("    3. Martingale:   " + (martingaleProfit >= 0 ? "+" : "") +
                         String.format("%.2f", martingaleProfit));
        System.out.println("  --------------------------");
        System.out.println("    TOTAL P/L:       " + (totalProfit >= 0 ? "+" : "") +
                         String.format("%.2f", totalProfit));
        System.out.println();
        System.out.println("  FINANCIAL SUMMARY:");
        System.out.println("  --------------------------");
        System.out.println("    Starting: $" + String.format("%.2f", initialBalance));
        System.out.println("    Ending:   $" + String.format("%.2f", finalBalance));
        System.out.println();

        // Risk assessment
        if (totalProfit >= 0) {
            System.out.println("  >> DEFENSIVE STRATEGY SUCCESSFUL!");
            System.out.println("     Capital preserved and grew safely.");
        } else if (totalProfit > -20) {
            System.out.println("  >> SMALL LOSS - Well controlled by defensive approach.");
        } else {
            System.out.println("  >> LARGER LOSS - Consider review of market conditions.");
        }

        System.out.println("+============================================================+\n");
    }
}

/*═════════════════════════════════════════════════════════════════════════

  MANUAL DEFENSIVE TRADING           🛡️ DEFENSIVE PRESET
  ══════════════════════════════════════════════════════════════════════

  Protection Setup:
  ├─ Manually place hedge positions  ├─ new HedgingOrchestrator(sugar)
  ├─ Monitor both sides constantly   ├─ Auto hedge trigger at 30 pts
  ├─ Calculate opposite volumes      ├─ Auto volume management
  └─ Adjust SL/TP on both manually   └─ .execute() - handles everything!

  Conservative Trading:
  ├─ Place small trades manually     ├─ new ScalpingOrchestrator(sugar)
  ├─ Set tight stops yourself        ├─ Auto tight SL/TP (8/15 pts)
  ├─ Calculate risk for each         ├─ setRiskAmount($15) - auto volume
  └─ Wait between trades manually    └─ Thread.sleep() - auto delays

  Conditional Execution:
  ├─ Track profit manually           ├─ Auto balance tracking
  ├─ Decide if profitable enough     ├─ if (totalProfit > $10)
  ├─ IF yes → place martingale       ├─ new MartingaleOrchestrator(sugar)
  └─ IF no → exit carefully          └─ Automatic skip with message

  Reporting:
  ├─ Calculate P/L per strategy      ├─ Auto profit tracking per phase
  ├─ Track overall session result    ├─ Professional formatted reports
  └─ Manual spreadsheet logging      └─ Built-in performance analysis

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         STRATEGY FLOW DIAGRAM                            ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 1: HEDGING ORCHESTRATOR (Protection First)               │
  │  Risk: $20 | SL: 100 pts | TP: 150 pts | Hedge: 30 pts          │
  │  Purpose: START with capital protection                         │
  └─────────────┬───────────────────────────────────────────────────┘
                │
                ├───> Result tracked in: hedgeProfit
                ├───> 5-second delay (market cooldown)
                │
                v
  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 2: SCALPING ORCHESTRATOR x2 (Conservative Growth)        │
  │  Trade 1: Risk $15 | SL: 8 pts | TP: 15 pts                     │
  │           8-second delay                                        │
  │  Trade 2: Risk $15 | SL: 8 pts | TP: 15 pts                     │
  │  Purpose: Accumulate small profits carefully                    │
  └─────────────┬───────────────────────────────────────────────────┘
                │
                ├───> Result tracked in: scalpingProfit
                ├───> Calculate: totalProfit = hedgeProfit + scalpingProfit
                │
                v
  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 3: CONDITIONAL DECISION                                  │
  │                                                                 │
  │  IF totalProfit > $10:                                          │
  │    ├─> We have profit cushion                                   │
  │    └─> Launch MARTINGALE ORCHESTRATOR                           │
  │        Risk: 0.01 base volume | SL/TP: 15 pts | Max: 3 trades   │
  │        Purpose: Grow existing profit                            │
  │                                                                 │
  │  ELSE:                                                          │
  │    ├─> Insufficient profit                                      │
  │    └─> SKIP MARTINGALE (stay defensive!)                        │
  │        Purpose: Preserve capital                                │
  └─────────────┬───────────────────────────────────────────────────┘
                │
                ├───> Result tracked in: martingaleProfit
                │
                v
  ┌─────────────────────────────────────────────────────────────────┐
  │  PHASE 4: PERFORMANCE REPORT                                    │
  │  • P/L per orchestrator                                         │
  │  • Total session profit/loss                                    │
  │  • Risk assessment and recommendations                          │
  └─────────────────────────────────────────────────────────────────┘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         DEFENSIVE PRESET BENEFITS                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  1. PROTECTION-FIRST PHILOSOPHY:
     • Hedging starts immediately (not as fallback)
     • Opposite positions limit loss in volatile markets
     • Quick hedge trigger at 30 points
     • Capital preservation prioritized over growth

  2. LOWER RISK EXPOSURE:
     • Phase 1: $20 risk (vs $50+ in aggressive)
     • Phase 2: $15 per trade (vs $20+ in aggressive)
     • Phase 3: Conditional only (vs always executed)
     • Total max risk: ~$50 (vs $110 in aggressive)

  3. TIME DELAYS FOR SAFETY:
     • 5-second wait after hedging (market settles)
     • 8-second delays between scalping trades
     • Prevents overtrading and rushed decisions
     • Allows market to breathe between phases

  4. CONDITIONAL EXECUTION INTELLIGENCE:
     • Martingale runs ONLY if profitable ($10+)
     • No revenge trading after losses
     • Preserves capital when session is negative
     • Smart risk-taking based on performance

  5. SUITABLE FOR BEGINNERS:
     • Lower risk parameters throughout
     • Conservative approach easier to understand
     • Limited maximum exposure ($50)
     • Good learning tool for risk management

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                    ⚠️ RISK MANAGEMENT WARNING ⚠️                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  TOTAL CAPITAL EXPOSURE:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Phase 1 (Hedging):         $20 risk
    Phase 2 (Scalping x2):     $15 + $15 = $30 risk
    Phase 3 (Martingale):      ~$20 risk (conditional)
    ─────────────────────────────────────────────────────────────
    MAXIMUM EXPOSURE:          ~$70 total risk (if all execute)

  BEST CASE SCENARIO (Martingale skipped):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    If total profit < $10, martingale is skipped
    Maximum risk = $20 (hedge) + $30 (scalping) = $50 only

    For $2000 account: $50 = 2.5% risk
    For $5000 account: $50 = 1.0% risk

  WORST CASE SCENARIO (All phases execute + all SL hit):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Loss = -$20 (hedge) -$30 (scalping) -$20 (martingale)
         = -$70 total loss

    For $2000 account: -$70 = 3.5% drawdown
    For $5000 account: -$70 = 1.4% drawdown

  RECOMMENDATIONS:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  ✓ Minimum account: $2000 (for 3.5% max risk)
  ✓ Recommended: $5000+ (for 1.4% max risk)
  ✓ Perfect for beginners on demo accounts
  ✗ Do NOT reduce risk amounts below $10 (spread/commission impact)
  ⚠️ Do NOT run multiple sessions simultaneously
  ⚠️ Allow 30+ minutes between sessions

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                       SESSION EXAMPLE OUTPUT                             ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  Starting Balance: $5000.00

  +============================================================+
  |  PHASE 1: HEDGING ORCHESTRATOR (Defensive Start)        |
  +============================================================+
  Entry: BUY 0.20 lots at 1.1000
  SL: 1.0900 (100 pts) | TP: 1.1150 (150 pts)
  Hedge trigger: 1.0970 (30 pts from entry)
  Result: TP hit → +$30.00
  >> Hedging result: +$30.00

  Waiting 5 seconds before next phase...

  +============================================================+
  |  PHASE 2: SCALPING ORCHESTRATOR (Conservative)          |
  +============================================================+

  -- Scalping trade 1/2 --
  Entry: BUY 0.19 lots at 1.1005
  SL: 1.0997 (8 pts) | TP: 1.1020 (15 pts)
  Result: TP hit → +$14.25
  Waiting 8 seconds...

  -- Scalping trade 2/2 --
  Entry: SELL 0.19 lots at 1.1010
  SL: 1.1018 (8 pts) | TP: 1.0995 (15 pts)
  Result: TP hit → +$14.25

  >> Scalping (2 trades) result: +$28.50

  +============================================================+
  |  PHASE 3: CONDITIONAL MARTINGALE                         |
  +============================================================+
  >> DECISION: Total profit $58.50
     Trying MARTINGALE to maximize gains!

  Trade 1: 0.01 lots → TP hit → +$1.50
  Trade 2: 0.02 lots → TP hit → +$3.00
  Trade 3: 0.04 lots → TP hit → +$6.00
  >> Martingale result: +$10.50

  +============================================================+
  |  >> DEFENSIVE SESSION COMPLETED                          |
  +============================================================+

  ORCHESTRATORS PERFORMANCE:
  --------------------------
    1. Hedging:      +$30.00
    2. Scalping x2:  +$28.50
    3. Martingale:   +$10.50
  --------------------------
    TOTAL P/L:       +$69.00

  FINANCIAL SUMMARY:
  --------------------------
    Starting: $5000.00
    Ending:   $5069.00

  >> DEFENSIVE STRATEGY SUCCESSFUL!
     Capital preserved and grew safely.

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                    AGGRESSIVE vs DEFENSIVE COMPARISON                    ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  FEATURE                    AGGRESSIVE PRESET    DEFENSIVE PRESET
  ═══════════════════════════════════════════════════════════════════════
  First Strategy             Scalping (test)      Hedging (protect)
  Phase 1 Risk              $50                  $20 (60% lower!)
  Phase 2 Risk              $50 or $30           $15 x2 (lower total)
  Phase 3 Execution         Always               Conditional (if +$10)
  Total Max Risk            $110                 $70 (36% lower!)
  Time Delays               None                 5s, 8s delays
  Minimum Account           $5000                $2000 (more accessible)
  Strategy Philosophy       Growth-focused       Protection-focused
  Difficulty Level          Advanced             Beginner-Intermediate
  Martingale Limit          Not specified        3 trades max
  Best For                  Experienced traders  Conservative traders

  WHEN TO USE EACH:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  Use AGGRESSIVE when:                Use DEFENSIVE when:
  ✓ You have $10,000+ account         ✓ You have $2,000-$5,000 account
  ✓ Experienced with trading          ✓ New to automated trading
  ✓ Can tolerate higher risk          ✓ Capital preservation priority
  ✓ Strong trending market            ✓ Uncertain/choppy market
  ✓ Want maximum profit potential     ✓ Want steady conservative growth

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                          CUSTOMIZATION IDEAS                             ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  CREATE YOUR OWN DEFENSIVE VARIATIONS:
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

  1. ULTRA-CONSERVATIVE PRESET:
     • Hedging: $10 risk (150/200 pts)
     • Scalping: $8 x2 (6/12 pts)
     • NO Martingale at all
     • Total max risk: $26

  2. HEDGING-ONLY PRESET:
     • Run 3 hedging sessions sequentially
     • Each with $15 risk
     • Different hedge trigger levels (30, 40, 50 pts)
     • Total max risk: $45

  3. PROFIT-CONDITIONAL PRESET:
     • Scalping: $15 risk
     • IF profit > $5 → Hedging: $20 risk
     • IF profit > $15 → Trend: $25 risk
     • Scales up only when winning
     • Total max risk: $60

  4. TIME-BASED DEFENSIVE:
     • Hedging: $20 risk
     • Wait 60 seconds
     • Scalping: $15 risk
     • Wait 120 seconds
     • Breakout: $25 risk (if +$20)
     • Total max risk: $60

══════════════════════════════════════════════════════════════════════════════*/
