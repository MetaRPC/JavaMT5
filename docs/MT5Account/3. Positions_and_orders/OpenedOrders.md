# ✅ Getting All Open Positions and Pending Orders

> **Request:** retrieve complete information about all currently open positions and pending orders. Returns detailed data including prices, volumes, profits, timestamps, and more.

**API Information:**

* **SDK wrapper:** `MT5Account.openedOrders(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountHelper`
* **Proto definition:** `OpenedOrders` (defined in `mt5-term-api-account-helper.proto`)

### RPC

* **Service:** `mt5_term_api.AccountHelper`
* **Method:** `OpenedOrders(OpenedOrdersRequest) → OpenedOrdersReply`
* **Low‑level client (generated):** `AccountHelperGrpc.AccountHelperBlockingStub.openedOrders(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets complete details for all currently open positions and pending orders.
     * Returns comprehensive information including symbols, volumes, prices, profits, and timestamps.
     * Use this to retrieve the full state of all active trading operations with sorting options.
     *
     * @param sortType Sort order for results (by symbol, open time, profit, etc.)
     * @return Complete list of open positions and pending orders with all details
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.OpenedOrdersReply openedOrders(
        Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE sortType) throws ApiExceptionMT5;
}
```

**Request message:** `OpenedOrdersRequest { inputSortMode: BMT5_ENUM_OPENED_ORDER_SORT_TYPE }`

**Reply message:** `OpenedOrdersReply { data: OpenedOrdersData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter  | Type                                 | Required | Description                                      |
| ---------- | ------------------------------------ | -------- | ------------------------------------------------ |
| `sortType` | `BMT5_ENUM_OPENED_ORDER_SORT_TYPE`   | ✅       | Sort order for results (see enum below)          |

---

## ⬆️ Output - `OpenedOrdersData`

| Field            | Type                      | Description                                |
| ---------------- | ------------------------- | ------------------------------------------ |
| `opened_orders`  | `List<OpenedOrderInfo>`   | List of pending orders                     |
| `position_infos` | `List<PositionInfo>`      | List of open positions                     |

### `PositionInfo` Fields

| Field                  | Type        | Description                                       |
| ---------------------- | ----------- | ------------------------------------------------- |
| `index`                | `int`       | Index in result array                             |
| `ticket`               | `long`      | Unique position ticket number                     |
| `open_time`            | `Timestamp` | Position open time                                |
| `volume`               | `double`    | Position volume (lots)                            |
| `price_open`           | `double`    | Open price                                        |
| `stop_loss`            | `double`    | Stop Loss price (0 if not set)                    |
| `take_profit`          | `double`    | Take Profit price (0 if not set)                  |
| `price_current`        | `double`    | Current market price                              |
| `swap`                 | `double`    | Accumulated swap                                  |
| `profit`               | `double`    | Current profit/loss                               |
| `last_update_time`     | `Timestamp` | Last update timestamp                             |
| `type`                 | `enum`      | Position type (BUY/SELL)                          |
| `magic_number`         | `long`      | Expert Advisor ID                                 |
| `identifier`           | `long`      | Position identifier                               |
| `reason`               | `enum`      | Position open reason (CLIENT/MOBILE/WEB/EXPERT)   |
| `symbol`               | `String`    | Trading symbol                                    |
| `comment`              | `String`    | Position comment                                  |
| `external_id`          | `String`    | External system ID                                |
| `position_commission`  | `double`    | Commission charged                                |
| `account_login`        | `long`      | Account number                                    |

### `OpenedOrderInfo` Fields

| Field             | Type        | Description                                       |
| ----------------- | ----------- | ------------------------------------------------- |
| `index`           | `int`       | Index in result array                             |
| `ticket`          | `long`      | Unique order ticket number                        |
| `price_current`   | `double`    | Current market price                              |
| `price_open`      | `double`    | Order price                                       |
| `stop_limit`      | `double`    | Stop Limit price (for STOP_LIMIT orders)          |
| `stop_loss`       | `double`    | Stop Loss price                                   |
| `take_profit`     | `double`    | Take Profit price                                 |
| `volume_current`  | `double`    | Current volume                                    |
| `volume_initial`  | `double`    | Initial volume                                    |
| `magic_number`    | `long`      | Expert Advisor ID                                 |
| `reason`          | `int`       | Order placement reason                            |
| `type`            | `enum`      | Order type (BUY_LIMIT, SELL_STOP, etc.)           |
| `state`           | `enum`      | Order state                                       |
| `time_expiration` | `Timestamp` | Order expiration time                             |
| `time_setup`      | `Timestamp` | Order setup time                                  |
| `time_done`       | `Timestamp` | Order execution/cancellation time                 |
| `type_filling`    | `enum`      | Filling type (FOK, IOC, Return)                   |
| `type_time`       | `enum`      | Time in force type                                |
| `position_id`     | `long`      | Position ID                                       |
| `position_by_id`  | `long`      | Opposite position ID (for close by)               |
| `symbol`          | `String`    | Trading symbol                                    |
| `external_id`     | `String`    | External system ID                                |
| `comment`         | `String`    | Order comment                                     |
| `account_login`   | `long`      | Account number                                    |

---

## 🧱 Related enums (from proto)

### `BMT5_ENUM_OPENED_ORDER_SORT_TYPE`

| Enum Value                              | Value | Description                        |
| --------------------------------------- | ----- | ---------------------------------- |
| `BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC` | 0   | Sort by open time (oldest first)   |
| `BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC` | 1  | Sort by open time (newest first)   |
| `BMT5_OPENED_ORDER_SORT_BY_ORDER_TICKET_ID_ASC` | 2 | Sort by ticket ID (ascending)  |
| `BMT5_OPENED_ORDER_SORT_BY_ORDER_TICKET_ID_DESC` | 3 | Sort by ticket ID (descending)|

### `BMT5_ENUM_POSITION_TYPE`

| Enum Value              | Value | Description   |
| ----------------------- | ----- | ------------- |
| `BMT5_POSITION_TYPE_BUY` | 0    | Long position |
| `BMT5_POSITION_TYPE_SELL` | 1   | Short position |

### `BMT5_ENUM_POSITION_REASON`

| Enum Value                     | Value | Description                       |
| ------------------------------ | ----- | --------------------------------- |
| `BMT5_POSITION_REASON_CLIENT`  | 0     | Opened from desktop terminal      |
| `BMT5_POSITION_REASON_MOBILE`  | 1     | Opened from mobile app            |
| `BMT5_POSITION_REASON_WEB`     | 2     | Opened from web terminal          |
| `BMT5_POSITION_REASON_EXPERT`  | 3     | Opened by Expert Advisor          |
| `ORDER_REASON_SL`              | 4     | Closed by Stop Loss               |
| `ORDER_REASON_TP`              | 5     | Closed by Take Profit             |
| `ORDER_REASON_SO`              | 6     | Closed by Stop Out (margin call)  |

---

## 💬 Just the essentials

* **What it is.** Comprehensive RPC returning ALL open positions and pending orders with full details.
* **Why you need it.** Essential for portfolio monitoring, risk management, and trading decisions.
* **Performance.** More expensive than `positionsTotal()` - fetches complete data for all positions.
* **Two lists.** Returns both positions (actively in market) and pending orders (not yet executed).

---

## 🎯 Purpose

Use this method when you need to:

* Display all active trades in UI/dashboard.
* Calculate total exposure and risk.
* Monitor profit/loss across all positions.
* Check for positions on specific symbols.
* Implement automated risk management.
* Close or modify existing positions.

---

## 🧩 Notes & Tips

* The method returns two separate lists: positions and pending orders.
* Use `positionsTotal()` first to check if positions exist before calling this.
* Profit and swap are in account currency.
* Timestamps are protobuf `Timestamp` - convert using `.getSeconds()` for Unix time.
* The method uses automatic reconnection via `executeWithReconnect()`.
* Empty lists returned if no positions/orders exist.

---

## 🔗 Usage Examples

### 1) Get all open positions

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get all opened orders and positions
            var reply = account.openedOrders(
                Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                    .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC
            );
            var data = reply.getData();

            System.out.printf("Open positions: %d%n", data.getPositionInfosCount());
            System.out.printf("Pending orders: %d%n", data.getOpenedOrdersCount());

            // Display positions
            for (var position : data.getPositionInfosList()) {
                System.out.printf("Ticket #%d: %s %.2f lots @ %.5f, P/L: %.2f%n",
                    position.getTicket(),
                    position.getSymbol(),
                    position.getVolume(),
                    position.getPriceOpen(),
                    position.getProfit()
                );
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Calculate total profit/loss

```java
public class ProfitCalculator {
    /**
     * Calculate total P/L across all positions
     */
    public static double getTotalProfit(MT5Account account) throws ApiExceptionMT5 {
        var reply = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
        );

        double totalProfit = 0.0;
        double totalSwap = 0.0;

        for (var position : reply.getData().getPositionInfosList()) {
            totalProfit += position.getProfit();
            totalSwap += position.getSwap();
        }

        double netProfit = totalProfit + totalSwap;

        System.out.printf("Gross P/L:  %.2f%n", totalProfit);
        System.out.printf("Swap:       %.2f%n", totalSwap);
        System.out.printf("Net P/L:    %.2f%n", netProfit);

        return netProfit;
    }
}

// Usage
double totalPL = ProfitCalculator.getTotalProfit(account);
```

### 3) Find positions by symbol

```java
public class PositionFinder {
    /**
     * Find all positions for specific symbol
     */
    public static java.util.List<Mt5TermApiAccountHelper.PositionInfo> findBySymbol(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        var reply = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC
        );

        return reply.getData().getPositionInfosList().stream()
            .filter(p -> p.getSymbol().equals(symbol))
            .collect(java.util.stream.Collectors.toList());
    }
}

// Usage
var eurusdPositions = PositionFinder.findBySymbol(account, "EURUSD");
System.out.printf("Found %d EURUSD positions%n", eurusdPositions.size());

for (var pos : eurusdPositions) {
    String type = pos.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY
        ? "BUY" : "SELL";
    System.out.printf("  #%d %s %.2f lots, P/L: %.2f%n",
        pos.getTicket(), type, pos.getVolume(), pos.getProfit());
}
```

### 4) Display positions table

```java
public class PositionsTable {
    public static void printTable(MT5Account account) throws ApiExceptionMT5 {
        var reply = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC
        );

        var positions = reply.getData().getPositionInfosList();

        if (positions.isEmpty()) {
            System.out.println("No open positions");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        OPEN POSITIONS                                 ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Ticket   │ Symbol  │ Type │ Volume │ Open    │ Current │ P/L      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");

        double totalPL = 0.0;

        for (var pos : positions) {
            String type = pos.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY
                ? "BUY " : "SELL";

            System.out.printf("║ %-8d │ %-7s │ %s │ %6.2f │ %.5f │ %.5f │ %8.2f ║%n",
                pos.getTicket(),
                pos.getSymbol(),
                type,
                pos.getVolume(),
                pos.getPriceOpen(),
                pos.getPriceCurrent(),
                pos.getProfit()
            );

            totalPL += pos.getProfit();
        }

        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Total P/L: %54.2f ║%n", totalPL);
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
    }
}

// Usage
PositionsTable.printTable(account);
```

### 5) Check risk exposure

```java
public class RiskCalculator {
    public record RiskMetrics(
        int totalPositions,
        double totalVolume,
        double totalProfit,
        double largestLoss,
        java.util.Map<String, Integer> symbolCounts
    ) {}

    /**
     * Calculate risk metrics
     */
    public static RiskMetrics calculateRisk(MT5Account account) throws ApiExceptionMT5 {
        var reply = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
        );

        var positions = reply.getData().getPositionInfosList();

        double totalVolume = 0.0;
        double totalProfit = 0.0;
        double largestLoss = 0.0;
        java.util.Map<String, Integer> symbolCounts = new java.util.HashMap<>();

        for (var pos : positions) {
            totalVolume += pos.getVolume();
            totalProfit += pos.getProfit();

            if (pos.getProfit() < largestLoss) {
                largestLoss = pos.getProfit();
            }

            symbolCounts.merge(pos.getSymbol(), 1, Integer::sum);
        }

        return new RiskMetrics(
            positions.size(),
            totalVolume,
            totalProfit,
            largestLoss,
            symbolCounts
        );
    }

    /**
     * Print risk report
     */
    public static void printRiskReport(RiskMetrics metrics) {
        System.out.println("\n=== RISK ANALYSIS ===");
        System.out.printf("Total positions:  %d%n", metrics.totalPositions());
        System.out.printf("Total volume:     %.2f lots%n", metrics.totalVolume());
        System.out.printf("Total P/L:        %.2f%n", metrics.totalProfit());
        System.out.printf("Largest loss:     %.2f%n", metrics.largestLoss());
        System.out.println("\nPositions by symbol:");
        metrics.symbolCounts().forEach((symbol, count) ->
            System.out.printf("  %s: %d position(s)%n", symbol, count)
        );
    }
}

// Usage
var risk = RiskCalculator.calculateRisk(account);
RiskCalculator.printRiskReport(risk);
```

### 6) Monitor positions in real-time

```java
public class PositionMonitor {
    /**
     * Monitor positions and alert on changes
     */
    public static void monitor(MT5Account account, int durationSeconds)
            throws InterruptedException {

        System.out.println("Monitoring positions...");
        System.out.println("═".repeat(70));

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (durationSeconds * 1000L);

        java.util.Set<Long> lastTickets = new java.util.HashSet<>();

        while (System.currentTimeMillis() < endTime) {
            try {
                var reply = account.openedOrders(
                    Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                        .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC
                );

                java.util.Set<Long> currentTickets = reply.getData().getPositionInfosList()
                    .stream()
                    .map(Mt5TermApiAccountHelper.PositionInfo::getTicket)
                    .collect(java.util.stream.Collectors.toSet());

                // Check for new positions
                for (long ticket : currentTickets) {
                    if (!lastTickets.contains(ticket)) {
                        System.out.printf("[%s] 🆕 New position opened: #%d%n",
                            java.time.LocalTime.now(), ticket);
                    }
                }

                // Check for closed positions
                for (long ticket : lastTickets) {
                    if (!currentTickets.contains(ticket)) {
                        System.out.printf("[%s] ❌ Position closed: #%d%n",
                            java.time.LocalTime.now(), ticket);
                    }
                }

                lastTickets = currentTickets;
                Thread.sleep(2000);

            } catch (ApiExceptionMT5 e) {
                System.err.println("Error: " + e.getMessage());
                Thread.sleep(2000);
            }
        }

        System.out.println("═".repeat(70));
        System.out.println("Monitoring stopped");
    }
}

// Usage
PositionMonitor.monitor(account, 60);
```

### 7) Export positions to CSV

```java
public class PositionExporter {
    /**
     * Export all positions to CSV file
     */
    public static void exportToCSV(MT5Account account, String filename)
            throws Exception {

        var reply = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC
        );

        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            // CSV header
            writer.println("Ticket,Symbol,Type,Volume,OpenPrice,CurrentPrice,StopLoss,TakeProfit,Profit,Swap,OpenTime,Comment");

            for (var pos : reply.getData().getPositionInfosList()) {
                String type = pos.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY
                    ? "BUY" : "SELL";

                long openTimeSec = pos.getOpenTime().getSeconds();
                String openTime = java.time.Instant.ofEpochSecond(openTimeSec).toString();

                writer.printf("%d,%s,%s,%.2f,%.5f,%.5f,%.5f,%.5f,%.2f,%.2f,%s,\"%s\"%n",
                    pos.getTicket(),
                    pos.getSymbol(),
                    type,
                    pos.getVolume(),
                    pos.getPriceOpen(),
                    pos.getPriceCurrent(),
                    pos.getStopLoss(),
                    pos.getTakeProfit(),
                    pos.getProfit(),
                    pos.getSwap(),
                    openTime,
                    pos.getComment()
                );
            }
        }

        System.out.printf("✅ Exported %d positions to %s%n",
            reply.getData().getPositionInfosCount(), filename);
    }
}

// Usage
PositionExporter.exportToCSV(account, "positions.csv");
```

### 8) Find positions without stop loss

```java
public class StopLossChecker {
    /**
     * Find positions without stop loss
     */
    public static void findUnprotectedPositions(MT5Account account)
            throws ApiExceptionMT5 {

        var reply = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
                .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC
        );

        var unprotected = reply.getData().getPositionInfosList().stream()
            .filter(p -> p.getStopLoss() == 0.0)
            .collect(java.util.stream.Collectors.toList());

        if (unprotected.isEmpty()) {
            System.out.println("✅ All positions have stop loss");
            return;
        }

        System.out.printf("⚠️ Found %d positions WITHOUT stop loss:%n", unprotected.size());

        for (var pos : unprotected) {
            String type = pos.getType() == Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY
                ? "BUY" : "SELL";

            System.out.printf("  #%d %s %s %.2f lots @ %.5f (P/L: %.2f)%n",
                pos.getTicket(),
                pos.getSymbol(),
                type,
                pos.getVolume(),
                pos.getPriceOpen(),
                pos.getProfit()
            );
        }
    }
}

// Usage
StopLossChecker.findUnprotectedPositions(account);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiAccountHelper.OpenedOrdersRequest request =
    Mt5TermApiAccountHelper.OpenedOrdersRequest.newBuilder()
        .setInputSortMode(Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE
            .BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountHelper.OpenedOrdersReply reply = accountClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .openedOrders(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
var data = reply.getData();
var positions = data.getPositionInfosList();
var orders = data.getOpenedOrdersList();
```
