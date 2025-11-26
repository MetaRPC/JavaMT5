# 🕐 Create Timestamp (3 variants)

> **Helper methods:** creates protobuf Timestamp objects for history queries. Three variants: from epoch seconds, from milliseconds, or current time.

**API Information:**

* **Sugar method (seconds):** `MT5Sugar.createTimestamp(long epochSeconds)`
* **Sugar method (millis):** `MT5Sugar.createTimestampFromMillis(long epochMillis)`
* **Sugar method (now):** `MT5Sugar.createTimestampNow()`
* **Returns:** `com.google.protobuf.Timestamp`

---

## 🔽 Input

| Variant           | Parameter     | Type   | Description                      |
| ----------------- | ------------- | ------ | -------------------------------- |
| `createTimestamp` | `epochSeconds` | `long` | Seconds since Unix epoch        |
| `...FromMillis`   | `epochMillis` | `long` | Milliseconds since Unix epoch   |
| `...Now`          | none          | -      | Uses current system time        |

---

## ⬆️ Output

**Returns:** `com.google.protobuf.Timestamp` - Protobuf timestamp object

---

## 🔗 Usage Examples

### 1) From seconds

```java
long epochSeconds = 1672531200L; // Jan 1, 2023
var timestamp = sugar.createTimestamp(epochSeconds);
```

### 2) From milliseconds

```java
long epochMillis = System.currentTimeMillis();
var timestamp = sugar.createTimestampFromMillis(epochMillis);
```

### 3) Current time

```java
var now = sugar.createTimestampNow();
```

---

## See also

* **Used in history queries:** [`getOrdersHistoryLastDays()`](../11.%20History_helpers/getOrdersHistoryLastDays.md)
