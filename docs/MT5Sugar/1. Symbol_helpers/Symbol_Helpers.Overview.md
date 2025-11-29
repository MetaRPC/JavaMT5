# MT5Sugar · Symbol Helpers - Overview

> Convenience methods for working with symbol properties, prices, and calculations. Simplifies common symbol operations that would otherwise require multiple low-level calls.

## 📁 What lives here

* **[ensureSymbolSelected](./ensureSymbolSelected.md)** - ensure symbol is available for trading (auto-select if needed).
* **[getPoint](./getPoint.md)** - get point size (smallest price increment).
* **[getDigits](./getDigits.md)** - get decimal places for symbol.
* **[getSpread](./getSpread.md)** - get current spread in **points**.
* **[getSpreadPrice](./getSpreadPrice.md)** - get current spread in **price units** (Ask - Bid).
* **[normalizePrice](./normalizePrice.md)** - round price to symbol's decimal precision.
* **[normalizeVolume](./normalizeVolume.md)** - adjust volume to symbol's min/max/step constraints.
* **[getBid](./getBid.md)** - get current Bid (SELL) price in one call.
* **[getAsk](./getAsk.md)** - get current Ask (BUY) price in one call.
* **[pointsToPrice](./pointsToPrice.md)** - convert points offset to price (direction-aware).
* **[priceFromOffsetPoints](./priceFromOffsetPoints.md)** - calculate price from current with offset (simple addition).
* **[pointsToPips](./pointsToPips.md)** - convert points to pips (handles 5-digit vs 3-digit brokers).

---

## 🧭 Plain English

* **Symbol selection:** `ensureSymbolSelected()` → make sure symbol is available.
* **Price info:** `getBid()`, `getAsk()`, `getSpread()`, `getSpreadPrice()` → current market data.
* **Symbol properties:** `getPoint()`, `getDigits()` → symbol specifications.
* **Normalization:** `normalizePrice()`, `normalizeVolume()` → fix values to broker rules.
* **Point calculations:** `pointsToPrice()`, `priceFromOffsetPoints()`, `pointsToPips()` → convert between formats.

> Rule of thumb: need **current price** → `getBid()/getAsk()`; need **symbol specs** → `getPoint()/getDigits()`; need **SL/TP calculation** → `pointsToPrice()`.

---

## Quick choose

| If you need…                                     | Use                                  | Returns                  | Underlying MT5Account method       |
| ------------------------------------------------ | ------------------------------------ | ------------------------ | ---------------------------------- |
| Make sure symbol is available                    | `ensureSymbolSelected`               | void                     | `symbolSelect`                     |
| Current BUY price                                | `getAsk`                             | double (price)           | `symbolInfoTick`                   |
| Current SELL price                               | `getBid`                             | double (price)           | `symbolInfoTick`                   |
| Spread in points                                 | `getSpread`                          | int (points)             | `symbolInfoInteger`                |
| Spread in price units                            | `getSpreadPrice`                     | double (Ask - Bid)       | `symbolInfoTick`                   |
| Smallest price increment                         | `getPoint`                           | double (e.g., 0.00001)   | `symbolInfoDouble`                 |
| Decimal places                                   | `getDigits`                          | int (e.g., 5)            | `symbolInfoInteger`                |
| Fix price to correct precision                   | `normalizePrice`                     | double (rounded)         | `symbolInfoInteger` (digits)       |
| Fix volume to broker limits                      | `normalizeVolume`                    | double (clamped/rounded) | `symbolInfoDouble` (vol limits)    |
| Calculate SL/TP from points (direction-aware)    | `pointsToPrice`                      | double (price)           | `symbolInfoTick` + `getPoint`      |
| Calculate price at offset (simple)               | `priceFromOffsetPoints`              | double (price)           | `symbolInfoTick` + `getPoint`      |
| Convert points to pips (for reporting)           | `pointsToPips`                       | double (pips)            | `getDigits`                        |

---

## ❌ Cross‑refs & gotchas

* **Bid vs Ask:**
  - **Bid** = price you SELL at (lower)
  - **Ask** = price you BUY at (higher)
  - **Spread** = Ask - Bid (your cost)

* **Points vs Pips:**
  - **Point** = broker's smallest increment (e.g., 0.00001 for 5-digit EURUSD)
  - **Pip** = industry standard (0.0001 for FX)
  - **5-digit brokers:** 10 points = 1 pip
  - **3-digit brokers (JPY):** 1 point = 1 pip
  - Use `pointsToPips()` for conversion

* **Normalization:**
  - `normalizePrice()` → rounds to symbol's decimal places (e.g., 5 digits)
  - `normalizeVolume()` → clamps to [min, max] and rounds to step
  - All trading methods auto-normalize, manual use rarely needed

* **pointsToPrice vs priceFromOffsetPoints:**
  - `pointsToPrice(symbol, 50, true)` → Ask + 50p (BUY), Bid - 50p (SELL) ← direction logic
  - `priceFromOffsetPoints(symbol, true, 50)` → Ask + 50p (always adds)
  - Use `pointsToPrice()` for SL/TP, `priceFromOffsetPoints()` for simple offsets

* **Symbol selection:**
  - Most methods don't require `ensureSymbolSelected()` first
  - Only needed if symbol might not be in Market Watch
  - All trading methods call this internally

* **Real-time data:**
  - `getBid()`, `getAsk()`, `getSpread()` return **current** market data
  - Values change tick-by-tick during market hours
  - Cache if you need consistent values across calculations

---

## 🟢 Minimal snippets

```java
// Get current prices
double bid = sugar.getBid("EURUSD");
double ask = sugar.getAsk("EURUSD");
double spread = sugar.getSpreadPrice("EURUSD"); // In price units
int spreadPoints = sugar.getSpread("EURUSD");   // In points

System.out.printf("EURUSD: Bid=%.5f, Ask=%.5f, Spread=%.5f (%d points)%n",
    bid, ask, spread, spreadPoints);
// Output: EURUSD: Bid=1.12340, Ask=1.12350, Spread=0.00010 (10 points)
```

```java
// Get symbol specifications
double point = sugar.getPoint("EURUSD");  // 0.00001
int digits = sugar.getDigits("EURUSD");   // 5

System.out.printf("EURUSD: Point=%.5f, Digits=%d%n", point, digits);
// Output: EURUSD: Point=0.00001, Digits=5
```

```java
// Calculate SL/TP from points
String symbol = "EURUSD";

// BUY: SL 50 points below, TP 100 points above
double slBuy = sugar.pointsToPrice(symbol, 50, true);   // Ask - 50p
double tpBuy = sugar.getAsk(symbol) + (100 * sugar.getPoint(symbol)); // Ask + 100p

// SELL: SL 50 points above, TP 100 points below
double slSell = sugar.pointsToPrice(symbol, 50, false);  // Bid + 50p
double tpSell = sugar.getBid(symbol) - (100 * sugar.getPoint(symbol)); // Bid - 100p

System.out.printf("BUY: SL=%.5f, TP=%.5f%n", slBuy, tpBuy);
System.out.printf("SELL: SL=%.5f, TP=%.5f%n", slSell, tpSell);
```

```java
// Normalize values before trading
String symbol = "EURUSD";
double calculatedPrice = 1.123456789;  // Too many decimals
double calculatedVolume = 0.157;       // Might not match broker step

double price = sugar.normalizePrice(symbol, calculatedPrice);   // 1.12346
double volume = sugar.normalizeVolume(symbol, calculatedVolume); // 0.16

System.out.printf("Normalized: Price=%.5f, Volume=%.2f%n", price, volume);
// Note: Trading methods auto-normalize, manual use rarely needed
```

```java
// Convert points to pips (for reporting)
String symbol = "EURUSD";
double profitPoints = 50;
double profitPips = sugar.pointsToPips(symbol, profitPoints);

System.out.printf("Profit: %.0f points (%.1f pips)%n", profitPoints, profitPips);
// Output: Profit: 50 points (5.0 pips)
```

```java
// Calculate price at offset from current
String symbol = "GBPUSD";

// BUY STOP 20 points above current Ask
double buyStopPrice = sugar.priceFromOffsetPoints(symbol, true, 20);

// SELL LIMIT 30 points above current Bid
double sellLimitPrice = sugar.priceFromOffsetPoints(symbol, false, 30);

System.out.printf("BUY STOP: %.5f (+20p from Ask)%n", buyStopPrice);
System.out.printf("SELL LIMIT: %.5f (+30p from Bid)%n", sellLimitPrice);
```

```java
// Ensure symbol is available (rarely needed)
sugar.ensureSymbolSelected("XAUUSD");
// Symbol is now in Market Watch if it wasn't already
```

---

## Common Patterns

### Pattern 1: Get current market state

```java
String symbol = "EURUSD";

// All in a few lines
double bid = sugar.getBid(symbol);
double ask = sugar.getAsk(symbol);
double spread = sugar.getSpreadPrice(symbol);
double point = sugar.getPoint(symbol);
int digits = sugar.getDigits(symbol);

System.out.printf("%s Market State:%n", symbol);
System.out.printf("  Bid: %." + digits + "f%n", bid);
System.out.printf("  Ask: %." + digits + "f%n", ask);
System.out.printf("  Spread: %." + digits + "f (%d points)%n",
    spread, (int)(spread / point));
System.out.printf("  Point: %." + digits + "f%n", point);
```

### Pattern 2: Calculate SL/TP for market order

```java
String symbol = "EURUSD";
int slPoints = 50;
int tpPoints = 100;

// For BUY
double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);
double slBuy = ask - (slPoints * point);
double tpBuy = ask + (tpPoints * point);

long ticket = sugar.buyMarket(symbol, 0.1, slBuy, tpBuy);

// For SELL
double bid = sugar.getBid(symbol);
double slSell = bid + (slPoints * point);
double tpSell = bid - (tpPoints * point);

long ticket2 = sugar.sellMarket(symbol, 0.1, slSell, tpSell);
```

### Pattern 3: Monitor spread conditions

```java
String symbol = "EURUSD";
double maxSpreadPrice = 0.00020; // Max 20 points

while (true) {
    double currentSpread = sugar.getSpreadPrice(symbol);

    if (currentSpread <= maxSpreadPrice) {
        System.out.println("✅ Good spread, placing trade");
        sugar.buyMarket(symbol, 0.1, null, null);
        break;
    }

    System.out.printf("⚠️ Spread too high: %.5f (max: %.5f)%n",
        currentSpread, maxSpreadPrice);
    Thread.sleep(1000);
}
```

### Pattern 4: Compare symbols

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};

System.out.println("Symbol   | Bid      | Ask      | Spread   | Point    | Digits");
System.out.println("---------|----------|----------|----------|----------|-------");

for (String symbol : symbols) {
    double bid = sugar.getBid(symbol);
    double ask = sugar.getAsk(symbol);
    double spread = sugar.getSpreadPrice(symbol);
    double point = sugar.getPoint(symbol);
    int digits = sugar.getDigits(symbol);

    System.out.printf("%8s | %8."+digits+"f | %8."+digits+"f | %8."+digits+"f | %8."+digits+"f | %6d%n",
        symbol, bid, ask, spread, point, digits);
}
```

---

## Best Practices

* **Cache symbol properties:** Point, digits don't change - cache them if used repeatedly.
  ```java
  // ❌ BAD: Repeated calls
  for (int i = 0; i < 1000; i++) {
      double price = ask + (i * sugar.getPoint(symbol)); // Calls getPoint 1000x
  }

  // ✅ GOOD: Cache once
  double point = sugar.getPoint(symbol);
  for (int i = 0; i < 1000; i++) {
      double price = ask + (i * point);
  }
  ```

* **Consistent base price:** Use same Bid/Ask for related calculations.
  ```java
  // ❌ BAD: Different prices
  double sl = sugar.getAsk(symbol) - (50 * point);  // Ask at time T1
  double tp = sugar.getAsk(symbol) + (100 * point); // Ask at time T2 (may differ!)

  // ✅ GOOD: Single snapshot
  double ask = sugar.getAsk(symbol);
  double sl = ask - (50 * point);
  double tp = ask + (100 * point);
  ```

* **Auto-normalization:** Trading methods normalize automatically.
  ```java
  // ❌ UNNECESSARY: Already normalized inside
  double volume = sugar.normalizeVolume(symbol, 0.1);
  sugar.buyMarket(symbol, volume, null, null);

  // ✅ GOOD: Direct call
  sugar.buyMarket(symbol, 0.1, null, null); // Auto-normalizes
  ```

* **Direction-aware calculations:** Use `pointsToPrice()` for SL/TP.
  ```java
  // ✅ GOOD: Direction-aware
  double sl = sugar.pointsToPrice(symbol, 50, true); // BUY: Ask - 50p

  // ❌ CONFUSING: Manual with priceFromOffsetPoints
  double sl2 = sugar.priceFromOffsetPoints(symbol, true, -50); // Same but less clear
  ```

* **Display in pips:** Convert to pips for user-facing reports.
  ```java
  double profitPoints = 50;
  double profitPips = sugar.pointsToPips(symbol, profitPoints);

  System.out.printf("Profit: %.0f points (%.1f pips)%n", profitPoints, profitPips);
  // More user-friendly than just "50 points"
  ```

---

## See also

* **MT5Account Symbol Methods:** [`SymbolInfoTick`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md), [`SymbolInfoDouble`](../../MT5Account/2.%20Symbol_information/SymbolInfoDouble.md), [`SymbolInfoInteger`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md)
* **Trading Methods:** [Market Orders](../2.%20Market_orders/), [Pending Orders](../3.%20Pending_orders/)
* **Position Management:** [Position Helpers](../5.%20Position_management/)
* **Account Info:** [Account Helpers](../9.%20Account_and_position_helpers/)
