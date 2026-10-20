# ❌ Close All Positions (Excluding Pending Orders)

> **Advanced method:** closes only market positions (active trades), not pending orders. Separates position management from order management.

**API Information:**

* **Sugar method:** `MT5Sugar.closeAllPositions(String symbol, Boolean isBuy)`
* **Underlying methods:**
  - [`openedOrders()`](../../MT5Account/3.%20Positions_and_orders/OpenedOrders.md) - get all positions
  - [`closePosition()`](../5.%20Position_management/closePosition.md) - close each position
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type      | Required | Description                                |
| --------- | --------- | -------- | ------------------------------------------ |
| `symbol`  | `String`  | ⚪       | Symbol name (null = all symbols)           |
| `isBuy`   | `Boolean` | ⚪       | true=BUY only, false=SELL only, null=all   |

---

## ⬆️ Output

**Returns:** `int` - Number of positions successfully closed

**Throws:** `ApiExceptionMT5` if operation fails

**Execution:**
- Filters only PositionInfo entries (not OpenedOrderInfo)
- Ignores pending orders (BUY_LIMIT, SELL_LIMIT, BUY_STOP, SELL_STOP)
- Closes only active market positions

---

## 💬 Just the essentials

* **What it is.** Close active positions only, keep pending orders intact.
* **Why you need it.** Selective cleanup - exit trades but preserve entry orders.
* **Difference.** vs closeAll() - this skips pending orders.

---

## 🎯 Purpose

Use this method when you need to:

* Close active trades but keep pending orders.
* Exit current positions without canceling future entries.
* Separate position vs order management.

---

## 🔗 Usage Examples

### 1) Close all active positions, keep pending orders

```java
String symbol = "EURUSD";

int closed = sugar.closeAllPositions(symbol, null);

System.out.printf("✅ Closed %d active positions for %s%n", closed, symbol);
System.out.printf("   Pending orders remain untouched%n");
```

### 2) Close all BUY positions only

```java
int closed = sugar.closeAllPositions("GBPUSD", true);

System.out.printf("✅ Closed %d BUY positions%n", closed);
```

### 3) Close all SELL positions only

```java
int closed = sugar.closeAllPositions("USDJPY", false);

System.out.printf("✅ Closed %d SELL positions%n", closed);
```

---

## 📌 Important Notes

* **Positions only:** Does NOT close pending orders (LIMIT/STOP)
* **vs closeAll():** closeAll() closes both positions AND pending orders
* **Use case:** When you want to exit trades but keep future entry orders active

---

## See also

* **Close all (including orders):** [`closeAll()`](../5.%20Position_management/closeAll.md)
* **Cancel pending orders:** [`closeAllPending()`](./closeAllPending.md)
* **Single close:** [`closePosition()`](../5.%20Position_management/closePosition.md)
