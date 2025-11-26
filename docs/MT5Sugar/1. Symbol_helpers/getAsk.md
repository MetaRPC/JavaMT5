# 📈 Get Ask Price

> **Convenience method:** retrieves the current Ask price for a symbol in one simple call. Essential for BUY order calculations and spread analysis.

**API Information:**

* **Sugar method:** `MT5Sugar.getAsk(String symbol)`
* **Underlying method:** [`MT5Account.symbolInfoTick()`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md)
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |

---

## ⬆️ Output

**Returns:** `double` - Current Ask price

**What is Ask:**
- Price at which you can **BUY**
- Higher than Bid price (Ask - Bid = Spread)
- Used for closing SELL positions
- Used for opening BUY positions

---

## 💬 Just the essentials

* **What it is.** Current buying price for a symbol.
* **Why you need it.** Calculate BUY entry/exit points.
* **Ask vs Bid.** Ask = BUY price, Bid = SELL price.
* **Spread.** Ask - Bid = trading cost.

---

## 🎯 Purpose

Use this method when you need to:

* Get current price for BUY order entry.
* Calculate profit for open SELL positions.
* Monitor price for trading decisions.
* Calculate spread (Ask - Bid).

---

## 🔗 Usage Examples

### 1) Get current Ask price

```java
double ask = sugar.getAsk("EURUSD");
System.out.printf("EURUSD Ask: %.5f%n", ask);
// Output: EURUSD Ask: 1.12350
```

### 2) Calculate BUY stop loss from points

```java
String symbol = "EURUSD";
double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// SL 50 points below entry (for BUY)
double stopLoss = ask - (50 * point);

// TP 100 points above entry (for BUY)
double takeProfit = ask + (100 * point);

System.out.printf("BUY at Ask: %.5f%n", ask);
System.out.printf("Stop Loss (-50p): %.5f%n", stopLoss);
System.out.printf("Take Profit (+100p): %.5f%n", takeProfit);

// Place BUY order
long ticket = sugar.buyMarket(symbol, 0.1, stopLoss, takeProfit);
```

### 3) Monitor for price breakout

```java
String symbol = "GBPUSD";
double resistanceLevel = 1.27000;

System.out.printf("Monitoring %s for breakout above %.5f%n",
    symbol, resistanceLevel);

while (true) {
    double currentAsk = sugar.getAsk(symbol);

    if (currentAsk >= resistanceLevel) {
        System.out.printf("✅ Breakout! Ask: %.5f%n", currentAsk);
        sugar.buyMarket(symbol, 0.1, null, null);
        break;
    }

    System.out.printf("Waiting... Ask: %.5f (resistance: %.5f)%n",
        currentAsk, resistanceLevel);
    Thread.sleep(1000);
}
```

### 4) Calculate spread and trading cost

```java
String symbol = "XAUUSD";
double bid = sugar.getBid(symbol);
double ask = sugar.getAsk(symbol);
double spreadPrice = ask - bid;
int spreadPoints = sugar.getSpread(symbol);
double point = sugar.getPoint(symbol);

System.out.printf("%s spread analysis:%n", symbol);
System.out.printf("  Bid: %.2f%n", bid);
System.out.printf("  Ask: %.2f%n", ask);
System.out.printf("  Spread (price): %.2f%n", spreadPrice);
System.out.printf("  Spread (points): %d%n", spreadPoints);
System.out.printf("  Point size: %.2f%n", point);

// Verify spread calculation
double calculatedSpread = spreadPrice / point;
System.out.printf("  Calculated spread: %.0f points%n", calculatedSpread);
```

### 5) Wait for favorable Ask price

```java
String symbol = "EURUSD";
double maxAcceptableAsk = 1.12500;

while (true) {
    double currentAsk = sugar.getAsk(symbol);
    double bid = sugar.getBid(symbol);
    double spread = currentAsk - bid;

    if (currentAsk <= maxAcceptableAsk) {
        System.out.printf("✅ Good entry! Ask: %.5f (max: %.5f)%n",
            currentAsk, maxAcceptableAsk);

        // Place BUY order
        sugar.buyMarket(symbol, 0.1, null, null);
        break;
    }

    System.out.printf("Ask too high: %.5f (waiting for <= %.5f), Spread: %.5f%n",
        currentAsk, maxAcceptableAsk, spread);

    Thread.sleep(5000); // Check every 5 seconds
}
```

---

## 📌 Important Notes

* **Ask vs Bid:**
  - **Ask** = Price you BUY at (higher)
  - **Bid** = Price you SELL at (lower)
  - Spread = Ask - Bid (your cost)

* **When to use Ask:**
  - Opening BUY positions → Entry price
  - Closing SELL positions → Exit price
  - Calculating BUY Stop Loss (below Ask)
  - Calculating BUY Take Profit (above Ask)

* **Market orders:**
  - BUY market orders execute at current Ask
  - SELL market orders execute at current Bid

* **Real-time:** Price changes every tick during market hours.

* **Precision:** Always format with symbol's digits:
  ```java
  int digits = sugar.getDigits(symbol);
  String formatted = String.format("%." + digits + "f", ask);
  ```

**Ask/Bid relationship:**
```
Ask (higher) ← You BUY here
    ↕ Spread (your cost)
Bid (lower)  ← You SELL here
```

**Trading costs:**
```
BUY at Ask:  1.12350
SELL at Bid: 1.12340
Loss: 0.00010 (the spread you pay)
```

---

## See also

* **Low-level method:** [`SymbolInfoTick`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md) - underlying implementation
* **Related:** [`getBid()`](./getBid.md) - get Bid (SELL) price
* **Related:** [`getSpreadPrice()`](./getSpreadPrice.md) - get spread (Ask - Bid)
* **Trading:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md) - uses Ask for entry
