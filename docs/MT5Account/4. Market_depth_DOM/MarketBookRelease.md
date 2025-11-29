# 🔓 Unsubscribe from Market Depth (DOM)

> **Request:** unsubscribe from Market Depth (DOM / Level II) updates for a symbol. Stops receiving order book data and releases associated resources.

**API Information:**

* **SDK wrapper:** `MT5Account.marketBookRelease(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `MarketBookRelease` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `MarketBookRelease(MarketBookReleaseRequest) → MarketBookReleaseReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.marketBookRelease(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Unsubscribes from Market Depth updates for a specified symbol.
     * Stops receiving order book data and releases associated resources.
     * Use this when you no longer need DOM data for a symbol to free up resources.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @return Unsubscription confirmation response
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.MarketBookReleaseReply marketBookRelease(String symbol) throws ApiExceptionMT5;
}
```

**Request message:** `MarketBookReleaseRequest { symbol: string }`

**Reply message:** `MarketBookReleaseReply { data: MarketBookReleaseData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")   |

---

## ⬆️ Output - `MarketBookReleaseData`

| Field                   | Type      | Description                                                  |
| ----------------------- | --------- | ------------------------------------------------------------ |
| `closed_successfully`   | `boolean` | `true` if unsubscription successful, `false` otherwise       |

Access using `reply.getData().getClosedSuccessfully()`.

---

## 💬 Just the essentials

* **What it is.** RPC to unsubscribe from Market Depth (order book) updates.
* **Why you need it.** Free up resources when you no longer need DOM data.
* **Resource cleanup.** Always call this when done with Market Depth monitoring.
* **Idempotent.** Safe to call even if not subscribed (will return success).
* **Best practice.** Use in `finally` blocks or cleanup code to ensure resource release.
* **Performance.** Reduces memory/bandwidth usage by stopping unused subscriptions.

---

## 🎯 Purpose

Use this method when you need to:

* Stop receiving Market Depth updates for a symbol.
* Free up terminal/server resources after analysis is complete.
* Clean up subscriptions before disconnecting.
* Implement proper resource management in trading applications.
* Release unused subscriptions to avoid resource leaks.

---

## 🧩 Notes & Tips

* **Always cleanup.** Call this method when done with DOM data to free resources.
* **Idempotent.** Safe to call multiple times or on unsubscribed symbols.
* **Use finally.** Place in `finally` blocks to ensure cleanup even on errors.
* **Success flag.** Check `closed_successfully` to verify release worked.
* **No side effects.** Won't affect other subscriptions or market data.
* **Auto-reconnect.** Uses `executeWithReconnect()` for reliability.
* **Best practice.** Always pair `marketBookAdd()` with `marketBookRelease()`.

---

## 🔗 Usage Examples

### 1) Basic unsubscription

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
            account.marketBookAdd("EURUSD");

            // Use Market Depth data...
            var bookData = account.marketBookGet("EURUSD");
            System.out.printf("Order book entries: %d%n",
                bookData.getData().getMqlBookInfosList().size());

            // Unsubscribe when done
            Mt5TermApiMarketInfo.MarketBookReleaseReply reply =
                account.marketBookRelease("EURUSD");

            if (reply.getData().getClosedSuccessfully()) {
                System.out.println("✅ Successfully unsubscribed from EURUSD Market Depth");
            } else {
                System.out.println("⚠️ Failed to unsubscribe");
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Proper resource cleanup with try-finally

```java
public class SafeCleanup {
    /**
     * Use Market Depth with guaranteed cleanup
     */
    public static void useMarketDepth(
            MT5Account account,
            String symbol) {

        boolean subscribed = false;

        try {
            // Subscribe
            var addReply = account.marketBookAdd(symbol);
            subscribed = addReply.getData().getOpenedSuccessfully();

            if (!subscribed) {
                System.out.printf("Failed to subscribe to %s%n", symbol);
                return;
            }

            System.out.printf("✅ Subscribed to %s Market Depth%n", symbol);

            // Use Market Depth data
            var bookReply = account.marketBookGet(symbol);
            System.out.printf("Order book entries: %d%n",
                bookReply.getData().getMqlBookInfosList().size());

            // Your analysis logic here...

        } catch (ApiExceptionMT5 e) {
            System.err.printf("Error: %s%n", e.getMessage());

        } finally {
            // Always cleanup
            if (subscribed) {
                try {
                    var releaseReply = account.marketBookRelease(symbol);
                    if (releaseReply.getData().getClosedSuccessfully()) {
                        System.out.printf("✅ Cleaned up %s subscription%n", symbol);
                    }
                } catch (ApiExceptionMT5 e) {
                    System.err.printf("Cleanup error: %s%n", e.getMessage());
                }
            }
        }
    }
}

// Usage
SafeCleanup.useMarketDepth(account, "EURUSD");
```

### 3) Unsubscribe from multiple symbols

```java
import java.util.*;

public class MultiSymbolCleanup {
    /**
     * Unsubscribe from multiple symbols
     */
    public static Map<String, Boolean> releaseMultiple(
            MT5Account account,
            String... symbols) {

        Map<String, Boolean> results = new LinkedHashMap<>();

        System.out.printf("Unsubscribing from %d symbols...%n", symbols.length);
        System.out.println("═".repeat(50));

        for (String symbol : symbols) {
            try {
                var reply = account.marketBookRelease(symbol);
                boolean success = reply.getData().getClosedSuccessfully();
                results.put(symbol, success);

                String status = success ? "✅ RELEASED" : "⚠️ FAILED";
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
        System.out.printf("Released: %d/%d subscriptions%n", successCount, symbols.length);

        return results;
    }
}

// Usage
MultiSymbolCleanup.releaseMultiple(account,
    "EURUSD", "GBPUSD", "USDJPY", "XAUUSD"
);
```

### 4) Auto-closing resource (try-with-resources pattern)

```java
public class MarketDepthResource implements AutoCloseable {
    private final MT5Account account;
    private final String symbol;
    private boolean subscribed = false;

    public MarketDepthResource(MT5Account account, String symbol) throws ApiExceptionMT5 {
        this.account = account;
        this.symbol = symbol;

        // Subscribe on creation
        var reply = account.marketBookAdd(symbol);
        this.subscribed = reply.getData().getOpenedSuccessfully();

        if (!subscribed) {
            throw new ApiExceptionMT5("Failed to subscribe to Market Depth for " + symbol);
        }

        System.out.printf("✅ Subscribed to %s Market Depth%n", symbol);
    }

    /**
     * Get current order book
     */
    public Mt5TermApiMarketInfo.MarketBookGetReply getOrderBook() throws ApiExceptionMT5 {
        if (!subscribed) {
            throw new IllegalStateException("Not subscribed to Market Depth");
        }
        return account.marketBookGet(symbol);
    }

    @Override
    public void close() {
        if (subscribed) {
            try {
                var reply = account.marketBookRelease(symbol);
                if (reply.getData().getClosedSuccessfully()) {
                    System.out.printf("✅ Released %s subscription%n", symbol);
                    subscribed = false;
                }
            } catch (ApiExceptionMT5 e) {
                System.err.printf("Failed to release %s: %s%n", symbol, e.getMessage());
            }
        }
    }
}

// Usage with try-with-resources (automatic cleanup!)
try (var marketDepth = new MarketDepthResource(account, "EURUSD")) {
    var book = marketDepth.getOrderBook();
    System.out.printf("Order book size: %d%n",
        book.getData().getMqlBookInfosList().size());

    // Analyze order book...

}  // Automatic unsubscription happens here
```

### 5) Subscription manager with tracking

```java
public class SubscriptionManager {
    private final MT5Account account;
    private final Set<String> activeSubscriptions = new HashSet<>();

    public SubscriptionManager(MT5Account account) {
        this.account = account;
    }

    /**
     * Subscribe with tracking
     */
    public boolean subscribe(String symbol) throws ApiExceptionMT5 {
        if (activeSubscriptions.contains(symbol)) {
            System.out.printf("Already subscribed to %s%n", symbol);
            return true;
        }

        var reply = account.marketBookAdd(symbol);
        boolean success = reply.getData().getOpenedSuccessfully();

        if (success) {
            activeSubscriptions.add(symbol);
            System.out.printf("✅ Subscribed to %s (%d active)%n",
                symbol, activeSubscriptions.size());
        }

        return success;
    }

    /**
     * Unsubscribe with tracking
     */
    public boolean unsubscribe(String symbol) throws ApiExceptionMT5 {
        if (!activeSubscriptions.contains(symbol)) {
            System.out.printf("Not subscribed to %s%n", symbol);
            return true;
        }

        var reply = account.marketBookRelease(symbol);
        boolean success = reply.getData().getClosedSuccessfully();

        if (success) {
            activeSubscriptions.remove(symbol);
            System.out.printf("✅ Unsubscribed from %s (%d remaining)%n",
                symbol, activeSubscriptions.size());
        }

        return success;
    }

    /**
     * Cleanup all subscriptions
     */
    public void cleanupAll() {
        if (activeSubscriptions.isEmpty()) {
            System.out.println("No active subscriptions to cleanup");
            return;
        }

        System.out.printf("Cleaning up %d subscriptions...%n",
            activeSubscriptions.size());

        var symbols = new ArrayList<>(activeSubscriptions);
        int successCount = 0;

        for (String symbol : symbols) {
            try {
                if (unsubscribe(symbol)) {
                    successCount++;
                }
            } catch (ApiExceptionMT5 e) {
                System.err.printf("Failed to release %s: %s%n",
                    symbol, e.getMessage());
            }
        }

        System.out.printf("✅ Cleaned up %d/%d subscriptions%n",
            successCount, symbols.size());
    }

    /**
     * Get active subscription count
     */
    public int getActiveCount() {
        return activeSubscriptions.size();
    }

    /**
     * Get active subscriptions
     */
    public Set<String> getActiveSubscriptions() {
        return new HashSet<>(activeSubscriptions);
    }

    /**
     * Print status
     */
    public void printStatus() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   MARKET DEPTH SUBSCRIPTIONS           ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Active: %-30d ║%n", activeSubscriptions.size());

        if (!activeSubscriptions.isEmpty()) {
            System.out.println("╠════════════════════════════════════════╣");
            for (String symbol : activeSubscriptions) {
                System.out.printf("║ ✅ %-36s ║%n", symbol);
            }
        }

        System.out.println("╚════════════════════════════════════════╝");
    }
}

// Usage
var manager = new SubscriptionManager(account);

// Subscribe to symbols
manager.subscribe("EURUSD");
manager.subscribe("GBPUSD");
manager.subscribe("XAUUSD");

manager.printStatus();

// Use market depth data...

// Cleanup when done
manager.cleanupAll();
```

### 6) Cleanup on application shutdown

```java
public class ShutdownHookCleanup {
    /**
     * Register shutdown hook for cleanup
     */
    public static void registerCleanupHook(
            MT5Account account,
            Set<String> subscribedSymbols) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Shutdown detected - cleaning up subscriptions...");

            for (String symbol : subscribedSymbols) {
                try {
                    var reply = account.marketBookRelease(symbol);
                    if (reply.getData().getClosedSuccessfully()) {
                        System.out.printf("✅ Released %s%n", symbol);
                    }
                } catch (Exception e) {
                    System.err.printf("Failed to release %s: %s%n",
                        symbol, e.getMessage());
                }
            }

            System.out.println("Cleanup complete");
        }));
    }
}

// Usage
Set<String> subscriptions = new HashSet<>();

// Register cleanup hook
ShutdownHookCleanup.registerCleanupHook(account, subscriptions);

// Subscribe to symbols
account.marketBookAdd("EURUSD");
subscriptions.add("EURUSD");

account.marketBookAdd("GBPUSD");
subscriptions.add("GBPUSD");

// Application runs...
// On shutdown (Ctrl+C or exit), cleanup happens automatically
```

### 7) Retry release with exponential backoff

```java
public class RetryRelease {
    /**
     * Release subscription with retry logic
     */
    public static boolean releaseWithRetry(
            MT5Account account,
            String symbol,
            int maxRetries) {

        System.out.printf("Releasing %s (max retries: %d)...%n", symbol, maxRetries);

        int retryDelay = 100;  // Start with 100ms

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                var reply = account.marketBookRelease(symbol);

                if (reply.getData().getClosedSuccessfully()) {
                    System.out.printf("✅ Released successfully (attempt %d/%d)%n",
                        attempt, maxRetries);
                    return true;
                }

                System.out.printf("⚠️ Release failed (attempt %d/%d)%n",
                    attempt, maxRetries);

            } catch (ApiExceptionMT5 e) {
                System.out.printf("❌ Error on attempt %d/%d: %s%n",
                    attempt, maxRetries, e.getMessage());
            }

            if (attempt < maxRetries) {
                try {
                    System.out.printf("⏳ Waiting %dms before retry...%n", retryDelay);
                    Thread.sleep(retryDelay);
                    retryDelay = Math.min(retryDelay * 2, 5000);  // Cap at 5s
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }

        System.out.printf("❌ Failed to release after %d attempts%n", maxRetries);
        return false;
    }
}

// Usage
RetryRelease.releaseWithRetry(account, "EURUSD", 3);
```

### 8) Batch cleanup with progress

```java
public class BatchCleanup {
    /**
     * Cleanup multiple subscriptions with progress tracking
     */
    public static void cleanupWithProgress(
            MT5Account account,
            Collection<String> symbols) {

        if (symbols.isEmpty()) {
            System.out.println("No subscriptions to cleanup");
            return;
        }

        int total = symbols.size();
        int completed = 0;
        int successful = 0;

        System.out.printf("Cleaning up %d subscriptions...%n", total);
        System.out.println("═".repeat(50));

        for (String symbol : symbols) {
            completed++;
            int progress = (completed * 100) / total;

            System.out.printf("[%3d%%] Releasing %s... ", progress, symbol);

            try {
                var reply = account.marketBookRelease(symbol);
                if (reply.getData().getClosedSuccessfully()) {
                    successful++;
                    System.out.println("✅ OK");
                } else {
                    System.out.println("⚠️ FAILED");
                }

            } catch (ApiExceptionMT5 e) {
                System.out.printf("❌ ERROR: %s%n", e.getMessage());
            }

            // Small delay between releases
            if (completed < total) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        System.out.println("═".repeat(50));
        System.out.printf("✅ Successfully released: %d/%d%n", successful, total);

        if (successful < total) {
            System.out.printf("⚠️ Failed to release: %d%n", total - successful);
        }
    }
}

// Usage
Set<String> subscriptions = Set.of(
    "EURUSD", "GBPUSD", "USDJPY", "AUDUSD",
    "USDCHF", "NZDUSD", "USDCAD"
);

BatchCleanup.cleanupWithProgress(account, subscriptions);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.MarketBookReleaseRequest request =
    Mt5TermApiMarketInfo.MarketBookReleaseRequest.newBuilder()
        .setSymbol("EURUSD")
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.MarketBookReleaseReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .marketBookRelease(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Check success
boolean success = reply.getData().getClosedSuccessfully();
```

---

## 📌 Best Practices

**Resource Management:**
1. Always call `marketBookRelease()` after `marketBookAdd()`
2. Use try-finally blocks to ensure cleanup
3. Consider try-with-resources pattern for automatic cleanup
4. Register shutdown hooks for application-level cleanup

**Error Handling:**
- Check `closed_successfully` flag to verify release
- Handle exceptions gracefully (release may fail after disconnect)
- Safe to call even if not subscribed (idempotent)
- Don't throw exceptions in cleanup code

**Performance:**
- Release unused subscriptions promptly
- Avoid accumulating subscriptions over time
- Track active subscriptions to prevent leaks
- Clean up before application exit

**Typical Lifecycle:**
```java
// 1. Subscribe
account.marketBookAdd("EURUSD");

// 2. Use data (multiple times)
for (int i = 0; i < 10; i++) {
    var book = account.marketBookGet("EURUSD");
    // Analyze...
    Thread.sleep(1000);
}

// 3. Release (in finally)
account.marketBookRelease("EURUSD");
```

**Common Patterns:**
- **Scoped usage:** Subscribe → Use → Release immediately
- **Long-lived:** Subscribe at startup → Use throughout session → Release at shutdown
- **On-demand:** Subscribe when needed → Use briefly → Release after use
