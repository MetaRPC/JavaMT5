# 🟢 Place BUY STOP Pending Order

> **Convenience method:** places a BUY STOP pending order above current price. Executes when price rises to the specified level (breakout trading). Simplified alternative to low-level OrderSend.

**API Information:**

* **Sugar method:** `MT5Sugar.buyStop(String symbol, double volume, double price, Double stopLoss, Double takeProfit)`
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
| `price`      | `double` | ✅       | Entry price (must be above current Ask)          |
| `stopLoss`   | `Double` | ✅       | Stop Loss price (null or 0 = no SL)              |
| `takeProfit` | `Double` | ✅       | Take Profit price (null or 0 = no TP)            |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number (pending order ticket)

**Throws:** `ApiExceptionMT5` if order fails (contains error code and description)

**Execution:**
- Order placed as pending (not executed immediately)
- Triggers when **Ask** price rises to specified price level
- Converts to market position upon activation
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Buy when price breaks above specified level (breakout entry).
* **Why you need it.** Enter BUY position on upward momentum/breakout.
* **Auto-handled.** Symbol selection, volume/price normalization, error checking.
* **SL/TP.** Optional - pass null or 0 to skip.

---

## 🎯 Purpose

Use this method when you need to:

* Place BUY order above current price (buy on breakout).
* Wait for price to rise before entering long position.
* Trade upward momentum and resistance breakouts.
* Enter BUY when price confirms bullish direction.

---

## 🔗 Usage Examples

### 1) Simple BUY STOP without SL/TP

```java
String symbol = "EURUSD";
double volume = 0.1; // 0.1 lot
double currentAsk = sugar.getAsk(symbol);
double entryPrice = currentAsk + 0.00050; // 50 points above Ask

// Place BUY STOP order
long ticket = sugar.buyStop(symbol, volume, entryPrice, null, null);

System.out.printf("BUY STOP order placed: #%d%n", ticket);
System.out.printf("Current Ask: %.5f%n", currentAsk);
System.out.printf("Entry price: %.5f (pending)%n", entryPrice);

// Output:
// BUY STOP order placed: #123456789
// Current Ask: 1.12340
// Entry price: 1.12390 (pending)
```

### 2) BUY STOP with Stop Loss and Take Profit

```java
String symbol = "GBPUSD";
double volume = 0.5;
double currentAsk = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// Entry 100 points above current price (breakout level)
double entryPrice = currentAsk + (100 * point);

// SL 50 points below entry
double stopLoss = entryPrice - (50 * point);

// TP 150 points above entry
double takeProfit = entryPrice + (150 * point);

long ticket = sugar.buyStop(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("BUY STOP %s: %.2f lots%n", symbol, volume);
System.out.printf("  Ticket: #%d%n", ticket);
System.out.printf("  Current Ask: %.5f%n", currentAsk);
System.out.printf("  Entry: %.5f (+100p breakout)%n", entryPrice);
System.out.printf("  SL: %.5f (-50p from entry)%n", stopLoss);
System.out.printf("  TP: %.5f (+150p from entry)%n", takeProfit);

// Output:
// BUY STOP GBPUSD: 0.50 lots
//   Ticket: #987654321
//   Current Ask: 1.26340
//   Entry: 1.26440 (+100p breakout)
//   SL: 1.26390 (-50p from entry)
//   TP: 1.26590 (+150p from entry)
```

### 3) BUY STOP at resistance breakout

```java
String symbol = "EURUSD";
double volume = 0.1;
double resistanceLevel = 1.12500; // Resistance to break
double point = sugar.getPoint(symbol);

// Place order slightly above resistance
double entryPrice = resistanceLevel + (10 * point); // 10p buffer
double stopLoss = resistanceLevel - (40 * point); // SL below resistance
double takeProfit = entryPrice + (200 * point); // TP 200 points above

long ticket = sugar.buyStop(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("BUY STOP on resistance breakout:%n");
System.out.printf("  Resistance: %.5f%n", resistanceLevel);
System.out.printf("  Entry: %.5f (10p above resistance)%n", entryPrice);
System.out.printf("  SL: %.5f (below resistance)%n", stopLoss);
System.out.printf("  TP: %.5f (200p above entry)%n", takeProfit);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 4) BUY STOP with error handling

```java
String symbol = "USDJPY";
double volume = 0.1;
double entryPrice = 149.500; // Desired breakout level

try {
    long ticket = sugar.buyStop(symbol, volume, entryPrice, null, null);

    System.out.printf("✅ BUY STOP order placed successfully%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Breakout level: %.3f%n", entryPrice);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Order failed:%n");
    System.err.printf("   Code: %d%n", e.getError().getMqlErrorTradeIntCode());
    System.err.printf("   Message: %s%n", e.getError().getErrorMessage());

    // Handle error (e.g., price too close to market, invalid price level)
}
```

### 5) BUY STOP breakout with trailing stop

```java
String symbol = "EURUSD";
double volume = 0.1;
double currentAsk = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// Entry on breakout
double breakoutLevel = 1.12500;
double entryPrice = breakoutLevel + (5 * point); // Small buffer

// Initial SL at recent swing low
double stopLoss = 1.12450;

// TP at next resistance
double takeProfit = 1.12700;

long ticket = sugar.buyStop(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("BUY STOP breakout setup:%n");
System.out.printf("  Entry: %.5f (breakout confirmation)%n", entryPrice);
System.out.printf("  SL: %.5f (swing low protection)%n", stopLoss);
System.out.printf("  TP: %.5f (next resistance)%n", takeProfit);
System.out.printf("  Risk/Reward: 1:%.1f%n",
    (takeProfit - entryPrice) / (entryPrice - stopLoss));
System.out.printf("  Ticket: #%d%n", ticket);
```

### 6) Multiple BUY STOP orders (breakout cascade)

```java
String symbol = "XAUUSD";
double volume = 0.01;
double currentAsk = sugar.getAsk(symbol);
double[] breakoutLevels = {2550.0, 2560.0, 2570.0};

System.out.printf("Placing BUY STOP cascade:%n");

for (int i = 0; i < breakoutLevels.length; i++) {
    double entryPrice = breakoutLevels[i];
    double stopLoss = entryPrice - 30.0;
    double takeProfit = entryPrice + 100.0;

    long ticket = sugar.buyStop(symbol, volume, entryPrice, stopLoss, takeProfit);

    System.out.printf("  Level %d: #%d at %.2f%n", i + 1, ticket, entryPrice);
}

// Output:
// Placing BUY STOP cascade:
//   Level 1: #111 at 2550.00
//   Level 2: #222 at 2560.00
//   Level 3: #333 at 2570.00
```

---

## 📌 Important Notes

* **Price placement:**
  - Entry price must be **above** current Ask
  - Order triggers when Ask rises to entry price
  - Invalid if entry price <= current Ask

* **Order activation:**
  - Remains pending until price reaches entry level
  - Converts to market position when triggered
  - Used for breakout and momentum trading

* **Stop Loss placement:**
  - Must be **below** entry price for BUY
  - Often placed below resistance level or swing low
  - Typical: `SL = entry - (points * point)`

* **Take Profit placement:**
  - Must be **above** entry price for BUY
  - Often at next resistance or projection level
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

**BUY STOP order structure:**
```
   ↑
   | TP: above entry (1.12590)
   |
Entry: (1.12390) — pending, waits for price to rise here
   |
   | SL: below entry (1.12340)
   ↓
Current Ask: (1.12290)
```

**Common patterns:**
```java
// Pattern 1: No SL/TP
sugar.buyStop(symbol, volume, price, null, null);

// Pattern 2: Only SL
sugar.buyStop(symbol, volume, price, stopLoss, null);

// Pattern 3: Only TP
sugar.buyStop(symbol, volume, price, null, takeProfit);

// Pattern 4: Both SL and TP
sugar.buyStop(symbol, volume, price, stopLoss, takeProfit);
```

**BUY STOP vs BUY LIMIT:**

| Aspect          | BUY STOP             | BUY LIMIT            |
|-----------------|----------------------|----------------------|
| Entry price     | Above current Ask    | Below current Ask    |
| Triggers when   | Price rises          | Price drops          |
| Use case        | Breakout trading     | Buy at discount      |
| Psychology      | Buy momentum up      | Buy at support       |
| Risk profile    | Higher entry price   | Lower entry price    |

---

## See also

* **Low-level method:** [`OrderSend`](../../MT5Account/5.%20Trading/OrderSend.md) - underlying implementation
* **Related:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md) - immediate BUY execution
* **Other pending:** [`buyLimit()`](./buyLimit.md), [`sellLimit()`](./sellLimit.md), [`sellStop()`](./sellStop.md)
* **Points-based:** [`buyStopPoints()`](../4.%20Pending_orders_points/buyStopPoints.md) - easier offset syntax
* **Position management:** [`closePosition()`](../5.%20Position_management/closePosition.md)
* **Price helpers:** [`getAsk()`](../1.%20Symbol_helpers/getAsk.md), [`normalizePrice()`](../1.%20Symbol_helpers/normalizePrice.md)
