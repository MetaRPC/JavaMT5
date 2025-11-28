# ✏️ Modify Order or Position

> **Request:** modify existing order or position parameters - update Stop Loss, Take Profit, price (for pending orders), or expiration time.

**API Information:**

* **SDK wrapper:** `MT5Account.orderModify(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.TradingHelper`
* **Proto definition:** `OrderModify` (defined in `mt5-term-api-trading-helper.proto`)

### RPC

* **Service:** `mt5_term_api.TradingHelper`
* **Method:** `OrderModify(OrderModifyRequest) → OrderModifyReply`
* **Low‑level client (generated):** `TradingHelperGrpc.TradingHelperBlockingStub.orderModify(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Modifies an existing order or position parameters.
     * Use this to update stop loss, take profit, price, or other parameters of an open position or pending order.
     *
     * @param request Modification request with order ticket and new parameters to update
     * @return Response indicating success or failure of the modification
     * @throws ApiExceptionMT5 if the modification fails or connection is lost
     */
    public Mt5TermApiTradingHelper.OrderModifyReply orderModify(
        Mt5TermApiTradingHelper.OrderModifyRequest request) throws ApiExceptionMT5;
}
```

**Request message:** `OrderModifyRequest { ticket, stop_loss?, take_profit?, price?, expiration_time_type?, expiration_time?, stop_limit? }`
**Reply message:** `OrderModifyReply { data: OrderModifyData }` or `{ error: Error }`

---

## 🔽 Input — `OrderModifyRequest`

| Parameter              | Type                          | Required | Description                                          |
| ---------------------- | ----------------------------- | -------- | ---------------------------------------------------- |
| `ticket`               | `long`                        | ✅       | Order or position ticket number                      |
| `stop_loss`            | `double`                      | ❌       | New Stop Loss price level                            |
| `take_profit`          | `double`                      | ❌       | New Take Profit price level                          |
| `price`                | `double`                      | ❌       | New order price (pending orders only)                |
| `expiration_time_type` | `TMT5_ENUM_ORDER_TYPE_TIME`   | ❌       | New expiration type (pending orders only)            |
| `expiration_time`      | `Timestamp`                   | ❌       | New expiration time (pending orders only)            |
| `stop_limit`           | `double`                      | ❌       | New stop limit price (stop-limit orders only)        |

**Important:**
- `ticket` is the only required field
- At least one optional parameter must be provided
- `price`, `expiration_time_type`, `expiration_time`, `stop_limit` - only for pending orders
- `stop_loss`, `take_profit` - for both positions and pending orders

### Enum: `TMT5_ENUM_ORDER_TYPE_TIME` (Time-in-Force)

| Value                            | Number | Description                                          |
| -------------------------------- | ------ | ---------------------------------------------------- |
| `TMT5_ORDER_TIME_GTC`            | 0      | Good Till Cancel (default)                           |
| `TMT5_ORDER_TIME_DAY`            | 1      | Good Till End of Trading Day                         |
| `TMT5_ORDER_TIME_SPECIFIED`      | 2      | Good Till Specified Time                             |
| `TMT5_ORDER_TIME_SPECIFIED_DAY`  | 3      | Good Till End of Specified Day (23:59:59)            |

---

## ⬆️ Output — `OrderModifyData`

| Field           | Type     | Description                                          |
| --------------- | -------- | ---------------------------------------------------- |
| `returned_code` | `int`    | Operation return code (10009 = success)              |
| `deal`          | `long`   | Deal ticket (usually 0 for modify operations)        |
| `order`         | `long`   | Order ticket that was modified                       |
| `volume`        | `double` | Order volume                                         |
| `price`         | `double` | Current/modified price                               |
| `bid`           | `double` | Current Bid price                                    |
| `ask`           | `double` | Current Ask price                                    |
| `comment`       | `String` | Broker comment (error description if failed)         |
| `request_id`    | `int`    | Request ID                                           |

**Success Code:**
- `10009` - TRADE_RETCODE_DONE - Modification successful

**Common Error Codes:**
- `10004` - TRADE_RETCODE_REJECT - Request rejected
- `10016` - TRADE_RETCODE_INVALID_STOPS - Invalid stop levels
- `10025` - TRADE_RETCODE_INVALID_FILL - Invalid fill type
- `10027` - TRADE_RETCODE_ERROR_ORDER - Order not found

---

## 💬 Just the essentials

* **What it is.** RPC to modify existing orders or positions.
* **For positions.** Update Stop Loss and Take Profit only.
* **For pending orders.** Can also modify price, expiration, stop limit.
* **At least one change.** Must provide at least one parameter to modify.
* **Return code.** Check `returned_code` - 10009 means success.
* **No partial changes.** If modification fails, no changes are applied.
* **Use cases.** Trailing stops, breakeven stops, TP adjustment, order price updates.

---

## 🎯 Purpose

Use this method when you need to:

* Add or update Stop Loss on an open position.
* Add or update Take Profit on an open position.
* Implement trailing stop logic.
* Move stops to breakeven after profit target.
* Adjust pending order prices.
* Update order expiration times.
* Protect profits by moving stops.

---

## 🧩 Notes & Tips

* **Ticket required.** Must provide valid ticket number of order or position.
* **Positions.** Can only modify SL/TP for open positions.
* **Pending orders.** Can modify price, SL/TP, expiration, and stop limit.
* **Stop levels.** Broker enforces minimum distance from current price.
* **Zero to remove.** Set SL or TP to 0.0 to remove protection.
* **Price validation.** Broker validates stop levels based on symbol properties.
* **Return code.** Always check `returned_code` for success (10009).
* **Auto-reconnect.** Uses `executeWithReconnect()` for reliability.

---

## 🔗 Usage Examples

### 1) Add Stop Loss to position

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiTradingHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            long positionTicket = 123456789;  // Your position ticket

            // Add Stop Loss
            Mt5TermApiTradingHelper.OrderModifyRequest request =
                Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                    .setTicket(positionTicket)
                    .setStopLoss(1.09500)
                    .build();

            Mt5TermApiTradingHelper.OrderModifyReply reply =
                account.orderModify(request);

            var data = reply.getData();

            if (data.getReturnedCode() == 10009) {
                System.out.printf("✅ Stop Loss set to %.5f%n", 1.09500);
            } else {
                System.out.printf("❌ Modification failed: %s (code: %d)%n",
                    data.getComment(), data.getReturnedCode());
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Update both SL and TP

```java
public class StopModifier {
    /**
     * Update Stop Loss and Take Profit
     */
    public static boolean updateStops(
            MT5Account account,
            long ticket,
            double newStopLoss,
            double newTakeProfit) throws ApiExceptionMT5 {

        Mt5TermApiTradingHelper.OrderModifyRequest request =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket)
                .setStopLoss(newStopLoss)
                .setTakeProfit(newTakeProfit)
                .build();

        var reply = account.orderModify(request);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.printf("✅ Updated stops for ticket #%d%n", ticket);
            System.out.printf("   Stop Loss: %.5f%n", newStopLoss);
            System.out.printf("   Take Profit: %.5f%n", newTakeProfit);
            return true;
        } else {
            System.out.printf("❌ Failed: %s (code: %d)%n",
                data.getComment(), data.getReturnedCode());
            return false;
        }
    }
}

// Usage
boolean success = StopModifier.updateStops(
    account,
    123456789,  // Ticket
    1.09500,    // New SL
    1.10500     // New TP
);
```

### 3) Trailing stop implementation

```java
public class TrailingStop {
    /**
     * Implement trailing stop logic
     */
    public static void trailStop(
            MT5Account account,
            long ticket,
            boolean isBuy,
            int trailDistancePips) throws ApiExceptionMT5 {

        // Get current position info
        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var position = positions.stream()
            .filter(p -> p.getTicket() == ticket)
            .findFirst()
            .orElseThrow(() -> new ApiExceptionMT5("Position not found"));

        double currentPrice = position.getPriceCurrent();
        double currentSL = position.getStopLoss();
        String symbol = position.getSymbol();

        // Calculate pip value
        double pipValue = symbol.contains("JPY") ? 0.01 : 0.0001;
        double trailDistance = trailDistancePips * pipValue;

        // Calculate new SL
        double newSL;
        if (isBuy) {
            newSL = currentPrice - trailDistance;
            // Only move SL up, never down
            if (currentSL > 0 && newSL <= currentSL) {
                System.out.println("SL already optimal, no update needed");
                return;
            }
        } else {
            newSL = currentPrice + trailDistance;
            // Only move SL down, never up
            if (currentSL > 0 && newSL >= currentSL) {
                System.out.println("SL already optimal, no update needed");
                return;
            }
        }

        // Update SL
        Mt5TermApiTradingHelper.OrderModifyRequest request =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket)
                .setStopLoss(newSL)
                .build();

        var reply = account.orderModify(request);

        if (reply.getData().getReturnedCode() == 10009) {
            System.out.printf("✅ Trailing stop updated: %.5f → %.5f%n",
                currentSL, newSL);
        } else {
            System.out.printf("❌ Failed to update: %s%n",
                reply.getData().getComment());
        }
    }
}

// Usage - trail stop 50 pips behind current price
TrailingStop.trailStop(account, 123456789, true, 50);
```

### 4) Move stop to breakeven

```java
public class BreakevenStop {
    /**
     * Move stop loss to breakeven when profit threshold reached
     */
    public static boolean moveToBreakeven(
            MT5Account account,
            long ticket,
            boolean isBuy,
            int profitThresholdPips,
            int breakevenOffsetPips) throws ApiExceptionMT5 {

        // Get position
        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var position = positions.stream()
            .filter(p -> p.getTicket() == ticket)
            .findFirst()
            .orElseThrow(() -> new ApiExceptionMT5("Position not found"));

        String symbol = position.getSymbol();
        double openPrice = position.getPriceOpen();
        double currentPrice = position.getPriceCurrent();
        double currentSL = position.getStopLoss();

        // Calculate pip value
        double pipValue = symbol.contains("JPY") ? 0.01 : 0.0001;

        // Calculate profit in pips
        double profitPips;
        if (isBuy) {
            profitPips = (currentPrice - openPrice) / pipValue;
        } else {
            profitPips = (openPrice - currentPrice) / pipValue;
        }

        System.out.printf("Current profit: %.1f pips%n", profitPips);

        // Check if threshold reached
        if (profitPips < profitThresholdPips) {
            System.out.printf("Threshold not reached (need %.1f pips)%n",
                (double) profitThresholdPips);
            return false;
        }

        // Calculate breakeven price with offset
        double breakevenPrice = openPrice + (breakevenOffsetPips * pipValue * (isBuy ? 1 : -1));

        // Check if already at breakeven or better
        if (isBuy && currentSL >= breakevenPrice) {
            System.out.println("Already at breakeven or better");
            return false;
        }
        if (!isBuy && currentSL > 0 && currentSL <= breakevenPrice) {
            System.out.println("Already at breakeven or better");
            return false;
        }

        // Move to breakeven
        Mt5TermApiTradingHelper.OrderModifyRequest request =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket)
                .setStopLoss(breakevenPrice)
                .build();

        var reply = account.orderModify(request);

        if (reply.getData().getReturnedCode() == 10009) {
            System.out.printf("✅ Moved to breakeven: %.5f%n", breakevenPrice);
            return true;
        } else {
            System.out.printf("❌ Failed: %s%n", reply.getData().getComment());
            return false;
        }
    }
}

// Usage - move to breakeven when 20 pips profit, with +2 pip offset
boolean moved = BreakevenStop.moveToBreakeven(
    account, 123456789, true, 20, 2
);
```

### 5) Modify pending order price

```java
public class PendingOrderModifier {
    /**
     * Update pending order price
     */
    public static boolean updateOrderPrice(
            MT5Account account,
            long orderTicket,
            double newPrice) throws ApiExceptionMT5 {

        Mt5TermApiTradingHelper.OrderModifyRequest request =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(orderTicket)
                .setPrice(newPrice)
                .build();

        var reply = account.orderModify(request);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.printf("✅ Order price updated to %.5f%n", newPrice);
            return true;
        } else {
            System.out.printf("❌ Failed: %s (code: %d)%n",
                data.getComment(), data.getReturnedCode());
            return false;
        }
    }

    /**
     * Update pending order with new price and stops
     */
    public static boolean updatePendingOrder(
            MT5Account account,
            long orderTicket,
            double newPrice,
            double newSL,
            double newTP) throws ApiExceptionMT5 {

        var builder = Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
            .setTicket(orderTicket)
            .setPrice(newPrice);

        if (newSL > 0) builder.setStopLoss(newSL);
        if (newTP > 0) builder.setTakeProfit(newTP);

        var reply = account.orderModify(builder.build());
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.printf("✅ Pending order updated%n");
            System.out.printf("   Price: %.5f%n", newPrice);
            if (newSL > 0) System.out.printf("   SL: %.5f%n", newSL);
            if (newTP > 0) System.out.printf("   TP: %.5f%n", newTP);
            return true;
        } else {
            System.out.printf("❌ Failed: %s%n", data.getComment());
            return false;
        }
    }
}

// Usage
PendingOrderModifier.updateOrderPrice(account, 123456, 1.10000);
```

### 6) Remove Stop Loss or Take Profit

```java
public class StopRemover {
    /**
     * Remove Stop Loss (set to 0)
     */
    public static boolean removeStopLoss(
            MT5Account account,
            long ticket) throws ApiExceptionMT5 {

        Mt5TermApiTradingHelper.OrderModifyRequest request =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket)
                .setStopLoss(0.0)  // 0 removes SL
                .build();

        var reply = account.orderModify(request);

        if (reply.getData().getReturnedCode() == 10009) {
            System.out.println("✅ Stop Loss removed");
            return true;
        } else {
            System.out.printf("❌ Failed: %s%n",
                reply.getData().getComment());
            return false;
        }
    }

    /**
     * Remove Take Profit (set to 0)
     */
    public static boolean removeTakeProfit(
            MT5Account account,
            long ticket) throws ApiExceptionMT5 {

        Mt5TermApiTradingHelper.OrderModifyRequest request =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket)
                .setTakeProfit(0.0)  // 0 removes TP
                .build();

        var reply = account.orderModify(request);

        if (reply.getData().getReturnedCode() == 10009) {
            System.out.println("✅ Take Profit removed");
            return true;
        } else {
            System.out.printf("❌ Failed: %s%n",
                reply.getData().getComment());
            return false;
        }
    }

    /**
     * Remove both SL and TP
     */
    public static boolean removeAllStops(
            MT5Account account,
            long ticket) throws ApiExceptionMT5 {

        Mt5TermApiTradingHelper.OrderModifyRequest request =
            Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                .setTicket(ticket)
                .setStopLoss(0.0)
                .setTakeProfit(0.0)
                .build();

        var reply = account.orderModify(request);

        if (reply.getData().getReturnedCode() == 10009) {
            System.out.println("✅ All stops removed");
            return true;
        } else {
            System.out.printf("❌ Failed: %s%n",
                reply.getData().getComment());
            return false;
        }
    }
}

// Usage
StopRemover.removeStopLoss(account, 123456789);
```

### 7) Batch modify multiple positions

```java
import java.util.*;

public class BatchModifier {
    /**
     * Modify SL for multiple positions
     */
    public static Map<Long, Boolean> batchUpdateStopLoss(
            MT5Account account,
            Map<Long, Double> ticketToSL) {

        Map<Long, Boolean> results = new LinkedHashMap<>();

        System.out.printf("Updating %d positions...%n", ticketToSL.size());
        System.out.println("═".repeat(50));

        for (var entry : ticketToSL.entrySet()) {
            long ticket = entry.getKey();
            double stopLoss = entry.getValue();

            try {
                Mt5TermApiTradingHelper.OrderModifyRequest request =
                    Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
                        .setTicket(ticket)
                        .setStopLoss(stopLoss)
                        .build();

                var reply = account.orderModify(request);
                boolean success = reply.getData().getReturnedCode() == 10009;
                results.put(ticket, success);

                String status = success ? "✅" : "❌";
                System.out.printf("%s Ticket #%d: SL %.5f%n",
                    status, ticket, stopLoss);

            } catch (ApiExceptionMT5 e) {
                results.put(ticket, false);
                System.out.printf("❌ Ticket #%d: %s%n", ticket, e.getMessage());
            }
        }

        long successCount = results.values().stream()
            .filter(success -> success)
            .count();

        System.out.println("═".repeat(50));
        System.out.printf("Success: %d/%d%n", successCount, ticketToSL.size());

        return results;
    }
}

// Usage
Map<Long, Double> updates = Map.of(
    123456L, 1.09500,
    789012L, 1.29500,
    345678L, 140.500
);

var results = BatchModifier.batchUpdateStopLoss(account, updates);
```

### 8) Smart stop modifier with validation

```java
public class SmartStopModifier {
    /**
     * Modify stops with validation and error handling
     */
    public static boolean modifyStopsWithValidation(
            MT5Account account,
            long ticket,
            Double newSL,
            Double newTP) throws ApiExceptionMT5 {

        // Get current position info
        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var position = positions.stream()
            .filter(p -> p.getTicket() == ticket)
            .findFirst()
            .orElseThrow(() -> new ApiExceptionMT5("Position not found: " + ticket));

        String symbol = position.getSymbol();
        double currentPrice = position.getPriceCurrent();
        boolean isBuy = position.getType() ==
            Mt5TermApiAccountHelper.BMT5_ENUM_POSITION_TYPE.BMT5_POSITION_TYPE_BUY;

        // Get symbol info for validation
        var symbolInfo = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_TRADE_STOPS_LEVEL
        );
        double stopLevel = symbolInfo.getData().getRequestedValue() * 0.00001;  // Convert to price

        System.out.printf("Modifying position #%d (%s %s)%n",
            ticket, isBuy ? "BUY" : "SELL", symbol);
        System.out.printf("Current price: %.5f%n", currentPrice);
        System.out.printf("Min stop distance: %.5f%n", stopLevel);

        // Build request
        var builder = Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
            .setTicket(ticket);

        boolean hasChanges = false;

        if (newSL != null && newSL > 0) {
            // Validate SL distance
            double slDistance = Math.abs(currentPrice - newSL);
            if (slDistance < stopLevel) {
                System.out.printf("⚠️ Stop Loss too close (%.5f < %.5f)%n",
                    slDistance, stopLevel);
                return false;
            }
            builder.setStopLoss(newSL);
            hasChanges = true;
            System.out.printf("New SL: %.5f%n", newSL);
        }

        if (newTP != null && newTP > 0) {
            // Validate TP distance
            double tpDistance = Math.abs(currentPrice - newTP);
            if (tpDistance < stopLevel) {
                System.out.printf("⚠️ Take Profit too close (%.5f < %.5f)%n",
                    tpDistance, stopLevel);
                return false;
            }
            builder.setTakeProfit(newTP);
            hasChanges = true;
            System.out.printf("New TP: %.5f%n", newTP);
        }

        if (!hasChanges) {
            System.out.println("No changes to apply");
            return false;
        }

        // Execute modification
        var reply = account.orderModify(builder.build());
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.println("✅ Modification successful");
            return true;
        } else {
            System.out.printf("❌ Failed: %s (code: %d)%n",
                data.getComment(), data.getReturnedCode());
            return false;
        }
    }
}

// Usage
boolean success = SmartStopModifier.modifyStopsWithValidation(
    account,
    123456789,
    1.09500,  // New SL
    1.10500   // New TP
);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Build request
Mt5TermApiTradingHelper.OrderModifyRequest request =
    Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
        .setTicket(123456789)
        .setStopLoss(1.09500)
        .setTakeProfit(1.10500)
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Modify order
Mt5TermApiTradingHelper.OrderModifyReply reply = tradingHelperClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .orderModify(request);

// Check result
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

if (reply.getData().getReturnedCode() == 10009) {
    System.out.println("Modification successful");
}
```

---

## 📌 Important Notes

**What Can Be Modified:**

**Open Positions:**
- ✅ Stop Loss
- ✅ Take Profit
- ❌ Price (cannot change entry price)
- ❌ Volume (use partial close instead)

**Pending Orders:**
- ✅ Stop Loss
- ✅ Take Profit
- ✅ Order Price
- ✅ Stop Limit Price
- ✅ Expiration Time
- ❌ Order Type (delete and create new instead)

**Stop Level Requirements:**
- Broker enforces minimum distance from current price
- Check `SYMBOL_TRADE_STOPS_LEVEL` via `symbolInfoDouble()`
- Too-close stops will be rejected with code 10016

**Removing Stops:**
- Set SL or TP to `0.0` to remove
- Useful for manual management or special strategies

**Best Practices:**
- Always validate stop distances before modification
- Check return code for success (10009)
- Handle errors gracefully (log and retry if needed)
- Use trailing stops for trend-following strategies
- Move to breakeven to protect profits
