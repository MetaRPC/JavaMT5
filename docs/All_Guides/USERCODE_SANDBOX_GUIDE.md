# User Code Sandbox Guide

## What is this?

This is **your sandbox** for writing custom MT5 trading code in **Java**. Connection setup,
config loading and cleanup are already done — you only add your trading logic in the
**"YOUR CODE HERE"** section.

**File:** `src/main/java/examples/usercode/UserCode.java`

## 🏁 Quick Start

1. **Open `src/main/java/examples/usercode/UserCode.java`** — write your code, or uncomment one
   of the ready examples.
2. **Run:**
   ```bash
   run.bat 0          # Windows CMD   (or: .\run.bat 0 in PowerShell)
   ```

That's it — your code runs with a live MT5 connection and disconnects cleanly in `finally`.

> Unlike some sibling SDKs, you do **not** need to enable a case in `Program.java` — the `0` /
> `usercode` command is already wired.

## How to use

### Option 1 — Uncomment examples

`UserCode.java` ships with ready-to-use examples. Just remove the `//`:

```java
// --- Example 2: current price for a symbol ---
// System.out.printf("EURUSD  bid=%.5f  ask=%.5f%n",
//     sugar.getBid("EURUSD"), sugar.getAsk("EURUSD"));
```

### Option 2 — Write your own

Add your logic between the markers:

```java
// =================================================================
// YOUR CODE HERE
// =================================================================

// Your trading strategy here...

// =================================================================
// END OF YOUR CODE
// =================================================================
```

## Available commands

```bash
run.bat 0
run.bat usercode
run.bat user
run.bat sandbox
```

All four run the same `examples.usercode.UserCode` class.

## What's already configured

- **Connection** — MT5 terminal connected via gRPC (`account.connectByServerName(...)`)
- **Configuration** — loaded from `appsettings.json` (the `DefaultConnection` section)
- **`account`** — low-level gRPC client (`MT5Account`) — returns protobuf `…Reply` objects
- **`service`** — mid-level wrapper (`MT5Service`) — unwraps `.getData()` and returns `…Data` / primitives
- **`sugar`** — high-level convenience (`MT5Sugar`) — auto-normalization, risk sizing, batch ops
- **Cleanup** — `account.disconnect()` + `account.close()` run automatically in `finally`

> All three layers are plain **synchronous** calls — no `async`/`await`, no `Future`. Each call
> blocks until the server answers (or throws `ApiExceptionMT5`).

## 🧩 Can I mix API levels?

**Yes** — all three variables are live in the same block, so you can drop down a layer whenever
you need more control:

```java
// Low-level (direct gRPC): full protobuf Reply, unwrap with getData()
double balanceLow = account.accountSummary().getData().getAccountBalance();

// Mid-level (MT5Service): already unwrapped to a primitive
double balanceSvc = service.getBalance();

// High-level (Sugar): same value, plus convenience everywhere
double balanceHi  = sugar.getBalance();
```

- `account` — full control (gRPC protobuf `…Reply`)
- `service` — convenient unwrapped data
- `sugar` — batteries-included helpers

## Quick reference

### Get account information

```java
// High-level shortcuts (easiest)
double balance = sugar.getBalance();
double equity  = sugar.getEquity();
System.out.printf("Balance: %.2f | Equity: %.2f%n", balance, equity);

// Full snapshot (one call)
var summary = service.accountSummary();   // AccountSummaryData (already unwrapped)
System.out.printf("Balance: %.2f %s%n",
    summary.getAccountBalance(), summary.getAccountCurrency());
```

### Get current price

```java
double bid = sugar.getBid("EURUSD");
double ask = sugar.getAsk("EURUSD");
System.out.printf("EURUSD: bid=%.5f ask=%.5f%n", bid, ask);

// Or the full tick via the mid-level wrapper
var tick = service.symbolInfoTick("EURUSD");   // MrpcMqlTick
System.out.printf("bid=%.5f ask=%.5f%n", tick.getBid(), tick.getAsk());
```

### Open a market order

```java
// Simple market BUY (0.01 lot, no SL/TP)
long ticket = sugar.buyMarket("EURUSD", 0.01, null, null);
System.out.println("Opened position #" + ticket);

// Market BUY with SL/TP (absolute prices)
long t2 = sugar.buyMarket("EURUSD", 0.01, 1.0800, 1.0900);

// Risk-based sizing: 500-point stop, risk $10, 1000-point TP
long t3 = sugar.buyByRisk("EURUSD", 500, 10.0, 1000);
System.out.println("Opened by-risk position #" + t3);
```

> Sugar trading methods **throw `ApiExceptionMT5`** if the server rejects the order; on success
> they return the position/order ticket. See [Error handling](#error-handling) below.

### Place a pending order (points-based)

```java
// Buy Limit 50 points below Ask, SL 100 / TP 200 points
sugar.buyLimitPoints("EURUSD", 0.01, -50, 100, 200);

// Sell Stop 50 points below Bid
sugar.sellStopPoints("EURUSD", 0.01, -50, 100, 200);
```

### Get open positions

```java
int count = sugar.getPositionCount();
System.out.println("Open positions: " + count);
boolean any = sugar.hasOpenPositions();
```

### Close positions

```java
sugar.closePosition(ticket);        // specific position (full)
sugar.closeAll("EURUSD");           // all positions for a symbol
sugar.closeAllBuy(null);            // all BUY positions (all symbols)
sugar.cancelAll();                  // cancel all pending orders
```

### Modify a position

```java
sugar.modifyPosition(ticket, 1.0850, 1.0950);   // new SL / TP
```

### Calculate position size by risk

```java
double volume = sugar.calculateVolume("EURUSD", 200, 10.0);  // 200-pt SL, $10 risk
System.out.printf("Volume for $10 risk with 200-point SL: %.2f lot%n", volume);
long ticket = sugar.buyMarket("EURUSD", volume, null, null);
```

## Return Codes (RetCodes)

When you drop to the low-level API, **always check the returned code** after a trade:

```java
import mt5_term_api.Mt5TermApiTradingHelper.OrderSendData;

var reply = account.orderSend(request);           // OrderSendReply
OrderSendData data = reply.getData();
int code = data.getReturnedCode();

if (code == 10009)            // market order executed
    System.out.println("[OK] Order #" + data.getOrder());
else if (code == 10008)       // pending order placed
    System.out.println("[OK] Pending order #" + data.getOrder());
else
    System.out.println("[X] Failed: " + data.getReturnedCodeDescription() + " (code " + code + ")");
```

**Common RetCodes:**

- `10009` — request completed / market order executed
- `10008` — pending order placed
- `10019` — not enough money (insufficient margin)
- `10016` — invalid stops (SL/TP too close to price)
- `10018` — market closed

Full list: [RETURN_CODES_REFERENCE.md](RETURN_CODES_REFERENCE.md)

> The **Sugar** layer checks this for you — it throws `ApiExceptionMT5` when the code isn't a
> success code, so you don't have to inspect it by hand.

## Error handling

Two independent things can go wrong — handle **both**:

### 1. Connection / server errors → `ApiExceptionMT5`

```java
try {
    long ticket = sugar.buyMarket("EURUSD", 0.01, null, null);
} catch (ApiExceptionMT5 ex) {
    System.err.println("API error: " + ex.getMessage());
}
```

### 2. Trade rejections (low-level) → returned code

```java
var reply = account.orderSend(request);
int code = reply.getData().getReturnedCode();
if (code != 10009)
    System.out.println("Trade rejected: " + reply.getData().getReturnedCodeDescription());
```

### Recommended pattern

```java
try {
    long ticket = sugar.buyMarket("EURUSD", 0.01, null, null);
    System.out.println("[OK] Position #" + ticket);
} catch (ApiExceptionMT5 ex) {
    System.err.println("[X] " + ex.getMessage());
}
```

The sandbox already wraps your whole block in `try/catch (ApiExceptionMT5 | Exception)` and cleans
up in `finally`, so an uncaught error won't leak the connection.

## 🔑 Configuration

Credentials come from `appsettings.json` in the project root:

```json
{
  "MT5Connections": {
    "FxProDemo": {
      "user": 123456,
      "password": "your_password",
      "serverName": "MetaQuotes-Demo",
      "baseSymbol": "EURUSD",
      "apiKey": "TRIAL"
    }
  },
  "DefaultConnection": "FxProDemo"
}
```

**API key override (keeps real keys out of source control):**

```bash
# Pass the key on the command line instead of editing appsettings.json
run.bat 0 --api-key=your-api-key

# Or via environment variable
set MRPC_API_KEY=your-api-key   # Windows CMD
run.bat 0
```

## Tips

1. **Start simple** — uncomment one example at a time.
2. **Use Sugar first** — `buyByRisk(...)` and `calculateVolume(...)` are easier than hand-building requests.
3. **Trust the exception** — Sugar throws `ApiExceptionMT5` on rejection, so a returned ticket already means success.
4. **Test on demo** — make sure you're on a demo account first.
5. **Read the return codes** — [RETURN_CODES_REFERENCE.md](RETURN_CODES_REFERENCE.md) explains every code.
6. **Mix layers freely** — `account` / `service` / `sugar` all point at the same connection.
7. **Browse examples** — see `src/main/java/examples/` (lowlevel, services, sugar).

## Common mistakes

❌ **Assuming a rejected order threw nothing**
```java
long t = sugar.buyMarket("EURUSD", 0.01, null, null); // throws on rejection — don't ignore it
```

✅ **Wrap in try/catch**
```java
try { long t = sugar.buyMarket("EURUSD", 0.01, null, null); }
catch (ApiExceptionMT5 ex) { System.err.println("Rejected: " + ex.getMessage()); }
```

❌ **Not checking the code at the low level**
```java
var r = account.orderSend(request); // assuming success!
```

✅ **Check it**
```java
var r = account.orderSend(request);
if (r.getData().getReturnedCode() != 10009) {
    System.out.println("Failed: " + r.getData().getReturnedCodeDescription());
    return;
}
```

## 📝 Documentation

- [MT5Account Master Overview](../MT5Account/MT5Account.Master.Overview.md) — low-level API reference
- [MT5Service Overview](../MT5Service/MT5Service.Overview.md) — mid-level wrappers
- [MT5Sugar Overview](../MT5Sugar/MT5Sugar.Overview.md) — high-level convenience helpers
- [RETURN_CODES_REFERENCE.md](RETURN_CODES_REFERENCE.md) — every trade return code
- [ENUMS_USAGE_REFERENCE.md](ENUMS_USAGE_REFERENCE.md) — enums in one place

## Example: complete sandbox body

A minimal strategy with proper error handling (the connection scaffolding is already provided by
`UserCode.main`):

```java
// account (MT5Account), service (MT5Service) and sugar (MT5Sugar) are already connected.

// 1) Account state
double balance = sugar.getBalance();
System.out.printf("Balance: %.2f%n", balance);

// 2) Size by risk: 1% of balance, 50-point stop
double risk   = balance * 0.01;
double volume = sugar.calculateVolume("EURUSD", 50, risk);

// 3) Open and report
try {
    long ticket = sugar.buyMarket("EURUSD", volume, null, null);
    System.out.printf("[OK] Position #%d with %.2f lot%n", ticket, volume);
} catch (ApiExceptionMT5 ex) {
    System.out.println("[X] " + ex.getMessage());
}
```

**Happy trading!**
