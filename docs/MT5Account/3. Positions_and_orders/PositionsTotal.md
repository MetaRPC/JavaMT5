# ✅ Getting Total Number of Open Positions

> **Request:** retrieve the count of currently open positions on the account. Returns a simple integer count without position details.

**API Information:**

* **SDK wrapper:** `MT5Account.positionsTotal()` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.TradeFunctions`
* **Proto definition:** `PositionsTotal` (defined in `mt5-term-api-trade-functions.proto`)

### RPC

* **Service:** `mt5_term_api.TradeFunctions`
* **Method:** `PositionsTotal(Empty) → PositionsTotalReply`
* **Low‑level client (generated):** `TradeFunctionsGrpc.TradeFunctionsBlockingStub.positionsTotal(Empty)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets the total count of currently open positions on the account.
     * Returns a simple count of all active positions regardless of symbol.
     * Use this for quick checks of position count before retrieving detailed position information.
     *
     * @return Total number of open positions
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.PositionsTotalReply positionsTotal() throws ApiExceptionMT5;
}
```

**Request message:** `Empty {}` (no parameters)

**Reply message:** `PositionsTotalReply { data: PositionsTotalData }` or `{ error: Error }`

---

## 🔽 Input

No parameters required. The method automatically uses connection metadata (instance ID).

---

## ⬆️ Output - `PositionsTotalData`

| Field   | Type  | Description                              |
| ------- | ----- | ---------------------------------------- |
| `total` | `int` | Total number of currently open positions |

Access the count using `reply.getData().getTotal()`.

---

## 💬 Just the essentials

* **What it is.** Simple RPC returning the count of open positions.
* **Why you need it.** Quick check before fetching full position details, monitoring position count limits.
* **Performance.** Very lightweight - just returns a count, no position data.
* **Use case.** Often used with `openedOrders()` to iterate through positions.

---

## 🎯 Purpose

Use this method when you need to:

* Check if there are any open positions before querying details.
* Monitor position count for risk management.
* Verify positions were closed successfully.
* Check against broker position limits.
* Display position count in UI dashboards.

---

## 🧩 Notes & Tips

* Returns only open positions, not pending orders (use `openedOrders()` for both).
* Count includes positions on all symbols.
* The method uses automatic reconnection via `executeWithReconnect()`.
* If count is 0, calling `openedOrders()` will return empty list.
* Does not count historical/closed positions.

---

## 🔗 Usage Examples

### 1) Basic position count

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiTradeFunctions;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get position count
            Mt5TermApiTradeFunctions.PositionsTotalReply reply = account.positionsTotal();
            int total = reply.getData().getTotal();

            System.out.printf("Open positions: %d%n", total);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Check if any positions exist

```java
// Quick check before querying details
var reply = account.positionsTotal();
int count = reply.getData().getTotal();

if (count == 0) {
    System.out.println("No open positions");
} else {
    System.out.printf("Found %d open positions%n", count);
    // Now fetch details
    var ordersReply = account.openedOrders(
        Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC
    );
    // Process positions...
}
```

### 3) Wait for all positions to close

```java
public class PositionWaiter {
    /**
     * Wait for all positions to close (with timeout)
     */
    public static boolean waitForAllClosed(
            MT5Account account,
            int timeoutSeconds) throws InterruptedException {

        System.out.println("Waiting for all positions to close...");

        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            try {
                var reply = account.positionsTotal();
                int count = reply.getData().getTotal();

                if (count == 0) {
                    System.out.println("✅ All positions closed");
                    return true;
                }

                System.out.printf("⏳ Waiting... (%d positions still open)%n", count);
                Thread.sleep(1000);

            } catch (ApiExceptionMT5 e) {
                System.err.println("Error: " + e.getMessage());
                Thread.sleep(1000);
            }
        }

        System.out.println("⚠️ Timeout - positions still open");
        return false;
    }
}

// Usage
if (PositionWaiter.waitForAllClosed(account, 30)) {
    System.out.println("Safe to disconnect");
}
```

### 4) Monitor position count

```java
public class PositionMonitor {
    /**
     * Monitor position count in real-time
     */
    public static void monitor(MT5Account account, int durationSeconds)
            throws InterruptedException {

        System.out.printf("Monitoring positions for %d seconds...%n", durationSeconds);
        System.out.println("═".repeat(50));

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (durationSeconds * 1000L);

        int lastCount = -1;

        while (System.currentTimeMillis() < endTime) {
            try {
                var reply = account.positionsTotal();
                int count = reply.getData().getTotal();

                if (count != lastCount) {
                    System.out.printf("[%s] Position count: %d%n",
                        java.time.LocalTime.now(),
                        count);
                    lastCount = count;
                }

                Thread.sleep(1000);

            } catch (ApiExceptionMT5 e) {
                System.err.println("Error: " + e.getMessage());
                Thread.sleep(1000);
            }
        }

        System.out.println("═".repeat(50));
        System.out.printf("Final count: %d%n", lastCount);
    }
}

// Usage
PositionMonitor.monitor(account, 60);
```

### 5) Check position limit

```java
public class PositionLimitChecker {
    /**
     * Check if opening new position would exceed limit
     */
    public static boolean canOpenPosition(
            MT5Account account,
            int maxPositions) throws ApiExceptionMT5 {

        var reply = account.positionsTotal();
        int current = reply.getData().getTotal();

        boolean allowed = current < maxPositions;

        System.out.printf("Positions: %d/%d%n", current, maxPositions);

        if (!allowed) {
            System.out.println("⚠️ Position limit reached");
        } else {
            System.out.printf("✅ Can open %d more positions%n", maxPositions - current);
        }

        return allowed;
    }
}

// Usage - check against broker limit or personal limit
if (PositionLimitChecker.canOpenPosition(account, 10)) {
    // Safe to open new position
    System.out.println("Opening new position...");
}
```

### 6) Dashboard status display

```java
public class TradingDashboard {
    public static void displayStatus(MT5Account account) {
        try {
            // Get position count
            var posReply = account.positionsTotal();
            int positions = posReply.getData().getTotal();

            // Get account summary
            var accReply = account.accountSummary();
            var accData = accReply.getData();

            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║       TRADING DASHBOARD               ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.printf("║ Account:       %-22d ║%n", accData.getAccountLogin());
            System.out.printf("║ Balance:       %-15.2f %s    ║%n",
                accData.getAccountBalance(),
                accData.getAccountCurrency());
            System.out.printf("║ Equity:        %-15.2f %s    ║%n",
                accData.getAccountEquity(),
                accData.getAccountCurrency());
            System.out.printf("║ Open Positions: %-21d ║%n", positions);

            String status = positions == 0 ? "No active trades" :
                           positions == 1 ? "1 active trade" :
                           positions + " active trades";
            System.out.printf("║ Status:        %-22s ║%n", status);
            System.out.println("╚═══════════════════════════════════════╝");

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

// Usage
TradingDashboard.displayStatus(account);
```

### 7) Compare position count over time

```java
public class PositionTracker {
    public record PositionSnapshot(
        java.time.Instant timestamp,
        int count
    ) {}

    private final java.util.List<PositionSnapshot> snapshots = new java.util.ArrayList<>();

    /**
     * Take a snapshot of current position count
     */
    public void takeSnapshot(MT5Account account) throws ApiExceptionMT5 {
        var reply = account.positionsTotal();
        int count = reply.getData().getTotal();

        snapshots.add(new PositionSnapshot(
            java.time.Instant.now(),
            count
        ));
    }

    /**
     * Print position history
     */
    public void printHistory() {
        System.out.println("\n=== Position Count History ===");
        for (var snapshot : snapshots) {
            System.out.printf("[%s] %d positions%n",
                snapshot.timestamp(),
                snapshot.count());
        }

        if (snapshots.size() >= 2) {
            int first = snapshots.get(0).count();
            int last = snapshots.get(snapshots.size() - 1).count();
            int change = last - first;

            System.out.printf("%nChange: %+d positions%n", change);
        }
    }
}

// Usage
var tracker = new PositionTracker();

for (int i = 0; i < 5; i++) {
    tracker.takeSnapshot(account);
    Thread.sleep(5000);
}

tracker.printHistory();
```

### 8) Position count alert

```java
public class PositionAlertSystem {
    /**
     * Alert when position count reaches threshold
     */
    public static void monitorWithAlert(
            MT5Account account,
            int warningThreshold,
            int durationSeconds) throws InterruptedException {

        System.out.printf("Monitoring positions (alert at %d)...%n", warningThreshold);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (durationSeconds * 1000L);

        boolean alerted = false;

        while (System.currentTimeMillis() < endTime) {
            try {
                var reply = account.positionsTotal();
                int count = reply.getData().getTotal();

                if (count >= warningThreshold && !alerted) {
                    System.out.printf("🚨 ALERT: %d positions (threshold: %d)%n",
                        count, warningThreshold);
                    alerted = true;
                } else if (count < warningThreshold && alerted) {
                    System.out.printf("✅ Position count back to normal: %d%n", count);
                    alerted = false;
                }

                Thread.sleep(2000);

            } catch (ApiExceptionMT5 e) {
                System.err.println("Error: " + e.getMessage());
                Thread.sleep(2000);
            }
        }
    }
}

// Usage - alert if 5 or more positions open
PositionAlertSystem.monitorWithAlert(account, 5, 60);
```

### 9) Pre-trade validation

```java
public class TradeValidator {
    /**
     * Validate trading conditions before opening position
     */
    public static boolean validateBeforeTrade(
            MT5Account account,
            int maxPositions,
            double maxMarginUsage) throws ApiExceptionMT5 {

        System.out.println("Validating trading conditions...");

        // Check position count
        var posReply = account.positionsTotal();
        int positions = posReply.getData().getTotal();

        if (positions >= maxPositions) {
            System.out.printf("❌ Too many positions: %d/%d%n", positions, maxPositions);
            return false;
        }
        System.out.printf("✅ Position count OK: %d/%d%n", positions, maxPositions);

        // Check margin level
        var marginReply = account.accountInfoDouble(
            Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN_LEVEL
        );
        double marginLevel = marginReply.getData().getRequestedValue();

        if (marginLevel < maxMarginUsage) {
            System.out.printf("❌ Insufficient margin: %.2f%% < %.2f%%%n",
                marginLevel, maxMarginUsage);
            return false;
        }
        System.out.printf("✅ Margin level OK: %.2f%%%n", marginLevel);

        System.out.println("✅ All checks passed - ready to trade");
        return true;
    }
}

// Usage
if (TradeValidator.validateBeforeTrade(account, 5, 200.0)) {
    // Place order
    System.out.println("Placing order...");
}
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;
import com.google.protobuf.Empty;

// Create request (empty)
Empty request = Empty.newBuilder().build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiTradeFunctions.PositionsTotalReply reply = tradeFunctionsClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .positionsTotal(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
int total = reply.getData().getTotal();
```
