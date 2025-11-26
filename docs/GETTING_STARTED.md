# Getting Started with JavaMT5

> **Welcome to JavaMT5** - a comprehensive educational project for learning MT5 trading automation from the ground up. This guide will show you the learning path and what opportunities this project opens for you.

---

## 🎯 What is JavaMT5?

JavaMT5 is an **educational project** designed specifically to teach you how to work with MetaTrader 5 terminal at all levels - from low-level protocol communication to high-level trading strategies.

This project was initially created to **learn low-level methods** of MT5 terminal communication. Everything else - convenience layers, orchestrators, presets - was built along the way to make this knowledge more accessible and practical.

---

## 🎓 What You'll Learn

### 1. Low-Level Protocol Communication (Foundation)

**The core foundation of everything** - direct communication with MT5 terminal via gRPC protocol.

**You'll learn:**
- How MT5 terminal communicates via Protocol Buffers (protobuf)
- How to send/receive proto Request/Response objects
- Direct gRPC calls for all MT5 operations
- Connection management and error handling
- Raw access to every MT5 function

**API:** `MT5Account` (Layer 1)

**Documentation:** [docs/MT5Account/](./MT5Account/)

**Why this matters:** Understanding the low-level foundation gives you complete control and deep knowledge of how everything works under the hood.

---

### 2. Wrapper Layer (Simplification)

**Built on top of MT5Account** - simplified method signatures without proto complexity.

**You'll learn:**
- How to wrap proto objects into simple method calls
- Type conversions (proto → Java primitives)
- Simplified API design patterns
- Direct data returns without proto wrapping

**API:** `MT5Service` (Layer 2)

**Why this matters:** Shows how to build convenient APIs on top of complex protocols.

---

### 3. Convenience Layer (Sugar Methods)

**Built on top of MT5Service** - ~50 ready-to-use methods with smart features.

**You'll learn:**
- Auto-normalization of volumes and prices
- Risk-based position sizing (calculate volume from $ risk)
- Batch operations (close all positions, cancel all orders)
- Smart helpers (snapshots, conversions, limits)
- High-level API design for common use cases

**API:** `MT5Sugar` (Layer 3)

**Documentation:** [docs/MT5Sugar/MT5Sugar.Overview.md](./MT5Sugar/MT5Sugar.Overview.md)

**Why this matters:** Shows how to build production-ready convenience APIs that handle edge cases automatically.

---

### 4. Trading Strategy Implementation (Orchestrators)

**Built using MT5Sugar** - complete trading strategy workflows.

**You'll learn:**
- How to implement real trading strategies in code
- Risk management and position sizing
- Position monitoring and management
- Trailing stops and breakeven logic
- Entry/exit automation
- Performance tracking

**Strategies included:**
- **Trend Following** - capture trending moves with trailing stops
- **Scalping** - quick in/out with tight SL/TP
- **Hedging** - defensive position protection
- **Breakout** - bi-directional pending orders
- **Martingale** - volume doubling (⚠️ demo only)

**Location:** `src/main/java/orchestrators/`

**Documentation:** [docs/Orchestrators.Overview.md](./Orchestrators.Overview.md)

**Why this matters:** Real-world strategy implementation patterns you can adapt for your own trading ideas.

---

### 5. Multi-Strategy Systems (Presets)

**Combine multiple orchestrators** - adaptive trading systems with conditional logic.

**You'll learn:**
- How to combine multiple strategies
- Adaptive decision-making (if profit > X then...)
- Multi-phase trading sessions
- Strategy orchestration patterns
- Performance tracking across phases

**Presets included:**
- **Aggressive Growth** - 3-4 orchestrators with adaptive logic
- **Defensive** - conservative protection-first approach

**Location:** `src/main/java/presets/`

**Documentation:** [docs/Orchestrators.Overview.md](./Orchestrators.Overview.md#-multi-orchestrator-presets)

**Why this matters:** Advanced strategy composition - how to build complex trading systems from simple building blocks.

---

## 🗺️ The Learning Path

### Foundation: MT5Account (Low-Level) 📦

**START HERE if you want to understand how everything works.**

```
MT5 Terminal ←→ gRPC ←→ Proto Messages ←→ MT5Account.java
```

**What you'll do:**
1. Read [docs/MT5Account/](./MT5Account/) documentation
2. Study proto files in `src/main/proto/`
3. Run examples in `src/main/java/examples/lowlevel/`
4. Explore `MT5Account.java` source code

**Key examples:**
- `MarketDataExample.java` - Get quotes, symbol info, account data
- `StreamingExample.java` - Real-time price subscriptions
- `TradingCalculationsExample.java` - Margin, profit calculations

**You'll understand:**
- ✅ How proto messages structure trading operations
- ✅ How gRPC communicates with MT5 terminal
- ✅ Every single MT5 function at protocol level
- ✅ Request/Response patterns for trading operations

**Time investment:** 2-3 days for solid foundation

---

### Step Up: MT5Service (Wrapper Layer) 🔧

**Continue here to see how to simplify the low-level API.**

```
MT5Account (proto) → MT5Service (wrappers) → Simple method calls
```

**What you'll do:**
1. Study how MT5Service wraps MT5Account methods
2. Run examples in `src/main/java/examples/services/`
3. Compare with low-level examples to see simplification

**Key examples:**
- `MarketDataServiceExample.java` - Simplified market data access
- `StreamingServiceExample.java` - Easier subscription handling
- `TradingServiceExample.java` - Trading without proto objects

**You'll understand:**
- ✅ API wrapper design patterns
- ✅ Type conversions and simplification techniques
- ✅ How to build convenient APIs on complex protocols

**Time investment:** 1 day to understand wrapper patterns

---

### Convenience: MT5Sugar (High-Level API) ⭐

**Start here if you want to trade quickly and learn foundations later.**

```
MT5Service → MT5Sugar → Auto-everything convenience methods
```

**What you'll do:**
1. Read [docs/MT5Sugar/MT5Sugar.Overview.md](./MT5Sugar/MT5Sugar.Overview.md)
2. Run examples in `src/main/java/examples/sugar/`
3. Study individual method docs in `docs/MT5Sugar/`

**Key examples:**
- `SimpleTradingScenario.java` - Basic trading workflow
- `RiskManagementScenario.java` - Risk-based position sizing
- `GridTradingScenario.java` - Grid trading strategy

**You'll understand:**
- ✅ Risk-based volume calculation ($ risk → lot size)
- ✅ Auto-normalization of volumes and prices
- ✅ Batch operations (close all, cancel all)
- ✅ Smart helpers and convenience patterns

**Time investment:** 1-2 days to master all 50+ methods

---

### Strategies: Orchestrators (Trading Automation) 🎯

**Learn complete strategy implementation patterns.**

```
MT5Sugar → Orchestrator → Complete trading strategy workflow
```

**What you'll do:**
1. Read [docs/Orchestrators.Overview.md](./Orchestrators.Overview.md)
2. Run `run.bat 10` (OrchestratorDemo) - interactive menu
3. Study orchestrator source code in `src/main/java/orchestrators/`
4. Copy and modify for your own strategies

**Key orchestrators:**
- `ScalpingOrchestrator.java` - Simplest, good starting point
- `TrendFollowingOrchestrator.java` - Position modification, trailing stops
- `BreakoutOrchestrator.java` - Pending orders, bi-directional entry
- `HedgingOrchestrator.java` - Dual position management

**You'll understand:**
- ✅ Complete strategy workflow implementation
- ✅ Position monitoring loops and state management
- ✅ Risk management and position sizing in practice
- ✅ Entry/exit logic automation
- ✅ Performance tracking patterns

**Time investment:** 2-3 days to study and adapt

---

### Advanced: Presets (Multi-Strategy Systems) 🎼

**Learn how to combine strategies with adaptive logic.**

```
Orchestrators → Preset → Multi-strategy adaptive system
```

**What you'll do:**
1. Read presets section in [Orchestrators.Overview.md](./Orchestrators.Overview.md#-multi-orchestrator-presets)
2. Run `run.bat 11` (PresetDemo) - interactive menu
3. Study preset source code in `src/main/java/presets/`
4. Design your own multi-strategy systems

**Key presets:**
- `DefensivePreset.java` - Conservative, easier to understand
- `AggressiveGrowthPreset.java` - Adaptive multi-phase system

**You'll understand:**
- ✅ Strategy composition patterns
- ✅ Adaptive decision-making logic
- ✅ Multi-phase trading sessions
- ✅ Complex system orchestration

**Time investment:** 1-2 days to master composition patterns

---

## 🚀 What Opportunities This Opens

### 1. Deep Understanding of MT5 Protocol

**You'll gain:**
- Complete knowledge of MT5 terminal communication
- Ability to implement any MT5 function from scratch
- Understanding of trading platform architecture
- Protocol-level debugging and troubleshooting skills

**Career value:** Work on trading platform integration, build custom MT5 tools, technical trading infrastructure roles.

---

### 2. API Design Skills

**You'll learn:**
- How to build layered architectures (3-tier design)
- API wrapper patterns and simplification techniques
- Convenience layer design for complex systems
- Progressive complexity approach

**Career value:** Backend development, API design, SDK development, developer tools.

---

### 3. Trading Automation Expertise

**You'll master:**
- Automated trading strategy implementation
- Risk management and position sizing
- Real-time position monitoring and management
- Strategy orchestration and composition

**Career value:** Algorithmic trading, quantitative development, trading system architecture.

---

### 4. Production-Ready Patterns

**You'll understand:**
- Error handling in trading systems
- Auto-normalization and edge case handling
- Batch operations and cleanup patterns
- Performance tracking and logging

**Career value:** Production trading systems, fintech development, high-reliability software.

---

## 📂 Examples Folder - Complete Method Reference

**Location:** `src/main/java/examples/`

The `examples/` folder contains **runnable demonstrations of every method** for full-fledged work with MT5.

### Structure

```
examples/
├── lowlevel/           ← MT5Account examples (proto level)
│   ├── MarketDataExample.java
│   ├── StreamingExample.java
│   └── TradingCalculationsExample.java
│
├── services/           ← MT5Service examples (wrapper level)
│   ├── MarketDataServiceExample.java
│   ├── StreamingServiceExample.java
│   └── TradingServiceExample.java
│
├── sugar/              ← MT5Sugar examples (convenience level)
│   ├── SimpleTradingScenario.java
│   ├── RiskManagementScenario.java
│   └── GridTradingScenario.java
│
├── orchestrators/
│   └── OrchestratorDemo.java    ← Interactive orchestrator menu
│
└── presets/
    └── PresetDemo.java           ← Interactive preset menu
```

### How to Use Examples

**1. Configure connection:**
```json
// Edit appsettings.json
{
  "MT5": {
    "Host": "localhost",
    "Port": 5555,
    "Login": YOUR_LOGIN,
    "Password": "YOUR_PASSWORD",
    "Symbol": "EURUSD"
  }
}
```

**2. Run examples:**
```bash
run.bat <example_number>

# Examples:
run.bat 1   # MarketDataExample (low-level)
run.bat 3   # SimpleTradingScenario (sugar)
run.bat 10  # OrchestratorDemo (strategies)
run.bat 11  # PresetDemo (multi-strategy)
```

**3. Study the code:**
- Each example demonstrates specific methods
- Read code comments for explanations
- Modify examples to experiment
- Copy patterns for your own code

**Purpose:** The examples folder is your **hands-on learning lab** - every method is demonstrated with working code you can run, study, and adapt.

---

## 🎯 Recommended Learning Paths

### Path A: Foundation-First (Deep Learning)

**For:** Developers who want deep understanding before building.

```
1. MT5Account (low-level)     → 2-3 days
2. MT5Service (wrappers)       → 1 day
3. MT5Sugar (convenience)      → 1-2 days
4. Orchestrators (strategies)  → 2-3 days
5. Presets (multi-strategy)    → 1-2 days
───────────────────────────────────────
Total: ~1.5-2 weeks for complete mastery
```

**Start with:** `examples/lowlevel/` and [MT5Account documentation](./MT5Account/)

---

### Path B: Quick-Start (Build First, Learn Later)

**For:** Traders who want to automate strategies quickly.

```
1. MT5Sugar (convenience)      → 1-2 days
2. Orchestrators (strategies)  → 2-3 days
3. Presets (multi-strategy)    → 1-2 days
4. MT5Service (wrappers)       → 1 day (when needed)
5. MT5Account (low-level)      → 2-3 days (when needed)
───────────────────────────────────────
Total: ~1 week to start trading, deepen as needed
```

**Start with:** `examples/sugar/` and [MT5Sugar.Overview.md](./MT5Sugar/MT5Sugar.Overview.md)

---

### Path C: Strategy-Focused (Copy & Modify)

**For:** Traders with specific strategy ideas to implement.

```
1. MT5Sugar basics            → 1 day
2. Choose orchestrator        → Study 1 day
3. Copy & modify              → 1-2 days
4. Test on demo               → Ongoing
5. Learn foundations as needed → As required
───────────────────────────────────────
Total: ~3-4 days to first custom strategy
```

**Start with:** [Orchestrators.Overview.md](./Orchestrators.Overview.md) and `run.bat 10`

---

## 📚 Documentation Structure

All documentation is organized for easy navigation:

```
docs/
├── GETTING_STARTED.md          ← You are here! 👈
├── PROJECT_MAP.md              ← Complete project structure guide
├── GLOSSARY.md                 ← Project-specific terms
│
├── MT5Account/                 ← Low-level proto API docs
│   ├── 1. Account_information/
│   ├── 2. Symbol_information/
│   ├── 3. Positions_and_orders/
│   ├── 4. Market_depth_DOM/
│   ├── 5. Trading/
│   └── 6. Subscriptions/
│
├── MT5Sugar/                   ← Convenience API docs
│   ├── MT5Sugar.Overview.md    ← Start here for Sugar
│   ├── 1. Symbol_helpers/
│   ├── 2. Market_orders/
│   └── ... (11 groups, 50+ methods)
│
└── Orchestrators.Overview.md   ← Strategies & presets
```

**Navigation tips:**
- Start with overview files (`.Overview.md`)
- Each method has its own detailed documentation
- Use [GLOSSARY.md](./GLOSSARY.md) to understand project terms
- Refer to [PROJECT_MAP.md](./PROJECT_MAP.md) for file locations

---

## ⚙️ Setup Requirements

**1. Java Development Kit (JDK)**
- Java 11 or higher required
- Recommended: Java 17+ (LTS)

**2. Maven / Maven Daemon**
- Maven for building project
- Maven Daemon (mvnd) recommended for faster builds
- Configured in `pom.xml`

**3. MetaTrader 5 Terminal**
- MT5 terminal with gRPC server enabled
- Demo or live account
- Configure connection in `appsettings.json`

**4. IDE (Recommended)**
- IntelliJ IDEA, Eclipse, or VS Code
- Java language support
- Git for version control

---

## 🎓 Learning Resources

**Inside the project:**
- 📖 **Documentation:** `docs/` folder (complete API reference)
- 💻 **Examples:** `src/main/java/examples/` (runnable demonstrations)
- 🎯 **Strategies:** `src/main/java/orchestrators/` (complete implementations)
- 🎼 **Presets:** `src/main/java/presets/` (multi-strategy systems)

**Key starting points:**
1. **[PROJECT_MAP.md](./PROJECT_MAP.md)** - Understand project structure
2. **[GLOSSARY.md](./GLOSSARY.md)** - Learn project terminology
3. **[MT5Sugar.Overview.md](./MT5Sugar/MT5Sugar.Overview.md)** - Quick start with trading
4. **[Orchestrators.Overview.md](./Orchestrators.Overview.md)** - Learn strategy patterns

---

## ⚠️ Important Notes

### This is an Educational Project

**What this means:**
- ✅ **DO** use for learning and experimentation
- ✅ **DO** study the code and patterns
- ✅ **DO** test on demo accounts
- ✅ **DO** adapt for your needs
- ❌ **DON'T** use as-is with real money without thorough testing
- ❌ **DON'T** expect production-grade error handling in examples
- ❌ **DON'T** blindly trust strategies without understanding them

### Risk Warning

**Trading involves risk:**
- Past performance doesn't guarantee future results
- Automated strategies can lose money
- Always test thoroughly on demo accounts first
- Never risk more than you can afford to lose
- Understand every line of code before trading real money

### Development Philosophy

**Progressive complexity:**
- Start at your comfort level (Sugar → Service → Account)
- Access complexity only when needed
- Build understanding gradually
- Focus on patterns, not memorization

**Educational focus:**
- Code is heavily commented for learning
- Examples demonstrate patterns, not production systems
- Orchestrators show strategy implementation, not guaranteed profits
- Goal is understanding, not black-box automation

---

## 🚀 Your Next Steps

### 1. Choose Your Path

Pick a learning path based on your goals:
- **Foundation-First:** Start with MT5Account for deep learning
- **Quick-Start:** Jump to MT5Sugar for fast results
- **Strategy-Focused:** Go to Orchestrators to implement your ideas

### 2. Setup Environment

```bash
# 1. Configure MT5 connection
edit appsettings.json

# 2. Build project
mvnd clean compile

# 3. Run your first example
run.bat 3  # SimpleTradingScenario
```

### 3. Study Documentation

```
Read: docs/PROJECT_MAP.md         (project structure)
Read: docs/GLOSSARY.md            (terminology)
Read: docs/MT5Sugar/MT5Sugar.Overview.md  (quick start)
```

### 4. Run Examples

```bash
# Try different levels
run.bat 1   # Low-level (MarketDataExample)
run.bat 3   # Sugar (SimpleTradingScenario)
run.bat 10  # Orchestrators (interactive menu)
```

### 5. Experiment & Build

- Modify examples to understand behavior
- Copy orchestrators as templates for your strategies
- Build your own trading automation
- Test everything on demo accounts!

---

## 📞 Need Help?

**Documentation:**
- **Project structure:** [PROJECT_MAP.md](./PROJECT_MAP.md)
- **API reference:** `docs/MT5Account/` and `docs/MT5Sugar/`
- **Strategy guide:** [Orchestrators.Overview.md](./Orchestrators.Overview.md)
- **Terminology:** [GLOSSARY.md](./GLOSSARY.md)

**Code examples:**
- **Low-level:** `examples/lowlevel/`
- **Wrappers:** `examples/services/`
- **Convenience:** `examples/sugar/`
- **Strategies:** `examples/orchestrators/` and `examples/presets/`

---

## 🎯 Final Thoughts

JavaMT5 is more than a trading library - it's a **complete learning journey** from protocol-level communication to production trading strategies.

**You'll walk away with:**
- Deep understanding of MT5 terminal architecture
- API design and layered architecture skills
- Trading automation implementation expertise
- Production-ready patterns and best practices
- Foundation for building your own trading systems

**The journey:**
```
Proto/gRPC → Wrappers → Convenience → Strategies → Your Ideas
(Foundation)  (Simplification)  (Automation)   (Production)
```

**Start wherever makes sense for you, and enjoy the learning process!**

---

> 💡 **Ready to begin?** Start with [PROJECT_MAP.md](./PROJECT_MAP.md) to understand the project structure, then choose your learning path above and dive in!

> 🎓 **Remember:** This project was created to learn low-level methods - everything else is built on that foundation. Understanding the base gives you power to build anything on top.
