# JavaMT5 Project Map

> Complete project structure guide. Shows what's where, what's user-facing vs internal, and how components connect.

---

## 🗺️ Project Overview

```
JavaMT5/
├── 📦 Core API (Internal - 3 layers)
├── 🎯 User Code (Orchestrators, Presets, Examples)
├── 📚 Documentation
├── ⚙️ Configuration & Build
└── 🔌 Proto Definitions
```

---

## 📦 Core API (Internal - src/main/java/io/metarpc/mt5/)

**What:** Three-tier architecture for MT5 trading automation.

**User interaction:** Import and use, but typically don't modify.

```
src/main/java/io/metarpc/mt5/
├── MT5Account.java         ← LAYER 1: Low-level proto/gRPC
│   └── Direct proto Request/Response objects
│   └── Connection management
│   └── Raw gRPC calls
│
├── MT5Service.java         ← LAYER 2: Wrapper methods
│   └── Direct data returns (no proto wrapping)
│   └── Type conversions
│   └── Simplified method signatures
│
└── MT5Sugar.java           ← LAYER 3: Convenience layer
    └── Auto-normalization (volumes, prices)
    └── Risk management (calculateVolume, buyByRisk)
    └── Batch operations (closeAll, cancelAll)
    └── Smart helpers (snapshots, conversions)

exceptions/
└── ApiExceptionMT5.java    ← Exception wrapper with MT5 error codes
```

**Architecture flow:**
```
MT5Sugar → uses → MT5Service → uses → MT5Account → gRPC → MT5 Terminal
```

**User decision:**
- **95% of cases:** Start with `MT5Sugar` (highest level, easiest)
- **Need wrappers:** Drop to `MT5Service` (no auto-normalization)
- **Need raw proto:** Drop to `MT5Account` (full control)

**Documentation:** [docs/MT5Sugar/MT5Sugar.Overview.md](./docs/MT5Sugar/MT5Sugar.Overview.md)

---

## 🎯 User Code (Your Trading Strategies)

### Orchestrators (src/main/java/orchestrators/)

**What:** Pre-built trading strategy implementations.

**User interaction:** ✅ **Start here!** Copy, modify, customize for your strategies.

```
orchestrators/
├── TrendFollowingOrchestrator.java    ← Trend following + trailing stops
├── ScalpingOrchestrator.java          ← Quick in/out, tight SL/TP
├── HedgingOrchestrator.java           ← Defensive hedging strategy
├── BreakoutOrchestrator.java          ← Bi-directional pending orders
└── MartingaleOrchestrator.java        ← Volume doubling ⚠️ HIGH RISK
```

**Purpose:** Educational examples showing complete strategy workflows:
- Entry logic (risk-based volume)
- Position monitoring
- Exit management
- Performance tracking

**How to use:**
1. Study existing orchestrators
2. Copy one as template
3. Modify for your strategy
4. Test on demo account

**Documentation:** [docs/Orchestrators.Overview.md](./docs/Orchestrators.Overview.md)

---

### Presets (src/main/java/presets/)

**What:** Multi-orchestrator combinations with adaptive logic.

**User interaction:** ✅ **Advanced usage** - combine multiple strategies.

```
presets/
├── AggressiveGrowthPreset.java    ← 3-4 orchestrators, adaptive
└── DefensivePreset.java           ← Conservative, protection-first
```

**Purpose:** Show how to:
- Chain multiple strategies
- Adaptive decision-making (if profit > X then...)
- Multi-phase trading sessions
- Performance tracking across phases

**Documentation:** [docs/Orchestrators.Overview.md](./docs/Orchestrators.Overview.md#-multi-orchestrator-presets)

---

### Examples (src/main/java/examples/)

**What:** Runnable examples demonstrating API usage.

**User interaction:** ✅ **Learning materials** - run to understand APIs.

```
examples/
├── lowlevel/                          ← MT5Account examples (proto level)
│   ├── MarketDataExample.java
│   ├── StreamingExample.java
│   └── TradingCalculationsExample.java
│
├── services/                          ← MT5Service examples (wrapper level)
│   ├── MarketDataServiceExample.java
│   ├── StreamingServiceExample.java
│   └── TradingServiceExample.java
│
├── sugar/                             ← MT5Sugar examples (convenience level)
│   ├── SimpleTradingScenario.java
│   ├── RiskManagementScenario.java
│   └── GridTradingScenario.java
│
├── orchestrators/
│   └── OrchestratorDemo.java         ← Interactive menu to run orchestrators
│
└── presets/
    └── PresetDemo.java                ← Interactive menu to run presets
```

**How to run:**
```bash
run.bat <example_number>
```

**Purpose:**
- Learn API usage patterns
- See working code examples
- Interactive demos for orchestrators/presets
- Copy-paste starting points

---

## 📚 Documentation (docs/)

**What:** Complete API and strategy documentation.

**User interaction:** 📖 **Read first!** Comprehensive reference.

```
docs/
├── MT5Sugar/                          ← 50+ convenience methods
│   ├── MT5Sugar.Overview.md           ← ⭐ START HERE
│   ├── 1. Symbol_helpers/             ← 12 methods (getPoint, getBid, etc.)
│   ├── 2. Market_orders/              ← 2 methods (buyMarket, sellMarket)
│   ├── 3. Pending_orders/             ← 4 methods (buyLimit, sellLimit, etc.)
│   ├── 4. Pending_orders_points/      ← 4 methods (offset-based)
│   ├── 5. Position_management/        ← 5 methods (close, modify, etc.)
│   ├── 6. Advanced_batch_operations/  ← 3 methods (batch close/cancel)
│   ├── 7. Risk_management/            ← 3 methods (calculateVolume, etc.)
│   ├── 8. Advanced_helpers/           ← 4 methods (limits, conversions)
│   ├── 9. Account_and_position_helpers/ ← 10 methods (balance, equity, etc.)
│   ├── 10. Snapshot_helpers/          ← 2 methods (full snapshots)
│   └── 11. History_helpers/           ← 2 methods (history queries)
│
├── MT5Account/                        ← Low-level proto documentation
│   ├── 1. Account_information/
│   ├── 2. Symbol_information/
│   ├── 3. Positions_and_orders/
│   ├── 4. Market_depth_DOM/
│   ├── 5. Trading/
│   └── 6. Subscriptions/
│
└── Orchestrators.Overview.md          ← ⭐ Strategies guide
    └── Includes: 5 orchestrators + 2 presets
```

**Structure:**
- Each method has its own `.md` file with examples
- Overview files provide quick navigation
- Links between related methods
- Usage examples in every file

---

## ⚙️ Configuration & Build

**What:** Project configuration and build tools.

**User interaction:** ⚙️ **Configure once** - mostly read-only after setup.

```
./
├── pom.xml                    ← Maven build configuration
│   └── Dependencies, plugins, build settings
│   └── Proto compilation setup
│   └── Maven Daemon (mvnd) integration
│
├── appsettings.json           ← MT5 connection settings ⭐ EDIT THIS
│   └── Host, port, login, password
│   └── Symbol, SSL, timeout
│   └── Use for all examples
│
├── run.bat                    ← Quick launcher for examples
│   └── run.bat <number>       ← Launch specific example
│   └── Handles Maven Daemon
│
├── .vscode/                   ← VS Code settings
│   └── settings.json
│
└── .gitignore                 ← Git ignore rules
```

**Key file - appsettings.json:**
```json
{
  "MT5": {
    "Host": "localhost",
    "Port": 5555,
    "Login": 12345678,
    "Password": "yourpassword",
    "Symbol": "EURUSD",
    "UseSSL": true,
    "TimeoutSeconds": 30
  }
}
```

---

## 🔌 Proto Definitions (src/main/proto/)

**What:** Protocol Buffer definitions for MT5 terminal communication.

**User interaction:** 📋 **Reference only** - typically don't modify.

```
src/main/proto/
├── mt5-term-api-account-helper.proto         ← Account helpers
├── mt5-term-api-account-information.proto    ← Account info
├── mt5-term-api-charts.proto                 ← Chart data
├── mt5-term-api-connection.proto             ← Connection management
├── mt5-term-api-market-info.proto            ← Market information
├── mt5-term-api-subscriptions.proto          ← Real-time subscriptions
├── mt5-term-api-trade-functions.proto        ← Trading operations
├── mt5-term-api-trading-helper.proto         ← Trading helpers
└── mt5/
    └── mrpc-mt5-error.proto                  ← Error definitions
```

**Purpose:**
- Define gRPC service contracts
- Compiled into Java classes (auto-generated)
- Used by MT5Account layer
- Checked into project for reference

**Compilation:** Handled automatically by Maven during build.

---

## 🎯 Quick Start Paths

### Path 1: Learn the API (Beginner)

```
1. Read: docs/MT5Sugar/MT5Sugar.Overview.md
2. Configure: appsettings.json
3. Run: run.bat 3 (SimpleTradingScenario)
4. Study: src/main/java/examples/sugar/
5. Try: Modify SimpleTradingScenario.java
```

### Path 2: Build a Strategy (Intermediate)

```
1. Read: docs/Orchestrators.Overview.md
2. Run: run.bat 10 (OrchestratorDemo)
3. Study: src/main/java/orchestrators/ScalpingOrchestrator.java
4. Copy: Create MyOrchestrator.java based on Scalping
5. Customize: Add your entry/exit logic
6. Test: On demo account
```

### Path 3: Multi-Strategy System (Advanced)

```
1. Study: All single orchestrators first
2. Read: docs/Orchestrators.Overview.md (Presets section)
3. Run: run.bat 11 (PresetDemo)
4. Study: src/main/java/presets/DefensivePreset.java
5. Design: Your multi-phase strategy
6. Build: Combine orchestrators with logic
7. Test: Extensively on demo
```

### Path 4: Low-Level Integration (Expert)

```
1. Read: docs/MT5Account/ documentation
2. Study: src/main/proto/ proto definitions
3. Study: src/main/java/io/metarpc/mt5/MT5Account.java
4. Use: When MT5Sugar doesn't fit your needs
5. Build: Custom proto-level integrations
```

---

## 📊 Component Interaction Diagram

```
┌─────────────────────────────────────────────────────┐
│  YOUR CODE (User-facing)                            │
│  ├─ Orchestrators (strategy implementations)        │
│  ├─ Presets (multi-strategy combinations)           │
│  └─ Examples (learning materials)                   │
└─────────────────┬───────────────────────────────────┘
                  │ uses
                  ↓
┌─────────────────────────────────────────────────────┐
│  MT5Sugar (Layer 3 - Convenience)                   │
│  ├─ Auto-normalization                              │
│  ├─ Risk management                                 │
│  └─ Batch operations                                │
└─────────────────┬───────────────────────────────────┘
                  │ uses
                  ↓
┌─────────────────────────────────────────────────────┐
│  MT5Service (Layer 2 - Wrappers)                    │
│  ├─ Direct data returns                             │
│  ├─ Type conversions                                │
│  └─ Simplified signatures                           │
└─────────────────┬───────────────────────────────────┘
                  │ uses
                  ↓
┌─────────────────────────────────────────────────────┐
│  MT5Account (Layer 1 - Low-level)                   │
│  ├─ Proto Request/Response                          │
│  ├─ gRPC communication                              │
│  └─ Connection management                           │
└─────────────────┬───────────────────────────────────┘
                  │ gRPC
                  ↓
┌─────────────────────────────────────────────────────┐
│  MT5 Terminal (External)                            │
│  └─ MetaTrader 5 with gRPC server                   │
└─────────────────────────────────────────────────────┘
```

---

## 🔍 File Naming Conventions

### Core API
- `MT5*.java` — Core API classes (Account, Service, Sugar)
- `*Exception.java` — Exception types

### User Code
- `*Orchestrator.java` — Single-strategy implementations
- `*Preset.java` — Multi-strategy combinations
- `*Example.java` / `*Demo.java` — Runnable examples
- `*Scenario.java` — Complex usage scenarios

### Documentation
- `*.Overview.md` — Category overview with all methods
- `methodName.md` — Individual method documentation

### Proto
- `mt5-term-api-*.proto` — MT5 terminal API definitions
- `mrpc-*.proto` — MetaRPC error definitions

---

## 📂 What to Modify vs What to Leave Alone

### ✅ MODIFY (User Code)

```
orchestrators/           ← Copy and customize for your strategies
presets/                 ← Create your own multi-strategy systems
examples/                ← Add your own examples
appsettings.json         ← Configure for your MT5 terminal
```

### 📖 READ (Core API)

```
io/metarpc/mt5/         ← Use but don't modify (import and call)
docs/                   ← Reference documentation
```

### 🔒 LEAVE ALONE (Generated/Internal)

```
src/main/proto/         ← Proto definitions (reference only)
target/                 ← Compiled classes (auto-generated)
.git/                   ← Git internals
```

---

## 📞 Need Help?

- **API documentation:** Check `docs/MT5Sugar/` or `docs/MT5Account/`
- **Strategy examples:** Check `orchestrators/` source code
- **Usage examples:** Check `examples/` directory
- **Quick reference:** This PROJECT_MAP.md file

---

## 🎯 Project Philosophy

**Goal:** Make MT5 trading automation accessible through progressive complexity.

**Three-tier design:**
1. **Low-level (MT5Account):** Full control, proto/gRPC
2. **Wrapper (MT5Service):** Simplified method calls
3. **Convenience (MT5Sugar):** Auto-everything, batteries included

**User code:**
- **Orchestrators:** Pre-built strategy templates
- **Presets:** Multi-strategy combinations
- **Examples:** Learning materials

**Start high (MT5Sugar), drop down only when needed.**

> 💡 **Remember:** This is an educational project. All orchestrators and presets are examples, not production-ready systems. Always test on demo accounts and understand the code before using real money.
