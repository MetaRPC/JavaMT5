# 🎯 Convert Points to Pips

> **Convenience method:** converts points to pips, automatically handling the difference between 5-digit brokers (1 pip = 10 points) and 3-digit brokers (1 pip = 1 point).

**API Information:**

* **Sugar method:** `MT5Sugar.pointsToPips(String symbol, double points)`
* **Underlying method:** [`getDigits()`](./getDigits.md) (which calls [`MT5Account.symbolInfoInteger()`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md))
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |
| `points`  | `double` | ✅       | Points value to convert                  |

---

## ⬆️ Output

**Returns:** `double` - Pips value

**Conversion rules:**
- **5-digit brokers** (EURUSD, GBPUSD, etc.): **1 pip = 10 points**
- **3-digit brokers** (USDJPY): **1 pip = 1 point**
- **2-digit brokers** (Gold, indices): **1 pip = 1 point**

**Examples:**
- EURUSD (5 digits): 50 points → **5.0 pips**
- USDJPY (3 digits): 50 points → **50.0 pips**
- XAUUSD (2 digits): 50 points → **50.0 pips**

---

## 💬 Just the essentials

* **What it is.** Convert broker's points to standard pips.
* **Why you need it.** Pips are industry-standard, points are broker-specific.
* **Auto-detects.** Handles 5-digit vs 3-digit brokers automatically.
* **For display.** Use pips when showing results to users.

---

## 🎯 Purpose

Use this method when you need to:

* Display point values in industry-standard pips.
* Compare trading results across different brokers.
* Report strategy performance in pips (standard measure).
* Convert internal calculations (points) to user-friendly format (pips).

---

## 🔗 Usage Examples

### 1) Convert points to pips

```java
String symbol = "EURUSD";
double points = 50;

double pips = sugar.pointsToPips(symbol, points);

System.out.printf("%s: %.0f points = %.1f pips%n",
    symbol, points, pips);
// Output: EURUSD: 50 points = 5.0 pips
```

### 2) Handle different broker types

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY", "XAUUSD"};
double points = 100;

System.out.printf("Converting %.0f points to pips:%n", points);
for (String symbol : symbols) {
    int digits = sugar.getDigits(symbol);
    double pips = sugar.pointsToPips(symbol, points);

    System.out.printf("  %s (%d digits): %.1f pips%n",
        symbol, digits, pips);
}

// Output:
// Converting 100 points to pips:
//   EURUSD (5 digits): 10.0 pips
//   GBPUSD (5 digits): 10.0 pips
//   USDJPY (3 digits): 100.0 pips
//   XAUUSD (2 digits): 100.0 pips
```

### 3) Display trade profit in pips

```java
String symbol = "EURUSD";
double entryPrice = 1.12340;
double exitPrice = 1.12390;
double point = sugar.getPoint(symbol);

// Calculate profit in points
double profitPoints = (exitPrice - entryPrice) / point;

// Convert to pips for display
double profitPips = sugar.pointsToPips(symbol, profitPoints);

System.out.printf("Trade closed:%n");
System.out.printf("  Entry: %.5f%n", entryPrice);
System.out.printf("  Exit: %.5f%n", exitPrice);
System.out.printf("  Profit: %.0f points (%.1f pips)%n",
    profitPoints, profitPips);

// Output:
// Trade closed:
//   Entry: 1.12340
//   Exit: 1.12390
//   Profit: 50 points (5.0 pips)
```

### 4) Calculate strategy statistics in pips

```java
String symbol = "GBPUSD";

// Example: Strategy generates average 35 points per trade
double avgPointsPerTrade = 35;
double avgPipsPerTrade = sugar.pointsToPips(symbol, avgPointsPerTrade);

// Example: Max drawdown was 150 points
double maxDrawdownPoints = 150;
double maxDrawdownPips = sugar.pointsToPips(symbol, maxDrawdownPoints);

System.out.printf("Strategy statistics (%s):%n", symbol);
System.out.printf("  Avg profit: %.0f points (%.1f pips)%n",
    avgPointsPerTrade, avgPipsPerTrade);
System.out.printf("  Max drawdown: %.0f points (%.1f pips)%n",
    maxDrawdownPoints, maxDrawdownPips);

// Output:
// Strategy statistics (GBPUSD):
//   Avg profit: 35 points (3.5 pips)
//   Max drawdown: 150 points (15.0 pips)
```

### 5) Compare stop loss across symbols

```java
String[] symbols = {"EURUSD", "USDJPY", "XAUUSD"};
double slPoints = 50; // Same SL in points for all

System.out.printf("Stop Loss comparison (%.0f points):%n", slPoints);
System.out.println("Symbol   | Digits | Points | Pips   | Price Units");
System.out.println("---------|--------|--------|--------|------------");

for (String symbol : symbols) {
    int digits = sugar.getDigits(symbol);
    double pips = sugar.pointsToPips(symbol, slPoints);
    double point = sugar.getPoint(symbol);
    double priceUnits = slPoints * point;

    System.out.printf("%8s | %6d | %6.0f | %6.1f | %." + digits + "f%n",
        symbol, digits, slPoints, pips, priceUnits);
}

// Output:
// Stop Loss comparison (50 points):
// Symbol   | Digits | Points | Pips   | Price Units
// ---------|--------|--------|--------|------------
//   EURUSD |      5 |     50 |    5.0 | 0.00050
//   USDJPY |      3 |     50 |   50.0 | 0.050
//   XAUUSD |      2 |     50 |   50.0 | 0.50
```

### 6) Real-time spread monitoring in pips

```java
String symbol = "EURUSD";

System.out.printf("Monitoring %s spread:%n", symbol);

for (int i = 0; i < 5; i++) {
    int spreadPoints = sugar.getSpread(symbol);
    double spreadPips = sugar.pointsToPips(symbol, spreadPoints);

    System.out.printf("  Spread: %2d points (%.1f pips)%n",
        spreadPoints, spreadPips);

    Thread.sleep(1000);
}

// Output:
// Monitoring EURUSD spread:
//   Spread: 10 points (1.0 pips)
//   Spread: 12 points (1.2 pips)
//   Spread: 11 points (1.1 pips)
//   Spread: 10 points (1.0 pips)
//   Spread: 13 points (1.3 pips)
```

---

## 📌 Important Notes

* **Pip vs Point:**
  - **Point** = smallest price increment broker offers (broker-specific)
  - **Pip** = standard industry unit (0.0001 for FX, 0.01 for JPY)

* **Conversion rules:**
  ```java
  if (digits <= 3) {
      pips = points;        // 1 pip = 1 point
  } else {
      pips = points / 10.0; // 1 pip = 10 points
  }
  ```

* **Common digit configurations:**
  - **5 digits** (0.00001): EURUSD, GBPUSD, AUDUSD → 10 points = 1 pip
  - **3 digits** (0.001): USDJPY, EURJPY → 1 point = 1 pip
  - **2 digits** (0.01): XAUUSD, indices → 1 point = 1 pip

* **When to use:**
  - ✅ Displaying results to users
  - ✅ Comparing across brokers
  - ✅ Strategy performance reports
  - ✅ Industry-standard communication
  - ❌ Internal calculations (use points)

* **Reverse conversion:**
  ```java
  // To convert pips back to points:
  int digits = sugar.getDigits(symbol);
  double points = (digits > 3) ? pips * 10 : pips;
  ```

**Digit examples:**
```
EURUSD (5 digits):
  Price: 1.12345
  Point: 0.00001
  Pip:   0.0001 (4th decimal)
  50 points = 5 pips

USDJPY (3 digits):
  Price: 110.123
  Point: 0.001
  Pip:   0.01 (2nd decimal)
  50 points = 50 pips

XAUUSD (2 digits):
  Price: 1850.50
  Point: 0.01
  Pip:   0.01 (same as point)
  50 points = 50 pips
```

---

## See also

* **Low-level method:** [`SymbolInfoInteger`](../../MT5Account/2.%20Symbol_information/SymbolInfoInteger.md) - gets digits
* **Related:** [`getDigits()`](./getDigits.md) - get decimal places
* **Related:** [`getPoint()`](./getPoint.md) - get point size
* **Related:** [`getSpread()`](./getSpread.md) - get spread in points
