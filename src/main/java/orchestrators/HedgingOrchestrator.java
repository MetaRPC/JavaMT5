/*══════════════════════════════════════════════════════════════════════════════
 FILE: HedgingOrchestrator.java — HEDGING TRADING ORCHESTRATOR

 LEVEL: 4 (Orchestrator)
 DIFFICULTY: Intermediate to Advanced

 PURPOSE:
   Professional hedging strategy for risk protection!
   This orchestrator implements defensive position hedging:
   • Opens primary position (BUY or SELL)
   • Monitors for adverse price movement
   • Opens opposite hedge position if trigger hit
   • Locks losses at hedge level
   • Manages both positions together

 🎯 WHY USE HEDGING ORCHESTRATOR?

   MANUAL TRADING:                     HEDGING ORCHESTRATOR:
   ────────────────────────────────────────────────────────────────────────
   // Open primary position            HedgingOrchestrator hedge =
   // Watch for adverse movement         new HedgingOrchestrator(sugar);
   // Calculate loss in points         hedge.setSymbol("EURUSD");
   // Manually open opposite           hedge.setHedgeTriggerPoints(50);
   // Monitor both positions           hedge.execute(true);
   // Close both manually              // Done! Auto-hedges if needed

 📚 WHAT YOU'LL LEARN:
   • Hedging strategy implementation (lock losses)
   • Monitoring price movement against position
   • Automatic opposite position opening
   • Managing multiple related positions
   • Defensive risk management techniques

 HEDGING STRATEGY EXPLAINED:
   1. Open primary position (BUY or SELL)
   2. Monitor price movement against entry
   3. If loss reaches trigger (e.g., -50 points), open hedge
   4. Hedge = opposite position with same volume
   5. Net position = 0 (locked), losses stop growing
   6. Close both positions when ready

 WHEN TO USE HEDGING ORCHESTRATOR:
   → Uncertain market conditions (high volatility)
   → Protect position during news events
   → Lock losses instead of stop loss
   → When you want to keep position open but limit risk
   → Trading regulations allow hedging (check your broker)

 USAGE:
   HedgingOrchestrator orchestrator = new HedgingOrchestrator(sugar);
   orchestrator.setSymbol("EURUSD");
   orchestrator.setRiskAmount(30.0);           // Risk $30 per trade
   orchestrator.setHedgeTriggerPoints(50.0);   // Hedge after 50 points loss
   orchestrator.setStopLossPoints(100.0);      // 100 points SL on primary
   orchestrator.execute(true);                 // true = BUY first

   Or from OrchestratorDemo:
   run.bat 10 3                           # Direct launch (or .\run.bat 10 3)
   mvnd exec:java -Dexec.args="10 3"      # Via Maven
══════════════════════════════════════════════════════════════════════════════*/

package orchestrators;

import pro.mrpc.mt5.MT5Sugar;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;

/**
 * Hedging Orchestrator - Opens opposite positions for risk management
 */
public class HedgingOrchestrator {

    private final MT5Sugar sugar;

    // Configuration
    private String symbol = "EURUSD";
    private double riskAmount = 30.0;
    private double stopLossPoints = 100.0;
    private double takeProfitPoints = 150.0;
    private double hedgeTriggerPoints = 50.0;  // Open hedge after this many points loss
    private String comment = "Hedge";

    // State
    private long primaryTicket = 0;
    private long hedgeTicket = 0;
    private double primaryEntry = 0;
    private boolean hedgeOpened = false;

    public HedgingOrchestrator(MT5Sugar sugar) {
        this.sugar = sugar;
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
     * Set the risk amount in account currency (e.g., $30 means max loss = $30).
     * Volume is auto-calculated to risk this exact amount if primary SL hits.
     * @param amount Risk amount per trade
     */
    public void setRiskAmount(double amount) {
        this.riskAmount = amount;
    }

    /**
     * Set stop loss distance in points from entry price (for primary position).
     * Used for volume calculation and risk management.
     * @param points Stop loss distance
     */
    public void setStopLossPoints(double points) {
        this.stopLossPoints = points;
    }

    /**
     * Set take profit distance in points from entry price (for primary position).
     * @param points Take profit distance
     */
    public void setTakeProfitPoints(double points) {
        this.takeProfitPoints = points;
    }

    /**
     * Set hedge trigger distance in points (loss threshold).
     * If primary position loses this many points, hedge position opens.
     * Example: 50 points = open hedge after -50 points movement.
     * @param points Loss trigger distance for hedge activation
     */
    public void setHedgeTriggerPoints(double points) {
        this.hedgeTriggerPoints = points;
    }

    /**
     * Set order comment for identification.
     * Will be suffixed with "-Primary" and "-Hedge".
     * @param comment Order comment string
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    // ========================================================================
    // EXECUTION
    // ========================================================================

    /**
     * Execute the hedging trading strategy.
     *
     * <p>STRATEGY WORKFLOW:</p>
     * <ol>
     *   <li>Get current market price (Bid/Ask)</li>
     *   <li>Open primary position (BUY or SELL based on direction)</li>
     *   <li>Monitor price movement against entry price</li>
     *   <li>If loss reaches hedge trigger → open opposite position</li>
     *   <li>Positions now locked (net = 0), losses stop growing</li>
     *   <li>Close both positions (primary + hedge if opened)</li>
     * </ol>
     *
     * <p><b>HEDGING MECHANICS:</b></p>
     * <ul>
     *   <li>Primary position: full SL/TP, subject to risk</li>
     *   <li>Hedge position: same volume, opposite direction, no SL/TP</li>
     *   <li>Net effect: losses locked at hedge trigger level</li>
     *   <li>Example: BUY at 1.1000, hedge trigger = 50 pts</li>
     *   <li>If price drops to 1.0950 → SELL opens → losses locked</li>
     * </ul>
     *
     * @param buyFirst true = open BUY as primary, false = open SELL as primary
     * @throws ApiExceptionMT5 if MT5 API error occurs
     * @throws InterruptedException if thread sleep is interrupted
     */
    public void execute(boolean buyFirst) throws ApiExceptionMT5, InterruptedException {
        printHeader(buyFirst);

        // ══════════════════════════════════════════════════════════════
        // STEP 1: GET MARKET INFO
        //
        // SUGAR BENEFIT: Direct getters - getBid(), getAsk(), getPoint()
        //                No need for multiple API calls
        // ══════════════════════════════════════════════════════════════
        double bid = sugar.getBid(symbol);
        double ask = sugar.getAsk(symbol);
        double point = sugar.getPoint(symbol);

        System.out.println("  Market Info:");
        System.out.println("    Bid/Ask: " + formatPrice(bid) + " / " + formatPrice(ask));
        System.out.println("    Spread: " + sugar.getSpread(symbol) + " points");
        System.out.println();

        // ══════════════════════════════════════════════════════════════
        // STEP 2: OPEN PRIMARY POSITION
        //
        // SUGAR BENEFIT: buyMarket/sellMarket - open position in 1 line
        //                calculateVolume() - auto volume for risk
        // ══════════════════════════════════════════════════════════════
        System.out.println("  Opening primary position...");
        System.out.println("    Direction: " + (buyFirst ? "BUY" : "SELL"));
        System.out.println("    Risk: $" + String.format("%.2f", riskAmount));
        System.out.println("    SL: " + stopLossPoints + " points");
        System.out.println("    TP: " + takeProfitPoints + " points");
        System.out.println("    Hedge trigger: " + hedgeTriggerPoints + " points loss");
        System.out.println();

        // Calculate volume
        double volume = sugar.calculateVolume(symbol, stopLossPoints, riskAmount);

        if (buyFirst) {
            double stopLoss = ask - (stopLossPoints * point);
            double takeProfit = ask + (takeProfitPoints * point);
            primaryTicket = sugar.buyMarket(symbol, volume, stopLoss, takeProfit, comment + "-Primary");
            primaryEntry = ask;
        } else {
            double stopLoss = bid + (stopLossPoints * point);
            double takeProfit = bid - (takeProfitPoints * point);
            primaryTicket = sugar.sellMarket(symbol, volume, stopLoss, takeProfit, comment + "-Primary");
            primaryEntry = bid;
        }

        System.out.println("  >> Primary position opened!");
        System.out.println("    Ticket: " + primaryTicket);
        System.out.println("    Entry: " + formatPrice(primaryEntry));
        System.out.println("    Volume: " + String.format("%.2f", volume) + " lots");
        System.out.println();

        // ══════════════════════════════════════════════════════════════
        // STEP 3: MONITOR FOR HEDGE TRIGGER
        //
        // SUGAR BENEFIT: getProfit() - real-time P/L monitoring
        //                getBid/getAsk() - current prices for calculation
        // ══════════════════════════════════════════════════════════════
        System.out.println("  Monitoring for hedge trigger...");

        for (int i = 0; i < 4; i++) {
            Thread.sleep(1500);

            double currentPrice = buyFirst ? sugar.getBid(symbol) : sugar.getAsk(symbol);
            double movement = buyFirst ?
                (currentPrice - primaryEntry) / point :
                (primaryEntry - currentPrice) / point;

            double currentProfit = sugar.getProfit();

            System.out.println("    [" + (i + 1) + "] Price: " + formatPrice(currentPrice) +
                             " | Movement: " + String.format("%.0f", movement) + " pts" +
                             " | P/L: $" + String.format("%.2f", currentProfit));

            // Check if we should open hedge
            if (!hedgeOpened && movement < -hedgeTriggerPoints) {
                System.out.println();
                System.out.println("  >> Hedge trigger activated!");
                System.out.println("    Opening opposite position to lock losses...");

                // Open opposite position with same volume
                if (buyFirst) {
                    // Primary is BUY, hedge with SELL
                    double sellPrice = sugar.getBid(symbol);
                    hedgeTicket = sugar.sellMarket(symbol, volume, null, null, comment + "-Hedge");
                    System.out.println("    >> SELL hedge opened at: " + formatPrice(sellPrice));
                } else {
                    // Primary is SELL, hedge with BUY
                    double buyPrice = sugar.getAsk(symbol);
                    hedgeTicket = sugar.buyMarket(symbol, volume, null, null, comment + "-Hedge");
                    System.out.println("    >> BUY hedge opened at: " + formatPrice(buyPrice));
                }

                hedgeOpened = true;
                System.out.println("    Hedge ticket: " + hedgeTicket);
                System.out.println("    >> Positions now hedged (locked)");
                System.out.println();
            }
        }
        System.out.println();

        // ══════════════════════════════════════════════════════════════
        // STEP 4: CLOSE BOTH POSITIONS
        //
        // SUGAR BENEFIT: closePosition(ticket) - close by ticket in 1 line
        // ══════════════════════════════════════════════════════════════
        System.out.println("  Closing positions...");

        sugar.closePosition(primaryTicket);
        System.out.println("  >> Primary position closed");

        if (hedgeOpened) {
            sugar.closePosition(hedgeTicket);
            System.out.println("  >> Hedge position closed");
        }

        System.out.println();

        // Final stats
        int remainingPositions = sugar.getPositionCount();
        double finalBalance = sugar.getBalance();

        System.out.println("  Final status:");
        System.out.println("    Remaining positions: " + remainingPositions);
        System.out.println("    Final balance: $" + String.format("%.2f", finalBalance));
        System.out.println("    Hedge was " + (hedgeOpened ? "ACTIVATED" : "NOT NEEDED"));
        System.out.println();

        printFooter();
    }

    // ========================================================================
    // HELPERS
    // ========================================================================

    private String formatPrice(double price) {
        try {
            int digits = sugar.getDigits(symbol);
            return String.format("%." + digits + "f", price);
        } catch (ApiExceptionMT5 e) {
            return String.format("%.5f", price); // Fallback
        }
    }

    private void printHeader(boolean buyFirst) {
        System.out.println("+============================================================+");
        System.out.println("  HEDGING ORCHESTRATOR                                    ");
        System.out.println("+============================================================+");
        System.out.println("  Symbol: " + symbol);
        System.out.println("  Primary direction: " + (buyFirst ? "BUY" : "SELL"));
        System.out.println("  Risk: $" + String.format("%.2f", riskAmount));
        System.out.println("  Hedge trigger: " + hedgeTriggerPoints + " points loss");
        System.out.println();
    }

    private void printFooter() {
        System.out.println("+============================================================+");
        System.out.println("  >> HEDGING STRATEGY COMPLETED                           ");
        System.out.println("+============================================================+\n");
    }
}

/*════════════════════════════════════════════════════════════════════════════

                    HEDGING ORCHESTRATOR: DEFENSIVE STRATEGY

══════════════════════════════════════════════════════════════════════════════

Complete hedging implementation with automatic risk locking!

┌─────────────────────────────────────────────────────────────────────────────┐
│ OPERATION            │ MANUAL APPROACH          │ HEDGING ORCHESTRATOR      │
├─────────────────────────────────────────────────────────────────────────────┤
│ Open primary         │ // Open BUY position     │ HedgingOrchestrator hedge │
│ and monitor          │ ticket = sugar.buyMarket │   = new HedgingOrch(sugar)│
│ for losses           │ // Monitor loss manually │ hedge.setSymbol("EURUSD");│
│                      │ while (true) {           │ hedge.setHedgeTrigger(50);│
│                      │   check P/L              │ hedge.execute(true);      │
│                      │   if loss > trigger:     │ // Done! Auto-hedges!     │
│                      │     open opposite        │                           │
│                      │ }                        │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Calculate loss       │ currentPrice = getBid()  │ // Built into execute()   │
│ in points            │ movement = (entry - curr)│ // Automatic monitoring   │
│                      │   / point                │ // Opens hedge when needed│
│                      │ if movement < -50:       │                           │
│                      │   openHedge()            │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Open hedge           │ // Manual opposite order │ // Automatic!             │
│ position             │ hedgeTicket = sugar      │ // Same volume            │
│                      │   .sellMarket(symbol,    │ // Opposite direction     │
│                      │   sameVolume, null, null)│ // No SL/TP (lock only)   │
└─────────────────────────────────────────────────────────────────────────────┘

KEY SUGAR API METHODS USED:
  ✓ calculateVolume()  - auto volume calculation for risk
  ✓ buyMarket()        - open BUY position with SL/TP
  ✓ sellMarket()       - open SELL position with SL/TP
  ✓ getBid/getAsk()    - current prices for movement calculation
  ✓ getProfit()        - real-time P/L monitoring
  ✓ closePosition()    - close positions by ticket

HEDGING STRATEGY BENEFITS:
  ✓ Lock losses instead of exiting position
  ✓ Keep position open during uncertain conditions
  ✓ Automatic hedge activation at trigger level
  ✓ Net position = 0 after hedge (fully protected)
  ✓ Can unwind later when market clarifies

BEST PRACTICES:
  → Use during high volatility or uncertain events
  → Set hedge trigger below SL level
  → Understand broker hedging rules (some brokers net positions)
  → Hedge is NOT free - costs spread on opposite position
  → Use when you expect market to reverse (not trend)
  → Close both positions when analysis complete

TYPICAL USE CASES:
  1. News trading - hedge during high-impact news release
  2. Weekend gap protection - hedge before market close Friday
  3. Uncertain direction - keep position but limit risk
  4. Avoid stop out - prefer locking to forced exit
  5. Scale out strategy - close primary, keep hedge as new trade

HEDGING MECHANICS EXPLAINED:

  Example: BUY 0.10 lots EURUSD at 1.1000
  Hedge trigger: 50 points loss

  Scenario 1: Price drops to 1.0950 (-50 points)
  → Hedge activates: SELL 0.10 lots at 1.0950
  → Net position: 0.10 BUY + 0.10 SELL = 0 (locked)
  → Loss locked at: -50 points = -$50 (depending on lot size)
  → Further price movement does NOT affect P/L

  Scenario 2: Price moves to profit (never reaches -50 points)
  → Hedge never activates
  → Position continues normally with SL/TP
  → Strategy behaves like normal trade

CONFIGURATION EXAMPLE:
  HedgingOrchestrator orchestrator = new HedgingOrchestrator(sugar);

  // Basic configuration
  orchestrator.setSymbol("EURUSD");
  orchestrator.setRiskAmount(30.0);           // Risk $30 per trade
  orchestrator.setStopLossPoints(100.0);      // 100 points SL on primary
  orchestrator.setTakeProfitPoints(150.0);    // 150 points TP on primary
  orchestrator.setHedgeTriggerPoints(50.0);   // Hedge after 50 points loss
  orchestrator.setComment("Hedge");

  // Execute strategy (BUY first)
  orchestrator.execute(true);   // true = BUY, false = SELL

ADVANTAGES OVER STOP LOSS:
  ✓ Position stays open (can unwind later)
  ✓ No slippage on exit (hedge locks price)
  ✓ Flexible - can close hedge only, keep primary
  ✓ Useful when expecting reversal
  ✓ Avoid forced exit during volatility

DISADVANTAGES (BE AWARE):
  ⚠ Costs additional spread to open hedge
  ⚠ Two positions = 2x margin requirement
  ⚠ Some brokers net positions (hedge may not work)
  ⚠ Locked losses don't recover unless you unwind
  ⚠ More complex to manage than simple SL

══════════════════════════════════════════════════════════════════════════════

                              NEXT STEPS

══════════════════════════════════════════════════════════════════════════════

1. Other Orchestrators (Level 4)
   → BreakoutOrchestrator - capture breakouts in either direction
   → TrendFollowingOrchestrator - follow market trends
   → ScalpingOrchestrator - quick in/out trades
   → run.bat 10 (select different options)

2. Presets (Level 5)
   → Pre-configured strategies ready to use
   → AggressiveGrowthPreset, DefensivePreset, etc.
   → run.bat 11

3. Build Your Own Orchestrator
   → Use this file as template
   → Customize hedging logic (dynamic triggers, partial hedge)
   → Integrate with your risk management system

══════════════════════════════════════════════════════════════════════════════*/
