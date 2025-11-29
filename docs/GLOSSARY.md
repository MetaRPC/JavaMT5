# JavaMT5 Glossary

> Project-specific terms and concepts. This glossary covers JavaMT5 architecture, components, and trading automation terminology used throughout the codebase.

---

## 🏗️ Architecture Terms

### Three-Tier Architecture

The core design pattern of JavaMT5 with three abstraction layers:

- **Layer 1 (MT5Account):** Low-level proto/gRPC communication
- **Layer 2 (MT5Service):** Wrapper methods with type conversions
- **Layer 3 (MT5Sugar):** High-level convenience methods

**Usage:** Start with Layer 3, drop down only when needed.

---

### MT5Account
**Layer 1 - Low-level API**

The foundational layer providing direct access to MT5 terminal via gRPC protocol.

**Key characteristics:**
- Works with proto Request/Response objects
- Raw gRPC calls to MT5 terminal
- Connection management
- Full control, maximum complexity

**When to use:** Custom integrations, proto-level control needed.

**Location:** `src/main/java/io/metarpc/mt5/MT5Account.java`

---

### MT5Service
**Layer 2 - Wrapper API**

Middle layer providing simplified method signatures without proto wrapping.

**Key characteristics:**
- Direct data returns (no proto objects in signatures)
- Type conversions (proto → Java primitives/objects)
- Simplified method names
- No auto-normalization

**When to use:** Need wrappers but not auto-normalization.

**Location:** `src/main/java/io/metarpc/mt5/MT5Service.java`

---

### MT5Sugar
**Layer 3 - Convenience API** ⭐

Highest-level API with ~50 convenience methods for common trading operations.

**Key characteristics:**
- Auto-normalization of volumes and prices
- Risk management helpers
- Batch operations (closeAll, cancelAll)
- Smart helpers (snapshots, conversions)
- Simplest API, handles edge cases

**When to use:** 95% of cases - easiest starting point.

**Location:** `src/main/java/io/metarpc/mt5/MT5Sugar.java`

**Documentation:** [MT5Sugar.Overview.md](MT5Sugar/MT5Sugar.Overview.md)

---

## 🎯 Strategy Components

### Orchestrator
Pre-built trading strategy implementation that automates complete trading workflow.

**Key characteristics:**
- Single strategy focus (trend following, scalping, etc.)
- Risk-based position sizing
- Position monitoring loops
- Automatic exits and cleanup
- Performance tracking

**Examples:**
- `TrendFollowingOrchestrator` - Trend following with trailing stops
- `ScalpingOrchestrator` - Quick in/out trades
- `HedgingOrchestrator` - Defensive hedging strategy
- `BreakoutOrchestrator` - Breakout trading with pending orders
- `MartingaleOrchestrator` - Volume doubling (⚠️ HIGH RISK)

**Location:** `src/main/java/orchestrators/`

**Documentation:** [Orchestrators.Overview.md](Orchestrators.Overview.md)

---

### Preset
Multi-orchestrator combination with adaptive decision-making logic.

**Key characteristics:**
- Combines 2-4 orchestrators
- Adaptive logic (if profit > X then...)
- Multi-phase trading sessions
- Performance tracking across phases
- Advanced strategy composition

**Examples:**
- `AggressiveGrowthPreset` - 3-4 orchestrators with adaptive phases
- `DefensivePreset` - Conservative protection-first approach

**Location:** `src/main/java/presets/`

**Documentation:** [Orchestrators.Overview.md](Orchestrators.Overview.md)

---

## 🔧 Technical Concepts

### Auto-Normalization
Automatic adjustment of trading parameters to broker requirements.

**What gets normalized:**
- **Volumes:** Rounded to broker's volume step (e.g., 0.01 lots)
- **Prices:** Rounded to symbol's tick size (e.g., 5 decimal places for EURUSD)
- **Stop Loss / Take Profit:** Adjusted to minimum distance from current price

**Where:** MT5Sugar layer only (MT5Service doesn't auto-normalize)

**Example:**
```java
// You pass: volume=0.0234, SL=1.09876543
// Sugar normalizes to: volume=0.02, SL=1.09877
sugar.buyMarket("EURUSD", 0.0234, 1.09876543, 1.10000);
```

---

### Risk-Based Volume Calculation
Position sizing based on dollar risk rather than fixed lot size.

**Formula:** `volume = riskAmount / (stopLossPoints × pointValue)`

**Parameters:**
- `riskAmount` - Dollar amount willing to risk (e.g., $50)
- `stopLossPoints` - Distance to SL in points (e.g., 50 points)
- Result: Lot size that risks exactly $50 if SL hit

**Methods:**
- `calculateVolume(symbol, slPoints, riskAmount)` - Calculate volume
- `buyByRisk(symbol, slPoints, riskAmount, tp)` - Buy with risk sizing
- `sellByRisk(symbol, slPoints, riskAmount, tp)` - Sell with risk sizing

**Documentation:** [calculateVolume.md](MT5Sugar/7. Risk_management/calculateVolume.md)

---

### Points vs Pips
**Point:** Smallest price movement for a symbol (1 tick).

**Pip:** Traditional forex unit (0.0001 for most pairs).

**Relationship:**
- **5-digit brokers:** 1 pip = 10 points (EURUSD: 1.10000 → 1.10010 = 1 pip)
- **3-digit brokers:** 1 pip = 1 point (USDJPY: 110.00 → 110.01 = 1 pip)

**In JavaMT5:** All APIs use **points** for consistency.

**Conversion:** Use `pointsToPips(symbol, points)` helper.

**Example:**
```java
double point = sugar.getPoint("EURUSD");  // 0.00001
double pips = sugar.pointsToPips("EURUSD", 50);  // 5.0 pips
```

---

### Trailing Stop
Dynamic Stop Loss that follows price in profit direction.

**How it works:**
1. Position opens with initial SL
2. When profit reaches threshold (e.g., +40 points)
3. SL moves to breakeven or better
4. SL continues to trail price at fixed distance
5. Locks in profits as price moves favorably

**Implementation:**
```java
// In monitoring loop
if (currentProfit >= trailingThreshold) {
    double newSL = currentPrice - trailingDistance;
    if (newSL > currentSL) {
        sugar.modifyPosition(ticket, newSL, tp);
    }
}
```

**Used in:** `TrendFollowingOrchestrator`

---

### Hedging
Opening opposite position to lock in current profit/loss level.

**How it works:**
1. Primary position opened (e.g., BUY EURUSD 0.1 lots)
2. Price moves against you (-50 points)
3. Hedge triggered: SELL EURUSD 0.1 lots
4. Net position = 0 (locked loss at -50 points level)

**Purpose:**
- Lock losses instead of closing at stop loss
- Protect position during volatility/news
- Wait for better exit opportunity

**Used in:** `HedgingOrchestrator`

**⚠️ Note:** Not all brokers/regulations allow hedging.

---

### Pending Order
Order that executes automatically when price reaches specified level.

**Types:**
- **BUY LIMIT:** Buy at price BELOW current (expecting pullback then up)
- **SELL LIMIT:** Sell at price ABOVE current (expecting rally then down)
- **BUY STOP:** Buy at price ABOVE current (breakout up)
- **SELL STOP:** Sell at price BELOW current (breakout down)

**Methods:**
```java
// Absolute price
sugar.buyLimit(symbol, volume, price, sl, tp);

// Offset in points from current price
sugar.buyLimitPoints(symbol, volume, pointsOffset, slPoints, tpPoints);
```

---

## 📦 Protocol Buffer Terms

### Proto / Protocol Buffers
Google's language-neutral data serialization format used for gRPC communication.

**In JavaMT5:**
- MT5 terminal communicates via gRPC protocol
- Proto files define message structures (`.proto` files)
- Compiled into Java classes by Maven
- Used in MT5Account layer

**Location:** `src/main/proto/`

---

### gRPC
High-performance RPC (Remote Procedure Call) framework using HTTP/2.

**In JavaMT5:**
- MT5Account layer sends gRPC requests to MT5 terminal
- Terminal runs gRPC server (configured in appsettings.json)
- Request/Response pattern for all operations

**Connection setup:**
```java
MT5Account account = new MT5Account(login, password);
account.connect(host, port, symbol, useSSL, timeout);
```

---

### Proto Request/Response
Structured message objects used in MT5Account layer.

**Example:**
```java
// Proto request object
var request = Mt5TermApiTradeFunctions.OrderSendRequest.newBuilder()
    .setSymbol(symbol)
    .setVolume(volume)
    .setType(orderType)
    .build();

// Proto response object
var response = account.orderSend(request);
var ticket = response.getData().getOrder();
```

**Note:** MT5Service and MT5Sugar hide proto objects from user.

---

## 🎓 Trading Terms (Project Context)

### Risk Amount
Dollar amount willing to lose if Stop Loss hit.

**Example:** Risk $50 per trade means if SL triggered, you lose exactly $50.

**Used in:**
- All orchestrators for position sizing
- `calculateVolume()`, `buyByRisk()`, `sellByRisk()` methods

---

### Breakeven
Moving Stop Loss to entry price to eliminate risk.

**Example:**
- Entry: BUY at 1.10000
- Price rises to 1.10050 (+50 points profit)
- Move SL from 1.09950 to 1.10000 (breakeven)
- Now risk-free: worst case = break even

**Used in:** `TrendFollowingOrchestrator`

---

### Martingale
High-risk betting system: double position size after each loss.

**Progression:**
```
Trade 1: 0.01 lots → Loss → -$10
Trade 2: 0.02 lots → Loss → -$30 total
Trade 3: 0.04 lots → Loss → -$70 total
Trade 4: 0.08 lots → WIN  → +$10 total ✓
```

**⚠️ WARNING:** Exponential capital requirement, can wipe account.

**Used in:** `MartingaleOrchestrator` (demo only!)

**Documentation:** [Orchestrators.Overview.md](./Orchestrators.Overview.md)

---

### Volume Limits
Broker-specific constraints on position sizing.

**Retrieved via:**
```java
var limits = sugar.getVolumeLimits(symbol);
double minVolume = limits.getMinVolume();  // e.g., 0.01
double maxVolume = limits.getMaxVolume();  // e.g., 100.0
double stepVolume = limits.getStepVolume(); // e.g., 0.01
```

**Used for:** Auto-normalization in MT5Sugar.

---

## 📁 File Organization Terms

### Examples
Runnable demonstration code showing API usage patterns.

**Structure:**
- `examples/lowlevel/` - MT5Account examples (proto level) - run.bat 1-3
- `examples/services/` - MT5Service examples (wrapper level) - run.bat 4-6
- `examples/sugar/` - MT5Sugar examples (convenience level) - run.bat 7-9

**How to run:**
```bash
run.bat 1-9    # Examples (various API levels)
run.bat 10     # Orchestrator menu
run.bat 10 1-5 # Specific orchestrator
run.bat 11     # Preset menu
run.bat 11 1-2 # Specific preset
```

**Location:** `src/main/java/examples/`

**See:** [RUNNING_EXAMPLES.md](./RUNNING_EXAMPLES.md) for complete command list.

---

### Scenario
Complex usage example demonstrating real-world workflow.

**Examples:**
- `SimpleTradingScenario.java` - Basic trading workflow (run.bat 7)
- `RiskManagementScenario.java` - Risk-based position sizing (run.bat 8)
- `GridTradingScenario.java` - Grid trading strategy (run.bat 9)

**Location:** `src/main/java/examples/sugar/`

---

### Program.java
Main entry point that routes run.bat commands to examples/orchestrators/presets.

**Key characteristics:**
- Single entry point for all examples (1-11)
- Uses reflection to find and launch example classes
- Provides interactive menus for orchestrators (10) and presets (11)
- Manages resource cleanup for orchestrators/presets
- Contains complete command reference in file header

**How it works:**
```
run.bat 7
    ↓
Program.java main(args)
    ↓
runDemo(7)
    ↓
Reflection finds: examples.sugar.SimpleTradingScenario
    ↓
Calls: SimpleTradingScenario.main()
```

**Location:** `src/main/java/Program.java`

**See:** [PROJECT_MAP.md](./PROJECT_MAP.md#programjava-srcmainjavaprogramjava) for details.

---

## ⚙️ Configuration Terms

### appsettings.json
Central configuration file for MT5 connection settings.

**Key settings:**
- `Host` - MT5 terminal host (usually localhost)
- `Port` - gRPC server port (default 5555)
- `Login` - MT5 account number
- `Password` - MT5 account password
- `Symbol` - Default trading symbol (e.g., "EURUSD")
- `UseSSL` - SSL/TLS encryption (true/false)
- `TimeoutSeconds` - Request timeout (default 30)

**Location:** `./appsettings.json`

**⚠️ Security:** Never commit real credentials to git (add to .gitignore).

---

### run.bat
Quick launcher script for running all JavaMT5 examples.

**Usage:**
```bash
run.bat <number> [sub-number]

# Examples:
run.bat 7      # SimpleTradingScenario
run.bat 10     # Orchestrator menu
run.bat 10 1   # Scalping orchestrator
run.bat 11 2   # Defensive preset
run.bat stop   # Stop Maven daemon
```

**What it does:**
- Compiles project: `mvnd compile`
- Runs Program.java: `mvnd exec:java -Dexec.args="<numbers>"`
- Uses Maven Daemon (mvnd) for fast execution
- Handles Java environment setup

**Location:** `./run.bat`

**Note:** If `run.bat` doesn't work, try `.\run.bat` (PowerShell/Git Bash).

**See:** [RUNNING_EXAMPLES.md](./RUNNING_EXAMPLES.md) for troubleshooting.

---

### Maven Daemon (mvnd)
Fast Maven build tool that reuses JVM between builds.

**Benefits:**
- Much faster than standard `mvn`
- Keeps JVM warm between builds
- Parallel compilation

**Usage in JavaMT5:**
```bash
mvnd compile                        # Build project
mvnd exec:java -Dexec.args="7"     # Run example 7
mvnd --stop                         # Stop daemon
```

**Configuration:** `pom.xml`

**Troubleshooting:** If build hangs, stop daemon with `run.bat stop` or `mvnd --stop`.

---

### target/ Folder
Build output directory containing all compiled files and generated code.

**Contents:**
```
target/
├── classes/                    # Compiled .class files
└── maven-status/              # Build metadata
```

**Purpose:**
- Maven puts all compiled classes here
- Auto-generated during `mvnd compile`
- Can be safely deleted for clean rebuild
- MetaRPC library classes come from JAR dependency (not generated locally)

**Troubleshooting:**
```bash
# If you get "Unresolved compilation problem" errors:
rmdir /s /q target         # Windows CMD
Remove-Item -Recurse -Force target  # PowerShell
rm -rf target              # Git Bash/Linux

# Then run any command - target/ will be regenerated:
run.bat 7
```

**⚠️ Java is finicky!** Deleting `target/` fixes 99% of weird compilation errors.

**See:** [RUNNING_EXAMPLES.md](./RUNNING_EXAMPLES.md#problem-2-compilation-errors-unresolved-compilation-problem) for details.

---

## 🔗 Cross-Component Terms

### Snapshot
Complete state capture at a point in time.

**Types:**
- **Account Snapshot:** Balance, equity, margin, profit, positions count
- **Symbol Snapshot:** Bid, ask, spread, point size, volume limits

**Methods:**
```java
var accountSnap = sugar.getAccountSnapshot();
var symbolSnap = sugar.getSymbolSnapshot(symbol);
```

**Use case:** Dashboards, logging, performance tracking.

---

### Batch Operations

Execute action on multiple positions/orders at once.

**Methods:**

- `closeAll()` - Close ALL positions (BUY and SELL)
- `closeAllBuy()` - Close only BUY positions
- `closeAllSell()` - Close only SELL positions
- `closeAllPositions(symbol)` - Close all for specific symbol
- `closeAllPending(symbol)` - Cancel all pending orders
- `cancelAll()` - Cancel ALL pending orders

**Use case:** Emergency exits, end-of-day cleanup, strategy resets.

---

### History Queries

Retrieve past orders and positions for analysis.

**Methods:**

- `getOrdersHistoryLastDays(days, symbol)` - Orders from last N days
- `getPositionsHistoryPaged(page, itemsPerPage)` - Paginated position history

**Use case:** Performance analysis, trade logs, backtesting validation.

---

## 🎯 Project Philosophy Terms

### Progressive Complexity
Design principle: start simple, access complexity only when needed.

**In JavaMT5:**
- Start with MT5Sugar (simplest)
- Drop to MT5Service if need wrappers without auto-normalization
- Drop to MT5Account for proto-level control

**User journey:** Sugar → Service → Account (as needed).

---

### Educational Project

JavaMT5 orchestrators and presets are learning materials, not production systems.

**Implications:**

- ✅ Study code and patterns
- ✅ Modify for your needs
- ✅ Test on demo accounts
- ❌ Don't use as-is with real money
- ❌ Don't expect production-grade error handling

---

### User-Facing vs Internal

**User-facing (modify freely):**
- Orchestrators
- Presets
- Examples
- appsettings.json

**Internal (use but don't modify):**
- MT5Account, MT5Service, MT5Sugar
- Proto definitions
- Generated classes

**See:** [PROJECT_MAP.md](./PROJECT_MAP.md#-what-to-modify-vs-what-to-leave-alone)

---

## 📚 See Also

- **[RUNNING_EXAMPLES.md](RUNNING_EXAMPLES.md)** - How to run examples + troubleshooting
- **[PROJECT_MAP.md](PROJECT_MAP.md)** - Complete project structure guide
- **[MT5Sugar.Overview.md](MT5Sugar/MT5Sugar.Overview.md)** - All 50+ convenience methods
- **[Orchestrators.Overview.md](Orchestrators.Overview.md)** - Strategy implementations
- **API Documentation** - [MT5Account](MT5Account/MT5Account.Master.Overview.md) and [MT5Sugar](MT5Sugar/MT5Sugar.Overview.md)

---

## 💡 Quick Term Lookup

| Term | Category | Definition |
|------|----------|------------|
| MT5Account | Architecture | Layer 1 - Low-level proto/gRPC API |
| MT5Service | Architecture | Layer 2 - Wrapper methods |
| MT5Sugar | Architecture | Layer 3 - Convenience API ⭐ |
| Program.java | Architecture | Main entry point, routes all commands |
| Orchestrator | Strategy | Single-strategy implementation |
| Preset | Strategy | Multi-orchestrator combination |
| Auto-normalization | Technical | Automatic parameter adjustment |
| Risk-based sizing | Trading | Position size from dollar risk |
| Point | Trading | Smallest price movement (1 tick) |
| Pip | Trading | Traditional forex unit (0.0001) |
| Trailing stop | Trading | SL that follows profit |
| Hedging | Trading | Opposite position to lock P/L |
| Pending order | Trading | Order at future price level |
| Proto | Technical | Protocol Buffer message format |
| gRPC | Technical | RPC framework for communication |
| Snapshot | Technical | Complete state capture |
| Batch operation | Technical | Action on multiple items |
| target/ folder | Build | Compiled output, delete for clean rebuild |
| run.bat | Tool | Quick launcher for examples |
| mvnd | Tool | Fast Maven daemon build tool |
| Progressive complexity | Philosophy | Start simple, access complexity as needed |

---

> 💡 **New to JavaMT5?**
> 1. Read [RUNNING_EXAMPLES.md](./RUNNING_EXAMPLES.md) to learn how to run examples and troubleshoot issues
> 2. Check [PROJECT_MAP.md](./PROJECT_MAP.md) to understand the project structure
> 3. Explore [MT5Sugar.Overview.md](./MT5Sugar/MT5Sugar.Overview.md) for the convenience API
