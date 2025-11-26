# 📊 Calculate Volume Based on Risk

> **Risk management method:** calculates position volume (lot size) based on desired risk amount in account currency. Core method for position sizing.

**API Information:**

* **Sugar method:** `MT5Sugar.calculateVolume(String symbol, double stopLossPoints, double riskAmount)`
* **Underlying methods:**
  - [`tickValueWithSize()`](../../MT5Account/4.%20Helper_functions/TickValueWithSize.md) - get tick value
  - [`getPoint()`](../1.%20Symbol_helpers/getPoint.md) - get point size
  - [`normalizeVolume()`](../1.%20Symbol_helpers/normalizeVolume.md) - normalize result
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter        | Type     | Required | Description                                |
| ---------------- | -------- | -------- | ------------------------------------------ |
| `symbol`         | `String` | ✅       | Symbol name (e.g., "EURUSD")               |
| `stopLossPoints` | `double` | ✅       | Stop Loss distance in points              |
| `riskAmount`     | `double` | ✅       | Risk amount in account currency (e.g., $50) |

---

## ⬆️ Output

**Returns:** `double` - Calculated and normalized volume in lots

**Throws:** `ApiExceptionMT5` if calculation fails

**Calculation:**
```
valuePerPoint = (tickValue / tickSize) × point
volume = riskAmount / (stopLossPoints × valuePerPoint)
result = normalizeVolume(volume)
```

---

## 💬 Just the essentials

* **What it is.** Auto-calculate lot size from $ risk and SL distance.
* **Why you need it.** Proper position sizing - risk same $ amount per trade.
* **Formula.** Volume = RiskAmount / (StopLossPoints × PointValue).
* **Returns.** Normalized volume ready for order placement.

---

## 🎯 Purpose

Use this method when you need to:

* Risk fixed $ amount per trade (e.g., $50).
* Calculate position size based on SL distance.
* Implement proper risk management (e.g., 2% per trade).
* Standardize risk across different symbols.

---

## 🔗 Usage Examples

### 1) Calculate volume for $50 risk

```java
String symbol = "EURUSD";
int slPoints = 50;          // 50 points SL
double riskAmount = 50.0;   // Risk $50

double volume = sugar.calculateVolume(symbol, slPoints, riskAmount);

System.out.printf("Risk management:%n");
System.out.printf("  Symbol: %s%n", symbol);
System.out.printf("  SL: %d points%n", slPoints);
System.out.printf("  Risk: $%.2f%n", riskAmount);
System.out.printf("  Calculated volume: %.2f lots%n", volume);

// Output:
// Risk management:
//   Symbol: EURUSD
//   SL: 50 points
//   Risk: $50.00
//   Calculated volume: 0.10 lots
```

### 2) Risk 2% of balance per trade

```java
String symbol = "GBPUSD";
double balance = sugar.getBalance();
double riskPercent = 2.0; // 2% risk
int slPoints = 100;

double riskAmount = balance * (riskPercent / 100.0);
double volume = sugar.calculateVolume(symbol, slPoints, riskAmount);

System.out.printf("2%% risk sizing:%n");
System.out.printf("  Balance: $%.2f%n", balance);
System.out.printf("  Risk: %.1f%% ($%.2f)%n", riskPercent, riskAmount);
System.out.printf("  SL: %d points%n", slPoints);
System.out.printf("  Volume: %.2f lots%n", volume);
```

### 3) Compare volume across different SL distances

```java
String symbol = "EURUSD";
double riskAmount = 100.0; // Fixed $100 risk
int[] slDistances = {30, 50, 100, 150};

System.out.printf("Volume for $%.2f risk:%n", riskAmount);

for (int sl : slDistances) {
    double volume = sugar.calculateVolume(symbol, sl, riskAmount);
    System.out.printf("  SL %3d points → %.3f lots%n", sl, volume);
}

// Output:
// Volume for $100.00 risk:
//   SL  30 points → 0.333 lots
//   SL  50 points → 0.200 lots
//   SL 100 points → 0.100 lots
//   SL 150 points → 0.067 lots
```

### 4) Calculate volume with min/max checks

```java
String symbol = "XAUUSD";
int slPoints = 50;
double riskAmount = 50.0;

double volume = sugar.calculateVolume(symbol, slPoints, riskAmount);
double[] limits = sugar.getVolumeLimits(symbol);
double minVol = limits[0];
double maxVol = limits[1];

System.out.printf("Calculated volume: %.2f lots%n", volume);
System.out.printf("Broker limits: [%.2f - %.2f]%n", minVol, maxVol);

if (volume < minVol) {
    System.out.printf("⚠️ Volume below minimum! Using %.2f%n", minVol);
    volume = minVol;
} else if (volume > maxVol) {
    System.out.printf("⚠️ Volume above maximum! Using %.2f%n", maxVol);
    volume = maxVol;
}
```

### 5) Multiple symbols with same $ risk

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};
double riskAmount = 50.0;
int slPoints = 50;

System.out.printf("Same $%.2f risk across symbols:%n", riskAmount);

for (String symbol : symbols) {
    double volume = sugar.calculateVolume(symbol, slPoints, riskAmount);
    System.out.printf("  %s: %.2f lots%n", symbol, volume);
}

// Output:
// Same $50.00 risk across symbols:
//   EURUSD: 0.10 lots
//   GBPUSD: 0.08 lots
//   USDJPY: 0.12 lots
```

### 6) Dynamic volume based on volatility

```java
String symbol = "EURUSD";
double riskAmount = 100.0;

// Wider SL in volatile markets
int spread = sugar.getSpread(symbol);
int slPoints = spread > 10 ? 100 : 50; // Wider SL if high spread

double volume = sugar.calculateVolume(symbol, slPoints, riskAmount);

System.out.printf("Volatility-adjusted sizing:%n");
System.out.printf("  Spread: %d points%n", spread);
System.out.printf("  SL: %d points%n", slPoints);
System.out.printf("  Volume: %.2f lots%n", volume);
```

---

## 📌 Important Notes

* **Risk amount:**
  - Specified in account currency (USD, EUR, etc.)
  - NOT in percentage - convert % to $ first
  - Example: 2% of $10,000 = $200

* **Stop Loss points:**
  - Distance in points, not price
  - NOT in pips (unless using pointsToPips())
  - Example: For EURUSD, 50 points = 5 pips (5-digit broker)

* **Volume normalization:**
  - Result automatically normalized to broker's min/max/step
  - May differ slightly from raw calculation
  - Always check returned volume before trading

* **Tick value:**
  - Automatically fetched for symbol
  - Varies by symbol and account currency
  - Affects final volume calculation

* **Error handling:**
  - Throws ApiExceptionMT5 if symbol data unavailable
  - Returns normalized volume (may hit broker limits)
  - Check volume limits separately if needed

**Formula breakdown:**
```
Example: EURUSD
- Risk: $50
- SL: 50 points
- Point: 0.00001
- Tick value: $1 per 0.00001 (mini lot)

valuePerPoint = ($1 / 0.00001) × 0.00001 = $1
volume = $50 / (50 points × $1/point)
       = $50 / $50
       = 1.0 lots

Normalized to 0.10 lots (broker limits)
```

---

## See also

* **Use calculated volume:** [`buyByRisk()`](./buyByRisk.md), [`sellByRisk()`](./sellByRisk.md)
* **Volume helpers:** [`normalizeVolume()`](../1.%20Symbol_helpers/normalizeVolume.md), [`getVolumeLimits()`](../8.%20Advanced_helpers/getVolumeLimits.md)
* **Tick data:** [`getTickValueAndSize()`](../8.%20Advanced_helpers/getTickValueAndSize.md)
* **Balance info:** [`getBalance()`](../9.%20Account_and_position_helpers/getBalance.md)
