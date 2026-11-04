# Trend Following Orchestrator

> Opens a position in the trend direction with a wide SL/TP, then activates a trailing stop that locks the trade at breakeven once it is far enough in profit.

!!! warning "Educational / demo only"
    This orchestrator shows the strategy idea and the MT5Sugar calls behind it — it is a template, not a production trading system. Trend direction is **simplified** (it assumes an uptrend → BUY). Study the code, test on a **demo account**, and add real analysis/risk controls before anything live.

* **Source:** [`src/main/java/orchestrators/TrendFollowingOrchestrator.java`](https://github.com/MetaRPC/JavaMT5/blob/main/src/main/java/orchestrators/TrendFollowingOrchestrator.java)
* **Run:** `run.bat 10 2` (or the menu: `run.bat 10` → `[2]`)
* **Risk level:** Medium

---

## 🧭 What it does

* Opens a market position in the trend direction (simplified: uptrend → **BUY**).
* Sizes the position from a fixed **risk amount** and the stop distance.
* Uses a **wide SL/TP** (80 / 160 points by default → 1:2 reward/risk) to let a trend run.
* Monitors profit and, once it reaches `trailingStopPoints`, **moves the stop-loss to breakeven** (+10-point buffer) to protect the trade.
* Closes the position and reports the final balance.

**Best for:** strong trending markets, higher timeframes (H1/H4/D1), swing-style entries.

---

## ⚙️ How it works

1. **Market analysis** — reads `bid` / `ask` / `point` / `spread` for the symbol.
2. **Entry** — computes volume with `calculateVolume(symbol, stopLossPoints, riskAmount)`, sets `SL = ask − stopLossPoints·point`, `TP = ask + takeProfitPoints·point`, and opens the trade with `buyMarket(symbol, volume, sl, tp, comment)`.
3. **Trailing monitor** — polls profit; when profit in points reaches `trailingStopPoints`, it calls `modifyPosition(ticket, newSL, tp)` once to move SL to breakeven + 10 points (`trailingActivated` guards against repeating).
4. **Exit** — `closePosition(ticket)` and prints the resulting balance.

---

## 🔧 Constructor & configuration

```java
TrendFollowingOrchestrator(MT5Sugar sugar)
```

| Setter | Type | Default | Meaning |
|--------|------|---------|---------|
| `setSymbol(String)` | `String` | `"EURUSD"` | Symbol to trade. |
| `setRiskAmount(double)` | `double` | `50.0` | Risk per trade in account currency; drives volume sizing. |
| `setStopLossPoints(double)` | `double` | `80.0` | Stop-loss distance in points (wide, for trend capture). |
| `setTakeProfitPoints(double)` | `double` | `160.0` | Take-profit distance in points (1:2 vs the SL). |
| `setTrailingStopPoints(double)` | `double` | `40.0` | Profit in points that activates the move-to-breakeven trailing stop. |
| `setComment(String)` | `String` | `"Trend"` | Order comment / tag. |

```java
void execute() throws ApiExceptionMT5, InterruptedException
```

---

## 🔗 Example

```java
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.MT5Service;
import pro.mrpc.mt5.MT5Sugar;
import orchestrators.TrendFollowingOrchestrator;

// A connected MT5Sugar (see Getting Started for the full setup)
MT5Account account = new MT5Account(login, password, grpcServer, apiKey);
account.connectByServerName("MetaQuotes-Demo", "EURUSD");
MT5Sugar sugar = new MT5Sugar(new MT5Service(account));

TrendFollowingOrchestrator trend = new TrendFollowingOrchestrator(sugar);
trend.setSymbol("EURUSD");
trend.setRiskAmount(50.0);          // $50 risk per trade
trend.setStopLossPoints(80.0);      // wide SL
trend.setTakeProfitPoints(160.0);   // 1:2 reward/risk
trend.setTrailingStopPoints(40.0);  // move SL to breakeven at +40 pts
trend.execute();

account.disconnect();
```

---

## 🧩 MT5Sugar methods used

`getBid` · `getAsk` · `getPoint` · `getSpread` · `calculateVolume` · `buyMarket` · `getProfit` · `normalizePrice` · `modifyPosition` · `closePosition` · `getBalance`

---

## ⚠️ Notes

* **Direction is assumed, not detected.** The demo always buys (uptrend assumption) — add your own trend filter before using it for anything real.
* **Trailing moves only to breakeven here** (entry + 10 points), once — it is a protective step, not a continuous trailing loop. Extend `execute()` for a full trailing ladder.
* **Monitoring is a short demo loop** (3 checks); replace it with your own duration/streaming logic for live use.

---

## 📚 See also

* [Orchestrators — Overview](Strategies.Master.Overview.md)
* [Scalping](ScalpingOrchestrator.md) · [Hedging](HedgingOrchestrator.md) · [Breakout](BreakoutOrchestrator.md) · [Martingale](MartingaleOrchestrator.md)
* [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md)
