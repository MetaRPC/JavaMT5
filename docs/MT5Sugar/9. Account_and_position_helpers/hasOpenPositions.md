# ✅ Check if Any Positions Open

> **Helper method:** checks if there are any open positions.

**API Information:**

* **Sugar method:** `MT5Sugar.hasOpenPositions()`
* **Returns:** `boolean` - true if positions exist
* **Underlying:** `MT5Service.positionsTotal()`

---

## ⬆️ Output

**Returns:** `boolean` - `true` if open positions exist, `false` otherwise

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
if (sugar.hasOpenPositions()) {
    System.out.println("⚠️ Open positions detected");
} else {
    System.out.println("✅ No open positions");
}
```

---

## See also

* **Position count:** [`getPositionCount()`](./getPositionCount.md)
