/*══════════════════════════════════════════════════════════════════════════════
 FILE: MarketDataServiceExample.java — SERVICE API: MARKET DATA WITH WRAPPERS

 LEVEL: 2 (Service API)
 DIFFICULTY: Beginner

 PURPOSE:
   Same functionality as Example 1 (Low-Level), but with convenient wrappers!
   This example demonstrates MT5Service - a simplified API layer that:
   • Automatically extracts .getData() from gRPC responses
   • Provides shortcut methods (getBalance(), getEquity(), etc.)
   • Reduces boilerplate code by 50-70%
   • Returns ready-to-use data objects instead of Reply wrappers

 🎯 WHY USE SERVICE API INSTEAD OF LOW-LEVEL?

   LOW-LEVEL (Example 1):                    SERVICE API (This Example):
   ────────────────────────────────────────────────────────────────────────
   Reply reply = acc.accountSummary();       Data summary = service.accountSummary();
   Data data = reply.getData();              // .getData() already extracted!
   double balance = data.getBalance();       double balance = summary.getBalance();

   OR even simpler:
   double balance = service.getBalance();    // Direct shortcut method!

 📚 WHAT YOU'LL LEARN:
   • How MT5Service wraps MT5Account for convenience
   • Shortcut methods: getBalance(), getEquity(), getMargin(), etc.
   • Auto .getData() extraction on all methods
   • How to write cleaner trading code

 USAGE:
   run.bat 4  or  .\run.bat 4               # Via run.bat (recommended)
   mvnd exec:java -Dexec.args="4"           # Via Maven

 PREREQUISITES:
   • Same as Example 1 (MT5 terminal, MetaRPC gateway, credentials)

 NEXT STEPS:
   • Compare with Example 1 to see the difference in code complexity
   • Try Example 5 (Trading Service) for trading operations wrappers
══════════════════════════════════════════════════════════════════════════════*/

package examples.services;

import io.metarpc.mt5.MT5Service;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class MarketDataServiceExample {

    public static void main(String[] args) {
        // Set UTF-8 encoding for console output
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        System.out.println("\n------------------------------------------------------------------");
        System.out.println("MT5 SERVICE - MARKET DATA EXAMPLE");
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
            // SECTION 1: CONNECTION & HEALTH CHECK
            //
            // WRAPPER BENEFIT: isConnected() returns boolean directly
            //                  vs low-level: checkConnect().getData().getHealthCheck().getIsAlive()
            // ══════════════════════════════════════════════════════════════

            section("1. CONNECTION & HEALTH CHECK");

            demo("isConnected()", "Check connection status");
            boolean isAlive = service.isConnected();
            result("Status", isAlive ? "✓ Connected" : "✗ Disconnected");

            demo("healthCheck()", "Get terminal health info");
            try {
                Mt5TermApiHealthCheck.HealthCheckReply health = service.healthCheck();
                result("Server Connected", String.valueOf(health.getIsConnectedToServer()));
                result("Server Time", String.valueOf(health.getServerTimeSeconds()));
            } catch (ApiExceptionMT5 e) {
                result("Health Check", "Not available on this server version");
            }

            // ══════════════════════════════════════════════════════════════
            // SECTION 2: ACCOUNT INFORMATION
            //
            // WRAPPER BENEFIT: Direct shortcut methods
            //   • service.getBalance()    vs  acc.accountSummary().getData().getBalance()
            //   • service.getEquity()     vs  acc.accountInfoDouble(...).getData().getValue()
            //   • service.getMargin()     saves 3-4 lines of code!
            //
            // All methods auto-extract .getData() - no Reply wrapper needed
            // ══════════════════════════════════════════════════════════════

            section("2. ACCOUNT INFORMATION - Convenience Methods");

            demo("getBalance(), getEquity(), getProfit()", "Shortcuts for common properties");
            result("Balance", String.format("$%.2f", service.getBalance()));
            result("Equity", String.format("$%.2f", service.getEquity()));
            result("Profit", String.format("$%.2f", service.getProfit()));

            demo("getMargin(), getFreeMargin()", "Margin information");
            result("Margin Used", String.format("$%.2f", service.getMargin()));
            result("Free Margin", String.format("$%.2f", service.getFreeMargin()));

            demo("getLogin(), getLeverage(), getCurrency()", "Account details");
            result("Login", String.valueOf(service.getLogin()));
            result("Leverage", "1:" + service.getLeverage());

            demo("accountSummary()", "Complete account snapshot (auto .getData())");
            Mt5TermApiAccountHelper.AccountSummaryData summary = service.accountSummary();
            result("Currency", summary.getAccountCurrency());
            result("Company", summary.getAccountCompanyName());
            result("User Name", summary.getAccountUserName());
            result("Account Name", service.getAccountName());
            result("Server Name", service.getServerName());

            // ══════════════════════════════════════════════════════════════
            // SECTION 3: SYMBOL INFORMATION & QUOTES
            //
            // WRAPPER BENEFIT: Returns data objects directly
            //   • service.quote(symbol)          → returns Tick (not TickReply)
            //   • service.symbolInfoDouble(...)  → returns Data (not Reply.getData())
            //   • service.symbolExist(symbol)    → returns boolean (not Reply.getData().getExists())
            //
            // Special: quoteMany(symbols[]) - batch quotes in one call!
            // ══════════════════════════════════════════════════════════════

            section("3. SYMBOL INFORMATION & QUOTES");

            String symbol = baseSymbol;

            demo("quote(symbol)", "Get current tick (auto .getData())");
            Mt5TermApiMarketInfo.MrpcMqlTick tick = service.quote(symbol);
            result("Bid", String.format("%.5f", tick.getBid()));
            result("Ask", String.format("%.5f", tick.getAsk()));
            result("Spread", String.format("%.5f", tick.getAsk() - tick.getBid()));
            result("Time", String.valueOf(tick.getTime()));

            demo("quoteMany(symbols)", "Get multiple quotes at once");
            String[] symbols = {baseSymbol, "GBPUSD", "USDJPY"};
            Mt5TermApiMarketInfo.SymbolInfoTickRequestReply[] quotes = service.quoteMany(symbols);
            for (int i = 0; i < quotes.length; i++) {
                if (quotes[i].getData() != null && quotes[i].getData().getBid() > 0) {
                    result(symbols[i],
                           String.format("Bid: %.5f | Ask: %.5f",
                                       quotes[i].getData().getBid(),
                                       quotes[i].getData().getAsk()));
                }
            }

            demo("symbolsTotal(selected)", "Count symbols (auto .getData().getTotal())");
            int totalSymbols = service.symbolsTotal(false);
            int selectedSymbols = service.symbolsTotal(true);
            result("Total Symbols", String.valueOf(totalSymbols));
            result("Selected Symbols", String.valueOf(selectedSymbols));

            demo("symbolExist(name)", "Check if symbol exists (auto .getData().getExists())");
            result("EURUSD exists?", service.symbolExist("EURUSD") ? "✓ Yes" : "✗ No");
            result("INVALID exists?", service.symbolExist("INVALID") ? "✓ Yes" : "✗ No");

            demo("symbolIsSynchronized(symbol)", "Check sync status (auto .getData().getSynchronized())");
            result(symbol + " synchronized?",
                   service.symbolIsSynchronized(symbol) ? "✓ Yes" : "✗ No");

            demo("symbolInfoDouble(symbol, property)", "Get numeric properties (auto .getData())");
            Mt5TermApiMarketInfo.SymbolInfoDoubleData bidData = service.symbolInfoDouble(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_BID
            );
            result("Current Bid", String.format("%.5f", bidData.getValue()));

            demo("symbolInfoInteger(symbol, property)", "Get integer properties (auto .getData())");
            Mt5TermApiMarketInfo.SymbolInfoIntegerData digits = service.symbolInfoInteger(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS
            );
            result("Digits", String.valueOf(digits.getValue()));

            demo("symbolInfoString(symbol, property)", "Get string properties (auto .getData())");
            Mt5TermApiMarketInfo.SymbolInfoStringData description = service.symbolInfoString(
                symbol,
                Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION
            );
            result("Description", description.getValue());

            demo("symbolInfoMarginRate(symbol, orderType)", "Get margin rates (auto .getData())");
            try {
                Mt5TermApiMarketInfo.SymbolInfoMarginRateData marginRate = service.symbolInfoMarginRate(
                    symbol,
                    Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.ORDER_TYPE_BUY
                );
                result("Initial Margin", String.format("%.4f", marginRate.getInitialMarginRate()));
                result("Maintenance Margin", String.format("%.4f", marginRate.getMaintenanceMarginRate()));
            } catch (ApiExceptionMT5 e) {
                result("Margin Rate", "Not available for this symbol");
            }

            // ══════════════════════════════════════════════════════════════
            // SECTION 4: POSITIONS & HISTORY
            //
            // WRAPPER BENEFIT: All methods return Data objects directly
            //   service.positionsTotal()      → Data (not Reply.getData())
            //   service.openedOrders()        → Data (not Reply.getData())
            //   service.openedOrdersTickets() → Data (not Reply.getData())
            // ══════════════════════════════════════════════════════════════

            section("4. POSITIONS & HISTORY");

            demo("positionsTotal()", "Get open positions count (auto .getData())");
            Mt5TermApiTradeFunctions.PositionsTotalData positions = service.positionsTotal();
            result("Open Positions", String.valueOf(positions.getTotalPositions()));

            demo("openedOrders()", "Get all open orders and positions (auto .getData())");
            Mt5TermApiAccountHelper.OpenedOrdersData orders = service.openedOrders();
            result("Pending Orders", String.valueOf(orders.getOpenedOrdersCount()));
            result("Total Positions", String.valueOf(orders.getPositionInfosCount()));

            demo("openedOrdersTickets()", "Get ticket numbers only (auto .getData())");
            Mt5TermApiAccountHelper.OpenedOrdersTicketsData tickets = service.openedOrdersTickets();
            result("Order Tickets", String.valueOf(tickets.getOpenedOrdersTicketsCount()));
            result("Position Tickets", String.valueOf(tickets.getOpenedPositionTicketsCount()));

            // ══════════════════════════════════════════════════════════════
            // SECTION 5: MARKET DEPTH (DOM)
            //
            // WRAPPER BENEFIT: marketBookGet() returns Data directly
            //   No need to check .hasData() and extract .getData()
            // ══════════════════════════════════════════════════════════════

            section("5. MARKET DEPTH (DOM)");

            demo("marketBookAdd(symbol)", "Subscribe to market depth");
            try {
                service.marketBookAdd(symbol);
                result("Subscription", "✓ Success");

                demo("marketBookGet(symbol)", "Get order book data (auto .getData())");
                Mt5TermApiMarketInfo.MarketBookGetData book = service.marketBookGet(symbol);
                result("Depth Levels", String.valueOf(book.getMqlBookInfosCount()));

                if (book.getMqlBookInfosCount() > 0) {
                    System.out.println("\n    ┌─────────────────────────────────────┐");
                    System.out.println("    │         ORDER BOOK (Top 5)          │");
                    System.out.println("    ├─────────────────────────────────────┤");
                    int limit = Math.min(5, book.getMqlBookInfosCount());
                    for (int i = 0; i < limit; i++) {
                        Mt5TermApiMarketInfo.MrpcMqlBookInfo entry = book.getMqlBookInfos(i);
                        String type = entry.getType().getNumber() == 1 ? "SELL" : "BUY ";
                        System.out.printf("    │ %s  %.5f  Vol: %.2f         │%n",
                            type, entry.getPrice(), entry.getVolumeReal());
                    }
                    System.out.println("    └─────────────────────────────────────┘\n");
                }

                demo("marketBookRelease(symbol)", "Unsubscribe from market depth");
                service.marketBookRelease(symbol);
                result("Unsubscribe", "✓ Success");

            } catch (ApiExceptionMT5 e) {
                result("Market Depth", "Not available for this symbol");
            }

            // ═══════════════════════════════════════════════════════════

            System.out.println("\n------------------------------------------------------------------");
            System.out.println("All market data operations completed successfully");
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

                    // Step 1: Disconnect from terminal - cancels all subscriptions
                    service.disconnect();

                    // Step 2: Close gRPC channel - frees JVM resources
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

                      KEY DIFFERENCES FROM LOW-LEVEL API

══════════════════════════════════════════════════════════════════════════════

┌─────────────────────────────────────────────────────────────────────────────┐
│ FEATURE              │ LOW-LEVEL (Example 1)      │ SERVICE (Example 4)     │
├─────────────────────────────────────────────────────────────────────────────┤
│ Get Balance          │ acc.accountSummary()       │ service.getBalance()    │
│                      │   .getData()               │ // Direct!              │
│                      │   .getBalance()            │                         │
├─────────────────────────────────────────────────────────────────────────────┤
│ Get Tick             │ Reply reply = acc.quote()  │ Tick tick =             │
│                      │ Tick tick = reply          │   service.quote(symbol) │
│                      │   .getData()               │ // Already extracted!   │
├─────────────────────────────────────────────────────────────────────────────┤
│ Check if connected   │ acc.checkConnect()         │ service.isConnected()   │
│                      │   .getData()               │ // Returns boolean      │
│                      │   .getHealthCheck()        │                         │
│                      │   .getIsAlive()            │                         │
├─────────────────────────────────────────────────────────────────────────────┤
│ Symbol exists?       │ Reply reply =              │ boolean exists =        │
│                      │   acc.symbolExist(name)    │   service.symbolExist() │
│                      │ boolean exists =           │ // Direct boolean!      │
│                      │   reply.getData()          │                         │
│                      │        .getExists()        │                         │
└─────────────────────────────────────────────────────────────────────────────┘

BENEFITS:
  ✓ 50-70% less boilerplate code
  ✓ No need to manually call .getData() on every response
  ✓ Shortcut methods for common operations (getBalance, getEquity, etc.)
  ✓ Cleaner, more readable code
  ✓ Same underlying gRPC calls - just wrapped for convenience

WHEN TO USE:
  → Use SERVICE API (this) for most trading applications
  → Use LOW-LEVEL API (Example 1) when you need full control over Reply objects


══════════════════════════════════════════════════════════════════════════════

                               HOW TO RUN

══════════════════════════════════════════════════════════════════════════════

1. Via run.bat (Recommended - fast):
   run.bat 4  or  .\run.bat 4

2. Via run-clean.bat (If run.bat fails with compilation errors):
   run-clean.bat 4  or  .\run-clean.bat 4
   → Stops daemon, removes target/, recompiles from scratch
   → Use this if you see "Unresolved compilation problem" errors

3. Via Maven:
   mvnd compile exec:java -Dexec.args="4"

══════════════════════════════════════════════════════════════════════════════*/
