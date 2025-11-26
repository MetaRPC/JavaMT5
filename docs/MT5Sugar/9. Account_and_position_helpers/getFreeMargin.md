# 💸 Get Free Margin

> **Helper method:** retrieves free margin (available funds for new positions).

**API Information:**

* **Sugar method:** `MT5Sugar.getFreeMargin()`
* **Returns:** `double` - Free margin
* **Underlying:** `MT5Service.getFreeMargin()`

---

## ⬆️ Output

**Returns:** `double` - Free margin in account currency

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
double freeMargin = sugar.getFreeMargin();
System.out.printf("Free margin: $%.2f%n", freeMargin);
```

---

## See also

* **Used margin:** [`getMargin()`](./getMargin.md)
