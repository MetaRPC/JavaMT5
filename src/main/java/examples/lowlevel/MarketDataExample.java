/*══════════════════════════════════════════════════════════════════════════════
 FILE: MarketDataExample.java — LOW-LEVEL API: MARKET DATA & ACCOUNT INFO

 LEVEL: 1 (Low-Level API)
 DIFFICULTY: Beginner to Intermediate

 PURPOSE:
   Demonstrates all market data, account information, and history methods.
   This example covers non-trading operations: connection check, account info,
   symbols, history, and market depth (DOM). This is a READ-ONLY example -
   no trading operations are performed.

 WHAT YOU'LL LEARN:
   • How to connect to MT5 via gRPC
   • How to check account balance, equity, margin
   • How to get symbol information (prices, spreads, volumes)
   • How to retrieve historical data
   • How to access market depth (order book)

 📚 WHAT THIS DEMO COVERS (5 Sections, 30+ methods):

   0. CONNECTION CHECK (1 method)
      • checkConnect() - Verify MT5 connection status

   1. ACCOUNT INFORMATION (4 methods)
      • accountSummary() - Complete account data
      • accountInfoDouble() - Numeric properties
      • accountInfoInteger() - Integer properties
      • accountInfoString() - Text properties

   2. SYMBOL INFORMATION (12 methods)
      • quote(), quoteMany() - Current prices
      • symbolSelect(), symbolExist(), symbolIsSynchronized()
      • symbolInfoDouble(), symbolInfoInteger(), symbolInfoString()
      • symbolsTotal(), symbolName()
      • symbolInfoMarginRate() - Margin calculation rates
      • symbolInfoSessionQuote() - Quote session times
      • symbolInfoSessionTrade() - Trade session times

   3. POSITIONS & ORDERS HISTORY (5 methods)
      • positionsTotal() - Count positions
      • openedOrders() - Get all open positions/orders
      • openedOrdersTickets() - Get only ticket numbers
      • orderHistory() - Historical orders
      • positionsHistory() - Historical positions

   4. MARKET DEPTH / DOM (3 methods)
      • marketBookAdd() - Subscribe to DOM
      • marketBookGet() - Get order book data
      • marketBookRelease() - Unsubscribe

 USAGE:
   run.bat 1  or  .\run.bat 1       # Via run.bat (recommended)
   mvnd exec:java -Dexec.args="1"   # Via Maven directly

 PREREQUISITES:
   • MT5 terminal installed and running
   • MetaRPC gRPC gateway (plugin) running in MT5 terminal
   • Valid MT5 account credentials in appsettings.json
   • Network access to gRPC server (mt5win.mrpc.pro)

 NEXT STEPS AFTER THIS DEMO:
   • Try [2] Trading Calculations - learn pre-trade analysis
   • Try [3] Streaming - real-time data feeds
   • Try [4] Market Data Service - simplified API version
══════════════════════════════════════════════════════════════════════════════*/

package examples.lowlevel;

import io.metarpc.mt5.MT5Account;
import io.metarpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class MarketDataExample {

    public static void main(String[] args) {
        // Set UTF-8 encoding for console output
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Fallback if UTF-8 not available
        }

        printBanner();

        MT5Account account = null;

        try {
            // Load configuration
            JsonObject config = loadConfig();
            long user = config.get("user").getAsLong();
            String password = config.get("password").getAsString();
            String grpcServer = config.get("grpcServer").getAsString();
            String serverName = config.get("serverName").getAsString();
            String baseSymbol = config.get("baseSymbol").getAsString();

            System.out.println("Configuration loaded: user=" + user);
            System.out.println();

            // Connect
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

            // Check connection status
            runConnectionCheck(account);

            // Run all market data demos
            runAccountInfoDemo(account);
            runSymbolInfoDemo(account, baseSymbol);
            runHistoryDemo(account);

            // Test DOM with different symbols (try to find one that supports it)
            String[] domTestSymbols = {baseSymbol, "BTCUSD", "US500", "GER40", "AAPL"};
            runMarketDepthDemo(account, domTestSymbols);

            System.out.println();
            System.out.println("+------------------------------------------------------------------+");
            System.out.println("|  ALL MARKET DATA DEMOS COMPLETED                                 |");
            System.out.println("+------------------------------------------------------------------+");
            System.out.println();
            System.out.println("Closing in 3 seconds...");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (account != null) {
                try {
                    System.out.println("\n► Disconnecting from MT5...");
                    account.disconnect();
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
    // 0. CONNECTION CHECK
    //    Verify that gRPC connection to MT5 terminal is active
    //    and session is valid. Essential first step before any
    //    API calls.
    // ══════════════════════════════════════════════════════════════

    private static void runConnectionCheck(MT5Account acc) throws ApiExceptionMT5 {
        printSection("0. CONNECTION STATUS CHECK");

        System.out.println("  [0.1] checkConnect() - Verify MT5 connection:");
        Mt5TermApiConnection.CheckConnectReply checkReply = acc.checkConnect();
        System.out.println("        Is Alive:  " + checkReply.getData().getHealthCheck().getIsAlive());
        System.out.println("        User:      " + acc.getUser());
        System.out.println("        Server:    " + acc.getServerName());
        System.out.println("        Session ID: " + checkReply.getData().getUniqueIdentifier());
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 1. ACCOUNT INFORMATION DEMO
    //    Get account details: balance, equity, margin, leverage,
    //    currency, company name. Shows 4 different ways to query
    //    account properties (summary, double, integer, string).
    // ══════════════════════════════════════════════════════════════

    private static void runAccountInfoDemo(MT5Account acc) throws ApiExceptionMT5 {
        printSection("1. ACCOUNT INFORMATION");

        // [1.1] accountSummary - recommended way to get all account data
        System.out.println("  [1.1] accountSummary() - Get all account data:");
        Mt5TermApiAccountHelper.AccountSummaryReply summaryReply = acc.accountSummary();
        if (summaryReply.hasData()) {
            Mt5TermApiAccountHelper.AccountSummaryData summary = summaryReply.getData();
            System.out.println("        Login:    " + summary.getAccountLogin());
            System.out.println("        Balance:  " + String.format("%.2f %s",
                summary.getAccountBalance(), summary.getAccountCurrency()));
            System.out.println("        Equity:   " + String.format("%.2f", summary.getAccountEquity()));
            System.out.println("        Leverage: 1:" + summary.getAccountLeverage());
            System.out.println("        Company:  " + summary.getAccountCompanyName());
        }
        System.out.println();

        // [1.2] accountInfoDouble - individual numeric properties
        System.out.println("  [1.2] accountInfoDouble() - Get individual numeric values:");
        Mt5TermApiAccountInformation.AccountInfoDoubleReply balanceReply =
            acc.accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_BALANCE);
        System.out.println("        Balance: " + String.format("%.2f", balanceReply.getData().getRequestedValue()));

        Mt5TermApiAccountInformation.AccountInfoDoubleReply equityReply =
            acc.accountInfoDouble(Mt5TermApiAccountInformation.AccountInfoDoublePropertyType.ACCOUNT_EQUITY);
        System.out.println("        Equity:  " + String.format("%.2f", equityReply.getData().getRequestedValue()));
        System.out.println();

        // [1.3] accountInfoInteger - integer properties
        System.out.println("  [1.3] accountInfoInteger() - Get integer properties:");
        Mt5TermApiAccountInformation.AccountInfoIntegerReply loginReply =
            acc.accountInfoInteger(Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType.ACCOUNT_LOGIN);
        System.out.println("        Login:    " + loginReply.getData().getRequestedValue());

        Mt5TermApiAccountInformation.AccountInfoIntegerReply leverageReply =
            acc.accountInfoInteger(Mt5TermApiAccountInformation.AccountInfoIntegerPropertyType.ACCOUNT_LEVERAGE);
        System.out.println("        Leverage: 1:" + leverageReply.getData().getRequestedValue());
        System.out.println();

        // [1.4] accountInfoString - string properties
        System.out.println("  [1.4] accountInfoString() - Get string properties:");
        Mt5TermApiAccountInformation.AccountInfoStringReply currencyReply =
            acc.accountInfoString(Mt5TermApiAccountInformation.AccountInfoStringPropertyType.ACCOUNT_CURRENCY);
        System.out.println("        Currency: " + currencyReply.getData().getRequestedValue());

        Mt5TermApiAccountInformation.AccountInfoStringReply companyReply =
            acc.accountInfoString(Mt5TermApiAccountInformation.AccountInfoStringPropertyType.ACCOUNT_COMPANY);
        System.out.println("        Company:  " + companyReply.getData().getRequestedValue());
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 2. SYMBOL INFORMATION DEMO
    //    Get detailed information about trading symbols: prices,
    //    spreads, volumes, digits, point size, trading sessions.
    //    Essential for understanding symbol properties before trading.
    // ══════════════════════════════════════════════════════════════

    private static void runSymbolInfoDemo(MT5Account acc, String symbol) throws ApiExceptionMT5 {
        printSection("2. SYMBOL INFORMATION");

        // [2.1] symbolSelect - ensure symbol is in Market Watch
        System.out.println("  [2.1] symbolSelect() - Select symbol in Market Watch:");
        acc.symbolSelect(symbol, true);
        System.out.println("        " + symbol + " selected");
        System.out.println();

        // [2.2] symbolExist - check if symbol exists
        System.out.println("  [2.2] symbolExist() - Check symbol existence:");
        Mt5TermApiMarketInfo.SymbolExistReply existReply = acc.symbolExist(symbol);
        System.out.println("        " + symbol + " exists: " + existReply.getData().getExists());
        System.out.println();

        // [2.3] symbolIsSynchronized - check data sync
        System.out.println("  [2.3] symbolIsSynchronized() - Check data synchronization:");
        Mt5TermApiMarketInfo.SymbolIsSynchronizedReply syncReply = acc.symbolIsSynchronized(symbol);
        System.out.println("        " + symbol + " synchronized: " + syncReply.getData().getSynchronized());
        System.out.println();

        // [2.4] quote - get current tick/price
        System.out.println("  [2.4] quote() - Get current prices:");
        Mt5TermApiMarketInfo.SymbolInfoTickRequestReply quoteReply = acc.quote(symbol);
        System.out.println("        Bid:    " + String.format("%.5f", quoteReply.getData().getBid()));
        System.out.println("        Ask:    " + String.format("%.5f", quoteReply.getData().getAsk()));
        System.out.println("        Last:   " + String.format("%.5f", quoteReply.getData().getLast()));
        System.out.println("        Volume: " + quoteReply.getData().getVolume());
        System.out.println();

        // [2.5] symbolInfoDouble - get numeric symbol properties
        System.out.println("  [2.5] symbolInfoDouble() - Get symbol numeric properties:");
        Mt5TermApiMarketInfo.SymbolInfoDoubleReply pointReply =
            acc.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_POINT);
        System.out.println("        Point:      " + String.format("%.5f", pointReply.getData().getValue()));

        Mt5TermApiMarketInfo.SymbolInfoDoubleReply volMinReply =
            acc.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MIN);
        System.out.println("        Volume Min: " + String.format("%.2f", volMinReply.getData().getValue()));

        Mt5TermApiMarketInfo.SymbolInfoDoubleReply volMaxReply =
            acc.symbolInfoDouble(symbol, Mt5TermApiMarketInfo.SymbolInfoDoubleProperty.SYMBOL_VOLUME_MAX);
        System.out.println("        Volume Max: " + String.format("%.2f", volMaxReply.getData().getValue()));
        System.out.println();

        // [2.6] symbolInfoInteger - get integer symbol properties
        System.out.println("  [2.6] symbolInfoInteger() - Get symbol integer properties:");
        Mt5TermApiMarketInfo.SymbolInfoIntegerReply digitsReply =
            acc.symbolInfoInteger(symbol, Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_DIGITS);
        System.out.println("        Digits: " + digitsReply.getData().getValue());

        Mt5TermApiMarketInfo.SymbolInfoIntegerReply spreadReply =
            acc.symbolInfoInteger(symbol, Mt5TermApiMarketInfo.SymbolInfoIntegerProperty.SYMBOL_SPREAD);
        System.out.println("        Spread: " + spreadReply.getData().getValue() + " points");
        System.out.println();

        // [2.7] symbolInfoString - get string symbol properties
        System.out.println("  [2.7] symbolInfoString() - Get symbol string properties:");
        Mt5TermApiMarketInfo.SymbolInfoStringReply descReply =
            acc.symbolInfoString(symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_DESCRIPTION);
        System.out.println("        Description: " + descReply.getData().getValue());

        Mt5TermApiMarketInfo.SymbolInfoStringReply baseReply =
            acc.symbolInfoString(symbol, Mt5TermApiMarketInfo.SymbolInfoStringProperty.SYMBOL_CURRENCY_BASE);
        System.out.println("        Base:        " + baseReply.getData().getValue());
        System.out.println();

        // [2.8] symbolsTotal - count symbols
        System.out.println("  [2.8] symbolsTotal() - Count available symbols:");
        Mt5TermApiMarketInfo.SymbolsTotalReply totalReply = acc.symbolsTotal(false);
        System.out.println("        Total symbols: " + totalReply.getData().getTotal());

        Mt5TermApiMarketInfo.SymbolsTotalReply selectedReply = acc.symbolsTotal(true);
        System.out.println("        Selected:      " + selectedReply.getData().getTotal());
        System.out.println();

        // [2.9] symbolName - get symbol name by index
        System.out.println("  [2.9] symbolName() - Get symbol by index:");
        Mt5TermApiMarketInfo.SymbolNameReply nameReply = acc.symbolName(0, true);
        System.out.println("        First selected symbol: " + nameReply.getData().getName());
        System.out.println();

        // [2.10] symbolInfoMarginRate - get margin rates
        System.out.println("  [2.10] symbolInfoMarginRate() - Get margin calculation rates:");
        try {
            Mt5TermApiMarketInfo.SymbolInfoMarginRateReply marginRateReply = acc.symbolInfoMarginRate(
                symbol,
                Mt5TermApiMarketInfo.ENUM_ORDER_TYPE.ORDER_TYPE_BUY
            );
            System.out.println("        Initial margin rate:     " + String.format("%.4f", marginRateReply.getData().getInitialMarginRate()));
            System.out.println("        Maintenance margin rate: " + String.format("%.4f", marginRateReply.getData().getMaintenanceMarginRate()));
        } catch (ApiExceptionMT5 e) {
            System.out.println("        Not available for this symbol: " + e.getMessage());
        }
        System.out.println();

        // [2.11] symbolInfoSessionQuote - get quote session info
        System.out.println("  [2.11] symbolInfoSessionQuote() - Get quote session times:");
        try {
            Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply quoteSessionReply = acc.symbolInfoSessionQuote(
                symbol,
                Mt5TermApiMarketInfo.DayOfWeek.MONDAY,
                0
            );
            System.out.println("        Monday quote session: from=" + quoteSessionReply.getData().getFrom().getSeconds() +
                             "s to=" + quoteSessionReply.getData().getTo().getSeconds() + "s");
        } catch (ApiExceptionMT5 e) {
            System.out.println("        Not available: " + e.getMessage());
        }
        System.out.println();

        // [2.12] symbolInfoSessionTrade - get trade session info
        System.out.println("  [2.12] symbolInfoSessionTrade() - Get trade session times:");
        try {
            Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply tradeSessionReply = acc.symbolInfoSessionTrade(
                symbol,
                Mt5TermApiMarketInfo.DayOfWeek.MONDAY,
                0
            );
            System.out.println("        Monday trade session: from=" + tradeSessionReply.getData().getFrom().getSeconds() +
                             "s to=" + tradeSessionReply.getData().getTo().getSeconds() + "s");
        } catch (ApiExceptionMT5 e) {
            System.out.println("        Not available: " + e.getMessage());
        }
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 3. POSITIONS & ORDERS HISTORY DEMO
    //    Query open positions, pending orders, and their tickets.
    //    Shows how to check current trading state and retrieve
    //    historical trading data.
    // ══════════════════════════════════════════════════════════════

    private static void runHistoryDemo(MT5Account acc) throws ApiExceptionMT5 {
        printSection("3. POSITIONS & ORDERS HISTORY");

        // [3.1] positionsTotal - count current positions
        System.out.println("  [3.1] positionsTotal() - Count open positions:");
        Mt5TermApiTradeFunctions.PositionsTotalReply posTotalReply = acc.positionsTotal();
        System.out.println("        Open positions: " + posTotalReply.getData().getTotalPositions());
        System.out.println();

        // [3.2] openedOrders - get all open positions and orders
        System.out.println("  [3.2] openedOrders() - Get detailed position/order info:");
        Mt5TermApiAccountHelper.OpenedOrdersReply ordersReply =
            acc.openedOrders(Mt5TermApiAccountHelper.BMT5_ENUM_OPENED_ORDER_SORT_TYPE.BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC);
        System.out.println("        Pending orders: " + ordersReply.getData().getOpenedOrdersCount());
        System.out.println("        Open positions: " + ordersReply.getData().getPositionInfosCount());
        System.out.println();

        // [3.3] openedOrdersTickets - get only ticket numbers
        System.out.println("  [3.3] openedOrdersTickets() - Get ticket numbers only:");
        Mt5TermApiAccountHelper.OpenedOrdersTicketsReply ticketsReply = acc.openedOrdersTickets();
        System.out.println("        Order tickets:    " + ticketsReply.getData().getOpenedOrdersTicketsCount());
        System.out.println("        Position tickets: " + ticketsReply.getData().getOpenedPositionTicketsCount());
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════
    // 4. MARKET DEPTH (DOM) DEMO - ORDER BOOK
    //    Access Level 2 market data showing buy/sell orders at
    //    different price levels. Typically available for stocks,
    //    futures, and cryptocurrencies.
    // ══════════════════════════════════════════════════════════════

    private static void runMarketDepthDemo(MT5Account acc, String[] symbols) throws ApiExceptionMT5 {
        printSection("4. MARKET DEPTH (DOM) - ORDER BOOK");

        System.out.println("  DOM (Depth of Market) - shows buy/sell price levels");
        System.out.println("  Testing different symbols to find DOM support...");
        System.out.println();

        boolean foundWorkingDom = false;

        for (String symbol : symbols) {
            System.out.println("  Testing symbol: " + symbol);
            try {
                // Ensure symbol is selected first
                acc.symbolSelect(symbol, true);

                // [4.1] marketBookAdd - subscribe to DOM
                Mt5TermApiMarketInfo.MarketBookAddReply domAdd = acc.marketBookAdd(symbol);
                boolean success = domAdd.getData().getOpenedSuccessfully();

                if (!success) {
                    System.out.println("    [X] Subscription failed");
                    continue;
                }

                System.out.println("    [OK] Subscription successful!");

                // [4.2] marketBookGet - get order book data
                Mt5TermApiMarketInfo.MarketBookGetReply domData = acc.marketBookGet(symbol);
                int totalEntries = domData.getData().getMqlBookInfosCount();

                if (totalEntries == 0) {
                    System.out.println("    [X] Order book empty (0 entries)");
                    acc.marketBookRelease(symbol);
                    continue;
                }

                // Success! Show the order book
                foundWorkingDom = true;
                System.out.println();
                System.out.println("    ┌─────────────────────────────────────────────────────┐");
                System.out.println("    │  ORDER BOOK for " + symbol + "                              │");
                System.out.println("    │  Total levels: " + totalEntries + "                                 │");
                System.out.println("    ├─────────────────────────────────────────────────────┤");
                System.out.println("    │  Type │     Price      │  Volume                    │");
                System.out.println("    ├─────────────────────────────────────────────────────┤");

                // Show all entries (up to 20)
                int displayCount = Math.min(20, totalEntries);
                for (int i = 0; i < displayCount; i++) {
                    Mt5TermApiMarketInfo.MrpcMqlBookInfo entry = domData.getData().getMqlBookInfos(i);
                    String type = entry.getType().getNumber() == 1 ? "SELL" : "BUY ";
                    System.out.println(String.format("    │  %s │  %12.5f  │  %8.2f                  │",
                        type, entry.getPrice(), entry.getVolume()));
                }
                System.out.println("    └─────────────────────────────────────────────────────┘");
                System.out.println();

                // [4.3] marketBookRelease - unsubscribe
                Mt5TermApiMarketInfo.MarketBookReleaseReply domRelease = acc.marketBookRelease(symbol);
                System.out.println("    [OK] Unsubscribed from DOM: " + domRelease.getData().getClosedSuccessfully());
                System.out.println();
                break; // Found working DOM, stop testing

            } catch (Exception ex) {
                System.out.println("    [X] Error: " + ex.getMessage().substring(0, Math.min(60, ex.getMessage().length())));
            }
        }

        if (!foundWorkingDom) {
            System.out.println();
            System.out.println("  [!] DOM not available for any of the tested symbols");
            System.out.println("  Note: Most Forex pairs do not support DOM.");
            System.out.println("  DOM is typically available for stocks, futures, or cryptocurrencies.");
        }
        System.out.println();
    }

    private static void printBanner() {
        System.out.println("+------------------------------------------------------------------+");
        System.out.println("|         MARKET DATA & ACCOUNT INFORMATION DEMO                   |");
        System.out.println("|  Account Info, Symbols, History, Market Depth                    |");
        System.out.println("+------------------------------------------------------------------+");
        System.out.println();
    }

    private static void printSection(String title) {
        System.out.println("------------------------------------------------------------------");
        System.out.println(title);
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }
}

/*══════════════════════════════════════════════════════════════════════════════
 HOW TO RUN THIS EXAMPLE:

 1. Via run.bat (Recommended - fast):
    run.bat 1  or  .\run.bat 1

 2. Via run-clean.bat (If run.bat fails with compilation errors):
    run-clean.bat 1  or  .\run-clean.bat 1
    → Stops daemon, removes target/, recompiles from scratch
    → Use this if you see "Unresolved compilation problem" errors

 3. Via Maven:
    mvnd compile exec:java -Dexec.args="1"

 4. Via IDE:
    Run this file directly as Java application

 EXPECTED OUTPUT:
   • Connection status and session info
   • Account details (balance, equity, leverage)
   • Symbol information (prices, spreads, volumes)
   • Position/order counts
   • Market depth (if available for symbol)

 TROUBLESHOOTING:
   • "Connection refused" → Check MT5 terminal and MetaRPC gateway are running
   • "Invalid credentials" → Verify appsettings.json has correct login/password
   • "Symbol not found" → Change baseSymbol in appsettings.json
   • "DOM not available" → Normal for Forex pairs, try stocks/futures
   • "Unresolved compilation problem" → Use run-clean.bat instead of run.bat

══════════════════════════════════════════════════════════════════════════════*/