# 💵 Calculate Required Margin

> **Request:** calculate the margin required to open a position with specified parameters. Returns the amount of funds needed in account currency.

**API Information:**

* **SDK wrapper:** `MT5Account.orderCalcMargin(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.TradeFunctions`
* **Proto definition:** `OrderCalcMargin` (defined in `mt5-term-api-trade-functions.proto`)

### RPC

* **Service:** `mt5_term_api.TradeFunctions`
* **Method:** `OrderCalcMargin(OrderCalcMarginRequest) → OrderCalcMarginReply`
* **Low‑level client (generated):** `TradeFunctionsGrpc.TradeFunctionsBlockingStub.orderCalcMargin(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Calculates the margin required to open a position with specified parameters.
     * Returns the amount of funds needed in account currency to maintain the position.
     * Use this before placing orders to verify sufficient margin and avoid margin call risks.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param orderType Order type (BUY or SELL)
     * @param volume Position volume in lots
     * @param openPrice Expected open price for the position
     * @return Calculated margin requirement in account currency
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.OrderCalcMarginReply orderCalcMargin(
        String symbol,
        Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF orderType,
        double volume,
        double openPrice) throws ApiExceptionMT5;
}
```

---

## 🔽 Input

| Parameter    | Type                      | Required | Description                              |
| ------------ | ------------------------- | -------- | ---------------------------------------- |
| `symbol`     | `String`                  | ✅       | Symbol name (e.g., "EURUSD")             |
| `orderType`  | `ENUM_ORDER_TYPE_TF`      | ✅       | Order type (BUY or SELL)                 |
| `volume`     | `double`                  | ✅       | Volume in lots                           |
| `openPrice`  | `double`                  | ✅       | Expected open price                      |

### Enum: `ENUM_ORDER_TYPE_TF`

| Value                     | Number | Description                  |
| ------------------------- | ------ | ---------------------------- |
| `ORDER_TYPE_TF_BUY`       | 0      | Buy order                    |
| `ORDER_TYPE_TF_SELL`      | 1      | Sell order                   |

---

## ⬆️ Output — `OrderCalcMarginData`

| Field    | Type     | Description                                          |
| -------- | -------- | ---------------------------------------------------- |
| `margin` | `double` | Required margin in account currency                  |

Access using `reply.getData().getMargin()`.

---

## 💬 Just the essentials

* **What it is.** Calculate margin needed to open a position.
* **Why you need it.** Verify sufficient funds before trading.
* **Returns.** Margin amount in your account currency.
* **Use before trading.** Check if you have enough balance.
* **Depends on.** Leverage, symbol, volume, and account settings.

---

## 🎯 Purpose

Use this method when you need to:

* Check if you have enough funds to open a position.
* Calculate maximum position size based on available margin.
* Implement risk management and position sizing.
* Avoid margin calls by pre-validating trades.
* Display margin requirements to users.
* Build automated trading systems with proper capital management.

---

## 🔗 Usage Examples

### 1) Basic margin calculation

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiTradeFunctions;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Calculate margin for 1 lot EURUSD
            Mt5TermApiTradeFunctions.OrderCalcMarginReply reply =
                account.orderCalcMargin(
                    "EURUSD",
                    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                    1.0,      // 1 lot
                    1.10000   // Price
                );

            double margin = reply.getData().getMargin();

            System.out.printf("Required margin: $%.2f%n", margin);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Check if enough margin available

```java
public class MarginChecker {
    /**
     * Check if there's enough free margin for a trade
     */
    public static boolean canOpenPosition(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots,
            double price) throws ApiExceptionMT5 {

        // Get required margin
        var orderType = isBuy
            ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
            : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL;

        var marginReply = account.orderCalcMargin(
            symbol, orderType, lots, price
        );

        double requiredMargin = marginReply.getData().getMargin();

        // Get account free margin
        var accountInfo = account.accountInfoDouble(
            Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN_FREE
        );

        double freeMargin = accountInfo.getData().getValue();

        System.out.printf("Trade analysis for %s %.2f lots:%n", symbol, lots);
        System.out.printf("  Required margin: $%.2f%n", requiredMargin);
        System.out.printf("  Free margin: $%.2f%n", freeMargin);

        if (freeMargin >= requiredMargin) {
            double marginAfter = freeMargin - requiredMargin;
            System.out.printf("  ✅ Sufficient margin (%.2f remaining)%n", marginAfter);
            return true;
        } else {
            double shortage = requiredMargin - freeMargin;
            System.out.printf("  ❌ Insufficient margin (short by $%.2f)%n", shortage);
            return false;
        }
    }
}

// Usage
if (MarginChecker.canOpenPosition(account, "EURUSD", true, 1.0, 1.10000)) {
    System.out.println("Safe to place order");
}
```

### 3) Calculate maximum lot size

```java
public class MaxLotCalculator {
    /**
     * Calculate maximum lot size based on available margin
     */
    public static double calculateMaxLots(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double price,
            double marginUtilization) throws ApiExceptionMT5 {

        // Get free margin
        var accountInfo = account.accountInfoDouble(
            Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN_FREE
        );
        double freeMargin = accountInfo.getData().getValue();

        // Calculate margin for 1 lot
        var orderType = isBuy
            ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
            : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL;

        var marginReply = account.orderCalcMargin(
            symbol, orderType, 1.0, price
        );

        double marginPer1Lot = marginReply.getData().getMargin();

        // Calculate max lots (with utilization factor)
        double availableMargin = freeMargin * marginUtilization;
        double maxLots = availableMargin / marginPer1Lot;

        // Round down to 0.01
        maxLots = Math.floor(maxLots * 100.0) / 100.0;

        System.out.printf("Max Lot Calculation for %s:%n", symbol);
        System.out.printf("  Free Margin: $%.2f%n", freeMargin);
        System.out.printf("  Utilization: %.0f%%%n", marginUtilization * 100);
        System.out.printf("  Margin per 1 lot: $%.2f%n", marginPer1Lot);
        System.out.printf("  Max Lots: %.2f%n", maxLots);

        return maxLots;
    }
}

// Usage - use 50% of free margin
double maxLots = MaxLotCalculator.calculateMaxLots(
    account, "EURUSD", true, 1.10000, 0.5
);
System.out.printf("Can trade up to %.2f lots%n", maxLots);
```

### 4) Margin comparison across symbols

```java
import java.util.*;

public class MarginComparison {
    public record SymbolMargin(
        String symbol,
        double marginPerLot,
        double maxLots
    ) {}

    /**
     * Compare margin requirements for multiple symbols
     */
    public static List<SymbolMargin> compareSymbols(
            MT5Account account,
            double freeMargin,
            String... symbols) throws ApiExceptionMT5 {

        List<SymbolMargin> comparison = new ArrayList<>();

        System.out.printf("Comparing margin for %.2f free margin:%n", freeMargin);
        System.out.println("═".repeat(60));

        for (String symbol : symbols) {
            try {
                // Get current price
                var quote = account.symbolInfoTick(symbol);
                double price = quote.getData().getAsk();

                // Calculate margin
                var marginReply = account.orderCalcMargin(
                    symbol,
                    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
                    1.0,
                    price
                );

                double marginPerLot = marginReply.getData().getMargin();
                double maxLots = Math.floor((freeMargin / marginPerLot) * 100) / 100;

                comparison.add(new SymbolMargin(symbol, marginPerLot, maxLots));

                System.out.printf("%-10s: $%-8.2f/lot → max %.2f lots%n",
                    symbol, marginPerLot, maxLots);

            } catch (ApiExceptionMT5 e) {
                System.err.printf("%-10s: Error - %s%n", symbol, e.getMessage());
            }
        }

        System.out.println("═".repeat(60));
        return comparison;
    }
}

// Usage
MarginComparison.compareSymbols(account, 10000.0,
    "EURUSD", "GBPUSD", "USDJPY", "XAUUSD", "BTCUSD"
);
```

### 5) Portfolio margin calculator

```java
public class PortfolioMarginCalculator {
    public record TradeRequest(
        String symbol,
        boolean isBuy,
        double lots,
        double price
    ) {}

    /**
     * Calculate total margin for multiple trades
     */
    public static double calculatePortfolioMargin(
            MT5Account account,
            List<TradeRequest> trades) throws ApiExceptionMT5 {

        double totalMargin = 0;

        System.out.println("Portfolio Margin Calculation:");
        System.out.println("═".repeat(60));

        for (int i = 0; i < trades.size(); i++) {
            var trade = trades.get(i);

            var orderType = trade.isBuy()
                ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
                : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL;

            var reply = account.orderCalcMargin(
                trade.symbol(),
                orderType,
                trade.lots(),
                trade.price()
            );

            double margin = reply.getData().getMargin();
            totalMargin += margin;

            System.out.printf("Trade %d: %s %s %.2f lots @ %.5f → $%.2f%n",
                i + 1,
                trade.isBuy() ? "BUY" : "SELL",
                trade.symbol(),
                trade.lots(),
                trade.price(),
                margin
            );
        }

        System.out.println("═".repeat(60));
        System.out.printf("Total Required Margin: $%.2f%n", totalMargin);

        // Check if sufficient
        var accountInfo = account.accountInfoDouble(
            Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_MARGIN_FREE
        );
        double freeMargin = accountInfo.getData().getValue();

        System.out.printf("Available Free Margin: $%.2f%n", freeMargin);

        if (freeMargin >= totalMargin) {
            System.out.printf("✅ Sufficient margin ($%.2f remaining)%n",
                freeMargin - totalMargin);
        } else {
            System.out.printf("❌ Insufficient margin (short by $%.2f)%n",
                totalMargin - freeMargin);
        }

        return totalMargin;
    }
}

// Usage
List<PortfolioMarginCalculator.TradeRequest> portfolio = List.of(
    new PortfolioMarginCalculator.TradeRequest("EURUSD", true, 0.5, 1.10000),
    new PortfolioMarginCalculator.TradeRequest("GBPUSD", false, 0.3, 1.27000),
    new PortfolioMarginCalculator.TradeRequest("USDJPY", true, 0.4, 148.500)
);

double totalMargin = PortfolioMarginCalculator.calculatePortfolioMargin(
    account, portfolio
);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Build request
Mt5TermApiTradeFunctions.OrderCalcMarginRequest request =
    Mt5TermApiTradeFunctions.OrderCalcMarginRequest.newBuilder()
        .setSymbol("EURUSD")
        .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
        .setVolume(1.0)
        .setOpenPrice(1.10000)
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiTradeFunctions.OrderCalcMarginReply reply = tradeFunctionsClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .orderCalcMargin(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Get margin
double margin = reply.getData().getMargin();
```

---

## 📌 Important Notes

**What Affects Margin:**
- Account leverage (1:100, 1:500, etc.)
- Symbol contract size
- Symbol margin requirements
- Account currency vs symbol currency
- Current market conditions

**Calculation:**
- Returns margin as if account has no other positions
- Actual margin may differ if you have existing positions
- Use for pre-trade validation, not real-time monitoring

**Best Practices:**
- Always check before placing orders
- Leave safety margin (don't use 100% of free margin)
- Consider other open positions
- Monitor free margin regularly
- Use margin level alerts

**Typical Margins (1:100 leverage):**
- EURUSD 1 lot: ~$1,000
- GBPUSD 1 lot: ~$1,270
- XAUUSD 1 lot: ~$2,000+
- Varies by broker and account type
