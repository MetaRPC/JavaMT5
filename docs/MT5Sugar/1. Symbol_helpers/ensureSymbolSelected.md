# ✅ Ensure Symbol Selected (Market Watch)

> **Convenience method:** automatically enables symbol in Market Watch if not already selected. Simplifies symbol preparation before trading.

**API Information:**

* **Sugar method:** `MT5Sugar.ensureSymbolSelected(String symbol)`
* **Underlying method:** [`MT5Account.symbolSelect()`](../../MT5Account/2.%20Symbol_information/SymbolSelect.md)
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD")             |

---

## ⬆️ Output

**Returns:** `void` (throws exception on failure)

---

## 💬 Just the essentials

* **What it is.** Ensures symbol is visible/active in Market Watch.
* **Why you need it.** Many operations require symbol to be selected first.
* **Auto-called.** All trading methods in MT5Sugar call this automatically.
* **No return.** Just enables the symbol, no return value needed.

---

## 🎯 Purpose

Use this method when you need to:

* Prepare symbol before manual quote retrieval.
* Enable symbol before charting or analysis.
* Ensure symbol availability before batch operations.

**Note:** All `buyMarket()`, `sellMarket()`, and pending order methods call this automatically, so you rarely need to call it manually.

---

## 🔗 Usage Example

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.MT5Service;
import io.metarpc.mt5.MT5Sugar;

public class Example {
    public static void main(String[] args) throws Exception {
        MT5Account account = new MT5Account(12345678, "password");
        MT5Service service = new MT5Service(account);
        MT5Sugar sugar = new MT5Sugar(service);

        account.connect("demo.mt5server.com", 443, "EURUSD");

        // Manually ensure symbol is selected
        sugar.ensureSymbolSelected("GBPUSD");

        System.out.println("GBPUSD is now available in Market Watch");

        // Now you can get quotes, place orders, etc.
        double bid = sugar.getBid("GBPUSD");
        System.out.printf("GBPUSD Bid: %.5f%n", bid);
    }
}
```

---

## 📌 Important Notes

* **Idempotent:** Safe to call multiple times - won't fail if already selected.
* **Auto-called:** All trading methods call this internally.
* **Market Watch:** Makes symbol visible in MT5 terminal's Market Watch window.
* **Required first:** Some symbols must be selected before quotes are available.
* **No performance cost:** If already selected, operation is very fast.

---

## See also

* **Low-level method:** [`SymbolSelect`](../../MT5Account/2.%20Symbol_information/SymbolSelect.md) - underlying implementation
* **Related:** [`getAsk()`](./getAsk.md), [`getBid()`](./getBid.md) - get current prices
* **Trading:** [`buyMarket()`](../2.%20Market_orders/buyMarket.md) - auto-calls this method
