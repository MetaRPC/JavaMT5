# MT5Sugar API Reference

**Layer 3 (High-Level API)** - Convenience methods with auto-normalization and risk management

**Source:** `src/main/java/io/metarpc/mt5/MT5Sugar.java`

---

## Symbol Helpers (12 methods)

- `ensureSymbolSelected(symbol)` - Auto-enable symbol in Market Watch
- `getPoint(symbol)` - Get point size (e.g., 0.00001)
- `getDigits(symbol)` - Get decimal places (e.g., 5)
- `getSpread(symbol)` - Get spread in points
- `getSpreadPrice(symbol)` - Get spread in price units
- `getBid(symbol)` - Get current Bid price
- `getAsk(symbol)` - Get current Ask price
- `normalizePrice(symbol, price)` - Round to symbol digits
- `normalizeVolume(symbol, volume)` - Normalize to min/max/step
- `pointsToPrice(symbol, points, isBuy)` - Convert points to price
- `pointsToPips(symbol, points)` - Convert points to pips
- `priceFromOffsetPoints(symbol, isBuy, points)` - Calculate offset price

## Market Orders (2 methods)

- `buyMarket(symbol, volume, stopLoss, takeProfit)` - BUY at current Ask
- `sellMarket(symbol, volume, stopLoss, takeProfit)` - SELL at current Bid

## Pending Orders (4 methods)

- `buyLimit(symbol, volume, price, SL, TP)` - BUY LIMIT (below price)
- `sellLimit(symbol, volume, price, SL, TP)` - SELL LIMIT (above price)
- `buyStop(symbol, volume, price, SL, TP)` - BUY STOP (breakout up)
- `sellStop(symbol, volume, price, SL, TP)` - SELL STOP (breakdown)

## Pending Orders with Points (4 methods)

- `buyLimitPoints(symbol, volume, offset, slPoints, tpPoints)` - BUY LIMIT via offset
- `sellLimitPoints(symbol, volume, offset, slPoints, tpPoints)` - SELL LIMIT via offset
- `buyStopPoints(symbol, volume, offset, slPoints, tpPoints)` - BUY STOP via offset
- `sellStopPoints(symbol, volume, offset, slPoints, tpPoints)` - SELL STOP via offset

## Position Management (5 methods)

- `modifyPosition(ticket, stopLoss, takeProfit)` - Change SL/TP
- `closePosition(ticket)` - Close position (full)
- `closePosition(ticket, volume)` - Close position (partial)
- `closeAll(symbol)` - Close all positions for symbol
- `closeAllBuy(symbol)` - Close all BUY positions
- `closeAllSell(symbol)` - Close all SELL positions

## Advanced Batch Operations (3 methods)

- `closeAllPositions(symbol, isBuy)` - Close only positions
- `closeAllPending(symbol, isBuy)` - Cancel only pending orders
- `cancelAll(symbol, isBuy)` - Alias for closeAllPending

## Risk Management (3 methods)

- `calculateVolume(symbol, slPoints, riskAmount)` - Calculate lot size from $ risk
- `buyByRisk(symbol, slPoints, risk, tpPoints)` - BUY with auto-calculated volume
- `sellByRisk(symbol, slPoints, risk, tpPoints)` - SELL with auto-calculated volume

## Advanced Helpers (4 methods)

- `getVolumeLimits(symbol)` - Get [min, max, step] array
- `getTickValueAndSize(symbol)` - Get [tickValue, tickSize] array

## Account & Position Helpers (10 methods)

- `getBalance()` - Account balance
- `getEquity()` - Account equity
- `getMargin()` - Used margin
- `getFreeMargin()` - Free margin
- `getProfit()` - Floating P/L
- `hasOpenPositions()` - Check if any positions open
- `getPositionCount()` - Count of open positions
- `normalizePriceDigits(symbol, price)` - Alias for normalizePrice
- `normalizeLots(symbol, lots)` - Alias for normalizeVolume
- `createTimestamp(seconds)` - Create protobuf timestamp

## Snapshot Helpers (2 methods + 2 classes)

- `getAccountSnapshot()` - Full account state → `AccountSnapshot`
- `getSymbolSnapshot(symbol)` - Full symbol info → `SymbolSnapshot`

## History Helpers (2 methods)

- `getOrdersHistoryLastDays(days, symbol)` - Orders history for N days
- `getPositionsHistoryPaged(page, itemsPerPage)` - Paginated positions history

---

**Key Features:**
- ✅ Auto-normalization (volumes, prices)
- ✅ Auto symbol selection
- ✅ Built-in risk management
- ✅ Simplified parameter handling
- ✅ Batch operations

**When to use:** Production trading bots, quick prototypes, beginners.

For detailed documentation with examples, see [MT5Sugar documentation](../MT5Sugar/MT5Sugar.Overview.md).
