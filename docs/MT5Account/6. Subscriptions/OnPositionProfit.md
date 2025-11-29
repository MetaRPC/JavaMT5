# 💰 Subscribe to Position Profit Updates (P/L Monitoring Stream)

> **Request:** subscribe to periodic updates of position profit/loss values. Receives profit changes at regular intervals for all open positions as market prices fluctuate.

**API Information:**

* **SDK wrapper:** `MT5Account.onPositionProfit(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.SubscriptionService`
* **Proto definition:** `OnPositionProfit` (defined in `mt5-term-api-subscriptions.proto`)

### RPC

* **Service:** `mt5_term_api.SubscriptionService`
* **Method:** `OnPositionProfit(OnPositionProfitRequest) → stream OnPositionProfitReply`
* **Low‑level client (generated):** `SubscriptionServiceGrpc.SubscriptionServiceStub.onPositionProfit(request, observer)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Subscribes to periodic updates of position profit/loss values.
     * Receives profit updates at regular intervals for all open positions.
     * Use this to monitor unrealized P&L in real-time and implement profit-based exit strategies.
     *
     * @param timerPeriodMilliseconds Update interval in milliseconds
     * @param ignoreEmptyData If true, skips updates when profit values haven't changed
     * @param responseObserver Observer to receive streaming position profit updates
     * @throws ApiExceptionMT5 if the subscription fails or connection is lost
     */
    public void onPositionProfit(
        int timerPeriodMilliseconds,
        boolean ignoreEmptyData,
        StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> responseObserver
    ) throws ApiExceptionMT5;
}
```

---

## 🔽 Input - `OnPositionProfitRequest`

| Parameter                   | Type      | Required | Description                                          |
| --------------------------- | --------- | -------- | ---------------------------------------------------- |
| `timer_period_milliseconds` | `int32`   | ✅       | Update interval in milliseconds (e.g., 1000 = 1 sec) |
| `ignore_empty_data`         | `bool`    | ✅       | Skip updates if no profit changes (recommended: false) |

---

## ⬆️ Output - `OnPositionProfitData` (stream)

Each update contains:

| Field               | Type                           | Description                                          |
| ------------------- | ------------------------------ | ---------------------------------------------------- |
| `new_positions`     | `OnPositionProfitPositionInfo[]` | Newly opened positions since last update           |
| `updated_positions` | `OnPositionProfitPositionInfo[]` | Positions with profit changes                      |
| `deleted_positions` | `OnPositionProfitPositionInfo[]` | Positions closed since last update                 |
| `account_info`      | `OnEventAccountInfo`           | Current account balance, equity, margin            |

Access using `reply.getData().<field>`.

### Position Info Fields (`OnPositionProfitPositionInfo`)

| Field            | Type     | Description                                          |
| ---------------- | -------- | ---------------------------------------------------- |
| `index`          | `int32`  | Position index                                       |
| `ticket`         | `int64`  | Position ticket number                               |
| `profit`         | `double` | Current profit/loss in account currency              |
| `position_symbol`| `String` | Trading symbol                                       |

### Account Info Fields (`OnEventAccountInfo`)

| Field          | Type     | Description                                          |
| -------------- | -------- | ---------------------------------------------------- |
| `balance`      | `double` | Account balance                                      |
| `credit`       | `double` | Credit amount                                        |
| `equity`       | `double` | Current equity (balance + profit)                    |
| `margin`       | `double` | Used margin                                          |
| `free_margin`  | `double` | Free margin available                                |
| `profit`       | `double` | Total profit across all positions                    |
| `margin_level` | `double` | Margin level (equity/margin * 100)                   |
| `login`        | `int64`  | Account login number                                 |

---

## 💬 Just the essentials

* **What it is.** Periodic updates of position P/L values.
* **Why you need it.** Track profit changes, implement stop strategies.
* **Returns.** Position profits + account equity at regular intervals.
* **Set interval.** You control update frequency (milliseconds).
* **Filter option.** Skip updates if nothing changed (`ignore_empty_data`).
* **Risk management.** Essential for monitoring drawdown and profit targets.

---

## 🎯 Purpose

Use this method when you need to:

* Monitor unrealized profit/loss in real-time.
* Implement trailing stop strategies based on profit.
* Track total account equity changes.
* Detect when profit reaches targets.
* Calculate real-time drawdown.
* Build profit-based alert systems.

---

## 🔗 Usage Examples

### 1) Basic profit monitoring

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

            StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> observer =
                new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {

                @Override
                public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
                    if (reply.hasData()) {
                        var data = reply.getData();
                        var accountInfo = data.getAccountInfo();

                        System.out.printf("\n💰 PROFIT UPDATE:%n");
                        System.out.printf("  Balance: $%.2f%n", accountInfo.getBalance());
                        System.out.printf("  Equity: $%.2f%n", accountInfo.getEquity());
                        System.out.printf("  Total Profit: $%.2f%n", accountInfo.getProfit());
                        System.out.printf("  Margin Level: %.2f%%%n", accountInfo.getMarginLevel());

                        // Show individual positions
                        for (int i = 0; i < data.getUpdatedPositionsCount(); i++) {
                            var pos = data.getUpdatedPositions(i);
                            System.out.printf("    #%d %s: $%.2f%n",
                                pos.getTicket(),
                                pos.getPositionSymbol(),
                                pos.getProfit());
                        }
                    }
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Profit stream error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Profit stream completed");
                }
            };

            // Update every 1000ms (1 second), don't skip empty updates
            account.onPositionProfit(1000, false, observer);

            // Keep alive
            Thread.sleep(60000); // 1 minute

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Profit target monitor with auto-close

```java
import java.util.HashMap;
import java.util.Map;

public class ProfitTargetMonitor {
    /**
     * Monitor positions and close when profit target reached
     */
    public static void monitorProfitTargets(
            MT5Account account,
            double profitTargetPerPosition) throws Exception {

        Map<Long, Double> positionTargets = new HashMap<>();

        StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();

                    // Track new positions
                    for (int i = 0; i < data.getNewPositionsCount(); i++) {
                        var pos = data.getNewPositions(i);
                        positionTargets.put(pos.getTicket(), profitTargetPerPosition);
                        System.out.printf("📍 Tracking #%d %s (target: $%.2f)%n",
                            pos.getTicket(),
                            pos.getPositionSymbol(),
                            profitTargetPerPosition);
                    }

                    // Check updated positions for profit target
                    for (int i = 0; i < data.getUpdatedPositionsCount(); i++) {
                        var pos = data.getUpdatedPositions(i);
                        long ticket = pos.getTicket();

                        if (positionTargets.containsKey(ticket)) {
                            double target = positionTargets.get(ticket);
                            double profit = pos.getProfit();

                            System.out.printf("  #%d %s: $%.2f / $%.2f%n",
                                ticket,
                                pos.getPositionSymbol(),
                                profit,
                                target);

                            // Check if target reached
                            if (profit >= target) {
                                System.out.printf("\n🎯 PROFIT TARGET REACHED - Closing #%d%n", ticket);
                                System.out.printf("   Profit: $%.2f (target was $%.2f)%n",
                                    profit, target);

                                try {
                                    // Close position
                                    var reply = account.orderClose(ticket, 0.0, 10);

                                    if (reply.getData().getReturnedCode() == 10009) {
                                        System.out.println("   ✅ Position closed successfully");
                                        positionTargets.remove(ticket);
                                    } else {
                                        System.out.println("   ❌ Failed to close: " +
                                            reply.getData().getComment());
                                    }
                                } catch (Exception e) {
                                    System.err.println("   Error closing: " + e.getMessage());
                                }
                            }
                        }
                    }

                    // Remove deleted positions from tracking
                    for (int i = 0; i < data.getDeletedPositionsCount(); i++) {
                        var pos = data.getDeletedPositions(i);
                        positionTargets.remove(pos.getTicket());
                        System.out.printf("❌ Position #%d closed (final P/L: $%.2f)%n",
                            pos.getTicket(), pos.getProfit());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Target monitor error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Target monitoring completed");
            }
        };

        System.out.printf("Monitoring profit targets ($%.2f per position)%n",
            profitTargetPerPosition);

        account.onPositionProfit(1000, false, observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage - close positions when they reach $100 profit
ProfitTargetMonitor.monitorProfitTargets(account, 100.0);
```

### 3) Drawdown monitor with alerts

```java
import java.util.concurrent.atomic.AtomicReference;

public class DrawdownMonitor {
    /**
     * Monitor account drawdown and send alerts
     */
    public static void monitorDrawdown(
            MT5Account account,
            double maxDrawdownPercent) throws Exception {

        AtomicReference<Double> startingBalance = new AtomicReference<>(null);
        AtomicReference<Double> peakEquity = new AtomicReference<>(null);

        StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
                if (reply.hasData()) {
                    var accountInfo = reply.getData().getAccountInfo();
                    double balance = accountInfo.getBalance();
                    double equity = accountInfo.getEquity();
                    double profit = accountInfo.getProfit();

                    // Initialize on first update
                    if (startingBalance.get() == null) {
                        startingBalance.set(balance);
                        peakEquity.set(equity);
                        System.out.printf("📊 Drawdown monitoring started%n");
                        System.out.printf("   Starting balance: $%.2f%n", balance);
                        System.out.printf("   Max drawdown threshold: %.1f%%%n", maxDrawdownPercent);
                        return;
                    }

                    // Update peak equity
                    if (equity > peakEquity.get()) {
                        peakEquity.set(equity);
                    }

                    // Calculate drawdown from peak
                    double drawdownAmount = peakEquity.get() - equity;
                    double drawdownPercent = (drawdownAmount / peakEquity.get()) * 100;

                    // Calculate drawdown from starting balance
                    double totalDrawdown = startingBalance.get() - equity;
                    double totalDrawdownPercent = (totalDrawdown / startingBalance.get()) * 100;

                    System.out.printf("\n💹 Equity: $%.2f | Profit: $%.2f%n", equity, profit);
                    System.out.printf("   Peak: $%.2f | Current DD: %.2f%% ($%.2f)%n",
                        peakEquity.get(), drawdownPercent, drawdownAmount);
                    System.out.printf("   From start: %.2f%% ($%.2f)%n",
                        totalDrawdownPercent, totalDrawdown);

                    // Alert on high drawdown
                    if (drawdownPercent >= maxDrawdownPercent) {
                        System.out.printf("\n⚠️  HIGH DRAWDOWN ALERT! ⚠️%n");
                        System.out.printf("   Current: %.2f%% (threshold: %.1f%%)%n",
                            drawdownPercent, maxDrawdownPercent);
                        System.out.printf("   Consider reducing risk or closing positions%n");
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Drawdown monitor error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Drawdown monitoring completed");
            }
        };

        account.onPositionProfit(2000, false, observer); // Update every 2 seconds
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage - alert on 10% drawdown
DrawdownMonitor.monitorDrawdown(account, 10.0);
```

### 4) Position performance tracker

```java
import java.util.HashMap;
import java.util.Map;

public class PositionPerformanceTracker {
    static class PositionStats {
        double maxProfit = 0;
        double maxLoss = 0;
        int updateCount = 0;
    }

    /**
     * Track performance metrics for each position
     */
    public static void trackPerformance(MT5Account account) throws Exception {

        Map<Long, PositionStats> positionStats = new HashMap<>();

        StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
                if (reply.hasData()) {
                    var data = reply.getData();

                    // Initialize new positions
                    for (int i = 0; i < data.getNewPositionsCount(); i++) {
                        var pos = data.getNewPositions(i);
                        positionStats.put(pos.getTicket(), new PositionStats());
                        System.out.printf("📈 Tracking #%d %s%n",
                            pos.getTicket(), pos.getPositionSymbol());
                    }

                    // Update statistics
                    for (int i = 0; i < data.getUpdatedPositionsCount(); i++) {
                        var pos = data.getUpdatedPositions(i);
                        long ticket = pos.getTicket();
                        double profit = pos.getProfit();

                        PositionStats stats = positionStats.computeIfAbsent(
                            ticket, k -> new PositionStats()
                        );

                        stats.updateCount++;

                        if (profit > stats.maxProfit) {
                            stats.maxProfit = profit;
                            System.out.printf("🟢 #%d %s - New peak: $%.2f%n",
                                ticket, pos.getPositionSymbol(), profit);
                        }

                        if (profit < stats.maxLoss) {
                            stats.maxLoss = profit;
                            System.out.printf("🔴 #%d %s - New low: $%.2f%n",
                                ticket, pos.getPositionSymbol(), profit);
                        }
                    }

                    // Report closed positions
                    for (int i = 0; i < data.getDeletedPositionsCount(); i++) {
                        var pos = data.getDeletedPositions(i);
                        long ticket = pos.getTicket();

                        if (positionStats.containsKey(ticket)) {
                            PositionStats stats = positionStats.get(ticket);

                            System.out.printf("\n📊 POSITION #%d %s CLOSED:%n",
                                ticket, pos.getPositionSymbol());
                            System.out.printf("   Final P/L: $%.2f%n", pos.getProfit());
                            System.out.printf("   Max Profit: $%.2f%n", stats.maxProfit);
                            System.out.printf("   Max Loss: $%.2f%n", stats.maxLoss);
                            System.out.printf("   Updates: %d%n", stats.updateCount);

                            double unrealizedGain = stats.maxProfit - pos.getProfit();
                            if (unrealizedGain > 0) {
                                System.out.printf("   Gave back: $%.2f%n", unrealizedGain);
                            }

                            positionStats.remove(ticket);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Performance tracker error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Performance tracking completed");
            }
        };

        System.out.println("Tracking position performance...");
        account.onPositionProfit(1000, false, observer);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
PositionPerformanceTracker.trackPerformance(account);
```

### 5) Equity curve logger

```java
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;

public class EquityCurveLogger {
    /**
     * Log equity curve to CSV for analysis
     */
    public static void logEquityCurve(MT5Account account, String filename)
            throws Exception {

        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        writer.println("Timestamp,Balance,Equity,Profit,Margin,FreeMargin,MarginLevel");
        writer.flush();

        StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
                if (reply.hasData()) {
                    var accountInfo = reply.getData().getAccountInfo();

                    String timestamp = Instant.now().toString();

                    // Write to CSV
                    writer.printf("%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f%n",
                        timestamp,
                        accountInfo.getBalance(),
                        accountInfo.getEquity(),
                        accountInfo.getProfit(),
                        accountInfo.getMargin(),
                        accountInfo.getFreeMargin(),
                        accountInfo.getMarginLevel());

                    writer.flush();

                    System.out.printf("📊 %s | Equity: $%.2f | Profit: $%.2f%n",
                        timestamp,
                        accountInfo.getEquity(),
                        accountInfo.getProfit());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Equity logger error: " + t.getMessage());
                writer.close();
            }

            @Override
            public void onCompleted() {
                System.out.println("Equity logging completed");
                writer.close();
            }
        };

        System.out.printf("Logging equity curve to %s%n", filename);
        account.onPositionProfit(5000, false, observer); // Every 5 seconds
        Thread.sleep(Integer.MAX_VALUE);
    }
}

// Usage
EquityCurveLogger.logEquityCurve(account, "equity_curve.csv");
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import mt5_term_api.*;

// Build request
Mt5TermApiSubscriptions.OnPositionProfitRequest request =
    Mt5TermApiSubscriptions.OnPositionProfitRequest.newBuilder()
        .setTimerPeriodMilliseconds(1000)  // 1 second
        .setIgnoreEmptyData(false)         // Don't skip empty updates
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Create observer
StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> observer =
    new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {
        @Override
        public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
            if (reply.hasData()) {
                var data = reply.getData();
                // Process profit updates
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

// Subscribe
subscriptionClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .onPositionProfit(request, observer);
```

---

## 📌 Important Notes

**Update Frequency:**
- You control update interval (milliseconds)
- Lower interval = more updates = more CPU/network
- Recommended: 1000-5000ms for real-time monitoring
- Too frequent = unnecessary load
- Too slow = delayed reactions

**Ignore Empty Data:**
- `false` = always receive updates (recommended)
- `true` = skip if no profit changes
- Use `false` for equity curve logging
- Use `true` to reduce bandwidth if only tracking changes

**Position Types:**
- **New positions** - opened since last update
- **Updated positions** - profit changed
- **Deleted positions** - closed since last update

**Account Info:**
- Included in every update
- Shows total account state
- Use for margin level monitoring
- Critical for risk management

**Thread Safety:**
- Updates arrive on gRPC thread
- Use thread-safe collections
- Synchronize access to shared state
- Consider concurrent data structures

**Best Practices:**
- Choose appropriate update interval
- Log equity curve for analysis
- Monitor margin level continuously
- Implement drawdown alerts
- Track peak equity for drawdown calculation
- Use for trailing stop implementation

**Common Use Cases:**
- Real-time profit monitoring
- Trailing stop strategies
- Drawdown alerts
- Profit target automation
- Equity curve generation
- Risk management systems

**Performance:**
- Updates fire at regular intervals
- Processing should be fast
- Offload heavy work to separate thread
- Consider batching operations
- Monitor memory for long-running streams

**Error Handling:**
- Connection loss triggers `onError()`
- Reconnect automatically
- May miss updates during disconnect
- Query current state after reconnect
