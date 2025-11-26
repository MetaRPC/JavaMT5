# 🟢 Place BUY LIMIT Pending Order

> **Convenience method:** places a BUY LIMIT pending order below current price. Executes when price drops to the specified level. Simplified alternative to low-level OrderSend.

**API Information:**

* **Sugar method:** `MT5Sugar.buyLimit(String symbol, double volume, double price, Double stopLoss, Double takeProfit)`
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
| `price`      | `double` | ✅       | Entry price (must be below current Ask)          |
| `stopLoss`   | `Double` | ✅       | Stop Loss price (null or 0 = no SL)              |
| `takeProfit` | `Double` | ✅       | Take Profit price (null or 0 = no TP)            |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number (pending order ticket)

**Throws:** `ApiExceptionMT5` if order fails (contains error code and description)

**Execution:**
- Order placed as pending (not executed immediately)
- Triggers when **Ask** price drops to specified price level
- Converts to market position upon activation
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Buy when price drops to specified level (buy at lower price).
* **Why you need it.** Enter BUY position at better price (below current market).
* **Auto-handled.** Symbol selection, volume/price normalization, error checking.
* **SL/TP.** Optional - pass null or 0 to skip.

---

## 🎯 Purpose

Use this method when you need to:

* Place BUY order below current price (buy at discount).
* Wait for price to drop before entering long position.
* Set entry price in advance (not immediate execution).
* Buy at support level or after pullback.

---

## 🔗 Usage Examples

### 1) Simple BUY LIMIT without SL/TP

```java
String symbol = "EURUSD";
double volume = 0.1; // 0.1 lot
double currentAsk = sugar.getAsk(symbol);
double entryPrice = currentAsk - 0.00050; // 50 points below Ask

// Place BUY LIMIT order
long ticket = sugar.buyLimit(symbol, volume, entryPrice, null, null);

System.out.printf("BUY LIMIT order placed: #%d%n", ticket);
System.out.printf("Current Ask: %.5f%n", currentAsk);
System.out.printf("Entry price: %.5f (pending)%n", entryPrice);

// Output:
// BUY LIMIT order placed: #123456789
// Current Ask: 1.12340
// Entry price: 1.12290 (pending)
```

### 2) BUY LIMIT with Stop Loss and Take Profit

```java
String symbol = "GBPUSD";
double volume = 0.5;
double currentAsk = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// Entry 100 points below current price
double entryPrice = currentAsk - (100 * point);

// SL 50 points below entry
double stopLoss = entryPrice - (50 * point);

// TP 100 points above entry
double takeProfit = entryPrice + (100 * point);

long ticket = sugar.buyLimit(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("BUY LIMIT %s: %.2f lots%n", symbol, volume);
System.out.printf("  Ticket: #%d%n", ticket);
System.out.printf("  Current Ask: %.5f%n", currentAsk);
System.out.printf("  Entry: %.5f (-100p)%n", entryPrice);
System.out.printf("  SL: %.5f (-50p from entry)%n", stopLoss);
System.out.printf("  TP: %.5f (+100p from entry)%n", takeProfit);

// Output:
// BUY LIMIT GBPUSD: 0.50 lots
//   Ticket: #987654321
//   Current Ask: 1.26340
//   Entry: 1.26240 (-100p)
//   SL: 1.26190 (-50p from entry)
//   TP: 1.26340 (+100p from entry)
```

### 3) BUY LIMIT at support level

```java
String symbol = "EURUSD";
double volume = 0.1;
double supportLevel = 1.12000; // Support level
double point = sugar.getPoint(symbol);

// Place order at support
double entryPrice = supportLevel;
double stopLoss = supportLevel - (50 * point); // SL below support
double takeProfit = supportLevel + (150 * point); // TP 150 points above

long ticket = sugar.buyLimit(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("BUY LIMIT at support:%n");
System.out.printf("  Entry: %.5f (support level)%n", entryPrice);
System.out.printf("  SL: %.5f (50p below support)%n", stopLoss);
System.out.printf("  TP: %.5f (150p above entry)%n", takeProfit);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 4) BUY LIMIT with error handling

```java
String symbol = "USDJPY";
double volume = 0.1;
double entryPrice = 148.500; // Desired entry price

try {
    long ticket = sugar.buyLimit(symbol, volume, entryPrice, null, null);

    System.out.printf("✅ BUY LIMIT order placed successfully%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Entry price: %.3f%n", entryPrice);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Order failed:%n");
    System.err.printf("   Code: %d%n", e.getError().getMqlErrorTradeIntCode());
    System.err.printf("   Message: %s%n", e.getError().getErrorMessage());

    // Handle error (e.g., price too close to market, invalid price level)
}
```

### 5) Multiple BUY LIMIT orders (grid trading)

```java
String symbol = "EURUSD";
double volume = 0.01;
double currentAsk = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);
int gridStep = 50; // 50 points between orders
int gridLevels = 5;

System.out.printf("Placing BUY LIMIT grid below %.5f:%n", currentAsk);

for (int i = 1; i <= gridLevels; i++) {
    double entryPrice = currentAsk - (gridStep * i * point);
    double stopLoss = entryPrice - (50 * point);
    double takeProfit = entryPrice + (100 * point);

    long ticket = sugar.buyLimit(symbol, volume, entryPrice, stopLoss, takeProfit);

    System.out.printf("  Level %d: #%d at %.5f (%d points below)%n",
        i, ticket, entryPrice, gridStep * i);
}

// Output:
// Placing BUY LIMIT grid below 1.12340:
//   Level 1: #111 at 1.12290 (50 points below)
//   Level 2: #222 at 1.12240 (100 points below)
//   Level 3: #333 at 1.12190 (150 points below)
//   Level 4: #444 at 1.12140 (200 points below)
//   Level 5: #555 at 1.12090 (250 points below)
```

### 6) BUY LIMIT with price validation

```java
String symbol = "XAUUSD";
double volume = 0.01;
double desiredEntry = 2500.00;
double currentAsk = sugar.getAsk(symbol);

// Validate that entry price is below current Ask
if (desiredEntry >= currentAsk) {
    System.err.printf("❌ Invalid price: BUY LIMIT must be below Ask (%.2f)%n", currentAsk);
} else {
    double stopLoss = desiredEntry - 50.0;
    double takeProfit = desiredEntry + 100.0;

    long ticket = sugar.buyLimit(symbol, volume, desiredEntry, stopLoss, takeProfit);

    System.out.printf("✅ BUY LIMIT placed:%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Entry: %.2f (%.2f below Ask)%n", desiredEntry, currentAsk - desiredEntry);
}
```

---

## 📌 Important Notes

* **Price placement:**
  - Entry price must be **below** current Ask
  - Order triggers when Ask drops to entry price
  - Invalid if entry price >= current Ask

* **Order activation:**
  - Remains pending until price reaches entry level
  - Converts to market position when triggered
  - May not trigger if price doesn't reach level

* **Stop Loss placement:**
  - Must be **below** entry price for BUY
  - Typical: `SL = entry - (points * point)`

* **Take Profit placement:**
  - Must be **above** entry price for BUY
  - Typical: `TP = entry + (points * point)`

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

**BUY LIMIT order structure:**
```
   ↑
   | TP: above entry (1.12390)
   |
Entry: (1.12290) — pending, waits for price to drop here
   |
   | SL: below entry (1.12240)
   ↓
Current Ask: (1.12340)
```

**Common patterns:**
```java
// Pattern 1: No SL/TP
sugar.buyLimit(symbol, volume, price, null, null);

// Pattern 2: Only SL
sugar.buyLimit(symbol, volume, price, stopLoss, null);

// Pattern 3: Only TP
sugar.buyLimit(symbol, volume, price, null, takeProfit);

// Pattern 4: Both SL and TP
sugar.buyLimit(symbol, volume, price, stopLoss, takeProfit);
```

**BUY LIMIT vs BUY STOP:**

| Aspect          | BUY LIMIT            | BUY STOP             |
|-----------------|----------------------|----------------------|
| Entry price     | Below current Ask    | Above current Ask    |
| Triggers when   | Price drops          | Price rises          |
| Use case        | Buy at discount      | Breakout trading     |
| Psychology      | Buy at support       | Buy momentum         |

---

## See also

* **Low-level method:** [`OrderSend`](../../MT5Account/5.%20Trading/OrderSend.md) - underlying implementation
* **Related:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md) - immediate BUY execution
* **Other pending:** [`sellLimit()`](./sellLimit.md), [`buyStop()`](./buyStop.md), [`sellStop()`](./sellStop.md)
* **Points-based:** [`buyLimitPoints()`](../4.%20Pending_orders_points/buyLimitPoints.md) - easier offset syntax
* **Position management:** [`closePosition()`](../5.%20Position_management/closePosition.md)
* **Price helpers:** [`getAsk()`](../1.%20Symbol_helpers/getAsk.md), [`normalizePrice()`](../1.%20Symbol_helpers/normalizePrice.md)
