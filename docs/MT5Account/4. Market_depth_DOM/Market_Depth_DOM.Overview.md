# MT5Account · Market Depth (DOM) — Overview

> Level II quotes, order book data, market depth subscription. Use this page to choose the right API for accessing deep market liquidity information.

## 📁 What lives here

* **[MarketBookAdd](./MarketBookAdd.md)** — **subscribe** to Market Depth (DOM/Level II) for a symbol.
* **[MarketBookGet](./MarketBookGet.md)** — **retrieve** current order book data for a subscribed symbol.
* **[MarketBookRelease](./MarketBookRelease.md)** — **unsubscribe** from Market Depth updates.

---

## 🧭 Plain English

* **MarketBookAdd** → **enable** Level II quotes for a symbol (must do first).
* **MarketBookGet** → **pull** current order book (bid/ask sides with volumes).
* **MarketBookRelease** → **disable** Level II when done (free resources).

> Rule of thumb: **Add** → **Get** → **Release**. Always subscribe before retrieving data, and unsubscribe when finished.

---

## Quick choose

| If you need…                                     | Use                | Returns                    | Key inputs                          |
| ------------------------------------------------ | ------------------ | -------------------------- | ----------------------------------- |
| Enable Market Depth for symbol                   | `MarketBookAdd`    | Success/failure            | `symbol`                            |
| Get current order book (bid/ask sides)           | `MarketBookGet`    | Order book entries         | `symbol` (must be subscribed)       |
| Disable Market Depth for symbol                  | `MarketBookRelease`| Success/failure            | `symbol`                            |

---

## ❌ Cross‑refs & gotchas

* **Must subscribe first**: Call `MarketBookAdd` before `MarketBookGet`.
* **Not all symbols**: Market Depth may not be available for all symbols.
* **Not all brokers**: Some brokers don't provide Level II data.
* **Resource intensive**: Unsubscribe when done to free resources.
* **Pull mode**: Use `MarketBookGet` to poll; no streaming available.
* **Bid side**: Buy orders (demand) at various price levels.
* **Ask side**: Sell orders (supply) at various price levels.
* **Volume at level**: Shows liquidity available at each price.
* **Order book types**: SELL orders (ask side), BUY orders (bid side).

---

## 🟢 Minimal snippets

```java
// Subscribe to Market Depth
account.marketBookAdd("EURUSD");
```

```java
// Get order book data
var reply = account.marketBookGet("EURUSD");
var data = reply.getData();

System.out.printf("Market Depth for EURUSD:%n");
System.out.printf("BID SIDE (demand):%n");
for (int i = 0; i < data.getBookListBidCount(); i++) {
    var entry = data.getBookListBid(i);
    System.out.printf("  %.5f: %.2f lots%n", entry.getPrice(), entry.getVolume());
}

System.out.printf("ASK SIDE (supply):%n");
for (int i = 0; i < data.getBookListAskCount(); i++) {
    var entry = data.getBookListAsk(i);
    System.out.printf("  %.5f: %.2f lots%n", entry.getPrice(), entry.getVolume());
}
```

```java
// Calculate total liquidity
var reply = account.marketBookGet("GBPUSD");
var data = reply.getData();

double totalBidVolume = 0;
for (int i = 0; i < data.getBookListBidCount(); i++) {
    totalBidVolume += data.getBookListBid(i).getVolume();
}

double totalAskVolume = 0;
for (int i = 0; i < data.getBookListAskCount(); i++) {
    totalAskVolume += data.getBookListAsk(i).getVolume();
}

System.out.printf("Total bid liquidity: %.2f lots%n", totalBidVolume);
System.out.printf("Total ask liquidity: %.2f lots%n", totalAskVolume);
System.out.printf("Bid/Ask ratio: %.2f%n", totalBidVolume / totalAskVolume);
```

```java
// Unsubscribe when done
account.marketBookRelease("EURUSD");
```

```java
// Complete workflow: Subscribe → Get → Release
try {
    // 1. Subscribe
    account.marketBookAdd("XAUUSD");

    // 2. Get data
    var reply = account.marketBookGet("XAUUSD");
    var data = reply.getData();

    // 3. Process order book
    System.out.printf("Best bid: %.2f (%.2f lots)%n",
        data.getBookListBid(0).getPrice(),
        data.getBookListBid(0).getVolume());
    System.out.printf("Best ask: %.2f (%.2f lots)%n",
        data.getBookListAsk(0).getPrice(),
        data.getBookListAsk(0).getVolume());

} finally {
    // 4. Always unsubscribe
    account.marketBookRelease("XAUUSD");
}
```

---

## See also

* **Symbol info:** [`SymbolInfoTick`](../2.%20Symbol_information/SymbolInfoTick.md) — Level I quotes (bid/ask only)
* **Subscriptions:** [`OnSymbolTick`](../6.%20Subscriptions/OnSymbolTick.md) — real-time price stream
* **Trading:** [`OrderSend`](../5.%20Trading/OrderSend.md) — place orders based on liquidity analysis
