# 📤 Send Trading Order (Open Position / Place Pending Order)

> **Request:** send a trading order to MT5 server - open market position (BUY/SELL) or place pending order (LIMIT/STOP). The primary method for executing trades.

**API Information:**

* **SDK wrapper:** `MT5Account.orderSend(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.TradingHelper`
* **Proto definition:** `OrderSend` (defined in `mt5-term-api-trading-helper.proto`)

### RPC

* **Service:** `mt5_term_api.TradingHelper`
* **Method:** `OrderSend(OrderSendRequest) → OrderSendReply`
* **Low‑level client (generated):** `TradingHelperGrpc.TradingHelperBlockingStub.orderSend(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Sends a trading order to the MT5 server (market or pending order).
     * Use this method to open new positions or place pending orders with specified parameters
     * including symbol, volume, price, stop loss, and take profit levels.
     *
     * @param request Complete order request containing symbol, order type, volume, prices, and other parameters
     * @return Response with the order/deal ticket number if successful
     * @throws ApiExceptionMT5 if the order fails or connection is lost
     */
    public Mt5TermApiTradingHelper.OrderSendReply orderSend(
        Mt5TermApiTradingHelper.OrderSendRequest request) throws ApiExceptionMT5;
}
```

**Request message:** `OrderSendRequest { symbol, operation, volume, price?, slippage?, stop_loss?, take_profit?, comment?, expert_id?, stop_limit_price?, expiration_time_type?, expiration_time? }`
**Reply message:** `OrderSendReply { data: OrderSendData }` or `{ error: Error }`

---

## 🔽 Input — `OrderSendRequest`

| Parameter              | Type                          | Required | Description                                          |
| ---------------------- | ----------------------------- | -------- | ---------------------------------------------------- |
| `symbol`               | `String`                      | ✅       | Symbol name (e.g., "EURUSD")                         |
| `operation`            | `TMT5_ENUM_ORDER_TYPE`        | ✅       | Order type (BUY, SELL, BUY_LIMIT, etc.)              |
| `volume`               | `double`                      | ✅       | Volume in lots                                       |
| `price`                | `double`                      | ❌       | Order price (required for pending orders, optional for market) |
| `slippage`             | `long`                        | ❌       | Maximum price slippage in points                     |
| `stop_loss`            | `double`                      | ❌       | Stop Loss price level                                |
| `take_profit`          | `double`                      | ❌       | Take Profit price level                              |
| `comment`              | `String`                      | ❌       | Order comment (max 31 characters)                    |
| `expert_id`            | `long`                        | ❌       | Expert Advisor ID (magic number)                     |
| `stop_limit_price`     | `double`                      | ❌       | Stop Limit price (for STOP_LIMIT orders)             |
| `expiration_time_type` | `TMT5_ENUM_ORDER_TYPE_TIME`   | ❌       | Order expiration type                                |
| `expiration_time`      | `Timestamp`                   | ❌       | Expiration time (if type is SPECIFIED)               |

### Enum: `TMT5_ENUM_ORDER_TYPE` (Order Types)

| Value                          | Number | Description                                          |
| ------------------------------ | ------ | ---------------------------------------------------- |
| `TMT5_ORDER_TYPE_BUY`          | 0      | Market Buy order (instant execution)                 |
| `TMT5_ORDER_TYPE_SELL`         | 1      | Market Sell order (instant execution)                |
| `TMT5_ORDER_TYPE_BUY_LIMIT`    | 2      | Buy Limit pending order (buy below market)           |
| `TMT5_ORDER_TYPE_SELL_LIMIT`   | 3      | Sell Limit pending order (sell above market)         |
| `TMT5_ORDER_TYPE_BUY_STOP`     | 4      | Buy Stop pending order (buy above market)            |
| `TMT5_ORDER_TYPE_SELL_STOP`    | 5      | Sell Stop pending order (sell below market)          |
| `TMT5_ORDER_TYPE_BUY_STOP_LIMIT` | 6    | Buy Stop Limit order                                 |
| `TMT5_ORDER_TYPE_SELL_STOP_LIMIT` | 7   | Sell Stop Limit order                                |
| `TMT5_ORDER_TYPE_CLOSE_BY`     | 8      | Close position by opposite position                  |

### Enum: `TMT5_ENUM_ORDER_TYPE_TIME` (Time-in-Force)

| Value                            | Number | Description                                          |
| -------------------------------- | ------ | ---------------------------------------------------- |
| `TMT5_ORDER_TIME_GTC`            | 0      | Good Till Cancel (default)                           |
| `TMT5_ORDER_TIME_DAY`            | 1      | Good Till End of Trading Day                         |
| `TMT5_ORDER_TIME_SPECIFIED`      | 2      | Good Till Specified Time                             |
| `TMT5_ORDER_TIME_SPECIFIED_DAY`  | 3      | Good Till End of Specified Day (23:59:59)            |

---

## ⬆️ Output — `OrderSendData`

| Field                | Type     | Description                                          |
| -------------------- | -------- | ---------------------------------------------------- |
| `returned_code`      | `int`    | Operation return code (10009 = success)              |
| `deal`               | `long`   | Deal ticket number (if executed immediately)         |
| `order`              | `long`   | Order ticket number (for pending orders or positions) |
| `volume`             | `double` | Executed volume confirmed by broker                  |
| `price`              | `double` | Execution price confirmed by broker                  |
| `bid`                | `double` | Current Bid price at execution time                  |
| `ask`                | `double` | Current Ask price at execution time                  |
| `comment`            | `String` | Broker comment (error description if failed)         |
| `request_id`         | `int`    | Request ID set by terminal                           |
| `ret_code_external`  | `int`    | External trading system return code                  |

**Success Return Codes:**
- `10009` - TRADE_RETCODE_DONE - Request completed successfully
- `10008` - TRADE_RETCODE_PLACED - Order placed successfully

**Common Error Codes:**
- `10004` - TRADE_RETCODE_REJECT - Request rejected
- `10006` - TRADE_RETCODE_REQUOTE - Requote
- `10013` - TRADE_RETCODE_INVALID - Invalid request
- `10014` - TRADE_RETCODE_INVALID_VOLUME - Invalid volume
- `10015` - TRADE_RETCODE_INVALID_PRICE - Invalid price
- `10016` - TRADE_RETCODE_INVALID_STOPS - Invalid stops
- `10019` - TRADE_RETCODE_NO_MONEY - Not enough money

---

## 💬 Just the essentials

* **What it is.** Primary RPC for opening positions and placing pending orders.
* **Market orders.** Use BUY/SELL types - executed immediately at current price.
* **Pending orders.** Use LIMIT/STOP types - activated when price reaches level.
* **Return codes.** Check `returned_code` - 10009 means success.
* **Tickets.** Save `deal` or `order` ticket for tracking/modification/closure.
* **Slippage.** Maximum acceptable price deviation (in points, not pips).
* **Magic number.** Use `expert_id` to tag orders from your EA/strategy.

---

## 🎯 Purpose

Use this method when you need to:

* Open market positions (instant execution).
* Place pending orders (limit/stop orders).
* Set stop loss and take profit on order placement.
* Tag orders with magic numbers for strategy tracking.
* Control order expiration time.
* Execute automated trading strategies.

---

## 🧩 Notes & Tips

* **Required fields.** Symbol, operation, volume are mandatory.
* **Market vs Pending.** Market orders (BUY/SELL) execute immediately; pending orders wait for price.
* **Price field.** Optional for market orders (uses current price), required for pending orders.
* **Stop Loss/Take Profit.** Can be set on order placement or added later via `orderModify()`.
* **Slippage.** In points (not pips). For EURUSD: 10 points = 1 pip.
* **Magic number.** Use to identify orders from your trading system.
* **Return code.** Always check `returned_code` - 10009 = success.
* **Ticket numbers.** `deal` for market orders, `order` for pending orders.
* **Volume.** In standard lots (1.0 = 100,000 units for forex).
* **Auto-reconnect.** Uses `executeWithReconnect()` for reliability.

---

## 🔗 Usage Examples

### 1) Simple market BUY order

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiTradingHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Build market BUY order
            Mt5TermApiTradingHelper.OrderSendRequest request =
                Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                    .setSymbol("EURUSD")
                    .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
                    .setVolume(0.01)  // 0.01 lots (micro lot)
                    .setComment("Test buy order")
                    .build();

            // Send order
            Mt5TermApiTradingHelper.OrderSendReply reply =
                account.orderSend(request);

            var data = reply.getData();

            if (data.getReturnedCode() == 10009) {  // Success
                System.out.println("✅ Order executed successfully!");
                System.out.printf("Deal ticket: %d%n", data.getDeal());
                System.out.printf("Order ticket: %d%n", data.getOrder());
                System.out.printf("Execution price: %.5f%n", data.getPrice());
                System.out.printf("Volume: %.2f lots%n", data.getVolume());
            } else {
                System.out.printf("❌ Order failed: %s (code: %d)%n",
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

### 2) Market order with Stop Loss and Take Profit

```java
public class OrderWithProtection {
    /**
     * Place market order with SL/TP
     */
    public static long placeBuyWithProtection(
            MT5Account account,
            String symbol,
            double lots,
            double stopLossPrice,
            double takeProfitPrice) throws ApiExceptionMT5 {

        Mt5TermApiTradingHelper.OrderSendRequest request =
            Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
                .setVolume(lots)
                .setStopLoss(stopLossPrice)
                .setTakeProfit(takeProfitPrice)
                .setSlippage(10)  // 10 points slippage
                .setComment("Buy with SL/TP")
                .build();

        var reply = account.orderSend(request);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.printf("✅ BUY order placed: %s %.2f lots%n",
                symbol, lots);
            System.out.printf("   Entry: %.5f%n", data.getPrice());
            System.out.printf("   Stop Loss: %.5f%n", stopLossPrice);
            System.out.printf("   Take Profit: %.5f%n", takeProfitPrice);
            System.out.printf("   Ticket: %d%n", data.getOrder());

            return data.getOrder();
        } else {
            throw new ApiExceptionMT5(String.format(
                "Order failed: %s (code: %d)",
                data.getComment(), data.getReturnedCode()
            ));
        }
    }
}

// Usage
long ticket = OrderWithProtection.placeBuyWithProtection(
    account,
    "EURUSD",
    0.1,        // 0.1 lots
    1.09500,    // Stop Loss
    1.10500     // Take Profit
);
```

### 3) Buy Limit pending order

```java
public class PendingOrderPlacer {
    /**
     * Place Buy Limit order (buy below current price)
     */
    public static long placeBuyLimit(
            MT5Account account,
            String symbol,
            double lots,
            double limitPrice,
            double stopLoss,
            double takeProfit) throws ApiExceptionMT5 {

        Mt5TermApiTradingHelper.OrderSendRequest request =
            Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY_LIMIT)
                .setVolume(lots)
                .setPrice(limitPrice)  // Required for pending orders
                .setStopLoss(stopLoss)
                .setTakeProfit(takeProfit)
                .setComment("Buy Limit order")
                .build();

        var reply = account.orderSend(request);
        var data = reply.getData();

        if (data.getReturnedCode() == 10008 || data.getReturnedCode() == 10009) {
            System.out.printf("✅ BUY LIMIT order placed at %.5f%n", limitPrice);
            System.out.printf("   Ticket: %d%n", data.getOrder());
            return data.getOrder();
        } else {
            throw new ApiExceptionMT5(String.format(
                "Failed to place order: %s (code: %d)",
                data.getComment(), data.getReturnedCode()
            ));
        }
    }
}

// Usage - buy when price drops to 1.09000
long ticket = PendingOrderPlacer.placeBuyLimit(
    account,
    "EURUSD",
    0.1,
    1.09000,  // Limit price
    1.08500,  // Stop Loss
    1.09500   // Take Profit
);
```

### 4) Order with magic number (EA tagging)

```java
public class MagicNumberOrder {
    private static final long EA_MAGIC = 123456;

    /**
     * Place order with EA identification
     */
    public static long placeOrderWithMagic(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots) throws ApiExceptionMT5 {

        var orderType = isBuy
            ? Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY
            : Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL;

        Mt5TermApiTradingHelper.OrderSendRequest request =
            Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(orderType)
                .setVolume(lots)
                .setExpertId(EA_MAGIC)  // Tag with magic number
                .setComment("EA Order")
                .build();

        var reply = account.orderSend(request);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.printf("✅ %s order placed with magic %d%n",
                isBuy ? "BUY" : "SELL", EA_MAGIC);
            System.out.printf("   Ticket: %d%n", data.getOrder());
            return data.getOrder();
        } else {
            throw new ApiExceptionMT5(data.getComment());
        }
    }
}

// Usage
long ticket = MagicNumberOrder.placeOrderWithMagic(
    account, "EURUSD", true, 0.1
);
```

### 5) Order with expiration time

```java
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ExpiringOrder {
    /**
     * Place pending order with expiration
     */
    public static long placeBuyStopWithExpiration(
            MT5Account account,
            String symbol,
            double lots,
            double stopPrice,
            int expirationMinutes) throws ApiExceptionMT5 {

        // Calculate expiration time
        Instant expiry = Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES);
        Timestamp expiryTimestamp = Timestamp.newBuilder()
            .setSeconds(expiry.getEpochSecond())
            .build();

        Mt5TermApiTradingHelper.OrderSendRequest request =
            Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY_STOP)
                .setVolume(lots)
                .setPrice(stopPrice)
                .setExpirationTimeType(
                    Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE_TIME.TMT5_ORDER_TIME_SPECIFIED
                )
                .setExpirationTime(expiryTimestamp)
                .setComment(String.format("Expires in %d min", expirationMinutes))
                .build();

        var reply = account.orderSend(request);
        var data = reply.getData();

        if (data.getReturnedCode() == 10008 || data.getReturnedCode() == 10009) {
            System.out.printf("✅ BUY STOP at %.5f (expires: %s)%n",
                stopPrice, expiry);
            return data.getOrder();
        } else {
            throw new ApiExceptionMT5(data.getComment());
        }
    }
}

// Usage - order expires in 60 minutes
long ticket = ExpiringOrder.placeBuyStopWithExpiration(
    account, "EURUSD", 0.1, 1.10500, 60
);
```

### 6) Calculate SL/TP from pips

```java
public class OrderWithPipSLTP {
    /**
     * Place order with SL/TP in pips
     */
    public static long placeOrderWithPips(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots,
            int stopLossPips,
            int takeProfitPips) throws ApiExceptionMT5 {

        // Get current price
        var quote = account.symbolInfoTick(symbol);
        var tick = quote.getData();

        double entryPrice = isBuy ? tick.getAsk() : tick.getBid();

        // Calculate pip value (0.0001 for most pairs, 0.01 for JPY)
        double pipValue = symbol.contains("JPY") ? 0.01 : 0.0001;

        // Calculate SL/TP prices
        double stopLoss, takeProfit;
        if (isBuy) {
            stopLoss = entryPrice - (stopLossPips * pipValue);
            takeProfit = entryPrice + (takeProfitPips * pipValue);
        } else {
            stopLoss = entryPrice + (stopLossPips * pipValue);
            takeProfit = entryPrice - (takeProfitPips * pipValue);
        }

        // Place order
        var orderType = isBuy
            ? Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY
            : Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL;

        Mt5TermApiTradingHelper.OrderSendRequest request =
            Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol)
                .setOperation(orderType)
                .setVolume(lots)
                .setStopLoss(stopLoss)
                .setTakeProfit(takeProfit)
                .setSlippage(10)
                .setComment(String.format("SL:%d TP:%d pips", stopLossPips, takeProfitPips))
                .build();

        var reply = account.orderSend(request);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.printf("✅ %s %.2f lots %s%n",
                isBuy ? "BUY" : "SELL", lots, symbol);
            System.out.printf("   Entry: %.5f%n", data.getPrice());
            System.out.printf("   SL: %.5f (%d pips)%n", stopLoss, stopLossPips);
            System.out.printf("   TP: %.5f (%d pips)%n", takeProfit, takeProfitPips);
            return data.getOrder();
        } else {
            throw new ApiExceptionMT5(data.getComment());
        }
    }
}

// Usage - 50 pip SL, 100 pip TP
long ticket = OrderWithPipSLTP.placeOrderWithPips(
    account, "EURUSD", true, 0.1, 50, 100
);
```

### 7) Retry logic for order placement

```java
public class ReliableOrderPlacer {
    /**
     * Place order with retry on requote
     */
    public static long placeOrderWithRetry(
            MT5Account account,
            String symbol,
            boolean isBuy,
            double lots,
            int maxRetries) throws ApiExceptionMT5 {

        var orderType = isBuy
            ? Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY
            : Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            Mt5TermApiTradingHelper.OrderSendRequest request =
                Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
                    .setSymbol(symbol)
                    .setOperation(orderType)
                    .setVolume(lots)
                    .setSlippage(20)  // Allow more slippage
                    .setComment(String.format("Attempt %d", attempt))
                    .build();

            var reply = account.orderSend(request);
            var data = reply.getData();

            if (data.getReturnedCode() == 10009) {
                System.out.printf("✅ Order placed on attempt %d%n", attempt);
                return data.getOrder();
            }

            // Check if we should retry
            if (data.getReturnedCode() == 10006) {  // Requote
                System.out.printf("⚠️ Requote on attempt %d, retrying...%n", attempt);
                continue;
            }

            // Other error - don't retry
            throw new ApiExceptionMT5(String.format(
                "Order failed: %s (code: %d)",
                data.getComment(), data.getReturnedCode()
            ));
        }

        throw new ApiExceptionMT5("Failed after " + maxRetries + " attempts");
    }
}

// Usage
long ticket = ReliableOrderPlacer.placeOrderWithRetry(
    account, "EURUSD", true, 0.1, 3
);
```

### 8) Advanced order builder class

```java
public class OrderBuilder {
    private final MT5Account account;
    private String symbol;
    private Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE orderType;
    private double volume;
    private Double price;
    private Long slippage;
    private Double stopLoss;
    private Double takeProfit;
    private String comment;
    private Long expertId;

    public OrderBuilder(MT5Account account) {
        this.account = account;
    }

    public OrderBuilder symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public OrderBuilder buy(double lots) {
        this.orderType = Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY;
        this.volume = lots;
        return this;
    }

    public OrderBuilder sell(double lots) {
        this.orderType = Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL;
        this.volume = lots;
        return this;
    }

    public OrderBuilder buyLimit(double lots, double price) {
        this.orderType = Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY_LIMIT;
        this.volume = lots;
        this.price = price;
        return this;
    }

    public OrderBuilder sellLimit(double lots, double price) {
        this.orderType = Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_SELL_LIMIT;
        this.volume = lots;
        this.price = price;
        return this;
    }

    public OrderBuilder stopLoss(double price) {
        this.stopLoss = price;
        return this;
    }

    public OrderBuilder takeProfit(double price) {
        this.takeProfit = price;
        return this;
    }

    public OrderBuilder slippage(int points) {
        this.slippage = (long) points;
        return this;
    }

    public OrderBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public OrderBuilder magic(long magic) {
        this.expertId = magic;
        return this;
    }

    /**
     * Execute the order
     */
    public long execute() throws ApiExceptionMT5 {
        var builder = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
            .setSymbol(symbol)
            .setOperation(orderType)
            .setVolume(volume);

        if (price != null) builder.setPrice(price);
        if (slippage != null) builder.setSlippage(slippage);
        if (stopLoss != null) builder.setStopLoss(stopLoss);
        if (takeProfit != null) builder.setTakeProfit(takeProfit);
        if (comment != null) builder.setComment(comment);
        if (expertId != null) builder.setExpertId(expertId);

        var reply = account.orderSend(builder.build());
        var data = reply.getData();

        if (data.getReturnedCode() == 10009 || data.getReturnedCode() == 10008) {
            return data.getOrder();
        } else {
            throw new ApiExceptionMT5(String.format(
                "Order failed: %s (code: %d)",
                data.getComment(), data.getReturnedCode()
            ));
        }
    }
}

// Usage - fluent API
long ticket = new OrderBuilder(account)
    .symbol("EURUSD")
    .buy(0.1)
    .stopLoss(1.09500)
    .takeProfit(1.10500)
    .slippage(10)
    .magic(123456)
    .comment("Fluent order")
    .execute();
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Build request
Mt5TermApiTradingHelper.OrderSendRequest request =
    Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
        .setSymbol("EURUSD")
        .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
        .setVolume(0.1)
        .setStopLoss(1.09500)
        .setTakeProfit(1.10500)
        .setSlippage(10)
        .setComment("Test order")
        .build();

// Add metadata
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Send order
Mt5TermApiTradingHelper.OrderSendReply reply = tradingHelperClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .orderSend(request);

// Check result
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

var data = reply.getData();
if (data.getReturnedCode() == 10009) {
    System.out.printf("Success! Ticket: %d%n", data.getOrder());
}
```

---

## 📊 Order Types Guide

**Market Orders (Instant Execution):**
- **BUY** - Buy at current Ask price
- **SELL** - Sell at current Bid price

**Pending Orders (Activated at Price):**
- **BUY LIMIT** - Buy when price drops to limit (below current)
- **SELL LIMIT** - Sell when price rises to limit (above current)
- **BUY STOP** - Buy when price breaks above stop (above current)
- **SELL STOP** - Sell when price breaks below stop (below current)

**Slippage:**
- Measured in points (not pips)
- For EURUSD: 10 points = 1 pip
- 0 = no slippage allowed (may cause rejections)
- 10-50 typical for market orders

**Return Codes:**
- `10009` - DONE - Order executed
- `10008` - PLACED - Pending order placed
- `10004` - REJECT - Request rejected
- `10006` - REQUOTE - Price changed, retry
- `10019` - NO_MONEY - Insufficient funds
