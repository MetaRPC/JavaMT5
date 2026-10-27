# ✅ Getting Symbol Trade Session Times

> **Request:** retrieve the trading session schedule for a symbol on a given day of the week. Returns the start and end time of the requested session during which trading is allowed.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolInfoSessionTrade(...)` (from package `pro.mrpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolInfoSessionTrade` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolInfoSessionTrade(SymbolInfoSessionTradeRequest) → SymbolInfoSessionTradeReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolInfoSessionTrade(request)`
* **SDK wrapper (high-level):**

```java
package pro.mrpc.mt5;

public class MT5Account {
    /**
     * Gets the trading session schedule for a symbol on a specific day.
     * Returns the start and end times when trading operations are allowed for the symbol.
     * Use this to determine when you can open/close positions and place orders.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param dayOfWeek Day of the week (SUNDAY=0, MONDAY=1, ..., SATURDAY=6)
     * @param sessionIndex Session index (0 for first session, 1 for second session if multiple sessions exist)
     * @return Trade session times including start and end times in seconds since midnight
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply symbolInfoSessionTrade(
        String symbol,
        Mt5TermApiMarketInfo.DayOfWeek dayOfWeek,
        int sessionIndex) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolInfoSessionTradeRequest { symbol: string, day_of_week: DayOfWeek, session_index: uint32 }`

**Reply message:** `SymbolInfoSessionTradeReply { data: SymbolInfoSessionTradeData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type          | Required | Description                                                              |
| -------------- | ------------- | -------- | ------------------------------------------------------------------------ |
| `symbol`       | `String`      | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")                                   |
| `dayOfWeek`    | `DayOfWeek`   | ✅       | Day of the week to query (SUNDAY … SATURDAY)                             |
| `sessionIndex` | `int`         | ✅       | Ordinal number of the session (0 = first session). Indexing starts at 0 |

---

## ⬆️ Output - `SymbolInfoSessionTradeData`

| Field  | Type        | Description                                                                                  |
| ------ | ----------- | -------------------------------------------------------------------------------------------- |
| `from` | `Timestamp` | Session beginning time, in seconds from 00:00. Ignore the date part of the returned value.   |
| `to`   | `Timestamp` | Session end time, in seconds from 00:00. Ignore the date part of the returned value.         |

Access using `reply.getData().getFrom()` and `reply.getData().getTo()`.

---

## 🧱 Related enums (from proto)

### `DayOfWeek`

| Enum Value   | Value | Description |
| ------------ | ----- | ----------- |
| `SUNDAY`     | 0     | Sunday      |
| `MONDAY`     | 1     | Monday      |
| `TUESDAY`    | 2     | Tuesday     |
| `WEDNESDAY`  | 3     | Wednesday   |
| `THURSDAY`   | 4     | Thursday    |
| `FRIDAY`     | 5     | Friday      |
| `SATURDAY`   | 6     | Saturday    |

Accessed in Java as `Mt5TermApiMarketInfo.DayOfWeek.MONDAY`.

---

## 💬 Just the essentials

* **What it is.** A single RPC returning the start/end of one trading session for a symbol on a chosen weekday.
* **Why you need it.** To know when orders can actually be placed/closed (quote hours and trade hours can differ).
* **Sanity check.** `from` and `to` encode seconds since midnight; take `getSeconds()` and treat only the time-of-day portion.

---

## 🎯 Purpose

Use it to gate trading logic by market hours:

* Determine when trading is allowed for a symbol.
* Avoid sending orders outside the tradeable window.
* Build per-weekday trade-session calendars.

---

## 🧩 Notes & Tips

* The method uses automatic reconnection via `executeWithReconnect()` to handle transient errors.
* All gRPC exceptions are converted to `ApiExceptionMT5` for consistent error handling.
* A symbol may expose multiple sessions per day — increment `sessionIndex` (0, 1, …) until the call returns an error.
* This is the **trade** session; for the pricing window use [`symbolInfoSessionQuote()`](./SymbolInfoSessionQuote.md).

---

## 🔗 Usage Examples

### 1) First trade session on Monday

```java
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply reply =
                account.symbolInfoSessionTrade(
                    "EURUSD",
                    Mt5TermApiMarketInfo.DayOfWeek.MONDAY,
                    0);

            var data = reply.getData();
            long fromSec = data.getFrom().getSeconds();
            long toSec   = data.getTo().getSeconds();
            System.out.printf("Mon trade session: %02d:%02d - %02d:%02d%n",
                (fromSec / 3600) % 24, (fromSec / 60) % 60,
                (toSec / 3600) % 24,  (toSec / 60) % 60);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Check if a symbol trades on a given weekday

```java
boolean tradesOnSaturday;
try {
    account.symbolInfoSessionTrade(
        "EURUSD", Mt5TermApiMarketInfo.DayOfWeek.SATURDAY, 0);
    tradesOnSaturday = true;
} catch (ApiExceptionMT5 e) {
    tradesOnSaturday = false; // no trade session defined for that day
}
System.out.println("Trades on Saturday: " + tradesOnSaturday);
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import mt5_term_api.*;

Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest request =
    Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest.newBuilder()
        .setSymbol("EURUSD")
        .setDayOfWeek(Mt5TermApiMarketInfo.DayOfWeek.MONDAY)
        .setSessionIndex(0)
        .build();

Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolInfoSessionTrade(request);

if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

Mt5TermApiMarketInfo.SymbolInfoSessionTradeData data = reply.getData();
```

---

## 📚 See also

* [SymbolInfoSessionQuote.md](./SymbolInfoSessionQuote.md) - Quote (pricing) session schedule for a symbol
* [SymbolInfoTick.md](./SymbolInfoTick.md) - Current tick data for a symbol
* [SymbolParamsMany.md](./SymbolParamsMany.md) - Get all symbol parameters at once
