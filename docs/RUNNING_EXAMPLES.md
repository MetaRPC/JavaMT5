# Running JavaMT5 Examples - Complete Guide

This guide explains how to run JavaMT5 examples using `run.bat` and how to troubleshoot common issues.

---

## Quick Start

The simplest way to run any example:

```bash
# Windows CMD
run.bat <number>

# PowerShell / Git Bash (if above doesn't work)
.\run.bat <number>
```

**Examples:**
```bash
run.bat 7          # Run SimpleTradingScenario (Sugar example)
run.bat 10 1       # Run Scalping Orchestrator
run.bat 11 2       # Run Defensive Preset
```

---

## All Available Commands

### Level 1: Low-Level API (MT5Account - Direct gRPC)

| Command | File | Description |
|---------|------|-------------|
| `run.bat 1` | `MarketDataExample.java` | Account info, symbols, quotes, order book |
| `run.bat 2` | `TradingCalculationsExample.java` | Margin, profit calculations, order validation |
| `run.bat 3` | `StreamingExample.java` | Real-time tick/trade/position streaming |

### Level 2: Service API (MT5Service - Simplified Wrappers)

| Command | File | Description |
|---------|------|-------------|
| `run.bat 4` | `MarketDataServiceExample.java` | Easier market data access with wrappers |
| `run.bat 5` | `TradingServiceExample.java` | Simplified trading operations |
| `run.bat 6` | `StreamingServiceExample.java` | Simplified real-time data streams |

### Level 3: Sugar API (MT5Sugar - One-Line Convenience)

| Command | File | Description |
|---------|------|-------------|
| `run.bat 7` | `SimpleTradingScenario.java` | Basic buy/sell in 1 line: `sugar.buyMarket()` |
| `run.bat 8` | `RiskManagementScenario.java` | Auto volume calculation by risk amount |
| `run.bat 9` | `GridTradingScenario.java` | Multiple pending orders at price levels |

### Level 4: Orchestrators (Automated Trading Strategies)

| Command | File | Description |
|---------|------|-------------|
| `run.bat 10` | Shows orchestrator menu | Interactive menu to choose strategy |
| `run.bat 10 1` | `ScalpingOrchestrator.java` | Scalping (8pt SL, 15pt TP, $20 risk) |
| `run.bat 10 2` | `TrendFollowingOrchestrator.java` | Trend Following (80pt SL, 160pt TP, trailing) |
| `run.bat 10 3` | `HedgingOrchestrator.java` | Hedging (auto-hedge at 50pt loss) |
| `run.bat 10 4` | `BreakoutOrchestrator.java` | Breakout (pending BUY/SELL STOP orders) |
| `run.bat 10 5` | `MartingaleOrchestrator.java` | Martingale (⚠️ high risk - doubles volume) |
| `run.bat 10 0` | Runs all 5 orchestrators | Demo mode - run all sequentially |

### Level 5: Presets (Multi-Strategy Trading Systems)

| Command | File | Description |
|---------|------|-------------|
| `run.bat 11` | Shows preset menu | Interactive menu to choose preset |
| `run.bat 11 1` | `AggressiveGrowthPreset.java` | Aggressive Growth (Scalping+Trend+Breakout) |
| `run.bat 11 2` | `DefensivePreset.java` | Defensive (Hedging+Scalping+Martingale) |
| `run.bat 11 0` | Runs both presets | Demo mode - run both with P/L comparison |

### Special Commands

| Command | Description |
|---------|-------------|
| `run.bat` | Show help menu |
| `run.bat stop` | Stop Maven daemon |

---

## What Each Command Does

### How `run.bat` Works Internally

When you run `run.bat 7` (or any number), here's what happens:

1. **Argument Parsing** - `run.bat` passes `7` to `Program.java`
2. **Program.java Main Method** - Receives argument and decides what to run
3. **Routing Logic:**
   - `1-9` → Calls `runDemo()` which uses reflection to launch the example class
   - `10` → Calls `runOrchestrator()` which shows menu or runs specific orchestrator
   - `11` → Calls `runPreset()` which shows menu or runs specific preset

### Example: `run.bat 7`

```
run.bat 7
    ↓
Program.java main(String[] args)  // args[0] = "7"
    ↓
runDemo(7)
    ↓
Uses reflection to find: examples.sugar.SimpleTradingScenario
    ↓
Calls: SimpleTradingScenario.main()
    ↓
Runs the example!
```

### Example: `run.bat 10 1`

```
run.bat 10 1
    ↓
Program.java main(String[] args)  // args[0] = "10", args[1] = "1"
    ↓
runOrchestrator(sugar, 1)
    ↓
Calls: runScalping(sugar)
    ↓
Creates ScalpingOrchestrator and executes it
    ↓
Runs the orchestrator!
```

### What Gets Compiled and Run

Every `run.bat` command does:

```bash
mvnd compile exec:java -Dexec.args="<your arguments>"
```

### This means:

1. **`mvnd compile`** - Compile all Java files
   - Generate protobuf files (if needed)
   - Compile all `.java` files to `.class` files
   - Put everything in `target/classes/`

2. **`exec:java`** - Execute `Program.java` main method
   - Pass your arguments (e.g., `"10 1"`)
   - Program.java routes to the correct example/orchestrator/preset

---

## Troubleshooting

### Problem 1: Processes Don't Stop (Script Hangs)

**Symptoms:**
- Script compiles but doesn't run the example
- Java processes stay running in background
- Can't start new examples

**Why This Happens:**
- Maven daemon or Java processes didn't terminate properly
- Previous run crashed or was forcefully stopped

**Solution - Kill All Processes:**

```bash
# Windows CMD
taskkill /F /IM java.exe         # Kill all Java processes
taskkill /F /IM mvnd.exe         # Kill Maven daemon

# Or use run.bat
run.bat stop                     # Stop Maven daemon
.\run.bat stop                   # PowerShell/Git Bash
```

**Then try again:**
```bash
run.bat 10 1                     # Should work now!
```

---

### Problem 2: Compilation Errors ("Unresolved compilation problem")

**Symptoms:**
- Error: `java.lang.Error: Unresolved compilation problem`
- Error: `BUILD FAILURE` during compilation
- Random errors that shouldn't exist
- Code was working before, now broken

**Why This Happens:**
- ⚠️ **Java build system is finicky!**
- Maven caches compiled files in the `target/` folder
- Sometimes these cached files get corrupted
- Daemon might be using old/stale versions

**Solution - Delete `target/` Folder:**

Java projects have a magical "turn it off and on again" button: **delete the `target/` folder!**

#### Windows CMD:
```bash
rmdir /s /q target               # Delete target folder
run.bat 11 2                     # Next run.bat rebuilds everything
```

#### Windows PowerShell:
```bash
Remove-Item -Recurse -Force target
.\run.bat 11 2
```

#### Git Bash / Linux:
```bash
rm -rf target
./run.bat 11 2
```

**What Happens When You Delete `target/`:**

1. **You delete:** All compiled `.class` files, generated protobuf files, Maven caches
2. **Next `run.bat` automatically:**
   - Recreates `target/` folder
   - Regenerates all protobuf files from `.proto` sources
   - Recompiles all `.java` files to `.class` files
   - Starts with a clean slate!

**This works 99% of the time!** It's the standard way to fix weird Java compilation issues.

---

### Problem 3: "No Connection" or MT5 Errors

**Symptoms:**
- Error: `TERMINAL_MANAGER_CREATE_TERMINAL_INSTANCE_ERROR`
- Error: `GetServersDataByClusterName error`
- Error: `TRADE_RETCODE_CONNECTION - No connection with trade server`

**Why This Happens:**
- MT5 terminal is not running
- MetaRPC gRPC server is not running
- Lost connection during trade execution

**Solution:**

1. **Check MT5 Terminal is running**
2. **Check MetaRPC server is running**
3. **Restart both if needed**
4. **If error persists** - might be temporary MT5 server issue, try again

---

## Understanding the Build Process

### What is `target/` Folder?

The `target/` folder is where Maven puts **your** compiled files:

```
target/
├── classes/                          # Only YOUR compiled code
│   ├── Program.class
│   ├── io/metarpc/mt5/              # YOUR wrappers (MT5Account, MT5Service, MT5Sugar)
│   │   ├── MT5Account.class
│   │   ├── MT5Service.class
│   │   └── MT5Sugar.class
│   ├── examples/                     # YOUR examples
│   ├── orchestrators/                # YOUR orchestrators
│   └── presets/                      # YOUR presets
└── maven-status/                     # Maven build metadata
```

**Important:** This shows ONLY your local code that gets compiled from `src/main/java/`.

**Proto classes are NOT here!** The gRPC proto classes (`mt5_term_api.*`, `mrpc.*`) come from the pre-compiled MetaRPC JAR downloaded from JitPack and stored in your Maven cache:

```
~/.m2/repository/com/github/MetaRPC/JavaMT5/-SNAPSHOT/
└── JavaMT5--SNAPSHOT.jar            # Contains all proto-generated classes
    ├── mt5_term_api/                # Proto classes (NOT in your target/)
    │   ├── AccountInformation$...class
    │   ├── Connection$...class
    │   ├── TradeFunctions$...class
    │   └── ...
    └── mrpc/
        └── ...
```

Your code (`MT5Account`, `MT5Service`, `MT5Sugar`) imports these proto classes from the JAR, not from `target/`.

### Build Steps (What `mvnd compile` Does)

1. **Download dependencies** (first run only)
   - MetaRPC JAR from JitPack (contains all proto classes)
   - gRPC runtime, Protobuf runtime, Gson
   - All cached in `~/.m2/repository/`
   - **Your code does NOT compile proto files** - they're already compiled in the JAR!

2. **Compile only YOUR local code**
   - `src/main/java/**/*.java` → `target/classes/**/*.class`
   - This includes: MT5Account, MT5Service, MT5Sugar, examples, orchestrators, presets
   - Proto classes are imported from the MetaRPC JAR, not compiled locally

3. **Copy resources**
   - `appsettings.json` → `target/classes/`

### Why Delete `target/` Fixes Everything

When you delete `target/`, Maven **must** recompile everything from scratch:

- No cached `.class` files → everything recompiled fresh
- No stale compiled code → clean compilation
- No daemon confusion → clean state
- Dependencies remain in `~/.m2/` → no re-download needed

This is why it's the #1 troubleshooting step for Java projects!

### How `run.bat` Works

When you run `run.bat 7`:

```bash
# 1. run.bat calls:
mvnd compile exec:java -Dexec.args="7"

# 2. This does:
- Compile: Build all Java files in src/main/java/
- exec:java: Run Program.java with argument "7"

# 3. Program.java routes to SimpleTradingScenario
# 4. Your code executes
```

**That's it!** Maven handles all dependencies automatically:
- Downloads MetaRPC library from JitPack (first run only)
- Sets up classpath with all required JARs
- Compiles and runs your code

---

## Pro Tips

### Faster Development Workflow

```bash
# Kill processes + clean rebuild in one go
run.bat stop && rmdir /s /q target && run.bat 7
```

### Check What's Running

```bash
# Windows - See all Java processes
tasklist | findstr java

# See Maven daemon status
mvnd --status
```

### Multiple Terminal Workflow

If you're testing multiple examples:

1. **Terminal 1:** Run first example
   ```bash
   run.bat 10 1
   ```

2. **Terminal 2:** Clean and run different example
   ```bash
   run.bat stop
   rmdir /s /q target
   run.bat 11 2
   ```

This way you don't interfere with running examples.

---

## Summary

| Problem | Solution |
|---------|----------|
| Script hangs or doesn't run | `taskkill /F /IM java.exe` + `run.bat stop` |
| Compilation errors | Delete `target/` folder, next `run.bat` rebuilds |
| MT5 connection errors | Check MT5 terminal and MetaRPC server are running |
| Want clean build | Delete `target/`, run.bat will regenerate everything |

**Remember:** Deleting `target/` is safe! It's just compiled files. Next `run.bat` will rebuild automatically.

---

## Need More Help?

- See [Program.java](../src/main/java/Program.java) header for all commands
- Check [GETTING_STARTED.md](GETTING_STARTED.md) for initial setup
- Review [PROJECT_MAP.md](PROJECT_MAP.md) for codebase structure
