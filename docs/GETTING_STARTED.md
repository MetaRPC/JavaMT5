# Getting Started with JavaMT5

> **Welcome to JavaMT5** - a comprehensive educational project for learning MT5 trading automation from the ground up. This guide will show you the learning path and what opportunities this project opens for you.

---

## 🚀 Prerequisites and Setup

Before you start working with JavaMT5, you need to set up your development environment.

### Step 1: Install Java (JDK 11 or higher)

JavaMT5 requires Java Development Kit (JDK) version 11 or higher.

**Download and Install:**

- **Recommended:** [Eclipse Adoptium JDK](https://adoptium.net/) (formerly AdoptOpenJDK)
- **Alternative:** [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)

**Verify installation:**

```bash
java -version
# Should show: java version "11" or higher
```

**Set JAVA_HOME environment variable:**

- Windows: Add to System Environment Variables
  ```
  JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-11.0.x-hotspot
  ```
- Add `%JAVA_HOME%\bin` to PATH

---

### Step 2: Install Maven Daemon (mvnd)

JavaMT5 uses Maven Daemon for fast builds and execution.

**Download:**

- Visit [Maven Daemon releases](https://github.com/apache/maven-mvnd/releases)
- Download the latest version for Windows (e.g., `maven-mvnd-1.0.3-windows-amd64.zip`)

**Install:**

1. Extract to a permanent location (e.g., `C:\Users\<your-username>\maven-mvnd-1.0.3-windows-amd64`)
2. Add to PATH or update `run.bat` with your path
3. Verify installation:
   ```bash
   mvnd --version
   # Should show Maven Daemon version
   ```

**Configure run.bat:**

Edit `run.bat` and update the MVND path if needed:
```batch
set "MVND=C:\Users\<your-username>\maven-mvnd-1.0.3-windows-amd64\bin\mvnd.cmd"
```

---

### Step 3: Configure MT5 Account Connection

JavaMT5 connects to MT5 terminal via the **MetaRPC gRPC gateway** - a cloud service that routes your requests to your broker's MT5 server.

**What is MetaRPC?**

- GitHub package: `com.github.MetaRPC.metarpcmt5`
- Provides gRPC communication with MT5 terminal
- Already included in `pom.xml` dependencies
- Connects via default gateway: `mt5.mrpc.pro:443`
- See: [MetaRPC on GitHub Packages](https://github.com/MetaRPC/JavaMT5/packages/2470968)

**Configure your MT5 account:**

Edit `appsettings.json` with your MT5 account credentials:

```json
{
  "MT5Connections": {
    "FxProDemo": {
      "user": 123456,
      "password": "your_password",
      "serverName": "YourBroker-MT5 Demo",
      "baseSymbol": "EURUSD"
    }
  },
  "DefaultConnection": "FxProDemo"
}
```

**Configuration parameters:**

- `user` - Your MT5 account number
- `password` - Your MT5 account password
- `serverName` - Your broker's MT5 server name (e.g., "FxPro-MT5 Demo")
- `baseSymbol` - Default trading symbol (e.g., "EURUSD")

The library automatically connects through `mt5.mrpc.pro:443` gateway, which routes to your broker's server based on `serverName`.

### Step 4: Clone and Build the Project

**Clone repository:**
```bash
git clone https://github.com/MetaRPC/JavaMT5.git
cd JavaMT5
```

**Understanding run.bat - Your Project Lifecycle Manager:**

The `run.bat` script is the central command that manages the entire JavaMT5 project lifecycle:

- **Runs examples** from the `examples/` folder (commands 1-9)
- **Runs orchestrators** - trading strategies (commands 10 1-5)
- **Runs presets** - multi-strategy systems (commands 11 1-2)
- **Auto-checks generated files** on every execution
- **Uses pre-compiled MetaRPC library** (no protobuf generation needed)
- **Compiles the project** before running

**What happens when you run `.\run.bat 7`:**
```
1. Maven downloads MetaRPC library (if not cached)
2. Compiles all Java source files
3. Launches SimpleTradingScenario example
```

This ensures every run starts with a fresh, complete build.

**For complete command reference and troubleshooting:**
See [RUNNING_EXAMPLES.md](./RUNNING_EXAMPLES.md) for:
- All available commands (1-11)
- How to fix compilation errors
- How to handle hanging processes
- Build process details

---

**First build:**
```bash
# Windows CMD or PowerShell
.\run.bat 7

# This will:
# 1. Download all Maven dependencies (including MetaRPC library)
# 2. Compile the project
# 3. Run SimpleTradingScenario example
```

**If you get errors:**
- See [RUNNING_EXAMPLES.md](./RUNNING_EXAMPLES.md) for troubleshooting
- Common fix: Delete `target/` folder and try again
  ```bash
  rmdir /s /q target
  .\run.bat 7
  ```

---

### Step 5: Verify Everything Works

**Test the setup:**
```bash
# Run a simple example
.\run.bat 7    # SimpleTradingScenario

# If it connects and runs - you're ready!
# If you see errors - check RUNNING_EXAMPLES.md
```

**Expected output:**
```
[INFO] BUILD SUCCESS
Configuration loaded: user=123456
► Connecting to MT5...
✓ Connected
...
```

---

### What's Next?

Now that your environment is set up, continue below to learn about JavaMT5 architecture and start your learning journey!

**Quick links:**
- [RUNNING_EXAMPLES.md](./RUNNING_EXAMPLES.md) - All commands and troubleshooting
- [PROJECT_MAP.md](./PROJECT_MAP.md) - Project structure overview
- [GLOSSARY.md](./GLOSSARY.md) - Terms and definitions

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

**Documentation:** [MT5Account/](MT5Account/MT5Account.Master.Overview.md)

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

**Documentation:** [MT5Sugar/MT5Sugar.Overview.md](MT5Sugar/MT5Sugar.Overview.md)

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

**Documentation:** [docs/Orchestrators.Overview.md](./Orchestrators.Overview.md)

**Why this matters:** Advanced strategy composition - how to build complex trading systems from simple building blocks.

---

## 🗺️ The Learning Path

### Foundation: MT5Account (Low-Level) 📦

**START HERE if you want to understand how everything works.**

```
MT5 Terminal ←→ gRPC ←→ Proto Messages ←→ MT5Account.java
```

**What you'll do:**

1. Read [MT5Account documentation](MT5Account/MT5Account.Master.Overview.md)
2. Study MetaRPC library structure (proto-generated classes in JAR)
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

1. Read [MT5Sugar.Overview.md](MT5Sugar/MT5Sugar.Overview.md)
2. Run examples in `src/main/java/examples/sugar/`
3. Study individual method docs in `MT5Sugar/` folder

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
2. Run `run.bat 10` - interactive orchestrator menu
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

1. Read presets section in [Orchestrators.Overview.md](./Orchestrators.Overview.md)
2. Run `run.bat 11` - interactive preset menu
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
└── sugar/              ← MT5Sugar examples (convenience level)
    ├── SimpleTradingScenario.java
    ├── RiskManagementScenario.java
    └── GridTradingScenario.java
```

## 🎓 Learning Resources

### Inside the project:

- 📖 **Documentation:** `docs/` folder (complete API reference)
- 💻 **Examples:** `src/main/java/examples/` (runnable demonstrations)
- 🎯 **Strategies:** `src/main/java/orchestrators/` (complete implementations)
- 🎼 **Presets:** `src/main/java/presets/` (multi-strategy systems)

### Key starting points:

1. **[PROJECT_MAP.md](PROJECT_MAP.md)** - Understand project structure
2. **[GLOSSARY.md](GLOSSARY.md)** - Learn project terminology
3. **[MT5Sugar.Overview.md](MT5Sugar/MT5Sugar.Overview.md)** - Quick start with trading
4. **[Orchestrators.Overview.md](Orchestrators.Overview.md)** - Learn strategy patterns

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

---

## 📞 Need Help?

### Documentation

- **Project structure:** [PROJECT_MAP.md](./PROJECT_MAP.md)
- **API reference:** `docs/MT5Account/` and `docs/MT5Sugar/`
- **Strategy guide:** [Orchestrators.Overview.md](./Orchestrators.Overview.md)
- **Terminology:** [GLOSSARY.md](./GLOSSARY.md)

### Code examples

- **Low-level:** `examples/lowlevel/`
- **Wrappers:** `examples/services/`
- **Convenience:** `examples/sugar/`
- **Strategies:** `orchestrators/` and `presets/`

---

## 🎯 Final Thoughts

JavaMT5 is more than a trading library - it's a **complete learning journey** from protocol-level communication to production trading strategies.

### You'll walk away with

- Deep understanding of MT5 terminal architecture
- API design and layered architecture skills
- Trading automation implementation expertise
- Production-ready patterns and best practices
- Foundation for building your own trading systems

### The journey

```
Proto/gRPC → Wrappers → Convenience → Strategies → Your Ideas
(Foundation)  (Simplification)  (Automation)   (Production)
```

**Start wherever makes sense for you, and enjoy the learning process!**

---

> 💡 **Ready to begin?** Start with [PROJECT_MAP.md](./PROJECT_MAP.md) to understand the project structure, then choose your learning path above and dive in!

> 🎓 **Remember:** This project was created to learn low-level methods - everything else is built on that foundation. Understanding the base gives you power to build anything on top.


---

## 🆔 Account ID Generation (`GetId`)

> ⚠️ **Important**: You must generate your deterministic account ID with `GetId` **firstly** before connecting or streaming.

MetaRPC endpoints require two essential credentials for all operations:
1. **`APIKey`**: Your personal authentication token from [https://mrpc.pro/my](https://mrpc.pro/my) (obtained by registering at [https://mrpc.pro/signup](https://mrpc.pro/signup)). Sent in the `APIKey` header.
2. **`id`**: A deterministic account GUID generated from your MetaTrader `user` (login number) and `password`. The same credentials always produce the exact same GUID.

### Calling GetId

#### Via REST API:
```bash
curl -X GET "https://mt5.mrpc.pro/GetId?user=YOUR_LOGIN&password=YOUR_PASSWORD" \
     -H "APIKey: YOUR_API_KEY"
```

Response:
```json
{
  "data": {
    "id": "e8d91060-c3d3-4f4d-8d2a-9e1b2c3d4e5f"
  }
}
```

#### Via gRPC:
Send a `GetIdRequest` with `user` and `password` to the connection service before calling `ConnectEx`.

Use this returned `id` GUID in the `id` header or session parameter for `ConnectEx`, `AccountSummary`, and all other terminal operations.
