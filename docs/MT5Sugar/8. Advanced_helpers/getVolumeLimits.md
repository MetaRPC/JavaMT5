# 📏 Get Volume Limits (Min/Max/Step)

> **Helper method:** retrieves symbol's volume limits - minimum, maximum, and step size. Essential for volume validation.

**API Information:**

* **Sugar method:** `MT5Sugar.getVolumeLimits(String symbol)`
* **Returns:** `double[3]` - Array with [min, max, step]
* **Underlying:** `symbolInfoDouble()` calls for VOLUME_MIN, VOLUME_MAX, VOLUME_STEP

---

## 🔽 Input

| Parameter | Type     | Required | Description              |
| --------- | -------- | -------- | ------------------------ |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD") |

---

## ⬆️ Output

**Returns:** `double[3]` - Array: `[volumeMin, volumeMax, volumeStep]`

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
String symbol = "EURUSD";
double[] limits = sugar.getVolumeLimits(symbol);

System.out.printf("%s volume limits:%n", symbol);
System.out.printf("  Min: %.2f lots%n", limits[0]);
System.out.printf("  Max: %.2f lots%n", limits[1]);
System.out.printf("  Step: %.2f lots%n", limits[2]);

// Output:
// EURUSD volume limits:
//   Min: 0.01 lots
//   Max: 100.00 lots
//   Step: 0.01 lots
```

---

## See also

* **Normalize volume:** [`normalizeVolume()`](../1.%20Symbol_helpers/normalizeVolume.md)
