# 🎯 Normalize Price to Symbol Precision

> **Convenience method:** rounds a price to the symbol's correct number of decimal places. Prevents "invalid price" errors when placing orders.

**API Information:**

* **Sugar method:** `MT5Sugar.normalizePrice(String symbol, double price)`
* **Underlying methods:**
  - [`MT5Account.symbolInfoInteger()`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md) with `SYMBOL_DIGITS`
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |
| `price`   | `double` | ✅       | Price to normalize                       |

---

## ⬆️ Output

**Returns:** `double` - Normalized price rounded to symbol's digits

**Examples:**
- Input: `1.123456789`, Symbol: EURUSD (5 digits) → Output: `1.12346`
- Input: `110.12345`, Symbol: USDJPY (3 digits) → Output: `110.123`

---

## 💬 Just the essentials

* **What it is.** Rounds price to broker's accepted precision.
* **Why you need it.** Broker rejects prices with wrong decimal places.
* **Auto-used.** All pending order methods call this internally.
* **Prevents errors.** "Invalid price" errors from too many/few decimals.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate custom entry/exit prices before placing orders.
* Round indicator-calculated prices to valid precision.
* Prepare prices from external sources (APIs, calculations).
* Ensure SL/TP prices are broker-compliant.

---

## 🔗 Usage Examples

### 1) Normalize calculated price

```java
String symbol = "EURUSD";
double calculatedPrice = 1.123456789; // Too many decimals

double normalized = sugar.normalizePrice(symbol, calculatedPrice);

System.out.printf("Raw price: %.9f%n", calculatedPrice);
System.out.printf("Normalized: %.5f%n", normalized);
// Output:
// Raw price: 1.123456789
// Normalized: 1.12346
```

### 2) Prepare SL/TP for pending order

```java
String symbol = "GBPUSD";
double entryPrice = 1.26500;

// Calculate SL/TP with some algorithm
double rawStopLoss = entryPrice - 0.001234567;
double rawTakeProfit = entryPrice + 0.002345678;

// Normalize before placing order
double stopLoss = sugar.normalizePrice(symbol, rawStopLoss);
double takeProfit = sugar.normalizePrice(symbol, rawTakeProfit);

System.out.printf("Entry: %.5f%n", entryPrice);
System.out.printf("SL: %.5f (normalized from %.9f)%n", stopLoss, rawStopLoss);
System.out.printf("TP: %.5f (normalized from %.9f)%n", takeProfit, rawTakeProfit);

// Now safe to use in order
long ticket = sugar.buyLimit(symbol, 0.1, entryPrice, stopLoss, takeProfit);
```

### 3) Normalize prices from indicator

```java
// Example: Moving Average returns high precision
double ma200 = calculateMovingAverage(200); // Returns 1.12345678

String symbol = "EURUSD";
double normalizedMA = sugar.normalizePrice(symbol, ma200);

System.out.printf("MA(200) raw: %.8f%n", ma200);
System.out.printf("MA(200) normalized: %.5f%n", normalizedMA);

// Use as entry price for limit order
double currentPrice = sugar.getAsk(symbol);
if (currentPrice > normalizedMA) {
    long ticket = sugar.buyLimit(symbol, 0.1, normalizedMA, null, null);
    System.out.printf("Buy Limit placed at %.5f%n", normalizedMA);
}
```

### 4) Normalize prices across different symbols

```java
String[] symbols = {"EURUSD", "USDJPY", "XAUUSD"};
double[] rawPrices = {1.123456789, 110.123456, 1850.123456};

System.out.println("Price normalization:");
for (int i = 0; i < symbols.length; i++) {
    double normalized = sugar.normalizePrice(symbols[i], rawPrices[i]);
    int digits = sugar.getDigits(symbols[i]);

    System.out.printf("  %s (%d digits): %.9f → %." + digits + "f%n",
        symbols[i], digits, rawPrices[i], normalized);
}

// Output:
// Price normalization:
//   EURUSD (5 digits): 1.123456789 → 1.12346
//   USDJPY (3 digits): 110.123456 → 110.123
//   XAUUSD (2 digits): 1850.123456 → 1850.12
```

### 5) Avoid "Invalid Price" error

```java
String symbol = "EURUSD";

// ❌ BAD: Price has wrong precision
double badPrice = 1.1234; // Only 4 digits, broker expects 5
// sugar.buyLimit(symbol, 0.1, badPrice, null, null); // May fail!

// ✅ GOOD: Normalize first
double goodPrice = sugar.normalizePrice(symbol, badPrice);
long ticket = sugar.buyLimit(symbol, 0.1, goodPrice, null, null);

System.out.printf("Order placed at normalized price: %.5f%n", goodPrice);
```

---

## 📌 Important Notes

* **Rounding:** Uses banker's rounding (round half to even).
* **Auto-called:** All pending order methods (`buyLimit`, `sellStop`, etc.) call this internally.
* **Market orders:** Don't need normalization (broker uses current market price).
* **Precision matters:** Must match broker's expected decimal places.

**Algorithm:**
```java
int digits = getDigits(symbol);
double multiplier = Math.pow(10, digits);
return Math.round(price * multiplier) / multiplier;
```

**When to use:**

- ✅ Prices from calculations, indicators, algorithms
- ✅ SL/TP from custom formulas
- ✅ Entry prices for pending orders
- ❌ Not needed for market orders (use 0 or current quote)

**Common precision by symbol type:**

- Major FX: 5 digits (0.00001)
- JPY pairs: 3 digits (0.001)
- Gold/Silver: 2 digits (0.01)
- Indices: 1-2 digits

---

## See also

* **Low-level method:** [`SymbolInfoInteger`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md) - gets digits
* **Related:** [`getDigits()`](./getDigits.md) - get decimal places
* **Related:** [`normalizeVolume()`](./normalizeVolume.md) - normalize lot size
* **Auto-uses this:** [`buyLimit()`](../3.%20Pending_orders/buyLimit.md) and all pending order methods
