# 🔧 Normalize Price (Alias)

> **Alias method:** normalizes price to symbol's digit count. Identical to `normalizePrice()`.

**API Information:**

* **Sugar method:** `MT5Sugar.normalizePriceDigits(String symbol, double price)`
* **Returns:** `double` - Normalized price
* **Underlying:** [`normalizePrice()`](../1.%20Symbol_helpers/normalizePrice.md) - internally calls normalizePrice()

---

## 🔽 Input

| Parameter | Type     | Required | Description              |
| --------- | -------- | -------- | ------------------------ |
| `symbol`  | `String` | ✅       | Symbol name              |
| `price`   | `double` | ✅       | Price to normalize       |

---

## ⬆️ Output

**Returns:** `double` - Normalized price

---

## 🔗 Usage Example

```java
double price = sugar.normalizePriceDigits("EURUSD", 1.123456789);
System.out.printf("Normalized: %.5f%n", price); // 1.12346
```

---

## See also

* **Actual implementation:** [`normalizePrice()`](../1.%20Symbol_helpers/normalizePrice.md)
