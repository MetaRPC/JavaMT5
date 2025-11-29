# 🎫 Subscribe to Position & Order Tickets (State Tracking Stream)

> **Request:** subscribe to periodic updates of position and pending order ticket numbers. Receives lists of currently open position tickets and pending order tickets at regular intervals without full position details.

**API Information:**

* **SDK wrapper:** `MT5Account.onPositionsAndPendingOrdersTickets(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.SubscriptionService`
* **Proto definition:** `OnPositionsAndPendingOrdersTickets` (defined in `mt5-term-api-subscriptions.proto`)

### RPC

* **Service:** `mt5_term_api.SubscriptionService`
* **Method:** `OnPositionsAndPendingOrdersTickets(OnPositionsAndPendingOrdersTicketsRequest) → stream OnPositionsAndPendingOrdersTicketsReply`
* **Low‑level client (generated):** `SubscriptionServiceGrpc.SubscriptionServiceStub.onPositionsAndPendingOrdersTickets(request, observer)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Subscribes to periodic updates of position and pending order ticket numbers.
     * Receives lists of currently open position tickets and pending order tickets at regular intervals.
     * Use this to efficiently track which positions/orders exist without retrieving full details.
     *
     * @param timerPeriodMilliseconds Update interval in milliseconds
     * @param responseObserver Observer to receive streaming ticket lists
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onPositionsAndPendingOrdersTickets(
        int timerPeriodMilliseconds,
        StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> responseObserver
    ) throws ApiExceptionMT5;
}
```

---

## 🔽 Input - `OnPositionsAndPendingOrdersTicketsRequest`

| Parameter                   | Type    | Required | Description                                          |
| --------------------------- | ------- | -------- | ---------------------------------------------------- |
| `timer_period_milliseconds` | `int32` | ✅       | Update interval in milliseconds (e.g., 1000 = 1 sec) |

---

## ⬆️ Output - `OnPositionsAndPendingOrdersTicketsData` (stream)

Each update contains:

| Field                   | Type       | Description                                          |
| ----------------------- | ---------- | ---------------------------------------------------- |
| `position_tickets`      | `uint64[]` | Array of currently open position ticket numbers      |
| `pending_order_tickets` | `uint64[]` | Array of pending order ticket numbers                |
| `server_time`           | `Timestamp`| Current server time                                  |

Access using `reply.getData().<field>`.

**Important:**
- Returns **only ticket numbers**, not full position/order details
- Lightweight alternative to full position queries
- Use to detect opens/closes by comparing arrays
- Query full details separately when needed

---

## 💬 Just the essentials

* **What it is.** Periodic list of position & order ticket numbers.
* **Why you need it.** Lightweight tracking of which positions/orders exist.
* **Returns.** Arrays of ticket numbers at regular intervals.
* **No full details.** Just ticket numbers - efficient for state tracking.
* **Detect changes.** Compare arrays to find opens/closes.
* **Query details.** Fetch full position data separately when needed.

---

## 🎯 Purpose

Use this method when you need to:

* Track which positions/orders are currently open.
* Detect when positions are opened or closed.
* Monitor pending order placements/cancellations.
* Efficiently poll account state without heavy queries.
* Build lightweight position tracking systems.
* Synchronize external systems with MT5 state.

---

## 🔗 Usage Examples

### 1) Basic ticket monitoring

```java
import io.grpc.stub.StreamObserver;
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiSubscriptions;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> observer =
                new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {

                @Override
                public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
                    if (reply.hasData()) {
                        var data = reply.getData();

                        int positionCount = data.getPositionTicketsCount();
                        int orderCount = data.getPendingOrderTicketsCount();

                        System.out.printf("\n🎫 TICKETS UPDATE:%n");
                        System.out.printf("  Open Positions: %d%n", positionCount);
                        System.out.printf("  Pending Orders: %d%n", orderCount);

                        if (positionCount > 0) {
                            System.out.print("  Position tickets: ");
                            for (int i = 0; i < positionCount; i++) {
                                System.out.printf("#%d ", data.getPositionTickets(i));
                            }
                            System.out.println();
                        }

                        if (orderCount > 0) {
                            System.out.print("  Order tickets: ");
                            for (int i = 0; i < orderCount; i++) {
                                System.out.printf("#%d ", data.getPendingOrderTickets(i));
                            }
                            System.out.println();
                        }
                    }
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Tickets stream error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Tickets stream completed");
                }
            };

            // Update every 2000ms (2 seconds)
            account.onPositionsAndPendingOrdersTickets(2000, observer);

            // Keep alive
            Thread.sleep(60000); // 1 minute

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Detect position opens and closes

```java
import java.util.HashSet;
import java.util.Set;

public class PositionChangeDetector {
    /**
     * Detect when positions are opened or closed
     */
    public static void detectChanges(MT5Account account) throws Exception {

        Set<Long> previousPositions = new HashSet<>();
        Set<Long> previousOrders = new HashSet<>();

        StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();

                    // Current position tickets
                    Set<Long> currentPositions = new HashSet<>();
                    for (int i = 0; i < data.getPositionTicketsCount(); i++) {
                        currentPositions.add(data.getPositionTickets(i));
                    }

                    // Current order tickets
                    Set<Long> currentOrders = new HashSet<>();
                    for (int i = 0; i < data.getPendingOrderTicketsCount(); i++) {
                        currentOrders.add(data.getPendingOrderTickets(i));
                    }

                    // Detect new positions
                    Set<Long> newPositions = new HashSet<>(currentPositions);
                    newPositions.removeAll(previousPositions);
                    for (Long ticket : newPositions) {
                        System.out.printf("✅ NEW POSITION OPENED: #%d%n", ticket);
                    }

                    // Detect closed positions
                    Set<Long> closedPositions = new HashSet<>(previousPositions);
                    closedPositions.removeAll(currentPositions);
                    for (Long ticket : closedPositions) {
                        System.out.printf("❌ POSITION CLOSED: #%d%n", ticket);
                    }

                    // Detect new orders
                    Set<Long> newOrders = new HashSet<>(currentOrders);
                    newOrders.removeAll(previousOrders);
                    for (Long ticket : newOrders) {
                        System.out.printf("📝 PENDING ORDER PLACED: #%d%n", ticket);
                    }

                    // Detect canceled/executed orders
                    Set<Long> removedOrders = new HashSet<>(previousOrders);
                    removedOrders.removeAll(currentOrders);
                    for (Long ticket : removedOrders) {
                        System.out.printf("🗑️  PENDING ORDER REMOVED: #%d%n", ticket);
                    }

                    // Update previous state
                    previousPositions = currentPositions;
                    previousOrders = currentOrders;

                    // Status summary
                    if (newPositions.isEmpty() && closedPositions.isEmpty() &&
                        newOrders.isEmpty() && removedOrders.isEmpty()) {
                        System.out.printf("⏸️  No changes | Positions: %d | Orders: %d%n",
                            currentPositions.size(), currentOrders.size());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Change detector error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Change detection completed");
            }
        };

        System.out.println("Detecting position/order changes...");
        account.onPositionsAndPendingOrdersTickets(1000, observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
PositionChangeDetector.detectChanges(account);
```

### 3) Position limit enforcer

```java
public class PositionLimitEnforcer {
    /**
     * Enforce maximum number of open positions
     */
    public static void enforceLimit(MT5Account account, int maxPositions) throws Exception {

        StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();
                    int positionCount = data.getPositionTicketsCount();

                    System.out.printf("📊 Positions: %d/%d%n", positionCount, maxPositions);

                    if (positionCount > maxPositions) {
                        System.out.printf("\n⚠️  LIMIT EXCEEDED! ⚠️%n");
                        System.out.printf("   Current: %d positions%n", positionCount);
                        System.out.printf("   Maximum: %d positions%n", maxPositions);
                        System.out.printf("   Excess: %d positions%n", positionCount - maxPositions);

                        // Close excess positions (oldest first)
                        int toClose = positionCount - maxPositions;
                        System.out.printf("   Closing %d oldest positions...%n", toClose);

                        for (int i = 0; i < toClose && i < data.getPositionTicketsCount(); i++) {
                            long ticket = data.getPositionTickets(i);

                            try {
                                System.out.printf("   Closing #%d...%n", ticket);
                                var closeReply = account.orderClose(ticket, 0.0, 10);

                                if (closeReply.getData().getReturnedCode() == 10009) {
                                    System.out.printf("   ✅ Closed #%d%n", ticket);
                                } else {
                                    System.out.printf("   ❌ Failed to close #%d: %s%n",
                                        ticket, closeReply.getData().getComment());
                                }
                            } catch (Exception e) {
                                System.err.printf("   Error closing #%d: %s%n",
                                    ticket, e.getMessage());
                            }
                        }
                    } else if (positionCount == maxPositions) {
                        System.out.println("⚠️  At maximum capacity");
                    } else {
                        System.out.printf("✅ Within limit (%d available)%n",
                            maxPositions - positionCount);
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Limit enforcer error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Limit enforcement completed");
            }
        };

        System.out.printf("Enforcing position limit: %d%n", maxPositions);
        account.onPositionsAndPendingOrdersTickets(2000, observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage - max 5 positions
PositionLimitEnforcer.enforceLimit(account, 5);
```

### 4) Synchronize external database

```java
import java.util.HashSet;
import java.util.Set;

public class DatabaseSynchronizer {
    /**
     * Keep external database in sync with MT5 positions
     */
    public static void synchronize(MT5Account account) throws Exception {

        // Simulated external database (replace with real DB)
        Set<Long> databasePositions = new HashSet<>();
        Set<Long> databaseOrders = new HashSet<>();

        StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();

                    // Current MT5 state
                    Set<Long> mt5Positions = new HashSet<>();
                    for (int i = 0; i < data.getPositionTicketsCount(); i++) {
                        mt5Positions.add(data.getPositionTickets(i));
                    }

                    Set<Long> mt5Orders = new HashSet<>();
                    for (int i = 0; i < data.getPendingOrderTicketsCount(); i++) {
                        mt5Orders.add(data.getPendingOrderTickets(i));
                    }

                    // Sync positions: Add new
                    Set<Long> toAddPositions = new HashSet<>(mt5Positions);
                    toAddPositions.removeAll(databasePositions);
                    for (Long ticket : toAddPositions) {
                        System.out.printf("💾 DB: Adding position #%d%n", ticket);
                        databasePositions.add(ticket);
                        // INSERT INTO positions (ticket, ...) VALUES (ticket, ...)
                    }

                    // Sync positions: Remove closed
                    Set<Long> toRemovePositions = new HashSet<>(databasePositions);
                    toRemovePositions.removeAll(mt5Positions);
                    for (Long ticket : toRemovePositions) {
                        System.out.printf("🗑️  DB: Removing position #%d%n", ticket);
                        databasePositions.remove(ticket);
                        // UPDATE positions SET closed_at = NOW() WHERE ticket = ticket
                    }

                    // Sync orders: Add new
                    Set<Long> toAddOrders = new HashSet<>(mt5Orders);
                    toAddOrders.removeAll(databaseOrders);
                    for (Long ticket : toAddOrders) {
                        System.out.printf("💾 DB: Adding order #%d%n", ticket);
                        databaseOrders.add(ticket);
                        // INSERT INTO orders (ticket, ...) VALUES (ticket, ...)
                    }

                    // Sync orders: Remove
                    Set<Long> toRemoveOrders = new HashSet<>(databaseOrders);
                    toRemoveOrders.removeAll(mt5Orders);
                    for (Long ticket : toRemoveOrders) {
                        System.out.printf("🗑️  DB: Removing order #%d%n", ticket);
                        databaseOrders.remove(ticket);
                        // UPDATE orders SET status = 'REMOVED' WHERE ticket = ticket
                    }

                    // Status
                    if (toAddPositions.isEmpty() && toRemovePositions.isEmpty() &&
                        toAddOrders.isEmpty() && toRemoveOrders.isEmpty()) {
                        System.out.printf("✅ DB synchronized | Positions: %d | Orders: %d%n",
                            databasePositions.size(), databaseOrders.size());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("DB sync error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("DB synchronization completed");
            }
        };

        System.out.println("Synchronizing external database...");
        account.onPositionsAndPendingOrdersTickets(3000, observer); // Every 3 seconds
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
DatabaseSynchronizer.synchronize(account);
```

### 5) Alert on first/last position

```java
public class FirstLastPositionAlert {
    /**
     * Alert when first position is opened or last position is closed
     */
    public static void monitorFirstLast(MT5Account account) throws Exception {

        final int[] previousCount = {-1}; // -1 = not initialized

        StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();
                    int currentCount = data.getPositionTicketsCount();

                    // Initialize on first update
                    if (previousCount[0] == -1) {
                        previousCount[0] = currentCount;
                        System.out.printf("📊 Initial state: %d positions%n", currentCount);
                        return;
                    }

                    // Check for transitions
                    if (previousCount[0] == 0 && currentCount == 1) {
                        System.out.printf("\n🟢 FIRST POSITION OPENED!%n");
                        System.out.printf("   Ticket: #%d%n", data.getPositionTickets(0));
                        System.out.printf("   Account is now ACTIVE%n");

                    } else if (previousCount[0] == 1 && currentCount == 0) {
                        System.out.printf("\n🔴 LAST POSITION CLOSED!%n");
                        System.out.printf("   Account is now FLAT (no positions)%n");

                    } else if (previousCount[0] < currentCount) {
                        System.out.printf("🟢 Position opened (%d → %d)%n",
                            previousCount[0], currentCount);

                    } else if (previousCount[0] > currentCount) {
                        System.out.printf("🔴 Position closed (%d → %d)%n",
                            previousCount[0], currentCount);
                    }

                    previousCount[0] = currentCount;
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Alert monitor error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Alert monitoring completed");
            }
        };

        System.out.println("Monitoring first/last positions...");
        account.onPositionsAndPendingOrdersTickets(1000, observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
FirstLastPositionAlert.monitorFirstLast(account);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import mt5_term_api.*;

// Build request
Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest request =
    Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest.newBuilder()
        .setTimerPeriodMilliseconds(2000)  // 2 seconds
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Create observer
StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> observer =
    new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
            if (reply.hasData()) {
                var data = reply.getData();
                // Process ticket lists
            }
        }

        @Override
        public void onError(Throwable t) {
            // Handle error
        }

        @Override
        public void onCompleted() {
            // Handle completion
        }
    };

// Subscribe
subscriptionClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .onPositionsAndPendingOrdersTickets(request, observer);
```

---

## 📌 Important Notes

**What You Get:**
- Arrays of ticket numbers only
- No position details (symbol, volume, profit, etc.)
- Lightweight and efficient
- Good for state tracking

**What You Don't Get:**
- Position symbols, volumes, or prices
- Order types or parameters
- Profit/loss values
- Account balance/equity

**Update Frequency:**
- You control interval (milliseconds)
- Recommended: 1000-5000ms
- Lower = more current, higher CPU/network
- Higher = less load, potentially stale

**Detecting Changes:**
- Compare current tickets with previous
- New tickets = positions/orders opened
- Missing tickets = positions/orders closed
- Use Sets for efficient comparison

**Query Full Details:**
- Use this to detect changes
- Query full position data separately when needed
- Example: ticket appears → call `openedOrders()` for details

**Thread Safety:**
- Updates arrive on gRPC thread
- Use thread-safe collections (ConcurrentHashMap, CopyOnWriteArraySet)
- Synchronize shared state access

**Best Practices:**
- Store previous state to detect changes
- Use Set for efficient comparison
- Query full details only when needed
- Log ticket changes for debugging
- Handle reconnection properly

**Common Use Cases:**
- Lightweight position state tracking
- Detect opens/closes without full queries
- Synchronize external systems
- Enforce position limits
- Monitor account activity
- Build audit logs

**Performance:**
- Much lighter than full position queries
- Good for frequent polling
- Minimal network bandwidth
- Fast comparison operations

**Comparison with OnTrade:**
- `OnTrade` = event-driven (immediate)
- `OnPositionsAndPendingOrdersTickets` = polling (periodic)
- `OnTrade` includes full details
- This method includes only tickets
- Use both for complete coverage

**Error Handling:**
- Connection loss triggers `onError()`
- Reconnect automatically
- Compare with query after reconnect
- Validate ticket sequences
