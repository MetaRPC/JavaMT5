/*══════════════════════════════════════════════════════════════════════════════
 FILE: MartingaleOrchestrator.java — MARTINGALE TRADING ORCHESTRATOR

 LEVEL: 4 (Orchestrator)
 DIFFICULTY: Advanced
 RISK LEVEL: ⚠️ VERY HIGH - USE WITH EXTREME CAUTION ⚠️

 PURPOSE:
   Classic Martingale strategy - double volume after losses!
   This orchestrator implements the controversial Martingale system:
   • Starts with base volume (e.g., 0.01 lots)
   • If trade wins → reset to base volume
   • If trade loses → DOUBLE the volume for next trade
   • Continue until profit or max trades reached
   • Goal: one win recovers all previous losses

 🎯 WHY USE MARTINGALE ORCHESTRATOR?

   MANUAL TRADING:                     MARTINGALE ORCHESTRATOR:
   ────────────────────────────────────────────────────────────────────────
   // Track wins/losses manually      MartingaleOrchestrator mart =
   // Calculate doubled volume           new MartingaleOrchestrator(sugar);
   // Place next trade                 mart.setBaseVolume(0.01);
   // Repeat if loss                   mart.setMaxTrades(5);
   // Reset if win                     mart.execute();
                                       // Done! Auto-manages entire sequence

 📚 WHAT YOU'LL LEARN:
   • Martingale strategy implementation (doubling system)
   • Volume progression management (0.01 → 0.02 → 0.04 → 0.08...)
   • Automatic win/loss detection and volume adjustment
   • Session-based trading with profit targets
   • Risk exponential growth mechanics

 MARTINGALE STRATEGY EXPLAINED:
   Trade 1: 0.01 lots → Loss → Total loss: -$X
   Trade 2: 0.02 lots → Loss → Total loss: -$3X
   Trade 3: 0.04 lots → Loss → Total loss: -$7X
   Trade 4: 0.08 lots → WIN  → Profit: +$8X
   Net result: +$X (recovered all losses + small profit)

 ⚠️ WARNING - CRITICAL RISKS:
   → Exponential capital requirement (doubles each loss)
   → Account can be wiped out in losing streak
   → Broker may have max volume limits
   → Margin calls likely during streak
   → ONE long losing streak = account blown
   → NOT recommended for real money trading

 WHEN TO USE MARTINGALE ORCHESTRATOR:
   ⚠️ DEMO ACCOUNTS ONLY for learning purposes
   → Understanding Martingale mechanics
   → Demonstrating exponential risk growth
   → Educational simulations
   → Academic research on betting systems
   → NEVER use with real money unless fully aware of risks

 USAGE:
   MartingaleOrchestrator orchestrator = new MartingaleOrchestrator(sugar);
   orchestrator.setSymbol("EURUSD");
   orchestrator.setBaseVolume(0.01);      // Start with 0.01 lots
   orchestrator.setMaxTrades(5);          // Max 5 trades in sequence
   orchestrator.setStopLossPoints(20.0);  // 20 points SL
   orchestrator.setTakeProfitPoints(20.0);// 20 points TP (1:1)
   orchestrator.execute();

   Or from OrchestratorDemo:
   run.bat 10 5                           # Direct launch (or .\run.bat 10 5)
   mvnd exec:java -Dexec.args="10 5"      # Via Maven
══════════════════════════════════════════════════════════════════════════════*/

package orchestrators;

import pro.mrpc.mt5.MT5Sugar;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;

public class MartingaleOrchestrator {

    private final MT5Sugar sugar;

    // Configuration
    private String symbol = "EURUSD";
    private double baseVolume = 0.01;
    private double stopLossPoints = 20.0;
    private double takeProfitPoints = 20.0;
    private int maxTrades = 5;
    private String comment = "Martingale";

    // State
    private double currentVolume;
    private int tradesExecuted = 0;
    private int consecutiveLosses = 0;
    private double totalProfit = 0;

    public MartingaleOrchestrator(MT5Sugar sugar) {
        this.sugar = sugar;
        this.currentVolume = baseVolume;
    }

    // ========================================================================
    // CONFIGURATION METHODS
    // ========================================================================

    /**
     * Set the trading symbol (e.g., "EURUSD", "GBPUSD").
     * @param symbol Symbol name
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Set the base volume to start with (e.g., 0.01 lots).
     * This volume will be doubled after each loss.
     * IMPORTANT: Start SMALL! Volume grows exponentially.
     * Example: 0.01 → 0.02 → 0.04 → 0.08 → 0.16 → 0.32 ...
     * @param volume Starting volume in lots
     */
    public void setBaseVolume(double volume) {
        this.baseVolume = volume;
        this.currentVolume = volume;
    }

    /**
     * Set stop loss distance in points from entry price.
     * Used for each individual trade in the sequence.
     * @param points Stop loss distance
     */
    public void setStopLossPoints(double points) {
        this.stopLossPoints = points;
    }

    /**
     * Set take profit distance in points from entry price.
     * Typically equals SL for Martingale (1:1 ratio).
     * @param points Take profit distance
     */
    public void setTakeProfitPoints(double points) {
        this.takeProfitPoints = points;
    }

    /**
     * Set maximum number of trades in sequence.
     * Limits how many times volume can double.
     * CRITICAL SAFETY: Lower number = less risk!
     * Example with 5 trades: 0.01 → 0.02 → 0.04 → 0.08 → 0.16 lots
     * @param max Maximum trades (recommend: 3-5 for demo, NEVER >7)
     */
    public void setMaxTrades(int max) {
        this.maxTrades = max;
    }

    /**
     * Set order comment for identification.
     * @param comment Order comment string
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    // ========================================================================
    // EXECUTION
    // ========================================================================

    /**
     * Execute the Martingale trading sequence.
     *
     * <p>STRATEGY WORKFLOW:</p>
     * <ol>
     *   <li>Start with base volume</li>
     *   <li>Execute trade with current volume</li>
     *   <li>If WIN → reset volume to base, check if session profitable</li>
     *   <li>If LOSS → double volume for next trade</li>
     *   <li>Repeat until: profit achieved, max trades reached, or volume too large</li>
     * </ol>
     *
     * <p><b>MARTINGALE MECHANICS:</b></p>
     * <ul>
     *   <li>Sequence example: 0.01 → 0.02 → 0.04 → 0.08 → 0.16 lots</li>
     *   <li>Total risk after 5 losses: 0.01+0.02+0.04+0.08+0.16 = 0.31 lots</li>
     *   <li>If 6th trade with 0.32 lots wins → recovers all + small profit</li>
     *   <li>Safety limit: stops if volume > baseVolume * 16</li>
     * </ul>
     *
     * <p><b>⚠️ WARNING:</b> This strategy can blow your account in ONE losing streak!</p>
     *
     * @throws ApiExceptionMT5 if MT5 API error occurs
     * @throws InterruptedException if thread sleep is interrupted
     */
    public void execute() throws ApiExceptionMT5, InterruptedException {
        printHeader();

        double initialBalance = sugar.getBalance();

        while (tradesExecuted < maxTrades) {
            tradesExecuted++;

            System.out.println("------------------------------------------------------------");
            System.out.println(" Trade " + tradesExecuted + "/" + maxTrades +
                             " | Volume: " + String.format("%.2f", currentVolume) + " lots");
            System.out.println("------------------------------------------------------------");

            double balanceBefore = sugar.getBalance();

            // Execute single trade
            try {
                executeSingleTrade();
            } catch (ApiExceptionMT5 e) {
                System.err.println("  X Trade failed: " + e.getMessage());
                break;
            }

            double balanceAfter = sugar.getBalance();
            double tradeProfit = balanceAfter - balanceBefore;
            totalProfit += tradeProfit;

            System.out.println();
            System.out.println("  Trade result: " + (tradeProfit >= 0 ? "+" : "") +
                             String.format("%.2f", tradeProfit));
            System.out.println("  Running total: " + (totalProfit >= 0 ? "+" : "") +
                             String.format("%.2f", totalProfit));

            // Martingale logic
            if (tradeProfit >= 0) {
                // Win - reset to base volume
                System.out.println("  >> WIN! Resetting volume to base: " +
                                 String.format("%.2f", baseVolume) + " lots");
                currentVolume = baseVolume;
                consecutiveLosses = 0;

                // Stop if we're in profit
                if (totalProfit > 0) {
                    System.out.println("  >> Total profit positive. Ending session.");
                    System.out.println();
                    break;
                }
            } else {
                // Loss - double the volume
                consecutiveLosses++;
                System.out.println("  >> LOSS! Doubling volume...");

                currentVolume = sugar.normalizeVolume(symbol, currentVolume * 2.0);

                System.out.println("  >> New volume: " + String.format("%.2f", currentVolume) + " lots");
                System.out.println("  >> Consecutive losses: " + consecutiveLosses);
            }

            System.out.println();

            // Safety check - don't continue if volume gets too large
            if (currentVolume > baseVolume * 16) {
                System.out.println("  >> Volume too large! Stopping for safety.");
                System.out.println();
                break;
            }

            // Wait before next trade
            if (tradesExecuted < maxTrades) {
                System.out.println("  Waiting 5 seconds before next trade...");
                Thread.sleep(5000);
                System.out.println();
            }
        }

        printFooter(initialBalance);
    }

    private void executeSingleTrade() throws ApiExceptionMT5, InterruptedException {
        double ask = sugar.getAsk(symbol);
        double point = sugar.getPoint(symbol);

        double stopLoss = ask - (stopLossPoints * point);
        double takeProfit = ask + (takeProfitPoints * point);

        System.out.println("  Opening BUY position...");
        System.out.println("    Volume: " + String.format("%.2f", currentVolume) + " lots");
        System.out.println("    Entry: " + formatPrice(ask));
        System.out.println("    SL: " + formatPrice(stopLoss) + " (-" + stopLossPoints + " pts)");
        System.out.println("    TP: " + formatPrice(takeProfit) + " (+" + takeProfitPoints + " pts)");

        long ticket = sugar.buyMarket(symbol, currentVolume, stopLoss, takeProfit, comment);

        System.out.println("  >> Position opened (ticket: " + ticket + ")");

        // Monitor for 3 seconds
        System.out.println("  Monitoring...");
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            double currentProfit = sugar.getProfit();
            System.out.println("    [" + (i + 1) + "/3] P/L: $" + String.format("%.2f", currentProfit));
        }

        // Close position
        System.out.println("  Closing position...");
        sugar.closePosition(ticket);
        System.out.println("  >> Position closed");
    }

    // ========================================================================
    // HELPERS
    // ========================================================================

    private String formatPrice(double price) {
        try {
            int digits = sugar.getDigits(symbol);
            return String.format("%." + digits + "f", price);
        } catch (ApiExceptionMT5 e) {
            return String.format("%.5f", price);
        }
    }

    private void printHeader() {
        System.out.println("+============================================================+");
        System.out.println("  MARTINGALE ORCHESTRATOR                                 ");
        System.out.println("+============================================================+");
        System.out.println("  Symbol: " + symbol);
        System.out.println("  Base volume: " + String.format("%.2f", baseVolume) + " lots");
        System.out.println("  SL/TP: " + stopLossPoints + "/" + takeProfitPoints + " points (1:1)");
        System.out.println("  Max trades: " + maxTrades);
        System.out.println();
        System.out.println("  WARNING: Martingale strategy carries high risk!");
        System.out.println("  Volume doubles after each loss.");
        System.out.println();
    }

    private void printFooter(double initialBalance) {
        double finalBalance = 0;
        try {
            finalBalance = sugar.getBalance();
        } catch (ApiExceptionMT5 e) {
            finalBalance = initialBalance;
        }

        System.out.println("+============================================================+");
        System.out.println("  >> MARTINGALE SESSION COMPLETED                         ");
        System.out.println("+============================================================+");
        System.out.println();
        System.out.println("  SESSION SUMMARY:");
        System.out.println("  ----------------");
        System.out.println("    Trades executed: " + tradesExecuted);
        System.out.println("    Consecutive losses: " + consecutiveLosses);
        System.out.println("    Max volume reached: " + String.format("%.2f", currentVolume) + " lots");
        System.out.println();
        System.out.println("  FINANCIAL RESULT:");
        System.out.println("  ----------------");
        System.out.println("    Starting balance: $" + String.format("%.2f", initialBalance));
        System.out.println("    Ending balance: $" + String.format("%.2f", finalBalance));
        System.out.println("    Total P/L: " + (totalProfit >= 0 ? "+" : "") +
                         String.format("%.2f", totalProfit));
        System.out.println();

        if (totalProfit > 0) {
            System.out.println("  >> Martingale recovered losses successfully!");
        } else if (consecutiveLosses >= 3) {
            System.out.println("  >> Multiple consecutive losses. Strategy risky!");
        }

        System.out.println("+============================================================+\n");
    }
}

/*══════════════════════════════════════════════════════════════════════════════

                    MARTINGALE ORCHESTRATOR: HIGH-RISK STRATEGY

══════════════════════════════════════════════════════════════════════════════

⚠️ EXTREME RISK WARNING ⚠️

This strategy is MATHEMATICALLY GUARANTEED to eventually wipe out your account!

┌─────────────────────────────────────────────────────────────────────────────┐
│ OPERATION            │ MANUAL APPROACH          │ MARTINGALE ORCHESTRATOR   │
├─────────────────────────────────────────────────────────────────────────────┤
│ Track sequence       │ // Manual tracking       │ MartingaleOrchestrator m  │
│ and double           │ volume = 0.01            │   = new MartingaleOrch(   │
│ volume               │ while (not profit) {     │       sugar);             │
│                      │   trade(volume)          │ m.setBaseVolume(0.01);    │
│                      │   if (loss) {            │ m.setMaxTrades(5);        │
│                      │     volume *= 2          │ m.execute();              │
│                      │   } else {               │ // Done! Auto-doubles!    │
│                      │     volume = 0.01        │                           │
│                      │   }                      │                           │
│                      │ }                        │                           │
└─────────────────────────────────────────────────────────────────────────────┘

KEY SUGAR API METHODS USED:
  ✓ getBalance()       - track starting/ending balance
  ✓ buyMarket()        - execute trades with current volume
  ✓ normalizeVolume()  - ensure volume meets broker requirements
  ✓ getProfit()        - monitor P/L during trade
  ✓ closePosition()    - close trades after monitoring

MARTINGALE MECHANICS EXPLAINED:

  Starting capital: $1000, Base volume: 0.01 lots, SL/TP: 20 points (1:1)
  Assume each trade risks/gains $2 (simplified for example)

  Trade 1: 0.01 lots → LOSS -$2    | Balance: $998  | Cumulative: -$2
  Trade 2: 0.02 lots → LOSS -$4    | Balance: $994  | Cumulative: -$6
  Trade 3: 0.04 lots → LOSS -$8    | Balance: $986  | Cumulative: -$14
  Trade 4: 0.08 lots → LOSS -$16   | Balance: $970  | Cumulative: -$30
  Trade 5: 0.16 lots → WIN  +$32   | Balance: $1002 | Cumulative: +$2

  Result: After 5 trades, recovered all losses + $2 profit

  BUT WHAT IF ALL 5 LOSE?
  Trade 6: 0.32 lots would be needed
  Total risk so far: $62 (6.2% of account)
  Continue losing? Account blown!

VOLUME PROGRESSION TABLE:

  Trade | Volume  | If Loss (cumulative) | Capital Required
  ------+---------+---------------------+------------------
    1   | 0.01    | -$2                 | $2
    2   | 0.02    | -$6                 | $6
    3   | 0.04    | -$14                | $14
    4   | 0.08    | -$30                | $30
    5   | 0.16    | -$62                | $62
    6   | 0.32    | -$126               | $126
    7   | 0.64    | -$254               | $254
    8   | 1.28    | -$510               | $510
    9   | 2.56    | -$1022              | $1022  ← $1000 account BLOWN!

WHY MARTINGALE FAILS:

  ✗ Exponential capital requirement
  ✗ Broker limits (max 100 lots typical)
  ✗ Margin requirements grow exponentially
  ✗ ONE losing streak destroys account
  ✗ No edge - doesn't change win probability
  ✗ Gambler's fallacy (past results don't affect future)

BEST PRACTICES (IF YOU INSIST ON USING):

  → DEMO ACCOUNT ONLY - NEVER real money
  → Set maxTrades = 3-5 maximum
  → Start with TINY base volume (0.01 lots)
  → Have 10x+ capital requirement calculated
  → Use 1:1 SL/TP ratio (equal risk/reward)
  → Track consecutive losses closely
  → Exit if volume exceeds comfort level
  → Understand this is a LOSING strategy long-term

CONFIGURATION EXAMPLE:

  MartingaleOrchestrator orchestrator = new MartingaleOrchestrator(sugar);

  // Conservative configuration (still risky!)
  orchestrator.setSymbol("EURUSD");
  orchestrator.setBaseVolume(0.01);          // START SMALL!
  orchestrator.setMaxTrades(3);              // Limit sequence length
  orchestrator.setStopLossPoints(20.0);      // 20 points SL
  orchestrator.setTakeProfitPoints(20.0);    // 20 points TP (1:1 ratio)
  orchestrator.setComment("Martingale-Demo");

  // Execute (DEMO ONLY!)
  orchestrator.execute();

MATHEMATICAL REALITY:

  Win Rate: 50% (random market, 1:1 SL/TP)
  Expected value per sequence: NEGATIVE (due to spread/commissions)

  Probability of 5 consecutive losses: (0.5)^5 = 3.125%
  Sounds rare? Trade 32 sequences → 63% chance it happens!
  When it happens → account blown

  Martingale DOES NOT change market probabilities.
  It only changes BET SIZING in a way that guarantees eventual ruin.

ALTERNATIVES TO MARTINGALE:

  ✓ Fixed % risk per trade (e.g., 1-2% of capital)
  ✓ Kelly Criterion for optimal position sizing
  ✓ Anti-Martingale (increase size on wins, not losses)
  ✓ Flat betting (same size always)
  ✓ ANY strategy that doesn't double after loss!

══════════════════════════════════════════════════════════════════════════════

                              NEXT STEPS

══════════════════════════════════════════════════════════════════════════════

1. Learn SAFE Risk Management
   → RiskManagementScenario - fixed % risk per trade
   → Fixed position sizing based on account equity
   → run.bat 8

2. Study Other Orchestrators
   → BreakoutOrchestrator - breakout trading
   → HedgingOrchestrator - defensive strategies
   → run.bat 10 (select other options)

3. Understand Why Martingale Fails
   → Research "Gambler's Ruin" theorem
   → Study Kelly Criterion for proper sizing
   → Read "Fooled by Randomness" by Nassim Taleb

═══════════════════════════════════════════════════════════════════════════════

                         FINAL WARNING

This code is provided for EDUCATIONAL PURPOSES ONLY.
Martingale is a mathematically flawed strategy.
Do NOT use with real money.
You WILL lose your account eventually.
Consider yourself warned.

══════════════════════════════════════════════════════════════════════════════*/
