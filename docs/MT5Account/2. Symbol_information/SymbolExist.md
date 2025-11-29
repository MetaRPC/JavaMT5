# ✅ Check if Symbol Exists

> **Request:** check if a symbol with specified name exists on the MT5 server. Returns existence status and whether it's a custom symbol.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolExist(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolExist` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolExist(SymbolExistRequest) → SymbolExistReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolExist(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Checks if a symbol exists on the MT5 server.
     * Returns whether the specified symbol name is available for trading.
     *
     * @param symbolName Symbol name to check (e.g., "EURUSD")
     * @return Reply with existence status
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolExistReply symbolExist(String symbolName) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolExistRequest { name: string }`

**Reply message:** `SymbolExistReply { data: SymbolExistData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter    | Type     | Required | Description                              |
| ------------ | -------- | -------- | ---------------------------------------- |
| `symbolName` | `String` | ✅       | Symbol name to check (e.g., "EURUSD")    |

---

## ⬆️ Output - `SymbolExistData`

| Field       | Type      | Description                                                           |
| ----------- | --------- | --------------------------------------------------------------------- |
| `exists`    | `boolean` | `true` if symbol exists, `false` if not found                         |
| `is_custom` | `boolean` | `true` if it's a custom symbol, `false` if it's a standard symbol     |

Access using `reply.getData().getExists()` and `reply.getData().getIsCustom()`.

---

## 💬 Just the essentials

* **What it is.** Simple RPC to verify symbol existence before attempting to use it.
* **Why you need it.** Prevent errors when working with user-provided or dynamic symbol names.
* **Custom symbols.** Can detect synthetic/custom symbols created on the broker side.
* **Performance.** Lightweight validation call before more expensive operations.

---

## 🎯 Purpose

Use this method when you need to:

* Validate user input before requesting symbol data.
* Check if a symbol is available on current broker.
* Distinguish between standard and custom symbols.
* Verify symbol names before adding to MarketWatch.
* Handle multi-broker scenarios where symbol availability varies.

---

## 🧩 Notes & Tips

* Call this before `symbolSelect()` to avoid errors on non-existent symbols.
* Custom symbols are synthetic instruments created from other symbols or external data.
* The method uses automatic reconnection via `executeWithReconnect()`.
* Symbol names are case-sensitive on some brokers.
* Some brokers use different naming conventions (e.g., "EURUSD" vs "EURUSDm").

---

## 🔗 Usage Examples

### 1) Basic symbol validation

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Check if symbol exists
            Mt5TermApiMarketInfo.SymbolExistReply reply =
                account.symbolExist("GBPUSD");
            Mt5TermApiMarketInfo.SymbolExistData data = reply.getData();

            if (data.getExists()) {
                System.out.println("✅ Symbol GBPUSD exists");
                System.out.printf("Custom symbol: %s%n", data.getIsCustom());
            } else {
                System.out.println("❌ Symbol GBPUSD not found");
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Validate before selecting

```java
// Safe symbol selection
String symbol = "XAUUSD";

var reply = account.symbolExist(symbol);

if (reply.getData().getExists()) {
    // Symbol exists, safe to select
    account.symbolSelect(symbol, true);
    System.out.printf("✅ Symbol %s selected%n", symbol);
} else {
    System.out.printf("❌ Symbol %s does not exist%n", symbol);
}
```

### 3) Check custom vs standard symbol

```java
public class SymbolChecker {
    public static void analyzeSymbol(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var reply = account.symbolExist(symbol);
        var data = reply.getData();

        if (!data.getExists()) {
            System.out.printf("❌ Symbol '%s' not found%n", symbol);
            return;
        }

        String type = data.getIsCustom() ? "Custom" : "Standard";
        System.out.printf("✅ Symbol: %s%n", symbol);
        System.out.printf("   Type: %s%n", type);

        if (data.getIsCustom()) {
            System.out.println("   ⚠️ This is a synthetic/custom symbol");
        }
    }
}

// Usage
SymbolChecker.analyzeSymbol(account, "EURUSD");
SymbolChecker.analyzeSymbol(account, "CUSTOM_INDEX");
```

### 4) Validate multiple symbols

```java
public class MultiSymbolValidator {
    /**
     * Validate multiple symbols at once
     */
    public static java.util.Map<String, Boolean> validateSymbols(
            MT5Account account,
            String... symbols) throws ApiExceptionMT5 {

        java.util.Map<String, Boolean> results = new java.util.LinkedHashMap<>();

        for (String symbol : symbols) {
            var reply = account.symbolExist(symbol);
            results.put(symbol, reply.getData().getExists());
        }

        return results;
    }

    /**
     * Print validation results
     */
    public static void printResults(java.util.Map<String, Boolean> results) {
        System.out.println("\nSymbol Validation Results:");
        System.out.println("═".repeat(40));

        int found = 0;
        for (var entry : results.entrySet()) {
            String status = entry.getValue() ? "✅" : "❌";
            System.out.printf("%s %-10s - %s%n",
                status,
                entry.getKey(),
                entry.getValue() ? "exists" : "not found"
            );

            if (entry.getValue()) found++;
        }

        System.out.println("═".repeat(40));
        System.out.printf("Found: %d/%d%n", found, results.size());
    }
}

// Usage
var results = MultiSymbolValidator.validateSymbols(account,
    "EURUSD", "GBPUSD", "INVALID", "XAUUSD", "BTCUSD"
);
MultiSymbolValidator.printResults(results);
```

### 5) Safe symbol operations wrapper

```java
public class SafeSymbolOperations {
    private final MT5Account account;

    public SafeSymbolOperations(MT5Account account) {
        this.account = account;
    }

    /**
     * Safely get quote - validates symbol first
     */
    public Mt5TermApiMarketInfo.MrpcMqlTick getQuoteSafe(String symbol)
            throws ApiExceptionMT5 {

        // Validate symbol exists
        var existReply = account.symbolExist(symbol);
        if (!existReply.getData().getExists()) {
            throw new ApiExceptionMT5("Symbol does not exist: " + symbol);
        }

        // Select symbol if needed
        account.symbolSelect(symbol, true);

        // Get quote
        var tickReply = account.quote(symbol);
        return tickReply.getData();
    }

    /**
     * Check if symbol is tradeable (exists and not custom)
     */
    public boolean isTradeable(String symbol) throws ApiExceptionMT5 {
        var reply = account.symbolExist(symbol);
        var data = reply.getData();

        return data.getExists() && !data.getIsCustom();
    }
}

// Usage
var safeOps = new SafeSymbolOperations(account);

if (safeOps.isTradeable("EURUSD")) {
    var tick = safeOps.getQuoteSafe("EURUSD");
    System.out.printf("EURUSD Bid: %.5f%n", tick.getBid());
}
```

### 6) Find similar symbols

```java
public class SymbolFinder {
    /**
     * Find symbols with similar names
     */
    public static java.util.List<String> findSimilar(
            MT5Account account,
            String pattern) throws ApiExceptionMT5 {

        java.util.List<String> similar = new java.util.ArrayList<>();

        // Common suffixes/prefixes brokers use
        String[] variations = {
            pattern,
            pattern + "m",
            pattern + ".",
            pattern.toLowerCase(),
            pattern + "pro",
            "." + pattern
        };

        for (String variant : variations) {
            var reply = account.symbolExist(variant);
            if (reply.getData().getExists()) {
                similar.add(variant);
            }
        }

        return similar;
    }
}

// Usage - find all variations of EURUSD
var variations = SymbolFinder.findSimilar(account, "EURUSD");
System.out.println("Found variations: " + variations);
```

### 7) Symbol existence record

```java
/**
 * Immutable record for symbol existence
 */
public record SymbolExistence(
    String name,
    boolean exists,
    boolean isCustom
) {
    public static SymbolExistence fromProto(
            String name,
            Mt5TermApiMarketInfo.SymbolExistData data) {
        return new SymbolExistence(
            name,
            data.getExists(),
            data.getIsCustom()
        );
    }

    public String getType() {
        if (!exists) return "Not Found";
        return isCustom ? "Custom" : "Standard";
    }

    @Override
    public String toString() {
        return String.format("%s: %s (%s)",
            name,
            exists ? "exists" : "not found",
            getType()
        );
    }
}

// Usage
var reply = account.symbolExist("EURUSD");
var existence = SymbolExistence.fromProto("EURUSD", reply.getData());
System.out.println(existence);
```

### 8) Batch validation with detailed info

```java
public class BatchSymbolValidator {
    public record ValidationResult(
        String symbol,
        boolean exists,
        boolean isCustom,
        String message
    ) {}

    /**
     * Validate symbols with detailed results
     */
    public static java.util.List<ValidationResult> validateBatch(
            MT5Account account,
            java.util.List<String> symbols) {

        java.util.List<ValidationResult> results = new java.util.ArrayList<>();

        for (String symbol : symbols) {
            try {
                var reply = account.symbolExist(symbol);
                var data = reply.getData();

                String message;
                if (!data.getExists()) {
                    message = "Symbol not found";
                } else if (data.getIsCustom()) {
                    message = "Custom symbol (synthetic)";
                } else {
                    message = "Standard symbol";
                }

                results.add(new ValidationResult(
                    symbol,
                    data.getExists(),
                    data.getIsCustom(),
                    message
                ));

            } catch (ApiExceptionMT5 e) {
                results.add(new ValidationResult(
                    symbol,
                    false,
                    false,
                    "Error: " + e.getMessage()
                ));
            }
        }

        return results;
    }

    /**
     * Print detailed validation report
     */
    public static void printReport(java.util.List<ValidationResult> results) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║      SYMBOL VALIDATION REPORT                 ║");
        System.out.println("╠═══════════════════════════════════════════════╣");

        for (var result : results) {
            String status = result.exists() ? "✅" : "❌";
            String custom = result.isCustom() ? "[CUSTOM]" : "";

            System.out.printf("║ %s %-10s %-8s %-20s ║%n",
                status,
                result.symbol(),
                custom,
                result.message()
            );
        }

        long existsCount = results.stream().filter(ValidationResult::exists).count();
        long customCount = results.stream().filter(ValidationResult::isCustom).count();

        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.printf("║ Total: %d | Found: %d | Custom: %d            ║%n",
            results.size(), existsCount, customCount);
        System.out.println("╚═══════════════════════════════════════════════╝");
    }
}

// Usage
var symbols = java.util.List.of(
    "EURUSD", "GBPUSD", "INVALID", "CUSTOM_INDEX", "XAUUSD"
);
var results = BatchSymbolValidator.validateBatch(account, symbols);
BatchSymbolValidator.printReport(results);
```

### 9) Pre-trade symbol validation

```java
public class TradeValidator {
    /**
     * Validate symbol before placing order
     */
    public static boolean validateForTrading(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        System.out.printf("Validating symbol '%s' for trading...%n", symbol);

        // Check existence
        var existReply = account.symbolExist(symbol);
        var existData = existReply.getData();

        if (!existData.getExists()) {
            System.out.println("❌ Symbol does not exist");
            return false;
        }
        System.out.println("✅ Symbol exists");

        // Warn if custom
        if (existData.getIsCustom()) {
            System.out.println("⚠️ WARNING: This is a custom symbol");
        }

        // Ensure selected
        var selectReply = account.symbolSelect(symbol, true);
        if (!selectReply.getData().getSuccess()) {
            System.out.println("❌ Failed to select symbol");
            return false;
        }
        System.out.println("✅ Symbol selected");

        // Check synchronization
        var syncReply = account.symbolIsSynchronized(symbol);
        if (!syncReply.getData().getSynchronized()) {
            System.out.println("⚠️ WARNING: Symbol not synchronized");
        } else {
            System.out.println("✅ Symbol synchronized");
        }

        System.out.println("✅ Symbol validated for trading");
        return true;
    }
}

// Usage
if (TradeValidator.validateForTrading(account, "EURUSD")) {
    // Safe to place order
    System.out.println("Ready to trade EURUSD");
}
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolExistRequest request =
    Mt5TermApiMarketInfo.SymbolExistRequest.newBuilder()
        .setName("EURUSD")
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolExistReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolExist(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
boolean exists = reply.getData().getExists();
boolean isCustom = reply.getData().getIsCustom();
```
