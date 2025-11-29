# MT5Account · Subscriptions (Streaming) - Overview

> Real-time event streams for prices, trades, positions, and transactions. Use this page to choose the right API for receiving live updates via gRPC streaming.

## 📁 What lives here

* **[OnSymbolTick](./OnSymbolTick.md)** - **real-time price updates** (bid, ask, volume) as they occur.
* **[OnTrade](./OnTrade.md)** - **trading events** (positions opened/closed, orders placed/modified/deleted).
* **[OnPositionProfit](./OnPositionProfit.md)** - **periodic profit/loss updates** for all open positions.
* **[OnPositionsAndPendingOrdersTickets](./OnPositionsAndPendingOrdersTickets.md)** - **periodic ticket lists** (lightweight position/order tracking).
* **[OnTradeTransaction](./OnTradeTransaction.md)** - **detailed transaction log** (most comprehensive trading event stream).

---

## 🧭 Plain English

* **OnSymbolTick** → **price changes** in real-time (tick-by-tick data stream).
* **OnTrade** → **trading activity** notifications (positions, orders, deals).
* **OnPositionProfit** → **profit tracking** at regular intervals (equity monitoring).
* **OnPositionsAndPendingOrdersTickets** → **lightweight state tracking** (just ticket numbers).
* **OnTradeTransaction** → **complete audit trail** (every trading action with full details).

> Rule of thumb: need **prices** → `OnSymbolTick`; need **trade events** → `OnTrade`; need **profit tracking** → `OnPositionProfit`; need **complete log** → `OnTradeTransaction`.

---

## Quick choose

| If you need…                                     | Use                                  | Event type          | Key inputs                          |
| ------------------------------------------------ | ------------------------------------ | ------------------- | ----------------------------------- |
| Real-time price updates (tick stream)            | `OnSymbolTick`                       | Continuous          | Symbols to monitor                  |
| Notifications when positions/orders change       | `OnTrade`                            | Event-driven        | *(none - monitors all)*             |
| Periodic profit/loss updates                     | `OnPositionProfit`                   | Periodic (timer)    | Update interval (milliseconds)      |
| Periodic ticket lists (lightweight)              | `OnPositionsAndPendingOrdersTickets` | Periodic (timer)    | Update interval (milliseconds)      |
| Detailed transaction log (complete audit)        | `OnTradeTransaction`                 | Event-driven        | *(none - monitors all)*             |

---

## ❌ Cross‑refs & gotchas

* **StreamObserver pattern**: All subscriptions use gRPC async streaming.
* **Three callback methods**: `onNext()`, `onError()`, `onCompleted()`.
* **Background thread**: Events arrive on gRPC thread pool, not main thread.
* **Thread safety**: Use concurrent collections for shared state.
* **Keep alive**: Main thread must stay alive to receive events.
* **OnSymbolTick**: Only during market hours; multiple symbols supported.
* **OnTrade**: Event-driven; only fires when trading activity occurs.
* **OnPositionProfit**: Periodic; you control update frequency.
* **OnPositionsAndPendingOrdersTickets**: Lightweight; just ticket numbers, no details.
* **OnTradeTransaction**: Highest frequency; most detailed; for audit trails.
* **Unsubscribe**: Close connection or let stream complete to clean up.

---

## 🟢 Minimal snippets

```java
import io.grpc.stub.StreamObserver;

// Subscribe to real-time ticks
StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> tickObserver =
    new StreamObserver<>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
            if (reply.hasData()) {
                var tick = reply.getData().getSymbolTick();
                System.out.printf("%s: Bid=%.5f, Ask=%.5f%n",
                    tick.getSymbol(), tick.getBid(), tick.getAsk());
            }
        }

        @Override
        public void onError(Throwable t) {
            System.err.println("Tick stream error: " + t.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("Tick stream completed");
        }
    };

account.onSymbolTick(new String[]{"EURUSD", "GBPUSD"}, tickObserver);
```

```java
// Subscribe to trade events
StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> tradeObserver =
    new StreamObserver<>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
            if (reply.hasData()) {
                var eventData = reply.getData().getEventData();
                int newPos = eventData.getNewPositionsCount();
                int closedPos = eventData.getDisappearedPositionsCount();

                if (newPos > 0) {
                    System.out.printf("✅ %d position(s) opened%n", newPos);
                }
                if (closedPos > 0) {
                    System.out.printf("❌ %d position(s) closed%n", closedPos);
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

account.onTrade(tradeObserver);
```

```java
// Subscribe to position profit updates (every 1 second)
StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> profitObserver =
    new StreamObserver<>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
            if (reply.hasData()) {
                var accountInfo = reply.getData().getAccountInfo();
                System.out.printf("Equity: $%.2f | Profit: $%.2f | Margin Level: %.2f%%%n",
                    accountInfo.getEquity(),
                    accountInfo.getProfit(),
                    accountInfo.getMarginLevel());
            }
        }

        @Override
        public void onError(Throwable t) {
            System.err.println("Profit stream error: " + t.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("Profit stream completed");
        }
    };

account.onPositionProfit(1000, false, profitObserver); // 1000ms interval
```

```java
// Subscribe to ticket updates (lightweight, every 2 seconds)
StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> ticketObserver =
    new StreamObserver<>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
            if (reply.hasData()) {
                var data = reply.getData();
                System.out.printf("Positions: %d | Orders: %d%n",
                    data.getPositionTicketsCount(),
                    data.getPendingOrderTicketsCount());
            }
        }

        @Override
        public void onError(Throwable t) {
            System.err.println("Ticket stream error: " + t.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("Ticket stream completed");
        }
    };

account.onPositionsAndPendingOrdersTickets(2000, ticketObserver); // 2000ms interval
```

```java
// Subscribe to detailed transactions (audit trail)
StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> transactionObserver =
    new StreamObserver<>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
            if (reply.hasData()) {
                var transaction = reply.getData().getTradeTransaction();
                System.out.printf("Transaction: %s | Order #%d | Symbol: %s%n",
                    transaction.getType(),
                    transaction.getOrderTicket(),
                    transaction.getSymbol());
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

account.onTradeTransaction(transactionObserver);
```

```java
// Keep main thread alive to receive events
Thread.sleep(60000); // Run for 1 minute
```

---

## Stream Comparison

| Stream                            | Frequency      | Detail Level | Use Case                          |
| --------------------------------- | -------------- | ------------ | --------------------------------- |
| `OnSymbolTick`                    | Continuous     | Price only   | Real-time charting, scalping      |
| `OnTrade`                         | Event-driven   | Medium       | Trade notifications, basic logging |
| `OnPositionProfit`                | Periodic       | Profit focus | Risk monitoring, equity tracking  |
| `OnPositionsAndPendingOrdersTickets` | Periodic    | Minimal      | Lightweight state sync            |
| `OnTradeTransaction`              | Event-driven   | Complete     | Audit logs, debugging, compliance |

---

## Best Practices

* **Always implement all three methods**: `onNext()`, `onError()`, `onCompleted()`.
* **Use CountDownLatch** for synchronization between main thread and stream thread.
* **Handle errors gracefully**: Implement reconnection logic in `onError()`.
* **Keep callbacks fast**: Offload heavy processing to separate threads.
* **Use concurrent collections**: Thread-safe data structures for shared state.
* **Monitor resource usage**: Streams consume network bandwidth and memory.
* **Unsubscribe when done**: Close connections to free resources.
* **Proper cleanup sequence**: Always call `disconnect()` then `close()` in finally block.

> ⚠️ **Critical:** Streams don't stop automatically! See [**gRPC Channel and Stream Management Guide**](../../GRPC_CHANNEL_AND_STREAM_MANAGEMENT_EN.md) for proper cleanup patterns and troubleshooting hanging programs.

---

## See also

* **Positions:** [`OpenedOrders`](../3.%20Positions_and_orders/OpenedOrders.md) - query current state
* **Trading:** [`OrderSend`](../5.%20Trading/OrderSend.md), [`OrderClose`](../5.%20Trading/OrderClose.md) - execute trades
* **Symbol info:** [`SymbolInfoTick`](../2.%20Symbol_information/SymbolInfoTick.md) - pull single quote
* **Account:** [`AccountSummary`](../1.%20Account_information/AccountSummary.md) - query account state
