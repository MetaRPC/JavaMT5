# MT5Account - Master Overview

> One page to **orient fast**: what lives where, how to choose the right API, and jump links to every **overview** and **method spec** in this docs set.

---

## 🚦 Start here - Section Overviews

* **[Account\_Information - Overview](./1.%20Account_information/Account_Information.Overview.md)**
  Account balance/equity/margin/leverage, complete snapshot or single properties.

* **[Symbol\_Information - Overview](./2.%20Symbol_information/Symbol_Information.Overview.md)**
  Quotes, symbol properties, trading rules, tick values, Market Watch management.

* **[Positions\_And\_Orders - Overview](./3.%20Positions_and_orders/Positions_And_Orders.Overview.md)**
  Open positions, pending orders, historical deals, order history.

* **[Market\_Depth\_DOM - Overview](./4.%20Market_depth_DOM/Market_Depth_DOM.Overview.md)**
  Level II quotes, order book data, market depth subscription.

* **[Trading - Overview](./5.%20Trading/Trading.Overview.md)**
  Order execution, position management, margin/profit calculations, trade validation.

* **[Subscriptions - Overview](./6.%20Subscriptions/Subscriptions.Overview.md)**
  Real-time streams: ticks, trades, profit updates, transaction log.

---

## 🧭 How to pick an API

| If you need…                   | Go to…               | Typical calls                                                                 |
| ------------------------------ | -------------------- | ----------------------------------------------------------------------------- |
| Account snapshot               | Account\_Information | `accountSummary`, `accountInfoDouble`, `accountInfoInteger`, `accountInfoString` |
| Quotes & symbol properties     | Symbol\_Information  | `symbolInfoTick`, `symbolInfoDouble`, `symbolInfoInteger`, `tickValueWithSize` |
| Current positions & orders     | Positions\_And\_Orders | `positionsTotal`, `openedOrders`, `openedOrdersTickets`                     |
| Historical trades              | Positions\_And\_Orders | `orderHistory`, `positionsHistory`                                          |
| Level II / Order book          | Market\_Depth\_DOM   | `marketBookAdd`, `marketBookGet`, `marketBookRelease`                        |
| Trading operations             | Trading              | `orderSend`, `orderModify`, `orderClose`                                     |
| Pre-trade calculations         | Trading              | `orderCalcMargin`, `orderCalcProfit`, `orderCheck`                           |
| Real-time updates              | Subscriptions        | `onSymbolTick`, `onTrade`, `onPositionProfit`, `onTradeTransaction`          |

---

## 🔌 Usage pattern (gRPC protocol)

Every method follows the same shape:

* **Proto Service/Method:** `Service.Method(Request) → Reply`
* **Java wrapper:** `MT5Account.method(...)`
* **Reply structure:** `Reply.getData()` payload (proto-generated objects)
* **Return codes:** `10009` = success; other codes = check error message

Timestamps = **UTC** (`google.protobuf.Timestamp`). For streaming subscriptions, use **`StreamObserver<T>`** pattern.

---

# 📚 Full Index · All Method Specs

---

## 📄 Account Information

* **Overview:** [Account\_Information.Overview.md](./1.%20Account_information/Account_Information.Overview.md)

### Complete Snapshot

* [AccountSummary.md](./1.%20Account_information/AccountSummary.md) - All account info at once (balance, equity, margin, etc.)

### Individual Properties

* [AccountInfoDouble.md](./1.%20Account_information/AccountInfoDouble.md) - Single double value (balance, equity, margin, profit, etc.)
* [AccountInfoInteger.md](./1.%20Account_information/AccountInfoInteger.md) - Single integer value (login, leverage, limit orders, etc.)
* [AccountInfoString.md](./1.%20Account_information/AccountInfoString.md) - Single string value (name, server, currency, company)

---

## 📊 Symbol Information

* **Overview:** [Symbol\_Information.Overview.md](./2.%20Symbol_information/Symbol_Information.Overview.md)

### Current Quotes

* [SymbolInfoTick.md](./2.%20Symbol_information/SymbolInfoTick.md) - Current quote for symbol (bid, ask, last, volume, time)

### Symbol Inventory & Management

* [SymbolsTotal.md](./2.%20Symbol_information/SymbolsTotal.md) - Count of available symbols
* [SymbolName.md](./2.%20Symbol_information/SymbolName.md) - Get symbol name by index
* [SymbolSelect.md](./2.%20Symbol_information/SymbolSelect.md) - Enable/disable symbol in Market Watch
* [SymbolExist.md](./2.%20Symbol_information/SymbolExist.md) - Check if symbol exists
* [SymbolIsSynchronized.md](./2.%20Symbol_information/SymbolIsSynchronized.md) - Check symbol data sync status

### Symbol Properties

* [SymbolInfoDouble.md](./2.%20Symbol_information/SymbolInfoDouble.md) - Single double property (bid, ask, point, volume min/max, etc.)
* [SymbolInfoInteger.md](./2.%20Symbol_information/SymbolInfoInteger.md) - Single integer property (digits, spread, stops level, etc.)
* [SymbolInfoString.md](./2.%20Symbol_information/SymbolInfoString.md) - Single string property (description, currency, path)

### Trading Calculations

* [TickValueWithSize.md](./2.%20Symbol_information/TickValueWithSize.md) - Batch tick values for multiple symbols

---

## 📦 Positions & Orders

* **Overview:** [Positions\_And\_Orders.Overview.md](./3.%20Positions_and_orders/Positions_And_Orders.Overview.md)

### Current State

* [PositionsTotal.md](./3.%20Positions_and_orders/PositionsTotal.md) - Count of open positions
* [OpenedOrders.md](./3.%20Positions_and_orders/OpenedOrders.md) - Full details of all open positions and pending orders
* [OpenedOrdersTickets.md](./3.%20Positions_and_orders/OpenedOrdersTickets.md) - Ticket numbers only (lightweight)

### Historical Data

* [OrderHistory.md](./3.%20Positions_and_orders/OrderHistory.md) - Historical orders within time range
* [PositionsHistory.md](./3.%20Positions_and_orders/PositionsHistory.md) - Historical deals (executed trades) within time range

---

## 📈 Market Depth (DOM)

* **Overview:** [Market\_Depth\_DOM.Overview.md](./4.%20Market_depth_DOM/Market_Depth_DOM.Overview.md)

### Level II Quotes

* [MarketBookAdd.md](./4.%20Market_depth_DOM/MarketBookAdd.md) - Subscribe to Market Depth for symbol
* [MarketBookGet.md](./4.%20Market_depth_DOM/MarketBookGet.md) - Get current order book data
* [MarketBookRelease.md](./4.%20Market_depth_DOM/MarketBookRelease.md) - Unsubscribe from Market Depth

---

## 🛠 Trading Actions

* **Overview:** [Trading.Overview.md](./5.%20Trading/Trading.Overview.md)

### Order Execution & Management

* [OrderSend.md](./5.%20Trading/OrderSend.md) - Place market or pending orders
* [OrderModify.md](./5.%20Trading/OrderModify.md) - Modify SL/TP or order parameters
* [OrderClose.md](./5.%20Trading/OrderClose.md) - Close positions or delete pending orders

### Pre-Trade Calculations

* [OrderCalcMargin.md](./5.%20Trading/OrderCalcMargin.md) - Calculate margin required for trade
* [OrderCalcProfit.md](./5.%20Trading/OrderCalcProfit.md) - Calculate profit/loss for trade scenario
* [OrderCheck.md](./5.%20Trading/OrderCheck.md) - Validate trade request before execution

---

## 📡 Subscriptions (Streaming)

* **Overview:** [Subscriptions.Overview.md](./6.%20Subscriptions/Subscriptions.Overview.md)

### Real-Time Price Updates

* [OnSymbolTick.md](./6.%20Subscriptions/OnSymbolTick.md) - Real-time tick stream for symbols

### Trading Events

* [OnTrade.md](./6.%20Subscriptions/OnTrade.md) - Position/order changes (opened, closed, modified)
* [OnTradeTransaction.md](./6.%20Subscriptions/OnTradeTransaction.md) - Detailed transaction log (complete audit trail)

### Position Monitoring

* [OnPositionProfit.md](./6.%20Subscriptions/OnPositionProfit.md) - Periodic profit/loss updates
* [OnPositionsAndPendingOrdersTickets.md](./6.%20Subscriptions/OnPositionsAndPendingOrdersTickets.md) - Periodic ticket lists (lightweight)

---

## 🎯 Quick Navigation by Use Case

| I want to... | Use this method |
|-------------|-----------------|
| **ACCOUNT INFORMATION** |
| Get complete account snapshot | [accountSummary](./1.%20Account_information/AccountSummary.md) |
| Get account balance | [accountInfoDouble](./1.%20Account_information/AccountInfoDouble.md) (BALANCE) |
| Get account equity | [accountInfoDouble](./1.%20Account_information/AccountInfoDouble.md) (EQUITY) |
| Get account leverage | [accountInfoInteger](./1.%20Account_information/AccountInfoInteger.md) (LEVERAGE) |
| Get account currency | [accountInfoString](./1.%20Account_information/AccountInfoString.md) (CURRENCY) |
| **SYMBOL INFORMATION** |
| Get current price for symbol | [symbolInfoTick](./2.%20Symbol_information/SymbolInfoTick.md) |
| List all available symbols | [symbolsTotal](./2.%20Symbol_information/SymbolsTotal.md) + [symbolName](./2.%20Symbol_information/SymbolName.md) |
| Add symbol to Market Watch | [symbolSelect](./2.%20Symbol_information/SymbolSelect.md) (true) |
| Get symbol digits (decimal places) | [symbolInfoInteger](./2.%20Symbol_information/SymbolInfoInteger.md) (DIGITS) |
| Get point size for symbol | [symbolInfoDouble](./2.%20Symbol_information/SymbolInfoDouble.md) (POINT) |
| Get symbol volume limits | [symbolInfoDouble](./2.%20Symbol_information/SymbolInfoDouble.md) (VOLUME_MIN/MAX/STEP) |
| Get tick values for symbols | [tickValueWithSize](./2.%20Symbol_information/TickValueWithSize.md) |
| **POSITIONS & ORDERS** |
| Count open positions | [positionsTotal](./3.%20Positions_and_orders/PositionsTotal.md) |
| Get all open positions (full details) | [openedOrders](./3.%20Positions_and_orders/OpenedOrders.md) |
| Get position ticket numbers only | [openedOrdersTickets](./3.%20Positions_and_orders/OpenedOrdersTickets.md) |
| Get historical orders | [orderHistory](./3.%20Positions_and_orders/OrderHistory.md) |
| Get historical deals/trades | [positionsHistory](./3.%20Positions_and_orders/PositionsHistory.md) |
| **MARKET DEPTH** |
| Subscribe to Level II quotes | [marketBookAdd](./4.%20Market_depth_DOM/MarketBookAdd.md) |
| Get order book data | [marketBookGet](./4.%20Market_depth_DOM/MarketBookGet.md) |
| Unsubscribe from Level II | [marketBookRelease](./4.%20Market_depth_DOM/MarketBookRelease.md) |
| **TRADING OPERATIONS** |
| Open market BUY position | [orderSend](./5.%20Trading/OrderSend.md) (type=BUY) |
| Open market SELL position | [orderSend](./5.%20Trading/OrderSend.md) (type=SELL) |
| Place BUY LIMIT order | [orderSend](./5.%20Trading/OrderSend.md) (type=BUY_LIMIT) |
| Place SELL LIMIT order | [orderSend](./5.%20Trading/OrderSend.md) (type=SELL_LIMIT) |
| Place BUY STOP order | [orderSend](./5.%20Trading/OrderSend.md) (type=BUY_STOP) |
| Place SELL STOP order | [orderSend](./5.%20Trading/OrderSend.md) (type=SELL_STOP) |
| Modify SL/TP of position | [orderModify](./5.%20Trading/OrderModify.md) |
| Close a position | [orderClose](./5.%20Trading/OrderClose.md) |
| Delete pending order | [orderClose](./5.%20Trading/OrderClose.md) |
| Calculate margin before trade | [orderCalcMargin](./5.%20Trading/OrderCalcMargin.md) |
| Calculate profit for trade | [orderCalcProfit](./5.%20Trading/OrderCalcProfit.md) |
| Validate trade before execution | [orderCheck](./5.%20Trading/OrderCheck.md) |
| **REAL-TIME SUBSCRIPTIONS** |
| Stream live prices | [onSymbolTick](./6.%20Subscriptions/OnSymbolTick.md) |
| Monitor trade events | [onTrade](./6.%20Subscriptions/OnTrade.md) |
| Track profit changes | [onPositionProfit](./6.%20Subscriptions/OnPositionProfit.md) |
| Monitor ticket changes | [onPositionsAndPendingOrdersTickets](./6.%20Subscriptions/OnPositionsAndPendingOrdersTickets.md) |
| Detailed transaction log | [onTradeTransaction](./6.%20Subscriptions/OnTradeTransaction.md) |

---

## 🏗️ API Architecture

### Layer 1: MT5Account (Low-Level)

**What:** Direct proto/gRPC communication with MT5 terminal.

**When to use:**
- Need full control over protocol
- Building custom wrappers
- Proto-level integration required

**Characteristics:**
- Works with proto Request/Response objects
- Raw gRPC method calls
- Complete access to all MT5 functions
- Highest complexity

**Location:** `src/main/java/io/metarpc/mt5/MT5Account.java`

**Documentation:** This folder (you are here!)

---

### Layer 2: MT5Service

**What:** Simplified wrapper methods without proto complexity.

**When to use:**
- Want simplified API but not auto-normalization
- Building custom convenience layers
- Need direct data returns

**Characteristics:**
- Simple method signatures
- Type conversions (proto → Java primitives)
- No proto objects in return values
- No auto-normalization

**Location:** `src/main/java/io/metarpc/mt5/MT5Service.java`

---

### Layer 3: MT5Sugar

**What:** High-level convenience API with ~50 smart methods.

**When to use:**
- Most trading scenarios (95% of cases)
- Want auto-normalization
- Need risk management helpers
- Building strategies quickly

**Characteristics:**
- Auto-normalization of volumes/prices
- Risk-based position sizing
- Batch operations
- Smart helpers

**Location:** `src/main/java/io/metarpc/mt5/MT5Sugar.java`

**Documentation:** [MT5Sugar.Overview.md](../MT5Sugar/MT5Sugar.Overview.md)

---

## 🎓 Learning Path

### Beginner: Start High (MT5Sugar)

```
1. Read: MT5Sugar.Overview.md
2. Use: High-level convenience methods
3. Run: examples/sugar/
4. Drop down when needed
```

**Recommendation:** Start with MT5Sugar for easiest learning curve.

---

### Intermediate: Understand Wrappers (MT5Service)

```
1. Study: How MT5Service wraps MT5Account
2. Compare: Wrapper vs low-level implementations
3. Run: examples/services/
4. Use: When auto-normalization not desired
```

---

### Advanced: Master Protocol (MT5Account)

```
1. Read: This documentation folder (MT5Account/)
2. Study: Proto definitions (src/main/proto/)
3. Run: examples/lowlevel/
4. Understand: Complete proto/gRPC communication
```

**Goal:** Deep understanding of MT5 protocol and terminal communication.

---


## 🔗 Related Documentation

* **[PROJECT_MAP.md](../PROJECT_MAP.md)** - Complete project structure
* **[MT5Sugar.Overview.md](../MT5Sugar/MT5Sugar.Overview.md)** - High-level convenience API (Layer 3)
* **[Orchestrators.Overview.md](../Orchestrators.Overview.md)** - Trading strategy implementations
* **[GLOSSARY.md](../GLOSSARY.md)** - Project terminology
* **[GETTING_STARTED.md](../GETTING_STARTED.md)** - Complete learning guide

---

## 💡 Key Concepts

### Proto Return Codes

* **10009** = Success / DONE
* **10004** = Requote
* **10006** = Request rejected
* **10013** = Invalid request
* **10014** = Invalid volume
* **10015** = Invalid price
* **10016** = Invalid stops
* **10018** = Market closed
* **10019** = Not enough money

Always check `returnedCode` field in trading operations.

---

## ⚠️ Important Notes

* **Demo account first:** Always test on demo before live trading.
* **Check return codes:** Every trading operation returns status code.
* **Validate parameters:** Use orderCheck() before orderSend().
* **Handle errors:** Network/protocol errors can occur.
* **Thread safety:** Streams execute on background threads.
* **Resource cleanup:** Unsubscribe from streams when done.
* **UTC timestamps:** All times are in UTC, not local time.
* **Broker limitations:** Not all brokers support all features (DOM, hedging, etc.).

---

"Trade safe, code clean, and may your proto buffers never overflow."
