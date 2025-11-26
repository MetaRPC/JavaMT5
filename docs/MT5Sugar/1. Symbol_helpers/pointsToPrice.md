# 📍 Calculate Price from Points Offset

> **Convenience method:** calculates target price from current market price with points offset. Automatically uses Ask for BUY and Bid for SELL direction.

**API Information:**

* **Sugar method:** `MT5Sugar.pointsToPrice(String symbol, double points, boolean isBuy)`
* **Underlying methods:**
  - [`MT5Account.symbolInfoTick()`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md) - gets current prices
  - [`getPoint()`](./getPoint.md) - gets point size
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type      | Required | Description                                      |
| --------- | --------- | -------- | ------------------------------------------------ |
| `symbol`  | `String`  | ✅       | Symbol name (e.g., "EURUSD")                     |
| `points`  | `double`  | ✅       | Points offset (positive or negative)             |
| `isBuy`   | `boolean` | ✅       | true = use Ask + offset, false = use Bid - offset|

---

## ⬆️ Output

**Returns:** `double` - Calculated price

**Calculation:**
```
For BUY (isBuy = true):
  basePrice = Ask
  result = Ask + (points * point)

For SELL (isBuy = false):
  basePrice = Bid
  result = Bid - (points * point)
```

**Example:**
- Symbol: EURUSD, Ask: 1.12340, Bid: 1.12330, Point: 0.00001
- BUY +50 points → 1.12340 + 0.00050 = **1.12390**
- SELL +50 points → 1.12330 - 0.00050 = **1.12280**

---

## 💬 Just the essentials

* **What it is.** Convert points offset to actual price.
* **Why you need it.** Calculate SL/TP prices from points distance.
* **Direction matters.** BUY adds points, SELL subtracts points.
* **Auto base price.** Uses Ask for BUY, Bid for SELL.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate Stop Loss price from points distance.
* Calculate Take Profit price from points distance.
* Set pending order price with offset from current market.
* Convert indicator signals in points to actual prices.

---

## 🔗 Usage Examples

### 1) Calculate BUY Stop Loss 50 points below

```java
String symbol = "EURUSD";

// Calculate SL 50 points below Ask
double stopLoss = sugar.pointsToPrice(symbol, 50, true);

System.out.printf("Current Ask: %.5f%n", sugar.getAsk(symbol));
System.out.printf("Stop Loss (-50p): %.5f%n", stopLoss);

// Place BUY order with SL
long ticket = sugar.buyMarket(symbol, 0.1, stopLoss, null);
```

### 2) Calculate SELL Take Profit 100 points below

```java
String symbol = "GBPUSD";

// Calculate TP 100 points below Bid (for SELL)
double takeProfit = sugar.pointsToPrice(symbol, 100, false);

System.out.printf("Current Bid: %.5f%n", sugar.getBid(symbol));
System.out.printf("Take Profit (-100p): %.5f%n", takeProfit);

// Place SELL order with TP
long ticket = sugar.sellMarket(symbol, 0.1, null, takeProfit);
```

### 3) Calculate both SL and TP for BUY

```java
String symbol = "USDJPY";
double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// Method 1: Using pointsToPrice
double stopLoss = sugar.pointsToPrice(symbol, 50, true);   // SL -50 points
double takeProfit = ask + (100 * point);                   // TP +100 points manually

// Method 2: Using pointsToPrice for both
double sl2 = ask - (50 * point);                           // Manual
double tp2 = sugar.pointsToPrice(symbol, -100, true);      // Negative offset = below

System.out.printf("Ask: %.3f%n", ask);
System.out.printf("SL (-50p): %.3f%n", stopLoss);
System.out.printf("TP (+100p): %.3f%n", takeProfit);
```

### 4) Place pending order with offset

```java
String symbol = "EURUSD";

// Place BUY LIMIT 30 points below current Ask
double limitPrice = sugar.pointsToPrice(symbol, 30, true);

// SL 20 points below limit price
double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);
double stopLoss = limitPrice - (20 * point);

// TP 50 points above limit price
double takeProfit = limitPrice + (50 * point);

long ticket = sugar.buyLimit(symbol, 0.1, limitPrice, stopLoss, takeProfit);

System.out.printf("Buy Limit placed at %.5f%n", limitPrice);
System.out.printf("  SL: %.5f (-20p from entry)%n", stopLoss);
System.out.printf("  TP: %.5f (+50p from entry)%n", takeProfit);
```

### 5) Calculate risk-reward levels

```java
String symbol = "XAUUSD";
double bid = sugar.getBid(symbol);

// Risk: 100 points SL
double stopLoss = sugar.pointsToPrice(symbol, 100, false);

// Reward: 300 points TP (1:3 risk-reward)
double takeProfit = bid - (300 * sugar.getPoint(symbol));

double riskPoints = 100;
double rewardPoints = 300;
double riskRewardRatio = rewardPoints / riskPoints;

System.out.printf("%s SELL setup (1:%.0f R:R):%n", symbol, riskRewardRatio);
System.out.printf("  Entry (Bid): %.2f%n", bid);
System.out.printf("  Stop Loss (+100p): %.2f%n", stopLoss);
System.out.printf("  Take Profit (-300p): %.2f%n", takeProfit);
System.out.printf("  Risk: %.2f points%n", riskPoints);
System.out.printf("  Reward: %.2f points%n", rewardPoints);
```

---

## 📌 Important Notes

* **Direction logic:**
  - **BUY (isBuy=true)**: Base = Ask, Offset = **+points** (SL below, TP above)
  - **SELL (isBuy=false)**: Base = Bid, Offset = **-points** (SL above, TP below)

* **Points direction:**
  - Positive points with BUY → **above** Ask
  - Positive points with SELL → **below** Bid (subtracted)
  - Negative points reverse the direction

* **Common pattern:**
  ```java
  // For BUY orders:
  double sl = ask - (slPoints * point);  // SL below
  double tp = ask + (tpPoints * point);  // TP above

  // For SELL orders:
  double sl = bid + (slPoints * point);  // SL above
  double tp = bid - (tpPoints * point);  // TP below
  ```

* **When to use:**
  - ✅ Converting strategy signals (e.g., "SL = 50 points")
  - ✅ Calculating SL/TP from point-based rules
  - ✅ Placing pending orders with point offsets
  - ❌ Not needed if you already have absolute prices

* **Alternative:** [`priceFromOffsetPoints()`](./priceFromOffsetPoints.md) - similar but different offset logic

---

## See also

* **Low-level method:** [`SymbolInfoTick`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md) - gets current prices
* **Related:** [`getPoint()`](./getPoint.md) - get point size
* **Related:** [`priceFromOffsetPoints()`](./priceFromOffsetPoints.md) - alternative offset calculation
* **Related:** [`getBid()`](./getBid.md) / [`getAsk()`](./getAsk.md) - get current prices
* **Trading:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md), [`sellMarket()`](../2.%20Market_orders/sellMarket.md)
