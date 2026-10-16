# Messages

The messages of the [API methods](methods.md) (package `mt5_term_api`), generated from the protos.

## AccountInfoDoubleData

| Field | Java | Type | Description |
|---|---|---|---|
| `requested_value` | `getRequestedValue()` | `double` |  |

## AccountInfoIntegerData

| Field | Java | Type | Description |
|---|---|---|---|
| `requested_value` | `getRequestedValue()` | `int64` |  |

## AccountInfoStringData

| Field | Java | Type | Description |
|---|---|---|---|
| `requested_value` | `getRequestedValue()` | `string` |  |

## AccountSummaryData

| Field | Java | Type | Description |
|---|---|---|---|
| `account_login` | `getAccountLogin()` | `int64` |  |
| `account_balance` | `getAccountBalance()` | `double` |  |
| `account_equity` | `getAccountEquity()` | `double` |  |
| `account_user_name` | `getAccountUserName()` | `string` |  |
| `account_leverage` | `getAccountLeverage()` | `int64` |  |
| `account_trade_mode` | `getAccountTradeMode()` | [MrpcEnumAccountTradeMode](enums.md#mrpcenumaccounttrademode) |  |
| `account_company_name` | `getAccountCompanyName()` | `string` |  |
| `account_currency` | `getAccountCurrency()` | `string` |  |
| `server_time` | `getServerTime()` | `google.protobuf.Timestamp` |  |
| `server_time_utc` | `getServerTimeUtc()` | `google.protobuf.Timestamp` | server_time in UTC (the same moment; server_time is in the broker server's time zone). |
| `utc_timezone_server_time_shift_minutes` | `getUtcTimezoneServerTimeShiftMinutes()` | `int64` |  |
| `account_credit` | `getAccountCredit()` | `double` |  |
| `server_utc_offset_seconds` | `getServerUtcOffsetSeconds()` | `int32` | The broker server's offset from UTC in seconds, positive = ahead of UTC: validated by the terminal (a multiple of 15 minutes). Information, never converted; it is the offset behind every *_utc field of this API. Valid when account_leverage > 0 (data with account_leverage above 0 always carries a valid offset); 0 while the account is not connected yet (not UTC+0). A connected terminal whose offset cannot be determined answers SERVER_TIMEZONE_UNDETERMINED instead of data and cannot start (MT4: typically a closed market with no quote). The current offset is applied to every value, so history from before the broker's last daylight-saving change is off by the daylight-saving step (usually 3600 s). |

## AccountsListData

| Field | Java | Type | Description |
|---|---|---|---|
| `accounts` | `getAccountsList()` | repeated [AccountsListItem](messages.md#accountslistitem) |  |
| `next_page_token` | `getNextPageToken()` | `string` | Pass back as page_token for the next page; empty when this was the last page. |
| `total_count` | `getTotalCount()` | `int32` | Accounts matching the filters over all pages, at the moment of the call. |

## AccountsListItem

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_id` | `getTerminalId()` | `string` | The terminal id to send as the "id" header of every other call, in the form the API itself returns it: mt5_live_<32 hex>. |
| `terminal_guid` | `getTerminalGuid()` | `string` | The same identity as a hyphenated GUID. The API accepts either form in the "id" header. |
| `platform` | `getPlatform()` | `string` | "MT5". |
| `login` | `getLogin()` | `uint64` | Account (login) number. |
| `server` | `getServer()` | `string` | Broker server name, as given to ConnectEx. |
| `name` | `getName()` | `string` | Name given to the terminal when it was started; may be empty. |
| `state` | `getState()` | `string` | Current state of the account's terminal: Queued, Starting, Authorizing, Syncing, Connected, WrongPassword, Unreachable, Suspended, Expired or Stopped (see ConnectState). It is the state the cluster publishes for the terminal right now; a running terminal whose published state has not been refreshed for over a minute reads Unreachable. Only when nothing is published is the last stored state returned. "Unknown" appears for a record that has never had a state. |
| `is_connected` | `getIsConnected()` | `bool` | state == "Connected". |
| `started_utc` | `getStartedUtc()` | `google.protobuf.Timestamp` | When the account was first started (UTC; a platform time, not a broker time); a restart does not change it. Unset when the record has no start time. |
| `stopped_utc` | `getStoppedUtc()` | `google.protobuf.Timestamp` | When it last stopped (UTC; a platform time, not a broker time). Unset while it is running. |
| `stop_reason` | `getStopReason()` | `string` | Why it stopped or is not running (for example "ByApi", "InvalidLogin", "Unreachable:StartupTimeout"); may be empty. Empty while the account is queued, starting or running. |
| `error_message` | `getErrorMessage()` | `string` | The last error recorded for the account, when its state is a failure; may be empty. |
| `expires_utc` | `getExpiresUtc()` | `google.protobuf.Timestamp` | When the terminal is set to expire (UTC; a platform time, not a broker time). Unset when no expiry is recorded for the account, which is the case for every account today. |

## AllHistoryOrdersData

| Field | Java | Type | Description |
|---|---|---|---|
| `arrayTotal` | `getArrayTotal()` | `int32` |  |
| `pageNumber` | `getPageNumber()` | `int32` |  |
| `itemsPerPage` | `getItemsPerPage()` | `int32` |  |
| `history_orders` | `getHistoryOrdersList()` | repeated [HistoryOrderInfo](messages.md#historyorderinfo) |  |
| `next_page_token` | `getNextPageToken()` | `string` | Cursor paging: pass back as page_token for the next page; empty when this was the last page (end of history for the range). |

## AveragePipsUsdData

| Field | Java | Type | Description |
|---|---|---|---|
| `average_pips` | `getAveragePips()` | `double` |  |
| `average_usd` | `getAverageUsd()` | `double` |  |

## CheckConnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `unique_identifier` | `getUniqueIdentifier()` | `string` | Connection unique identifier. Reflects value from header 'mt-sticky-session-header' |
| `health_check` | `getHealthCheck()` | [TerminalHealthCheck](messages.md#terminalhealthcheck) | Connection health status |

## ConnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_instance_guid` | `getTerminalInstanceGuid()` | `string` | Connection unique identifier - use this in the 'mt-sticky-session-header' value |
| `terminal_type` | `getTerminalType()` | [TerminalType](enums.md#terminaltype) | Terminal type MT5 or MT4 (Temporary only MT5) |

## ConnectProxyData

| Field | Java | Type | Description |
|---|---|---|---|
| `unique_identifier` | `getUniqueIdentifier()` | `string` | Connection unique identifier. Reflects value from header 'mrpc-terminal-guid' |
| `terminal_type` | `getTerminalType()` | [TerminalType](enums.md#terminaltype) | Terminal type MT5 or MT4 (Temporary only MT5) |

## ConnectStateData

| Field | Java | Type | Description |
|---|---|---|---|
| `id` | `getId()` | `string` |  |
| `state` | `getState()` | `string` | "Queued", "Starting", "Authorizing", "Syncing", "Connected", "WrongPassword", "Unreachable", "Stopped", "Expired" |
| `is_connected` | `getIsConnected()` | `bool` |  |
| `is_alive` | `getIsAlive()` | `bool` |  |
| `api_is_alive` | `getApiIsAlive()` | `bool` |  |
| `server` | `getServer()` | `string` |  |
| `account` | `getAccount()` | `uint64` |  |
| `error_message` | `getErrorMessage()` | `string` |  |
| `timestamp` | `getTimestamp()` | `google.protobuf.Timestamp` |  |

## ConnectStreamEvent

Event message streamed during ConnectStream / ConnectExStream. Each event represents one step in the connection process.

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

## ConnectionStatusData

| Field | Java | Type | Description |
|---|---|---|---|
| `id` | `getId()` | `string` |  |
| `is_connected` | `getIsConnected()` | `bool` |  |
| `is_alive` | `getIsAlive()` | `bool` |  |
| `api_is_alive` | `getApiIsAlive()` | `bool` |  |
| `server` | `getServer()` | `string` |  |
| `account` | `getAccount()` | `uint64` |  |
| `error_message` | `getErrorMessage()` | `string` |  |
| `state` | `getState()` | `string` |  |

## DealHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `uint64` |  |
| `profit` | `getProfit()` | `double` |  |
| `commission` | `getCommission()` | `double` |  |
| `fee` | `getFee()` | `double` |  |
| `price` | `getPrice()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `volume` | `getVolume()` | `double` |  |
| `entry_type` | `getEntryType()` | [BMT5_ENUM_DEAL_ENTRY_TYPE](enums.md#bmt5_enum_deal_entry_type) |  |
| `time` | `getTime()` | `google.protobuf.Timestamp` |  |
| `time_utc` | `getTimeUtc()` | `google.protobuf.Timestamp` | time in UTC (the same moment; time is in the broker server's time zone). |
| `type` | `getType()` | [BMT5_ENUM_DEAL_TYPE](enums.md#bmt5_enum_deal_type) |  |
| `reason` | `getReason()` | [BMT5_ENUM_DEAL_REASON](enums.md#bmt5_enum_deal_reason) |  |
| `position_id` | `getPositionId()` | `uint64` |  |
| `comment` | `getComment()` | `string` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `external_id` | `getExternalId()` | `string` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |

## DisconnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `unique_identifier` | `getUniqueIdentifier()` | `string` | Identifier of closed connection. Reflects value from header 'mt-sticky-session-header' |
| `full_life_time_seconds` | `getFullLifeTimeSeconds()` | `int64` |  |

## EquityHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `points` | `getPointsList()` | repeated [EquityPointData](messages.md#equitypointdata) |  |

## EquityPointData

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `google.protobuf.Timestamp` |  |
| `time_utc` | `getTimeUtc()` | `google.protobuf.Timestamp` | time in UTC (the same moment; time is in the broker server's time zone). |
| `balance` | `getBalance()` | `double` |  |
| `equity` | `getEquity()` | `double` |  |
| `balance_drawdown_raw` | `getBalanceDrawdownRaw()` | `double` |  |
| `balance_drawdown_relative` | `getBalanceDrawdownRelative()` | `double` |  |
| `equity_drawdown_raw` | `getEquityDrawdownRaw()` | `double` |  |
| `equity_drawdown_relative` | `getEquityDrawdownRelative()` | `double` |  |
| `realized_pl` | `getRealizedPl()` | `double` |  |
| `unrealized_pl` | `getUnrealizedPl()` | `double` |  |

## Error

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [ErrorType](enums.md#errortype) |  |
| `error_code` | `getErrorCode()` | `string` |  |
| `error_message` | `getErrorMessage()` | `string` |  |
| `mql_error_code` | `getMqlErrorCode()` | [MqlErrorCode](enums.md#mqlerrorcode) |  |
| `mql_error_int_code` | `getMqlErrorIntCode()` | `int32` |  |
| `mql_error_description` | `getMqlErrorDescription()` | `string` |  |
| `mql_error_trade_code` | `getMqlErrorTradeCode()` | [MqlErrorTradeCode](enums.md#mqlerrortradecode) |  |
| `mql_error_trade_int_code` | `getMqlErrorTradeIntCode()` | `int32` |  |
| `mql_error_trade_description` | `getMqlErrorTradeDescription()` | `string` |  |
| `stack_trace` | `getStackTrace()` | `string` |  |
| `command_type_name` | `getCommandTypeName()` | `string` |  |
| `commandId` | `getCommandId()` | `int64` |  |
| `properties` | `getPropertiesList()` | repeated [ErrorProperty](messages.md#errorproperty) |  |

## ErrorProperty

| Field | Java | Type | Description |
|---|---|---|---|
| `error_arg_key` | `getErrorArgKey()` | `string` |  |
| `error_arg_value` | `getErrorArgValue()` | `string` |  |

## ExpectancyData

| Field | Java | Type | Description |
|---|---|---|---|
| `pips` | `getPips()` | `double` |  |
| `dollar` | `getDollar()` | `double` |  |

## ExpertAdviser

| Field | Java | Type | Description |
|---|---|---|---|
| `file_name` | `getFileName()` | `string` |  |
| `file_content` | `getFileContent()` | `bytes` | File content as a byte array |

## GetBrokerServersByBrokerNameData

| Field | Java | Type | Description |
|---|---|---|---|
| `result` | `getResultList()` | repeated [GetBrokerServersByBrokerNameData.BrokerResult](messages.md#getbrokerserversbybrokernamedatabrokerresult) |  |

## GetBrokerServersByBrokerNameData.BrokerResult

| Field | Java | Type | Description |
|---|---|---|---|
| `company` | `getCompany()` | `string` |  |
| `results` | `getResultsList()` | repeated [GetBrokerServersByBrokerNameData.BrokerServer](messages.md#getbrokerserversbybrokernamedatabrokerserver) |  |

## GetBrokerServersByBrokerNameData.BrokerServer

| Field | Java | Type | Description |
|---|---|---|---|
| `name` | `getName()` | `string` |  |
| `logo_url` | `getLogoUrl()` | `string` |  |
| `site` | `getSite()` | `string` |  |
| `access` | `getAccessList()` | repeated `string` |  |

## GetIdData

| Field | Java | Type | Description |
|---|---|---|---|
| `id` | `getId()` | `string` | Deterministic GUID generated from user+password |

## GetTerminalJournalData

| Field | Java | Type | Description |
|---|---|---|---|
| `rows` | `getRowsList()` | repeated [TerminalJournalRow](messages.md#terminaljournalrow) | List of log rows from the terminal tab |
| `diagnostics` | `getDiagnostics()` | `string` | Reader diagnostics for this capture: which Journal/Experts list it found, how the rows were captured, counts and a short trace. Empty when the terminal API does not report it. |

## GuiDemoOpenAccountReply

| Field | Java | Type | Description |
|---|---|---|---|
| `result_code` | `getResultCode()` | `int32` |  |
| `login` | `getLogin()` | `string` |  |
| `password` | `getPassword()` | `string` |  |
| `investor` | `getInvestor()` | `string` |  |
| `server` | `getServer()` | `string` |  |
| `debug_log` | `getDebugLog()` | `string` |  |

## HistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `uint32` |  |
| `history_order` | `getHistoryOrder()` | [OrderHistoryData](messages.md#orderhistorydata) |  |
| `history_deal` | `getHistoryDeal()` | [DealHistoryData](messages.md#dealhistorydata) |  |

## HistoryOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `record_index` | `getRecordIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `uint64` |  |
| `time_setup` | `getTimeSetup()` | `google.protobuf.Timestamp` |  |
| `time_setup_utc` | `getTimeSetupUtc()` | `google.protobuf.Timestamp` | time_setup in UTC (the same moment; time_setup is in the broker server's time zone). |
| `type` | `getType()` | [BMT5_ENUM_ORDER_TYPE](enums.md#bmt5_enum_order_type) |  |
| `state` | `getState()` | [BMT5_ENUM_ORDER_STATE](enums.md#bmt5_enum_order_state) |  |
| `time_done` | `getTimeDone()` | `google.protobuf.Timestamp` |  |
| `time_done_utc` | `getTimeDoneUtc()` | `google.protobuf.Timestamp` | time_done in UTC (the same moment; time_done is in the broker server's time zone). |
| `expiration_time_type` | `getExpirationTimeType()` | [BMT5_ENUM_ORDER_TYPE_TIME](enums.md#bmt5_enum_order_type_time) |  |
| `expiration_time` | `getExpirationTime()` | `google.protobuf.Timestamp` |  |
| `expiration_time_utc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | expiration_time in UTC (the same moment; expiration_time is in the broker server's time zone). |
| `filling_type` | `getFillingType()` | [BMT5_ENUM_ORDER_TYPE_FILLING](enums.md#bmt5_enum_order_type_filling) |  |
| `magic` | `getMagic()` | `int64` |  |
| `position_id` | `getPositionId()` | `int64` |  |
| `position_by_id` | `getPositionById()` | `int64` |  |
| `volume_initial` | `getVolumeInitial()` | `double` |  |
| `volume_current` | `getVolumeCurrent()` | `double` |  |
| `price_open` | `getPriceOpen()` | `double` |  |
| `sl` | `getSl()` | `double` |  |
| `tp` | `getTp()` | `double` |  |
| `price_current` | `getPriceCurrent()` | `double` |  |
| `stop_limit` | `getStopLimit()` | `double` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `comment` | `getComment()` | `string` |  |
| `order_external_id_name` | `getOrderExternalIdName()` | `string` |  |

## LogFileInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `log_type` | `getLogType()` | [LogType](enums.md#logtype) |  |
| `full_path` | `getFullPath()` | `string` |  |
| `name` | `getName()` | `string` |  |
| `content` | `getContent()` | `string` |  |

## MarketBookAddData

| Field | Java | Type | Description |
|---|---|---|---|
| `opened_successfully` | `getOpenedSuccessfully()` | `bool` | The true value if opened successfully, otherwise false |

## MarketBookGetData

| Field | Java | Type | Description |
|---|---|---|---|
| `mql_book_infos` | `getMqlBookInfosList()` | repeated [MrpcMqlBookInfo](messages.md#mrpcmqlbookinfo) | Reference to an array of Depth of Market records. |

## MarketBookReleaseData

| Field | Java | Type | Description |
|---|---|---|---|
| `closed_successfully` | `getClosedSuccessfully()` | `bool` | The true value if closed successfully, otherwise false |

## MarketTradeCountData

| Field | Java | Type | Description |
|---|---|---|---|
| `market_name` | `getMarketName()` | `string` |  |
| `count` | `getCount()` | `int32` |  |
| `lots` | `getLots()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `win_rate` | `getWinRate()` | `double` |  |
| `won_count` | `getWonCount()` | `int32` |  |
| `lost_count` | `getLostCount()` | `int32` |  |

## MqlTradeRequest

| Field | Java | Type | Description |
|---|---|---|---|
| `trade_operation_type` | `getTradeOperationType()` | [SUB_ENUM_TRADE_REQUEST_ACTIONS](enums.md#sub_enum_trade_request_actions) | Trade operation type |
| `magic` | `getMagic()` | `uint64` | Expert Advisor ID (magic number) |
| `order_ticket` | `getOrderTicket()` | `uint64` | Order ticket |
| `symbol` | `getSymbol()` | `string` | Trade symbol |
| `requested_deal_volume_lots` | `getRequestedDealVolumeLots()` | `double` | Requested volume for a deal in lots |
| `price` | `getPrice()` | `double` | Price |
| `stopl_imit` | `getStoplImit()` | `double` | StopLimit level of the order |
| `stop_loss` | `getStopLoss()` | `double` | Stop Loss level of the order |
| `take_profit` | `getTakeProfit()` | `double` | Take Profit level of the order |
| `deviation` | `getDeviation()` | `uint64` | Maximal possible deviation from the requested price |
| `order_type` | `getOrderType()` | [SUB_ENUM_ORDER_TYPE](enums.md#sub_enum_order_type) | Order type |
| `order_type_filling` | `getOrderTypeFilling()` | [SUB_ENUM_ORDER_TYPE_FILLING](enums.md#sub_enum_order_type_filling) | Order execution type |
| `type_time` | `getTypeTime()` | [SUB_ENUM_ORDER_TYPE_TIME](enums.md#sub_enum_order_type_time) | Order expiration type |
| `order_expiration_time` | `getOrderExpirationTime()` | `google.protobuf.Timestamp` | Order expiration time (for the orders of ORDER_TIME_SPECIFIED type) |
| `order_expiration_time_utc` | `getOrderExpirationTimeUtc()` | `google.protobuf.Timestamp` | order_expiration_time in UTC (the same moment; order_expiration_time is in the broker server's time zone). |
| `order_comment` | `getOrderComment()` | `string` | Order comment |
| `position_ticket` | `getPositionTicket()` | `uint64` | Position ticket |
| `position_by_opposite_position` | `getPositionByOppositePosition()` | `uint64` | The ticket of an opposite position |

## MqlTradeResult

| Field | Java | Type | Description |
|---|---|---|---|
| `trade_return_int_code` | `getTradeReturnIntCode()` | `uint32` | Operation return code |
| `trade_return_code` | `getTradeReturnCode()` | [MqlErrorTradeCode](enums.md#mqlerrortradecode) |  |
| `deal_ticket` | `getDealTicket()` | `uint64` | Deal ticket, if it is performed |
| `order_ticket` | `getOrderTicket()` | `uint64` | Order ticket, if it is placed |
| `deal_volume` | `getDealVolume()` | `double` | Deal volume, confirmed by broker |
| `deal_price` | `getDealPrice()` | `double` | Deal price, confirmed by broker |
| `current_bid` | `getCurrentBid()` | `double` | Current Bid price |
| `current_ask` | `getCurrentAsk()` | `double` | Current Ask price |
| `broker_comment_to_operation` | `getBrokerCommentToOperation()` | `string` | Broker comment to operation (by default it is filled by description of trade server return code) |
| `terminal_dispatch_request_id` | `getTerminalDispatchRequestId()` | `uint32` | Request ID set by the terminal during the dispatch |
| `return_code_external` | `getReturnCodeExternal()` | `int32` | Return code of an external trading system |

## MqlTradeTransaction

| Field | Java | Type | Description |
|---|---|---|---|
| `deal_ticket` | `getDealTicket()` | `uint64` |  |
| `order_ticket` | `getOrderTicket()` | `uint64` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `type` | `getType()` | [SUB_ENUM_TRADE_TRANSACTION_TYPE](enums.md#sub_enum_trade_transaction_type) |  |
| `order_type` | `getOrderType()` | [SUB_ENUM_ORDER_TYPE](enums.md#sub_enum_order_type) |  |
| `order_state` | `getOrderState()` | [SUB_ENUM_ORDER_STATE](enums.md#sub_enum_order_state) |  |
| `deal_type` | `getDealType()` | [SUB_ENUM_DEAL_TYPE](enums.md#sub_enum_deal_type) |  |
| `order_time_type` | `getOrderTimeType()` | [SUB_ENUM_ORDER_TYPE_TIME](enums.md#sub_enum_order_type_time) |  |
| `order_expiration_time` | `getOrderExpirationTime()` | `google.protobuf.Timestamp` |  |
| `order_expiration_time_utc` | `getOrderExpirationTimeUtc()` | `google.protobuf.Timestamp` | order_expiration_time in UTC (the same moment; order_expiration_time is in the broker server's time zone). |
| `price` | `getPrice()` | `double` |  |
| `price_trigger_stop_limit` | `getPriceTriggerStopLimit()` | `double` | Stop limit order activation price |
| `price_stop_loss` | `getPriceStopLoss()` | `double` | Stop Loss level |
| `price_take_profit` | `getPriceTakeProfit()` | `double` | Take Profit level |
| `volume` | `getVolume()` | `double` | Volume in lots |
| `position_ticket` | `getPositionTicket()` | `uint64` | Position ticket |
| `position_by_opposite_position` | `getPositionByOppositePosition()` | `uint64` | Ticket of an opposite position |

## MrpcMqlBookInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [BookType](enums.md#booktype) |  |
| `price` | `getPrice()` | `double` |  |
| `volume` | `getVolume()` | `int64` |  |
| `volume_real` | `getVolumeReal()` | `double` |  |

## MrpcMqlTick

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `int64` | Time of the last prices update (Unix timestamp) |
| `time_utc` | `getTimeUtc()` | `int64` | time in UTC (the same moment; time is in the broker server's time zone). |
| `bid` | `getBid()` | `double` | Current Bid price |
| `ask` | `getAsk()` | `double` | Current Ask price |
| `last` | `getLast()` | `double` | Price of the last deal (Last) |
| `volume` | `getVolume()` | `uint64` | Volume for the current Last price |
| `time_msc` | `getTimeMsc()` | `int64` | Time of a price last update in milliseconds |
| `time_msc_utc` | `getTimeMscUtc()` | `int64` | time_msc in UTC (the same moment; time_msc is in the broker server's time zone). |
| `flags` | `getFlags()` | `uint32` | Tick flags |
| `volume_real` | `getVolumeReal()` | `double` | Volume for the current Last price with greater accuracy |

## MrpcMqlTradeCheckResult

| Field | Java | Type | Description |
|---|---|---|---|
| `returned_code` | `getReturnedCode()` | `uint32` | Reply code |
| `balance_after_deal` | `getBalanceAfterDeal()` | `double` | Balance after the execution of the deal |
| `equity_after_deal` | `getEquityAfterDeal()` | `double` | Equity after the execution of the deal |
| `profit` | `getProfit()` | `double` | Floating profit |
| `margin` | `getMargin()` | `double` | Margin requirements |
| `free_margin` | `getFreeMargin()` | `double` | Free margin |
| `margin_level` | `getMarginLevel()` | `double` | Margin level |
| `comment` | `getComment()` | `string` | Comment to the reply code (description of the error) |

## MrpcMqlTradeRequest

| Field | Java | Type | Description |
|---|---|---|---|
| `action` | `getAction()` | [MRPC_ENUM_TRADE_REQUEST_ACTIONS](enums.md#mrpc_enum_trade_request_actions) | Trade operation type |
| `expert_advisor_magic_number` | `getExpertAdvisorMagicNumber()` | `uint64` | Expert Advisor ID (magic number) |
| `order` | `getOrder()` | `uint64` | Order ticket |
| `symbol` | `getSymbol()` | `string` | Symbol name |
| `volume` | `getVolume()` | `double` | Requested volume for a deal in lots |
| `price` | `getPrice()` | `double` | Price |
| `stop_limit` | `getStopLimit()` | `double` | StopLimit level of the order |
| `stop_loss` | `getStopLoss()` | `double` | Stop Loss level of the order |
| `take_profit` | `getTakeProfit()` | `double` | Take Profit level of the order |
| `deviation` | `getDeviation()` | `uint64` | Maximal possible deviation from the requested price |
| `order_type` | `getOrderType()` | [ENUM_ORDER_TYPE_TF](enums.md#enum_order_type_tf) | type of order |
| `type_filling` | `getTypeFilling()` | [MRPC_ENUM_ORDER_TYPE_FILLING](enums.md#mrpc_enum_order_type_filling) | Order execution type |
| `type_time` | `getTypeTime()` | [MRPC_ENUM_ORDER_TYPE_TIME](enums.md#mrpc_enum_order_type_time) | Order expiration type |
| `expiration` | `getExpiration()` | `google.protobuf.Timestamp` | Order expiration time (for the orders of ORDER_TIME_SPECIFIED type) |
| `expiration_utc` | `getExpirationUtc()` | `google.protobuf.Timestamp` | expiration in UTC: send either expiration or expiration_utc, not both. |
| `comment` | `getComment()` | `string` | Order comment |
| `position` | `getPosition()` | `uint64` | Position ticket |
| `position_by` | `getPositionBy()` | `uint64` | The ticket of an opposite position |

## MrpcSubscriptionMqlTick

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `google.protobuf.Timestamp` | Time of the last prices update (Unix timestamp) |
| `time_utc` | `getTimeUtc()` | `google.protobuf.Timestamp` | time in UTC (the same moment; time is in the broker server's time zone). |
| `bid` | `getBid()` | `double` | Current Bid price |
| `ask` | `getAsk()` | `double` | Current Ask price |
| `last` | `getLast()` | `double` | Price of the last deal (Last) |
| `volume` | `getVolume()` | `uint64` | Volume for the current Last price |
| `time_msc` | `getTimeMsc()` | `int64` | Time of a price last update in milliseconds |
| `time_msc_utc` | `getTimeMscUtc()` | `int64` | time_msc in UTC (the same moment; time_msc is in the broker server's time zone). |
| `flags` | `getFlags()` | `uint32` | Tick flags |
| `volume_real` | `getVolumeReal()` | `double` | Volume for the current Last price with greater accuracy |
| `symbol` | `getSymbol()` | `string` |  |

## OnEventAccountInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `balance` | `getBalance()` | `double` |  |
| `credit` | `getCredit()` | `double` |  |
| `equity` | `getEquity()` | `double` |  |
| `margin` | `getMargin()` | `double` |  |
| `free_margin` | `getFreeMargin()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `margin_level` | `getMarginLevel()` | `double` |  |
| `login` | `getLogin()` | `int64` |  |

## OnPositionProfitData

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [MT5_SUB_ENUM_EVENT_GROUP_TYPE](enums.md#mt5_sub_enum_event_group_type) |  |
| `new_positions` | `getNewPositionsList()` | repeated [OnPositionProfitPositionInfo](messages.md#onpositionprofitpositioninfo) |  |
| `updated_positions` | `getUpdatedPositionsList()` | repeated [OnPositionProfitPositionInfo](messages.md#onpositionprofitpositioninfo) |  |
| `deleted_positions` | `getDeletedPositionsList()` | repeated [OnPositionProfitPositionInfo](messages.md#onpositionprofitpositioninfo) |  |
| `account_info` | `getAccountInfo()` | [OnEventAccountInfo](messages.md#oneventaccountinfo) |  |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnPositionProfitPositionInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `int64` |  |
| `profit` | `getProfit()` | `double` |  |
| `position_symbol` | `getPositionSymbol()` | `string` |  |

## OnPositionsAndPendingOrdersTicketsData

| Field | Java | Type | Description |
|---|---|---|---|
| `position_tickets` | `getPositionTicketsList()` | repeated `uint64` |  |
| `pending_order_tickets` | `getPendingOrderTicketsList()` | repeated `uint64` |  |
| `server_time` | `getServerTime()` | `google.protobuf.Timestamp` |  |
| `server_time_utc` | `getServerTimeUtc()` | `google.protobuf.Timestamp` | server_time in UTC (the same moment; server_time is in the broker server's time zone). |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnSymbolTickData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_tick` | `getSymbolTick()` | [MrpcSubscriptionMqlTick](messages.md#mrpcsubscriptionmqltick) |  |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnTadeEventData

| Field | Java | Type | Description |
|---|---|---|---|
| `new_orders` | `getNewOrdersList()` | repeated [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |
| `disappeared_orders` | `getDisappearedOrdersList()` | repeated [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |
| `state_changed_orders` | `getStateChangedOrdersList()` | repeated [OnTradeOrderStateChange](messages.md#ontradeorderstatechange) |  |
| `new_history_orders` | `getNewHistoryOrdersList()` | repeated [OnTradeHistoryOrderInfo](messages.md#ontradehistoryorderinfo) |  |
| `disappeared_history_orders` | `getDisappearedHistoryOrdersList()` | repeated [OnTradeHistoryOrderInfo](messages.md#ontradehistoryorderinfo) |  |
| `updated_history_orders` | `getUpdatedHistoryOrdersList()` | repeated [OnTradeHistoryOrderUpdate](messages.md#ontradehistoryorderupdate) |  |
| `new_history_deals` | `getNewHistoryDealsList()` | repeated [OnTradeHistoryDealInfo](messages.md#ontradehistorydealinfo) |  |
| `disappeared_history_deals` | `getDisappearedHistoryDealsList()` | repeated [OnTradeHistoryDealInfo](messages.md#ontradehistorydealinfo) |  |
| `updated_history_deals` | `getUpdatedHistoryDealsList()` | repeated [OnTradeHistoryDealUpdate](messages.md#ontradehistorydealupdate) |  |
| `new_positions` | `getNewPositionsList()` | repeated [OnTradePositionInfo](messages.md#ontradepositioninfo) |  |
| `disappeared_positions` | `getDisappearedPositionsList()` | repeated [OnTradePositionInfo](messages.md#ontradepositioninfo) |  |
| `updated_positions` | `getUpdatedPositionsList()` | repeated [OnTradePositionUpdate](messages.md#ontradepositionupdate) |  |

## OnTradeData

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [MT5_SUB_ENUM_EVENT_GROUP_TYPE](enums.md#mt5_sub_enum_event_group_type) |  |
| `event_data` | `getEventData()` | [OnTadeEventData](messages.md#ontadeeventdata) |  |
| `account_info` | `getAccountInfo()` | [OnEventAccountInfo](messages.md#oneventaccountinfo) |  |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnTradeHistoryDealInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `uint64` |  |
| `order_ticket` | `getOrderTicket()` | `int64` |  |
| `type` | `getType()` | [SUB_ENUM_DEAL_TYPE](enums.md#sub_enum_deal_type) |  |
| `deal_time` | `getDealTime()` | `google.protobuf.Timestamp` |  |
| `deal_time_utc` | `getDealTimeUtc()` | `google.protobuf.Timestamp` | deal_time in UTC (the same moment; deal_time is in the broker server's time zone). |
| `entry` | `getEntry()` | [SUB_ENUM_DEAL_ENTRY](enums.md#sub_enum_deal_entry) |  |
| `deal_position_id` | `getDealPositionId()` | `int64` |  |
| `commission` | `getCommission()` | `double` |  |
| `fee` | `getFee()` | `double` |  |
| `price` | `getPrice()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `sl` | `getSl()` | `double` |  |
| `tp` | `getTp()` | `double` |  |
| `volume` | `getVolume()` | `double` |  |
| `comment` | `getComment()` | `string` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `swap` | `getSwap()` | `double` |  |
| `reason` | `getReason()` | [SUB_ENUM_DEAL_REASON](enums.md#sub_enum_deal_reason) |  |
| `magic` | `getMagic()` | `int64` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |

## OnTradeHistoryDealUpdate

| Field | Java | Type | Description |
|---|---|---|---|
| `previous_history_deal` | `getPreviousHistoryDeal()` | [OnTradeHistoryDealInfo](messages.md#ontradehistorydealinfo) |  |
| `current_history_deal` | `getCurrentHistoryDeal()` | [OnTradeHistoryDealInfo](messages.md#ontradehistorydealinfo) |  |

## OnTradeHistoryOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `int64` |  |
| `state` | `getState()` | [SUB_ENUM_ORDER_STATE](enums.md#sub_enum_order_state) |  |
| `setup_time` | `getSetupTime()` | `google.protobuf.Timestamp` |  |
| `setup_time_utc` | `getSetupTimeUtc()` | `google.protobuf.Timestamp` | setup_time in UTC (the same moment; setup_time is in the broker server's time zone). |
| `done_time` | `getDoneTime()` | `google.protobuf.Timestamp` |  |
| `done_time_utc` | `getDoneTimeUtc()` | `google.protobuf.Timestamp` | done_time in UTC (the same moment; done_time is in the broker server's time zone). |
| `time_expiration` | `getTimeExpiration()` | `google.protobuf.Timestamp` |  |
| `time_expiration_utc` | `getTimeExpirationUtc()` | `google.protobuf.Timestamp` | time_expiration in UTC (the same moment; time_expiration is in the broker server's time zone). |
| `position_id` | `getPositionId()` | `uint64` |  |
| `type_time` | `getTypeTime()` | [SUB_ENUM_ORDER_TYPE_TIME](enums.md#sub_enum_order_type_time) |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `stop_limit` | `getStopLimit()` | `double` |  |
| `price_current` | `getPriceCurrent()` | `double` |  |
| `price_open` | `getPriceOpen()` | `double` |  |
| `volume_current` | `getVolumeCurrent()` | `double` |  |
| `volume_initial` | `getVolumeInitial()` | `double` |  |
| `magic` | `getMagic()` | `int64` |  |
| `position_by` | `getPositionBy()` | `int64` |  |
| `reason` | `getReason()` | [SUB_ENUM_DEAL_REASON](enums.md#sub_enum_deal_reason) |  |
| `comment` | `getComment()` | `string` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `time_expiration_seconds` | `getTimeExpirationSeconds()` | `int64` |  |
| `time_expiration_seconds_utc` | `getTimeExpirationSecondsUtc()` | `int64` | time_expiration_seconds in UTC (the same moment; time_expiration_seconds is in the broker server's time zone). |
| `account_login` | `getAccountLogin()` | `int64` |  |
| `order_type` | `getOrderType()` | [SUB_ENUM_ORDER_TYPE](enums.md#sub_enum_order_type) |  |
| `order_type_filling` | `getOrderTypeFilling()` | [SUB_ENUM_ORDER_TYPE_FILLING](enums.md#sub_enum_order_type_filling) |  |

## OnTradeHistoryOrderUpdate

| Field | Java | Type | Description |
|---|---|---|---|
| `previous_history_order` | `getPreviousHistoryOrder()` | [OnTradeHistoryOrderInfo](messages.md#ontradehistoryorderinfo) |  |
| `current_history_order` | `getCurrentHistoryOrder()` | [OnTradeHistoryOrderInfo](messages.md#ontradehistoryorderinfo) |  |

## OnTradeOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `int64` |  |
| `state` | `getState()` | [SUB_ENUM_ORDER_STATE](enums.md#sub_enum_order_state) |  |
| `setup_time_msc` | `getSetupTimeMsc()` | `int64` |  |
| `setup_time_msc_utc` | `getSetupTimeMscUtc()` | `int64` | setup_time_msc in UTC (the same moment; setup_time_msc is in the broker server's time zone). |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `stop_limit` | `getStopLimit()` | `double` |  |
| `price_current` | `getPriceCurrent()` | `double` |  |
| `time_expiration` | `getTimeExpiration()` | `google.protobuf.Timestamp` |  |
| `time_expiration_utc` | `getTimeExpirationUtc()` | `google.protobuf.Timestamp` | time_expiration in UTC (the same moment; time_expiration is in the broker server's time zone). |
| `time_type` | `getTimeType()` | [SUB_ENUM_ORDER_TYPE_TIME](enums.md#sub_enum_order_type_time) |  |
| `comment` | `getComment()` | `string` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `magic` | `getMagic()` | `int64` |  |
| `price_open` | `getPriceOpen()` | `double` |  |
| `setup_time` | `getSetupTime()` | `google.protobuf.Timestamp` |  |
| `setup_time_utc` | `getSetupTimeUtc()` | `google.protobuf.Timestamp` | setup_time in UTC (the same moment; setup_time is in the broker server's time zone). |
| `time_expiration_seconds` | `getTimeExpirationSeconds()` | `int64` |  |
| `time_expiration_seconds_utc` | `getTimeExpirationSecondsUtc()` | `int64` | time_expiration_seconds in UTC (the same moment; time_expiration_seconds is in the broker server's time zone). |
| `volume_current` | `getVolumeCurrent()` | `double` |  |
| `volume_initial` | `getVolumeInitial()` | `double` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |
| `order_type` | `getOrderType()` | [SUB_ENUM_ORDER_TYPE](enums.md#sub_enum_order_type) |  |
| `order_type_filling` | `getOrderTypeFilling()` | [SUB_ENUM_ORDER_TYPE_FILLING](enums.md#sub_enum_order_type_filling) |  |
| `order_reason` | `getOrderReason()` | [SUB_ENUM_ORDER_REASON](enums.md#sub_enum_order_reason) |  |
| `position_id` | `getPositionId()` | `int64` |  |
| `position_by_id` | `getPositionById()` | `int64` |  |

## OnTradeOrderStateChange

| Field | Java | Type | Description |
|---|---|---|---|
| `previous_order` | `getPreviousOrder()` | [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |
| `current_order` | `getCurrentOrder()` | [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |

## OnTradePositionInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `int64` |  |
| `type` | `getType()` | [SUB_ENUM_POSITION_TYPE](enums.md#sub_enum_position_type) |  |
| `position_time` | `getPositionTime()` | `google.protobuf.Timestamp` |  |
| `position_time_utc` | `getPositionTimeUtc()` | `google.protobuf.Timestamp` | position_time in UTC (the same moment; position_time is in the broker server's time zone). |
| `last_update_time` | `getLastUpdateTime()` | `google.protobuf.Timestamp` |  |
| `last_update_time_utc` | `getLastUpdateTimeUtc()` | `google.protobuf.Timestamp` | last_update_time in UTC (the same moment; last_update_time is in the broker server's time zone). |
| `price_open` | `getPriceOpen()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `sl` | `getSl()` | `double` |  |
| `tp` | `getTp()` | `double` |  |
| `volume` | `getVolume()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `comment` | `getComment()` | `string` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `magic` | `getMagic()` | `int64` |  |
| `price_current` | `getPriceCurrent()` | `double` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |
| `reason` | `getReason()` | [SUB_ENUM_POSITION_REASON](enums.md#sub_enum_position_reason) |  |
| `from_pending_order` | `getFromPendingOrder()` | `bool` |  |

## OnTradePositionUpdate

| Field | Java | Type | Description |
|---|---|---|---|
| `previous_position` | `getPreviousPosition()` | [OnTradePositionInfo](messages.md#ontradepositioninfo) |  |
| `current_position` | `getCurrentPosition()` | [OnTradePositionInfo](messages.md#ontradepositioninfo) |  |

## OnTradeTransactionData

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [MT5_SUB_ENUM_EVENT_GROUP_TYPE](enums.md#mt5_sub_enum_event_group_type) |  |
| `trade_transaction` | `getTradeTransaction()` | [MqlTradeTransaction](messages.md#mqltradetransaction) |  |
| `trade_request` | `getTradeRequest()` | [MqlTradeRequest](messages.md#mqltraderequest) |  |
| `trade_result` | `getTradeResult()` | [MqlTradeResult](messages.md#mqltraderesult) |  |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |
| `account_info` | `getAccountInfo()` | [OnEventAccountInfo](messages.md#oneventaccountinfo) |  |

## OpenedOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `uint32` |  |
| `ticket` | `getTicket()` | `uint64` |  |
| `price_current` | `getPriceCurrent()` | `double` |  |
| `price_open` | `getPriceOpen()` | `double` |  |
| `stop_limit` | `getStopLimit()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `volume_current` | `getVolumeCurrent()` | `double` |  |
| `volume_initial` | `getVolumeInitial()` | `double` |  |
| `magic_number` | `getMagicNumber()` | `int64` |  |
| `reason` | `getReason()` | `int32` |  |
| `type` | `getType()` | [BMT5_ENUM_ORDER_TYPE](enums.md#bmt5_enum_order_type) |  |
| `state` | `getState()` | [BMT5_ENUM_ORDER_STATE](enums.md#bmt5_enum_order_state) |  |
| `time_expiration` | `getTimeExpiration()` | `google.protobuf.Timestamp` |  |
| `time_expiration_utc` | `getTimeExpirationUtc()` | `google.protobuf.Timestamp` | time_expiration in UTC (the same moment; time_expiration is in the broker server's time zone). |
| `time_setup` | `getTimeSetup()` | `google.protobuf.Timestamp` |  |
| `time_setup_utc` | `getTimeSetupUtc()` | `google.protobuf.Timestamp` | time_setup in UTC (the same moment; time_setup is in the broker server's time zone). |
| `time_done` | `getTimeDone()` | `google.protobuf.Timestamp` |  |
| `time_done_utc` | `getTimeDoneUtc()` | `google.protobuf.Timestamp` | time_done in UTC (the same moment; time_done is in the broker server's time zone). |
| `type_filling` | `getTypeFilling()` | [BMT5_ENUM_ORDER_TYPE_FILLING](enums.md#bmt5_enum_order_type_filling) |  |
| `type_time` | `getTypeTime()` | [BMT5_ENUM_ORDER_TYPE_TIME](enums.md#bmt5_enum_order_type_time) |  |
| `position_id` | `getPositionId()` | `int64` |  |
| `position_by_id` | `getPositionById()` | `int64` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `external_id` | `getExternalId()` | `string` |  |
| `comment` | `getComment()` | `string` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |

## OpenedOrdersData

| Field | Java | Type | Description |
|---|---|---|---|
| `opened_orders` | `getOpenedOrdersList()` | repeated [OpenedOrderInfo](messages.md#openedorderinfo) |  |
| `position_infos` | `getPositionInfosList()` | repeated [PositionInfo](messages.md#positioninfo) |  |

## OpenedOrdersTicketsData

| Field | Java | Type | Description |
|---|---|---|---|
| `opened_orders_tickets` | `getOpenedOrdersTicketsList()` | repeated `int64` |  |
| `opened_position_tickets` | `getOpenedPositionTicketsList()` | repeated `int64` |  |

## OrderCalcMarginData

| Field | Java | Type | Description |
|---|---|---|---|
| `margin` | `getMargin()` | `double` | The variable, to which the value of the required margin will be written in case the function is successfully executed. The calculation is performed as if there were no pending orders and open positions on the current account. The margin value depends on many factors, and can differ in different market environments. |

## OrderCalcProfitData

| Field | Java | Type | Description |
|---|---|---|---|
| `profit` | `getProfit()` | `double` | The estimated profit value depends on many factors, and can differ in different market environments. |

## OrderCheckData

| Field | Java | Type | Description |
|---|---|---|---|
| `mql_trade_check_result` | `getMqlTradeCheckResult()` | [MrpcMqlTradeCheckResult](messages.md#mrpcmqltradecheckresult) |  |

## OrderCloseData

| Field | Java | Type | Description |
|---|---|---|---|
| `returned_code` | `getReturnedCode()` | `uint32` | Operation return code |
| `returned_string_code` | `getReturnedStringCode()` | `string` |  |
| `returned_code_description` | `getReturnedCodeDescription()` | `string` |  |
| `close_mode` | `getCloseMode()` | [MRPC_ORDER_CLOSE_MODE](enums.md#mrpc_order_close_mode) |  |

## OrderHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `uint64` |  |
| `setup_time` | `getSetupTime()` | `google.protobuf.Timestamp` |  |
| `setup_time_utc` | `getSetupTimeUtc()` | `google.protobuf.Timestamp` | setup_time in UTC (the same moment; setup_time is in the broker server's time zone). |
| `done_time` | `getDoneTime()` | `google.protobuf.Timestamp` |  |
| `done_time_utc` | `getDoneTimeUtc()` | `google.protobuf.Timestamp` | done_time in UTC (the same moment; done_time is in the broker server's time zone). |
| `state` | `getState()` | [BMT5_ENUM_ORDER_STATE](enums.md#bmt5_enum_order_state) |  |
| `price_current` | `getPriceCurrent()` | `double` |  |
| `price_open` | `getPriceOpen()` | `double` |  |
| `stop_limit` | `getStopLimit()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `volume_current` | `getVolumeCurrent()` | `double` |  |
| `volume_initial` | `getVolumeInitial()` | `double` |  |
| `magic_number` | `getMagicNumber()` | `int64` |  |
| `type` | `getType()` | [BMT5_ENUM_ORDER_TYPE](enums.md#bmt5_enum_order_type) |  |
| `time_expiration` | `getTimeExpiration()` | `google.protobuf.Timestamp` |  |
| `time_expiration_utc` | `getTimeExpirationUtc()` | `google.protobuf.Timestamp` | time_expiration in UTC (the same moment; time_expiration is in the broker server's time zone). |
| `type_filling` | `getTypeFilling()` | [BMT5_ENUM_ORDER_TYPE_FILLING](enums.md#bmt5_enum_order_type_filling) |  |
| `type_time` | `getTypeTime()` | [BMT5_ENUM_ORDER_TYPE_TIME](enums.md#bmt5_enum_order_type_time) |  |
| `position_id` | `getPositionId()` | `uint64` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `external_id` | `getExternalId()` | `string` |  |
| `comment` | `getComment()` | `string` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |

## OrderModifyData

| Field | Java | Type | Description |
|---|---|---|---|
| `returned_code` | `getReturnedCode()` | `uint32` | Operation return code |
| `deal` | `getDeal()` | `uint64` | Deal ticket, if it is performed |
| `order` | `getOrder()` | `uint64` | Order ticket, if it is placed |
| `volume` | `getVolume()` | `double` | Deal volume, confirmed by broker |
| `price` | `getPrice()` | `double` | Deal price, confirmed by broker |
| `bid` | `getBid()` | `double` | Current Bid price |
| `ask` | `getAsk()` | `double` | Current Ask price |
| `comment` | `getComment()` | `string` | Broker comment to operation (by default it is filled by description of trade server return code) |
| `request_id` | `getRequestId()` | `uint32` | Request ID set by the terminal during the dispatch |
| `ret_code_external` | `getRetCodeExternal()` | `int32` | Return code of an external trading system |
| `returned_string_code` | `getReturnedStringCode()` | `string` |  |
| `returned_code_description` | `getReturnedCodeDescription()` | `string` |  |

## OrderSendData

| Field | Java | Type | Description |
|---|---|---|---|
| `returned_code` | `getReturnedCode()` | `uint32` | Operation return code |
| `deal` | `getDeal()` | `uint64` | Deal ticket, if it is performed |
| `order` | `getOrder()` | `uint64` | Order ticket, if it is placed |
| `volume` | `getVolume()` | `double` | Deal volume, confirmed by broker |
| `price` | `getPrice()` | `double` | Deal price, confirmed by broker |
| `bid` | `getBid()` | `double` | Current Bid price |
| `ask` | `getAsk()` | `double` | Current Ask price |
| `comment` | `getComment()` | `string` | Broker comment to operation (by default it is filled by description of trade server return code) |
| `request_id` | `getRequestId()` | `uint32` | Request ID set by the terminal during the dispatch |
| `ret_code_external` | `getRetCodeExternal()` | `int32` | Return code of an external trading system |
| `returned_string_code` | `getReturnedStringCode()` | `string` |  |
| `returned_code_description` | `getReturnedCodeDescription()` | `string` |  |

## OrdersHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `arrayTotal` | `getArrayTotal()` | `int32` |  |
| `pageNumber` | `getPageNumber()` | `int32` |  |
| `itemsPerPage` | `getItemsPerPage()` | `int32` |  |
| `history_data` | `getHistoryDataList()` | repeated [HistoryData](messages.md#historydata) |  |
| `next_page_token` | `getNextPageToken()` | `string` | Cursor paging: pass back as page_token for the next page; empty when this was the last page (end of history for the range). |

## PositionHistoryInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `position_ticket` | `getPositionTicket()` | `uint64` |  |
| `order_type` | `getOrderType()` | [AH_ENUM_POSITIONS_HISTORY_ORDER_TYPE](enums.md#ah_enum_positions_history_order_type) |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` |  |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `close_time` | `getCloseTime()` | `google.protobuf.Timestamp` |  |
| `close_time_utc` | `getCloseTimeUtc()` | `google.protobuf.Timestamp` | close_time in UTC (the same moment; close_time is in the broker server's time zone). |
| `volume` | `getVolume()` | `double` |  |
| `open_price` | `getOpenPrice()` | `double` |  |
| `close_price` | `getClosePrice()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `market_value` | `getMarketValue()` | `double` |  |
| `commission` | `getCommission()` | `double` |  |
| `fee` | `getFee()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `comment` | `getComment()` | `string` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `magic` | `getMagic()` | `int64` |  |

## PositionInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `uint32` |  |
| `ticket` | `getTicket()` | `uint64` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` |  |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `volume` | `getVolume()` | `double` |  |
| `price_open` | `getPriceOpen()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `price_current` | `getPriceCurrent()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `last_update_time` | `getLastUpdateTime()` | `google.protobuf.Timestamp` |  |
| `last_update_time_utc` | `getLastUpdateTimeUtc()` | `google.protobuf.Timestamp` | last_update_time in UTC (the same moment; last_update_time is in the broker server's time zone). |
| `type` | `getType()` | [BMT5_ENUM_POSITION_TYPE](enums.md#bmt5_enum_position_type) |  |
| `magic_number` | `getMagicNumber()` | `int64` |  |
| `identifier` | `getIdentifier()` | `int64` |  |
| `reason` | `getReason()` | [BMT5_ENUM_POSITION_REASON](enums.md#bmt5_enum_position_reason) |  |
| `symbol` | `getSymbol()` | `string` |  |
| `comment` | `getComment()` | `string` |  |
| `external_id` | `getExternalId()` | `string` |  |
| `position_commission` | `getPositionCommission()` | `double` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |

## PositionsHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `history_positions` | `getHistoryPositionsList()` | repeated [PositionHistoryInfo](messages.md#positionhistoryinfo) |  |
| `total_count` | `getTotalCount()` | `int32` | Number of positions in the selected range, over all pages (at the moment of the call). |
| `next_page_token` | `getNextPageToken()` | `string` | Cursor paging: pass back as page_token for the next page; empty when this was the last page (end of history for the range). |

## PositionsTotalData

Message to reply with the total number of positions

| Field | Java | Type | Description |
|---|---|---|---|
| `total_positions` | `getTotalPositions()` | `int32` | Total number of positions |

## PriceBar

One candle, named as MQL5 names them so the mapping stays obvious.

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `int64` | Unix seconds, bar open |
| `time_utc` | `getTimeUtc()` | `int64` | time in UTC (the same moment; time is in the broker server's time zone). |
| `open` | `getOpen()` | `double` |  |
| `high` | `getHigh()` | `double` |  |
| `low` | `getLow()` | `double` |  |
| `close` | `getClose()` | `double` |  |
| `tick_volume` | `getTickVolume()` | `int64` |  |
| `spread` | `getSpread()` | `int32` |  |
| `real_volume` | `getRealVolume()` | `int64` |  |

## PriceHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `bars` | `getBarsList()` | repeated [PriceBar](messages.md#pricebar) |  |

## ProfitData

| Field | Java | Type | Description |
|---|---|---|---|
| `tiket` | `getTiket()` | `int64` |  |
| `date` | `getDate()` | `google.protobuf.Timestamp` |  |
| `date_utc` | `getDateUtc()` | `google.protobuf.Timestamp` | date in UTC (the same moment; date is in the broker server's time zone). |
| `profit` | `getProfit()` | `double` |  |

## ProfitabilityData

| Field | Java | Type | Description |
|---|---|---|---|
| `won_trades` | `getWonTrades()` | `int64` |  |
| `won_trades_percent` | `getWonTradesPercent()` | `double` |  |
| `lost_trades` | `getLostTrades()` | `int64` |  |
| `lost_trades_percent` | `getLostTradesPercent()` | `double` |  |

## QuoteHistoryBar

One bar (candle), named as MQL5 names the fields of MqlRates.

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `int64` | Unix seconds (UTC), bar open |
| `time_utc` | `getTimeUtc()` | `int64` | time in UTC (the same moment; time is in the broker server's time zone). |
| `open` | `getOpen()` | `double` |  |
| `high` | `getHigh()` | `double` |  |
| `low` | `getLow()` | `double` |  |
| `close` | `getClose()` | `double` |  |
| `tick_volume` | `getTickVolume()` | `int64` | ticks in the bar |
| `spread` | `getSpread()` | `int32` | points |
| `real_volume` | `getRealVolume()` | `int64` | exchange volume, 0 where the broker has none |

## QuoteHistoryBarsData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` |  |
| `timeframe` | `getTimeframe()` | [QUOTE_HISTORY_TIMEFRAME](enums.md#quote_history_timeframe) |  |
| `bars` | `getBarsList()` | repeated [QuoteHistoryBar](messages.md#quotehistorybar) | Oldest first. Empty when the range holds no bars. |
| `next_page_token` | `getNextPageToken()` | `string` | Pass back as page_token for the next page; empty when this was the last. |
| `server_first_time` | `getServerFirstTime()` | `int64` | Unix seconds of the oldest bar the broker has for this symbol and timeframe (0 when the terminal does not know yet). |
| `server_first_time_utc` | `getServerFirstTimeUtc()` | `int64` | server_first_time in UTC (the same moment; server_first_time is in the broker server's time zone). |

## QuoteHistoryTick

One tick, named as MQL5 names the fields of MqlTick.

| Field | Java | Type | Description |
|---|---|---|---|
| `time_msc` | `getTimeMsc()` | `int64` | Unix milliseconds (UTC) |
| `time_msc_utc` | `getTimeMscUtc()` | `int64` | time_msc in UTC (the same moment; time_msc is in the broker server's time zone). |
| `bid` | `getBid()` | `double` |  |
| `ask` | `getAsk()` | `double` |  |
| `last` | `getLast()` | `double` | last deal price, 0 where the broker has none |
| `volume` | `getVolume()` | `int64` | last deal volume, 0 where the broker has none |
| `volume_real` | `getVolumeReal()` | `double` | last deal volume with greater accuracy |
| `flags` | `getFlags()` | `int32` | MQL5 TICK_FLAG_* bits: what changed in this tick |

## QuoteHistoryTicksData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` |  |
| `ticks` | `getTicksList()` | repeated [QuoteHistoryTick](messages.md#quotehistorytick) | Oldest first. Several ticks can share one millisecond; the order within a millisecond is the terminal's. |
| `next_page_token` | `getNextPageToken()` | `string` | Pass back as page_token for the next page; empty when this was the last. |

## ReconnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_was_recreated` | `getTerminalWasRecreated()` | `bool` |  |
| `terminal_type` | `getTerminalType()` | [TerminalType](enums.md#terminaltype) |  |
| `terminal_instance_guid` | `getTerminalInstanceGuid()` | `string` |  |
| `old_terminal_full_live_time_seconds` | `getOldTerminalFullLiveTimeSeconds()` | `int64` |  |
| `old_log_files` | `getOldLogFilesList()` | repeated [LogFileInfo](messages.md#logfileinfo) |  |

## ScreenshotData

| Field | Java | Type | Description |
|---|---|---|---|
| `image_data` | `getImageData()` | `bytes` |  |
| `display_number` | `getDisplayNumber()` | `int32` |  |
| `terminal_id` | `getTerminalId()` | `string` |  |
| `content_type` | `getContentType()` | `string` |  |

## ServerTimezoneData

| Field | Java | Type | Description |
|---|---|---|---|
| `server` | `getServer()` | `string` | The broker server name of the account. |
| `utc_offset_seconds` | `getUtcOffsetSeconds()` | `int32` | Seconds east of UTC (broker server time = UTC + utc_offset_seconds), a multiple of 15 minutes. Absent when not known (source SERVER_TIMEZONE_SOURCE_UNKNOWN); a present 0 is a UTC+0 broker. |
| `source` | `getSource()` | [SERVER_TIMEZONE_SOURCE](enums.md#server_timezone_source) | Where utc_offset_seconds came from. |
| `validated_utc` | `getValidatedUtc()` | `google.protobuf.Timestamp` | When the offset was last validated from a fresh quote: for LIVE the latest reading of this terminal (refreshed every few seconds), for LAST_VALIDATED the latest reading of any terminal of the same broker server. Unset when UNKNOWN. |

## StatsData

| Field | Java | Type | Description |
|---|---|---|---|
| `summary` | `getSummary()` | [TradeSummaryData](messages.md#tradesummarydata) |  |
| `max_balance_drawdown_raw` | `getMaxBalanceDrawdownRaw()` | `double` |  |
| `max_balance_drawdown_relative` | `getMaxBalanceDrawdownRelative()` | `double` |  |
| `max_equity_drawdown_raw` | `getMaxEquityDrawdownRaw()` | `double` |  |
| `max_equity_drawdown_relative` | `getMaxEquityDrawdownRelative()` | `double` |  |
| `markets` | `getMarketsList()` | repeated [MarketTradeCountData](messages.md#markettradecountdata) |  |
| `profitability` | `getProfitability()` | [ProfitabilityData](messages.md#profitabilitydata) |  |
| `pips` | `getPips()` | `double` |  |
| `lots` | `getLots()` | `double` |  |
| `comissions` | `getComissions()` | `double` |  |
| `average_win` | `getAverageWin()` | [AveragePipsUsdData](messages.md#averagepipsusddata) |  |
| `average_lost` | `getAverageLost()` | [AveragePipsUsdData](messages.md#averagepipsusddata) |  |
| `longs_won` | `getLongsWon()` | [WonData](messages.md#wondata) |  |
| `shorts_won` | `getShortsWon()` | [WonData](messages.md#wondata) |  |
| `best_trade` | `getBestTrade()` | [ProfitData](messages.md#profitdata) |  |
| `worst_trade` | `getWorstTrade()` | [ProfitData](messages.md#profitdata) |  |
| `best_trade_pips` | `getBestTradePips()` | [ProfitData](messages.md#profitdata) |  |
| `worst_trade_pips` | `getWorstTradePips()` | [ProfitData](messages.md#profitdata) |  |
| `average_trade_length` | `getAverageTradeLength()` | `string` |  |
| `profit_factor` | `getProfitFactor()` | `double` |  |
| `standard_deviation` | `getStandardDeviation()` | `double` |  |
| `sharpe_ratio` | `getSharpeRatio()` | `double` |  |
| `z_score` | `getZScore()` | [ZScoreData](messages.md#zscoredata) |  |
| `expectancy` | `getExpectancy()` | [ExpectancyData](messages.md#expectancydata) |  |
| `ghpr` | `getGhpr()` | `double` |  |
| `trades` | `getTrades()` | `int64` |  |

## StatsWithChartsData

| Field | Java | Type | Description |
|---|---|---|---|
| `stats` | `getStats()` | [StatsData](messages.md#statsdata) |  |
| `charts` | `getChartsList()` | repeated [EquityPointData](messages.md#equitypointdata) |  |
| `trades` | `getTradesList()` | repeated [TradeHistoryItemData](messages.md#tradehistoryitemdata) |  |
| `open_trades` | `getOpenTradesList()` | repeated [TradeHistoryItemData](messages.md#tradehistoryitemdata) |  |

## SymbolExistData

| Field | Java | Type | Description |
|---|---|---|---|
| `exists` | `getExists()` | `bool` | If false, the symbol is not found among standard and custom ones (https://www.mql5.com/en/docs/customsymbols). |
| `is_custom` | `getIsCustom()` | `bool` | If true, the detected symbol is a custom one. |

## SymbolInfoDoubleData

| Field | Java | Type | Description |
|---|---|---|---|
| `value` | `getValue()` | `double` | the property value |

## SymbolInfoIntegerData

| Field | Java | Type | Description |
|---|---|---|---|
| `value` | `getValue()` | `int64` | Variable of the long type receiving the value of the requested property |
| `value_utc` | `getValueUtc()` | `int64` | UTC of value when the requested property is a time (SYMBOL_TIME, SYMBOL_TIME_MSC, SYMBOL_START_TIME, SYMBOL_EXPIRATION_TIME); 0 for any other property. |

## SymbolInfoMarginRateData

| Field | Java | Type | Description |
|---|---|---|---|
| `maintenance_margin_rate` | `getMaintenanceMarginRate()` | `double` | A double type variable for receiving a maintenance margin rate. Maintenance margin is a minimum amount for maintaining an open position of 1 lot in the appropriate direction. Multiplying the rate by the maintenance margin, we receive the amount of funds to be reserved on the account after an order of the specified type is activated |
| `initial_margin_rate` | `getInitialMarginRate()` | `double` | A double type variable for receiving an initial margin rate. Initial margin is a security deposit for 1 lot deal in the appropriate direction. Multiplying the rate by the initial margin, we receive the amount of funds to be reserved on the account when placing an order of the specified type. |

## SymbolInfoSessionQuoteData

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `google.protobuf.Timestamp` | Session beginning time in seconds from 00 hours 00 minutes, in the returned value date should be ignored |
| `to` | `getTo()` | `google.protobuf.Timestamp` | Session end time in seconds from 00 hours 00 minutes, in the returned value date should be ignored |

## SymbolInfoSessionTradeData

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `google.protobuf.Timestamp` | Session beginning time in seconds from 00 hours 00 minutes, in the returned value date should be ignored. |
| `to` | `getTo()` | `google.protobuf.Timestamp` | Session end time in seconds from 00 hours 00 minutes, in the returned value date should be ignored. |

## SymbolInfoStringData

| Field | Java | Type | Description |
|---|---|---|---|
| `value` | `getValue()` | `string` | Variable of the string type receiving the value of the requested property |

## SymbolIsSynchronizedData

| Field | Java | Type | Description |
|---|---|---|---|
| `synchronized` | `getSynchronized()` | `bool` | If data are synchronized, returns 'true'; otherwise returns 'false' |

## SymbolListData

| Field | Java | Type | Description |
|---|---|---|---|
| `Symbols` | `getSymbolsList()` | repeated `string` |  |

## SymbolNameData

| Field | Java | Type | Description |
|---|---|---|---|
| `name` | `getName()` | `string` | Value of string type with the symbol name |

## SymbolParameters

| Field | Java | Type | Description |
|---|---|---|---|
| `name` | `getName()` | `string` |  |
| `bid` | `getBid()` | `double` |  |
| `bid_high` | `getBidHigh()` | `double` |  |
| `bid_low` | `getBidLow()` | `double` |  |
| `ask` | `getAsk()` | `double` |  |
| `ask_high` | `getAskHigh()` | `double` |  |
| `ask_low` | `getAskLow()` | `double` |  |
| `last` | `getLast()` | `double` |  |
| `last_high` | `getLastHigh()` | `double` |  |
| `last_low` | `getLastLow()` | `double` |  |
| `volume_real` | `getVolumeReal()` | `double` |  |
| `volume_high_real` | `getVolumeHighReal()` | `double` |  |
| `volume_low_real` | `getVolumeLowReal()` | `double` |  |
| `option_strike` | `getOptionStrike()` | `double` |  |
| `point` | `getPoint()` | `double` |  |
| `trade_tick_value` | `getTradeTickValue()` | `double` |  |
| `trade_tick_value_profit` | `getTradeTickValueProfit()` | `double` |  |
| `trade_tick_value_loss` | `getTradeTickValueLoss()` | `double` |  |
| `trade_tick_size` | `getTradeTickSize()` | `double` |  |
| `trade_contract_size` | `getTradeContractSize()` | `double` |  |
| `trade_accrued_interest` | `getTradeAccruedInterest()` | `double` |  |
| `trade_face_value` | `getTradeFaceValue()` | `double` |  |
| `trade_liquidity_rate` | `getTradeLiquidityRate()` | `double` |  |
| `volume_min` | `getVolumeMin()` | `double` |  |
| `volume_max` | `getVolumeMax()` | `double` |  |
| `volume_step` | `getVolumeStep()` | `double` |  |
| `volume_limit` | `getVolumeLimit()` | `double` |  |
| `swap_long` | `getSwapLong()` | `double` |  |
| `swap_short` | `getSwapShort()` | `double` |  |
| `swap_sunday` | `getSwapSunday()` | `double` |  |
| `swap_monday` | `getSwapMonday()` | `double` |  |
| `swap_tuesday` | `getSwapTuesday()` | `double` |  |
| `swap_wednesday` | `getSwapWednesday()` | `double` |  |
| `swap_thursday` | `getSwapThursday()` | `double` |  |
| `swap_friday` | `getSwapFriday()` | `double` |  |
| `swap_saturday` | `getSwapSaturday()` | `double` |  |
| `margin_initial` | `getMarginInitial()` | `double` |  |
| `margin_maintenance` | `getMarginMaintenance()` | `double` |  |
| `session_volume` | `getSessionVolume()` | `double` |  |
| `session_turnover` | `getSessionTurnover()` | `double` |  |
| `session_interest` | `getSessionInterest()` | `double` |  |
| `session_buy_orders_volume` | `getSessionBuyOrdersVolume()` | `double` |  |
| `session_sell_orders_volume` | `getSessionSellOrdersVolume()` | `double` |  |
| `session_open` | `getSessionOpen()` | `double` |  |
| `session_close` | `getSessionClose()` | `double` |  |
| `session_aw` | `getSessionAw()` | `double` |  |
| `session_price_settlement` | `getSessionPriceSettlement()` | `double` |  |
| `session_price_limit_min` | `getSessionPriceLimitMin()` | `double` |  |
| `session_price_limit_max` | `getSessionPriceLimitMax()` | `double` |  |
| `margin_hedged` | `getMarginHedged()` | `double` |  |
| `price_change` | `getPriceChange()` | `double` |  |
| `price_volatility` | `getPriceVolatility()` | `double` |  |
| `price_theoretical` | `getPriceTheoretical()` | `double` |  |
| `price_delta` | `getPriceDelta()` | `double` |  |
| `price_theta` | `getPriceTheta()` | `double` |  |
| `price_gamma` | `getPriceGamma()` | `double` |  |
| `price_vega` | `getPriceVega()` | `double` |  |
| `price_rho` | `getPriceRho()` | `double` |  |
| `price_omega` | `getPriceOmega()` | `double` |  |
| `price_sensitivity` | `getPriceSensitivity()` | `double` |  |
| `sector` | `getSector()` | [BMT5_ENUM_SYMBOL_SECTOR](enums.md#bmt5_enum_symbol_sector) |  |
| `industry` | `getIndustry()` | [BMT5_ENUM_SYMBOL_INDUSTRY](enums.md#bmt5_enum_symbol_industry) |  |
| `custom` | `getCustom()` | `bool` |  |
| `background_color` | `getBackgroundColor()` | `string` |  |
| `chart_mode` | `getChartMode()` | [BMT5_ENUM_SYMBOL_CHART_MODE](enums.md#bmt5_enum_symbol_chart_mode) |  |
| `exist` | `getExist()` | `bool` |  |
| `select` | `getSelect()` | `bool` |  |
| `subscription_delay` | `getSubscriptionDelay()` | `int32` |  |
| `visible` | `getVisible()` | `bool` |  |
| `session_deals` | `getSessionDeals()` | `int64` |  |
| `session_buy_orders` | `getSessionBuyOrders()` | `int64` |  |
| `session_sell_orders` | `getSessionSellOrders()` | `int64` |  |
| `volume` | `getVolume()` | `int64` |  |
| `volume_high` | `getVolumeHigh()` | `int64` |  |
| `volume_low` | `getVolumeLow()` | `int64` |  |
| `time` | `getTime()` | `google.protobuf.Timestamp` |  |
| `time_utc` | `getTimeUtc()` | `google.protobuf.Timestamp` | time in UTC (the same moment; time is in the broker server's time zone). |
| `time_msc` | `getTimeMsc()` | `int64` |  |
| `time_msc_utc` | `getTimeMscUtc()` | `int64` | time_msc in UTC (the same moment; time_msc is in the broker server's time zone). |
| `digits` | `getDigits()` | `int32` |  |
| `spread_float` | `getSpreadFloat()` | `bool` |  |
| `spread` | `getSpread()` | `int32` |  |
| `ticks_book_depth` | `getTicksBookDepth()` | `int32` |  |
| `trade_calc_mode` | `getTradeCalcMode()` | [BMT5_ENUM_SYMBOL_CALC_MODE](enums.md#bmt5_enum_symbol_calc_mode) |  |
| `trade_mode` | `getTradeMode()` | [BMT5_ENUM_SYMBOL_TRADE_MODE](enums.md#bmt5_enum_symbol_trade_mode) |  |
| `start_time` | `getStartTime()` | `google.protobuf.Timestamp` |  |
| `start_time_utc` | `getStartTimeUtc()` | `google.protobuf.Timestamp` | start_time in UTC (the same moment; start_time is in the broker server's time zone). |
| `expiration_time` | `getExpirationTime()` | `google.protobuf.Timestamp` |  |
| `expiration_time_utc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | expiration_time in UTC (the same moment; expiration_time is in the broker server's time zone). |
| `trade_stops_level` | `getTradeStopsLevel()` | `int32` |  |
| `trade_freeze_level` | `getTradeFreezeLevel()` | `int32` |  |
| `trade_exe_mode` | `getTradeExeMode()` | [BMT5_ENUM_SYMBOL_TRADE_EXECUTION](enums.md#bmt5_enum_symbol_trade_execution) |  |
| `swap_mode` | `getSwapMode()` | [BMT5_ENUM_SYMBOL_SWAP_MODE](enums.md#bmt5_enum_symbol_swap_mode) |  |
| `swap_rollover_3days` | `getSwapRollover3Days()` | [BMT5_ENUM_DAY_OF_WEEK](enums.md#bmt5_enum_day_of_week) |  |
| `margin_hedged_use_leg` | `getMarginHedgedUseLeg()` | `bool` |  |
| `expiration_mode` | `getExpirationMode()` | `int32` |  |
| `filling_mode` | `getFillingModeList()` | repeated [BMT5_ENUM_ORDER_TYPE_FILLING](enums.md#bmt5_enum_order_type_filling) |  |
| `order_mode` | `getOrderMode()` | [BMT5_ENUM_ORDER_TYPE](enums.md#bmt5_enum_order_type) |  |
| `order_gtc_mode` | `getOrderGtcMode()` | [BMT5_ENUM_SYMBOL_ORDER_GTC_MODE](enums.md#bmt5_enum_symbol_order_gtc_mode) |  |
| `option_mode` | `getOptionMode()` | [BMT5_ENUM_SYMBOL_OPTION_MODE](enums.md#bmt5_enum_symbol_option_mode) |  |
| `option_right` | `getOptionRight()` | [BMT5_ENUM_SYMBOL_OPTION_RIGHT](enums.md#bmt5_enum_symbol_option_right) |  |
| `basis` | `getBasis()` | `string` |  |
| `category` | `getCategory()` | `string` |  |
| `country` | `getCountry()` | `string` |  |
| `sector_name` | `getSectorName()` | `string` |  |
| `industry_name` | `getIndustryName()` | `string` |  |
| `currency_base` | `getCurrencyBase()` | `string` |  |
| `currency_profit` | `getCurrencyProfit()` | `string` |  |
| `currency_margin` | `getCurrencyMargin()` | `string` |  |
| `bank` | `getBank()` | `string` |  |
| `sym_description` | `getSymDescription()` | `string` |  |
| `exchange` | `getExchange()` | `string` |  |
| `formula` | `getFormula()` | `string` |  |
| `isin` | `getIsin()` | `string` |  |
| `page` | `getPage()` | `string` |  |
| `path` | `getPath()` | `string` |  |

## SymbolParamsManyData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_infos` | `getSymbolInfosList()` | repeated [SymbolParameters](messages.md#symbolparameters) |  |
| `symbols_total` | `getSymbolsTotal()` | `int32` |  |
| `page_number` | `getPageNumber()` | `int32` |  |
| `items_per_page` | `getItemsPerPage()` | `int32` |  |

## SymbolSelectData

| Field | Java | Type | Description |
|---|---|---|---|
| `success` | `getSuccess()` | `bool` | In case of failure returns false |

## SymbolsTotalData

| Field | Java | Type | Description |
|---|---|---|---|
| `total` | `getTotal()` | `int32` | If the 'selected' parameter is true, the function returns the number of symbols selected in MarketWatch. If the value is false, it returns the total number of all symbols. |

## TerminalHealthCheck

| Field | Java | Type | Description |
|---|---|---|---|
| `is_alive` | `getIsAlive()` | `bool` | Connection is alive |
| `ErrorMessage` | `getErrorMessage()` | `google.protobuf.StringValue` | Error message |
| `ErrorCode` | `getErrorCode()` | `google.protobuf.StringValue` | Error code |
| `StackTrace` | `getStackTrace()` | `google.protobuf.StringValue` | Stack trace |
| `has_authorization_error` | `getHasAuthorizationError()` | `bool` |  |
| `api_is_alive` | `getApiIsAlive()` | `bool` |  |
| `terminal_is_connected_to_mt_server` | `getTerminalIsConnectedToMtServer()` | `bool` |  |

## TerminalJournalRow

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `google.protobuf.Timestamp` | Timestamp of the log entry |
| `source` | `getSource()` | `string` | Source of the log entry (e.g. "Network", "MQL5", "Tester", EA name) |
| `message` | `getMessage()` | `string` | Log message text |

## TickSizeSymbol

| Field | Java | Type | Description |
|---|---|---|---|
| `Index` | `getIndex()` | `int32` |  |
| `Name` | `getName()` | `string` |  |
| `TradeTickValue` | `getTradeTickValue()` | `double` |  |
| `TradeTickValueProfit` | `getTradeTickValueProfit()` | `double` |  |
| `TradeTickValueLoss` | `getTradeTickValueLoss()` | `double` |  |
| `TradeTickSize` | `getTradeTickSize()` | `double` |  |
| `TradeContractSize` | `getTradeContractSize()` | `double` |  |

## TickValueWithSizeData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_tick_size_infos` | `getSymbolTickSizeInfosList()` | repeated [TickSizeSymbol](messages.md#ticksizesymbol) |  |

## TradeHistoryItemData

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `int64` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `is_buy` | `getIsBuy()` | `bool` |  |
| `lots` | `getLots()` | `double` |  |
| `open_price` | `getOpenPrice()` | `double` |  |
| `close_price` | `getClosePrice()` | `double` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` |  |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `close_time` | `getCloseTime()` | `google.protobuf.Timestamp` |  |
| `close_time_utc` | `getCloseTimeUtc()` | `google.protobuf.Timestamp` | close_time in UTC (the same moment; close_time is in the broker server's time zone). |
| `profit` | `getProfit()` | `double` |  |
| `commission` | `getCommission()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `comment` | `getComment()` | `string` |  |

## TradeSummaryData

| Field | Java | Type | Description |
|---|---|---|---|
| `open_trades` | `getOpenTrades()` | `int32` |  |
| `open_profit` | `getOpenProfit()` | `double` |  |
| `day_profit` | `getDayProfit()` | `double` |  |
| `week_profit` | `getWeekProfit()` | `double` |  |
| `month_profit` | `getMonthProfit()` | `double` |  |
| `total_profit` | `getTotalProfit()` | `double` |  |

## TradeUnrealizedExtremaData

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `int64` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `max_unrealized_time` | `getMaxUnrealizedTime()` | `google.protobuf.Timestamp` |  |
| `max_unrealized_time_utc` | `getMaxUnrealizedTimeUtc()` | `google.protobuf.Timestamp` | max_unrealized_time in UTC (the same moment; max_unrealized_time is in the broker server's time zone). |
| `max_unrealized_pl` | `getMaxUnrealizedPl()` | `double` |  |
| `min_unrealized_time` | `getMinUnrealizedTime()` | `google.protobuf.Timestamp` |  |
| `min_unrealized_time_utc` | `getMinUnrealizedTimeUtc()` | `google.protobuf.Timestamp` | min_unrealized_time in UTC (the same moment; min_unrealized_time is in the broker server's time zone). |
| `min_unrealized_pl` | `getMinUnrealizedPl()` | `double` |  |

## TradeUnrealizedPLData

| Field | Java | Type | Description |
|---|---|---|---|
| `extrema` | `getExtremaList()` | repeated [TradeUnrealizedExtremaData](messages.md#tradeunrealizedextremadata) |  |

## WonData

| Field | Java | Type | Description |
|---|---|---|---|
| `won_count` | `getWonCount()` | `int32` |  |
| `all` | `getAll()` | `int32` |  |
| `won_persent` | `getWonPersent()` | `double` |  |

## ZScoreData

| Field | Java | Type | Description |
|---|---|---|---|
| `z_score_decimal` | `getZScoreDecimal()` | `double` |  |
| `z_score_probability` | `getZScoreProbability()` | `double` |  |
