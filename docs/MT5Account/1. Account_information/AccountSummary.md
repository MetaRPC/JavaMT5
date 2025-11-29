# ✅ Getting an Account Summary

> **Request:** full account summary (`AccountSummaryData`) from **MT5**. Fetch all core account metrics in a single call.

**API Information:**

* **SDK wrapper:** `MT5Account.accountSummary()` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountHelper`
* **Proto definition:** `AccountSummary` (defined in `mt5-term-api-account-helper.proto`)

### RPC

* **Service:** `mt5_term_api.AccountHelper`
* **Method:** `AccountSummary(AccountSummaryRequest) → AccountSummaryReply`
* **Low‑level client (generated):** `AccountHelperGrpc.AccountHelperBlockingStub.accountSummary(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets the complete summary of the trading account in a single call.
     * Returns all essential account information including balance, equity, margin, profit, leverage, and currency.
     * This is the recommended method for retrieving account data as it minimizes network calls.
     *
     * @return Account summary containing all account properties
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.AccountSummaryReply accountSummary() throws ApiExceptionMT5;
}
```

**Request message:** `AccountSummaryRequest {}`

**Reply message:** `AccountSummaryReply { data: AccountSummaryData }` or `{ error: Error }`

---

## 🔽 Input

No required parameters for the method call itself (internally passes empty `AccountSummaryRequest`).

The method automatically uses connection metadata (instance ID) established during `connect()` or `connectByServerName()`.

---

## ⬆️ Output - `AccountSummaryData`

| Field                                | Type                         | Description                                       |
| ------------------------------------ | ---------------------------- | ------------------------------------------------- |
| `account_login`                      | `long`                       | Trading account login (ID)                        |
| `account_balance`                    | `double`                     | Balance excluding floating P/L                    |
| `account_equity`                     | `double`                     | Equity = balance + floating P/L                   |
| `account_user_name`                  | `String`                     | Account holder name                               |
| `account_leverage`                   | `long`                       | Leverage (e.g., `100` for 1:100)                  |
| `account_trade_mode`                 | `MrpcEnumAccountTradeMode`   | Account type: DEMO, CONTEST, or REAL              |
| `account_company_name`               | `String`                     | Broker/company display name                       |
| `account_currency`                   | `String`                     | Deposit currency (e.g., `USD`, `EUR`)             |
| `server_time`                        | `Timestamp`                  | Server time (UTC) at response                     |
| `utc_timezone_server_time_shift_minutes` | `long`                   | Server offset relative to UTC (minutes)           |
| `account_credit`                     | `double`                     | Credit amount                                     |

---

## 🧱 Related enums (from proto)

### `MrpcEnumAccountTradeMode`

| Enum Value                        | Value | Description           |
| --------------------------------- | ----- | --------------------- |
| `MRPC_ACCOUNT_TRADE_MODE_DEMO`    | 0     | Demo/practice account |
| `MRPC_ACCOUNT_TRADE_MODE_CONTEST` | 1     | Contest account       |
| `MRPC_ACCOUNT_TRADE_MODE_REAL`    | 2     | Real trading account  |

---

## 💬 Just the essentials

* **What it is.** Single RPC returning account state: balance, equity, currency, leverage, trade mode, server time.
* **Why you need it.** Fast dashboard/CLI status; double‑check login/currency/leverage; heartbeat via `server_time`.
* **Sanity check.** If you see `account_login`, `account_currency`, `account_leverage`, `account_equity` → connection is alive.
* **Performance.** More efficient than calling individual `accountInfoDouble()`, `accountInfoInteger()`, `accountInfoString()` methods separately.

---

## 🎯 Purpose

Use it to display real‑time account state and sanity‑check connectivity:

* Dashboard/CLI status in one call.
* Verify equity & free margin before trading.
* Terminal heartbeat via `server_time` and `utc_timezone_server_time_shift_minutes`.
* Check account type (demo/real) before executing strategies.

---

## 🧩 Notes & Tips

* The method uses automatic reconnection via `executeWithReconnect()` wrapper to handle transient errors.
* All gRPC exceptions are converted to `ApiExceptionMT5` for consistent error handling.
* Use this method instead of multiple individual property calls when you need complete account state.
* The method is thread-safe and can be called from multiple threads.
* Server time is returned as protobuf `Timestamp` - convert using `timestamp.getSeconds()` for Unix timestamp.

---

## 🔗 Usage Examples

### 1) Basic account status

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            // Connect first
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get account summary
            Mt5TermApiAccountHelper.AccountSummaryReply reply = account.accountSummary();
            Mt5TermApiAccountHelper.AccountSummaryData data = reply.getData();

            System.out.printf("Account: %d%n", data.getAccountLogin());
            System.out.printf("Balance: %.2f %s%n", data.getAccountBalance(), data.getAccountCurrency());
            System.out.printf("Equity:  %.2f %s%n", data.getAccountEquity(), data.getAccountCurrency());
            System.out.printf("Leverage: 1:%d%n", data.getAccountLeverage());

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Compact status line for UI/CLI

```java
var reply = account.accountSummary();
var data = reply.getData();

String status = String.format(
    "Acc %d | %s | Bal %.2f | Eq %.2f | Lev 1:%d | Mode %s",
    data.getAccountLogin(),
    data.getAccountCurrency(),
    data.getAccountBalance(),
    data.getAccountEquity(),
    data.getAccountLeverage(),
    data.getAccountTradeMode().name()
);

System.out.println(status);
```

### 3) Human‑readable server time with timezone shift

```java
var reply = account.accountSummary();
var data = reply.getData();

// Convert protobuf Timestamp to Java Instant
long serverTimeSeconds = data.getServerTime().getSeconds();
java.time.Instant serverUtc = java.time.Instant.ofEpochSecond(serverTimeSeconds);

// Apply timezone shift
long shiftMinutes = data.getUtcTimezoneServerTimeShiftMinutes();
java.time.Instant serverLocal = serverUtc.plusSeconds(shiftMinutes * 60);

System.out.printf("Server time: %s (UTC%+d minutes)%n",
    serverLocal.toString(),
    shiftMinutes);
```

### 4) Check account mode before trading

```java
var reply = account.accountSummary();
var data = reply.getData();

// Determine account type
String modeLabel = switch (data.getAccountTradeMode()) {
    case MRPC_ACCOUNT_TRADE_MODE_DEMO -> "Demo";
    case MRPC_ACCOUNT_TRADE_MODE_CONTEST -> "Contest";
    case MRPC_ACCOUNT_TRADE_MODE_REAL -> "Real";
    default -> "Unknown";
};

System.out.println("Account mode: " + modeLabel);

// Warning for demo accounts
if (data.getAccountTradeMode() ==
    mt5_term_api.Mt5TermApiAccountHelper.MrpcEnumAccountTradeMode.MRPC_ACCOUNT_TRADE_MODE_DEMO) {
    System.out.println("⚠️ WARNING: This is a DEMO account!");
}
```

### 5) Create a Java record for cleaner code

```java
// Define a record for easier data handling
public record AccountSummaryView(
    long login,
    String currency,
    double balance,
    double equity,
    long leverage,
    String tradeModeLabel,
    String companyName
) {
    public static AccountSummaryView fromProto(Mt5TermApiAccountHelper.AccountSummaryData proto) {
        String mode = switch (proto.getAccountTradeMode()) {
            case MRPC_ACCOUNT_TRADE_MODE_DEMO -> "Demo";
            case MRPC_ACCOUNT_TRADE_MODE_CONTEST -> "Contest";
            case MRPC_ACCOUNT_TRADE_MODE_REAL -> "Real";
            default -> "Unknown";
        };

        return new AccountSummaryView(
            proto.getAccountLogin(),
            proto.getAccountCurrency(),
            proto.getAccountBalance(),
            proto.getAccountEquity(),
            proto.getAccountLeverage(),
            mode,
            proto.getAccountCompanyName()
        );
    }
}

// Usage
var reply = account.accountSummary();
var view = AccountSummaryView.fromProto(reply.getData());
System.out.println(view);
```

### 6) Monitoring loop with error handling

```java
import java.util.concurrent.TimeUnit;

public class AccountMonitor {
    public static void monitorAccount(MT5Account account) {
        while (true) {
            try {
                var reply = account.accountSummary();
                var data = reply.getData();

                System.out.printf("[%s] Equity: %.2f %s%n",
                    java.time.LocalTime.now(),
                    data.getAccountEquity(),
                    data.getAccountCurrency());

                TimeUnit.SECONDS.sleep(5);

            } catch (ApiExceptionMT5 e) {
                System.err.println("API Error: " + e.getMessage());
                // Automatic reconnection is handled internally
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ie) {
                    break;
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
```

---

## 🔄 Low-level gRPC call (for reference)

If you need to call the gRPC service directly without the wrapper:

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiAccountHelper.AccountSummaryRequest request =
    Mt5TermApiAccountHelper.AccountSummaryRequest.newBuilder().build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountHelper.AccountSummaryReply reply = accountClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .accountSummary(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
Mt5TermApiAccountHelper.AccountSummaryData data = reply.getData();
```
