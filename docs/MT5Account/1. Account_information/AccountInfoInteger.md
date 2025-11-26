# ✅ Getting Individual Account Integer Properties

> **Request:** single `long` property from **MT5** account. Fetch specific integer properties like login, leverage, trade mode, limits, etc.

**API Information:**

* **SDK wrapper:** `MT5Account.accountInfoInteger(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountInformation`
* **Proto definition:** `AccountInfoInteger` (defined in `mt5-term-api-account-information.proto`)

### RPC

* **Service:** `mt5_term_api.AccountInformation`
* **Method:** `AccountInfoInteger(AccountInfoIntegerRequest) → AccountInfoIntegerReply`
* **Low‑level client (generated):** `AccountInformationGrpc.AccountInformationBlockingStub.accountInfoInteger(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Retrieves a specific integer property of the trading account.
     * Use this to get values such as LOGIN, LEVERAGE, TRADE_MODE, LIMIT_ORDERS, etc.
     *
     * @param propertyType The specific property to retrieve (e.g., ACCOUNT_LOGIN, ACCOUNT_LEVERAGE)
     * @return Response containing the requested integer value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountInformation.AccountInfoIntegerReply accountInfoInteger(
        Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType propertyType) throws ApiExceptionMT5;
}
```

**Request message:** `AccountInfoIntegerRequest { property_id: AccountInfoIntegerPropertyType }`
**Reply message:** `AccountInfoIntegerReply { data: AccountInfoIntegerData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type                               | Required | Description                       |
| -------------- | ---------------------------------- | -------- | --------------------------------- |
| `propertyType` | `AccountInfoIntegerPropertyType`   | ✅       | Property to retrieve (see enum below) |

---

## ⬆️ Output — `AccountInfoIntegerData`

| Field            | Type   | Description                 |
| ---------------- | ------ | --------------------------- |
| `requested_value` | `long` | The requested property value (int64) |

Access the value using `reply.getData().getRequestedValue()`.

**Important:** All properties return `long` (int64), including boolean flags where `1` = true and `0` = false.

---

## 🧱 Related enums (from proto)

### `AccountInfoIntegerPropertyType`

| Enum Value               | Value | Return Type                 | Description                                                                                                                                                                                                                                                                                                                                                                                                                                            | MQL5 Docs                                                        |
| ------------------------ | ----- | --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------- |
| `ACCOUNT_LOGIN`          | 0     | `long`                      | Account number                                                                                                                                                                                                                                                                                                                                                                                                                                         | [AccountInfoInteger](https://www.mql5.com/en/docs/account/accountinfointeger) |
| `ACCOUNT_TRADE_MODE`     | 1     | `int` (enum)                | Account trade mode: 0=Demo, 1=Contest, 2=Real                                                                                                                                                                                                                                                                                                                                                                                                          |                                                                  |
| `ACCOUNT_LEVERAGE`       | 2     | `long`                      | Account leverage (e.g., `100` for 1:100)                                                                                                                                                                                                                                                                                                                                                                                                              |                                                                  |
| `ACCOUNT_LIMIT_ORDERS`   | 3     | `int`                       | Maximum allowed number of open positions and active pending orders (in total). `0` = unlimited                                                                                                                                                                                                                                                                                                                                                        |                                                                  |
| `ACCOUNT_MARGIN_SO_MODE` | 4     | `int` (enum)                | Mode for setting the minimal allowed margin                                                                                                                                                                                                                                                                                                                                                                                                            |                                                                  |
| `ACCOUNT_TRADE_ALLOWED`  | 5     | `long` (1 or 0)             | Allowed trade for the current account: 1=allowed, 0=disabled                                                                                                                                                                                                                                                                                                                                                                                           |                                                                  |
| `ACCOUNT_TRADE_EXPERT`   | 6     | `long` (1 or 0)             | Allowed trade for an Expert Advisor: 1=allowed, 0=disabled                                                                                                                                                                                                                                                                                                                                                                                             |                                                                  |
| `ACCOUNT_MARGIN_MODE`    | 7     | `int` (enum)                | Margin calculation mode                                                                                                                                                                                                                                                                                                                                                                                                                                |                                                                  |
| `ACCOUNT_CURRENCY_DIGITS`| 8     | `int`                       | The number of decimal places in the account currency, which are required for an accurate display of trading results                                                                                                                                                                                                                                                                                                                                    |                                                                  |
| `ACCOUNT_FIFO_CLOSE`     | 9     | `long` (1 or 0)             | An indication showing that positions can only be closed by FIFO rule. If the property value is set to 1, then each symbol positions will be closed in the same order, in which they are opened, starting with the oldest one. In case of an attempt to close positions in a different order, the trader will receive an appropriate error.                                                                                                             |                                                                  |
| `ACCOUNT_HEDGE_ALLOWED`  | 10    | `long` (1 or 0)             | Allowed opposite positions on a single symbol (hedging): 1=allowed, 0=disabled                                                                                                                                                                                                                                                                                                                                                                         |                                                                  |

---

## 💬 Just the essentials

* **What it is.** Single RPC returning one specific `long` property of the account.
* **Why you need it.** When you only need one integer property (e.g., leverage, login, trade mode) instead of fetching the full account summary.
* **Performance.** Lightweight call — ideal for quick checks of specific properties.
* **Type note.** All properties return `long` (int64), even boolean flags (1 = true, 0 = false).
* **Alternative.** Use `accountSummary()` if you need multiple properties at once.

---

## 🎯 Purpose

Use this method when you need to:

* Check a single integer account property without fetching all account data.
* Verify account leverage before calculating position sizes.
* Check if trading is allowed (`ACCOUNT_TRADE_ALLOWED`, `ACCOUNT_TRADE_EXPERT`).
* Determine account trade mode (demo/contest/real).
* Check position limits (`ACCOUNT_LIMIT_ORDERS`).
* Verify FIFO close rules or hedging permissions.

---

## 🧩 Notes & Tips

* Prefer `accountSummary()` if you need multiple properties — it's more efficient to fetch all data in one call.
* Boolean properties return `1` (true) or `0` (false) as `long` values.
* The method uses automatic reconnection via `executeWithReconnect()` to handle transient gRPC errors.
* All exceptions are wrapped in `ApiExceptionMT5` for consistent error handling.
* The method is thread-safe and can be called from multiple threads.

---

## 🔗 Usage Examples

### 1) Get account login

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountInformation.*;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Retrieve account login number
            AccountInfoIntegerReply reply = account.accountInfoInteger(
                AccountInfoIntegerPropertyType.ACCOUNT_LOGIN
            );
            long login = reply.getData().getRequestedValue();

            System.out.printf("Account login: %d%n", login);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Check account leverage

```java
// Get current leverage setting
var reply = account.accountInfoInteger(
    AccountInfoIntegerPropertyType.ACCOUNT_LEVERAGE
);
long leverage = reply.getData().getRequestedValue();

System.out.printf("Account leverage: 1:%d%n", leverage);
```

### 3) Verify trading is allowed

```java
// Check if trading is enabled for this account
var tradeAllowedReply = account.accountInfoInteger(
    AccountInfoIntegerPropertyType.ACCOUNT_TRADE_ALLOWED
);
long tradeAllowed = tradeAllowedReply.getData().getRequestedValue();

if (tradeAllowed == 0) {
    System.out.println("⚠️ Trading is disabled for this account");
    return;
}

// Check if EA trading is allowed
var expertAllowedReply = account.accountInfoInteger(
    AccountInfoIntegerPropertyType.ACCOUNT_TRADE_EXPERT
);
long expertAllowed = expertAllowedReply.getData().getRequestedValue();

if (expertAllowed == 0) {
    System.out.println("⚠️ Expert Advisor trading is disabled");
}
```

### 4) Get account trade mode

```java
// Determine if account is demo, contest, or real
var reply = account.accountInfoInteger(
    AccountInfoIntegerPropertyType.ACCOUNT_TRADE_MODE
);
long tradeMode = reply.getData().getRequestedValue();

String modeLabel = switch ((int) tradeMode) {
    case 0 -> "Demo";
    case 1 -> "Contest";
    case 2 -> "Real";
    default -> "Unknown";
};

System.out.println("Account mode: " + modeLabel);
```

### 5) Check position limits

```java
// Get maximum allowed positions + pending orders
var reply = account.accountInfoInteger(
    AccountInfoIntegerPropertyType.ACCOUNT_LIMIT_ORDERS
);
long limitOrders = reply.getData().getRequestedValue();

if (limitOrders == 0) {
    System.out.println("✅ No position limits");
} else {
    System.out.printf("⚠️ Max positions + pending orders: %d%n", limitOrders);
}
```

### 6) Check hedging and FIFO rules

```java
// Check if hedging is allowed
var hedgeReply = account.accountInfoInteger(
    AccountInfoIntegerPropertyType.ACCOUNT_HEDGE_ALLOWED
);
boolean hedgingAllowed = hedgeReply.getData().getRequestedValue() == 1;

// Check if FIFO close is required
var fifoReply = account.accountInfoInteger(
    AccountInfoIntegerPropertyType.ACCOUNT_FIFO_CLOSE
);
boolean fifoRequired = fifoReply.getData().getRequestedValue() == 1;

System.out.println("Hedging allowed: " + hedgingAllowed);
System.out.println("FIFO close required: " + fifoRequired);

if (fifoRequired) {
    System.out.println("⚠️ Positions must be closed in FIFO order");
}
```

### 7) Helper method for cleaner code

```java
public class AccountHelper {
    private final MT5Account account;

    public AccountHelper(MT5Account account) {
        this.account = account;
    }

    /**
     * Helper to get integer property directly
     */
    public long getIntegerProperty(AccountInfoIntegerPropertyType property)
            throws ApiExceptionMT5 {
        var reply = account.accountInfoInteger(property);
        return reply.getData().getRequestedValue();
    }

    /**
     * Check if trading is allowed
     */
    public boolean isTradingAllowed() throws ApiExceptionMT5 {
        return getIntegerProperty(AccountInfoIntegerPropertyType.ACCOUNT_TRADE_ALLOWED) == 1;
    }

    /**
     * Check if EA trading is allowed
     */
    public boolean isExpertTradingAllowed() throws ApiExceptionMT5 {
        return getIntegerProperty(AccountInfoIntegerPropertyType.ACCOUNT_TRADE_EXPERT) == 1;
    }

    /**
     * Get account trade mode as string
     */
    public String getTradeMode() throws ApiExceptionMT5 {
        long mode = getIntegerProperty(AccountInfoIntegerPropertyType.ACCOUNT_TRADE_MODE);
        return switch ((int) mode) {
            case 0 -> "Demo";
            case 1 -> "Contest";
            case 2 -> "Real";
            default -> "Unknown";
        };
    }

    /**
     * Check if hedging is allowed
     */
    public boolean isHedgingAllowed() throws ApiExceptionMT5 {
        return getIntegerProperty(AccountInfoIntegerPropertyType.ACCOUNT_HEDGE_ALLOWED) == 1;
    }
}

// Usage
AccountHelper helper = new AccountHelper(account);

if (!helper.isTradingAllowed()) {
    System.out.println("Trading is disabled!");
    return;
}

System.out.println("Trade mode: " + helper.getTradeMode());
System.out.println("Hedging allowed: " + helper.isHedgingAllowed());
```

### 8) Pre-trade validation

```java
public class TradeValidator {
    /**
     * Validate account permissions before trading
     */
    public static boolean validateAccountForTrading(MT5Account account)
            throws ApiExceptionMT5 {

        // Check if manual trading is allowed
        var tradeReply = account.accountInfoInteger(
            AccountInfoIntegerPropertyType.ACCOUNT_TRADE_ALLOWED
        );
        if (tradeReply.getData().getRequestedValue() == 0) {
            System.out.println("❌ Manual trading is disabled");
            return false;
        }

        // Check if EA trading is allowed
        var expertReply = account.accountInfoInteger(
            AccountInfoIntegerPropertyType.ACCOUNT_TRADE_EXPERT
        );
        if (expertReply.getData().getRequestedValue() == 0) {
            System.out.println("❌ Expert Advisor trading is disabled");
            return false;
        }

        // Check trade mode
        var modeReply = account.accountInfoInteger(
            AccountInfoIntegerPropertyType.ACCOUNT_TRADE_MODE
        );
        long mode = modeReply.getData().getRequestedValue();
        String modeStr = mode == 0 ? "Demo" : mode == 1 ? "Contest" : "Real";
        System.out.printf("✅ Account mode: %s%n", modeStr);

        // Get leverage
        var leverageReply = account.accountInfoInteger(
            AccountInfoIntegerPropertyType.ACCOUNT_LEVERAGE
        );
        long leverage = leverageReply.getData().getRequestedValue();
        System.out.printf("✅ Leverage: 1:%d%n", leverage);

        return true;
    }
}

// Usage
if (TradeValidator.validateAccountForTrading(account)) {
    System.out.println("✅ Account validated - ready to trade");
} else {
    System.out.println("❌ Account validation failed");
}
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiAccountInformation.AccountInfoIntegerRequest request =
    Mt5TermApiAccountInformation.AccountInfoIntegerRequest.newBuilder()
        .setPropertyId(Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType.ACCOUNT_LOGIN)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountInformation.AccountInfoIntegerReply reply = accountInformationClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .accountInfoInteger(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
long value = reply.getData().getRequestedValue();
```
