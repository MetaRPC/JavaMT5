# 📊 Get Spread (in Points)

> **Convenience method:** retrieves the current spread for a symbol in points. Essential for understanding trading costs and slippage.

**API Information:**

* **Sugar method:** `MT5Sugar.getSpread(String symbol)`
* **Underlying method:** [`MT5Account.symbolInfoInteger()`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md) with `SYMBOL_SPREAD`
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |

---

## ⬆️ Output

**Returns:** `int` - Current spread in points

**Examples:**
- EURUSD: `10` points = 1 pip (on 5-digit broker)
- USDJPY: `1` point = 1 pip (on 3-digit broker)
- XAUUSD: `30` points = 0.30 (variable spread)

---

## 💬 Just the essentials

* **What it is.** Difference between Ask and Bid in points.
* **Why you need it.** Understand trading cost before placing orders.
* **Variable.** Changes with market conditions (volatility, liquidity).
* **Trading cost.** You pay spread on every market order entry.

---

## 🎯 Purpose

Use this method when you need to:

* Check current trading costs before entering position.
* Monitor spread for optimal entry timing.
* Filter symbols with acceptable spreads.
* Calculate total trade cost (spread + commission).

---

## 🔗 Usage Examples

### 1) Get current spread

```java
int spread = sugar.getSpread("EURUSD");
System.out.printf("EURUSD spread: %d points%n", spread);
// Output: EURUSD spread: 10 points
```

### 2) Convert spread to pips

```java
String symbol = "EURUSD";
int spreadPoints = sugar.getSpread(symbol);
double spreadPips = sugar.pointsToPips(symbol, spreadPoints);

System.out.printf("%s spread: %d points = %.1f pips%n",
    symbol, spreadPoints, spreadPips);
// Output: EURUSD spread: 10 points = 1.0 pips
```

### 3) Check if spread is acceptable

```java
String symbol = "GBPUSD";
int spread = sugar.getSpread(symbol);
int maxAcceptableSpread = 20; // points

if (spread <= maxAcceptableSpread) {
    System.out.printf("✅ Spread OK: %d points%n", spread);
    // Safe to trade
    long ticket = sugar.buyMarket(symbol, 0.1, null, null);
} else {
    System.out.printf("⚠️ Spread too high: %d points (max: %d)%n",
        spread, maxAcceptableSpread);
    // Wait for better conditions
}
```

### 4) Monitor spreads across symbols

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY", "XAUUSD"};

System.out.println("Current spreads:");
for (String symbol : symbols) {
    int spread = sugar.getSpread(symbol);
    double spreadPips = sugar.pointsToPips(symbol, spread);

    System.out.printf("  %s: %d points (%.1f pips)%n",
        symbol, spread, spreadPips);
}

// Output:
// Current spreads:
//   EURUSD: 10 points (1.0 pips)
//   GBPUSD: 15 points (1.5 pips)
//   USDJPY: 1 points (1.0 pips)
//   XAUUSD: 30 points (30.0 pips)
```

### 5) Calculate total trading cost

```java
String symbol = "EURUSD";
double volume = 1.0; // 1 lot

// Get spread cost
int spread = sugar.getSpread(symbol);
double point = sugar.getPoint(symbol);
double[] tickData = sugar.getTickValueAndSize(symbol);
double tickValue = tickData[0];

// Calculate spread cost in account currency
double spreadCost = spread * point * tickValue;

System.out.printf("Trading cost for %s %.2f lot:%n", symbol, volume);
System.out.printf("  Spread: %d points%n", spread);
System.out.printf("  Cost: $%.2f per lot%n", spreadCost);

// Output:
// Trading cost for EURUSD 1.00 lot:
//   Spread: 10 points
//   Cost: $10.00 per lot
```

---

## 📌 Important Notes

* **Variable spread:** Changes constantly based on:
  - Market volatility
  - Liquidity (spreads widen during news)
  - Time of day (wider during Asian session)
  - Broker type (fixed vs variable spread)

* **Points vs Pips:**
  - 5-digit EURUSD: 10 points = 1 pip
  - 3-digit USDJPY: 1 point = 1 pip
  - Always check broker's precision

* **Trading impact:**
  - Market orders: Pay spread immediately
  - Limit orders: May avoid spread if price moves favorably
  - Wide spreads: Increase required profit for breakeven

* **Best practices:**
  - Check spread before trading
  - Avoid trading during high-spread periods (news events)
  - Set maximum acceptable spread threshold
  - Monitor spread patterns for your symbols

**Typical spreads:**
- Major pairs (EURUSD, GBPUSD): 1-2 pips
- Minor pairs (EURGBP): 2-3 pips
- Exotic pairs (USDTRY): 10+ pips
- Gold (XAUUSD): 0.20-0.50

---

## See also

* **Low-level method:** [`SymbolInfoInteger`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md) - underlying implementation
* **Related:** [`getSpreadPrice()`](./getSpreadPrice.md) - spread in price units (Ask - Bid)
* **Related:** [`pointsToPips()`](./pointsToPips.md) - convert points to pips
* **Related:** [`getBid()`](./getBid.md), [`getAsk()`](./getAsk.md) - get current prices
