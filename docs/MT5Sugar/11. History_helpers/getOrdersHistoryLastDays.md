# 📜 Get Orders History (Last N Days)

> **History method:** retrieves orders history for the last N days. Simplified alternative to manual timestamp calculation.

**API Information:**

* **Sugar method:** `MT5Sugar.getOrdersHistoryLastDays(int days, String symbol)`
* **Returns:** `OrdersHistoryData` - Proto message with historical orders
* **Underlying:** `MT5Service.orderHistory()` with auto-calculated timestamps

---

## 🔽 Input

| Parameter | Type     | Required | Description                                |
| --------- | -------- | -------- | ------------------------------------------ |
| `days`    | `int`    | ✅       | Number of days back to retrieve            |
| `symbol`  | `String` | ⚪       | Symbol filter (null = all symbols)         |

---

## ⬆️ Output

**Returns:** `Mt5TermApiAccountHelper.OrdersHistoryData` - Historical orders data

**Throws:** `ApiExceptionMT5` if request fails

**Execution:**
- Automatically calculates time range: (now - days) to now
- Sorts by close time descending (newest first)
- No pagination (offset=0, limit=0 = all results)

---

## 💬 Just the essentials

* **What it is.** Get order history for last N days without manual timestamps.
* **Why you need it.** Simpler than calculating Unix timestamps manually.
* **Use case.** Daily reports, performance analysis, trade review.

---

## 🔗 Usage Examples

### 1) Last 7 days history

```java
var history = sugar.getOrdersHistoryLastDays(7, null);

System.out.printf("Orders from last 7 days: %d%n",
    history.getOpenedOrderHistoriesCount());

for (var order : history.getOpenedOrderHistoriesList()) {
    System.out.printf("  #%d %s %.2f lots @ %.5f%n",
        order.getTicket(),
        order.getSymbol(),
        order.getVolume(),
        order.getClosePrice());
}
```

### 2) Today's orders for symbol

```java
String symbol = "EURUSD";
var history = sugar.getOrdersHistoryLastDays(1, symbol);

System.out.printf("Today's %s orders: %d%n",
    symbol, history.getOpenedOrderHistoriesCount());
```

### 3) Last 30 days performance

```java
var history = sugar.getOrdersHistoryLastDays(30, null);

double totalProfit = 0.0;
int wins = 0;
int losses = 0;

for (var order : history.getOpenedOrderHistoriesList()) {
    double profit = order.getProfit();
    totalProfit += profit;

    if (profit > 0) wins++;
    else if (profit < 0) losses++;
}

System.out.printf("Last 30 days:%n");
System.out.printf("  Total P/L: $%.2f%n", totalProfit);
System.out.printf("  Wins: %d | Losses: %d%n", wins, losses);
System.out.printf("  Win rate: %.1f%%%n", (wins * 100.0) / (wins + losses));
```

---

## 📌 Important Notes

* **Time range:** From (now - days) to now
* **Symbol filter:** Pass null for all symbols
* **Sort order:** Newest first (close time descending)
* **All results:** No pagination applied

---

## See also

* **Positions history:** [`getPositionsHistoryPaged()`](./getPositionsHistoryPaged.md)
* **Low-level:** [`orderHistory()`](../../MT5Account/3.%20Positions_and_orders/OrderHistory.md)
* **Timestamp helpers:** [`createTimestamp()`](../9.%20Account_and_position_helpers/createTimestamp.md)
