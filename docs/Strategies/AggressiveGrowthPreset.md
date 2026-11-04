# 🎼 Aggressive Growth Preset

> **Multi-orchestrator preset:** chains three orchestrators into one adaptive session — a scalping warm-up, an adaptive Trend-or-Hedge phase, and a final Breakout — then prints a performance report.

**API Information:**

* **Class:** `presets.AggressiveGrowthPreset`
* **Constructor:** `new AggressiveGrowthPreset(MT5Sugar sugar)`
* **Configure:** `setSymbol(String)` *(optional, default `"EURUSD"`)*
* **Run:** `execute()` — `throws ApiExceptionMT5, InterruptedException`
* **Command:** `run.bat 11 1`

---

## ▶️ How to run

```bash
run.bat 11 1        # Windows CMD   (or: .\run.bat 11 1 in PowerShell)
```

Programmatically (an `MT5Sugar sugar` is already connected):

```java
AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
preset.setSymbol("EURUSD");   // optional
preset.execute();
```

---

## 🔁 What it does

The preset runs **three phases** on one symbol, deciding Phase 2 from the Phase 1 result, then reports.

### Phase 1 — Scalping (market test)
Runs `ScalpingOrchestrator` as a quick warm-up to gauge conditions.

| Param | Value |
|-------|-------|
| Risk | `$20` |
| Stop Loss | `10` points |
| Take Profit | `20` points |

### Phase 2 — Adaptive decision
Looks at the Phase 1 profit and picks the next orchestrator:

* **If scalping profit `> $5`** → `TrendFollowingOrchestrator` (momentum detected)
  * Risk `$50`, SL `60` / TP `120` points, trailing stop `30` points
* **Otherwise** → `HedgingOrchestrator` (choppy market → protect capital)
  * Risk `$30`, SL `80` / TP `120` points, hedge trigger `40` points

### Phase 3 — Breakout (final entry)
Runs `BreakoutOrchestrator` with pending orders either side of price.

| Param | Value |
|-------|-------|
| Risk | `$40` |
| Breakout distance | `25` points |
| Stop Loss | `40` points |
| Take Profit | `80` points |

### Phase 4 — Performance report
Prints P/L per phase, total session P/L, and how many of the 3 orchestrators were profitable.

---

## ⚠️ Risk

| | |
|---|---|
| **Maximum exposure** | `$110` total (`$20` + `$50`/`$30` + `$40`) |
| **Worst case** | all three hit SL → about `-$110` |
| **Minimum account** | `$5000` (≈2.2% max drawdown) |
| **Recommended** | `$10000+` (≈1.1% max drawdown) |

> ⚠️ Aggressive: multiple strategies in one session mean higher total exposure and more commission. Demo only; do not run several preset sessions at once.

---

## 📊 Example (demo run)

```
[1/2] AGGRESSIVE GROWTH PRESET
  Starting balance: $100000.00
  PHASE 1: SCALPING ORCHESTRATOR (Market Test)   → result ~ -6.00
  PHASE 2: ADAPTIVE DECISION
    >> Scalping unprofitable → launching HEDGING to protect capital
  PHASE 3: BREAKOUT ORCHESTRATOR (Final Entry)
  >> AGGRESSIVE GROWTH SESSION COMPLETED
```

The adaptive branch is real: a losing warm-up routes Phase 2 into Hedging rather than Trend Following.

---

## See also

* **All strategies:** [Strategies — Master Overview](Strategies.Master.Overview.md)
* **Other preset:** [Defensive Preset](DefensivePreset.md)
* **Building blocks:** [Scalping](ScalpingOrchestrator.md) · [Trend Following](TrendFollowingOrchestrator.md) · [Hedging](HedgingOrchestrator.md) · [Breakout](BreakoutOrchestrator.md)
* **High-level API it uses:** [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md)
