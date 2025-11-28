/*══════════════════════════════════════════════════════════════════════════════
 FILE: BreakoutOrchestrator.java — BREAKOUT TRADING ORCHESTRATOR

 LEVEL: 4 (Orchestrator)
 DIFFICULTY: Intermediate

 PURPOSE:
   Professional breakout trading strategy using pending orders!
   This orchestrator implements automated price breakout detection:
   • Places BUY STOP above current price
   • Places SELL STOP below current price
   • Waits for breakout in either direction
   • Cancels opposite order when one triggers
   • Monitors position with automatic cleanup

 🎯 WHY USE BREAKOUT ORCHESTRATOR?

   MANUAL TRADING:                     BREAKOUT ORCHESTRATOR:
   ────────────────────────────────────────────────────────────────────────
   // Watch chart manually            BreakoutOrchestrator orchestrator =
   // Place pending orders manually      new BreakoutOrchestrator(sugar);
   // Monitor for breakout             orchestrator.setSymbol("EURUSD");
   // Cancel opposite manually         orchestrator.setBreakoutDistance(30);
   // Close positions manually         orchestrator.execute();
                                       // Done! Auto-manages entire strategy

 📚 WHAT YOU'LL LEARN:
   • Breakout strategy implementation with pending orders
   • BUY STOP/SELL STOP order placement (both directions)
   • Automatic order cancellation after breakout
   • Position monitoring and cleanup
   • Risk-based volume calculation

 BREAKOUT STRATEGY EXPLAINED:
   1. Identify current price level
   2. Place BUY STOP above (triggered on upward breakout)
   3. Place SELL STOP below (triggered on downward breakout)
   4. Wait for price to break one level
   5. Cancel remaining pending order
   6. Monitor triggered position

 WHEN TO USE BREAKOUT ORCHESTRATOR:
   → Range-bound markets ready to break out
   → Support/resistance level trading
   → News event trading (volatility expected)
   → When direction unclear but movement expected
   → Consolidation/compression patterns

 USAGE:
   BreakoutOrchestrator orchestrator = new BreakoutOrchestrator(sugar);
   orchestrator.setSymbol("EURUSD");
   orchestrator.setBreakoutDistance(30);  // 30 points from current price
   orchestrator.setRiskAmount(40.0);      // Risk $40 per trade
   orchestrator.execute();

   Or from OrchestratorDemo:
   run.bat 10 4                           # Direct launch (or .\run.bat 10 4)
   mvnd exec:java -Dexec.args="10 4"      # Via Maven
══════════════════════════════════════════════════════════════════════════════*/

package orchestrators;

import io.metarpc.mt5.MT5Sugar;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;

public class BreakoutOrchestrator {

    private final MT5Sugar sugar;

    // Configuration
    private String symbol = "EURUSD";
    private double riskAmount = 40.0;
    private double breakoutDistance = 30.0;  // Points from current price
    private double stopLossPoints = 50.0;
    private double takeProfitPoints = 100.0;

    // State
    private long buyStopTicket = 0;
    private long sellStopTicket = 0;

    public BreakoutOrchestrator(MT5Sugar sugar) {
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
     * Set the risk amount in account currency (e.g., $40 means max loss = $40).
     * Volume is auto-calculated to risk this exact amount if SL hits.
     * @param amount Risk amount per trade
     */
    public void setRiskAmount(double amount) {
        this.riskAmount = amount;
    }

    /**
     * Set breakout distance in points from current price.
     * BUY STOP will be placed this many points above Ask.
     * SELL STOP will be placed this many points below Bid.
     * @param points Distance in points (e.g., 30 points)
     */
    public void setBreakoutDistance(double points) {
        this.breakoutDistance = points;
    }

    /**
     * Set stop loss distance in points from entry price.
     * @param points Stop loss distance
     */
    public void setStopLossPoints(double points) {
        this.stopLossPoints = points;
    }

    /**
     * Set take profit distance in points from entry price.
     * @param points Take profit distance
     */
    public void setTakeProfitPoints(double points) {
        this.takeProfitPoints = points;
    }


    // ========================================================================
    // EXECUTION
    // ========================================================================

    /**
     * Execute the breakout trading strategy.
     *
     * <p>STRATEGY WORKFLOW:</p>
     * <ol>
     *   <li>Get current market price (Bid/Ask)</li>
     *   <li>Place BUY STOP above current price</li>
     *   <li>Place SELL STOP below current price</li>
     *   <li>Wait for price to break one level (max 20 seconds)</li>
     *   <li>Cancel remaining pending order when breakout occurs</li>
     *   <li>Monitor triggered position briefly</li>
     *   <li>Close all positions (cleanup)</li>
     * </ol>
     *
     * @throws ApiExceptionMT5 if MT5 API error occurs
     * @throws InterruptedException if thread sleep is interrupted
     */
    public void execute() throws ApiExceptionMT5, InterruptedException {
        printHeader();

        // ══════════════════════════════════════════════════════════════
        // STEP 1: GET CURRENT PRICE
        //
        // SUGAR BENEFIT: Direct getters - getBid(), getAsk(), getPoint()
        //                No need for multiple API calls
        // ══════════════════════════════════════════════════════════════
        double bid = sugar.getBid(symbol);
        double ask = sugar.getAsk(symbol);
        double point = sugar.getPoint(symbol);

        System.out.println("  Current Price:");
        System.out.println("    Bid/Ask: " + formatPrice(bid) + " / " + formatPrice(ask));
        System.out.println("    Breakout distance: " + breakoutDistance + " points");
        System.out.println();

        // ══════════════════════════════════════════════════════════════
        // STEP 2: PLACE PENDING ORDERS
        //
        // SUGAR BENEFIT: buyStopPoints/sellStopPoints - place pending
        //                orders with points offset from current price
        //                Volume auto-calculated using calculateVolume()
        // ══════════════════════════════════════════════════════════════
        System.out.println("  Setting up breakout trap...");

        double volume = sugar.calculateVolume(symbol, stopLossPoints, riskAmount);

        // BUY STOP above current price
        double buyStopPrice = ask + (breakoutDistance * point);

        buyStopTicket = sugar.buyStopPoints(symbol, volume, breakoutDistance,
                                           stopLossPoints, takeProfitPoints);

        System.out.println("    BUY STOP placed at: " + formatPrice(buyStopPrice));
        System.out.println("      Ticket: " + buyStopTicket);

        // SELL STOP below current price
        double sellStopPrice = bid - (breakoutDistance * point);

        sellStopTicket = sugar.sellStopPoints(symbol, volume, -breakoutDistance,
                                             stopLossPoints, takeProfitPoints);

        System.out.println("    SELL STOP placed at: " + formatPrice(sellStopPrice));
        System.out.println("      Ticket: " + sellStopTicket);
        System.out.println();

        // ══════════════════════════════════════════════════════════════
        // STEP 3: WAIT FOR BREAKOUT
        //
        // SUGAR BENEFIT: getPositionCount() - quickly check if any
        //                pending order was triggered (position opened)
        // ══════════════════════════════════════════════════════════════
        System.out.println("  Waiting for breakout (checking every 2 seconds)...");
        System.out.println();

        boolean breakoutDetected = false;
        int checks = 0;
        int maxChecks = 10;  // 20 seconds total

        while (!breakoutDetected && checks < maxChecks) {
            Thread.sleep(2000);
            checks++;

            double currentPrice = sugar.getBid(symbol);
            int positionCount = sugar.getPositionCount();

            System.out.println("    [" + checks + "/" + maxChecks + "] Price: " + formatPrice(currentPrice) +
                             " | Positions: " + positionCount);

            // Check if any order was triggered (position opened)
            if (positionCount > 0) {
                breakoutDetected = true;
                System.out.println();
                System.out.println("  >> BREAKOUT DETECTED! Position opened");
                System.out.println();
                break;
            }
        }

        // ══════════════════════════════════════════════════════════════
        // STEP 4: CANCEL OPPOSITE PENDING ORDER
        //
        // SUGAR BENEFIT: cancelAll(symbol, null) - cancel all pending
        //                orders for symbol in one call
        // ══════════════════════════════════════════════════════════════
        if (breakoutDetected) {
            System.out.println("  Canceling remaining pending orders...");

            int canceledCount = sugar.cancelAll(symbol, null);

            System.out.println("  >> Canceled pending orders: " + canceledCount);
            System.out.println();

            // Monitor position briefly
            System.out.println("  Monitoring position for 6 seconds...");

            for (int i = 1; i <= 3; i++) {
                Thread.sleep(2000);
                double currentProfit = sugar.getProfit();
                System.out.println("    [" + i + "/3] P/L: $" + String.format("%.2f", currentProfit));
            }
            System.out.println();

        } else {
            System.out.println();
            System.out.println("  >> No breakout detected within time limit");
            System.out.println("     Canceling all pending orders...");

            int canceledCount = sugar.cancelAll(symbol, null);

            System.out.println("  >> Canceled orders: " + canceledCount);
            System.out.println();
        }

        // ══════════════════════════════════════════════════════════════
        // STEP 5: CLOSE ALL POSITIONS
        //
        // SUGAR BENEFIT: closeAll(symbol) - close all positions for
        //                symbol in one call (cleanup)
        // ══════════════════════════════════════════════════════════════
        System.out.println("  Closing all positions...");
        int closedCount = sugar.closeAll(symbol);
        System.out.println("  >> Closed positions: " + closedCount);
        System.out.println();

        printFooter(breakoutDetected);
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
        System.out.println("  BREAKOUT ORCHESTRATOR                                   ");
        System.out.println("+============================================================+");
        System.out.println("  Symbol: " + symbol);
        System.out.println("  Risk: $" + String.format("%.2f", riskAmount));
        System.out.println("  Breakout distance: " + breakoutDistance + " points");
        System.out.println("  SL/TP: " + stopLossPoints + "/" + takeProfitPoints + " points");
        System.out.println();
    }

    private void printFooter(boolean breakoutDetected) {
        System.out.println("+============================================================+");
        if (breakoutDetected) {
            System.out.println("  >> BREAKOUT TRADE COMPLETED                             ");
        } else {
            System.out.println("  >> BREAKOUT SETUP EXPIRED (NO TRIGGER)                  ");
        }
        System.out.println("+============================================================+\n");
    }
}

/*══════════════════════════════════════════════════════════════════════════════

                    BREAKOUT ORCHESTRATOR: PROFESSIONAL STRATEGY

══════════════════════════════════════════════════════════════════════════════

Complete breakout trading with automated pending order management!

┌─────────────────────────────────────────────────────────────────────────────┐
│ OPERATION            │ MANUAL APPROACH          │ BREAKOUT ORCHESTRATOR     │
├─────────────────────────────────────────────────────────────────────────────┤
│ Place pending        │ // Calculate prices      │ BreakoutOrchestrator orch │
│ orders for           │ buyStopPrice = ask + 30p │   = new BreakoutOrch(sugar│
│ breakout             │ sellStopPrice = bid - 30p│ orch.setSymbol("EURUSD"); │
│                      │ // Place BUY STOP        │ orch.setBreakoutDistance( │
│                      │ sugar.buyStop(...)       │   30);                    │
│                      │ // Place SELL STOP       │ orch.execute();           │
│                      │ sugar.sellStop(...)      │ // Done! Auto-manages all!│
│                      │ // Monitor manually      │                           │
│                      │ // Cancel opposite       │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Monitor breakout     │ while (true) {           │ // Built into execute()   │
│                      │   check positions        │ // Auto-detects breakout  │
│                      │   if breakout:           │ // Cancels opposite order │
│                      │     cancel opposite      │ // Monitors position      │
│                      │     break                │                           │
│                      │ }                        │                           │
├─────────────────────────────────────────────────────────────────────────────┤
│ Risk management      │ // Manual calculation    │ orch.setRiskAmount(40.0); │
│                      │ volume = calculateVolume │ // Auto-calculates volume │
│                      │ // Use in order          │ // for fixed $40 risk     │
└─────────────────────────────────────────────────────────────────────────────┘

KEY SUGAR API METHODS USED:
  ✓ calculateVolume() - auto volume calculation for risk
  ✓ buyStopPoints()   - place BUY STOP with points offset
  ✓ sellStopPoints()  - place SELL STOP with points offset
  ✓ getPositionCount() - check if breakout triggered
  ✓ cancelAll()       - cancel remaining pending orders
  ✓ closeAll()        - close all positions (cleanup)
  ✓ getProfit()       - monitor position P/L

BREAKOUT STRATEGY BENEFITS:
  ✓ Catch strong directional moves in either direction
  ✓ No need to predict market direction
  ✓ Automatic order cancellation after breakout
  ✓ Works great with volatility events (news, sessions)
  ✓ Professional risk management built-in

BEST PRACTICES:
  → Use during high volatility periods (news events, session opens)
  → Set breakoutDistance based on ATR or recent range
  → Ensure SL/TP ratio is favorable (e.g., 1:2)
  → Monitor for false breakouts in ranging markets
  → Consider time limits to avoid leaving orders indefinitely

TYPICAL USE CASES:
  1. News trading - place before high-impact news release
  2. Range breakout - when price consolidates in tight range
  3. Support/resistance - pending orders at key levels
  4. Session breakout - Asian/London/NY session opens
  5. Pattern breakout - triangles, flags, consolidations

CONFIGURATION EXAMPLE:
  BreakoutOrchestrator orchestrator = new BreakoutOrchestrator(sugar);

  // Basic configuration
  orchestrator.setSymbol("EURUSD");
  orchestrator.setBreakoutDistance(30);    // 30 points from current price
  orchestrator.setRiskAmount(40.0);        // Risk $40 per trade
  orchestrator.setStopLossPoints(50.0);    // 50 points SL
  orchestrator.setTakeProfitPoints(100.0); // 100 points TP (1:2 ratio)
  orchestrator.setComment("Breakout");

  // Execute strategy
  orchestrator.execute();

STRATEGY WORKFLOW EXPLAINED:
  1. Get current Bid/Ask prices
  2. Place BUY STOP = Ask + breakoutDistance
     → Triggered when price moves UP through this level
  3. Place SELL STOP = Bid - breakoutDistance
     → Triggered when price moves DOWN through this level
  4. Monitor position count every 2 seconds (max 20 seconds)
     → If position opens = breakout detected!
  5. Cancel opposite pending order immediately
     → If BUY triggered, cancel SELL STOP (and vice versa)
  6. Monitor triggered position briefly (6 seconds)
  7. Close all positions and complete strategy

ADVANTAGES OVER MANUAL TRADING:
  ✓ No need to watch charts constantly
  ✓ Instant reaction to breakout
  ✓ Automatic cleanup (cancel/close)
  ✓ Consistent risk management
  ✓ No emotional decisions
  ✓ Repeatable strategy

══════════════════════════════════════════════════════════════════════════════

                              NEXT STEPS

══════════════════════════════════════════════════════════════════════════════

1. Other Orchestrators (Level 4)
   → TrendFollowingOrchestrator - follow market trends
   → ScalpingOrchestrator - quick in/out trades
   → HedgingOrchestrator - risk mitigation strategies
   → run.bat 10 (select different options)

2. Presets (Level 5)
   → Pre-configured strategies ready to use
   → AggressiveGrowthPreset, DefensivePreset, etc.
   → run.bat 11

3. Build Your Own Orchestrator
   → Use this file as template
   → Customize strategy logic
   → Integrate with your trading system

══════════════════════════════════════════════════════════════════════════════*/
