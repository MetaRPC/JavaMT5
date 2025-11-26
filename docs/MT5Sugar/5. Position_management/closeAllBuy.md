# 🟢 Close All BUY Positions

> **Convenience method:** closes all BUY positions for specified symbol. Shortcut for `closeAll(symbol, true)`.

**API Information:**

* **Sugar method:** `MT5Sugar.closeAllBuy(String symbol)`
* **Underlying methods:**
  - [`closeAll()`](./closeAll.md) - internally calls closeAll(symbol, true)
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                      |
| --------- | -------- | -------- | -------------------------------- |
| `symbol`  | `String` | ⚪       | Symbol name (null = all symbols) |

---

## ⬆️ Output

**Returns:** `int` - Number of BUY positions successfully closed

**Throws:** `ApiExceptionMT5` if operation fails

---

## 💬 Just the essentials

* **What it is.** Close all long positions for symbol (convenience alias).
* **Why you need it.** Simpler syntax than `closeAll(symbol, true)`.
* **Use case.** Exit all BUY positions quickly.

---

## 🔗 Usage Examples

### 1) Close all BUY for symbol

```java
String symbol = "EURUSD";

int closed = sugar.closeAllBuy(symbol);

System.out.printf("✅ Closed %d BUY positions for %s%n", closed, symbol);
```

### 2) Close all BUY positions (all symbols)

```java
int closed = sugar.closeAllBuy(null);

System.out.printf("✅ Closed %d BUY positions across all symbols%n", closed);
```

### 3) Report before closing

```java
String symbol = "GBPUSD";

// Get BUY positions count
Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
);

int buyCount = (int) opened.getPositionInfosList().stream()
    .filter(p -> p.getSymbol().equals(symbol))
    .filter(p -> p.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY)
    .count();

System.out.printf("Closing %d BUY positions for %s...%n", buyCount, symbol);

int closed = sugar.closeAllBuy(symbol);

System.out.printf("✅ Successfully closed: %d%n", closed);
```

---

## 📌 Important Notes

* **Equivalent to:** `closeAll(symbol, true)`
* **Filters:** Only BUY positions, SELL positions ignored
* **Symbol:** Pass `null` to close all BUY positions across all symbols

---

## See also

* **Generic method:** [`closeAll()`](./closeAll.md)
* **SELL equivalent:** [`closeAllSell()`](./closeAllSell.md)
* **Single close:** [`closePosition()`](./closePosition.md)
