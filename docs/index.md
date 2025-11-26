# JavaMT5 SDK Documentation

> Complete Java SDK for MetaTrader 5 trading automation via gRPC

---

## 🚀 Quick Start

```java
// 1. Initialize connection
MT5Sugar sugar = new MT5Sugar("localhost:5777");

// 2. Place a market order with risk management
long ticket = sugar.buyByRisk("EURUSD", 2.0, 50.0, 100.0);

// 3. Monitor and manage
sugar.modifyPosition(ticket, newSL, newTP);
sugar.closePosition(ticket);
```

---

<div class="home-grid">

<!-- Top (1) - Bright orange -->
<a href="GETTING_STARTED/" class="card orange-1">
  <span class="material-symbols-rounded">bolt</span>
  <h3>Getting Started</h3>
  <p>Setup guide, installation, and your first trading bot in 5 minutes.</p>
</a>

<!-- Middle Row (2-3) - Medium orange -->
<a href="MT5Account/MT5Account.Master.Overview/" class="card orange-2">
  <span class="material-symbols-rounded">settings</span>
  <h3>MT5Account</h3>
  <p>Low-level gRPC protocol layer. Direct proto access for maximum control.</p>
</a>

<a href="MT5Sugar/MT5Sugar.Overview/" class="card orange-2">
  <span class="material-symbols-rounded">auto_awesome</span>
  <h3>MT5Sugar</h3>
  <p>High-level convenience API with auto-normalization and risk management.</p>
</a>

<!-- Bottom Row (4-5-6) - Dark orange -->
<a href="GLOSSARY/" class="card orange-3">
  <span class="material-symbols-rounded">menu_book</span>
  <h3>Glossary</h3>
  <p>Complete reference of MT5 terms, order types, and API concepts.</p>
</a>

<a href="Orchestrators.Overview/" class="card orange-3">
  <span class="material-symbols-rounded">science</span>
  <h3>Orchestrators</h3>
  <p>Pre-built trading strategies: trend following, scalping, breakout, and more.</p>
</a>

<a href="PROJECT_MAP/" class="card orange-3">
  <span class="material-symbols-rounded">map</span>
  <h3>Project Map</h3>
  <p>Complete architecture overview and component relationships.</p>
</a>

</div>

---

## 🎯 Architecture Layers

```
┌─────────────────────────────────────────┐
│  Your Strategy (Orchestrators/Presets)  │
├─────────────────────────────────────────┤
│  MT5Sugar    ← Start Here (95% cases)   │
│  • Risk management                      │
│  • Auto-normalization                   │
│  • Batch operations                     │
├─────────────────────────────────────────┤
│  MT5Service  ← Direct data access       │
│  • Type conversions                     │
│  • Simplified signatures                │
├─────────────────────────────────────────┤
│  MT5Account  ← Full gRPC control        │
│  • Raw proto objects                    │
│  • Connection management                │
└─────────────────────────────────────────┘
         ↓ gRPC
┌─────────────────────────────────────────┐
│       MetaTrader 5 Terminal             │
└─────────────────────────────────────────┘
```

---

## 📚 Key Features

- **Three-tier API** - Choose your level of control (Sugar → Service → Account)
- **Risk Management** - Built-in position sizing and risk calculations
- **Real-time Streams** - Live tick data, trade events, position updates
- **Batch Operations** - Close all, cancel all, mass modifications
- **Type Safety** - Full Java type system with compile-time checks
- **Auto-normalization** - Volumes, prices, and lots handled automatically
- **gRPC Protocol** - High-performance binary communication
- **Comprehensive Docs** - Every method documented with examples

---

## 🛠️ Build & Run

```bash
# Recommended (fast daemon mode)
.\run.bat 9

# Clean build (if errors occur)
.\run-clean.bat 9

# Maven direct
mvnd compile exec:java -Dexec.args="9"
```

**Examples:**
- `1-3`: Low-level (MT5Account)
- `4-6`: Service layer (MT5Service)
- `7-9`: Sugar API (MT5Sugar)
- `10`: Orchestrators
- `11`: Presets

---

## 📖 Documentation Structure

- **[MT5Account](MT5Account/MT5Account.Master.Overview/)** - Low-level gRPC API
  - Account Information
  - Symbol Information
  - Positions & Orders
  - Market Depth (DOM)
  - Trading Operations
  - Real-time Subscriptions

- **[MT5Sugar](MT5Sugar/MT5Sugar.Overview/)** - High-level convenience API
  - Symbol Helpers
  - Market Orders
  - Pending Orders
  - Position Management
  - Risk Management
  - Batch Operations

- **[Orchestrators](Orchestrators.Overview/)** - Pre-built strategies
  - Trend Following
  - Scalping
  - Hedging
  - Breakout
  - Martingale

---

## 🔗 Quick Links

- **GitHub**: [MetaRPC/JavaMT5](https://github.com/MetaRPC/JavaMT5)
- **Issues**: [Report bugs](https://github.com/MetaRPC/JavaMT5/issues)
- **MT5 Terminal**: [Download](https://www.metatrader5.com/)

---

## ⚠️ Disclaimer

MetaTrader 5 is a trademark of MetaQuotes Software Corp. This SDK is community-maintained and not affiliated with MetaQuotes. Trading involves risk. Test thoroughly on demo accounts before live trading.
