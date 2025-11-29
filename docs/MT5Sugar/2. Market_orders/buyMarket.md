# 🟢 Open BUY Market Order

> **Convenience method:** opens a BUY position at current Ask price with optional Stop Loss and Take Profit. Simplified alternative to low-level OrderSend.

**API Information:**

* **Sugar method:** `MT5Sugar.buyMarket(String symbol, double volume, Double stopLoss, Double takeProfit)`
* **Sugar method (with comment):** `MT5Sugar.buyMarket(String symbol, double volume, Double stopLoss, Double takeProfit, String comment)`
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
- Order executes at current **Ask** price
- Position opens immediately (market execution)
- Return code **10009** = success (TRADE_RETCODE_DONE)

---

## 💬 Just the essentials

* **What it is.** Buy at current market price instantly.
* **Why you need it.** Simplest way to open BUY position.
* **Auto-handled.** Symbol selection, volume normalization, error checking.
* **SL/TP.** Optional - pass null or 0 to skip.

---

## 🎯 Purpose

Use this method when you need to:

* Open BUY position at current market price.
* Execute buy-side market orders with SL/TP.
* Enter long position immediately (no pending order).
* Simplify trading logic (vs manual OrderSend construction).

---

## 🔗 Usage Examples

### 1) Simple BUY without SL/TP

```java
String symbol = "EURUSD";
double volume = 0.1; // 0.1 lot

// Buy at market, no SL/TP
long ticket = sugar.buyMarket(symbol, volume, null, null);

System.out.printf("BUY position opened: #%d%n", ticket);
System.out.printf("Entry price (Ask): %.5f%n", sugar.getAsk(symbol));

// Output:
// BUY position opened: #123456789
// Entry price (Ask): 1.12350
```

### 2) BUY with Stop Loss and Take Profit

```java
String symbol = "GBPUSD";
double volume = 0.5;
double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// SL 50 points below entry
double stopLoss = ask - (50 * point);

// TP 100 points above entry
double takeProfit = ask + (100 * point);

long ticket = sugar.buyMarket(symbol, volume, stopLoss, takeProfit);

System.out.printf("BUY %s: %.2f lots%n", symbol, volume);
System.out.printf("  Ticket: #%d%n", ticket);
System.out.printf("  Entry: %.5f%n", ask);
System.out.printf("  SL: %.5f (-50p)%n", stopLoss);
System.out.printf("  TP: %.5f (+100p)%n", takeProfit);

// Output:
// BUY GBPUSD: 0.50 lots
//   Ticket: #987654321
//   Entry: 1.26355
//   SL: 1.26305 (-50p)
//   TP: 1.26455 (+100p)
```

### 3) BUY with risk management

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
double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);
double stopLoss = ask - (slPoints * point);

// Place order
long ticket = sugar.buyMarket(symbol, volume, stopLoss, null);

System.out.printf("Risk-managed BUY:%n");
System.out.printf("  Balance: $%.2f%n", balance);
System.out.printf("  Risk: %.1f%% ($%.2f)%n", riskPercent, riskAmount);
System.out.printf("  Volume: %.2f lots%n", volume);
System.out.printf("  SL: %.5f (%d points)%n", stopLoss, slPoints);
System.out.printf("  Ticket: #%d%n", ticket);
```

### 4) BUY with comment and error handling

```java
String symbol = "USDJPY";
double volume = 0.1;
String comment = "Strategy-A Entry #1";

try {
    long ticket = sugar.buyMarket(symbol, volume, null, null, comment);

    System.out.printf("✅ BUY order placed successfully%n");
    System.out.printf("   Ticket: #%d%n", ticket);
    System.out.printf("   Comment: %s%n", comment);

} catch (ApiExceptionMT5 e) {
    System.err.printf("❌ Order failed:%n");
    System.err.printf("   Code: %d%n", e.getError().getMqlErrorTradeIntCode());
    System.err.printf("   Message: %s%n", e.getError().getErrorMessage());

    // Handle error (e.g., retry, log, alert)
}
```

### 5) Multiple BUY positions with different parameters

```java
String symbol = "XAUUSD";
double baseVolume = 0.01;
double ask = sugar.getAsk(symbol);
double point = sugar.getPoint(symbol);

// Position 1: No SL/TP (manual management)
long ticket1 = sugar.buyMarket(symbol, baseVolume, null, null);

// Position 2: Tight SL, close TP (scalping)
double sl2 = ask - (20 * point);
double tp2 = ask + (30 * point);
long ticket2 = sugar.buyMarket(symbol, baseVolume, sl2, tp2);

// Position 3: Wide SL, far TP (swing)
double sl3 = ask - (100 * point);
double tp3 = ask + (300 * point);
long ticket3 = sugar.buyMarket(symbol, baseVolume, sl3, tp3);

System.out.printf("Opened 3 BUY positions on %s:%n", symbol);
System.out.printf("  #%d: No SL/TP%n", ticket1);
System.out.printf("  #%d: Scalp (20p SL, 30p TP)%n", ticket2);
System.out.printf("  #%d: Swing (100p SL, 300p TP)%n", ticket3);
```

### 6) Conditional market entry

```java
String symbol = "EURUSD";
double volume = 0.1;
double targetAsk = 1.12500;

System.out.printf("Waiting for %s Ask <= %.5f...%n", symbol, targetAsk);

while (true) {
    double currentAsk = sugar.getAsk(symbol);

    if (currentAsk <= targetAsk) {
        // Price reached target, enter BUY
        double sl = currentAsk - (50 * sugar.getPoint(symbol));
        double tp = currentAsk + (100 * sugar.getPoint(symbol));

        long ticket = sugar.buyMarket(symbol, volume, sl, tp);

        System.out.printf("✅ Entered BUY at Ask: %.5f%n", currentAsk);
        System.out.printf("   Ticket: #%d%n", ticket);
        break;
    }

    System.out.printf("Current Ask: %.5f (waiting...)%n", currentAsk);
    Thread.sleep(1000); // Check every second
}
```

---

## 📌 Important Notes

* **Execution price:**
  - BUY executes at **Ask** (higher price)
  - Price may differ slightly due to slippage
  - Market orders execute immediately

* **Stop Loss placement:**
  - Must be **below** Ask for BUY
  - Typical: `SL = Ask - (points * point)`
  - Use [`pointsToPrice()`](../1.%20Symbol_helpers/pointsToPrice.md) for convenience

* **Take Profit placement:**
  - Must be **above** Ask for BUY
  - Typical: `TP = Ask + (points * point)`

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

**BUY order structure:**
```
Entry: Ask (1.12350)
   ↑
   | TP: above entry (1.12450)
   |
Entry
   |
   | SL: below entry (1.12300)
   ↓
```

**Common patterns:**
```java
// Pattern 1: No SL/TP
sugar.buyMarket(symbol, volume, null, null);

// Pattern 2: Only SL
sugar.buyMarket(symbol, volume, stopLoss, null);

// Pattern 3: Only TP
sugar.buyMarket(symbol, volume, null, takeProfit);

// Pattern 4: Both SL and TP
sugar.buyMarket(symbol, volume, stopLoss, takeProfit);

// Pattern 5: With comment
sugar.buyMarket(symbol, volume, stopLoss, takeProfit, "Strategy A");
```

---

## See also

* **Low-level method:** [`OrderSend`](../../MT5Account/5.%20Trading/OrderSend.md) - underlying implementation
* **Related:** [`sellMarket()`](./sellMarket.md) - open SELL position
* **Pending orders:** [`buyLimit()`](../3.%20Pending_orders/buyLimit.md), [`buyStop()`](../3.%20Pending_orders/buyStop.md)
* **Position management:** [`closePosition()`](../5.%20Position_management/closePosition.md)
* **Price helpers:** [`getAsk()`](../1.%20Symbol_helpers/getAsk.md), [`pointsToPrice()`](../1.%20Symbol_helpers/pointsToPrice.md)
* **Risk management:** [`calculateVolume()`](../7.%20Risk_management/calculateVolume.md)
