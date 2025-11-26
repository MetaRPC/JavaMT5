# ✅ Getting Individual Account Double Properties

> **Request:** single `double` property from **MT5** account. Fetch specific numeric properties like balance, equity, margin, profit, etc.

**API Information:**

* **SDK wrapper:** `MT5Account.accountInfoDouble(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountInformation`
* **Proto definition:** `AccountInfoDouble` (defined in `mt5-term-api-account-information.proto`)

### RPC

* **Service:** `mt5_term_api.AccountInformation`
* **Method:** `AccountInfoDouble(AccountInfoDoubleRequest) → AccountInfoDoubleReply`
* **Low‑level client (generated):** `AccountInformationGrpc.AccountInformationBlockingStub.accountInfoDouble(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Retrieves a specific double-precision property of the trading account.
     * Use this to get individual numeric values such as BALANCE, EQUITY, MARGIN, PROFIT, etc.
     *
     * @param propertyType The specific property to retrieve (e.g., ACCOUNT_BALANCE, ACCOUNT_EQUITY)
     * @return Response containing the requested double value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountInformation.AccountInfoDoubleReply accountInfoDouble(
        Mt5TermApiAccountInformation.AccountInfoDoublePropertyType propertyType) throws ApiExceptionMT5;
}
```

**Request message:** `AccountInfoDoubleRequest { property_id: AccountInfoDoublePropertyType }`
**Reply message:** `AccountInfoDoubleReply { data: AccountInfoDoubleData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type                              | Required | Description                       |
| -------------- | --------------------------------- | -------- | --------------------------------- |
| `propertyType` | `AccountInfoDoublePropertyType`   | ✅       | Property to retrieve (see enum below) |

---

## ⬆️ Output — `AccountInfoDoubleData`

| Field            | Type     | Description                 |
| ---------------- | -------- | --------------------------- |
| `requested_value` | `double` | The requested property value |

Access the value using `reply.getData().getRequestedValue()`.

---

## 🧱 Related enums (from proto)

### `AccountInfoDoublePropertyType`

| Enum Value                   | Value | Description                                                                                                                       | MQL5 Docs                                                     |
| ---------------------------- | ----- | --------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `ACCOUNT_BALANCE`            | 0     | Account balance in the deposit currency                                                                                           | [AccountInfoDouble](https://www.mql5.com/en/docs/account/accountinfodouble) |
| `ACCOUNT_CREDIT`             | 1     | Account credit in the deposit currency                                                                                            |                                                               |
| `ACCOUNT_PROFIT`             | 2     | Current profit of an account in the deposit currency                                                                              |                                                               |
| `ACCOUNT_EQUITY`             | 3     | Account equity in the deposit currency (Balance + Profit)                                                                         |                                                               |
| `ACCOUNT_MARGIN`             | 4     | Account margin used in the deposit currency                                                                                       |                                                               |
| `ACCOUNT_MARGIN_FREE`        | 5     | Free margin of an account in the deposit currency                                                                                 |                                                               |
| `ACCOUNT_MARGIN_LEVEL`       | 6     | Account margin level in percents                                                                                                  |                                                               |
| `ACCOUNT_MARGIN_SO_CALL`     | 7     | Margin call level. Depending on the set ACCOUNT_MARGIN_SO_MODE is expressed in percents or in the deposit currency               |                                                               |
| `ACCOUNT_MARGIN_SO_SO`       | 8     | Margin stop out level. Depending on the set ACCOUNT_MARGIN_SO_MODE is expressed in percents or in the deposit currency           |                                                               |
| `ACCOUNT_MARGIN_INITIAL`     | 9     | Initial margin. The amount reserved on an account to cover the margin of all pending orders                                       |                                                               |
| `ACCOUNT_MARGIN_MAINTENANCE` | 10    | Maintenance margin. The minimum equity reserved on an account to cover the minimum amount of all open positions                   |                                                               |
| `ACCOUNT_ASSETS`             | 11    | The current assets of an account                                                                                                  |                                                               |
| `ACCOUNT_LIABILITIES`        | 12    | The current liabilities on an account                                                                                             |                                                               |
| `ACCOUNT_COMMISSION_BLOCKED` | 13    | The current blocked commission amount on an account                                                                               |                                                               |

---

## 💬 Just the essentials

* **What it is.** Single RPC returning one specific `double` property of the account.
* **Why you need it.** When you only need one property (e.g., margin level before placing an order) instead of fetching the full account summary.
* **Performance.** Lightweight call — ideal for frequent checks of specific properties.
* **Alternative.** Use `accountSummary()` if you need multiple properties at once.

---

## 🎯 Purpose

Use this method when you need to:

* Check a single account property (margin, equity, profit, etc.) without fetching all account data.
* Monitor specific properties frequently (e.g., margin level for risk management).
* Verify free margin before placing trades.
* Calculate margin requirements dynamically.

---

## 🧩 Notes & Tips

* Prefer `accountSummary()` if you need multiple properties — it's more efficient to fetch all data in one call.
* The method uses automatic reconnection via `executeWithReconnect()` to handle transient gRPC errors.
* All exceptions are wrapped in `ApiExceptionMT5` for consistent error handling.
* For UI dashboards displaying multiple properties, use `accountSummary()` instead.
* The method is thread-safe and can be called from multiple threads.

---

## 🔗 Usage Examples

### 1) Check margin level before placing order

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountInformation.*;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Check margin level
            AccountInfoDoubleReply reply = account.accountInfoDouble(
                AccountInfoDoublePropertyType.ACCOUNT_MARGIN_LEVEL
            );
            double marginLevel = reply.getData().getRequestedValue();

            if (marginLevel < 200.0) {
                System.out.printf("⚠️ Warning: Low margin level %.2f%%%n", marginLevel);
                // Skip trading or reduce position size
            } else {
                System.out.printf("✅ Margin level OK: %.2f%%%n", marginLevel);
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Get current profit

```java
// Monitor floating profit/loss
var reply = account.accountInfoDouble(
    AccountInfoDoublePropertyType.ACCOUNT_PROFIT
);
double profit = reply.getData().getRequestedValue();

System.out.printf("Current P/L: %.2f%n", profit);
```

### 3) Check free margin

```java
// Verify free margin before opening position
var reply = account.accountInfoDouble(
    AccountInfoDoublePropertyType.ACCOUNT_MARGIN_FREE
);
double freeMargin = reply.getData().getRequestedValue();

System.out.printf("Free margin: %.2f%n", freeMargin);

if (freeMargin < 100.0) {
    System.out.println("⚠️ Insufficient free margin");
}
```

### 4) Monitor margin usage

```java
// Fetch margin-related properties
var marginReply = account.accountInfoDouble(
    AccountInfoDoublePropertyType.ACCOUNT_MARGIN
);
var marginFreeReply = account.accountInfoDouble(
    AccountInfoDoublePropertyType.ACCOUNT_MARGIN_FREE
);
var marginLevelReply = account.accountInfoDouble(
    AccountInfoDoublePropertyType.ACCOUNT_MARGIN_LEVEL
);

double margin = marginReply.getData().getRequestedValue();
double marginFree = marginFreeReply.getData().getRequestedValue();
double marginLevel = marginLevelReply.getData().getRequestedValue();

System.out.printf("Margin:       %.2f%n", margin);
System.out.printf("Free:         %.2f%n", marginFree);
System.out.printf("Level:        %.2f%%%n", marginLevel);
```

### 5) Compare equity vs balance

```java
// Calculate floating P/L by comparing equity and balance
var balanceReply = account.accountInfoDouble(
    AccountInfoDoublePropertyType.ACCOUNT_BALANCE
);
var equityReply = account.accountInfoDouble(
    AccountInfoDoublePropertyType.ACCOUNT_EQUITY
);

double balance = balanceReply.getData().getRequestedValue();
double equity = equityReply.getData().getRequestedValue();
double floatingPL = equity - balance;

System.out.printf("Balance:      %.2f%n", balance);
System.out.printf("Equity:       %.2f%n", equity);
System.out.printf("Floating P/L: %.2f%n", floatingPL);
```

### 6) Helper method for cleaner code

```java
public class AccountHelper {
    private final MT5Account account;

    public AccountHelper(MT5Account account) {
        this.account = account;
    }

    /**
     * Helper to get double property directly
     */
    public double getDoubleProperty(AccountInfoDoublePropertyType property)
            throws ApiExceptionMT5 {
        var reply = account.accountInfoDouble(property);
        return reply.getData().getRequestedValue();
    }

    /**
     * Check if margin level is safe
     */
    public boolean isMarginLevelSafe(double minimumLevel) throws ApiExceptionMT5 {
        double marginLevel = getDoubleProperty(
            AccountInfoDoublePropertyType.ACCOUNT_MARGIN_LEVEL
        );
        return marginLevel >= minimumLevel;
    }

    /**
     * Get floating P/L
     */
    public double getFloatingPL() throws ApiExceptionMT5 {
        return getDoubleProperty(AccountInfoDoublePropertyType.ACCOUNT_PROFIT);
    }
}

// Usage
AccountHelper helper = new AccountHelper(account);

if (helper.isMarginLevelSafe(200.0)) {
    System.out.println("Safe to trade");
}

double pl = helper.getFloatingPL();
System.out.printf("Current P/L: %.2f%n", pl);
```

### 7) Risk monitoring loop

```java
import java.util.concurrent.TimeUnit;

public class RiskMonitor {
    public static void monitorMargin(MT5Account account, double warningLevel) {
        while (true) {
            try {
                var reply = account.accountInfoDouble(
                    AccountInfoDoublePropertyType.ACCOUNT_MARGIN_LEVEL
                );
                double marginLevel = reply.getData().getRequestedValue();

                if (marginLevel < warningLevel) {
                    System.out.printf("⚠️ [%s] ALERT: Margin level %.2f%% (threshold %.2f%%)%n",
                        java.time.LocalTime.now(),
                        marginLevel,
                        warningLevel);
                } else {
                    System.out.printf("[%s] Margin level: %.2f%%%n",
                        java.time.LocalTime.now(),
                        marginLevel);
                }

                TimeUnit.SECONDS.sleep(5);

            } catch (ApiExceptionMT5 e) {
                System.err.println("API Error: " + e.getMessage());
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

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiAccountInformation.AccountInfoDoubleRequest request =
    Mt5TermApiAccountInformation.AccountInfoDoubleRequest.newBuilder()
        .setPropertyId(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_BALANCE)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountInformation.AccountInfoDoubleReply reply = accountInformationClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .accountInfoDouble(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
double value = reply.getData().getRequestedValue();
```
