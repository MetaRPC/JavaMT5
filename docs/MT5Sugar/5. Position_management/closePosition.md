# ❌ Close Position

> **Convenience method:** closes an existing position by ticket number. Supports both full and partial closes. Simplified alternative to low-level OrderClose.

**API Information:**

* **Sugar method (full close):** `MT5Sugar.closePosition(long ticket)`
* **Sugar method (partial close):** `MT5Sugar.closePosition(long ticket, Double volume)`
* **Underlying methods:**
  - [`MT5Service.orderClose()`](../../MT5Account/5.%20Trading/OrderClose.md) - low-level order close
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                                      |
| --------- | -------- | -------- | ------------------------------------------------ |
| `ticket`  | `long`   | ✅       | Position ticket number to close                  |
| `volume`  | `Double` | ⚪       | Volume to close (null or 0 = close all)          |

---

## ⬆️ Output

**Returns:** `void`

**Throws:** `ApiExceptionMT5` if close fails (contains error code and description)

**Execution:**
- Closes position at current market price
- BUY positions close at Bid price
- SELL positions close at Ask price
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Close open position instantly at market price.
* **Why you need it.** Exit trades, take profit, cut losses manually.
* **Auto-handled.** Market execution, slippage handling, error checking.
* **Partial close.** Can close part of position (volume parameter).

---

## 🎯 Purpose

Use this method when you need to:

* Close entire position immediately.
* Partially close position (scale out).
* Exit losing trades manually.
* Take profit at specific conditions.

---

## 🔗 Usage Examples

### 1) Simple full position close

```java
long ticket = 123456789;

sugar.closePosition(ticket);

System.out.printf("✅ Position #%d closed%n", ticket);
```

### 2) Partial position close (scale out)

```java
long ticket = 987654321;
double volume = 0.05; // Close 0.05 lots

sugar.closePosition(ticket, volume);

System.out.printf("✅ Partial close: %.2f lots of position #%d%n", volume, ticket);
```

### 3) Close position with error handling

```java
long ticket = 111222333;

try {
    sugar.closePosition(ticket);
    System.out.printf("✅ Position #%d closed successfully%n", ticket);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Close failed:%n");
    System.err.printf("   Code: %d%n", e.getError().getMqlErrorTradeIntCode());
    System.err.printf("   Message: %s%n", e.getError().getErrorMessage());
}
```

### 4) Close position at profit target

```java
String symbol = "EURUSD";
long ticket = 444555666;
double targetProfit = 100.0; // $100 profit

// Monitor position
Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
);

for (Mt5TermApiAccountHelper.PositionInfo pos : opened.getPositionInfosList()) {
    if (pos.getTicket() == ticket && pos.getProfit() >= targetProfit) {
        sugar.closePosition(ticket);
        System.out.printf("✅ Position closed at profit: $%.2f%n", pos.getProfit());
        break;
    }
}
```

### 5) Scale out strategy (close in stages)

```java
long ticket = 777888999;
double totalVolume = 0.10; // Total position size
double[] closeStages = {0.03, 0.03, 0.04}; // Close in 3 stages

System.out.printf("Scaling out of position #%d:%n", ticket);

for (int i = 0; i < closeStages.length; i++) {
    sugar.closePosition(ticket, closeStages[i]);
    System.out.printf("  Stage %d: Closed %.2f lots%n", i + 1, closeStages[i]);

    // Wait between closes (optional)
    Thread.sleep(1000);
}

System.out.printf("✅ Position fully scaled out%n");
```

### 6) Close with profit/loss reporting

```java
long ticket = 123987456;
String symbol = "GBPUSD";

// Get position info before closing
Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
);

for (Mt5TermApiAccountHelper.PositionInfo pos : opened.getPositionInfosList()) {
    if (pos.getTicket() == ticket) {
        double profit = pos.getProfit();
        double entryPrice = pos.getOpenPrice();
        double volume = pos.getVolume();

        // Close position
        sugar.closePosition(ticket);

        // Report
        System.out.printf("Position #%d closed:%n", ticket);
        System.out.printf("  Symbol: %s%n", symbol);
        System.out.printf("  Volume: %.2f lots%n", volume);
        System.out.printf("  Entry: %.5f%n", entryPrice);
        System.out.printf("  Profit/Loss: $%.2f%n", profit);
        break;
    }
}
```

---

## 📌 Important Notes

* **Execution:**
  - Closes at current market price immediately
  - BUY closes at Bid (sell to close long)
  - SELL closes at Ask (buy to close short)
  - Slippage may occur in fast markets

* **Full vs Partial:**
  - `closePosition(ticket)` - closes entire position
  - `closePosition(ticket, volume)` - closes specified volume
  - Pass `null` or `0.0` for volume = full close
  - Partial close leaves remaining position open

* **Volume validation:**
  - Volume must be <= position size
  - Volume normalized automatically
  - Cannot close more than available

* **Error handling:**
  - Throws `ApiExceptionMT5` on failure
  - Common errors: invalid ticket, market closed, insufficient volume
  - Return code 10009 = success

* **After close:**
  - Position ticket becomes invalid
  - Cannot modify closed position
  - Check OrderHistory for closed positions

**Common patterns:**
```java
// Pattern 1: Full close
sugar.closePosition(ticket);

// Pattern 2: Partial close (50%)
sugar.closePosition(ticket, totalVolume * 0.5);

// Pattern 3: Close specific amount
sugar.closePosition(ticket, 0.1);

// Pattern 4: Full close (explicit)
sugar.closePosition(ticket, null);
sugar.closePosition(ticket, 0.0);
```

**Slippage consideration:**
```java
// Actual close price may differ from expected
double expectedBid = sugar.getBid(symbol);
sugar.closePosition(ticket);

// Check actual close price in history
// Slippage = actualClosePrice - expectedBid
```

---

## See also

* **Low-level method:** [`OrderClose`](../../MT5Account/5.%20Trading/OrderClose.md) - underlying implementation
* **Batch close:** [`closeAll()`](./closeAll.md) - close multiple positions
* **Direction close:** [`closeAllBuy()`](./closeAllBuy.md), [`closeAllSell()`](./closeAllSell.md)
* **Modify instead:** [`modifyPosition()`](./modifyPosition.md) - change SL/TP without closing
* **Get positions:** [`openedOrders()`](../../MT5Account/3.%20Positions_and_orders/OpenedOrders.md)
