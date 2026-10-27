/*==============================================================================
 FILE: UserCode.java — YOUR SANDBOX FOR CUSTOM MT5 CODE

 This is your playground. Connection, config and the three API levels
 (account / service / sugar) are already wired up for you — just add your
 trading logic in the "YOUR CODE HERE" section below.

 RUN:
   run.bat 0           (Windows)      ./run.sh 0        (Linux/macOS)
   run.bat usercode                   ./run.sh usercode

 WHAT'S READY FOR YOU INSIDE main():
   • account  — low-level gRPC client   (MT5Account)
   • service  — mid-level wrapper        (MT5Service)
   • sugar    — high-level convenience   (MT5Sugar)
   • a live connection to the MT5 terminal (from appsettings.json)
   • automatic cleanup (disconnect + close) in the finally block
==============================================================================*/

package examples.usercode;

import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.MT5Service;
import pro.mrpc.mt5.MT5Sugar;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;

import java.io.InputStream;

public class UserCode {

    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception ignored) { }

        System.out.println("\n+============================================================+");
        System.out.println("|  USER CODE SANDBOX                                         |");
        System.out.println("+============================================================+\n");

        MT5Account account = null;
        try {
            // ---- Load credentials from appsettings.json (DefaultConnection) ----
            InputStream is = UserCode.class.getClassLoader().getResourceAsStream("appsettings.json");
            if (is == null) is = new java.io.FileInputStream("appsettings.json");
            String json = new String(is.readAllBytes());
            is.close();

            String section = json.split("\"FxProDemo\":\\s*\\{")[1].split("\\}")[0];
            long user = Long.parseLong(section.split("\"user\":\\s*")[1].split(",")[0].trim());
            String password = section.split("\"password\":\\s*\"")[1].split("\"")[0];
            String grpcServer = section.contains("\"grpcServer\"")
                ? section.split("\"grpcServer\":\\s*\"")[1].split("\"")[0] : null;
            String apiKey = section.contains("\"apiKey\"")
                ? section.split("\"apiKey\":\\s*\"")[1].split("\"")[0] : null;
            String serverName = section.contains("\"serverName\"")
                ? section.split("\"serverName\":\\s*\"")[1].split("\"")[0] : "MetaQuotes-Demo";

            // Optional: override the API key from the command line (--api-key=...)
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].startsWith("--api-key=")) apiKey = args[i].substring("--api-key=".length());
                    else if (args[i].equals("--api-key") && i + 1 < args.length) apiKey = args[i + 1];
                }
            }

            // ---- Build the full stack and connect ----
            account = new MT5Account(user, password, grpcServer, apiKey, null);
            MT5Service service = new MT5Service(account);
            MT5Sugar sugar = new MT5Sugar(service);

            System.out.println("Connecting to MT5 (user=" + user + ", server=" + serverName + ") ...");
            account.connectByServerName(serverName, "EURUSD");
            System.out.println("Connected.\n");

            // =================================================================
            // YOUR CODE HERE
            // Uncomment an example or write your own logic. You can freely mix
            // sugar / service / account in the same block.
            // =================================================================

            // --- Example 1: account balance (easiest, via Sugar) ---
            double balance = sugar.getBalance();
            System.out.printf("Balance: %.2f%n", balance);

            // --- Example 2: current price for a symbol ---
            // System.out.printf("EURUSD  bid=%.5f  ask=%.5f%n",
            //     sugar.getBid("EURUSD"), sugar.getAsk("EURUSD"));

            // --- Example 3: open a market BUY (0.01 lot, no SL/TP) ---
            // long ticket = sugar.buyMarket("EURUSD", 0.01, null, null);
            // System.out.println("Opened position #" + ticket);

            // --- Example 4: open by risk ($10 risk, 500-point SL, 1000-point TP) ---
            // long t = sugar.buyByRisk("EURUSD", 500, 10.0, 1000);
            // System.out.println("Opened by-risk position #" + t);

            // --- Example 5: close everything for a symbol ---
            // int closed = sugar.closeAll("EURUSD");
            // System.out.println("Closed " + closed + " positions");

            // TODO: write your trading logic here ...

            // =================================================================
            // END OF YOUR CODE
            // =================================================================

        } catch (ApiExceptionMT5 e) {
            System.err.println("API error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (account != null) {
                try { account.disconnect(); account.close(); } catch (Exception ignored) { }
            }
            System.out.println("\nDone.");
        }
    }
}
