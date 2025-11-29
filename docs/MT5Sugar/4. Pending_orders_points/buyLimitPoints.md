# 🟢 Place BUY LIMIT using Points Offset

> **Convenience method:** places a BUY LIMIT order using points offset from current Ask price. Automatically calculates entry price and SL/TP levels. Easier than manual price calculation.

**API Information:**

* **Sugar method:** `MT5Sugar.buyLimitPoints(String symbol, double volume, double pointsOffset, double stopLossPoints, double takeProfitPoints)`
* **Underlying methods:**
  - [`buyLimit()`](../3.%20Pending_orders/buyLimit.md) - underlying pending order method
  - `MT5Service.symbolInfoTick()` - get current prices
  - [`getPoint()`](../1.%20Symbol_helpers/getPoint.md) - get point size
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter          | Type     | Required | Description                                      |
| ------------------ | -------- | -------- | ------------------------------------------------ |
| `symbol`           | `String` | ✅       | Symbol name (e.g., "EURUSD")                     |
| `volume`           | `double` | ✅       | Volume in lots (e.g., 0.1)                       |
| `pointsOffset`     | `double` | ✅       | Points offset from Ask (negative = below price)  |
| `stopLossPoints`   | `double` | ✅       | SL distance in points (0 = no SL)                |
| `takeProfitPoints` | `double` | ✅       | TP distance in points (0 = no TP)                |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number (pending order ticket)

**Throws:** `ApiExceptionMT5` if order fails (contains error code and description)

**Execution:**
- Automatically calculates entry price: `Ask + (pointsOffset * point)`
- Automatically calculates SL: `entry - (stopLossPoints * point)`
- Automatically calculates TP: `entry + (takeProfitPoints * point)`
- Places BUY LIMIT order with calculated prices

---

## 💬 Just the essentials

* **What it is.** BUY LIMIT with automatic price calculation from points.
* **Why you need it.** Simpler than manual price math - just specify points.
* **Auto-handled.** All price calculations, symbol selection, normalization.
* **Use case.** Quick entry orders without price calculator.

---

## 🎯 Purpose

Use this method when you need to:

* Place BUY LIMIT without calculating exact prices.
* Use relative points instead of absolute prices.
* Simplify code - no manual `getAsk()` and `getPoint()` calls.
* Standardize risk in points across different symbols.

---

## 🔗 Usage Examples

### 1) Simple BUY LIMIT 50 points below

```java
String symbol = "EURUSD";
double volume = 0.1;

// Buy 50 points below current Ask, SL=50p, TP=100p
long ticket = sugar.buyLimitPoints(symbol, volume, -50, 50, 100);

System.out.printf("BUY LIMIT order placed: #%d%n", ticket);
System.out.printf("Entry: 50 points below Ask%n");
System.out.printf("SL: 50 points below entry%n");
System.out.printf("TP: 100 points above entry%n");

// Output:
// BUY LIMIT order placed: #123456789
// Entry: 50 points below Ask
// SL: 50 points below entry
// TP: 100 points above entry
```

### 2) BUY LIMIT with risk/reward 1:2

```java
String symbol = "GBPUSD";
double volume = 0.5;
int entryOffset = -100; // 100 points below Ask
int slPoints = 50;      // 50 points SL
int tpPoints = 100;     // 100 points TP (1:2 R/R)

long ticket = sugar.buyLimitPoints(symbol, volume, entryOffset, slPoints, tpPoints);

double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);
double entry = ask + (entryOffset * point);

System.out.printf("BUY LIMIT %s: %.2f lots%n", symbol, volume);
System.out.printf("  Current Ask: %.5f%n", ask);
System.out.printf("  Entry: %.5f (%d points below)%n", entry, Math.abs(entryOffset));
System.out.printf("  Risk/Reward: 1:%.1f%n", (double) tpPoints / slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 3) Multiple BUY LIMIT grid with points

```java
String symbol = "EURUSD";
double volume = 0.01;
int slPoints = 50;
int tpPoints = 150;

System.out.printf("Placing BUY LIMIT grid:%n");

// Place orders at -50, -100, -150, -200, -250 points
for (int i = 1; i <= 5; i++) {
    int offset = -50 * i;
    long ticket = sugar.buyLimitPoints(symbol, volume, offset, slPoints, tpPoints);

    System.out.printf("  Level %d: #%d at %d points below Ask%n", i, ticket, Math.abs(offset));
}

// Output:
// Placing BUY LIMIT grid:
//   Level 1: #111 at 50 points below Ask
//   Level 2: #222 at 100 points below Ask
//   Level 3: #333 at 150 points below Ask
//   Level 4: #444 at 200 points below Ask
//   Level 5: #555 at 250 points below Ask
```

### 4) BUY LIMIT without SL/TP

```java
String symbol = "USDJPY";
double volume = 0.1;
int entryOffset = -30; // 30 points below

// No SL/TP (pass 0)
long ticket = sugar.buyLimitPoints(symbol, volume, entryOffset, 0, 0);

System.out.printf("✅ BUY LIMIT placed: #%d%n", ticket);
System.out.printf("   Entry: %d points below Ask%n", Math.abs(entryOffset));
System.out.printf("   No SL/TP%n");
```

### 5) BUY LIMIT with error handling

```java
String symbol = "XAUUSD";
double volume = 0.01;
int entryOffset = -100; // 100 points below
int slPoints = 50;
int tpPoints = 200;

try {
    long ticket = sugar.buyLimitPoints(symbol, volume, entryOffset, slPoints, tpPoints);

    double ask = sugar.getAsk(symbol);
    double point = sugar.getPoint(symbol);
    double entryPrice = ask + (entryOffset * point);

    System.out.printf("✅ BUY LIMIT placed:%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Entry: %.2f (%d points below Ask)%n", entryPrice, Math.abs(entryOffset));

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Order failed: %s%n", e.getError().getErrorMessage());
}
```

### 6) Standardized risk across symbols

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};
double volume = 0.1;
int entryOffset = -50;  // Same for all
int slPoints = 30;      // Same risk
int tpPoints = 90;      // Same reward

for (String symbol : symbols) {
    long ticket = sugar.buyLimitPoints(symbol, volume, entryOffset, slPoints, tpPoints);

    double ask = sugar.getAsk(symbol);
    double point = sugar.getPoint(symbol);
    int digits = sugar.getDigits(symbol);

    System.out.printf("%s: #%d | Ask: %.*f | Entry: %d points below%n",
        symbol, ticket, digits, ask, Math.abs(entryOffset));
}

// Output:
// EURUSD: #111 | Ask: 1.12340 | Entry: 50 points below
// GBPUSD: #222 | Ask: 1.26450 | Entry: 50 points below
// USDJPY: #333 | Ask: 149.123 | Entry: 50 points below
```

---

## 📌 Important Notes

* **Points offset:**
  - **Negative** offset = below current Ask (typical for BUY LIMIT)
  - Positive offset would place order above Ask (unusual)
  - Entry price = `Ask + (pointsOffset * point)`

* **Stop Loss calculation:**
  - SL distance is **below** entry for BUY
  - `SL = entry - (stopLossPoints * point)`
  - Pass `0` for no stop loss

* **Take Profit calculation:**
  - TP distance is **above** entry for BUY
  - `TP = entry + (takeProfitPoints * point)`
  - Pass `0` for no take profit

* **Automatic price fetching:**
  - Method fetches current Ask automatically
  - No need to call `getAsk()` manually
  - Prices calculated at order placement time

* **vs buyLimit():**
  - `buyLimit()` - requires absolute prices
  - `buyLimitPoints()` - requires only point offsets
  - This method is simpler for relative positioning

* **Risk management:**
  - Easy to standardize risk across symbols
  - Same point values work for all symbols
  - Point value varies by symbol (use calculateVolume for $ risk)

**Calculation flow:**
```java
// What happens internally:
Ask = 1.12340
point = 0.00001
pointsOffset = -50

entry = Ask + (pointsOffset × point)
      = 1.12340 + (-50 × 0.00001)
      = 1.12340 - 0.00050
      = 1.12290

SL = entry - (stopLossPoints × point)
   = 1.12290 - (50 × 0.00001)
   = 1.12290 - 0.00050
   = 1.12240

TP = entry + (takeProfitPoints × point)
   = 1.12290 + (100 × 0.00001)
   = 1.12290 + 0.00100
   = 1.12390
```

**Common patterns:**
```java
// Pattern 1: Standard setup (entry, SL, TP)
sugar.buyLimitPoints(symbol, volume, -50, 30, 90);

// Pattern 2: No SL/TP
sugar.buyLimitPoints(symbol, volume, -50, 0, 0);

// Pattern 3: Only SL
sugar.buyLimitPoints(symbol, volume, -50, 30, 0);

// Pattern 4: Only TP
sugar.buyLimitPoints(symbol, volume, -50, 0, 90);
```

---

## See also

* **Absolute price version:** [`buyLimit()`](../3.%20Pending_orders/buyLimit.md) - use exact prices
* **Related points methods:** [`sellLimitPoints()`](./sellLimitPoints.md), [`buyStopPoints()`](./buyStopPoints.md), [`sellStopPoints()`](./sellStopPoints.md)
* **Market order:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md)
* **Price helpers:** [`getPoint()`](../1.%20Symbol_helpers/getPoint.md), [`getAsk()`](../1.%20Symbol_helpers/getAsk.md)
* **Risk management:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md)
