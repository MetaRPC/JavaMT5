# Getting Started with JavaMT5

> **Quick Setup & Overview** - Start automating your trading with JavaMT5 (Java for MetaTrader 5) in minutes.

---

## 🎯 What is JavaMT5?

**JavaMT5** is an industrial-grade, type-safe Java client library for interacting with **MetaTrader 5** terminals via high-performance **gRPC**. It eliminates complex C++ DLL wrappers and provides direct, reliable programmatic trading.

### 🌟 Key Advantages
- 🚀 **High Throughput**: Native gRPC streaming for sub-millisecond price ticks and trade execution.
- 🛡️ **Three-Layer Architecture**: Low-level gRPC (`MT5Account`), typed wrapper methods (`MT5Service`), and high-level convenience (`MT5Sugar`).
- 🔄 **Resilient Connection**: Auto-reconnect, exponential backoff, and transparent channel healing.
- 💼 **Production Ready**: Fully verified across institutional accounts, hedge fund systems, and automated retail bots.

---

## 📦 Installation

Install JavaMT5 via your standard Java package manager:

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

## 🔑 API Key & Authentication

Connecting to MetaRPC production endpoints (`mt5.mrpc.pro:443`) requires an API key:

1. **Sign Up**: Create an account for free at [https://mrpc.pro/signup](https://mrpc.pro/signup).
2. **Generate API Key**: In your MetaRPC Portal dashboard at [https://mrpc.pro/my](https://mrpc.pro/my), go to **API Keys** to generate and copy your personal API token.
3. **Configure Connection**: Pass your API key / token along with the server address (`mt5.mrpc.pro:443`) in your connection settings.

---


---

## 🆔 Automatic Account ID & Authentication

MetaRPC endpoints require authentication and session management:
1. **`APIKey`**: Your personal authentication token from [https://mrpc.pro/my](https://mrpc.pro/my) (obtained by registering at [https://mrpc.pro/signup](https://mrpc.pro/signup)). Sent in the `APIKey` header.
2. **`id`**: A terminal session GUID returned by `Connect` / `ConnectEx` (`terminalInstanceGuid`).

> 💡 **Seamless Automation**: You do not need to call `GetId` or provide an `id` header when connecting. The server automatically generates a session GUID upon connection and returns it to the caller. The SDK automatically captures this session ID and attaches it alongside your `APIKey` to all subsequent requests and streaming subscriptions.

## 🔌 Minimal Connection Example

Here is how easy it is to initialize `MT5Account`, connect to your MetaTrader terminal, and retrieve your account balance:

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

## 🗺️ Documentation Road Map

To get the most out of JavaMT5, follow this suggested reading order:

1. 🚀 **[Your First Project](Your_First_Project.md)** - Build and run a working project in 10 minutes.
2. 🗺️ **[Project Map](PROJECT_MAP.md)** - Understand the 3 architectural layers and interaction flow.
3. 📖 **[Glossary](GLOSSARY.md)** - Essential MetaTrader 5 and algorithmic trading terminology.
4. 🐣 **[MT5 for Beginners](MT5_For_Beginners.md)** - Step-by-step terminal setup and demo account guide.
5. 📡 **[gRPC Streaming](GRPC_STREAM_MANAGEMENT.md)** - Subscribe to ticks, trades, DOM, and position updates.
6. 📊 **[Return Codes](RETURN_CODES_REFERENCE.md)** - Complete reference of broker and gateway return codes.
