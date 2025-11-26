# ✅ Getting Individual Account String Properties

> **Request:** single `String` property from **MT5** account. Fetch specific string properties like account name, server, currency, company, etc.

**API Information:**

* **SDK wrapper:** `MT5Account.accountInfoString(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountInformation`
* **Proto definition:** `AccountInfoString` (defined in `mt5-term-api-account-information.proto`)

### RPC

* **Service:** `mt5_term_api.AccountInformation`
* **Method:** `AccountInfoString(AccountInfoStringRequest) → AccountInfoStringReply`
* **Low‑level client (generated):** `AccountInformationGrpc.AccountInformationBlockingStub.accountInfoString(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Retrieves a specific string property of the trading account.
     * Use this to get textual information such as account NAME, SERVER, CURRENCY, or COMPANY.
     *
     * @param propertyType The specific property to retrieve (e.g., ACCOUNT_NAME, ACCOUNT_SERVER)
     * @return Response containing the requested string value
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountInformation.AccountInfoStringReply accountInfoString(
        Mt5TermApiAccountInformation.AccountInfoStringPropertyType propertyType) throws ApiExceptionMT5;
}
```

**Request message:** `AccountInfoStringRequest { property_id: AccountInfoStringPropertyType }`
**Reply message:** `AccountInfoStringReply { data: AccountInfoStringData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type                              | Required | Description                       |
| -------------- | --------------------------------- | -------- | --------------------------------- |
| `propertyType` | `AccountInfoStringPropertyType`   | ✅       | Property to retrieve (see enum below) |

---

## ⬆️ Output — `AccountInfoStringData`

| Field            | Type     | Description                 |
| ---------------- | -------- | --------------------------- |
| `requested_value` | `String` | The requested property value |

Access the value using `reply.getData().getRequestedValue()`.

---

## 🧱 Related enums (from proto)

### `AccountInfoStringPropertyType`

| Enum Value        | Value | Description                                      | MQL5 Docs                                                        |
| ----------------- | ----- | ------------------------------------------------ | ---------------------------------------------------------------- |
| `ACCOUNT_NAME`    | 0     | Client name (account holder name)                | [AccountInfoString](https://www.mql5.com/en/docs/account/accountinfostring) |
| `ACCOUNT_SERVER`  | 1     | Trade server name (e.g., "CompanyName-Demo")     |                                                                  |
| `ACCOUNT_CURRENCY`| 2     | Account currency (e.g., "USD", "EUR", "GBP")     |                                                                  |
| `ACCOUNT_COMPANY` | 3     | Name of a company that serves the account        |                                                                  |

---

## 💬 Just the essentials

* **What it is.** Single RPC returning one specific `String` property of the account.
* **Why you need it.** When you only need one string property (e.g., currency, server name) instead of fetching the full account summary.
* **Performance.** Lightweight call — ideal for quick checks of specific text properties.
* **Alternative.** Use `accountSummary()` if you need multiple properties at once.

---

## 🎯 Purpose

Use this method when you need to:

* Check a single string account property without fetching all account data.
* Verify account currency before calculating monetary values.
* Display broker/server information in UI.
* Log account details for audit purposes.
* Verify connection to correct trading server.

---

## 🧩 Notes & Tips

* Prefer `accountSummary()` if you need multiple properties — it's more efficient to fetch all data in one call.
* The method uses automatic reconnection via `executeWithReconnect()` to handle transient gRPC errors.
* All exceptions are wrapped in `ApiExceptionMT5` for consistent error handling.
* The method is thread-safe and can be called from multiple threads.
* Server names often include broker name and account type (e.g., "XMGlobal-Demo", "Alpari-Real").

---

## 🔗 Usage Examples

### 1) Get account name

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountInformation.*;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get account holder name
            AccountInfoStringReply reply = account.accountInfoString(
                AccountInfoStringPropertyType.ACCOUNT_NAME
            );
            String accountName = reply.getData().getRequestedValue();

            System.out.printf("Account name: %s%n", accountName);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Get account currency

```java
// Verify account currency
var reply = account.accountInfoString(
    AccountInfoStringPropertyType.ACCOUNT_CURRENCY
);
String currency = reply.getData().getRequestedValue();

System.out.printf("Account currency: %s%n", currency);

if (!currency.equals("USD")) {
    System.out.println("⚠️ Warning: Account currency is not USD");
}
```

### 3) Get server name

```java
// Get trading server name
var reply = account.accountInfoString(
    AccountInfoStringPropertyType.ACCOUNT_SERVER
);
String serverName = reply.getData().getRequestedValue();

System.out.printf("Connected to server: %s%n", serverName);

// Check if it's a demo server
if (serverName.toLowerCase().contains("demo")) {
    System.out.println("✅ Demo server detected");
} else {
    System.out.println("⚠️ Live/Real server detected");
}
```

### 4) Get broker/company name

```java
// Get broker company name
var reply = account.accountInfoString(
    AccountInfoStringPropertyType.ACCOUNT_COMPANY
);
String companyName = reply.getData().getRequestedValue();

System.out.printf("Broker: %s%n", companyName);
```

### 5) Display complete account info

```java
// Fetch all string properties
var nameReply = account.accountInfoString(
    AccountInfoStringPropertyType.ACCOUNT_NAME
);
var serverReply = account.accountInfoString(
    AccountInfoStringPropertyType.ACCOUNT_SERVER
);
var currencyReply = account.accountInfoString(
    AccountInfoStringPropertyType.ACCOUNT_CURRENCY
);
var companyReply = account.accountInfoString(
    AccountInfoStringPropertyType.ACCOUNT_COMPANY
);

System.out.println("=== Account Information ===");
System.out.printf("Name:     %s%n", nameReply.getData().getRequestedValue());
System.out.printf("Server:   %s%n", serverReply.getData().getRequestedValue());
System.out.printf("Currency: %s%n", currencyReply.getData().getRequestedValue());
System.out.printf("Broker:   %s%n", companyReply.getData().getRequestedValue());
```

### 6) Helper method for cleaner code

```java
public class AccountHelper {
    private final MT5Account account;

    public AccountHelper(MT5Account account) {
        this.account = account;
    }

    /**
     * Helper to get string property directly
     */
    public String getStringProperty(AccountInfoStringPropertyType property)
            throws ApiExceptionMT5 {
        var reply = account.accountInfoString(property);
        return reply.getData().getRequestedValue();
    }

    /**
     * Get account currency
     */
    public String getCurrency() throws ApiExceptionMT5 {
        return getStringProperty(AccountInfoStringPropertyType.ACCOUNT_CURRENCY);
    }

    /**
     * Get server name
     */
    public String getServerName() throws ApiExceptionMT5 {
        return getStringProperty(AccountInfoStringPropertyType.ACCOUNT_SERVER);
    }

    /**
     * Check if connected to demo server
     */
    public boolean isDemoServer() throws ApiExceptionMT5 {
        String serverName = getServerName().toLowerCase();
        return serverName.contains("demo") || serverName.contains("test");
    }

    /**
     * Get complete account info
     */
    public AccountInfo getAccountInfo() throws ApiExceptionMT5 {
        return new AccountInfo(
            getStringProperty(AccountInfoStringPropertyType.ACCOUNT_NAME),
            getStringProperty(AccountInfoStringPropertyType.ACCOUNT_SERVER),
            getStringProperty(AccountInfoStringPropertyType.ACCOUNT_CURRENCY),
            getStringProperty(AccountInfoStringPropertyType.ACCOUNT_COMPANY)
        );
    }

    public record AccountInfo(
        String name,
        String server,
        String currency,
        String company
    ) {
        @Override
        public String toString() {
            return String.format(
                "Account[name=%s, server=%s, currency=%s, broker=%s]",
                name, server, currency, company
            );
        }
    }
}

// Usage
AccountHelper helper = new AccountHelper(account);

System.out.println("Currency: " + helper.getCurrency());
System.out.println("Is demo: " + helper.isDemoServer());
System.out.println(helper.getAccountInfo());
```

### 7) Validate connection to expected broker

```java
public class ConnectionValidator {
    /**
     * Validate that we're connected to the expected broker and server
     */
    public static boolean validateConnection(
            MT5Account account,
            String expectedBroker,
            boolean allowDemoOnly) throws ApiExceptionMT5 {

        // Check broker
        var companyReply = account.accountInfoString(
            AccountInfoStringPropertyType.ACCOUNT_COMPANY
        );
        String broker = companyReply.getData().getRequestedValue();

        if (!broker.equalsIgnoreCase(expectedBroker)) {
            System.out.printf("❌ Wrong broker: expected '%s', got '%s'%n",
                expectedBroker, broker);
            return false;
        }
        System.out.printf("✅ Broker: %s%n", broker);

        // Check server type
        var serverReply = account.accountInfoString(
            AccountInfoStringPropertyType.ACCOUNT_SERVER
        );
        String server = serverReply.getData().getRequestedValue();

        boolean isDemo = server.toLowerCase().contains("demo");
        if (allowDemoOnly && !isDemo) {
            System.out.printf("❌ Real server detected: %s (only demo allowed)%n", server);
            return false;
        }
        System.out.printf("✅ Server: %s (%s)%n", server, isDemo ? "Demo" : "Real");

        return true;
    }
}

// Usage
if (ConnectionValidator.validateConnection(account, "XM Global Limited", true)) {
    System.out.println("✅ Connection validated");
} else {
    System.out.println("❌ Connection validation failed");
}
```

### 8) Log account details for audit

```java
import java.time.LocalDateTime;
import java.util.logging.*;

public class AccountAuditor {
    private static final Logger logger = Logger.getLogger(AccountAuditor.class.getName());

    /**
     * Log complete account details for audit trail
     */
    public static void logAccountDetails(MT5Account account) {
        try {
            String name = account.accountInfoString(
                AccountInfoStringPropertyType.ACCOUNT_NAME
            ).getData().getRequestedValue();

            String server = account.accountInfoString(
                AccountInfoStringPropertyType.ACCOUNT_SERVER
            ).getData().getRequestedValue();

            String currency = account.accountInfoString(
                AccountInfoStringPropertyType.ACCOUNT_CURRENCY
            ).getData().getRequestedValue();

            String company = account.accountInfoString(
                AccountInfoStringPropertyType.ACCOUNT_COMPANY
            ).getData().getRequestedValue();

            logger.info(String.format(
                "[%s] Account Details: Name='%s', Server='%s', Currency='%s', Broker='%s'",
                LocalDateTime.now(),
                name, server, currency, company
            ));

        } catch (ApiExceptionMT5 e) {
            logger.severe("Failed to retrieve account details: " + e.getMessage());
        }
    }
}

// Usage
AccountAuditor.logAccountDetails(account);
```

### 9) Create formatted account summary

```java
public class AccountFormatter {
    /**
     * Create a formatted account summary string
     */
    public static String formatAccountSummary(MT5Account account) throws ApiExceptionMT5 {
        var nameReply = account.accountInfoString(
            AccountInfoStringPropertyType.ACCOUNT_NAME
        );
        var serverReply = account.accountInfoString(
            AccountInfoStringPropertyType.ACCOUNT_SERVER
        );
        var currencyReply = account.accountInfoString(
            AccountInfoStringPropertyType.ACCOUNT_CURRENCY
        );
        var companyReply = account.accountInfoString(
            AccountInfoStringPropertyType.ACCOUNT_COMPANY
        );

        return String.format("""
            ╔════════════════════════════════════════╗
            ║        ACCOUNT INFORMATION             ║
            ╠════════════════════════════════════════╣
            ║ Name:     %-28s ║
            ║ Server:   %-28s ║
            ║ Currency: %-28s ║
            ║ Broker:   %-28s ║
            ╚════════════════════════════════════════╝
            """,
            nameReply.getData().getRequestedValue(),
            serverReply.getData().getRequestedValue(),
            currencyReply.getData().getRequestedValue(),
            companyReply.getData().getRequestedValue()
        );
    }
}

// Usage
System.out.println(AccountFormatter.formatAccountSummary(account));
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiAccountInformation.AccountInfoStringRequest request =
    Mt5TermApiAccountInformation.AccountInfoStringRequest.newBuilder()
        .setPropertyId(Mt5TermApiAccountInformation.AccountInfoStringPropertyType.ACCOUNT_NAME)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountInformation.AccountInfoStringReply reply = accountInformationClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .accountInfoString(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
String value = reply.getData().getRequestedValue();
```
