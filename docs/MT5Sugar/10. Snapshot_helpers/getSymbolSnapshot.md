# 📸 Get Symbol Snapshot

> **Snapshot method:** retrieves complete symbol information in one call. Returns SymbolSnapshot object with all key metrics.

**API Information:**

* **Sugar method:** `MT5Sugar.getSymbolSnapshot(String symbol)`
* **Returns:** `SymbolSnapshot` - Object containing all symbol metrics
* **Underlying:** Multiple `symbolInfo*()` calls

---

## 🔽 Input

| Parameter | Type     | Required | Description              |
| --------- | -------- | -------- | ------------------------ |
| `symbol`  | `String` | ✅       | Symbol name              |

---

## ⬆️ Output

**Returns:** `SymbolSnapshot` object with fields:

- `String name` - Symbol name
- `double bid` - Current Bid price
- `double ask` - Current Ask price
- `double point` - Point size
- `int digits` - Decimal digits
- `int spread` - Spread in points
- `double volumeMin` - Minimum volume
- `double volumeMax` - Maximum volume
- `double volumeStep` - Volume step
- `double tickValue` - Tick value
- `double tickSize` - Tick size

**Throws:** `ApiExceptionMT5` if request fails

---

## 🔗 Usage Examples

### 1) Simple snapshot

```java
var snapshot = sugar.getSymbolSnapshot("EURUSD");

System.out.printf("Symbol: %s%n", snapshot.name);
System.out.printf("Bid: %.5f | Ask: %.5f%n", snapshot.bid, snapshot.ask);
System.out.printf("Spread: %d points%n", snapshot.spread);
System.out.printf("Volume: [%.2f - %.2f] step %.2f%n",
    snapshot.volumeMin, snapshot.volumeMax, snapshot.volumeStep);
```

### 2) Using toString()

```java
var snapshot = sugar.getSymbolSnapshot("GBPUSD");
System.out.println(snapshot.toString());

// Output:
// Symbol[GBPUSD, bid=1.26340, ask=1.26350, spread=10, point=0.00001,
//        digits=5, vol=[0.01-100.00 step 0.01]]
```

### 3) Compare symbols

```java
String[] symbols = {"EURUSD", "GBPUSD", "USDJPY"};

for (String symbol : symbols) {
    var snap = sugar.getSymbolSnapshot(symbol);
    System.out.printf("%s: spread=%dp, point=%.5f%n",
        snap.name, snap.spread, snap.point);
}
```

---

## 📌 Important Notes

* **One call:** Fetches all symbol data efficiently
* **Immutable:** Snapshot is point-in-time data
* **toString():** Built-in formatted string representation

---

## See also

* **Account snapshot:** [`getAccountSnapshot()`](./getAccountSnapshot.md)
* **Individual getters:** [`getBid()`](../1.%20Symbol_helpers/getBid.md), [`getSpread()`](../1.%20Symbol_helpers/getSpread.md)
