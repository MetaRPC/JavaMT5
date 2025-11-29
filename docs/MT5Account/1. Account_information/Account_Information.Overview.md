# MT5Account · Account Information - Overview

> Account balance, equity, margin, leverage, currency, and other account properties. Use this page to choose the right API for accessing account state.

## 📁 What lives here

* **[AccountSummary](./AccountSummary.md)** - **all account info** at once (balance, equity, margin, leverage, profit, etc.).
* **[AccountInfoDouble](./AccountInfoDouble.md)** - **single double value** from account (balance, equity, margin, profit, credit, etc.).
* **[AccountInfoInteger](./AccountInfoInteger.md)** - **single integer value** from account (login, leverage, limit orders, etc.).
* **[AccountInfoString](./AccountInfoString.md)** - **single string value** from account (name, server, currency, company).

---

## 🧭 Plain English

* **AccountSummary** → the **one-stop shop** for complete account snapshot (balance, equity, margin level, etc.).
* **AccountInfoDouble** → grab **one numeric property** when you need just balance or margin.
* **AccountInfoInteger** → grab **one integer property** like login number or leverage.
* **AccountInfoString** → grab **one text property** like account name or currency.

> Rule of thumb: need **full snapshot** → `AccountSummary`; need **one specific value** → `AccountInfo*` (Double/Integer/String).

---

## Quick choose

| If you need…                                     | Use                  | Returns                    | Key inputs                          |
| ------------------------------------------------ | -------------------- | -------------------------- | ----------------------------------- |
| Complete account snapshot (all values)           | `AccountSummary`     | Full account data object   | *(none)*                            |
| One numeric value (balance, equity, margin, etc.)| `AccountInfoDouble`  | Single `double`            | Property enum (BALANCE, EQUITY, etc.) |
| One integer value (login, leverage, etc.)        | `AccountInfoInteger` | Single `long`              | Property enum (LOGIN, LEVERAGE, etc.) |
| One text value (name, currency, server, etc.)    | `AccountInfoString`  | Single `String`            | Property enum (NAME, CURRENCY, etc.) |

---

## ❌ Cross‑refs & gotchas

* **Margin Level** = (Equity / Margin) × 100 - watch for stop-out level.
* **Free Margin** = Equity - Margin - available for new positions.
* **AccountSummary** includes everything; use it for dashboards.
* **AccountInfo*** methods are lighter if you only need one property.
* **Currency** affects how profits are calculated - always check account currency.
* **Leverage** determines margin requirements - higher leverage = less margin needed.

---

## 🟢 Minimal snippets

```java
// Get complete account snapshot
var summary = account.accountSummary();
var data = summary.getData();
System.out.printf("Balance: $%.2f, Equity: $%.2f, Leverage: 1:%d%n",
    data.getAccountBalance(), data.getAccountEquity(), data.getAccountLeverage());
```

```java
// Get single property - account balance
var reply = account.accountInfoDouble(
    Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_BALANCE
);
double balance = reply.getData().getRequestedValue();
System.out.printf("Balance: $%.2f%n", balance);
```

```java
// Get account leverage
var reply = account.accountInfoInteger(
    Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType.ACCOUNT_LEVERAGE
);
long leverage = reply.getData().getRequestedValue();
System.out.printf("Leverage: 1:%d%n", leverage);
```

```java
// Get account currency
var reply = account.accountInfoString(
    Mt5TermApiAccountInformation.AccountInfoStringPropertyType.ACCOUNT_CURRENCY
);
String currency = reply.getData().getRequestedValue();
System.out.printf("Currency: %s%n", currency);
```

```java
// Check account balance and equity
var summary = account.accountSummary();
var data = summary.getData();
double balance = data.getAccountBalance();
double equity = data.getAccountEquity();
if (equity < balance * 0.8) {
    System.out.println("⚠️ Warning: Equity below 80% of balance!");
}
```

---

## See also

* **Subscriptions:** [`OnPositionProfit`](../6.%20Subscriptions/OnPositionProfit.md) - real-time equity/profit updates
* **Trading calculations:** [`OrderCalcMargin`](../5.%20Trading/OrderCalcMargin.md) - calculate required margin before trading
* **Positions:** [`PositionsTotal`](../3.%20Positions_and_orders/PositionsTotal.md) - count open positions
