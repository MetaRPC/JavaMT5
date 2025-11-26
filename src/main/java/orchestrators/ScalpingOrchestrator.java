/*══════════════════════════════════════════════════════════════════════════════
 FILE: ScalpingOrchestrator.java — SCALPING TRADING ORCHESTRATOR

 LEVEL: 4 (Orchestrator)
 DIFFICULTY: Beginner to Intermediate

 PURPOSE:
   Professional scalping strategy for quick in/out trades!
   • Opens market positions with tight SL/TP (8-15 points)
   • Risk-based volume calculation
   • Quick profit targets with minimal exposure
   • Ideal for high-frequency trading
   • Can execute single trades or trading sessions

 📊 MANUAL SCALPING                  ⚡ SCALPING ORCHESTRATOR
 ├─ Manually watch price tick        ├─ Auto entry with calculated volume
 ├─ Calculate volume by hand          ├─ Tight SL/TP set automatically
 ├─ Place order with SL/TP            ├─ Monitor position in real-time
 ├─ Watch position constantly         ├─ Multiple trades with statistics
 ├─ Close manually                    ├─ Auto risk management
 └─ Track results manually            └─ Session reports with success rate

 📚 WHAT YOU'LL LEARN:
   → How to implement scalping strategy with MT5Sugar
   → Risk-based volume calculation for tight SL
   → Quick entry/exit with optimal risk/reward ratio
   → Monitoring multiple scalping trades
   → Session-based trading with performance tracking

 SCALPING STRATEGY EXPLAINED:

   1. Market Analysis:
      • Get current Bid/Ask prices
      • Check spread (should be low for scalping)
      • Verify free margin availability

   2. Position Entry:
      • Calculate volume based on risk amount and SL distance
      • Set tight stop loss (typically 5-10 points)
      • Set quick take profit (typically 10-20 points)
      • Risk/Reward ratio usually 1:1.5 to 1:2

   3. Position Monitoring:
      • Watch P/L in real-time
      • SL/TP manage risk automatically
      • Quick exit on target or stop

   4. Trade Execution Modes:
      a) Single trade: execute() - One quick in/out trade
      b) Multiple trades: executeMultiple() - Trading session with statistics

 WHEN TO USE SCALPING ORCHESTRATOR:
   ✓ Low-spread instruments (EURUSD, USDJPY during active sessions)
   ✓ High liquidity periods (London/New York session overlap)
   ✓ Tight spreads (< 5 points for most pairs)
   ✓ Fast execution environment
   ✓ Small account practice (start with $10-20 risk)
   ✗ High-spread instruments (exotic pairs)
   ✗ Low liquidity periods (Asian session for majors)
   ✗ News events (spread widens)

 USAGE:
   // Initialize with MT5Sugar instance
   ScalpingOrchestrator scalper = new ScalpingOrchestrator(sugar);

   // Configure strategy
   scalper.setSymbol("EURUSD");         // Low-spread symbol
   scalper.setRiskAmount(20.0);         // $20 risk per trade
   scalper.setTakeProfitPoints(15);     // 15 points TP
   scalper.setStopLossPoints(8);        // 8 points SL (1:1.87 R/R)
   scalper.setMaxPositions(1);          // One position at a time

   // Execute single trade
   scalper.execute();

   // OR: Execute trading session (5 trades, 10s delay)
   scalper.executeMultiple(5, 10000);

══════════════════════════════════════════════════════════════════════════════*/

package orchestrators;

import io.metarpc.mt5.MT5Sugar;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;

/**
 * Scalping Orchestrator - Automates quick in/out trades with tight SL/TP
 */
public class ScalpingOrchestrator {

    private final MT5Sugar sugar;

    // Configuration
    private String symbol = "EURUSD";
    private double riskAmount = 20.0;          // Risk per trade in USD
    private double takeProfitPoints = 15.0;    // TP in points
    private double stopLossPoints = 8.0;       // SL in points
    private int maxPositions = 1;              // Max concurrent positions
    private String comment = "Scalp";

    // State
    private long currentTicket = 0;
    private double entryPrice = 0;

    public ScalpingOrchestrator(MT5Sugar sugar) {
        this.sugar = sugar;
    }

    // ========================================================================
    // CONFIGURATION METHODS
    // ========================================================================

    /**
     * Set trading symbol.
     * For scalping, choose low-spread instruments.
     * Example: "EURUSD", "USDJPY" (< 2 points spread)
     * @param symbol Trading symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Set risk amount per trade in account currency (USD).
     * This determines position volume based on SL distance.
     * Example: $20 risk with 8-point SL = 0.25 lots (EURUSD)
     * @param riskAmount Risk per trade in USD
     */
    public void setRiskAmount(double riskAmount) {
        this.riskAmount = riskAmount;
    }

    /**
     * Set take profit distance in points.
     * For scalping: typically 10-20 points.
     * Example: 15 points = $15 profit (1.0 lot EURUSD)
     * @param points Take profit distance in points
     */
    public void setTakeProfitPoints(double points) {
        this.takeProfitPoints = points;
    }

    /**
     * Set stop loss distance in points.
     * For scalping: typically 5-10 points.
     * Example: 8 points = $8 risk (1.0 lot EURUSD)
     * Recommended R/R: 1:1.5 to 1:2 (SL:TP ratio)
     * @param points Stop loss distance in points
     */
    public void setStopLossPoints(double points) {
        this.stopLossPoints = points;
    }

    /**
     * Set maximum concurrent positions.
     * For scalping: usually 1 position at a time.
     * Multiple positions increase risk exposure.
     * @param max Maximum concurrent positions
     */
    public void setMaxPositions(int max) {
        this.maxPositions = max;
    }

    /**
     * Set comment for opened positions.
     * Helps identify scalping trades in trading history.
     * @param comment Position comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    // ========================================================================
    // MAIN EXECUTION
    // ========================================================================

    /**
     * Execute a single scalping trade.
     *
     * SCALPING WORKFLOW:
     * 1. Check if we can enter (max positions, margin check)
     * 2. Get current market info (Bid/Ask, spread)
     * 3. Calculate risk-based volume
     * 4. Open position with tight SL/TP
     * 5. Monitor position briefly
     * 6. Close position (manual close for demo)
     *
     * SUGAR BENEFITS:
     * • calculateVolume() - Auto volume from risk/SL
     * • getBid()/getAsk() - Quick price fetch
     * • getSpread() - Check spread before entry
     * • buyMarket() - One call to open with SL/TP
     * • getProfit() - Real-time P/L monitoring
     * • closePosition() - Quick exit
     *
     * @throws ApiExceptionMT5 If trade fails
     * @throws InterruptedException If monitoring interrupted
     */
    public void execute() throws ApiExceptionMT5, InterruptedException {
        printHeader();

        // ═══════════════════════════════════════════════════════════════
        // STEP 1: PRE-ENTRY VALIDATION
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: getPositionCount() + getFreeMargin() in helper

        if (!canEnter()) {
            System.out.println("  Cannot enter: Max positions reached or insufficient margin");
            return;
        }

        // ═══════════════════════════════════════════════════════════════
        // STEP 2: MARKET ANALYSIS
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: getBid(), getAsk(), getSpread() - All market info in 3 calls

        double bid = sugar.getBid(symbol);
        double ask = sugar.getAsk(symbol);
        int spread = sugar.getSpread(symbol);

        System.out.println("  Market Info:");
        System.out.println("    Bid/Ask: " + formatPrice(bid) + " / " + formatPrice(ask));
        System.out.println("    Spread: " + spread + " points");
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // STEP 3: POSITION ENTRY (RISK-BASED)
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: calculateVolume() auto-calculates from risk
        // Manual: You'd calculate volume = risk / (SL_points * point_value)

        System.out.println("  Opening scalping position...");
        System.out.println("    Risk: $" + String.format("%.2f", riskAmount));
        System.out.println("    SL: " + stopLossPoints + " points");
        System.out.println("    TP: " + takeProfitPoints + " points");
        System.out.println("    Risk/Reward: 1:" + String.format("%.2f", takeProfitPoints / stopLossPoints));
        System.out.println();

        // Calculate volume based on risk
        double volume = sugar.calculateVolume(symbol, stopLossPoints, riskAmount);

        // Calculate SL/TP using already fetched ask price
        double point = sugar.getPoint(symbol);
        double stopLoss = ask - (stopLossPoints * point);
        double takeProfit = ask + (takeProfitPoints * point);

        // SUGAR BENEFIT: buyMarket() opens with SL/TP in one call
        currentTicket = sugar.buyMarket(symbol, volume, stopLoss, takeProfit, comment);
        entryPrice = ask;

        System.out.println("  >> Position opened!");
        System.out.println("    Ticket: " + currentTicket);
        System.out.println("    Entry: " + formatPrice(entryPrice));
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // STEP 4: POSITION MONITORING
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: getProfit() returns total P/L instantly

        System.out.println("  Monitoring position...");
        Thread.sleep(2000);

        double currentProfit = sugar.getProfit();
        System.out.println("    Current P/L: $" + String.format("%.2f", currentProfit));
        System.out.println();

        // ═══════════════════════════════════════════════════════════════
        // STEP 5: POSITION EXIT
        // ═══════════════════════════════════════════════════════════════
        // SUGAR BENEFIT: closePosition() - One call to exit
        // Manual: OrderSend() with opposite direction, volume matching

        System.out.println("  Closing position...");
        sugar.closePosition(currentTicket);

        System.out.println("  >> Position closed!");
        System.out.println();

        printFooter();
    }

    /**
     * Execute multiple scalping trades with performance tracking.
     *
     * TRADING SESSION WORKFLOW:
     * 1. Execute specified number of trades
     * 2. Track success/failure rate
     * 3. Wait between trades to avoid over-trading
     * 4. Display session statistics
     * 5. Show final balance/equity
     *
     * SUGAR BENEFITS:
     * • Reuse execute() for each trade
     * • getBalance()/getEquity() for session report
     * • Auto error handling per trade
     *
     * @param count Number of trades to execute
     * @param delayBetweenTrades Delay in milliseconds between trades
     * @throws ApiExceptionMT5 If session fails
     * @throws InterruptedException If session interrupted
     */
    public void executeMultiple(int count, long delayBetweenTrades) throws ApiExceptionMT5, InterruptedException {
        System.out.println("\n+============================================================+");
        System.out.println("|  SCALPING ORCHESTRATOR - MULTIPLE TRADES                 |");
        System.out.println("+============================================================+");
        System.out.println("  Target trades: " + count);
        System.out.println("  Delay between trades: " + (delayBetweenTrades / 1000) + "s");
        System.out.println();

        int successCount = 0;
        int failCount = 0;

        for (int i = 1; i <= count; i++) {
            System.out.println("------------------------------------------------------------");
            System.out.println(" TRADE " + i + " / " + count);
            System.out.println("------------------------------------------------------------");

            try {
                execute();
                successCount++;
            } catch (ApiExceptionMT5 e) {
                System.err.println("  X Trade failed: " + e.getMessage());
                failCount++;
            }

            if (i < count) {
                System.out.println("  Waiting " + (delayBetweenTrades / 1000) + "s before next trade...");
                Thread.sleep(delayBetweenTrades);
                System.out.println();
            }
        }

        // Summary
        System.out.println("\n+============================================================+");
        System.out.println("|  >> SCALPING SESSION COMPLETED                           |");
        System.out.println("+============================================================+");
        System.out.println("  Total trades attempted: " + count);
        System.out.println("  Successful: " + successCount);
        System.out.println("  Failed: " + failCount);
        System.out.println("  Success rate: " + String.format("%.1f%%", (successCount * 100.0 / count)));

        double finalBalance = sugar.getBalance();
        double finalEquity = sugar.getEquity();
        System.out.println();
        System.out.println("  Final balance: $" + String.format("%.2f", finalBalance));
        System.out.println("  Final equity: $" + String.format("%.2f", finalEquity));
        System.out.println("+============================================================+\n");
    }

    // ════════════════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ════════════════════════════════════════════════════════════════════════

    private boolean canEnter() throws ApiExceptionMT5 {
        int currentPositions = sugar.getPositionCount();
        if (currentPositions >= maxPositions) {
            return false;
        }

        double freeMargin = sugar.getFreeMargin();
        return freeMargin > 100; // Minimum $100 free margin
    }

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
        System.out.println("|  SCALPING ORCHESTRATOR - SINGLE TRADE                    |");
        System.out.println("+============================================================+");
        System.out.println("  Symbol: " + symbol);
        System.out.println("  Risk: $" + String.format("%.2f", riskAmount));
        System.out.println("  SL/TP: " + stopLossPoints + "/" + takeProfitPoints + " points");
        System.out.println();
    }

    private void printFooter() {
        System.out.println("+============================================================+");
        System.out.println("|  >> SCALPING TRADE COMPLETED                             |");
        System.out.println("+============================================================+\n");
    }
}

/*══════════════════════════════════════════════════════════════════════════

                      📈 SCALPING ORCHESTRATOR - END DOCUMENTATION 📈



┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                          WHY USE SCALPING ORCHESTRATOR?                  ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  MANUAL SCALPING                    ⚡ SCALPING ORCHESTRATOR
  ════════════════════════════════   ══════════════════════════════════════

  Market Analysis:
  ├─ Check Bid/Ask manually          ├─ getBid(), getAsk() - instant fetch
  ├─ Calculate spread yourself       ├─ getSpread() - one call
  └─ Watch tick chart constantly     └─ Auto market info collection

  Volume Calculation:
  ├─ Calculate: risk/(SL*point)      ├─ calculateVolume() - automatic
  ├─ Check min/max volume limits     ├─ Respects broker limits
  └─ Manual adjustment for margin    └─ Margin-aware calculation

  Trade Execution:
  ├─ Open market order               ├─ buyMarket() with SL/TP
  ├─ Manually set SL (ask - SL*pt)   ├─ Auto SL/TP calculation
  ├─ Manually set TP (ask + TP*pt)   ├─ One-call position opening
  └─ Track ticket number             └─ Auto ticket management

  Position Monitoring:
  ├─ Refresh terminal for P/L        ├─ getProfit() - real-time P/L
  ├─ Watch for SL/TP hit             ├─ Auto SL/TP management
  └─ Manual close on discretion      └─ closePosition() - instant exit

  Multiple Trades:
  ├─ Repeat process manually         ├─ executeMultiple() - auto session
  ├─ Track wins/losses manually      ├─ Success rate tracking
  └─ Calculate session P/L yourself  └─ Auto balance/equity reports

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                        KEY SUGAR API METHODS USED                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  getBid(symbol)
  └─ Returns current bid price instantly

  getAsk(symbol)
  └─ Returns current ask price instantly

  getSpread(symbol)
  └─ Returns spread in points (crucial for scalping)

  calculateVolume(symbol, slPoints, riskAmount)
  └─ Auto-calculates position volume based on risk and SL
  └─ Example: symbol="EURUSD", SL=8pts, risk=$20 → volume=0.25 lots

  buyMarket(symbol, volume, sl, tp, comment)
  └─ Opens BUY position with SL/TP in one call
  └─ Returns ticket number for position tracking

  getProfit()
  └─ Returns total floating P/L of all open positions

  closePosition(ticket)
  └─ Closes position by ticket number instantly

  getPositionCount()
  └─ Returns number of open positions

  getFreeMargin()
  └─ Returns available margin for new trades

  getBalance()
  └─ Returns account balance (realized P/L)

  getEquity()
  └─ Returns account equity (balance + floating P/L)

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         SCALPING STRATEGY BENEFITS                       ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  1. LOW RISK PER TRADE:
     • Tight stop losses (5-10 points)
     • Fixed dollar risk ($10-20 typical)
     • Quick exit if wrong

  2. HIGH WIN RATE POTENTIAL:
     • Small profit targets (10-20 points)
     • Easier to reach than large targets
     • Multiple opportunities per day

  3. MINIMAL MARKET EXPOSURE:
     • Positions held for seconds to minutes
     • Less overnight risk
     • Less exposure to news events

  4. VOLUME-BASED PROFITS:
     • Multiple trades compound profits
     • Small wins add up over session
     • Example: 10 trades × $15 profit = $150/day

  5. CLEAR RISK/REWARD:
     • Typically 1:1.5 to 1:2 (SL:TP)
     • Example: Risk $10, target $15-20
     • Break-even with 50-60% win rate

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         SCALPING BEST PRACTICES                          ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  INSTRUMENT SELECTION:
  ✓ Major pairs (EURUSD, USDJPY, GBPUSD)
  ✓ Low spread (< 2-3 points)
  ✓ High liquidity
  ✗ Exotic pairs (high spread kills scalping)
  ✗ Low liquidity instruments

  TIMING:
  ✓ London session (08:00-17:00 GMT)
  ✓ New York session (13:00-22:00 GMT)
  ✓ Session overlaps (highest liquidity)
  ✗ Asian session for majors (low volume)
  ✗ Before/after major news (spread widens)

  RISK MANAGEMENT:
  • Risk 1-2% max per trade ($10-20 on $1000 account)
  • Never hold losing position hoping for recovery
  • Set max daily loss limit (e.g., 3 consecutive losses = stop)
  • Track win rate (aim for 55%+ for profitability)

  POSITION SIZING:
  • Use calculateVolume() for consistent risk
  • Don't override calculated volume manually
  • Smaller positions = more trades possible

  STOP LOSS / TAKE PROFIT:
  • SL: 5-10 points (tight protection)
  • TP: 10-20 points (realistic targets)
  • R/R: 1:1.5 minimum (prefer 1:2)
  • NEVER remove SL "to give trade room"

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                          CONFIGURATION EXAMPLES                          ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  CONSERVATIVE SCALPING ($1000 account):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    scalper.setSymbol("EURUSD");
    scalper.setRiskAmount(10.0);        // 1% risk
    scalper.setStopLossPoints(8);       // Tight SL
    scalper.setTakeProfitPoints(12);    // 1:1.5 R/R
    scalper.setMaxPositions(1);
    scalper.execute();

  MODERATE SCALPING ($2000 account):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    scalper.setSymbol("GBPUSD");
    scalper.setRiskAmount(20.0);        // 1% risk
    scalper.setStopLossPoints(10);      // Standard SL
    scalper.setTakeProfitPoints(20);    // 1:2 R/R
    scalper.setMaxPositions(1);
    scalper.executeMultiple(5, 10000);  // 5 trades, 10s apart

  AGGRESSIVE SCALPING ($5000 account):
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    scalper.setSymbol("EURUSD");
    scalper.setRiskAmount(50.0);        // 1% risk
    scalper.setStopLossPoints(8);       // Very tight SL
    scalper.setTakeProfitPoints(16);    // 1:2 R/R
    scalper.setMaxPositions(2);         // Allow 2 concurrent
    scalper.executeMultiple(10, 15000); // 10 trades, 15s apart

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                            SCALPING SESSION EXAMPLE                      ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  Starting Balance: $1000.00

  Trade 1: BUY 0.12 EURUSD at 1.1000
    SL: 1.0992 (8 points = $10 risk)
    TP: 1.1015 (15 points = $18 profit)
    Result: TP hit → +$18
    Balance: $1018.00

  Trade 2: BUY 0.12 EURUSD at 1.1005
    SL: 1.0997 (8 points = $10 risk)
    TP: 1.1020 (15 points = $18 profit)
    Result: TP hit → +$18
    Balance: $1036.00

  Trade 3: BUY 0.12 EURUSD at 1.1012
    SL: 1.1004 (8 points = $10 risk)
    TP: 1.1027 (15 points = $18 profit)
    Result: SL hit → -$10
    Balance: $1026.00

  Trade 4: BUY 0.12 EURUSD at 1.1008
    SL: 1.1000 (8 points = $10 risk)
    TP: 1.1023 (15 points = $18 profit)
    Result: TP hit → +$18
    Balance: $1044.00

  Trade 5: BUY 0.12 EURUSD at 1.1015
    SL: 1.1007 (8 points = $10 risk)
    TP: 1.1030 (15 points = $18 profit)
    Result: TP hit → +$18
    Balance: $1062.00

  SESSION RESULTS:
  ════════════════════════════════════════════════════════════════════
    Total trades: 5
    Wins: 4 (80%)
    Losses: 1 (20%)
    Total profit: +$62 (+6.2% in one session!)
    Time: ~2 minutes

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                               HOW TO RUN                                 ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  From run.bat:
    run.bat 10
    Then choose option "4" (Scalping Strategy)

  From Maven:
    mvnd compile exec:java -Dexec.mainClass="examples.orchestrators.OrchestratorDemo" -Dexec.args="4"

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                NEXT STEPS                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

  1. Start with demo account:
     • Practice with default settings (risk=$10-20)
     • Run executeMultiple(10, 10000) sessions
     • Track your win rate (aim for 55%+)

  2. Experiment with parameters:
     • Try different SL/TP combinations
     • Test on different symbols (EURUSD, GBPUSD, USDJPY)
     • Adjust risk amount based on account size

  3. Study other orchestrators:
     • TrendOrchestrator (examples/orchestrators/TrendOrchestrator.java)
     • BreakoutOrchestrator (examples/orchestrators/BreakoutOrchestrator.java)
     • HedgingOrchestrator (examples/orchestrators/HedgingOrchestrator.java)

  4. Build your own strategy:
     • Copy this orchestrator as template
     • Add custom entry logic
     • Implement your own risk management

══════════════════════════════════════════════════════════════════════════════*/
