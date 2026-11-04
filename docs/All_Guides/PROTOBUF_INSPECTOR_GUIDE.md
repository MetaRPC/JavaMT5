# Protobuf Types — Exploring the API

> **How to discover the MT5 gRPC protobuf types, fields and enums before you write code.**

---

## 🏛️ What this covers

Unlike some sibling SDKs, JavaMT5 does **not** ship a separate interactive inspector program. It
doesn't need one: the generated gRPC stubs are **plain Java source**, strongly typed, and bundled
right in the repository. You explore them the same way you explore any Java API — with your IDE,
with text search, or with the generated Javadoc. This guide shows how to:

- Find a message type and see its **fields** (getters) and their types
- Find **which types contain a given field**
- List all **values of an enum**
- Search for types by name

> The names you see are exactly what you write in code — Java getters (`getAccountBalance()`) and
> enum constants in UPPER_SNAKE (`TMT5_ORDER_TYPE_BUY`).

---

## 📁 Where the types live

All generated stubs are committed Java source under:

```
package/mt5_term_api/
├── Mt5TermApiAccountHelper.java        # AccountSummaryData, OpenedOrdersData, OrdersHistoryData, …
├── Mt5TermApiAccountInformation.java   # AccountInfoDouble/Integer/String property enums
├── Mt5TermApiMarketInfo.java           # SymbolInfo* property enums, MrpcMqlTick, BookType
├── Mt5TermApiTradingHelper.java        # OrderSendRequest/Data, TMT5_ENUM_ORDER_TYPE, MRPC_ORDER_CLOSE_MODE
├── Mt5TermApiTradeFunctions.java       # ENUM_ORDER_TYPE_TF, OrderCheck request/result
├── Mt5TermApiSubscriptions.java        # OnTrade/OnTradeTransaction events + SUB_ENUM_* enums
├── Mt5TermApiConnection.java           # ConnectEx*, connection messages
└── …
```

Each message is a Java class with `getXxx()` getters and a `newBuilder()`; each enum is a Java
`enum` whose constants carry the proto number.

---

## 🧰 Four ways to explore

| Technique | Use it for | How |
|-----------|-----------|-----|
| **IDE autocomplete** | Fields of a type, values of an enum | Type `summary.` or `TMT5_ENUM_ORDER_TYPE.` and read the completion list |
| **Text search (grep / "Find in Files")** | "which type has this field", "where is this enum" | Search `package/mt5_term_api/` |
| **Read the stub** | Full shape of a message or enum | Open the `Mt5TermApi*.java` file |
| **Javadoc** | Browsable HTML reference | `mvn javadoc:javadoc` → open `target/site/apidocs/index.html` |

> **No MT5 connection required** — everything here is static source inspection; nothing connects to
> a terminal.

---

## Practical Examples

### Example 1: What fields does `AccountSummaryData` have?

**IDE:** type `summary.` (where `summary` is an `AccountSummaryData`) and read the completions.

**Search:** list its getters from the stub:

```bash
grep -oE "public [A-Za-z0-9_.]+ get[A-Za-z0-9]+\(\)" package/mt5_term_api/Mt5TermApiAccountHelper.java | sort -u
```

**Output (abridged):**
```
public double getAccountBalance()
public double getAccountCredit()
public double getAccountEquity()
public long   getAccountLeverage()
public long   getAccountLogin()
public java.lang.String getAccountCompanyName()
public java.lang.String getAccountCurrency()
public java.lang.String getAccountUserName()
...
```

> Note: `AccountSummaryData` has **no** margin/profit fields — fetch those via `AccountInfoDouble`
> (`ACCOUNT_MARGIN`, `ACCOUNT_PROFIT`).

---

### Example 2: Find which types contain a `Ticket` field

**Command:**
```bash
grep -rl "public long getTicket(" package/mt5_term_api/ | xargs -n1 basename
```

**Output:**
```
Mt5TermApiAccountHelper.java
Mt5TermApiSubscriptions.java
Mt5TermApiTradeStats.java
Mt5TermApiTradingHelper.java
```

To see the exact classes, open the file or narrow the search (e.g. `getPositionTicket`,
`getOrderTicket`, `getDealTicket` are separate getters on the history/transaction messages).

---

### Example 3: See all order-type enum values

**IDE:** type `Mt5TermApiTradingHelper.TMT5_ENUM_ORDER_TYPE.` and read the list.

**Search:**
```bash
grep -oE "TMT5_ORDER_TYPE_[A-Z_]+\([0-9]+\)" package/mt5_term_api/Mt5TermApiTradingHelper.java | sort -u
```

**Output:**
```
TMT5_ORDER_TYPE_BUY(0)
TMT5_ORDER_TYPE_SELL(1)
TMT5_ORDER_TYPE_BUY_LIMIT(2)
TMT5_ORDER_TYPE_SELL_LIMIT(3)
TMT5_ORDER_TYPE_BUY_STOP(4)
TMT5_ORDER_TYPE_SELL_STOP(5)
TMT5_ORDER_TYPE_BUY_STOP_LIMIT(6)
TMT5_ORDER_TYPE_SELL_STOP_LIMIT(7)
TMT5_ORDER_TYPE_CLOSE_BY(8)
```

> **Tip:** trading requests use `TMT5_ENUM_ORDER_TYPE` / `ENUM_ORDER_TYPE_TF`; streaming events use
> the `SUB_ENUM_*` enums; history/symbol data uses the `BMT5_ENUM_*` enums.

---

### Example 4: Find all types related to "Order"

**Command (type/enum declarations):**
```bash
grep -rhoE "(class|enum) [A-Za-z0-9_]*Order[A-Za-z0-9_]*" package/mt5_term_api/ | sort -u
```

**Output (abridged):**
```
class OpenedOrdersData
class OrderSendRequest
class OrderSendData
class OrderCloseRequest
class OrdersHistoryData
enum TMT5_ENUM_ORDER_TYPE
enum ENUM_ORDER_TYPE_TF
enum BMT5_ENUM_ORDER_TYPE
...
```

---

### Example 5: Browse everything

- **Javadoc:** run `mvn javadoc:javadoc`, then open `target/site/apidocs/index.html` and browse the
  `mt5_term_api` package — every class, getter and enum, cross-linked.
- **Files:** list the stubs — `ls package/mt5_term_api/` — and open the one for the area you need
  (see the map above).

---

## 📦 Common Use Cases

### "Wrong getter name won't compile"

Your code calls `summary.getBallance()` — search the real getters:
```bash
grep "getBalance" package/mt5_term_api/Mt5TermApiAccountHelper.java
```
→ the correct getter is `getAccountBalance()`.

### "What data is in X?"

Open `Mt5TermApi…java` for the type, or grep its getters (Example 1) → every field and its type.

### "What enum value do I pass?"

Grep the enum's constants (Example 3), or let IDE autocomplete list them after the `.`.

### "Which type holds this field?"

`grep -rl "get<Field>(" package/mt5_term_api/` (Example 2).

---

## Frequently used enums

| Enum | Context | Where |
|------|---------|-------|
| `TMT5_ENUM_ORDER_TYPE` | `OrderSend` request operation | `Mt5TermApiTradingHelper` |
| `TMT5_ENUM_ORDER_TYPE_TIME` | Order lifetime (GTC / Day / Specified) | `Mt5TermApiTradingHelper` |
| `MRPC_ORDER_CLOSE_MODE` | `OrderClose` result mode | `Mt5TermApiTradingHelper` |
| `ENUM_ORDER_TYPE_TF` | `OrderCalcMargin` / `OrderCalcProfit` / `OrderCheck` | `Mt5TermApiTradeFunctions` |
| `BMT5_ENUM_ORDER_TYPE`, `BMT5_ENUM_ORDER_HISTORY_SORT_TYPE` | History orders & sorting | `Mt5TermApiAccountHelper` |
| `SUB_ENUM_ORDER_TYPE`, `SUB_ENUM_DEAL_TYPE`, … | Streaming events (`onTrade`, `onTradeTransaction`) | `Mt5TermApiSubscriptions` |
| `SymbolInfoDoubleProperty`, `SymbolInfoIntegerProperty` | `symbolInfoDouble` / `symbolInfoInteger` selectors | `Mt5TermApiMarketInfo` |
| `AccountInfoDoublePropertyType` | `accountInfoDouble` selector | `Mt5TermApiAccountInformation` |
| `BookType` | `MarketBookGet` order type in the book | `Mt5TermApiMarketInfo` |

**Prefix meaning:**

- `TMT5_*` — trading-helper request enums (OrderSend)
- `ENUM_*_TF` — trade-functions enums (margin / profit / check)
- `BMT5_*` — base MT5 enums (history, symbol params)
- `SUB_*` — subscription / streaming enums (real-time events)

Full tables: [Enums Usage Reference](ENUMS_USAGE_REFERENCE.md).

---

## Technical Notes

- **Stubs are source, not a dependency** — they live in `package/mt5_term_api/` and compile with the
  project; there is no separate JAR to download and no `protoc` run.
- **Java names** — getters are `getXxx()`, enum constants are UPPER_SNAKE proto names; that's exactly
  what you type in code.
- **Javadoc** — `mvn javadoc:javadoc` (the build also attaches a Javadoc jar on `package`).
- **Static only** — this is API-shape discovery, not runtime inspection; to see live data use the
  debugger.

---

## When to use this

**Use it when:**

- Learning the MT5 gRPC API structure
- Debugging a wrong getter/field name
- Looking up enum values before building a request
- Finding the right Request / Reply / Data type for a call

**Don't use it for:**

- Inspecting runtime data (use the debugger)
- Testing connectivity (use a demo connection)

---

## See Also

- [Enums Usage Reference](ENUMS_USAGE_REFERENCE.md) — all enums and where they're used
- [Return Codes Reference](RETURN_CODES_REFERENCE.md) — trade return codes
- [User Code Sandbox Guide](USERCODE_SANDBOX_GUIDE.md) — write and test your own code
- [MT5Account Master Overview](../MT5Account/MT5Account.Master.Overview.md) — complete API reference
