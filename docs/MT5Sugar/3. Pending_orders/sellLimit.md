# 🔴 Place SELL LIMIT Pending Order

> **Convenience method:** places a SELL LIMIT pending order above current price. Executes when price rises to the specified level. Simplified alternative to low-level OrderSend.

**API Information:**

* **Sugar method:** `MT5Sugar.sellLimit(String symbol, double volume, double price, Double stopLoss, Double takeProfit)`
* **Underlying methods:**

  - [`MT5Service.orderSend()`](../../MT5Account/5.%20Trading/OrderSend.md) - low-level order placement
  - [`ensureSymbolSelected()`](../1.%20Symbol_helpers/ensureSymbolSelected.md) - auto symbol selection
  - [`normalizeVolume()`](../1.%20Symbol_helpers/normalizeVolume.md) - auto volume normalization
  - [`normalizePrice()`](../1.%20Symbol_helpers/normalizePrice.md) - auto price normalization
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter    | Type     | Required | Description                                      |
| ------------ | -------- | -------- | ------------------------------------------------ |
| `symbol`     | `String` | ✅       | Symbol name (e.g., "EURUSD")                     |
| `volume`     | `double` | ✅       | Volume in lots (e.g., 0.1)                       |
| `price`      | `double` | ✅       | Entry price (must be above current Bid)          |
| `stopLoss`   | `Double` | ✅       | Stop Loss price (null or 0 = no SL)              |
| `takeProfit` | `Double` | ✅       | Take Profit price (null or 0 = no TP)            |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number (pending order ticket)

**Throws:** `ApiExceptionMT5` if order fails (contains error code and description)

**Execution:**
- Order placed as pending (not executed immediately)
- Triggers when **Bid** price rises to specified price level
- Converts to market position upon activation
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Sell when price rises to specified level (sell at higher price).
* **Why you need it.** Enter SELL position at better price (above current market).
* **Auto-handled.** Symbol selection, volume/price normalization, error checking.
* **SL/TP.** Optional - pass null or 0 to skip.

---

## 🎯 Purpose

Use this method when you need to:

* Place SELL order above current price (sell at premium).
* Wait for price to rise before entering short position.
* Set entry price in advance (not immediate execution).
* Sell at resistance level or after rally.

---

## 🔗 Usage Examples

### 1) Simple SELL LIMIT without SL/TP

```java
String symbol = "EURUSD";
double volume = 0.1; // 0.1 lot
double currentBid = sugar.getBid(symbol);
double entryPrice = currentBid + 0.00050; // 50 points above Bid

// Place SELL LIMIT order
long ticket = sugar.sellLimit(symbol, volume, entryPrice, null, null);

System.out.printf("SELL LIMIT order placed: #%d%n", ticket);
System.out.printf("Current Bid: %.5f%n", currentBid);
System.out.printf("Entry price: %.5f (pending)%n", entryPrice);

// Output:
// SELL LIMIT order placed: #123456789
// Current Bid: 1.12340
// Entry price: 1.12390 (pending)
```

### 2) SELL LIMIT with Stop Loss and Take Profit

```java
String symbol = "GBPUSD";
double volume = 0.5;
double currentBid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);

// Entry 100 points above current price
double entryPrice = currentBid + (100 * point);

// SL 50 points above entry
double stopLoss = entryPrice + (50 * point);

// TP 100 points below entry
double takeProfit = entryPrice - (100 * point);

long ticket = sugar.sellLimit(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("SELL LIMIT %s: %.2f lots%n", symbol, volume);
System.out.printf("  Ticket: #%d%n", ticket);
System.out.printf("  Current Bid: %.5f%n", currentBid);
System.out.printf("  Entry: %.5f (+100p)%n", entryPrice);
System.out.printf("  SL: %.5f (+50p from entry)%n", stopLoss);
System.out.printf("  TP: %.5f (-100p from entry)%n", takeProfit);

// Output:
// SELL LIMIT GBPUSD: 0.50 lots
//   Ticket: #987654321
//   Current Bid: 1.26340
//   Entry: 1.26440 (+100p)
//   SL: 1.26490 (+50p from entry)
//   TP: 1.26340 (-100p from entry)
```

### 3) SELL LIMIT at resistance level

```java
String symbol = "EURUSD";
double volume = 0.1;
double resistanceLevel = 1.12500; // Resistance level
double point = sugar.getPoint(symbol);

// Place order at resistance
double entryPrice = resistanceLevel;
double stopLoss = resistanceLevel + (50 * point); // SL above resistance
double takeProfit = resistanceLevel - (150 * point); // TP 150 points below

long ticket = sugar.sellLimit(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("SELL LIMIT at resistance:%n");
System.out.printf("  Entry: %.5f (resistance level)%n", entryPrice);
System.out.printf("  SL: %.5f (50p above resistance)%n", stopLoss);
System.out.printf("  TP: %.5f (150p below entry)%n", takeProfit);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 4) SELL LIMIT with error handling

```java
String symbol = "USDJPY";
double volume = 0.1;
double entryPrice = 149.500; // Desired entry price

try {
    long ticket = sugar.sellLimit(symbol, volume, entryPrice, null, null);

    System.out.printf("✅ SELL LIMIT order placed successfully%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Entry price: %.3f%n", entryPrice);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Order failed:%n");
    System.err.printf("   Code: %d%n", e.getError().getMqlErrorTradeIntCode());
    System.err.printf("   Message: %s%n", e.getError().getErrorMessage());

    // Handle error (e.g., price too close to market, invalid price level)
}
```

### 5) Multiple SELL LIMIT orders (grid trading)

```java
String symbol = "EURUSD";
double volume = 0.01;
double currentBid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);
int gridStep = 50; // 50 points between orders
int gridLevels = 5;

System.out.printf("Placing SELL LIMIT grid above %.5f:%n", currentBid);

for (int i = 1; i <= gridLevels; i++) {
    double entryPrice = currentBid + (gridStep * i * point);
    double stopLoss = entryPrice + (50 * point);
    double takeProfit = entryPrice - (100 * point);

    long ticket = sugar.sellLimit(symbol, volume, entryPrice, stopLoss, takeProfit);

    System.out.printf("  Level %d: #%d at %.5f (%d points above)%n",
        i, ticket, entryPrice, gridStep * i);
}

// Output:
// Placing SELL LIMIT grid above 1.12340:
//   Level 1: #111 at 1.12390 (50 points above)
//   Level 2: #222 at 1.12440 (100 points above)
//   Level 3: #333 at 1.12490 (150 points above)
//   Level 4: #444 at 1.12540 (200 points above)
//   Level 5: #555 at 1.12590 (250 points above)
```

### 6) SELL LIMIT with price validation

```java
String symbol = "XAUUSD";
double volume = 0.01;
double desiredEntry = 2600.00;
double currentBid = sugar.getBid(symbol);

// Validate that entry price is above current Bid
if (desiredEntry <= currentBid) {
    System.err.printf("❌ Invalid price: SELL LIMIT must be above Bid (%.2f)%n", currentBid);
} else {
    double stopLoss = desiredEntry + 50.0;
    double takeProfit = desiredEntry - 100.0;

    long ticket = sugar.sellLimit(symbol, volume, desiredEntry, stopLoss, takeProfit);

    System.out.printf("✅ SELL LIMIT placed:%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Entry: %.2f (%.2f above Bid)%n", desiredEntry, desiredEntry - currentBid);
}
```

---

## 📌 Important Notes

* **Price placement:**
  - Entry price must be **above** current Bid
  - Order triggers when Bid rises to entry price
  - Invalid if entry price <= current Bid

* **Order activation:**
  - Remains pending until price reaches entry level
  - Converts to market position when triggered
  - May not trigger if price doesn't reach level

* **Stop Loss placement:**
  - Must be **above** entry price for SELL
  - Typical: `SL = entry + (points * point)`

* **Take Profit placement:**
  - Must be **below** entry price for SELL
  - Typical: `TP = entry - (points * point)`

* **Auto-normalization:**
  - Volume normalized to symbol's min/max/step
  - Price normalized to symbol's digits
  - No manual normalization needed

* **Auto symbol selection:**
  - Method calls `ensureSymbolSelected()` internally
  - Symbol added to Market Watch if missing

* **Error handling:**
  - Throws `ApiExceptionMT5` if order fails
  - Common errors: invalid price level, invalid stops
  - Return code 10009 = success

* **null vs 0 for SL/TP:**
  - Both `null` and `0` mean "no SL/TP"
  - Internally converted to 0.0

**SELL LIMIT order structure:**
```
Current Bid: (1.12340)
   ↑
   | TP: below entry (1.12240)
   |
Entry: (1.12390) - pending, waits for price to rise here
   |
   | SL: above entry (1.12440)
   ↓
```

**Common patterns:**
```java
// Pattern 1: No SL/TP
sugar.sellLimit(symbol, volume, price, null, null);

// Pattern 2: Only SL
sugar.sellLimit(symbol, volume, price, stopLoss, null);

// Pattern 3: Only TP
sugar.sellLimit(symbol, volume, price, null, takeProfit);

// Pattern 4: Both SL and TP
sugar.sellLimit(symbol, volume, price, stopLoss, takeProfit);
```

**SELL LIMIT vs SELL STOP:**

| Aspect          | SELL LIMIT           | SELL STOP            |
|-----------------|----------------------|----------------------|
| Entry price     | Above current Bid    | Below current Bid    |
| Triggers when   | Price rises          | Price drops          |
| Use case        | Sell at premium      | Breakout trading     |
| Psychology      | Sell at resistance   | Sell momentum down   |

---

## See also

* **Low-level method:** [`OrderSend`](../../MT5Account/5.%20Trading/OrderSend.md) - underlying implementation
* **Related:** [`sellMarket()`](../2.%20Market_orders/sellMarket.md) - immediate SELL execution
* **Other pending:** [`buyLimit()`](./buyLimit.md), [`buyStop()`](./buyStop.md), [`sellStop()`](./sellStop.md)
* **Points-based:** [`sellLimitPoints()`](../4.%20Pending_orders_points/sellLimitPoints.md) - easier offset syntax
* **Position management:** [`closePosition()`](../5.%20Position_management/closePosition.md)
* **Price helpers:** [`getBid()`](../1.%20Symbol_helpers/getBid.md), [`normalizePrice()`](../1.%20Symbol_helpers/normalizePrice.md)
