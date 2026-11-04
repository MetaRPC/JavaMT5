# 🗺️ JavaMT5 Project Map

> The whole project as one annotated tree, with links. **Links point to section *overviews*, never to individual method pages** — open an overview to drill down into its methods.

---

## 🧱 The three layers

JavaMT5 is built in three decoupled layers. Your code can enter at any level.

| Layer | Source | What it does | Overview |
| ----- | ------ | ------------ | -------- |
| **3 · MT5Sugar** | `src/main/java/pro/mrpc/mt5/MT5Sugar.java` | Convenience on top of Service: risk-based sizing, price/volume normalization, point-offset helpers, batch close/cancel. | [MT5Sugar overview](../MT5Sugar/MT5Sugar.Overview.md) |
| **2 · MT5Service** | `src/main/java/pro/mrpc/mt5/MT5Service.java` | Typed facade: clean signatures that return ready Java data (`…Data` / primitives, no raw proto). What most code uses. | [MT5Service overview](../MT5Service/MT5Service.Overview.md) |
| **1 · MT5Account** | `src/main/java/pro/mrpc/mt5/MT5Account.java` | Low-level gRPC: proto request/reply, connect/session, streaming. Everything else builds on it. | [MT5Account overview](../MT5Account/MT5Account.Master.Overview.md) |

```
Your code  →  MT5Sugar  →  MT5Service  →  MT5Account  →  gRPC  →  MetaRPC gateway → MT5 terminal
```

---

## 🌳 Repository tree

- **`JavaMT5/`** — repo root (examples/demo project; the SDK is the `pro.mrpc:mt5` artifact)
  - **`src/main/java/pro/mrpc/mt5/`** — the three-layer core API
    - `MT5Sugar.java` — **Layer 3** source → [MT5Sugar overview](../MT5Sugar/MT5Sugar.Overview.md)
    - `MT5Service.java` — **Layer 2** source → [MT5Service overview](../MT5Service/MT5Service.Overview.md)
    - `MT5Account.java` — **Layer 1** source → [MT5Account overview](../MT5Account/MT5Account.Master.Overview.md)
    - **`exceptions/`** — `ApiExceptionMT5`, `ConnectExceptionMT5`
  - **`package/mt5_term_api/`** — generated gRPC / protobuf stubs (the raw contract), compiled with the project → [Protobuf Types Explorer](PROTOBUF_INSPECTOR_GUIDE.md)
  - **`src/main/java/`** — the launcher and runnable code
    - `Program.java` — interactive menu / CLI runner (`run.bat <n>`) → [Running Examples](RUNNING_EXAMPLES.md)
    - **`examples/lowlevel/`** — `run.bat 1`–`3` → [MT5Account overview](../MT5Account/MT5Account.Master.Overview.md)
    - **`examples/services/`** — `run.bat 4`–`6` → [MT5Service overview](../MT5Service/MT5Service.Overview.md)
    - **`examples/sugar/`** — `run.bat 7`–`9` → [MT5Sugar overview](../MT5Sugar/MT5Sugar.Overview.md)
    - **`examples/usercode/`** — `run.bat 0` → [UserCode Sandbox](USERCODE_SANDBOX_GUIDE.md)
    - **`orchestrators/`** — `run.bat 10` (Scalping / Trend / Hedging / Breakout / Martingale) → [Strategies overview](../Strategies/Strategies.Master.Overview.md)
    - **`presets/`** — `run.bat 11` (Aggressive Growth / Defensive) → [Strategies overview](../Strategies/Strategies.Master.Overview.md)
  - **`docs/`** — this documentation site (see the map below)
  - `appsettings.json` — your MT5 credentials (`DefaultConnection`) · `run.bat` — launcher · `pom.xml` — Maven build · `mkdocs.yml` — docs site config

---

## 📚 Documentation tree

- **`docs/`**
  - [Home](../index.md) — landing page
  - **`All_Guides/`** — the guides (MT5 for Beginners, Getting Started, Your First Project, Running Examples, Glossary, gRPC Channel Management, User Code Sandbox, Return Codes, ENUMs, Protobuf Types); browse them in the left nav
  - **`MT5Account/`** — low-level gRPC docs → [overview](../MT5Account/MT5Account.Master.Overview.md) *(per-method pages inside)*
  - **`MT5Service/`** — typed facade docs → [overview](../MT5Service/MT5Service.Overview.md) *(per-group pages inside)*
  - **`MT5Sugar/`** — convenience API docs → [overview](../MT5Sugar/MT5Sugar.Overview.md) *(per-method pages inside)*
  - **`Strategies/`** — the five orchestrators → [overview](../Strategies/Strategies.Master.Overview.md) *(per-strategy pages inside)*
  - **`API_Reference/`** — flat method / message / enum reference
  - `Guide_Images/` · `styles/` · `javascripts/` · `includes/` — assets & theme

> The `MT5Account/`, `MT5Service/`, `MT5Sugar/` and `Strategies/` folders hold per-method / per-strategy pages. By design this map links only their **overviews** — each overview is the index into its own detail pages.

---

## 🎯 Which layer should you use?

- **Most trading apps → [MT5Sugar](../MT5Sugar/MT5Sugar.Overview.md).** Risk sizing and auto-normalization protect you from off-quote and invalid-volume rejections.
- **Custom execution → [MT5Service](../MT5Service/MT5Service.Overview.md).** Typed calls when you manage your own sizing/normalization.
- **Protocol-level control → [MT5Account](../MT5Account/MT5Account.Master.Overview.md).** Raw proto structures and streams.
