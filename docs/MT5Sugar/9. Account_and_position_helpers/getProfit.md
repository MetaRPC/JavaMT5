# 📈 Get Current Profit/Loss

> **Helper method:** retrieves current floating profit/loss for all open positions.

**API Information:**

* **Sugar method:** `MT5Sugar.getProfit()`
* **Returns:** `double` - Current profit/loss
* **Underlying:** `MT5Service.getProfit()`

---

## ⬆️ Output

**Returns:** `double` - Profit/loss in account currency (positive = profit, negative = loss)

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
double profit = sugar.getProfit();
System.out.printf("Current P/L: $%.2f%n", profit);
```

---

## See also

* **Account info:** [`getEquity()`](./getEquity.md), [`getBalance()`](./getBalance.md)
