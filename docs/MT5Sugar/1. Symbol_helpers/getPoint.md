# 📏 Get Point Size

> **Convenience method:** retrieves the point size for a symbol (smallest price change). Essential for calculating prices with point offsets.

**API Information:**

* **Sugar method:** `MT5Sugar.getPoint(String symbol)`
* **Underlying method:** [`MT5Account.symbolInfoDouble()`](../../MT5Account/2.%20Symbol_information/SymbolInfoDouble.md) with `SYMBOL_POINT`
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |

---

## ⬆️ Output

**Returns:** `double` - Point size

**Examples:**
- EURUSD (5 digits): `0.00001`
- USDJPY (3 digits): `0.001`
- XAUUSD (2 digits): `0.01`

---

## 💬 Just the essentials

* **What it is.** Smallest price change for a symbol.
* **Why you need it.** Calculate SL/TP with point offsets.
* **Point vs Pip.** For 5-digit EURUSD: 1 pip = 10 points.
* **Used internally.** Many sugar methods use this for calculations.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate Stop Loss / Take Profit from points offset.
* Convert points to price units.
* Understand price precision for a symbol.
* Calculate spread in price units.

---

## 🔗 Usage Examples

### 1) Get point size

```java
double point = sugar.getPoint("EURUSD");
System.out.printf("EURUSD point: %.5f%n", point);
// Output: EURUSD point: 0.00001
```

### 2) Calculate SL/TP from points

```java
String symbol = "EURUSD";
double point = sugar.getPoint(symbol);
double ask = sugar.getAsk(symbol);

// SL 50 points below entry
double stopLoss = ask - (50 * point);

// TP 100 points above entry
double takeProfit = ask + (100 * point);

System.out.printf("Entry: %.5f%n", ask);
System.out.printf("SL (-50 points): %.5f%n", stopLoss);
System.out.printf("TP (+100 points): %.5f%n", takeProfit);
```

### 3) Compare point sizes across symbols

```java
String[] symbols = {"EURUSD", "USDJPY", "XAUUSD", "GBPUSD"};

for (String symbol : symbols) {
    double point = sugar.getPoint(symbol);
    System.out.printf("%s point size: %.5f%n", symbol, point);
}

// Output:
// EURUSD point size: 0.00001
// USDJPY point size: 0.001
// XAUUSD point size: 0.01
// GBPUSD point size: 0.00001
```

---

## 📌 Important Notes

* **Point vs Pip:**
  - 5-digit broker (EURUSD): 1 pip = 10 points
  - 3-digit broker (USDJPY): 1 pip = 1 point
* **Precision:** Point defines minimum price movement.
* **Calculations:** Always multiply points by point size to get price offset.
* **Symbol-specific:** Each symbol has different point size.
* **Use with digits:** Combine with `getDigits()` for price formatting.

**Formula:**
```
Price offset = Points × Point size
Example: 50 points on EURUSD = 50 × 0.00001 = 0.0005
```

---

## See also

* **Low-level method:** [`SymbolInfoDouble`](../../MT5Account/2.%20Symbol_information/SymbolInfoDouble.md) - underlying implementation
* **Related:** [`getDigits()`](./getDigits.md) - decimal places for formatting
* **Related:** [`pointsToPips()`](./pointsToPips.md) - convert points to pips
* **Related:** [`normalizePrice()`](./normalizePrice.md) - normalize price to symbol's precision
