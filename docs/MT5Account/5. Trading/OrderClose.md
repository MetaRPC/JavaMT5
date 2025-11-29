# ❌ Close Position or Delete Pending Order

> **Request:** close an open position (full or partial) or delete a pending order. The primary method for exiting trades.

**API Information:**

* **SDK wrapper:** `MT5Account.orderClose(...)` (from package `io.metarpc.mt5`)
* **gRPC service:** `mt5_term_api.TradingHelper`
* **Proto definition:** `OrderClose` (defined in `mt5-term-api-trading-helper.proto`)

### RPC

* **Service:** `mt5_term_api.TradingHelper`
* **Method:** `OrderClose(OrderCloseRequest) → OrderCloseReply`
* **Low‑level client (generated):** `TradingHelperGrpc.TradingHelperBlockingStub.orderClose(request)`
* **SDK wrapper (high-level):**

```java
package io.metarpc.mt5;

public class MT5Account {
    /**
     * Closes an open position or deletes a pending order.
     * For positions, you can specify partial closure by providing a volume less than the total position size.
     *
     * @param ticket The ticket number of the order or position to close
     * @param volume Volume to close in lots (use position's full volume to close completely)
     * @param slippage Maximum acceptable price slippage in points
     * @return Response indicating success or failure of the closure
     * @throws ApiExceptionMT5 if the closure fails or connection is lost
     */
    public Mt5TermApiTradingHelper.OrderCloseReply orderClose(
        long ticket,
        double volume,
        int slippage) throws ApiExceptionMT5;
}
```

**Request message:** `OrderCloseRequest { ticket, volume, slippage }`

**Reply message:** `OrderCloseReply { data: OrderCloseData }` or `{ error: Error }`

---

## 🔽 Input

| Parameter  | Type     | Required | Description                                          |
| ---------- | -------- | -------- | ---------------------------------------------------- |
| `ticket`   | `long`   | ✅       | Order or position ticket number                      |
| `volume`   | `double` | ✅       | Volume to close in lots                              |
| `slippage` | `int`    | ✅       | Maximum price slippage in points                     |

**Volume Notes:**
- For full closure: use position's total volume
- For partial closure: use volume less than total
- For pending orders: volume is ignored (always deleted fully)

---

## ⬆️ Output - `OrderCloseData`

| Field                      | Type                      | Description                                          |
| -------------------------- | ------------------------- | ---------------------------------------------------- |
| `returned_code`            | `int`                     | Operation return code (10009 = success)              |
| `returned_string_code`     | `String`                  | String representation of return code                 |
| `returned_code_description`| `String`                  | Human-readable description                           |
| `close_mode`               | `MRPC_ORDER_CLOSE_MODE`   | Type of closure performed                            |

### Enum: `MRPC_ORDER_CLOSE_MODE`

| Value                            | Number | Description                                          |
| -------------------------------- | ------ | ---------------------------------------------------- |
| `MRPC_MARKET_ORDER_CLOSE`        | 0      | Full position closed                                 |
| `MRPC_MARKET_ORDER_PARTIAL_CLOSE`| 1      | Partial position closed                              |
| `MRPC_PENDING_ORDER_REMOVE`      | 2      | Pending order deleted                                |

---

## 💬 Just the essentials

* **What it is.** RPC to close positions or delete pending orders.
* **Full closure.** Use position's full volume to close completely.
* **Partial closure.** Use volume less than total for partial close.
* **Pending orders.** Volume parameter ignored - always deleted fully.
* **Slippage.** In points (not pips) - maximum acceptable price deviation.
* **Return code.** 10009 = success.

---

## 🎯 Purpose

Use this method when you need to:

* Close open positions (full or partial).
* Take profits or cut losses manually.
* Delete pending orders that are no longer needed.
* Implement exit strategies.
* Close positions based on signals or conditions.
* Scale out of positions gradually.

---

## 🔗 Usage Examples

### 1) Close position completely

```java
import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiTradingHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");

        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            long ticket = 123456789;
            double volume = 0.1;  // Full position volume
            int slippage = 10;    // 10 points

            Mt5TermApiTradingHelper.OrderCloseReply reply =
                account.orderClose(ticket, volume, slippage);

            var data = reply.getData();

            if (data.getReturnedCode() == 10009) {
                System.out.printf("✅ Position #%d closed%n", ticket);
                System.out.printf("   Mode: %s%n", data.getCloseMode());
            } else {
                System.out.printf("❌ Failed: %s (code: %d)%n",
                    data.getReturnedCodeDescription(),
                    data.getReturnedCode());
            }

        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) Close with position lookup

```java
public class PositionCloser {
    /**
     * Close position by ticket with automatic volume lookup
     */
    public static boolean closePosition(
            MT5Account account,
            long ticket) throws ApiExceptionMT5 {

        // Get position volume
        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var position = positions.stream()
            .filter(p -> p.getTicket() == ticket)
            .findFirst()
            .orElseThrow(() -> new ApiExceptionMT5("Position not found: " + ticket));

        double volume = position.getVolume();
        System.out.printf("Closing position #%d (%s %.2f lots)%n",
            ticket, position.getSymbol(), volume);

        var reply = account.orderClose(ticket, volume, 10);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.println("✅ Position closed successfully");
            return true;
        } else {
            System.out.printf("❌ Failed: %s%n",
                data.getReturnedCodeDescription());
            return false;
        }
    }
}

// Usage
boolean success = PositionCloser.closePosition(account, 123456789);
```

### 3) Partial position close

```java
public class PartialCloser {
    /**
     * Close part of position
     */
    public static boolean closePartial(
            MT5Account account,
            long ticket,
            double volumeToClose) throws ApiExceptionMT5 {

        // Get current position
        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var position = positions.stream()
            .filter(p -> p.getTicket() == ticket)
            .findFirst()
            .orElseThrow(() -> new ApiExceptionMT5("Position not found"));

        double totalVolume = position.getVolume();

        if (volumeToClose >= totalVolume) {
            System.out.println("⚠️ Volume >= total, closing fully");
            volumeToClose = totalVolume;
        }

        System.out.printf("Closing %.2f of %.2f lots (%.1f%%)%n",
            volumeToClose,
            totalVolume,
            (volumeToClose / totalVolume) * 100);

        var reply = account.orderClose(ticket, volumeToClose, 10);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            if (data.getCloseMode() == Mt5TermApiTradingHelper.MRPC_ORDER_CLOSE_MODE.MRPC_MARKET_ORDER_PARTIAL_CLOSE) {
                System.out.printf("✅ Partial close: %.2f lots closed, %.2f remains%n",
                    volumeToClose,
                    totalVolume - volumeToClose);
            } else {
                System.out.println("✅ Full position closed");
            }
            return true;
        } else {
            System.out.printf("❌ Failed: %s%n", data.getReturnedCodeDescription());
            return false;
        }
    }
}

// Usage - close half the position
PartialCloser.closePartial(account, 123456789, 0.05);
```

### 4) Delete pending order

```java
public class PendingOrderDeleter {
    /**
     * Delete pending order
     */
    public static boolean deletePendingOrder(
            MT5Account account,
            long orderTicket) throws ApiExceptionMT5 {

        System.out.printf("Deleting pending order #%d...%n", orderTicket);

        // For pending orders, volume doesn't matter
        var reply = account.orderClose(orderTicket, 0.0, 0);
        var data = reply.getData();

        if (data.getReturnedCode() == 10009) {
            System.out.println("✅ Pending order deleted");
            return true;
        } else {
            System.out.printf("❌ Failed: %s (code: %d)%n",
                data.getReturnedCodeDescription(),
                data.getReturnedCode());
            return false;
        }
    }
}

// Usage
PendingOrderDeleter.deletePendingOrder(account, 123456);
```

### 5) Close all positions for symbol

```java
public class SymbolCloser {
    /**
     * Close all positions for a specific symbol
     */
    public static int closeAllForSymbol(
            MT5Account account,
            String symbol) throws ApiExceptionMT5 {

        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var symbolPositions = positions.stream()
            .filter(p -> p.getSymbol().equals(symbol))
            .toList();

        if (symbolPositions.isEmpty()) {
            System.out.printf("No positions found for %s%n", symbol);
            return 0;
        }

        System.out.printf("Closing %d positions for %s...%n",
            symbolPositions.size(), symbol);

        int closedCount = 0;

        for (var position : symbolPositions) {
            try {
                var reply = account.orderClose(
                    position.getTicket(),
                    position.getVolume(),
                    10
                );

                if (reply.getData().getReturnedCode() == 10009) {
                    System.out.printf("✅ Closed #%d (%.2f lots)%n",
                        position.getTicket(),
                        position.getVolume());
                    closedCount++;
                } else {
                    System.out.printf("❌ Failed #%d: %s%n",
                        position.getTicket(),
                        reply.getData().getReturnedCodeDescription());
                }

            } catch (ApiExceptionMT5 e) {
                System.err.printf("Error closing #%d: %s%n",
                    position.getTicket(), e.getMessage());
            }
        }

        System.out.printf("Closed: %d/%d positions%n",
            closedCount, symbolPositions.size());

        return closedCount;
    }
}

// Usage
int closed = SymbolCloser.closeAllForSymbol(account, "EURUSD");
```

### 6) Close all losing positions

```java
public class LossCloser {
    /**
     * Close all positions with losses
     */
    public static int closeAllLosing(MT5Account account) throws ApiExceptionMT5 {
        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var losingPositions = positions.stream()
            .filter(p -> p.getProfit() < 0)
            .toList();

        if (losingPositions.isEmpty()) {
            System.out.println("No losing positions");
            return 0;
        }

        System.out.printf("Closing %d losing positions...%n", losingPositions.size());

        int closedCount = 0;
        double totalLoss = 0;

        for (var position : losingPositions) {
            try {
                var reply = account.orderClose(
                    position.getTicket(),
                    position.getVolume(),
                    10
                );

                if (reply.getData().getReturnedCode() == 10009) {
                    System.out.printf("✅ Closed #%d: %.2f loss%n",
                        position.getTicket(),
                        position.getProfit());
                    closedCount++;
                    totalLoss += position.getProfit();
                }

            } catch (ApiExceptionMT5 e) {
                System.err.printf("Error: %s%n", e.getMessage());
            }
        }

        System.out.printf("Closed: %d positions, Total loss: %.2f%n",
            closedCount, totalLoss);

        return closedCount;
    }
}

// Usage
LossCloser.closeAllLosing(account);
```

### 7) Scale out (close in steps)

```java
public class ScaleOutCloser {
    /**
     * Scale out of position in multiple steps
     */
    public static void scaleOut(
            MT5Account account,
            long ticket,
            int steps) throws ApiExceptionMT5, InterruptedException {

        // Get position
        var positions = account.openedOrders(
            Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
        ).getData().getPositionInfosList();

        var position = positions.stream()
            .filter(p -> p.getTicket() == ticket)
            .findFirst()
            .orElseThrow(() -> new ApiExceptionMT5("Position not found"));

        double totalVolume = position.getVolume();
        double volumePerStep = totalVolume / steps;

        // Round to 0.01
        volumePerStep = Math.round(volumePerStep * 100.0) / 100.0;

        System.out.printf("Scaling out of position #%d in %d steps%n",
            ticket, steps);
        System.out.printf("Total: %.2f lots, Per step: %.2f lots%n",
            totalVolume, volumePerStep);

        double remainingVolume = totalVolume;

        for (int i = 1; i <= steps; i++) {
            double volumeToClose = Math.min(volumePerStep, remainingVolume);

            System.out.printf("\nStep %d/%d: Closing %.2f lots...%n",
                i, steps, volumeToClose);

            var reply = account.orderClose(ticket, volumeToClose, 10);

            if (reply.getData().getReturnedCode() == 10009) {
                remainingVolume -= volumeToClose;
                System.out.printf("✅ Closed %.2f lots, %.2f remains%n",
                    volumeToClose, remainingVolume);

                if (remainingVolume <= 0.01) {
                    System.out.println("Position fully closed");
                    break;
                }
            } else {
                System.out.printf("❌ Failed: %s%n",
                    reply.getData().getReturnedCodeDescription());
                break;
            }

            // Wait before next step
            if (i < steps) {
                Thread.sleep(1000);
            }
        }
    }
}

// Usage - close position in 3 steps
ScaleOutCloser.scaleOut(account, 123456789, 3);
```

### 8) Emergency close all

```java
public class EmergencyCloser {
    /**
     * Emergency close all positions and delete all pending orders
     */
    public static void closeAll(MT5Account account) {
        System.out.println("🚨 EMERGENCY CLOSE ALL 🚨");
        System.out.println("═".repeat(50));

        try {
            var data = account.openedOrders(
                Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_SORT_BY_TICKET_ASC
            ).getData();

            int positionsCount = data.getPositionInfosCount();
            int ordersCount = data.getOpenedOrdersCount();

            System.out.printf("Found: %d positions, %d pending orders%n",
                positionsCount, ordersCount);

            // Close all positions
            int positionsClosed = 0;
            for (var position : data.getPositionInfosList()) {
                try {
                    var reply = account.orderClose(
                        position.getTicket(),
                        position.getVolume(),
                        50  // Allow high slippage
                    );

                    if (reply.getData().getReturnedCode() == 10009) {
                        System.out.printf("✅ Closed position #%d%n",
                            position.getTicket());
                        positionsClosed++;
                    }
                } catch (Exception e) {
                    System.err.printf("❌ Failed to close #%d: %s%n",
                        position.getTicket(), e.getMessage());
                }
            }

            // Delete all pending orders
            int ordersDeleted = 0;
            for (var order : data.getOpenedOrdersList()) {
                try {
                    var reply = account.orderClose(order.getTicket(), 0.0, 0);

                    if (reply.getData().getReturnedCode() == 10009) {
                        System.out.printf("✅ Deleted order #%d%n",
                            order.getTicket());
                        ordersDeleted++;
                    }
                } catch (Exception e) {
                    System.err.printf("❌ Failed to delete #%d: %s%n",
                        order.getTicket(), e.getMessage());
                }
            }

            System.out.println("═".repeat(50));
            System.out.printf("Results: %d/%d positions closed, %d/%d orders deleted%n",
                positionsClosed, positionsCount,
                ordersDeleted, ordersCount);

        } catch (ApiExceptionMT5 e) {
            System.err.println("Fatal error: " + e.getMessage());
        }
    }
}

// Usage - EMERGENCY ONLY!
EmergencyCloser.closeAll(account);
```

---

## 📌 Important Notes

**Slippage:**
- In points, not pips (10 points = 1 pip for EURUSD)
- 0 = no slippage allowed (may cause rejections)
- 10-50 typical for normal conditions
- Higher slippage for emergency closes

**Partial Closure:**
- Creates new ticket for remaining position
- Original ticket is closed
- Useful for scaling out of positions

**Pending Orders:**
- Always deleted fully (volume ignored)
- No slippage applies to pending orders

**Return Codes:**
- `10009` = SUCCESS
- Other codes indicate failure

**Best Practices:**
- Always check return code
- Use reasonable slippage values
- Handle errors gracefully
- Consider partial closes for large positions
