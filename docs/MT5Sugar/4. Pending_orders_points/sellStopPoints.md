# 🔴 Place SELL STOP using Points Offset

> **Convenience method:** places a SELL STOP order using points offset from current Bid price. Automatically calculates entry price and SL/TP levels for breakdown trading. Easier than manual price calculation.

**API Information:**

* **Sugar method:** `MT5Sugar.sellStopPoints(String symbol, double volume, double pointsOffset, double stopLossPoints, double takeProfitPoints)`
* **Underlying methods:**
  - [`sellStop()`](../3.%20Pending_orders/sellStop.md) - underlying pending order method
  - [`MT5Service.symbolInfoTick()`](../../MT5Account/2.%20Market_information/SymbolInfoTick.md) - get current prices
  - [`getPoint()`](../1.%20Symbol_helpers/getPoint.md) - get point size
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter          | Type     | Required | Description                                      |
| ------------------ | -------- | -------- | ------------------------------------------------ |
| `symbol`           | `String` | ✅       | Symbol name (e.g., "EURUSD")                     |
| `volume`           | `double` | ✅       | Volume in lots (e.g., 0.1)                       |
| `pointsOffset`     | `double` | ✅       | Points offset from Bid (negative = below price)  |
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
- Places SELL STOP order with calculated prices

---

## 💬 Just the essentials

* **What it is.** SELL STOP with automatic price calculation from points (breakdown entry).
* **Why you need it.** Simpler breakdown orders - just specify points below current price.
* **Auto-handled.** All price calculations, symbol selection, normalization.
* **Use case.** Support breakdowns, momentum trading, downward breakouts.

---

## 🎯 Purpose

Use this method when you need to:

* Place SELL STOP without calculating exact prices.
* Set breakdown orders using point offsets.
* Simplify momentum trading setup.
* Standardize breakdown distance across symbols.

---

## 🔗 Usage Examples

### 1) Simple SELL STOP 50 points below

```java
String symbol = "EURUSD";
double volume = 0.1;

// Sell on breakdown 50 points below Bid, SL=50p, TP=150p
long ticket = sugar.sellStopPoints(symbol, volume, -50, 50, 150);

System.out.printf("SELL STOP order placed: #%d%n", ticket);
System.out.printf("Entry: 50 points below Bid (breakdown)%n");
System.out.printf("SL: 50 points above entry%n");
System.out.printf("TP: 150 points below entry%n");

// Output:
// SELL STOP order placed: #123456789
// Entry: 50 points below Bid (breakdown)
// SL: 50 points above entry
// TP: 150 points below entry
```

### 2) SELL STOP breakdown with 1:2 risk/reward

```java
String symbol = "GBPUSD";
double volume = 0.5;
int breakdownOffset = -100; // 100 points below Bid
int slPoints = 60;          // 60 points SL
int tpPoints = 120;         // 120 points TP (1:2 R/R)

long ticket = sugar.sellStopPoints(symbol, volume, breakdownOffset, slPoints, tpPoints);

double bid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);
double entry = bid + (breakdownOffset * point);

System.out.printf("SELL STOP breakdown %s: %.2f lots%n", symbol, volume);
System.out.printf("  Current Bid: %.5f%n", bid);
System.out.printf("  Breakdown entry: %.5f (%d points below)%n", entry, Math.abs(breakdownOffset));
System.out.printf("  Risk/Reward: 1:%.1f%n", (double) tpPoints / slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 3) Multiple SELL STOP orders (breakdown cascade)

```java
String symbol = "EURUSD";
double volume = 0.01;
int slPoints = 40;
int tpPoints = 120;

System.out.printf("Placing SELL STOP cascade:%n");

// Place orders at -50, -100, -150 points (different breakdown levels)
for (int i = 1; i <= 3; i++) {
    int offset = -50 * i;
    long ticket = sugar.sellStopPoints(symbol, volume, offset, slPoints, tpPoints);

    System.out.printf("  Level %d: #%d at %d points below Bid%n", i, ticket, Math.abs(offset));
}

// Output:
// Placing SELL STOP cascade:
//   Level 1: #111 at 50 points below Bid
//   Level 2: #222 at 100 points below Bid
//   Level 3: #333 at 150 points below Bid
```

### 4) SELL STOP without SL/TP

```java
String symbol = "USDJPY";
double volume = 0.1;
int breakdownOffset = -30; // 30 points below

// No SL/TP (pass 0)
long ticket = sugar.sellStopPoints(symbol, volume, breakdownOffset, 0, 0);

System.out.printf("✅ SELL STOP placed: #%d%n", ticket);
System.out.printf("   Breakdown: %d points below Bid%n", Math.abs(breakdownOffset));
System.out.printf("   No SL/TP (manual management)%n");
```

### 5) SELL STOP on support breakdown

```java
String symbol = "XAUUSD";
double volume = 0.01;
double bid = sugar.getBid(symbol);
double supportLevel = 2500.0;
double point = sugar.getPoint(symbol);

// Calculate offset to support - buffer
int pointsToSupport = (int) ((supportLevel - bid) / point);
int breakdownBuffer = -10; // 10 points below support
int totalOffset = pointsToSupport + breakdownBuffer;

// Place order with SL above support
long ticket = sugar.sellStopPoints(symbol, volume, totalOffset, 60, 200);

System.out.printf("✅ SELL STOP on support breakdown:%n");
System.out.printf("   Current Bid: %.2f%n", bid);
System.out.printf("   Support: %.2f%n", supportLevel);
System.out.printf("   Entry offset: %d points (support - %d buffer)%n",
    totalOffset, Math.abs(breakdownBuffer));
System.out.printf("   Ticket: #%d%n", ticket);
```

### 6) SELL STOP with tight SL for scalping

```java
String symbol = "EURUSD";
double volume = 0.2;
int breakdownOffset = -20; // Small breakdown
int slPoints = 15;         // Tight SL
int tpPoints = 30;         // Quick TP

long ticket = sugar.sellStopPoints(symbol, volume, breakdownOffset, slPoints, tpPoints);

System.out.printf("Scalping SELL STOP:%n");
System.out.printf("  Entry: %d points below Bid%n", Math.abs(breakdownOffset));
System.out.printf("  SL: %d points (tight)%n", slPoints);
System.out.printf("  TP: %d points (quick)%n", tpPoints);
System.out.printf("  R/R: 1:%.1f%n", (double) tpPoints / slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

---

## 📌 Important Notes

* **Points offset:**
  - **Negative** offset = below current Bid (typical for SELL STOP)
  - Positive offset would place order above Bid (invalid for SELL STOP)
  - Entry price = `Bid + (pointsOffset * point)`

* **Stop Loss calculation:**
  - SL distance is **above** entry for SELL
  - `SL = entry + (stopLossPoints * point)`
  - Often placed above support level
  - Pass `0` for no stop loss

* **Take Profit calculation:**
  - TP distance is **below** entry for SELL
  - `TP = entry - (takeProfitPoints * point)`
  - Pass `0` for no take profit

* **Breakdown trading:**
  - Used for downward momentum and support breakdowns
  - Entry confirms bearish direction
  - Typically with tight SL above support

* **Automatic price fetching:**
  - Method fetches current Bid automatically
  - No need to call `getBid()` manually
  - Prices calculated at order placement time

* **vs sellStop():**
  - `sellStop()` - requires absolute prices
  - `sellStopPoints()` - requires only point offsets
  - This method is simpler for breakdown setups

**Calculation flow:**
```java
// What happens internally:
Bid = 1.12340
point = 0.00001
pointsOffset = -50 (breakdown level)

entry = Bid + (pointsOffset × point)
      = 1.12340 + (-50 × 0.00001)
      = 1.12340 - 0.00050
      = 1.12290 (breakdown entry)

SL = entry + (stopLossPoints × point)
   = 1.12290 + (40 × 0.00001)
   = 1.12290 + 0.00040
   = 1.12330 (above support)

TP = entry - (takeProfitPoints × point)
   = 1.12290 - (120 × 0.00001)
   = 1.12290 - 0.00120
   = 1.12170 (profit target)
```

**Common patterns:**
```java
// Pattern 1: Breakdown with SL/TP
sugar.sellStopPoints(symbol, volume, -50, 40, 120);

// Pattern 2: Breakdown without SL/TP
sugar.sellStopPoints(symbol, volume, -50, 0, 0);

// Pattern 3: Tight scalping
sugar.sellStopPoints(symbol, volume, -20, 15, 30);

// Pattern 4: Wide swing trading
sugar.sellStopPoints(symbol, volume, -100, 80, 200);
```

**SELL STOP vs SELL LIMIT:**

| Aspect          | SELL STOP            | SELL LIMIT           |
|-----------------|----------------------|----------------------|
| Offset direction| Negative (below)     | Positive (above)     |
| Use case        | Breakdown trading    | Sell at premium      |
| Entry timing    | Price drops          | Price rises          |
| Psychology      | Sell momentum        | Sell rallies         |

---

## See also

* **Absolute price version:** [`sellStop()`](../3.%20Pending_orders/sellStop.md) - use exact prices
* **Related points methods:** [`buyLimitPoints()`](./buyLimitPoints.md), [`sellLimitPoints()`](./sellLimitPoints.md), [`buyStopPoints()`](./buyStopPoints.md)
* **Market order:** [`sellMarket()`](../2.%20Market_orders/sellMarket.md)
* **Price helpers:** [`getPoint()`](../1.%20Symbol_helpers/getPoint.md), [`getBid()`](../1.%20Symbol_helpers/getBid.md)
* **Risk management:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md)
