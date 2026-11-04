# Strategies — Master Overview

> One page to orient fast: which **orchestrators** and **presets** ship with JavaMT5, how to choose one, how to run it, and links to every strategy page.

---

!!! warning "Educational & demonstration purpose only"
    Every orchestrator and preset here is a **demonstration example** that showcases the JavaMT5 API and serves as a template for building your own strategies. They are **not** production-ready trading systems: market analysis is simplified, risk controls are minimal, and execution logic is basic.

    **Use demo accounts only.** Before anything live: understand every line, backtest, forward-test on demo for a long time, add real risk management, and add monitoring. Trading involves substantial risk of loss; you are responsible for your own decisions.

---

## The orchestrators

Single-strategy building blocks in [`src/main/java/orchestrators/`](../All_Guides/RUNNING_EXAMPLES.md), launched from `Program.java` (`run.bat 10 <n>`). Each is configured with setters, then `execute()`.

| # | Strategy | What it does | Best for | Run |
| --- | --- | --- | --- | --- |
| 10 1 | **[Scalping](ScalpingOrchestrator.md)** | Quick in/out with tight SL/TP; one risk-sized trade. | Fast market tests | `run.bat 10 1` |
| 10 2 | **[Trend Following](TrendFollowingOrchestrator.md)** | Rides momentum with a trailing stop and wider TP. | Letting a winner run | `run.bat 10 2` |
| 10 3 | **[Hedging](HedgingOrchestrator.md)** | Opens an opposite position when loss hits a trigger. | Protecting an open trade | `run.bat 10 3` |
| 10 4 | **[Breakout](BreakoutOrchestrator.md)** | Pending BUY/SELL STOP both sides; trades whichever fires. | Range breakouts | `run.bat 10 4` |
| 10 5 | **[Martingale](MartingaleOrchestrator.md)** ⚠️ | Doubles volume after a loss (high risk — demo only). | Studying progression risk | `run.bat 10 5` |

> `run.bat 10` with no sub-choice shows the menu; `run.bat 10 0` runs all five in sequence.

## The presets

Multi-orchestrator sessions in [`src/main/java/presets/`](../All_Guides/RUNNING_EXAMPLES.md) that **chain orchestrators with adaptive/conditional logic** (`run.bat 11 <n>`).

| # | Preset | What it does | Max risk | Run |
| --- | --- | --- | --- | --- |
| 11 1 | **[Aggressive Growth](AggressiveGrowthPreset.md)** | Scalping warm-up → adaptive Trend **or** Hedging → Breakout. | ~$110 (min account $5000) | `run.bat 11 1` |
| 11 2 | **[Defensive](DefensivePreset.md)** | Hedging first → Scalping ×2 → conditional Martingale. | ~$70 (min account $2000) | `run.bat 11 2` |

> `run.bat 11` shows the menu; `run.bat 11 0` runs both presets and prints a P/L comparison.

## Pick one by goal

- **Test the market quickly** → [Scalping](ScalpingOrchestrator.md)
- **Ride a move with a trailing stop** → [Trend Following](TrendFollowingOrchestrator.md)
- **Protect an open position** → [Hedging](HedgingOrchestrator.md)
- **Catch a breakout either way** → [Breakout](BreakoutOrchestrator.md)
- **A full adaptive session** → [Aggressive Growth](AggressiveGrowthPreset.md)
- **Capital-preservation session** → [Defensive](DefensivePreset.md)

---

## How a strategy fits the SDK

Orchestrators and presets are plain Java classes on top of the three-layer API — they drive `MT5Sugar`:

```
PRESETS        (src/main/java/presets/)      Aggressive Growth · Defensive
  │  chain orchestrators, adaptive/conditional decisions
  ▼
ORCHESTRATORS  (src/main/java/orchestrators/) Scalping · Trend · Hedging · Breakout · Martingale
  │  size by risk, open, monitor, close
  ▼
MT5Sugar      high-level helpers  (buyMarket, buyByRisk, modifyPosition, closeAll, …)
  ▼
MT5Service    typed facade        (orderSend, getBalance, symbolInfoTick, …)
  ▼
MT5Account    low-level gRPC
```

A **preset** simply instantiates several **orchestrators** and runs them in phases, deciding the next phase from the running P/L.

## Common run pattern

The quickest way to try any of them is `run.bat` (use `.\run.bat` in PowerShell):

```bash
run.bat 10 1    # one orchestrator (Scalping)
run.bat 11 1    # one preset (Aggressive Growth)
run.bat 10 0    # all orchestrators   ·   run.bat 11 0 = both presets
```

Programmatically (an `MT5Sugar sugar` is already connected):

```java
// Orchestrator — configure with setters, then execute()
ScalpingOrchestrator scalper = new ScalpingOrchestrator(sugar);
scalper.setSymbol("EURUSD");
scalper.setRiskAmount(20.0);
scalper.setStopLossPoints(8);
scalper.setTakeProfitPoints(15);
scalper.execute();

// Preset — just set the symbol and run; it configures its orchestrators itself
AggressiveGrowthPreset preset = new AggressiveGrowthPreset(sugar);
preset.setSymbol("EURUSD");
preset.execute();
```

## Shared behavior

- **Demo-safe:** each strategy opens its own small test position(s) and closes them within the session.
- **Risk-sized:** entries use `sugar.calculateVolume(...)` / `buyByRisk(...)` so position size follows the `$risk` + SL points you set.
- **Points, not pips:** all distances are in points (on 5-digit symbols 1 pip = 10 points).
- **Cleanup:** when launched via `run.bat 10/11`, `Program.java` disconnects and closes the gRPC channel in `finally`.
- **Presets continue on failure:** if one phase throws, the preset logs it and moves on, tracking P/L per phase.

---

## Related documentation

- [MT5Account — Master Overview](../MT5Account/MT5Account.Master.Overview.md) — low-level gRPC API
- [MT5Service — Overview](../MT5Service/MT5Service.Overview.md) — typed wrapper methods
- [MT5Sugar — Overview](../MT5Sugar/MT5Sugar.Overview.md) — high-level helpers the strategies use
- [gRPC Channel Management](../All_Guides/GRPC_CHANNEL_AND_STREAM_MANAGEMENT_EN.md) — streams and cleanup
- [User Code Sandbox](../All_Guides/USERCODE_SANDBOX_GUIDE.md) — write your own strategy

## Quick jump

**Orchestrators:** [Scalping](ScalpingOrchestrator.md) · [Trend Following](TrendFollowingOrchestrator.md) · [Hedging](HedgingOrchestrator.md) · [Breakout](BreakoutOrchestrator.md) · [Martingale](MartingaleOrchestrator.md)

**Presets:** [Aggressive Growth](AggressiveGrowthPreset.md) · [Defensive](DefensivePreset.md)
