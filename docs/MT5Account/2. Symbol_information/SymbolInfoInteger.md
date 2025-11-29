# ✅ Getting Symbol Integer Properties

> **Request:** retrieve specific integer properties of a trading symbol. Access symbol digits, spread, trading modes, session data, and various flags.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolInfoInteger(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolInfoInteger` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolInfoInteger(SymbolInfoIntegerRequest) → SymbolInfoIntegerReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolInfoInteger(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Gets a specific integer property of a trading symbol.
     * Returns integer values such as digits, spread, trading mode, session data, and boolean flags.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param property Property type to retrieve (e.g., SYMBOL_DIGITS, SYMBOL_SPREAD, SYMBOL_SELECT)
     * @return Property value as long (int64)
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoIntegerReply symbolInfoInteger(
        String symbol,
        Mt5TermApiMarketInfo.SymbolInfoIntegerProperty property) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolInfoIntegerRequest { symbol: string, type: SymbolInfoIntegerProperty }`

**Reply message:** `SymbolInfoIntegerReply { data: SymbolInfoIntegerData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter  | Type                            | Required | Description                                      |
| ---------- | ------------------------------- | -------- | ------------------------------------------------ |
| `symbol`   | `String`                        | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")           |
| `property` | `SymbolInfoIntegerProperty`     | ✅       | Property to retrieve (see enum below)            |

---

## ⬆️ Output - `SymbolInfoIntegerData`

| Field   | Type   | Description                      |
| ------- | ------ | -------------------------------- |
| `value` | `long` | The requested property value (int64) |

Access using `reply.getData().getValue()`.

**Note:** Boolean properties return `1` (true) or `0` (false).

---

## 🧱 Related enums (from proto)

### `SymbolInfoIntegerProperty`

| Enum Value | Value | Type | Description |
|------------|-------|------|-------------|
| `SYMBOL_SUBSCRIPTION_DELAY` | 0 | `bool` | Data arrives with delay (1=yes, 0=no) |
| `SYMBOL_SECTOR` | 1 | `enum` | Economic sector |
| `SYMBOL_INDUSTRY` | 2 | `enum` | Industry/economy branch |
| `SYMBOL_CUSTOM` | 3 | `bool` | Is custom symbol (1=yes, 0=no) |
| `SYMBOL_BACKGROUND_COLOR` | 4 | `color` | Background color in MarketWatch |
| `SYMBOL_CHART_MODE` | 5 | `enum` | Price type for bars (Bid/Last) |
| `SYMBOL_EXIST` | 6 | `bool` | Symbol exists (1=yes, 0=no) |
| `SYMBOL_SELECT` | 7 | `bool` | Selected in MarketWatch (1=yes, 0=no) |
| `SYMBOL_VISIBLE` | 8 | `bool` | Visible in MarketWatch (1=yes, 0=no) |
| `SYMBOL_SESSION_DEALS` | 9 | `int` | Number of deals in current session |
| `SYMBOL_SESSION_BUY_ORDERS` | 10 | `int` | Number of buy orders |
| `SYMBOL_SESSION_SELL_ORDERS` | 11 | `int` | Number of sell orders |
| `SYMBOL_VOLUME` | 12 | `long` | Volume of last deal |
| `SYMBOL_VOLUMEHIGH` | 13 | `long` | Maximum day volume |
| `SYMBOL_VOLUMELOW` | 14 | `long` | Minimum day volume |
| `SYMBOL_TIME` | 15 | `datetime` | Time of last quote (Unix timestamp) |
| `SYMBOL_TIME_MSC` | 16 | `datetime` | Time of last quote (milliseconds) |
| `SYMBOL_DIGITS` | 17 | `int` | Digits after decimal point |
| `SYMBOL_SPREAD_FLOAT` | 18 | `bool` | Floating spread (1=yes, 0=no) |
| `SYMBOL_SPREAD` | 19 | `int` | Spread value in points |
| `SYMBOL_TICKS_BOOKDEPTH` | 20 | `int` | Market Depth levels (0=not available) |
| `SYMBOL_TRADE_CALC_MODE` | 21 | `enum` | Contract price calculation mode |
| `SYMBOL_TRADE_MODE` | 22 | `enum` | Order execution type |
| `SYMBOL_START_TIME` | 23 | `datetime` | Symbol trade start date (futures) |
| `SYMBOL_EXPIRATION_TIME` | 24 | `datetime` | Symbol trade end date (futures) |
| `SYMBOL_TRADE_STOPS_LEVEL` | 25 | `int` | Minimum distance for stop orders (points) |
| `SYMBOL_TRADE_FREEZE_LEVEL` | 26 | `int` | Distance to freeze operations (points) |
| `SYMBOL_TRADE_EXEMODE` | 27 | `enum` | Deal execution mode |
| `SYMBOL_SWAP_MODE` | 28 | `enum` | Swap calculation model |
| `SYMBOL_SWAP_ROLLOVER3DAYS` | 29 | `enum` | Day for 3x swap rollover |
| `SYMBOL_MARGIN_HEDGED_USE_LEG` | 30 | `bool` | Calculate hedging using larger leg |
| `SYMBOL_EXPIRATION_MODE` | 31 | `flags` | Allowed order expiration modes |
| `SYMBOL_FILLING_MODE` | 32 | `flags` | Allowed order filling modes |
| `SYMBOL_ORDER_MODE` | 33 | `flags` | Allowed order types |
| `SYMBOL_ORDER_GTC_MODE` | 34 | `enum` | SL/TP expiration mode |
| `SYMBOL_OPTION_MODE` | 35 | `enum` | Option type |
| `SYMBOL_OPTION_RIGHT` | 36 | `enum` | Option right (Call/Put) |

---

## 💬 Just the essentials

* **What it is.** RPC to get individual integer/boolean properties of a symbol.
* **Why you need it.** Get precision (digits), spread, check if symbol is selected, get stop levels.
* **Boolean values.** Properties like `SYMBOL_SELECT` return `1` (true) or `0` (false).
* **Timestamps.** Time properties return Unix timestamps (seconds or milliseconds).

---

## 🎯 Purpose

Use this method when you need to:

* Get symbol precision (digits) for price formatting.
* Check current spread in points.
* Verify if symbol is selected/visible in MarketWatch.
* Get minimum distance for stop orders.
* Check trading mode and execution type.

---

## 🧩 Notes & Tips

* `SYMBOL_DIGITS` is crucial for price formatting (e.g., 5 for EURUSD = %.5f).
* `SYMBOL_SPREAD` returns points, not price units.
* `SYMBOL_TRADE_STOPS_LEVEL` is minimum distance for SL/TP from current price.
* Boolean properties return `1` or `0`, not Java `true`/`false`.
* The method uses automatic reconnection via `executeWithReconnect()`.

---

## 🔗 Usage Examples

### 1) Get symbol digits (precision)

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // Get digits
            var reply = account.symbolInfoInteger(
                "EURUSD",
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS
            );
            long digits = reply.getData().getValue();

            System.out.printf("EURUSD digits: %d%n", digits);
            // Output: 5 (prices like 1.09876)

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Check if symbol is selected

```java
// Check if symbol is in MarketWatch
var reply = account.symbolInfoInteger(
    "GBPUSD",
    Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SELECT
);
boolean isSelected = reply.getData().getValue() == 1;

if (isSelected) {
    System.out.println("✅ GBPUSD is in MarketWatch");
} else {
    System.out.println("❌ GBPUSD not in MarketWatch");
    // Select it
    account.symbolSelect("GBPUSD", true);
}
```

### 3) Get current spread in points

```java
// Get spread
var spreadReply = account.symbolInfoInteger(
    "EURUSD",
    Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD
);
long spreadPoints = spreadReply.getData().getValue();

// Check if spread is floating
var floatReply = account.symbolInfoInteger(
    "EURUSD",
    Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD_FLOAT
);
boolean isFloating = floatReply.getData().getValue() == 1;

System.out.printf("Spread: %d points (%s)%n",
    spreadPoints,
    isFloating ? "floating" : "fixed"
);
```

### 4) Check stop levels

```java
public class StopLevelChecker {
    public static void checkStopLevels(MT5Account account, String symbol)
            throws ApiExceptionMT5 {

        var stopsReply = account.symbolInfoInteger(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_TRADE_STOPS_LEVEL
        );
        var freezeReply = account.symbolInfoInteger(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_TRADE_FREEZE_LEVEL
        );

        long stopsLevel = stopsReply.getData().getValue();
        long freezeLevel = freezeReply.getData().getValue();

        System.out.printf("=== %s Stop Levels ===%n", symbol);
        System.out.printf("Stops level:  %d points (min distance for SL/TP)%n", stopsLevel);
        System.out.printf("Freeze level: %d points (modification disabled)%n", freezeLevel);

        if (stopsLevel == 0) {
            System.out.println("✅ No minimum stop level restriction");
        }
    }
}

// Usage
StopLevelChecker.checkStopLevels(account, "EURUSD");
```

### 5) Format price with correct precision

```java
public class PriceFormatter {
    /**
     * Format price with symbol-specific precision
     */
    public static String formatPrice(
            MT5Account account,
            String symbol,
            double price) throws ApiExceptionMT5 {

        var reply = account.symbolInfoInteger(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS
        );
        int digits = (int) reply.getData().getValue();

        return String.format("%." + digits + "f", price);
    }
}

// Usage
double price = 1.098765;
String formatted = PriceFormatter.formatPrice(account, "EURUSD", price);
System.out.println("Price: " + formatted); // Output: 1.09877 (if 5 digits)
```

### 6) Symbol info helper with caching

```java
public class SymbolProperties {
    private final MT5Account account;
    private final String symbol;
    private Long cachedDigits;
    private Boolean cachedIsSelected;

    public SymbolProperties(MT5Account account, String symbol) {
        this.account = account;
        this.symbol = symbol;
    }

    private long getInteger(Mt5TermApiMarketInfo.SymbolInfoIntegerProperty property)
            throws ApiExceptionMT5 {
        var reply = account.symbolInfoInteger(symbol, property);
        return reply.getData().getValue();
    }

    public int getDigits() throws ApiExceptionMT5 {
        if (cachedDigits == null) {
            cachedDigits = getInteger(
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS
            );
        }
        return cachedDigits.intValue();
    }

    public boolean isSelected() throws ApiExceptionMT5 {
        if (cachedIsSelected == null) {
            cachedIsSelected = getInteger(
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SELECT
            ) == 1;
        }
        return cachedIsSelected;
    }

    public long getSpread() throws ApiExceptionMT5 {
        return getInteger(Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD);
    }

    public long getStopsLevel() throws ApiExceptionMT5 {
        return getInteger(Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_TRADE_STOPS_LEVEL);
    }

    public boolean isFloatingSpread() throws ApiExceptionMT5 {
        return getInteger(Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD_FLOAT) == 1;
    }

    public void clearCache() {
        cachedDigits = null;
        cachedIsSelected = null;
    }
}

// Usage
var props = new SymbolProperties(account, "EURUSD");
System.out.printf("Digits: %d%n", props.getDigits());
System.out.printf("Selected: %s%n", props.isSelected());
System.out.printf("Spread: %d points%n", props.getSpread());
```

### 7) Validate stop loss distance

```java
public class StopValidator {
    /**
     * Validate if stop loss distance is allowed
     */
    public static boolean isStopDistanceValid(
            MT5Account account,
            String symbol,
            double entryPrice,
            double stopPrice) throws ApiExceptionMT5 {

        // Get point size
        var pointReply = account.symbolInfoDouble(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT
        );
        double point = pointReply.getData().getValue();

        // Get minimum stop level
        var stopsReply = account.symbolInfoInteger(
            symbol,
            Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_TRADE_STOPS_LEVEL
        );
        long minStopsLevel = stopsReply.getData().getValue();

        // Calculate distance in points
        double distancePrice = Math.abs(entryPrice - stopPrice);
        double distancePoints = distancePrice / point;

        boolean valid = distancePoints >= minStopsLevel;

        if (!valid) {
            System.out.printf("❌ Stop distance %.0f points < minimum %d points%n",
                distancePoints, minStopsLevel);
        } else {
            System.out.printf("✅ Stop distance %.0f points OK%n", distancePoints);
        }

        return valid;
    }
}

// Usage
double entry = 1.09000;
double stop = 1.08950;
if (StopValidator.isStopDistanceValid(account, "EURUSD", entry, stop)) {
    System.out.println("Stop loss is valid");
}
```

### 8) Symbol status report

```java
public class SymbolStatus {
    public static void printStatus(MT5Account account, String symbol) {
        try {
            System.out.printf("\n╔═══════════════════════════════════════╗%n");
            System.out.printf("║    %s STATUS                      ║%n", symbol);
            System.out.printf("╠═══════════════════════════════════════╣%n");

            // Digits
            var digitsReply = account.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS
            );
            System.out.printf("║ Digits:       %-23d ║%n", digitsReply.getData().getValue());

            // Selected
            var selectReply = account.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SELECT
            );
            boolean selected = selectReply.getData().getValue() == 1;
            System.out.printf("║ Selected:     %-23s ║%n", selected ? "Yes" : "No");

            // Spread
            var spreadReply = account.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD
            );
            System.out.printf("║ Spread:       %-20d pts ║%n", spreadReply.getData().getValue());

            // Floating spread
            var floatReply = account.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD_FLOAT
            );
            boolean floating = floatReply.getData().getValue() == 1;
            System.out.printf("║ Spread type:  %-23s ║%n", floating ? "Floating" : "Fixed");

            // Stops level
            var stopsReply = account.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_TRADE_STOPS_LEVEL
            );
            System.out.printf("║ Stops level:  %-20d pts ║%n", stopsReply.getData().getValue());

            // Freeze level
            var freezeReply = account.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_TRADE_FREEZE_LEVEL
            );
            System.out.printf("║ Freeze level: %-20d pts ║%n", freezeReply.getData().getValue());

            System.out.printf("╚═══════════════════════════════════════╝%n");

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

// Usage
SymbolStatus.printStatus(account, "EURUSD");
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import mt5_term_api.*;

// Create request
Mt5TermApiMarketInfo.SymbolInfoIntegerRequest request =
    Mt5TermApiMarketInfo.SymbolInfoIntegerRequest.newBuilder()
        .setSymbol("EURUSD")
        .setType(Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS)
        .build();

// Add metadata headers
Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

// Call service
Mt5TermApiMarketInfo.SymbolInfoIntegerReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolInfoInteger(request);

// Check for errors
if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

// Use data
long value = reply.getData().getValue();
```
