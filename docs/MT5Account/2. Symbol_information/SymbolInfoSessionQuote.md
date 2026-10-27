# ✅ Getting Symbol Quote Session Times

> **Request:** retrieve the quote (pricing) session schedule for a symbol on a given day of the week. Returns the start and end time of the requested session.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolInfoSessionQuote(...)` (from package `pro.mrpc.mt5`)
* **gRPC service:** `mt5_term_api.MarketInfo`
* **Proto definition:** `SymbolInfoSessionQuote` (defined in `mt5-term-api-market-info.proto`)

### RPC

* **Service:** `mt5_term_api.MarketInfo`
* **Method:** `SymbolInfoSessionQuote(SymbolInfoSessionQuoteRequest) → SymbolInfoSessionQuoteReply`
* **Low‑level client (generated):** `MarketInfoGrpc.MarketInfoBlockingStub.symbolInfoSessionQuote(request)`
* **SDK wrapper (high-level):**

```java
package pro.mrpc.mt5;

public class MT5Account {
    /**
     * Gets the quote (pricing) session schedule for a symbol on a specific day.
     * Returns the start and end times when price quotes are available for the symbol.
     * Use this to determine when you can expect to receive price updates for market data.
     *
     * @param symbol Symbol name (e.g., "EURUSD")
     * @param dayOfWeek Day of the week (SUNDAY=0, MONDAY=1, ..., SATURDAY=6)
     * @param sessionIndex Session index (0 for first session, 1 for second session if multiple sessions exist)
     * @return Quote session times including start and end times in seconds since midnight
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply symbolInfoSessionQuote(
        String symbol,
        Mt5TermApiMarketInfo.DayOfWeek dayOfWeek,
        int sessionIndex) throws ApiExceptionMT5;
}
```

**Request message:** `SymbolInfoSessionQuoteRequest { symbol: string, day_of_week: DayOfWeek, session_index: uint32 }`

**Reply message:** `SymbolInfoSessionQuoteReply { data: SymbolInfoSessionQuoteData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter      | Type          | Required | Description                                                              |
| -------------- | ------------- | -------- | ------------------------------------------------------------------------ |
| `symbol`       | `String`      | ✅       | Symbol name (e.g., "EURUSD", "XAUUSD")                                   |
| `dayOfWeek`    | `DayOfWeek`   | ✅       | Day of the week to query (SUNDAY … SATURDAY)                             |
| `sessionIndex` | `int`         | ✅       | Ordinal number of the session (0 = first session). Indexing starts at 0 |

---

## ⬆️ Output - `SymbolInfoSessionQuoteData`

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

* **What it is.** A single RPC returning the start/end of one quote session for a symbol on a chosen weekday.
* **Why you need it.** To know when price quotes are published for a symbol (useful for exchange-traded instruments with limited hours).
* **Sanity check.** `from` and `to` encode seconds since midnight; take `getSeconds()` and treat only the time-of-day portion.

---

## 🎯 Purpose

Use it to align logic with market hours:

* Determine when quotes are expected for a symbol.
* Skip polling/subscriptions outside quote sessions.
* Build per-weekday session calendars.

---

## 🧩 Notes & Tips

* The method uses automatic reconnection via `executeWithReconnect()` to handle transient errors.
* All gRPC exceptions are converted to `ApiExceptionMT5` for consistent error handling.
* A symbol may expose multiple sessions per day — increment `sessionIndex` (0, 1, …) until the call returns an error.
* This is the **quote** session; for the tradeable window use [`symbolInfoSessionTrade()`](./SymbolInfoSessionTrade.md).

---

## 🔗 Usage Examples

### 1) First quote session on Monday

```java
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiMarketInfo;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply reply =
                account.symbolInfoSessionQuote(
                    "EURUSD",
                    Mt5TermApiMarketInfo.DayOfWeek.MONDAY,
                    0);

            var data = reply.getData();
            long fromSec = data.getFrom().getSeconds();
            long toSec   = data.getTo().getSeconds();
            System.out.printf("Mon quote session: %02d:%02d - %02d:%02d%n",
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

### 2) Enumerate all quote sessions for a day

```java
int index = 0;
while (true) {
    try {
        var data = account.symbolInfoSessionQuote(
            "EURUSD", Mt5TermApiMarketInfo.DayOfWeek.MONDAY, index).getData();
        System.out.printf("Session %d: %d - %d (sec of day)%n",
            index, data.getFrom().getSeconds(), data.getTo().getSeconds());
        index++;
    } catch (ApiExceptionMT5 e) {
        break; // no more sessions for this day
    }
}
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import mt5_term_api.*;

Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest request =
    Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest.newBuilder()
        .setSymbol("EURUSD")
        .setDayOfWeek(Mt5TermApiMarketInfo.DayOfWeek.MONDAY)
        .setSessionIndex(0)
        .build();

Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply reply = marketInfoClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolInfoSessionQuote(request);

if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

Mt5TermApiMarketInfo.SymbolInfoSessionQuoteData data = reply.getData();
```

---

## 📚 See also

* [SymbolInfoSessionTrade.md](./SymbolInfoSessionTrade.md) - Trading session schedule for a symbol
* [SymbolInfoTick.md](./SymbolInfoTick.md) - Current tick data for a symbol
* [SymbolParamsMany.md](./SymbolParamsMany.md) - Get all symbol parameters at once
