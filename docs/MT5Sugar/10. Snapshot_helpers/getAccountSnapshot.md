# 📸 Get Account Snapshot

> **Snapshot method:** retrieves complete account state in one call. Returns AccountSnapshot object with all key metrics.

**API Information:**

* **Sugar method:** `MT5Sugar.getAccountSnapshot()`
* **Returns:** `AccountSnapshot` - Object containing all account metrics
* **Underlying:** `accountSummary()`, `accountInfoDouble()` calls

---

## ⬆️ Output

**Returns:** `AccountSnapshot` object with fields:

- `long login` - Account number
- `double balance` - Account balance
- `double equity` - Account equity
- `double margin` - Used margin
- `double freeMargin` - Free margin
- `double marginLevel` - Margin level %
- `double profit` - Current profit/loss
- `long leverage` - Account leverage
- `String currency` - Account currency
- `String company` - Broker name

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Examples

### 1) Simple snapshot

```java
var snapshot = sugar.getAccountSnapshot();

System.out.printf("Account: %d%n", snapshot.login);
System.out.printf("Balance: $%.2f%n", snapshot.balance);
System.out.printf("Equity: $%.2f%n", snapshot.equity);
System.out.printf("Profit: $%.2f%n", snapshot.profit);
System.out.printf("Margin level: %.2f%%%n", snapshot.marginLevel);
```

### 2) Using toString()

```java
var snapshot = sugar.getAccountSnapshot();
System.out.println(snapshot.toString());

// Output:
// Account[login=12345678, balance=10000.00, equity=10150.00, margin=500.00,
//         free=9650.00, level=2030.00%, profit=150.00, leverage=100, currency=USD]
```

### 3) Risk check

```java
var snapshot = sugar.getAccountSnapshot();

if (snapshot.marginLevel < 200.0) {
    System.out.printf("⚠️ Low margin level: %.2f%%%n", snapshot.marginLevel);
}
```

---

## 📌 Important Notes

* **One call:** Fetches all account data in single operation
* **Immutable:** Snapshot is point-in-time data
* **toString():** Built-in formatted string representation

---

## See also

* **Symbol snapshot:** [`getSymbolSnapshot()`](./getSymbolSnapshot.md)
* **Individual getters:** [`getBalance()`](../9.%20Account_and_position_helpers/getBalance.md), [`getEquity()`](../9.%20Account_and_position_helpers/getEquity.md)
