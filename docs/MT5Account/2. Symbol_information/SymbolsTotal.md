# ✅ Getting Total Number of Symbols

> **Request:** total count of available symbols on the MT5 server. Returns either all symbols or only those visible in MarketWatch.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolsTotal(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolsTotal` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolsTotal(SymbolsTotalRequest) → SymbolsTotalReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolsTotal(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets the total count of available symbols on the MT5 server.
     * Returns either all symbols known to the server or only those currently shown in the MarketWatch window.
     * Use this to determine how many symbols are available before requesting detailed symbol information.
     *
     * @param selectedOnly If true, returns only symbols visible in MarketWatch; if false, returns all available symbols
     * @return Total number of symbols matching the filter criteria
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolsTotalReply symbolsTotal(boolean selectedOnly) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolsTotalRequest { mode: bool }`
**Reply message:** `SymbolsTotalReply { data: SymbolsTotalData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type      | Required | Description                                                    |
| -------------- | --------- | -------- | -------------------------------------------------------------- |
| `selectedOnly` | `boolean` | ✅       | `true` = only MarketWatch symbols, `false` = all symbols      |

---

## ⬆️ Output — `SymbolsTotalData`

| Field   | Type  | Description                                                                |
| ------- | ----- | -------------------------------------------------------------------------- |
| `total` | `int` | Number of symbols (MarketWatch count if `mode=true`, all symbols if `false`) |

Access the total using `reply.getData().getTotal()`.

---

## 💬 Just the essentials

* **What it is.** Simple RPC returning the count of available symbols.
* **Why you need it.** To determine how many symbols exist before iterating through them with `symbolName()`.
* **Performance.** Very lightweight call — just returns a count, no symbol details.
* **Use case.** Typically used with `symbolName()` to iterate through all symbols.

---

## 🎯 Purpose

Use this method when you need to:

* Count how many symbols are in MarketWatch.
* Get the total number of symbols available on the broker.
* Iterate through all symbols using `symbolName(index, selectedOnly)`.
* Verify symbol availability before requesting data.

---

## 🧩 Notes & Tips

* Pass `true` to count only symbols in MarketWatch (selected symbols).
* Pass `false` to count all symbols available on the broker (can be thousands).
* Use this with `symbolName()` to loop through all symbols.
* The method uses automatic reconnection via `executeWithReconnect()`.
* MarketWatch symbols are those you've selected or that auto-selected for margin calculations.

---

## 🔗 Usage Examples

### 1) Count MarketWatch symbols

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Count symbols in MarketWatch
            Mt5TermApiMarketInfo.SymbolsTotalReply reply =
                account.symbolsTotal(true);
            int count = reply.getData().getTotal();

            System.out.printf("Symbols in MarketWatch: %d%n", count);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Count all available symbols

```java
// Get total number of symbols on broker
var reply = account.symbolsTotal(false);
int totalSymbols = reply.getData().getTotal();

System.out.printf("Total symbols available: %d%n", totalSymbols);
```

### 3) Compare MarketWatch vs all symbols

```java
// Count selected symbols
var selectedReply = account.symbolsTotal(true);
int selectedCount = selectedReply.getData().getTotal();

// Count all symbols
var allReply = account.symbolsTotal(false);
int allCount = allReply.getData().getTotal();

System.out.printf("MarketWatch symbols: %d%n", selectedCount);
System.out.printf("Total symbols:       %d%n", allCount);
System.out.printf("Not in MarketWatch:  %d%n", allCount - selectedCount);
```

### 4) Iterate through all MarketWatch symbols

```java
// Get count of symbols in MarketWatch
var reply = account.symbolsTotal(true);
int count = reply.getData().getTotal();

System.out.printf("Listing %d symbols in MarketWatch:%n", count);

// Iterate through all selected symbols
for (int i = 0; i < count; i++) {
    var nameReply = account.symbolName(i, true);
    String symbolName = nameReply.getData().getName();
    System.out.printf("%d. %s%n", i + 1, symbolName);
}
```

### 5) Iterate through all available symbols

```java
// Get total count
var reply = account.symbolsTotal(false);
int total = reply.getData().getTotal();

System.out.printf("Found %d total symbols:%n", total);

// WARNING: This can be thousands of symbols!
for (int i = 0; i < Math.min(total, 10); i++) {  // Limit to first 10
    var nameReply = account.symbolName(i, false);
    String symbol = nameReply.getData().getName();
    System.out.printf("%d. %s%n", i + 1, symbol);
}

if (total > 10) {
    System.out.printf("... and %d more symbols%n", total - 10);
}
```

### 6) Find specific symbols with pattern

```java
public class SymbolFinder {
    /**
     * Find all symbols matching a pattern (e.g., "EUR*")
     */
    public static java.util.List<String> findSymbols(
            MT5Account account,
            String pattern,
            boolean selectedOnly) throws ApiExceptionMT5 {

        var reply = account.symbolsTotal(selectedOnly);
        int total = reply.getData().getTotal();

        java.util.List<String> matchingSymbols = new java.util.ArrayList<>();

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            String symbol = nameReply.getData().getName();

            if (symbol.matches(pattern.replace("*", ".*"))) {
                matchingSymbols.add(symbol);
            }
        }

        return matchingSymbols;
    }
}

// Usage - find all EUR pairs in MarketWatch
var eurPairs = SymbolFinder.findSymbols(account, "EUR.*", true);
System.out.println("EUR pairs: " + eurPairs);
```

### 7) Check if any symbols in MarketWatch

```java
public class MarketWatchChecker {
    /**
     * Check if MarketWatch has any symbols
     */
    public static boolean hasSymbolsInMarketWatch(MT5Account account)
            throws ApiExceptionMT5 {

        var reply = account.symbolsTotal(true);
        int count = reply.getData().getTotal();

        if (count == 0) {
            System.out.println("⚠️ No symbols in MarketWatch!");
            return false;
        }

        System.out.printf("✅ MarketWatch has %d symbols%n", count);
        return true;
    }
}
```

### 8) Get all symbol names efficiently

```java
public class SymbolList {
    /**
     * Get list of all symbol names in MarketWatch
     */
    public static java.util.List<String> getAllSymbolNames(
            MT5Account account,
            boolean selectedOnly) throws ApiExceptionMT5 {

        // Get total count
        var reply = account.symbolsTotal(selectedOnly);
        int total = reply.getData().getTotal();

        // Pre-allocate list
        java.util.List<String> symbols = new java.util.ArrayList<>(total);

        // Fetch all symbol names
        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            symbols.add(nameReply.getData().getName());
        }

        return symbols;
    }
}

// Usage
var marketWatchSymbols = SymbolList.getAllSymbolNames(account, true);
System.out.println("MarketWatch symbols: " + marketWatchSymbols);
```

### 9) Display symbol statistics

```java
public class SymbolStats {
    public static void displayStatistics(MT5Account account) throws ApiExceptionMT5 {
        var selectedReply = account.symbolsTotal(true);
        var allReply = account.symbolsTotal(false);

        int selected = selectedReply.getData().getTotal();
        int total = allReply.getData().getTotal();

        double percentage = (selected * 100.0) / total;

        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║      SYMBOL STATISTICS            ║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.printf("║ Total available:      %-11d ║%n", total);
        System.out.printf("║ In MarketWatch:       %-11d ║%n", selected);
        System.out.printf("║ Not selected:         %-11d ║%n", total - selected);
        System.out.printf("║ Selection ratio:      %-7.2f %%   ║%n", percentage);
        System.out.println("╚═══════════════════════════════════╝");
    }
}
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolsTotalRequest request =
    Mt5TermApiMarketInfo.SymbolsTotalRequest.newBuilder()
        .setMode(true)  // true = MarketWatch only
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolsTotalReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolsTotal(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
int total = reply.getData().getTotal();
```
