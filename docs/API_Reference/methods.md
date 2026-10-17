# API methods

Every public method of the MT5 API, generated from the protos this package is built from. Each is a gRPC method of
package `mt5_term_api` on `mt5.mrpc.pro:443` and a REST route on `https://mt5.mrpc.pro` (server streams also as a WebSocket on the same
path, `wss://mt5.mrpc.pro/...?id=...&apikey=...`). Every reply carries either `data` (described below) or `error`
(see [Errors](messages.md#error)). Try them in the browser: [https://mt5.mrpc.pro/apiui](https://mt5.mrpc.pro/apiui).

## Calling a method

Every method below is a method of the generated stub of its service (`mt5_term_api.{Service}Grpc`). `MT5Account.authorized(stub)`
adds the API key and the terminal id to its calls:

```java
import mt5_term_api.MarketInfoGrpc;
import mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest;

// account: a connected MT5Account
var marketInfo = account.authorized(MarketInfoGrpc.newBlockingStub(account.getChannel()));
var reply = marketInfo.symbolInfoTick(SymbolInfoTickRequest.newBuilder().setSymbol("EURUSD").build());
if (reply.hasError()) throw new IllegalStateException(reply.getError().getErrorCode() + ": " + reply.getError().getErrorMessage());
System.out.println("EURUSD " + reply.getData().getBid() + " / " + reply.getData().getAsk());
```

Field `foo_bar` is `getFooBar()` / `setFooBar(value)` on the builder (the exact getter of every field is in the tables
below); a repeated field is `get...List()` / `add...(value)` / `addAll...(list)`. A server stream is an async stub
(`newStub`) with a `StreamObserver`: see [Streams](../GRPC_CHANNEL_AND_STREAM_MANAGEMENT_EN.md).

## AccountHelper

### AccountHelper.AccountSummary

Some information about account

- gRPC: `mt5_term_api.AccountHelper/AccountSummary` (unary)
- REST: `GET /AccountSummary`

**Request** `AccountSummaryRequest`

_No fields._

**Reply `data`** [AccountSummaryData](messages.md#accountsummarydata)

### AccountHelper.OrderHistory

Orders History

- gRPC: `mt5_term_api.AccountHelper/OrderHistory` (unary)
- REST: `GET /OrderHistory`

**Request** `OrderHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `inputFrom` | `getInputFrom()` | `google.protobuf.Timestamp` | required (server time) |
| `inputFromUtc` | `getInputFromUtc()` | `google.protobuf.Timestamp` | inputFrom in UTC: send either inputFrom or inputFromUtc, not both. |
| `inputTo` | `getInputTo()` | `google.protobuf.Timestamp` | required (server time) |
| `inputToUtc` | `getInputToUtc()` | `google.protobuf.Timestamp` | inputTo in UTC: send either inputTo or inputToUtc, not both. |
| `inputSortMode` | `getInputSortMode()` | [BMT5_ENUM_ORDER_HISTORY_SORT_TYPE](enums.md#bmt5_enum_order_history_sort_type) | 0 - by open time, 1 - by close time, 2 - ticketid |
| `pageNumber` | `getPageNumber()` | `int32` | Page-number paging (legacy): 1-based, needs itemsPerPage too. 0 = not used |
| `itemsPerPage` | `getItemsPerPage()` | `int32` | Page-number paging (legacy): rows per page, needs pageNumber too. 0 = not used |
| `page_token` | `getPageToken()` | `string` | Cursor paging (preferred, stable): the next_page_token of the previous page; empty for the first page. Send it back with the same inputFrom, inputTo and inputSortMode. Cursor paging is on when page_token or page_size is set; it cannot be combined with pageNumber/itemsPerPage (ORDER_HISTORY_PAGING_INVALID). |
| `page_size` | `getPageSize()` | `int32` | Cursor paging: rows (deals) per page, 1..5000. 0 = 1000 when page_token is set. |

**Reply `data`** [OrdersHistoryData](messages.md#ordershistorydata)

### AccountHelper.OpenedOrders

Pending orders and opened positions

- gRPC: `mt5_term_api.AccountHelper/OpenedOrders` (unary)
- REST: `GET /OpenedOrders`

**Request** `OpenedOrdersRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `inputSortMode` | `getInputSortMode()` | [BMT5_ENUM_OPENED_ORDER_SORT_TYPE](enums.md#bmt5_enum_opened_order_sort_type) | 0 - by open time, 1 - by close time, 2 - ticketid |

**Reply `data`** [OpenedOrdersData](messages.md#openedordersdata)

### AccountHelper.OpenedOrdersTickets

Just opened order tickets

- gRPC: `mt5_term_api.AccountHelper/OpenedOrdersTickets` (unary)
- REST: `GET /OpenedOrdersTickets`

**Request** `OpenedOrdersTicketsRequest`

_No fields._

**Reply `data`** [OpenedOrdersTicketsData](messages.md#openedordersticketsdata)

### AccountHelper.SymbolParamsMany

Full information about symbol and his group for several symbols

- gRPC: `mt5_term_api.AccountHelper/SymbolParamsMany` (unary)
- REST: `GET /SymbolParamsMany`

**Request** `SymbolParamsManyRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_name` | `getSymbolName()` | `string` |  |
| `sort_type` | `getSortType()` | [AH_SYMBOL_PARAMS_MANY_SORT_TYPE](enums.md#ah_symbol_params_many_sort_type) |  |
| `page_number` | `getPageNumber()` | `int32` |  |
| `items_per_page` | `getItemsPerPage()` | `int32` |  |

**Reply `data`** [SymbolParamsManyData](messages.md#symbolparamsmanydata)

### AccountHelper.TickValueWithSize

Tick value as array

- gRPC: `mt5_term_api.AccountHelper/TickValueWithSize` (unary)
- REST: `GET /TickValueWithSize`

**Request** `TickValueWithSizeRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_names` | `getSymbolNamesList()` | repeated `string` | Symbols to read, by exact name (REST: repeated query parameter symbolNames). Each named symbol is added to Market Watch when it is not there yet, then read. At most 1000 names per request; a longer list is answered with an error. EMPTY list = every symbol currently in Market Watch. It is NOT "all symbols of the server": the terminal's Market Watch holds at most ~1000 symbols, so serving the server's list (12,000+ at some brokers) would mean adding all of them to Market Watch and changing the user's selection. Name the symbols you need. |

**Reply `data`** [TickValueWithSizeData](messages.md#tickvaluewithsizedata)

### AccountHelper.PositionsHistory

History positions

- gRPC: `mt5_term_api.AccountHelper/PositionsHistory` (unary)
- REST: `GET /PositionsHistory`

**Request** `PositionsHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `sort_type` | `getSortType()` | [AH_ENUM_POSITIONS_HISTORY_SORT_TYPE](enums.md#ah_enum_positions_history_sort_type) |  |
| `position_open_time_from` | `getPositionOpenTimeFrom()` | `google.protobuf.Timestamp` |  |
| `position_open_time_from_utc` | `getPositionOpenTimeFromUtc()` | `google.protobuf.Timestamp` | position_open_time_from in UTC: send either position_open_time_from or position_open_time_from_utc, not both. |
| `position_open_time_to` | `getPositionOpenTimeTo()` | `google.protobuf.Timestamp` |  |
| `position_open_time_to_utc` | `getPositionOpenTimeToUtc()` | `google.protobuf.Timestamp` | position_open_time_to in UTC: send either position_open_time_to or position_open_time_to_utc, not both. |
| `page_number` | `getPageNumber()` | `int32` |  |
| `items_per_page` | `getItemsPerPage()` | `int32` |  |
| `page_token` | `getPageToken()` | `string` | Cursor paging (preferred, stable): the next_page_token of the previous page; empty for the first page. Send it back with the same sort_type and position_open_time_from/to. Cursor paging is on when page_token or page_size is set; it cannot be combined with page_number/items_per_page (POSITIONS_HISTORY_PAGING_INVALID). |
| `page_size` | `getPageSize()` | `int32` | Cursor paging: positions per page, 1..5000. 0 = 1000 when page_token is set. |

**Reply `data`** [PositionsHistoryData](messages.md#positionshistorydata)

### AccountHelper.AllHistoryOrders

- gRPC: `mt5_term_api.AccountHelper/AllHistoryOrders` (unary)
- REST: `GET /AllHistoryOrders`

**Request** `AllHistoryOrdersRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `inputSortMode` | `getInputSortMode()` | [BMT5_ENUM_ORDER_HISTORY_SORT_TYPE](enums.md#bmt5_enum_order_history_sort_type) | 0 - by open time, 1 - by close time, 2 - ticketid |
| `inputFrom` | `getInputFrom()` | `google.protobuf.Timestamp` |  |
| `inputFromUtc` | `getInputFromUtc()` | `google.protobuf.Timestamp` | inputFrom in UTC: send either inputFrom or inputFromUtc, not both. |
| `inputTo` | `getInputTo()` | `google.protobuf.Timestamp` |  |
| `inputToUtc` | `getInputToUtc()` | `google.protobuf.Timestamp` | inputTo in UTC: send either inputTo or inputToUtc, not both. |
| `pageNumber` | `getPageNumber()` | `int32` |  |
| `itemsPerPage` | `getItemsPerPage()` | `int32` |  |
| `page_token` | `getPageToken()` | `string` | Cursor paging (preferred, stable): the next_page_token of the previous page; empty for the first page. Send it back with the same inputFrom, inputTo and inputSortMode. Cursor paging is on when page_token or page_size is set; it cannot be combined with pageNumber/itemsPerPage (ALL_HISTORY_ORDERS_PAGING_INVALID). |
| `page_size` | `getPageSize()` | `int32` | Cursor paging: orders per page, 1..5000. 0 = 1000 when page_token is set. |

**Reply `data`** [AllHistoryOrdersData](messages.md#allhistoryordersdata)

## AccountInformation

### AccountInformation.AccountInfoDouble

Returns the double value of the corresponding account property https://www.mql5.com/en/docs/account/accountinfodouble

- gRPC: `mt5_term_api.AccountInformation/AccountInfoDouble` (unary)
- REST: `GET /AccountInfoDouble`

**Request** `AccountInfoDoubleRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `property_id` | `getPropertyId()` | [AccountInfoDoublePropertyType](enums.md#accountinfodoublepropertytype) |  |

**Reply `data`** [AccountInfoDoubleData](messages.md#accountinfodoubledata)

### AccountInformation.AccountInfoInteger

Returns the value of the properties of the account https://www.mql5.com/en/docs/account/accountinfointeger

- gRPC: `mt5_term_api.AccountInformation/AccountInfoInteger` (unary)
- REST: `GET /AccountInfoInteger`

**Request** `AccountInfoIntegerRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `property_id` | `getPropertyId()` | [AccountInfoIntegerPropertyType](enums.md#accountinfointegerpropertytype) |  |

**Reply `data`** [AccountInfoIntegerData](messages.md#accountinfointegerdata)

### AccountInformation.AccountInfoString

Returns the value of the appropriate account property https://www.mql5.com/en/docs/account/accountinfostring

- gRPC: `mt5_term_api.AccountInformation/AccountInfoString` (unary)
- REST: `GET /AccountInfoString`

**Request** `AccountInfoStringRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `property_id` | `getPropertyId()` | [AccountInfoStringPropertyType](enums.md#accountinfostringpropertytype) |  |

**Reply `data`** [AccountInfoStringData](messages.md#accountinfostringdata)

## Accounts

============================================================================ Accounts: the MT5 accounts (terminals) that belong to your API key. This is the call to make when you need the terminal ids of accounts that already exist - started from the dashboard or by an earlier ConnectEx - so that you can send them as the "id" header of every other call. Unlike the terminal calls it needs NO "id" header: it is answered from the platform's records of the API key sent in the "APIKey" header, and it never starts, wakes or bills a terminal. Accounts are listed newest first (by started_utc, then terminal_id) and returned by page. Only accounts of THIS platform's endpoint are listed (mt5.mrpc.pro lists MT5 accounts, mt4.mrpc.pro lists MT4 accounts). ============================================================================

### Accounts.List

Lists the accounts of the API key sent in the APIKey header, one page per call. Pass the next_page_token of a page back as page_token to continue; an empty next_page_token means that was the last page.

- gRPC: `mt5_term_api.Accounts/List` (unary)
- REST: `GET /Accounts`

**Request** `AccountsListRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `state` | `getState()` | `string` | Only accounts in this state, e.g. "Connected", "Syncing", "Unreachable", "WrongPassword", "Suspended" (the vocabulary of ConnectState.state, compared without regard to case). Empty = every state. |
| `login` | `getLogin()` | `uint64` | Only the account with this login number. 0 = every login. |
| `include_stopped` | `getIncludeStopped()` | `bool` | false (default): only accounts that are queued, starting or running, or that stopped on their own (Unreachable, WrongPassword, Suspended, Expired) and can be started again. true: also accounts that were stopped by the user, by an API call or by an administrator. Deleted accounts are never listed. |
| `page_size` | `getPageSize()` | `int32` | Accounts per page, 1..1000. 0 = 200. A larger value is clamped, a negative one is rejected with ACCOUNTS_PAGE_SIZE_INVALID. |
| `page_token` | `getPageToken()` | `string` | The next_page_token of the previous page; empty for the first page. Must be sent with the same state, login and include_stopped. Undecodable -> ACCOUNTS_PAGE_TOKEN_INVALID. |

**Reply `data`** [AccountsListData](messages.md#accountslistdata)

## Connection

### Connection.ConnectEx

Creates terminal connection to the MT5 server by MT cluster name, waits for connection and returns Guid of it

- gRPC: `mt5_term_api.Connection/ConnectEx` (unary)
- REST: `GET /ConnectEx`

**Request** `ConnectExRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `mt_cluster_name` | `getMtClusterName()` | `string` | MT cluster name. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) | EA files to add to terminal instance [Optional] |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 120) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectData](messages.md#connectdata)

### Connection.Connect

Creates terminal connection to the MT5 server and returns Guid of it

- gRPC: `mt5_term_api.Connection/Connect` (unary)
- REST: `GET /Connect`

**Request** `ConnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `host` | `getHost()` | `string` | Host - ip adddress or dns name. [Required] |
| `port` | `getPort()` | `int32` | Port. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 120) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectData](messages.md#connectdata)

### Connection.ConnectProxy

Connect to account with user, password, host, port.

- gRPC: `mt5_term_api.Connection/ConnectProxy` (unary)
- REST: `GET /ConnectProxy`

**Request** `ConnectProxyRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `host` | `getHost()` | `string` | Host - ip address or dns name. [Required] |
| `port` | `getPort()` | `int32` | Port. [Required] |
| `proxyUser` | `getProxyUser()` | `string` | Proxy user [Required] |
| `proxyPassword` | `getProxyPassword()` | `string` | Proxy password [Required] |
| `proxyHost` | `getProxyHost()` | `string` | Proxy server ip adddress [Required] |
| `proxyPort` | `getProxyPort()` | `uint32` | Proxy port [Required] |
| `proxyType` | `getProxyType()` | [ProxyTypes](enums.md#proxytypes) | Proxy type. Put 'Socks5' for default parameters [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 120) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectProxyData](messages.md#connectproxydata)

### Connection.CheckConnect

Checks if terminal connection to MT5 server is alive

- gRPC: `mt5_term_api.Connection/CheckConnect` (unary)
- REST: `GET /CheckConnect`

**Request** `CheckConnectRequest`

_No fields._

**Reply `data`** [CheckConnectData](messages.md#checkconnectdata)

### Connection.ConnectState

Returns detailed live connection state of this terminal instance

- gRPC: `mt5_term_api.Connection/ConnectState` (unary)
- REST: `GET /ConnectState`

**Request** `ConnectStateRequest`

_No fields._

**Reply `data`** [ConnectStateData](messages.md#connectstatedata)

### Connection.OnConnectState

Streams real-time connection state changes for specified or visible terminals

- gRPC: `mt5_term_api.Connection/OnConnectState` (server stream)
- REST: `GET /OnConnectState` (also a WebSocket)

**Request** `OnConnectStateRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_ids` | `getTerminalIdsList()` | repeated `string` |  |

**Each message (`data`)** [ConnectStateData](messages.md#connectstatedata)

### Connection.ConnectionStatus

Deprecated alias for ConnectState

- gRPC: `mt5_term_api.Connection/ConnectionStatus` (unary)
- REST: `GET /ConnectionStatus`

**Request** `ConnectionStatusRequest`

_No fields._

**Reply `data`** [ConnectionStatusData](messages.md#connectionstatusdata)

### Connection.Disconnect

Close terminal connection to MT5 server

- gRPC: `mt5_term_api.Connection/Disconnect` (unary)
- REST: `GET /Disconnect`

**Request** `DisconnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `reason` | `getReason()` | `string` |  |
| `delete` | `getDelete()` | `bool` |  |

**Reply `data`** [DisconnectData](messages.md#disconnectdata)

### Connection.Reconnect

If you need to recreate terminal instance with the same id

- gRPC: `mt5_term_api.Connection/Reconnect` (unary)
- REST: `GET /Reconnect`

**Request** `ReconnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `force_reconnection` | `getForceReconnection()` | `bool` | Recreate a terminal instance even if current instance is alive |

**Reply `data`** [ReconnectData](messages.md#reconnectdata)

### Connection.ConnectByToken

Rebuild a terminal instance from a previously-saved token_details_mt5 row. The id header names the terminal to bring back; every credential (User/Password/Server or Host+Port, proxy settings, servers.dat bytes, PfxFile, HardwareId, Build) is read from the DB. Same reply as ConnectEx so callers do not need a separate result path.

- gRPC: `mt5_term_api.Connection/ConnectByToken` (unary)
- REST: `GET /ConnectByToken`

**Request** `ConnectByTokenRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | override how long to wait for the terminal (default 120) [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectData](messages.md#connectdata)

### Connection.GetBrokerServersByBrokerName

- gRPC: `mt5_term_api.Connection/GetBrokerServersByBrokerName` (unary)
- REST: `GET /GetBrokerServersByBrokerName`

**Request** `GetBrokerServersByBrokerNameRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `BrokerName` | `getBrokerName()` | `string` |  |

**Reply `data`** [GetBrokerServersByBrokerNameData](messages.md#getbrokerserversbybrokernamedata)

### Connection.Screenshot

Captures the Xvfb display screenshot for the terminal instance

- gRPC: `mt5_term_api.Connection/Screenshot` (unary)
- REST: `GET /Screenshot`

**Request** `ScreenshotRequest`

_No fields._

**Reply `data`** [ScreenshotData](messages.md#screenshotdata)

### Connection.GetId

Generates a deterministic GUID from user and password. The same user/password combination always produces the same GUID. Use this GUID as the 'id' header for Connect and other endpoints.

- gRPC: `mt5_term_api.Connection/GetId` (unary)
- REST: `GET /GetId`

**Request** `GetIdRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `string` | Account number [Required] |
| `password` | `getPassword()` | `string` | Account password [Required] |

**Reply `data`** [GetIdData](messages.md#getiddata)

### Connection.ConnectStream

Same as Connect but streams real-time progress events. Optional 'id' header — automatically generated by the server if omitted. Swagger does not support streaming — use /connect-stream interactive viewer.

- gRPC: `mt5_term_api.Connection/ConnectStream` (server stream)
- REST: `GET /ConnectStream` (also a WebSocket)

**Request** `ConnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `host` | `getHost()` | `string` | Host - ip adddress or dns name. [Required] |
| `port` | `getPort()` | `int32` | Port. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 120) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply** `ConnectStreamEvent`

| Field | Java | Type | Description |
|---|---|---|---|
| `step` | `getStep()` | `string` | Step name: "Init", "Validation", "ColdStart-CreateInstance", "ColdStart-ReadinessCheck", "Complete", "Error" |
| `message` | `getMessage()` | `string` | Human-readable message with details |
| `level` | `getLevel()` | `string` | Log level: INFO, WARN, ERROR |
| `elapsed_ms` | `getElapsedMs()` | `int64` | Elapsed milliseconds since start |
| `timestamp` | `getTimestamp()` | `google.protobuf.Timestamp` | Timestamp of this event |
| `is_final` | `getIsFinal()` | `bool` | True only on the final event (success or error) |
| `connect_data` | `getConnectData()` | [ConnectData](messages.md#connectdata) | Populated only on the final success event |
| `error_data` | `getErrorData()` | [Error](messages.md#error) | Populated only on the final error event |

### Connection.ConnectExStream

Same as ConnectEx but streams real-time progress events. Optional 'id' header — automatically generated by the server if omitted. Swagger does not support streaming — use /connect-stream interactive viewer.

- gRPC: `mt5_term_api.Connection/ConnectExStream` (server stream)
- REST: `GET /ConnectExStream` (also a WebSocket)

**Request** `ConnectExRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `mt_cluster_name` | `getMtClusterName()` | `string` | MT cluster name. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) | EA files to add to terminal instance [Optional] |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 120) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply** `ConnectStreamEvent`

| Field | Java | Type | Description |
|---|---|---|---|
| `step` | `getStep()` | `string` | Step name: "Init", "Validation", "ColdStart-CreateInstance", "ColdStart-ReadinessCheck", "Complete", "Error" |
| `message` | `getMessage()` | `string` | Human-readable message with details |
| `level` | `getLevel()` | `string` | Log level: INFO, WARN, ERROR |
| `elapsed_ms` | `getElapsedMs()` | `int64` | Elapsed milliseconds since start |
| `timestamp` | `getTimestamp()` | `google.protobuf.Timestamp` | Timestamp of this event |
| `is_final` | `getIsFinal()` | `bool` | True only on the final event (success or error) |
| `connect_data` | `getConnectData()` | [ConnectData](messages.md#connectdata) | Populated only on the final success event |
| `error_data` | `getErrorData()` | [Error](messages.md#error) | Populated only on the final error event |

## Logs

Provides access to terminal log content (Journal and Experts tabs). Reads log entries directly from the MT5 terminal GUI. Requires 'id' header with the terminal connection GUID returned by Connect.

### Logs.Journal

Returns log entries from the terminal Journal tab. The Journal tab contains system messages about terminal connection status, network activity, server synchronization and other internal events. Works regardless of which tab is currently active in the terminal UI.

- gRPC: `mt5_term_api.Logs/Journal` (unary)
- REST: `GET /Logs/Journal`

**Request** `JournalRequest`

_No fields._

**Reply `data`** [GetTerminalJournalData](messages.md#getterminaljournaldata)

### Logs.OnJournal

Streams log entries from the terminal Journal tab in real-time. Requires 'id' header with the terminal connection GUID returned by Connect.

- gRPC: `mt5_term_api.Logs/OnJournal` (server stream)
- REST: `GET /Logs/OnJournal` (also a WebSocket)

**Request** `OnJournalRequest`

_No fields._

**Each message (`data`)** [GetTerminalJournalData](messages.md#getterminaljournaldata)

### Logs.Experts

Returns log entries from the terminal Experts tab. The Experts tab contains messages from Expert Advisors (EAs), scripts and indicators including Print() output, initialization/deinitialization events and runtime errors. Works regardless of which tab is currently active in the terminal UI.

- gRPC: `mt5_term_api.Logs/Experts` (unary)
- REST: `GET /Logs/Experts`

**Request** `JournalRequest`

_No fields._

**Reply `data`** [GetTerminalJournalData](messages.md#getterminaljournaldata)

### Logs.OnExperts

Streams log entries from the terminal Experts tab in real-time. Requires 'id' header with the terminal connection GUID returned by Connect.

- gRPC: `mt5_term_api.Logs/OnExperts` (server stream)
- REST: `GET /Logs/OnExperts` (also a WebSocket)

**Request** `OnJournalRequest`

_No fields._

**Each message (`data`)** [GetTerminalJournalData](messages.md#getterminaljournaldata)

## DemoAccount

Demo account creation service. Automates the MT5 "Open an Account" wizard via Win32 GUI automation. Does NOT require 'id' header — auto-picks any available terminal.

### DemoAccount.OpenDemoAccount

Open a demo account. Full wizard flow: search → select → fill form → register. Returns login, password, and investor password for the new demo account.

- gRPC: `mt5_term_api.DemoAccount/OpenDemoAccount` (unary)
- REST: `GET /DemoAccount/Open`

**Request** `GuiDemoOpenAccountRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `company` | `getCompany()` | `string` |  |
| `first_name` | `getFirstName()` | `string` |  |
| `last_name` | `getLastName()` | `string` |  |
| `email` | `getEmail()` | `string` |  |
| `phone` | `getPhone()` | `string` |  |
| `server` | `getServer()` | `string` |  |
| `account_type` | `getAccountType()` | `string` |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `int32` |  |

**Reply** `GuiDemoOpenAccountReply`

| Field | Java | Type | Description |
|---|---|---|---|
| `result_code` | `getResultCode()` | `int32` |  |
| `login` | `getLogin()` | `string` |  |
| `password` | `getPassword()` | `string` |  |
| `investor` | `getInvestor()` | `string` |  |
| `server` | `getServer()` | `string` |  |
| `debug_log` | `getDebugLog()` | `string` |  |

## MarketInfo

The MarketInfo service provides information about the current state of the market

### MarketInfo.SymbolsTotal

Returns the number of available (selected in Market Watch or all) symbols https://www.mql5.com/en/docs/marketinformation/symbolstotal

- gRPC: `mt5_term_api.MarketInfo/SymbolsTotal` (unary)
- REST: `GET /SymbolsTotal`

**Request** `SymbolsTotalRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `mode` | `getMode()` | `bool` | True - only symbols in MarketWatch |

**Reply `data`** [SymbolsTotalData](messages.md#symbolstotaldata)

### MarketInfo.SymbolExist

Checks if a symbol with a specified name exists https://www.mql5.com/en/docs/marketinformation/symbolexist

- gRPC: `mt5_term_api.MarketInfo/SymbolExist` (unary)
- REST: `GET /SymbolExist`

**Request** `SymbolExistRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `name` | `getName()` | `string` |  |

**Reply `data`** [SymbolExistData](messages.md#symbolexistdata)

### MarketInfo.SymbolName

Returns the name of a symbol https://www.mql5.com/en/docs/marketinformation/symbolname

- gRPC: `mt5_term_api.MarketInfo/SymbolName` (unary)
- REST: `GET /SymbolName`

**Request** `SymbolNameRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` | Order number of a symbol |
| `selected` | `getSelected()` | `bool` | Request mode. If the value is true, the symbol is taken from the list of symbols selected in MarketWatch. If the value is false, the symbol is taken from the general list. |

**Reply `data`** [SymbolNameData](messages.md#symbolnamedata)

### MarketInfo.SymbolSelect

Selects a symbol in the Market Watch window or removes a symbol from the window https://www.mql5.com/en/docs/marketinformation/symbolselect

- gRPC: `mt5_term_api.MarketInfo/SymbolSelect` (unary)
- REST: `GET /SymbolSelect`

**Request** `SymbolSelectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name |
| `select` | `getSelect()` | `bool` | Switch. If the value is false, a symbol should be removed from MarketWatch, otherwise a symbol should be selected in this window. A symbol can't be removed if the symbol chart is open, or there are open positions for this symbol |

**Reply `data`** [SymbolSelectData](messages.md#symbolselectdata)

### MarketInfo.SymbolIsSynchronized

The function checks whether data of a selected symbol in the terminal are synchronized with data on the trade server. https://www.mql5.com/en/docs/marketinformation/symbolissynchronized

- gRPC: `mt5_term_api.MarketInfo/SymbolIsSynchronized` (unary)
- REST: `GET /SymbolIsSynchronized`

**Request** `SymbolIsSynchronizedRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name |

**Reply `data`** [SymbolIsSynchronizedData](messages.md#symbolissynchronizeddata)

### MarketInfo.SymbolInfoDouble

Returns the corresponding property of a specified symbol. Immediately returns the property value. https://www.mql5.com/en/docs/marketinformation/symbolinfodouble

- gRPC: `mt5_term_api.MarketInfo/SymbolInfoDouble` (unary)
- REST: `GET /SymbolInfoDouble`

**Request** `SymbolInfoDoubleRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | symbol |
| `type` | `getType()` | [SymbolInfoDoubleProperty](enums.md#symbolinfodoubleproperty) | identifier of the property |

**Reply `data`** [SymbolInfoDoubleData](messages.md#symbolinfodoubledata)

### MarketInfo.SymbolInfoInteger

Returns the corresponding property of a specified symbol. https://www.mql5.com/en/docs/marketinformation/symbolinfointeger

- gRPC: `mt5_term_api.MarketInfo/SymbolInfoInteger` (unary)
- REST: `GET /SymbolInfoInteger`

**Request** `SymbolInfoIntegerRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | symbol |
| `type` | `getType()` | [SymbolInfoIntegerProperty](enums.md#symbolinfointegerproperty) | Identifier of a symbol property |

**Reply `data`** [SymbolInfoIntegerData](messages.md#symbolinfointegerdata)

### MarketInfo.SymbolInfoString

Returns the corresponding property of a specified symbol. Immediately returns the property value https://www.mql5.com/en/docs/marketinformation/symbolinfostring

- gRPC: `mt5_term_api.MarketInfo/SymbolInfoString` (unary)
- REST: `GET /SymbolInfoString`

**Request** `SymbolInfoStringRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | symbol |
| `type` | `getType()` | [SymbolInfoStringProperty](enums.md#symbolinfostringproperty) | Property identifier |

**Reply `data`** [SymbolInfoStringData](messages.md#symbolinfostringdata)

### MarketInfo.SymbolInfoMarginRate

Returns the margin rates depending on the order type and direction https://www.mql5.com/en/docs/marketinformation/symbolinfomarginrate

- gRPC: `mt5_term_api.MarketInfo/SymbolInfoMarginRate` (unary)
- REST: `GET /SymbolInfoMarginRate`

**Request** `SymbolInfoMarginRateRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name |
| `order_type` | `getOrderType()` | [ENUM_ORDER_TYPE](enums.md#enum_order_type) | Order type |

**Reply `data`** [SymbolInfoMarginRateData](messages.md#symbolinfomarginratedata)

### MarketInfo.SymbolInfoTick

The function returns current prices of a specified symbol in a variable of the MqlTick type https://www.mql5.com/en/docs/marketinformation/symbolinfotick

- gRPC: `mt5_term_api.MarketInfo/SymbolInfoTick` (unary)
- REST: `GET /SymbolInfoTick`

**Request** `SymbolInfoTickRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name |

**Reply `data`** [MrpcMqlTick](messages.md#mrpcmqltick)

### MarketInfo.SymbolInfoSessionQuote

Allows receiving time of beginning and end of the specified quoting sessions for a specified symbol and day of week https://www.mql5.com/en/docs/marketinformation/symbolinfosessionquote

- gRPC: `mt5_term_api.MarketInfo/SymbolInfoSessionQuote` (unary)
- REST: `GET /SymbolInfoSessionQuote`

**Request** `SymbolInfoSessionQuoteRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name |
| `day_of_week` | `getDayOfWeek()` | [DayOfWeek](enums.md#dayofweek) | day of the week |
| `session_index` | `getSessionIndex()` | `uint32` | Ordinal number of a session, whose beginning and end time we want to receive. Indexing of sessions starts with 0 |

**Reply `data`** [SymbolInfoSessionQuoteData](messages.md#symbolinfosessionquotedata)

### MarketInfo.SymbolInfoSessionTrade

Allows receiving time of beginning and end of the specified trading sessions for a specified symbol and day of week https://www.mql5.com/en/docs/marketinformation/symbolinfosessiontrade

- gRPC: `mt5_term_api.MarketInfo/SymbolInfoSessionTrade` (unary)
- REST: `GET /SymbolInfoSessionTrade`

**Request** `SymbolInfoSessionTradeRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name |
| `day_of_week` | `getDayOfWeek()` | [DayOfWeek](enums.md#dayofweek) | day of the week |
| `session_index` | `getSessionIndex()` | `uint32` | Ordinal number of a session, whose beginning and end time we want to receive. Indexing of sessions starts with 0 |

**Reply `data`** [SymbolInfoSessionTradeData](messages.md#symbolinfosessiontradedata)

### MarketInfo.MarketBookAdd

Provides opening of Depth of Market for a selected symbol, and subscribes for receiving notifications of the DOM changes https://www.mql5.com/en/docs/marketinformation/marketbookadd

- gRPC: `mt5_term_api.MarketInfo/MarketBookAdd` (unary)
- REST: `GET /MarketBookAdd`

**Request** `MarketBookAddRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | The name of a symbol, whose Depth of Market is to be used in the Expert Advisor or script |

**Reply `data`** [MarketBookAddData](messages.md#marketbookadddata)

### MarketInfo.MarketBookRelease

Provides closing of Depth of Market for a selected symbol, and cancels the subscription for receiving notifications of the DOM changes https://www.mql5.com/en/docs/marketinformation/marketbookrelease

- gRPC: `mt5_term_api.MarketInfo/MarketBookRelease` (unary)
- REST: `GET /MarketBookRelease`

**Request** `MarketBookReleaseRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | The name of a symbol |

**Reply `data`** [MarketBookReleaseData](messages.md#marketbookreleasedata)

### MarketInfo.MarketBookGet

Returns a structure array MqlBookInfo containing records of the Depth of Market of a specified symbol https://www.mql5.com/en/docs/marketinformation/marketbookget

- gRPC: `mt5_term_api.MarketInfo/MarketBookGet` (unary)
- REST: `GET /MarketBookGet`

**Request** `MarketBookGetRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | The name of a symbol |

**Reply `data`** [MarketBookGetData](messages.md#marketbookgetdata)

### MarketInfo.SymbolList

Returns a collection of a broker symbols

- gRPC: `mt5_term_api.MarketInfo/SymbolList` (unary)
- REST: `GET /SymbolList`

**Request** `SymbolListRequest`

_No fields._

**Reply `data`** [SymbolListData](messages.md#symbollistdata)

### MarketInfo.PriceHistory

Historical bars for a symbol - what a chart is drawn from. This API had no such call, so a client holding a terminal id could list symbols, read a live price and trade, and still not draw a chart. Ticks are not candles. DEPRECATED: use QuoteHistory.Bars (mt5-term-api-quote-history.proto): paged, bounded, oldest first.

- gRPC: `mt5_term_api.MarketInfo/PriceHistory` (unary)
- REST: `GET /PriceHistory`

**Request** `PriceHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Required |
| `timeframe` | `getTimeframe()` | `int32` | MQL5 ENUM_TIMEFRAMES. PERIOD_M1 = 1, PERIOD_H1 = 16385, PERIOD_D1 = 16408. |
| `from_time` | `getFromTime()` | `int64` | Unix seconds. Leave both at 0 to take the newest max_bars bars instead, which is what a chart opening on a symbol actually wants. |
| `from_time_utc` | `getFromTimeUtc()` | `int64` | from_time in UTC: send either from_time or from_time_utc, not both. |
| `to_time` | `getToTime()` | `int64` |  |
| `to_time_utc` | `getToTimeUtc()` | `int64` | to_time in UTC: send either to_time or to_time_utc, not both. |
| `max_bars` | `getMaxBars()` | `int32` | Upper bound on the answer. Defaults to 500; the terminal has its own ceiling as well (ERR_HISTORY_BARS_LIMIT), and one request must not ask for a decade of M1. |

**Reply `data`** [PriceHistoryData](messages.md#pricehistorydata)

## QuoteHistory

============================================================================ Quote history: the bars a chart is drawn from, and the ticks those bars are built from, for one symbol - returned by PAGE, oldest first. Both calls walk a time range forward. A page carries at most page_size items and, when the range holds more, a next_page_token; passing that token back (same symbol, timeframe/flags and range) returns the next page. An empty next_page_token means the range is exhausted. Tokens are opaque: a client stores and returns them, nothing else. History lives in the terminal and is fetched from the broker on demand. The first request for a symbol or a period the terminal has not loaded yet can take a few seconds, and when the broker has not delivered it within that time the call answers with an error whose error_code is QUOTE_HISTORY_NOT_READY - ask again a moment later. Nothing is served from a cache outside the terminal. Every call needs the terminal id header ("id"), like every other call. ============================================================================

### QuoteHistory.Bars

OHLC bars (candles) of one symbol and timeframe, oldest first, by page.

- gRPC: `mt5_term_api.QuoteHistory/Bars` (unary)
- REST: `GET /QuoteHistory/Bars`

**Request** `QuoteHistoryBarsRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Required. Broker symbol name, e.g. "EURUSD". |
| `timeframe` | `getTimeframe()` | [QUOTE_HISTORY_TIMEFRAME](enums.md#quote_history_timeframe) | Required. |
| `from_time` | `getFromTime()` | `int64` | Required. Unix seconds (UTC) of the oldest bar wanted, inclusive. A time before the broker's first bar is clamped to that first bar. |
| `from_time_utc` | `getFromTimeUtc()` | `int64` | from_time in UTC: send either from_time or from_time_utc, not both. |
| `to_time` | `getToTime()` | `int64` | Unix seconds (UTC) of the newest bar wanted, inclusive. 0 = now. |
| `to_time_utc` | `getToTimeUtc()` | `int64` | to_time in UTC: send either to_time or to_time_utc, not both. |
| `page_size` | `getPageSize()` | `int32` | Bars per page, 1..5000. 0 = 1000. |
| `page_token` | `getPageToken()` | `string` | The next_page_token of the previous page, to continue; empty for the first page. Must be used with the same symbol, timeframe and range. |

**Reply `data`** [QuoteHistoryBarsData](messages.md#quotehistorybarsdata)

### QuoteHistory.Ticks

Ticks of one symbol, oldest first, by page. MT5 keeps real tick history (bid/ask/last with flags) for as far back as the broker provides it.

- gRPC: `mt5_term_api.QuoteHistory/Ticks` (unary)
- REST: `GET /QuoteHistory/Ticks`

**Request** `QuoteHistoryTicksRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Required. Broker symbol name. |
| `from_time_msc` | `getFromTimeMsc()` | `int64` | Required. Unix MILLISECONDS (UTC) of the oldest tick wanted, inclusive. |
| `from_time_msc_utc` | `getFromTimeMscUtc()` | `int64` | from_time_msc in UTC: send either from_time_msc or from_time_msc_utc, not both. |
| `to_time_msc` | `getToTimeMsc()` | `int64` | Unix milliseconds (UTC) of the newest tick wanted, inclusive. 0 = now. |
| `to_time_msc_utc` | `getToTimeMscUtc()` | `int64` | to_time_msc in UTC: send either to_time_msc or to_time_msc_utc, not both. |
| `flags` | `getFlags()` | [QUOTE_HISTORY_TICK_FLAGS](enums.md#quote_history_tick_flags) | Which ticks. Default: all. |
| `page_size` | `getPageSize()` | `int32` | Ticks per page, 1..20000. 0 = 5000. |
| `page_token` | `getPageToken()` | `string` | The next_page_token of the previous page; empty for the first page. |

**Reply `data`** [QuoteHistoryTicksData](messages.md#quotehistoryticksdata)

## ServerTimezone

============================================================================ ServerTimezone: the broker server's time zone the platform uses for this account, and where it came from. Every *_utc field of the API is the broker server time minus this offset. Answered by the platform itself from what it knows (the terminal is not asked unless nothing is known yet), so it is cheap to call. ============================================================================

### ServerTimezone.ServerTimezone

The broker server's UTC offset of the terminal in the "id" header: live (read by this terminal from a fresh quote) or the last one validated for the same broker server (an account whose market is closed has no quote to read it from - MT4 only; MT5 reads its server clock directly and is always live).

- gRPC: `mt5_term_api.ServerTimezone/ServerTimezone` (unary)
- REST: `GET /ServerTimezone`

**Request** `ServerTimezoneRequest`

_No fields._

**Reply `data`** [ServerTimezoneData](messages.md#servertimezonedata)

## SubscriptionService

### SubscriptionService.OnSymbolTick

Streams real-time symbol tick data for the specified symbols. Requires 'id' header with the terminal connection GUID returned by Connect. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt5_term_api.SubscriptionService/OnSymbolTick` (server stream)
- REST: `GET /OnSymbolTick` (also a WebSocket)

**Request** `OnSymbolTickRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_names` | `getSymbolNamesList()` | repeated `string` |  |

**Each message (`data`)** [OnSymbolTickData](messages.md#onsymboltickdata)

### SubscriptionService.OnTrade

Streams real-time trade events (orders, positions, deals changes). Requires 'id' header with the terminal connection GUID returned by Connect. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt5_term_api.SubscriptionService/OnTrade` (server stream)
- REST: `GET /OnTrade` (also a WebSocket)

**Request** `OnTradeRequest`

_No fields._

**Each message (`data`)** [OnTradeData](messages.md#ontradedata)

### SubscriptionService.OnPositionProfit

Streams real-time position profit updates at the specified timer interval. Requires 'id' header with the terminal connection GUID returned by Connect. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt5_term_api.SubscriptionService/OnPositionProfit` (server stream)
- REST: `GET /OnPositionProfit` (also a WebSocket)

**Request** `OnPositionProfitRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `timer_period_milliseconds` | `getTimerPeriodMilliseconds()` | `int32` |  |
| `ignore_empty_data` | `getIgnoreEmptyData()` | `bool` |  |

**Each message (`data`)** [OnPositionProfitData](messages.md#onpositionprofitdata)

### SubscriptionService.OnPositionsAndPendingOrdersTickets

Streams real-time position and pending order ticket changes. Requires 'id' header with the terminal connection GUID returned by Connect. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt5_term_api.SubscriptionService/OnPositionsAndPendingOrdersTickets` (server stream)
- REST: `GET /OnPositionsAndPendingOrdersTickets` (also a WebSocket)

**Request** `OnPositionsAndPendingOrdersTicketsRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `timer_period_milliseconds` | `getTimerPeriodMilliseconds()` | `int32` |  |

**Each message (`data`)** [OnPositionsAndPendingOrdersTicketsData](messages.md#onpositionsandpendingordersticketsdata)

### SubscriptionService.OnTradeTransaction

Streams real-time trade transaction events (order add/update/delete, deal add, position changes). Requires 'id' header with the terminal connection GUID returned by Connect. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt5_term_api.SubscriptionService/OnTradeTransaction` (server stream)
- REST: `GET /OnTradeTransaction` (also a WebSocket)

**Request** `OnTradeTransactionRequest`

_No fields._

**Each message (`data`)** [OnTradeTransactionData](messages.md#ontradetransactiondata)

## TradeFunctions

### TradeFunctions.OrderCalcMargin

The function calculates the margin required for the specified order type, on the current account, in the current market environment not taking into account current pending orders and open positions. It allows the evaluation of margin for the trade operation planned. The value is returned in the account currency + https://www.mql5.com/en/docs/trading/ordercalcmargin

- gRPC: `mt5_term_api.TradeFunctions/OrderCalcMargin` (unary)
- REST: `GET /OrderCalcMargin`

**Request** `OrderCalcMarginRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | symbol name |
| `order_type` | `getOrderType()` | [ENUM_ORDER_TYPE_TF](enums.md#enum_order_type_tf) | type of order |
| `volume` | `getVolume()` | `double` | Volume of the trade operation |
| `open_price` | `getOpenPrice()` | `double` | Open price |

**Reply `data`** [OrderCalcMarginData](messages.md#ordercalcmargindata)

### TradeFunctions.OrderCalcProfit

The function calculates the profit for the current account, in the current market conditions, based on the parameters passed. The function is used for pre-evaluation of the result of a trade operation. The value is returned in the account currency. + https://www.mql5.com/en/docs/trading/ordercalcprofit

- gRPC: `mt5_term_api.TradeFunctions/OrderCalcProfit` (unary)
- REST: `GET /OrderCalcProfit`

**Request** `OrderCalcProfitRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `order_type` | `getOrderType()` | [ENUM_ORDER_TYPE_TF](enums.md#enum_order_type_tf) | type of order |
| `symbol` | `getSymbol()` | `string` | symbol name |
| `volume` | `getVolume()` | `double` | Volume of the trade operation |
| `open_price` | `getOpenPrice()` | `double` | Open price |
| `close_price` | `getClosePrice()` | `double` | Close price |

**Reply `data`** [OrderCalcProfitData](messages.md#ordercalcprofitdata)

### TradeFunctions.OrderCheck

Checks if there are enough money to execute a required Trade Operation Type + https://www.mql5.com/en/docs/trading/ordercheck

- gRPC: `mt5_term_api.TradeFunctions/OrderCheck` (unary)
- REST: `GET /OrderCheck`

**Request** `OrderCheckRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `mql_trade_request` | `getMqlTradeRequest()` | [MrpcMqlTradeRequest](messages.md#mrpcmqltraderequest) | Interaction between the client terminal and a trade server for executing the order placing operation |

**Reply `data`** [OrderCheckData](messages.md#ordercheckdata)

### TradeFunctions.PositionsTotal

Returns the number of open positions + https://www.mql5.com/en/docs/trading/positionstotal

- gRPC: `mt5_term_api.TradeFunctions/PositionsTotal` (unary)
- REST: `GET /PositionsTotal`

**Request** `google.protobuf.Empty`

_No fields._

**Reply `data`** [PositionsTotalData](messages.md#positionstotaldata)

## TradeStats

### TradeStats.TradeStats

Trading statistics calculated from order history and open orders

- gRPC: `mt5_term_api.TradeStats/TradeStats` (unary)
- REST: `GET /TradeStats`

**Request** `TradeStatsRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |

**Reply `data`** [StatsData](messages.md#statsdata)

### TradeStats.TradeStatsEquityHistory

Trading statistics with equity history chart points

- gRPC: `mt5_term_api.TradeStats/TradeStatsEquityHistory` (unary)
- REST: `GET /TradeStatsEquityHistory`

**Request** `TradeStatsEquityHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |
| `exclude_same_bars` | `getExcludeSameBars()` | `bool` | Return short version without equal bars |

**Reply `data`** [StatsWithChartsData](messages.md#statswithchartsdata)

### TradeStats.EquityHistory

Equity history curve points with drawdown calculation

- gRPC: `mt5_term_api.TradeStats/EquityHistory` (unary)
- REST: `GET /EquityHistory`

**Request** `EquityHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |
| `exclude_same_bars` | `getExcludeSameBars()` | `bool` | Return short version without equal bars |

**Reply `data`** [EquityHistoryData](messages.md#equityhistorydata)

### TradeStats.TradeUnrealizedPL

Maximum and minimum unrealized profit/loss per trade

- gRPC: `mt5_term_api.TradeStats/TradeUnrealizedPL` (unary)
- REST: `GET /TradeUnrealizedPL`

**Request** `TradeUnrealizedPLRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |

**Reply `data`** [TradeUnrealizedPLData](messages.md#tradeunrealizedpldata)

## TradingHelper

### TradingHelper.OrderSend

Send market or pending order

- gRPC: `mt5_term_api.TradingHelper/OrderSend` (unary)
- REST: `GET /OrderSend`

**Request** `OrderSendRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name REQUIRED |
| `operation` | `getOperation()` | [TMT5_ENUM_ORDER_TYPE](enums.md#tmt5_enum_order_type) | Type of order |
| `volume` | `getVolume()` | `double` | Requested volume for a deal in lots |
| `price` | `getPrice()` | `double` | Price |
| `slippage` | `getSlippage()` | `uint64` | Maximal possible deviation from the requested price |
| `stop_loss` | `getStopLoss()` | `double` | Stop Loss level of the order |
| `take_profit` | `getTakeProfit()` | `double` | Take Profit level of the order |
| `comment` | `getComment()` | `string` | Order comment |
| `expert_id` | `getExpertId()` | `uint64` | Expert Advisor ID (magic number) |
| `stop_limit_price` | `getStopLimitPrice()` | `double` | StopLimit level of the order |
| `expiration_time_type` | `getExpirationTimeType()` | [TMT5_ENUM_ORDER_TYPE_TIME](enums.md#tmt5_enum_order_type_time) | Order expiration type |
| `expiration_time` | `getExpirationTime()` | `google.protobuf.Timestamp` | Order expiration time (for the orders of expiration_time_type == ORDER_TIME_SPECIFIED type) |
| `expiration_time_utc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | expiration_time in UTC: send either expiration_time or expiration_time_utc, not both. |
| `quotes_wait_ms` | `getQuotesWaitMs()` | `int32` | How long OrderSend waits for the symbol's first quote, in milliseconds (default 5000, max 60000). Skipped entirely for a symbol already known to be streaming. Ignored by OrderSendWithoutChecks. |

**Reply `data`** [OrderSendData](messages.md#ordersenddata)

### TradingHelper.OrderSendWithoutChecks

Send market or pending order immediately, with no Market Watch or quotes preparation. Same request and reply as OrderSend, but nothing is checked first and quotes_wait_ms is ignored. Use only for a symbol already known to be streaming; otherwise the trade server rejects the order (ErrMarketLasttimeUnknown 4304 "no ticks" and friends).

- gRPC: `mt5_term_api.TradingHelper/OrderSendWithoutChecks` (unary)
- REST: `GET /OrderSendWithoutChecks`

**Request** `OrderSendRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name REQUIRED |
| `operation` | `getOperation()` | [TMT5_ENUM_ORDER_TYPE](enums.md#tmt5_enum_order_type) | Type of order |
| `volume` | `getVolume()` | `double` | Requested volume for a deal in lots |
| `price` | `getPrice()` | `double` | Price |
| `slippage` | `getSlippage()` | `uint64` | Maximal possible deviation from the requested price |
| `stop_loss` | `getStopLoss()` | `double` | Stop Loss level of the order |
| `take_profit` | `getTakeProfit()` | `double` | Take Profit level of the order |
| `comment` | `getComment()` | `string` | Order comment |
| `expert_id` | `getExpertId()` | `uint64` | Expert Advisor ID (magic number) |
| `stop_limit_price` | `getStopLimitPrice()` | `double` | StopLimit level of the order |
| `expiration_time_type` | `getExpirationTimeType()` | [TMT5_ENUM_ORDER_TYPE_TIME](enums.md#tmt5_enum_order_type_time) | Order expiration type |
| `expiration_time` | `getExpirationTime()` | `google.protobuf.Timestamp` | Order expiration time (for the orders of expiration_time_type == ORDER_TIME_SPECIFIED type) |
| `expiration_time_utc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | expiration_time in UTC: send either expiration_time or expiration_time_utc, not both. |
| `quotes_wait_ms` | `getQuotesWaitMs()` | `int32` | How long OrderSend waits for the symbol's first quote, in milliseconds (default 5000, max 60000). Skipped entirely for a symbol already known to be streaming. Ignored by OrderSendWithoutChecks. |

**Reply `data`** [OrderSendData](messages.md#ordersenddata)

### TradingHelper.OrderModify

Modify market or pending order

- gRPC: `mt5_term_api.TradingHelper/OrderModify` (unary)
- REST: `GET /OrderModify`

**Request** `OrderModifyRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `uint64` | Pending order or position ticket |
| `stop_loss` | `getStopLoss()` | `double` | new SL for position or pending order |
| `take_profit` | `getTakeProfit()` | `double` | new TP for position or pending order |
| `price` | `getPrice()` | `double` | new price ONLY for pending order modification |
| `expiration_time_type` | `getExpirationTimeType()` | [TMT5_ENUM_ORDER_TYPE_TIME](enums.md#tmt5_enum_order_type_time) | Expiration type ONLY for pending order |
| `expiration_time` | `getExpirationTime()` | `google.protobuf.Timestamp` | Order expiration time (for the orders of expiration_time_type == ORDER_TIME_SPECIFIED OR ORDER_TIME_SPECIFIED_DAY) |
| `expiration_time_utc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | expiration_time in UTC: send either expiration_time or expiration_time_utc, not both. |
| `stop_limit` | `getStopLimit()` | `double` | new stop limit ONLY for pending order modification |

**Reply `data`** [OrderModifyData](messages.md#ordermodifydata)

### TradingHelper.OrderClose

Close market or pending order

- gRPC: `mt5_term_api.TradingHelper/OrderClose` (unary)
- REST: `GET /OrderClose`

**Request** `OrderCloseRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `uint64` |  |
| `volume` | `getVolume()` | `double` |  |
| `slippage` | `getSlippage()` | `int32` |  |

**Reply `data`** [OrderCloseData](messages.md#orderclosedata)
