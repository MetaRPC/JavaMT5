# ✅ Getting Symbol String Properties

> **Request:** retrieve specific string properties of a trading symbol. Access symbol descriptions, currency information, exchange details, and other textual data.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolInfoString(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolInfoString` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolInfoString(SymbolInfoStringRequest) → SymbolInfoStringReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolInfoString(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets a specific string property of a trading symbol.
     * Returns textual information such as description, currencies, exchange, category, etc.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param property Property type to retrieve (e.g., SYMBOL_DESCRIPTION, SYMBOL_CURRENCY_BASE)
     * @return Property value as string
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoStringReply symbolInfoString(
        String symbol,
        Mt5TermApiMarketInfo.SymbolInfoStringProperty property) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolInfoStringRequest { symbol: string, type: SymbolInfoStringProperty }`

**Reply message:** `SymbolInfoStringReply { data: SymbolInfoStringData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter  | Type                           | Required | Description                                      |
| ---------- | ------------------------------ | -------- | ------------------------------------------------ |
| `symbol`   | `String`                       | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")           |
| `property` | `SymbolInfoStringProperty`     | ✅       | Property to retrieve (see enum below)            |

---

## ⬆️ Output - `SymbolInfoStringData`

| Field   | Type     | Description                      |
| ------- | -------- | -------------------------------- |
| `value` | `String` | The requested property value     |

Access using `reply.getData().getValue()`.

---

## 🧱 Related enums (from proto)

### `SymbolInfoStringProperty`

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_BASIS` | 0 | The underlying asset of a derivative |
| `SYMBOL_CATEGORY` | 1 | Category/sector name (e.g., "Forex", "Metals") |
| `SYMBOL_COUNTRY` | 2 | Country to which the symbol belongs |
| `SYMBOL_SECTOR_NAME` | 3 | Economic sector name |
| `SYMBOL_INDUSTRY_NAME` | 4 | Industry branch name |
| `SYMBOL_CURRENCY_BASE` | 5 | Base currency (first currency in pair, e.g., "EUR" in EURUSD) |
| `SYMBOL_CURRENCY_PROFIT` | 6 | Profit currency (typically account currency) |
| `SYMBOL_CURRENCY_MARGIN` | 7 | Margin currency (currency for margin requirements) |
| `SYMBOL_BANK` | 8 | Feeder/source of current quote |
| `SYMBOL_DESCRIPTION` | 9 | Human-readable symbol description |
| `SYMBOL_EXCHANGE` | 10 | Exchange name where symbol is traded |
| `SYMBOL_FORMULA` | 11 | Formula for custom symbol pricing |
| `SYMBOL_ISIN` | 12 | ISIN code (International Securities Identification Number) |
| `SYMBOL_PAGE` | 13 | Web page URL with symbol information |
| `SYMBOL_PATH` | 14 | Path in symbol tree/hierarchy |

---

## 💬 Just the essentials

* **What it is.** RPC to get textual/descriptive properties of a symbol.
* **Why you need it.** Get human-readable descriptions, identify currencies, check exchange info.
* **Currency info.** Critical for understanding what you're trading and how P/L is calculated.
* **Custom symbols.** `SYMBOL_FORMULA` shows how synthetic symbols are calculated.

---

## 🎯 Purpose

Use this method when you need to:

* Display symbol descriptions in UI.
* Identify base and quote currencies.
* Determine profit/margin currencies for P/L calculations.
* Get exchange information for stocks/futures.
* Access ISIN codes for compliance/reporting.
* Understand custom symbol formulas.

---

## 🧩 Notes & Tips

* `SYMBOL_CURRENCY_BASE` is the first currency in Forex pairs (EUR in EURUSD).
* `SYMBOL_CURRENCY_PROFIT` determines how profit is calculated/converted.
* `SYMBOL_CURRENCY_MARGIN` determines margin calculation currency.
* `SYMBOL_DESCRIPTION` provides user-friendly names for display.
* The method uses automatic reconnection via `executeWithReconnect()`.

---

## 🔗 Usage Examples

### 1) Get symbol description

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get description
            var reply = account.symbolInfoString(
                "EURUSD",
                Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION
            );
            String description = reply.getData().getValue();

            System.out.printf("EURUSD: %s%n", description);
            // Output: "Euro vs US Dollar" or similar

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Get currency information

```java
public class CurrencyInfo {
    public record SymbolCurrencies(
        String base,
        String profit,
        String margin
    ) {}

    /**
     * Get all currency information for a symbol
     */
    public static SymbolCurrencies getCurrencies(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        var baseReply = account.symbolInfoString(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_BASE
        );
        var profitReply = account.symbolInfoString(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_PROFIT
        );
        var marginReply = account.symbolInfoString(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_MARGIN
        );

        return new SymbolCurrencies(
            baseReply.getData().getValue(),
            profitReply.getData().getValue(),
            marginReply.getData().getValue()
        );
    }

    /**
     * Print currency information
     */
    public static void printCurrencies(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var currencies = getCurrencies(account, symbol);

        System.out.printf("=== %s Currency Information ===%n", symbol);
        System.out.printf("Base currency:   %s (what you buy/sell)%n", currencies.base());
        System.out.printf("Profit currency: %s (P/L calculated in)%n", currencies.profit());
        System.out.printf("Margin currency: %s (margin required in)%n", currencies.margin());
    }
}

// Usage
CurrencyInfo.printCurrencies(account, "EURUSD");
CurrencyInfo.printCurrencies(account, "XAUUSD");
```

### 3) Build symbol info display

```java
public class SymbolDisplay {
    /**
     * Get complete displayable symbol information
     */
    public static String getDisplayInfo(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var descReply = account.symbolInfoString(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION
        );
        var categoryReply = account.symbolInfoString(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CATEGORY
        );

        String description = descReply.getData().getValue();
        String category = categoryReply.getData().getValue();

        return String.format("%s - %s (%s)", symbol, description, category);
    }
}

// Usage
String display = SymbolDisplay.getDisplayInfo(account, "GBPUSD");
System.out.println(display);
// Output: "GBPUSD - British Pound vs US Dollar (Forex)"
```

### 4) Check if symbols are from same category

```java
public class SymbolCategorizer {
    /**
     * Get symbol category
     */
    public static String getCategory(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var reply = account.symbolInfoString(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CATEGORY
        );
        return reply.getData().getValue();
    }

    /**
     * Check if symbols are from same category
     */
    public static boolean isSameCategory(
            MT5Account account,
            String symbol1,
            String symbol2) throws ApiExceptionMT5 {

        String cat1 = getCategory(account, symbol1);
        String cat2 = getCategory(account, symbol2);

        return cat1.equalsIgnoreCase(cat2);
    }
}

// Usage
if (SymbolCategorizer.isSameCategory(account, "EURUSD", "GBPUSD")) {
    System.out.println("Both symbols are from the same category");
}
```

### 5) Symbol information record

```java
public record SymbolInfo(
    String symbol,
    String description,
    String category,
    String baseCurrency,
    String profitCurrency,
    String marginCurrency,
    String exchange
) {
    /**
     * Build from MT5 account
     */
    public static SymbolInfo fromMT5(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        return new SymbolInfo(
            symbol,
            getString(account, symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION),
            getString(account, symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CATEGORY),
            getString(account, symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_BASE),
            getString(account, symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_PROFIT),
            getString(account, symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_MARGIN),
            getString(account, symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_EXCHANGE)
        );
    }

    private static String getString(
            MT5Account account,
            String symbol,
            Mt5TermApiMarketInfo.SymbolInfoStringProperty property)
            throws ApiExceptionMT5 {

        var reply = account.symbolInfoString(symbol, property);
        return reply.getData().getValue();
    }

    @Override
    public String toString() {
        return String.format(
            "%s (%s)%n" +
            "  Description: %s%n" +
            "  Category: %s%n" +
            "  Currencies: %s/%s (margin: %s)%n" +
            "  Exchange: %s",
            symbol, description,
            description,
            category,
            baseCurrency, profitCurrency, marginCurrency,
            exchange.isEmpty() ? "N/A" : exchange
        );
    }
}

// Usage
var info = SymbolInfo.fromMT5(account, "EURUSD");
System.out.println(info);
```

### 6) Filter symbols by category

```java
public class SymbolFilter {
    /**
     * Get all symbols from specific category
     */
    public static java.util.List<String> getSymbolsByCategory(
            MT5Account account,
            String targetCategory,
            boolean selectedOnly) throws ApiExceptionMT5 {

        // Get all symbols
        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        java.util.List<String> matching = new java.util.ArrayList<>();

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            String symbol = nameReply.getData().getName();

            try {
                var catReply = account.symbolInfoString(
                    symbol,
                    Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CATEGORY
                );
                String category = catReply.getData().getValue();

                if (category.equalsIgnoreCase(targetCategory)) {
                    matching.add(symbol);
                }
            } catch (ApiExceptionMT5 e) {
                // Skip symbols that don't have category
            }
        }

        return matching;
    }
}

// Usage - get all Forex symbols
var forexSymbols = SymbolFilter.getSymbolsByCategory(account, "Forex", true);
System.out.println("Forex symbols: " + forexSymbols);
```

### 7) Symbol details panel

```java
public class SymbolDetailsPanel {
    public static void printDetails(MT5Account account, String symbol) {
        try {
            System.out.printf("\n╔═══════════════════════════════════════════════╗%n");
            System.out.printf("║        SYMBOL DETAILS: %-20s║%n", symbol);
            System.out.printf("╠═══════════════════════════════════════════════╣%n");

            // Description
            var descReply = account.symbolInfoString(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION
            );
            System.out.printf("║ Description:  %-31s ║%n",
                truncate(descReply.getData().getValue(), 31));

            // Category
            var catReply = account.symbolInfoString(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CATEGORY
            );
            System.out.printf("║ Category:     %-31s ║%n",
                truncate(catReply.getData().getValue(), 31));

            // Currencies
            var baseReply = account.symbolInfoString(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_BASE
            );
            var profitReply = account.symbolInfoString(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_PROFIT
            );

            System.out.printf("║ Base:         %-31s ║%n", baseReply.getData().getValue());
            System.out.printf("║ Profit:       %-31s ║%n", profitReply.getData().getValue());

            // Exchange
            var exReply = account.symbolInfoString(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_EXCHANGE
            );
            String exchange = exReply.getData().getValue();
            System.out.printf("║ Exchange:     %-31s ║%n",
                exchange.isEmpty() ? "N/A" : truncate(exchange, 31));

            System.out.printf("╚═══════════════════════════════════════════════╝%n");

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String truncate(String str, int maxLen) {
        if (str.length() <= maxLen) return str;
        return str.substring(0, maxLen - 3) + "...";
    }
}

// Usage
SymbolDetailsPanel.printDetails(account, "EURUSD");
```

### 8) Create symbol dropdown data

```java
public class SymbolDropdownBuilder {
    public record SymbolItem(
        String symbol,
        String displayText,
        String category
    ) implements Comparable<SymbolItem> {
        @Override
        public int compareTo(SymbolItem other) {
            int catCompare = this.category.compareTo(other.category);
            if (catCompare != 0) return catCompare;
            return this.symbol.compareTo(other.symbol);
        }
    }

    /**
     * Build sorted list for UI dropdown
     */
    public static java.util.List<SymbolItem> buildDropdownList(
            MT5Account account,
            boolean selectedOnly) throws ApiExceptionMT5 {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        java.util.List<SymbolItem> items = new java.util.ArrayList<>();

        for (int i = 0; i < total; i++) {
            var nameReply = account.symbolName(i, selectedOnly);
            String symbol = nameReply.getData().getName();

            try {
                var descReply = account.symbolInfoString(
                    symbol,
                    Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION
                );
                var catReply = account.symbolInfoString(
                    symbol,
                    Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CATEGORY
                );

                String desc = descReply.getData().getValue();
                String cat = catReply.getData().getValue();
                String displayText = String.format("%s - %s", symbol, desc);

                items.add(new SymbolItem(symbol, displayText, cat));

            } catch (ApiExceptionMT5 e) {
                // Use symbol name only if description not available
                items.add(new SymbolItem(symbol, symbol, "Other"));
            }
        }

        java.util.Collections.sort(items);
        return items;
    }

    /**
     * Print dropdown list
     */
    public static void printDropdownList(java.util.List<SymbolItem> items) {
        String lastCategory = "";

        for (var item : items) {
            if (!item.category().equals(lastCategory)) {
                System.out.printf("%n=== %s ===%n", item.category());
                lastCategory = item.category();
            }
            System.out.println("  " + item.displayText());
        }
    }
}

// Usage
var dropdownItems = SymbolDropdownBuilder.buildDropdownList(account, true);
SymbolDropdownBuilder.printDropdownList(dropdownItems);
```

### 9) Export symbol database

```java
public class SymbolDatabase {
    /**
     * Export symbol information to CSV
     */
    public static void exportToCSV(
            MT5Account account,
            String filename,
            boolean selectedOnly) throws Exception {

        var totalReply = account.symbolsTotal(selectedOnly);
        int total = totalReply.getData().getTotal();

        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            // CSV header
            writer.println("Symbol,Description,Category,BaseCurrency,ProfitCurrency,Exchange");

            for (int i = 0; i < total; i++) {
                var nameReply = account.symbolName(i, selectedOnly);
                String symbol = nameReply.getData().getName();

                try {
                    String desc = account.symbolInfoString(symbol,
                        Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION
                    ).getData().getValue();

                    String cat = account.symbolInfoString(symbol,
                        Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CATEGORY
                    ).getData().getValue();

                    String base = account.symbolInfoString(symbol,
                        Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_BASE
                    ).getData().getValue();

                    String profit = account.symbolInfoString(symbol,
                        Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_PROFIT
                    ).getData().getValue();

                    String exchange = account.symbolInfoString(symbol,
                        Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_EXCHANGE
                    ).getData().getValue();

                    writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                        symbol, desc, cat, base, profit, exchange);

                } catch (ApiExceptionMT5 e) {
                    // Write partial data
                    writer.printf("\"%s\",\"N/A\",\"N/A\",\"N/A\",\"N/A\",\"N/A\"%n", symbol);
                }
            }
        }

        System.out.printf("✅ Exported %d symbols to %s%n", total, filename);
    }
}

// Usage
SymbolDatabase.exportToCSV(account, "symbols.csv", false);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolInfoStringRequest request =
    Mt5TermApiMarketInfo.SymbolInfoStringRequest.newBuilder()
        .setSymbol("EURUSD")
        .setType(Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolInfoStringReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolInfoString(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
String value = reply.getData().getValue();
```
