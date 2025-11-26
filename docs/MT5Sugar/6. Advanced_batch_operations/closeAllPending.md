# 🗑️ Cancel All Pending Orders (Excluding Positions)

> **Advanced method:** cancels only pending orders (LIMIT/STOP), not active positions. Separates order management from position management.

**API Information:**

* **Sugar method:** `MT5Sugar.closeAllPending(String symbol, Boolean isBuy)`
* **Underlying methods:**
  - [`openedOrders()`](../../MT5Account/3.%20Positions_and_orders/OpenedOrders.md) - get all orders
  - [`orderClose()`](../../MT5Account/5.%20Trading/OrderClose.md) - cancel each order
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type      | Required | Description                                |
| --------- | --------- | -------- | ------------------------------------------ |
| `symbol`  | `String`  | ⚪       | Symbol name (null = all symbols)           |
| `isBuy`   | `Boolean` | ⚪       | true=BUY orders, false=SELL orders, null=all |

---

## ⬆️ Output

**Returns:** `int` - Number of pending orders successfully cancelled

**Throws:** `ApiExceptionMT5` if operation fails

**Execution:**
- Filters only OpenedOrderInfo entries (pending orders)
- Types: BUY_LIMIT, SELL_LIMIT, BUY_STOP, SELL_STOP (order type 2-7)
- Ignores active positions
- Continues even if some cancellations fail

---

## 💬 Just the essentials

* **What it is.** Cancel pending entry orders, keep active positions open.
* **Why you need it.** Remove future entries without exiting current trades.
* **Difference.** vs closeAll() - this skips active positions.

---

## 🎯 Purpose

Use this method when you need to:

* Cancel pending orders but keep positions open.
* Remove future entries without closing current trades.
* Clean up unused LIMIT/STOP orders.

---

## 🔗 Usage Examples

### 1) Cancel all pending orders for symbol

```java
String symbol = "EURUSD";

int cancelled = sugar.closeAllPending(symbol, null);

System.out.printf("✅ Cancelled %d pending orders for %s%n", cancelled, symbol);
System.out.printf("   Active positions remain open%n");
```

### 2) Cancel all BUY pending orders

```java
int cancelled = sugar.closeAllPending("GBPUSD", true);

System.out.printf("✅ Cancelled %d BUY pending orders%n", cancelled);
```

### 3) Cancel all SELL pending orders

```java
int cancelled = sugar.closeAllPending("USDJPY", false);

System.out.printf("✅ Cancelled %d SELL pending orders%n", cancelled);
```

### 4) Cancel all pending orders (all symbols)

```java
int cancelled = sugar.closeAllPending(null, null);

System.out.printf("✅ Cancelled %d pending orders across all symbols%n", cancelled);
```

---

## 📌 Important Notes

* **Pending orders only:** Does NOT close active positions
* **Order types:** BUY_LIMIT, SELL_LIMIT, BUY_STOP, SELL_STOP
* **vs closeAll():** closeAll() closes both positions AND pending orders
* **Use case:** When you want to cancel future entries but keep current trades

---

## See also

* **Close all (including positions):** [`closeAll()`](../5.%20Position_management/closeAll.md)
* **Close positions only:** [`closeAllPositions()`](./closeAllPositions.md)
* **Alias:** [`cancelAll()`](./cancelAll.md) - same functionality
