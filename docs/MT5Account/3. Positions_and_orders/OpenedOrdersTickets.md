# 🎫 Get Ticket Numbers for Open Positions and Orders

> **Request:** retrieve only the ticket IDs of all open positions and pending orders. Lightweight alternative to `openedOrders()` when you only need to track which positions exist.

**API Information:**

* **SDK wrapper:** `MT5Account.openedOrdersTickets()` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountHelper`
* **Proto definition:** `OpenedOrdersTickets` (defined in `mt5-term-api-account-helper.proto`)

### RPC

* **Service:** `mt5_term_api.AccountHelper`
* **Method:** `OpenedOrdersTickets(OpenedOrdersTicketsRequest) → OpenedOrdersTicketsReply`
* **Low‑level client (generated):** `AccountHelperGrpc.AccountHelperBlockingStub.openedOrdersTickets(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets only the ticket numbers of all open positions and pending orders.
     * This lightweight method returns just the ticket IDs without full position details.
     * Use this when you only need to track which positions exist or for efficient position monitoring.
     *
     * @return Reply containing lists of position and order ticket IDs
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.OpenedOrdersTicketsReply openedOrdersTickets() throws ApiExceptionMT5;
}
```

**Request message:** `OpenedOrdersTicketsRequest { }` (empty - no parameters)
**Reply message:** `OpenedOrdersTicketsReply { data: OpenedOrdersTicketsData }` or `{ error: Error }`

---

## 🔽 Input

**No parameters required.** This method takes no input parameters.

---

## ⬆️ Output — `OpenedOrdersTicketsData`

| Field                      | Type          | Description                                          |
| -------------------------- | ------------- | ---------------------------------------------------- |
| `opened_orders_tickets`    | `List<Long>`  | List of ticket IDs for pending orders                |
| `opened_position_tickets`  | `List<Long>`  | List of ticket IDs for open positions                |

Access using:
- `reply.getData().getOpenedOrdersTicketsList()` - List of order tickets
- `reply.getData().getOpenedPositionTicketsList()` - List of position tickets

---

## 💬 Just the essentials

* **What it is.** Lightweight RPC that returns only ticket numbers for open positions and pending orders.
* **Why you need it.** Efficient monitoring without transferring full position details.
* **Use case.** Track which positions exist, detect new/closed positions, or monitor position count.
* **Performance.** Much faster than `openedOrders()` when you don't need full details.
* **When to upgrade.** Call `openedOrders()` when you need full position information.

---

## 🎯 Purpose

Use this method when you need to:

* Monitor which positions and orders are currently active.
* Track changes in position/order count efficiently.
* Detect newly opened or closed positions without full data.
* Build position monitoring systems with minimal bandwidth.
* Check if specific ticket IDs still exist.
* Implement polling-based position watchers.

---

## 🧩 Notes & Tips

* Returns two separate lists: one for positions, one for pending orders.
* Much lighter than `openedOrders()` - only transfers ticket numbers.
* Use this for frequent polling/monitoring scenarios.
* Ticket IDs are unique identifiers (int64/long type).
* Empty lists indicate no open positions or pending orders.
* For full position details, use `openedOrders()` instead.
* The method uses automatic reconnection via `executeWithReconnect()`.

---

## 🔗 Usage Examples

### 1) Basic ticket retrieval

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get ticket numbers
            Mt5TermApiAccountHelper.OpenedOrdersTicketsReply reply =
                account.openedOrdersTickets();

            var data = reply.getData();
            var positionTickets = data.getOpenedPositionTicketsList();
            var orderTickets = data.getOpenedOrdersTicketsList();

            System.out.printf("Open positions: %d%n", positionTickets.size());
            System.out.printf("Pending orders: %d%n", orderTickets.size());

            System.out.println("\nPosition tickets:");
            positionTickets.forEach(ticket ->
                System.out.printf("  #%d%n", ticket)
            );

            System.out.println("\nOrder tickets:");
            orderTickets.forEach(ticket ->
                System.out.printf("  #%d%n", ticket)
            );

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Position change detector

```java
import java.util.*;

public class PositionChangeDetector {
    private Set<Long> lastPositionTickets = new HashSet<>();
    private Set<Long> lastOrderTickets = new HashSet<>();

    /**
     * Detect changes in positions and orders
     */
    public ChangeReport detectChanges(MT5Account account) throws ApiExceptionMT5 {
        var reply = account.openedOrdersTickets();
        var data = reply.getData();

        // Current tickets
        Set<Long> currentPositions = new HashSet<>(
            data.getOpenedPositionTicketsList()
        );
        Set<Long> currentOrders = new HashSet<>(
            data.getOpenedOrdersTicketsList()
        );

        // Find new and closed positions
        Set<Long> newPositions = new HashSet<>(currentPositions);
        newPositions.removeAll(lastPositionTickets);

        Set<Long> closedPositions = new HashSet<>(lastPositionTickets);
        closedPositions.removeAll(currentPositions);

        // Find new and removed orders
        Set<Long> newOrders = new HashSet<>(currentOrders);
        newOrders.removeAll(lastOrderTickets);

        Set<Long> removedOrders = new HashSet<>(lastOrderTickets);
        removedOrders.removeAll(currentOrders);

        // Update last known state
        lastPositionTickets = currentPositions;
        lastOrderTickets = currentOrders;

        return new ChangeReport(
            newPositions,
            closedPositions,
            newOrders,
            removedOrders,
            currentPositions.size(),
            currentOrders.size()
        );
    }

    public record ChangeReport(
        Set<Long> newPositions,
        Set<Long> closedPositions,
        Set<Long> newOrders,
        Set<Long> removedOrders,
        int totalPositions,
        int totalOrders
    ) {
        public boolean hasChanges() {
            return !newPositions.isEmpty() ||
                   !closedPositions.isEmpty() ||
                   !newOrders.isEmpty() ||
                   !removedOrders.isEmpty();
        }

        public void print() {
            System.out.printf("[%s] Positions: %d, Orders: %d%n",
                java.time.LocalTime.now(),
                totalPositions,
                totalOrders
            );

            if (!newPositions.isEmpty()) {
                System.out.println("  ✅ New positions: " + newPositions);
            }
            if (!closedPositions.isEmpty()) {
                System.out.println("  ❌ Closed positions: " + closedPositions);
            }
            if (!newOrders.isEmpty()) {
                System.out.println("  📝 New orders: " + newOrders);
            }
            if (!removedOrders.isEmpty()) {
                System.out.println("  🗑️ Removed orders: " + removedOrders);
            }
        }
    }
}

// Usage - monitor changes every 5 seconds
var detector = new PositionChangeDetector();
while (true) {
    var changes = detector.detectChanges(account);
    if (changes.hasChanges()) {
        changes.print();
    }
    Thread.sleep(5000);
}
```

### 3) Check if specific ticket exists

```java
public class TicketChecker {
    /**
     * Check if a ticket ID is currently active
     */
    public static boolean isTicketActive(
            MT5Account account,
            long ticketId) throws ApiExceptionMT5 {

        var reply = account.openedOrdersTickets();
        var data = reply.getData();

        // Check in positions
        if (data.getOpenedPositionTicketsList().contains(ticketId)) {
            System.out.printf("✅ Ticket #%d is an active position%n", ticketId);
            return true;
        }

        // Check in orders
        if (data.getOpenedOrdersTicketsList().contains(ticketId)) {
            System.out.printf("✅ Ticket #%d is a pending order%n", ticketId);
            return true;
        }

        System.out.printf("❌ Ticket #%d not found%n", ticketId);
        return false;
    }

    /**
     * Get ticket type
     */
    public static String getTicketType(
            MT5Account account,
            long ticketId) throws ApiExceptionMT5 {

        var reply = account.openedOrdersTickets();
        var data = reply.getData();

        if (data.getOpenedPositionTicketsList().contains(ticketId)) {
            return "POSITION";
        }
        if (data.getOpenedOrdersTicketsList().contains(ticketId)) {
            return "ORDER";
        }
        return "NOT_FOUND";
    }
}

// Usage
long myTicket = 123456789;
if (TicketChecker.isTicketActive(account, myTicket)) {
    String type = TicketChecker.getTicketType(account, myTicket);
    System.out.printf("Ticket type: %s%n", type);
}
```

### 4) Wait for position closure

```java
public class PositionWaiter {
    /**
     * Wait for a specific position to close
     */
    public static boolean waitForClosure(
            MT5Account account,
            long positionTicket,
            int timeoutSeconds) throws ApiExceptionMT5, InterruptedException {

        System.out.printf("Waiting for position #%d to close...%n", positionTicket);

        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            var reply = account.openedOrdersTickets();
            var tickets = reply.getData().getOpenedPositionTicketsList();

            if (!tickets.contains(positionTicket)) {
                System.out.println("✅ Position closed");
                return true;
            }

            System.out.print(".");
            Thread.sleep(1000);
        }

        System.out.println("\n⚠️ Timeout - position still open");
        return false;
    }

    /**
     * Wait for all positions to close
     */
    public static boolean waitForAllClosure(
            MT5Account account,
            int timeoutSeconds) throws ApiExceptionMT5, InterruptedException {

        System.out.println("Waiting for all positions to close...");

        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            var reply = account.openedOrdersTickets();
            var data = reply.getData();

            int posCount = data.getOpenedPositionTicketsList().size();
            int ordCount = data.getOpenedOrdersTicketsList().size();

            if (posCount == 0 && ordCount == 0) {
                System.out.println("✅ All positions and orders closed");
                return true;
            }

            System.out.printf("[%s] Positions: %d, Orders: %d%n",
                java.time.LocalTime.now(), posCount, ordCount);

            Thread.sleep(2000);
        }

        System.out.println("⚠️ Timeout - not all positions closed");
        return false;
    }
}

// Usage
if (PositionWaiter.waitForClosure(account, 123456789, 30)) {
    System.out.println("Safe to proceed");
}
```

### 5) Simple position monitor with alerts

```java
public class SimpleMonitor {
    /**
     * Monitor positions with threshold alerts
     */
    public static void monitorWithAlerts(
            MT5Account account,
            int maxPositions,
            int maxOrders,
            int checkIntervalSeconds) throws InterruptedException {

        System.out.printf("Monitoring (max positions: %d, max orders: %d)%n",
            maxPositions, maxOrders);
        System.out.println("═".repeat(50));

        while (true) {
            try {
                var reply = account.openedOrdersTickets();
                var data = reply.getData();

                int posCount = data.getOpenedPositionTicketsList().size();
                int ordCount = data.getOpenedOrdersTicketsList().size();

                // Check thresholds
                boolean posAlert = posCount > maxPositions;
                boolean ordAlert = ordCount > maxOrders;

                String posIcon = posAlert ? "⚠️" : "✅";
                String ordIcon = ordAlert ? "⚠️" : "✅";

                System.out.printf("[%s] %s Positions: %d/%d | %s Orders: %d/%d%n",
                    java.time.LocalTime.now(),
                    posIcon, posCount, maxPositions,
                    ordIcon, ordCount, maxOrders
                );

                if (posAlert) {
                    System.out.printf("  🚨 ALERT: Position count exceeded! " +
                        "(%d > %d)%n", posCount, maxPositions);
                }
                if (ordAlert) {
                    System.out.printf("  🚨 ALERT: Order count exceeded! " +
                        "(%d > %d)%n", ordCount, maxOrders);
                }

            } catch (ApiExceptionMT5 e) {
                System.err.printf("❌ Error: %s%n", e.getMessage());
            }

            Thread.sleep(checkIntervalSeconds * 1000L);
        }
    }
}

// Usage
SimpleMonitor.monitorWithAlerts(account, 10, 5, 5);
```

### 6) Batch ticket validator

```java
import java.util.*;
import java.util.stream.*;

public class BatchTicketValidator {
    public record ValidationResult(
        long ticket,
        boolean exists,
        String type  // "POSITION", "ORDER", or "NOT_FOUND"
    ) {}

    /**
     * Validate multiple tickets at once
     */
    public static List<ValidationResult> validateTickets(
            MT5Account account,
            long... tickets) throws ApiExceptionMT5 {

        var reply = account.openedOrdersTickets();
        var data = reply.getData();

        Set<Long> positions = new HashSet<>(
            data.getOpenedPositionTicketsList()
        );
        Set<Long> orders = new HashSet<>(
            data.getOpenedOrdersTicketsList()
        );

        return Arrays.stream(tickets)
            .mapToObj(ticket -> {
                if (positions.contains(ticket)) {
                    return new ValidationResult(ticket, true, "POSITION");
                } else if (orders.contains(ticket)) {
                    return new ValidationResult(ticket, true, "ORDER");
                } else {
                    return new ValidationResult(ticket, false, "NOT_FOUND");
                }
            })
            .toList();
    }

    /**
     * Print validation report
     */
    public static void printReport(List<ValidationResult> results) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     TICKET VALIDATION REPORT       ║");
        System.out.println("╠════════════════════════════════════╣");

        for (var result : results) {
            String icon = result.exists() ? "✅" : "❌";
            System.out.printf("║ %s Ticket #%-12d %-10s ║%n",
                icon,
                result.ticket(),
                result.type()
            );
        }

        long existsCount = results.stream()
            .filter(ValidationResult::exists)
            .count();

        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Valid: %d/%d                        ║%n",
            existsCount, results.size());
        System.out.println("╚════════════════════════════════════╝");
    }
}

// Usage
long[] ticketsToCheck = {123456, 789012, 345678, 901234};
var results = BatchTicketValidator.validateTickets(account, ticketsToCheck);
BatchTicketValidator.printReport(results);
```

### 7) Efficient position polling system

```java
public class PositionPoller {
    private final MT5Account account;
    private final int pollIntervalMs;
    private volatile boolean running = false;

    public PositionPoller(MT5Account account, int pollIntervalMs) {
        this.account = account;
        this.pollIntervalMs = pollIntervalMs;
    }

    /**
     * Start polling in background thread
     */
    public Thread startPolling(PositionListener listener) {
        running = true;

        Thread thread = new Thread(() -> {
            Set<Long> previousPositions = new HashSet<>();
            Set<Long> previousOrders = new HashSet<>();

            while (running) {
                try {
                    var reply = account.openedOrdersTickets();
                    var data = reply.getData();

                    Set<Long> currentPositions = new HashSet<>(
                        data.getOpenedPositionTicketsList()
                    );
                    Set<Long> currentOrders = new HashSet<>(
                        data.getOpenedOrdersTicketsList()
                    );

                    // Detect new positions
                    for (long ticket : currentPositions) {
                        if (!previousPositions.contains(ticket)) {
                            listener.onPositionOpened(ticket);
                        }
                    }

                    // Detect closed positions
                    for (long ticket : previousPositions) {
                        if (!currentPositions.contains(ticket)) {
                            listener.onPositionClosed(ticket);
                        }
                    }

                    // Detect new orders
                    for (long ticket : currentOrders) {
                        if (!previousOrders.contains(ticket)) {
                            listener.onOrderPlaced(ticket);
                        }
                    }

                    // Detect removed orders
                    for (long ticket : previousOrders) {
                        if (!currentOrders.contains(ticket)) {
                            listener.onOrderRemoved(ticket);
                        }
                    }

                    previousPositions = currentPositions;
                    previousOrders = currentOrders;

                    Thread.sleep(pollIntervalMs);

                } catch (ApiExceptionMT5 e) {
                    listener.onError(e);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        thread.start();
        return thread;
    }

    /**
     * Stop polling
     */
    public void stop() {
        running = false;
    }

    /**
     * Listener interface for position events
     */
    public interface PositionListener {
        void onPositionOpened(long ticket);
        void onPositionClosed(long ticket);
        void onOrderPlaced(long ticket);
        void onOrderRemoved(long ticket);
        void onError(ApiExceptionMT5 error);
    }
}

// Usage
var poller = new PositionPoller(account, 2000);  // Poll every 2 seconds

Thread pollerThread = poller.startPolling(new PositionPoller.PositionListener() {
    @Override
    public void onPositionOpened(long ticket) {
        System.out.printf("✅ Position opened: #%d%n", ticket);
    }

    @Override
    public void onPositionClosed(long ticket) {
        System.out.printf("❌ Position closed: #%d%n", ticket);
    }

    @Override
    public void onOrderPlaced(long ticket) {
        System.out.printf("📝 Order placed: #%d%n", ticket);
    }

    @Override
    public void onOrderRemoved(long ticket) {
        System.out.printf("🗑️ Order removed: #%d%n", ticket);
    }

    @Override
    public void onError(ApiExceptionMT5 error) {
        System.err.printf("❌ Polling error: %s%n", error.getMessage());
    }
});

// Later: stop polling
// poller.stop();
// pollerThread.join();
```

### 8) Statistics tracker

```java
public class TicketStatistics {
    /**
     * Track ticket statistics over time
     */
    public static class StatsTracker {
        private final List<Snapshot> history = new ArrayList<>();

        public record Snapshot(
            java.time.Instant timestamp,
            int positionCount,
            int orderCount,
            List<Long> positionTickets,
            List<Long> orderTickets
        ) {}

        public void recordSnapshot(MT5Account account) throws ApiExceptionMT5 {
            var reply = account.openedOrdersTickets();
            var data = reply.getData();

            var snapshot = new Snapshot(
                java.time.Instant.now(),
                data.getOpenedPositionTicketsList().size(),
                data.getOpenedOrdersTicketsList().size(),
                new ArrayList<>(data.getOpenedPositionTicketsList()),
                new ArrayList<>(data.getOpenedOrdersTicketsList())
            );

            history.add(snapshot);
        }

        public void printSummary() {
            if (history.isEmpty()) {
                System.out.println("No data recorded");
                return;
            }

            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║      TICKET STATISTICS SUMMARY     ║");
            System.out.println("╠════════════════════════════════════╣");

            int avgPos = (int) history.stream()
                .mapToInt(Snapshot::positionCount)
                .average()
                .orElse(0);

            int avgOrd = (int) history.stream()
                .mapToInt(Snapshot::orderCount)
                .average()
                .orElse(0);

            int maxPos = history.stream()
                .mapToInt(Snapshot::positionCount)
                .max()
                .orElse(0);

            int maxOrd = history.stream()
                .mapToInt(Snapshot::orderCount)
                .max()
                .orElse(0);

            var latest = history.get(history.size() - 1);

            System.out.printf("║ Snapshots taken: %-18d ║%n", history.size());
            System.out.printf("║ Current positions: %-16d ║%n", latest.positionCount());
            System.out.printf("║ Current orders: %-19d ║%n", latest.orderCount());
            System.out.printf("║ Average positions: %-16d ║%n", avgPos);
            System.out.printf("║ Average orders: %-19d ║%n", avgOrd);
            System.out.printf("║ Max positions: %-20d ║%n", maxPos);
            System.out.printf("║ Max orders: %-23d ║%n", maxOrd);
            System.out.println("╚════════════════════════════════════╝");
        }

        public void printHistory() {
            System.out.println("\nHistory:");
            for (int i = 0; i < history.size(); i++) {
                var snapshot = history.get(i);
                System.out.printf("[%d] %s - Pos: %d, Ord: %d%n",
                    i + 1,
                    snapshot.timestamp(),
                    snapshot.positionCount(),
                    snapshot.orderCount()
                );
            }
        }
    }
}

// Usage
var tracker = new TicketStatistics.StatsTracker();

// Record snapshots every 10 seconds for 1 minute
for (int i = 0; i < 6; i++) {
    tracker.recordSnapshot(account);
    Thread.sleep(10000);
}

tracker.printSummary();
tracker.printHistory();
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request (empty)
Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest request =
    Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest.newBuilder()
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountHelper.OpenedOrdersTicketsReply reply = accountHelperClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .openedOrdersTickets(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
List<Long> positionTickets = reply.getData().getOpenedPositionTicketsList();
List<Long> orderTickets = reply.getData().getOpenedOrdersTicketsList();
```

---

## 📊 Comparison: `openedOrdersTickets()` vs `openedOrders()`

| Aspect             | `openedOrdersTickets()`               | `openedOrders()`                         |
| ------------------ | ------------------------------------- | ---------------------------------------- |
| **Data returned**  | Only ticket numbers                   | Full position and order details          |
| **Bandwidth**      | Very low (just IDs)                   | High (complete structures)               |
| **Performance**    | Fast                                  | Slower (more data to transfer)           |
| **Use case**       | Monitoring, change detection          | Full analysis, profit calculation        |
| **Polling**        | Ideal for frequent polling            | Use sparingly for polling                |
| **Information**    | Minimal (just ticket IDs)             | Complete (prices, volumes, times, etc.)  |

**When to use which:**
- Use `openedOrdersTickets()` for frequent monitoring loops
- Use `openedOrders()` when you need detailed position information
- Common pattern: Poll with `openedOrdersTickets()`, then call `openedOrders()` when changes detected
