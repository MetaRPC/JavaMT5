# 🔒 Get Used Margin

> **Helper method:** retrieves currently used margin (locked funds for open positions).

**API Information:**

* **Sugar method:** `MT5Sugar.getMargin()`
* **Returns:** `double` - Used margin
* **Underlying:** `MT5Service.getMargin()`

---

## ⬆️ Output

**Returns:** `double` - Used margin in account currency

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
double margin = sugar.getMargin();
System.out.printf("Used margin: $%.2f%n", margin);
```

---

## See also

* **Free margin:** [`getFreeMargin()`](./getFreeMargin.md)
