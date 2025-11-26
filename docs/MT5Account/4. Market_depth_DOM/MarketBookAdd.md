# 📊 Subscribe to Market Depth (DOM / Level II)

> **Request:** subscribe to Market Depth (Depth of Market / Level II) updates for a symbol. Enables access to order book data showing pending buy/sell orders at different price levels.

**API Information:**

* **SDK wrapper:** `MT5Account.marketBookAdd(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `MarketBookAdd` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `MarketBookAdd(MarketBookAddRequest) → MarketBookAddReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.marketBookAdd(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Subscribes to Market Depth (DOM/Level II) updates for a specified symbol.
     * After subscription, you can retrieve current order book data showing pending buy and sell orders.
     * Use this to access liquidity information and see the market depth before placing large orders.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Subscription confirmation response
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.MarketBookAddReply marketBookAdd(String symbol) throws ApiExceptionMT5;
}
```

**Request message:** `MarketBookAddRequest { symbol: string }`
**Reply message:** `MarketBookAddReply { data: MarketBookAddData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")   |

---

## ⬆️ Output — `MarketBookAddData`

| Field                  | Type      | Description                                                  |
| ---------------------- | --------- | ------------------------------------------------------------ |
| `opened_successfully`  | `boolean` | `true` if subscription successful, `false` otherwise         |

Access using `reply.getData().getOpenedSuccessfully()`.

---

## 💬 Just the essentials

* **What it is.** RPC to subscribe to Market Depth (order book) updates for a symbol.
* **Why you need it.** Access liquidity data, see pending orders at different price levels.
* **Required first.** Must subscribe before calling `marketBookGet()` to retrieve data.
* **Broker support.** Not all brokers/symbols support Market Depth - check availability first.
* **Resource usage.** Subscription uses resources - call `marketBookRelease()` when done.
* **Use case.** Analyze liquidity, identify support/resistance, optimize large order execution.

---

## 🎯 Purpose

Use this method when you need to:

* Access order book data (pending buy/sell orders at different prices).
* Analyze market liquidity before placing large orders.
* Identify support and resistance levels from order concentration.
* Assess market depth to avoid slippage on big trades.
* Monitor order flow for trading signals.
* Build advanced trading algorithms using Level II data.

---

## 🧩 Notes & Tips

* **Broker support.** Not all brokers provide Market Depth data - verify support first.
* **Symbol must exist.** Use `symbolExist()` to verify symbol availability.
* **Symbol selection.** Symbol doesn't need to be in MarketWatch for DOM subscription.
* **One subscription per symbol.** Calling twice for same symbol is safe (idempotent).
* **Resource cleanup.** Call `marketBookRelease()` when done to free resources.
* **After subscription.** Use `marketBookGet()` to retrieve actual order book data.
* **Auto-reconnect.** Uses `executeWithReconnect()` for reliability.
* **Success flag.** Check `opened_successfully` to verify subscription worked.

---

## 🔗 Usage Examples

### 1) Basic subscription

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Subscribe to Market Depth
            Mt5TermApiMarketInfo.MarketBookAddReply reply =
                account.marketBookAdd("EURUSD");

            if (reply.getData().getOpenedSuccessfully()) {
                System.out.println("✅ Successfully subscribed to EURUSD Market Depth");

                // Now you can use marketBookGet() to retrieve order book data
            } else {
                System.out.println("❌ Failed to subscribe to Market Depth");
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Subscribe with validation

```java
public class SafeSubscription {
    /**
     * Subscribe to Market Depth with validation
     */
    public static boolean subscribeWithValidation(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        System.out.printf("Attempting to subscribe to %s Market Depth...%n", symbol);

        // Step 1: Verify symbol exists
        var existReply = account.symbolExist(symbol);
        if (!existReply.getData().getExists()) {
            System.out.printf("❌ Symbol %s does not exist%n", symbol);
            return false;
        }
        System.out.println("✅ Symbol exists");

        // Step 2: Subscribe to Market Depth
        var reply = account.marketBookAdd(symbol);

        if (reply.getData().getOpenedSuccessfully()) {
            System.out.printf("✅ Successfully subscribed to %s Market Depth%n", symbol);
            return true;
        } else {
            System.out.printf("❌ Failed to subscribe - broker may not support DOM for %s%n", symbol);
            return false;
        }
    }
}

// Usage
if (SafeSubscription.subscribeWithValidation(account, "EURUSD")) {
    // Proceed to use marketBookGet()
}
```

### 3) Subscribe to multiple symbols

```java
import java.util.*;

public class MultiSymbolSubscription {
    /**
     * Subscribe to Market Depth for multiple symbols
     */
    public static Map<String, Boolean> subscribeMultiple(
            MT5Account account,
            String... symbols) {

        Map<String, Boolean> results = new LinkedHashMap<>();

        System.out.printf("Subscribing to Market Depth for %d symbols...%n", symbols.length);
        System.out.println("═".repeat(50));

        for (String symbol : symbols) {
            try {
                var reply = account.marketBookAdd(symbol);
                boolean success = reply.getData().getOpenedSuccessfully();
                results.put(symbol, success);

                String status = success ? "✅ SUCCESS" : "❌ FAILED";
                System.out.printf("%s: %s%n", symbol, status);

            } catch (ApiExceptionMT5 e) {
                results.put(symbol, false);
                System.out.printf("%s: ❌ ERROR - %s%n", symbol, e.getMessage());
            }
        }

        long successCount = results.values().stream()
            .filter(success -> success)
            .count();

        System.out.println("═".repeat(50));
        System.out.printf("Subscribed: %d/%d symbols%n", successCount, symbols.length);

        return results;
    }

    /**
     * Print subscription report
     */
    public static void printReport(Map<String, Boolean> results) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   MARKET DEPTH SUBSCRIPTIONS       ║");
        System.out.println("╠════════════════════════════════════╣");

        for (var entry : results.entrySet()) {
            String icon = entry.getValue() ? "✅" : "❌";
            String status = entry.getValue() ? "ACTIVE" : "FAILED";
            System.out.printf("║ %s %-10s %-16s ║%n",
                icon,
                entry.getKey(),
                status
            );
        }

        long activeCount = results.values().stream()
            .filter(success -> success)
            .count();

        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Active: %d/%d                       ║%n",
            activeCount, results.size());
        System.out.println("╚════════════════════════════════════╝");
    }
}

// Usage
var results = MultiSymbolSubscription.subscribeMultiple(account,
    "EURUSD", "GBPUSD", "USDJPY", "XAUUSD"
);
MultiSymbolSubscription.printReport(results);
```

### 4) Subscribe with retry logic

```java
public class RetrySubscription {
    /**
     * Subscribe with retry attempts
     */
    public static boolean subscribeWithRetry(
            MT5Account account,
            String symbol,
            int maxRetries,
            int retryDelayMs) throws InterruptedException {

        System.out.printf("Subscribing to %s (max retries: %d)...%n", symbol, maxRetries);

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                var reply = account.marketBookAdd(symbol);

                if (reply.getData().getOpenedSuccessfully()) {
                    System.out.printf("✅ Subscribed successfully (attempt %d/%d)%n",
                        attempt, maxRetries);
                    return true;
                }

                System.out.printf("⚠️ Subscription failed (attempt %d/%d)%n",
                    attempt, maxRetries);

            } catch (ApiExceptionMT5 e) {
                System.out.printf("❌ Error on attempt %d/%d: %s%n",
                    attempt, maxRetries, e.getMessage());
            }

            if (attempt < maxRetries) {
                System.out.printf("⏳ Waiting %dms before retry...%n", retryDelayMs);
                Thread.sleep(retryDelayMs);
            }
        }

        System.out.printf("❌ Failed to subscribe after %d attempts%n", maxRetries);
        return false;
    }
}

// Usage
if (RetrySubscription.subscribeWithRetry(account, "EURUSD", 3, 1000)) {
    System.out.println("Ready to retrieve Market Depth data");
}
```

### 5) Subscription manager class

```java
public class MarketDepthManager {
    private final MT5Account account;
    private final Set<String> subscribedSymbols = new HashSet<>();

    public MarketDepthManager(MT5Account account) {
        this.account = account;
    }

    /**
     * Subscribe to Market Depth
     */
    public boolean subscribe(String symbol) throws ApiExceptionMT5 {
        if (subscribedSymbols.contains(symbol)) {
            System.out.printf("⚠️ Already subscribed to %s%n", symbol);
            return true;
        }

        var reply = account.marketBookAdd(symbol);
        boolean success = reply.getData().getOpenedSuccessfully();

        if (success) {
            subscribedSymbols.add(symbol);
            System.out.printf("✅ Subscribed to %s Market Depth%n", symbol);
        } else {
            System.out.printf("❌ Failed to subscribe to %s%n", symbol);
        }

        return success;
    }

    /**
     * Unsubscribe from Market Depth
     */
    public boolean unsubscribe(String symbol) throws ApiExceptionMT5 {
        if (!subscribedSymbols.contains(symbol)) {
            System.out.printf("⚠️ Not subscribed to %s%n", symbol);
            return true;
        }

        var reply = account.marketBookRelease(symbol);
        boolean success = reply.getData().getReleasedSuccessfully();

        if (success) {
            subscribedSymbols.remove(symbol);
            System.out.printf("✅ Unsubscribed from %s Market Depth%n", symbol);
        } else {
            System.out.printf("❌ Failed to unsubscribe from %s%n", symbol);
        }

        return success;
    }

    /**
     * Check if subscribed
     */
    public boolean isSubscribed(String symbol) {
        return subscribedSymbols.contains(symbol);
    }

    /**
     * Get all subscribed symbols
     */
    public Set<String> getSubscribedSymbols() {
        return new HashSet<>(subscribedSymbols);
    }

    /**
     * Unsubscribe from all
     */
    public void unsubscribeAll() {
        System.out.printf("Unsubscribing from %d symbols...%n",
            subscribedSymbols.size());

        var symbols = new ArrayList<>(subscribedSymbols);
        for (String symbol : symbols) {
            try {
                unsubscribe(symbol);
            } catch (ApiExceptionMT5 e) {
                System.err.printf("Error unsubscribing from %s: %s%n",
                    symbol, e.getMessage());
            }
        }
    }

    /**
     * Print status
     */
    public void printStatus() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   MARKET DEPTH SUBSCRIPTIONS       ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Active subscriptions: %-12d ║%n",
            subscribedSymbols.size());

        if (!subscribedSymbols.isEmpty()) {
            System.out.println("╠════════════════════════════════════╣");
            for (String symbol : subscribedSymbols) {
                System.out.printf("║ ✅ %-30s ║%n", symbol);
            }
        }

        System.out.println("╚════════════════════════════════════╝");
    }
}

// Usage
var manager = new MarketDepthManager(account);

manager.subscribe("EURUSD");
manager.subscribe("GBPUSD");
manager.subscribe("XAUUSD");

manager.printStatus();

// Later: cleanup
manager.unsubscribeAll();
```

### 6) Check broker DOM support

```java
public class DomSupportChecker {
    /**
     * Check if broker supports Market Depth for symbol
     */
    public static boolean checkDomSupport(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        System.out.printf("Checking Market Depth support for %s...%n", symbol);

        // Try to subscribe
        var addReply = account.marketBookAdd(symbol);

        if (!addReply.getData().getOpenedSuccessfully()) {
            System.out.printf("❌ %s: Market Depth not supported%n", symbol);
            return false;
        }

        System.out.printf("✅ %s: Market Depth supported%n", symbol);

        // Try to get data to confirm
        try {
            var getReply = account.marketBookGet(symbol);
            int bookSize = getReply.getData().getMqlBookInfosList().size();
            System.out.printf("   Order book entries: %d%n", bookSize);

            // Clean up
            account.marketBookRelease(symbol);

            return true;

        } catch (ApiExceptionMT5 e) {
            System.out.printf("⚠️ %s: Subscribed but failed to get data: %s%n",
                symbol, e.getMessage());

            // Clean up
            try {
                account.marketBookRelease(symbol);
            } catch (ApiExceptionMT5 ignored) {}

            return false;
        }
    }

    /**
     * Test multiple symbols for DOM support
     */
    public static Map<String, Boolean> testMultipleSymbols(
            MT5Account account,
            String... symbols) {

        Map<String, Boolean> support = new LinkedHashMap<>();

        System.out.println("Testing Market Depth support...");
        System.out.println("═".repeat(50));

        for (String symbol : symbols) {
            try {
                boolean supported = checkDomSupport(account, symbol);
                support.put(symbol, supported);
            } catch (ApiExceptionMT5 e) {
                support.put(symbol, false);
                System.err.printf("❌ %s: Error - %s%n", symbol, e.getMessage());
            }
            System.out.println();
        }

        return support;
    }
}

// Usage
var support = DomSupportChecker.testMultipleSymbols(account,
    "EURUSD", "GBPUSD", "USDJPY", "BTCUSD"
);

System.out.println("Symbols with DOM support:");
support.forEach((symbol, supported) -> {
    if (supported) {
        System.out.printf("  ✅ %s%n", symbol);
    }
});
```

### 7) Subscribe on-demand before data retrieval

```java
public class LazyDomSubscription {
    private final MT5Account account;
    private final Set<String> subscribed = new HashSet<>();

    public LazyDomSubscription(MT5Account account) {
        this.account = account;
    }

    /**
     * Ensure subscription exists, subscribe if needed
     */
    public boolean ensureSubscription(String symbol) throws ApiExceptionMT5 {
        if (subscribed.contains(symbol)) {
            return true;  // Already subscribed
        }

        System.out.printf("Auto-subscribing to %s Market Depth...%n", symbol);

        var reply = account.marketBookAdd(symbol);
        boolean success = reply.getData().getOpenedSuccessfully();

        if (success) {
            subscribed.add(symbol);
            System.out.printf("✅ Subscribed to %s%n", symbol);
        } else {
            System.out.printf("❌ Failed to subscribe to %s%n", symbol);
        }

        return success;
    }

    /**
     * Get market book with auto-subscription
     */
    public Mt5TermApiMarketInfo.MarketBookGetReply getMarketBook(
            String symbol) throws ApiExceptionMT5 {

        // Ensure subscription exists
        if (!ensureSubscription(symbol)) {
            throw new ApiExceptionMT5("Failed to subscribe to Market Depth for " + symbol);
        }

        // Retrieve data
        return account.marketBookGet(symbol);
    }
}

// Usage
var lazyDom = new LazyDomSubscription(account);

// First call: auto-subscribes, then retrieves data
var book1 = lazyDom.getMarketBook("EURUSD");
System.out.printf("EURUSD order book size: %d%n",
    book1.getData().getMqlBookInfosList().size());

// Second call: already subscribed, just retrieves data
var book2 = lazyDom.getMarketBook("EURUSD");
```

### 8) Batch subscription with progress tracking

```java
public class BatchSubscription {
    /**
     * Subscribe to multiple symbols with progress tracking
     */
    public static Map<String, Boolean> batchSubscribe(
            MT5Account account,
            String... symbols) {

        Map<String, Boolean> results = new LinkedHashMap<>();
        int total = symbols.length;

        System.out.printf("Batch subscribing to %d symbols...%n", total);
        System.out.println();

        for (int i = 0; i < total; i++) {
            String symbol = symbols[i];
            int progress = (int) ((i + 1) * 100.0 / total);

            System.out.printf("[%3d%%] Subscribing to %s... ", progress, symbol);

            try {
                var reply = account.marketBookAdd(symbol);
                boolean success = reply.getData().getOpenedSuccessfully();
                results.put(symbol, success);

                System.out.println(success ? "✅ OK" : "❌ FAILED");

            } catch (ApiExceptionMT5 e) {
                results.put(symbol, false);
                System.out.printf("❌ ERROR: %s%n", e.getMessage());
            }

            // Small delay between subscriptions
            if (i < total - 1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        long successCount = results.values().stream()
            .filter(success -> success)
            .count();

        System.out.println();
        System.out.printf("✅ Successfully subscribed: %d/%d symbols%n",
            successCount, total);

        return results;
    }
}

// Usage
var results = BatchSubscription.batchSubscribe(account,
    "EURUSD", "GBPUSD", "USDJPY", "AUDUSD", "USDCHF",
    "NZDUSD", "USDCAD", "XAUUSD", "XAGUSD", "BTCUSD"
);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.MarketBookAddRequest request =
    Mt5TermApiMarketInfo.MarketBookAddRequest.newBuilder()
        .setSymbol("EURUSD")
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.MarketBookAddReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .marketBookAdd(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Check success
boolean success = reply.getData().getOpenedSuccessfully();
```

---

## 📌 Important Notes

**Broker Compatibility:**
- Not all brokers provide Market Depth data
- Some brokers only support DOM for specific instruments (e.g., futures, stocks)
- Forex brokers often don't provide true Level II data
- Check with your broker about DOM availability

**Typical Workflow:**
1. Call `marketBookAdd(symbol)` to subscribe
2. Check `opened_successfully` flag
3. Use `marketBookGet(symbol)` to retrieve order book data
4. Call `marketBookRelease(symbol)` when done to free resources

**Resource Management:**
- Each subscription uses terminal/server resources
- Always call `marketBookRelease()` when done
- Avoid subscribing to unnecessary symbols
- Consider using a subscription manager for cleanup
