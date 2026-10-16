# 🔴 Open SELL Market Order

> **Convenience method:** opens a SELL position at current Bid price with optional Stop Loss and Take Profit. Simplified alternative to low-level OrderSend.

**API Information:**

* **Sugar method:** `MT5Sugar.sellMarket(String symbol, double volume, Double stopLoss, Double takeProfit)`
* **Sugar method (with comment):** `MT5Sugar.sellMarket(String symbol, double volume, Double stopLoss, Double takeProfit, String comment)`
* **Underlying methods:**
  - `MT5Service.orderSend()` - low-level order placement
  - [`ensureSymbolSelected()`](../1.%20Symbol_helpers/ensureSymbolSelected.md) - auto symbol selection
  - [`normalizeVolume()`](../1.%20Symbol_helpers/normalizeVolume.md) - auto volume normalization
* **Source:** MT5Sugar convenience layer

---

## 🔽 Input

| Parameter    | Type     | Required | Description                                      |
| ------------ | -------- | -------- | ------------------------------------------------ |
| `symbol`     | `String` | ✅       | Symbol name (e.g., "EURUSD")                     |
| `volume`     | `double` | ✅       | Volume in lots (e.g., 0.1)                       |
| `stopLoss`   | `Double` | ✅       | Stop Loss price (null or 0 = no SL)              |
| `takeProfit` | `Double` | ✅       | Take Profit price (null or 0 = no TP)            |
| `comment`    | `String` | ⚪       | Order comment (optional, default = "")           |

---

## ⬆️ Output

**Returns:** `long` - Order ticket number (position ticket)

**Throws:** `ApiExceptionMT5` if order fails (contains error code and description)

**Execution:**
- Order executes at current **Bid** price
- Position opens immediately (market execution)
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Sell at current market price instantly.
* **Why you need it.** Simplest way to open SELL position.
* **Auto-handled.** Symbol selection, volume normalization, error checking.
* **SL/TP.** Optional - pass null or 0 to skip.

---

## 🎯 Purpose

Use this method when you need to:

* Open SELL position at current market price.
* Execute sell-side market orders with SL/TP.
* Enter short position immediately (no pending order).
* Simplify trading logic (vs manual OrderSend construction).

---

## 🔗 Usage Examples

### 1) Simple SELL without SL/TP

```java
String symbol = "EURUSD";
double volume = 0.1; // 0.1 lot

// Sell at market, no SL/TP
long ticket = sugar.sellMarket(symbol, volume, null, null);

System.out.printf("SELL position opened: #%d%n", ticket);
System.out.printf("Entry price (Bid): %.5f%n", sugar.getBid(symbol));

// Output:
// SELL position opened: #123456789
// Entry price (Bid): 1.12340
```

### 2) SELL with Stop Loss and Take Profit

```java
String symbol = "GBPUSD";
double volume = 0.5;
double bid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);

// SL 50 points above entry
double stopLoss = bid + (50 * point);

// TP 100 points below entry
double takeProfit = bid - (100 * point);

long ticket = sugar.sellMarket(symbol, volume, stopLoss, takeProfit);

System.out.printf("SELL %s: %.2f lots%n", symbol, volume);
System.out.printf("  Ticket: #%d%n", ticket);
System.out.printf("  Entry: %.5f%n", bid);
System.out.printf("  SL: %.5f (+50p)%n", stopLoss);
System.out.printf("  TP: %.5f (-100p)%n", takeProfit);

// Output:
// SELL GBPUSD: 0.50 lots
//   Ticket: #987654321
//   Entry: 1.26340
//   SL: 1.26390 (+50p)
//   TP: 1.26240 (-100p)
```

### 3) SELL with risk management

```java
String symbol = "EURUSD";
double balance = sugar.getBalance();
double riskPercent = 2.0; // Risk 2% of balance
int slPoints = 50;

// Calculate risk amount
double riskAmount = balance * (riskPercent / 100.0);

// Calculate volume based on risk
double volume = sugar.calculateVolume(symbol, slPoints, riskAmount);

// Calculate SL price
double bid = sugar.getBid(symbol);
double point = sugar.getPoint(symbol);
double stopLoss = bid + (slPoints * point);

// Place order
long ticket = sugar.sellMarket(symbol, volume, stopLoss, null);

System.out.printf("Risk-managed SELL:%n");
System.out.printf("  Balance: $%.2f%n", balance);
System.out.printf("  Risk: %.1f%% ($%.2f)%n", riskPercent, riskAmount);
System.out.printf("  Volume: %.2f lots%n", volume);
System.out.printf("  SL: %.5f (%d points)%n", stopLoss, slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 4) SELL with comment and error handling

```java
String symbol = "USDJPY";
double volume = 0.1;
String comment = "Strategy-B Short Entry";

try {
    long ticket = sugar.sellMarket(symbol, volume, null, null, comment);

    System.out.printf("✅ SELL order placed successfully%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Comment: %s%n", comment);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Order failed:%n");
    System.err.printf("   Code: %d%n", e.getError().getMqlErrorTradeIntCode());
    System.err.printf("   Message: %s%n", e.getError().getErrorMessage());

    // Handle error (e.g., retry, log, alert)
}
```

### 5) SELL on resistance breakout

```java
String symbol = "EURUSD";
double volume = 0.1;
double resistanceLevel = 1.12500;

System.out.printf("Monitoring %s for resistance break...%n", symbol);

while (true) {
    double currentBid = sugar.getBid(symbol);

    if (currentBid >= resistanceLevel) {
        // Price broke above resistance, enter SELL (expecting reversal)
        double sl = currentBid + (50 * sugar.getPoint(symbol));
        double tp = currentBid - (100 * sugar.getPoint(symbol));

        long ticket = sugar.sellMarket(symbol, volume, sl, tp);

        System.out.printf("✅ Resistance broken! Entered SELL%n");
        System.out.printf("   Bid: %.5f%n", currentBid);
        System.out.printf("   Ticket: #%d%n", ticket);
        break;
    }

    System.out.printf("Bid: %.5f (waiting for >= %.5f)%n",
        currentBid, resistanceLevel);
    Thread.sleep(1000);
}
```

### 6) Hedging with BUY and SELL

```java
String symbol = "XAUUSD";
double volume = 0.01;

// Open BUY position
long buyTicket = sugar.buyMarket(symbol, volume, null, null);
double buyEntry = sugar.getAsk(symbol);

// Immediately hedge with SELL
long sellTicket = sugar.sellMarket(symbol, volume, null, null);
double sellEntry = sugar.getBid(symbol);

// Locked position (profit = spread cost)
double spread = buyEntry - sellEntry;

System.out.printf("Hedged position on %s:%n", symbol);
System.out.printf("  BUY #%d at %.2f%n", buyTicket, buyEntry);
System.out.printf("  SELL #%d at %.2f%n", sellTicket, sellEntry);
System.out.printf("  Lock cost: %.2f%n", spread);
```

---

## 📌 Important Notes

* **Execution price:**
  - SELL executes at **Bid** (lower price)
  - Price may differ slightly due to slippage
  - Market orders execute immediately

* **Stop Loss placement:**
  - Must be **above** Bid for SELL
  - Typical: `SL = Bid + (points * point)`
  - Use [`pointsToPrice()`](../1.%20Symbol_helpers/pointsToPrice.md) for convenience

* **Take Profit placement:**
  - Must be **below** Bid for SELL
  - Typical: `TP = Bid - (points * point)`

* **Auto-normalization:**
  - Volume is automatically normalized to symbol's min/max/step
  - No need to call `normalizeVolume()` manually

* **Auto symbol selection:**
  - Method calls `ensureSymbolSelected()` internally
  - Symbol added to Market Watch if missing

* **Error handling:**
  - Throws `ApiExceptionMT5` if order fails
  - Error contains return code and description
  - Return code 10009 = success

* **null vs 0 for SL/TP:**
  - Both `null` and `0` mean "no SL/TP"
  - Internally converted to 0.0

**SELL order structure:**
```
   ↑
   | SL: above entry (1.12390)
   |
Entry: Bid (1.12340)
   |
   | TP: below entry (1.12240)
   ↓
```

**Common patterns:**
```java
// Pattern 1: No SL/TP
sugar.sellMarket(symbol, volume, null, null);

// Pattern 2: Only SL
sugar.sellMarket(symbol, volume, stopLoss, null);

// Pattern 3: Only TP
sugar.sellMarket(symbol, volume, null, takeProfit);

// Pattern 4: Both SL and TP
sugar.sellMarket(symbol, volume, stopLoss, takeProfit);

// Pattern 5: With comment
sugar.sellMarket(symbol, volume, stopLoss, takeProfit, "Strategy B");
```

**BUY vs SELL:**

| Aspect          | BUY                  | SELL                 |
|-----------------|----------------------|----------------------|
| Entry price     | Ask (higher)         | Bid (lower)          |
| Stop Loss       | Below entry          | Above entry          |
| Take Profit     | Above entry          | Below entry          |
| Profit when     | Price goes UP        | Price goes DOWN      |
| SL calculation  | Entry - (points × p) | Entry + (points × p) |
| TP calculation  | Entry + (points × p) | Entry - (points × p) |

---

## See also

* **Low-level method:** [`OrderSend`](../../MT5Account/5.%20Trading/OrderSend.md) - underlying implementation
* **Related:** [`buyMarket()`](./buyMarket.md) - open BUY position
* **Pending orders:** [`sellLimit()`](../3.%20Pending_orders/sellLimit.md), [`sellStop()`](../3.%20Pending_orders/sellStop.md)
* **Position management:** [`closePosition()`](../5.%20Position_management/closePosition.md)
* **Price helpers:** [`getBid()`](../1.%20Symbol_helpers/getBid.md), [`pointsToPrice()`](../1.%20Symbol_helpers/pointsToPrice.md)
* **Risk management:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md)
