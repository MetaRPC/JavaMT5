# 💰 Get Account Balance

> **Helper method:** retrieves current account balance.

**API Information:**

* **Sugar method:** `MT5Sugar.getBalance()`
* **Returns:** `double` - Account balance
* **Underlying:** `MT5Service.getBalance()`

---

## ⬆️ Output

**Returns:** `double` - Balance in account currency

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Example

```java
double balance = sugar.getBalance();
System.out.printf("Account balance: $%.2f%n", balance);
```

---

## See also

* **Other account info:** [`getEquity()`](./getEquity.md), [`getMargin()`](./getMargin.md), [`getFreeMargin()`](./getFreeMargin.md)
* **Full snapshot:** [`getAccountSnapshot()`](../10.%20Snapshot_helpers/getAccountSnapshot.md)
