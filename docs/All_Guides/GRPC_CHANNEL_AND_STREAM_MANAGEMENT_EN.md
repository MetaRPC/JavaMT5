# gRPC Channel and Stream Management

## Problem: Channels and Streams Don't Close Automatically

When working with gRPC streaming in JavaMT5, two main issues occur:

1. **gRPC channels** remain open after the program finishes
2. **Stream subscriptions** (onSymbolTick, onTrade, etc.) continue running even after exiting the method

---

## Solution 1: Correct Disconnection Sequence

### IMPORTANT: Difference Between `disconnect()` and `close()`

- **`disconnect()`** - disconnects from MT5 terminal, **cancels all event subscriptions**
- **`close()`** - closes the gRPC channel, **frees JVM resources**

### Correct Sequence:

```java
try {
    // Work with MT5
} finally {
    if (service != null) {
        // 1. First disconnect - cancels subscriptions on MT5 side
        service.disconnect();

        // 2. Then close channel - frees JVM resources
        service.getAccount().close();
    }
}
```

### The `MT5Account.close()` Method

The `MT5Account` class has a `close()` method that properly closes the gRPC channel:

```java
// In pro.mrpc.mt5.MT5Account.java (the close() method)
public void close() {
    try {
        grpcChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
        grpcChannel.shutdownNow();
        Thread.currentThread().interrupt();
    }
}
```

### How to Use:

```java
MT5Account account = null;
try {
    account = new MT5Account(user, password, grpcServer, apiKey, null);
    account.connectByServerName(serverName, baseSymbol, 30);

    // Your code...

} finally {
    if (account != null) {
        account.close();  // ✓ Properly closes gRPC channel
        System.out.println("gRPC channel closed");
    }
}
```

### Force Close (If close() Hangs):

```java
finally {
    if (account != null) {
        try {
            account.close();
        } catch (Exception e) {
            System.err.println("Error during close: " + e.getMessage());
        }
        // Force JVM termination
        System.exit(0);
    }
}
```

---

## Solution 2: Stopping Streams (StreamObserver)

### The current API already returns a cancellation handle

Every streaming method (`onSymbolTick`, `onTrade`, `onPositionProfit`,
`onPositionsAndPendingOrdersTickets`, `onTradeTransaction`) returns an
**`io.grpc.Context.CancellableContext`** — call `.close()` on it to stop that one stream:

```java
import io.grpc.Context;

// ✓ Returns a handle you can cancel
Context.CancellableContext sub = account.onSymbolTick(
    new String[]{"EURUSD"}, observer);

// ... later, stop just this stream:
sub.close();
```

> The same handle is returned by the `MT5Service` wrappers (they delegate to `MT5Account`).

### Alternative: Using ClientCall for lower-level stream management

If you need finer control (e.g., a custom cancellation message), you can also hold a
reference to the underlying `ClientCall`:

```java
import io.grpc.ClientCall;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.StreamObserver;

public class ManagedStreamExample {

    // Store references to active streams
    private ClientCall<?, ?> activeTickStream = null;
    private ClientCall<?, ?> activeTradeStream = null;

    public void subscribeToTicks(MT5Account account, String[] symbols) {
        StreamObserver<OnSymbolTickReply> observer = new StreamObserver<>() {
            @Override
            public void onNext(OnSymbolTickReply reply) {
                // Process tick
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Stream error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed");
            }
        };

        // Start stream
        account.onSymbolTick(symbols, observer);

        // TODO: In future version, save ClientCall for management
        // activeTickStream = ...;
    }

    // Method to stop all streams
    public void stopAllStreams() {
        if (activeTickStream != null) {
            activeTickStream.cancel("User requested cancellation", null);
            activeTickStream = null;
        }
        if (activeTradeStream != null) {
            activeTradeStream.cancel("User requested cancellation", null);
            activeTradeStream = null;
        }
    }
}
```

---

## Solution 3: Using CountDownLatch for Stream Lifetime Control

The current pattern in examples uses `CountDownLatch` to limit stream runtime:

```java
private static void runSymbolTickDemo(MT5Account acc, String[] symbols)
        throws ApiExceptionMT5, InterruptedException {

    CountDownLatch latch = new CountDownLatch(1);
    AtomicInteger eventCount = new AtomicInteger(0);

    StreamObserver<OnSymbolTickReply> observer = new StreamObserver<>() {
        @Override
        public void onNext(OnSymbolTickReply reply) {
            int count = eventCount.incrementAndGet();

            // Process event...

            if (count >= MAX_EVENTS) {
                latch.countDown();  // ✓ Signal completion
            }
        }

        @Override
        public void onError(Throwable t) {
            latch.countDown();
        }

        @Override
        public void onCompleted() {
            latch.countDown();
        }
    };

    // Start stream (keep the returned handle)
    io.grpc.Context.CancellableContext sub = acc.onSymbolTick(symbols, observer);

    // ✓ Wait for completion or timeout
    boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);

    if (!completed) {
        System.out.println("Timeout - cancelling the stream");
        sub.close();   // ✓ Stop this stream explicitly
    }
}
```

**Tip:** Closing the returned `CancellableContext` stops that stream without tearing down the
whole channel; `account.close()` (or `disconnect()`) cancels everything at once.

---

## Solution 4: Managing Streams with the Built-in Handle

You don't need to build a custom subscription manager — each `on…` method already hands you a
`io.grpc.Context.CancellableContext`. Keep the handles you care about and close them to stop
individual streams; use `disconnect()` / `close()` to stop everything.

```java
import io.grpc.Context;

MT5Account account = new MT5Account(user, password, grpcServer, apiKey, null);
account.connectByServerName(serverName, baseSymbol, 30);

StreamObserver<OnSymbolTickReply> observer = new StreamObserver<>() {
    @Override public void onNext(OnSymbolTickReply reply) { /* process tick */ }
    @Override public void onError(Throwable t) { System.err.println("Error: " + t.getMessage()); }
    @Override public void onCompleted() { System.out.println("Stream completed"); }
};

// ✓ Subscribe — keep the returned handle
Context.CancellableContext tickSub = account.onSymbolTick(
    new String[]{"EURUSD", "GBPUSD"}, observer);

// Work with the stream...
Thread.sleep(5000);

// ✓ Stop just this stream
tickSub.close();

// ✓ Or stop every subscription and the connection at once
account.disconnect();   // cancels all subscriptions on the server
account.close();        // frees the gRPC channel
```

**Managing several streams:** collect the handles in a list and close them together.

```java
List<Context.CancellableContext> subs = new ArrayList<>();
subs.add(account.onSymbolTick(new String[]{"EURUSD"}, tickObserver));
subs.add(account.onTrade(tradeObserver));

// Later — stop all tracked streams:
subs.forEach(Context.CancellableContext::close);
subs.clear();
```

---

## Extra Safety Nets

On top of closing streams explicitly, these patterns help guarantee a clean exit:

### 1. Force JVM Termination

```java
finally {
    if (account != null) {
        account.close();
    }
    System.exit(0);  // ✓ Guaranteed termination
}
```

### 2. Set Timeout for Entire Process

```java
// At the start of main()
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    System.out.println("Shutdown hook - forcing exit");
}));

// Set timeout
ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
executor.schedule(() -> {
    System.out.println("Timeout reached - forcing exit");
    System.exit(0);
}, 30, TimeUnit.SECONDS);
```

### 3. Explicit Channel Close via shutdownNow()

```java
public void forceClose() {
    try {
        if (!grpcChannel.shutdown().awaitTermination(2, TimeUnit.SECONDS)) {
            grpcChannel.shutdownNow();  // ✓ Force stop
        }
    } catch (InterruptedException e) {
        grpcChannel.shutdownNow();
        Thread.currentThread().interrupt();
    }
}
```

---

## Recommendations

1. **Always call `account.close()`** in `finally` block
2. **Use `System.exit(0)`** if `close()` hangs
3. **Limit stream lifetime** via `CountDownLatch` with timeout
4. **For production code** - consider implementing `StreamSubscription` API
5. **Monitor threads** - use VisualVM or JConsole to track hanging threads

---

## Common Warnings (Can Be Ignored)

### File Lock Warning: `2025.hcc`

```
Status(StatusCode="Internal", Detail="The process cannot access the file '2025.hcc'
because it is being used by another process.")
```

**What it means:**
- `2025.hcc` is MT5's history cache file
- MT5 terminal keeps it locked while running
- This warning occurs during `disconnect()` call
- **It's NORMAL and can be safely ignored**

**Why it happens:**
- gRPC tries to sync/cleanup history files on disconnect
- MT5 terminal holds exclusive lock on history files
- The lock prevents cleanup, but doesn't affect functionality

**Solution:**
```java
} catch (Exception e) {
    // Silently ignore all disconnect errors (file locks, etc.)
    // They are harmless during shutdown
}
```

### Maven Daemon Exception After `System.exit(0)`

```
DaemonException$StaleAddressException: Could not receive a message from the daemon.
```

**What it means:**
- Maven daemon lost connection after program forced exit
- This happens AFTER your program finished successfully
- **It's NORMAL when using `System.exit(0)`**

**Why it happens:**
- `System.exit(0)` terminates JVM immediately
- Maven daemon expects graceful shutdown signal
- Connection breaks before daemon receives exit message

**Solution:**
```bash
# If you see this error repeatedly, stop and restart Maven daemon:
mvnd --stop
mvnd compile
```

**Note:** This doesn't affect your program - it ran successfully. The error appears during Maven cleanup.

---

## Example Finally Block for All Examples

### Variant 1: With MT5Service (Recommended)

```java
MT5Service service = null;
try {
    service = new MT5Service(user, password, grpcServer, apiKey, null);
    service.connectByServerName(serverName, baseSymbol, 30);

    // Your code...

} catch (Exception e) {
    System.err.println("ERROR: " + e.getMessage());
    e.printStackTrace();
} finally {
    if (service != null) {
        try {
            System.out.println("► Disconnecting from MT5...");

            // ✓ CORRECT: First disconnect - cancels subscriptions
            service.disconnect();

            // ✓ CORRECT: Then close - closes gRPC channel
            service.getAccount().close();

            System.out.println("► Disconnected successfully");
        } catch (Exception e) {
            // Silently ignore all disconnect errors (file locks, etc.)
            // They are harmless during shutdown
        }
    }
    // Force termination for reliability
    System.exit(0);
}
```

### Variant 2: With MT5Account Directly

```java
MT5Account account = null;
try {
    account = new MT5Account(user, password, grpcServer, apiKey, null);
    account.connectByServerName(serverName, baseSymbol, 30);

    // Your code...

} catch (Exception e) {
    System.err.println("ERROR: " + e.getMessage());
    e.printStackTrace();
} finally {
    if (account != null) {
        try {
            System.out.println("► Disconnecting from MT5...");

            // ✓ CORRECT: First disconnect - cancels subscriptions
            account.disconnect();

            // ✓ CORRECT: Then close - closes gRPC channel
            account.close();

            System.out.println("► Disconnected successfully");
        } catch (Exception e) {
            // Silently ignore all disconnect errors (file locks, etc.)
            // They are harmless during shutdown
        }
    }
    // Force termination for reliability
    System.exit(0);
}
```

### Variant 3: Quick Exit (If Experiencing Hanging Issues)

```java
} finally {
    if (service != null) {
        try {
            System.out.println("► Disconnecting...");
            service.disconnect();
            service.getAccount().close();
        } catch (Exception e) {
            // Ignore file lock errors
        }
    }
    // Force exit without waiting
    System.exit(0);
}
```

---

---

## Resource Cleanup in JavaMT5 Examples

### Program.java Implements This Pattern

All orchestrators and presets in JavaMT5 follow the correct cleanup pattern through `Program.java`:

**How it works:**
- `run.bat 10 1-5` → Runs orchestrators via `Program.runOrchestrator()`
- `run.bat 11 1-2` → Runs presets via `Program.runPreset()`
- Both methods handle resource cleanup automatically

**Implementation in Program.java:**
```java
// In runOrchestrator() and runPreset() methods:
} finally {
    account.disconnect();  // ✓ Cancel subscriptions
    account.close();       // ✓ Free gRPC resources
}
```

**This means:**
- ✅ Orchestrators (1-5) automatically clean up resources
- ✅ Presets (1-2) automatically clean up resources
- ✅ You don't need to manually handle cleanup when using `run.bat`

---

## See Also

- **[RUNNING_EXAMPLES.md](RUNNING_EXAMPLES.md)** - How to run examples + troubleshooting
- **[GLOSSARY.md](GLOSSARY.md)** - See "target/ Folder" for build troubleshooting
