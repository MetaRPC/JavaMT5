/*══════════════════════════════════════════════════════════════════════════════
 FILE: StreamingServiceExample.java — SERVICE API: STREAMING WITH WRAPPERS

 LEVEL: 2 (Service API)
 DIFFICULTY: Beginner to Intermediate

 PURPOSE:
   Same streaming functionality as Example 3 (Low-Level), but with Service API!
   This example demonstrates MT5Service streaming methods that:
   • Use the same StreamObserver pattern (no difference here)
   • Provide convenient subscription methods
   • Same as low-level but accessed through service wrapper

 🎯 WHY USE SERVICE API FOR STREAMING?

   The streaming API is IDENTICAL to low-level in terms of StreamObserver usage.
   The benefit is consistency - all your code uses MT5Service instead of mixing
   MT5Account and MT5Service.

   LOW-LEVEL (Example 3):                SERVICE API (This Example):
   ────────────────────────────────────────────────────────────────────────
   acc.onSymbolTick(symbols, observer)   service.onSymbolTick(symbols, observer)
   acc.onTrade(observer)                 service.onTrade(observer)
   // Same StreamObserver pattern!       // Same StreamObserver pattern!

 📚 WHAT YOU'LL LEARN:
   • Same 5 streaming methods as Example 3
   • Accessed through MT5Service wrapper
   • Consistent API surface (all via service object)

 USAGE:
   run.bat 6  or  .\run.bat 6               # Via run.bat (recommended)
   mvnd exec:java -Dexec.args="6"           # Via Maven

══════════════════════════════════════════════════════════════════════════════*/

package examples.services;

import io.grpc.stub.StreamObserver;
import io.metarpc.mt5.MT5Service;
import mt5_term_api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamingServiceExample {

    private static final int MAX_EVENTS = 10;
    private static final int TIMEOUT_SECONDS = 30;

    public static void main(String[] args) {
        // Set UTF-8 encoding for console output
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        System.out.println("\n------------------------------------------------------------------");
        System.out.println("MT5 SERVICE - STREAMING EXAMPLE");
        System.out.println("------------------------------------------------------------------\n");

        MT5Service service = null;
        try {
            // ═══════════════════════════════════════════════════════════
            // INITIALIZATION
            // ═══════════════════════════════════════════════════════════

            JsonObject config = loadConfig();
            long user = config.get("user").getAsLong();
            String password = config.get("password").getAsString();
            String grpcServer = config.get("grpcServer").getAsString();
            String serverName = config.get("serverName").getAsString();
            String baseSymbol = config.get("baseSymbol").getAsString();

            System.out.println("► Initializing MT5Service...");
            service = new MT5Service(user, password, grpcServer, null);

            System.out.println("► Connecting to " + serverName + "...");
            service.connectByServerName(serverName, baseSymbol, 30);
            System.out.println("✓ Connected successfully\n");

            // Register shutdown hook for Ctrl+C handling
            final MT5Service finalService = service;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n[Ctrl+C detected] Shutting down gracefully...");
                try {
                    if (finalService != null) {
                        finalService.disconnect();
                        finalService.getAccount().close();
                        System.out.println("[Shutdown hook] Disconnected successfully");
                    }
                } catch (Exception e) {
                    // Silently ignore errors during emergency shutdown
                }
            }));

            // ══════════════════════════════════════════════════════════════
            // STREAM 1: SYMBOL TICK
            //
            // WRAPPER: service.onSymbolTick() vs acc.onSymbolTick()
            //          → Same method, just accessed through service wrapper
            // ══════════════════════════════════════════════════════════════

            section("1. SYMBOL TICK STREAM");
            demo("onSymbolTick(symbols, observer)", "Subscribe to real-time price ticks");

            CountDownLatch tickLatch = new CountDownLatch(1);
            AtomicInteger tickCount = new AtomicInteger(0);
            final boolean[] tickStopped = {false};

            StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> tickObserver =
                new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {
                    @Override
                    public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
                        if (tickStopped[0]) return;
                        int count = tickCount.incrementAndGet();

                        if (reply.hasData()) {
                            Mt5TermApiSubscriptions.OnSymbolTickData data = reply.getData();
                            Mt5TermApiSubscriptions.MrpcSubscriptionMqlTick tick = data.getSymbolTick();

                            System.out.printf("    [%d] %s: Bid=%.5f Ask=%.5f%n",
                                count, tick.getSymbol(), tick.getBid(), tick.getAsk());
                        }

                        if (count >= MAX_EVENTS) {
                            tickStopped[0] = true;
                            tickLatch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (tickStopped[0]) return;
                        tickStopped[0] = true;
                        System.err.println("    ✗ Tick stream error: " + t.getMessage());
                        tickLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        if (tickStopped[0]) return;
                        tickStopped[0] = true;
                        System.out.println("    ✓ Tick stream completed");
                        tickLatch.countDown();
                    }
                };

            String[] symbols = {baseSymbol, "GBPUSD"};
            service.onSymbolTick(symbols, tickObserver);
            result("Subscribed to", String.join(", ", symbols));
            result("Waiting for", MAX_EVENTS + " tick events or " + TIMEOUT_SECONDS + " sec...");

            if (tickLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                result("Received", tickCount.get() + " tick events ✓");
            } else {
                result("Timeout", "Received " + tickCount.get() + " events in " + TIMEOUT_SECONDS + " sec");
            }

            // ══════════════════════════════════════════════════════════════
            // STREAM 2: TRADE EVENTS
            //
            // Real-time notifications when orders are placed, modified, closed
            // ══════════════════════════════════════════════════════════════

            section("2. TRADE EVENTS STREAM");
            demo("onTrade(observer)", "Subscribe to trade events (orders, deals)");

            CountDownLatch tradeLatch = new CountDownLatch(1);
            AtomicInteger tradeCount = new AtomicInteger(0);
            final boolean[] tradeStopped = {false};

            StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> tradeObserver =
                new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {
                    @Override
                    public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
                        if (tradeStopped[0]) return;
                        int count = tradeCount.incrementAndGet();
                        System.out.printf("    [%d] Trade event received%n", count);

                        if (count >= MAX_EVENTS) {
                            tradeStopped[0] = true;
                            tradeLatch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (tradeStopped[0]) return;
                        tradeStopped[0] = true;
                        System.err.println("    ✗ Trade stream error: " + t.getMessage());
                        tradeLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        if (tradeStopped[0]) return;
                        tradeStopped[0] = true;
                        System.out.println("    ✓ Trade stream completed");
                        tradeLatch.countDown();
                    }
                };

            service.onTrade(tradeObserver);
            result("Subscribed", "Listening for trade events");
            result("Waiting for", MAX_EVENTS + " events or " + TIMEOUT_SECONDS + " sec...");

            if (tradeLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                result("Received", tradeCount.get() + " trade events ✓");
            } else {
                result("Timeout", "Received " + tradeCount.get() + " events (no trades during demo)");
            }

            // ══════════════════════════════════════════════════════════════
            // STREAM 3: POSITION PROFIT
            //
            // Monitor profit changes on open positions in real-time
            // ══════════════════════════════════════════════════════════════

            section("3. POSITION PROFIT STREAM");
            demo("onPositionProfit(timerMs, ignoreEmpty, observer)", "Monitor position profit");

            CountDownLatch profitLatch = new CountDownLatch(1);
            AtomicInteger profitCount = new AtomicInteger(0);
            final boolean[] profitStopped = {false};

            StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> profitObserver =
                new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {
                    @Override
                    public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
                        if (profitStopped[0]) return;
                        int count = profitCount.incrementAndGet();

                        if (reply.hasData()) {
                            Mt5TermApiSubscriptions.OnPositionProfitData data = reply.getData();
                            double totalProfit = 0;
                            for (var pos : data.getNewPositionsList()) {
                                totalProfit += pos.getProfit();
                            }
                            for (var pos : data.getUpdatedPositionsList()) {
                                totalProfit += pos.getProfit();
                            }
                            System.out.printf("    [%d] Profit update: %.2f%n", count, totalProfit);
                        }

                        if (count >= MAX_EVENTS) {
                            profitStopped[0] = true;
                            profitLatch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (profitStopped[0]) return;
                        profitStopped[0] = true;
                        System.err.println("    ✗ Profit stream error: " + t.getMessage());
                        profitLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        if (profitStopped[0]) return;
                        profitStopped[0] = true;
                        System.out.println("    ✓ Profit stream completed");
                        profitLatch.countDown();
                    }
                };

            service.onPositionProfit(1000, false, profitObserver);
            result("Subscribed", "Profit updates every 1000ms");
            result("Waiting for", MAX_EVENTS + " events or " + TIMEOUT_SECONDS + " sec...");

            if (profitLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                result("Received", profitCount.get() + " profit events ✓");
            } else {
                result("Timeout", "Received " + profitCount.get() + " events");
            }

            // ══════════════════════════════════════════════════════════════
            // STREAM 4: TICKETS MONITORING
            //
            // Track which position/order tickets are currently active
            // ══════════════════════════════════════════════════════════════

            section("4. TICKETS MONITORING STREAM");
            demo("onPositionsAndPendingOrdersTickets(timerMs, observer)", "Monitor position/order tickets");

            CountDownLatch ticketsLatch = new CountDownLatch(1);
            AtomicInteger ticketsCount = new AtomicInteger(0);
            final boolean[] ticketsStopped = {false};

            StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> ticketsObserver =
                new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {
                    @Override
                    public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
                        if (ticketsStopped[0]) return;
                        int count = ticketsCount.incrementAndGet();

                        if (reply.hasData()) {
                            Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsData data = reply.getData();
                            System.out.printf("    [%d] Tickets: Positions=%d Orders=%d%n",
                                count, data.getPositionTicketsCount(), data.getPendingOrderTicketsCount());
                        }

                        if (count >= MAX_EVENTS) {
                            ticketsStopped[0] = true;
                            ticketsLatch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (ticketsStopped[0]) return;
                        ticketsStopped[0] = true;
                        System.err.println("    ✗ Tickets stream error: " + t.getMessage());
                        ticketsLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        if (ticketsStopped[0]) return;
                        ticketsStopped[0] = true;
                        System.out.println("    ✓ Tickets stream completed");
                        ticketsLatch.countDown();
                    }
                };

            service.onPositionsAndPendingOrdersTickets(1000, ticketsObserver);
            result("Subscribed", "Ticket updates every 1000ms");
            result("Waiting for", MAX_EVENTS + " events or " + TIMEOUT_SECONDS + " sec...");

            if (ticketsLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                result("Received", ticketsCount.get() + " ticket events ✓");
            } else {
                result("Timeout", "Received " + ticketsCount.get() + " events");
            }

            // ══════════════════════════════════════════════════════════════
            // STREAM 5: TRADE TRANSACTIONS
            //
            // Low-level transaction events for advanced order tracking
            // ══════════════════════════════════════════════════════════════

            section("5. TRADE TRANSACTION STREAM");
            demo("onTradeTransaction(observer)", "Subscribe to trade transaction events");

            CountDownLatch transactionLatch = new CountDownLatch(1);
            AtomicInteger transactionCount = new AtomicInteger(0);
            final boolean[] transactionStopped = {false};

            StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> transactionObserver =
                new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {
                    @Override
                    public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
                        if (transactionStopped[0]) return;
                        int count = transactionCount.incrementAndGet();
                        System.out.printf("    [%d] Transaction event received%n", count);

                        if (count >= MAX_EVENTS) {
                            transactionStopped[0] = true;
                            transactionLatch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (transactionStopped[0]) return;
                        transactionStopped[0] = true;
                        System.err.println("    ✗ Transaction stream error: " + t.getMessage());
                        transactionLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        if (transactionStopped[0]) return;
                        transactionStopped[0] = true;
                        System.out.println("    ✓ Transaction stream completed");
                        transactionLatch.countDown();
                    }
                };

            service.onTradeTransaction(transactionObserver);
            result("Subscribed", "Listening for trade transactions");
            result("Waiting for", MAX_EVENTS + " events or " + TIMEOUT_SECONDS + " sec...");

            if (transactionLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                result("Received", transactionCount.get() + " transaction events ✓");
            } else {
                result("Timeout", "Received " + transactionCount.get() + " events (no transactions during demo)");
            }

            // ═══════════════════════════════════════════════════════════

            System.out.println("\n------------------------------------------------------------------");
            System.out.println("All streaming subscriptions demonstrated");
            System.out.println();
            System.out.println("NOTE: Streams run until MAX_EVENTS or timeout");
            System.out.println("In production, streams can run indefinitely");
            System.out.println("------------------------------------------------------------------\n");
            System.out.println("Closing in 3 seconds...");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("\n✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (service != null) {
                try {
                    System.out.println("► Disconnecting from MT5...");
                    service.disconnect();
                    service.getAccount().close();
                    System.out.println("► Disconnected successfully");
                } catch (Exception e) {
                    // Silently ignore all disconnect errors (file locks, etc.)
                    // They are harmless during shutdown
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ═══════════════════════════════════════════════════════════════════

    private static JsonObject loadConfig() throws Exception {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(new FileReader("appsettings.json"), JsonObject.class);
        String defaultConn = root.get("DefaultConnection").getAsString();
        return root.getAsJsonObject("MT5Connections").getAsJsonObject(defaultConn);
    }

    private static void section(String title) {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println(" " + title);
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    private static void demo(String method, String description) {
        System.out.println("  ▸ " + method);
        System.out.println("    " + description);
    }

    private static void result(String label, String value) {
        System.out.println("    → " + label + ": " + value);
    }
}

/*══════════════════════════════════════════════════════════════════════════════

                    SERVICE API VS LOW-LEVEL FOR STREAMING

══════════════════════════════════════════════════════════════════════════════

IMPORTANT: For streaming, Service API and Low-Level API are IDENTICAL!

┌─────────────────────────────────────────────────────────────────────────────┐
│ METHOD                 │ LOW-LEVEL              │ SERVICE API               │
├─────────────────────────────────────────────────────────────────────────────┤
│ Subscribe to ticks     │ acc.onSymbolTick(...)  │ service.onSymbolTick(...) │
│ Subscribe to trades    │ acc.onTrade(...)       │ service.onTrade(...)      │
│ Monitor profit         │ acc.onPositionProfit(.)│ service.onPositionProfit()│
│ StreamObserver usage   │ Same!                  │ Same!                     │
│ CountDownLatch pattern │ Same!                  │ Same!                     │
└─────────────────────────────────────────────────────────────────────────────┘

WHY USE SERVICE API FOR STREAMING?
  → Consistency: All code uses one object (service) instead of mixing acc/service
  → That's it! No other benefits for streaming specifically.

The real benefits of Service API are in market data and trading operations where
.getData() extraction saves significant boilerplate.


══════════════════════════════════════════════════════════════════════════════

                               HOW TO RUN

══════════════════════════════════════════════════════════════════════════════

1. Via run.bat (Recommended - fast):
   run.bat 6  or  .\run.bat 6

2. Via run-clean.bat (If run.bat fails with compilation errors):
   run-clean.bat 6  or  .\run-clean.bat 6
   → Stops daemon, removes target/, recompiles from scratch
   → Use this if you see "Unresolved compilation problem" errors

3. Via Maven:
   mvnd compile exec:java -Dexec.args="6"

══════════════════════════════════════════════════════════════════════════════*/