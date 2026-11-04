# ENUMs Usage Reference — MT5Account (Java)

> ENUM presence lookup table for all **MT5Account** low-level methods. Enum **type** names are the
> proto/Java names; enum **values** are shown as their **Java constants** — the UPPER_SNAKE proto
> names (e.g. `AccountInfoDoublePropertyType.ACCOUNT_BALANCE`). All enum types live in the
> `mt5_term_api` package, inside the `Mt5TermApi…` message classes.

---

## ✅ Summary Statistics

| Method Group | With ENUMs | Total | Percentage |
|--------------|------------|-------|------------|
| **Account Information** | 4 | 4 | 100% |
| **Symbol Information** | 8 | 14 | 57% |
| **Positions & Orders** | 3 | 5 | 60% |
| **Market Depth/DOM** | 1 | 3 | 33% |
| **Trading Operations** | 6 | 6 | 100% |
| **Subscriptions** | 3 | 5 | 60% |
| **TOTAL** | **25** | **37** | **68%** |

---

## 👤 1. Account Information (4 methods)

### 4/4 methods use ENUMs (100%)

| Method | ENUMs Count | Description | Input ENUMs | Output ENUMs |
|--------|-------------|-------------|-------------|--------------|
| **AccountSummary** | **[In: 0, Out: 1]** | Get all account data in one call (RECOMMENDED) | - | account trade mode (demo / contest / real) + margin mode (`MrpcEnumAccountMarginMode`: `RETAIL_NETTING`, `EXCHANGE`, `RETAIL_HEDGING`) |
| **AccountInfoDouble** | **[In: 1, Out: 0]**<br>1 ENUM (14 values) | Get double properties (Balance, Equity, Margin, …) | `AccountInfoDoublePropertyType` (14): `ACCOUNT_BALANCE`, `ACCOUNT_CREDIT`, `ACCOUNT_PROFIT`, `ACCOUNT_EQUITY`, `ACCOUNT_MARGIN`, `ACCOUNT_MARGIN_FREE`, `ACCOUNT_MARGIN_LEVEL`, `ACCOUNT_MARGIN_SO_CALL`, `ACCOUNT_MARGIN_SO_SO`, `ACCOUNT_MARGIN_INITIAL`, `ACCOUNT_MARGIN_MAINTENANCE`, `ACCOUNT_ASSETS`, `ACCOUNT_LIABILITIES`, `ACCOUNT_COMMISSION_BLOCKED` | - |
| **AccountInfoInteger** | **[In: 1, Out: 0]**<br>1 ENUM (11 values) | Get integer properties (Login, Leverage, …) | `AccountInfoIntegerPropertyType` (11): `ACCOUNT_LOGIN`, `ACCOUNT_TRADE_MODE`, `ACCOUNT_LEVERAGE`, `ACCOUNT_LIMIT_ORDERS`, `ACCOUNT_MARGIN_SO_MODE`, `ACCOUNT_TRADE_ALLOWED`, `ACCOUNT_TRADE_EXPERT`, `ACCOUNT_MARGIN_MODE`, `ACCOUNT_CURRENCY_DIGITS`, `ACCOUNT_FIFO_CLOSE`, `ACCOUNT_HEDGE_ALLOWED` | - |
| **AccountInfoString** | **[In: 1, Out: 0]**<br>1 ENUM (4 values) | Get string properties (Currency, Company, …) | `AccountInfoStringPropertyType` (4): `ACCOUNT_NAME`, `ACCOUNT_SERVER`, `ACCOUNT_CURRENCY`, `ACCOUNT_COMPANY` | - |

---

## 📊 2. Symbol Information (14 methods)

### 8/14 methods use ENUMs (57%)

| Method | ENUMs Count | Description | Input ENUMs | Output ENUMs |
|--------|-------------|-------------|-------------|--------------|
| **SymbolsTotal** | 0 ENUMs | Count total/selected symbols | - | - |
| **SymbolExist** | 0 ENUMs | Check if symbol exists | - | - |
| **SymbolName** | 0 ENUMs | Get symbol name by index | - | - |
| **SymbolSelect** | 0 ENUMs | Add/remove symbol from Market Watch | - | - |
| **SymbolIsSynchronized** | 0 ENUMs | Check sync status with server | - | - |
| **SymbolInfoDouble** | **[In: 1]** | Get double properties (Bid, Ask, Point, …) | `SymbolInfoDoubleProperty` — property selector | - |
| **SymbolInfoInteger** | **[In: 1]** | Get integer properties (Digits, Spread, …) | `SymbolInfoIntegerProperty` — property selector | - |
| **SymbolInfoString** | **[In: 1]** | Get string properties (Description, …) | `SymbolInfoStringProperty` — property selector | - |
| **SymbolInfoMarginRate** | **[In: 1]** | Get margin requirements for an order type | `ENUM_ORDER_TYPE` — order type | - |
| **SymbolInfoTick** | 0 ENUMs | Get last tick (Bid/Ask/last/volume + time) | - | - |
| **SymbolInfoSessionQuote** | **[In: 1]** | Get quote session times | `DayOfWeek` (7) — day of week | - |
| **SymbolInfoSessionTrade** | **[In: 1]** | Get trade session times | `DayOfWeek` (7) — day of week | - |
| **SymbolParamsMany** | **[In: 1, Out: many]** | Detailed parameters for multiple symbols | `AH_SYMBOL_PARAMS_MANY_SORT_TYPE` — sort mode | nested enums in `SymbolParameters`: `BMT5_ENUM_SYMBOL_CALC_MODE`, `BMT5_ENUM_SYMBOL_TRADE_MODE`, `BMT5_ENUM_SYMBOL_TRADE_EXECUTION`, `BMT5_ENUM_SYMBOL_SWAP_MODE`, `BMT5_ENUM_ORDER_TYPE_FILLING`, `BMT5_ENUM_SYMBOL_ORDER_GTC_MODE`, `BMT5_ENUM_SYMBOL_OPTION_MODE`, `BMT5_ENUM_SYMBOL_OPTION_RIGHT`, … |
| **TickValueWithSize** | 0 ENUMs | Get tick value and size for symbols | - | - |

---

## 📋 3. Positions & Orders (5 methods)

### 3/5 methods use ENUMs (60%)

| Method | ENUMs Count | Description | Input ENUMs | Output ENUMs |
|--------|-------------|-------------|-------------|--------------|
| **PositionsTotal** | 0 ENUMs | Count open positions | - | - |
| **OpenedOrders** | **[In: 1, Out: many]** | Get all opened orders & positions | `BMT5_ENUM_OPENED_ORDER_SORT_TYPE` — sort mode | in `PositionInfo`: `BMT5_ENUM_POSITION_TYPE`, `BMT5_ENUM_POSITION_REASON`; in `OpenedOrderInfo`: `BMT5_ENUM_ORDER_TYPE`, `BMT5_ENUM_ORDER_STATE`, `BMT5_ENUM_ORDER_TYPE_FILLING`, `BMT5_ENUM_ORDER_TYPE_TIME` |
| **OpenedOrdersTickets** | 0 ENUMs | Get only ticket numbers (lightweight) | - | - |
| **OrdersHistory** | **[In: 1, Out: many]** | Historical orders & deals with pagination | `BMT5_ENUM_ORDER_HISTORY_SORT_TYPE` — sort mode | in history order: `BMT5_ENUM_ORDER_STATE`, `BMT5_ENUM_ORDER_TYPE`, `BMT5_ENUM_ORDER_TYPE_FILLING`, `BMT5_ENUM_ORDER_TYPE_TIME`; in history deal: `BMT5_ENUM_DEAL_ENTRY_TYPE`, `BMT5_ENUM_DEAL_TYPE`, `BMT5_ENUM_DEAL_REASON` |
| **PositionsHistory** | **[In: 1, Out: 1]** | Historical positions with P&L | `AH_ENUM_POSITIONS_HISTORY_SORT_TYPE` — sort mode | `AH_ENUM_POSITIONS_HISTORY_ORDER_TYPE` — order type |

---

## 📚 4. Market Depth / DOM (3 methods)

### 1/3 methods use ENUMs (33%)

| Method | ENUMs Count | Description | Input ENUMs | Output ENUMs |
|--------|-------------|-------------|-------------|--------------|
| **MarketBookAdd** | 0 ENUMs | Subscribe to Depth of Market updates | - | - |
| **MarketBookRelease** | 0 ENUMs | Unsubscribe from DOM | - | - |
| **MarketBookGet** | **[Out: 1]**<br>1 ENUM (4 values) | Get current market depth snapshot | - | `BookType` (4): `BOOK_TYPE_SELL`, `BOOK_TYPE_BUY`, `BOOK_TYPE_SELL_MARKET`, `BOOK_TYPE_BUY_MARKET` |

---

## 💰 5. Trading (6 methods)

### 6/6 methods use ENUMs (100%)

| Method | ENUMs Count | Description | Input ENUMs | Output ENUMs |
|--------|-------------|-------------|-------------|--------------|
| **OrderSend** | **[In: 2]** | Send market or pending order | `TMT5_ENUM_ORDER_TYPE` (order type, e.g. `TMT5_ORDER_TYPE_BUY`), `TMT5_ENUM_ORDER_TYPE_TIME` (order lifetime) | - |
| **OrderModify** | **[In: 1]** | Modify an existing order | `TMT5_ENUM_ORDER_TYPE_TIME` — order lifetime | - |
| **OrderClose** | **[Out: 1]**<br>1 ENUM (3 values) | Close/remove a market or pending order | - | `MRPC_ORDER_CLOSE_MODE` (3): `MRPC_MARKET_ORDER_CLOSE`, `MRPC_MARKET_ORDER_PARTIAL_CLOSE`, `MRPC_PENDING_ORDER_REMOVE` |
| **OrderCheck** | **[In: 4]** | Validate an order before sending | in `MrpcMqlTradeRequest`: `MRPC_ENUM_TRADE_REQUEST_ACTIONS`, `ENUM_ORDER_TYPE_TF`, `MRPC_ENUM_ORDER_TYPE_FILLING`, `MRPC_ENUM_ORDER_TYPE_TIME` | - |
| **OrderCalcMargin** | **[In: 1]** | Calculate required margin | `ENUM_ORDER_TYPE_TF` — order type | - |
| **OrderCalcProfit** | **[In: 1]** | Calculate expected profit/loss | `ENUM_ORDER_TYPE_TF` — order type | - |

---

## 📡 6. Subscriptions (5 methods)

### 3/5 methods use ENUMs (60%)

| Method | ENUMs Count | Description | Input ENUMs | Output ENUMs |
|--------|-------------|-------------|-------------|--------------|
| **onSymbolTick** | 0 ENUMs | Stream tick data (Bid/Ask updates) | - | - |
| **onTrade** | **[Out: many]** | Stream trade events | - | `MT5_SUB_ENUM_EVENT_GROUP_TYPE` + nested: `SUB_ENUM_POSITION_TYPE`, `SUB_ENUM_POSITION_REASON`, `SUB_ENUM_ORDER_TYPE`, `SUB_ENUM_ORDER_STATE`, `SUB_ENUM_DEAL_TYPE`, `SUB_ENUM_ORDER_TYPE_TIME`, `SUB_ENUM_ORDER_TYPE_FILLING`, `SUB_ENUM_ORDER_REASON`, `SUB_ENUM_DEAL_ENTRY`, `SUB_ENUM_DEAL_REASON` |
| **onPositionProfit** | **[Out: 1]** | Stream position P&L updates | - | `MT5_SUB_ENUM_EVENT_GROUP_TYPE` — event type |
| **onPositionsAndPendingOrdersTickets** | 0 ENUMs | Stream ticket changes | - | - |
| **onTradeTransaction** | **[Out: many]** | Stream trade transaction events | - | `MT5_SUB_ENUM_EVENT_GROUP_TYPE` + nested transaction/request/result enums (`SUB_ENUM_TRADE_TRANSACTION_TYPE`, `SUB_ENUM_ORDER_TYPE`, `SUB_ENUM_ORDER_STATE`, `SUB_ENUM_DEAL_TYPE`, `SUB_ENUM_TRADE_REQUEST_ACTIONS`, `MqlErrorTradeCode`, …) |

---

## Important Notes

### Java ENUM Access

Enums live in the `mt5_term_api` package inside the `Mt5TermApi…` message classes, and are used
directly as Java enum constants (UPPER_SNAKE names):

```java
import mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoublePropertyType;
import mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleProperty;
import mt5_term_api.Mt5TermApiTradingHelper;

// Account double property — value getter is getRequestedValue()
double balance = account.accountInfoDouble(AccountInfoDoublePropertyType.ACCOUNT_BALANCE)
    .getData().getRequestedValue();

// Symbol double property — value getter is getValue()
double bid = account.symbolInfoDouble("EURUSD", SymbolInfoDoubleProperty.SYMBOL_BID)
    .getData().getValue();

// Enum inside a request object
var request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
    .setSymbol("EURUSD")
    .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
    .setVolume(0.10)
    .build();
```

> ⚠️ **Mind the value getter.** For **account** properties the reply value is
> `getData().getRequestedValue()`; for **symbol** properties it is `getData().getValue()`. They are
> not interchangeable.

> **Proto vs Java names.** The generated Java constant is the proto `OriginalName` in UPPER_SNAKE
> (`ACCOUNT_BALANCE`). Sibling SDKs in other languages may render the same value in their own casing
> (e.g. C# `AccountBalance`) — on the wire it is the same enum.

### Special Cases

1. **AccountInfoInteger / SymbolInfoInteger** — some properties *represent* an enum but come back as
   `long`/`int`; they are plain numbers you interpret, not typed enums in the reply.
2. **returnedCode** — the `returnedCode` field in trading replies is **not** an enum; it is a plain
   `int` trade return code (`10009` = done; see [Return Codes](RETURN_CODES_REFERENCE.md)).
3. **Timeframe** — chart/quote-history timeframes are carried as the MQL5 `ENUM_TIMEFRAMES` integer
   value (e.g. `PERIOD_H1 = 16385`), not a generated Java enum.

---

## See Also

### Documentation by Group

- [Account Information Overview](../MT5Account/1.%20Account_information/Account_Information.Overview.md)
- [Symbol Information Overview](../MT5Account/2.%20Symbol_information/Symbol_Information.Overview.md)
- [Positions & Orders Overview](../MT5Account/3.%20Positions_and_orders/Positions_And_Orders.Overview.md)
- [Market Depth (DOM) Overview](../MT5Account/4.%20Market_depth_DOM/Market_Depth_DOM.Overview.md)
- [Trading Overview](../MT5Account/5.%20Trading/Trading.Overview.md)
- [Subscriptions Overview](../MT5Account/6.%20Subscriptions/Subscriptions.Overview.md)
- [MT5Account Master Overview](../MT5Account/MT5Account.Master.Overview.md)
- [Return Codes Reference](RETURN_CODES_REFERENCE.md)
