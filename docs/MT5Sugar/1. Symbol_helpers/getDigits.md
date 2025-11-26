# 🔢 Get Digits (Decimal Places)

> **Convenience method:** retrieves the number of decimal places for a symbol's price. Used for price formatting and normalization.

**API Information:**

* **Sugar method:** `MT5Sugar.getDigits(String symbol)`
* **Underlying method:** [`MT5Account.symbolInfoInteger()`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md) with `SYMBOL_DIGITS`
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |

---

## ⬆️ Output

**Returns:** `int` - Number of digits after decimal point

**Examples:**
- EURUSD: `5` (price: 1.12345)
- USDJPY: `3` (price: 110.123)
- XAUUSD: `2` (price: 1850.12)

---

## 💬 Just the essentials

* **What it is.** Number of decimal places in price.
* **Why you need it.** Format prices correctly, normalize values.
* **Price precision.** Determines how many decimals to display/store.
* **Broker-dependent.** 5-digit brokers vs 3-digit brokers.

---

## 🎯 Purpose

Use this method when you need to:

* Format prices for display.
* Round prices to symbol's precision.
* Validate price input.
* Understand broker's price precision.

---

## 🔗 Usage Examples

### 1) Get digits for symbol

```java
int digits = sugar.getDigits("EURUSD");
System.out.printf("EURUSD has %d decimal places%n", digits);
// Output: EURUSD has 5 decimal places
```

### 2) Format price with correct precision

```java
String symbol = "GBPUSD";
int digits = sugar.getDigits(symbol);
double price = sugar.getAsk(symbol);

// Format price with correct decimal places
String formatted = String.format("%." + digits + "f", price);
System.out.printf("%s price: %s%n", symbol, formatted);
// Output: GBPUSD price: 1.26543
```

### 3) Compare digits across symbols

```java
String[] symbols = {"EURUSD", "USDJPY", "XAUUSD", "BTCUSD"};

System.out.println("Symbol precision:");
for (String symbol : symbols) {
    int digits = sugar.getDigits(symbol);
    double point = sugar.getPoint(symbol);

    System.out.printf("  %s: %d digits (point: %.5f)%n",
        symbol, digits, point);
}

// Output:
// Symbol precision:
//   EURUSD: 5 digits (point: 0.00001)
//   USDJPY: 3 digits (point: 0.001)
//   XAUUSD: 2 digits (point: 0.01)
//   BTCUSD: 2 digits (point: 0.01)
```

### 4) Normalize price to symbol's digits

```java
String symbol = "EURUSD";
int digits = sugar.getDigits(symbol);
double rawPrice = 1.123456789;

// Round to symbol's precision
double multiplier = Math.pow(10, digits);
double normalized = Math.round(rawPrice * multiplier) / multiplier;

System.out.printf("Raw: %.9f%n", rawPrice);
System.out.printf("Normalized: %." + digits + "f%n", normalized);
// Output:
// Raw: 1.123456789
// Normalized: 1.12346
```

### 5) Dynamic price display

```java
public void displayPrice(String symbol, double price) throws Exception {
    int digits = sugar.getDigits(symbol);
    String format = "%." + digits + "f";

    System.out.printf("%s: " + format + "%n", symbol, price);
}

// Usage
displayPrice("EURUSD", 1.12345);  // EURUSD: 1.12345
displayPrice("USDJPY", 110.123);  // USDJPY: 110.123
displayPrice("XAUUSD", 1850.12);  // XAUUSD: 1850.12
```

---

## 📌 Important Notes

* **Broker-dependent:** 5-digit vs 3-digit brokers have different precision.
* **Always use for formatting:** Don't hardcode decimal places.
* **Price normalization:** Use `normalizePrice()` instead of manual rounding.
* **Point relationship:** Point = 10^(-digits)
* **Display vs calculation:** Use full precision for calculations, round only for display.

**Common digit counts:**
- **Major FX pairs:** 5 digits (EURUSD, GBPUSD, etc.)
- **JPY pairs:** 3 digits (USDJPY, EURJPY, etc.)
- **Metals:** 2 digits (XAUUSD, XAGUSD)
- **Crypto:** 2-8 digits (varies by broker)

---

## See also

* **Low-level method:** [`SymbolInfoInteger`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md) - underlying implementation
* **Related:** [`getPoint()`](./getPoint.md) - get point size (10^-digits)
* **Related:** [`normalizePrice()`](./normalizePrice.md) - auto-normalize to symbol's precision
* **Related:** [`getSpread()`](./getSpread.md) - spread uses same precision
