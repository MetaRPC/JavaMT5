# 📜 Get Historical Orders and Deals

> **Request:** retrieve historical orders and deals for a specified time period with pagination support. Returns comprehensive order and deal data including execution details, prices, and timestamps.

**API Information:**

* **SDK wrapper:** `MT5Account.orderHistory(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountHelper`
* **Proto definition:** `OrderHistory` (defined in `mt5-term-api-account-helper.proto`)

### RPC

* **Service:** `mt5_term_api.AccountHelper`
* **Method:** `OrderHistory(OrderHistoryRequest) → OrderHistoryReply`
* **Low‑level client (generated):** `AccountHelperGrpc.AccountHelperBlockingStub.orderHistory(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Retrieves historical orders and deals for the specified time period.
     * Supports pagination and different sorting options.
     * Returns both order data (placement, modification) and deal data (execution, closure).
     *
     * @param from Start time (server time)
     * @param to End time (server time)
     * @param sortType Sort order for results
     * @param pageNumber Page number (0-based, use 0 for first page)
     * @param itemsPerPage Items per page (use 0 for all items)
     * @return Reply containing paginated history data
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.OrderHistoryReply orderHistory(
        com.google.protobuf.Timestamp from,
        com.google.protobuf.Timestamp to,
        Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE sortType,
        int pageNumber,
        int itemsPerPage
    ) throws ApiExceptionMT5;
}
```

**Request message:** `OrderHistoryRequest { inputFrom, inputTo, inputSortMode, pageNumber, itemsPerPage }`

**Reply message:** `OrderHistoryReply { data: OrdersHistoryData }` or `{ error: Error }`

---

## 🔽 Input Parameters

| Parameter      | Type                                       | Required | Description                                          |
| -------------- | ------------------------------------------ | -------- | ---------------------------------------------------- |
| `from`         | `Timestamp`                                | ✅       | Start time (server time) for history period          |
| `to`           | `Timestamp`                                | ✅       | End time (server time) for history period            |
| `sortType`     | `BMT5_ENUM_ORDER_HISTORY_SORT_TYPE`        | ✅       | Sorting mode for results                             |
| `pageNumber`   | `int`                                      | ✅       | Page number (0-based); use 0 for first page          |
| `itemsPerPage` | `int`                                      | ✅       | Items per page; use 0 to get all items               |

### Enum: `BMT5_ENUM_ORDER_HISTORY_SORT_TYPE`

| Value                                  | Number | Description                                |
| -------------------------------------- | ------ | ------------------------------------------ |
| `BMT5_SORT_BY_OPEN_TIME_ASC`           | 0      | Sort by order setup time (ascending)       |
| `BMT5_SORT_BY_OPEN_TIME_DESC`          | 1      | Sort by order setup time (descending)      |
| `BMT5_SORT_BY_CLOSE_TIME_ASC`          | 2      | Sort by order done time (ascending)        |
| `BMT5_SORT_BY_CLOSE_TIME_DESC`         | 3      | Sort by order done time (descending)       |
| `BMT5_SORT_BY_ORDER_TICKET_ID_ASC`     | 4      | Sort by ticket ID (ascending)              |
| `BMT5_SORT_BY_ORDER_TICKET_ID_DESC`    | 5      | Sort by ticket ID (descending)             |

---

## ⬆️ Output - `OrdersHistoryData`

| Field           | Type                  | Description                                          |
| --------------- | --------------------- | ---------------------------------------------------- |
| `arrayTotal`    | `int`                 | Total number of items in full history (all pages)    |
| `pageNumber`    | `int`                 | Current page number (0-based)                        |
| `itemsPerPage`  | `int`                 | Items per page (0 = all items)                       |
| `history_data`  | `List<HistoryData>`   | List of history entries (orders + deals)             |

Access using:
- `reply.getData().getArrayTotal()` - Total items count
- `reply.getData().getPageNumber()` - Current page
- `reply.getData().getItemsPerPage()` - Items per page
- `reply.getData().getHistoryDataList()` - List of history entries

### Structure: `HistoryData`

Each history entry contains:

| Field           | Type                  | Description                                          |
| --------------- | --------------------- | ---------------------------------------------------- |
| `index`         | `int`                 | Index in result list                                 |
| `history_order` | `OrderHistoryData`    | Order information (placement, modification, status)  |
| `history_deal`  | `DealHistoryData`     | Deal information (execution, profit, commission)     |

### Structure: `OrderHistoryData` (Order Details)

| Field               | Type                              | Description                                          |
| ------------------- | --------------------------------- | ---------------------------------------------------- |
| `ticket`            | `long`                            | Order ticket number (unique ID)                      |
| `setup_time`        | `Timestamp`                       | Order setup time (when placed)                       |
| `done_time`         | `Timestamp`                       | Order done time (when filled/cancelled/expired)      |
| `state`             | `BMT5_ENUM_ORDER_STATE`           | Order state (filled, cancelled, expired, etc.)       |
| `price_current`     | `double`                          | Current price (at time of request)                   |
| `price_open`        | `double`                          | Order price (limit/stop price)                       |
| `stop_limit`        | `double`                          | Stop limit price (for stop-limit orders)             |
| `stop_loss`         | `double`                          | Stop loss price                                      |
| `take_profit`       | `double`                          | Take profit price                                    |
| `volume_current`    | `double`                          | Current unfilled volume (lots)                       |
| `volume_initial`    | `double`                          | Initial order volume (lots)                          |
| `magic_number`      | `long`                            | Expert Advisor ID (magic number)                     |
| `type`              | `BMT5_ENUM_ORDER_TYPE`            | Order type (BUY, SELL, BUY_LIMIT, etc.)              |
| `time_expiration`   | `Timestamp`                       | Order expiration time                                |
| `type_filling`      | `BMT5_ENUM_ORDER_TYPE_FILLING`    | Filling type (FOK, IOC, RETURN)                      |
| `type_time`         | `BMT5_ENUM_ORDER_TYPE_TIME`       | Time type (GTC, DAY, SPECIFIED)                      |
| `position_id`       | `long`                            | Position ID this order belongs to                    |
| `symbol`            | `string`                          | Symbol name (e.g., "EURUSD")                         |
| `external_id`       | `string`                          | External order ID                                    |

### Structure: `DealHistoryData` (Deal/Execution Details)

| Field           | Type                          | Description                                          |
| --------------- | ----------------------------- | ---------------------------------------------------- |
| `ticket`        | `long`                        | Deal ticket number (unique ID)                       |
| `profit`        | `double`                      | Profit from deal (in account currency)               |
| `commission`    | `double`                      | Commission charged                                   |
| `fee`           | `double`                      | Additional fees                                      |
| `price`         | `double`                      | Execution price                                      |
| `stop_loss`     | `double`                      | Stop loss at deal execution                          |
| `take_profit`   | `double`                      | Take profit at deal execution                        |
| `swap`          | `double`                      | Swap charges                                         |
| `volume`        | `double`                      | Deal volume (lots)                                   |
| `entry_type`    | `BMT5_ENUM_DEAL_ENTRY_TYPE`   | Entry type (IN, OUT, INOUT, OUT_BY)                  |
| `time`          | `Timestamp`                   | Deal execution time                                  |
| `type`          | `BMT5_ENUM_DEAL_TYPE`         | Deal type (BUY, SELL, BALANCE, etc.)                 |
| `reason`        | `BMT5_ENUM_DEAL_REASON`       | Deal reason (CLIENT, SL, TP, etc.)                   |
| `position_id`   | `long`                        | Position ID this deal belongs to                     |

---

## 📋 Enums Reference

### `BMT5_ENUM_ORDER_STATE` (Order Status)

| Value                          | Number | Description                                          |
| ------------------------------ | ------ | ---------------------------------------------------- |
| `BMT5_ORDER_STATE_STARTED`     | 0      | Order checked, but not yet accepted by broker        |
| `BMT5_ORDER_STATE_PLACED`      | 1      | Order accepted by broker                             |
| `BMT5_ORDER_STATE_CANCELED`    | 2      | Order canceled by client                             |
| `BMT5_ORDER_STATE_PARTIAL`     | 3      | Order partially executed                             |
| `BMT5_ORDER_STATE_FILLED`      | 4      | Order fully executed                                 |
| `BMT5_ORDER_STATE_REJECTED`    | 5      | Order rejected by broker                             |
| `BMT5_ORDER_STATE_EXPIRED`     | 6      | Order expired                                        |
| `BMT5_ORDER_STATE_REQUEST_ADD` | 7      | Order is being registered                            |
| `BMT5_ORDER_STATE_REQUEST_MODIFY` | 8   | Order is being modified                              |
| `BMT5_ORDER_STATE_REQUEST_CANCEL` | 9   | Order is being deleted                               |

### `BMT5_ENUM_ORDER_TYPE` (Order Types)

| Value                             | Number | Description                                          |
| --------------------------------- | ------ | ---------------------------------------------------- |
| `BMT5_ORDER_TYPE_BUY`             | 0      | Market Buy order                                     |
| `BMT5_ORDER_TYPE_SELL`            | 1      | Market Sell order                                    |
| `BMT5_ORDER_TYPE_BUY_LIMIT`       | 2      | Buy Limit pending order                              |
| `BMT5_ORDER_TYPE_SELL_LIMIT`      | 3      | Sell Limit pending order                             |
| `BMT5_ORDER_TYPE_BUY_STOP`        | 4      | Buy Stop pending order                               |
| `BMT5_ORDER_TYPE_SELL_STOP`       | 5      | Sell Stop pending order                              |
| `BMT5_ORDER_TYPE_BUY_STOP_LIMIT`  | 6      | Buy Stop Limit order                                 |
| `BMT5_ORDER_TYPE_SELL_STOP_LIMIT` | 7      | Sell Stop Limit order                                |
| `BMT5_ORDER_TYPE_CLOSE_BY`        | 8      | Order to close position by opposite one              |

### `BMT5_ENUM_ORDER_TYPE_FILLING` (Fill Policies)

| Value                        | Number | Description                                          |
| ---------------------------- | ------ | ---------------------------------------------------- |
| `BMT5_ORDER_FILLING_FOK`     | 0      | Fill or Kill (full volume or nothing)                |
| `BMT5_ORDER_FILLING_IOC`     | 1      | Immediate or Cancel (partial fills allowed)          |
| `BMT5_ORDER_FILLING_RETURN`  | 2      | Return orders (remaining volume stays as order)      |
| `BMT5_ORDER_FILLING_BOC`     | 3      | Book or Cancel                                       |

### `BMT5_ENUM_ORDER_TYPE_TIME` (Time-in-Force)

| Value                              | Number | Description                                          |
| ---------------------------------- | ------ | ---------------------------------------------------- |
| `BMT5_ORDER_TIME_GTC`              | 0      | Good Till Cancel                                     |
| `BMT5_ORDER_TIME_DAY`              | 1      | Good Till Current Trade Day                          |
| `BMT5_ORDER_TIME_SPECIFIED`        | 2      | Good Till Specified Time                             |
| `BMT5_ORDER_TIME_SPECIFIED_DAY`    | 3      | Good Till End of Specified Day (23:59:59)            |

### `BMT5_ENUM_DEAL_ENTRY_TYPE` (Deal Entry Direction)

| Value                      | Number | Description                                          |
| -------------------------- | ------ | ---------------------------------------------------- |
| `BMT5_DEAL_ENTRY_IN`       | 0      | Entry into market (opening position)                 |
| `BMT5_DEAL_ENTRY_OUT`      | 1      | Exit from market (closing position)                  |
| `BMT5_DEAL_ENTRY_INOUT`    | 2      | Position reversal (close + open opposite)            |
| `BMT5_DEAL_ENTRY_OUT_BY`   | 3      | Closing by opposite position                         |

### `BMT5_ENUM_DEAL_TYPE` (Deal Operation Type)

| Value                                  | Number | Description                                          |
| -------------------------------------- | ------ | ---------------------------------------------------- |
| `BMT5_DEAL_TYPE_BUY`                   | 0      | Buy operation                                        |
| `BMT5_DEAL_TYPE_SELL`                  | 1      | Sell operation                                       |
| `BMT5_DEAL_TYPE_BALANCE`               | 2      | Balance operation                                    |
| `BMT5_DEAL_TYPE_CREDIT`                | 3      | Credit operation                                     |
| `BMT5_DEAL_TYPE_CHARGE`                | 4      | Additional charge                                    |
| `BMT5_DEAL_TYPE_CORRECTION`            | 5      | Correction                                           |
| `BMT5_DEAL_TYPE_BONUS`                 | 6      | Bonus                                                |
| `BMT5_DEAL_TYPE_COMMISSION`            | 7      | Additional commission                                |
| `BMT5_DEAL_TYPE_COMMISSION_DAILY`      | 8      | Daily commission                                     |
| `BMT5_DEAL_TYPE_COMMISSION_MONTHLY`    | 9      | Monthly commission                                   |
| `BMT5_DEAL_TYPE_COMMISSION_AGENT_DAILY` | 10    | Daily agent commission                               |
| `BMT5_DEAL_TYPE_COMMISSION_AGENT_MONTHLY` | 11  | Monthly agent commission                             |
| `BMT5_DEAL_TYPE_INTEREST`              | 12     | Interest rate                                        |
| `BMT5_DEAL_TYPE_BUY_CANCELED`          | 13     | Canceled buy deal                                    |
| `BMT5_DEAL_TYPE_SELL_CANCELED`         | 14     | Canceled sell deal                                   |
| `BMT5_DEAL_DIVIDEND`                   | 15     | Dividend operations                                  |
| `BMT5_DEAL_DIVIDEND_FRANKED`           | 16     | Franked (non-taxable) dividend                       |
| `BMT5_DEAL_TAX`                        | 17     | Tax charges                                          |

### `BMT5_ENUM_DEAL_REASON` (Deal Execution Reason)

| Value                              | Number | Description                                          |
| ---------------------------------- | ------ | ---------------------------------------------------- |
| `BMT5_DEAL_REASON_CLIENT`          | 0      | Deal from desktop terminal order                     |
| `BMT5_DEAL_REASON_MOBILE`          | 1      | Deal from mobile app order                           |
| `BMT5_DEAL_REASON_WEB`             | 2      | Deal from web platform order                         |
| `BMT5_DEAL_REASON_EXPERT`          | 3      | Deal from Expert Advisor/script                      |
| `BMT5_DEAL_REASON_SL`              | 4      | Deal from Stop Loss activation                       |
| `BMT5_DEAL_REASON_TP`              | 5      | Deal from Take Profit activation                     |
| `BMT5_DEAL_REASON_SO`              | 6      | Deal from Stop Out                                   |
| `BMT5_DEAL_REASON_ROLLOVER`        | 7      | Deal due to rollover                                 |
| `BMT5_DEAL_REASON_VMARGIN`         | 8      | Deal after variation margin charge                   |
| `BMT5_DEAL_REASON_SPLIT`           | 9      | Deal after stock split                               |
| `BMT5_DEAL_REASON_CORPORATE_ACTION` | 10    | Deal from corporate action                           |

---

## 💬 Just the essentials

* **What it is.** Comprehensive RPC for retrieving order and deal history with pagination.
* **Why you need it.** Analyze trading performance, generate reports, track execution quality.
* **Two data types.** Returns both orders (placement/modification) and deals (actual executions).
* **Pagination.** Handles large history datasets with page-based navigation.
* **Time range.** Specify exact time period using server timestamps.
* **Sorting.** Multiple sort options by time or ticket ID.

---

## 🎯 Purpose

Use this method when you need to:

* Analyze past trading activity and performance.
* Generate trading reports and statistics.
* Track order execution quality (slippage, fills).
* Calculate realized profits and losses.
* Audit trading history for compliance.
* Debug Expert Advisor trading strategies.
* Export trading history for analysis tools.

---

## 🧩 Notes & Tips

* **Server time.** All timestamps use server time, not local time.
* **Pagination.** Use `pageNumber=0, itemsPerPage=0` to get all items at once.
* **Two data types.** Each `HistoryData` contains both order and deal info.
* **Order vs Deal.** Orders track placement/modification; deals track actual execution/closure.
* **Large datasets.** For long time ranges, use pagination to avoid timeouts.
* **Sorting.** Choose sort type based on your analysis needs (time vs ticket).
* **Performance.** Shorter time ranges = faster response times.
* **Auto-reconnect.** Uses `executeWithReconnect()` for reliability.

---

## 🔗 Usage Examples

### 1) Basic history retrieval (last 7 days)

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountHelper;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get last 7 days of history
            Instant now = Instant.now();
            Instant weekAgo = now.minus(7, ChronoUnit.DAYS);

            Timestamp from = Timestamp.newBuilder()
                .setSeconds(weekAgo.getEpochSecond())
                .build();
            Timestamp to = Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .build();

            Mt5TermApiAccountHelper.OrderHistoryReply reply =
                account.orderHistory(
                    from,
                    to,
                    Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
                    0,  // First page
                    0   // All items
                );

            var data = reply.getData();
            System.out.printf("Total history items: %d%n", data.getArrayTotal());

            for (var historyItem : data.getHistoryDataList()) {
                var order = historyItem.getHistoryOrder();
                var deal = historyItem.getHistoryDeal();

                System.out.printf("\nOrder #%d - %s - %s%n",
                    order.getTicket(),
                    order.getSymbol(),
                    order.getType()
                );

                if (deal.getTicket() > 0) {
                    System.out.printf("  Deal #%d - Profit: %.2f, Commission: %.2f%n",
                        deal.getTicket(),
                        deal.getProfit(),
                        deal.getCommission()
                    );
                }
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Calculate total profit from history

```java
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ProfitCalculator {
    /**
     * Calculate total profit/loss for a time period
     */
    public static double calculateTotalProfit(
            MT5Account account,
            int daysBack) throws ApiExceptionMT5 {

        Instant now = Instant.now();
        Instant start = now.minus(daysBack, ChronoUnit.DAYS);

        Timestamp from = Timestamp.newBuilder()
            .setSeconds(start.getEpochSecond())
            .build();
        Timestamp to = Timestamp.newBuilder()
            .setSeconds(now.getEpochSecond())
            .build();

        var reply = account.orderHistory(
            from, to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
            0, 0
        );

        double totalProfit = 0.0;
        double totalCommission = 0.0;
        double totalFees = 0.0;
        double totalSwap = 0.0;

        for (var historyItem : reply.getData().getHistoryDataList()) {
            var deal = historyItem.getHistoryDeal();
            totalProfit += deal.getProfit();
            totalCommission += deal.getCommission();
            totalFees += deal.getFee();
            totalSwap += deal.getSwap();
        }

        double netProfit = totalProfit - totalCommission - totalFees + totalSwap;

        System.out.printf("Trading Period: Last %d days%n", daysBack);
        System.out.printf("Gross Profit: %.2f%n", totalProfit);
        System.out.printf("Commission: %.2f%n", totalCommission);
        System.out.printf("Fees: %.2f%n", totalFees);
        System.out.printf("Swap: %.2f%n", totalSwap);
        System.out.printf("Net Profit: %.2f%n", netProfit);

        return netProfit;
    }
}

// Usage
double profit = ProfitCalculator.calculateTotalProfit(account, 30);
System.out.printf("Monthly profit: %.2f%n", profit);
```

### 3) Paginated history retrieval

```java
public class PaginatedHistory {
    /**
     * Retrieve history with pagination
     */
    public static void fetchPaginated(
            MT5Account account,
            Timestamp from,
            Timestamp to,
            int itemsPerPage) throws ApiExceptionMT5 {

        int currentPage = 0;
        int totalItems = 0;

        System.out.println("Fetching paginated history...\n");

        do {
            var reply = account.orderHistory(
                from, to,
                Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
                currentPage,
                itemsPerPage
            );

            var data = reply.getData();
            totalItems = data.getArrayTotal();

            System.out.printf("Page %d/%d (Items: %d/%d)%n",
                currentPage + 1,
                (totalItems + itemsPerPage - 1) / itemsPerPage,
                data.getHistoryDataList().size(),
                totalItems
            );

            // Process items on this page
            for (var item : data.getHistoryDataList()) {
                var order = item.getHistoryOrder();
                System.out.printf("  Order #%d - %s%n",
                    order.getTicket(),
                    order.getSymbol()
                );
            }

            currentPage++;

            // Check if more pages exist
            int itemsProcessed = currentPage * itemsPerPage;
            if (itemsProcessed >= totalItems) {
                break;
            }

        } while (true);

        System.out.printf("\nTotal items processed: %d%n", totalItems);
    }
}

// Usage
Instant now = Instant.now();
Instant monthAgo = now.minus(30, ChronoUnit.DAYS);

Timestamp from = Timestamp.newBuilder()
    .setSeconds(monthAgo.getEpochSecond())
    .build();
Timestamp to = Timestamp.newBuilder()
    .setSeconds(now.getEpochSecond())
    .build();

PaginatedHistory.fetchPaginated(account, from, to, 50);
```

### 4) Filter by symbol

```java
public class SymbolFilter {
    /**
     * Get history for specific symbol
     */
    public static java.util.List<Mt5TermApiAccountHelper.HistoryData> getSymbolHistory(
            MT5Account account,
            String symbol,
            int daysBack) throws ApiExceptionMT5 {

        Instant now = Instant.now();
        Instant start = now.minus(daysBack, ChronoUnit.DAYS);

        Timestamp from = Timestamp.newBuilder()
            .setSeconds(start.getEpochSecond())
            .build();
        Timestamp to = Timestamp.newBuilder()
            .setSeconds(now.getEpochSecond())
            .build();

        var reply = account.orderHistory(
            from, to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
            0, 0
        );

        // Filter by symbol
        return reply.getData().getHistoryDataList().stream()
            .filter(item -> item.getHistoryOrder().getSymbol().equals(symbol))
            .toList();
    }

    /**
     * Print symbol history summary
     */
    public static void printSymbolSummary(
            java.util.List<Mt5TermApiAccountHelper.HistoryData> history,
            String symbol) {

        double totalProfit = history.stream()
            .mapToDouble(item -> item.getHistoryDeal().getProfit())
            .sum();

        long tradeCount = history.stream()
            .filter(item -> item.getHistoryDeal().getTicket() > 0)
            .count();

        System.out.printf("\n%s History Summary%n", symbol);
        System.out.println("═".repeat(40));
        System.out.printf("Total Trades: %d%n", tradeCount);
        System.out.printf("Total Profit: %.2f%n", totalProfit);
        System.out.printf("Average Per Trade: %.2f%n",
            tradeCount > 0 ? totalProfit / tradeCount : 0.0);
    }
}

// Usage
var eurusdHistory = SymbolFilter.getSymbolHistory(account, "EURUSD", 30);
SymbolFilter.printSymbolSummary(eurusdHistory, "EURUSD");
```

### 5) Trading statistics analyzer

```java
public class TradingStats {
    public record Statistics(
        int totalTrades,
        int winningTrades,
        int losingTrades,
        double winRate,
        double totalProfit,
        double totalLoss,
        double netProfit,
        double largestWin,
        double largestLoss,
        double averageWin,
        double averageLoss,
        double profitFactor
    ) {}

    /**
     * Calculate detailed trading statistics
     */
    public static Statistics analyze(
            MT5Account account,
            int daysBack) throws ApiExceptionMT5 {

        Instant now = Instant.now();
        Instant start = now.minus(daysBack, ChronoUnit.DAYS);

        Timestamp from = Timestamp.newBuilder()
            .setSeconds(start.getEpochSecond())
            .build();
        Timestamp to = Timestamp.newBuilder()
            .setSeconds(now.getEpochSecond())
            .build();

        var reply = account.orderHistory(
            from, to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
            0, 0
        );

        java.util.List<Double> profits = new java.util.ArrayList<>();

        for (var item : reply.getData().getHistoryDataList()) {
            var deal = item.getHistoryDeal();
            if (deal.getTicket() > 0) {
                double netProfit = deal.getProfit() - deal.getCommission() - deal.getFee();
                profits.add(netProfit);
            }
        }

        if (profits.isEmpty()) {
            return new Statistics(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        int winningTrades = (int) profits.stream().filter(p -> p > 0).count();
        int losingTrades = (int) profits.stream().filter(p -> p < 0).count();
        int totalTrades = profits.size();

        double totalProfit = profits.stream().filter(p -> p > 0).mapToDouble(Double::doubleValue).sum();
        double totalLoss = Math.abs(profits.stream().filter(p -> p < 0).mapToDouble(Double::doubleValue).sum());
        double netProfit = totalProfit - totalLoss;

        double largestWin = profits.stream().filter(p -> p > 0).mapToDouble(Double::doubleValue).max().orElse(0);
        double largestLoss = Math.abs(profits.stream().filter(p -> p < 0).mapToDouble(Double::doubleValue).min().orElse(0));

        double averageWin = winningTrades > 0 ? totalProfit / winningTrades : 0;
        double averageLoss = losingTrades > 0 ? totalLoss / losingTrades : 0;

        double winRate = (double) winningTrades / totalTrades * 100;
        double profitFactor = totalLoss > 0 ? totalProfit / totalLoss : 0;

        return new Statistics(
            totalTrades,
            winningTrades,
            losingTrades,
            winRate,
            totalProfit,
            totalLoss,
            netProfit,
            largestWin,
            largestLoss,
            averageWin,
            averageLoss,
            profitFactor
        );
    }

    /**
     * Print statistics report
     */
    public static void printReport(Statistics stats) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       TRADING STATISTICS REPORT        ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Total Trades: %-24d ║%n", stats.totalTrades());
        System.out.printf("║ Winning Trades: %-22d ║%n", stats.winningTrades());
        System.out.printf("║ Losing Trades: %-23d ║%n", stats.losingTrades());
        System.out.printf("║ Win Rate: %-27.2f%% ║%n", stats.winRate());
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Total Profit: $%-23.2f ║%n", stats.totalProfit());
        System.out.printf("║ Total Loss: $%-25.2f ║%n", stats.totalLoss());
        System.out.printf("║ Net Profit: $%-25.2f ║%n", stats.netProfit());
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Largest Win: $%-24.2f ║%n", stats.largestWin());
        System.out.printf("║ Largest Loss: $%-23.2f ║%n", stats.largestLoss());
        System.out.printf("║ Average Win: $%-24.2f ║%n", stats.averageWin());
        System.out.printf("║ Average Loss: $%-23.2f ║%n", stats.averageLoss());
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Profit Factor: %-23.2f ║%n", stats.profitFactor());
        System.out.println("╚════════════════════════════════════════╝");
    }
}

// Usage
var stats = TradingStats.analyze(account, 30);
TradingStats.printReport(stats);
```

### 6) Export to CSV

```java
import java.io.*;
import java.time.Instant;

public class CsvExporter {
    /**
     * Export trading history to CSV file
     */
    public static void exportToCsv(
            MT5Account account,
            Timestamp from,
            Timestamp to,
            String filename) throws ApiExceptionMT5, IOException {

        var reply = account.orderHistory(
            from, to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
            0, 0
        );

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // CSV header
            writer.println("OrderTicket,Symbol,OrderType,OrderState," +
                "SetupTime,DoneTime,VolumeInitial,VolumeCurrent," +
                "PriceOpen,StopLoss,TakeProfit," +
                "DealTicket,DealType,DealProfit,Commission,Fee,Swap," +
                "DealPrice,DealVolume,DealTime,EntryType,DealReason");

            // Data rows
            for (var item : reply.getData().getHistoryDataList()) {
                var order = item.getHistoryOrder();
                var deal = item.getHistoryDeal();

                Instant setupTime = Instant.ofEpochSecond(
                    order.getSetupTime().getSeconds()
                );
                Instant doneTime = Instant.ofEpochSecond(
                    order.getDoneTime().getSeconds()
                );
                Instant dealTime = Instant.ofEpochSecond(
                    deal.getTime().getSeconds()
                );

                writer.printf("%d,%s,%s,%s,%s,%s,%.2f,%.2f,%.5f,%.5f,%.5f," +
                    "%d,%s,%.2f,%.2f,%.2f,%.2f,%.5f,%.2f,%s,%s,%s%n",
                    order.getTicket(),
                    order.getSymbol(),
                    order.getType(),
                    order.getState(),
                    setupTime,
                    doneTime,
                    order.getVolumeInitial(),
                    order.getVolumeCurrent(),
                    order.getPriceOpen(),
                    order.getStopLoss(),
                    order.getTakeProfit(),
                    deal.getTicket(),
                    deal.getType(),
                    deal.getProfit(),
                    deal.getCommission(),
                    deal.getFee(),
                    deal.getSwap(),
                    deal.getPrice(),
                    deal.getVolume(),
                    dealTime,
                    deal.getEntryType(),
                    deal.getReason()
                );
            }

            System.out.printf("✅ Exported %d records to %s%n",
                reply.getData().getArrayTotal(),
                filename
            );
        }
    }
}

// Usage
Instant now = Instant.now();
Instant monthAgo = now.minus(30, ChronoUnit.DAYS);

Timestamp from = Timestamp.newBuilder()
    .setSeconds(monthAgo.getEpochSecond())
    .build();
Timestamp to = Timestamp.newBuilder()
    .setSeconds(now.getEpochSecond())
    .build();

CsvExporter.exportToCsv(account, from, to, "trading_history.csv");
```

### 7) Find orders by magic number

```java
public class MagicNumberFilter {
    /**
     * Find all orders/deals by magic number (EA ID)
     */
    public static java.util.List<Mt5TermApiAccountHelper.HistoryData> findByMagicNumber(
            MT5Account account,
            long magicNumber,
            int daysBack) throws ApiExceptionMT5 {

        Instant now = Instant.now();
        Instant start = now.minus(daysBack, ChronoUnit.DAYS);

        Timestamp from = Timestamp.newBuilder()
            .setSeconds(start.getEpochSecond())
            .build();
        Timestamp to = Timestamp.newBuilder()
            .setSeconds(now.getEpochSecond())
            .build();

        var reply = account.orderHistory(
            from, to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
            0, 0
        );

        return reply.getData().getHistoryDataList().stream()
            .filter(item -> item.getHistoryOrder().getMagicNumber() == magicNumber)
            .toList();
    }

    /**
     * Print EA performance report
     */
    public static void printEaReport(
            java.util.List<Mt5TermApiAccountHelper.HistoryData> history,
            long magicNumber) {

        double totalProfit = history.stream()
            .mapToDouble(item -> {
                var deal = item.getHistoryDeal();
                return deal.getProfit() - deal.getCommission() - deal.getFee();
            })
            .sum();

        long tradeCount = history.stream()
            .filter(item -> item.getHistoryDeal().getTicket() > 0)
            .count();

        System.out.printf("\nEA Report (Magic: %d)%n", magicNumber);
        System.out.println("═".repeat(40));
        System.out.printf("Trades: %d%n", tradeCount);
        System.out.printf("Net Profit: %.2f%n", totalProfit);
    }
}

// Usage
long eaMagic = 123456;
var eaHistory = MagicNumberFilter.findByMagicNumber(account, eaMagic, 30);
MagicNumberFilter.printEaReport(eaHistory, eaMagic);
```

### 8) Find Stop Loss and Take Profit executions

```java
public class SlTpAnalyzer {
    /**
     * Analyze SL and TP execution statistics
     */
    public static void analyzeSlTpExecutions(
            MT5Account account,
            int daysBack) throws ApiExceptionMT5 {

        Instant now = Instant.now();
        Instant start = now.minus(daysBack, ChronoUnit.DAYS);

        Timestamp from = Timestamp.newBuilder()
            .setSeconds(start.getEpochSecond())
            .build();
        Timestamp to = Timestamp.newBuilder()
            .setSeconds(now.getEpochSecond())
            .build();

        var reply = account.orderHistory(
            from, to,
            Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC,
            0, 0
        );

        int slCount = 0;
        int tpCount = 0;
        double slTotalProfit = 0.0;
        double tpTotalProfit = 0.0;

        for (var item : reply.getData().getHistoryDataList()) {
            var deal = item.getHistoryDeal();
            var reason = deal.getReason();

            if (reason == Mt5TermApiAccountHelper.BMT5_ENUM_DEAL_REASON.BMT5_DEAL_REASON_SL) {
                slCount++;
                slTotalProfit += deal.getProfit();
            } else if (reason == Mt5TermApiAccountHelper.BMT5_ENUM_DEAL_REASON.BMT5_DEAL_REASON_TP) {
                tpCount++;
                tpTotalProfit += deal.getProfit();
            }
        }

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      SL/TP EXECUTION ANALYSIS      ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Stop Loss hits: %-18d ║%n", slCount);
        System.out.printf("║ Take Profit hits: %-16d ║%n", tpCount);
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ SL Total P/L: $%-19.2f ║%n", slTotalProfit);
        System.out.printf("║ TP Total P/L: $%-19.2f ║%n", tpTotalProfit);
        System.out.printf("║ SL Average: $%-21.2f ║%n",
            slCount > 0 ? slTotalProfit / slCount : 0.0);
        System.out.printf("║ TP Average: $%-21.2f ║%n",
            tpCount > 0 ? tpTotalProfit / tpCount : 0.0);
        System.out.println("╚════════════════════════════════════╝");
    }
}

// Usage
SlTpAnalyzer.analyzeSlTpExecutions(account, 30);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;
import com.google.protobuf.Timestamp;

// Create request
Timestamp from = Timestamp.newBuilder()
    .setSeconds(Instant.now().minus(7, ChronoUnit.DAYS).getEpochSecond())
    .build();
Timestamp to = Timestamp.newBuilder()
    .setSeconds(Instant.now().getEpochSecond())
    .build();

Mt5TermApiAccountHelper.OrderHistoryRequest request =
    Mt5TermApiAccountHelper.OrderHistoryRequest.newBuilder()
        .setInputFrom(from)
        .setInputTo(to)
        .setInputSortMode(Mt5TermApiAccountHelper.BMT5_ENUM_ORDER_HISTORY_SORT_TYPE.BMT5_SORT_BY_CLOSE_TIME_DESC)
        .setPageNumber(0)
        .setItemsPerPage(0)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountHelper.OrderHistoryReply reply = accountHelperClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .orderHistory(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
List<Mt5TermApiAccountHelper.HistoryData> history =
    reply.getData().getHistoryDataList();
```

---

## 📊 Understanding Orders vs Deals

**Orders** represent trading intentions and modifications:
- Order placement (BUY, SELL, BUY_LIMIT, etc.)
- Order modifications (SL/TP changes, price changes)
- Order cancellations and expirations

**Deals** represent actual market executions:
- Position openings (entry into market)
- Position closings (exit from market)
- Position reversals (close + open opposite)
- Actual profit/loss from executions

**Key difference:** One order can result in multiple deals (partial fills), and each deal shows actual execution details including profit/loss.
