# 🛡️ Defensive Preset

> **Multi-orchestrator preset (capital-preservation focus):** protection first (Hedging), then two conservative Scalping trades, then a Martingale phase that runs **only if the session is already profitable**.

**API Information:**

* **Class:** `presets.DefensivePreset`
* **Constructor:** `new DefensivePreset(MT5Sugar sugar)`
* **Configure:** `setSymbol(String)` *(optional, default `"EURUSD"`)*
* **Run:** `execute()` — `throws ApiExceptionMT5, InterruptedException`
* **Command:** `run.bat 11 2`

---

## ▶️ How to run

```bash
run.bat 11 2        # Windows CMD   (or: .\run.bat 11 2 in PowerShell)
```

Programmatically (an `MT5Sugar sugar` is already connected):

```java
DefensivePreset preset = new DefensivePreset(sugar);
preset.setSymbol("EURUSD");   // optional
preset.execute();
```

---

## 🔁 What it does

Protection-first: it starts with a hedge, adds conservative scalps, and only risks a Martingale phase once it is already ahead.

### Phase 1 — Hedging (protection first)
Runs `HedgingOrchestrator` to open a position with immediate hedge protection.

| Param | Value |
|-------|-------|
| Risk | `$20` (low exposure) |
| Stop Loss | `100` points |
| Take Profit | `150` points |
| Hedge trigger | `30` points |

### Phase 2 — Scalping ×2 (conservative growth)
Runs `ScalpingOrchestrator` **twice** with small risk and tight stops for conservative wins.

| Param | Value |
|-------|-------|
| Risk | `$15` per trade (×2) |
| Trades | 2 sequential |

### Phase 3 — Martingale (conditional)
Runs `MartingaleOrchestrator` **only if the running session profit is `> $10`** — i.e. it risks a progression phase only from a position of strength. If the session isn't profitable enough, this phase is skipped.

### Final report
Prints P/L per phase and the total session result.

---

## ⚠️ Risk

| | |
|---|---|
| **Maximum exposure** | ≈ `$70` total (lower than Aggressive Growth) |
| **Minimum account** | `$2000` |
| **Style** | Conservative — protection first, conditional final phase |

> Even defensive strategies carry risk: multiple orchestrators mean several exposure points, hedging reduces but doesn't eliminate risk, and market gaps can exceed stop-loss protection. Demo only.

---

## 📊 Example (demo run)

```
[2/2] DEFENSIVE PRESET
  PHASE 1: HEDGING (protection)
  PHASE 2: SCALPING ×2            → Scalping (2 trades) result ~ +13.16
  PHASE 3: CONDITIONAL MARTINGALE
    >> DECISION: total profit $14.56 → Martingale runs
  >> DEFENSIVE SESSION COMPLETED  → TOTAL P/L ~ +14.53
```

The conditional branch is real: Martingale only launched because the session was already up over `$10`.

---

## See also

* **All strategies:** [Strategies — Master Overview](Strategies.Master.Overview.md)
* **Other preset:** [Aggressive Growth Preset](AggressiveGrowthPreset.md)
* **Building blocks:** [Hedging](HedgingOrchestrator.md) · [Scalping](ScalpingOrchestrator.md) · [Martingale](MartingaleOrchestrator.md)
* **High-level API it uses:** [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md)
