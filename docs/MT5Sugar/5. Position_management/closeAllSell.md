# 🔴 Close All SELL Positions

> **Convenience method:** closes all SELL positions for specified symbol. Shortcut for `closeAll(symbol, false)`.

**API Information:**

* **Sugar method:** `MT5Sugar.closeAllSell(String symbol)`
* **Underlying methods:**
  - [`closeAll()`](./closeAll.md) - internally calls closeAll(symbol, false)
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                      |
| --------- | -------- | -------- | -------------------------------- |
| `symbol`  | `String` | ⚪       | Symbol name (null = all symbols) |

---

## ⬆️ Output

**Returns:** `int` - Number of SELL positions successfully closed

**Throws:** `ApiExceptionMT5` if operation fails

---

## 💬 Just the essentials

* **What it is.** Close all short positions for symbol (convenience alias).
* **Why you need it.** Simpler syntax than `closeAll(symbol, false)`.
* **Use case.** Exit all SELL positions quickly.

---

## 🔗 Usage Examples

### 1) Close all SELL for symbol

```java
String symbol = "EURUSD";

int closed = sugar.closeAllSell(symbol);

System.out.printf("✅ Closed %d SELL positions for %s%n", closed, symbol);
```

### 2) Close all SELL positions (all symbols)

```java
int closed = sugar.closeAllSell(null);

System.out.printf("✅ Closed %d SELL positions across all symbols%n", closed);
```

### 3) Report before closing

```java
String symbol = "USDJPY";

// Get SELL positions count
Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
);

int sellCount = (int) opened.getPositionInfosList().stream()
    .filter(p -> p.getSymbol().equals(symbol))
    .filter(p -> p.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_SELL)
    .count();

System.out.printf("Closing %d SELL positions for %s...%n", sellCount, symbol);

int closed = sugar.closeAllSell(symbol);

System.out.printf("✅ Successfully closed: %d%n", closed);
```

---

## 📌 Important Notes

* **Equivalent to:** `closeAll(symbol, false)`
* **Filters:** Only SELL positions, BUY positions ignored
* **Symbol:** Pass `null` to close all SELL positions across all symbols

---

## See also

* **Generic method:** [`closeAll()`](./closeAll.md)
* **BUY equivalent:** [`closeAllBuy()`](./closeAllBuy.md)
* **Single close:** [`closePosition()`](./closePosition.md)
