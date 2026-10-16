# JavaMT5 API Reference

Quick reference for the three main API classes.

## Files

- **[MT5Account.md](MT5Account.md)** - Layer 1: Low-level proto/gRPC API
- **[MT5Service.md](MT5Service.md)** - Layer 2: Mid-level wrapper API
- **[MT5Sugar.md](MT5Sugar.md)** - Layer 3: High-level convenience API

## Architecture Overview

```
┌─────────────────────────────────────┐
│         MT5Sugar (Layer 3)          │  ← Start here (easiest)
│    High-level convenience API       │
│  • Auto-normalization               │
│  • Risk management                  │
│  • Simple method signatures         │
└─────────────────────────────────────┘
              ↓ uses
┌─────────────────────────────────────┐
│        MT5Service (Layer 2)         │
│      Mid-level wrapper API          │
│  • Enhanced error handling          │
│  • Convenient overloads             │
│  • Type conversions                 │
└─────────────────────────────────────┘
              ↓ uses
┌─────────────────────────────────────┐
│       MT5Account (Layer 1)          │
│      Low-level proto/gRPC           │
│  • Direct MT5 terminal access       │
│  • Maximum control                  │
│  • Proto message handling           │
└─────────────────────────────────────┘
```

## Quick Start

**Recommended:** Start with MT5Sugar for simplest API.

```java
// Create connection
MT5Account account = new MT5Account(user, password);
MT5Service service = new MT5Service(account);
MT5Sugar sugar = new MT5Sugar(service);

account.connectByServerName("ServerName", "EURUSD");

// Use MT5Sugar for trading
long ticket = sugar.buyMarket("EURUSD", 0.1, null, null);
sugar.closePosition(ticket);

// Cleanup
account.disconnect();
account.close();
```

## Full Documentation

For complete documentation with examples:
- [Complete project documentation](../index.md)
- [MT5Account detailed docs](../MT5Account/MT5Account.Master.Overview.md)
- [MT5Sugar detailed docs](../MT5Sugar/MT5Sugar.Overview.md)
