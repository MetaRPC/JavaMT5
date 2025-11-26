# 📊 Get Closed Positions History

> **Request:** retrieve historical closed positions for a specified time period with pagination support. Returns complete position lifecycle data including entry/exit prices, profit/loss, and trading costs.

**API Information:**

* **SDK wrapper:** `MT5Account.positionsHistory(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountHelper`
* **Proto definition:** `PositionsHistory` (defined in `mt5-term-api-account-helper.proto`)

### RPC

* **Service:** `mt5_term_api.AccountHelper`
* **Method:** `PositionsHistory(PositionsHistoryRequest) → PositionsHistoryReply`
* **Low‑level client (generated):** `AccountHelperGrpc.AccountHelperBlockingStub.positionsHistory(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Retrieves closed positions history for the specified time period.
     * Supports pagination and different sorting options.
     * Returns complete position lifecycle including entry/exit prices and profit/loss.
     *
     * @param sortType Sort order for results
     * @param from Optional start time (position open time), null for no limit
     * @param to Optional end time (position open time), null for no limit
     * @param pageNumber Optional page number (0-based), null for default (0)
     * @param itemsPerPage Optional items per page, null for default (all items)
     * @return Reply containing paginated closed positions data
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.PositionsHistoryReply positionsHistory(
        Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE sortType,
        com.google.protobuf.Timestamp from,
        com.google.protobuf.Timestamp to,
        Integer pageNumber,
        Integer itemsPerPage
    ) throws ApiExceptionMT5;
}
```

**Request message:** `PositionsHistoryRequest { sort_type, position_open_time_from?, position_open_time_to?, page_number?, items_per_page? }`
**Reply message:** `PositionsHistoryReply { data: PositionsHistoryData }` or `{ error: Error }`

---

## 🔽 Input Parameters

| Parameter      | Type                                        | Required | Description                                          |
| -------------- | ------------------------------------------- | -------- | ---------------------------------------------------- |
| `sortType`     | `AH_ENUM_POSITIONS_HISTORY_SORT_TYPE`       | ✅       | Sorting mode for results                             |
| `from`         | `Timestamp`                                 | ❌       | Start time (position open time); null = no limit     |
| `to`           | `Timestamp`                                 | ❌       | End time (position open time); null = no limit       |
| `pageNumber`   | `Integer`                                   | ❌       | Page number (0-based); null = 0                      |
| `itemsPerPage` | `Integer`                                   | ❌       | Items per page; null = all items                     |

### Enum: `AH_ENUM_POSITIONS_HISTORY_SORT_TYPE`

| Value                          | Number | Description                                |
| ------------------------------ | ------ | ------------------------------------------ |
| `AH_POSITION_OPEN_TIME_ASC`    | 0      | Sort by position open time (ascending)     |
| `AH_POSITION_OPEN_TIME_DESC`   | 1      | Sort by position open time (descending)    |
| `AH_POSITION_TICKET_ASC`       | 2      | Sort by position ticket ID (ascending)     |
| `AH_POSITION_TICKET_DESC`      | 3      | Sort by position ticket ID (descending)    |

---

## ⬆️ Output — `PositionsHistoryData`

| Field                | Type                           | Description                                          |
| -------------------- | ------------------------------ | ---------------------------------------------------- |
| `history_positions`  | `List<PositionHistoryInfo>`    | List of closed position records                      |

Access using: `reply.getData().getHistoryPositionsList()`

### Structure: `PositionHistoryInfo`

| Field             | Type                                      | Description                                          |
| ----------------- | ----------------------------------------- | ---------------------------------------------------- |
| `index`           | `int`                                     | Index in result list                                 |
| `position_ticket` | `long`                                    | Position ticket number (unique ID)                   |
| `order_type`      | `AH_ENUM_POSITIONS_HISTORY_ORDER_TYPE`    | Position type (BUY, SELL, etc.)                      |
| `open_time`       | `Timestamp`                               | Position opening time                                |
| `close_time`      | `Timestamp`                               | Position closing time                                |
| `volume`          | `double`                                  | Position volume (lots)                               |
| `open_price`      | `double`                                  | Opening price                                        |
| `close_price`     | `double`                                  | Closing price                                        |
| `stop_loss`       | `double`                                  | Stop loss level                                      |
| `take_profit`     | `double`                                  | Take profit level                                    |
| `market_value`    | `double`                                  | Position market value                                |
| `commission`      | `double`                                  | Total commission charged                             |
| `fee`             | `double`                                  | Additional fees                                      |
| `profit`          | `double`                                  | Gross profit/loss (in account currency)              |
| `swap`            | `double`                                  | Swap charges (rollover)                              |
| `comment`         | `string`                                  | Position comment                                     |
| `symbol`          | `string`                                  | Symbol name (e.g., "EURUSD")                         |
| `magic`           | `long`                                    | Expert Advisor ID (magic number)                     |

### Enum: `AH_ENUM_POSITIONS_HISTORY_ORDER_TYPE`

| Value                             | Number | Description                                          |
| --------------------------------- | ------ | ---------------------------------------------------- |
| `AH_ORDER_TYPE_BUY`               | 0      | Buy position (long)                                  |
| `AH_ORDER_TYPE_SELL`              | 1      | Sell position (short)                                |
| `AH_ORDER_TYPE_BUY_LIMIT`         | 2      | Buy Limit order                                      |
| `AH_ORDER_TYPE_SELL_LIMIT`        | 3      | Sell Limit order                                     |
| `AH_ORDER_TYPE_BUY_STOP`          | 4      | Buy Stop order                                       |
| `AH_ORDER_TYPE_SELL_STOP`         | 5      | Sell Stop order                                      |
| `AH_ORDER_TYPE_BUY_STOP_LIMIT`    | 6      | Buy Stop Limit order                                 |
| `AH_ORDER_TYPE_SELL_STOP_LIMIT`   | 7      | Sell Stop Limit order                                |
| `AH_ORDER_TYPE_CLOSE_BY`          | 8      | Position closed by opposite position                 |

---

## 💬 Just the essentials

* **What it is.** RPC to retrieve closed positions history with complete lifecycle data.
* **Why you need it.** Analyze trading performance, calculate metrics, generate reports.
* **Complete data.** Includes entry/exit prices, profit/loss, costs, and timestamps.
* **Flexible filtering.** Optional time range filtering by position open time.
* **Pagination.** Handles large datasets efficiently.
* **Net profit.** Calculate using: `profit - commission - fee + swap`.

---

## 🎯 Purpose

Use this method when you need to:

* Analyze trading performance and strategy effectiveness.
* Calculate trading statistics (win rate, profit factor, etc.).
* Generate profit/loss reports for specific periods.
* Track commission and swap costs.
* Audit closed positions for compliance.
* Export trading history for external analysis.
* Monitor Expert Advisor performance by magic number.

---

## 🧩 Notes & Tips

* **Time filter.** `from` and `to` filter by **position open time**, not close time.
* **Optional params.** Pass `null` for `from`/`to` to get all positions.
* **Net profit.** Calculate as: `profit - commission - fee + swap`.
* **Pagination.** Use `pageNumber=null, itemsPerPage=null` for all items.
* **Market value.** Calculated position value at closure.
* **Swap charges.** Overnight rollover costs (can be positive or negative).
* **Magic number.** Use to filter positions by Expert Advisor.
* **Auto-reconnect.** Uses `executeWithReconnect()` for reliability.

---

## 🔗 Usage Examples

### 1) Basic closed positions retrieval (last 30 days)

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

            // Get last 30 days of closed positions
            Instant now = Instant.now();
            Instant monthAgo = now.minus(30, ChronoUnit.DAYS);

            Timestamp from = Timestamp.newBuilder()
                .setSeconds(monthAgo.getEpochSecond())
                .build();
            Timestamp to = Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .build();

            Mt5TermApiAccountHelper.PositionsHistoryReply reply =
                account.positionsHistory(
                    Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC,
                    from,
                    to,
                    null,  // All pages
                    null   // All items
                );

            var positions = reply.getData().getHistoryPositionsList();
            System.out.printf("Closed positions: %d%n%n", positions.size());

            for (var position : positions) {
                double netProfit = position.getProfit() -
                                   position.getCommission() -
                                   position.getFee() +
                                   position.getSwap();

                System.out.printf("Position #%d%n", position.getPositionTicket());
                System.out.printf("  Symbol: %s%n", position.getSymbol());
                System.out.printf("  Type: %s%n", position.getOrderType());
                System.out.printf("  Volume: %.2f lots%n", position.getVolume());
                System.out.printf("  Open: %.5f @ %s%n",
                    position.getOpenPrice(),
                    Instant.ofEpochSecond(position.getOpenTime().getSeconds())
                );
                System.out.printf("  Close: %.5f @ %s%n",
                    position.getClosePrice(),
                    Instant.ofEpochSecond(position.getCloseTime().getSeconds())
                );
                System.out.printf("  Profit: %.2f%n", position.getProfit());
                System.out.printf("  Net P/L: %.2f%n%n", netProfit);
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Calculate trading statistics

```java
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.Duration;

public class TradingStatistics {
    public record Stats(
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
        double profitFactor,
        double averageHoldingTime,
        double totalCommission,
        double totalSwap
    ) {}

    /**
     * Calculate comprehensive trading statistics
     */
    public static Stats calculate(
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

        var reply = account.positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC,
            from, to, null, null
        );

        var positions = reply.getData().getHistoryPositionsList();
        if (positions.isEmpty()) {
            return new Stats(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        java.util.List<Double> netProfits = new java.util.ArrayList<>();
        double totalCommission = 0;
        double totalSwap = 0;
        long totalHoldingSeconds = 0;

        for (var pos : positions) {
            double netProfit = pos.getProfit() - pos.getCommission() -
                               pos.getFee() + pos.getSwap();
            netProfits.add(netProfit);

            totalCommission += pos.getCommission();
            totalSwap += pos.getSwap();

            // Calculate holding time
            long holdingSec = pos.getCloseTime().getSeconds() -
                              pos.getOpenTime().getSeconds();
            totalHoldingSeconds += holdingSec;
        }

        int totalTrades = positions.size();
        int winningTrades = (int) netProfits.stream().filter(p -> p > 0).count();
        int losingTrades = (int) netProfits.stream().filter(p -> p < 0).count();

        double totalProfit = netProfits.stream()
            .filter(p -> p > 0)
            .mapToDouble(Double::doubleValue)
            .sum();
        double totalLoss = Math.abs(netProfits.stream()
            .filter(p -> p < 0)
            .mapToDouble(Double::doubleValue)
            .sum());
        double netProfit = totalProfit - totalLoss;

        double largestWin = netProfits.stream()
            .filter(p -> p > 0)
            .mapToDouble(Double::doubleValue)
            .max()
            .orElse(0);
        double largestLoss = Math.abs(netProfits.stream()
            .filter(p -> p < 0)
            .mapToDouble(Double::doubleValue)
            .min()
            .orElse(0));

        double averageWin = winningTrades > 0 ? totalProfit / winningTrades : 0;
        double averageLoss = losingTrades > 0 ? totalLoss / losingTrades : 0;

        double winRate = (double) winningTrades / totalTrades * 100;
        double profitFactor = totalLoss > 0 ? totalProfit / totalLoss : 0;

        double averageHoldingTime = (double) totalHoldingSeconds / totalTrades;

        return new Stats(
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
            profitFactor,
            averageHoldingTime,
            totalCommission,
            totalSwap
        );
    }

    /**
     * Print statistics report
     */
    public static void printReport(Stats stats) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    CLOSED POSITIONS STATISTICS         ║");
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
        System.out.printf("║ Avg Hold Time: %-19.1f hrs ║%n",
            stats.averageHoldingTime() / 3600);
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Total Commission: $%-19.2f ║%n", stats.totalCommission());
        System.out.printf("║ Total Swap: $%-25.2f ║%n", stats.totalSwap());
        System.out.println("╚════════════════════════════════════════╝");
    }
}

// Usage
var stats = TradingStatistics.calculate(account, 30);
TradingStatistics.printReport(stats);
```

### 3) Filter positions by symbol

```java
public class SymbolAnalyzer {
    /**
     * Get closed positions for specific symbol
     */
    public static java.util.List<Mt5TermApiAccountHelper.PositionHistoryInfo> getBySymbol(
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

        var reply = account.positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC,
            from, to, null, null
        );

        return reply.getData().getHistoryPositionsList().stream()
            .filter(pos -> pos.getSymbol().equals(symbol))
            .toList();
    }

    /**
     * Print symbol performance summary
     */
    public static void printSymbolPerformance(
            java.util.List<Mt5TermApiAccountHelper.PositionHistoryInfo> positions,
            String symbol) {

        if (positions.isEmpty()) {
            System.out.printf("No positions found for %s%n", symbol);
            return;
        }

        double totalNetProfit = positions.stream()
            .mapToDouble(p -> p.getProfit() - p.getCommission() -
                              p.getFee() + p.getSwap())
            .sum();

        long wins = positions.stream()
            .filter(p -> (p.getProfit() - p.getCommission() -
                          p.getFee() + p.getSwap()) > 0)
            .count();

        double winRate = (double) wins / positions.size() * 100;

        System.out.printf("\n%s Performance Summary%n", symbol);
        System.out.println("═".repeat(40));
        System.out.printf("Total Positions: %d%n", positions.size());
        System.out.printf("Winning Positions: %d%n", wins);
        System.out.printf("Win Rate: %.2f%%%n", winRate);
        System.out.printf("Net Profit: $%.2f%n", totalNetProfit);
        System.out.printf("Average Per Trade: $%.2f%n",
            totalNetProfit / positions.size());
    }
}

// Usage
var eurusdPositions = SymbolAnalyzer.getBySymbol(account, "EURUSD", 30);
SymbolAnalyzer.printSymbolPerformance(eurusdPositions, "EURUSD");
```

### 4) Analyze positions by magic number (EA tracking)

```java
public class ExpertAdvisorAnalyzer {
    /**
     * Get positions by Expert Advisor (magic number)
     */
    public static java.util.List<Mt5TermApiAccountHelper.PositionHistoryInfo> getByMagic(
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

        var reply = account.positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC,
            from, to, null, null
        );

        return reply.getData().getHistoryPositionsList().stream()
            .filter(pos -> pos.getMagic() == magicNumber)
            .toList();
    }

    /**
     * Print EA performance report
     */
    public static void printEaReport(
            java.util.List<Mt5TermApiAccountHelper.PositionHistoryInfo> positions,
            long magicNumber) {

        if (positions.isEmpty()) {
            System.out.printf("No positions found for EA (magic: %d)%n", magicNumber);
            return;
        }

        double totalProfit = 0;
        double totalCommission = 0;
        double totalSwap = 0;
        int wins = 0;
        int losses = 0;

        for (var pos : positions) {
            double netProfit = pos.getProfit() - pos.getCommission() -
                               pos.getFee() + pos.getSwap();
            totalProfit += netProfit;
            totalCommission += pos.getCommission();
            totalSwap += pos.getSwap();

            if (netProfit > 0) wins++;
            else if (netProfit < 0) losses++;
        }

        System.out.printf("\nExpert Advisor Report (Magic: %d)%n", magicNumber);
        System.out.println("═".repeat(50));
        System.out.printf("Total Trades: %d%n", positions.size());
        System.out.printf("Wins: %d | Losses: %d%n", wins, losses);
        System.out.printf("Win Rate: %.2f%%%n",
            (double) wins / positions.size() * 100);
        System.out.printf("Net Profit: $%.2f%n", totalProfit);
        System.out.printf("Total Commission: $%.2f%n", totalCommission);
        System.out.printf("Total Swap: $%.2f%n", totalSwap);
    }
}

// Usage
long myEaMagic = 123456;
var eaPositions = ExpertAdvisorAnalyzer.getByMagic(account, myEaMagic, 30);
ExpertAdvisorAnalyzer.printEaReport(eaPositions, myEaMagic);
```

### 5) Export to CSV

```java
import java.io.*;
import java.time.Instant;

public class CsvExporter {
    /**
     * Export closed positions to CSV file
     */
    public static void exportToCsv(
            MT5Account account,
            Timestamp from,
            Timestamp to,
            String filename) throws ApiExceptionMT5, IOException {

        var reply = account.positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC,
            from, to, null, null
        );

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // CSV header
            writer.println("PositionTicket,Symbol,Type,Volume," +
                "OpenTime,CloseTime,OpenPrice,ClosePrice," +
                "StopLoss,TakeProfit,Profit,Commission,Fee,Swap," +
                "NetProfit,MarketValue,Magic,Comment");

            // Data rows
            for (var pos : reply.getData().getHistoryPositionsList()) {
                Instant openTime = Instant.ofEpochSecond(
                    pos.getOpenTime().getSeconds()
                );
                Instant closeTime = Instant.ofEpochSecond(
                    pos.getCloseTime().getSeconds()
                );

                double netProfit = pos.getProfit() - pos.getCommission() -
                                   pos.getFee() + pos.getSwap();

                writer.printf("%d,%s,%s,%.2f,%s,%s,%.5f,%.5f,%.5f,%.5f," +
                    "%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%d,%s%n",
                    pos.getPositionTicket(),
                    pos.getSymbol(),
                    pos.getOrderType(),
                    pos.getVolume(),
                    openTime,
                    closeTime,
                    pos.getOpenPrice(),
                    pos.getClosePrice(),
                    pos.getStopLoss(),
                    pos.getTakeProfit(),
                    pos.getProfit(),
                    pos.getCommission(),
                    pos.getFee(),
                    pos.getSwap(),
                    netProfit,
                    pos.getMarketValue(),
                    pos.getMagic(),
                    pos.getComment().replace(",", ";")  // Escape commas
                );
            }

            System.out.printf("✅ Exported %d positions to %s%n",
                reply.getData().getHistoryPositionsList().size(),
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

CsvExporter.exportToCsv(account, from, to, "closed_positions.csv");
```

### 6) Win/Loss streak analyzer

```java
public class StreakAnalyzer {
    public record StreakInfo(
        int currentStreak,
        int longestWinStreak,
        int longestLossStreak,
        boolean isWinStreak
    ) {}

    /**
     * Analyze winning and losing streaks
     */
    public static StreakInfo analyzeStreaks(
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

        var reply = account.positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_ASC,  // Oldest first
            from, to, null, null
        );

        var positions = reply.getData().getHistoryPositionsList();
        if (positions.isEmpty()) {
            return new StreakInfo(0, 0, 0, true);
        }

        int currentStreak = 0;
        int longestWinStreak = 0;
        int longestLossStreak = 0;
        boolean lastWasWin = false;
        boolean isWinStreak = false;

        for (var pos : positions) {
            double netProfit = pos.getProfit() - pos.getCommission() -
                               pos.getFee() + pos.getSwap();
            boolean isWin = netProfit > 0;

            if (currentStreak == 0) {
                // First trade
                currentStreak = 1;
                lastWasWin = isWin;
                isWinStreak = isWin;
            } else if (isWin == lastWasWin) {
                // Streak continues
                currentStreak++;
            } else {
                // Streak breaks - record and reset
                if (lastWasWin) {
                    longestWinStreak = Math.max(longestWinStreak, currentStreak);
                } else {
                    longestLossStreak = Math.max(longestLossStreak, currentStreak);
                }
                currentStreak = 1;
                lastWasWin = isWin;
                isWinStreak = isWin;
            }
        }

        // Record final streak
        if (lastWasWin) {
            longestWinStreak = Math.max(longestWinStreak, currentStreak);
        } else {
            longestLossStreak = Math.max(longestLossStreak, currentStreak);
        }

        return new StreakInfo(
            currentStreak,
            longestWinStreak,
            longestLossStreak,
            isWinStreak
        );
    }

    /**
     * Print streak analysis
     */
    public static void printStreakReport(StreakInfo info) {
        String currentStreakType = info.isWinStreak() ? "WIN" : "LOSS";

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║       STREAK ANALYSIS              ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Current Streak: %-2d %-13s ║%n",
            info.currentStreak(), currentStreakType);
        System.out.printf("║ Longest Win Streak: %-14d ║%n",
            info.longestWinStreak());
        System.out.printf("║ Longest Loss Streak: %-13d ║%n",
            info.longestLossStreak());
        System.out.println("╚════════════════════════════════════╝");
    }
}

// Usage
var streakInfo = StreakAnalyzer.analyzeStreaks(account, 30);
StreakAnalyzer.printStreakReport(streakInfo);
```

### 7) Calculate Risk-Reward ratio

```java
public class RiskRewardAnalyzer {
    /**
     * Calculate average risk-reward ratio from closed positions
     */
    public static void analyzeRiskReward(
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

        var reply = account.positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC,
            from, to, null, null
        );

        java.util.List<Double> riskRewardRatios = new java.util.ArrayList<>();

        for (var pos : reply.getData().getHistoryPositionsList()) {
            if (pos.getStopLoss() == 0 || pos.getTakeProfit() == 0) {
                continue;  // Skip positions without SL/TP
            }

            boolean isBuy = pos.getOrderType() ==
                Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_ORDER_TYPE.AH_ORDER_TYPE_BUY;

            double risk, reward;
            if (isBuy) {
                risk = Math.abs(pos.getOpenPrice() - pos.getStopLoss());
                reward = Math.abs(pos.getTakeProfit() - pos.getOpenPrice());
            } else {
                risk = Math.abs(pos.getStopLoss() - pos.getOpenPrice());
                reward = Math.abs(pos.getOpenPrice() - pos.getTakeProfit());
            }

            if (risk > 0) {
                riskRewardRatios.add(reward / risk);
            }
        }

        if (riskRewardRatios.isEmpty()) {
            System.out.println("No positions with SL/TP found");
            return;
        }

        double avgRiskReward = riskRewardRatios.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0);

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     RISK-REWARD ANALYSIS           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Positions with SL/TP: %-12d ║%n",
            riskRewardRatios.size());
        System.out.printf("║ Avg Risk-Reward Ratio: 1:%-8.2f ║%n",
            avgRiskReward);
        System.out.println("╚════════════════════════════════════╝");
    }
}

// Usage
RiskRewardAnalyzer.analyzeRiskReward(account, 30);
```

### 8) Monthly performance breakdown

```java
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MonthlyPerformance {
    public record MonthData(
        YearMonth month,
        int trades,
        double netProfit,
        double winRate
    ) {}

    /**
     * Break down performance by month
     */
    public static java.util.List<MonthData> getMonthlyBreakdown(
            MT5Account account,
            int monthsBack) throws ApiExceptionMT5 {

        Instant now = Instant.now();
        Instant start = now.minus(monthsBack * 30L, ChronoUnit.DAYS);

        Timestamp from = Timestamp.newBuilder()
            .setSeconds(start.getEpochSecond())
            .build();
        Timestamp to = Timestamp.newBuilder()
            .setSeconds(now.getEpochSecond())
            .build();

        var reply = account.positionsHistory(
            Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_ASC,
            from, to, null, null
        );

        // Group by month
        Map<YearMonth, java.util.List<Mt5TermApiAccountHelper.PositionHistoryInfo>> byMonth =
            new TreeMap<>();

        for (var pos : reply.getData().getHistoryPositionsList()) {
            Instant openTime = Instant.ofEpochSecond(
                pos.getOpenTime().getSeconds()
            );
            YearMonth month = YearMonth.from(
                LocalDate.ofInstant(openTime, ZoneId.systemDefault())
            );

            byMonth.computeIfAbsent(month, k -> new ArrayList<>()).add(pos);
        }

        // Calculate monthly stats
        java.util.List<MonthData> monthlyData = new ArrayList<>();

        for (var entry : byMonth.entrySet()) {
            var positions = entry.getValue();
            int trades = positions.size();

            double netProfit = positions.stream()
                .mapToDouble(p -> p.getProfit() - p.getCommission() -
                                  p.getFee() + p.getSwap())
                .sum();

            long wins = positions.stream()
                .filter(p -> (p.getProfit() - p.getCommission() -
                              p.getFee() + p.getSwap()) > 0)
                .count();

            double winRate = (double) wins / trades * 100;

            monthlyData.add(new MonthData(
                entry.getKey(),
                trades,
                netProfit,
                winRate
            ));
        }

        return monthlyData;
    }

    /**
     * Print monthly performance report
     */
    public static void printMonthlyReport(java.util.List<MonthData> data) {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         MONTHLY PERFORMANCE BREAKDOWN          ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║  Month    | Trades | Net Profit | Win Rate   ║");
        System.out.println("╠════════════════════════════════════════════════╣");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        for (var month : data) {
            System.out.printf("║ %-9s | %-6d | $%-9.2f | %-8.1f%% ║%n",
                month.month().format(formatter),
                month.trades(),
                month.netProfit(),
                month.winRate()
            );
        }

        double totalProfit = data.stream()
            .mapToDouble(MonthData::netProfit)
            .sum();

        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.printf("║ TOTAL                | $%-9.2f           ║%n",
            totalProfit);
        System.out.println("╚════════════════════════════════════════════════╝");
    }
}

// Usage
var monthlyData = MonthlyPerformance.getMonthlyBreakdown(account, 12);
MonthlyPerformance.printMonthlyReport(monthlyData);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;
import com.google.protobuf.Timestamp;

// Create request
Timestamp from = Timestamp.newBuilder()
    .setSeconds(Instant.now().minus(30, ChronoUnit.DAYS).getEpochSecond())
    .build();
Timestamp to = Timestamp.newBuilder()
    .setSeconds(Instant.now().getEpochSecond())
    .build();

Mt5TermApiAccountHelper.PositionsHistoryRequest request =
    Mt5TermApiAccountHelper.PositionsHistoryRequest.newBuilder()
        .setSortType(Mt5TermApiAccountHelper.AH_ENUM_POSITIONS_HISTORY_SORT_TYPE.AH_POSITION_OPEN_TIME_DESC)
        .setPositionOpenTimeFrom(from)
        .setPositionOpenTimeTo(to)
        .setPageNumber(0)
        .setItemsPerPage(0)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountHelper.PositionsHistoryReply reply = accountHelperClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .positionsHistory(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
List<Mt5TermApiAccountHelper.PositionHistoryInfo> positions =
    reply.getData().getHistoryPositionsList();
```

---

## 📊 Key Metrics Calculations

**Net Profit (per position):**
```java
double netProfit = position.getProfit() - position.getCommission()
                   - position.getFee() + position.getSwap();
```

**Win Rate:**
```java
double winRate = (double) winningTrades / totalTrades * 100;
```

**Profit Factor:**
```java
double profitFactor = totalProfit / totalLoss;  // > 1 is profitable
```

**Average Holding Time:**
```java
long holdingSeconds = position.getCloseTime().getSeconds()
                      - position.getOpenTime().getSeconds();
double holdingHours = holdingSeconds / 3600.0;
```

**Price Movement (pips):**
```java
double priceMove = position.getOrderType() == AH_ORDER_TYPE_BUY
    ? (position.getClosePrice() - position.getOpenPrice())
    : (position.getOpenPrice() - position.getClosePrice());
```
