# 📜 Get Positions History (Paginated)

> **History method:** retrieves positions history with pagination support. Useful for large datasets.

**API Information:**

* **Sugar method:** `MT5Sugar.getPositionsHistoryPaged(int page, int itemsPerPage)`
* **Returns:** `PositionsHistoryData` - Proto message with historical positions
* **Underlying:** `MT5Service.positionsHistory()` with pagination

---

## 🔽 Input

| Parameter      | Type  | Required | Description                        |
| -------------- | ----- | -------- | ---------------------------------- |
| `page`         | `int` | ✅       | Page number (0-based)              |
| `itemsPerPage` | `int` | ✅       | Number of items per page           |

---

## ⬆️ Output

**Returns:** `Mt5TermApiAccountHelper.PositionsHistoryData` - Historical positions page

**Throws:** `ApiExceptionMT5` if request fails

**Execution:**
- Sorts by open time descending (newest first)
- Returns one page of results
- No time filtering (all history)

---

## 💬 Just the essentials

* **What it is.** Paginated positions history retrieval.
* **Why you need it.** Handle large history without loading everything.
* **Use case.** Performance analysis, reports, trade logs.

---

## 🔗 Usage Examples

### 1) First page (latest 50 positions)

```java
int page = 0;
int itemsPerPage = 50;

var history = sugar.getPositionsHistoryPaged(page, itemsPerPage);

System.out.printf("Page 1: %d positions%n",
    history.getPositionsCount());

for (var pos : history.getPositionsList()) {
    System.out.printf("  #%d %s: $%.2f profit%n",
        pos.getTicket(),
        pos.getSymbol(),
        pos.getProfit());
}
```

### 2) Iterate through all pages

```java
int itemsPerPage = 100;
int page = 0;

while (true) {
    var history = sugar.getPositionsHistoryPaged(page, itemsPerPage);

    if (history.getPositionsCount() == 0) {
        break; // No more data
    }

    System.out.printf("Page %d: %d positions%n",
        page + 1, history.getPositionsCount());

    // Process positions...

    page++;
}

System.out.printf("✅ Processed %d pages%n", page);
```

### 3) Calculate total history profit

```java
int itemsPerPage = 100;
int page = 0;
double totalProfit = 0.0;
int totalTrades = 0;

while (true) {
    var history = sugar.getPositionsHistoryPaged(page, itemsPerPage);

    if (history.getPositionsCount() == 0) break;

    for (var pos : history.getPositionsList()) {
        totalProfit += pos.getProfit();
        totalTrades++;
    }

    page++;
}

System.out.printf("Total history:%n");
System.out.printf("  Trades: %d%n", totalTrades);
System.out.printf("  Profit: $%.2f%n", totalProfit);
```

---

## 📌 Important Notes

* **Pages:** 0-based (first page = 0)
* **Sort order:** Newest first (open time descending)
* **No time filter:** Returns all history
* **Empty page:** Indicates end of data

---

## See also

* **Orders history:** [`getOrdersHistoryLastDays()`](./getOrdersHistoryLastDays.md)
* **Low-level:** [`positionsHistory()`](../../MT5Account/3.%20Positions_and_orders/PositionsHistory.md) (if exists)
