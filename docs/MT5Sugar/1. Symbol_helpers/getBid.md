# 📉 Get Bid Price

> **Convenience method:** retrieves the current Bid price for a symbol in one simple call. Essential for SELL order calculations and spread analysis.

**API Information:**

* **Sugar method:** `MT5Sugar.getBid(String symbol)`
* **Underlying method:** [`MT5Account.symbolInfoTick()`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md)
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |

---

## ⬆️ Output

**Returns:** `double` - Current Bid price

**What is Bid:**
- Price at which you can **SELL**
- Lower than Ask price (Ask - Bid = Spread)
- Used for closing BUY positions
- Used for opening SELL positions

---

## 💬 Just the essentials

* **What it is.** Current selling price for a symbol.
* **Why you need it.** Calculate SELL entry/exit points.
* **Bid vs Ask.** Bid = SELL price, Ask = BUY price.
* **Spread.** Ask - Bid = trading cost.

---

## 🎯 Purpose

Use this method when you need to:

* Get current price for SELL order entry.
* Calculate profit for open BUY positions.
* Monitor price for trading decisions.
* Calculate spread (Ask - Bid).

---

## 🔗 Usage Examples

### 1) Get current Bid price

```java
double bid = sugar.getBid("EURUSD");
System.out.printf("EURUSD Bid: %.5f%n", bid);
// Output: EURUSD Bid: 1.12340
```

### 2) Calculate spread

```java
String symbol = "GBPUSD";
double bid = sugar.getBid(symbol);
double ask = sugar.getAsk(symbol);
double spread = ask - bid;

System.out.printf("%s prices:%n", symbol);
System.out.printf("  Bid: %.5f%n", bid);
System.out.printf("  Ask: %.5f%n", ask);
System.out.printf("  Spread: %.5f%n", spread);

// Output:
// GBPUSD prices:
//   Bid: 1.26340
//   Ask: 1.26355
//   Spread: 0.00015
```

### 3) Calculate SELL stop loss from points

```java
String symbol = "EURUSD";
double bid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);

// SL 50 points above entry (for SELL)
double stopLoss = bid + (50 * point);

// TP 100 points below entry (for SELL)
double takeProfit = bid - (100 * point);

System.out.printf("SELL at Bid: %.5f%n", bid);
System.out.printf("Stop Loss (+50p): %.5f%n", stopLoss);
System.out.printf("Take Profit (-100p): %.5f%n", takeProfit);

// Place SELL order
long ticket = sugar.sellMarket(symbol, 0.1, stopLoss, takeProfit);
```

### 4) Monitor price levels

```java
String symbol = "USDJPY";
double targetBid = 110.500;

while (true) {
    double currentBid = sugar.getBid(symbol);

    if (currentBid <= targetBid) {
        System.out.printf("✅ Target reached! Bid: %.3f%n", currentBid);
        // Execute trading logic
        sugar.sellMarket(symbol, 0.1, null, null);
        break;
    }

    System.out.printf("Waiting... Current Bid: %.3f (target: %.3f)%n",
        currentBid, targetBid);
    Thread.sleep(1000);
}
```

### 5) Compare Bid across symbols

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};

System.out.println("Current Bid prices:");
for (String symbol : symbols) {
    double bid = sugar.getBid(symbol);
    int digits = sugar.getDigits(symbol);

    System.out.printf("  %s: %." + digits + "f%n", symbol, bid);
}

// Output:
// Current Bid prices:
//   EURUSD: 1.12340
//   GBPUSD: 1.26340
//   USDJPY: 110.123
```

---

## 📌 Important Notes

* **Bid vs Ask:**
  - **Bid** = Price you SELL at (lower)
  - **Ask** = Price you BUY at (higher)
  - Spread = Ask - Bid (your cost)

* **When to use Bid:**
  - Opening SELL positions → Entry price
  - Closing BUY positions → Exit price
  - Calculating SELL Stop Loss (above Bid)
  - Calculating SELL Take Profit (below Bid)

* **Market orders:**
  - SELL market orders execute at current Bid
  - BUY market orders execute at current Ask

* **Real-time:** Price changes every tick during market hours.

* **Precision:** Always format with symbol's digits:
  ```java
  int digits = sugar.getDigits(symbol);
  String formatted = String.format("%." + digits + "f", bid);
  ```

**Bid/Ask relationship:**
```
Ask (higher) ← You BUY here
    ↕ Spread (your cost)
Bid (lower)  ← You SELL here
```

---

## See also

* **Low-level method:** [`SymbolInfoTick`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md) - underlying implementation
* **Related:** [`getAsk()`](./getAsk.md) - get Ask (BUY) price
* **Related:** [`getSpreadPrice()`](./getSpreadPrice.md) - get spread (Ask - Bid)
* **Trading:** [`sellMarket()`](../2.%20Market_orders/sellMarket.md) - uses Bid for entry
