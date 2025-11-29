# MT5Service API Reference

**Layer 2 (Mid-Level API)** - Wrapper methods with enhanced error handling and convenience

**Source:** `src/main/java/io/metarpc/mt5/MT5Service.java`

---

## Account Information Helpers

- `getBalance()` - Get account balance
- `getEquity()` - Get account equity
- `getMargin()` - Get used margin
- `getFreeMargin()` - Get free margin
- `getProfit()` - Get current floating profit/loss

## Position & Order Helpers

- `positionsTotal()` - Get count of open positions
- `openedOrders(sortType)` - Get all positions and orders
- `openedOrdersTickets()` - Get only ticket numbers
- `orderHistory(from, to, sortType, offset, limit)` - Get orders history
- `positionsHistory(from, to, sortType, offset, limit)` - Get positions history

## Trading Wrappers

- `orderSend(request)` - Place order with enhanced error handling
- `orderCheck(request)` - Validate order before placing
- `orderModify(request)` - Modify position/order
- `orderClose(request)` - Close position
- `orderCalcMargin(...)` - Calculate margin
- `orderCalcProfit(...)` - Calculate profit

## Symbol Information Wrappers

- `symbolInfoTick(symbol)` - Get current tick
- `symbolInfoDouble(symbol, property)` - Get double property
- `symbolInfoInteger(symbol, property)` - Get integer property
- `symbolInfoString(symbol, property)` - Get string property
- `symbolSelect(symbol, select)` - Enable/disable symbol
- `symbolsTotal(selectedOnly)` - Get symbols count

## Market Depth Wrappers

- `marketBookAdd(symbol)` - Subscribe to DOM
- `marketBookGet(symbol)` - Get DOM data
- `marketBookRelease(symbol)` - Unsubscribe from DOM

## Streaming Subscriptions

- `onSymbolTick(symbols[], observer)` - Subscribe to ticks
- `onTrade(observer)` - Subscribe to trades
- `onPositionProfit(period, ignoreEmpty, observer)` - Subscribe to P/L updates
- `onPositionsAndPendingOrdersTickets(period, observer)` - Subscribe to tickets
- `onTradeTransaction(observer)` - Subscribe to transactions

---

**Key Differences from MT5Account:**
- Enhanced error messages
- Convenient overloaded methods
- Automatic type conversions
- Better default parameters

**Note:** MT5Service wraps MT5Account and provides the same functionality with improved usability.

For detailed documentation with examples, see [MT5Account documentation](../MT5Account/MT5Account.Master.Overview.md).
