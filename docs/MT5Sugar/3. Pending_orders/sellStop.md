# 🔴 Place SELL STOP Pending Order

> **Convenience method:** places a SELL STOP pending order below current price. Executes when price drops to the specified level (breakdown trading). Simplified alternative to low-level OrderSend.

**API Information:**

* **Sugar method:** `MT5Sugar.sellStop(String symbol, double volume, double price, Double stopLoss, Double takeProfit)`
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
| `price`      | `double` | ✅       | Entry price (must be below current Bid)          |
| `stopLoss`   | `Double` | ✅       | Stop Loss price (null or 0 = no SL)              |
| `takeProfit` | `Double` | ✅       | Take Profit price (null or 0 = no TP)            |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number (pending order ticket)

**Throws:** `ApiExceptionMT5` if order fails (contains error code and description)

**Execution:**
- Order placed as pending (not executed immediately)
- Triggers when **Bid** price drops to specified price level
- Converts to market position upon activation
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Sell when price breaks below specified level (breakdown entry).
* **Why you need it.** Enter SELL position on downward momentum/breakdown.
* **Auto-handled.** Symbol selection, volume/price normalization, error checking.
* **SL/TP.** Optional - pass null or 0 to skip.

---

## 🎯 Purpose

Use this method when you need to:

* Place SELL order below current price (sell on breakdown).
* Wait for price to drop before entering short position.
* Trade downward momentum and support breakdowns.
* Enter SELL when price confirms bearish direction.

---

## 🔗 Usage Examples

### 1) Simple SELL STOP without SL/TP

```java
String symbol = "EURUSD";
double volume = 0.1; // 0.1 lot
double currentBid = sugar.getBid(symbol);
double entryPrice = currentBid - 0.00050; // 50 points below Bid

// Place SELL STOP order
long ticket = sugar.sellStop(symbol, volume, entryPrice, null, null);

System.out.printf("SELL STOP order placed: #%d%n", ticket);
System.out.printf("Current Bid: %.5f%n", currentBid);
System.out.printf("Entry price: %.5f (pending)%n", entryPrice);

// Output:
// SELL STOP order placed: #123456789
// Current Bid: 1.12340
// Entry price: 1.12290 (pending)
```

### 2) SELL STOP with Stop Loss and Take Profit

```java
String symbol = "GBPUSD";
double volume = 0.5;
double currentBid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);

// Entry 100 points below current price (breakdown level)
double entryPrice = currentBid - (100 * point);

// SL 50 points above entry
double stopLoss = entryPrice + (50 * point);

// TP 150 points below entry
double takeProfit = entryPrice - (150 * point);

long ticket = sugar.sellStop(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("SELL STOP %s: %.2f lots%n", symbol, volume);
System.out.printf("  Ticket: #%d%n", ticket);
System.out.printf("  Current Bid: %.5f%n", currentBid);
System.out.printf("  Entry: %.5f (-100p breakdown)%n", entryPrice);
System.out.printf("  SL: %.5f (+50p from entry)%n", stopLoss);
System.out.printf("  TP: %.5f (-150p from entry)%n", takeProfit);

// Output:
// SELL STOP GBPUSD: 0.50 lots
//   Ticket: #987654321
//   Current Bid: 1.26340
//   Entry: 1.26240 (-100p breakdown)
//   SL: 1.26290 (+50p from entry)
//   TP: 1.26090 (-150p from entry)
```

### 3) SELL STOP at support breakdown

```java
String symbol = "EURUSD";
double volume = 0.1;
double supportLevel = 1.12000; // Support to break
double point = sugar.getPoint(symbol);

// Place order slightly below support
double entryPrice = supportLevel - (10 * point); // 10p buffer
double stopLoss = supportLevel + (40 * point); // SL above support
double takeProfit = entryPrice - (200 * point); // TP 200 points below

long ticket = sugar.sellStop(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("SELL STOP on support breakdown:%n");
System.out.printf("  Support: %.5f%n", supportLevel);
System.out.printf("  Entry: %.5f (10p below support)%n", entryPrice);
System.out.printf("  SL: %.5f (above support)%n", stopLoss);
System.out.printf("  TP: %.5f (200p below entry)%n", takeProfit);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 4) SELL STOP with error handling

```java
String symbol = "USDJPY";
double volume = 0.1;
double entryPrice = 148.000; // Desired breakdown level

try {
    long ticket = sugar.sellStop(symbol, volume, entryPrice, null, null);

    System.out.printf("✅ SELL STOP order placed successfully%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Breakdown level: %.3f%n", entryPrice);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Order failed:%n");
    System.err.printf("   Code: %d%n", e.getError().getMqlErrorTradeIntCode());
    System.err.printf("   Message: %s%n", e.getError().getErrorMessage());

    // Handle error (e.g., price too close to market, invalid price level)
}
```

### 5) SELL STOP breakdown with tight SL

```java
String symbol = "EURUSD";
double volume = 0.1;
double currentBid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);

// Entry on breakdown
double breakdownLevel = 1.12000;
double entryPrice = breakdownLevel - (5 * point); // Small buffer

// Initial SL at recent swing high
double stopLoss = 1.12050;

// TP at next support
double takeProfit = 1.11800;

long ticket = sugar.sellStop(symbol, volume, entryPrice, stopLoss, takeProfit);

System.out.printf("SELL STOP breakdown setup:%n");
System.out.printf("  Entry: %.5f (breakdown confirmation)%n", entryPrice);
System.out.printf("  SL: %.5f (swing high protection)%n", stopLoss);
System.out.printf("  TP: %.5f (next support)%n", takeProfit);
System.out.printf("  Risk/Reward: 1:%.1f%n",
    (entryPrice - takeProfit) / (stopLoss - entryPrice));
System.out.printf("  Ticket: #%d%n", ticket);
```

### 6) Multiple SELL STOP orders (breakdown cascade)

```java
String symbol = "XAUUSD";
double volume = 0.01;
double currentBid = sugar.getBid(symbol);
double[] breakdownLevels = {2500.0, 2490.0, 2480.0};

System.out.printf("Placing SELL STOP cascade:%n");

for (int i = 0; i < breakdownLevels.length; i++) {
    double entryPrice = breakdownLevels[i];
    double stopLoss = entryPrice + 30.0;
    double takeProfit = entryPrice - 100.0;

    long ticket = sugar.sellStop(symbol, volume, entryPrice, stopLoss, takeProfit);

    System.out.printf("  Level %d: #%d at %.2f%n", i + 1, ticket, entryPrice);
}

// Output:
// Placing SELL STOP cascade:
//   Level 1: #111 at 2500.00
//   Level 2: #222 at 2490.00
//   Level 3: #333 at 2480.00
```

---

## 📌 Important Notes

* **Price placement:**
  - Entry price must be **below** current Bid
  - Order triggers when Bid drops to entry price
  - Invalid if entry price >= current Bid

* **Order activation:**
  - Remains pending until price reaches entry level
  - Converts to market position when triggered
  - Used for breakdown and momentum trading

* **Stop Loss placement:**
  - Must be **above** entry price for SELL
  - Often placed above support level or swing high
  - Typical: `SL = entry + (points * point)`

* **Take Profit placement:**
  - Must be **below** entry price for SELL
  - Often at next support or projection level
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

**SELL STOP order structure:**
```
Current Bid: (1.12390)
   ↑
   | SL: above entry (1.12340)
   |
Entry: (1.12290) — pending, waits for price to drop here
   |
   | TP: below entry (1.12090)
   ↓
```

**Common patterns:**
```java
// Pattern 1: No SL/TP
sugar.sellStop(symbol, volume, price, null, null);

// Pattern 2: Only SL
sugar.sellStop(symbol, volume, price, stopLoss, null);

// Pattern 3: Only TP
sugar.sellStop(symbol, volume, price, null, takeProfit);

// Pattern 4: Both SL and TP
sugar.sellStop(symbol, volume, price, stopLoss, takeProfit);
```

**SELL STOP vs SELL LIMIT:**

| Aspect          | SELL STOP            | SELL LIMIT           |
|-----------------|----------------------|----------------------|
| Entry price     | Below current Bid    | Above current Bid    |
| Triggers when   | Price drops          | Price rises          |
| Use case        | Breakdown trading    | Sell at premium      |
| Psychology      | Sell momentum down   | Sell at resistance   |
| Risk profile    | Lower entry price    | Higher entry price   |

---

## See also

* **Low-level method:** [`OrderSend`](../../MT5Account/5.%20Trading/OrderSend.md) - underlying implementation
* **Related:** [`sellMarket()`](../2.%20Market_orders/sellMarket.md) - immediate SELL execution
* **Other pending:** [`buyLimit()`](./buyLimit.md), [`sellLimit()`](./sellLimit.md), [`buyStop()`](./buyStop.md)
* **Points-based:** [`sellStopPoints()`](../4.%20Pending_orders_points/sellStopPoints.md) - easier offset syntax
* **Position management:** [`closePosition()`](../5.%20Position_management/closePosition.md)
* **Price helpers:** [`getBid()`](../1.%20Symbol_helpers/getBid.md), [`normalizePrice()`](../1.%20Symbol_helpers/normalizePrice.md)
