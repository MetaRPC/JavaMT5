# 🔧 Normalize Lots (Alias)

> **Alias method:** normalizes volume to symbol's volume step. Identical to `normalizeVolume()`.

**API Information:**

* **Sugar method:** `MT5Sugar.normalizeLots(String symbol, double lots)`
* **Returns:** `double` - Normalized volume
* **Underlying:** [`normalizeVolume()`](../1.%20Symbol_helpers/normalizeVolume.md) - internally calls normalizeVolume()

---

## 🔽 Input

| Parameter | Type     | Required | Description              |
| --------- | -------- | -------- | ------------------------ |
| `symbol`  | `String` | ✅       | Symbol name              |
| `lots`    | `double` | ✅       | Volume to normalize      |

---

## ⬆️ Output

**Returns:** `double` - Normalized volume

---

## 🔗 Usage Example

```java
double volume = sugar.normalizeLots("EURUSD", 0.137);
System.out.printf("Normalized: %.2f lots%n", volume); // 0.13 or 0.14
```

---

## See also

* **Actual implementation:** [`normalizeVolume()`](../1.%20Symbol_helpers/normalizeVolume.md)
