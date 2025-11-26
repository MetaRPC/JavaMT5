# ⚖️ Normalize Volume to Symbol Rules

> **Convenience method:** adjusts volume (lot size) to meet symbol's min/max/step requirements. Prevents "invalid volume" errors when placing orders.

**API Information:**

* **Sugar method:** `MT5Sugar.normalizeVolume(String symbol, double volume)`
* **Underlying methods:**
  - [`MT5Account.symbolInfoDouble()`](../../MT5Account/2.%20Symbol_information/SymbolInfoDouble.md) with `VOLUME_MIN`, `VOLUME_MAX`, `VOLUME_STEP`
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |
| `volume`  | `double` | ✅       | Volume in lots to normalize              |

---

## ⬆️ Output

**Returns:** `double` - Normalized volume meeting symbol's constraints

**Normalization rules:**
1. Clamp to `[volumeMin, volumeMax]`
2. Round to nearest `volumeStep`

**Examples:**
- Input: `0.05`, Symbol: EURUSD (min=0.01, step=0.01) → Output: `0.05`
- Input: `0.03`, Symbol: XAUUSD (min=0.01, step=0.10) → Output: `0.10`
- Input: `100.0`, Symbol: EURUSD (max=50.0) → Output: `50.0`

---

## 💬 Just the essentials

* **What it is.** Adjusts lot size to broker's allowed values.
* **Why you need it.** Broker rejects invalid volume values.
* **Auto-used.** All trading methods call this internally.
* **Prevents errors.** "Invalid volume" rejections from wrong step.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate volume from risk amount or percentage.
* Ensure calculated volume meets broker requirements.
* Prepare volume before manual order placement.
* Round fractional lot sizes to valid increments.

---

## 🔗 Usage Examples

### 1) Normalize calculated volume

```java
String symbol = "EURUSD";
double calculatedVolume = 0.157; // From risk calculation

double normalized = sugar.normalizeVolume(symbol, calculatedVolume);

System.out.printf("Calculated volume: %.3f%n", calculatedVolume);
System.out.printf("Normalized volume: %.2f%n", normalized);
// Output:
// Calculated volume: 0.157
// Normalized volume: 0.16  (rounded to 0.01 step)
```

### 2) Volume from risk percentage

```java
String symbol = "EURUSD";
double balance = sugar.getBalance();
double riskPercent = 2.0; // 2% of balance

// Calculate risk amount
double riskAmount = balance * (riskPercent / 100.0);

// Calculate volume for 50 point SL
double rawVolume = sugar.calculateVolume(symbol, 50, riskAmount);

// Normalize to broker's requirements
double volume = sugar.normalizeVolume(symbol, rawVolume);

System.out.printf("Balance: $%.2f%n", balance);
System.out.printf("Risk (%.1f%%): $%.2f%n", riskPercent, riskAmount);
System.out.printf("Raw volume: %.3f%n", rawVolume);
System.out.printf("Normalized: %.2f lots%n", volume);
```

### 3) Check volume limits

```java
String symbol = "XAUUSD";
double[] limits = sugar.getVolumeLimits(symbol);
double minVol = limits[0];
double maxVol = limits[1];
double stepVol = limits[2];

System.out.printf("%s volume constraints:%n", symbol);
System.out.printf("  Min: %.2f%n", minVol);
System.out.printf("  Max: %.2f%n", maxVol);
System.out.printf("  Step: %.2f%n", stepVol);

// Test normalization
double[] testVolumes = {0.05, 0.03, 0.15, 100.0};
for (double vol : testVolumes) {
    double normalized = sugar.normalizeVolume(symbol, vol);
    System.out.printf("  %.2f → %.2f%n", vol, normalized);
}

// Output:
// XAUUSD volume constraints:
//   Min: 0.01
//   Max: 50.00
//   Step: 0.01
//   0.05 → 0.05
//   0.03 → 0.03
//   0.15 → 0.15
//   100.00 → 50.00
```

### 4) Handle different step sizes

```java
String[] symbols = {"EURUSD", "XAUUSD", "BTCUSD"};
double targetVolume = 0.05;

System.out.printf("Normalizing %.2f lots across symbols:%n", targetVolume);
for (String symbol : symbols) {
    double[] limits = sugar.getVolumeLimits(symbol);
    double normalized = sugar.normalizeVolume(symbol, targetVolume);

    System.out.printf("  %s (step: %.2f) → %.2f%n",
        symbol, limits[2], normalized);
}

// Output:
// Normalizing 0.05 lots across symbols:
//   EURUSD (step: 0.01) → 0.05
//   XAUUSD (step: 0.01) → 0.05
//   BTCUSD (step: 0.10) → 0.10  (rounded up to step)
```

### 5) Safe volume calculation

```java
String symbol = "GBPUSD";

// Calculate volume from some algorithm
double algorithmVolume = calculateOptimalVolume(); // Returns 0.237

// ❌ BAD: Use raw volume (may be rejected)
// long ticket = sugar.buyMarket(symbol, algorithmVolume, null, null);

// ✅ GOOD: Normalize first (though buyMarket does this automatically)
double safeVolume = sugar.normalizeVolume(symbol, algorithmVolume);
long ticket = sugar.buyMarket(symbol, safeVolume, null, null);

System.out.printf("Algorithm suggested: %.3f lots%n", algorithmVolume);
System.out.printf("Normalized to: %.2f lots%n", safeVolume);
System.out.printf("Order placed: #%d%n", ticket);
```

---

## 📌 Important Notes

* **Normalization algorithm:**
  1. Clamp to [min, max]: `volume = Math.max(min, Math.min(max, volume))`
  2. Round to step: `steps = round(volume / step); return steps * step`

* **Auto-called:** All trading methods (`buyMarket`, `sellMarket`, `buyLimit`, etc.) call this internally.

* **Common volume constraints:**
  - Standard FX: min=0.01, max=100, step=0.01
  - Micro lots: min=0.001, step=0.001
  - Gold: min=0.01, max=50, step=0.01
  - Crypto: varies widely by broker

* **Edge cases:**
  - Volume < min → Returns min volume
  - Volume > max → Returns max volume
  - Between steps → Rounds to nearest step

* **When to use manually:**
  - Risk-based volume calculations
  - Position sizing algorithms
  - Portfolio rebalancing
  - Generally: Let trading methods handle it automatically

**Typical volume rules:**
```
EURUSD: [0.01 - 100.0] step 0.01
XAUUSD: [0.01 - 50.0] step 0.01
BTCUSD: [0.01 - 10.0] step 0.01
```

---

## See also

* **Low-level method:** [`SymbolInfoDouble`](../../MT5Account/2.%20Symbol_information/SymbolInfoDouble.md) - gets volume limits
* **Related:** [`getVolumeLimits()`](./getVolumeLimits.md) - get [min, max, step] array
* **Related:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md) - calculate volume from risk
* **Auto-uses this:** All trading methods normalize volume automatically
