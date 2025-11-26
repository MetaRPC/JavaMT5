# ✅ Check if Symbol Data is Synchronized

> **Request:** verify if symbol data in terminal is synchronized with trade server. Ensures you're working with up-to-date market information.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolIsSynchronized(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolIsSynchronized` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolIsSynchronized(SymbolIsSynchronizedRequest) → SymbolIsSynchronizedReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolIsSynchronized(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Checks if symbol data in the terminal is synchronized with the trade server.
     * Returns true if data is synchronized and up-to-date, false otherwise.
     *
     * @param symbol Symbol name to check (e.g., "EURUSD")
     * @return Synchronization status
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolIsSynchronizedReply symbolIsSynchronized(String symbol) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolIsSynchronizedRequest { symbol: string }`
**Reply message:** `SymbolIsSynchronizedReply { data: SymbolIsSynchronizedData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")   |

---

## ⬆️ Output — `SymbolIsSynchronizedData`

| Field          | Type      | Description                                                  |
| -------------- | --------- | ------------------------------------------------------------ |
| `synchronized` | `boolean` | `true` if data is synchronized, `false` if outdated          |

Access using `reply.getData().getSynchronized()`.

---

## 💬 Just the essentials

* **What it is.** RPC to check if terminal has up-to-date symbol data from server.
* **Why you need it.** Ensure prices and symbol properties are current before trading.
* **When false.** Data may be outdated due to connection issues or terminal startup.
* **Performance.** Quick validation call before critical trading operations.

---

## 🎯 Purpose

Use this method when you need to:

* Verify data freshness before placing orders.
* Check if terminal finished loading symbol data after connection.
* Diagnose why quotes appear stale or frozen.
* Validate data quality for trading algorithms.
* Ensure accurate price data for backtesting or analysis.

---

## 🧩 Notes & Tips

* Symbol must be selected in MarketWatch for this check.
* Returns `false` during terminal startup while data is loading.
* Check this after connection before getting critical market data.
* The method uses automatic reconnection via `executeWithReconnect()`.
* If `false`, wait a moment and retry - synchronization may be in progress.

---

## 🔗 Usage Examples

### 1) Basic synchronization check

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Check synchronization
            Mt5TermApiMarketInfo.SymbolIsSynchronizedReply reply =
                account.symbolIsSynchronized("EURUSD");
            boolean synced = reply.getData().getSynchronized();

            if (synced) {
                System.out.println("✅ EURUSD data is synchronized");
            } else {
                System.out.println("⚠️ EURUSD data is NOT synchronized");
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Wait for synchronization

```java
public class SyncWaiter {
    /**
     * Wait for symbol to synchronize (with timeout)
     */
    public static boolean waitForSync(
            MT5Account account,
            String symbol,
            int timeoutSeconds) throws ApiExceptionMT5, InterruptedException {

        System.out.printf("Waiting for %s to synchronize...%n", symbol);

        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            var reply = account.symbolIsSynchronized(symbol);

            if (reply.getData().getSynchronized()) {
                System.out.println("✅ Synchronized");
                return true;
            }

            System.out.print(".");
            Thread.sleep(500); // Wait 500ms before retry
        }

        System.out.println("\n⚠️ Timeout - not synchronized");
        return false;
    }
}

// Usage
if (SyncWaiter.waitForSync(account, "EURUSD", 10)) {
    // Safe to proceed
    var tick = account.quote("EURUSD");
    System.out.printf("Bid: %.5f%n", tick.getData().getBid());
}
```

### 3) Pre-trade validation

```java
public class TradeValidator {
    /**
     * Validate symbol before trading
     */
    public static boolean validateSymbol(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        System.out.printf("Validating %s...%n", symbol);

        // Check existence
        var existReply = account.symbolExist(symbol);
        if (!existReply.getData().getExists()) {
            System.out.println("❌ Symbol does not exist");
            return false;
        }
        System.out.println("✅ Symbol exists");

        // Select symbol
        var selectReply = account.symbolSelect(symbol, true);
        if (!selectReply.getData().getSuccess()) {
            System.out.println("❌ Failed to select symbol");
            return false;
        }
        System.out.println("✅ Symbol selected");

        // Check synchronization
        var syncReply = account.symbolIsSynchronized(symbol);
        if (!syncReply.getData().getSynchronized()) {
            System.out.println("⚠️ Symbol not synchronized - data may be outdated");
            return false;
        }
        System.out.println("✅ Symbol synchronized");

        return true;
    }
}

// Usage
if (TradeValidator.validateSymbol(account, "GBPUSD")) {
    System.out.println("Ready to trade GBPUSD");
    // Place order
}
```

### 4) Check multiple symbols

```java
public class MultiSymbolSync {
    public record SyncStatus(
        String symbol,
        boolean synchronized,
        String message
    ) {}

    /**
     * Check sync status for multiple symbols
     */
    public static java.util.List<SyncStatus> checkMultiple(
            MT5Account account,
            String... symbols) {

        java.util.List<SyncStatus> results = new java.util.ArrayList<>();

        for (String symbol : symbols) {
            try {
                var reply = account.symbolIsSynchronized(symbol);
                boolean synced = reply.getData().getSynchronized();

                results.add(new SyncStatus(
                    symbol,
                    synced,
                    synced ? "Synchronized" : "Not synchronized"
                ));

            } catch (ApiExceptionMT5 e) {
                results.add(new SyncStatus(
                    symbol,
                    false,
                    "Error: " + e.getMessage()
                ));
            }
        }

        return results;
    }

    /**
     * Print sync status report
     */
    public static void printReport(java.util.List<SyncStatus> results) {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║   SYMBOL SYNCHRONIZATION STATUS     ║");
        System.out.println("╠═════════════════════════════════════╣");

        for (var status : results) {
            String icon = status.synchronized() ? "✅" : "⚠️";
            System.out.printf("║ %s %-10s %-20s ║%n",
                icon,
                status.symbol(),
                status.message()
            );
        }

        long syncedCount = results.stream()
            .filter(SyncStatus::synchronized)
            .count();

        System.out.println("╠═════════════════════════════════════╣");
        System.out.printf("║ Synchronized: %d/%d                 ║%n",
            syncedCount, results.size());
        System.out.println("╚═════════════════════════════════════╝");
    }
}

// Usage
var statuses = MultiSymbolSync.checkMultiple(account,
    "EURUSD", "GBPUSD", "USDJPY", "XAUUSD"
);
MultiSymbolSync.printReport(statuses);
```

### 5) Retry with exponential backoff

```java
public class SyncRetry {
    /**
     * Wait for sync with exponential backoff
     */
    public static boolean waitWithBackoff(
            MT5Account account,
            String symbol,
            int maxRetries) throws ApiExceptionMT5 {

        int retryDelay = 100; // Start with 100ms

        for (int retry = 0; retry < maxRetries; retry++) {
            var reply = account.symbolIsSynchronized(symbol);

            if (reply.getData().getSynchronized()) {
                System.out.printf("✅ %s synchronized (attempt %d/%d)%n",
                    symbol, retry + 1, maxRetries);
                return true;
            }

            System.out.printf("⏳ Attempt %d/%d - waiting %dms...%n",
                retry + 1, maxRetries, retryDelay);

            try {
                Thread.sleep(retryDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            // Exponential backoff: 100ms, 200ms, 400ms, 800ms, ...
            retryDelay = Math.min(retryDelay * 2, 5000); // Cap at 5 seconds
        }

        System.out.printf("❌ %s failed to synchronize after %d attempts%n",
            symbol, maxRetries);
        return false;
    }
}

// Usage
if (SyncRetry.waitWithBackoff(account, "EURUSD", 5)) {
    // Proceed with trading
}
```

### 6) Connection health check

```java
public class ConnectionHealth {
    /**
     * Check overall connection health via symbol sync
     */
    public static boolean isConnectionHealthy(
            MT5Account account,
            String... testSymbols) throws ApiExceptionMT5 {

        if (testSymbols.length == 0) {
            testSymbols = new String[]{"EURUSD"};
        }

        int syncedCount = 0;

        for (String symbol : testSymbols) {
            var reply = account.symbolIsSynchronized(symbol);
            if (reply.getData().getSynchronized()) {
                syncedCount++;
            }
        }

        double syncRate = (double) syncedCount / testSymbols.length;
        boolean healthy = syncRate >= 0.8; // 80% threshold

        System.out.printf("Connection health: %.0f%% (%d/%d symbols synced)%n",
            syncRate * 100, syncedCount, testSymbols.length);

        if (!healthy) {
            System.out.println("⚠️ Connection may be unstable");
        }

        return healthy;
    }
}

// Usage - check major pairs
boolean healthy = ConnectionHealth.isConnectionHealthy(account,
    "EURUSD", "GBPUSD", "USDJPY", "USDCHF"
);
```

### 7) Symbol readiness validator

```java
public class SymbolReadiness {
    public enum Status {
        READY,
        NOT_SELECTED,
        NOT_SYNCHRONIZED,
        ERROR
    }

    public record ReadinessResult(
        String symbol,
        Status status,
        String message
    ) {
        public boolean isReady() {
            return status == Status.READY;
        }
    }

    /**
     * Check if symbol is ready for trading
     */
    public static ReadinessResult checkReadiness(
            MT5Account account,
            String symbol) {

        try {
            // Check if selected
            var intReply = account.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SELECT
            );

            if (intReply.getData().getValue() == 0) {
                return new ReadinessResult(
                    symbol,
                    Status.NOT_SELECTED,
                    "Symbol not in MarketWatch"
                );
            }

            // Check synchronization
            var syncReply = account.symbolIsSynchronized(symbol);

            if (!syncReply.getData().getSynchronized()) {
                return new ReadinessResult(
                    symbol,
                    Status.NOT_SYNCHRONIZED,
                    "Symbol data not synchronized"
                );
            }

            return new ReadinessResult(
                symbol,
                Status.READY,
                "Symbol ready for trading"
            );

        } catch (ApiExceptionMT5 e) {
            return new ReadinessResult(
                symbol,
                Status.ERROR,
                "Error: " + e.getMessage()
            );
        }
    }

    /**
     * Ensure symbol is ready (auto-fix if possible)
     */
    public static boolean ensureReady(
            MT5Account account,
            String symbol) throws ApiExceptionMT5, InterruptedException {

        var readiness = checkReadiness(account, symbol);

        switch (readiness.status()) {
            case READY:
                System.out.printf("✅ %s is ready%n", symbol);
                return true;

            case NOT_SELECTED:
                System.out.printf("⚙️ Selecting %s...%n", symbol);
                account.symbolSelect(symbol, true);
                Thread.sleep(500);
                return ensureReady(account, symbol); // Retry

            case NOT_SYNCHRONIZED:
                System.out.printf("⏳ Waiting for %s to synchronize...%n", symbol);
                return SyncWaiter.waitForSync(account, symbol, 10);

            case ERROR:
                System.out.printf("❌ %s: %s%n", symbol, readiness.message());
                return false;

            default:
                return false;
        }
    }
}

// Usage
if (SymbolReadiness.ensureReady(account, "XAUUSD")) {
    System.out.println("Ready to trade gold");
}
```

### 8) Data quality monitor

```java
public class DataQualityMonitor {
    /**
     * Monitor data quality in real-time
     */
    public static void monitor(
            MT5Account account,
            String symbol,
            int durationSeconds) throws InterruptedException {

        System.out.printf("Monitoring %s data quality for %d seconds...%n",
            symbol, durationSeconds);
        System.out.println("═".repeat(50));

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (durationSeconds * 1000L);

        int checkCount = 0;
        int syncedCount = 0;

        while (System.currentTimeMillis() < endTime) {
            try {
                var reply = account.symbolIsSynchronized(symbol);
                boolean synced = reply.getData().getSynchronized();

                checkCount++;
                if (synced) syncedCount++;

                String status = synced ? "✅ SYNCED" : "⚠️ NOT SYNCED";
                System.out.printf("[%s] Check %d: %s%n",
                    java.time.LocalTime.now(),
                    checkCount,
                    status
                );

                Thread.sleep(1000); // Check every second

            } catch (ApiExceptionMT5 e) {
                System.out.printf("❌ Error: %s%n", e.getMessage());
            }
        }

        double reliability = (syncedCount * 100.0) / checkCount;
        System.out.println("═".repeat(50));
        System.out.printf("Reliability: %.1f%% (%d/%d checks synced)%n",
            reliability, syncedCount, checkCount);
    }
}

// Usage
DataQualityMonitor.monitor(account, "EURUSD", 30);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest request =
    Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest.newBuilder()
        .setSymbol("EURUSD")
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolIsSynchronizedReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolIsSynchronized(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
boolean synchronized = reply.getData().getSynchronized();
```
