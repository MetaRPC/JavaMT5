# 🗑️ Cancel All Pending Orders (Alias)

> **Alias method:** cancels all pending orders. Identical to `closeAllPending()` - provided for semantic clarity.

**API Information:**

* **Sugar method:** `MT5Sugar.cancelAll(String symbol, Boolean isBuy)`
* **Underlying methods:**
  - [`closeAllPending()`](./closeAllPending.md) - internally calls closeAllPending()
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

---

## 💬 Just the essentials

* **What it is.** Semantic alias for closeAllPending() - "cancel" is clearer than "close" for orders.
* **Identical to:** `closeAllPending(symbol, isBuy)`
* **Use case.** When you want clearer naming (cancel orders vs close positions).

---

## 🔗 Usage Examples

### 1) Cancel all pending orders

```java
int cancelled = sugar.cancelAll("EURUSD", null);

System.out.printf("✅ Cancelled %d pending orders%n", cancelled);
```

---

## See also

* **Actual implementation:** [`closeAllPending()`](./closeAllPending.md)
* **Close positions:** [`closeAllPositions()`](./closeAllPositions.md)
