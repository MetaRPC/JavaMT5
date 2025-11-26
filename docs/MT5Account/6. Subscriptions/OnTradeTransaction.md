# 📝 Subscribe to Trade Transactions (Detailed Trading Events Stream)

> **Request:** subscribe to trade transaction events. Receives most detailed stream of all trading actions including order placement, modification, execution, position changes, and deal execution. The most comprehensive trading event stream available.

**API Information:**

* **SDK wrapper:** `MT5Account.onTradeTransaction(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.SubscriptionService`
* **Proto definition:** `OnTradeTransaction` (defined in `mt5-term-api-subscriptions.proto`)

### RPC

* **Service:** `mt5_term_api.SubscriptionService`
* **Method:** `OnTradeTransaction(OnTradeTransactionRequest) → stream OnTradeTransactionReply`
* **Low‑level client (generated):** `SubscriptionServiceGrpc.SubscriptionServiceStub.onTradeTransaction(request, observer)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Subscribes to trade transaction events - the most detailed trading event stream.
     * Receives notifications for all trading actions: order placement, modification,
     * execution, position changes, and deal execution with complete details.
     * Use this for complete trading system integration and detailed audit trails.
     *
     * @param responseObserver Observer to receive streaming trade transaction events
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onTradeTransaction(
        StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> responseObserver
    ) throws ApiExceptionMT5;
}
```

---

## 🔽 Input — `OnTradeTransactionRequest`

| Parameter | Type | Required | Description                              |
| --------- | ---- | -------- | ---------------------------------------- |
| _(empty)_ | -    | -        | No parameters required for subscription  |

---

## ⬆️ Output — `OnTradeTransactionData` (stream)

Each transaction event contains:

| Field               | Type                    | Description                                          |
| ------------------- | ----------------------- | ---------------------------------------------------- |
| `trade_transaction` | `MqlTradeTransaction`   | Transaction details                                  |
| `trade_request`     | `MqlTradeRequest`       | Original trade request (if applicable)               |
| `trade_result`      | `MqlTradeResult`        | Trade execution result (if applicable)               |
| `account_info`      | `OnEventAccountInfo`    | Account state after transaction                      |

Access using `reply.getData().<field>`.

### Transaction Fields (`MqlTradeTransaction`)

| Field                        | Type        | Description                                          |
| ---------------------------- | ----------- | ---------------------------------------------------- |
| `type`                       | Enum        | Transaction type (ORDER_ADD, DEAL_ADD, POSITION, etc.) |
| `order_ticket`               | `uint64`    | Order ticket number                                  |
| `deal_ticket`                | `uint64`    | Deal ticket number (if deal transaction)             |
| `symbol`                     | `String`    | Trading symbol                                       |
| `order_type`                 | Enum        | Order type (BUY, SELL, BUY_LIMIT, etc.)              |
| `order_state`                | Enum        | Order state (PLACED, FILLED, CANCELED, etc.)         |
| `deal_type`                  | Enum        | Deal type (BUY, SELL, BALANCE, etc.)                 |
| `order_time_type`            | Enum        | Time-in-force (GTC, DAY, etc.)                       |
| `order_expiration_time`      | `Timestamp` | Order expiration time                                |
| `price`                      | `double`    | Price                                                |
| `price_trigger_stop_limit`   | `double`    | Stop limit activation price                          |
| `price_stop_loss`            | `double`    | Stop Loss level                                      |
| `price_take_profit`          | `double`    | Take Profit level                                    |
| `volume`                     | `double`    | Volume in lots                                       |
| `position_ticket`            | `uint64`    | Position ticket                                      |
| `position_by_opposite_position` | `uint64` | Opposite position ticket (for close-by)              |

### Transaction Types (`SUB_ENUM_TRADE_TRANSACTION_TYPE`)

| Value                           | Number | Description                                          |
| ------------------------------- | ------ | ---------------------------------------------------- |
| `SUB_TRADE_TRANSACTION_ORDER_ADD` | 0    | New order added                                      |
| `SUB_TRADE_TRANSACTION_ORDER_UPDATE` | 1 | Order updated (state change)                         |
| `SUB_TRADE_TRANSACTION_ORDER_DELETE` | 2 | Order deleted from open orders                       |
| `SUB_TRADE_TRANSACTION_DEAL_ADD` | 3    | New deal executed                                    |
| `SUB_TRADE_TRANSACTION_DEAL_UPDATE` | 4 | Deal updated in history                              |
| `SUB_TRADE_TRANSACTION_DEAL_DELETE` | 5 | Deal deleted from history                            |
| `SUB_TRADE_TRANSACTION_HISTORY_ADD` | 6 | Order moved to history (filled/canceled)             |
| `SUB_TRADE_TRANSACTION_HISTORY_UPDATE` | 7 | History order updated                              |
| `SUB_TRADE_TRANSACTION_HISTORY_DELETE` | 8 | History order deleted                              |
| `SUB_TRADE_TRANSACTION_POSITION` | 9    | Position changed (not by deal)                       |
| `SUB_TRADE_TRANSACTION_REQUEST` | 10    | Trade request processed                              |

### Request Fields (`MqlTradeRequest`)

| Field                          | Type        | Description                                          |
| ------------------------------ | ----------- | ---------------------------------------------------- |
| `trade_operation_type`         | Enum        | Operation (DEAL, PENDING, SLTP, MODIFY, REMOVE)      |
| `magic`                        | `uint64`    | Expert Advisor magic number                          |
| `order_ticket`                 | `uint64`    | Order ticket                                         |
| `symbol`                       | `String`    | Trading symbol                                       |
| `requested_deal_volume_lots`   | `double`    | Requested volume                                     |
| `price`                        | `double`    | Price                                                |
| `stop_loss`                    | `double`    | Stop Loss                                            |
| `take_profit`                  | `double`    | Take Profit                                          |
| `deviation`                    | `uint64`    | Maximum slippage                                     |
| `order_type`                   | Enum        | Order type                                           |
| `order_type_filling`           | Enum        | Fill type (FOK, IOC, RETURN)                         |
| `type_time`                    | Enum        | Time-in-force                                        |
| `order_expiration_time`        | `Timestamp` | Expiration time                                      |
| `order_comment`                | `String`    | Comment                                              |

### Result Fields (`MqlTradeResult`)

| Field                        | Type        | Description                                          |
| ---------------------------- | ----------- | ---------------------------------------------------- |
| `trade_return_int_code`      | `uint32`    | Return code (10009 = success)                        |
| `deal_ticket`                | `uint64`    | Deal ticket (if executed)                            |
| `order_ticket`               | `uint64`    | Order ticket                                         |
| `deal_volume`                | `double`    | Executed volume                                      |
| `deal_price`                 | `double`    | Execution price                                      |
| `current_bid`                | `double`    | Current Bid                                          |
| `current_ask`                | `double`    | Current Ask                                          |
| `broker_comment_to_operation`| `String`    | Broker comment                                       |

---

## 💬 Just the essentials

* **What it is.** Most detailed stream of all trading actions.
* **Why you need it.** Complete audit trail, detailed event tracking.
* **Returns.** Every trading action with full context.
* **Transaction types.** 11 different types of trading events.
* **Includes context.** Request, result, and account state.
* **Most comprehensive.** More detail than OnTrade stream.

---

## 🎯 Purpose

Use this method when you need to:

* Build complete trading audit logs.
* Track every trading action in detail.
* Implement sophisticated order management systems.
* Debug trading logic and execution.
* Create detailed trading analytics.
* Integrate with external trading systems.

---

## 🔗 Usage Examples

### 1) Basic transaction monitoring

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

            StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> observer =
                new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {

                @Override
                public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
                    if (reply.hasData()) {
                        var transaction = reply.getData().getTradeTransaction();

                        System.out.printf("\n📝 TRANSACTION: %s%n", transaction.getType());
                        System.out.printf("  Symbol: %s%n", transaction.getSymbol());
                        System.out.printf("  Order: #%d%n", transaction.getOrderTicket());

                        if (transaction.getDealTicket() > 0) {
                            System.out.printf("  Deal: #%d%n", transaction.getDealTicket());
                        }

                        if (transaction.getVolume() > 0) {
                            System.out.printf("  Volume: %.2f lots%n", transaction.getVolume());
                        }

                        if (transaction.getPrice() > 0) {
                            System.out.printf("  Price: %.5f%n", transaction.getPrice());
                        }
                    }
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Transaction stream error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Transaction stream completed");
                }
            };

            // Subscribe to trade transactions
            account.onTradeTransaction(observer);

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

### 2) Transaction type classifier

```java
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionClassifier {
    /**
     * Classify and count transaction types
     */
    public static void classifyTransactions(MT5Account account) throws Exception {

        Map<String, AtomicInteger> transactionCounts = new HashMap<>();

        StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
                if (reply.hasData()) {
                    var transaction = reply.getData().getTradeTransaction();
                    String type = transaction.getType().toString();

                    // Count transaction type
                    transactionCounts
                        .computeIfAbsent(type, k -> new AtomicInteger(0))
                        .incrementAndGet();

                    System.out.printf("\n📊 %s (total: %d)%n",
                        type, transactionCounts.get(type).get());

                    // Detailed info based on type
                    switch (transaction.getType()) {
                        case SUB_TRADE_TRANSACTION_ORDER_ADD:
                            System.out.printf("  ✅ Order added: #%d %s%n",
                                transaction.getOrderTicket(),
                                transaction.getOrderType());
                            break;

                        case SUB_TRADE_TRANSACTION_ORDER_UPDATE:
                            System.out.printf("  🔄 Order updated: #%d → %s%n",
                                transaction.getOrderTicket(),
                                transaction.getOrderState());
                            break;

                        case SUB_TRADE_TRANSACTION_ORDER_DELETE:
                            System.out.printf("  ❌ Order deleted: #%d%n",
                                transaction.getOrderTicket());
                            break;

                        case SUB_TRADE_TRANSACTION_DEAL_ADD:
                            System.out.printf("  💰 Deal executed: #%d %s %.2f @ %.5f%n",
                                transaction.getDealTicket(),
                                transaction.getDealType(),
                                transaction.getVolume(),
                                transaction.getPrice());
                            break;

                        case SUB_TRADE_TRANSACTION_POSITION:
                            System.out.printf("  📍 Position changed: #%d%n",
                                transaction.getPositionTicket());
                            break;

                        case SUB_TRADE_TRANSACTION_HISTORY_ADD:
                            System.out.printf("  📚 Moved to history: #%d%n",
                                transaction.getOrderTicket());
                            break;

                        case SUB_TRADE_TRANSACTION_REQUEST:
                            System.out.println("  📤 Trade request processed");
                            break;
                    }

                    // Print summary every 10 transactions
                    int total = transactionCounts.values().stream()
                        .mapToInt(AtomicInteger::get)
                        .sum();

                    if (total % 10 == 0) {
                        System.out.println("\n═══════════════════════════════════════");
                        System.out.println("TRANSACTION SUMMARY:");
                        transactionCounts.forEach((t, c) ->
                            System.out.printf("  %s: %d%n", t, c.get())
                        );
                        System.out.println("═══════════════════════════════════════");
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Classifier error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("\nFinal Transaction Summary:");
                transactionCounts.forEach((type, count) ->
                    System.out.printf("  %s: %d%n", type, count.get())
                );
            }
        };

        System.out.println("Classifying transactions...");
        account.onTradeTransaction(observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
TransactionClassifier.classifyTransactions(account);
```

### 3) Complete audit logger

```java
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;

public class AuditLogger {
    /**
     * Log all transactions to audit file
     */
    public static void logAudit(MT5Account account, String filename) throws Exception {

        PrintWriter writer = new PrintWriter(new FileWriter(filename, true));

        // CSV header
        writer.println("Timestamp,Type,OrderTicket,DealTicket,Symbol,OrderType," +
            "Volume,Price,SL,TP,State,Comment");
        writer.flush();

        StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();
                    var transaction = data.getTradeTransaction();

                    String timestamp = Instant.now().toString();

                    // Build comment from result if available
                    String comment = "";
                    if (data.hasTradeResult()) {
                        comment = data.getTradeResult().getBrokerCommentToOperation();
                    }

                    // Write to CSV
                    writer.printf("%s,%s,%d,%d,%s,%s,%.2f,%.5f,%.5f,%.5f,%s,\"%s\"%n",
                        timestamp,
                        transaction.getType(),
                        transaction.getOrderTicket(),
                        transaction.getDealTicket(),
                        transaction.getSymbol(),
                        transaction.getOrderType(),
                        transaction.getVolume(),
                        transaction.getPrice(),
                        transaction.getPriceStopLoss(),
                        transaction.getPriceTakeProfit(),
                        transaction.getOrderState(),
                        comment.replace("\"", "\"\"") // Escape quotes
                    );

                    writer.flush();

                    System.out.printf("📝 Logged: %s | Order #%d%n",
                        transaction.getType(), transaction.getOrderTicket());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Audit logger error: " + t.getMessage());
                writer.close();
            }

            @Override
            public void onCompleted() {
                System.out.println("Audit logging completed");
                writer.close();
            }
        };

        System.out.printf("Logging audit trail to %s%n", filename);
        account.onTradeTransaction(observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
AuditLogger.logAudit(account, "audit_trail.csv");
```

### 4) Order lifecycle tracker

```java
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;

public class OrderLifecycleTracker {
    static class OrderLifecycle {
        Instant created;
        String symbol;
        String orderType;
        double volume;
        String lastState;
        int updateCount;

        OrderLifecycle(String symbol, String orderType, double volume) {
            this.created = Instant.now();
            this.symbol = symbol;
            this.orderType = orderType;
            this.volume = volume;
            this.updateCount = 0;
        }
    }

    /**
     * Track complete lifecycle of each order
     */
    public static void trackLifecycles(MT5Account account) throws Exception {

        Map<Long, OrderLifecycle> orderLifecycles = new HashMap<>();

        StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
                if (reply.hasData()) {
                    var transaction = reply.getData().getTradeTransaction();
                    long orderTicket = transaction.getOrderTicket();

                    switch (transaction.getType()) {
                        case SUB_TRADE_TRANSACTION_ORDER_ADD:
                            // New order lifecycle starts
                            OrderLifecycle lifecycle = new OrderLifecycle(
                                transaction.getSymbol(),
                                transaction.getOrderType().toString(),
                                transaction.getVolume()
                            );
                            orderLifecycles.put(orderTicket, lifecycle);

                            System.out.printf("\n🆕 ORDER #%d CREATED%n", orderTicket);
                            System.out.printf("   Symbol: %s | Type: %s | Volume: %.2f%n",
                                lifecycle.symbol, lifecycle.orderType, lifecycle.volume);
                            break;

                        case SUB_TRADE_TRANSACTION_ORDER_UPDATE:
                            // Order state changed
                            if (orderLifecycles.containsKey(orderTicket)) {
                                OrderLifecycle lc = orderLifecycles.get(orderTicket);
                                lc.updateCount++;
                                lc.lastState = transaction.getOrderState().toString();

                                System.out.printf("\n🔄 ORDER #%d UPDATE #%d%n",
                                    orderTicket, lc.updateCount);
                                System.out.printf("   State: %s%n", lc.lastState);
                            }
                            break;

                        case SUB_TRADE_TRANSACTION_ORDER_DELETE:
                        case SUB_TRADE_TRANSACTION_HISTORY_ADD:
                            // Order lifecycle ends
                            if (orderLifecycles.containsKey(orderTicket)) {
                                OrderLifecycle lc = orderLifecycles.get(orderTicket);
                                long durationMs = Instant.now().toEpochMilli() -
                                    lc.created.toEpochMilli();

                                System.out.printf("\n✅ ORDER #%d COMPLETED%n", orderTicket);
                                System.out.printf("   Symbol: %s%n", lc.symbol);
                                System.out.printf("   Duration: %.2f seconds%n", durationMs / 1000.0);
                                System.out.printf("   Updates: %d%n", lc.updateCount);
                                System.out.printf("   Final State: %s%n", lc.lastState);

                                orderLifecycles.remove(orderTicket);
                            }
                            break;
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Lifecycle tracker error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("\nActive order lifecycles:");
                orderLifecycles.forEach((ticket, lifecycle) ->
                    System.out.printf("  #%d: %s %.2f lots (updates: %d)%n",
                        ticket, lifecycle.symbol, lifecycle.volume, lifecycle.updateCount)
                );
            }
        };

        System.out.println("Tracking order lifecycles...");
        account.onTradeTransaction(observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
OrderLifecycleTracker.trackLifecycles(account);
```

### 5) Real-time trade request monitor

```java
public class TradeRequestMonitor {
    /**
     * Monitor trade requests and their results
     */
    public static void monitorRequests(MT5Account account) throws Exception {

        StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();
                    var transaction = data.getTradeTransaction();

                    // Only process REQUEST transactions
                    if (transaction.getType() ==
                        Mt5TermApiSubscriptions.SUB_ENUM_TRADE_TRANSACTION_TYPE.SUB_TRADE_TRANSACTION_REQUEST) {

                        System.out.println("\n═══════════════════════════════════════");
                        System.out.println("📤 TRADE REQUEST PROCESSED");
                        System.out.println("═══════════════════════════════════════");

                        // Request details
                        if (data.hasTradeRequest()) {
                            var request = data.getTradeRequest();

                            System.out.printf("REQUEST:%n");
                            System.out.printf("  Operation: %s%n", request.getTradeOperationType());
                            System.out.printf("  Symbol: %s%n", request.getSymbol());
                            System.out.printf("  Volume: %.2f lots%n", request.getRequestedDealVolumeLots());
                            System.out.printf("  Price: %.5f%n", request.getPrice());
                            System.out.printf("  SL: %.5f | TP: %.5f%n",
                                request.getStopLoss(), request.getTakeProfit());

                            if (!request.getOrderComment().isEmpty()) {
                                System.out.printf("  Comment: %s%n", request.getOrderComment());
                            }
                        }

                        // Result details
                        if (data.hasTradeResult()) {
                            var result = data.getTradeResult();

                            System.out.printf("\nRESULT:%n");
                            System.out.printf("  Return Code: %d (%s)%n",
                                result.getTradeReturnIntCode(),
                                result.getTradeReturnCode());

                            if (result.getOrderTicket() > 0) {
                                System.out.printf("  Order Ticket: #%d%n", result.getOrderTicket());
                            }

                            if (result.getDealTicket() > 0) {
                                System.out.printf("  Deal Ticket: #%d%n", result.getDealTicket());
                                System.out.printf("  Executed: %.2f lots @ %.5f%n",
                                    result.getDealVolume(), result.getDealPrice());
                            }

                            if (!result.getBrokerCommentToOperation().isEmpty()) {
                                System.out.printf("  Broker: %s%n",
                                    result.getBrokerCommentToOperation());
                            }

                            // Success/failure indicator
                            if (result.getTradeReturnIntCode() == 10009) {
                                System.out.println("  Status: ✅ SUCCESS");
                            } else {
                                System.out.println("  Status: ❌ FAILED");
                            }
                        }

                        // Account state after request
                        if (data.hasAccountInfo()) {
                            var accountInfo = data.getAccountInfo();
                            System.out.printf("\nACCOUNT:%n");
                            System.out.printf("  Balance: $%.2f%n", accountInfo.getBalance());
                            System.out.printf("  Equity: $%.2f%n", accountInfo.getEquity());
                            System.out.printf("  Margin Level: %.2f%%%n", accountInfo.getMarginLevel());
                        }

                        System.out.println("═══════════════════════════════════════");
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Request monitor error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Request monitoring completed");
            }
        };

        System.out.println("Monitoring trade requests...");
        account.onTradeTransaction(observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
TradeRequestMonitor.monitorRequests(account);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import mt5_term_api.*;

// Build request (empty)
Mt5TermApiSubscriptions.OnTradeTransactionRequest request =
    Mt5TermApiSubscriptions.OnTradeTransactionRequest.newBuilder().build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Create observer
StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> observer =
    new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
            if (reply.hasData()) {
                var data = reply.getData();
                // Process transaction
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
    .onTradeTransaction(request, observer);
```

---

## 📌 Important Notes

**Transaction Types (11 types):**
1. **ORDER_ADD** - New order placed
2. **ORDER_UPDATE** - Order state changed
3. **ORDER_DELETE** - Order removed from active
4. **DEAL_ADD** - New deal executed
5. **DEAL_UPDATE** - Deal modified in history
6. **DEAL_DELETE** - Deal deleted from history
7. **HISTORY_ADD** - Order moved to history
8. **HISTORY_UPDATE** - History order modified
9. **HISTORY_DELETE** - History order deleted
10. **POSITION** - Position changed (not by deal)
11. **REQUEST** - Trade request processed

**Event Frequency:**
- Most frequent of all trading streams
- Multiple events per single trade action
- Example: Market order → REQUEST + ORDER_ADD + DEAL_ADD + HISTORY_ADD
- High-volume stream during active trading

**Data Completeness:**
- Most detailed information available
- Includes request, result, and account state
- Transaction object has all order/deal parameters
- Best for audit trails and debugging

**Comparison with OnTrade:**
- `OnTrade` = simplified, aggregated events
- `OnTradeTransaction` = detailed, individual events
- `OnTrade` = easier to use for basic needs
- `OnTradeTransaction` = complete for advanced needs

**Thread Safety:**
- Events arrive on gRPC thread
- High frequency = careful synchronization needed
- Use concurrent collections
- Consider event queues for processing

**Best Practices:**
- Filter by transaction type for specific needs
- Log all events for debugging
- Use for complete audit trails
- Combine with OnTrade for comprehensive coverage
- Handle high event volume efficiently

**Common Use Cases:**
- Complete trading audit logs
- Debugging trading execution
- Regulatory compliance logging
- Order execution analysis
- Trading system integration
- Performance monitoring

**Performance:**
- Highest event volume of all streams
- Keep processing fast
- Consider batching or queuing
- Monitor memory usage
- Offload heavy work to separate thread

**Error Handling:**
- Connection loss triggers `onError()`
- Implement reconnection
- May miss events during disconnect
- Query state after reconnect
- Validate event sequences
