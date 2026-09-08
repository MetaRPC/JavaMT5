/*══════════════════════════════════════════════════════════════════════════════
 FILE: StreamingExample.java — LOW-LEVEL API: REAL-TIME STREAMING

 LEVEL: 1 (Low-Level API)
 DIFFICULTY: Intermediate to Advanced

 PURPOSE:
   Demonstrates all streaming/subscription methods for real-time data feeds.
   Uses gRPC StreamObserver pattern for asynchronous event handling.
   This is essential for building reactive trading systems that respond to
   market changes in real-time.

 WHAT YOU'LL LEARN:
   • How to subscribe to real-time tick data (price updates)
   • How to monitor trade events (new positions, orders, deals)
   • How to track position profit changes in real-time
   • How to receive trade transaction notifications
   • How to handle asynchronous streams with StreamObserver

 📚 WHAT THIS DEMO COVERS (5 Streaming Methods):

   1. onSymbolTick() - Real-time tick data for multiple symbols
      • Subscribe to live price updates (bid, ask, volume)
      • Monitor multiple symbols simultaneously
      • Essential for price-based trading strategies

   2. onTrade() - Trade event notifications
      • Get notified when new positions are opened
      • Track pending orders and their changes
      • Monitor historical deals

   3. onPositionProfit() - Position profit monitoring
      • Real-time profit/loss tracking
      • Position state changes
      • Critical for risk management

   4. onPositionsAndPendingOrdersTickets() - Tickets monitoring
      • Track position and order ticket numbers
      • Detect when positions are opened/closed
      • Monitor pending orders

   5. onTradeTransaction() - Trade transaction events
      • Low-level transaction notifications
      • Most detailed event stream
      • For advanced trading system integration

 USAGE:
   run.bat 3  or  .\run.bat 3       # Via run.bat (recommended)
   mvnd exec:java -Dexec.args="3"   # Via Maven directly

 PREREQUISITES:
   • MT5 terminal installed and running
   • MetaRPC gRPC gateway (plugin) running in MT5 terminal
   • Valid MT5 account credentials in appsettings.json
   • Basic understanding of asynchronous programming (StreamObserver pattern)
   • Recommended: First complete [1] Market Data and [2] Trading Calculations

 NEXT STEPS AFTER THIS DEMO:
   • Try [6] Streaming Service - simplified streaming API
   • Try [7] Simple Trading - combine streaming with trading operations
   • Try [10] Orchestrators - see streaming used in real strategies

 IMPORTANT NOTES:
   • Streams are asynchronous - events arrive independently
   • Each demo waits for 10 events or 5 seconds timeout
   • You may need to perform trades manually to trigger some events
   • Tick events are continuous during market hours
══════════════════════════════════════════════════════════════════════════════*/

package examples.lowlevel;

import io.grpc.stub.StreamObserver;
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import utils.HelperUtils;
import java.io.FileReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamingExample {

    private static final int MAX_EVENTS = 10;
    private static final int TIMEOUT_SECONDS = 5;

    public static void main(String[] args) {
        // Set UTF-8 encoding for console output
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        HelperUtils.printBanner(
            "REAL-TIME STREAMING DEMO",
            "Tick Data, Trades, Positions, Transactions"
        );

        MT5Account account = null;

        try {
            JsonObject config = loadConfig();
            long user = config.get("user").getAsLong();
            String password = config.get("password").getAsString();
            String grpcServer = config.has("grpcServer") ? config.get("grpcServer").getAsString() : null;
            String serverName = config.get("serverName").getAsString();
            String baseSymbol = config.get("baseSymbol").getAsString();

            System.out.println("Configuration loaded: user=" + user);
            System.out.println();

            account = new MT5Account(user, password, grpcServer, null);
            account.connectByServerName(serverName, baseSymbol, 30);
            System.out.println("Connected to MT5");
            System.out.println();

            // Register shutdown hook for Ctrl+C handling
            final MT5Account finalAccount = account;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n[Ctrl+C detected] Shutting down gracefully...");
                try {
                    if (finalAccount != null) {
                        finalAccount.disconnect();
                        finalAccount.close();
                        System.out.println("[Shutdown hook] Disconnected successfully");
                    }
                } catch (Exception e) {
                    // Silently ignore errors during emergency shutdown
                }
            }));

            // Run all streaming demos
            runSymbolTickDemo(account, new String[]{baseSymbol, "GBPUSD"});
            runTradeDemo(account);
            runPositionProfitDemo(account);
            runPositionsTicketsDemo(account);
            runTradeTransactionDemo(account);

            System.out.println();
            HelperUtils.printBox("ALL STREAMING DEMOS COMPLETED");
            System.out.println();
            System.out.println("Closing in 3 seconds...");
            Thread.sleep(3000);

        } catch (Exception e) {
            HelperUtils.printError("Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (account != null) {
                try {
                    System.out.println("\n► Disconnecting from MT5...");

                    // Step 1: Disconnect from terminal - cancels all streaming subscriptions
                    account.disconnect();

                    // Step 2: Close gRPC channel - frees JVM resources
                    account.close();

                    System.out.println("► Disconnected successfully");
                } catch (Exception e) {
                    // Silently ignore all disconnect errors (file locks, etc.)
                    // They are harmless during shutdown
                }
            }
        }
    }

    private static JsonObject loadConfig() throws Exception {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(new FileReader("appsettings.json"), JsonObject.class);
        String defaultConn = root.get("DefaultConnection").getAsString();
        return root.getAsJsonObject("MT5Connections").getAsJsonObject(defaultConn);
    }

    // ══════════════════════════════════════════════════════════════
    // 1. SYMBOL TICK STREAM - Real-time tick data
    //    Subscribe to live price updates for multiple symbols.
    //    Receives bid/ask/volume changes as they occur in the market.
    //    Essential for any price-based trading strategy.
    // ══════════════════════════════════════════════════════════════

    private static void runSymbolTickDemo(MT5Account acc, String[] symbols) throws ApiExceptionMT5, InterruptedException {
        HelperUtils.printSection("1. SYMBOL TICK STREAM - Real-time Tick Data");

        System.out.println("  Subscribing to ticks for: " + String.join(", ", symbols));
        HelperUtils.printWaiting("Waiting for " + MAX_EVENTS + " tick events or " + TIMEOUT_SECONDS + " seconds...");

        // Synchronization: CountDownLatch allows main thread to wait for async events
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger eventCount = new AtomicInteger(0);

        // StreamObserver handles asynchronous events from gRPC stream
        // This is the core pattern for all streaming operations
        StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply> observer = new StreamObserver<Mt5TermApiSubscriptions.OnSymbolTickReply>() {
            @Override
            public void onNext(Mt5TermApiSubscriptions.OnSymbolTickReply reply) {
                // Called every time a new tick event arrives
                // This runs on a separate thread (gRPC thread pool)
                int count = eventCount.incrementAndGet();

                if (reply.hasData()) {
                    Mt5TermApiSubscriptions.OnSymbolTickData data = reply.getData();
                    Mt5TermApiSubscriptions.MrpcSubscriptionMqlTick tick = data.getSymbolTick();

                    HelperUtils.printEvent(count, "TICK", null);
                    HelperUtils.printTick(tick.getSymbol(), tick.getBid(), tick.getAsk(), tick.getVolume());
                }

                if (count >= MAX_EVENTS) {
                    latch.countDown();  // Signal main thread we're done
                }
            }

            @Override
            public void onError(Throwable t) {
                // Called if stream encounters an error
                HelperUtils.printError("Tick stream error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                // Called when stream is gracefully closed by server
                HelperUtils.printCompletion("Tick stream completed");
                latch.countDown();
            }
        };

        // Subscribe to the stream - this starts receiving events asynchronously
        acc.onSymbolTick(symbols, observer);

        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!completed) {
            HelperUtils.printCompletion("Timeout reached, received " + eventCount.get() + " events");
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 2. TRADE STREAM - Trade event notifications
    //    Monitor trading activity: new positions, pending orders,
    //    and historical deals. Triggers when trades are executed.
    //    Useful for tracking trading activity in real-time.
    // ══════════════════════════════════════════════════════════════

    private static void runTradeDemo(MT5Account acc) throws ApiExceptionMT5, InterruptedException {
        HelperUtils.printSection("2. TRADE STREAM - Trade Events");

        System.out.println("  Subscribing to trade events...");
        HelperUtils.printWaiting("Waiting for " + MAX_EVENTS + " trade events or " + TIMEOUT_SECONDS + " seconds...");

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger eventCount = new AtomicInteger(0);

        StreamObserver<Mt5TermApiSubscriptions.OnTradeReply> observer = new StreamObserver<Mt5TermApiSubscriptions.OnTradeReply>() {
            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeReply reply) {
                int count = eventCount.incrementAndGet();

                if (reply.hasData()) {
                    Mt5TermApiSubscriptions.OnTradeData data = reply.getData();
                    Mt5TermApiSubscriptions.OnTadeEventData eventData = data.getEventData();

                    // Count new positions/orders
                    int newPositions = eventData.getNewPositionsCount();
                    int newOrders = eventData.getNewOrdersCount();
                    int newDeals = eventData.getNewHistoryDealsCount();

                    HelperUtils.printEvent(count, "TRADE",
                        String.format("New: %d positions, %d orders, %d deals", newPositions, newOrders, newDeals));
                }

                if (count >= MAX_EVENTS) {
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
                HelperUtils.printError("Trade stream error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                HelperUtils.printCompletion("Trade stream completed");
                latch.countDown();
            }
        };

        acc.onTrade(observer);

        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!completed) {
            HelperUtils.printCompletion("Timeout reached, received " + eventCount.get() + " events");
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 3. POSITION PROFIT STREAM - Real-time profit monitoring
    //    Track profit/loss changes for open positions in real-time.
    //    Critical for risk management and automated stop-loss systems.
    //    Updates as market prices change.
    // ══════════════════════════════════════════════════════════════

    private static void runPositionProfitDemo(MT5Account acc) throws ApiExceptionMT5, InterruptedException {
        HelperUtils.printSection("3. POSITION PROFIT STREAM - Profit Monitoring");

        System.out.println("  Subscribing to position profit updates (every 1000ms)...");
        HelperUtils.printWaiting("Waiting for " + MAX_EVENTS + " profit events or " + TIMEOUT_SECONDS + " seconds...");

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger eventCount = new AtomicInteger(0);

        StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply> observer = new StreamObserver<Mt5TermApiSubscriptions.OnPositionProfitReply>() {
            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionProfitReply reply) {
                int count = eventCount.incrementAndGet();

                if (reply.hasData()) {
                    Mt5TermApiSubscriptions.OnPositionProfitData data = reply.getData();

                    int newCount = data.getNewPositionsCount();
                    int updatedCount = data.getUpdatedPositionsCount();
                    int deletedCount = data.getDeletedPositionsCount();

                    double totalProfit = 0;
                    for (int i = 0; i < updatedCount; i++) {
                        totalProfit += data.getUpdatedPositions(i).getProfit();
                    }

                    HelperUtils.printEvent(count, "PROFIT UPDATE",
                        String.format("New: %d, Updated: %d (P/L: %.2f), Deleted: %d",
                            newCount, updatedCount, totalProfit, deletedCount));
                }

                if (count >= MAX_EVENTS) {
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
                HelperUtils.printError("Position profit stream error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                HelperUtils.printCompletion("Position profit stream completed");
                latch.countDown();
            }
        };

        acc.onPositionProfit(1000, false, observer);

        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!completed) {
            HelperUtils.printCompletion("Timeout reached, received " + eventCount.get() + " events");
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 4. POSITIONS & ORDERS TICKETS STREAM - Tickets monitoring
    //    Monitor position and order ticket numbers in real-time.
    //    Detects when positions are opened/closed or orders placed.
    //    Useful for tracking account state changes.
    // ══════════════════════════════════════════════════════════════

    private static void runPositionsTicketsDemo(MT5Account acc) throws ApiExceptionMT5, InterruptedException {
        HelperUtils.printSection("4. POSITIONS & ORDERS TICKETS STREAM");

        System.out.println("  Subscribing to position/order tickets (every 1000ms)...");
        HelperUtils.printWaiting("Waiting for " + MAX_EVENTS + " ticket events or " + TIMEOUT_SECONDS + " seconds...");

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger eventCount = new AtomicInteger(0);

        StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply reply) {
                int count = eventCount.incrementAndGet();

                if (reply.hasData()) {
                    Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsData data = reply.getData();
                    int positionCount = data.getPositionTicketsCount();
                    int orderCount = data.getPendingOrderTicketsCount();

                    HelperUtils.printEvent(count, "TICKETS UPDATE",
                        String.format("Positions: %d, Pending orders: %d", positionCount, orderCount));
                }

                if (count >= MAX_EVENTS) {
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
                HelperUtils.printError("Tickets stream error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                HelperUtils.printCompletion("Tickets stream completed");
                latch.countDown();
            }
        };

        acc.onPositionsAndPendingOrdersTickets(1000, observer);

        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!completed) {
            HelperUtils.printCompletion("Timeout reached, received " + eventCount.get() + " events");
        }

        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 5. TRADE TRANSACTION STREAM - Transaction events
    //    Most detailed event stream showing all trading actions.
    //    Includes order placement, modification, execution, etc.
    //    Essential for complete trading system integration.
    // ══════════════════════════════════════════════════════════════

    private static void runTradeTransactionDemo(MT5Account acc) throws ApiExceptionMT5, InterruptedException {
        HelperUtils.printSection("5. TRADE TRANSACTION STREAM");

        System.out.println("  Subscribing to trade transactions...");
        HelperUtils.printWaiting("Waiting for " + MAX_EVENTS + " transaction events or " + TIMEOUT_SECONDS + " seconds...");

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger eventCount = new AtomicInteger(0);

        StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply> observer =
            new StreamObserver<Mt5TermApiSubscriptions.OnTradeTransactionReply>() {

            @Override
            public void onNext(Mt5TermApiSubscriptions.OnTradeTransactionReply reply) {
                int count = eventCount.incrementAndGet();

                if (reply.hasData()) {
                    Mt5TermApiSubscriptions.OnTradeTransactionData data = reply.getData();
                    Mt5TermApiSubscriptions.MqlTradeTransaction transaction = data.getTradeTransaction();

                    String transType = transaction.getType().toString();
                    long orderTicket = transaction.getOrderTicket();
                    String symbol = transaction.getSymbol();

                    HelperUtils.printEvent(count, "TRANSACTION",
                        String.format("Type: %s, Order: %d, Symbol: %s", transType, orderTicket, symbol));
                }

                if (count >= MAX_EVENTS) {
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
                HelperUtils.printError("Transaction stream error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                HelperUtils.printCompletion("Transaction stream completed");
                latch.countDown();
            }
        };

        acc.onTradeTransaction(observer);

        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!completed) {
            HelperUtils.printCompletion("Timeout reached, received " + eventCount.get() + " events");
        }

        System.out.println();
    }
}

/*══════════════════════════════════════════════════════════════════════════════
 HOW TO RUN THIS EXAMPLE:

 1. Via run.bat (Recommended - fast):
    run.bat 3  or  .\run.bat 3

 2. Via run-clean.bat (If run.bat fails with compilation errors):
    run-clean.bat 3  or  .\run-clean.bat 3
    → Stops daemon, removes target/, recompiles from scratch
    → Use this if you see "Unresolved compilation problem" errors

 3. Via Maven:
    mvnd compile exec:java -Dexec.args="3"

 4. Via IDE:
    Run this file directly as Java application

 EXPECTED OUTPUT:
   • Real-time tick updates (prices changing)
   • Trade events (if you execute trades during the demo)
   • Position profit updates (if you have open positions)
   • Transaction notifications (detailed trade events)

 UNDERSTANDING STREAMOBSERVER:
   • onNext() - Called when new event arrives
   • onError() - Called if stream encounters error
   • onCompleted() - Called when stream finishes normally

 HOW TO TRIGGER EVENTS:
   • Tick events - happen automatically during market hours
   • Trade events - open/close positions in MT5 terminal
   • Position profit - open a position and watch price move
   • Transactions - any trading action triggers these

 TROUBLESHOOTING:
   • "No events received" → Check market is open (trading hours)
   • "Stream error" → Verify MT5 terminal and MetaRPC gateway are running
   • "Few events" → Normal if market is quiet, try more liquid symbols
   • "Timeout" → Expected if no trading activity occurs

 BEST PRACTICES:
   • Always implement all three StreamObserver methods
   • Use CountDownLatch or similar for synchronization
   • Handle errors gracefully (onError)
   • Unsubscribe when done to free resources

══════════════════════════════════════════════════════════════════════════════*/
