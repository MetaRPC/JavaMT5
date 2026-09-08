/*══════════════════════════════════════════════════════════════════════════════
 FILE: TrendFollowingOrchestrator.java — TREND FOLLOWING ORCHESTRATOR

 LEVEL: 4 (Orchestrator)
 DIFFICULTY: Intermediate

 PURPOSE:
   Professional trend following strategy with trailing stops!
   • Opens position in trend direction (BUY for uptrend)
   • Uses wider SL/TP for trend trades (80/160 points default)
   • Monitors position and activates trailing stop
   • Moves SL to breakeven when profit threshold reached
   • Risk-based volume calculation
   • Ideal for capturing trending markets

 📊 MANUAL TREND TRADING            ⚡ TREND FOLLOWING ORCHESTRATOR
 ├─ Watch chart for trend           ├─ Auto entry in trend direction
 ├─ Place order with wide SL/TP     ├─ Wider SL/TP pre-configured
 ├─ Monitor position constantly     ├─ Auto trailing stop activation
 ├─ Manually trail stop loss        ├─ Move SL to breakeven automatically
 ├─ Close manually                  ├─ Risk-based position sizing
 └─ Track P/L manually              └─ Complete automation with reporting

 📚 WHAT YOU'LL LEARN:
   → Trend following strategy implementation
   → Trailing stop loss mechanics
   → Risk-based volume calculation for wider SL
   → Position monitoring and modification
   → Breakeven stop loss management
   → Wider SL/TP ratios for trend trades (1:2)

 TREND FOLLOWING STRATEGY EXPLAINED:

   1. Market Analysis:
      • Get current Bid/Ask prices
      • Assume uptrend (simplified - normally use MA/indicators)
      • Verify free margin availability

   2. Position Entry:
      • Calculate volume based on risk amount and wider SL
      • Open BUY position with 80-point SL, 160-point TP (1:2 ratio)
      • Typical risk: $50 per trade

   3. Trailing Stop Monitoring:
      • Monitor position every 1.5 seconds
      • Check profit in points vs trailing threshold (40 points default)
      • If profit >= 40 points → activate trailing stop

   4. Breakeven Protection:
      • Move SL to entry price + 10 points (breakeven + buffer)
      • Lock in small profit, eliminate risk
      • Let position run to TP with protected capital

   5. Position Exit:
      • Manual close after monitoring (in demo)
      • In real trading: SL/TP handle exit automatically

 WHEN TO USE TREND FOLLOWING ORCHESTRATOR:
   ✓ Strong trending markets (uptrend/downtrend)
   ✓ Higher timeframes (H1, H4, D1)
   ✓ Low volatility instruments (major pairs during sessions)
   ✓ When you want to capture larger moves
   ✓ For swing trading strategies
   ✗ Ranging/choppy markets (use Breakout instead)
   ✗ High volatility periods (SL too wide = high risk)
   ✗ Scalping timeframes (use ScalpingOrchestrator)

 USAGE:
   // Initialize with MT5Sugar instance
   TrendFollowingOrchestrator trend = new TrendFollowingOrchestrator(sugar);

   // Configure strategy
   trend.setSymbol("EURUSD");              // Trending instrument
   trend.setRiskAmount(50.0);              // $50 risk per trade
   trend.setStopLossPoints(80.0);          // 80 points SL (wider for trends)
   trend.setTakeProfitPoints(160.0);       // 160 points TP (1:2 R/R)
   trend.setTrailingStopPoints(40.0);      // Activate trailing at +40 pts
   trend.setComment("Trend");

   // Execute single trend trade
   trend.execute();

   Or from OrchestratorDemo:
   run.bat 10 2                            # Direct launch (or .\run.bat 10 2)
   mvnd exec:java -Dexec.args="10 2"       # Via Maven

══════════════════════════════════════════════════════════════════════════════*/

package orchestrators;

import pro.mrpc.mt5.MT5Sugar;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;

/**
 * Trend Following Orchestrator - Trades with the trend using wider SL/TP
 */
public class TrendFollowingOrchestrator {

    private final MT5Sugar sugar;

    // Configuration
    private String symbol = "EURUSD";
    private double riskAmount = 50.0;
    private double stopLossPoints = 80.0;
    private double takeProfitPoints = 160.0;
    private double trailingStopPoints = 40.0;  // Move SL when profit reaches this
    private String comment = "Trend";

    // State
    private long currentTicket = 0;
    private double entryPrice = 0;
    private boolean trailingActivated = false;

    public TrendFollowingOrchestrator(MT5Sugar sugar) {
        this.sugar = sugar;
    }

    // ========================================================================
    // CONFIGURATION METHODS
    // ========================================================================

    /**
     * Set trading symbol.
     * For trend following, choose instruments with clear trends.
     * Example: "EURUSD", "GBPUSD" on H1/H4 timeframes
     * @param symbol Trading symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Set risk amount per trade in account currency (USD).
     * This determines position volume based on wider SL distance.
     * Example: $50 risk with 80-point SL = 0.06 lots (EURUSD)
     * @param amount Risk per trade in USD
     */
    public void setRiskAmount(double amount) {
        this.riskAmount = amount;
    }

    /**
     * Set stop loss distance in points.
     * For trend following: typically 50-100 points (wider than scalping).
     * Example: 80 points = $80 risk (1.0 lot EURUSD)
     * Recommended R/R: 1:2 (SL 80, TP 160)
     * @param points Stop loss distance in points
     */
    public void setStopLossPoints(double points) {
        this.stopLossPoints = points;
    }

    /**
     * Set take profit distance in points.
     * For trend following: typically 100-200 points.
     * Example: 160 points = $160 profit (1.0 lot EURUSD)
     * Recommended R/R: 1:2 (TP = 2 × SL)
     * @param points Take profit distance in points
     */
    public void setTakeProfitPoints(double points) {
        this.takeProfitPoints = points;
    }

    /**
     * Set trailing stop activation threshold in points.
     * When profit reaches this level, SL moves to breakeven.
     * Example: 40 points = activate trailing at +$40
     * Typical: 50% of SL distance (e.g., SL=80, trailing=40)
     * @param points Trailing stop activation threshold
     */
    public void setTrailingStopPoints(double points) {
        this.trailingStopPoints = points;
    }

    /**
     * Set comment for opened positions.
     * Helps identify trend trades in trading history.
     * @param comment Position comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    // ========================================================================
    // MAIN EXECUTION
    // ========================================================================

    /**
     * Execute trend following trade with trailing stop.
     *
     * TREND FOLLOWING WORKFLOW:
     * 1. Get market info (Bid/Ask/Spread)
     * 2. Open position in trend direction (BUY assumed)
     * 3. Monitor position and check profit in points
     * 4. Activate trailing stop when profit >= threshold
     * 5. Move SL to breakeven + buffer
     * 6. Close position (manual in demo)
     *
     * TRAILING STOP MECHANICS:
     * • Entry: BUY at 1.1000, SL=1.0920 (80pts), TP=1.1160 (160pts)
     * • Trailing threshold: 40 points
     * • Price moves to 1.1040 (+40 points profit)
     * • → Trailing activates, SL moves to 1.1010 (breakeven + 10pts)
     * • → Trade now risk-free, can only profit or breakeven
     *
     * SUGAR BENEFITS:
     * • calculateVolume() - Auto volume from risk/SL
     * • buyMarket() - One call to open with SL/TP
     * • getBid()/getAsk() - Quick price monitoring
     * • getProfit() - Real-time P/L
     * • modifyPosition() - Update SL for trailing
     * • closePosition() - Quick exit
     *
     * @throws ApiExceptionMT5 If trade fails
     * @throws InterruptedException If monitoring interrupted
     */
    public void execute() throws ApiExceptionMT5, InterruptedException {
        printHeader();

        // ═══════════════════════════════════════════════════════════════
        // STEP 1: MARKET ANALYSIS
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: getBid(), getAsk(), getSpread() - instant market info

        double bid = sugar.getBid(symbol);
        double ask = sugar.getAsk(symbol);
        double point = sugar.getPoint(symbol);

        System.out.println("  Market Info:");
        System.out.println("    Bid/Ask: " + formatPrice(bid) + " / " + formatPrice(ask));
        System.out.println("    Spread: " + sugar.getSpread(symbol) + " points");
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // STEP 2: POSITION ENTRY (TREND DIRECTION)
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: calculateVolume() auto-calculates from wider SL
        // Manual: volume = riskAmount / (stopLossPoints * pointValue)

        System.out.println("  Opening trend position...");
        System.out.println("    Direction: BUY (uptrend assumed)");
        System.out.println("    Risk: $" + String.format("%.2f", riskAmount));
        System.out.println("    SL: " + stopLossPoints + " points");
        System.out.println("    TP: " + takeProfitPoints + " points");
        System.out.println("    Trailing: " + trailingStopPoints + " points");
        System.out.println();

        // Calculate volume and SL/TP
        double volume = sugar.calculateVolume(symbol, stopLossPoints, riskAmount);
        double stopLoss = ask - (stopLossPoints * point);
        double takeProfit = ask + (takeProfitPoints * point);

        // SUGAR BENEFIT: buyMarket() opens with SL/TP in one call
        currentTicket = sugar.buyMarket(symbol, volume, stopLoss, takeProfit, comment);
        entryPrice = ask;

        System.out.println("  >> Position opened!");
        System.out.println("    Ticket: " + currentTicket);
        System.out.println("    Entry: " + formatPrice(entryPrice));
        System.out.println("    Volume: " + String.format("%.2f", volume) + " lots");
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // STEP 3: TRAILING STOP MONITORING
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: getBid(), getProfit(), modifyPosition()
        // Monitor profit and activate trailing stop at threshold

        System.out.println("  Monitoring position for trailing stop...");

        for (int i = 0; i < 3; i++) {
            Thread.sleep(1500);

            double currentBid = sugar.getBid(symbol);
            double profitPoints = (currentBid - entryPrice) / point;
            double currentProfit = sugar.getProfit();

            System.out.println("    [" + (i + 1) + "] Bid: " + formatPrice(currentBid) +
                             " | P/L: $" + String.format("%.2f", currentProfit) +
                             " (" + String.format("%.0f", profitPoints) + " pts)");

            // Check if we should activate trailing stop
            if (!trailingActivated && profitPoints >= trailingStopPoints) {
                System.out.println("    >> Trailing stop activated! Moving SL to breakeven...");

                // SUGAR BENEFIT: modifyPosition() - one call to update SL
                // Move SL to breakeven + small buffer (10 points)
                double newSL = sugar.normalizePrice(symbol, entryPrice + (10 * point));
                sugar.modifyPosition(currentTicket, newSL, takeProfit);

                trailingActivated = true;
                System.out.println("    >> SL moved to: " + formatPrice(newSL));
            }
        }
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // STEP 4: POSITION EXIT
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: closePosition() - one call to exit

        System.out.println("  Closing position...");
        sugar.closePosition(currentTicket);

        double finalBalance = sugar.getBalance();
        System.out.println("  >> Position closed!");
        System.out.println("    Final balance: $" + String.format("%.2f", finalBalance));
        System.out.println();

        printFooter();
    }

    // ════════════════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ════════════════════════════════════════════════════════════════════════

    private String formatPrice(double price) {
        try {
            int digits = sugar.getDigits(symbol);
            return String.format("%." + digits + "f", price);
        } catch (ApiExceptionMT5 e) {
            return String.format("%.5f", price); // Fallback to 5 digits
        }
    }

    private void printHeader() {
        System.out.println("+============================================================+");
        System.out.println("|  TREND FOLLOWING ORCHESTRATOR                              |");
        System.out.println("+============================================================+");
        System.out.println("  Symbol: " + symbol);
        System.out.println("  Risk: $" + String.format("%.2f", riskAmount));
        System.out.println("  SL/TP: " + stopLossPoints + "/" + takeProfitPoints + " points");
        System.out.println("  Trailing: " + trailingStopPoints + " points");
        System.out.println();
    }

    private void printFooter() {
        System.out.println("+============================================================+");
        System.out.println("|  >> TREND TRADE COMPLETED                                  |");
        System.out.println("+============================================================+\n");
    }
}

/*══════════════════════════════════════════════════════════════════════════════

                   📈 TREND FOLLOWING ORCHESTRATOR - END DOCUMENTATION 📈

════════════════════════════════════════════════════════════════════════════════

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                      WHY USE TREND FOLLOWING ORCHESTRATOR?               ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  MANUAL TREND TRADING               ⚡ TREND FOLLOWING ORCHESTRATOR
  ════════════════════════════════   ══════════════════════════════════════

  Trend Identification:
  ├─ Watch chart manually            ├─ Assumes uptrend (BUY direction)
  ├─ Calculate MA/indicators         ├─ Auto entry configuration
  └─ Decide entry manually           └─ Risk-based position sizing

  Position Entry:
  ├─ Calculate wider SL/TP           ├─ Pre-configured 80/160 points
  ├─ Manual volume calculation       ├─ calculateVolume() automatic
  ├─ Place order with wide stops     ├─ buyMarket() with SL/TP
  └─ Track ticket number             └─ Auto ticket management

  Trailing Stop Management:
  ├─ Watch position constantly       ├─ Auto monitoring (every 1.5s)
  ├─ Calculate profit in points      ├─ Real-time profit tracking
  ├─ Manually move SL to breakeven   ├─ Auto SL modification at threshold
  └─ Track breakeven level           └─ Breakeven + buffer (10 pts)

  Position Monitoring:
  ├─ Refresh terminal for P/L        ├─ getProfit() - real-time P/L
  ├─ Check if trailing threshold hit ├─ Auto activation at +40 pts
  └─ Manual SL modification          └─ modifyPosition() - instant update

  Position Exit:
  ├─ Wait for SL/TP hit              ├─ Auto SL/TP management
  ├─ Or close manually               ├─ closePosition() - quick exit
  └─ Calculate final P/L             └─ getBalance() - final report

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                        KEY SUGAR API METHODS USED                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  getBid(symbol)
  └─ Returns current bid price instantly

  getAsk(symbol)
  └─ Returns current ask price instantly

  getSpread(symbol)
  └─ Returns spread in points

  getPoint(symbol)
  └─ Returns point size for calculations

  calculateVolume(symbol, slPoints, riskAmount)
  └─ Auto-calculates position volume based on risk and wider SL
  └─ Example: symbol="EURUSD", SL=80pts, risk=$50 → volume=0.06 lots

  buyMarket(symbol, volume, sl, tp, comment)
  └─ Opens BUY position with wider SL/TP in one call
  └─ Returns ticket number for position tracking

  getProfit()
  └─ Returns total floating P/L of all open positions

  modifyPosition(ticket, newSL, newTP)
  └─ Updates SL/TP of open position
  └─ Used for trailing stop to breakeven

  normalizePrice(symbol, price)
  └─ Ensures price has correct digits for symbol

  closePosition(ticket)
  └─ Closes position by ticket number instantly

  getBalance()
  └─ Returns account balance after trade

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                      TREND FOLLOWING STRATEGY BENEFITS                   ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  1. CAPTURE LARGE MOVES:
     • Wider TP targets (100-200 points)
     • Ride the trend, don't cut profits short
     • 1:2 R/R ratio (risk $50, target $100)

  2. TRAILING STOP PROTECTION:
     • Lock profits as trade moves in your favor
     • Move SL to breakeven at +40 points
     • Risk-free trade after trailing activates
     • Can only profit or breakeven (no loss)

  3. LOWER TRADE FREQUENCY:
     • Wider stops = fewer stop-outs
     • Hold positions longer (hours/days)
     • Less screen time required
     • Ideal for swing trading

  4. BETTER RISK/REWARD:
     • 1:2 ratio minimum (SL 80, TP 160)
     • Need only 40% win rate to be profitable
     • Example: 4 wins ($400) vs 6 losses ($300) = +$100

  5. PROFESSIONAL MONEY MANAGEMENT:
     • Fixed $ risk per trade ($50)
     • Volume adjusts for wider SL automatically
     • Consistent risk regardless of stop placement

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                       TREND FOLLOWING BEST PRACTICES                     ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  INSTRUMENT SELECTION:
  ✓ Major pairs with clear trends (EURUSD, GBPUSD, USDJPY)
  ✓ Higher timeframes (H1, H4, D1)
  ✓ Low spread instruments
  ✗ Ranging/choppy markets
  ✗ Exotic pairs (high spread hurts R/R)
  ✗ Lower timeframes (M1, M5 = too noisy)

  TIMING:
  ✓ Strong trending sessions
  ✓ After breakout from consolidation
  ✓ Clear higher highs/higher lows (uptrend)
  ✗ Flat/ranging markets
  ✗ Before major news (volatility spike)
  ✗ Low liquidity periods

  RISK MANAGEMENT:
  • Risk 1-2% max per trade ($50 on $5000 account)
  • Wider SL = natural filter for bad entries
  • Trailing stop eliminates risk after threshold
  • Use 1:2 R/R minimum (prefer 1:3 for trends)

  STOP LOSS / TAKE PROFIT:
  • SL: 50-100 points (wider for trend trades)
  • TP: 100-200 points (let winners run)
  • R/R: 1:2 minimum (SL 80, TP 160)
  • Trailing: 50% of SL (SL=80, trailing=40)

  TRAILING STOP STRATEGY:
  • Activate at 50% of SL distance
  • Move SL to breakeven + buffer (10 pts)
  • NEVER move SL closer than breakeven
  • Let TP handle the exit (don't exit early)

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                          CONFIGURATION EXAMPLES                          ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  CONSERVATIVE TREND TRADING ($5000 account):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    trend.setSymbol("EURUSD");
    trend.setRiskAmount(50.0);         // 1% risk
    trend.setStopLossPoints(100.0);    // Wide SL for trends
    trend.setTakeProfitPoints(200.0);  // 1:2 R/R
    trend.setTrailingStopPoints(50.0); // Activate at +50 pts
    trend.execute();

  MODERATE TREND TRADING ($10000 account):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    trend.setSymbol("GBPUSD");
    trend.setRiskAmount(100.0);        // 1% risk
    trend.setStopLossPoints(80.0);     // Standard SL
    trend.setTakeProfitPoints(240.0);  // 1:3 R/R (better for trends)
    trend.setTrailingStopPoints(40.0); // Activate at +40 pts
    trend.execute();

  AGGRESSIVE TREND TRADING ($20000 account):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    trend.setSymbol("USDJPY");
    trend.setRiskAmount(200.0);        // 1% risk
    trend.setStopLossPoints(60.0);     // Tighter SL
    trend.setTakeProfitPoints(180.0);  // 1:3 R/R
    trend.setTrailingStopPoints(30.0); // Activate at +30 pts
    trend.execute();

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         TRAILING STOP EXAMPLE                            ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  Starting Balance: $5000.00

  Trade Setup:
    Symbol: EURUSD
    Entry: BUY at 1.1000
    Volume: 0.06 lots (risk $50, SL 80 pts)
    SL: 1.0920 (80 points = $50 risk)
    TP: 1.1160 (160 points = $100 profit)
    Trailing: Activate at +40 points

  SCENARIO 1: Trailing Stop Activates (Win)
  ═══════════════════════════════════════════════════════════════════

    Time 0s: Entry at 1.1000
      Price: 1.1000 | P/L: $0.00 | SL: 1.0920

    Time 2s: Price moves up
      Price: 1.1020 | P/L: +$12.00 (20 pts) | SL: 1.0920

    Time 4s: Price continues up
      Price: 1.1040 | P/L: +$24.00 (40 pts) | SL: 1.0920
      >>> TRAILING ACTIVATED! SL moved to 1.1010 (breakeven + 10 pts)

    Time 6s: Price moves higher
      Price: 1.1080 | P/L: +$48.00 (80 pts) | SL: 1.1010 (protected!)

    Result: TP hit at 1.1160 → Profit: +$96.00
    OR: Price reverses, SL at 1.1010 → Profit: +$6.00 (no loss!)

  SCENARIO 2: Trailing Never Activates (Loss)
  ═══════════════════════════════════════════════════════════════════

    Time 0s: Entry at 1.1000
      Price: 1.1000 | P/L: $0.00 | SL: 1.0920

    Time 2s: Price moves down
      Price: 1.0980 | P/L: -$12.00 (20 pts) | SL: 1.0920

    Time 4s: Price hits SL
      Price: 1.0920 | SL hit → Loss: -$50.00

    Result: Never reached +40 pts, SL hit at original level
    Loss: -$50.00 (exactly as planned)

  KEY BENEFIT: Once trailing activates, you can't lose on the trade!

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                NEXT STEPS                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  1. Start with demo account:
     • Practice with default settings (risk=$50, SL=80, TP=160)
     • Observe trailing stop activation
     • Test different trailing thresholds

  2. Experiment with parameters:
     • Try different SL/TP combinations (1:2, 1:3 ratios)
     • Test on different symbols (EURUSD, GBPUSD, USDJPY)
     • Adjust trailing threshold (30, 40, 50 points)
     • Vary risk amount based on account size

  3. Study other orchestrators:
     • ScalpingOrchestrator (orchestrators/ScalpingOrchestrator.java)
       → Quick in/out trades, tight stops
     • BreakoutOrchestrator (orchestrators/BreakoutOrchestrator.java)
       → Pending orders for breakout trading
     • HedgingOrchestrator (orchestrators/HedgingOrchestrator.java)
       → Risk protection with opposite positions
     • run.bat 10 (select other options)

  4. Enhance this strategy:
     • Add MA-based trend detection
     • Implement dynamic trailing (move SL incrementally)
     • Add multiple TP levels (partial exits)
     • Integrate with your own indicators

  5. Build your own orchestrator:
     • Copy this file as template
     • Add custom trend detection logic
     • Implement your trailing stop rules
     • Share with community!

══════════════════════════════════════════════════════════════════════════════*/
