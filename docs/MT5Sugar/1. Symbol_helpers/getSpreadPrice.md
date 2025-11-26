# 📊 Get Spread in Price Units

> **Convenience method:** retrieves the current spread as a price difference (Ask - Bid). Returns actual currency value, not points.

**API Information:**

* **Sugar method:** `MT5Sugar.getSpreadPrice(String symbol)`
* **Underlying method:** [`MT5Account.symbolInfoTick()`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md)
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |

---

## ⬆️ Output

**Returns:** `double` - Spread in price units (Ask - Bid)

**Calculation:**
```
spreadPrice = Ask - Bid
```

**Example:**
- EURUSD: Ask=1.12350, Bid=1.12340 → Spread = **0.00010**
- XAUUSD: Ask=1850.50, Bid=1850.20 → Spread = **0.30**

---

## 💬 Just the essentials

* **What it is.** Spread expressed as price difference.
* **Why you need it.** Understand trading costs in currency units.
* **vs getSpread().** `getSpread()` returns points, this returns price.
* **Trading cost.** This is your cost to open + immediately close position.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate trading costs in currency units.
* Monitor spread conditions before placing trades.
* Compare spreads across different symbols in same units.
* Calculate break-even price including spread.

---

## 🔗 Usage Examples

### 1) Get current spread in price

```java
double spread = sugar.getSpreadPrice("EURUSD");
System.out.printf("EURUSD spread: %.5f%n", spread);
// Output: EURUSD spread: 0.00010
```

### 2) Compare spread representations

```java
String symbol = "GBPUSD";

// Get spread in different formats
int spreadPoints = sugar.getSpread(symbol);           // In points
double spreadPrice = sugar.getSpreadPrice(symbol);    // In price units
double point = sugar.getPoint(symbol);

// Verify calculation
double calculatedSpread = spreadPoints * point;

System.out.printf("%s spread analysis:%n", symbol);
System.out.printf("  Points: %d%n", spreadPoints);
System.out.printf("  Price: %.5f%n", spreadPrice);
System.out.printf("  Point size: %.5f%n", point);
System.out.printf("  Calculated: %.5f%n", calculatedSpread);
System.out.printf("  Match: %s%n",
    Math.abs(spreadPrice - calculatedSpread) < 0.000001 ? "✅" : "❌");

// Output:
// GBPUSD spread analysis:
//   Points: 15
//   Price: 0.00015
//   Point size: 0.00001
//   Calculated: 0.00015
//   Match: ✅
```

### 3) Check spread conditions before trading

```java
String symbol = "EURUSD";
double maxAcceptableSpread = 0.00020; // 20 points in price

while (true) {
    double currentSpread = sugar.getSpreadPrice(symbol);

    if (currentSpread <= maxAcceptableSpread) {
        System.out.printf("✅ Good spread: %.5f (max: %.5f)%n",
            currentSpread, maxAcceptableSpread);

        // Place trade
        sugar.buyMarket(symbol, 0.1, null, null);
        break;
    }

    System.out.printf("⚠️ Spread too high: %.5f (waiting for <= %.5f)%n",
        currentSpread, maxAcceptableSpread);

    Thread.sleep(1000); // Check every second
}
```

### 4) Calculate break-even including spread

```java
String symbol = "EURUSD";
double entryBid = sugar.getBid(symbol);
double spreadPrice = sugar.getSpreadPrice(symbol);

// For SELL order
System.out.printf("%s SELL break-even:%n", symbol);
System.out.printf("  Entry (Bid): %.5f%n", entryBid);
System.out.printf("  Spread cost: %.5f%n", spreadPrice);
System.out.printf("  Break-even price: %.5f%n", entryBid - spreadPrice);
System.out.printf("  (Price must move DOWN by %.5f to break even)%n", spreadPrice);

// For BUY order
double entryAsk = sugar.getAsk(symbol);
System.out.printf("%n%s BUY break-even:%n", symbol);
System.out.printf("  Entry (Ask): %.5f%n", entryAsk);
System.out.printf("  Spread cost: %.5f%n", spreadPrice);
System.out.printf("  Break-even price: %.5f%n", entryAsk);
System.out.printf("  (Already at Ask, must move UP to profit)%n");
```

### 5) Monitor spread across multiple symbols

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY", "XAUUSD"};

System.out.println("Current spreads (price units):");
System.out.println("Symbol   | Bid      | Ask      | Spread   | Points");
System.out.println("---------|----------|----------|----------|-------");

for (String symbol : symbols) {
    double bid = sugar.getBid(symbol);
    double ask = sugar.getAsk(symbol);
    double spreadPrice = sugar.getSpreadPrice(symbol);
    int spreadPoints = sugar.getSpread(symbol);
    int digits = sugar.getDigits(symbol);

    System.out.printf("%8s | %8."+digits+"f | %8."+digits+"f | %8."+digits+"f | %6d%n",
        symbol, bid, ask, spreadPrice, spreadPoints);
}

// Output:
// Symbol   | Bid      | Ask      | Spread   | Points
// ---------|----------|----------|----------|-------
//   EURUSD | 1.12340  | 1.12350  | 0.00010  |     10
//   GBPUSD | 1.26340  | 1.26355  | 0.00015  |     15
//   USDJPY | 110.123  | 110.133  | 0.010    |     10
//   XAUUSD | 1850.20  | 1850.50  | 0.30     |     30
```

### 6) Calculate trading cost for position size

```java
String symbol = "EURUSD";
double volume = 1.0; // 1 lot
double spreadPrice = sugar.getSpreadPrice(symbol);

// Get contract size (usually 100,000 for forex)
double contractSize = sugar.getContractSize(symbol);

// Calculate spread cost in account currency
double spreadCost = spreadPrice * volume * contractSize;

System.out.printf("%s spread cost analysis:%n", symbol);
System.out.printf("  Volume: %.2f lots%n", volume);
System.out.printf("  Spread: %.5f%n", spreadPrice);
System.out.printf("  Contract size: %.0f%n", contractSize);
System.out.printf("  Spread cost: $%.2f%n", spreadCost);
System.out.printf("  (Cost to open + immediately close position)%n");

// Output:
// EURUSD spread cost analysis:
//   Volume: 1.00 lots
//   Spread: 0.00010
//   Contract size: 100000
//   Spread cost: $10.00
//   (Cost to open + immediately close position)
```

---

## 📌 Important Notes

* **Always positive:** Spread is always ≥ 0 (Ask ≥ Bid).

* **vs getSpread():**
  - `getSpread()` → **integer points** (e.g., 10)
  - `getSpreadPrice()` → **price units** (e.g., 0.00010)
  - Relationship: `spreadPrice = spreadPoints * point`

* **Conversion:**
  ```java
  int spreadPoints = sugar.getSpread(symbol);
  double point = sugar.getPoint(symbol);
  double spreadPrice = spreadPoints * point;

  // Or directly:
  double spreadPrice = sugar.getSpreadPrice(symbol);
  ```

* **Trading cost:**
  ```
  When you BUY at Ask and immediately SELL at Bid,
  you lose the spread amount.
  ```

* **Variable spread:**
  - Spread widens during news, low liquidity, market close
  - Check before placing trades
  - Consider max acceptable spread threshold

* **Break-even calculation:**
  - **BUY:** Already at Ask, need price to rise to profit
  - **SELL:** Entered at Bid, need price to fall by spread to break even

**Spread relationship:**
```
Ask (1.12350) ← You BUY here
    ↕ Spread = 0.00010 (trading cost)
Bid (1.12340) ← You SELL here
```

---

## See also

* **Low-level method:** [`SymbolInfoTick`](../../MT5Account/2.%20Symbol_information/SymbolInfoTick.md) - gets Bid/Ask
* **Related:** [`getSpread()`](./getSpread.md) - get spread in **points**
* **Related:** [`getPoint()`](./getPoint.md) - get point size
* **Related:** [`getBid()`](./getBid.md) / [`getAsk()`](./getAsk.md) - get current prices
