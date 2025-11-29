# MT5Account · Positions & Orders - Overview

> Open positions, pending orders, historical deals, and order history. Use this page to choose the right API for querying trading state and history.

## 📁 What lives here

* **[PositionsTotal](./PositionsTotal.md)** - **count** of currently open positions.
* **[OpenedOrders](./OpenedOrders.md)** - **full details** of all open positions and pending orders.
* **[OpenedOrdersTickets](./OpenedOrdersTickets.md)** - **ticket numbers only** for positions and orders (lightweight).
* **[OrderHistory](./OrderHistory.md)** - **historical orders** within a time range.
* **[PositionsHistory](./PositionsHistory.md)** - **historical deals** (executed positions) within a time range.

---

## 🧭 Plain English

* **PositionsTotal** → **quick count** of open positions (no details).
* **OpenedOrders** → **complete picture** of all current positions and pending orders (symbol, volume, profit, SL/TP, etc.).
* **OpenedOrdersTickets** → **lightweight list** of ticket numbers (efficient for tracking changes).
* **OrderHistory** → **what orders were placed/canceled** in the past (order audit trail).
* **PositionsHistory** → **what deals were executed** (actual trades with profit/loss).

> Rule of thumb: need **full current state** → `OpenedOrders`; need **just count** → `PositionsTotal`; need **past trades** → `PositionsHistory`; need **past orders** → `OrderHistory`.

---

## Quick choose

| If you need…                                     | Use                      | Returns                    | Key inputs                          |
| ------------------------------------------------ | ------------------------ | -------------------------- | ----------------------------------- |
| Number of open positions                         | `PositionsTotal`         | Position count             | *(none)*                            |
| Full details of all open positions/orders        | `OpenedOrders`           | Complete position/order data | `with_history_orders` (bool)      |
| Just ticket numbers (lightweight)                | `OpenedOrdersTickets`    | Ticket arrays              | *(none)*                            |
| Historical orders (time range)                   | `OrderHistory`           | Order history list         | `from_time`, `to_time`              |
| Historical deals/trades (time range)             | `PositionsHistory`       | Deal history list          | `from_time`, `to_time`              |

---

## ❌ Cross‑refs & gotchas

* **Positions vs Orders**: Position = open trade; Pending Order = not yet executed.
* **OpenedOrders** returns BOTH positions and pending orders in one call.
* **OpenedOrdersTickets** is lightweight - use for change detection, then query full details if needed.
* **OrderHistory** shows order lifecycle (placed, modified, filled, canceled).
* **PositionsHistory** shows actual trade executions (deals) with profit/loss.
* **Time range** for history queries must be within broker's history limit.
* **Magic numbers** can filter orders/positions by Expert Advisor.
* **UTC timestamps** for historical queries.

---

## 🟢 Minimal snippets

```java
// Count open positions
var total = account.positionsTotal();
int count = total.getData().getTotal();
System.out.printf("Open positions: %d%n", count);
```

```java
// Get all open positions and orders
var reply = account.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
);
var data = reply.getData();

System.out.printf("Open Positions: %d%n", data.getPositionInfosCount());
for (int i = 0; i < data.getPositionInfosCount(); i++) {
    var pos = data.getPositionInfos(i);
    System.out.printf("  #%d %s %.2f lots: $%.2f%n",
        pos.getTicket(),
        pos.getSymbol(),
        pos.getVolume(),
        pos.getProfit());
}

System.out.printf("Pending Orders: %d%n", data.getOpenedOrdersCount());
for (int i = 0; i < data.getOpenedOrdersCount(); i++) {
    var order = data.getOpenedOrders(i);
    System.out.printf("  #%d %s %s @ %.5f%n",
        order.getTicket(),
        order.getSymbol(),
        order.getType(),
        order.getPrice());
}
```

```java
// Get just ticket numbers (lightweight)
var reply = account.openedOrdersTickets();
var data = reply.getData();

System.out.println("Position tickets:");
for (long ticket : data.getOpenedPositionTicketsList()) {
    System.out.printf("  #%d%n", ticket);
}

System.out.println("Order tickets:");
for (long ticket : data.getOpenedOrdersTicketsList()) {
    System.out.printf("  #%d%n", ticket);
}
```

```java
// Get historical deals (last 7 days)
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

Instant to = Instant.now();
Instant from = to.minus(7, ChronoUnit.DAYS);

Timestamp fromTs = Timestamp.newBuilder().setSeconds(from.getEpochSecond()).build();
Timestamp toTs = Timestamp.newBuilder().setSeconds(to.getEpochSecond()).build();

var reply = account.positionsHistory(fromTs, toTs);
var deals = reply.getData().getDealsList();

System.out.printf("Historical deals (last 7 days): %d%n", deals.size());
for (var deal : deals) {
    System.out.printf("  #%d %s %.2f @ %.5f = $%.2f%n",
        deal.getDealTicket(),
        deal.getSymbol(),
        deal.getVolume(),
        deal.getPrice(),
        deal.getProfit());
}
```

```java
// Get order history (last 30 days)
Instant to = Instant.now();
Instant from = to.minus(30, ChronoUnit.DAYS);

Timestamp fromTs = Timestamp.newBuilder().setSeconds(from.getEpochSecond()).build();
Timestamp toTs = Timestamp.newBuilder().setSeconds(to.getEpochSecond()).build();

var reply = account.orderHistory(fromTs, toTs);
var orders = reply.getData().getOrdersList();

System.out.printf("Order history (last 30 days): %d%n", orders.size());
for (var order : orders) {
    System.out.printf("  #%d %s %s: %s%n",
        order.getTicket(),
        order.getSymbol(),
        order.getType(),
        order.getState());
}
```

---

## See also

* **Subscriptions:** [`OnTrade`](../6.%20Subscriptions/OnTrade.md) - real-time position/order changes
* **Subscriptions:** [`OnPositionsAndPendingOrdersTickets`](../6.%20Subscriptions/OnPositionsAndPendingOrdersTickets.md) - real-time ticket tracking
* **Trading actions:** [`OrderSend`](../5.%20Trading/OrderSend.md), [`OrderClose`](../5.%20Trading/OrderClose.md)
