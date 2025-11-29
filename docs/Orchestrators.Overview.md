# Trading Orchestrators - Overview

> **Strategy orchestrators** automate complete trading workflows. Each orchestrator implements a specific trading strategy using MT5Sugar API. Located in `src/main/java/orchestrators/`.

---

## 📁 What lives here

* **TrendFollowingOrchestrator.java** - trend following with trailing stops
* **ScalpingOrchestrator.java** - quick in/out trades with tight SL/TP
* **HedgingOrchestrator.java** - defensive hedging on adverse moves
* **BreakoutOrchestrator.java** - breakout trading with pending orders
* **MartingaleOrchestrator.java** - doubling volume after losses ⚠️ HIGH RISK

---

## 🧭 Plain English

**What are orchestrators?**

Pre-built trading strategies that automate entire workflows:

* Position entry with calculated volume
* Stop Loss / Take Profit management
* Position monitoring
* Automatic exits and cleanup
* Performance tracking

**When to use:**

* ✅ Learning trading strategy automation
* ✅ Prototyping new strategies quickly
* ✅ Demo/testing environments
* ✅ Educational purposes

**When NOT to use:**

* ❌ Production without modification
* ❌ Real money without thorough testing
* ❌ As-is without understanding the code

> **Rule of thumb:** Orchestrators are **educational examples** - adapt them to your needs, don't use blindly.

---

## Quick choose

| Strategy need                        | Orchestrator            | Best for                          | Risk level |
| ------------------------------------ | ----------------------- | --------------------------------- | ---------- |
| Capture trending moves               | `TrendFollowing`        | Strong trends, swing trading      | Medium     |
| Quick profits, tight stops           | `Scalping`              | High liquidity, low spreads       | Low-Medium |
| Protect position on adverse moves    | `Hedging`               | Uncertain markets, volatility     | Medium     |
| Trade breakouts in either direction  | `Breakout`              | Range-bound ready to break        | Medium     |
| Double volume after losses           | `Martingale`            | ⚠️ DEMO ONLY - educational       | VERY HIGH  |

---

## Strategy details

### 1. TrendFollowingOrchestrator

**What it does:**
* Opens position in trend direction (simplified: assumes uptrend = BUY)
* Uses wider SL/TP (80/160 points default) for trend capture
* Monitors position and activates trailing stop
* Moves SL to breakeven when profit threshold reached (40 points)
* Risk-based volume calculation

**When to use:**
* Strong trending markets (uptrend/downtrend)
* Higher timeframes (H1, H4, D1)
* Low volatility instruments during sessions
* Swing trading strategies

**Configuration:**
```java
TrendFollowingOrchestrator trend = new TrendFollowingOrchestrator(sugar);
trend.setSymbol("EURUSD");
trend.setRiskAmount(50.0);              // $50 risk per trade
trend.setStopLossPoints(80.0);          // 80 points SL (wider)
trend.setTakeProfitPoints(160.0);       // 160 points TP (1:2 R/R)
trend.setTrailingStopPoints(40.0);      // Activate trailing at +40p
trend.execute();
```

**Key features:**
* Trailing stop activation
* Breakeven protection
* 1:2 risk/reward ratio
* Position monitoring loop

---

### 2. ScalpingOrchestrator

**What it does:**
* Opens market positions with tight SL/TP (8-15 points typical)
* Quick profit targets with minimal exposure
* Can execute single trades or trading sessions
* Real-time position monitoring
* Session statistics tracking

**When to use:**
* Low-spread instruments (EURUSD, USDJPY)
* High liquidity periods (London/NY overlap)
* Tight spreads (< 5 points)
* Fast execution environment

**Configuration:**
```java
ScalpingOrchestrator scalper = new ScalpingOrchestrator(sugar);
scalper.setSymbol("EURUSD");
scalper.setRiskAmount(20.0);            // $20 risk per trade
scalper.setStopLossPoints(8);           // 8 points SL (tight)
scalper.setTakeProfitPoints(15);        // 15 points TP (1:1.87 R/R)
scalper.setMaxPositions(1);

// Single trade
scalper.execute();

// OR: Trading session (5 trades, 10s delay)
scalper.executeMultiple(5, 10000);
```

**Key features:**
* Tight SL/TP for quick exits
* Multiple trade execution mode
* Session performance tracking
* Win rate statistics

---

### 3. HedgingOrchestrator

**What it does:**
* Opens primary position (BUY or SELL)
* Monitors for adverse price movement
* Opens opposite hedge position if trigger hit (default: 50 points loss)
* Locks losses at hedge level (net position = 0)
* Manages both positions together

**When to use:**
* Uncertain market conditions (high volatility)
* Protect position during news events
* Lock losses instead of stop loss
* When regulations allow hedging

**Configuration:**
```java
HedgingOrchestrator hedge = new HedgingOrchestrator(sugar);
hedge.setSymbol("EURUSD");
hedge.setRiskAmount(30.0);              // Risk $30 per trade
hedge.setHedgeTriggerPoints(50.0);      // Hedge after 50p loss
hedge.setStopLossPoints(100.0);         // 100p SL on primary
hedge.setTakeProfitPoints(150.0);       // 150p TP
hedge.execute(true);                    // true = BUY first
```

**Key features:**
* Automatic hedge activation
* Loss locking mechanism
* Dual position management
* Defensive risk control

---

### 4. BreakoutOrchestrator

**What it does:**
* Places BUY STOP above current price
* Places SELL STOP below current price
* Waits for breakout in either direction
* Cancels opposite order when one triggers
* Monitors triggered position with cleanup

**When to use:**
* Range-bound markets ready to break out
* Support/resistance level trading
* News event trading (volatility expected)
* When direction unclear but movement expected

**Configuration:**
```java
BreakoutOrchestrator breakout = new BreakoutOrchestrator(sugar);
breakout.setSymbol("EURUSD");
breakout.setBreakoutDistance(30);       // 30p from current price
breakout.setRiskAmount(40.0);           // Risk $40 per trade
breakout.setStopLossPoints(50.0);       // 50p SL
breakout.setTakeProfitPoints(100.0);    // 100p TP (1:2 R/R)
breakout.execute();
```

**Key features:**
* Bi-directional pending orders
* Automatic opposite order cancellation
* Breakout confirmation entry
* Position monitoring

---

### 5. MartingaleOrchestrator ⚠️

**What it does:**
* Starts with base volume (e.g., 0.01 lots)
* If trade wins → reset to base volume
* If trade loses → DOUBLE volume for next trade
* Continue until profit or max trades reached
* Goal: one win recovers all previous losses

**⚠️ WARNING - EXTREME RISK:**
* Exponential capital requirement
* Account can be wiped out quickly
* Margin calls likely during losing streak
* NOT recommended for real money

**When to use:**
* ⚠️ **DEMO ACCOUNTS ONLY** for learning
* Understanding Martingale mechanics
* Educational simulations
* Academic research on betting systems

**Configuration:**
```java
MartingaleOrchestrator mart = new MartingaleOrchestrator(sugar);
mart.setSymbol("EURUSD");
mart.setBaseVolume(0.01);               // Start with 0.01 lots
mart.setMaxTrades(5);                   // Max 5 trades in sequence
mart.setStopLossPoints(20.0);           // 20p SL
mart.setTakeProfitPoints(20.0);         // 20p TP (1:1)
mart.execute();
```

**Volume progression example:**
```
Trade 1: 0.01 lots → Loss → Total: -$10
Trade 2: 0.02 lots → Loss → Total: -$30
Trade 3: 0.04 lots → Loss → Total: -$70
Trade 4: 0.08 lots → WIN  → Total: +$10 ✓
```

---

## ❌ Cross-refs & gotchas

### General orchestrator notes

* **Educational purpose:** Orchestrators are examples, not production-ready
* **Customize parameters:** Adjust SL/TP/risk to your strategy
* **Test thoroughly:** Always test on demo before real money
* **Comment annotations:** Each file has detailed explanations in code
* **Risk management:** All use risk-based volume calculation
* **Error handling:** Basic error handling - enhance for production

### Risk levels explained

* **Low-Medium:** Tight stops, controlled risk (Scalping)
* **Medium:** Standard stops, reasonable risk (Trend, Hedging, Breakout)
* **VERY HIGH:** Exponential risk growth (Martingale) ⚠️

### Common patterns

All orchestrators follow similar structure:

1. **Configuration** - set symbol, risk, SL/TP parameters
2. **Entry** - open position(s) with calculated volume
3. **Monitoring** - watch position state in loop
4. **Management** - trailing stops, hedging, etc.
5. **Exit** - close positions and cleanup
6. **Reporting** - print statistics and results

### Integration with MT5Sugar

All orchestrators use MT5Sugar API:

* `buyMarket()` / `sellMarket()` - market orders
* `buyStop()` / `sellStop()` - pending orders
* `calculateVolume()` - risk-based sizing
* `modifyPosition()` - trailing stops
* `closePosition()` - exits
* `getBalance()`, `getBid()`, `getAsk()` - info

---

## 🟢 Running orchestrators

### Via OrchestratorDemo menu

```bash
# Run interactive demo
run.bat 10

# Select orchestrator from menu:
[1] Trend Following
[2] Scalping
[3] Hedging
[4] Breakout
[5] Martingale
```

### Programmatically

```java
// Create MT5Sugar instance
MT5Account account = new MT5Account(login, password);
account.connect(host, port, symbol, useSSL, timeout);
MT5Service service = new MT5Service(account);
MT5Sugar sugar = new MT5Sugar(service);

// Choose and configure orchestrator
TrendFollowingOrchestrator trend = new TrendFollowingOrchestrator(sugar);
trend.setSymbol("EURUSD");
trend.setRiskAmount(50.0);
trend.setStopLossPoints(80.0);
trend.setTakeProfitPoints(160.0);

// Execute strategy
trend.execute();

// Cleanup
account.disconnect();
```

### Custom orchestrator template

```java
public class MyOrchestrator {
    private final MT5Sugar sugar;

    public MyOrchestrator(MT5Sugar sugar) {
        this.sugar = sugar;
    }

    public void execute() throws ApiExceptionMT5 {
        // 1. Configuration
        String symbol = "EURUSD";
        double risk = 50.0;

        // 2. Entry
        double volume = sugar.calculateVolume(symbol, slPoints, risk);
        long ticket = sugar.buyMarket(symbol, volume, sl, tp);

        // 3. Monitoring
        while (positionOpen) {
            // Check conditions
            // Modify if needed
        }

        // 4. Exit & cleanup
        sugar.closePosition(ticket);
    }
}
```

---

## See also

* **MT5Sugar API:** [MT5Sugar.Overview.md](./MT5Sugar/MT5Sugar.Overview.md) - underlying convenience layer
* **MT5Service:** Wrapper methods used by orchestrators
* **MT5Account:** Low-level proto API
* **Example demos:** Check `src/main/java/examples/` for more usage patterns
* **Risk management:** [calculateVolume](./MT5Sugar/7.%20Risk_management/calculateVolume.md) documentation

---

## 📚 Learning path

**Recommended order for studying orchestrators:**

1. **ScalpingOrchestrator** - simplest, good starting point
2. **BreakoutOrchestrator** - introduces pending orders
3. **TrendFollowingOrchestrator** - adds position modification (trailing)
4. **HedgingOrchestrator** - dual position management
5. **MartingaleOrchestrator** - advanced (demo only!)

**Each orchestrator teaches:**
* Different trading strategy implementation
* Risk management techniques
* Position monitoring patterns
* MT5Sugar API usage in context
* Real-world trading automation challenges

> 💡 **Tip:** Read the detailed comments in each orchestrator's source code - they contain extensive explanations of strategy logic, parameters, and usage examples.
