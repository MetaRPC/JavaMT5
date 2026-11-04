# MT5Service — Mid-Level API Overview

> **MT5Service is the architectural middle layer** between `MT5Account` (low-level gRPC) and `MT5Sugar` (high-level business logic). It wraps every `MT5Account` call and returns the **unwrapped `…Data` object or a primitive** instead of the full `…Reply`, so you skip the `.getData()` boilerplate on every call.

---

## 🏛️ Three-Layer Architecture

```text
┌────────────────────────────────────────────────────────────┐
│ MT5Sugar   — HIGH                                          │
│ Business logic, ready-made patterns, one-line operations   │
└───────────────────────┬────────────────────────────────────┘
                        │  uses Service methods
                        ▼
┌────────────────────────────────────────────────────────────┐
│ MT5Service — MID      ← you are here                       │
│ Unwraps Reply → Data / primitive (some add value,          │
│ some are plain pass-through)                               │
└───────────────────────┬────────────────────────────────────┘
                        │  calls Account methods
                        ▼
┌────────────────────────────────────────────────────────────┐
│ MT5Account — LOW                                           │
│ Direct gRPC calls, returns protobuf `…Reply` objects       │
└───────────────────────┬────────────────────────────────────┘
                        │  gRPC
                        ▼
                     MT5 Terminal
```

Reach the low-level layer any time via `service.getAccount()`.

---

## ⚖️ Value Reality Check

Java's `MT5Service` has **no custom DTOs** (unlike the Python SDK) — it returns the same protobuf `…Data` types as `MT5Account.…().getData()`. Its value is therefore mostly **ergonomic**:

- ✅ **Real value** — adds logic or combines calls:
  - `lastBars(...)` — walks `QuoteHistory.Bars` pages and trims to the newest `count` bars.
  - `isConnected()` — digs `checkConnect().getData().getHealthCheck().getIsAlive()` → `boolean`.
  - `getServerTime()`, the `getBalance()/getEquity()/…` convenience getters — one named call instead of an enum lookup.
- ⚪ **Convenience unwrap** — `return account.x().getData()` (or a single primitive field). Saves the `.getData()`/`.getRequestedValue()` boilerplate; otherwise identical to `MT5Account`.
- ⛔ **Pure pass-through** — `return account.x()` with no change: all streams, `quoteMany`, `marketBookAdd/Release`, `healthCheck`, `disconnect`, `closeChartForSymbol`, `stopListening`, `close`.

> **Key insight:** For single values or pass-through methods, calling `MT5Account` directly is identical. Use `MT5Service` for the cleaner return types, the convenience getters, and `lastBars`, or whenever you work through `MT5Sugar`.

---

## 📂 Sections

| # | Section | Methods | Highlights |
|---|---------|---------|------------|
| 1 | [Connection](./1.%20Connection.md) | 10 | `connect`, `connectByServerName`, `isConnected`, `connectProxy`, `reconnect`, `disconnect` |
| 2 | [Account Information](./2.%20Account_Information.md) | 14 | `accountSummary` + `accountInfo*` + 10 convenience getters (`getBalance`, `getEquity`, …) |
| 3 | [Symbol Information](./3.%20Symbol_Information.md) | 15 | `quote`, `symbolInfo*`, `symbolParamsMany`, session schedules |
| 4 | [Positions & Orders](./4.%20Positions_Orders.md) | 8 | `positionsTotal`, `openedOrders`, `orderHistory`, `positionsHistory` (+ UTC variants) |
| 5 | [Market Depth](./5.%20Market_Depth.md) | 3 | `marketBookAdd` / `marketBookGet` / `marketBookRelease` |
| 6 | [Market Data](./6.%20Market_Data.md) | 6 | `lastBars` ✅, `quoteHistoryBars`, `priceHistory`, `symbolList`, `tickValueWithSize`, `allHistoryOrders` |
| 7 | [Trading Operations](./7.%20Trading_Operations.md) | 7 | `orderSend`, `orderModify`, `orderClose`, `orderCalcMargin/Profit`, `orderCheck` |
| 8 | [Charts & Health](./8.%20Charts_and_Health.md) | 5 | `openChartForSymbol`, `closeChartForSymbol`, `healthCheck`, `getServerTime`, `stopListening` |
| 9 | [Streaming Methods](./9.%20Streaming_Methods.md) | 5 | `onSymbolTick`, `onTrade`, `onPositionProfit`, `onPositionsAndPendingOrdersTickets`, `onTradeTransaction` |

Plus `getAccount()` (escape hatch to the low-level layer) and `close()` (release the gRPC channel).

---

## 📇 Complete Method Index

Legend: ✅ adds logic · ⚪ convenience unwrap · ⛔ pure pass-through.

### 1. Connection
| Method | Returns | Value |
|--------|---------|-------|
| `connect(host, port, baseChartSymbol, waitForTerminalIsAlive, timeoutSeconds)` | `ConnectData` | ⚪ unwrap |
| `connect(host, port, baseChartSymbol)` | `ConnectData` | ⚪ unwrap |
| `connectByServerName(serverName, baseChartSymbol, timeoutSeconds)` | `ConnectData` | ⚪ unwrap |
| `connectByServerName(serverName, baseChartSymbol)` | `ConnectData` | ⚪ unwrap |
| `connectByServerName(serverName, baseChartSymbol, timeoutSeconds, name)` | `ConnectData` | ⚪ unwrap |
| `isConnected()` | `boolean` | ✅ digs health-check |
| `disconnect()` / `disconnect(delete)` | `void` | ⛔ pass-through |
| `connectProxy(…)` | `ConnectProxyData` | ⚪ unwrap |
| `reconnect(forceReconnection, timeoutSeconds)` | `ReconnectData` | ⚪ unwrap |

### 2. Account Information
| Method | Returns | Value |
|--------|---------|-------|
| `accountSummary()` | `AccountSummaryData` | ⚪ unwrap |
| `accountInfoDouble(propertyType)` | `double` | ⚪ unwrap field |
| `accountInfoInteger(propertyType)` | `long` | ⚪ unwrap field |
| `accountInfoString(propertyType)` | `String` | ⚪ unwrap field |
| `getBalance()` `getEquity()` `getMargin()` `getFreeMargin()` `getProfit()` | `double` | ✅ named convenience |
| `getLogin()` `getLeverage()` | `long` | ✅ named convenience |
| `getAccountName()` `getServerName()` `getCurrency()` | `String` | ✅ named convenience |

### 3. Symbol Information
| Method | Returns | Value |
|--------|---------|-------|
| `quote(symbol)` | `MrpcMqlTick` | ⚪ unwrap |
| `quoteMany(symbols)` | `SymbolInfoTickRequestReply[]` | ⛔ pass-through |
| `symbolsTotal(selectedOnly)` | `int` | ⚪ unwrap field |
| `symbolInfoTick(symbol)` | `MrpcMqlTick` | ⚪ unwrap |
| `symbolSelect(symbol, select)` | `boolean` | ⚪ unwrap field |
| `symbolInfoDouble(symbol, property)` | `SymbolInfoDoubleData` | ⚪ unwrap |
| `symbolInfoInteger(symbol, property)` | `SymbolInfoIntegerData` | ⚪ unwrap |
| `symbolInfoString(symbol, property)` | `SymbolInfoStringData` | ⚪ unwrap |
| `symbolExist(symbolName)` | `boolean` | ⚪ unwrap field |
| `symbolName(index, selectedOnly)` | `String` | ⚪ unwrap field |
| `symbolIsSynchronized(symbol)` | `boolean` | ⚪ unwrap field |
| `symbolInfoMarginRate(symbol, orderType)` | `SymbolInfoMarginRateData` | ⚪ unwrap |
| `symbolInfoSessionQuote(symbol, dayOfWeek, sessionIndex)` | `SymbolInfoSessionQuoteData` | ⚪ unwrap |
| `symbolInfoSessionTrade(symbol, dayOfWeek, sessionIndex)` | `SymbolInfoSessionTradeData` | ⚪ unwrap |
| `symbolParamsMany(symbolName, sortType, pageNumber, itemsPerPage)` | `SymbolParamsManyData` | ⚪ unwrap |

### 4. Positions & Orders
| Method | Returns | Value |
|--------|---------|-------|
| `positionsTotal()` | `PositionsTotalData` | ⚪ unwrap |
| `openedOrders(sortType)` | `OpenedOrdersData` | ⚪ unwrap |
| `openedOrders()` | `OpenedOrdersData` | ✅ default sort |
| `openedOrdersTickets()` | `OpenedOrdersTicketsData` | ⚪ unwrap |
| `orderHistory(from, to, sortType, pageNumber, itemsPerPage)` | `OrdersHistoryData` | ⚪ unwrap |
| `orderHistoryUtc(fromUtc, toUtc, sortType, pageToken, pageSize)` | `OrdersHistoryData` | ⚪ unwrap |
| `positionsHistory(sortType, from, to, pageNumber, itemsPerPage)` | `PositionsHistoryData` | ⚪ unwrap |
| `positionsHistoryUtc(sortType, fromUtc, toUtc, pageToken, pageSize)` | `PositionsHistoryData` | ⚪ unwrap |

### 5. Market Depth
| Method | Returns | Value |
|--------|---------|-------|
| `marketBookAdd(symbol)` | `void` | ⛔ pass-through |
| `marketBookGet(symbol)` | `MarketBookGetData` | ⚪ unwrap |
| `marketBookRelease(symbol)` | `void` | ⛔ pass-through |

### 6. Market Data
| Method | Returns | Value |
|--------|---------|-------|
| `lastBars(symbol, timeframe, count)` | `List<QuoteHistoryBar>` | ✅ paginates + trims |
| `quoteHistoryBars(symbol, timeframe, fromTimeUtc, toTimeUtc, pageSize, pageToken)` | `QuoteHistoryBarsData` | ⚪ unwrap |
| `priceHistory(symbol, mqlTimeframe, fromTimeUtc, toTimeUtc, maxBars)` | `List<PriceBar>` | ⚪ unwrap list |
| `symbolList()` | `List<String>` | ⚪ unwrap list |
| `tickValueWithSize(symbolNames)` | `TickValueWithSizeData` | ⚪ unwrap |
| `allHistoryOrders(fromUtc, toUtc, sortType, pageToken, pageSize)` | `AllHistoryOrdersData` | ⚪ unwrap |

### 7. Trading Operations
| Method | Returns | Value |
|--------|---------|-------|
| `orderSend(request)` | `OrderSendData` | ⚪ unwrap |
| `orderModify(request)` | `OrderModifyData` | ⚪ unwrap |
| `orderClose(ticket, volume, slippage)` | `OrderCloseData` | ⚪ unwrap |
| `orderClose(request)` | `OrderCloseData` | ✅ unpacks request |
| `orderCalcMargin(symbol, orderType, volume, openPrice)` | `double` | ⚪ unwrap field |
| `orderCalcProfit(symbol, orderType, volume, openPrice, closePrice)` | `double` | ⚪ unwrap field |
| `orderCheck(tradeRequest)` | `OrderCheckData` | ⚪ unwrap |

### 8. Charts & Health
| Method | Returns | Value |
|--------|---------|-------|
| `openChartForSymbol(symbolName, expertMode, timerPeriod, ignoreEmptyData)` | `long` | ⚪ unwrap id |
| `closeChartForSymbol(mqlChartId)` | `void` | ⛔ pass-through |
| `healthCheck()` | `HealthCheckReply` | ⛔ pass-through (full Reply) |
| `getServerTime()` | `long` | ✅ unwrap server time |
| `stopListening()` | `void` | ⛔ pass-through |

### 9. Streaming Methods
| Method | Returns | Value |
|--------|---------|-------|
| `onSymbolTick(symbolNames, observer)` | `Context.CancellableContext` | ⛔ pass-through |
| `onTrade(observer)` | `Context.CancellableContext` | ⛔ pass-through |
| `onPositionProfit(timerPeriodMilliseconds, ignoreEmptyData, observer)` | `Context.CancellableContext` | ⛔ pass-through |
| `onPositionsAndPendingOrdersTickets(timerPeriodMilliseconds, observer)` | `Context.CancellableContext` | ⛔ pass-through |
| `onTradeTransaction(observer)` | `Context.CancellableContext` | ⛔ pass-through |

---

## 🟢 Usage Pattern

```java
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.MT5Service;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;

public class Example {
    public static void main(String[] args) throws Exception {
        // 1. Low-level client
        MT5Account account = new MT5Account(12345678, "password");

        // 2. Wrap in the mid-level service
        MT5Service service = new MT5Service(account);

        try {
            // 3. Connect (unwrapped — returns ConnectData, not ConnectReply)
            service.connect("demo.mt5server.com", 443, "EURUSD");

            // 4. Convenience getters — no enum lookup, no .getData()
            System.out.printf("Balance: %.2f %s%n", service.getBalance(), service.getCurrency());
            System.out.printf("Equity:  %.2f%n", service.getEquity());

            // 5. Unwrapped Data objects
            var tick = service.symbolInfoTick("EURUSD");   // MrpcMqlTick, not a Reply
            System.out.printf("EURUSD: %.5f / %.5f%n", tick.getBid(), tick.getAsk());

            // 6. For a pass-through or single value, MT5Account is identical — reach it when needed:
            // var reply = service.getAccount().symbolInfoTick("EURUSD"); // full Reply

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            service.close();   // release the gRPC channel
        }
    }
}
```

---

## 🧭 When to use MT5Service vs MT5Account

**Use `MT5Service` when:**
- you want clean return types (`…Data` / primitives) without `.getData()` everywhere;
- you want the named convenience getters (`getBalance()`, `getLeverage()`, …) or `lastBars(...)`;
- you build on top of `MT5Sugar` (it works through Service).

**Use `MT5Account` directly when:**
- you need the full `…Reply` (e.g. to call `hasError()`/`getError()` yourself);
- the method is a pure pass-through and you want one fewer layer;
- a method exists only on `MT5Account` (e.g. `reconnect` overloads, `symbolList` variants, low-level streaming context control).

---

## 📚 Related

- [MT5Account — low-level API](../MT5Account/1.%20Account_information/Account_Information.Overview.md)
- [MT5Sugar — high-level API](../MT5Sugar/MT5Sugar.Overview.md)
- [gRPC Channel & Stream Management](../All_Guides/GRPC_CHANNEL_AND_STREAM_MANAGEMENT_EN.md)
