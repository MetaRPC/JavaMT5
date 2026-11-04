# MT5 Return Codes (RetCodes) Reference (Java)

## 🛡️ What is RetCode?

**RetCode** (Return Code) is a status code the MT5 terminal returns after a trading operation. It
indicates success or the reason for failure.

RetCodes **appear only in trading methods** because only trading operations go through the broker
and can be rejected (insufficient margin, invalid price, market closed, etc.).

### Quick Example: proper RetCode handling

```java
var result = service.orderSend(request);   // OrderSendData

// ✅ ALWAYS check the returned code explicitly
if (result.getReturnedCode() == 10009)     // TRADE_RETCODE_DONE
    System.out.println("✓ Trade successful! Order #" + result.getOrder());
else
    System.out.println("✗ Trade failed: " + result.getReturnedCodeDescription()
        + " (code " + result.getReturnedCode() + ")");
```

**Key point:** no exception = the API call worked, but you still **check the returned code** to know
whether the **trade** succeeded.

---

## RetCode Source

RetCodes are **standard MQL5 codes** defined in official MetaQuotes documentation:

**MQL5 Documentation**: [Trade Server Return Codes (ENUM_TRADE_RETCODE)](https://www.mql5.com/en/docs/constants/errorswarnings/enum_trade_return_codes)

These codes are:

- **Unified across all languages** (Java, C#, Python, Node.js, Go, PHP)
- **Unified with the MT5 terminal** — returned directly from the trading server
- **Defined in protobuf** — delivered as the `int` field `returnedCode` in trading result messages
- **Paired with text** — the reply also carries `returnedCodeDescription`, a ready-made
  human-readable message (no helper lookup needed)

---

## 📥 Where is RetCode used in the API?

RetCode is returned by **all trading operations** that modify positions or orders. At the low level
you read it off the `…Data` message; the **Sugar** layer checks it for you and throws
`ApiExceptionMT5` when it isn't a success code.

### 1. orderSend (opening orders)

```java
import mt5_term_api.Mt5TermApiTradingHelper;

var request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
    .setSymbol("EURUSD")
    .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
    .setVolume(0.01)
    .setPrice(sugar.getAsk("EURUSD"))
    .build();

var result = service.orderSend(request);   // OrderSendData (mid-level, already unwrapped)
if (result.getReturnedCode() == 10009)
    System.out.println("[OK] Order opened! Ticket: " + result.getOrder());
else
    System.out.println("[X] Order failed: " + result.getReturnedCodeDescription());
```

> At the raw level, `account.orderSend(request)` returns an `OrderSendReply`; call `.getData()`
> to get the same `OrderSendData`.

### 2. Modifying SL/TP and closing (Sugar)

```java
sugar.modifyPosition(ticket, 1.0850, 1.0950);   // throws ApiExceptionMT5 if not a success code
sugar.closePosition(ticket);                      // same — success is implied by no exception
```

> **Exceptions vs codes.** The Sugar layer turns a non-success code into an `ApiExceptionMT5`. At
> the low level you instead read `getReturnedCode()` yourself. Handle **both** paths (see
> [Error handling](#error-handling)).

---

## 👁️ Why RetCode only in trading methods?

**Informational methods** (prices, symbols, balance) **do not** return a RetCode because:

- They don't go through the broker.
- They can't be "rejected" — either the data exists or there's a gRPC error.
- They read local terminal data.

**Trading methods** return a RetCode because:

- The request is sent to the broker via the trading server.
- The broker validates margin, symbol rules, trading hours, limits.
- The broker can reject for dozens of reasons — each with a unique code.

---

## Complete RetCode List

> The **MQL5 Name** column is the official `ENUM_TRADE_RETCODE` identifier (language-neutral). Java
> returns the raw `int`; if you prefer named constants, see [Defining constants in Java](#defining-constants-in-java).

### Success Codes

| Code | MQL5 Name | Description | When Returned |
|------|-----------|-------------|---------------|
| **10009** | `TRADE_RETCODE_DONE` | **Request completed successfully** | Market order opened/closed |
| **10010** | `TRADE_RETCODE_DONE_PARTIAL` | **Partial execution** | Only part of volume executed |
| **10008** | `TRADE_RETCODE_PLACED` | **Pending order placed** | Limit/Stop order placed |

### Requote Codes (retry recommended)

| Code | MQL5 Name | Description | Action |
|------|-----------|-------------|--------|
| **10004** | `TRADE_RETCODE_REQUOTE` | **Requote** | Price changed, retry request |
| **10020** | `TRADE_RETCODE_PRICE_CHANGED` | **Price changed** | Similar to requote, retry |

### ⚠️ Request Validation Errors

| Code | MQL5 Name | Description | Common Cause |
|------|-----------|-------------|--------------|
| **10013** | `TRADE_RETCODE_INVALID_REQUEST` | **Invalid request** | Incorrect parameters |
| **10014** | `TRADE_RETCODE_INVALID_VOLUME` | **Invalid volume** | Volume < Min or > Max |
| **10015** | `TRADE_RETCODE_INVALID_PRICE` | **Invalid price** | Price doesn't match symbol rules |
| **10016** | `TRADE_RETCODE_INVALID_STOPS` | **Invalid stops** | SL/TP too close to price (check StopLevel) |
| **10022** | `TRADE_RETCODE_INVALID_EXPIRATION` | **Invalid expiration** | Expiration time incorrect |
| **10030** | `TRADE_RETCODE_INVALID_FILL` | **Invalid order filling type** | Fill type not allowed |
| **10035** | `TRADE_RETCODE_INVALID_ORDER` | **Invalid order type** | Order type prohibited for symbol |
| **10038** | `TRADE_RETCODE_INVALID_CLOSE_VOLUME` | **Invalid close volume** | Close volume exceeds position volume |

### 🚫 Trading Restrictions

| Code | MQL5 Name | Description | Reason |
|------|-----------|-------------|--------|
| **10017** | `TRADE_RETCODE_TRADE_DISABLED` | **Trading disabled** | Trading disabled for symbol |
| **10018** | `TRADE_RETCODE_MARKET_CLOSED` | **Market closed** | Outside trading hours |
| **10026** | `TRADE_RETCODE_SERVER_DISABLES_AT` | **Autotrading disabled by server** | Server disabled auto-trading |
| **10027** | `TRADE_RETCODE_CLIENT_DISABLES_AT` | **Autotrading disabled by client** | Terminal disabled auto-trading |
| **10032** | `TRADE_RETCODE_ONLY_REAL` | **Only real accounts** | Action unavailable on demo |
| **10042** | `TRADE_RETCODE_LONG_ONLY` | **Only long positions allowed** | Short positions prohibited |
| **10043** | `TRADE_RETCODE_SHORT_ONLY` | **Only short positions allowed** | Long positions prohibited |
| **10044** | `TRADE_RETCODE_CLOSE_ONLY` | **Only position closing allowed** | Opening new positions prohibited |
| **10045** | `TRADE_RETCODE_FIFO_CLOSE` | **Position close only by FIFO rule** | FIFO rule enforced |
| **10046** | `TRADE_RETCODE_HEDGE_PROHIBITED` | **Opposite positions prohibited** | Hedging disabled |

### 💰 Resource Limits

| Code | MQL5 Name | Description | Solution |
|------|-----------|-------------|----------|
| **10019** | `TRADE_RETCODE_NO_MONEY` | **Insufficient funds** | Check free margin |
| **10033** | `TRADE_RETCODE_LIMIT_ORDERS` | **Pending orders limit** | Close some pending orders |
| **10034** | `TRADE_RETCODE_LIMIT_VOLUME` | **Volume limit** | Reduce position volume |
| **10040** | `TRADE_RETCODE_LIMIT_POSITIONS` | **Positions limit** | Close some positions |

### 🔄 Technical Issues (Retryable)

| Code | MQL5 Name | Description | Retryable |
|------|-----------|-------------|-----------|
| **10011** | `TRADE_RETCODE_ERROR` | **Processing error** | No |
| **10012** | `TRADE_RETCODE_TIMEOUT` | **Request timeout** | Yes |
| **10021** | `TRADE_RETCODE_NO_QUOTES` | **No quotes** | Yes |
| **10024** | `TRADE_RETCODE_TOO_MANY_REQUESTS` | **Rate limiting** | Yes (with delay) |
| **10028** | `TRADE_RETCODE_LOCKED` | **Request locked for processing** | Yes |
| **10029** | `TRADE_RETCODE_FROZEN` | **Order or position frozen** | Yes |
| **10031** | `TRADE_RETCODE_NO_CONNECTION` | **No connection** | Yes |

### State Management

| Code | MQL5 Name | Description | Meaning |
|------|-----------|-------------|---------|
| **10023** | `TRADE_RETCODE_ORDER_CHANGED` | **Order state changed** | Order already modified/closed |
| **10025** | `TRADE_RETCODE_NO_CHANGES` | **No changes** | New parameters = current parameters |
| **10036** | `TRADE_RETCODE_POSITION_CLOSED` | **Position closed** | Position doesn't exist |
| **10039** | `TRADE_RETCODE_CLOSE_ORDER_EXIST` | **Close order exists** | Cannot close more than position volume |

### ❌ Rejection Codes

| Code | MQL5 Name | Description | Reason |
|------|-----------|-------------|--------|
| **10006** | `TRADE_RETCODE_REJECT` | **Request rejected** | Broker rejected |
| **10007** | `TRADE_RETCODE_CANCEL` | **Request canceled** | Canceled by trader |
| **10041** | `TRADE_RETCODE_REJECT_CANCEL` | **Pending order activation rejected** | Activation rejected and canceled |

---

## Reading the result in Java

The SDK does **not** ship a helper module or named-constant class for RetCodes — you read them
directly off the `…Data` message:

```java
var result = service.orderSend(request);

System.out.println(result.getReturnedCode());            // e.g. 10018 (int)
System.out.println(result.getReturnedCodeDescription()); // e.g. "Market is closed" (server-provided)
```

`getReturnedCodeDescription()` already gives you the human-readable text, so there's no
`getRetcodeMessage()`-style lookup to call.

### When to explicitly check RetCode

**CRITICAL:** check the returned code after every low-level trading operation:

```java
// ❌ WRONG — no exception does NOT mean the trade succeeded
var result = service.orderSend(request);
System.out.println("Order placed: " + result.getOrder()); // Order may be 0 if rejected!

// ✅ CORRECT — always check the returned code
var result = service.orderSend(request);
if (result.getReturnedCode() == 10009)
    System.out.println("✓ Order #" + result.getOrder());
else
    System.out.println("✗ " + result.getReturnedCodeDescription() + " (code " + result.getReturnedCode() + ")");
```

Because: no exception means the **API call** succeeded (connection OK, terminal responded) — it does
**not** mean the **trade** succeeded. The returned code is the actual trade result.

### Defining constants in Java

If you dislike magic numbers, drop a small helper into your project:

```java
public final class TradeRetcode {
    public static final int DONE = 10009, DONE_PARTIAL = 10010, PLACED = 10008;
    public static final int REQUOTE = 10004, PRICE_CHANGED = 10020;
    public static final int NO_MONEY = 10019, MARKET_CLOSED = 10018,
                            INVALID_STOPS = 10016, INVALID_VOLUME = 10014;

    public static boolean isSuccess(int c)   { return c == DONE || c == PLACED; }
    public static boolean isRequote(int c)   { return c == REQUOTE || c == PRICE_CHANGED; }
    public static boolean isRetryable(int c) {
        return c == 10012 || c == 10021 || c == 10024 || c == 10028 || c == 10029 || c == 10031;
    }
    private TradeRetcode() {}
}

// Usage
if (TradeRetcode.isSuccess(result.getReturnedCode())) { /* ... */ }
```

---

## Error handling

Two independent things can go wrong — handle **both**:

```java
import io.grpc.StatusRuntimeException;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;

try {
    var result = service.orderSend(request);

    if (result.getReturnedCode() == 10009)
        System.out.println("[OK] Order #" + result.getOrder());
    else
        System.out.println("[X] Trade rejected: " + result.getReturnedCodeDescription()
            + " (code " + result.getReturnedCode() + ")");
} catch (ApiExceptionMT5 ex) {        // business error from the server
    System.out.println("[X] API error: " + ex.getErrorCode() + " — " + ex.getErrorMessage());
} catch (StatusRuntimeException ex) { // transport/network error (e.g. UNAVAILABLE / 502)
    System.out.println("[X] gRPC error: " + ex.getStatus());
}
```

> `ApiExceptionMT5` exposes `getErrorCode()` (string error code) and `getErrorMessage()`.
> Transport problems surface as `io.grpc.StatusRuntimeException` — for example
> `UNAVAILABLE: HTTP status code 502` when the gateway is unreachable.

---

## Usage Examples

### Example 1 — Basic order placement with RetCode check

```java
import mt5_term_api.Mt5TermApiTradingHelper;
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.MT5Service;

try (MT5Account account = new MT5Account(12345678L, "your_password", "mt5.mrpc.pro:443", apiKey)) {
    account.connectByServerName("MetaQuotes-Demo", "EURUSD", 120);
    MT5Service service = new MT5Service(account);

    var request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
        .setSymbol("EURUSD")
        .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
        .setVolume(0.01)
        .setPrice(service.getAsk("EURUSD"))
        .build();

    var result = service.orderSend(request);
    if (result.getReturnedCode() == 10009)
        System.out.printf("[OK] Order #%d, volume %.2f, price %.5f%n",
            result.getOrder(), result.getVolume(), result.getPrice());
    else
        System.out.println("[X] " + result.getReturnedCodeDescription()
            + " (code " + result.getReturnedCode() + ")");
} finally {
    // try-with-resources closes the account (AutoCloseable)
}
```

### Example 2 — Handling common errors (switch)

```java
var result = service.orderSend(request);

switch (result.getReturnedCode()) {
    case 10009 ->
        System.out.println("[OK] Order #" + result.getOrder());
    case 10019 -> // NO_MONEY
        System.out.println("[WARN] Insufficient funds — reduce volume or add margin");
    case 10018 -> // MARKET_CLOSED
        System.out.println("[WARN] Market closed — try during trading hours");
    case 10016 -> // INVALID_STOPS
        System.out.println("[WARN] SL/TP too close — increase distance (check StopLevel)");
    case 10014 -> { // INVALID_VOLUME
        double[] v = sugar.getVolumeLimits("EURUSD");   // [min, max, step]
        System.out.printf("[WARN] Invalid volume. Min %.2f, Max %.2f, Step %.2f%n", v[0], v[1], v[2]);
    }
    case 10004, 10020 -> // REQUOTE / PRICE_CHANGED
        System.out.println("[WARN] Price changed — retry");
    default ->
        System.out.println("[X] " + result.getReturnedCodeDescription()
            + " (code " + result.getReturnedCode() + ")");
}
```

### Example 3 — Retry logic for transient errors

```java
OrderSendData placeWithRetry(MT5Service service, OrderSendRequest request, int maxRetries)
        throws Exception {
    for (int attempt = 1; attempt <= maxRetries; attempt++) {
        var result = service.orderSend(request);

        if (result.getReturnedCode() == 10009) return result;              // success

        if (result.getReturnedCode() == 10004 || result.getReturnedCode() == 10020) { // requote
            System.out.println("Requote on attempt " + attempt + ", retrying...");
            Thread.sleep(100);
            request = request.toBuilder().setPrice(service.getAsk(request.getSymbol())).build();
            continue;
        }

        if (TradeRetcode.isRetryable(result.getReturnedCode())) {          // transient
            System.out.println("Transient error on attempt " + attempt + ", backing off...");
            Thread.sleep(attempt * 1000L);                                 // simple backoff
            continue;
        }

        throw new IllegalStateException("Permanent error: "
            + result.getReturnedCodeDescription() + " (" + result.getReturnedCode() + ")");
    }
    throw new IllegalStateException("Failed after " + maxRetries + " attempts");
}
```

### Example 4 — Modifying SL/TP via Sugar

```java
try {
    sugar.modifyPosition(123456L, 1.0850, 1.0950);   // throws if the server rejects it
    System.out.println("[OK] SL/TP updated");
} catch (ApiExceptionMT5 ex) {
    // e.g. INVALID_STOPS (10016) or NO_CHANGES (10025) surfaced as an exception
    System.out.println("[X] " + ex.getErrorCode() + " — " + ex.getErrorMessage());
}
```

---

## Best Practices

### ✅ DO

1. **Always check the returned code** after low-level trading operations.
2. **Use `getReturnedCodeDescription()`** for the message — it's already human-readable.
3. **Retry requotes** (10004, 10020) after refreshing the price.
4. **Check margin before trading** to avoid 10019:
   ```java
   double freeMargin = sugar.getFreeMargin();
   if (freeMargin < requiredMargin) System.out.println("Insufficient margin!");
   ```

### ❌ DON'T

1. **Don't assume no exception = success** — check the returned code too.
2. **Don't retry permanent errors** (insufficient margin, market closed).
3. **Don't swallow `ApiExceptionMT5` / `StatusRuntimeException`** silently.
4. **Don't hard-code bare numbers everywhere** — centralize them (see [Defining constants in Java](#defining-constants-in-java)).

---

## ℹ️ Quick Reference Table

| Category | RetCodes | Action |
|----------|----------|--------|
| **Success** | 10008, 10009, 10010 | Continue normally |
| **Requote** | 10004, 10020 | Retry immediately (refresh price) |
| **Temporary** | 10012, 10021, 10024, 10028, 10029, 10031 | Retry with delay |
| **Validation** | 10013–10016, 10022, 10030, 10035, 10038 | Fix parameters |
| **Restrictions** | 10017, 10018, 10026, 10027, 10032, 10042–10046 | Check trading conditions |
| **Limits** | 10019, 10033, 10034, 10040 | Reduce volume/positions |
| **State** | 10023, 10025, 10036, 10039 | Check current state |
| **Rejection** | 10006, 10007, 10041 | Check request |
| **Technical** | 10011 | Contact support |

---

## See Also

- [User Code Sandbox Guide](USERCODE_SANDBOX_GUIDE.md) — where to write and test trading code
- [Enums Usage Reference](ENUMS_USAGE_REFERENCE.md) — order-type and other enums used in requests
- [MT5Account — Trading](../MT5Account/5.%20Trading/OrderSend.md) — OrderSend/Modify/Close reference
