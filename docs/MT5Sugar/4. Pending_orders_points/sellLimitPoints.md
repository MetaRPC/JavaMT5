# 🔴 Place SELL LIMIT using Points Offset

> **Convenience method:** places a SELL LIMIT order using points offset from current Bid price. Automatically calculates entry price and SL/TP levels. Easier than manual price calculation.

**API Information:**

* **Sugar method:** `MT5Sugar.sellLimitPoints(String symbol, double volume, double pointsOffset, double stopLossPoints, double takeProfitPoints)`
* **Underlying methods:**
  - [`sellLimit()`](../3.%20Pending_orders/sellLimit.md) - underlying pending order method
  - `MT5Service.symbolInfoTick()` - get current prices
  - [`getPoint()`](../1.%20Symbol_helpers/getPoint.md) - get point size
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter          | Type     | Required | Description                                      |
| ------------------ | -------- | -------- | ------------------------------------------------ |
| `symbol`           | `String` | ✅       | Symbol name (e.g., "EURUSD")                     |
| `volume`           | `double` | ✅       | Volume in lots (e.g., 0.1)                       |
| `pointsOffset`     | `double` | ✅       | Points offset from Bid (positive = above price)  |
| `stopLossPoints`   | `double` | ✅       | SL distance in points (0 = no SL)                |
| `takeProfitPoints` | `double` | ✅       | TP distance in points (0 = no TP)                |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number (pending order ticket)

**Throws:** `ApiExceptionMT5` if order fails (contains error code and description)

**Execution:**
- Automatically calculates entry price: `Bid + (pointsOffset * point)`
- Automatically calculates SL: `entry + (stopLossPoints * point)`
- Automatically calculates TP: `entry - (takeProfitPoints * point)`
- Places SELL LIMIT order with calculated prices

---

## 💬 Just the essentials

* **What it is.** SELL LIMIT with automatic price calculation from points.
* **Why you need it.** Simpler than manual price math - just specify points.
* **Auto-handled.** All price calculations, symbol selection, normalization.
* **Use case.** Quick entry orders without price calculator.

---

## 🎯 Purpose

Use this method when you need to:

* Place SELL LIMIT without calculating exact prices.
* Use relative points instead of absolute prices.
* Simplify code - no manual `getBid()` and `getPoint()` calls.
* Standardize risk in points across different symbols.

---

## 🔗 Usage Examples

### 1) Simple SELL LIMIT 50 points above

```java
String symbol = "EURUSD";
double volume = 0.1;

// Sell 50 points above current Bid, SL=50p, TP=100p
long ticket = sugar.sellLimitPoints(symbol, volume, 50, 50, 100);

System.out.printf("SELL LIMIT order placed: #%d%n", ticket);
System.out.printf("Entry: 50 points above Bid%n");
System.out.printf("SL: 50 points above entry%n");
System.out.printf("TP: 100 points below entry%n");

// Output:
// SELL LIMIT order placed: #123456789
// Entry: 50 points above Bid
// SL: 50 points above entry
// TP: 100 points below entry
```

### 2) SELL LIMIT with risk/reward 1:3

```java
String symbol = "GBPUSD";
double volume = 0.5;
int entryOffset = 100; // 100 points above Bid
int slPoints = 50;     // 50 points SL
int tpPoints = 150;    // 150 points TP (1:3 R/R)

long ticket = sugar.sellLimitPoints(symbol, volume, entryOffset, slPoints, tpPoints);

double bid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);
double entry = bid + (entryOffset * point);

System.out.printf("SELL LIMIT %s: %.2f lots%n", symbol, volume);
System.out.printf("  Current Bid: %.5f%n", bid);
System.out.printf("  Entry: %.5f (%d points above)%n", entry, entryOffset);
System.out.printf("  Risk/Reward: 1:%.1f%n", (double) tpPoints / slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 3) Multiple SELL LIMIT grid with points

```java
String symbol = "EURUSD";
double volume = 0.01;
int slPoints = 50;
int tpPoints = 150;

System.out.printf("Placing SELL LIMIT grid:%n");

// Place orders at +50, +100, +150, +200, +250 points
for (int i = 1; i <= 5; i++) {
    int offset = 50 * i;
    long ticket = sugar.sellLimitPoints(symbol, volume, offset, slPoints, tpPoints);

    System.out.printf("  Level %d: #%d at %d points above Bid%n", i, ticket, offset);
}

// Output:
// Placing SELL LIMIT grid:
//   Level 1: #111 at 50 points above Bid
//   Level 2: #222 at 100 points above Bid
//   Level 3: #333 at 150 points above Bid
//   Level 4: #444 at 200 points above Bid
//   Level 5: #555 at 250 points above Bid
```

### 4) SELL LIMIT without SL/TP

```java
String symbol = "USDJPY";
double volume = 0.1;
int entryOffset = 30; // 30 points above

// No SL/TP (pass 0)
long ticket = sugar.sellLimitPoints(symbol, volume, entryOffset, 0, 0);

System.out.printf("✅ SELL LIMIT placed: #%d%n", ticket);
System.out.printf("   Entry: %d points above Bid%n", entryOffset);
System.out.printf("   No SL/TP%n");
```

### 5) SELL LIMIT at resistance with points

```java
String symbol = "XAUUSD";
double volume = 0.01;
double bid = sugar.getBid(symbol);
double resistanceLevel = 2550.0;
double point = sugar.getPoint(symbol);

// Calculate offset to resistance
int pointsToResistance = (int) ((resistanceLevel - bid) / point);

// Place order at resistance
long ticket = sugar.sellLimitPoints(symbol, volume, pointsToResistance, 50, 200);

System.out.printf("✅ SELL LIMIT at resistance:%n");
System.out.printf("   Current Bid: %.2f%n", bid);
System.out.printf("   Resistance: %.2f%n", resistanceLevel);
System.out.printf("   Offset: %d points%n", pointsToResistance);
System.out.printf("   Ticket: #%d%n", ticket);
```

### 6) Standardized risk across symbols

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};
double volume = 0.1;
int entryOffset = 50;   // Same for all
int slPoints = 30;      // Same risk
int tpPoints = 90;      // Same reward

for (String symbol : symbols) {
    long ticket = sugar.sellLimitPoints(symbol, volume, entryOffset, slPoints, tpPoints);

    double bid = sugar.getBid(symbol);
    int digits = sugar.getDigits(symbol);

    System.out.printf("%s: #%d | Bid: %.*f | Entry: %d points above%n",
        symbol, ticket, digits, bid, entryOffset);
}

// Output:
// EURUSD: #111 | Bid: 1.12340 | Entry: 50 points above
// GBPUSD: #222 | Bid: 1.26450 | Entry: 50 points above
// USDJPY: #333 | Bid: 149.123 | Entry: 50 points above
```

---

## 📌 Important Notes

* **Points offset:**
  - **Positive** offset = above current Bid (typical for SELL LIMIT)
  - Negative offset would place order below Bid (unusual)
  - Entry price = `Bid + (pointsOffset * point)`

* **Stop Loss calculation:**
  - SL distance is **above** entry for SELL
  - `SL = entry + (stopLossPoints * point)`
  - Pass `0` for no stop loss

* **Take Profit calculation:**
  - TP distance is **below** entry for SELL
  - `TP = entry - (takeProfitPoints * point)`
  - Pass `0` for no take profit

* **Automatic price fetching:**
  - Method fetches current Bid automatically
  - No need to call `getBid()` manually
  - Prices calculated at order placement time

* **vs sellLimit():**
  - `sellLimit()` - requires absolute prices
  - `sellLimitPoints()` - requires only point offsets
  - This method is simpler for relative positioning

* **Risk management:**
  - Easy to standardize risk across symbols
  - Same point values work for all symbols
  - Point value varies by symbol (use calculateVolume for $ risk)

**Calculation flow:**
```java
// What happens internally:
Bid = 1.12340
point = 0.00001
pointsOffset = 50

entry = Bid + (pointsOffset × point)
      = 1.12340 + (50 × 0.00001)
      = 1.12340 + 0.00050
      = 1.12390

SL = entry + (stopLossPoints × point)
   = 1.12390 + (50 × 0.00001)
   = 1.12390 + 0.00050
   = 1.12440

TP = entry - (takeProfitPoints × point)
   = 1.12390 - (100 × 0.00001)
   = 1.12390 - 0.00100
   = 1.12290
```

**Common patterns:**
```java
// Pattern 1: Standard setup (entry, SL, TP)
sugar.sellLimitPoints(symbol, volume, 50, 30, 90);

// Pattern 2: No SL/TP
sugar.sellLimitPoints(symbol, volume, 50, 0, 0);

// Pattern 3: Only SL
sugar.sellLimitPoints(symbol, volume, 50, 30, 0);

// Pattern 4: Only TP
sugar.sellLimitPoints(symbol, volume, 50, 0, 90);
```

---

## See also

* **Absolute price version:** [`sellLimit()`](../3.%20Pending_orders/sellLimit.md) - use exact prices
* **Related points methods:** [`buyLimitPoints()`](./buyLimitPoints.md), [`buyStopPoints()`](./buyStopPoints.md), [`sellStopPoints()`](./sellStopPoints.md)
* **Market order:** [`sellMarket()`](../2.%20Market_orders/sellMarket.md)
* **Price helpers:** [`getPoint()`](../1.%20Symbol_helpers/getPoint.md), [`getBid()`](../1.%20Symbol_helpers/getBid.md)
* **Risk management:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md)
