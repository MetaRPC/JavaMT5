# ✅ Get Symbol Name by Index

> **Request:** retrieve symbol name by its index position. Use with `symbolsTotal()` to iterate through all available symbols.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolName(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolName` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolName(SymbolNameRequest) → SymbolNameReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolName(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets the name of a symbol by its index position.
     * Use in combination with symbolsTotal() to iterate through all symbols.
     *
     * @param index Zero-based index of the symbol
     * @param selectedOnly If true, search only in MarketWatch; if false, search all symbols
     * @return Symbol name at the specified index
     * @throws ApiExceptionMT5 if the call fails or index is out of range
     */
    public Mt5TermApiMarketInfo.SymbolNameReply symbolName(int index, boolean selectedOnly) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolNameRequest { index: int32, selected: bool }`
**Reply message:** `SymbolNameReply { data: SymbolNameData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type      | Required | Description                                                    |
| -------------- | --------- | -------- | -------------------------------------------------------------- |
| `index`        | `int`     | ✅       | Zero-based index (0 to total-1)                                |
| `selectedOnly` | `boolean` | ✅       | `true` = MarketWatch only, `false` = all available symbols     |

---

## ⬆️ Output — `SymbolNameData`

| Field  | Type     | Description                              |
| ------ | -------- | ---------------------------------------- |
| `name` | `String` | Symbol name at the specified index       |

Access the name using `reply.getData().getName()`.

---

## 💬 Just the essentials

* **What it is.** RPC to get symbol name by index position in the symbol list.
* **Why you need it.** To iterate through all symbols with `symbolsTotal()` + loop.
* **Index range.** Must be `0 <= index < symbolsTotal()` or will throw error.
* **Use case.** Primary method for discovering all available symbols on broker.

---

## 🎯 Purpose

Use this method when you need to:

* Iterate through all symbols in MarketWatch.
* Discover all symbols available on the broker.
* Build a complete symbol list programmatically.
* Find symbols matching specific patterns.
* Create symbol selection UIs.

---

## 🧩 Notes & Tips

* Always call `symbolsTotal()` first to get the valid index range.
* Index is zero-based: valid range is `[0, total-1]`.
* Out-of-range index will throw `ApiExceptionMT5`.
* The method uses automatic reconnection via `executeWithReconnect()`.
* Symbol order may change if symbols are added/removed between calls.

---

## 🔗 Usage Examples

### 1) Get symbol by index

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get first symbol in MarketWatch
            Mt5TermApiMarketInfo.SymbolNameReply reply =
                account.symbolName(0, true);
            String symbolName = reply.getData().getName();

            System.out.printf("First symbol in MarketWatch: %s%n", symbolName);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) List all MarketWatch symbols

```java
// Get count
var totalReply = account.symbolsTotal(true);
int total = totalReply.getData().getTotal();

System.out.printf("Symbols in MarketWatch: %d%n", total);
System.out.println("═".repeat(40));

// Iterate through all
for (int i = 0; i < total; i++) {
    var nameReply = account.symbolName(i, true);
    String symbol = nameReply.getData().getName();
    System.out.printf("%3d. %s%n", i + 1, symbol);
}
```

### 3) List all available symbols (limited)

```java
// Get total count
var totalReply = account.symbolsTotal(false);
int total = totalReply.getData().getTotal();

System.out.printf("Total available symbols: %d%n", total);
System.out.println("Showing first 20:");
System.out.println("═".repeat(40));

// Iterate (limit to avoid thousands of symbols)
int limit = Math.min(total, 20);
for (int i = 0; i < limit; i++) {
    var nameReply = account.symbolName(i, false);
    String symbol = nameReply.getData().getName();
    System.out.printf("%3d. %s%n", i + 1, symbol);
}

if (total > limit) {
    System.out.printf("... and %d more%n", total - limit);
}
```

### 4) Get all symbol names as List

```java
public class SymbolListHelper {
    /**
     * Get all symbol names as a List
     */
    public static java.util.List<String> getAllSymbols(
            MT5Account account,
            boolean selectedOnly) throws ApiExceptionMT5 {

        // Get total
        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        // Pre-allocate list
        java.util.List<String> symbols = new java.util.ArrayList<>(total);

        // Fetch all names
        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            symbols.add(nameReply.getData().getName());
        }

        return symbols;
    }
}

// Usage
var marketWatchSymbols = SymbolListHelper.getAllSymbols(account, true);
System.out.println("MarketWatch: " + marketWatchSymbols);

var allSymbols = SymbolListHelper.getAllSymbols(account, false);
System.out.printf("Total symbols: %d%n", allSymbols.size());
```

### 5) Find symbols by prefix

```java
public class SymbolFinder {
    /**
     * Find all symbols starting with prefix
     */
    public static java.util.List<String> findByPrefix(
            MT5Account account,
            String prefix,
            boolean selectedOnly) throws ApiExceptionMT5 {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        java.util.List<String> matching = new java.util.ArrayList<>();

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            String symbol = nameReply.getData().getName();

            if (symbol.startsWith(prefix)) {
                matching.add(symbol);
            }
        }

        return matching;
    }
}

// Usage - find all EUR pairs
var eurPairs = SymbolFinder.findByPrefix(account, "EUR", true);
System.out.println("EUR pairs: " + eurPairs);

// Find all gold symbols
var goldSymbols = SymbolFinder.findByPrefix(account, "XAU", false);
System.out.println("Gold symbols: " + goldSymbols);
```

### 6) Find symbols by pattern (regex)

```java
public class SymbolPatternMatcher {
    /**
     * Find symbols matching regex pattern
     */
    public static java.util.List<String> findByPattern(
            MT5Account account,
            String regex,
            boolean selectedOnly) throws ApiExceptionMT5 {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        java.util.List<String> matching = new java.util.ArrayList<>();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            String symbol = nameReply.getData().getName();

            if (pattern.matcher(symbol).matches()) {
                matching.add(symbol);
            }
        }

        return matching;
    }
}

// Usage - find major FX pairs (6 characters)
var majorPairs = SymbolPatternMatcher.findByPattern(
    account, "^[A-Z]{6}$", true
);
System.out.println("Major pairs: " + majorPairs);

// Find symbols ending with specific suffix
var suffixSymbols = SymbolPatternMatcher.findByPattern(
    account, ".*\\.m$", false
);
System.out.println("Symbols with .m suffix: " + suffixSymbols);
```

### 7) Group symbols by type

```java
public class SymbolGrouper {
    /**
     * Group symbols by currency, metals, crypto, etc.
     */
    public static java.util.Map<String, java.util.List<String>> groupSymbols(
            MT5Account account,
            boolean selectedOnly) throws ApiExceptionMT5 {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        java.util.Map<String, java.util.List<String>> groups = new java.util.LinkedHashMap<>();
        groups.put("Forex", new java.util.ArrayList<>());
        groups.put("Metals", new java.util.ArrayList<>());
        groups.put("Crypto", new java.util.ArrayList<>());
        groups.put("Indices", new java.util.ArrayList<>());
        groups.put("Other", new java.util.ArrayList<>());

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            String symbol = nameReply.getData().getName();

            if (symbol.matches("^[A-Z]{6}$")) {
                groups.get("Forex").add(symbol);
            } else if (symbol.startsWith("XAU") || symbol.startsWith("XAG")) {
                groups.get("Metals").add(symbol);
            } else if (symbol.contains("BTC") || symbol.contains("ETH")) {
                groups.get("Crypto").add(symbol);
            } else if (symbol.contains("30") || symbol.contains("500")) {
                groups.get("Indices").add(symbol);
            } else {
                groups.get("Other").add(symbol);
            }
        }

        return groups;
    }

    /**
     * Print grouped symbols
     */
    public static void printGroups(java.util.Map<String, java.util.List<String>> groups) {
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║     SYMBOLS BY CATEGORY           ║");
        System.out.println("╠═══════════════════════════════════╣");

        for (var entry : groups.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                System.out.printf("║ %-15s: %-3d symbols     ║%n",
                    entry.getKey(), entry.getValue().size());
                for (String symbol : entry.getValue()) {
                    System.out.printf("║   • %-29s ║%n", symbol);
                }
                System.out.println("╠═══════════════════════════════════╣");
            }
        }
        System.out.println("╚═══════════════════════════════════╝");
    }
}

// Usage
var groups = SymbolGrouper.groupSymbols(account, true);
SymbolGrouper.printGroups(groups);
```

### 8) Build symbol cache

```java
public class SymbolCache {
    private final java.util.List<String> symbols;
    private final java.util.Map<String, Integer> indexMap;

    private SymbolCache(java.util.List<String> symbols) {
        this.symbols = java.util.List.copyOf(symbols);
        this.indexMap = new java.util.HashMap<>();

        for (int i = 0; i < symbols.size(); i++) {
            indexMap.put(symbols.get(i), i);
        }
    }

    /**
     * Build cache from MT5
     */
    public static SymbolCache build(MT5Account account, boolean selectedOnly)
            throws ApiExceptionMT5 {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        java.util.List<String> symbols = new java.util.ArrayList<>(total);

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            symbols.add(nameReply.getData().getName());
        }

        return new SymbolCache(symbols);
    }

    public java.util.List<String> getAll() {
        return symbols;
    }

    public int getCount() {
        return symbols.size();
    }

    public boolean contains(String symbol) {
        return indexMap.containsKey(symbol);
    }

    public int getIndex(String symbol) {
        return indexMap.getOrDefault(symbol, -1);
    }

    public String getByIndex(int index) {
        if (index < 0 || index >= symbols.size()) {
            return null;
        }
        return symbols.get(index);
    }

    public java.util.List<String> filter(java.util.function.Predicate<String> predicate) {
        return symbols.stream()
            .filter(predicate)
            .collect(java.util.stream.Collectors.toList());
    }
}

// Usage
var cache = SymbolCache.build(account, true);

System.out.printf("Cached %d symbols%n", cache.getCount());
System.out.println("EUR pairs: " + cache.filter(s -> s.startsWith("EUR")));
System.out.println("EURUSD exists: " + cache.contains("EURUSD"));
System.out.println("EURUSD index: " + cache.getIndex("EURUSD"));
```

### 9) Export symbols to file

```java
public class SymbolExporter {
    /**
     * Export all symbols to text file
     */
    public static void exportToFile(
            MT5Account account,
            String filename,
            boolean selectedOnly) throws Exception {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.println("# MT5 Symbols Export");
            writer.println("# Generated: " + java.time.LocalDateTime.now());
            writer.println("# Source: " + (selectedOnly ? "MarketWatch" : "All Symbols"));
            writer.println("# Total: " + total);
            writer.println();

            for (int i = 0; i < total; i++) {
                var nameReply = account.symbolName(i, selectedOnly);
                String symbol = nameReply.getData().getName();
                writer.println(symbol);
            }
        }

        System.out.printf("✅ Exported %d symbols to %s%n", total, filename);
    }

    /**
     * Export with detailed info
     */
    public static void exportDetailed(
            MT5Account account,
            String filename,
            boolean selectedOnly) throws Exception {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.println("Index,Symbol,Type");

            for (int i = 0; i < total; i++) {
                var nameReply = account.symbolName(i, selectedOnly);
                String symbol = nameReply.getData().getName();

                // Determine type
                String type = "Other";
                if (symbol.matches("^[A-Z]{6}$")) type = "Forex";
                else if (symbol.startsWith("XAU")) type = "Gold";
                else if (symbol.contains("BTC")) type = "Crypto";

                writer.printf("%d,%s,%s%n", i, symbol, type);
            }
        }

        System.out.printf("✅ Exported %d symbols with details to %s%n", total, filename);
    }
}

// Usage
SymbolExporter.exportToFile(account, "symbols.txt", true);
SymbolExporter.exportDetailed(account, "symbols.csv", false);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolNameRequest request =
    Mt5TermApiMarketInfo.SymbolNameRequest.newBuilder()
        .setIndex(0)
        .setSelected(true)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolNameReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolName(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
String name = reply.getData().getName();
```
