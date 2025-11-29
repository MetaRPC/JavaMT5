# 📋 Subscribe to Trade Events (Trading Activity Stream)

> **Request:** subscribe to trade event notifications. Receives stream of events when positions are opened/closed, pending orders are placed/modified/deleted, or deals are executed.

**API Information:**

* **SDK wrapper:** `MT5Account.onTrade(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.SubscriptionService`
* **Proto definition:** `OnTrade` (defined in `mt5-term-api-subscriptions.proto`)

### RPC

* **Service:** `mt5_term_api.SubscriptionService`
* **Method:** `OnTrade(OnTradeRequest) → stream OnTradeReply`
* **Low‑level client (generated):** `SubscriptionServiceGrpc.SubscriptionServiceStub.onTrade(request, observer)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Subscribes to trade events whenever a trading operation occurs.
     * Receives notifications when orders are opened, closed, modified, or deleted.
     * Use this to track all trading activity in real-time and react to order execution events.
     *
     * @param responseObserver Observer to receive streaming trade event notifications
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onTrade(
        StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> responseObserver
    ) throws ApiExceptionMT5;
}
```

---

## 🔽 Input - `OnTradeRequest`

| Parameter | Type | Required | Description                              |
| --------- | ---- | -------- | ---------------------------------------- |
| _(empty)_ | -    | -        | No parameters required for subscription  |

---

## ⬆️ Output - `OnTadeEventData` (stream)

Each trade event contains:

| Field                         | Type                         | Description                                          |
| ----------------------------- | ---------------------------- | ---------------------------------------------------- |
| `new_positions`               | `OnTradePositionInfo[]`      | Newly opened positions                               |
| `disappeared_positions`       | `OnTradePositionInfo[]`      | Closed positions                                     |
| `updated_positions`           | `OnTradePositionUpdate[]`    | Modified positions (SL/TP changes)                   |
| `new_orders`                  | `OnTradeOrderInfo[]`         | New pending orders placed                            |
| `disappeared_orders`          | `OnTradeOrderInfo[]`         | Canceled or executed pending orders                  |
| `state_changed_orders`        | `OnTradeOrderStateChange[]`  | Orders with state changes                            |
| `new_history_deals`           | `OnTradeHistoryDealInfo[]`   | New deals executed                                   |
| `disappeared_history_deals`   | `OnTradeHistoryDealInfo[]`   | Deleted deals                                        |
| `updated_history_deals`       | `OnTradeHistoryDealUpdate[]` | Modified deals                                       |
| `new_history_orders`          | `OnTradeHistoryOrderInfo[]`  | Orders moved to history                              |
| `disappeared_history_orders`  | `OnTradeHistoryOrderInfo[]`  | Deleted history orders                               |
| `updated_history_orders`      | `OnTradeHistoryOrderUpdate[]`| Modified history orders                              |

Access using `reply.getData().getEventData().<field>`.

### Position Info Fields (`OnTradePositionInfo`)

| Field           | Type        | Description                                          |
| --------------- | ----------- | ---------------------------------------------------- |
| `ticket`        | `int64`     | Position ticket number                               |
| `symbol`        | `String`    | Trading symbol                                       |
| `type`          | Enum        | BUY or SELL                                          |
| `volume`        | `double`    | Position volume in lots                              |
| `price_open`    | `double`    | Opening price                                        |
| `price_current` | `double`    | Current price                                        |
| `profit`        | `double`    | Current profit/loss                                  |
| `sl`            | `double`    | Stop Loss level                                      |
| `tp`            | `double`    | Take Profit level                                    |
| `swap`          | `double`    | Swap charges                                         |
| `magic`         | `int64`     | Expert Advisor magic number                          |
| `comment`       | `String`    | Position comment                                     |

### Order Info Fields (`OnTradeOrderInfo`)

| Field           | Type        | Description                                          |
| --------------- | ----------- | ---------------------------------------------------- |
| `ticket`        | `int64`     | Order ticket number                                  |
| `symbol`        | `String`    | Trading symbol                                       |
| `order_type`    | Enum        | Order type (BUY_LIMIT, SELL_STOP, etc.)              |
| `state`         | Enum        | Order state (PLACED, FILLED, CANCELED, etc.)         |
| `volume_initial`| `double`    | Initial volume                                       |
| `volume_current`| `double`    | Current volume (remaining)                           |
| `price_open`    | `double`    | Order price                                          |
| `stop_loss`     | `double`    | Stop Loss level                                      |
| `take_profit`   | `double`    | Take Profit level                                    |
| `stop_limit`    | `double`    | Stop Limit price                                     |
| `time_expiration`| `Timestamp`| Expiration time                                      |
| `magic`         | `int64`     | Expert Advisor magic number                          |
| `comment`       | `String`    | Order comment                                        |

### Deal Info Fields (`OnTradeHistoryDealInfo`)

| Field          | Type        | Description                                          |
| -------------- | ----------- | ---------------------------------------------------- |
| `ticket`       | `uint64`    | Deal ticket number                                   |
| `order_ticket` | `int64`     | Order that created this deal                         |
| `symbol`       | `String`    | Trading symbol                                       |
| `type`         | Enum        | Deal type (BUY, SELL, BALANCE, etc.)                 |
| `entry`        | Enum        | IN (open), OUT (close), INOUT (reverse)              |
| `volume`       | `double`    | Deal volume                                          |
| `price`        | `double`    | Deal price                                           |
| `profit`       | `double`    | Deal profit                                          |
| `commission`   | `double`    | Commission charged                                   |
| `swap`         | `double`    | Swap charged                                         |
| `fee`          | `double`    | Additional fee                                       |
| `magic`        | `int64`     | Expert Advisor magic number                          |
| `comment`      | `String`    | Deal comment                                         |

---

## 💬 Just the essentials

* **What it is.** Real-time notifications of all trading activity.
* **Why you need it.** Track order execution, position changes, deals.
* **Returns.** Stream of trading events as they occur.
* **No parameters.** Automatically monitors all trading activity.
* **Comprehensive.** Includes positions, orders, deals, and history.
* **React instantly.** Build automated responses to trading events.

---

## 🎯 Purpose

Use this method when you need to:

* Monitor all trading activity in real-time.
* React to order executions automatically.
* Track position opens/closes for logging.
* Detect when pending orders are filled.
* Build notification systems for trades.
* Synchronize trading state across systems.

---

## 🔗 Usage Examples

### 1) Basic trade event monitoring

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

            StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> observer =
                new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {

                @Override
                public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
                    if (reply.hasData()) {
                        var eventData = reply.getData().getEventData();

                        // Count new positions
                        int newPositions = eventData.getNewPositionsCount();
                        int closedPositions = eventData.getDisappearedPositionsCount();
                        int newOrders = eventData.getNewOrdersCount();
                        int newDeals = eventData.getNewHistoryDealsCount();

                        if (newPositions > 0 || closedPositions > 0 ||
                            newOrders > 0 || newDeals > 0) {

                            System.out.printf("\n📊 TRADE EVENT:%n");
                            System.out.printf("  New positions: %d%n", newPositions);
                            System.out.printf("  Closed positions: %d%n", closedPositions);
                            System.out.printf("  New orders: %d%n", newOrders);
                            System.out.printf("  New deals: %d%n", newDeals);
                        }
                    }
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Trade stream error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Trade stream completed");
                }
            };

            // Subscribe to trade events
            account.onTrade(observer);

            // Keep alive
            Thread.sleep(300000); // 5 minutes

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Position opened/closed notifications

```java
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PositionNotifier {
    private static final DateTimeFormatter TIME_FORMAT =
        DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

    /**
     * Send notifications when positions are opened or closed
     */
    public static void monitorPositions(MT5Account account)
            throws ApiExceptionMT5, InterruptedException {

        StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
                if (reply.hasData()) {
                    var eventData = reply.getData().getEventData();

                    // New positions opened
                    for (int i = 0; i < eventData.getNewPositionsCount(); i++) {
                        var position = eventData.getNewPositions(i);

                        System.out.printf("\n✅ POSITION OPENED @ %s:%n",
                            TIME_FORMAT.format(Instant.now()));
                        System.out.printf("  Ticket: #%d%n", position.getTicket());
                        System.out.printf("  Symbol: %s%n", position.getSymbol());
                        System.out.printf("  Type: %s%n", position.getType());
                        System.out.printf("  Volume: %.2f lots%n", position.getVolume());
                        System.out.printf("  Price: %.5f%n", position.getPriceOpen());
                        System.out.printf("  SL: %.5f | TP: %.5f%n",
                            position.getSl(), position.getTp());

                        if (!position.getComment().isEmpty()) {
                            System.out.printf("  Comment: %s%n", position.getComment());
                        }
                    }

                    // Positions closed
                    for (int i = 0; i < eventData.getDisappearedPositionsCount(); i++) {
                        var position = eventData.getDisappearedPositions(i);

                        System.out.printf("\n❌ POSITION CLOSED @ %s:%n",
                            TIME_FORMAT.format(Instant.now()));
                        System.out.printf("  Ticket: #%d%n", position.getTicket());
                        System.out.printf("  Symbol: %s%n", position.getSymbol());
                        System.out.printf("  Type: %s%n", position.getType());
                        System.out.printf("  Volume: %.2f lots%n", position.getVolume());
                        System.out.printf("  Profit: $%.2f%n", position.getProfit());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Position monitor error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Position monitoring completed");
            }
        };

        System.out.println("Monitoring position changes...");
        account.onTrade(observer);

        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
PositionNotifier.monitorPositions(account);
```

### 3) Pending order execution detector

```java
public class OrderExecutionDetector {
    /**
     * Detect when pending orders are executed
     */
    public static void detectExecutions(MT5Account account)
            throws ApiExceptionMT5, InterruptedException {

        StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
                if (reply.hasData()) {
                    var eventData = reply.getData().getEventData();

                    // New pending orders
                    for (int i = 0; i < eventData.getNewOrdersCount(); i++) {
                        var order = eventData.getNewOrders(i);

                        System.out.printf("\n📝 PENDING ORDER PLACED:%n");
                        System.out.printf("  Ticket: #%d%n", order.getTicket());
                        System.out.printf("  Symbol: %s%n", order.getSymbol());
                        System.out.printf("  Type: %s%n", order.getOrderType());
                        System.out.printf("  Volume: %.2f lots%n", order.getVolumeInitial());
                        System.out.printf("  Price: %.5f%n", order.getPriceOpen());
                        System.out.printf("  State: %s%n", order.getState());
                    }

                    // Orders that disappeared (executed or canceled)
                    for (int i = 0; i < eventData.getDisappearedOrdersCount(); i++) {
                        var order = eventData.getDisappearedOrders(i);

                        String status = order.getState().toString().contains("FILLED")
                            ? "✅ EXECUTED"
                            : "❌ CANCELED";

                        System.out.printf("\n%s - Order #%d:%n", status, order.getTicket());
                        System.out.printf("  Symbol: %s%n", order.getSymbol());
                        System.out.printf("  Type: %s%n", order.getOrderType());
                        System.out.printf("  Volume: %.2f lots%n", order.getVolumeInitial());
                        System.out.printf("  Final State: %s%n", order.getState());
                    }

                    // Order state changes
                    for (int i = 0; i < eventData.getStateChangedOrdersCount(); i++) {
                        var change = eventData.getStateChangedOrders(i);
                        var prev = change.getPreviousOrder();
                        var curr = change.getCurrentOrder();

                        System.out.printf("\n🔄 ORDER STATE CHANGED - #%d:%n", curr.getTicket());
                        System.out.printf("  %s → %s%n",
                            prev.getState(), curr.getState());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Execution detector error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Execution detection completed");
            }
        };

        System.out.println("Monitoring order executions...");
        account.onTrade(observer);

        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
OrderExecutionDetector.detectExecutions(account);
```

### 4) Deal history logger

```java
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;

public class DealLogger {
    /**
     * Log all executed deals to file
     */
    public static void logDeals(MT5Account account, String filename)
            throws Exception {

        PrintWriter writer = new PrintWriter(new FileWriter(filename, true));

        // Write header if file is new
        writer.println("Timestamp,Ticket,Symbol,Type,Entry,Volume,Price,Profit,Commission,Swap");
        writer.flush();

        StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
                if (reply.hasData()) {
                    var eventData = reply.getData().getEventData();

                    // Log new deals
                    for (int i = 0; i < eventData.getNewHistoryDealsCount(); i++) {
                        var deal = eventData.getNewHistoryDeals(i);

                        Instant dealTime = Instant.ofEpochSecond(
                            deal.getDealTime().getSeconds()
                        );

                        // Write to CSV
                        writer.printf("%s,%d,%s,%s,%s,%.2f,%.5f,%.2f,%.2f,%.2f%n",
                            dealTime.toString(),
                            deal.getTicket(),
                            deal.getSymbol(),
                            deal.getType(),
                            deal.getEntry(),
                            deal.getVolume(),
                            deal.getPrice(),
                            deal.getProfit(),
                            deal.getCommission(),
                            deal.getSwap());

                        writer.flush();

                        // Console notification
                        System.out.printf("📄 Logged deal #%d: %s %s %.2f @ %.5f = $%.2f%n",
                            deal.getTicket(),
                            deal.getEntry(),
                            deal.getSymbol(),
                            deal.getVolume(),
                            deal.getPrice(),
                            deal.getProfit());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Deal logger error: " + t.getMessage());
                writer.close();
            }

            @Override
            public void onCompleted() {
                System.out.println("Deal logging completed");
                writer.close();
            }
        };

        System.out.printf("Logging deals to %s%n", filename);
        account.onTrade(observer);

        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
DealLogger.logDeals(account, "deals.csv");
```

### 5) Trading statistics tracker

```java
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TradingStatsTracker {
    /**
     * Track real-time trading statistics
     */
    public static void trackStats(MT5Account account)
            throws ApiExceptionMT5, InterruptedException {

        AtomicInteger totalPositionsOpened = new AtomicInteger(0);
        AtomicInteger totalPositionsClosed = new AtomicInteger(0);
        AtomicInteger totalOrdersPlaced = new AtomicInteger(0);
        AtomicInteger totalDeals = new AtomicInteger(0);
        AtomicReference<Double> totalProfit = new AtomicReference<>(0.0);

        StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
                if (reply.hasData()) {
                    var eventData = reply.getData().getEventData();

                    // Update counters
                    int newPos = eventData.getNewPositionsCount();
                    int closedPos = eventData.getDisappearedPositionsCount();
                    int newOrders = eventData.getNewOrdersCount();
                    int newDeals = eventData.getNewHistoryDealsCount();

                    if (newPos > 0) totalPositionsOpened.addAndGet(newPos);
                    if (closedPos > 0) totalPositionsClosed.addAndGet(closedPos);
                    if (newOrders > 0) totalOrdersPlaced.addAndGet(newOrders);
                    if (newDeals > 0) totalDeals.addAndGet(newDeals);

                    // Calculate profit from closed positions
                    for (int i = 0; i < closedPos; i++) {
                        var position = eventData.getDisappearedPositions(i);
                        totalProfit.updateAndGet(v -> v + position.getProfit());
                    }

                    // Print stats if any activity
                    if (newPos > 0 || closedPos > 0 || newOrders > 0 || newDeals > 0) {
                        System.out.printf("\n═══════════════════════════════════════%n");
                        System.out.printf("📊 TRADING STATISTICS%n");
                        System.out.printf("═══════════════════════════════════════%n");
                        System.out.printf("Positions Opened:  %d%n", totalPositionsOpened.get());
                        System.out.printf("Positions Closed:  %d%n", totalPositionsClosed.get());
                        System.out.printf("Orders Placed:     %d%n", totalOrdersPlaced.get());
                        System.out.printf("Deals Executed:    %d%n", totalDeals.get());
                        System.out.printf("Total Profit/Loss: $%.2f%n", totalProfit.get());
                        System.out.printf("═══════════════════════════════════════%n");
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Stats tracker error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("\nFinal Statistics:");
                System.out.printf("  Positions: %d opened, %d closed%n",
                    totalPositionsOpened.get(), totalPositionsClosed.get());
                System.out.printf("  Total P/L: $%.2f%n", totalProfit.get());
            }
        };

        System.out.println("Tracking trading statistics...");
        account.onTrade(observer);

        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
TradingStatsTracker.trackStats(account);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import mt5_term_api.*;

// Build request (empty)
Mt5TermApiSubscriptions.OnTradeRequest request =
    Mt5TermApiSubscriptions.OnTradeRequest.newBuilder().build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Create observer
StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> observer =
    new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
            if (reply.hasData()) {
                var eventData = reply.getData().getEventData();
                // Process trade events
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
    .onTrade(request, observer);
```

---

## 📌 Important Notes

**Event Types:**
- **New positions** - positions just opened
- **Disappeared positions** - positions just closed
- **Updated positions** - SL/TP or other changes
- **New orders** - pending orders just placed
- **Disappeared orders** - orders executed or canceled
- **State changed orders** - order state transitions
- **New deals** - deals just executed
- **History updates** - changes to historical data

**When Events Fire:**
- Position opened → `new_positions`
- Position closed → `disappeared_positions`
- SL/TP modified → `updated_positions`
- Pending order placed → `new_orders`
- Pending order filled → `disappeared_orders` + `new_positions`
- Pending order canceled → `disappeared_orders`
- Market order executed → `new_deals` + `new_positions`

**Event Frequency:**
- Only fires when trading activity occurs
- No events during idle periods
- Multiple events can arrive in single message
- Check all arrays - may have multiple changes

**Thread Safety:**
- Events arrive on gRPC thread
- Use thread-safe collections for shared state
- Synchronize access to mutable data
- Consider using concurrent queues

**Best Practices:**
- Check all event arrays - don't assume only one type
- Log ticket numbers for debugging
- Handle rapid succession of events
- Implement idempotency for event processing
- Store position/order tickets for tracking
- Use magic numbers to identify your trades

**Common Use Cases:**
- Trade execution notifications
- Position tracking systems
- Order management systems
- Trading journals and logs
- Performance analytics
- Risk management triggers

**Error Handling:**
- Connection loss triggers `onError()`
- Implement reconnection logic
- May miss events during disconnection
- Query current state after reconnect
- Validate event data integrity
