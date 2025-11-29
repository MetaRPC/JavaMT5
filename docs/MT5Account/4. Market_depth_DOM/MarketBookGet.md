# 📖 Get Market Depth Order Book Data

> **Request:** retrieve current Market Depth (order book) data for a subscribed symbol. Returns pending buy/sell orders at different price levels with volumes.

**API Information:**

* **SDK wrapper:** `MT5Account.marketBookGet(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `MarketBookGet` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `MarketBookGet(MarketBookGetRequest) → MarketBookGetReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.marketBookGet(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets the current Market Depth (order book) data for a subscribed symbol.
     * Returns pending buy and sell orders with prices and volumes from the order book.
     * Use this to analyze liquidity, identify support/resistance levels, or optimize order placement.
     *
     * @param symbol Symbol name (must be subscribed via marketBookAdd first)
     * @return Market book data containing arrays of buy and sell orders with prices and volumes
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.MarketBookGetReply marketBookGet(String symbol) throws ApiExceptionMT5;
}
```

**Request message:** `MarketBookGetRequest { symbol: string }`

**Reply message:** `MarketBookGetReply { data: MarketBookGetData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (must be subscribed first)   |

**Important:** Symbol must be subscribed via `marketBookAdd()` before calling this method.

---

## ⬆️ Output - `MarketBookGetData`

| Field            | Type                       | Description                                          |
| ---------------- | -------------------------- | ---------------------------------------------------- |
| `mql_book_infos` | `List<MrpcMqlBookInfo>`    | List of order book entries (buy/sell orders)         |

Access using `reply.getData().getMqlBookInfosList()`.

### Structure: `MrpcMqlBookInfo` (Order Book Entry)

Each order book entry contains:

| Field         | Type        | Description                                          |
| ------------- | ----------- | ---------------------------------------------------- |
| `type`        | `BookType`  | Order type (BUY, SELL, BUY_MARKET, SELL_MARKET)      |
| `price`       | `double`    | Price level                                          |
| `volume`      | `long`      | Volume in integer units                              |
| `volume_real` | `double`    | Volume in real units (lots)                          |

Access using:
- `entry.getType()` - Order type (enum)
- `entry.getPrice()` - Price level
- `entry.getVolume()` - Volume (integer)
- `entry.getVolumeReal()` - Volume in lots (double)

### Enum: `BookType`

| Value                   | Number | Description                                          |
| ----------------------- | ------ | ---------------------------------------------------- |
| `BOOK_TYPE_SELL`        | 0      | Sell limit order (Ask/Offer side)                    |
| `BOOK_TYPE_BUY`         | 1      | Buy limit order (Bid side)                           |
| `BOOK_TYPE_SELL_MARKET` | 2      | Market sell order                                    |
| `BOOK_TYPE_BUY_MARKET`  | 3      | Market buy order                                     |

---

## 💬 Just the essentials

* **What it is.** RPC to retrieve current order book (Level II) data for a symbol.
* **Why you need it.** See pending orders, analyze liquidity, identify price levels with high volume.
* **Prerequisite.** Must call `marketBookAdd()` first to subscribe.
* **Data structure.** Returns list of price levels with volumes, separated by buy/sell type.
* **Use case.** Liquidity analysis, support/resistance identification, large order optimization.
* **Real-time.** Data reflects current state at time of request.

---

## 🎯 Purpose

Use this method when you need to:

* Analyze market liquidity at different price levels.
* Identify support/resistance zones from order concentration.
* Assess potential slippage before placing large orders.
* Monitor order flow changes.
* Find best prices with sufficient liquidity.
* Build advanced trading algorithms using Level II data.
* Visualize market depth for trading dashboards.

---

## 🧩 Notes & Tips

* **Subscription required.** Call `marketBookAdd()` first, otherwise this will fail.
* **Snapshot data.** Returns current state, not historical or streaming data.
* **Price sorting.** Entries are typically sorted by price (best prices first).
* **Volume fields.** Use `volume_real` (double) for lot-based calculations.
* **Buy vs Sell.** Filter by `BookType` to separate bids from asks.
* **Market orders.** Some brokers include executed market orders in the book.
* **Empty book.** Empty list doesn't mean error - symbol may have no pending orders.
* **Polling.** Call repeatedly for real-time monitoring (consider rate limits).

---

## 🔗 Usage Examples

### 1) Basic order book retrieval

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Subscribe to Market Depth
            account.marketBookAdd("EURUSD");

            // Get order book data
            Mt5TermApiMarketInfo.MarketBookGetReply reply =
                account.marketBookGet("EURUSD");

            var bookEntries = reply.getData().getMqlBookInfosList();
            System.out.printf("Order book entries: %d%n%n", bookEntries.size());

            // Display order book
            for (var entry : bookEntries) {
                String typeStr = entry.getType().name();
                System.out.printf("%s | Price: %.5f | Volume: %.2f lots%n",
                    typeStr,
                    entry.getPrice(),
                    entry.getVolumeReal()
                );
            }

            // Cleanup
            account.marketBookRelease("EURUSD");

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Separate bids and asks

```java
import java.util.*;

public class OrderBookAnalyzer {
    public record OrderLevel(double price, double volume) {}

    public record OrderBook(
        List<OrderLevel> bids,    // Buy orders
        List<OrderLevel> asks     // Sell orders
    ) {}

    /**
     * Parse order book into bids and asks
     */
    public static OrderBook parseOrderBook(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        var reply = account.marketBookGet(symbol);
        var entries = reply.getData().getMqlBookInfosList();

        List<OrderLevel> bids = new ArrayList<>();
        List<OrderLevel> asks = new ArrayList<>();

        for (var entry : entries) {
            OrderLevel level = new OrderLevel(
                entry.getPrice(),
                entry.getVolumeReal()
            );

            switch (entry.getType()) {
                case BOOK_TYPE_BUY:
                case BOOK_TYPE_BUY_MARKET:
                    bids.add(level);
                    break;

                case BOOK_TYPE_SELL:
                case BOOK_TYPE_SELL_MARKET:
                    asks.add(level);
                    break;
            }
        }

        // Sort: bids descending (best bid first), asks ascending (best ask first)
        bids.sort((a, b) -> Double.compare(b.price(), a.price()));
        asks.sort((a, b) -> Double.compare(a.price(), b.price()));

        return new OrderBook(bids, asks);
    }

    /**
     * Display formatted order book
     */
    public static void displayOrderBook(OrderBook book) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║            ORDER BOOK (DOM)                ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║      BIDS (Buy)      │      ASKS (Sell)    ║");
        System.out.println("║  Price    │ Volume   │  Price    │ Volume  ║");
        System.out.println("╠════════════════════════════════════════════╣");

        int maxRows = Math.max(book.bids().size(), book.asks().size());

        for (int i = 0; i < Math.min(maxRows, 10); i++) {  // Show top 10 levels
            String bidPrice = i < book.bids().size()
                ? String.format("%.5f", book.bids().get(i).price())
                : "        ";
            String bidVol = i < book.bids().size()
                ? String.format("%.2f", book.bids().get(i).volume())
                : "     ";

            String askPrice = i < book.asks().size()
                ? String.format("%.5f", book.asks().get(i).price())
                : "        ";
            String askVol = i < book.asks().size()
                ? String.format("%.2f", book.asks().get(i).volume())
                : "     ";

            System.out.printf("║ %s │ %s │ %s │ %s  ║%n",
                bidPrice, bidVol, askPrice, askVol);
        }

        System.out.println("╚════════════════════════════════════════════╝");
    }
}

// Usage
var book = OrderBookAnalyzer.parseOrderBook(account, "EURUSD");
OrderBookAnalyzer.displayOrderBook(book);

System.out.printf("\nBest Bid: %.5f (Volume: %.2f lots)%n",
    book.bids().get(0).price(),
    book.bids().get(0).volume()
);
System.out.printf("Best Ask: %.5f (Volume: %.2f lots)%n",
    book.asks().get(0).price(),
    book.asks().get(0).volume()
);
```

### 3) Calculate total liquidity

```java
public class LiquidityCalculator {
    /**
     * Calculate total liquidity on each side
     */
    public static void analyzeLiquidity(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        var reply = account.marketBookGet(symbol);
        var entries = reply.getData().getMqlBookInfosList();

        double totalBidVolume = 0;
        double totalAskVolume = 0;
        int bidLevels = 0;
        int askLevels = 0;

        for (var entry : entries) {
            double volume = entry.getVolumeReal();

            switch (entry.getType()) {
                case BOOK_TYPE_BUY:
                case BOOK_TYPE_BUY_MARKET:
                    totalBidVolume += volume;
                    bidLevels++;
                    break;

                case BOOK_TYPE_SELL:
                case BOOK_TYPE_SELL_MARKET:
                    totalAskVolume += volume;
                    askLevels++;
                    break;
            }
        }

        double totalVolume = totalBidVolume + totalAskVolume;
        double bidRatio = totalVolume > 0 ? (totalBidVolume / totalVolume) * 100 : 0;
        double askRatio = totalVolume > 0 ? (totalAskVolume / totalVolume) * 100 : 0;

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       LIQUIDITY ANALYSIS               ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Symbol: %-30s ║%n", symbol);
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Bid Levels: %-26d ║%n", bidLevels);
        System.out.printf("║ Ask Levels: %-26d ║%n", askLevels);
        System.out.printf("║ Total Bid Volume: %-19.2f ║%n", totalBidVolume);
        System.out.printf("║ Total Ask Volume: %-19.2f ║%n", totalAskVolume);
        System.out.printf("║ Bid Ratio: %-25.2f%% ║%n", bidRatio);
        System.out.printf("║ Ask Ratio: %-25.2f%% ║%n", askRatio);
        System.out.println("╚════════════════════════════════════════╝");

        // Interpret imbalance
        if (Math.abs(bidRatio - askRatio) > 20) {
            String side = bidRatio > askRatio ? "BUY" : "SELL";
            System.out.printf("\n⚠️ Significant order imbalance: %s pressure detected%n", side);
        }
    }
}

// Usage
LiquidityCalculator.analyzeLiquidity(account, "EURUSD");
```

### 4) Find price levels with high volume

```java
import java.util.*;

public class HighVolumeLevelFinder {
    public record VolumeLevel(
        double price,
        double volume,
        String type
    ) {}

    /**
     * Find top volume levels in order book
     */
    public static List<VolumeLevel> findTopVolumeLevels(
            MT5Account account,
            String symbol,
            int topN) throws ApiExceptionMT5 {

        var reply = account.marketBookGet(symbol);
        var entries = reply.getData().getMqlBookInfosList();

        List<VolumeLevel> levels = new ArrayList<>();

        for (var entry : entries) {
            String typeStr = entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY ||
                             entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY_MARKET
                ? "BID" : "ASK";

            levels.add(new VolumeLevel(
                entry.getPrice(),
                entry.getVolumeReal(),
                typeStr
            ));
        }

        // Sort by volume descending
        levels.sort((a, b) -> Double.compare(b.volume(), a.volume()));

        return levels.subList(0, Math.min(topN, levels.size()));
    }

    /**
     * Display high volume levels
     */
    public static void displayHighVolumeLevels(
            List<VolumeLevel> levels,
            String symbol) {

        System.out.printf("\n╔════════════════════════════════════════╗%n");
        System.out.printf("║  HIGH VOLUME LEVELS - %-16s ║%n", symbol);
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Rank │ Type │   Price   │   Volume   ║");
        System.out.println("╠════════════════════════════════════════╣");

        for (int i = 0; i < levels.size(); i++) {
            var level = levels.get(i);
            System.out.printf("║  %2d  │ %-4s │ %9.5f │ %10.2f ║%n",
                i + 1,
                level.type(),
                level.price(),
                level.volume()
            );
        }

        System.out.println("╚════════════════════════════════════════╝");
    }
}

// Usage
var topLevels = HighVolumeLevelFinder.findTopVolumeLevels(account, "EURUSD", 10);
HighVolumeLevelFinder.displayHighVolumeLevels(topLevels, "EURUSD");
```

### 5) Estimate slippage for large order

```java
public class SlippageEstimator {
    /**
     * Estimate slippage for a large market order
     */
    public static double estimateSlippage(
            MT5Account account,
            String symbol,
            double volumeLots,
            boolean isBuy) throws ApiExceptionMT5 {

        var reply = account.marketBookGet(symbol);
        var entries = reply.getData().getMqlBookInfosList();

        // Get relevant side of book
        var relevantSide = entries.stream()
            .filter(entry -> {
                if (isBuy) {
                    return entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_SELL ||
                           entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_SELL_MARKET;
                } else {
                    return entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY ||
                           entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY_MARKET;
                }
            })
            .sorted((a, b) -> {
                // Sort by best price first
                if (isBuy) {
                    return Double.compare(a.getPrice(), b.getPrice());  // Ascending for buys
                } else {
                    return Double.compare(b.getPrice(), a.getPrice());  // Descending for sells
                }
            })
            .toList();

        if (relevantSide.isEmpty()) {
            System.out.println("⚠️ No liquidity available on " + (isBuy ? "ask" : "bid") + " side");
            return Double.MAX_VALUE;
        }

        double remainingVolume = volumeLots;
        double totalCost = 0;
        double bestPrice = relevantSide.get(0).getPrice();

        System.out.printf("\nFilling %s order for %.2f lots:%n",
            isBuy ? "BUY" : "SELL", volumeLots);
        System.out.println("─".repeat(50));

        for (var entry : relevantSide) {
            if (remainingVolume <= 0) break;

            double availableVolume = entry.getVolumeReal();
            double fillVolume = Math.min(remainingVolume, availableVolume);

            totalCost += fillVolume * entry.getPrice();
            remainingVolume -= fillVolume;

            System.out.printf("  Price: %.5f │ Fill: %.2f/%.2f lots%n",
                entry.getPrice(),
                fillVolume,
                availableVolume
            );
        }

        if (remainingVolume > 0) {
            System.out.printf("\n⚠️ WARNING: Insufficient liquidity! %.2f lots unfilled%n",
                remainingVolume);
            return Double.MAX_VALUE;
        }

        double avgPrice = totalCost / volumeLots;
        double slippage = Math.abs(avgPrice - bestPrice);

        System.out.println("─".repeat(50));
        System.out.printf("Best Price: %.5f%n", bestPrice);
        System.out.printf("Avg Fill Price: %.5f%n", avgPrice);
        System.out.printf("Estimated Slippage: %.5f (%.1f pips)%n",
            slippage, slippage * 10000);

        return slippage;
    }
}

// Usage
double slippage = SlippageEstimator.estimateSlippage(
    account,
    "EURUSD",
    10.0,  // 10 lots
    true   // BUY order
);
```

### 6) Monitor order book changes

```java
public class OrderBookMonitor {
    /**
     * Monitor order book for changes
     */
    public static void monitorChanges(
            MT5Account account,
            String symbol,
            int durationSeconds) throws InterruptedException {

        System.out.printf("Monitoring %s order book for %d seconds...%n",
            symbol, durationSeconds);
        System.out.println("═".repeat(50));

        var prevBids = new ArrayList<Double>();
        var prevAsks = new ArrayList<Double>();

        long endTime = System.currentTimeMillis() + (durationSeconds * 1000L);

        while (System.currentTimeMillis() < endTime) {
            try {
                var reply = account.marketBookGet(symbol);
                var entries = reply.getData().getMqlBookInfosList();

                var currentBids = entries.stream()
                    .filter(e -> e.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY)
                    .map(e -> e.getPrice())
                    .sorted((a, b) -> Double.compare(b, a))  // Descending
                    .toList();

                var currentAsks = entries.stream()
                    .filter(e -> e.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_SELL)
                    .map(e -> e.getPrice())
                    .sorted()  // Ascending
                    .toList();

                // Check for changes
                if (!currentBids.equals(prevBids) || !currentAsks.equals(prevAsks)) {
                    System.out.printf("[%s] Order book changed%n",
                        java.time.LocalTime.now());

                    if (!currentBids.isEmpty()) {
                        System.out.printf("  Best Bid: %.5f%n", currentBids.get(0));
                    }
                    if (!currentAsks.isEmpty()) {
                        System.out.printf("  Best Ask: %.5f%n", currentAsks.get(0));
                    }
                    if (!currentBids.isEmpty() && !currentAsks.isEmpty()) {
                        double spread = currentAsks.get(0) - currentBids.get(0);
                        System.out.printf("  Spread: %.5f (%.1f pips)%n",
                            spread, spread * 10000);
                    }
                    System.out.println();

                    prevBids = new ArrayList<>(currentBids);
                    prevAsks = new ArrayList<>(currentAsks);
                }

                Thread.sleep(500);  // Check every 500ms

            } catch (ApiExceptionMT5 e) {
                System.err.printf("Error: %s%n", e.getMessage());
            }
        }

        System.out.println("Monitoring complete");
    }
}

// Usage
account.marketBookAdd("EURUSD");
OrderBookMonitor.monitorChanges(account, "EURUSD", 30);
account.marketBookRelease("EURUSD");
```

### 7) Calculate bid-ask spread from order book

```java
public class SpreadCalculator {
    /**
     * Calculate bid-ask spread from order book
     */
    public static void calculateSpread(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        var reply = account.marketBookGet(symbol);
        var entries = reply.getData().getMqlBookInfosList();

        // Find best bid and ask
        OptionalDouble bestBid = entries.stream()
            .filter(e -> e.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY)
            .mapToDouble(e -> e.getPrice())
            .max();

        OptionalDouble bestAsk = entries.stream()
            .filter(e -> e.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_SELL)
            .mapToDouble(e -> e.getPrice())
            .min();

        if (bestBid.isEmpty() || bestAsk.isEmpty()) {
            System.out.println("⚠️ Incomplete order book (missing bid or ask)");
            return;
        }

        double bid = bestBid.getAsDouble();
        double ask = bestAsk.getAsDouble();
        double spread = ask - bid;
        double spreadPips = spread * 10000;
        double spreadPercent = (spread / bid) * 100;

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.printf("║  SPREAD ANALYSIS - %-19s ║%n", symbol);
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Best Bid: %-28.5f ║%n", bid);
        System.out.printf("║ Best Ask: %-28.5f ║%n", ask);
        System.out.printf("║ Spread: %-30.5f ║%n", spread);
        System.out.printf("║ Spread (pips): %-23.1f ║%n", spreadPips);
        System.out.printf("║ Spread (%%): %-26.4f ║%n", spreadPercent);
        System.out.println("╚════════════════════════════════════════╝");
    }
}

// Usage
SpreadCalculator.calculateSpread(account, "EURUSD");
```

### 8) Export order book to JSON

```java
import com.google.gson.*;
import java.io.*;

public class OrderBookExporter {
    /**
     * Export order book to JSON file
     */
    public static void exportToJson(
            MT5Account account,
            String symbol,
            String filename) throws ApiExceptionMT5, IOException {

        var reply = account.marketBookGet(symbol);
        var entries = reply.getData().getMqlBookInfosList();

        // Build JSON structure
        JsonObject root = new JsonObject();
        root.addProperty("symbol", symbol);
        root.addProperty("timestamp", java.time.Instant.now().toString());

        JsonArray bids = new JsonArray();
        JsonArray asks = new JsonArray();

        for (var entry : entries) {
            JsonObject level = new JsonObject();
            level.addProperty("price", entry.getPrice());
            level.addProperty("volume", entry.getVolumeReal());

            boolean isBid = entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY ||
                           entry.getType() == Mt5TermApiMarketInfo.BookType.BOOK_TYPE_BUY_MARKET;

            if (isBid) {
                bids.add(level);
            } else {
                asks.add(level);
            }
        }

        root.add("bids", bids);
        root.add("asks", asks);

        // Write to file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(root, writer);
        }

        System.out.printf("✅ Exported order book to %s%n", filename);
        System.out.printf("   Bids: %d levels, Asks: %d levels%n",
            bids.size(), asks.size());
    }
}

// Usage
OrderBookExporter.exportToJson(account, "EURUSD", "eurusd_orderbook.json");
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.MarketBookGetRequest request =
    Mt5TermApiMarketInfo.MarketBookGetRequest.newBuilder()
        .setSymbol("EURUSD")
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.MarketBookGetReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .marketBookGet(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
List<Mt5TermApiMarketInfo.MrpcMqlBookInfo> bookEntries =
    reply.getData().getMqlBookInfosList();
```

---

## 📌 Understanding Order Book Data

**Order Types:**
- **BOOK_TYPE_BUY** - Pending buy limit orders (bids)
- **BOOK_TYPE_SELL** - Pending sell limit orders (asks/offers)
- **BOOK_TYPE_BUY_MARKET** - Market buy orders (rare in retail)
- **BOOK_TYPE_SELL_MARKET** - Market sell orders (rare in retail)

**Price Levels:**
- **Bid side:** Buyers willing to buy at specific prices (below market)
- **Ask side:** Sellers willing to sell at specific prices (above market)
- **Best bid:** Highest bid price (top of bid side)
- **Best ask:** Lowest ask price (top of ask side)

**Volume Interpretation:**
- Higher volume at a level = more liquidity
- Large volumes = potential support/resistance
- Volume imbalance = potential price movement direction

**Typical Workflow:**
1. Subscribe with `marketBookAdd(symbol)`
2. Poll with `marketBookGet(symbol)` for current data
3. Parse and analyze bid/ask sides
4. Make trading decisions based on liquidity
5. Unsubscribe with `marketBookRelease(symbol)` when done
