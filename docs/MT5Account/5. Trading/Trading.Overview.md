# MT5Account · Trading - Overview

> Order execution, position management, margin calculations, and trade validation. Use this page to choose the right API for trading operations and pre-trade calculations.

## 📁 What lives here

* **[OrderSend](./OrderSend.md)** - **place orders** (market or pending) to open positions.
* **[OrderModify](./OrderModify.md)** - **modify** existing positions or pending orders (SL/TP/price/expiration).
* **[OrderClose](./OrderClose.md)** - **close positions** or delete pending orders (full or partial).
* **[OrderCalcMargin](./OrderCalcMargin.md)** - **calculate margin** required for a trade (pre-trade check).
* **[OrderCalcProfit](./OrderCalcProfit.md)** - **calculate profit/loss** for a trade scenario (risk/reward analysis).
* **[OrderCheck](./OrderCheck.md)** - **validate trade request** before sending (check funds, margin, parameters).

---

## 🧭 Plain English

* **OrderSend** → **execute trades** (market BUY/SELL or pending orders).
* **OrderModify** → **change SL/TP** or order parameters after placement.
* **OrderClose** → **exit positions** or cancel pending orders.
* **OrderCalcMargin** → **"How much margin do I need?"** before placing order.
* **OrderCalcProfit** → **"How much will I make/lose?"** for profit targets and stop losses.
* **OrderCheck** → **"Can I afford this trade?"** validation before execution.

> Rule of thumb: **Calculate first** (CalcMargin, CalcProfit, OrderCheck) → then **execute** (OrderSend) → then **manage** (OrderModify, OrderClose).

---

## Quick choose

| If you need…                                     | Use              | Returns                    | Key inputs                          |
| ------------------------------------------------ | ---------------- | -------------------------- | ----------------------------------- |
| Open position or place pending order             | `OrderSend`      | Order/deal ticket          | Symbol, operation, volume, price, SL/TP |
| Modify SL/TP or order parameters                 | `OrderModify`    | Success/failure            | Ticket, new SL/TP/price/expiration  |
| Close position or cancel pending order           | `OrderClose`     | Success/failure            | Ticket, volume, slippage            |
| Calculate margin required for trade              | `OrderCalcMargin`| Margin amount              | Symbol, order type, volume, price   |
| Calculate profit/loss for trade scenario         | `OrderCalcProfit`| P/L amount                 | Symbol, order type, volume, open/close price |
| Validate trade before execution                  | `OrderCheck`     | Validation result + estimates | Trade request object             |

---

## ❌ Cross‑refs & gotchas

* **Return code 10009** = success; other codes = failure (check comment).
* **Slippage** in points, not pips (EURUSD: 10 points = 1 pip for 5-digit).
* **Market orders**: price optional (use 0 or current ask/bid).
* **Pending orders**: price required, must be away from current price.
* **SL/TP distance**: must meet symbol's Stops Level (see SymbolInfoInteger).
* **Partial close**: specify volume < position volume.
* **Margin calculation**: returns margin in account currency.
* **Profit calculation**: excludes commission, swap, spread.
* **OrderCheck**: validates parameters and funds, but doesn't execute.
* **Magic numbers**: use to identify your orders (Expert Advisor ID).

---

## 🟢 Minimal snippets

```java
// Open market BUY position
var request = Mt5TermApiTradingHelper.OrderSendRequest.newBuilder()
    .setSymbol("EURUSD")
    .setOperation(Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.TMT5_ORDER_TYPE_BUY)
    .setVolume(0.1)
    .setSlippage(10)
    .setStopLoss(1.08000)
    .setTakeProfit(1.12000)
    .build();

var reply = account.orderSend(request);
if (reply.getData().getReturnedCode() == 10009) {
    System.out.printf("✅ Position opened: #%d%n", reply.getData().getOrder());
}
```

```java
// Modify SL/TP of existing position
var request = Mt5TermApiTradingHelper.OrderModifyRequest.newBuilder()
    .setTicket(123456789)
    .setStopLoss(1.09000)
    .setTakeProfit(1.11000)
    .build();

var reply = account.orderModify(request);
if (reply.getData().getReturnedCode() == 10009) {
    System.out.println("✅ SL/TP modified");
}
```

```java
// Close position (full close: volume = 0)
var reply = account.orderClose(123456789, 0.0, 10);
if (reply.getData().getReturnedCode() == 10009) {
    System.out.println("✅ Position closed");
}
```

```java
// Calculate margin before trading
var reply = account.orderCalcMargin(
    "GBPUSD",
    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
    1.0,  // 1 lot
    0.0   // current price
);
double margin = reply.getData().getMargin();
System.out.printf("Required margin: $%.2f%n", margin);
```

```java
// Calculate profit for trade scenario
var reply = account.orderCalcProfit(
    "EURUSD",
    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
    1.0,      // 1 lot
    1.10000,  // entry
    1.10500   // exit
);
double profit = reply.getData().getProfit();
System.out.printf("Potential profit: $%.2f%n", profit);
```

```java
// Validate trade before execution
var tradeRequest = Mt5TermApiTradeFunctions.MrpcMqlTradeRequest.newBuilder()
    .setAction(Mt5TermApiTradeFunctions.MRPC_ENUM_TRADE_REQUEST_ACTIONS.TRADE_ACTION_DEAL)
    .setSymbol("XAUUSD")
    .setVolume(0.1)
    .setOrderType(Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY)
    .build();

var reply = account.orderCheck(tradeRequest);
var result = reply.getData().getMqlTradeCheckResult();

if (result.getReturnedCode() == 10009) {
    System.out.printf("✅ Trade valid | Margin: $%.2f | Free margin after: $%.2f%n",
        result.getMargin(), result.getFreeMargin());
} else {
    System.out.printf("❌ Trade invalid: %s%n", result.getComment());
}
```

```java
// Risk/reward calculation
double entry = 1.10000;
double stopLoss = 1.09500;
double takeProfit = 1.11000;

// Calculate loss at SL
var lossReply = account.orderCalcProfit("EURUSD",
    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
    1.0, entry, stopLoss);
double loss = Math.abs(lossReply.getData().getProfit());

// Calculate profit at TP
var profitReply = account.orderCalcProfit("EURUSD",
    Mt5TermApiTradeFunctions.ENUM_ORDER_TYPE_TF.ORDER_TYPE_TF_BUY,
    1.0, entry, takeProfit);
double profit = profitReply.getData().getProfit();

double riskReward = profit / loss;
System.out.printf("Risk/Reward: 1:%.2f%n", riskReward);
```

---

## See also

* **Subscriptions:** [`OnTrade`](../6.%20Subscriptions/OnTrade.md) - real-time trade execution events
* **Subscriptions:** [`OnTradeTransaction`](../6.%20Subscriptions/OnTradeTransaction.md) - detailed transaction log
* **Positions:** [`OpenedOrders`](../3.%20Positions_and_orders/OpenedOrders.md) - view current positions
* **Account:** [`AccountSummary`](../1.%20Account_information/AccountSummary.md) - check available margin
* **Symbol info:** [`SymbolInfoDouble`](../2.%20Symbol_information/SymbolInfoDouble.md) - get stops level, min volume
