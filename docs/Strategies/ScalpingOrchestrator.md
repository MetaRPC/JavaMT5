# Scalping Orchestrator

> Quick in-and-out trades with tight SL/TP — one trade, or a whole session of them with a delay in between.

!!! warning "Educational / demo only"
    A template showing the scalping workflow and the MT5Sugar calls behind it, not a production system. Entry direction is **simplified** (BUY). Study the code and test on a **demo account** first.

* **Source:** [`src/main/java/orchestrators/ScalpingOrchestrator.java`](https://github.com/MetaRPC/JavaMT5/blob/main/src/main/java/orchestrators/ScalpingOrchestrator.java)
* **Run:** `run.bat 10 1` (or the menu: `run.bat 10` → `[1]`)
* **Risk level:** Low–Medium

---

## 🧭 What it does

* Opens a market position with **tight SL/TP** (8 / 15 points by default) for fast exits.
* Sizes the position from a fixed **risk amount** and the stop distance.
* Can run a **single trade** (`execute()`) or a **session of trades** (`executeMultiple(count, delay)`).
* Tracks session stats (balance, equity, free margin, open-position count, win/loss).

**Best for:** low-spread, high-liquidity symbols (EURUSD, USDJPY) during active sessions.

---

## ⚙️ How it works

1. **Market analysis** — reads `bid` / `ask` / `point` / `spread`.
2. **Entry** — `calculateVolume(symbol, stopLossPoints, riskAmount)` → `buyMarket(symbol, volume, sl, tp, comment)` with SL/TP computed from the tight point distances.
3. **Monitor** — watches `getProfit()` / `getPositionCount()` for a quick exit.
4. **Exit & report** — `closePosition(ticket)`, then prints balance / equity / free margin. `executeMultiple(count, delay)` repeats this `count` times, sleeping `delay` ms between trades, and sums the session result.

---

## 🔧 Constructor & configuration

```java
ScalpingOrchestrator(MT5Sugar sugar)
```

| Setter | Type | Default | Meaning |
|--------|------|---------|---------|
| `setSymbol(String)` | `String` | `"EURUSD"` | Symbol to trade. |
| `setRiskAmount(double)` | `double` | `20.0` | Risk per trade in account currency. |
| `setStopLossPoints(double)` | `double` | `8.0` | Stop-loss distance in points (tight). |
| `setTakeProfitPoints(double)` | `double` | `15.0` | Take-profit distance in points. |
| `setMaxPositions(int)` | `int` | `1` | Max concurrent positions. |
| `setComment(String)` | `String` | `"Scalp"` | Order comment / tag. |

```java
void execute() throws ApiExceptionMT5, InterruptedException                       // one trade
void executeMultiple(int count, long delayBetweenTrades) throws ApiExceptionMT5, InterruptedException  // session
```

> `delayBetweenTrades` is in **milliseconds** (e.g. `10000` = 10 s).

---

## 🔗 Example

```java
ScalpingOrchestrator scalper = new ScalpingOrchestrator(sugar);
scalper.setSymbol("EURUSD");
scalper.setRiskAmount(20.0);
scalper.setStopLossPoints(8);
scalper.setTakeProfitPoints(15);
scalper.setMaxPositions(1);

scalper.execute();                 // single trade
// scalper.executeMultiple(5, 10000);  // or a session: 5 trades, 10s apart
```

---

## 🧩 MT5Sugar methods used

`getBid` · `getAsk` · `getPoint` · `getDigits` · `getSpread` · `calculateVolume` · `buyMarket` · `getProfit` · `getPositionCount` · `getBalance` · `getEquity` · `getFreeMargin` · `closePosition`

---

## ⚠️ Notes

* **Direction is assumed** (BUY) — add your own signal before live use.
* **Spread sensitivity:** scalping only makes sense on tight spreads; check `getSpread(symbol)` before entering.
* **Session mode** opens trades sequentially, not concurrently (respects `maxPositions`).

---

## 📚 See also

* [Orchestrators — Overview](Strategies.Master.Overview.md)
* [Trend Following](TrendFollowingOrchestrator.md) · [Hedging](HedgingOrchestrator.md) · [Breakout](BreakoutOrchestrator.md) · [Martingale](MartingaleOrchestrator.md)
* [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md)
