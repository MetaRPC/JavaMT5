# ✅ Getting Symbol Margin Rates

> **Request:** retrieve the initial and maintenance margin rate multipliers for a symbol, for a specific order type. Use it to compute the exact margin reserved before placing an order.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolInfoMarginRate(...)` (from package `pro.mrpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolInfoMarginRate` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolInfoMarginRate(SymbolInfoMarginRateRequest) → SymbolInfoMarginRateReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolInfoMarginRate(request)`
* **SDK wrapper (high-level):**

```java
package pro.mrpc.mt5;

public class MT5Account {
    /**
     * Gets the margin rate multipliers for a symbol and order type.
     * Returns the margin multiplier applied for buy and sell orders, which varies based on order type.
     * Use this to calculate the exact margin requirement before placing orders.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param orderType Type of order (BUY, SELL, etc.)
     * @return Margin rate information including initial and maintenance margin multipliers
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoMarginRateReply symbolInfoMarginRate(
        String symbol,
        Mt5TermApiMarketInfo.ENUM_ORDER_TYPE orderType) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolInfoMarginRateRequest { symbol: string, order_type: ENUM_ORDER_TYPE }`

**Reply message:** `SymbolInfoMarginRateReply { data: SymbolInfoMarginRateData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter   | Type               | Required | Description                                        |
| ----------- | ------------------ | -------- | -------------------------------------------------- |
| `symbol`    | `String`           | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")             |
| `orderType` | `ENUM_ORDER_TYPE`  | ✅       | Order type the margin rate is requested for        |

---

## ⬆️ Output - `SymbolInfoMarginRateData`

| Field                     | Type     | Description                                                                                               |
| ------------------------- | -------- | --------------------------------------------------------------------------------------------------------- |
| `initial_margin_rate`     | `double` | Initial margin rate. Multiply by the initial margin to get the funds reserved when placing the order.     |
| `maintenance_margin_rate` | `double` | Maintenance margin rate. Multiply by the maintenance margin to get the funds reserved after activation.   |

Access using `reply.getData().getInitialMarginRate()` and `reply.getData().getMaintenanceMarginRate()`.

---

## 🧱 Related enums (from proto)

### `ENUM_ORDER_TYPE`

| Enum Value                   | Value | Description                                                                 |
| ---------------------------- | ----- | --------------------------------------------------------------------------- |
| `ORDER_TYPE_BUY`             | 0     | Market Buy order                                                            |
| `ORDER_TYPE_SELL`            | 1     | Market Sell order                                                           |
| `ORDER_TYPE_BUY_LIMIT`       | 2     | Buy Limit pending order                                                     |
| `ORDER_TYPE_SELL_LIMIT`      | 3     | Sell Limit pending order                                                    |
| `ORDER_TYPE_BUY_STOP`        | 4     | Buy Stop pending order                                                      |
| `ORDER_TYPE_SELL_STOP`       | 5     | Sell Stop pending order                                                     |
| `ORDER_TYPE_BUY_STOP_LIMIT`  | 6     | On reaching the order price, a Buy Limit is placed at the StopLimit price   |
| `ORDER_TYPE_SELL_STOP_LIMIT` | 7     | On reaching the order price, a Sell Limit is placed at the StopLimit price  |
| `ORDER_TYPE_CLOSE_BY`        | 8     | Order to close a position by an opposite one                                |

Accessed in Java as `Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.ORDER_TYPE_BUY`.

---

## 💬 Just the essentials

* **What it is.** A single RPC returning the initial and maintenance margin rate multipliers for a symbol and order type.
* **Why you need it.** To compute the exact margin reserved before sending an order, especially when it differs between buy/sell and pending types.
* **Sanity check.** Both rates are typically `1.0` for standard Forex symbols; values other than `1.0` indicate reduced/elevated margin for that order type.

---

## 🎯 Purpose

Use it to size positions against available margin:

* Pre-trade margin estimation per order type.
* Risk checks before placing large or pending orders.
* Comparing margin requirements across symbols/order types.

---

## 🧩 Notes & Tips

* The method uses automatic reconnection via `executeWithReconnect()` to handle transient errors.
* All gRPC exceptions are converted to `ApiExceptionMT5` for consistent error handling.
* Rates are multipliers, not currency amounts — multiply by the symbol's base margin to get the reserved funds.
* For the full margin amount, combine with `orderCalcMargin()`.

---

## 🔗 Usage Examples

### 1) Basic margin rate lookup

```java
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            Mt5TermApiMarketInfo.SymbolInfoMarginRateReply reply =
                account.symbolInfoMarginRate(
                    "EURUSD",
                    Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.ORDER_TYPE_BUY);

            Mt5TermApiMarketInfo.SymbolInfoMarginRateData data = reply.getData();
            System.out.printf("Initial rate:     %.4f%n", data.getInitialMarginRate());
            System.out.printf("Maintenance rate: %.4f%n", data.getMaintenanceMarginRate());

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Compare buy vs sell margin rates

```java
var buy = account.symbolInfoMarginRate(
        "XAUUSD", Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.ORDER_TYPE_BUY).getData();
var sell = account.symbolInfoMarginRate(
        "XAUUSD", Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.ORDER_TYPE_SELL).getData();

System.out.printf("XAUUSD buy initial:  %.4f%n", buy.getInitialMarginRate());
System.out.printf("XAUUSD sell initial: %.4f%n", sell.getInitialMarginRate());
```

### 3) Iterate over order types

```java
for (var type : Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.values()) {
    if (type == Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.UNRECOGNIZED) continue;
    var data = account.symbolInfoMarginRate("EURUSD", type).getData();
    System.out.printf("%-26s init=%.4f maint=%.4f%n",
        type.name(), data.getInitialMarginRate(), data.getMaintenanceMarginRate());
}
```

---

## 🔄 Low-level gRPC call (for reference)

If you need to call the gRPC service directly without the wrapper:

```java
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest request =
    Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest.newBuilder()
        .setSymbol("EURUSD")
        .setOrderType(Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.ORDER_TYPE_BUY)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolInfoMarginRateReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolInfoMarginRate(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
Mt5TermApiMarketInfo.SymbolInfoMarginRateData data = reply.getData();
```

---

## 📚 See also

* [SymbolInfoDouble.md](./SymbolInfoDouble.md) - Get single double property (margins, swaps, etc.)
* [SymbolInfoInteger.md](./SymbolInfoInteger.md) - Get single integer property
* [SymbolParamsMany.md](./SymbolParamsMany.md) - Get all symbol parameters at once
* [OrderCalcMargin.md](../5.%20Trading/OrderCalcMargin.md) - Calculate the full margin amount for a trade
