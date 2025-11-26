# ⚙️ Modify Position (Change SL/TP)

> **Convenience method:** modifies Stop Loss and/or Take Profit of an existing position. Allows changing risk parameters without closing the position.

**API Information:**

* **Sugar method:** `MT5Sugar.modifyPosition(long ticket, Double stopLoss, Double takeProfit)`
* **Underlying methods:**
  - [`MT5Service.orderModify()`](../../MT5Account/5.%20Trading/OrderModify.md) - low-level order modification
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter    | Type     | Required | Description                                      |
| ------------ | -------- | -------- | ------------------------------------------------ |
| `ticket`     | `long`   | ✅       | Position ticket number                           |
| `stopLoss`   | `Double` | ⚪       | New Stop Loss price (null = keep current)        |
| `takeProfit` | `Double` | ⚪       | New Take Profit price (null = keep current)      |

---

## ⬆️ Output

**Returns:** `void`

**Throws:**
- `ApiExceptionMT5` if modification fails (contains error code and description)
- `IllegalArgumentException` if both stopLoss and takeProfit are null

**Execution:**
- Modifies existing position's SL and/or TP
- At least one parameter must be provided
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Change SL/TP of open position without closing it.
* **Why you need it.** Trail stops, adjust targets, manage risk dynamically.
* **Use case.** Breakeven stops, trailing stops, partial TP adjustments.

---

## 🎯 Purpose

Use this method when you need to:

* Move Stop Loss to breakeven after profit.
* Trail Stop Loss as price moves in your favor.
* Adjust Take Profit based on market conditions.
* Update risk parameters without re-entering position.

---

## 🔗 Usage Examples

### 1) Move Stop Loss to breakeven

```java
long ticket = 123456789; // Existing BUY position
String symbol = "EURUSD";

// Get position entry price
Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
);

for (Mt5TermApiAccountHelper.PositionInfo pos : opened.getPositionInfosList()) {
    if (pos.getTicket() == ticket) {
        double entryPrice = pos.getOpenPrice();
        double currentPrice = sugar.getBid(symbol);

        // If in profit by 50 points, move SL to breakeven
        double point = sugar.getPoint(symbol);
        if (currentPrice > entryPrice + (50 * point)) {
            sugar.modifyPosition(ticket, entryPrice, null);
            System.out.printf("✅ SL moved to breakeven: %.5f%n", entryPrice);
        }
        break;
    }
}
```

### 2) Simple trailing stop

```java
long ticket = 987654321; // Existing SELL position
String symbol = "GBPUSD";
double point = sugar.getPoint(symbol);
int trailingPoints = 50; // Trail by 50 points

// Get current position data
Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
);

for (Mt5TermApiAccountHelper.PositionInfo pos : opened.getPositionInfosList()) {
    if (pos.getTicket() == ticket) {
        double currentBid = sugar.getBid(symbol);
        double currentSL = pos.getStopLoss();

        // Calculate new SL (50 points from current price)
        double newSL = currentBid + (trailingPoints * point);

        // Only trail if new SL is better (lower for SELL)
        if (currentSL == 0 || newSL < currentSL) {
            sugar.modifyPosition(ticket, newSL, null);
            System.out.printf("✅ SL trailed to %.5f%n", newSL);
        }
        break;
    }
}
```

### 3) Adjust Take Profit based on indicator

```java
long ticket = 111222333;
String symbol = "EURUSD";
double point = sugar.getPoint(symbol);

// New TP based on support/resistance level
double newResistance = 1.13000;

try {
    sugar.modifyPosition(ticket, null, newResistance);

    System.out.printf("✅ TP adjusted:%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   New TP: %.5f%n", newResistance);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Modification failed: %s%n", e.getError().getErrorMessage());
}
```

### 4) Modify both SL and TP

```java
long ticket = 444555666;
String symbol = "USDJPY";
double currentAsk = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// New SL and TP
double newSL = currentAsk - (100 * point);
double newTP = currentAsk + (200 * point);

sugar.modifyPosition(ticket, newSL, newTP);

System.out.printf("✅ Position modified:%n");
System.out.printf("   Ticket: #%d%n", ticket);
System.out.printf("   New SL: %.3f%n", newSL);
System.out.printf("   New TP: %.3f%n", newTP);
```

### 5) Partial profit protection (move SL to lock profit)

```java
long ticket = 777888999;
String symbol = "XAUUSD";

// Get position info
Mt5TermApiAccountHelper.OpenedOrdersData opened = service.openedOrders(
    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
);

for (Mt5TermApiAccountHelper.PositionInfo pos : opened.getPositionInfosList()) {
    if (pos.getTicket() == ticket) {
        double entryPrice = pos.getOpenPrice();
        double currentProfit = pos.getProfit();

        // If profit > $100, lock in half of profit
        if (currentProfit > 100.0) {
            double currentPrice = sugar.getAsk(symbol);
            double profitDistance = Math.abs(currentPrice - entryPrice);

            // Move SL to lock 50% of profit
            double newSL = entryPrice + (profitDistance * 0.5);

            sugar.modifyPosition(ticket, newSL, null);

            System.out.printf("✅ Profit protected:%n");
            System.out.printf("   Current profit: $%.2f%n", currentProfit);
            System.out.printf("   SL moved to: %.2f (locking profit)%n", newSL);
        }
        break;
    }
}
```

### 6) Remove Stop Loss (set to 0)

```java
long ticket = 123987456;

// Remove SL, keep TP
sugar.modifyPosition(ticket, 0.0, null);

System.out.printf("✅ Stop Loss removed from position #%d%n", ticket);
System.out.printf("   Warning: Position now has no SL protection!%n");
```

---

## 📌 Important Notes

* **Parameter requirements:**
  - At least ONE of (stopLoss, takeProfit) must be non-null
  - Pass `null` to keep current value unchanged
  - Pass `0.0` to remove SL or TP

* **SL/TP validation:**
  - MT5 validates new levels against minimum distance
  - Too close to current price will fail
  - Check broker's SYMBOL_TRADE_STOPS_LEVEL

* **Modification timing:**
  - Can modify while position is open
  - Cannot modify during order execution
  - Fast markets may reject modifications

* **Error handling:**
  - Throws `ApiExceptionMT5` on failure
  - Common errors: invalid stops, market closed
  - Return code 10009 = success

* **null vs 0.0:**
  - `null` = keep current value (don't change)
  - `0.0` = remove SL/TP (set to none)
  - Both parameters null = throws IllegalArgumentException

**Common patterns:**
```java
// Pattern 1: Modify only SL
sugar.modifyPosition(ticket, newSL, null);

// Pattern 2: Modify only TP
sugar.modifyPosition(ticket, null, newTP);

// Pattern 3: Modify both
sugar.modifyPosition(ticket, newSL, newTP);

// Pattern 4: Remove SL
sugar.modifyPosition(ticket, 0.0, null);

// Pattern 5: Remove TP
sugar.modifyPosition(ticket, null, 0.0);
```

**Trailing stop logic:**
```java
// BUY position trailing
double currentPrice = sugar.getBid(symbol);
double newSL = currentPrice - (trailingPoints * point);
if (currentSL == 0 || newSL > currentSL) {
    sugar.modifyPosition(ticket, newSL, null);
}

// SELL position trailing
double currentPrice = sugar.getBid(symbol);
double newSL = currentPrice + (trailingPoints * point);
if (currentSL == 0 || newSL < currentSL) {
    sugar.modifyPosition(ticket, newSL, null);
}
```

---

## See also

* **Low-level method:** [`OrderModify`](../../MT5Account/5.%20Trading/OrderModify.md) - underlying implementation
* **Close position:** [`closePosition()`](./closePosition.md) - close instead of modify
* **Get position info:** [`openedOrders()`](../../MT5Account/3.%20Positions_and_orders/OpenedOrders.md)
* **Price helpers:** [`getBid()`](../1.%20Symbol_helpers/getBid.md), [`getAsk()`](../1.%20Symbol_helpers/getAsk.md), [`getPoint()`](../1.%20Symbol_helpers/getPoint.md)
