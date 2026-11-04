# Martingale Orchestrator ⚠️

> Doubles the trade volume after every loss and resets to the base volume after a win — so a single win recovers the prior losing streak. **Extreme risk.**

!!! danger "DEMO ACCOUNTS ONLY — extreme risk"
    Martingale grows exposure **exponentially**: a losing streak multiplies volume (0.01 → 0.02 → 0.04 → 0.08 → …) and can wipe an account or hit a margin call fast. This is an **educational** example of the mechanics only. Never run it on real money.

* **Source:** [`src/main/java/orchestrators/MartingaleOrchestrator.java`](https://github.com/MetaRPC/JavaMT5/blob/main/src/main/java/orchestrators/MartingaleOrchestrator.java)
* **Run:** `run.bat 10 5` (or the menu: `run.bat 10` → `[5]`)
* **Risk level:** VERY HIGH ⚠️

---

## 🧭 What it does

* Starts with `baseVolume` (e.g. 0.01 lots) and a symmetric SL/TP (20 / 20 points by default → 1:1).
* **Win** → reset volume to base.
* **Loss** → **double** the volume for the next trade.
* Repeats until a win or `maxTrades` is reached; the goal is for one win to recover the streak.

---

## ⚙️ How it works

1. **Trade** — `buyMarket(symbol, volume, sl, tp, comment)` with `volume` = base × 2^(consecutive losses), normalized via `normalizeVolume(...)`.
2. **Resolve** — watches `getProfit()` to classify the trade as win/loss after it closes (`closePosition`).
3. **Adjust** — on a loss, increment `consecutiveLosses` (next volume doubles); on a win, reset to base.
4. **Stop** — ends after a win or when `maxTrades` is hit; prints the running total.

---

## 🔧 Constructor & configuration

```java
MartingaleOrchestrator(MT5Sugar sugar)
```

| Setter | Type | Default | Meaning |
|--------|------|---------|---------|
| `setSymbol(String)` | `String` | `"EURUSD"` | Symbol to trade. |
| `setBaseVolume(double)` | `double` | `0.01` | Starting lot size (doubles after each loss). |
| `setStopLossPoints(double)` | `double` | `20.0` | Stop-loss distance in points. |
| `setTakeProfitPoints(double)` | `double` | `20.0` | Take-profit distance in points (1:1). |
| `setMaxTrades(int)` | `int` | `5` | Max trades in the doubling sequence (safety cap). |
| `setComment(String)` | `String` | `"Martingale"` | Order comment / tag. |

```java
void execute() throws ApiExceptionMT5, InterruptedException
```

---

## 🔗 Example

```java
MartingaleOrchestrator mart = new MartingaleOrchestrator(sugar);
mart.setSymbol("EURUSD");
mart.setBaseVolume(0.01);
mart.setStopLossPoints(20.0);
mart.setTakeProfitPoints(20.0);
mart.setMaxTrades(5);   // cap the doubling sequence
mart.execute();
```

**Volume progression (illustrative):**
```
Trade 1: 0.01 lots → Loss
Trade 2: 0.02 lots → Loss
Trade 3: 0.04 lots → Loss
Trade 4: 0.08 lots → WIN  → recovers the streak, reset to 0.01
```

---

## 🧩 MT5Sugar methods used

`getAsk` · `getPoint` · `getDigits` · `normalizeVolume` · `buyMarket` · `getProfit` · `closePosition` · `getBalance`

---

## ⚠️ Notes

* **`maxTrades` is the only brake.** Keep it small; each extra step doubles the required margin and risk.
* **A margin call ends the sequence**, not a win — on a real account a long streak blows up before the recovering win arrives.
* Symmetric 1:1 SL/TP means roughly even win odds minus spread — the strategy's "edge" is pure position sizing, which is why it fails over time.

---

## 📚 See also

* [Orchestrators — Overview](Strategies.Master.Overview.md)
* [Trend Following](TrendFollowingOrchestrator.md) · [Scalping](ScalpingOrchestrator.md) · [Hedging](HedgingOrchestrator.md) · [Breakout](BreakoutOrchestrator.md)
* [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md)
