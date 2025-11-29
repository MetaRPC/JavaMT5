# ✅ Validate Trading Request Before Execution

> **Request:** validate a trading request without actually sending it. Returns detailed information about account state after the trade, required margin, and whether sufficient funds are available.

**API Information:**

* **SDK wrapper:** `MT5Account.orderCheck(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.TradeFunctions`
* **Proto definition:** `OrderCheck` (defined in `mt5-term-api-trade-functions.proto`)

### RPC

* **Service:** `mt5_term_api.TradeFunctions`
* **Method:** `OrderCheck(OrderCheckRequest) → OrderCheckReply`
* **Low‑level client (generated):** `TradeFunctionsGrpc.TradeFunctionsBlockingStub.orderCheck(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Validates a trading request before sending it to the broker.
     * Returns estimated balance, equity, margin requirements, and profit after the trade.
     * Use this to ensure sufficient funds before placing orders.
     *
     * @param request Trading request to validate (MrpcMqlTradeRequest)
     * @return Validation result with margin, balance, equity, profit calculations
     * @throws ApiExceptionMT5 if the validation fails or connection is lost
     */
    public Mt5TermApiTradeFunctions.OrderCheckReply orderCheck(
        Mt5TermApiTradeFunctions.MrpcMqlTradeRequest request) throws ApiExceptionMT5;
}
```

---

## 🔽 Input - `MrpcMqlTradeRequest`

| Parameter                     | Type                              | Required | Description                                          |
| ----------------------------- | --------------------------------- | -------- | ---------------------------------------------------- |
| `action`                      | `MRPC_ENUM_TRADE_REQUEST_ACTIONS` | ✅       | Trade operation type (DEAL, PENDING, etc.)           |
| `symbol`                      | `String`                          | ✅       | Symbol name (e.g., "EURUSD")                         |
| `volume`                      | `double`                          | ✅       | Requested volume in lots                             |
| `order_type`                  | `ENUM_ORDER_TYPE_TF`              | ✅       | Order type (BUY, SELL, BUY_LIMIT, etc.)              |
| `price`                       | `double`                          | ❌       | Order price (required for pending orders)            |
| `stop_loss`                   | `double`                          | ❌       | Stop Loss level                                      |
| `take_profit`                 | `double`                          | ❌       | Take Profit level                                    |
| `deviation`                   | `uint64`                          | ❌       | Maximum slippage in points                           |
| `expert_advisor_magic_number` | `uint64`                          | ❌       | Expert Advisor ID (magic number)                     |
| `stop_limit`                  | `double`                          | ❌       | Stop Limit price (for STOP_LIMIT orders)             |
| `type_filling`                | `MRPC_ENUM_ORDER_TYPE_FILLING`    | ❌       | Order filling type (FOK, IOC, RETURN, BOC)           |
| `type_time`                   | `MRPC_ENUM_ORDER_TYPE_TIME`       | ❌       | Order expiration type                                |
| `expiration`                  | `Timestamp`                       | ❌       | Expiration time (if type is SPECIFIED)               |
| `comment`                     | `String`                          | ❌       | Order comment                                        |
| `order`                       | `uint64`                          | ❌       | Order ticket (for modify/remove actions)             |
| `position`                    | `uint64`                          | ❌       | Position ticket (for SLTP action)                    |
| `position_by`                 | `uint64`                          | ❌       | Opposite position ticket (for CLOSE_BY action)       |

### Enum: `MRPC_ENUM_TRADE_REQUEST_ACTIONS` (Trade Actions)

| Value                    | Number | Description                                          |
| ------------------------ | ------ | ---------------------------------------------------- |
| `TRADE_ACTION_DEAL`      | 0      | Market order for immediate execution                 |
| `TRADE_ACTION_PENDING`   | 1      | Pending order with specified conditions              |
| `TRADE_ACTION_SLTP`      | 2      | Modify SL/TP of opened position                      |
| `TRADE_ACTION_MODIFY`    | 3      | Modify pending order parameters                      |
| `TRADE_ACTION_REMOVE`    | 4      | Delete pending order                                 |
| `TRADE_ACTION_CLOSE_BY`  | 5      | Close position by opposite position                  |

### Enum: `ENUM_ORDER_TYPE_TF` (Order Types)

| Value                          | Number | Description                                          |
| ------------------------------ | ------ | ---------------------------------------------------- |
| `ORDER_TYPE_TF_BUY`            | 0      | Market Buy order                                     |
| `ORDER_TYPE_TF_SELL`           | 1      | Market Sell order                                    |
| `ORDER_TYPE_TF_BUY_LIMIT`      | 2      | Buy Limit pending order                              |
| `ORDER_TYPE_TF_SELL_LIMIT`     | 3      | Sell Limit pending order                             |
| `ORDER_TYPE_TF_BUY_STOP`       | 4      | Buy Stop pending order                               |
| `ORDER_TYPE_TF_SELL_STOP`      | 5      | Sell Stop pending order                              |
| `ORDER_TYPE_TF_BUY_STOP_LIMIT` | 6      | Buy Stop Limit order                                 |
| `ORDER_TYPE_TF_SELL_STOP_LIMIT`| 7      | Sell Stop Limit order                                |
| `ORDER_TYPE_TF_CLOSE_BY`       | 8      | Close by opposite position                           |

### Enum: `MRPC_ENUM_ORDER_TYPE_FILLING` (Order Filling Types)

| Value                  | Number | Description                                          |
| ---------------------- | ------ | ---------------------------------------------------- |
| `ORDER_FILLING_FOK`    | 0      | Fill or Kill - execute full volume or cancel         |
| `ORDER_FILLING_IOC`    | 1      | Immediate or Cancel - execute available, cancel rest |
| `ORDER_FILLING_RETURN` | 2      | Return orders - partial fills allowed                |
| `ORDER_FILLING_BOC`    | 3      | Book or Cancel - must be in order book               |

### Enum: `MRPC_ENUM_ORDER_TYPE_TIME` (Time in Force)

| Value                      | Number | Description                                          |
| -------------------------- | ------ | ---------------------------------------------------- |
| `ORDER_TIME_GTC`           | 0      | Good Till Cancel                                     |
| `ORDER_TIME_DAY`           | 1      | Good Till End of Trading Day                         |
| `ORDER_TIME_SPECIFIED`     | 2      | Good Till Specified Time                             |
| `ORDER_TIME_SPECIFIED_DAY` | 3      | Good Till End of Specified Day (23:59:59)            |

---

## ⬆️ Output - `MrpcMqlTradeCheckResult`

| Field                 | Type     | Description                                          |
| --------------------- | -------- | ---------------------------------------------------- |
| `returned_code`       | `uint32` | Return code (10009 = success, other = error)         |
| `balance_after_deal`  | `double` | Estimated account balance after trade execution      |
| `equity_after_deal`   | `double` | Estimated account equity after trade execution       |
| `profit`              | `double` | Estimated floating profit                            |
| `margin`              | `double` | Required margin for this trade                       |
| `free_margin`         | `double` | Free margin after trade execution                    |
| `margin_level`        | `double` | Margin level after trade (equity/margin * 100)       |
| `comment`             | `String` | Broker comment (error description if failed)         |

Access using `reply.getData().getMqlTradeCheckResult().<field>`.

**Important:**
- `returned_code = 10009` means validation passed (sufficient funds)
- Other codes indicate errors (insufficient margin, invalid parameters, etc.)
- All financial values are estimated and may differ from actual execution

---

## 💬 Just the essentials

* **What it is.** Pre-validate trading requests before sending.
* **Why you need it.** Check funds, avoid rejected orders, calculate margin impact.
* **Returns.** Estimated balance, equity, margin, profit after trade.
* **No actual trade.** Validation only - no order is placed.
* **Risk management.** Essential for position sizing and fund verification.

---

## 🎯 Purpose

Use this method when you need to:

* Verify sufficient funds before placing orders.
* Calculate required margin for a trade.
* Estimate balance/equity after trade execution.
* Validate trading parameters without risking actual trade.
* Build position sizing calculators.
* Check if your account can handle a specific trade size.

---

## 🔗 Usage Examples

### 1) Basic validation - check if order is possible

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiTradeFunctions;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Build trade request
            var tradeRequest = Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
                .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
                .setSymbol("EURUSD")
                .setVolume(1.0)  // 1 lot
                .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
                .setDeviation(10)
                .build();

            // Validate order
            var reply = account.orderCheck(tradeRequest);

            var result = reply.getData().getMqlTradeCheckResult();

            System.out.printf("Validation Result:%n");
            System.out.printf("Return Code: %d%n", result.getReturnedCode());
            System.out.printf("Required Margin: $%.2f%n", result.getMargin());
            System.out.printf("Free Margin After: $%.2f%n", result.getFreeMargin());
            System.out.printf("Margin Level After: %.2f%%%n", result.getMarginLevel());

            if (result.getReturnedCode() == 10009) {
                System.out.println("✅ Order can be placed!");
            } else {
                System.out.println("❌ Order rejected: " + result.getComment());
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Validate order with SL/TP

```java
import com.google.protobuf.Timestamp;

public class ValidateOrderWithStops {
    /**
     * Check if order with Stop Loss and Take Profit is valid
     */
    public static boolean validateOrder(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots,
            double stopLoss,
            double takeProfit) throws ApiExceptionMT5 {

        // Get current price
        var quote = account.symbolInfoTick(symbol);
        double entryPrice = isBuy ? quote.getData().getAsk() : quote.getData().getBid();

        // Build trade request with SL/TP
        var tradeRequest = Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
            .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
            .setSymbol(symbol)
            .setVolume(lots)
            .setOrderType(isBuy
                ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
                : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL)
            .setPrice(entryPrice)
            .setStopLoss(stopLoss)
            .setTakeProfit(takeProfit)
            .setDeviation(10)
            .build();

        // Validate
        var reply = account.orderCheck(tradeRequest);
        var result = reply.getData().getMqlTradeCheckResult();

        System.out.printf("\n%s %s %.2f lots:%n",
            isBuy ? "BUY" : "SELL", symbol, lots);
        System.out.println("═".repeat(50));
        System.out.printf("Entry: %.5f%n", entryPrice);
        System.out.printf("Stop Loss: %.5f%n", stopLoss);
        System.out.printf("Take Profit: %.5f%n", takeProfit);
        System.out.println("─".repeat(50));
        System.out.printf("Required Margin: $%.2f%n", result.getMargin());
        System.out.printf("Free Margin After: $%.2f%n", result.getFreeMargin());
        System.out.printf("Balance After: $%.2f%n", result.getBalanceAfterDeal());
        System.out.printf("Equity After: $%.2f%n", result.getEquityAfterDeal());
        System.out.printf("Margin Level: %.2f%%%n", result.getMarginLevel());
        System.out.println("═".repeat(50));

        boolean isValid = result.getReturnedCode() == 10009;

        if (isValid) {
            System.out.println("✅ Order valid - sufficient funds");
        } else {
            System.out.println("❌ Order invalid: " + result.getComment());
        }

        return isValid;
    }
}

// Usage
boolean canTrade = ValidateOrderWithStops.validateOrder(
    account, "EURUSD", true, 1.0, 1.09500, 1.11000
);
```

### 3) Calculate maximum lot size

```java
public class MaxLotSizeCalculator {
    /**
     * Calculate maximum lot size based on available margin
     */
    public static double calculateMaxLots(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double marginUsagePercent) throws ApiExceptionMT5 {

        // Get account info
        var accInfo = account.accountInformation();
        double freeMargin = accInfo.getData().getMargin();
        double maxMargin = freeMargin * (marginUsagePercent / 100.0);

        System.out.printf("\nCalculating max lot size for %s:%n", symbol);
        System.out.println("═".repeat(50));
        System.out.printf("Free Margin: $%.2f%n", freeMargin);
        System.out.printf("Max Usage: %.0f%% = $%.2f%n",
            marginUsagePercent, maxMargin);
        System.out.println("─".repeat(50));

        // Binary search for max lot size
        double minLots = 0.01;
        double maxLots = 100.0;
        double bestLots = 0.0;

        while (maxLots - minLots > 0.01) {
            double testLots = (minLots + maxLots) / 2.0;

            // Build request
            var tradeRequest = Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
                .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
                .setSymbol(symbol)
                .setVolume(testLots)
                .setOrderType(isBuy
                    ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
                    : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL)
                .build();

            // Check if valid
            var reply = account.orderCheck(tradeRequest);
            var result = reply.getData().getMqlTradeCheckResult();

            if (result.getReturnedCode() == 10009 &&
                result.getMargin() <= maxMargin) {
                bestLots = testLots;
                minLots = testLots;
            } else {
                maxLots = testLots;
            }
        }

        System.out.printf("Maximum Lot Size: %.2f lots%n", bestLots);

        // Get margin for best lot size
        var finalRequest = Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
            .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
            .setSymbol(symbol)
            .setVolume(bestLots)
            .setOrderType(isBuy
                ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
                : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL)
            .build();

        var finalReply = account.orderCheck(finalRequest);
        var finalResult = finalReply.getData().getMqlTradeCheckResult();

        System.out.printf("Required Margin: $%.2f%n", finalResult.getMargin());
        System.out.printf("Free Margin After: $%.2f%n", finalResult.getFreeMargin());
        System.out.printf("Margin Level After: %.2f%%%n", finalResult.getMarginLevel());
        System.out.println("═".repeat(50));

        return bestLots;
    }
}

// Usage - use 80% of available margin
double maxLots = MaxLotSizeCalculator.calculateMaxLots(
    account, "EURUSD", true, 80.0
);
```

### 4) Validate pending order

```java
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ValidatePendingOrder {
    /**
     * Validate pending order before placing
     */
    public static boolean validateBuyLimit(
            MT5Account account,
            String symbol,
            double lots,
            double limitPrice,
            double stopLoss,
            double takeProfit) throws ApiExceptionMT5 {

        // Set expiration to 24 hours
        Instant expiration = Instant.now().plus(24, ChronoUnit.HOURS);
        Timestamp expirationTs = Timestamp.newBuilder()
            .setSeconds(expiration.getEpochSecond())
            .build();

        // Build pending order request
        var tradeRequest = Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
            .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_PENDING)
            .setSymbol(symbol)
            .setVolume(lots)
            .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY_LIMIT)
            .setPrice(limitPrice)
            .setStopLoss(stopLoss)
            .setTakeProfit(takeProfit)
            .setTypeTime(Mt5TermApiTradeFunctions.MRPC_ENUM_ORDER_TYPE_TIME.ORDER_TIME_SPECIFIED)
            .setExpiration(expirationTs)
            .build();

        // Validate
        var reply = account.orderCheck(tradeRequest);
        var result = reply.getData().getMqlTradeCheckResult();

        System.out.printf("\nBUY LIMIT Validation:%n");
        System.out.println("═".repeat(50));
        System.out.printf("Symbol: %s%n", symbol);
        System.out.printf("Volume: %.2f lots%n", lots);
        System.out.printf("Limit Price: %.5f%n", limitPrice);
        System.out.printf("Stop Loss: %.5f%n", stopLoss);
        System.out.printf("Take Profit: %.5f%n", takeProfit);
        System.out.println("─".repeat(50));
        System.out.printf("Required Margin: $%.2f%n", result.getMargin());
        System.out.printf("Free Margin After: $%.2f%n", result.getFreeMargin());
        System.out.printf("Margin Level: %.2f%%%n", result.getMarginLevel());
        System.out.println("═".repeat(50));

        boolean isValid = result.getReturnedCode() == 10009;

        if (isValid) {
            System.out.println("✅ Pending order valid");
        } else {
            System.out.println("❌ Pending order invalid: " + result.getComment());
        }

        return isValid;
    }
}

// Usage
boolean canPlace = ValidatePendingOrder.validateBuyLimit(
    account, "EURUSD", 1.0, 1.09500, 1.09000, 1.10500
);
```

### 5) Batch validation for multiple symbols

```java
import java.util.*;

public class BatchOrderValidator {
    public record ValidationResult(
        String symbol,
        double lots,
        boolean isValid,
        double requiredMargin,
        double marginLevel,
        String comment
    ) {}

    /**
     * Validate multiple trading ideas at once
     */
    public static List<ValidationResult> validateMultipleOrders(
            MT5Account account,
            Map<String, Double> symbolsAndLots,
            boolean isBuy) throws ApiExceptionMT5 {

        List<ValidationResult> results = new ArrayList<>();

        System.out.println("\nBatch Order Validation:");
        System.out.println("═".repeat(80));
        System.out.printf("%-12s | %-8s | %-15s | %-12s | Status%n",
            "Symbol", "Lots", "Margin Req", "Margin Lvl");
        System.out.println("─".repeat(80));

        for (Map.Entry<String, Double> entry : symbolsAndLots.entrySet()) {
            String symbol = entry.getKey();
            double lots = entry.getValue();

            try {
                // Build request
                var tradeRequest = Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
                    .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
                    .setSymbol(symbol)
                    .setVolume(lots)
                    .setOrderType(isBuy
                        ? Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY
                        : Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_SELL)
                    .build();

                // Validate
                var reply = account.orderCheck(tradeRequest);
                var result = reply.getData().getMqlTradeCheckResult();

                boolean isValid = result.getReturnedCode() == 10009;
                String status = isValid ? "✅ Valid" : "❌ Invalid";

                System.out.printf("%-12s | %.2f | $%-14.2f | %9.2f%% | %s%n",
                    symbol, lots, result.getMargin(), result.getMarginLevel(), status);

                results.add(new ValidationResult(
                    symbol,
                    lots,
                    isValid,
                    result.getMargin(),
                    result.getMarginLevel(),
                    result.getComment()
                ));

            } catch (ApiExceptionMT5 e) {
                System.out.printf("%-12s | %.2f | Error: %s%n",
                    symbol, lots, e.getMessage());

                results.add(new ValidationResult(
                    symbol, lots, false, 0.0, 0.0, e.getMessage()
                ));
            }
        }

        System.out.println("═".repeat(80));

        // Summary
        long validCount = results.stream().filter(ValidationResult::isValid).count();
        double totalMargin = results.stream()
            .filter(ValidationResult::isValid)
            .mapToDouble(ValidationResult::requiredMargin)
            .sum();

        System.out.printf("\nSummary: %d/%d orders valid%n", validCount, results.size());
        System.out.printf("Total Required Margin: $%.2f%n", totalMargin);

        return results;
    }
}

// Usage
Map<String, Double> tradingIdeas = Map.of(
    "EURUSD", 1.0,
    "GBPUSD", 0.5,
    "USDJPY", 1.5,
    "AUDUSD", 0.75,
    "XAUUSD", 0.1
);

List<ValidationResult> results = BatchOrderValidator.validateMultipleOrders(
    account, tradingIdeas, true
);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Build trade request
Mt5TermApiTradeFunctions.MrpcMqlTradeRequest tradeRequest =
    Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
        .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
        .setSymbol("EURUSD")
        .setVolume(1.0)
        .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
        .setDeviation(10)
        .build();

// Build check request
Mt5TermApiTradeFunctions.OrderCheckRequest request =
    Mt5TermApiTradeFunctions.OrderCheckRequest.newBuilder()
        .setMqlTradeRequest(tradeRequest)
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiTradeFunctions.OrderCheckReply reply = tradeFunctionsClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .orderCheck(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Get validation result
var result = reply.getData().getMqlTradeCheckResult();
boolean isValid = result.getReturnedCode() == 10009;
```

---

## 📌 Important Notes

### Validation Results:

- `returned_code = 10009` means order is valid
- Other codes indicate errors (check `comment` field)
- Validation does NOT place actual order
- Results are estimates and may differ from actual execution

### Financial Calculations:

- `balance_after_deal` - estimated balance after trade
- `equity_after_deal` - estimated equity after trade
- `margin` - required margin for this trade
- `free_margin` - available margin after trade
- `margin_level` - (equity / margin) × 100
- `profit` - estimated floating profit

### Use Cases:

- Pre-trade validation before OrderSend
- Position sizing calculators
- Risk management checks
- Margin requirement calculations
- Portfolio analysis tools

### Margin Level:

- Above 100% = healthy account
- Below 100% = approaching margin call
- Below broker's stop-out level = positions may be closed

### Best Practices:

- Always validate before placing large orders
- Check margin level stays above 200%
- Factor in multiple open positions
- Use for risk management and position sizing
- Combine with OrderCalcMargin for precise calculations
- Validate all pending orders before placement

### Limitations:

- Does not account for slippage
- Does not include commission/swap
- Estimates may differ from actual execution
- Market conditions can change between check and order
- Server-side validation may have additional rules

### Common Return Codes:

- `10009` - TRADE_RETCODE_DONE - Valid
- `10019` - TRADE_RETCODE_NO_MONEY - Insufficient funds
- `10004` - TRADE_RETCODE_REJECT - Request rejected
- `10006` - TRADE_RETCODE_INVALID - Invalid request
- `10013` - TRADE_RETCODE_INVALID_VOLUME - Invalid volume
