# ✅ Getting Latest Tick Data for Symbol

> **Request:** current market tick data for a specified symbol. Returns real-time bid/ask prices, last trade price, volume, and timestamp.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolInfoTick(...)` and `MT5Account.quote(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolInfoTick` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolInfoTick(SymbolInfoTickRequest) → SymbolInfoTickRequestReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolInfoTick(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets the latest tick data for a specified symbol.
     * Returns real-time market information including current bid/ask prices, last trade price, volume, and timestamp.
     * This is the primary method for retrieving current market prices for trading decisions.
     *
     * @param symbol Symbol name (e.g., "EURUSD", "GBPUSD")
     * @return Latest tick data with bid/ask prices, last price, volume, and time
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoTickRequestReply symbolInfoTick(String symbol) throws ApiExceptionMT5;

    /**
     * Gets the current market quote (Bid/Ask prices) for a trading symbol.
     * This is a convenience method that internally calls symbolInfoTick().
     *
     * @param symbol Trading symbol name (e.g., "EURUSD", "GBPUSD")
     * @return Current tick data including bid, ask, last price, and volume
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoTickRequestReply quote(String symbol) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolInfoTickRequest { symbol: string }`
**Reply message:** `SymbolInfoTickRequestReply { data: MrpcMqlTick }` or `{ error: Error }`

---

## 🔽 Input

| Parameter | Type     | Required | Description                              |
| --------- | -------- | -------- | ---------------------------------------- |
| `symbol`  | `String` | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")   |

---

## ⬆️ Output — `MrpcMqlTick`

| Field         | Type     | Description                                                  |
| ------------- | -------- | ------------------------------------------------------------ |
| `time`        | `long`   | Time of the last prices update (Unix timestamp in seconds)   |
| `bid`         | `double` | Current Bid price (sell price)                               |
| `ask`         | `double` | Current Ask price (buy price)                                |
| `last`        | `double` | Price of the last deal (Last)                                |
| `volume`      | `long`   | Volume for the current Last price                            |
| `time_msc`    | `long`   | Time of a price last update in milliseconds since 1970.01.01 |
| `flags`       | `int`    | Tick flags (see MQL5 documentation)                          |
| `volume_real` | `double` | Volume for the current Last price with greater accuracy      |

Access the tick data using `reply.getData()`.

---

## 💬 Just the essentials

* **What it is.** Single RPC returning current market prices and tick information for a symbol.
* **Why you need it.** Essential for getting real-time quotes before placing orders or analyzing market conditions.
* **Performance.** Lightweight call — ideal for frequent price checks.
* **Spread calculation.** Spread = Ask - Bid (in price units, not points).
* **Alternative.** Use `quoteMany()` to fetch quotes for multiple symbols in batch.

---

## 🎯 Purpose

Use this method when you need to:

* Get current bid/ask prices before placing market orders.
* Calculate spread for trading decisions.
* Monitor real-time price changes.
* Verify market is active (check if prices are updating).
* Get last trade information (price and volume).

---

## 🧩 Notes & Tips

* The symbol must be selected in MarketWatch or the call may fail.
* Use `symbolSelect(symbol, true)` first if you're unsure if the symbol is selected.
* For Forex symbols, typically use `bid` for selling and `ask` for buying.
* The `time_msc` field provides millisecond precision for high-frequency trading.
* The method uses automatic reconnection via `executeWithReconnect()` to handle transient errors.
* For monitoring multiple symbols, consider using `quoteMany()` or tick subscriptions (`onSymbolTick()`).

---

## 🔗 Usage Examples

### 1) Get current bid/ask prices

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get current tick
            Mt5TermApiMarketInfo.SymbolInfoTickRequestReply reply =
                account.symbolInfoTick("EURUSD");
            Mt5TermApiMarketInfo.MrpcMqlTick tick = reply.getData();

            System.out.printf("Symbol: EURUSD%n");
            System.out.printf("Bid: %.5f%n", tick.getBid());
            System.out.printf("Ask: %.5f%n", tick.getAsk());
            System.out.printf("Spread: %.5f%n", tick.getAsk() - tick.getBid());

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Using the `quote()` convenience method

```java
// Simpler syntax using quote() wrapper
var reply = account.quote("GBPUSD");
var tick = reply.getData();

System.out.printf("GBPUSD Bid: %.5f | Ask: %.5f%n",
    tick.getBid(), tick.getAsk());
```

### 3) Calculate spread in points

```java
var reply = account.symbolInfoTick("EURUSD");
var tick = reply.getData();

// Get symbol point size (e.g., 0.00001 for EURUSD)
var symbolReply = account.symbolInfoDouble(
    "EURUSD",
    Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT
);
double point = symbolReply.getData().getValue();

// Calculate spread
double spreadPrice = tick.getAsk() - tick.getBid();
double spreadPoints = spreadPrice / point;

System.out.printf("Spread: %.1f points (%.5f)%n", spreadPoints, spreadPrice);
```

### 4) Get tick with timestamp

```java
var reply = account.symbolInfoTick("XAUUSD");
var tick = reply.getData();

// Convert Unix timestamp to Java Instant
java.time.Instant tickTime = java.time.Instant.ofEpochSecond(tick.getTime());
java.time.Instant tickTimeMsc = java.time.Instant.ofEpochMilli(tick.getTimeMsc());

System.out.printf("Gold price at %s:%n", tickTime);
System.out.printf("Bid: %.2f | Ask: %.2f%n", tick.getBid(), tick.getAsk());
System.out.printf("Millisecond precision: %s%n", tickTimeMsc);
```

### 5) Monitor price changes

```java
import java.util.concurrent.TimeUnit;

public class PriceMonitor {
    public static void monitorPrice(MT5Account account, String symbol) {
        double lastBid = 0;

        while (true) {
            try {
                var reply = account.quote(symbol);
                var tick = reply.getData();

                if (tick.getBid() != lastBid) {
                    System.out.printf("[%s] %s: %.5f / %.5f (Spread: %.5f)%n",
                        java.time.LocalTime.now(),
                        symbol,
                        tick.getBid(),
                        tick.getAsk(),
                        tick.getAsk() - tick.getBid()
                    );
                    lastBid = tick.getBid();
                }

                TimeUnit.SECONDS.sleep(1);

            } catch (ApiExceptionMT5 e) {
                System.err.println("API Error: " + e.getMessage());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ie) {
                    break;
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
```

### 6) Get quotes for multiple symbols

```java
// Get quotes for multiple symbols using quoteMany()
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY", "XAUUSD"};

var quotes = account.quoteMany(symbols);

for (int i = 0; i < symbols.length; i++) {
    var tick = quotes[i].getData();
    System.out.printf("%s: Bid %.5f | Ask %.5f%n",
        symbols[i],
        tick.getBid(),
        tick.getAsk()
    );
}
```

### 7) Check if market is active

```java
public class MarketChecker {
    /**
     * Check if market is actively trading by comparing tick timestamps
     */
    public static boolean isMarketActive(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var reply = account.symbolInfoTick(symbol);
        var tick = reply.getData();

        // Get current time
        long currentTime = System.currentTimeMillis();
        long tickTime = tick.getTimeMsc();

        // Check if tick is fresh (within last 5 seconds)
        long ageMillis = currentTime - tickTime;
        boolean isActive = ageMillis < 5000;

        System.out.printf("Symbol: %s%n", symbol);
        System.out.printf("Tick age: %.1f seconds%n", ageMillis / 1000.0);
        System.out.printf("Market active: %s%n", isActive);

        return isActive;
    }
}
```

### 8) Create a tick data record

```java
/**
 * Immutable record for tick data
 */
public record TickData(
    String symbol,
    double bid,
    double ask,
    double last,
    double spread,
    java.time.Instant time
) {
    public static TickData fromProto(String symbol, Mt5TermApiMarketInfo.MrpcMqlTick tick) {
        return new TickData(
            symbol,
            tick.getBid(),
            tick.getAsk(),
            tick.getLast(),
            tick.getAsk() - tick.getBid(),
            java.time.Instant.ofEpochMilli(tick.getTimeMsc())
        );
    }

    @Override
    public String toString() {
        return String.format("%s @ %s: Bid=%.5f Ask=%.5f Spread=%.5f",
            symbol, time, bid, ask, spread);
    }
}

// Usage
var reply = account.quote("EURUSD");
var tickData = TickData.fromProto("EURUSD", reply.getData());
System.out.println(tickData);
```

### 9) Pre-trade price check

```java
public class TradeHelper {
    /**
     * Get current price for order placement
     */
    public static double getCurrentPrice(
            MT5Account account,
            String symbol,
            boolean isBuy) throws ApiExceptionMT5 {

        var reply = account.quote(symbol);
        var tick = reply.getData();

        // Buy at Ask, Sell at Bid
        double price = isBuy ? tick.getAsk() : tick.getBid();

        System.out.printf("%s %s at price: %.5f%n",
            isBuy ? "Buying" : "Selling",
            symbol,
            price);

        return price;
    }

    /**
     * Check if spread is acceptable
     */
    public static boolean isSpreadAcceptable(
            MT5Account account,
            String symbol,
            double maxSpreadPoints) throws ApiExceptionMT5 {

        var reply = account.quote(symbol);
        var tick = reply.getData();

        // Get point size
        var pointReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT
        );
        double point = pointReply.getData().getValue();

        // Calculate spread in points
        double spreadPoints = (tick.getAsk() - tick.getBid()) / point;

        boolean acceptable = spreadPoints <= maxSpreadPoints;

        if (!acceptable) {
            System.out.printf("⚠️ Spread too high: %.1f points (max: %.1f)%n",
                spreadPoints, maxSpreadPoints);
        }

        return acceptable;
    }
}

// Usage
if (TradeHelper.isSpreadAcceptable(account, "EURUSD", 3.0)) {
    double buyPrice = TradeHelper.getCurrentPrice(account, "EURUSD", true);
    // Place buy order at buyPrice
}
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolInfoTickRequest request =
    Mt5TermApiMarketInfo.SymbolInfoTickRequest.newBuilder()
        .setSymbol("EURUSD")
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolInfoTickRequestReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolInfoTick(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
Mt5TermApiMarketInfo.MrpcMqlTick tick = reply.getData();
double bid = tick.getBid();
double ask = tick.getAsk();
```
