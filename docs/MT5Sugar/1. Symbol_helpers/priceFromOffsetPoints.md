# 🎲 Calculate Price from Current with Offset

> **Convenience method:** calculates target price by adding points offset to current market price. Simpler alternative to `pointsToPrice()` with unified offset direction.

**API Information:**

* **Sugar method:** `MT5Sugar.priceFromOffsetPoints(String symbol, boolean isBuy, double pointsOffset)`
* **Underlying methods:**
  - [`MT5Account.symbolInfoTick()`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md) - gets current prices
  - [`getPoint()`](./getPoint.md) - gets point size
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter      | Type      | Required | Description                                      |
| -------------- | --------- | -------- | ------------------------------------------------ |
| `symbol`       | `String`  | ✅       | Symbol name (e.g., "EURUSD")                     |
| `isBuy`        | `boolean` | ✅       | true = use Ask, false = use Bid                  |
| `pointsOffset` | `double`  | ✅       | Points offset (positive = up, negative = down)   |

---

## ⬆️ Output

**Returns:** `double` - Calculated price

**Calculation:**
```
basePrice = isBuy ? Ask : Bid
result = basePrice + (pointsOffset * point)
```

**Key difference from `pointsToPrice()`:**
- **This method:** Offset is **always added** (positive=up, negative=down)
- **pointsToPrice():** BUY adds, SELL subtracts (different logic)

**Example:**
- Symbol: EURUSD, Ask: 1.12340, Bid: 1.12330, Point: 0.00001
- BUY +50 points → 1.12340 + 0.00050 = **1.12390**
- BUY -50 points → 1.12340 - 0.00050 = **1.12290**
- SELL +50 points → 1.12330 + 0.00050 = **1.12380**
- SELL -50 points → 1.12330 - 0.00050 = **1.12280**

---

## 💬 Just the essentials

* **What it is.** Add/subtract points from current price.
* **Why you need it.** Simple offset calculation for any direction.
* **vs pointsToPrice().** This uses simple addition, pointsToPrice has direction logic.
* **Positive = up.** Negative = down. Simple.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate price at fixed offset from current market.
* Set pending orders above/below current price.
* Calculate trailing stop prices.
* Any scenario where you think "current price ± X points".

---

## 🔗 Usage Examples

### 1) Calculate price 50 points above Ask

```java
String symbol = "EURUSD";
double currentAsk = sugar.getAsk(symbol);

// 50 points above current Ask
double targetPrice = sugar.priceFromOffsetPoints(symbol, true, 50);

System.out.printf("Current Ask: %.5f%n", currentAsk);
System.out.printf("Target (+50p): %.5f%n", targetPrice);

// Output:
// Current Ask: 1.12340
// Target (+50p): 1.12390
```

### 2) Calculate price 30 points below Bid

```java
String symbol = "GBPUSD";
double currentBid = sugar.getBid(symbol);

// 30 points below current Bid (negative offset)
double targetPrice = sugar.priceFromOffsetPoints(symbol, false, -30);

System.out.printf("Current Bid: %.5f%n", currentBid);
System.out.printf("Target (-30p): %.5f%n", targetPrice);

// Output:
// Current Bid: 1.26340
// Target (-30p): 1.26310
```

### 3) Place BUY STOP order above current price

```java
String symbol = "EURUSD";

// Place BUY STOP 20 points above Ask
double stopPrice = sugar.priceFromOffsetPoints(symbol, true, 20);

// SL 40 points below entry
double stopLoss = stopPrice - (40 * sugar.getPoint(symbol));

// TP 80 points above entry
double takeProfit = stopPrice + (80 * sugar.getPoint(symbol));

long ticket = sugar.buyStop(symbol, 0.1, stopPrice, stopLoss, takeProfit);

System.out.printf("BUY STOP placed at %.5f (+20p from Ask)%n", stopPrice);
System.out.printf("  SL: %.5f%n", stopLoss);
System.out.printf("  TP: %.5f%n", takeProfit);
```

### 4) Trailing stop calculation

```java
String symbol = "USDJPY";
double trailingPoints = 50; // Trail by 50 points

// For open BUY position, trail stop below current Bid
double currentBid = sugar.getBid(symbol);
double newStopLoss = sugar.priceFromOffsetPoints(symbol, false, -trailingPoints);

System.out.printf("Trailing stop for BUY:%n");
System.out.printf("  Current Bid: %.3f%n", currentBid);
System.out.printf("  New SL: %.3f (50p trail)%n", newStopLoss);

// For open SELL position, trail stop above current Ask
double currentAsk = sugar.getAsk(symbol);
double newStopLossSell = sugar.priceFromOffsetPoints(symbol, true, trailingPoints);

System.out.printf("Trailing stop for SELL:%n");
System.out.printf("  Current Ask: %.3f%n", currentAsk);
System.out.printf("  New SL: %.3f (50p trail)%n", newStopLossSell);
```

### 5) Grid trading levels

```java
String symbol = "EURUSD";
int gridStep = 20; // 20 points between levels
int levels = 5;

double currentAsk = sugar.getAsk(symbol);
double currentBid = sugar.getBid(symbol);

System.out.printf("%s Grid Trading Setup:%n", symbol);
System.out.printf("Current Ask: %.5f | Bid: %.5f%n%n", currentAsk, currentBid);

// BUY grid above current price
System.out.println("BUY levels (above Ask):");
for (int i = 1; i <= levels; i++) {
    double price = sugar.priceFromOffsetPoints(symbol, true, gridStep * i);
    System.out.printf("  Level %d: %.5f (+%dp)%n", i, price, gridStep * i);
}

// SELL grid below current price
System.out.println("\nSELL levels (below Bid):");
for (int i = 1; i <= levels; i++) {
    double price = sugar.priceFromOffsetPoints(symbol, false, -gridStep * i);
    System.out.printf("  Level %d: %.5f (-%dp)%n", i, price, gridStep * i);
}

// Output:
// EURUSD Grid Trading Setup:
// Current Ask: 1.12340 | Bid: 1.12330
//
// BUY levels (above Ask):
//   Level 1: 1.12360 (+20p)
//   Level 2: 1.12380 (+40p)
//   Level 3: 1.12400 (+60p)
//   Level 4: 1.12420 (+80p)
//   Level 5: 1.12440 (+100p)
//
// SELL levels (below Bid):
//   Level 1: 1.12310 (-20p)
//   Level 2: 1.12290 (-40p)
//   Level 3: 1.12270 (-60p)
//   Level 4: 1.12250 (-80p)
//   Level 5: 1.12230 (-100p)
```

### 6) Compare with pointsToPrice()

```java
String symbol = "EURUSD";
double ask = sugar.getAsk(symbol);
double bid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);

System.out.printf("%s: Ask=%.5f, Bid=%.5f%n%n", symbol, ask, bid);

// Compare BUY scenarios (both add points)
double buyPoints = 50;
double price1 = sugar.priceFromOffsetPoints(symbol, true, buyPoints);
double price2 = sugar.pointsToPrice(symbol, buyPoints, true);

System.out.printf("BUY +50 points:%n");
System.out.printf("  priceFromOffsetPoints: %.5f%n", price1);
System.out.printf("  pointsToPrice: %.5f%n", price2);
System.out.printf("  Manual: %.5f%n", ask + (buyPoints * point));
System.out.printf("  Match: %s%n%n", price1 == price2 ? "✅" : "❌");

// Compare SELL scenarios (DIFFERENT logic!)
double sellPoints = 50;
double price3 = sugar.priceFromOffsetPoints(symbol, false, sellPoints);
double price4 = sugar.pointsToPrice(symbol, sellPoints, false);

System.out.printf("SELL with 50 points offset:%n");
System.out.printf("  priceFromOffsetPoints: %.5f (Bid + 50p = UP)%n", price3);
System.out.printf("  pointsToPrice: %.5f (Bid - 50p = DOWN)%n", price4);
System.out.printf("  Difference: pointsToPrice subtracts for SELL%n");

// Output:
// EURUSD: Ask=1.12340, Bid=1.12330
//
// BUY +50 points:
//   priceFromOffsetPoints: 1.12390
//   pointsToPrice: 1.12390
//   Manual: 1.12390
//   Match: ✅
//
// SELL with 50 points offset:
//   priceFromOffsetPoints: 1.12380 (Bid + 50p = UP)
//   pointsToPrice: 1.12280 (Bid - 50p = DOWN)
//   Difference: pointsToPrice subtracts for SELL
```

---

## 📌 Important Notes

* **Direction logic:**
  - **Positive offset** → price **UP** (for both BUY and SELL)
  - **Negative offset** → price **DOWN** (for both BUY and SELL)
  - Simple: `result = basePrice + offset`

* **vs pointsToPrice():**

  | Method                    | BUY Logic       | SELL Logic      |
  |---------------------------|-----------------|-----------------|
  | `priceFromOffsetPoints()` | Ask + offset    | Bid + offset    |
  | `pointsToPrice()`         | Ask + offset    | Bid - offset    |

  `pointsToPrice()` has special SELL logic (subtracts), this method doesn't.

* **Which to use:**
  - **priceFromOffsetPoints:** When you think "current price ± X points"
  - **pointsToPrice:** When calculating SL/TP (has direction awareness)

  Example:
  ```java
  // SL for BUY: 50 points below entry
  // pointsToPrice is more intuitive:
  double sl1 = sugar.pointsToPrice(symbol, 50, true); // Ask - 50p

  // priceFromOffsetPoints requires negative:
  double sl2 = sugar.priceFromOffsetPoints(symbol, true, -50); // Ask - 50p
  ```

* **Common use cases:**
  - ✅ Pending orders at fixed distance
  - ✅ Grid trading levels
  - ✅ Trailing stops
  - ✅ Price alerts/notifications
  - ❌ SL/TP calculation (prefer `pointsToPrice`)

* **Base price selection:**
  - `isBuy = true` → uses **Ask** (for BUY orders)
  - `isBuy = false` → uses **Bid** (for SELL orders)

**Calculation examples:**
```
Ask = 1.12340, Bid = 1.12330, Point = 0.00001

priceFromOffsetPoints(symbol, true, 50):
  → 1.12340 + (50 * 0.00001) = 1.12390

priceFromOffsetPoints(symbol, true, -50):
  → 1.12340 + (-50 * 0.00001) = 1.12290

priceFromOffsetPoints(symbol, false, 50):
  → 1.12330 + (50 * 0.00001) = 1.12380

priceFromOffsetPoints(symbol, false, -50):
  → 1.12330 + (-50 * 0.00001) = 1.12280
```

---

## See also

* **Low-level method:** [`SymbolInfoTick`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md) - gets current prices
* **Related:** [`pointsToPrice()`](./pointsToPrice.md) - alternative with direction-aware logic
* **Related:** [`getPoint()`](./getPoint.md) - get point size
* **Related:** [`getBid()`](./getBid.md) / [`getAsk()`](./getAsk.md) - get current prices
* **Trading:** Pending order methods ([`buyLimit`](../3.%20Pending_orders/buyLimit.md), [`sellStop`](../3.%20Pending_orders/sellStop.md), etc.)
