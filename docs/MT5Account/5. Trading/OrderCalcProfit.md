# 📈 Calculate Potential Profit/Loss

> **Request:** calculate potential profit or loss for a trading operation. Returns P&L in account currency for opening at one price and closing at another.

**API Information:**

* **SDK wrapper:** `MT5Account.orderCalcProfit(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.TradeFunctions`
* **Proto definition:** `OrderCalcProfit` (defined in `mt5-term-api-trade-functions.proto`)

### RPC

* **Service:** `mt5_term_api.TradeFunctions`
* **Method:** `OrderCalcProfit(OrderCalcProfitRequest) → OrderCalcProfitReply`
* **Low‑level client (generated):** `TradeFunctionsGrpc.TradeFunctionsBlockingStub.orderCalcProfit(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Calculates the potential profit or loss for a trading operation.
     * Returns the P&L in account currency for opening at one price and closing at another.
     * Use this to evaluate potential trades, set profit targets, or calculate risk/reward ratios.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param orderType Order type (BUY or SELL)
     * @param volume Position volume in lots
     * @param openPrice Entry price for the position
     * @param closePrice Exit price for the position
     * @return Calculated profit/loss in account currency
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.OrderCalcProfitReply orderCalcProfit(
        String symbol,
        Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF orderType,
        double volume,
        double openPrice,
        double closePrice) throws ApiExceptionMT5;
}
```

---

## 🔽 Input

| Parameter    | Type                 | Required | Description                              |
| ------------ | -------------------- | -------- | ---------------------------------------- |
| `symbol`     | `String`             | ✅       | Symbol name (e.g., "EURUSD")             |
| `orderType`  | `ENUM_ORDER_TYPE_TF` | ✅       | Order type (BUY or SELL)                 |
| `volume`     | `double`             | ✅       | Volume in lots                           |
| `openPrice`  | `double`             | ✅       | Entry price                              |
| `closePrice` | `double`             | ✅       | Exit price                               |

---

## ⬆️ Output — `OrderCalcProfitData`

| Field    | Type     | Description                                          |
| -------- | -------- | ---------------------------------------------------- |
| `profit` | `double` | Calculated profit/loss in account currency           |

Access using `reply.getData().getProfit()`.

**Note:** Positive value = profit, negative value = loss.

---

## 💬 Just the essentials

* **What it is.** Calculate P&L for a trade scenario.
* **Why you need it.** Evaluate trades, calculate risk/reward, set targets.
* **Returns.** Profit/loss in your account currency.
* **Positive = profit.** Negative = loss.
* **Pre-trade planning.** Use before placing orders to assess potential.

---

## 🎯 Purpose

Use this method when you need to:

* Calculate potential profit for a trade idea.
* Evaluate risk/reward ratios before trading.
* Set realistic profit targets.
* Calculate stop loss distances in currency.
* Compare profitability across different symbols.
* Build trading calculators and analysis tools.

---

## 🔗 Usage Examples

### 1) Basic profit calculation

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiTradeFunctions;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Calculate profit: BUY at 1.10000, SELL at 1.10500
            Mt5TermApiTradeFunctions.OrderCalcProfitReply reply =
                account.orderCalcProfit(
                    "EURUSD",
                    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                    1.0,      // 1 lot
                    1.10000,  // Entry
                    1.10500   // Exit
                );

            double profit = reply.getData().getProfit();

            System.out.printf("Potential profit: $%.2f%n", profit);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Calculate risk/reward ratio

```java
public class RiskRewardCalculator {
    /**
     * Calculate risk/reward ratio for a trade
     */
    public static double calculateRiskReward(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots,
            double entryPrice,
            double stopLoss,
            double takeProfit) throws ApiExceptionMT5 {

        var orderType = isBuy
            ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
            : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL;

        // Calculate potential profit (to TP)
        var profitReply = account.orderCalcProfit(
            symbol, orderType, lots, entryPrice, takeProfit
        );
        double potentialProfit = profitReply.getData().getProfit();

        // Calculate potential loss (to SL)
        var lossReply = account.orderCalcProfit(
            symbol, orderType, lots, entryPrice, stopLoss
        );
        double potentialLoss = Math.abs(lossReply.getData().getProfit());

        // Calculate ratio
        double riskRewardRatio = potentialProfit / potentialLoss;

        System.out.printf("\nRisk/Reward Analysis for %s:%n", symbol);
        System.out.println("═".repeat(50));
        System.out.printf("Entry: %.5f%n", entryPrice);
        System.out.printf("Stop Loss: %.5f%n", stopLoss);
        System.out.printf("Take Profit: %.5f%n", takeProfit);
        System.out.println("─".repeat(50));
        System.out.printf("Potential Risk: $%.2f%n", potentialLoss);
        System.out.printf("Potential Reward: $%.2f%n", potentialProfit);
        System.out.printf("Risk/Reward Ratio: 1:%.2f%n", riskRewardRatio);
        System.out.println("═".repeat(50));

        if (riskRewardRatio >= 2.0) {
            System.out.println("✅ Good risk/reward (≥ 1:2)");
        } else if (riskRewardRatio >= 1.5) {
            System.out.println("⚠️ Acceptable risk/reward (≥ 1:1.5)");
        } else {
            System.out.println("❌ Poor risk/reward (< 1:1.5)");
        }

        return riskRewardRatio;
    }
}

// Usage
double ratio = RiskRewardCalculator.calculateRiskReward(
    account,
    "EURUSD",
    true,       // BUY
    1.0,        // 1 lot
    1.10000,    // Entry
    1.09500,    // SL (-50 pips)
    1.11000     // TP (+100 pips)
);
```

### 3) Calculate profit at different targets

```java
import java.util.*;

public class ProfitTargetCalculator {
    /**
     * Calculate profit at multiple price targets
     */
    public static void calculateTargets(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots,
            double entryPrice,
            int[] targetPips) throws ApiExceptionMT5 {

        var orderType = isBuy
            ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
            : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL;

        double pipValue = symbol.contains("JPY") ? 0.01 : 0.0001;

        System.out.printf("\nProfit Targets for %s %s %.2f lots @ %.5f:%n",
            isBuy ? "BUY" : "SELL", symbol, lots, entryPrice);
        System.out.println("═".repeat(60));
        System.out.printf("%-10s | %-12s | %-15s%n", "Target", "Price", "Profit");
        System.out.println("─".repeat(60));

        for (int pips : targetPips) {
            double targetPrice = entryPrice + (pips * pipValue * (isBuy ? 1 : -1));

            var reply = account.orderCalcProfit(
                symbol, orderType, lots, entryPrice, targetPrice
            );

            double profit = reply.getData().getProfit();

            System.out.printf("%-10d | %.5f | $%-14.2f%n",
                pips, targetPrice, profit);
        }

        System.out.println("═".repeat(60));
    }
}

// Usage
ProfitTargetCalculator.calculateTargets(
    account,
    "EURUSD",
    true,
    1.0,
    1.10000,
    new int[]{10, 25, 50, 100, 200}  // Pip targets
);
```

### 4) Calculate Stop Loss in currency

```java
public class StopLossCalculator {
    /**
     * Calculate how much you'll lose at different SL levels
     */
    public static void calculateStopLosses(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots,
            double entryPrice,
            double maxRiskAmount) throws ApiExceptionMT5 {

        var orderType = isBuy
            ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
            : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL;

        double pipValue = symbol.contains("JPY") ? 0.01 : 0.0001;

        System.out.printf("\nStop Loss Analysis (Max Risk: $%.2f):%n", maxRiskAmount);
        System.out.println("═".repeat(60));
        System.out.printf("%-10s | %-12s | %-10s | Status%n",
            "SL Pips", "SL Price", "Loss");
        System.out.println("─".repeat(60));

        int[] slPips = {20, 30, 40, 50, 75, 100};

        for (int pips : slPips) {
            double slPrice = entryPrice - (pips * pipValue * (isBuy ? 1 : -1));

            var reply = account.orderCalcProfit(
                symbol, orderType, lots, entryPrice, slPrice
            );

            double loss = Math.abs(reply.getData().getProfit());
            String status = loss <= maxRiskAmount ? "✅ OK" : "❌ Too High";

            System.out.printf("%-10d | %.5f | $%-9.2f | %s%n",
                pips, slPrice, loss, status);
        }

        System.out.println("═".repeat(60));
    }
}

// Usage - max risk $500
StopLossCalculator.calculateStopLosses(
    account, "EURUSD", true, 1.0, 1.10000, 500.0
);
```

### 5) Compare profitability across symbols

```java
public class SymbolProfitComparison {
    public record SymbolProfit(
        String symbol,
        double profitPer100Pips
    ) {}

    /**
     * Compare profit potential across different symbols
     */
    public static List<SymbolProfit> compareSymbols(
            MT5Account account,
            double lots,
            String... symbols) throws ApiExceptionMT5 {

        List<SymbolProfit> comparison = new ArrayList<>();

        System.out.printf("Profit Comparison (%.2f lots, 100 pips move):%n", lots);
        System.out.println("═".repeat(50));

        for (String symbol : symbols) {
            try {
                // Get current price
                var quote = account.symbolInfoTick(symbol);
                double entryPrice = quote.getData().getAsk();

                // Calculate 100 pip move
                double pipValue = symbol.contains("JPY") ? 0.01 : 0.0001;
                double targetPrice = entryPrice + (100 * pipValue);

                // Calculate profit
                var reply = account.orderCalcProfit(
                    symbol,
                    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                    lots,
                    entryPrice,
                    targetPrice
                );

                double profit = reply.getData().getProfit();
                comparison.add(new SymbolProfit(symbol, profit));

                System.out.printf("%-10s: $%.2f%n", symbol, profit);

            } catch (ApiExceptionMT5 e) {
                System.err.printf("%-10s: Error - %s%n", symbol, e.getMessage());
            }
        }

        System.out.println("═".repeat(50));

        // Sort by profit
        comparison.sort((a, b) ->
            Double.compare(b.profitPer100Pips(), a.profitPer100Pips())
        );

        System.out.println("\nMost Profitable:");
        comparison.stream().limit(3).forEach(s ->
            System.out.printf("  %s: $%.2f%n", s.symbol(), s.profitPer100Pips())
        );

        return comparison;
    }
}

// Usage
SymbolProfitComparison.compareSymbols(account, 1.0,
    "EURUSD", "GBPUSD", "USDJPY", "AUDUSD", "XAUUSD"
);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Build request
Mt5TermApiTradeFunctions.OrderCalcProfitRequest request =
    Mt5TermApiTradeFunctions.OrderCalcProfitRequest.newBuilder()
        .setSymbol("EURUSD")
        .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
        .setVolume(1.0)
        .setOpenPrice(1.10000)
        .setClosePrice(1.10500)
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiTradeFunctions.OrderCalcProfitReply reply = tradeFunctionsClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .orderCalcProfit(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Get profit
double profit = reply.getData().getProfit();
```

---

## 📌 Important Notes

**Profit Calculation:**
- Positive value = profit
- Negative value = loss
- In your account currency
- Excludes commission, swap, fees

**For BUY orders:**
- Profit when close price > open price
- Loss when close price < open price

**For SELL orders:**
- Profit when close price < open price
- Loss when close price > open price

**Use Cases:**
- Pre-trade analysis
- Risk/reward calculation
- Profit target planning
- Position sizing decisions
- Trading calculator tools

**Limitations:**
- Does not include commission
- Does not include swap charges
- Does not include spreads
- Actual profit may differ due to slippage
- Use for planning, not exact P&L tracking

**Best Practices:**
- Always calculate risk/reward before trading
- Aim for minimum 1:2 risk/reward ratio
- Factor in additional costs (commission, spread)
- Use for trade evaluation and planning
- Combine with margin calculations for complete analysis
