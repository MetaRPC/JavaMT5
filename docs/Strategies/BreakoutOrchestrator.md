# Breakout Orchestrator

> Brackets the current price with a BUY STOP above and a SELL STOP below; whichever side the market breaks, that order fills and the opposite one is cancelled.

!!! warning "Educational / demo only"
    A template for bi-directional breakout entry, not a production system. Test on a **demo account** first and add your own level/volatility filter.

* **Source:** [`src/main/java/orchestrators/BreakoutOrchestrator.java`](https://github.com/MetaRPC/JavaMT5/blob/main/src/main/java/orchestrators/BreakoutOrchestrator.java)
* **Run:** `run.bat 10 4` (or the menu: `run.bat 10` → `[4]`)
* **Risk level:** Medium

---

## 🧭 What it does

* Places a **BUY STOP** `breakoutDistance` points above price and a **SELL STOP** the same distance below.
* Each pending order carries its own SL/TP (50 / 100 points by default → 1:2 reward/risk).
* Waits for a breakout; when one side triggers, **cancels the opposite** pending order.
* Monitors the triggered position and cleans up on exit.

**Best for:** range-bound markets poised to break, support/resistance plays, news where direction is unknown but movement is expected.

---

## ⚙️ How it works

1. **Setup** — reads `bid`/`ask`/`point`; sizes volume with `calculateVolume(symbol, stopLossPoints, riskAmount)`.
2. **Place the straddle** — `buyStopPoints(...)` above and `sellStopPoints(...)` below (points-based pending orders), each with SL/TP.
3. **Wait & select** — polls `getPositionCount()`/`getProfit()`; once a pending order fills, `cancelAll(...)` removes the other side.
4. **Exit** — `closeAll(...)` closes the position and cleans up any leftovers.

---

## 🔧 Constructor & configuration

```java
BreakoutOrchestrator(MT5Sugar sugar)
```

| Setter | Type | Default | Meaning |
|--------|------|---------|---------|
| `setSymbol(String)` | `String` | `"EURUSD"` | Symbol to trade. |
| `setRiskAmount(double)` | `double` | `40.0` | Risk per trade in account currency. |
| `setBreakoutDistance(double)` | `double` | `30.0` | Distance (points) above/below price for the stop orders. |
| `setStopLossPoints(double)` | `double` | `50.0` | Stop-loss distance in points. |
| `setTakeProfitPoints(double)` | `double` | `100.0` | Take-profit distance in points (1:2 vs the SL). |

```java
void execute() throws ApiExceptionMT5, InterruptedException
```

> This orchestrator has no `setComment()` — the order comment is managed internally.

---

## 🔗 Example

```java
BreakoutOrchestrator breakout = new BreakoutOrchestrator(sugar);
breakout.setSymbol("EURUSD");
breakout.setBreakoutDistance(30);    // straddle ±30 points
breakout.setRiskAmount(40.0);
breakout.setStopLossPoints(50.0);
breakout.setTakeProfitPoints(100.0); // 1:2 reward/risk
breakout.execute();
```

---

## 🧩 MT5Sugar methods used

`getBid` · `getAsk` · `getPoint` · `getDigits` · `calculateVolume` · `buyStopPoints` · `sellStopPoints` · `buyStop` · `sellStop` · `getPositionCount` · `getProfit` · `cancelAll` · `closeAll`

---

## ⚠️ Notes

* **Both legs are pending** until price moves; on a quiet market neither may fill within the demo loop.
* **Fast breakouts can slip** or briefly touch both levels — the orchestrator cancels the opposite side as soon as one fills.
* Mind the broker's **minimum stop distance**: `breakoutDistance` must be far enough from price for the stop orders to be accepted.

---

## 📚 See also

* [Orchestrators — Overview](Strategies.Master.Overview.md)
* [Trend Following](TrendFollowingOrchestrator.md) · [Scalping](ScalpingOrchestrator.md) · [Hedging](HedgingOrchestrator.md) · [Martingale](MartingaleOrchestrator.md)
* [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md)
