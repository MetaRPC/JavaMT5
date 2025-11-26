# 💰 Calculate Price from Points Offset

> **Helper method:** calculates price from current market price with points offset. Handles BUY (Ask) vs SELL (Bid) automatically.

**API Information:**

* **Sugar method:** `MT5Sugar.priceFromOffsetPoints(String symbol, boolean isBuy, double pointsOffset)`
* **Returns:** `double` - Calculated price
* **Formula:**
  - BUY: `Ask + (pointsOffset × point)`
  - SELL: `Bid + (pointsOffset × point)`

---

## 🔽 Input

| Parameter      | Type      | Required | Description                        |
| -------------- | --------- | -------- | ---------------------------------- |
| `symbol`       | `String`  | ✅       | Symbol name                        |
| `isBuy`        | `boolean` | ✅       | true=use Ask, false=use Bid        |
| `pointsOffset` | `double`  | ✅       | Points offset (positive or negative) |

---

## ⬆️ Output

**Returns:** `double` - Calculated price

---

## 🔗 Usage Example

```java
String symbol = "EURUSD";

// BUY: 50 points above Ask
double buyPrice = sugar.priceFromOffsetPoints(symbol, true, 50);

// SELL: 50 points below Bid
double sellPrice = sugar.priceFromOffsetPoints(symbol, false, -50);

System.out.printf("BUY entry 50p above: %.5f%n", buyPrice);
System.out.printf("SELL entry 50p below: %.5f%n", sellPrice);
```

---

## See also

* **Get prices:** [`getAsk()`](../1.%20Symbol_helpers/getAsk.md), [`getBid()`](../1.%20Symbol_helpers/getBid.md)
* **Points offset orders:** [`buyStopPoints()`](../4.%20Pending_orders_points/buyStopPoints.md)
