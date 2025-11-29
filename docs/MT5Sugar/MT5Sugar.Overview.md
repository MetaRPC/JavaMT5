# MT5Sugar · Convenience Layer - Complete Overview

> High-level convenience methods for common trading operations. Simplified API with auto-normalization, risk management, and batch operations. **~50 methods** organized in 11 functional groups.

---

## 📁 What lives here

### 1. Symbol Helpers (12 methods)

Essential symbol information and normalization utilities.

* **[ensureSymbolSelected](./1.%20Symbol_helpers/ensureSymbolSelected.md)** - enable symbol in Market Watch
* **[getPoint](./1.%20Symbol_helpers/getPoint.md)** - point size (e.g., 0.00001)
* **[getDigits](./1.%20Symbol_helpers/getDigits.md)** - decimal places (e.g., 5)
* **[getSpread](./1.%20Symbol_helpers/getSpread.md)** - spread in points
* **[normalizePrice](./1.%20Symbol_helpers/normalizePrice.md)** - round price to symbol digits
* **[normalizeVolume](./1.%20Symbol_helpers/normalizeVolume.md)** - normalize to min/max/step
* **[pointsToPrice](./1.%20Symbol_helpers/pointsToPrice.md)** - convert points → price
* **[getBid](./1.%20Symbol_helpers/getBid.md)** - current Bid
* **[getAsk](./1.%20Symbol_helpers/getAsk.md)** - current Ask
* **[getSpreadPrice](./1.%20Symbol_helpers/getSpreadPrice.md)** - spread in price units
* **[pointsToPips](./1.%20Symbol_helpers/pointsToPips.md)** - points → pips conversion
* **[priceFromOffsetPoints](./1.%20Symbol_helpers/priceFromOffsetPoints.md)** - price + offset

### 2. Market Orders (2 methods)

Instant execution at market price.

* **[buyMarket](./2.%20Market_orders/buyMarket.md)** - BUY at Ask (instant)
* **[sellMarket](./2.%20Market_orders/sellMarket.md)** - SELL at Bid (instant)

### 3. Pending Orders (4 methods)

Entry orders with exact price.

* **[buyLimit](./3.%20Pending_orders/buyLimit.md)** - BUY LIMIT (below price)
* **[sellLimit](./3.%20Pending_orders/sellLimit.md)** - SELL LIMIT (above price)
* **[buyStop](./3.%20Pending_orders/buyStop.md)** - BUY STOP (breakout up)
* **[sellStop](./3.%20Pending_orders/sellStop.md)** - SELL STOP (breakdown)

### 4. Pending Orders with Points (4 methods)

Simplified pending orders via offset.

* **[buyLimitPoints](./4.%20Pending_orders_points/buyLimitPoints.md)** - BUY LIMIT via offset
* **[sellLimitPoints](./4.%20Pending_orders_points/sellLimitPoints.md)** - SELL LIMIT via offset
* **[buyStopPoints](./4.%20Pending_orders_points/buyStopPoints.md)** - BUY STOP via offset
* **[sellStopPoints](./4.%20Pending_orders_points/sellStopPoints.md)** - SELL STOP via offset

### 5. Position Management (5 methods)

Modify and close positions.

* **[modifyPosition](./5.%20Position_management/modifyPosition.md)** - change SL/TP
* **[closePosition](./5.%20Position_management/closePosition.md)** - close (full/partial)
* **[closeAll](./5.%20Position_management/closeAll.md)** - close with filters
* **[closeAllBuy](./5.%20Position_management/closeAllBuy.md)** - close all BUY
* **[closeAllSell](./5.%20Position_management/closeAllSell.md)** - close all SELL

### 6. Advanced Batch Operations (3 methods)

Separate positions vs orders control.

* **[closeAllPositions](./6.%20Advanced_batch_operations/closeAllPositions.md)** - close only positions
* **[closeAllPending](./6.%20Advanced_batch_operations/closeAllPending.md)** - cancel only orders
* **[cancelAll](./6.%20Advanced_batch_operations/cancelAll.md)** - alias for closeAllPending

### 7. Risk Management (3 methods)

Position sizing from $ risk.

* **[calculateVolume](./7.%20Risk_management/calculateVolume.md)** - lot size from $risk + SL
* **[buyByRisk](./7.%20Risk_management/buyByRisk.md)** - BUY with auto volume
* **[sellByRisk](./7.%20Risk_management/sellByRisk.md)** - SELL with auto volume

### 8. Advanced Helpers (4 methods)

Additional utilities.

* **[getVolumeLimits](./8.%20Advanced_helpers/getVolumeLimits.md)** - [min, max, step]
* **[pointsToPips](./8.%20Advanced_helpers/pointsToPips.md)** - broker-aware conversion
* **[priceFromOffsetPoints](./8.%20Advanced_helpers/priceFromOffsetPoints.md)** - price + offset
* **[getTickValueAndSize](./8.%20Advanced_helpers/getTickValueAndSize.md)** - [value, size]

### 9. Account & Position Helpers (10 methods)

Quick account state access.

* **[getBalance](./9.%20Account_and_position_helpers/getBalance.md)** - account balance
* **[getEquity](./9.%20Account_and_position_helpers/getEquity.md)** - account equity
* **[getMargin](./9.%20Account_and_position_helpers/getMargin.md)** - used margin
* **[getFreeMargin](./9.%20Account_and_position_helpers/getFreeMargin.md)** - free margin
* **[getProfit](./9.%20Account_and_position_helpers/getProfit.md)** - floating P/L
* **[hasOpenPositions](./9.%20Account_and_position_helpers/hasOpenPositions.md)** - bool check
* **[getPositionCount](./9.%20Account_and_position_helpers/getPositionCount.md)** - count
* **[normalizePriceDigits](./9.%20Account_and_position_helpers/normalizePriceDigits.md)** - alias
* **[normalizeLots](./9.%20Account_and_position_helpers/normalizeLots.md)** - alias
* **[createTimestamp](./9.%20Account_and_position_helpers/createTimestamp.md)** - 3 variants

### 10. Snapshot Helpers (2 methods + 2 classes)

Complete state snapshots.

* **[getAccountSnapshot](./10.%20Snapshot_helpers/getAccountSnapshot.md)** - full account state
* **[getSymbolSnapshot](./10.%20Snapshot_helpers/getSymbolSnapshot.md)** - full symbol info
* **Classes:** `AccountSnapshot`, `SymbolSnapshot`

### 11. History Helpers (2 methods)

Historical data retrieval.

* **[getOrdersHistoryLastDays](./11.%20History_helpers/getOrdersHistoryLastDays.md)** - orders history
* **[getPositionsHistoryPaged](./11.%20History_helpers/getPositionsHistoryPaged.md)** - paginated history

---

## 🧭 Plain English

**What is MT5Sugar?**

Convenience layer on top of MT5Service/MT5Account with:

* Auto-normalization (volumes, prices)
* Auto symbol selection
* Simplified APIs
* Built-in risk management
* Batch operations

**When to use:**

* ✅ Production trading bots
* ✅ Quick prototypes
* ✅ Risk-managed trading
* ✅ Beginners

**When NOT to use:**

* ❌ Need exact control over parameters
* ❌ Custom normalization logic
* ❌ Advanced order types (STOP_LIMIT)

> Rule of thumb: **Start with MT5Sugar** → drop down only when needed.

---

## Quick choose

### Trading

| Need                          | Use                  | Inputs                    |
| ----------------------------- | -------------------- | ------------------------- |
| Open BUY at market            | `buyMarket`          | symbol, vol, SL, TP       |
| Open SELL at market           | `sellMarket`         | symbol, vol, SL, TP       |
| BUY at price (below)          | `buyLimit`           | symbol, vol, price, SL, TP |
| SELL at price (above)         | `sellLimit`          | symbol, vol, price, SL, TP |
| BUY on breakout (above)       | `buyStop`            | symbol, vol, price, SL, TP |
| SELL on breakdown (below)     | `sellStop`           | symbol, vol, price, SL, TP |
| BUY with points offset        | `buyLimitPoints`     | symbol, vol, offset, SL pts, TP pts |
| BUY with auto volume ($risk)  | `buyByRisk`          | symbol, SL pts, $risk, TP pts |

### Position Management

| Need                          | Use                  | Inputs                    |
| ----------------------------- | -------------------- | ------------------------- |
| Change SL/TP                  | `modifyPosition`     | ticket, SL, TP            |
| Close position                | `closePosition`      | ticket (+ vol)            |
| Close all for symbol          | `closeAll`           | symbol, isBuy filter      |
| Close all BUY                 | `closeAllBuy`        | symbol                    |
| Close all SELL                | `closeAllSell`       | symbol                    |
| Close only positions          | `closeAllPositions`  | symbol, isBuy             |
| Cancel only pending orders    | `closeAllPending`    | symbol, isBuy             |

### Symbol & Account Info

| Need                          | Use                  | Returns                   |
| ----------------------------- | -------------------- | ------------------------- |
| Current Bid/Ask               | `getBid` / `getAsk`  | double                    |
| Point size                    | `getPoint`           | double                    |
| Spread                        | `getSpread`          | int (points)              |
| Normalize price/volume        | `normalizePrice/Volume` | double                 |
| Full symbol snapshot          | `getSymbolSnapshot`  | SymbolSnapshot            |
| Account balance/equity        | `getBalance/Equity`  | double                    |
| Full account snapshot         | `getAccountSnapshot` | AccountSnapshot           |

### Risk Management

| Need                          | Use                  | Inputs                    |
| ----------------------------- | -------------------- | ------------------------- |
| Calculate lot size from $risk | `calculateVolume`    | symbol, SL pts, $risk     |
| Open BUY with auto volume     | `buyByRisk`          | symbol, SL pts, $risk, TP |
| Open SELL with auto volume    | `sellByRisk`         | symbol, SL pts, $risk, TP |

---

## ❌ Cross-refs & gotchas

### Auto-normalization
* Volumes/prices normalized automatically
* Symbols added to Market Watch automatically
* May change input values slightly

### SL/TP rules
**BUY:** SL below entry, TP above entry
**SELL:** SL above entry, TP below entry

**Pending:**
* BUY LIMIT: below Ask, triggers on drop
* SELL LIMIT: above Bid, triggers on rise
* BUY STOP: above Ask, triggers on rise (breakout)
* SELL STOP: below Bid, triggers on drop (breakdown)

### Execution prices
* BUY executes at **Ask** (higher)
* SELL executes at **Bid** (lower)
* Spread = Ask - Bid (cost on entry)

### Risk management
* Uses **points**, not pips
* Check broker min/max volume limits
* 2% rule: max 2% risk per trade

### Batch operations
* Continue on failures
* Return count of successful ops
* Failed ops logged to System.err

### Points vs Pips
* 5-digit: 1 pip = 10 points
* 3-digit: 1 pip = 1 point
* All methods use **points**

### null vs 0
* `null` = keep current / no SL-TP
* `0` / `0.0` = no SL-TP / remove

### Errors
* Throw `ApiExceptionMT5` on failure
* Code **10009** = success
* Common: invalid stops, market closed

---

## 🟢 Minimal snippets

```java
// Market orders
long ticket = sugar.buyMarket("EURUSD", 0.1, 1.08000, 1.09000);
long ticket = sugar.sellMarket("GBPUSD", 0.1, 1.27000, 1.26000);

// Pending with price
double entry = sugar.getAsk("EURUSD") - (50 * sugar.getPoint("EURUSD"));
long ticket = sugar.buyLimit("EURUSD", 0.1, entry, null, null);

// Pending with points offset
long ticket = sugar.buyLimitPoints("EURUSD", 0.1, -50, 40, 120);

// Risk-based
double risk = sugar.getBalance() * 0.02;
long ticket = sugar.buyByRisk("EURUSD", 50, risk, 100);

// Close positions
sugar.closePosition(123456789);
int closed = sugar.closeAll("EURUSD");
int closed = sugar.closeAllBuy(null);

// Modify
sugar.modifyPosition(123456789, newSL, newTP);

// Info
double balance = sugar.getBalance();
double bid = sugar.getBid("EURUSD");
var snapshot = sugar.getAccountSnapshot();
var symSnap = sugar.getSymbolSnapshot("EURUSD");

// Calculate volume
double vol = sugar.calculateVolume("EURUSD", 50, 100.0);

// History
var history = sugar.getOrdersHistoryLastDays(7, null);
var posHistory = sugar.getPositionsHistoryPaged(0, 50);
```

---