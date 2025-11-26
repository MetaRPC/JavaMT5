# 📊 Get Account Equity

> **Helper method:** retrieves current account equity (balance + floating profit/loss).

**API Information:**

* **Sugar method:** `MT5Sugar.getEquity()`
* **Returns:** `double` - Account equity
* **Underlying:** `MT5Service.getEquity()`

---

## ⬆️ Output

**Returns:** `double` - Equity in account currency

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
double equity = sugar.getEquity();
System.out.printf("Account equity: $%.2f%n", equity);
```

---

## See also

* **Other account info:** [`getBalance()`](./getBalance.md), [`getProfit()`](./getProfit.md)
