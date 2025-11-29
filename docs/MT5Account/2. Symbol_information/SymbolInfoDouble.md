# ✅ Getting Symbol Double Properties

> **Request:** retrieve specific double-precision properties of a trading symbol. Access prices, spreads, swaps, volumes, margins, and other numeric symbol characteristics.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolInfoDouble(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolInfoDouble` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolInfoDouble(SymbolInfoDoubleRequest) → SymbolInfoDoubleReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolInfoDouble(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets a specific double-precision property of a trading symbol.
     * Returns numeric values such as bid/ask prices, point size, swap rates, volume limits, margins, etc.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param property Property type to retrieve (e.g., SYMBOL_BID, SYMBOL_POINT, SYMBOL_SWAP_LONG)
     * @return Property value as double
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoDoubleReply symbolInfoDouble(
        String symbol,
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty property) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolInfoDoubleRequest { symbol: string, type: SymbolInfoDoubleProperty }`

**Reply message:** `SymbolInfoDoubleReply { data: SymbolInfoDoubleData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter  | Type                          | Required | Description                                      |
| ---------- | ----------------------------- | -------- | ------------------------------------------------ |
| `symbol`   | `String`                      | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")           |
| `property` | `SymbolInfoDoubleProperty`    | ✅       | Property to retrieve (see enum below)            |

---

## ⬆️ Output - `SymbolInfoDoubleData`

| Field   | Type     | Description                      |
| ------- | -------- | -------------------------------- |
| `value` | `double` | The requested property value     |

Access using `reply.getData().getValue()`.

---

## 🧱 Related enums (from proto)

### `SymbolInfoDoubleProperty`

**Price Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_BID` | 0 | Current Bid price (sell price) |
| `SYMBOL_ASK` | 3 | Current Ask price (buy price) |
| `SYMBOL_LAST` | 6 | Last deal price |
| `SYMBOL_BIDHIGH` | 1 | Maximum Bid of the day |
| `SYMBOL_BIDLOW` | 2 | Minimum Bid of the day |
| `SYMBOL_ASKHIGH` | 4 | Maximum Ask of the day |
| `SYMBOL_ASKLOW` | 5 | Minimum Ask of the day |
| `SYMBOL_LASTHIGH` | 7 | Maximum Last price of the day |
| `SYMBOL_LASTLOW` | 8 | Minimum Last price of the day |

**Volume Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_VOLUME_MIN` | 22 | Minimum volume for a deal (lots) |
| `SYMBOL_VOLUME_MAX` | 23 | Maximum volume for a deal (lots) |
| `SYMBOL_VOLUME_STEP` | 24 | Volume change step (lots) |
| `SYMBOL_VOLUME_LIMIT` | 25 | Maximum aggregate volume in one direction |
| `SYMBOL_VOLUME_REAL` | 9 | Volume of the last deal |
| `SYMBOL_VOLUMEHIGH_REAL` | 10 | Maximum volume of the day |
| `SYMBOL_VOLUMELOW_REAL` | 11 | Minimum volume of the day |

**Trading Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_POINT` | 13 | Point value (e.g., 0.00001 for EURUSD) |
| `SYMBOL_TRADE_TICK_VALUE` | 14 | Tick value |
| `SYMBOL_TRADE_TICK_VALUE_PROFIT` | 15 | Tick value for profitable position |
| `SYMBOL_TRADE_TICK_VALUE_LOSS` | 16 | Tick value for losing position |
| `SYMBOL_TRADE_TICK_SIZE` | 17 | Minimum price change |
| `SYMBOL_TRADE_CONTRACT_SIZE` | 18 | Contract size (e.g., 100000 for 1 lot EURUSD) |

**Swap Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_SWAP_LONG` | 26 | Swap for long positions |
| `SYMBOL_SWAP_SHORT` | 27 | Swap for short positions |
| `SYMBOL_SWAP_SUNDAY` | 28 | Swap multiplier for Sunday rollover |
| `SYMBOL_SWAP_MONDAY` | 29 | Swap multiplier for Monday rollover |
| `SYMBOL_SWAP_TUESDAY` | 30 | Swap multiplier for Tuesday rollover |
| `SYMBOL_SWAP_WEDNESDAY` | 31 | Swap multiplier for Wednesday rollover |
| `SYMBOL_SWAP_THURSDAY` | 32 | Swap multiplier for Thursday rollover |
| `SYMBOL_SWAP_FRIDAY` | 33 | Swap multiplier for Friday rollover |
| `SYMBOL_SWAP_SATURDAY` | 34 | Swap multiplier for Saturday rollover |

**Margin Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_MARGIN_INITIAL` | 35 | Initial margin for 1 lot |
| `SYMBOL_MARGIN_MAINTENANCE` | 36 | Maintenance margin |
| `SYMBOL_MARGIN_HEDGED` | 48 | Margin for hedged positions |

**Session Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_SESSION_VOLUME` | 37 | Session total volume |
| `SYMBOL_SESSION_TURNOVER` | 38 | Session turnover |
| `SYMBOL_SESSION_INTEREST` | 39 | Open interest |
| `SYMBOL_SESSION_BUY_ORDERS_VOLUME` | 40 | Buy orders volume |
| `SYMBOL_SESSION_SELL_ORDERS_VOLUME` | 41 | Sell orders volume |
| `SYMBOL_SESSION_OPEN` | 42 | Session open price |
| `SYMBOL_SESSION_CLOSE` | 43 | Session close price |
| `SYMBOL_SESSION_AW` | 44 | Average weighted price |
| `SYMBOL_SESSION_PRICE_SETTLEMENT` | 45 | Settlement price |
| `SYMBOL_SESSION_PRICE_LIMIT_MIN` | 46 | Session minimum price limit |
| `SYMBOL_SESSION_PRICE_LIMIT_MAX` | 47 | Session maximum price limit |

**Price Change Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_PRICE_CHANGE` | 49 | Price change % from previous day close |
| `SYMBOL_PRICE_VOLATILITY` | 50 | Price volatility % |

**Options Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_OPTION_STRIKE` | 12 | Option strike price |
| `SYMBOL_PRICE_THEORETICAL` | 51 | Theoretical option price |
| `SYMBOL_PRICE_DELTA` | 52 | Option delta |
| `SYMBOL_PRICE_THETA` | 53 | Option theta |
| `SYMBOL_PRICE_GAMMA` | 54 | Option gamma |
| `SYMBOL_PRICE_VEGA` | 55 | Option vega |
| `SYMBOL_PRICE_RHO` | 56 | Option rho |
| `SYMBOL_PRICE_OMEGA` | 57 | Option omega (elasticity) |
| `SYMBOL_PRICE_SENSITIVITY` | 58 | Option sensitivity |

**Other Properties:**

| Enum Value | Value | Description |
|------------|-------|-------------|
| `SYMBOL_TRADE_ACCRUED_INTEREST` | 19 | Accrued interest (bonds) |
| `SYMBOL_TRADE_FACE_VALUE` | 20 | Face value (bonds) |
| `SYMBOL_TRADE_LIQUIDITY_RATE` | 21 | Liquidity rate for margin |

---

## 💬 Just the essentials

* **What it is.** RPC to get individual numeric properties of a symbol.
* **Why you need it.** Essential for calculating lot sizes, spreads, swaps, margins before trading.
* **Performance.** Single property per call - use `symbolInfoTick()` for bulk price data.
* **Precision.** Returns raw double values - format appropriately for display.

---

## 🎯 Purpose

Use this method when you need to:

* Get point size for spread/SL/TP calculations.
* Check swap rates before holding overnight positions.
* Validate volume limits before placing orders.
* Calculate required margin for position sizes.
* Get contract size for lot calculations.

---

## 🧩 Notes & Tips

* Symbol must be selected in MarketWatch for accurate data.
* Use `SYMBOL_POINT` for converting points to price units.
* `SYMBOL_TRADE_CONTRACT_SIZE` is typically 100,000 for Forex pairs.
* Swap values can be negative (cost) or positive (credit).
* The method uses automatic reconnection via `executeWithReconnect()`.

---

## 🔗 Usage Examples

### 1) Get point size

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get point size
            var reply = account.symbolInfoDouble(
                "EURUSD",
                Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT
            );
            double point = reply.getData().getValue();

            System.out.printf("EURUSD point size: %.5f%n", point);
            // Output: 0.00001 (1 pip for 5-digit broker)

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Calculate spread in points

```java
// Get Bid and Ask
var bidReply = account.symbolInfoDouble(
    "GBPUSD",
    Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_BID
);
var askReply = account.symbolInfoDouble(
    "GBPUSD",
    Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_ASK
);
var pointReply = account.symbolInfoDouble(
    "GBPUSD",
    Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT
);

double bid = bidReply.getData().getValue();
double ask = askReply.getData().getValue();
double point = pointReply.getData().getValue();

double spreadPrice = ask - bid;
double spreadPoints = spreadPrice / point;

System.out.printf("GBPUSD Spread: %.1f points (%.5f)%n", spreadPoints, spreadPrice);
```

### 3) Check swap rates

```java
public class SwapChecker {
    public static void checkSwap(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var longReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_LONG
        );
        var shortReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_SHORT
        );

        double swapLong = longReply.getData().getValue();
        double swapShort = shortReply.getData().getValue();

        System.out.printf("=== %s Swap Rates ===%n", symbol);
        System.out.printf("Long:  %.2f %s%n",
            swapLong, swapLong >= 0 ? "(credit)" : "(cost)");
        System.out.printf("Short: %.2f %s%n",
            swapShort, swapShort >= 0 ? "(credit)" : "(cost)");
    }
}

// Usage
SwapChecker.checkSwap(account, "EURUSD");
```

### 4) Check volume limits

```java
public class VolumeValidator {
    public record VolumeLimits(
        double min,
        double max,
        double step
    ) {
        public boolean isValid(double volume) {
            if (volume < min || volume > max) return false;

            double diff = (volume - min) / step;
            return Math.abs(diff - Math.round(diff)) < 0.0001;
        }
    }

    public static VolumeLimits getLimits(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var minReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MIN
        );
        var maxReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MAX
        );
        var stepReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_STEP
        );

        return new VolumeLimits(
            minReply.getData().getValue(),
            maxReply.getData().getValue(),
            stepReply.getData().getValue()
        );
    }
}

// Usage
var limits = VolumeValidator.getLimits(account, "EURUSD");
System.out.printf("Volume: min=%.2f, max=%.2f, step=%.2f%n",
    limits.min(), limits.max(), limits.step());

double testVolume = 0.5;
System.out.printf("%.2f lots valid: %s%n",
    testVolume, limits.isValid(testVolume));
```

### 5) Calculate lot size from risk

```java
public class LotCalculator {
    /**
     * Calculate lot size based on risk amount
     */
    public static double calculateLots(
            MT5Account account,
            String symbol,
            double riskAmount,
            double stopLossPoints) throws ApiExceptionMT5 {

        // Get tick value
        var tickValueReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_TRADE_TICK_VALUE
        );
        double tickValue = tickValueReply.getData().getValue();

        // Get point size
        var pointReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT
        );
        double point = pointReply.getData().getValue();

        // Calculate lots
        double lots = riskAmount / (stopLossPoints * point * tickValue * 100000);

        // Get volume limits
        var limits = VolumeValidator.getLimits(account, symbol);

        // Round to step
        lots = Math.round(lots / limits.step()) * limits.step();

        // Clamp to limits
        lots = Math.max(limits.min(), Math.min(limits.max(), lots));

        return lots;
    }
}

// Usage - risk $100 with 50-point SL
double lots = LotCalculator.calculateLots(account, "EURUSD", 100.0, 50.0);
System.out.printf("Calculated lot size: %.2f%n", lots);
```

### 6) Get contract size

```java
// Get contract size for position value calculation
var contractReply = account.symbolInfoDouble(
    "XAUUSD",
    Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_TRADE_CONTRACT_SIZE
);
double contractSize = contractReply.getData().getValue();

// Get current price
var bidReply = account.symbolInfoDouble(
    "XAUUSD",
    Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_BID
);
double price = bidReply.getData().getValue();

// Calculate position value
double lots = 1.0;
double positionValue = lots * contractSize * price;

System.out.printf("XAUUSD Contract size: %.0f%n", contractSize);
System.out.printf("1 lot position value: $%.2f%n", positionValue);
```

### 7) Symbol info helper class

```java
public class SymbolInfo {
    private final MT5Account account;
    private final String symbol;

    public SymbolInfo(MT5Account account, String symbol) {
        this.account = account;
        this.symbol = symbol;
    }

    private double getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty property)
            throws ApiExceptionMT5 {
        var reply = account.symbolInfoDouble(symbol, property);
        return reply.getData().getValue();
    }

    public double getPoint() throws ApiExceptionMT5 {
        return getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT);
    }

    public double getBid() throws ApiExceptionMT5 {
        return getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_BID);
    }

    public double getAsk() throws ApiExceptionMT5 {
        return getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_ASK);
    }

    public double getSpreadPoints() throws ApiExceptionMT5 {
        double bid = getBid();
        double ask = getAsk();
        double point = getPoint();
        return (ask - bid) / point;
    }

    public double getContractSize() throws ApiExceptionMT5 {
        return getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_TRADE_CONTRACT_SIZE);
    }

    public double getVolumeMin() throws ApiExceptionMT5 {
        return getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MIN);
    }

    public double getVolumeMax() throws ApiExceptionMT5 {
        return getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MAX);
    }

    public double getVolumeStep() throws ApiExceptionMT5 {
        return getDouble(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_STEP);
    }
}

// Usage
var info = new SymbolInfo(account, "EURUSD");
System.out.printf("Point: %.5f%n", info.getPoint());
System.out.printf("Spread: %.1f points%n", info.getSpreadPoints());
System.out.printf("Min lot: %.2f%n", info.getVolumeMin());
```

### 8) Weekly swap calendar

```java
public class SwapCalendar {
    private static final String[] DAYS = {
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    private static final Mt5TermApiMarketInfo.SymbolInfoDoubleProperty[] SWAP_PROPERTIES = {
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_SUNDAY,
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_MONDAY,
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_TUESDAY,
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_WEDNESDAY,
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_THURSDAY,
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_FRIDAY,
        Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_SATURDAY
    };

    public static void printSwapCalendar(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        // Get base swap rates
        var longReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_LONG
        );
        var shortReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_SWAP_SHORT
        );

        double swapLong = longReply.getData().getValue();
        double swapShort = shortReply.getData().getValue();

        System.out.printf("\n╔═══════════════════════════════════════════════╗%n");
        System.out.printf("║       %s SWAP CALENDAR                    ║%n", symbol);
        System.out.printf("╠═══════════════════════════════════════════════╣%n");
        System.out.printf("║ Base rates: Long=%.2f | Short=%.2f          ║%n", swapLong, swapShort);
        System.out.printf("╠═══════════════════════════════════════════════╣%n");
        System.out.printf("║ Day       │ Multiplier │ Long    │ Short   ║%n");
        System.out.printf("╠═══════════════════════════════════════════════╣%n");

        for (int i = 0; i < 7; i++) {
            var reply = account.symbolInfoDouble(symbol, SWAP_PROPERTIES[i]);
            double multiplier = reply.getData().getValue();

            String multiplierStr = multiplier == 0 ? "No swap" :
                                   multiplier == 1 ? "1x" :
                                   multiplier == 3 ? "3x (triple)" :
                                   String.format("%.0fx", multiplier);

            System.out.printf("║ %-9s │ %-10s │ %7.2f │ %7.2f ║%n",
                DAYS[i],
                multiplierStr,
                swapLong * multiplier,
                swapShort * multiplier
            );
        }

        System.out.printf("╚═══════════════════════════════════════════════╝%n");
    }
}

// Usage
SwapCalendar.printSwapCalendar(account, "EURUSD");
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolInfoDoubleRequest request =
    Mt5TermApiMarketInfo.SymbolInfoDoubleRequest.newBuilder()
        .setSymbol("EURUSD")
        .setType(Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolInfoDoubleReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolInfoDouble(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
double value = reply.getData().getValue();
```
