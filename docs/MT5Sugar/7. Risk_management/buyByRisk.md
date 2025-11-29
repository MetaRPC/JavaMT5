# 🟢 BUY with Automatic Risk-Based Volume

> **Risk management method:** opens BUY market order with automatic volume calculation based on risk amount and SL distance. All-in-one position sizing + execution.

**API Information:**

* **Sugar method:** `MT5Sugar.buyByRisk(String symbol, double stopLossPoints, double riskAmount, double takeProfitPoints)`
* **Underlying methods:**
  - [`calculateVolume()`](./calculateVolume.md) - calculate position size
  - [`buyMarket()`](../2.%20Market_orders/buyMarket.md) - place order
  - [`getPoint()`](../1.%20Symbol_helpers/getPoint.md), `symbolInfoTick()` - get current prices
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter          | Type     | Required | Description                                |
| ------------------ | -------- | -------- | ------------------------------------------ |
| `symbol`           | `String` | ✅       | Symbol name (e.g., "EURUSD")               |
| `stopLossPoints`   | `double` | ✅       | SL distance in points                      |
| `riskAmount`       | `double` | ✅       | Risk amount in account currency            |
| `takeProfitPoints` | `double` | ✅       | TP distance in points (0 = no TP)          |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number

**Throws:** `ApiExceptionMT5` if order fails

**Execution:**
1. Calculates volume using `calculateVolume()`
2. Gets current Ask price
3. Calculates SL price: `Ask - (stopLossPoints × point)`
4. Calculates TP price: `Ask + (takeProfitPoints × point)` (if > 0)
5. Places BUY market order

---

## 💬 Just the essentials

* **What it is.** BUY order with auto-calculated volume from $ risk.
* **Why you need it.** One-step risk-managed entry - no manual calculations.
* **Use case.** Consistent $ risk per trade (e.g., always risk $50).

---

## 🔗 Usage Examples

### 1) Simple BUY with $50 risk

```java
String symbol = "EURUSD";
double riskAmount = 50.0;   // Risk $50
int slPoints = 50;          // 50 points SL
int tpPoints = 100;         // 100 points TP

long ticket = sugar.buyByRisk(symbol, slPoints, riskAmount, tpPoints);

System.out.printf("✅ BUY order placed: #%d%n", ticket);
System.out.printf("   Risk: $%.2f | SL: %d points | TP: %d points%n",
    riskAmount, slPoints, tpPoints);
```

### 2) BUY with 2% account risk

```java
String symbol = "GBPUSD";
double balance = sugar.getBalance();
double riskPercent = 2.0;
double riskAmount = balance * (riskPercent / 100.0);

long ticket = sugar.buyByRisk(symbol, 100, riskAmount, 200);

System.out.printf("✅ BUY with 2%% risk:%n");
System.out.printf("   Balance: $%.2f%n", balance);
System.out.printf("   Risk: $%.2f (%.1f%%)%n", riskAmount, riskPercent);
System.out.printf("   Ticket: #%d%n", ticket);
```

### 3) BUY without TP (manual exit)

```java
long ticket = sugar.buyByRisk("USDJPY", 50, 100.0, 0);

System.out.printf("✅ BUY: #%d | Risk $100 | No TP (manual exit)%n", ticket);
```

---

## 📌 Important Notes

* **Volume calculation:** Automatic based on risk and SL points
* **SL placement:** `Ask - (stopLossPoints × point)`
* **TP placement:** `Ask + (takeProfitPoints × point)` (if > 0)
* **Pass 0 for TP:** If you don't want automatic TP

---

## See also

* **SELL version:** [`sellByRisk()`](./sellByRisk.md)
* **Volume calculator:** [`calculateVolume()`](./calculateVolume.md)
* **Manual volume:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md)
