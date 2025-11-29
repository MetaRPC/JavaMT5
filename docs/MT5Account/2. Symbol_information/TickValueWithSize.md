# 💰 Get Tick Value and Size for Multiple Symbols

> **Request:** retrieve tick value, tick size, and contract size information for multiple symbols in a single call. Essential data for profit/loss calculations and position sizing.

**API Information:**

* **SDK wrapper:** `MT5Account.tickValueWithSize(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountHelper`
* **Proto definition:** `TickValueWithSize` (defined in `mt5-term-api-account-helper.proto`)

### RPC

* **Service:** `mt5_term_api.AccountHelper`
* **Method:** `TickValueWithSize(TickValueWithSizeRequest) → TickValueWithSizeReply`
* **Low‑level client (generated):** `AccountHelperGrpc.AccountHelperBlockingStub.tickValueWithSize(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Get tick value and size information for multiple symbols.
     * Returns tick values, contract size, and tick size for trading calculations.
     *
     * @param symbolNames Array of symbol names
     * @return Tick value and size data for all requested symbols
     * @throws ApiExceptionMT5 if call fails
     */
    public Mt5TermApiAccountHelper.TickValueWithSizeReply tickValueWithSize(String[] symbolNames) throws ApiExceptionMT5;
}
```

**Request message:** `TickValueWithSizeRequest { symbol_names: repeated string }`

**Reply message:** `TickValueWithSizeReply { data: TickValueWithSizeData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type       | Required | Description                              |
| -------------- | ---------- | -------- | ---------------------------------------- |
| `symbolNames`  | `String[]` | ✅       | Array of symbol names to query           |

---

## ⬆️ Output - `TickValueWithSizeData`

| Field                    | Type                       | Description                                          |
| ------------------------ | -------------------------- | ---------------------------------------------------- |
| `symbol_tick_size_infos` | `List<TickSizeSymbol>`     | List of tick/size info for each requested symbol     |

Access using `reply.getData().getSymbolTickSizeInfosList()`.

### Structure: `TickSizeSymbol`

| Field                   | Type     | Description                                          |
| ----------------------- | -------- | ---------------------------------------------------- |
| `Index`                 | `int`    | Symbol index in request array                        |
| `Name`                  | `String` | Symbol name (e.g., "EURUSD")                         |
| `TradeTickValue`        | `double` | Calculated tick value in account currency            |
| `TradeTickValueProfit`  | `double` | Calculated tick value for profit direction           |
| `TradeTickValueLoss`    | `double` | Calculated tick value for loss direction             |
| `TradeTickSize`         | `double` | Minimal price change (tick size)                     |
| `TradeContractSize`     | `double` | Trade contract size (lot size in base units)         |

Access using:
- `symbol.getIndex()` - Position in request array
- `symbol.getName()` - Symbol name
- `symbol.getTradeTickValue()` - General tick value
- `symbol.getTradeTickValueProfit()` - Tick value for profit
- `symbol.getTradeTickValueLoss()` - Tick value for loss
- `symbol.getTradeTickSize()` - Minimum price change
- `symbol.getTradeContractSize()` - Contract/lot size

---

## 💬 Just the essentials

* **What it is.** Batch RPC to get tick value and contract size data for multiple symbols.
* **Why you need it.** Calculate profit/loss, position sizing, margin requirements.
* **Tick value.** Cost of one tick movement in account currency.
* **Contract size.** Size of one lot in base units (e.g., 100,000 for EURUSD).
* **Tick size.** Minimum price change step.
* **Batch operation.** More efficient than querying symbols individually.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate profit/loss for positions.
* Determine position size based on risk.
* Calculate margin requirements.
* Convert pips/points to account currency.
* Optimize trading calculations for multiple symbols.
* Build portfolio risk management systems.
* Calculate transaction costs.

---

## 🧩 Notes & Tips

* **Batch efficiency.** Query multiple symbols in one call for better performance.
* **Account currency.** Tick values are in your account currency.
* **Different directions.** Some symbols have different tick values for profit/loss.
* **Forex symbols.** Contract size typically 100,000 (1 standard lot).
* **CFD/Futures.** Contract size varies by instrument.
* **Tick size.** Used to validate price levels and calculate pip values.
* **Missing symbols.** Invalid symbols may be omitted from results.
* **Auto-reconnect.** Uses `executeWithReconnect()` for reliability.

---

## 🔗 Usage Examples

### 1) Basic tick value retrieval

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get tick values for multiple symbols
            String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};
            Mt5TermApiAccountHelper.TickValueWithSizeReply reply =
                account.tickValueWithSize(symbols);

            var tickInfos = reply.getData().getSymbolTickSizeInfosList();

            System.out.println("Tick Value and Size Information:");
            System.out.println("═".repeat(70));

            for (var info : tickInfos) {
                System.out.printf("\n%s:%n", info.getName());
                System.out.printf("  Tick Value: %.2f %s%n",
                    info.getTradeTickValue(), "USD");  // Assuming USD account
                System.out.printf("  Tick Size: %.5f%n", info.getTradeTickSize());
                System.out.printf("  Contract Size: %.0f%n", info.getTradeContractSize());
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Calculate pip value

```java
public class PipValueCalculator {
    /**
     * Calculate pip value for a symbol
     */
    public static double calculatePipValue(
            MT5Account account,
            String symbol,
            double lots) throws ApiExceptionMT5 {

        String[] symbols = {symbol};
        var reply = account.tickValueWithSize(symbols);

        if (reply.getData().getSymbolTickSizeInfosList().isEmpty()) {
            throw new ApiExceptionMT5("Symbol not found: " + symbol);
        }

        var info = reply.getData().getSymbolTickSizeInfosList().get(0);

        // For most forex pairs, 1 pip = 0.0001 (4 decimal places)
        // For JPY pairs, 1 pip = 0.01 (2 decimal places)
        double pipSize = symbol.contains("JPY") ? 0.01 : 0.0001;

        // Calculate how many ticks in a pip
        double ticksPerPip = pipSize / info.getTradeTickSize();

        // Pip value = tick value * ticks per pip * lots
        double pipValue = info.getTradeTickValue() * ticksPerPip * lots;

        System.out.printf("%s Pip Value Calculation:%n", symbol);
        System.out.printf("  Lot Size: %.2f%n", lots);
        System.out.printf("  Tick Value: %.2f%n", info.getTradeTickValue());
        System.out.printf("  Tick Size: %.5f%n", info.getTradeTickSize());
        System.out.printf("  Pip Size: %.5f%n", pipSize);
        System.out.printf("  Ticks per Pip: %.0f%n", ticksPerPip);
        System.out.printf("  Pip Value: %.2f%n", pipValue);

        return pipValue;
    }
}

// Usage
double pipValue = PipValueCalculator.calculatePipValue(account, "EURUSD", 1.0);
System.out.printf("\n1 pip movement = $%.2f for 1 lot EURUSD%n", pipValue);
```

### 3) Calculate profit/loss

```java
public class ProfitLossCalculator {
    /**
     * Calculate profit/loss for a position
     */
    public static double calculateProfit(
            MT5Account account,
            String symbol,
            double lots,
            double openPrice,
            double currentPrice,
            boolean isBuy) throws ApiExceptionMT5 {

        String[] symbols = {symbol};
        var reply = account.tickValueWithSize(symbols);
        var info = reply.getData().getSymbolTickSizeInfosList().get(0);

        // Calculate price difference in ticks
        double priceDiff = isBuy
            ? (currentPrice - openPrice)
            : (openPrice - currentPrice);

        double ticks = priceDiff / info.getTradeTickSize();

        // Use appropriate tick value (profit or loss direction)
        double tickValue = priceDiff >= 0
            ? info.getTradeTickValueProfit()
            : info.getTradeTickValueLoss();

        double profit = ticks * tickValue * lots;

        System.out.printf("\nProfit/Loss Calculation for %s:%n", symbol);
        System.out.printf("  Position: %s %.2f lots%n",
            isBuy ? "BUY" : "SELL", lots);
        System.out.printf("  Open Price: %.5f%n", openPrice);
        System.out.printf("  Current Price: %.5f%n", currentPrice);
        System.out.printf("  Price Difference: %.5f%n", priceDiff);
        System.out.printf("  Ticks: %.0f%n", ticks);
        System.out.printf("  Tick Value: %.2f%n", tickValue);
        System.out.printf("  Profit/Loss: %.2f%n", profit);

        return profit;
    }
}

// Usage
double profit = ProfitLossCalculator.calculateProfit(
    account,
    "EURUSD",
    1.0,        // 1 lot
    1.10000,    // Open price
    1.10050,    // Current price
    true        // BUY position
);
```

### 4) Position sizing based on risk

```java
public class PositionSizer {
    /**
     * Calculate lot size based on risk amount and stop loss
     */
    public static double calculateLotSize(
            MT5Account account,
            String symbol,
            double riskAmount,
            double stopLossPips) throws ApiExceptionMT5 {

        String[] symbols = {symbol};
        var reply = account.tickValueWithSize(symbols);
        var info = reply.getData().getSymbolTickSizeInfosList().get(0);

        // Calculate pip value for 1 lot
        double pipSize = symbol.contains("JPY") ? 0.01 : 0.0001;
        double ticksPerPip = pipSize / info.getTradeTickSize();
        double pipValuePerLot = info.getTradeTickValue() * ticksPerPip;

        // Calculate lot size
        double lotSize = riskAmount / (stopLossPips * pipValuePerLot);

        // Round to valid lot size (typically 0.01 step)
        lotSize = Math.round(lotSize * 100.0) / 100.0;

        System.out.printf("\nPosition Sizing for %s:%n", symbol);
        System.out.printf("  Risk Amount: $%.2f%n", riskAmount);
        System.out.printf("  Stop Loss: %.1f pips%n", stopLossPips);
        System.out.printf("  Pip Value (1 lot): $%.2f%n", pipValuePerLot);
        System.out.printf("  Calculated Lot Size: %.2f%n", lotSize);
        System.out.printf("  Actual Risk: $%.2f%n",
            lotSize * stopLossPips * pipValuePerLot);

        return lotSize;
    }
}

// Usage - risk $100 with 50 pip stop loss
double lotSize = PositionSizer.calculateLotSize(
    account,
    "EURUSD",
    100.0,  // Risk $100
    50.0    // 50 pip stop loss
);
System.out.printf("Trade %.2f lots%n", lotSize);
```

### 5) Compare tick values across symbols

```java
import java.util.*;

public class TickValueComparison {
    public record SymbolTickInfo(
        String symbol,
        double tickValue,
        double tickSize,
        double contractSize,
        double pipValue
    ) {}

    /**
     * Compare tick values for multiple symbols
     */
    public static List<SymbolTickInfo> compareSymbols(
            MT5Account account,
            String... symbols) throws ApiExceptionMT5 {

        var reply = account.tickValueWithSize(symbols);
        var tickInfos = reply.getData().getSymbolTickSizeInfosList();

        List<SymbolTickInfo> comparison = new ArrayList<>();

        for (var info : tickInfos) {
            String symbol = info.getName();
            double pipSize = symbol.contains("JPY") ? 0.01 : 0.0001;
            double ticksPerPip = pipSize / info.getTradeTickSize();
            double pipValue = info.getTradeTickValue() * ticksPerPip;

            comparison.add(new SymbolTickInfo(
                symbol,
                info.getTradeTickValue(),
                info.getTradeTickSize(),
                info.getTradeContractSize(),
                pipValue
            ));
        }

        return comparison;
    }

    /**
     * Print comparison table
     */
    public static void printComparison(List<SymbolTickInfo> comparison) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║            TICK VALUE COMPARISON (1 LOT)                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║ Symbol   │ Tick Val │ Tick Size │ Contract  │ Pip Value   ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");

        for (var info : comparison) {
            System.out.printf("║ %-8s │ $%-7.2f │ %-9.5f │ %-9.0f │ $%-10.2f║%n",
                info.symbol(),
                info.tickValue(),
                info.tickSize(),
                info.contractSize(),
                info.pipValue()
            );
        }

        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}

// Usage
var comparison = TickValueComparison.compareSymbols(account,
    "EURUSD", "GBPUSD", "USDJPY", "AUDUSD", "USDCHF"
);
TickValueComparison.printComparison(comparison);
```

### 6) Portfolio risk calculator

```java
import java.util.*;

public class PortfolioRiskCalculator {
    public record PositionRisk(
        String symbol,
        double lots,
        double stopLossPips,
        double riskAmount
    ) {}

    /**
     * Calculate total portfolio risk
     */
    public static double calculatePortfolioRisk(
            MT5Account account,
            List<PositionRisk> positions) throws ApiExceptionMT5 {

        // Get tick values for all symbols
        String[] symbols = positions.stream()
            .map(PositionRisk::symbol)
            .toArray(String[]::new);

        var reply = account.tickValueWithSize(symbols);
        var tickInfos = reply.getData().getSymbolTickSizeInfosList();

        // Create symbol -> tick info map
        Map<String, Mt5TermApiAccountHelper.TickSizeSymbol> tickMap =
            new HashMap<>();
        for (var info : tickInfos) {
            tickMap.put(info.getName(), info);
        }

        double totalRisk = 0;

        System.out.println("\n╔═════════════════════════════════════════════════╗");
        System.out.println("║         PORTFOLIO RISK ANALYSIS                 ║");
        System.out.println("╠═════════════════════════════════════════════════╣");
        System.out.println("║ Symbol   │ Lots │ SL Pips │ Risk Amount       ║");
        System.out.println("╠═════════════════════════════════════════════════╣");

        for (var position : positions) {
            var info = tickMap.get(position.symbol());
            if (info == null) continue;

            // Calculate pip value
            String symbol = position.symbol();
            double pipSize = symbol.contains("JPY") ? 0.01 : 0.0001;
            double ticksPerPip = pipSize / info.getTradeTickSize();
            double pipValue = info.getTradeTickValue() * ticksPerPip;

            // Calculate risk
            double risk = position.lots() * position.stopLossPips() * pipValue;
            totalRisk += risk;

            System.out.printf("║ %-8s │ %.2f │ %-7.1f │ $%-16.2f ║%n",
                position.symbol(),
                position.lots(),
                position.stopLossPips(),
                risk
            );
        }

        System.out.println("╠═════════════════════════════════════════════════╣");
        System.out.printf("║ TOTAL PORTFOLIO RISK: $%-25.2f ║%n", totalRisk);
        System.out.println("╚═════════════════════════════════════════════════╝");

        return totalRisk;
    }
}

// Usage
List<PortfolioRiskCalculator.PositionRisk> positions = List.of(
    new PortfolioRiskCalculator.PositionRisk("EURUSD", 1.0, 50, 0),
    new PortfolioRiskCalculator.PositionRisk("GBPUSD", 0.5, 40, 0),
    new PortfolioRiskCalculator.PositionRisk("USDJPY", 1.5, 30, 0)
);

double totalRisk = PortfolioRiskCalculator.calculatePortfolioRisk(
    account, positions
);

System.out.printf("\nTotal risk exposure: $%.2f%n", totalRisk);
```

### 7) Convert points to currency

```java
public class PointToCurrencyConverter {
    /**
     * Convert points/pips to account currency
     */
    public static double convertPointsToCurrency(
            MT5Account account,
            String symbol,
            double points,
            double lots) throws ApiExceptionMT5 {

        String[] symbols = {symbol};
        var reply = account.tickValueWithSize(symbols);
        var info = reply.getData().getSymbolTickSizeInfosList().get(0);

        // Calculate ticks (points = price difference in symbol's point unit)
        double ticks = points / info.getTradeTickSize();

        // Convert to currency
        double currencyValue = ticks * info.getTradeTickValue() * lots;

        System.out.printf("\nPoint-to-Currency Conversion for %s:%n", symbol);
        System.out.printf("  Points: %.1f%n", points);
        System.out.printf("  Lots: %.2f%n", lots);
        System.out.printf("  Tick Size: %.5f%n", info.getTradeTickSize());
        System.out.printf("  Ticks: %.0f%n", ticks);
        System.out.printf("  Tick Value: %.2f%n", info.getTradeTickValue());
        System.out.printf("  Currency Value: $%.2f%n", currencyValue);

        return currencyValue;
    }

    /**
     * Convert currency to points
     */
    public static double convertCurrencyToPoints(
            MT5Account account,
            String symbol,
            double currencyAmount,
            double lots) throws ApiExceptionMT5 {

        String[] symbols = {symbol};
        var reply = account.tickValueWithSize(symbols);
        var info = reply.getData().getSymbolTickSizeInfosList().get(0);

        // Calculate ticks needed
        double ticks = currencyAmount / (info.getTradeTickValue() * lots);

        // Convert to points
        double points = ticks * info.getTradeTickSize();

        System.out.printf("\nCurrency-to-Point Conversion for %s:%n", symbol);
        System.out.printf("  Currency Amount: $%.2f%n", currencyAmount);
        System.out.printf("  Lots: %.2f%n", lots);
        System.out.printf("  Required Points: %.5f%n", points);

        return points;
    }
}

// Usage
// How much is 50 pips worth?
double value = PointToCurrencyConverter.convertPointsToCurrency(
    account, "EURUSD", 0.0050, 1.0
);

// How many pips to make $100?
double pips = PointToCurrencyConverter.convertCurrencyToPoints(
    account, "EURUSD", 100.0, 1.0
);
```

### 8) Batch symbol data exporter

```java
import java.io.*;

public class SymbolDataExporter {
    /**
     * Export tick value data to CSV
     */
    public static void exportToCsv(
            MT5Account account,
            String filename,
            String... symbols) throws ApiExceptionMT5, IOException {

        var reply = account.tickValueWithSize(symbols);
        var tickInfos = reply.getData().getSymbolTickSizeInfosList();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // CSV header
            writer.println("Symbol,TickValue,TickValueProfit,TickValueLoss," +
                "TickSize,ContractSize,PipValue");

            // Data rows
            for (var info : tickInfos) {
                String symbol = info.getName();
                double pipSize = symbol.contains("JPY") ? 0.01 : 0.0001;
                double ticksPerPip = pipSize / info.getTradeTickSize();
                double pipValue = info.getTradeTickValue() * ticksPerPip;

                writer.printf("%s,%.5f,%.5f,%.5f,%.7f,%.2f,%.5f%n",
                    info.getName(),
                    info.getTradeTickValue(),
                    info.getTradeTickValueProfit(),
                    info.getTradeTickValueLoss(),
                    info.getTradeTickSize(),
                    info.getTradeContractSize(),
                    pipValue
                );
            }

            System.out.printf("✅ Exported %d symbols to %s%n",
                tickInfos.size(), filename);
        }
    }
}

// Usage
SymbolDataExporter.exportToCsv(account, "symbol_tick_data.csv",
    "EURUSD", "GBPUSD", "USDJPY", "AUDUSD", "USDCHF",
    "NZDUSD", "USDCAD", "EURJPY", "GBPJPY", "XAUUSD"
);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;
import java.util.Arrays;

// Create request
Mt5TermApiAccountHelper.TickValueWithSizeRequest request =
    Mt5TermApiAccountHelper.TickValueWithSizeRequest.newBuilder()
        .addAllSymbolNames(Arrays.asList("EURUSD", "GBPUSD", "USDJPY"))
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiAccountHelper.TickValueWithSizeReply reply = accountHelperClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .tickValueWithSize(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
List<Mt5TermApiAccountHelper.TickSizeSymbol> tickInfos =
    reply.getData().getSymbolTickSizeInfosList();
```

---

## 📊 Understanding Tick Values

### Tick Value:

- Cost of one minimum price change (tick) in your account currency
- Used to calculate profit/loss from price movements
- Different for each symbol and account currency

### Tick Size:

- Minimum price change for the symbol
- Example: EURUSD = 0.00001 (1 point)
- Used to validate prices and count ticks

### Contract Size:

- Size of one standard lot
- Forex: typically 100,000 units of base currency
- CFDs/Futures: varies by instrument

### Profit/Loss Tick Values:

- Some instruments have different tick values for profit vs loss direction
- Usually same for forex, different for some CFDs/futures
- Use appropriate value for accurate P/L calculations

### Key Formulas:

```java
// Calculate profit/loss
ticks = (closePrice - openPrice) / tickSize;
profit = ticks * tickValue * lots;

// Calculate pip value
ticksPerPip = pipSize / tickSize;
pipValue = tickValue * ticksPerPip;

// Calculate position size from risk
lotSize = riskAmount / (stopLossPips * pipValue);
```

### Common Pip Sizes:

- Most forex pairs: 0.0001 (4 decimals)
- JPY pairs: 0.01 (2 decimals)
- Some brokers show 5 decimal places (pipettes)
