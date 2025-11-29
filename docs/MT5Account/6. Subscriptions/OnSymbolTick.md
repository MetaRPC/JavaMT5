# 📊 Subscribe to Real-Time Tick Data (Price Stream)

> **Request:** subscribe to real-time tick updates for one or more symbols. Receives continuous stream of price changes (bid, ask, last, volume) as they occur in the market.

**API Information:**

* **SDK wrapper:** `MT5Account.onSymbolTick(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.SubscriptionService`
* **Proto definition:** `OnSymbolTick` (defined in `mt5-term-api-subscriptions.proto`)

### RPC

* **Service:** `mt5_term_api.SubscriptionService`
* **Method:** `OnSymbolTick(OnSymbolTickRequest) → stream OnSymbolTickReply`
* **Low‑level client (generated):** `SubscriptionServiceGrpc.SubscriptionServiceStub.onSymbolTick(request, observer)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Subscribes to real-time tick updates for one or more symbols.
     * Receives a continuous stream of price updates (bid, ask, last, volume) whenever prices change.
     * Use this for real-time price monitoring, tick-based trading strategies, or market data feeds.
     *
     * @param symbolNames Array of symbol names to monitor (e.g., ["EURUSD", "GBPUSD"])
     * @param responseObserver Observer to receive streaming tick updates
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onSymbolTick(
        String[] symbolNames,
        StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> responseObserver
    ) throws ApiExceptionMT5;
}
```

---

## 🔽 Input - `OnSymbolTickRequest`

| Parameter      | Type       | Required | Description                                          |
| -------------- | ---------- | -------- | ---------------------------------------------------- |
| `symbol_names` | `String[]` | ✅       | Array of symbol names to monitor (e.g., ["EURUSD"]) |

---

## ⬆️ Output - `MrpcSubscriptionMqlTick` (stream)

Each tick event contains:

| Field         | Type        | Description                                          |
| ------------- | ----------- | ---------------------------------------------------- |
| `symbol`      | `String`    | Symbol name                                          |
| `time`        | `Timestamp` | Time of the last price update                        |
| `bid`         | `double`    | Current Bid price                                    |
| `ask`         | `double`    | Current Ask price                                    |
| `last`        | `double`    | Price of the last deal (Last)                        |
| `volume`      | `uint64`    | Volume for the current Last price                    |
| `volume_real` | `double`    | Volume with greater accuracy                         |
| `time_msc`    | `int64`     | Time of price update in milliseconds                 |
| `flags`       | `uint32`    | Tick flags (bid/ask changed indicators)              |

Access using `reply.getData().getSymbolTick().<field>`.

**Important:**
- This is a **streaming** RPC - events arrive continuously
- You must use `StreamObserver` pattern for asynchronous handling
- Stream remains open until explicitly closed or error occurs
- Multiple symbols can be monitored with a single subscription

---

## 💬 Just the essentials

* **What it is.** Real-time price feed for multiple symbols.
* **Why you need it.** Essential for any price-based trading strategy.
* **Returns.** Continuous stream of bid/ask/volume updates.
* **Asynchronous.** Uses StreamObserver pattern - events arrive on separate thread.
* **Multiple symbols.** Monitor many symbols with one subscription.
* **Market hours only.** Ticks only arrive during active trading hours.

---

## 🎯 Purpose

Use this method when you need to:

* Monitor real-time price changes for trading strategies.
* Build tick-based trading algorithms.
* Track spread changes across multiple symbols.
* Create live price feeds for UI/dashboards.
* Detect price breakouts or pattern formations.
* Calculate real-time indicators based on tick data.

---

## 🔗 Usage Examples

### 1) Basic tick subscription

```java
import io.grpc.stub.StreamObserver;
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiSubscriptions;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Create observer to handle tick events
            StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> observer =
                new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {

                @Override
                public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
                    // Called every time a new tick arrives
                    if (reply.hasData()) {
                        var tick = reply.getData().getSymbolTick();

                        System.out.printf("%s: Bid=%.5f, Ask=%.5f, Spread=%.5f%n",
                            tick.getSymbol(),
                            tick.getBid(),
                            tick.getAsk(),
                            tick.getAsk() - tick.getBid());
                    }
                }

                @Override
                public void onError(Throwable t) {
                    // Called if stream encounters an error
                    System.err.println("Tick stream error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    // Called when stream is closed by server
                    System.out.println("Tick stream completed");
                }
            };

            // Subscribe to EURUSD ticks
            account.onSymbolTick(new String[]{"EURUSD"}, observer);

            // Keep main thread alive to receive events
            Thread.sleep(60000); // Run for 1 minute

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Monitor multiple symbols simultaneously

```java
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiSymbolMonitor {
    /**
     * Monitor multiple symbols and count ticks
     */
    public static void monitorSymbols(MT5Account account, String[] symbols, int maxTicks)
            throws ApiExceptionMT5, InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger tickCount = new AtomicInteger(0);

        StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
                if (reply.hasData()) {
                    var tick = reply.getData().getSymbolTick();
                    int count = tickCount.incrementAndGet();

                    System.out.printf("[%d] %s | Bid: %.5f | Ask: %.5f | Volume: %d%n",
                        count,
                        tick.getSymbol(),
                        tick.getBid(),
                        tick.getAsk(),
                        tick.getVolume());

                    if (count >= maxTicks) {
                        latch.countDown(); // Stop after max ticks
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Stream error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed");
                latch.countDown();
            }
        };

        System.out.printf("Subscribing to: %s%n", String.join(", ", symbols));
        account.onSymbolTick(symbols, observer);

        // Wait for max ticks or 30 seconds
        boolean completed = latch.await(30, TimeUnit.SECONDS);

        if (!completed) {
            System.out.printf("Timeout - received %d ticks%n", tickCount.get());
        }
    }
}

// Usage
MultiSymbolMonitor.monitorSymbols(
    account,
    new String[]{"EURUSD", "GBPUSD", "USDJPY"},
    100  // Stop after 100 ticks
);
```

### 3) Spread monitoring and alerts

```java
import java.util.HashMap;
import java.util.Map;

public class SpreadMonitor {
    /**
     * Monitor spreads and alert when they exceed threshold
     */
    public static void monitorSpreads(
            MT5Account account,
            String[] symbols,
            double maxSpreadPips) throws ApiExceptionMT5 {

        Map<String, Double> lastBid = new HashMap<>();
        Map<String, Double> lastAsk = new HashMap<>();

        StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
                if (reply.hasData()) {
                    var tick = reply.getData().getSymbolTick();
                    String symbol = tick.getSymbol();
                    double bid = tick.getBid();
                    double ask = tick.getAsk();

                    lastBid.put(symbol, bid);
                    lastAsk.put(symbol, ask);

                    // Calculate spread in pips
                    double pipSize = symbol.contains("JPY") ? 0.01 : 0.0001;
                    double spreadPips = (ask - bid) / pipSize;

                    // Alert if spread too wide
                    if (spreadPips > maxSpreadPips) {
                        System.out.printf("⚠️  WIDE SPREAD: %s = %.1f pips " +
                            "(Bid: %.5f, Ask: %.5f)%n",
                            symbol, spreadPips, bid, ask);
                    } else {
                        System.out.printf("✅ %s: %.1f pips%n", symbol, spreadPips);
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Spread monitor error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("\nSpread monitoring completed");
                System.out.println("Last quotes:");
                lastBid.forEach((symbol, bid) -> {
                    double ask = lastAsk.get(symbol);
                    System.out.printf("  %s: %.5f / %.5f%n", symbol, bid, ask);
                });
            }
        };

        System.out.printf("Monitoring spreads (max: %.1f pips)%n", maxSpreadPips);
        account.onSymbolTick(symbols, observer);

        // Keep alive
        Thread.sleep(60000);
    }
}

// Usage - alert if spread > 3 pips
SpreadMonitor.monitorSpreads(
    account,
    new String[]{"EURUSD", "GBPUSD", "AUDUSD"},
    3.0
);
```

### 4) Price change detector

```java
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PriceChangeDetector {
    private static final DateTimeFormatter TIME_FORMAT =
        DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

    /**
     * Detect significant price movements
     */
    public static void detectPriceChanges(
            MT5Account account,
            String symbol,
            double thresholdPips) throws ApiExceptionMT5, InterruptedException {

        final double[] previousBid = {0.0};
        final double[] previousAsk = {0.0};

        StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
                if (reply.hasData()) {
                    var tick = reply.getData().getSymbolTick();

                    double bid = tick.getBid();
                    double ask = tick.getAsk();

                    // Initialize on first tick
                    if (previousBid[0] == 0.0) {
                        previousBid[0] = bid;
                        previousAsk[0] = ask;
                        System.out.printf("Initial price: Bid=%.5f, Ask=%.5f%n", bid, ask);
                        return;
                    }

                    // Calculate pip size
                    double pipSize = symbol.contains("JPY") ? 0.01 : 0.0001;

                    // Calculate change in pips
                    double bidChangePips = (bid - previousBid[0]) / pipSize;
                    double askChangePips = (ask - previousAsk[0]) / pipSize;

                    // Check if significant move
                    if (Math.abs(bidChangePips) >= thresholdPips ||
                        Math.abs(askChangePips) >= thresholdPips) {

                        Instant time = Instant.ofEpochSecond(
                            tick.getTime().getSeconds()
                        );

                        System.out.printf("\n🔔 PRICE MOVE @ %s:%n",
                            TIME_FORMAT.format(time));
                        System.out.printf("  Bid: %.5f → %.5f (%+.1f pips)%n",
                            previousBid[0], bid, bidChangePips);
                        System.out.printf("  Ask: %.5f → %.5f (%+.1f pips)%n",
                            previousAsk[0], ask, askChangePips);

                        previousBid[0] = bid;
                        previousAsk[0] = ask;
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Price detector error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Price monitoring completed");
            }
        };

        System.out.printf("Monitoring %s for moves >= %.1f pips%n",
            symbol, thresholdPips);

        account.onSymbolTick(new String[]{symbol}, observer);

        Thread.sleep(300000); // Run for 5 minutes
    }
}

// Usage - detect 5+ pip moves
PriceChangeDetector.detectPriceChanges(account, "EURUSD", 5.0);
```

### 5) Tick data recorder

```java
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class TickRecorder {
    /**
     * Record tick data to CSV file
     */
    public static void recordTicks(
            MT5Account account,
            String symbol,
            int maxTicks,
            String filename) throws Exception {

        AtomicInteger tickCount = new AtomicInteger(0);
        PrintWriter writer = new PrintWriter(new FileWriter(filename));

        // Write CSV header
        writer.println("Timestamp,Symbol,Bid,Ask,Last,Volume,Spread");

        StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
                if (reply.hasData()) {
                    var tick = reply.getData().getSymbolTick();
                    int count = tickCount.incrementAndGet();

                    // Convert timestamp
                    Instant time = Instant.ofEpochSecond(
                        tick.getTime().getSeconds()
                    );

                    // Calculate spread
                    double spread = tick.getAsk() - tick.getBid();

                    // Write to CSV
                    writer.printf("%s,%s,%.5f,%.5f,%.5f,%d,%.5f%n",
                        time.toString(),
                        tick.getSymbol(),
                        tick.getBid(),
                        tick.getAsk(),
                        tick.getLast(),
                        tick.getVolume(),
                        spread);

                    if (count % 100 == 0) {
                        System.out.printf("Recorded %d ticks...%n", count);
                        writer.flush(); // Flush periodically
                    }

                    if (count >= maxTicks) {
                        System.out.printf("Reached %d ticks, stopping%n", maxTicks);
                        writer.close();
                        System.exit(0);
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Recording error: " + t.getMessage());
                writer.close();
            }

            @Override
            public void onCompleted() {
                System.out.printf("Recording completed: %d ticks saved to %s%n",
                    tickCount.get(), filename);
                writer.close();
            }
        };

        System.out.printf("Recording %s ticks to %s (max %d ticks)%n",
            symbol, filename, maxTicks);

        account.onSymbolTick(new String[]{symbol}, observer);

        // Keep alive
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage - record 1000 ticks to file
TickRecorder.recordTicks(account, "EURUSD", 1000, "eurusd_ticks.csv");
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import mt5_term_api.*;

// Build request
Mt5TermApiSubscriptions.OnSymbolTickRequest request =
    Mt5TermApiSubscriptions.OnSymbolTickRequest.newBuilder()
        .addSymbolNames("EURUSD")
        .addSymbolNames("GBPUSD")
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Create observer
StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> observer =
    new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
            if (reply.hasData()) {
                var tick = reply.getData().getSymbolTick();
                // Process tick data
            }
        }

        @Override
        public void onError(Throwable t) {
            // Handle error
        }

        @Override
        public void onCompleted() {
            // Handle completion
        }
    };

// Subscribe (async stub for streaming)
subscriptionClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .onSymbolTick(request, observer);
```

---

## 📌 Important Notes

**Stream Behavior:**
- Continuous stream - events arrive as prices change
- Runs on separate gRPC thread pool
- Must keep main thread alive to receive events
- Stream stays open until error or explicit close

**Tick Frequency:**
- During active market: many ticks per second
- During quiet periods: fewer ticks
- Outside market hours: no ticks
- High volatility = more tick events

**Multiple Symbols:**
- Single subscription can monitor many symbols
- All ticks arrive through same observer
- Use `tick.getSymbol()` to identify source
- More symbols = more events per second

**Thread Safety:**
- `onNext()` called on gRPC thread, not main thread
- Implement proper synchronization for shared data
- Use thread-safe collections (e.g., ConcurrentHashMap)
- Consider using CountDownLatch for coordination

**Resource Management:**
- Stream consumes network bandwidth continuously
- Unsubscribe when not needed
- Don't create too many subscriptions
- One subscription per symbol set is sufficient

**Best Practices:**
- Always implement all three StreamObserver methods
- Use CountDownLatch or similar for synchronization
- Handle `onError()` gracefully - reconnect if needed
- Log tick timestamps for accurate replay/analysis
- Buffer tick data if processing is slow
- Monitor for missed ticks (compare timestamps)

**Common Use Cases:**
- Real-time price monitoring
- Tick-based scalping strategies
- Spread analysis and arbitrage
- Market microstructure research
- High-frequency trading systems
- Live price feeds for UI

**Performance Considerations:**
- Each tick triggers `onNext()` callback
- Keep callback processing fast
- Offload heavy work to separate thread
- Consider batching tick processing
- Monitor memory if storing ticks

**Error Handling:**
- Connection loss triggers `onError()`
- Implement reconnection logic
- Use SDK's automatic reconnection
- Check for gaps in tick sequence
- Validate tick data (bid < ask)
