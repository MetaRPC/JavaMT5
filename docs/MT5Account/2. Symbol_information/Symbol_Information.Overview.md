# MT5Account · Symbol Information - Overview

> Symbol properties, current quotes, trading rules, and symbol availability. Use this page to choose the right API for accessing market data and symbol parameters.

## 📁 What lives here

* **[SymbolInfoTick](./SymbolInfoTick.md)** - **current quote** for a symbol (bid, ask, last, volume, time).
* **[SymbolsTotal](./SymbolsTotal.md)** - **count** of available symbols (all or selected only).
* **[SymbolName](./SymbolName.md)** - get **symbol name** by index position.
* **[SymbolSelect](./SymbolSelect.md)** - **enable/disable** symbol in Market Watch.
* **[SymbolExist](./SymbolExist.md)** - **check if symbol exists** on server.
* **[SymbolIsSynchronized](./SymbolIsSynchronized.md)** - **check if symbol data** is synchronized.
* **[SymbolInfoDouble](./SymbolInfoDouble.md)** - **single double property** (bid, ask, point, volume min/max, etc.).
* **[SymbolInfoInteger](./SymbolInfoInteger.md)** - **single integer property** (digits, spread, stops level, etc.).
* **[SymbolInfoString](./SymbolInfoString.md)** - **single string property** (description, base/profit currency, path).
* **[TickValueWithSize](./TickValueWithSize.md)** - **batch tick values** for multiple symbols at once.

---

## 🧭 Plain English

* **SymbolInfoTick** → your **live price** for a symbol (pull mode).
* **SymbolsTotal / SymbolName** → **list all available symbols** from terminal.
* **SymbolSelect** → **add/remove symbols** from Market Watch.
* **SymbolExist / SymbolIsSynchronized** → **validate** symbol before trading.
* **SymbolInfo*** (Double/Integer/String) → **symbol properties** one at a time.
* **TickValueWithSize** → **efficient batch** retrieval for multiple symbols.

> Rule of thumb: need **price** → `SymbolInfoTick`; need **trading rules** → `SymbolInfo*` (digits, point, min volume); need **list** → `SymbolsTotal` + `SymbolName`.

---

## Quick choose

| If you need…                                     | Use                    | Returns                    | Key inputs                          |
| ------------------------------------------------ | ---------------------- | -------------------------- | ----------------------------------- |
| Current price for symbol                         | `SymbolInfoTick`       | Bid, ask, last, volume     | `symbol`                            |
| Count of available symbols                       | `SymbolsTotal`         | Symbol count               | `selected_only` (bool)              |
| Symbol name by position                          | `SymbolName`           | Symbol name                | `index`, `selected_only`            |
| Add/remove symbol in Market Watch                | `SymbolSelect`         | Success/failure            | `symbol`, `select` (bool)           |
| Check if symbol exists                           | `SymbolExist`          | Boolean                    | `symbol`                            |
| Check symbol synchronization                     | `SymbolIsSynchronized` | Boolean                    | `symbol`                            |
| Single double property (bid, point, volume, etc.)| `SymbolInfoDouble`     | Single `double`            | `symbol`, property enum             |
| Single integer property (digits, spread, etc.)   | `SymbolInfoInteger`    | Single `long`              | `symbol`, property enum             |
| Single string property (description, currency)   | `SymbolInfoString`     | Single `String`            | `symbol`, property enum             |
| Tick values for multiple symbols                 | `TickValueWithSize`    | Batch tick values          | `symbols: String[]`                 |

---

## ❌ Cross‑refs & gotchas

* **Digits** → decimal places for price formatting (e.g., 5 for EURUSD = 1.12345).
* **Point** → smallest price change (e.g., 0.00001 for 5-digit EURUSD).
* **Volume Min/Max/Step** → allowed lot sizes (validate before OrderSend).
* **Stops Level** → minimum distance for SL/TP from current price.
* **Spread** → difference between ask and bid (in points).
* **SymbolSelect** → must enable symbol before getting quotes.
* **Synchronization** → symbol data may be outdated if not synchronized.
* **Tick Value** → monetary value per 1 point move (for risk calculations).

---

## 🟢 Minimal snippets

```java
// Get current price for EURUSD
var quote = account.symbolInfoTick("EURUSD");
var data = quote.getData();
System.out.printf("EURUSD: Bid=%.5f, Ask=%.5f, Spread=%.5f%n",
    data.getBid(), data.getAsk(), data.getAsk() - data.getBid());
```

```java
// List all available symbols
var total = account.symbolsTotal(false);
int count = total.getData().getTotal();
for (int i = 0; i < count; i++) {
    var name = account.symbolName(i, false);
    System.out.println(name.getData().getName());
}
```

```java
// Enable symbol in Market Watch
account.symbolSelect("GBPUSD", true);
```

```java
// Get symbol digits (decimal places)
var reply = account.symbolInfoInteger(
    "EURUSD",
    Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS
);
long digits = reply.getData().getValue();
System.out.printf("EURUSD digits: %d%n", digits);
```

```java
// Get minimum volume for symbol
var reply = account.symbolInfoDouble(
    "XAUUSD",
    Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MIN
);
double minVolume = reply.getData().getValue();
System.out.printf("Min volume: %.2f lots%n", minVolume);
```

```java
// Validate symbol before trading
var exists = account.symbolExist("EURUSD");
var synced = account.symbolIsSynchronized("EURUSD");
if (exists.getData().getValue() && synced.getData().getValue()) {
    System.out.println("✅ Symbol ready for trading");
}
```

```java
// Get tick values for multiple symbols
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};
var reply = account.tickValueWithSize(symbols);
for (var tickValue : reply.getData().getTickValuesList()) {
    System.out.printf("%s: $%.2f per lot%n",
        tickValue.getSymbol(), tickValue.getTickValue());
}
```

---

## See also

* **Subscriptions:** [`OnSymbolTick`](../6.%20Subscriptions/OnSymbolTick.md) - real-time price stream
* **Trading calculations:** [`OrderCalcMargin`](../5.%20Trading/OrderCalcMargin.md), [`OrderCalcProfit`](../5.%20Trading/OrderCalcProfit.md)
* **Market depth:** [`MarketBookAdd`](../4.%20Market_depth_DOM/MarketBookAdd.md) - Level II quotes
