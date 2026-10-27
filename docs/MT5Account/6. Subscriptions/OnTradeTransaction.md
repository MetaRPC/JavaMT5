# 📝 Subscribe to Trade Transactions (Detailed Trading Events Stream)

> **Request:** subscribe to trade transaction events. Receives most detailed stream of all trading actions including order placement, modification, execution, position changes, and deal execution. The most comprehensive trading event stream available.

**API Information:**

* **SDK wrapper:** `MT5Account.onTradeTransaction(...)` (from package `pro.mrpc.mt5`)
* **gRPC service:** `mt5_term_api.SubscriptionService`
* **Proto definition:** `OnTradeTransaction` (defined in `mt5-term-api-subscriptions.proto`)

### RPC

* **Service:** `mt5_term_api.SubscriptionService`
* **Method:** `OnTradeTransaction(OnTradeTransactionRequest) → stream OnTradeTransactionReply`
* **Low‑level client (generated):** `SubscriptionServiceGrpc.SubscriptionServiceStub.onTradeTransaction(request, observer)`
* **SDK wrapper (high-level):**

```java
package pro.mrpc.mt5;

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

## 🔽 Input - `OnTradeTransactionRequest`

| Parameter | Type | Required | Description                              |
| --------- | ---- | -------- | ---------------------------------------- |
| _(empty)_ | -    | -        | No parameters required for subscription  |

---

## ⬆️ Output - `OnTradeTransactionData` (stream)

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

## 🧱 Related enums (from the generated stubs)

> All enums below come straight from `mt5-term-api-subscriptions.proto`. In Java they are nested on `Mt5TermApiSubscriptions` (e.g. `Mt5TermApiSubscriptions.SUB_ENUM_ORDER_TYPE.SUB_ORDER_TYPE_BUY`).

### `MT5_SUB_ENUM_EVENT_GROUP_TYPE` — field `OnTradeTransactionData.type`

| Enum Value | Value | Description |
|------------|-------|-------------|
| `OrderProfit` | 0 |  |
| `OrderUpdate` | 1 |  |

### `SUB_ENUM_TRADE_TRANSACTION_TYPE` — MqlTradeTransaction.type

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SUB_TRADE_TRANSACTION_ORDER_ADD` | 0 | Adding a new open order. |
| `SUB_TRADE_TRANSACTION_ORDER_UPDATE` | 1 | Updating an open order. The updates include not only evident changes from the client terminal or a trade server sides but also changes of an order state when setting it (for example, transition from ORDER_STATE_STARTED to ORDER_STATE_PLACED or from ORDER_STATE_PLACED to ORDER_STATE_PARTIAL, etc.). |
| `SUB_TRADE_TRANSACTION_ORDER_DELETE` | 2 | Removing an order from the list of the open ones. An order can be deleted from the open ones as a result of setting an appropriate request or execution (filling) and moving to the history. |
| `SUB_TRADE_TRANSACTION_DEAL_ADD` | 3 | Adding a deal to the history. The action is performed as a result of an order execution or performing operations with an account balance. |
| `SUB_TRADE_TRANSACTION_DEAL_UPDATE` | 4 | Updating a deal in the history. There may be cases when a previously executed deal is changed on a server. For example, a deal has been changed in an external trading system (exchange) where it was previously transferred by a broker. |
| `SUB_TRADE_TRANSACTION_DEAL_DELETE` | 5 | Deleting a deal from the history. There may be cases when a previously executed deal is deleted from a server. For example, a deal has been deleted in an external trading system (exchange) where it was previously transferred by a broker. |
| `SUB_TRADE_TRANSACTION_HISTORY_ADD` | 6 | Adding an order to the history as a result of execution or cancellation. |
| `SUB_TRADE_TRANSACTION_HISTORY_UPDATE` | 7 | Changing an order located in the orders history. This type is provided for enhancing functionality on a trade server side. |
| `SUB_TRADE_TRANSACTION_HISTORY_DELETE` | 8 | Deleting an order from the orders history. This type is provided for enhancing functionality on a trade server side. |
| `SUB_TRADE_TRANSACTION_POSITION` | 9 | Changing a position not related to a deal execution. This type of transaction shows that a position has been changed on a trade server side. Position volume, open price, Stop Loss and Take Profit levels can be changed. Data on changes are submitted in MqlTradeTransaction structure via OnTradeTransaction handler. Position change (adding, changing or closing), as a result of a deal execution, does not lead to the occurrence of TRADE_TRANSACTION_POSITION transaction. |
| `SUB_TRADE_TRANSACTION_REQUEST` | 10 | Notification of the fact that a trade request has been processed by a server and processing result has been received. Only type field (trade transaction type) must be analyzed for such transactions in MqlTradeTransaction structure. The second and third parameters of OnTradeTransaction (request and result) must be analyzed for additional data. |

### `SUB_ENUM_ORDER_TYPE` — MqlTradeTransaction / MqlTradeRequest.order_type

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SUB_ORDER_TYPE_BUY` | 0 |  |
| `SUB_ORDER_TYPE_SELL` | 1 |  |
| `SUB_ORDER_TYPE_BUY_LIMIT` | 2 |  |
| `SUB_ORDER_TYPE_SELL_LIMIT` | 3 |  |
| `SUB_ORDER_TYPE_BUY_STOP` | 4 |  |
| `SUB_ORDER_TYPE_SELL_STOP` | 5 |  |
| `SUB_ORDER_TYPE_BUY_STOP_LIMIT` | 6 |  |
| `SUB_ORDER_TYPE_SELL_STOP_LIMIT` | 7 |  |
| `SUB_ORDER_TYPE_CLOSE_BY` | 8 |  |

### `SUB_ENUM_ORDER_STATE` — MqlTradeTransaction.order_state

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SUB_ORDER_STATE_STARTED` | 0 | Order checked, but not yet accepted by broker |
| `SUB_ORDER_STATE_PLACED` | 1 | Order accepted |
| `SUB_ORDER_STATE_CANCELED` | 2 | Order canceled by client |
| `SUB_ORDER_STATE_PARTIAL` | 3 | Order partially executed |
| `SUB_ORDER_STATE_FILLED` | 4 | Order fully executed |
| `SUB_ORDER_STATE_REJECTED` | 5 | Order rejected |
| `SUB_ORDER_STATE_EXPIRED` | 6 | Order expired |
| `SUB_ORDER_STATE_REQUEST_ADD` | 7 | Order is being registered (placing to the trading system) |
| `SUB_ORDER_STATE_REQUEST_MODIFY` | 8 | Order is being modified (changing its parameters) |
| `SUB_ORDER_STATE_REQUEST_CANCEL` | 9 | Order is being deleted (deleting from the trading system) |

### `SUB_ENUM_DEAL_TYPE` — MqlTradeTransaction.deal_type

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SUB_DEAL_TYPE_BUY` | 0 | Buy |
| `SUB_DEAL_TYPE_SELL` | 1 | Sell |
| `SUB_DEAL_TYPE_BALANCE` | 2 | Balance |
| `SUB_DEAL_TYPE_CREDIT` | 3 | Credit |
| `SUB_DEAL_TYPE_CHARGE` | 4 | Additional charge |
| `SUB_DEAL_TYPE_CORRECTION` | 5 | Correction |
| `SUB_DEAL_TYPE_BONUS` | 6 | Bonus |
| `SUB_DEAL_TYPE_COMMISSION` | 7 | Additional commission |
| `SUB_DEAL_TYPE_COMMISSION_DAILY` | 8 | Daily commission |
| `SUB_DEAL_TYPE_COMMISSION_MONTHLY` | 9 | Monthly commission |
| `SUB_DEAL_TYPE_COMMISSION_AGENT_DAILY` | 10 | Daily agent commission |
| `SUB_DEAL_TYPE_COMMISSION_AGENT_MONTHLY` | 11 | Monthly agent commission |
| `SUB_DEAL_TYPE_INTEREST` | 12 | Interest rate |
| `SUB_DEAL_TYPE_BUY_CANCELED` | 13 | Canceled buy deal |
| `SUB_DEAL_TYPE_SELL_CANCELED` | 14 | Canceled sell deal |
| `SUB_DEAL_DIVIDEND` | 15 | Dividend operations |
| `SUB_DEAL_DIVIDEND_FRANKED` | 16 | Franked (non-taxable) dividend operations |
| `SUB_DEAL_TAX` | 17 | Tax charges |

### `SUB_ENUM_ORDER_TYPE_TIME` — MqlTradeTransaction / MqlTradeRequest.type_time

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SUB_ORDER_TIME_GTC` | 0 | Good till cancel order |
| `SUB_ORDER_TIME_DAY` | 1 | Good till current trade day order |
| `SUB_ORDER_TIME_SPECIFIED` | 2 | Good till expired order |
| `SUB_ORDER_TIME_SPECIFIED_DAY` | 3 | The order will be effective till 23:59:59 of the specified day. |

### `SUB_ENUM_TRADE_REQUEST_ACTIONS` — MqlTradeRequest.action

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SUB_TRADE_ACTION_UNDEFINED` | 0 |  |
| `SUB_TRADE_ACTION_DEAL` | 1 | Place a trade order for an immediate execution with the specified parameters (market order) |
| `SUB_TRADE_ACTION_PENDING` | 2 | Place a trade order for the execution under specified conditions (pending order) |
| `SUB_TRADE_ACTION_SLTP` | 3 | Modify Stop Loss and Take Profit values of an opened position |
| `SUB_TRADE_ACTION_MODIFY` | 4 | Modify the parameters of the order placed previously |
| `SUB_TRADE_ACTION_REMOVE` | 5 | Delete the pending order placed previously |
| `SUB_TRADE_ACTION_CLOSE_BY` | 6 | Close a position by an opposite one |

### `SUB_ENUM_ORDER_TYPE_FILLING` — MqlTradeRequest.type_filling

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SUB_ORDER_FILLING_FOK` | 0 |  |
| `SUB_ORDER_FILLING_IOC` | 1 |  |
| `SUB_ORDER_FILLING_BOC` | 2 |  |
| `SUB_ORDER_FILLING_RETURN` | 3 |  |

### `MqlErrorTradeCode` — MqlTradeResult.retcode

| Enum Value | Value | Description |
|------------|-------|-------------|
| `TRADE_RETCODE_SUCCESS` | 0 |  |
| `TRADE_RETCODE_REQUOTE` | 10004 | Requote |
| `TRADE_RETCODE_REJECT` | 10006 | Request rejected |
| `TRADE_RETCODE_CANCEL` | 10007 | Request canceled by trader |
| `TRADE_RETCODE_PLACED` | 10008 | Order placed |
| `TRADE_RETCODE_DONE` | 10009 | Request completed |
| `TRADE_RETCODE_DONE_PARTIAL` | 10010 | Only part of the request was completed |
| `TRADE_RETCODE_ERROR` | 10011 | Request processing error |
| `TRADE_RETCODE_TIMEOUT` | 10012 | Request canceled by timeout |
| `TRADE_RETCODE_INVALID` | 10013 | Invalid request |
| `TRADE_RETCODE_INVALID_VOLUME` | 10014 | Invalid volume in the request |
| `TRADE_RETCODE_INVALID_PRICE` | 10015 | Invalid price in the request |
| `TRADE_RETCODE_INVALID_STOPS` | 10016 | Invalid stops in the request |
| `TRADE_RETCODE_TRADE_DISABLED` | 10017 | Trade is disabled |
| `TRADE_RETCODE_MARKET_CLOSED` | 10018 | Market is closed |
| `TRADE_RETCODE_NO_MONEY` | 10019 | Not enough money to complete the request |
| `TRADE_RETCODE_PRICE_CHANGED` | 10020 | Prices changed |
| `TRADE_RETCODE_PRICE_OFF` | 10021 | No quotes to process the request |
| `TRADE_RETCODE_INVALID_EXPIRATION` | 10022 | Invalid order expiration date |
| `TRADE_RETCODE_ORDER_CHANGED` | 10023 | Order state changed |
| `TRADE_RETCODE_TOO_MANY_REQUESTS` | 10024 | Too frequent requests |
| `TRADE_RETCODE_NO_CHANGES` | 10025 | No changes in request |
| `TRADE_RETCODE_SERVER_DISABLES_AT` | 10026 | Autotrading disabled by server |
| `TRADE_RETCODE_CLIENT_DISABLES_AT` | 10027 | Autotrading disabled by client terminal |
| `TRADE_RETCODE_LOCKED` | 10028 | Request locked for processing |
| `TRADE_RETCODE_FROZEN` | 10029 | Order or position frozen |
| `TRADE_RETCODE_INVALID_FILL` | 10030 | Invalid order filling type |
| `TRADE_RETCODE_CONNECTION` | 10031 | No connection with trade server |
| `TRADE_RETCODE_ONLY_REAL` | 10032 | Operation allowed only for live accounts |
| `TRADE_RETCODE_LIMIT_ORDERS` | 10033 | Limit of pending orders reached |
| `TRADE_RETCODE_LIMIT_VOLUME` | 10034 | Volume of orders and positions reached the limit |
| `TRADE_RETCODE_INVALID_ORDER` | 10035 | Incorrect or prohibited order type |
| `TRADE_RETCODE_POSITION_CLOSED` | 10036 | Position already closed |
| `TRADE_RETCODE_INVALID_CLOSE_VOLUME` | 10038 | Close volume exceeds position volume |
| `TRADE_RETCODE_CLOSE_ORDER_EXIST` | 10039 | Close order already exists |
| `TRADE_RETCODE_LIMIT_POSITIONS` | 10040 | Limit of open positions reached |
| `TRADE_RETCODE_REJECT_CANCEL` | 10041 | Pending order activation request rejected |
| `TRADE_RETCODE_LONG_ONLY` | 10042 | Only long positions allowed |
| `TRADE_RETCODE_SHORT_ONLY` | 10043 | Only short positions allowed |
| `TRADE_RETCODE_CLOSE_ONLY` | 10044 | Only position closing allowed |
| `TRADE_RETCODE_FIFO_CLOSE` | 10045 | Closing allowed only by FIFO rule |
| `TRADE_RETCODE_HEDGE_PROHIBITED` | 10046 | Opposite positions disabled |

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
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
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
