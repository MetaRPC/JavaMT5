# 📐 Convert Points to Pips

> **Helper method:** converts points to pips based on symbol's digit count. Handles 3-digit (JPY) vs 5-digit (standard) brokers.

**API Information:**

* **Sugar method:** `MT5Sugar.pointsToPips(String symbol, double points)`
* **Returns:** `double` - Pips value
* **Formula:**
  - 3 digits or less: 1 pip = 1 point
  - 5 digits: 1 pip = 10 points

---

## 🔽 Input

| Parameter | Type     | Required | Description              |
| --------- | -------- | -------- | ------------------------ |
| `symbol`  | `String` | ✅       | Symbol name              |
| `points`  | `double` | ✅       | Points to convert        |

---

## ⬆️ Output

**Returns:** `double` - Pips value

---

## 🔗 Usage Example

```java
String symbol1 = "EURUSD"; // 5 digits
String symbol2 = "USDJPY"; // 3 digits

double pips1 = sugar.pointsToPips(symbol1, 50); // 50 points
double pips2 = sugar.pointsToPips(symbol2, 50); // 50 points

System.out.printf("EURUSD: 50 points = %.1f pips%n", pips1); // 5.0 pips
System.out.printf("USDJPY: 50 points = %.1f pips%n", pips2); // 50.0 pips
```

---

## See also

* **Get point size:** [`getPoint()`](../1.%20Symbol_helpers/getPoint.md)
* **Get digits:** [`getDigits()`](../1.%20Symbol_helpers/getDigits.md)
