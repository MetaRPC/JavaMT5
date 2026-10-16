# ❌ Close All Positions

> **Convenience method:** closes all open positions for specified symbol and/or direction. Batch operation for mass position closing.

**API Information:**

* **Sugar method (all positions):** `MT5Sugar.closeAll(String symbol)`
* **Sugar method (by direction):** `MT5Sugar.closeAll(String symbol, Boolean isBuy)`
* **Underlying methods:**
  - [`openedOrders()`](../../MT5Account/3.%20Positions_and_orders/OpenedOrders.md) - get all positions
  - [`closePosition()`](./closePosition.md) - close each position
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type      | Required | Description                                      |
| --------- | --------- | -------- | ------------------------------------------------ |
| `symbol`  | `String`  | ⚪       | Symbol name (null = all symbols)                 |
| `isBuy`   | `Boolean` | ⚪       | true=BUY only, false=SELL only, null=all         |

---

## ⬆️ Output

**Returns:** `int` - Number of positions successfully closed

**Throws:** `ApiExceptionMT5` if operation fails

**Execution:**
- Iterates through all open positions
- Filters by symbol and/or direction
- Closes matching positions individually
- Continues even if some closes fail

---

## 💬 Just the essentials

* **What it is.** Close multiple positions at once with filters.
* **Why you need it.** Emergency exit, end-of-day cleanup, symbol-specific exit.
* **Filters.** By symbol, by direction (BUY/SELL), or both.
* **Returns.** Count of successfully closed positions.

---

## 🎯 Purpose

Use this method when you need to:

* Close all positions for specific symbol.
* Close all BUY or all SELL positions.
* Emergency exit (close everything).
* End-of-trading-day cleanup.

---

## 🔗 Usage Examples

### 1) Close all positions for symbol

```java
String symbol = "EURUSD";

int closed = sugar.closeAll(symbol);

System.out.printf("✅ Closed %d positions for %s%n", closed, symbol);
```

### 2) Close all positions (all symbols)

```java
int closed = sugar.closeAll(null);

System.out.printf("✅ Emergency exit: closed %d positions%n", closed);
```

### 3) Close all BUY positions for symbol

```java
String symbol = "GBPUSD";

int closed = sugar.closeAll(symbol, true); // true = BUY only

System.out.printf("✅ Closed %d BUY positions for %s%n", closed, symbol);
```

### 4) Close all SELL positions for symbol

```java
String symbol = "USDJPY";

int closed = sugar.closeAll(symbol, false); // false = SELL only

System.out.printf("✅ Closed %d SELL positions for %s%n", closed, symbol);
```

### 5) Close all BUY positions (all symbols)

```java
int closed = sugar.closeAll(null, true);

System.out.printf("✅ Closed %d BUY positions across all symbols%n", closed);
```

### 6) Close all SELL positions (all symbols)

```java
int closed = sugar.closeAll(null, false);

System.out.printf("✅ Closed %d SELL positions across all symbols%n", closed);
```

### 7) End-of-day cleanup with reporting

```java
System.out.println("📊 End-of-day cleanup:");

// Close all positions per symbol
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};
int totalClosed = 0;

for (String symbol : symbols) {
    int closed = sugar.closeAll(symbol);
    if (closed > 0) {
        System.out.printf("  %s: %d positions closed%n", symbol, closed);
        totalClosed += closed;
    }
}

System.out.printf("✅ Total: %d positions closed%n", totalClosed);
```

### 8) Conditional close based on market conditions

```java
String symbol = "XAUUSD";
double currentPrice = sugar.getBid(symbol);
double closeThreshold = 2500.0;

if (currentPrice < closeThreshold) {
    System.out.printf("⚠️ Price below threshold (%.2f < %.2f)%n", currentPrice, closeThreshold);

    int closed = sugar.closeAll(symbol);

    System.out.printf("✅ Emergency exit: %d positions closed%n", closed);
}
```

---

## 📌 Important Notes

* **Symbol parameter:**
  - Specific symbol (e.g., "EURUSD") = close only that symbol
  - `null` = close all symbols

* **Direction parameter:**
  - `true` = close only BUY positions
  - `false` = close only SELL positions
  - `null` = close all directions

* **Error handling:**
  - Continues closing even if some positions fail
  - Failed closes are logged to System.err
  - Returns count of SUCCESSFUL closes only

* **Return value:**
  - Returns number of positions successfully closed
  - `0` = no positions matched filters
  - Check return value to verify action

* **Execution order:**
  - Positions closed in order by open time (ascending)
  - Oldest positions closed first
  - No rollback if partial failure

**Common patterns:**
```java
// Pattern 1: Close all for symbol
sugar.closeAll("EURUSD");

// Pattern 2: Close all (emergency)
sugar.closeAll(null);

// Pattern 3: Close all BUY for symbol
sugar.closeAll("GBPUSD", true);

// Pattern 4: Close all SELL for symbol
sugar.closeAll("USDJPY", false);

// Pattern 5: Close all BUY (all symbols)
sugar.closeAll(null, true);

// Pattern 6: Close all SELL (all symbols)
sugar.closeAll(null, false);
```

**Filter combinations:**
```java
// Symbol + Direction filters:
closeAll("EURUSD", true)   → EURUSD BUY only
closeAll("EURUSD", false)  → EURUSD SELL only
closeAll("EURUSD", null)   → EURUSD all directions

// All symbols + Direction filters:
closeAll(null, true)   → All BUY positions
closeAll(null, false)  → All SELL positions
closeAll(null, null)   → All positions (everything)
```

---

## See also

* **Single close:** [`closePosition()`](./closePosition.md) - close one position
* **Direction-specific:** [`closeAllBuy()`](./closeAllBuy.md), [`closeAllSell()`](./closeAllSell.md) - convenience methods
* **Advanced batch:** [`closeAllPositions()`](../6.%20Advanced_batch_operations/closeAllPositions.md) - positions only (not orders)
* **Get positions:** [`openedOrders()`](../../MT5Account/3.%20Positions_and_orders/OpenedOrders.md)
