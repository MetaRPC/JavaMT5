# ✅ Select/Deselect Symbol in MarketWatch

> **Request:** select or deselect a symbol in the MarketWatch window. Symbols must be selected to receive price updates and place trades.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolSelect(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolSelect` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolSelect(SymbolSelectRequest) → SymbolSelectReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolSelect(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Selects or deselects a symbol in the Market Watch window.
     * Symbols must be selected in Market Watch to receive price updates and place trades.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param select True to select symbol, false to remove from Market Watch
     * @return Reply with success status
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolSelectReply symbolSelect(String symbol, boolean select) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolSelectRequest { symbol: string, select: bool }`
**Reply message:** `SymbolSelectReply { data: SymbolSelectData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter | Type      | Required | Description                                                  |
| --------- | --------- | -------- | ------------------------------------------------------------ |
| `symbol`  | `String`  | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")                       |
| `select`  | `boolean` | ✅       | `true` to add to MarketWatch, `false` to remove              |

---

## ⬆️ Output — `SymbolSelectData`

| Field     | Type      | Description                                       |
| --------- | --------- | ------------------------------------------------- |
| `success` | `boolean` | `true` if operation succeeded, `false` on failure |

Access the result using `reply.getData().getSuccess()`.

---

## 💬 Just the essentials

* **What it is.** RPC to add/remove symbols from MarketWatch window.
* **Why you need it.** Symbols must be in MarketWatch to receive quotes and trade them.
* **Important.** Cannot remove symbol if chart is open or positions exist for it.
* **Auto-selection.** Some symbols auto-select for margin calculations even if not visible.

---

## 🎯 Purpose

Use this method when you need to:

* Add a symbol to MarketWatch before getting quotes.
* Enable price updates for a symbol.
* Prepare symbol for trading operations.
* Clean up MarketWatch by removing unused symbols.

---

## 🧩 Notes & Tips

* Always select a symbol before calling `symbolInfoTick()` or trading it.
* Cannot remove a symbol if:
  - Symbol chart is currently open
  - Open positions exist for the symbol
  - Pending orders exist for the symbol
* The method uses automatic reconnection via `executeWithReconnect()`.
* Some symbols are auto-selected by MT5 for margin calculations.

---

## 🔗 Usage Examples

### 1) Select a symbol

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Select GBPUSD in MarketWatch
            Mt5TermApiMarketInfo.SymbolSelectReply reply =
                account.symbolSelect("GBPUSD", true);

            if (reply.getData().getSuccess()) {
                System.out.println("✅ GBPUSD added to MarketWatch");
            } else {
                System.out.println("❌ Failed to add GBPUSD");
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Remove symbol from MarketWatch

```java
// Remove symbol
var reply = account.symbolSelect("GBPUSD", false);

if (reply.getData().getSuccess()) {
    System.out.println("✅ GBPUSD removed from MarketWatch");
} else {
    System.out.println("❌ Failed to remove GBPUSD (chart open or positions exist?)");
}
```

### 3) Ensure symbol is selected before trading

```java
public class SymbolHelper {
    /**
     * Ensure symbol is selected before trading
     */
    public static void ensureSymbolSelected(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        // Try to select symbol
        var reply = account.symbolSelect(symbol, true);

        if (!reply.getData().getSuccess()) {
            throw new ApiExceptionMT5(
                "Failed to select symbol: " + symbol
            );
        }

        System.out.printf("✅ Symbol selected: %s%n", symbol);
    }
}

// Usage
SymbolHelper.ensureSymbolSelected(account, "EURUSD");

// Now safe to get quotes
var tick = account.quote("EURUSD");
```

### 4) Select multiple symbols

```java
public class MultiSymbolSelector {
    /**
     * Select multiple symbols in MarketWatch
     */
    public static void selectSymbols(MT5Account account, String... symbols)
            throws ApiExceptionMT5 {

        int successful = 0;
        int failed = 0;

        for (String symbol : symbols) {
            var reply = account.symbolSelect(symbol, true);

            if (reply.getData().getSuccess()) {
                System.out.printf("✅ Selected: %s%n", symbol);
                successful++;
            } else {
                System.out.printf("❌ Failed: %s%n", symbol);
                failed++;
            }
        }

        System.out.printf("%nResults: %d selected, %d failed%n", successful, failed);
    }
}

// Usage
MultiSymbolSelector.selectSymbols(account,
    "EURUSD", "GBPUSD", "USDJPY", "XAUUSD", "BTCUSD"
);
```

### 5) Clean up MarketWatch

```java
public class MarketWatchCleaner {
    /**
     * Remove all unused symbols from MarketWatch
     */
    public static void cleanupMarketWatch(MT5Account account, String[] keepSymbols)
            throws ApiExceptionMT5 {

        // Get all symbols in MarketWatch
        var totalReply = account.symbolsTotal(true);
        int total = totalReply.getData().getTotal();

        java.util.Set<String> keep = java.util.Set.of(keepSymbols);
        int removed = 0;

        System.out.println("Cleaning up MarketWatch...");

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, true);
            String symbol = nameReply.getData().getName();

            // Skip symbols we want to keep
            if (keep.contains(symbol)) {
                continue;
            }

            // Try to remove symbol
            var reply = account.symbolSelect(symbol, false);
            if (reply.getData().getSuccess()) {
                System.out.printf("Removed: %s%n", symbol);
                removed++;
            }
        }

        System.out.printf("%n✅ Cleanup complete: removed %d symbols%n", removed);
    }
}

// Usage - keep only major pairs
MarketWatchCleaner.cleanupMarketWatch(account, new String[]{
    "EURUSD", "GBPUSD", "USDJPY", "USDCHF"
});
```

### 6) Toggle symbol visibility

```java
public class SymbolToggler {
    /**
     * Toggle symbol in/out of MarketWatch
     */
    public static boolean toggleSymbol(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        // Check if symbol is currently selected
        var intReply = account.symbolInfoInteger(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SELECT
        );
        boolean isSelected = intReply.getData().getValue() == 1;

        // Toggle selection
        var reply = account.symbolSelect(symbol, !isSelected);

        if (reply.getData().getSuccess()) {
            System.out.printf("✅ %s: %s → %s%n",
                symbol,
                isSelected ? "selected" : "not selected",
                !isSelected ? "selected" : "not selected"
            );
            return true;
        }

        return false;
    }
}
```

### 7) Check if symbol can be selected

```java
public class SymbolValidator {
    /**
     * Check if symbol exists and can be selected
     */
    public static boolean canSelectSymbol(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        // Check if symbol exists
        var existReply = account.symbolExist(symbol);

        if (!existReply.getData().getExists()) {
            System.out.printf("❌ Symbol does not exist: %s%n", symbol);
            return false;
        }

        System.out.printf("✅ Symbol exists: %s%n", symbol);

        // Try to select it
        var selectReply = account.symbolSelect(symbol, true);

        if (selectReply.getData().getSuccess()) {
            System.out.printf("✅ Symbol selected successfully%n");
            return true;
        } else {
            System.out.printf("❌ Failed to select symbol%n");
            return false;
        }
    }
}
```

### 8) Batch symbol operations

```java
public class SymbolBatchOperations {
    public record SelectionResult(
        String symbol,
        boolean success,
        String message
    ) {}

    /**
     * Batch select symbols with detailed results
     */
    public static java.util.List<SelectionResult> batchSelect(
            MT5Account account,
            java.util.List<String> symbols,
            boolean select) throws ApiExceptionMT5 {

        java.util.List<SelectionResult> results = new java.util.ArrayList<>();

        for (String symbol : symbols) {
            try {
                var reply = account.symbolSelect(symbol, select);
                boolean success = reply.getData().getSuccess();

                results.add(new SelectionResult(
                    symbol,
                    success,
                    success
                        ? (select ? "Added to MarketWatch" : "Removed from MarketWatch")
                        : "Operation failed"
                ));

            } catch (ApiExceptionMT5 e) {
                results.add(new SelectionResult(
                    symbol,
                    false,
                    "Error: " + e.getMessage()
                ));
            }
        }

        return results;
    }

    /**
     * Print batch operation results
     */
    public static void printResults(java.util.List<SelectionResult> results) {
        System.out.println("\nBatch Operation Results:");
        System.out.println("═".repeat(60));

        int successful = 0;
        for (var result : results) {
            String status = result.success() ? "✅" : "❌";
            System.out.printf("%s %-10s - %s%n",
                status, result.symbol(), result.message());

            if (result.success()) successful++;
        }

        System.out.println("═".repeat(60));
        System.out.printf("Total: %d | Success: %d | Failed: %d%n",
            results.size(), successful, results.size() - successful);
    }
}

// Usage
var symbols = java.util.List.of("EURUSD", "GBPUSD", "INVALID", "XAUUSD");
var results = SymbolBatchOperations.batchSelect(account, symbols, true);
SymbolBatchOperations.printResults(results);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolSelectRequest request =
    Mt5TermApiMarketInfo.SymbolSelectRequest.newBuilder()
        .setSymbol("EURUSD")
        .setSelect(true)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolSelectReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolSelect(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
boolean success = reply.getData().getSuccess();
```
