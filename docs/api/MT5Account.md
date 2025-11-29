# MT5Account API Reference

**Layer 1 (Low-Level API)** - Direct proto/gRPC methods for MetaTrader 5

**Source:** `src/main/java/io/metarpc/mt5/MT5Account.java`

---

## Connection Methods

- `connect(String host, int port, String baseChartSymbol)` - Connect to MT5 terminal
- `connectByServerName(String serverName, String baseChartSymbol)` - Connect using server name
- `disconnect()` - Disconnect from MT5 terminal
- `close()` - Close gRPC channel and free resources

## Account Information

- `accountSummary()` - Get complete account summary
- `accountInfoDouble(ENUM_ACCOUNT_INFO_DOUBLE property)` - Get double property
- `accountInfoInteger(ENUM_ACCOUNT_INFO_INTEGER property)` - Get integer property
- `accountInfoString(ENUM_ACCOUNT_INFO_STRING property)` - Get string property

## Symbol Information

- `quote(String symbol)` - Get current quote (alias for symbolInfoTick)
- `quoteMany(String[] symbols)` - Get quotes for multiple symbols
- `symbolInfoTick(String symbol)` - Get current tick data
- `symbolInfoDouble(String symbol, ENUM_SYMBOL_INFO_DOUBLE property)` - Get double property
- `symbolInfoInteger(String symbol, ENUM_SYMBOL_INFO_INTEGER property)` - Get integer property
- `symbolInfoString(String symbol, ENUM_SYMBOL_INFO_STRING property)` - Get string property
- `symbolSelect(String symbol, boolean select)` - Enable/disable symbol in Market Watch
- `symbolName(int index, boolean selectedOnly)` - Get symbol name by index
- `symbolsTotal(boolean selectedOnly)` - Get total symbols count
- `symbolExist(String symbol)` - Check if symbol exists
- `symbolIsSynchronized(String symbol)` - Check if symbol data is synchronized

## Market Depth (DOM)

- `marketBookAdd(String symbol)` - Subscribe to market depth
- `marketBookGet(String symbol)` - Get current market depth data
- `marketBookRelease(String symbol)` - Unsubscribe from market depth

## Positions & Orders

- `openedOrders(ENUM_OPENED_ORDER_SORT_TYPE sortType)` - Get all open positions and orders
- `openedOrdersTickets()` - Get only ticket numbers
- `positionsTotal()` - Get count of open positions
- `orderHistory(from, to, sortType, offset, limit)` - Get orders history
- `positionsHistory(from, to, sortType, offset, limit)` - Get positions history

## Trading Operations

- `orderSend(request)` - Place new order (market/pending)
- `orderCheck(request)` - Check order before placing
- `orderModify(request)` - Modify existing order/position
- `orderClose(request)` - Close position
- `orderCalcMargin(action, symbol, volume, price)` - Calculate required margin
- `orderCalcProfit(action, symbol, volume, priceOpen, priceClose)` - Calculate profit
- `tickValueWithSize(symbol, tickValue, tickSize, volume)` - Calculate tick value

## Streaming Subscriptions

- `onSymbolTick(symbols[], observer)` - Subscribe to tick data stream
- `onTrade(observer)` - Subscribe to trade events
- `onPositionProfit(timerPeriod, ignoreEmpty, observer)` - Subscribe to position profit updates
- `onPositionsAndPendingOrdersTickets(timerPeriod, observer)` - Subscribe to tickets updates
- `onTradeTransaction(observer)` - Subscribe to trade transactions

---

**Note:** All methods throw `ApiExceptionMT5` on errors. Use try-catch for error handling.

For detailed documentation with examples, see [MT5Account documentation](../MT5Account/MT5Account.Master.Overview.md).
