# 🔢 Get Position Count

> **Helper method:** retrieves count of open positions.

**API Information:**

* **Sugar method:** `MT5Sugar.getPositionCount()`
* **Returns:** `int` - Number of open positions
* **Underlying:** `MT5Service.positionsTotal()`

---

## ⬆️ Output

**Returns:** `int` - Count of open positions

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
int count = sugar.getPositionCount();
System.out.printf("Open positions: %d%n", count);
```

---

## See also

* **Check existence:** [`hasOpenPositions()`](./hasOpenPositions.md)
