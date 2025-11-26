# 🎯 Get Tick Value and Size

> **Helper method:** retrieves tick value and tick size for symbol in one call. Used for profit calculations and volume sizing.

**API Information:**

* **Sugar method:** `MT5Sugar.getTickValueAndSize(String symbol)`
* **Returns:** `double[2]` - Array with [tickValue, tickSize]
* **Underlying:** `tickValueWithSize()` service call

---

## 🔽 Input

| Parameter | Type     | Required | Description              |
| --------- | -------- | -------- | ------------------------ |
| `symbol`  | `String` | ✅       | Symbol name              |

---

## ⬆️ Output

**Returns:** `double[2]` - Array: `[tickValue, tickSize]`

**Throws:** `ApiExceptionMT5` if no data available

---

## 🔗 Usage Example

```java
String symbol = "EURUSD";
double[] tickData = sugar.getTickValueAndSize(symbol);

double tickValue = tickData[0];
double tickSize = tickData[1];

System.out.printf("%s tick data:%n", symbol);
System.out.printf("  Tick value: $%.2f%n", tickValue);
System.out.printf("  Tick size: %.5f%n", tickSize);

// Output:
// EURUSD tick data:
//   Tick value: $1.00
//   Tick size: 0.00001
```

---

## See also

* **Volume calculation:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md) - uses tick data
