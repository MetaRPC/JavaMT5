# 🟢 Place BUY STOP using Points Offset

> **Convenience method:** places a BUY STOP order using points offset from current Ask price. Automatically calculates entry price and SL/TP levels for breakout trading. Easier than manual price calculation.

**API Information:**

* **Sugar method:** `MT5Sugar.buyStopPoints(String symbol, double volume, double pointsOffset, double stopLossPoints, double takeProfitPoints)`
* **Underlying methods:**
  - [`buyStop()`](../3.%20Pending_orders/buyStop.md) - underlying pending order method
  - [`MT5Service.symbolInfoTick()`](../../MT5Account/2.%20Market_information/SymbolInfoTick.md) - get current prices
  - [`getPoint()`](../1.%20Symbol_helpers/getPoint.md) - get point size
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter          | Type     | Required | Description                                      |
| ------------------ | -------- | -------- | ------------------------------------------------ |
| `symbol`           | `String` | ✅       | Symbol name (e.g., "EURUSD")                     |
| `volume`           | `double` | ✅       | Volume in lots (e.g., 0.1)                       |
| `pointsOffset`     | `double` | ✅       | Points offset from Ask (positive = above price)  |
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
- Places BUY STOP order with calculated prices

---

## 💬 Just the essentials

* **What it is.** BUY STOP with automatic price calculation from points (breakout entry).
* **Why you need it.** Simpler breakout orders - just specify points above current price.
* **Auto-handled.** All price calculations, symbol selection, normalization.
* **Use case.** Resistance breakouts, momentum trading, upward breakouts.

---

## 🎯 Purpose

Use this method when you need to:

* Place BUY STOP without calculating exact prices.
* Set breakout orders using point offsets.
* Simplify momentum trading setup.
* Standardize breakout distance across symbols.

---

## 🔗 Usage Examples

### 1) Simple BUY STOP 50 points above

```java
String symbol = "EURUSD";
double volume = 0.1;

// Buy on breakout 50 points above Ask, SL=50p, TP=150p
long ticket = sugar.buyStopPoints(symbol, volume, 50, 50, 150);

System.out.printf("BUY STOP order placed: #%d%n", ticket);
System.out.printf("Entry: 50 points above Ask (breakout)%n");
System.out.printf("SL: 50 points below entry%n");
System.out.printf("TP: 150 points above entry%n");

// Output:
// BUY STOP order placed: #123456789
// Entry: 50 points above Ask (breakout)
// SL: 50 points below entry
// TP: 150 points above entry
```

### 2) BUY STOP breakout with 1:2 risk/reward

```java
String symbol = "GBPUSD";
double volume = 0.5;
int breakoutOffset = 100; // 100 points above Ask
int slPoints = 60;        // 60 points SL
int tpPoints = 120;       // 120 points TP (1:2 R/R)

long ticket = sugar.buyStopPoints(symbol, volume, breakoutOffset, slPoints, tpPoints);

double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);
double entry = ask + (breakoutOffset * point);

System.out.printf("BUY STOP breakout %s: %.2f lots%n", symbol, volume);
System.out.printf("  Current Ask: %.5f%n", ask);
System.out.printf("  Breakout entry: %.5f (%d points above)%n", entry, breakoutOffset);
System.out.printf("  Risk/Reward: 1:%.1f%n", (double) tpPoints / slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 3) Multiple BUY STOP orders (breakout cascade)

```java
String symbol = "EURUSD";
double volume = 0.01;
int slPoints = 40;
int tpPoints = 120;

System.out.printf("Placing BUY STOP cascade:%n");

// Place orders at +50, +100, +150 points (different breakout levels)
for (int i = 1; i <= 3; i++) {
    int offset = 50 * i;
    long ticket = sugar.buyStopPoints(symbol, volume, offset, slPoints, tpPoints);

    System.out.printf("  Level %d: #%d at %d points above Ask%n", i, ticket, offset);
}

// Output:
// Placing BUY STOP cascade:
//   Level 1: #111 at 50 points above Ask
//   Level 2: #222 at 100 points above Ask
//   Level 3: #333 at 150 points above Ask
```

### 4) BUY STOP without SL/TP

```java
String symbol = "USDJPY";
double volume = 0.1;
int breakoutOffset = 30; // 30 points above

// No SL/TP (pass 0)
long ticket = sugar.buyStopPoints(symbol, volume, breakoutOffset, 0, 0);

System.out.printf("✅ BUY STOP placed: #%d%n", ticket);
System.out.printf("   Breakout: %d points above Ask%n", breakoutOffset);
System.out.printf("   No SL/TP (manual management)%n");
```

### 5) BUY STOP on resistance breakout

```java
String symbol = "XAUUSD";
double volume = 0.01;
double ask = sugar.getAsk(symbol);
double resistanceLevel = 2550.0;
double point = sugar.getPoint(symbol);

// Calculate offset to resistance + buffer
int pointsToResistance = (int) ((resistanceLevel - ask) / point);
int breakoutBuffer = 10; // 10 points above resistance
int totalOffset = pointsToResistance + breakoutBuffer;

// Place order with SL below resistance
long ticket = sugar.buyStopPoints(symbol, volume, totalOffset, 60, 200);

System.out.printf("✅ BUY STOP on resistance breakout:%n");
System.out.printf("   Current Ask: %.2f%n", ask);
System.out.printf("   Resistance: %.2f%n", resistanceLevel);
System.out.printf("   Entry offset: %d points (resistance + %d buffer)%n",
    totalOffset, breakoutBuffer);
System.out.printf("   Ticket: #%d%n", ticket);
```

### 6) BUY STOP with tight SL for scalping

```java
String symbol = "EURUSD";
double volume = 0.2;
int breakoutOffset = 20; // Small breakout
int slPoints = 15;       // Tight SL
int tpPoints = 30;       // Quick TP

long ticket = sugar.buyStopPoints(symbol, volume, breakoutOffset, slPoints, tpPoints);

System.out.printf("Scalping BUY STOP:%n");
System.out.printf("  Entry: %d points above Ask%n", breakoutOffset);
System.out.printf("  SL: %d points (tight)%n", slPoints);
System.out.printf("  TP: %d points (quick)%n", tpPoints);
System.out.printf("  R/R: 1:%.1f%n", (double) tpPoints / slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

---

## 📌 Important Notes

* **Points offset:**
  - **Positive** offset = above current Ask (typical for BUY STOP)
  - Negative offset would place order below Ask (invalid for BUY STOP)
  - Entry price = `Ask + (pointsOffset * point)`

* **Stop Loss calculation:**
  - SL distance is **below** entry for BUY
  - `SL = entry - (stopLossPoints * point)`
  - Often placed below resistance level
  - Pass `0` for no stop loss

* **Take Profit calculation:**
  - TP distance is **above** entry for BUY
  - `TP = entry + (takeProfitPoints * point)`
  - Pass `0` for no take profit

* **Breakout trading:**
  - Used for upward momentum and resistance breakouts
  - Entry confirms bullish direction
  - Typically with tight SL below resistance

* **Automatic price fetching:**
  - Method fetches current Ask automatically
  - No need to call `getAsk()` manually
  - Prices calculated at order placement time

* **vs buyStop():**
  - `buyStop()` - requires absolute prices
  - `buyStopPoints()` - requires only point offsets
  - This method is simpler for breakout setups

**Calculation flow:**
```java
// What happens internally:
Ask = 1.12340
point = 0.00001
pointsOffset = 50 (breakout level)

entry = Ask + (pointsOffset × point)
      = 1.12340 + (50 × 0.00001)
      = 1.12340 + 0.00050
      = 1.12390 (breakout entry)

SL = entry - (stopLossPoints × point)
   = 1.12390 - (40 × 0.00001)
   = 1.12390 - 0.00040
   = 1.12350 (below resistance)

TP = entry + (takeProfitPoints × point)
   = 1.12390 + (120 × 0.00001)
   = 1.12390 + 0.00120
   = 1.12510 (profit target)
```

**Common patterns:**
```java
// Pattern 1: Breakout with SL/TP
sugar.buyStopPoints(symbol, volume, 50, 40, 120);

// Pattern 2: Breakout without SL/TP
sugar.buyStopPoints(symbol, volume, 50, 0, 0);

// Pattern 3: Tight scalping
sugar.buyStopPoints(symbol, volume, 20, 15, 30);

// Pattern 4: Wide swing trading
sugar.buyStopPoints(symbol, volume, 100, 80, 200);
```

**BUY STOP vs BUY LIMIT:**

| Aspect          | BUY STOP             | BUY LIMIT            |
|-----------------|----------------------|----------------------|
| Offset direction| Positive (above)     | Negative (below)     |
| Use case        | Breakout trading     | Buy at discount      |
| Entry timing    | Price rises          | Price drops          |
| Psychology      | Buy momentum         | Buy dips             |

---

## See also

* **Absolute price version:** [`buyStop()`](../3.%20Pending_orders/buyStop.md) - use exact prices
* **Related points methods:** [`buyLimitPoints()`](./buyLimitPoints.md), [`sellLimitPoints()`](./sellLimitPoints.md), [`sellStopPoints()`](./sellStopPoints.md)
* **Market order:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md)
* **Price helpers:** [`getPoint()`](../1.%20Symbol_helpers/getPoint.md), [`getAsk()`](../1.%20Symbol_helpers/getAsk.md)
* **Risk management:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md)
