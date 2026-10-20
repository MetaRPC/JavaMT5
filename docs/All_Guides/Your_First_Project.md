# Your First Project in 10 Minutes (Java)

> **Hands-on Quick Start** - Create a working trading project with MetaTrader 5 and JavaMT5 from scratch.

---

## Step 0: Obtain Your API Key

To connect to MetaRPC endpoints (`mt5.mrpc.pro:443`), obtain your API key:
1. Register for free at [https://mrpc.pro/signup](https://mrpc.pro/signup).
2. Generate your API token in your dashboard at [https://mrpc.pro/my](https://mrpc.pro/my).
3. Set your token in your environment or connection config.

---

## Step 1: Create Your Project

Create a new directory for your trading bot:

```bash
mkdir my_javamt5_bot
cd my_javamt5_bot
```

Add the SDK to the project's `build.gradle`:

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.MetaRPC:JavaMT5:main-SNAPSHOT'   // or a commit hash of the repository
}
```

From [JitPack](https://jitpack.io/#MetaRPC/JavaMT5), built from the GitHub repository; Java 11+. It is not on Maven Central.
Or build it yourself: `git clone https://github.com/MetaRPC/JavaMT5.git && cd JavaMT5 && mvn install` installs
`pro.mrpc:mt5:1.0.0` into your local Maven repository.

---

## Step 2: Write Your Trading Code

`src/main/java/App.java` (it connects, reads the account and disconnects):

```java
import mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryData;
import pro.mrpc.mt5.MT5Account;

public class App {
    public static void main(String[] args) throws Exception {
        try (MT5Account account = new MT5Account(Long.parseLong(System.getenv("MT5_LOGIN")), System.getenv("MT5_PASSWORD"),
                "mt5.mrpc.pro:443", System.getenv("MRPC_API_KEY"))) {
            account.connectByServerName("MetaQuotes-Demo", "EURUSD", 120);
            try {
                AccountSummaryData summary = account.accountSummary().getData();
                System.out.println("Balance: " + summary.getAccountBalance() + ", equity: " + summary.getAccountEquity());
            } finally {
                account.disconnect();   // disconnect(true) also deletes the terminal
            }
        }
    }
}
```

---

## Step 3: Run the Program

```bash
export MRPC_API_KEY=your-api-key MT5_LOGIN=your-login MT5_PASSWORD=your-password
./gradlew run
```

No account yet? `curl -H "APIKey: your-api-key" "https://mt5.mrpc.pro/DemoAccount/Open?server=MetaQuotes-Demo"`
opens a demo account and returns its login and password.

---

## 🚀 Next Steps

Congratulations! You have successfully established a direct gRPC connection to MetaTrader 5. Next:
- Explore **[Subscriptions](../MT5Account/6.%20Subscriptions/Subscriptions.Overview.md)** to listen to live ticks and trades.
- Check **[MT5Account](../MT5Account/MT5Account.Master.Overview.md)** for its methods, and **[API methods](../API_Reference/methods.md)** for every method of the API.
- Learn about high-level risk management and auto-normalization in **[MT5Sugar](../MT5Sugar/MT5Sugar.Overview.md)**.
