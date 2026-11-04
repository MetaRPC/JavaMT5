# Hedging Orchestrator

> Opens a primary position and, if price moves against it far enough, opens an opposite position to lock the loss instead of being stopped out.

!!! warning "Educational / demo only"
    A template for the hedging workflow, not a production system. Hedging must be **allowed by your broker/account**; some jurisdictions and account types forbid it. Test on a **demo account** first.

* **Source:** [`src/main/java/orchestrators/HedgingOrchestrator.java`](https://github.com/MetaRPC/JavaMT5/blob/main/src/main/java/orchestrators/HedgingOrchestrator.java)
* **Run:** `run.bat 10 3` (or the menu: `run.bat 10` → `[3]`)
* **Risk level:** Medium

---

## 🧭 What it does

* Opens a **primary** position (BUY or SELL, chosen via `execute(buyFirst)`).
* Monitors it for an adverse move.
* If the loss reaches `hedgeTriggerPoints`, opens an **opposite hedge** position — the net exposure becomes ~0, locking the loss at that level.
* Manages both legs and closes them on exit.

**Best for:** uncertain / volatile conditions, protecting a position through a news event, locking a loss instead of taking a hard stop.

---

## ⚙️ How it works

1. **Entry** — `calculateVolume(...)` → `buyMarket(...)` or `sellMarket(...)` for the primary leg (per `buyFirst`), with SL/TP from the point distances.
2. **Monitor** — tracks the floating loss in points via `getBid`/`getAsk`/`getProfit`.
3. **Hedge** — once the adverse move hits `hedgeTriggerPoints`, opens the opposite-direction order (`sellMarket`/`buyMarket`) so the combined position is flat (`hedgeOpened` guards against repeating).
4. **Exit** — closes the open legs (`closePosition`) and reports the balance.

---

## 🔧 Constructor & configuration

```java
HedgingOrchestrator(MT5Sugar sugar)
```

| Setter | Type | Default | Meaning |
|--------|------|---------|---------|
| `setSymbol(String)` | `String` | `"EURUSD"` | Symbol to trade. |
| `setRiskAmount(double)` | `double` | `30.0` | Risk per trade in account currency. |
| `setStopLossPoints(double)` | `double` | `100.0` | Stop-loss distance in points on the primary leg. |
| `setTakeProfitPoints(double)` | `double` | `150.0` | Take-profit distance in points. |
| `setHedgeTriggerPoints(double)` | `double` | `50.0` | Adverse move (points) that opens the hedge. |
| `setComment(String)` | `String` | `"Hedge"` | Order comment / tag. |

```java
void execute(boolean buyFirst) throws ApiExceptionMT5, InterruptedException
```

> `buyFirst = true` → primary is a **BUY** (hedge is a SELL); `false` → primary is a **SELL**.

---

## 🔗 Example

```java
HedgingOrchestrator hedge = new HedgingOrchestrator(sugar);
hedge.setSymbol("EURUSD");
hedge.setRiskAmount(30.0);
hedge.setStopLossPoints(100.0);
hedge.setTakeProfitPoints(150.0);
hedge.setHedgeTriggerPoints(50.0);   // hedge after 50 points of loss
hedge.execute(true);                 // BUY primary first
```

---

## 🧩 MT5Sugar methods used

`getBid` · `getAsk` · `getPoint` · `getDigits` · `getSpread` · `calculateVolume` · `buyMarket` · `sellMarket` · `getProfit` · `getPositionCount` · `getBalance` · `closePosition`

---

## ⚠️ Notes

* **Broker must allow hedging** (opposite positions on the same symbol); netting accounts will reject the hedge leg.
* **Hedging locks the loss, it doesn't erase it** — you still carry the spread/swap of two legs; it buys time to decide, not a free exit.
* The hedge opens **once**, at the trigger; extend `execute()` for layered/dynamic hedging.

---

## 📚 See also

* [Orchestrators — Overview](Strategies.Master.Overview.md)
* [Trend Following](TrendFollowingOrchestrator.md) · [Scalping](ScalpingOrchestrator.md) · [Breakout](BreakoutOrchestrator.md) · [Martingale](MartingaleOrchestrator.md)
* [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md)
