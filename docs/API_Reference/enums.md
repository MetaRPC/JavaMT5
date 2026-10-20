# Enums

The enums of the [API methods](methods.md) (package `mt5_term_api`), generated from the protos. JSON (REST) uses the
value names; gRPC the numbers.

## AH_ENUM_POSITIONS_HISTORY_ORDER_TYPE

| Value | Number | Description |
|---|---|---|
| `AH_ORDER_TYPE_BUY` | 0 | Market Buy order |
| `AH_ORDER_TYPE_SELL` | 1 | Market Sell order |
| `AH_ORDER_TYPE_BUY_LIMIT` | 2 | Buy Limit pending order |
| `AH_ORDER_TYPE_SELL_LIMIT` | 3 | Sell Limit pending order |
| `AH_ORDER_TYPE_BUY_STOP` | 4 | Buy Stop pending order |
| `AH_ORDER_TYPE_SELL_STOP` | 5 | Sell Stop pending order |
| `AH_ORDER_TYPE_BUY_STOP_LIMIT` | 6 | Upon reaching the order price, a pending Buy Limit order is placed at the StopLimit price |
| `AH_ORDER_TYPE_SELL_STOP_LIMIT` | 7 | Upon reaching the order price, a pending Sell Limit order is placed at the StopLimit price |
| `AH_ORDER_TYPE_CLOSE_BY` | 8 | Order to close a position by an opposite one |

## AH_ENUM_POSITIONS_HISTORY_SORT_TYPE

| Value | Number | Description |
|---|---|---|
| `AH_POSITION_OPEN_TIME_ASC` | 0 |  |
| `AH_POSITION_OPEN_TIME_DESC` | 1 |  |
| `AH_POSITION_TICKET_ASC` | 2 |  |
| `AH_POSITION_TICKET_DESC` | 3 |  |

## AH_SYMBOL_PARAMS_MANY_SORT_TYPE

| Value | Number | Description |
|---|---|---|
| `AH_PARAMS_MANY_SORT_TYPE_SYMBOL_NAME_ASC` | 0 |  |
| `AH_PARAMS_MANY_SORT_TYPE_SYMBOL_NAME_DESC` | 1 |  |
| `AH_PARAMS_MANY_SORT_TYPE_MQL_INDEX_ASC` | 2 |  |
| `AH_PARAMS_MANY_SORT_TYPE_MQL_INDEX_DESC` | 3 |  |

## AccountInfoDoublePropertyType

| Value | Number | Description |
|---|---|---|
| `ACCOUNT_BALANCE` | 0 | Account balance in the deposit currency |
| `ACCOUNT_CREDIT` | 1 | Account credit in the deposit currency |
| `ACCOUNT_PROFIT` | 2 | Current profit of an account in the deposit currency |
| `ACCOUNT_EQUITY` | 3 | Account equity in the deposit currency |
| `ACCOUNT_MARGIN` | 4 | Account margin used in the deposit currency |
| `ACCOUNT_MARGIN_FREE` | 5 | Free margin of an account in the deposit currency |
| `ACCOUNT_MARGIN_LEVEL` | 6 | Account margin level in percents |
| `ACCOUNT_MARGIN_SO_CALL` | 7 | Margin call level. Depending on the set ACCOUNT_MARGIN_SO_MODE is expressed in percents or in the deposit currency |
| `ACCOUNT_MARGIN_SO_SO` | 8 | Margin stop out level. Depending on the set ACCOUNT_MARGIN_SO_MODE is expressed in percents or in the deposit currency |
| `ACCOUNT_MARGIN_INITIAL` | 9 | Initial margin. The amount reserved on an account to cover the margin of all pending orders |
| `ACCOUNT_MARGIN_MAINTENANCE` | 10 | Maintenance margin. The minimum equity reserved on an account to cover the minimum amount of all open positions |
| `ACCOUNT_ASSETS` | 11 | The current assets of an account |
| `ACCOUNT_LIABILITIES` | 12 | The current liabilities on an account |
| `ACCOUNT_COMMISSION_BLOCKED` | 13 | The current blocked commission amount on an account |

## AccountInfoIntegerPropertyType

| Value | Number | Description |
|---|---|---|
| `ACCOUNT_LOGIN` | 0 | Account number - returned type long |
| `ACCOUNT_TRADE_MODE` | 1 | Account trade mode - returned type ENUM_ACCOUNT_TRADE_MODE |
| `ACCOUNT_LEVERAGE` | 2 | Account leverage - long |
| `ACCOUNT_LIMIT_ORDERS` | 3 | Maximum allowed number of open positions and active pending orders (in total), 0  unlimited - returned type int |
| `ACCOUNT_MARGIN_SO_MODE` | 4 | Mode for setting the minimal allowed margin - returned type ENUM_ACCOUNT_STOP_OUT_MODE |
| `ACCOUNT_TRADE_ALLOWED` | 5 | Allowed trade for the current account - returned type bool (1 or 0) |
| `ACCOUNT_TRADE_EXPERT` | 6 | Allowed trade for an Expert Advisor - return type bool (1 or 0) |
| `ACCOUNT_MARGIN_MODE` | 7 | Margin calculation mode - return type ENUM_ACCOUNT_MARGIN_MODE |
| `ACCOUNT_CURRENCY_DIGITS` | 8 | The number of decimal places in the account currency, which are required for an accurate display of trading results - returned type int |
| `ACCOUNT_FIFO_CLOSE` | 9 | An indication showing that positions can only be closed by FIFO rule. If the property value is set to true, then each symbol positions will be closed in the same order, in which they are opened, starting with the oldest one. In case of an attempt to close positions in a different order, the trader will receive an appropriate error.  - return type bool (1 or 0) |
| `ACCOUNT_HEDGE_ALLOWED` | 10 | Allowed opposite positions on a single symbol - return type bool (1 or 0) |

## AccountInfoStringPropertyType

| Value | Number | Description |
|---|---|---|
| `ACCOUNT_NAME` | 0 | Client name |
| `ACCOUNT_SERVER` | 1 | Trade server name |
| `ACCOUNT_CURRENCY` | 2 | Account currency |
| `ACCOUNT_COMPANY` | 3 | Name of a company that serves the account |

## BMT5_ENUM_DAY_OF_WEEK

| Value | Number | Description |
|---|---|---|
| `BMT5_SUNDAY` | 0 | Sunday |
| `BMT5_MONDAY` | 1 | Monday |
| `BMT5_TUESDAY` | 2 | Tuesday |
| `BMT5_WEDNESDAY` | 3 | Wednesday |
| `BMT5_THURSDAY` | 4 | Thursday |
| `BMT5_FRIDAY` | 5 | Friday |
| `BMT5_SATURDAY` | 6 | Saturday |

## BMT5_ENUM_DEAL_ENTRY_TYPE

| Value | Number | Description |
|---|---|---|
| `BMT5_DEAL_ENTRY_IN` | 0 |  |
| `BMT5_DEAL_ENTRY_OUT` | 1 |  |
| `BMT5_DEAL_ENTRY_INOUT` | 2 |  |
| `BMT5_DEAL_ENTRY_OUT_BY` | 3 |  |

## BMT5_ENUM_DEAL_REASON

| Value | Number | Description |
|---|---|---|
| `BMT5_DEAL_REASON_CLIENT` | 0 | The deal was executed as a result of activation of an order placed from a desktop terminal |
| `BMT5_DEAL_REASON_MOBILE` | 1 | The deal was executed as a result of activation of an order placed from a mobile application |
| `BMT5_DEAL_REASON_WEB` | 2 | The deal was executed as a result of activation of an order placed from the web platform |
| `BMT5_DEAL_REASON_EXPERT` | 3 | The deal was executed as a result of activation of an order placed from an MQL5 program, i.e. an Expert Advisor or a script |
| `BMT5_DEAL_REASON_SL` | 4 | The deal was executed as a result of Stop Loss activation |
| `BMT5_DEAL_REASON_TP` | 5 | The deal was executed as a result of Take Profit activation |
| `BMT5_DEAL_REASON_SO` | 6 | The deal was executed as a result of the Stop Out event |
| `BMT5_DEAL_REASON_ROLLOVER` | 7 | The deal was executed due to a rollover |
| `BMT5_DEAL_REASON_VMARGIN` | 8 | The deal was executed after charging the variation margin |
| `BMT5_DEAL_REASON_SPLIT` | 9 | The deal was executed after the split (price reduction) of an instrument, which had an open position during split announcement |
| `BMT5_DEAL_REASON_CORPORATE_ACTION` | 10 | The deal was executed as a result of a corporate action: merging or renaming a security, transferring a client to another account, etc. |

## BMT5_ENUM_DEAL_TYPE

| Value | Number | Description |
|---|---|---|
| `BMT5_DEAL_TYPE_BUY` | 0 | Buy |
| `BMT5_DEAL_TYPE_SELL` | 1 | Sell |
| `BMT5_DEAL_TYPE_BALANCE` | 2 | Balance |
| `BMT5_DEAL_TYPE_CREDIT` | 3 | Credit |
| `BMT5_DEAL_TYPE_CHARGE` | 4 | Additional charge |
| `BMT5_DEAL_TYPE_CORRECTION` | 5 | Correction |
| `BMT5_DEAL_TYPE_BONUS` | 6 | Bonus |
| `BMT5_DEAL_TYPE_COMMISSION` | 7 | Additional commission |
| `BMT5_DEAL_TYPE_COMMISSION_DAILY` | 8 | Daily commission |
| `BMT5_DEAL_TYPE_COMMISSION_MONTHLY` | 9 | Monthly commission |
| `BMT5_DEAL_TYPE_COMMISSION_AGENT_DAILY` | 10 | Daily agent commission |
| `BMT5_DEAL_TYPE_COMMISSION_AGENT_MONTHLY` | 11 | Monthly agent commission |
| `BMT5_DEAL_TYPE_INTEREST` | 12 | Interest rate |
| `BMT5_DEAL_TYPE_BUY_CANCELED` | 13 | Canceled buy deal |
| `BMT5_DEAL_TYPE_SELL_CANCELED` | 14 | Canceled sell deal |
| `BMT5_DEAL_DIVIDEND` | 15 | Dividend operations |
| `BMT5_DEAL_DIVIDEND_FRANKED` | 16 | Franked (non-taxable) dividend operations |
| `BMT5_DEAL_TAX` | 17 | Tax charges |

## BMT5_ENUM_OPENED_ORDER_SORT_TYPE

| Value | Number | Description |
|---|---|---|
| `BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_ASC` | 0 |  |
| `BMT5_OPENED_ORDER_SORT_BY_OPEN_TIME_DESC` | 1 |  |
| `BMT5_OPENED_ORDER_SORT_BY_ORDER_TICKET_ID_ASC` | 2 |  |
| `BMT5_OPENED_ORDER_SORT_BY_ORDER_TICKET_ID_DESC` | 3 |  |

## BMT5_ENUM_ORDER_HISTORY_SORT_TYPE

| Value | Number | Description |
|---|---|---|
| `BMT5_SORT_BY_OPEN_TIME_ASC` | 0 |  |
| `BMT5_SORT_BY_OPEN_TIME_DESC` | 1 |  |
| `BMT5_SORT_BY_CLOSE_TIME_ASC` | 2 |  |
| `BMT5_SORT_BY_CLOSE_TIME_DESC` | 3 |  |
| `BMT5_SORT_BY_ORDER_TICKET_ID_ASC` | 4 |  |
| `BMT5_SORT_BY_ORDER_TICKET_ID_DESC` | 5 |  |

## BMT5_ENUM_ORDER_STATE

| Value | Number | Description |
|---|---|---|
| `BMT5_ORDER_STATE_STARTED` | 0 | Order checked, but not yet accepted by broker |
| `BMT5_ORDER_STATE_PLACED` | 1 | Order accepted |
| `BMT5_ORDER_STATE_CANCELED` | 2 | Order canceled by client |
| `BMT5_ORDER_STATE_PARTIAL` | 3 | Order partially executed |
| `BMT5_ORDER_STATE_FILLED` | 4 | Order fully executed |
| `BMT5_ORDER_STATE_REJECTED` | 5 | Order rejected |
| `BMT5_ORDER_STATE_EXPIRED` | 6 | Order expired |
| `BMT5_ORDER_STATE_REQUEST_ADD` | 7 | Order is being registered (placing to the trading system) |
| `BMT5_ORDER_STATE_REQUEST_MODIFY` | 8 | Order is being modified (changing its parameters) |
| `BMT5_ORDER_STATE_REQUEST_CANCEL` | 9 | Order is being deleted (deleting from the trading system) |

## BMT5_ENUM_ORDER_TYPE

| Value | Number | Description |
|---|---|---|
| `BMT5_ORDER_TYPE_BUY` | 0 | Market Buy order |
| `BMT5_ORDER_TYPE_SELL` | 1 | Market Sell order |
| `BMT5_ORDER_TYPE_BUY_LIMIT` | 2 | Buy Limit pending order |
| `BMT5_ORDER_TYPE_SELL_LIMIT` | 3 | Sell Limit pending order |
| `BMT5_ORDER_TYPE_BUY_STOP` | 4 | Buy Stop pending order |
| `BMT5_ORDER_TYPE_SELL_STOP` | 5 | Sell Stop pending order |
| `BMT5_ORDER_TYPE_BUY_STOP_LIMIT` | 6 | Upon reaching the order price, a pending Buy Limit order is placed at the StopLimit price |
| `BMT5_ORDER_TYPE_SELL_STOP_LIMIT` | 7 | Upon reaching the order price, a pending Sell Limit order is placed at the StopLimit price |
| `BMT5_ORDER_TYPE_CLOSE_BY` | 8 | Order to close a position by an opposite one |

## BMT5_ENUM_ORDER_TYPE_FILLING

| Value | Number | Description |
|---|---|---|
| `BMT5_ORDER_FILLING_FOK` | 0 | An order can be executed in the specified volume only. If the necessary amount of a financial instrument is currently unavailable in the market, the order will not be executed. The desired volume can be made up of several available offers. The possibility of using FOK orders is determined at the trade server. |
| `BMT5_ORDER_FILLING_IOC` | 1 | A trader agrees to execute a deal with the volume maximally available in the market within that indicated in the order. If the request cannot be filled completely, an order with the available volume will be executed, and the remaining volume will be canceled. The possibility of using IOC orders is determined at the trade server. |
| `BMT5_ORDER_FILLING_BOC` | 3 | The BoC order assumes that the order can only be placed in the Depth of Market and cannot be immediately executed. If the order can be executed immediately when placed, then it is canceled. In fact, the BOC policy guarantees that the price of the placed order will be worse than the current market. BoC orders are used to implement passive trading, so that the order is not executed immediately when placed and does not affect current liquidity. Only limit and stop limit orders are supported (ORDER_TYPE_BUY_LIMIT, ORDER_TYPE_SELL_LIMIT, ORDER_TYPE_BUY_STOP_LIMIT, ORDER_TYPE_SELL_STOP_LIMIT). |
| `BMT5_ORDER_FILLING_RETURN` | 2 | In case of partial filling, an order with remaining volume is not canceled but processed further. Return orders are not allowed in the Market Execution mode (market execution — SYMBOL_TRADE_EXECUTION_MARKET). |

## BMT5_ENUM_ORDER_TYPE_TIME

| Value | Number | Description |
|---|---|---|
| `BMT5_ORDER_TIME_GTC` | 0 | Good till cancel order |
| `BMT5_ORDER_TIME_DAY` | 1 | Good till current trade day order |
| `BMT5_ORDER_TIME_SPECIFIED` | 2 | Good till expired order |
| `BMT5_ORDER_TIME_SPECIFIED_DAY` | 3 | The order will be effective till 23:59:59 of the specified day. If this time is outside a trading session, the order expires in the nearest trading time. |

## BMT5_ENUM_POSITION_REASON

| Value | Number | Description |
|---|---|---|
| `BMT5_POSITION_REASON_CLIENT` | 0 |  |
| `BMT5_POSITION_REASON_MOBILE` | 1 |  |
| `BMT5_POSITION_REASON_WEB` | 2 |  |
| `BMT5_POSITION_REASON_EXPERT` | 3 |  |
| `ORDER_REASON_SL` | 4 |  |
| `ORDER_REASON_TP` | 5 |  |
| `ORDER_REASON_SO` | 6 |  |

## BMT5_ENUM_POSITION_TYPE

| Value | Number | Description |
|---|---|---|
| `BMT5_POSITION_TYPE_BUY` | 0 |  |
| `BMT5_POSITION_TYPE_SELL` | 1 |  |

## BMT5_ENUM_SYMBOL_CALC_MODE

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_CALC_MODE_FOREX` | 0 | Forex mode - calculation of profit and margin for Forex // Margin:  Lots * Contract_Size / Leverage * Margin_Rate  // Profit:  (close_price - open_price) * Contract_Size * Lots |
| `BMT5_SYMBOL_CALC_MODE_FOREX_NO_LEVERAGE` | 1 | Forex No Leverage mode – calculation of profit and margin for Forex symbols without taking into account the leverage // Margin:  Lots * Contract_Size * Margin_Rate // Profit:  (close_price - open_price) * Contract_Size * Lots |
| `BMT5_SYMBOL_CALC_MODE_FUTURES` | 2 | Futures mode - calculation of margin and profit for futures   // Margin: Lots * InitialMargin * Margin_Rate  // Profit:  (close_price - open_price) * TickPrice / TickSize * Lots |
| `BMT5_SYMBOL_CALC_MODE_CFD` | 3 | CFD mode - calculation of margin and profit for CFD    // Margin: Lots * ContractSize * MarketPrice * Margin_Rate  // Profit:  (close_price - open_price) * Contract_Size * Lots |
| `BMT5_SYMBOL_CALC_MODE_CFDINDEX` | 4 | CFD index mode - calculation of margin and profit for CFD by indexes    // Margin: (Lots * ContractSize * MarketPrice) * TickPrice / TickSize * Margin_Rate  // Profit:  (close_price - open_price) * Contract_Size * Lots |
| `BMT5_SYMBOL_CALC_MODE_CFDLEVERAGE` | 5 | CFD Leverage mode - calculation of margin and profit for CFD at leverage trading   // Margin: (Lots * ContractSize * MarketPrice) / Leverage * Margin_Rate  // Profit:  (close_price - open_price) * Contract_Size * Lots |
| `BMT5_SYMBOL_CALC_MODE_EXCH_STOCKS` | 6 | Exchange mode – calculation of margin and profit for trading securities on a stock exchange  // Margin: Lots * ContractSize * LastPrice * Margin_Rate  // Profit:  (close_price - open_price) * Contract_Size * Lots |
| `BMT5_SYMBOL_CALC_MODE_EXCH_FUTURES` | 7 | Futures mode – calculation of margin and profit for trading futures contracts on a stock exchange // Margin: Lots * InitialMargin * Margin_Rate or Lots * MaintenanceMargin * Margin_Rate // Profit:  (close_price - open_price) * Lots * TickPrice / TickSize |
| `BMT5_SYMBOL_CALC_MODE_EXCH_FUTURES_FORTS` | 8 | FORTS Futures mode – calculation of margin and profit for trading futures contracts on FORTS. The margin may be reduced by the amount of MarginDiscount deviation according to the following rules:  // 1. If the price of a long position (buy order) is less than the estimated price, MarginDiscount = Lots * ((PriceSettle - PriceOrder) * TickPrice / TickSize) // 2. If the price of a short position (sell order) exceeds the estimated price, MarginDiscount = Lots * ((PriceOrder - PriceSettle) * TickPrice / TickSize) // where: // PriceSettle – estimated (clearing) price of the previous session; // PriceOrder – average weighted position price or open price set in the order (request); // TickPrice – tick price (cost of the price change by one point) // TickSize – tick size (minimum price change step) // Margin: Lots * InitialMargin * Margin_Rate or Lots * MaintenanceMargin * Margin_Rate // Profit:  (close_price - open_price) * Lots * TickPrice / TickSize |
| `BMT5_SYMBOL_CALC_MODE_EXCH_BONDS` | 9 | Exchange Bonds mode – calculation of margin and profit for trading bonds on a stock exchange  // Margin: Lots * ContractSize * FaceValue * open_price / 100  // Profit:  Lots * close_price * FaceValue * Contract_Size + AccruedInterest * Lots * ContractSize |
| `BMT5_SYMBOL_CALC_MODE_EXCH_STOCKS_MOEX` | 10 | Exchange MOEX Stocks mode – calculation of margin and profit for trading securities on MOEX  // Margin: Lots * ContractSize * LastPrice * Margin_Rate  // Profit:  (close_price - open_price) * Contract_Size * Lots |
| `BMT5_SYMBOL_CALC_MODE_EXCH_BONDS_MOEX` | 11 | Exchange MOEX Bonds mode – calculation of margin and profit for trading bonds on MOEX  // Margin: Lots * ContractSize * FaceValue * open_price / 100 // Profit:  Lots * close_price * FaceValue * Contract_Size + AccruedInterest * Lots * ContractSize |
| `BMT5_SYMBOL_CALC_MODE_SERV_COLLATERAL` | 12 | Collateral mode - a symbol is used as a non-tradable asset on a trading account. The market value of an open position is calculated based on the volume, current market price, contract size and liquidity ratio. The value is included into Assets, which are added to Equity. Open positions of such symbols increase the Free Margin amount and are used as additional margin (collateral) for open positions of tradable instruments. // Margin: no  // Profit: no  // Market Value: Lots * ContractSize * MarketPrice * LiquidityRate |

## BMT5_ENUM_SYMBOL_CHART_MODE

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_CHART_MODE_BID` | 0 |  |
| `BMT5_SYMBOL_CHART_MODE_LAST` | 1 |  |

## BMT5_ENUM_SYMBOL_INDUSTRY

| Value | Number | Description |
|---|---|---|
| `BMT5_INDUSTRY_UNDEFINED` | 0 | Undefined |
| `BMT5_INDUSTRY_AGRICULTURAL_INPUTS` | 1 | Agricultural inputs |
| `BMT5_INDUSTRY_ALUMINIUM` | 2 | Aluminium |
| `BMT5_INDUSTRY_BUILDING_MATERIALS` | 3 | Building materials |
| `BMT5_INDUSTRY_CHEMICALS` | 4 | Chemicals |
| `BMT5_INDUSTRY_COKING_COAL` | 5 | Coking coal |
| `BMT5_INDUSTRY_COPPER` | 6 | Copper |
| `BMT5_INDUSTRY_GOLD` | 7 | Gold |
| `BMT5_INDUSTRY_LUMBER_WOOD` | 8 | Lumber and wood production |
| `BMT5_INDUSTRY_INDUSTRIAL_METALS` | 9 | Other industrial metals and mining |
| `BMT5_INDUSTRY_PRECIOUS_METALS` | 10 | Other precious metals and mining |
| `BMT5_INDUSTRY_PAPER` | 11 | Paper and paper products |
| `BMT5_INDUSTRY_SILVER` | 12 | Silver |
| `BMT5_INDUSTRY_SPECIALTY_CHEMICALS` | 13 | Specialty chemicals |
| `BMT5_INDUSTRY_STEEL` | 14 | Steel |
| `BMT5_INDUSTRY_ADVERTISING` | 15 | Advertising agencies |
| `BMT5_INDUSTRY_BROADCASTING` | 16 | Broadcasting |
| `BMT5_INDUSTRY_GAMING_MULTIMEDIA` | 17 | Electronic gaming and multimedia |
| `BMT5_INDUSTRY_ENTERTAINMENT` | 18 | Entertainment |
| `BMT5_INDUSTRY_INTERNET_CONTENT` | 19 | Internet content and information |
| `BMT5_INDUSTRY_PUBLISHING` | 20 | Publishing |
| `BMT5_INDUSTRY_TELECOM` | 21 | Telecom services |
| `BMT5_INDUSTRY_APPAREL_MANUFACTURING` | 22 | Apparel manufacturing |
| `BMT5_INDUSTRY_APPAREL_RETAIL` | 23 | Apparel retail |
| `BMT5_INDUSTRY_AUTO_MANUFACTURERS` | 24 | Auto manufacturers |
| `BMT5_INDUSTRY_AUTO_PARTS` | 25 | Auto parts |
| `BMT5_INDUSTRY_AUTO_DEALERSHIP` | 26 | Auto and truck dealerships |
| `BMT5_INDUSTRY_DEPARTMENT_STORES` | 27 | Department stores |
| `BMT5_INDUSTRY_FOOTWEAR_ACCESSORIES` | 28 | Footwear and accessories |
| `BMT5_INDUSTRY_FURNISHINGS` | 29 | Furnishing, fixtures and appliances |
| `BMT5_INDUSTRY_GAMBLING` | 30 | Gambling |
| `BMT5_INDUSTRY_HOME_IMPROV_RETAIL` | 31 | Home improvement retail |
| `BMT5_INDUSTRY_INTERNET_RETAIL` | 32 | Internet retail |
| `BMT5_INDUSTRY_LEISURE` | 33 | Leisure |
| `BMT5_INDUSTRY_LODGING` | 34 | Lodging |
| `BMT5_INDUSTRY_LUXURY_GOODS` | 35 | Luxury goods |
| `BMT5_INDUSTRY_PACKAGING_CONTAINERS` | 36 | Packaging and containers |
| `BMT5_INDUSTRY_PERSONAL_SERVICES` | 37 | Personal services |
| `BMT5_INDUSTRY_RECREATIONAL_VEHICLES` | 38 | Recreational vehicles |
| `BMT5_INDUSTRY_RESIDENT_CONSTRUCTION` | 39 | Residential construction |
| `BMT5_INDUSTRY_RESORTS_CASINOS` | 40 | Resorts and casinos |
| `BMT5_INDUSTRY_RESTAURANTS` | 41 | Restaurants |
| `BMT5_INDUSTRY_SPECIALTY_RETAIL` | 42 | Specialty retail |
| `BMT5_INDUSTRY_TEXTILE_MANUFACTURING` | 43 | Textile manufacturing |
| `BMT5_INDUSTRY_TRAVEL_SERVICES` | 44 | Travel services |
| `BMT5_INDUSTRY_BEVERAGES_BREWERS` | 45 | Beverages - Brewers |
| `BMT5_INDUSTRY_BEVERAGES_NON_ALCO` | 46 | Beverages - Non-alcoholic |
| `BMT5_INDUSTRY_BEVERAGES_WINERIES` | 47 | Beverages - Wineries and distilleries |
| `BMT5_INDUSTRY_CONFECTIONERS` | 48 | Confectioners |
| `BMT5_INDUSTRY_DISCOUNT_STORES` | 49 | Discount stores |
| `BMT5_INDUSTRY_EDUCATION_TRAINIG` | 50 | Education and training services |
| `BMT5_INDUSTRY_FARM_PRODUCTS` | 51 | Farm products |
| `BMT5_INDUSTRY_FOOD_DISTRIBUTION` | 52 | Food distribution |
| `BMT5_INDUSTRY_GROCERY_STORES` | 53 | Grocery stores |
| `BMT5_INDUSTRY_HOUSEHOLD_PRODUCTS` | 54 | Household and personal products |
| `BMT5_INDUSTRY_PACKAGED_FOODS` | 55 | Packaged foods |
| `BMT5_INDUSTRY_TOBACCO` | 56 | Tobacco |
| `BMT5_INDUSTRY_OIL_GAS_DRILLING` | 57 | Oil and gas drilling |
| `BMT5_INDUSTRY_OIL_GAS_EP` | 58 | Oil and gas extraction and processing |
| `BMT5_INDUSTRY_OIL_GAS_EQUIPMENT` | 59 | Oil and gas equipment and services |
| `BMT5_INDUSTRY_OIL_GAS_INTEGRATED` | 60 | Oil and gas integrated |
| `BMT5_INDUSTRY_OIL_GAS_MIDSTREAM` | 61 | Oil and gas midstream |
| `BMT5_INDUSTRY_OIL_GAS_REFINING` | 62 | Oil and gas refining and marketing |
| `BMT5_INDUSTRY_THERMAL_COAL` | 63 | Thermal coal |
| `BMT5_INDUSTRY_URANIUM` | 64 | Uranium |
| `BMT5_INDUSTRY_EXCHANGE_TRADED_FUND` | 65 | Exchange traded fund |
| `BMT5_INDUSTRY_ASSETS_MANAGEMENT` | 66 | Assets management |
| `BMT5_INDUSTRY_BANKS_DIVERSIFIED` | 67 | Banks - Diversified |
| `BMT5_INDUSTRY_BANKS_REGIONAL` | 68 | Banks - Regional |
| `BMT5_INDUSTRY_CAPITAL_MARKETS` | 69 | Capital markets |
| `BMT5_INDUSTRY_CLOSE_END_FUND_DEBT` | 70 | Closed-End fund - Debt |
| `BMT5_INDUSTRY_CLOSE_END_FUND_EQUITY` | 71 | Closed-end fund - Equity |
| `BMT5_INDUSTRY_CLOSE_END_FUND_FOREIGN` | 72 | Closed-end fund - Foreign |
| `BMT5_INDUSTRY_CREDIT_SERVICES` | 73 | Credit services |
| `BMT5_INDUSTRY_FINANCIAL_CONGLOMERATE` | 74 | Financial conglomerates |
| `BMT5_INDUSTRY_FINANCIAL_DATA_EXCHANGE` | 75 | Financial data and stock exchange |
| `BMT5_INDUSTRY_INSURANCE_BROKERS` | 76 | Insurance brokers |
| `BMT5_INDUSTRY_INSURANCE_DIVERSIFIED` | 77 | Insurance - Diversified |
| `BMT5_INDUSTRY_INSURANCE_LIFE` | 78 | Insurance - Life |
| `BMT5_INDUSTRY_INSURANCE_PROPERTY` | 79 | Insurance - Property and casualty |
| `BMT5_INDUSTRY_INSURANCE_REINSURANCE` | 80 | Insurance - Reinsurance |
| `BMT5_INDUSTRY_INSURANCE_SPECIALTY` | 81 | Insurance - Specialty |
| `BMT5_INDUSTRY_MORTGAGE_FINANCE` | 82 | Mortgage finance |
| `BMT5_INDUSTRY_SHELL_COMPANIES` | 83 | Shell companies |
| `BMT5_INDUSTRY_BIOTECHNOLOGY` | 84 | Biotechnology |
| `BMT5_INDUSTRY_DIAGNOSTICS_RESEARCH` | 85 | Diagnostics and research |
| `BMT5_INDUSTRY_DRUGS_MANUFACTURERS` | 86 | Drugs manufacturers - general |
| `BMT5_INDUSTRY_DRUGS_MANUFACTURERS_SPEC` | 87 | Drugs manufacturers - Specialty and generic |
| `BMT5_INDUSTRY_HEALTHCARE_PLANS` | 88 | Healthcare plans |
| `BMT5_INDUSTRY_HEALTH_INFORMATION` | 89 | Health information services |
| `BMT5_INDUSTRY_MEDICAL_FACILITIES` | 90 | Medical care facilities |
| `BMT5_INDUSTRY_MEDICAL_DEVICES` | 91 | Medical devices |
| `BMT5_INDUSTRY_MEDICAL_DISTRIBUTION` | 92 | Medical distribution |
| `BMT5_INDUSTRY_MEDICAL_INSTRUMENTS` | 93 | Medical instruments and supplies |
| `BMT5_INDUSTRY_PHARM_RETAILERS` | 94 | Pharmaceutical retailers |
| `BMT5_INDUSTRY_AEROSPACE_DEFENSE` | 95 | Aerospace and defense |
| `BMT5_INDUSTRY_AIRLINES` | 96 | Airlines |
| `BMT5_INDUSTRY_AIRPORTS_SERVICES` | 97 | Airports and air services |
| `BMT5_INDUSTRY_BUILDING_PRODUCTS` | 98 | Building products and equipment |
| `BMT5_INDUSTRY_BUSINESS_EQUIPMENT` | 99 | Business equipment and supplies |
| `BMT5_INDUSTRY_CONGLOMERATES` | 100 | Conglomerates |
| `BMT5_INDUSTRY_CONSULTING_SERVICES` | 101 | Consulting services |
| `BMT5_INDUSTRY_ELECTRICAL_EQUIPMENT` | 102 | Electrical equipment and parts |
| `BMT5_INDUSTRY_ENGINEERING_CONSTRUCTION` | 103 | Engineering and construction |
| `BMT5_INDUSTRY_FARM_HEAVY_MACHINERY` | 104 | Farm and heavy construction machinery |
| `BMT5_INDUSTRY_INDUSTRIAL_DISTRIBUTION` | 105 | Industrial distribution |
| `BMT5_INDUSTRY_INFRASTRUCTURE_OPERATIONS` | 106 | Infrastructure operations |
| `BMT5_INDUSTRY_FREIGHT_LOGISTICS` | 107 | Integrated freight and logistics |
| `BMT5_INDUSTRY_MARINE_SHIPPING` | 108 | Marine shipping |
| `BMT5_INDUSTRY_METAL_FABRICATION` | 109 | Metal fabrication |
| `BMT5_INDUSTRY_POLLUTION_CONTROL` | 110 | Pollution and treatment controls |
| `BMT5_INDUSTRY_RAILROADS` | 111 | Railroads |
| `BMT5_INDUSTRY_RENTAL_LEASING` | 112 | Rental and leasing services |
| `BMT5_INDUSTRY_SECURITY_PROTECTION` | 113 | Security and protection services |
| `BMT5_INDUSTRY_SPEALITY_BUSINESS_SERVICES` | 114 | Specialty business services |
| `BMT5_INDUSTRY_SPEALITY_MACHINERY` | 115 | Specialty industrial machinery |
| `BMT5_INDUSTRY_STUFFING_EMPLOYMENT` | 116 | Staffing and employment services |
| `BMT5_INDUSTRY_TOOLS_ACCESSORIES` | 117 | Tools and accessories |
| `BMT5_INDUSTRY_TRUCKING` | 118 | Trucking |
| `BMT5_INDUSTRY_WASTE_MANAGEMENT` | 119 | Waste management |
| `BMT5_INDUSTRY_REAL_ESTATE_DEVELOPMENT` | 120 | Real estate - Development |
| `BMT5_INDUSTRY_REAL_ESTATE_DIVERSIFIED` | 121 | Real estate - Diversified |
| `BMT5_INDUSTRY_REAL_ESTATE_SERVICES` | 122 | Real estate services |
| `BMT5_INDUSTRY_REIT_DIVERSIFIED` | 123 | REIT - Diversified |
| `BMT5_INDUSTRY_REIT_HEALTCARE` | 124 | REIT - Healthcare facilities |
| `BMT5_INDUSTRY_REIT_HOTEL_MOTEL` | 125 | REIT - Hotel and motel |
| `BMT5_INDUSTRY_REIT_INDUSTRIAL` | 126 | REIT - Industrial |
| `BMT5_INDUSTRY_REIT_MORTAGE` | 127 | REIT - Mortgage |
| `BMT5_INDUSTRY_REIT_OFFICE` | 128 | REIT - Office |
| `BMT5_INDUSTRY_REIT_RESIDENTAL` | 129 | REIT - Residential |
| `BMT5_INDUSTRY_REIT_RETAIL` | 130 | REIT - Retail |
| `BMT5_INDUSTRY_REIT_SPECIALITY` | 131 | REIT - Specialty |
| `BMT5_INDUSTRY_COMMUNICATION_EQUIPMENT` | 132 | Communication equipment |
| `BMT5_INDUSTRY_COMPUTER_HARDWARE` | 133 | Computer hardware |
| `BMT5_INDUSTRY_CONSUMER_ELECTRONICS` | 134 | Consumer electronics |
| `BMT5_INDUSTRY_ELECTRONIC_COMPONENTS` | 135 | Electronic components |
| `BMT5_INDUSTRY_ELECTRONIC_DISTRIBUTION` | 136 | Electronics and computer distribution |
| `BMT5_INDUSTRY_IT_SERVICES` | 137 | Information technology services |
| `BMT5_INDUSTRY_SCIENTIFIC_INSTRUMENTS` | 138 | Scientific and technical instruments |
| `BMT5_INDUSTRY_SEMICONDUCTOR_EQUIPMENT` | 139 | Semiconductor equipment and materials |
| `BMT5_INDUSTRY_SEMICONDUCTORS` | 140 | Semiconductors |
| `BMT5_INDUSTRY_SOFTWARE_APPLICATION` | 141 | Software - Application |
| `BMT5_INDUSTRY_SOFTWARE_INFRASTRUCTURE` | 142 | Software - Infrastructure |
| `BMT5_INDUSTRY_SOLAR` | 143 | Solar |
| `BMT5_INDUSTRY_UTILITIES_DIVERSIFIED` | 144 | Utilities - Diversified |
| `BMT5_INDUSTRY_UTILITIES_POWERPRODUCERS` | 145 | Utilities - Independent power producers |
| `BMT5_INDUSTRY_UTILITIES_RENEWABLE` | 146 | Utilities - Renewable |
| `BMT5_INDUSTRY_UTILITIES_REGULATED_ELECTRIC` | 147 | Utilities - Regulated electric |
| `BMT5_INDUSTRY_UTILITIES_REGULATED_GAS` | 148 | Utilities - Regulated gas |
| `BMT5_INDUSTRY_UTILITIES_REGULATED_WATER` | 149 | Utilities - Regulated water |
| `BMT5_INDUSTRY_UTILITIES_FIRST` | 150 | Start of the utilities services types enumeration. Corresponds to INDUSTRY_UTILITIES_DIVERSIFIED. |
| `BMT5_INDUSTRY_UTILITIES_LAST` | 151 | End of the utilities services types enumeration. Corresponds to INDUSTRY_UTILITIES_REGULATED_WATER. |

## BMT5_ENUM_SYMBOL_OPTION_MODE

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_OPTION_MODE_EUROPEAN` | 0 | European option may only be exercised on a specified date (expiration, execution date, delivery date) |
| `BMT5_SYMBOL_OPTION_MODE_AMERICAN` | 1 | American option may be exercised on any trading day or before expiry. The period within which a buyer can exercise the option is specified for it |

## BMT5_ENUM_SYMBOL_OPTION_RIGHT

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_OPTION_RIGHT_CALL` | 0 | A call option gives you the right to buy an asset at a specified price |
| `BMT5_SYMBOL_OPTION_RIGHT_PUT` | 1 | A put option gives you the right to sell an asset at a specified price |

## BMT5_ENUM_SYMBOL_ORDER_GTC_MODE

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_ORDERS_GTC` | 0 | Pending orders and Stop Loss/Take Profit levels are valid for an unlimited period until their explicit cancellation |
| `BMT5_SYMBOL_ORDERS_DAILY` | 1 | Orders are valid during one trading day. At the end of the day, all Stop Loss and Take Profit levels, as well as pending orders are deleted. |
| `BMT5_SYMBOL_ORDERS_DAILY_EXCLUDING_STOPS` | 2 | When a trade day changes, only pending orders are deleted, while Stop Loss and Take Profit levels are preserved. |

## BMT5_ENUM_SYMBOL_SECTOR

| Value | Number | Description |
|---|---|---|
| `BMT5_SECTOR_UNDEFINED` | 0 | Undefined |
| `BMT5_SECTOR_BASIC_MATERIALS` | 1 | Basic materials |
| `BMT5_SECTOR_COMMUNICATION_SERVICES` | 2 | Communication services |
| `BMT5_SECTOR_CONSUMER_CYCLICAL` | 3 | Consumer cyclical |
| `BMT5_SECTOR_CONSUMER_DEFENSIVE` | 4 | Consumer defensive |
| `BMT5_SECTOR_CURRENCY` | 5 | Currencies |
| `BMT5_SECTOR_CURRENCY_CRYPTO` | 6 | Cryptocurrencies |
| `BMT5_SECTOR_ENERGY` | 7 | Energy |
| `BMT5_SECTOR_FINANCIAL` | 8 | Finance |
| `BMT5_SECTOR_HEALTHCARE` | 9 | Healthcare |
| `BMT5_SECTOR_INDUSTRIALS` | 10 | Industrials |
| `BMT5_SECTOR_REAL_ESTATE` | 11 | Real estate |
| `BMT5_SECTOR_TECHNOLOGY` | 12 | Technology |
| `BMT5_SECTOR_UTILITIES` | 13 | Utilities |

## BMT5_ENUM_SYMBOL_SWAP_MODE

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_SWAP_MODE_DISABLED` | 0 | Swaps disabled (no swaps) |
| `BMT5_SYMBOL_SWAP_MODE_POINTS` | 1 | Swaps are charged in points |
| `BMT5_SYMBOL_SWAP_MODE_CURRENCY_SYMBOL` | 2 | Swaps are charged in money in base currency of the symbol |
| `BMT5_SYMBOL_SWAP_MODE_CURRENCY_MARGIN` | 3 | Swaps are charged in money in margin currency of the symbol |
| `BMT5_SYMBOL_SWAP_MODE_CURRENCY_DEPOSIT` | 4 | Swaps are charged in money, in client deposit currency |
| `BMT5_SYMBOL_SWAP_MODE_CURRENCY_PROFIT` | 5 | Swaps are charged in money in profit calculation currency |
| `BMT5_SYMBOL_SWAP_MODE_INTEREST_CURRENT` | 6 | Swaps are charged as the specified annual interest from the instrument price at calculation of swap (standard bank year is 360 days) |
| `BMT5_SYMBOL_SWAP_MODE_INTEREST_OPEN` | 7 | Swaps are charged as the specified annual interest from the open price of position (standard bank year is 360 days) |
| `BMT5_SYMBOL_SWAP_MODE_REOPEN_CURRENT` | 8 | Swaps are charged by reopening positions. At the end of a trading day the position is closed. Next day it is reopened by the close price +/- specified number of points (parameters SYMBOL_SWAP_LONG and SYMBOL_SWAP_SHORT) |
| `BMT5_SYMBOL_SWAP_MODE_REOPEN_BID` | 9 | Swaps are charged by reopening positions. At the end of a trading day the position is closed. Next day it is reopened by the current Bid price +/- specified number of points (parameters SYMBOL_SWAP_LONG and SYMBOL_SWAP_SHORT) |

## BMT5_ENUM_SYMBOL_TRADE_EXECUTION

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_TRADE_EXECUTION_REQUEST` | 0 | Execution by request |
| `BMT5_SYMBOL_TRADE_EXECUTION_INSTANT` | 1 | Instant execution |
| `BMT5_SYMBOL_TRADE_EXECUTION_MARKET` | 2 | Market execution |
| `BMT5_SYMBOL_TRADE_EXECUTION_EXCHANGE` | 3 | Exchange execution |

## BMT5_ENUM_SYMBOL_TRADE_MODE

| Value | Number | Description |
|---|---|---|
| `BMT5_SYMBOL_TRADE_MODE_DISABLED` | 0 | Trade is disabled for the symbol |
| `BMT5_SYMBOL_TRADE_MODE_LONGONLY` | 1 | Allowed only long positions |
| `BMT5_SYMBOL_TRADE_MODE_SHORTONLY` | 2 | Allowed only short positions |
| `BMT5_SYMBOL_TRADE_MODE_CLOSEONLY` | 3 | Allowed only position close operations |
| `BMT5_SYMBOL_TRADE_MODE_FULL` | 4 | No trade restrictions |

## BookType

| Value | Number | Description |
|---|---|---|
| `BOOK_TYPE_SELL` | 0 | Sell order (Offer) |
| `BOOK_TYPE_BUY` | 1 | Buy order (Bid) |
| `BOOK_TYPE_SELL_MARKET` | 2 | Sell order by Market |
| `BOOK_TYPE_BUY_MARKET` | 3 | Buy order by Market |

## DayOfWeek

| Value | Number | Description |
|---|---|---|
| `SUNDAY` | 0 | Sunday |
| `MONDAY` | 1 | Monday |
| `TUESDAY` | 2 | Tuesday |
| `WEDNESDAY` | 3 | Wednesday |
| `THURSDAY` | 4 | Thursday |
| `FRIDAY` | 5 | Friday |
| `SATURDAY` | 6 | Saturday |

## ENUM_ORDER_TYPE

| Value | Number | Description |
|---|---|---|
| `ORDER_TYPE_BUY` | 0 | Market Buy order |
| `ORDER_TYPE_SELL` | 1 | Market Sell order |
| `ORDER_TYPE_BUY_LIMIT` | 2 | Buy Limit pending order |
| `ORDER_TYPE_SELL_LIMIT` | 3 | Sell Limit pending order |
| `ORDER_TYPE_BUY_STOP` | 4 | Buy Stop pending order |
| `ORDER_TYPE_SELL_STOP` | 5 | Sell Stop pending order |
| `ORDER_TYPE_BUY_STOP_LIMIT` | 6 | Upon reaching the order price, a pending Buy Limit order is placed at the StopLimit price |
| `ORDER_TYPE_SELL_STOP_LIMIT` | 7 | Upon reaching the order price, a pending Sell Limit order is placed at the StopLimit price |
| `ORDER_TYPE_CLOSE_BY` | 8 | Order to close a position by an opposite one |

## ENUM_ORDER_TYPE_TF

| Value | Number | Description |
|---|---|---|
| `ORDER_TYPE_TF_BUY` | 0 | Market Buy order |
| `ORDER_TYPE_TF_SELL` | 1 | Market Sell order |
| `ORDER_TYPE_TF_BUY_LIMIT` | 2 | Buy Limit pending order |
| `ORDER_TYPE_TF_SELL_LIMIT` | 3 | Sell Limit pending order |
| `ORDER_TYPE_TF_BUY_STOP` | 4 | Buy Stop pending order |
| `ORDER_TYPE_TF_SELL_STOP` | 5 | Sell Stop pending order |
| `ORDER_TYPE_TF_BUY_STOP_LIMIT` | 6 | Upon reaching the order price, a pending Buy Limit order is placed at the StopLimit price |
| `ORDER_TYPE_TF_SELL_STOP_LIMIT` | 7 | Upon reaching the order price, a pending Sell Limit order is placed at the StopLimit price |
| `ORDER_TYPE_TF_CLOSE_BY` | 8 | Order to close a position by an opposite one |

## ErrorType

| Value | Number | Description |
|---|---|---|
| `UNDEFINED` | 0 |  |
| `MRPC` | 1 |  |
| `MRPC_VALIDATION` | 2 |  |
| `MRPC_TIMEOUT` | 3 |  |
| `TERMINAL_API_TIMEOUT` | 4 |  |
| `TERMINAL_API_GET_PARAMS` | 5 |  |
| `TERMINAL_API_SET_RESULT` | 6 |  |
| `TERMINAL_API_UNEXPECTED` | 7 |  |
| `MQL_EXECUTION` | 8 |  |
| `MQL_CUSTOM_EXECUTION` | 9 |  |
| `MQL_TRADE_EXECUTION` | 10 |  |

## LogType

| Value | Number | Description |
|---|---|---|
| `Global` | 0 |  |
| `MQL5` | 1 |  |
| `MQL4` | 2 |  |

## MRPC_ENUM_ORDER_TYPE_FILLING

| Value | Number | Description |
|---|---|---|
| `ORDER_FILLING_FOK` | 0 | An order can be executed in the specified volume only. If the necessary amount of a financial instrument is currently unavailable in the market, the order will not be executed. The desired volume can be made up of several available offers. The possibility of using FOK orders is determined at the trade server. |
| `ORDER_FILLING_IOC` | 1 | A trader agrees to execute a deal with the volume maximally available in the market within that indicated in the order. If the request cannot be filled completely, an order with the available volume will be executed, and the remaining volume will be canceled. The possibility of using IOC orders is determined at the trade server. |
| `ORDER_FILLING_BOC` | 3 | The BoC order assumes that the order can only be placed in the Depth of Market and cannot be immediately executed. If the order can be executed immediately when placed, then it is canceled. In fact, the BOC policy guarantees that the price of the placed order will be worse than the current market. BoC orders are used to implement passive trading, so that the order is not executed immediately when placed and does not affect current liquidity. Only limit and stop limit orders are supported (ORDER_TYPE_BUY_LIMIT, ORDER_TYPE_SELL_LIMIT, ORDER_TYPE_BUY_STOP_LIMIT, ORDER_TYPE_SELL_STOP_LIMIT). |
| `ORDER_FILLING_RETURN` | 2 | In case of partial filling, an order with remaining volume is not canceled but processed further. Return orders are not allowed in the Market Execution mode (market execution — SYMBOL_TRADE_EXECUTION_MARKET). |

## MRPC_ENUM_ORDER_TYPE_TIME

| Value | Number | Description |
|---|---|---|
| `ORDER_TIME_GTC` | 0 | Good till cancel order |
| `ORDER_TIME_DAY` | 1 | Good till current trade day order |
| `ORDER_TIME_SPECIFIED` | 2 | Good till expired order |
| `ORDER_TIME_SPECIFIED_DAY` | 3 | The order will be effective till 23:59:59 of the specified day. If this time is outside a trading session, the order expires in the nearest trading time. |

## MRPC_ENUM_TRADE_REQUEST_ACTIONS

| Value | Number | Description |
|---|---|---|
| `TRADE_ACTION_DEAL` | 0 | Place a trade order for an immediate execution with the specified parameters (market order) |
| `TRADE_ACTION_PENDING` | 1 | Place a trade order for the execution under specified conditions (pending order) |
| `TRADE_ACTION_SLTP` | 2 | Modify Stop Loss and Take Profit values of an opened position |
| `TRADE_ACTION_MODIFY` | 3 | Modify the parameters of the order placed previously |
| `TRADE_ACTION_REMOVE` | 4 | Delete the pending order placed previously |
| `TRADE_ACTION_CLOSE_BY` | 5 | Close a position by an opposite one |

## MRPC_ORDER_CLOSE_MODE

| Value | Number | Description |
|---|---|---|
| `MRPC_MARKET_ORDER_CLOSE` | 0 |  |
| `MRPC_MARKET_ORDER_PARTIAL_CLOSE` | 1 |  |
| `MRPC_PENDING_ORDER_REMOVE` | 2 |  |

## MT5_SUB_ENUM_EVENT_GROUP_TYPE

| Value | Number | Description |
|---|---|---|
| `OrderProfit` | 0 |  |
| `OrderUpdate` | 1 |  |

## MqlErrorCode

| Value | Number | Description |
|---|---|---|
| `ERR_SUCCESS` | 0 | The operation completed successfully |
| `ERR_INTERNAL_ERROR` | 4001 | Unexpected internal error |
| `ERR_WRONG_INTERNAL_PARAMETER` | 4002 | Wrong parameter in the inner call of the client terminal function |
| `ERR_INVALID_PARAMETER` | 4003 | Wrong parameter when calling the system function |
| `ERR_NOT_ENOUGH_MEMORY` | 4004 | Not enough memory to perform the system function |
| `ERR_STRUCT_WITHOBJECTS_ORCLASS` | 4005 | The structure contains objects of strings and/or dynamic arrays and/or structure of such objects and/or classes |
| `ERR_INVALID_ARRAY` | 4006 | Array of a wrong type, wrong size, or a damaged object of a dynamic array |
| `ERR_ARRAY_RESIZE_ERROR` | 4007 | Not enough memory for the relocation of an array, or an attempt to change the size of a static array |
| `ERR_STRING_RESIZE_ERROR` | 4008 | Not enough memory for the relocation of string |
| `ERR_NOTINITIALIZED_STRING` | 4009 | Not initialized string |
| `ERR_INVALID_DATETIME` | 4010 | Invalid date and/or time |
| `ERR_ARRAY_BAD_SIZE` | 4011 | Total amount of elements in the array cannot exceed 2147483647 |
| `ERR_INVALID_POINTER` | 4012 | Wrong pointer |
| `ERR_INVALID_POINTER_TYPE` | 4013 | Wrong type of pointer |
| `ERR_FUNCTION_NOT_ALLOWED` | 4014 | Function is not allowed for call |
| `ERR_RESOURCE_NAME_DUPLICATED` | 4015 | The names of the dynamic and the static resource match |
| `ERR_RESOURCE_NOT_FOUND` | 4016 | Resource with this name has not been found in EX5 |
| `ERR_RESOURCE_UNSUPPORTED_TYPE` | 4017 | Unsupported resource type or its size exceeds 16 Mb |
| `ERR_RESOURCE_NAME_IS_TOO_LONG` | 4018 | The resource name exceeds 63 characters |
| `ERR_MATH_OVERFLOW` | 4019 | Overflow occurred when calculating math function |
| `ERR_SLEEP_ERROR` | 4020 | Out of test end date after calling Sleep() |
| `ERR_PROGRAM_STOPPED` | 4022 | Test forcibly stopped from the outside |
| `ERR_INVALID_TYPE` | 4023 | Invalid type |
| `ERR_INVALID_HANDLE` | 4024 | Invalid handle |
| `ERR_TOO_MANY_OBJECTS` | 4025 | Object pool filled out |
| `ERR_CHART_WRONG_ID` | 4101 | Charts |
| `ERR_CHART_NO_REPLY` | 4102 | Chart does not respond |
| `ERR_CHART_NOT_FOUND` | 4103 | Chart not found |
| `ERR_CHART_NO_EXPERT` | 4104 | No Expert Advisor in the chart that could handle the event |
| `ERR_CHART_CANNOT_OPEN` | 4105 | Chart opening error |
| `ERR_CHART_CANNOT_CHANGE` | 4106 | Failed to change chart symbol and period |
| `ERR_CHART_WRONG_PARAMETER` | 4107 | Error value of the parameter for the function of working with charts |
| `ERR_CHART_CANNOT_CREATE_TIMER` | 4108 | Failed to create timer |
| `ERR_CHART_WRONG_PROPERTY` | 4109 | Wrong chart property ID |
| `ERR_CHART_SCREENSHOT_FAILED` | 4110 | Error creating screenshots |
| `ERR_CHART_NAVIGATE_FAILED` | 4111 | Error navigating through chart |
| `ERR_CHART_TEMPLATE_FAILED` | 4112 | Error applying template |
| `ERR_CHART_WINDOW_NOT_FOUND` | 4113 | Subwindow containing the indicator was not found |
| `ERR_CHART_INDICATOR_CANNOT_ADD` | 4114 | Error adding an indicator to chart |
| `ERR_CHART_INDICATOR_CANNOT_DEL` | 4115 | Error deleting an indicator from the chart |
| `ERR_CHART_INDICATOR_NOT_FOUND` | 4116 | Indicator not found on the specified chart |
| `ERR_OBJECT_ERROR` | 4201 | Graphical Objects |
| `ERR_OBJECT_NOT_FOUND` | 4202 | Graphical object was not found |
| `ERR_OBJECT_WRONG_PROPERTY` | 4203 | Wrong ID of a graphical object property |
| `ERR_OBJECT_GETDATE_FAILED` | 4204 | Unable to get date corresponding to the value |
| `ERR_OBJECT_GETVALUE_FAILED` | 4205 | Unable to get value corresponding to the date |
| `ERR_MARKET_UNKNOWN_SYMBOL` | 4301 | MarketInfo |
| `ERR_MARKET_NOT_SELECTED` | 4302 | Symbol is not selected in MarketWatch |
| `ERR_MARKET_WRONG_PROPERTY` | 4303 | Wrong identifier of a symbol property |
| `ERR_MARKET_LASTTIME_UNKNOWN` | 4304 | Time of the last tick is not known (no ticks) |
| `ERR_MARKET_SELECT_ERROR` | 4305 | Error adding or deleting a symbol in MarketWatch |
| `ERR_MARKET_SELECT_LIMIT` | 4306 | Exceeded the limit of selected symbols in MarketWatch |
| `ERR_MARKET_SESSION_INDEX` | 4307 | Wrong session ID when calling the SymbolInfoSessionQuote/SymbolInfoSessionTrade function |
| `ERR_HISTORY_NOT_FOUND` | 4401 | History Access Errors |
| `ERR_HISTORY_WRONG_PROPERTY` | 4402 | Wrong ID of the history property |
| `ERR_HISTORY_TIMEOUT` | 4403 | Exceeded history request timeout |
| `ERR_HISTORY_BARS_LIMIT` | 4404 | Number of requested bars limited by terminal settings |
| `ERR_HISTORY_LOAD_ERRORS` | 4405 | Multiple errors when loading history |
| `ERR_HISTORY_SMALL_BUFFER` | 4407 | Receiving array is too small to store all requested data |
| `ERR_GLOBALVARIABLE_NOT_FOUND` | 4501 | Global Variables Errors |
| `ERR_GLOBALVARIABLE_EXISTS` | 4502 | Global variable already exists |
| `ERR_GLOBALVARIABLE_NOT_MODIFIED` | 4503 | Global variables were not modified |
| `ERR_GLOBALVARIABLE_CANNOTREAD` | 4504 | Cannot read file with global variable values |
| `ERR_GLOBALVARIABLE_CANNOTWRITE` | 4505 | Cannot write file with global variable values |
| `ERR_MAIL_SEND_FAILED` | 4510 | Email sending failed |
| `ERR_PLAY_SOUND_FAILED` | 4511 | Sound playing failed |
| `ERR_MQL5_WRONG_PROPERTY` | 4512 | Wrong identifier of the program property |
| `ERR_TERMINAL_WRONG_PROPERTY` | 4513 | Wrong identifier of the terminal property |
| `ERR_FTP_SEND_FAILED` | 4514 | File sending via FTP failed |
| `ERR_NOTIFICATION_SEND_FAILED` | 4515 | Failed to send a notification |
| `ERR_NOTIFICATION_WRONG_PARAMETER` | 4516 | Invalid parameter for notification |
| `ERR_NOTIFICATION_WRONG_SETTINGS` | 4517 | Wrong notification settings |
| `ERR_NOTIFICATION_TOO_FREQUENT` | 4518 | Too frequent notifications |
| `ERR_FTP_NOSERVER` | 4519 | FTP server not specified |
| `ERR_FTP_NOLOGIN` | 4520 | FTP login not specified |
| `ERR_FTP_FILE_ERROR` | 4521 | File not found for FTP upload |
| `ERR_FTP_CONNECT_FAILED` | 4522 | FTP connection failed |
| `ERR_FTP_CHANGEDIR` | 4523 | FTP path not found on server |
| `ERR_BUFFERS_NO_MEMORY` | 4601 | Custom Indicator Buffers Errors |
| `ERR_BUFFERS_WRONG_INDEX` | 4602 | Wrong indicator buffer index |
| `ERR_CUSTOM_WRONG_PROPERTY` | 4603 | Custom Indicator Properties Errors |
| `ERR_ACCOUNT_WRONG_PROPERTY` | 4701 | Account Errors |
| `ERR_TRADE_WRONG_PROPERTY` | 4751 | Wrong trade property ID |
| `ERR_TRADE_DISABLED` | 4752 | Trading by Expert Advisors prohibited |
| `ERR_TRADE_POSITION_NOT_FOUND` | 4753 | Position not found |
| `ERR_TRADE_ORDER_NOT_FOUND` | 4754 | Order not found |
| `ERR_TRADE_DEAL_NOT_FOUND` | 4755 | Deal not found |
| `ERR_TRADE_SEND_FAILED` | 4756 | Trade request sending failed |
| `ERR_TRADE_CALC_FAILED` | 4758 | Failed to calculate profit or margin |
| `ERR_INDICATOR_UNKNOWN_SYMBOL` | 4801 | Indicator Errors |
| `ERR_INDICATOR_CANNOT_CREATE` | 4802 | Indicator cannot be created |
| `ERR_INDICATOR_NO_MEMORY` | 4803 | Not enough memory for the indicator |
| `ERR_INDICATOR_CANNOT_APPLY` | 4804 | Cannot apply indicator to another indicator |
| `ERR_INDICATOR_CANNOT_ADD` | 4805 | Error applying indicator to chart |
| `ERR_INDICATOR_DATA_NOT_FOUND` | 4806 | Requested data not found |
| `ERR_INDICATOR_WRONG_HANDLE` | 4807 | Wrong indicator handle |
| `ERR_INDICATOR_WRONG_PARAMETERS` | 4808 | Wrong parameters when creating indicator |
| `ERR_BOOKS_CANNOT_ADD` | 4901 | Depth of Market Errors |
| `ERR_BOOKS_CANNOT_DELETE` | 4902 | Depth Of Market cannot be removed |
| `ERR_BOOKS_CANNOT_GET` | 4903 | Cannot obtain Depth Of Market data |
| `ERR_BOOKS_CANNOT_SUBSCRIBE` | 4904 | Subscription error for Depth Of Market |
| `ERR_TOO_MANY_FILES` | 5001 | File Operations Errors |
| `ERR_WRONG_FILENAME` | 5002 | Invalid file name |
| `ERR_TOO_LONG_FILENAME` | 5003 | File name too long |
| `ERR_CANNOT_OPEN_FILE` | 5004 | File opening error |
| `ERR_FILE_CACHEBUFFER_ERROR` | 5005 | Not enough memory for cache |
| `ERR_CANNOT_DELETE_FILE` | 5006 | File deleting error |
| `ERR_INVALID_FILEHANDLE` | 5007 | Invalid file handle |
| `ERR_WRONG_FILEHANDLE` | 5008 | Wrong file handle |
| `ERR_NO_STRING_DATE` | 5030 | String Casting Errors |
| `ERR_WRONG_STRING_DATE` | 5031 | Wrong date in string |
| `ERR_WRONG_STRING_TIME` | 5032 | Wrong time in string |
| `ERR_STRING_TIME_ERROR` | 5033 | Error converting string to date |
| `ERR_STRING_OUT_OF_MEMORY` | 5034 | Not enough memory for string |
| `ERR_INCOMPATIBLE_ARRAYS` | 5050 | Operations with Arrays |
| `ERR_SMALL_ASSERIES_ARRAY` | 5051 | Insufficient size of AS_SERIES array |
| `ERR_SMALL_ARRAY` | 5052 | Array too small |
| `ERR_ZEROSIZE_ARRAY` | 5053 | Zero-length array |
| `ERR_NUMBER_ARRAYS_ONLY` | 5054 | Must be a numeric array |
| `ERR_OPENCL_NOT_SUPPORTED` | 5100 | OpenCL Errors |
| `ERR_OPENCL_INTERNAL` | 5101 | Internal OpenCL error |
| `ERR_OPENCL_INVALID_HANDLE` | 5102 | Invalid OpenCL handle |
| `ERR_OPENCL_CONTEXT_CREATE` | 5103 | Error creating OpenCL context |
| `ERR_OPENCL_QUEUE_CREATE` | 5104 | Error creating OpenCL run queue |
| `ERR_OPENCL_PROGRAM_CREATE` | 5105 | OpenCL program compilation error |
| `ERR_OPENCL_TOO_LONG_KERNEL_NAME` | 5106 | Kernel name too long |
| `ERR_OPENCL_KERNEL_CREATE` | 5107 | OpenCL kernel creation error |
| `ERR_OPENCL_SET_KERNEL_PARAMETER` | 5108 | Error occurred when setting parameters for the OpenCL kernel |
| `ERR_OPENCL_EXECUTE` | 5109 | OpenCL program runtime error |
| `ERR_OPENCL_WRONG_BUFFER_SIZE` | 5110 | Invalid size of the OpenCL buffer |
| `ERR_OPENCL_WRONG_BUFFER_OFFSET` | 5111 | Invalid offset in the OpenCL buffer |
| `ERR_OPENCL_BUFFER_CREATE` | 5112 | Failed to create an OpenCL buffer |
| `ERR_OPENCL_TOO_MANY_OBJECTS` | 5113 | Too many OpenCL objects |
| `ERR_OPENCL_SELECTDEVICE` | 5114 | OpenCL device selection error |
| `ERR_DATABASE_INTERNAL` | 5120 | Database Errors |
| `ERR_DATABASE_INVALID_HANDLE` | 5121 | Invalid database handle |
| `ERR_DATABASE_TOO_MANY_OBJECTS` | 5122 | Exceeded the maximum acceptable number of Database objects |
| `ERR_DATABASE_CONNECT` | 5123 | Database connection error |
| `ERR_DATABASE_EXECUTE` | 5124 | Request execution error |
| `ERR_DATABASE_PREPARE` | 5125 | Request generation error |
| `ERR_DATABASE_NO_MORE_DATA` | 5126 | No more data to read |
| `ERR_DATABASE_STEP` | 5127 | Failed to move to the next request entry |
| `ERR_DATABASE_NOT_READY` | 5128 | Data for reading request results are not ready yet |
| `ERR_DATABASE_BIND_PARAMETERS` | 5129 | Failed to auto substitute parameters to an SQL request |
| `ERR_WEBREQUEST_INVALID_ADDRESS` | 5200 | WebRequest Errors |
| `ERR_WEBREQUEST_CONNECT_FAILED` | 5201 | Failed to connect to specified URL |
| `ERR_WEBREQUEST_TIMEOUT` | 5202 | Timeout exceeded |
| `ERR_WEBREQUEST_REQUEST_FAILED` | 5203 | HTTP request failed |
| `ERR_NETSOCKET_INVALIDHANDLE` | 5270 | Network (Sockets) Errors |
| `ERR_NETSOCKET_TOO_MANY_OPENED` | 5271 | Too many open sockets (max 128) |
| `ERR_NETSOCKET_CANNOT_CONNECT` | 5272 | Failed to connect to remote host |
| `ERR_NETSOCKET_IO_ERROR` | 5273 | Failed to send/receive data from socket |
| `ERR_NETSOCKET_HANDSHAKE_FAILED` | 5274 | Failed to establish secure connection (TLS Handshake) |
| `ERR_NETSOCKET_NO_CERTIFICATE` | 5275 | No data on certificate protecting the connection |
| `ERR_NOT_CUSTOM_SYMBOL` | 5300 | Custom Symbols Errors |
| `ERR_CUSTOM_SYMBOL_WRONG_NAME` | 5301 | Invalid custom symbol name |
| `ERR_CUSTOM_SYMBOL_NAME_LONG` | 5302 | Custom symbol name too long |
| `ERR_CUSTOM_SYMBOL_PATH_LONG` | 5303 | Custom symbol path too long |
| `ERR_CUSTOM_SYMBOL_EXIST` | 5304 | Custom symbol with the same name already exists |
| `ERR_CUSTOM_SYMBOL_ERROR` | 5305 | Error creating, deleting, or modifying the custom symbol |
| `ERR_CUSTOM_SYMBOL_SELECTED` | 5306 | Cannot delete a selected custom symbol in Market Watch |
| `ERR_CUSTOM_SYMBOL_PROPERTY_WRONG` | 5307 | Invalid custom symbol property |
| `ERR_CUSTOM_SYMBOL_PARAMETER_ERROR` | 5308 | Incorrect parameter for setting a custom symbol property |
| `ERR_CUSTOM_SYMBOL_PARAMETER_LONG` | 5309 | Too long parameter string for setting a custom symbol property |
| `ERR_CUSTOM_TICKS_WRONG_ORDER` | 5310 | Ticks in the array are not arranged in the order of time |
| `ERR_CALENDAR_MORE_DATA` | 5400 | Economic Calendar Errors |
| `ERR_CALENDAR_TIMEOUT` | 5401 | Request time limit exceeded |
| `ERR_CALENDAR_NO_DATA` | 5402 | Country not found |
| `ERR_DATABASE_ERROR` | 5601 | Additional Database Errors |
| `ERR_DATABASE_LOGIC` | 5602 | SQLite internal logic error |
| `ERR_DATABASE_PERM` | 5603 | Access denied |
| `ERR_DATABASE_ABORT` | 5604 | Callback routine requested abort |
| `ERR_DATABASE_BUSY` | 5605 | Database file locked |
| `ERR_DATABASE_LOCKED` | 5606 | Database table locked |
| `ERR_DATABASE_NOMEM` | 5607 | Insufficient memory for completing operation |
| `ERR_DATABASE_READONLY` | 5608 | Attempt to write to readonly database |
| `ERR_DATABASE_INTERRUPT` | 5609 | Operation terminated by sqlite3_interrupt() |
| `ERR_DATABASE_IOERR` | 5610 | Disk I/O error |
| `ERR_DATABASE_CORRUPT` | 5611 | Database disk image corrupted |
| `ERR_DATABASE_NOTFOUND` | 5612 | Unknown operation code in sqlite3_file_control() |
| `ERR_DATABASE_FULL` | 5613 | Insertion failed because database is full |
| `ERR_DATABASE_CANTOPEN` | 5614 | Unable to open the database file |
| `ERR_DATABASE_PROTOCOL` | 5615 | Database lock protocol error |
| `ERR_DATABASE_EMPTY` | 5616 | Internal use only |
| `ERR_DATABASE_SCHEMA` | 5617 | Database schema changed |
| `ERR_DATABASE_TOOBIG` | 5618 | String or BLOB exceeds size limit |
| `ERR_DATABASE_CONSTRAINT` | 5619 | Abort due to constraint violation |
| `ERR_DATABASE_MISMATCH` | 5620 | Data type mismatch |
| `ERR_DATABASE_MISUSE` | 5621 | Library used incorrectly |
| `ERR_DATABASE_NOLFS` | 5622 | Uses OS features not supported on host |
| `ERR_DATABASE_AUTH` | 5623 | Authorization denied |
| `ERR_DATABASE_FORMAT` | 5624 | Not used |
| `ERR_DATABASE_RANGE` | 5625 | Bind parameter error, incorrect index |
| `ERR_DATABASE_NOTADB` | 5626 | File opened that is not a database file |
| `ERR_MATRIX_INTERNAL` | 5700 | Matrix and Vector Methods Errors |
| `ERR_MATRIX_NOT_INITIALIZED` | 5701 | Matrix/vector not initialized |
| `ERR_MATRIX_INCONSISTENT` | 5702 | Inconsistent size of matrices/vectors in operation |
| `ERR_MATRIX_INVALID_SIZE` | 5703 | Invalid matrix/vector size |
| `ERR_MATRIX_INVALID_TYPE` | 5704 | Invalid matrix/vector type |
| `ERR_MATRIX_FUNC_NOT_ALLOWED` | 5705 | Function not available for this matrix/vector |
| `ERR_MATRIX_CONTAINS_NAN` | 5706 | Matrix/vector contains non-numbers (NaN/Inf) |
| `ERR_ONNX_INTERNAL` | 5800 | ONNX Model Errors |
| `ERR_ONNX_NOT_INITIALIZED` | 5801 | ONNX Runtime API initialization error |
| `ERR_ONNX_NOT_SUPPORTED` | 5802 | Property or value not supported by MQL5 |
| `ERR_ONNX_RUN_FAILED` | 5803 | ONNX runtime API run error |
| `ERR_ONNX_INVALID_PARAMETERS_COUNT` | 5804 | Invalid number of parameters passed to OnnxRun |
| `ERR_ONNX_INVALID_PARAMETER` | 5805 | Invalid parameter value |
| `ERR_ONNX_INVALID_PARAMETER_TYPE` | 5806 | Invalid parameter type |
| `ERR_ONNX_INVALID_PARAMETER_SIZE` | 5807 | Invalid parameter size |
| `ERR_ONNX_WRONG_DIMENSION` | 5808 | Tensor dimension not set or invalid |
| `ERR_USER_ERROR_FIRST` | 65536 | User Errors |

## MqlErrorTradeCode

| Value | Number | Description |
|---|---|---|
| `TRADE_RETCODE_SUCCESS` | 0 |  |
| `TRADE_RETCODE_REQUOTE` | 10004 | Requote |
| `TRADE_RETCODE_REJECT` | 10006 | Request rejected |
| `TRADE_RETCODE_CANCEL` | 10007 | Request canceled by trader |
| `TRADE_RETCODE_PLACED` | 10008 | Order placed |
| `TRADE_RETCODE_DONE` | 10009 | Request completed |
| `TRADE_RETCODE_DONE_PARTIAL` | 10010 | Only part of the request was completed |
| `TRADE_RETCODE_ERROR` | 10011 | Request processing error |
| `TRADE_RETCODE_TIMEOUT` | 10012 | Request canceled by timeout |
| `TRADE_RETCODE_INVALID` | 10013 | Invalid request |
| `TRADE_RETCODE_INVALID_VOLUME` | 10014 | Invalid volume in the request |
| `TRADE_RETCODE_INVALID_PRICE` | 10015 | Invalid price in the request |
| `TRADE_RETCODE_INVALID_STOPS` | 10016 | Invalid stops in the request |
| `TRADE_RETCODE_TRADE_DISABLED` | 10017 | Trade is disabled |
| `TRADE_RETCODE_MARKET_CLOSED` | 10018 | Market is closed |
| `TRADE_RETCODE_NO_MONEY` | 10019 | Not enough money to complete the request |
| `TRADE_RETCODE_PRICE_CHANGED` | 10020 | Prices changed |
| `TRADE_RETCODE_PRICE_OFF` | 10021 | No quotes to process the request |
| `TRADE_RETCODE_INVALID_EXPIRATION` | 10022 | Invalid order expiration date |
| `TRADE_RETCODE_ORDER_CHANGED` | 10023 | Order state changed |
| `TRADE_RETCODE_TOO_MANY_REQUESTS` | 10024 | Too frequent requests |
| `TRADE_RETCODE_NO_CHANGES` | 10025 | No changes in request |
| `TRADE_RETCODE_SERVER_DISABLES_AT` | 10026 | Autotrading disabled by server |
| `TRADE_RETCODE_CLIENT_DISABLES_AT` | 10027 | Autotrading disabled by client terminal |
| `TRADE_RETCODE_LOCKED` | 10028 | Request locked for processing |
| `TRADE_RETCODE_FROZEN` | 10029 | Order or position frozen |
| `TRADE_RETCODE_INVALID_FILL` | 10030 | Invalid order filling type |
| `TRADE_RETCODE_CONNECTION` | 10031 | No connection with trade server |
| `TRADE_RETCODE_ONLY_REAL` | 10032 | Operation allowed only for live accounts |
| `TRADE_RETCODE_LIMIT_ORDERS` | 10033 | Limit of pending orders reached |
| `TRADE_RETCODE_LIMIT_VOLUME` | 10034 | Volume of orders and positions reached the limit |
| `TRADE_RETCODE_INVALID_ORDER` | 10035 | Incorrect or prohibited order type |
| `TRADE_RETCODE_POSITION_CLOSED` | 10036 | Position already closed |
| `TRADE_RETCODE_INVALID_CLOSE_VOLUME` | 10038 | Close volume exceeds position volume |
| `TRADE_RETCODE_CLOSE_ORDER_EXIST` | 10039 | Close order already exists |
| `TRADE_RETCODE_LIMIT_POSITIONS` | 10040 | Limit of open positions reached |
| `TRADE_RETCODE_REJECT_CANCEL` | 10041 | Pending order activation request rejected |
| `TRADE_RETCODE_LONG_ONLY` | 10042 | Only long positions allowed |
| `TRADE_RETCODE_SHORT_ONLY` | 10043 | Only short positions allowed |
| `TRADE_RETCODE_CLOSE_ONLY` | 10044 | Only position closing allowed |
| `TRADE_RETCODE_FIFO_CLOSE` | 10045 | Closing allowed only by FIFO rule |
| `TRADE_RETCODE_HEDGE_PROHIBITED` | 10046 | Opposite positions disabled |

## MrpcEnumAccountTradeMode

| Value | Number | Description |
|---|---|---|
| `MRPC_ACCOUNT_TRADE_MODE_DEMO` | 0 |  |
| `MRPC_ACCOUNT_TRADE_MODE_CONTEST` | 1 |  |
| `MRPC_ACCOUNT_TRADE_MODE_REAL` | 2 |  |

## ProxyTypes

Specifies the type of proxy servers that an instance of the ProxySocket class can use.

| Value | Number | Description |
|---|---|---|
| `None` | 0 | No proxy server; the ProxySocket object behaves exactly like an ordinary Socket object. |
| `Https` | 1 | A HTTPS (CONNECT) proxy server. |
| `Socks4` | 2 | A SOCKS4[A] proxy server. |
| `Socks5` | 3 | A SOCKS5 proxy server. |

## QUOTE_HISTORY_TICK_FLAGS

Which ticks to return - MQL5's COPY_TICKS_* modes.

| Value | Number | Description |
|---|---|---|
| `QH_TICKS_ALL` | 0 | every tick |
| `QH_TICKS_INFO` | 1 | ticks with bid and/or ask changes |
| `QH_TICKS_TRADE` | 2 | ticks with last and/or volume changes |

## QUOTE_HISTORY_TIMEFRAME

Timeframe of a bar. The value is the bar length in MINUTES, which is also what MT4 calls these periods; the terminal maps it to MQL5's ENUM_TIMEFRAMES.

| Value | Number | Description |
|---|---|---|
| `QH_PERIOD_UNSPECIFIED` | 0 |  |
| `QH_PERIOD_M1` | 1 |  |
| `QH_PERIOD_M2` | 2 |  |
| `QH_PERIOD_M3` | 3 |  |
| `QH_PERIOD_M4` | 4 |  |
| `QH_PERIOD_M5` | 5 |  |
| `QH_PERIOD_M6` | 6 |  |
| `QH_PERIOD_M10` | 10 |  |
| `QH_PERIOD_M12` | 12 |  |
| `QH_PERIOD_M15` | 15 |  |
| `QH_PERIOD_M20` | 20 |  |
| `QH_PERIOD_M30` | 30 |  |
| `QH_PERIOD_H1` | 60 |  |
| `QH_PERIOD_H2` | 120 |  |
| `QH_PERIOD_H3` | 180 |  |
| `QH_PERIOD_H4` | 240 |  |
| `QH_PERIOD_H6` | 360 |  |
| `QH_PERIOD_H8` | 480 |  |
| `QH_PERIOD_H12` | 720 |  |
| `QH_PERIOD_D1` | 1440 |  |
| `QH_PERIOD_W1` | 10080 |  |
| `QH_PERIOD_MN1` | 43200 |  |

## SERVER_TIMEZONE_SOURCE

| Value | Number | Description |
|---|---|---|
| `SERVER_TIMEZONE_SOURCE_UNKNOWN` | 0 | No terminal of this broker server has read the offset yet (MT4 with the market closed); the *_utc fields stay unset. |
| `SERVER_TIMEZONE_SOURCE_LIVE` | 1 | Read by this terminal itself. |
| `SERVER_TIMEZONE_SOURCE_LAST_VALIDATED` | 2 | The last offset validated for this broker server, used until this terminal reads its own (MT4 with the market closed). |

## SUB_ENUM_DEAL_ENTRY

| Value | Number | Description |
|---|---|---|
| `SUB_DEAL_ENTRY_IN` | 0 | Entry in |
| `SUB_DEAL_ENTRY_OUT` | 1 | Entry out |
| `SUB_DEAL_ENTRY_INOUT` | 2 | Reverse |
| `SUB_DEAL_ENTRY_OUT_BY` | 3 | Close a position by an opposite one |

## SUB_ENUM_DEAL_REASON

| Value | Number | Description |
|---|---|---|
| `SUB_DEAL_REASON_CLIENT` | 0 |  |
| `SUB_DEAL_REASON_MOBILE` | 1 |  |
| `SUB_DEAL_REASON_WEB` | 2 |  |
| `SUB_DEAL_REASON_EXPERT` | 3 |  |
| `SUB_DEAL_REASON_SL` | 4 |  |
| `SUB_DEAL_REASON_TP` | 5 |  |
| `SUB_DEAL_REASON_SO` | 6 |  |
| `SUB_DEAL_REASON_ROLLOVER` | 7 |  |
| `SUB_DEAL_REASON_VMARGIN` | 8 |  |
| `SUB_DEAL_REASON_SPLIT` | 9 |  |
| `SUB_DEAL_REASON_CORPORATE_ACTION` | 10 |  |

## SUB_ENUM_DEAL_TYPE

| Value | Number | Description |
|---|---|---|
| `SUB_DEAL_TYPE_BUY` | 0 | Buy |
| `SUB_DEAL_TYPE_SELL` | 1 | Sell |
| `SUB_DEAL_TYPE_BALANCE` | 2 | Balance |
| `SUB_DEAL_TYPE_CREDIT` | 3 | Credit |
| `SUB_DEAL_TYPE_CHARGE` | 4 | Additional charge |
| `SUB_DEAL_TYPE_CORRECTION` | 5 | Correction |
| `SUB_DEAL_TYPE_BONUS` | 6 | Bonus |
| `SUB_DEAL_TYPE_COMMISSION` | 7 | Additional commission |
| `SUB_DEAL_TYPE_COMMISSION_DAILY` | 8 | Daily commission |
| `SUB_DEAL_TYPE_COMMISSION_MONTHLY` | 9 | Monthly commission |
| `SUB_DEAL_TYPE_COMMISSION_AGENT_DAILY` | 10 | Daily agent commission |
| `SUB_DEAL_TYPE_COMMISSION_AGENT_MONTHLY` | 11 | Monthly agent commission |
| `SUB_DEAL_TYPE_INTEREST` | 12 | Interest rate |
| `SUB_DEAL_TYPE_BUY_CANCELED` | 13 | Canceled buy deal |
| `SUB_DEAL_TYPE_SELL_CANCELED` | 14 | Canceled sell deal |
| `SUB_DEAL_DIVIDEND` | 15 | Dividend operations |
| `SUB_DEAL_DIVIDEND_FRANKED` | 16 | Franked (non-taxable) dividend operations |
| `SUB_DEAL_TAX` | 17 | Tax charges |

## SUB_ENUM_ORDER_REASON

| Value | Number | Description |
|---|---|---|
| `SUB_ORDER_REASON_CLIENT` | 0 |  |
| `SUB_ORDER_REASON_MOBILE` | 2 |  |
| `SUB_ORDER_REASON_WEB` | 3 |  |
| `SUB_ORDER_REASON_EXPERT` | 4 |  |
| `SUB_ORDER_REASON_SL` | 5 |  |
| `SUB_ORDER_REASON_TP` | 6 |  |
| `SUB_ORDER_REASON_SO` | 7 |  |

## SUB_ENUM_ORDER_STATE

| Value | Number | Description |
|---|---|---|
| `SUB_ORDER_STATE_STARTED` | 0 | Order checked, but not yet accepted by broker |
| `SUB_ORDER_STATE_PLACED` | 1 | Order accepted |
| `SUB_ORDER_STATE_CANCELED` | 2 | Order canceled by client |
| `SUB_ORDER_STATE_PARTIAL` | 3 | Order partially executed |
| `SUB_ORDER_STATE_FILLED` | 4 | Order fully executed |
| `SUB_ORDER_STATE_REJECTED` | 5 | Order rejected |
| `SUB_ORDER_STATE_EXPIRED` | 6 | Order expired |
| `SUB_ORDER_STATE_REQUEST_ADD` | 7 | Order is being registered (placing to the trading system) |
| `SUB_ORDER_STATE_REQUEST_MODIFY` | 8 | Order is being modified (changing its parameters) |
| `SUB_ORDER_STATE_REQUEST_CANCEL` | 9 | Order is being deleted (deleting from the trading system) |

## SUB_ENUM_ORDER_TYPE

| Value | Number | Description |
|---|---|---|
| `SUB_ORDER_TYPE_BUY` | 0 |  |
| `SUB_ORDER_TYPE_SELL` | 1 |  |
| `SUB_ORDER_TYPE_BUY_LIMIT` | 2 |  |
| `SUB_ORDER_TYPE_SELL_LIMIT` | 3 |  |
| `SUB_ORDER_TYPE_BUY_STOP` | 4 |  |
| `SUB_ORDER_TYPE_SELL_STOP` | 5 |  |
| `SUB_ORDER_TYPE_BUY_STOP_LIMIT` | 6 |  |
| `SUB_ORDER_TYPE_SELL_STOP_LIMIT` | 7 |  |
| `SUB_ORDER_TYPE_CLOSE_BY` | 8 |  |

## SUB_ENUM_ORDER_TYPE_FILLING

| Value | Number | Description |
|---|---|---|
| `SUB_ORDER_FILLING_FOK` | 0 |  |
| `SUB_ORDER_FILLING_IOC` | 1 |  |
| `SUB_ORDER_FILLING_BOC` | 2 |  |
| `SUB_ORDER_FILLING_RETURN` | 3 |  |

## SUB_ENUM_ORDER_TYPE_TIME

| Value | Number | Description |
|---|---|---|
| `SUB_ORDER_TIME_GTC` | 0 | Good till cancel order |
| `SUB_ORDER_TIME_DAY` | 1 | Good till current trade day order |
| `SUB_ORDER_TIME_SPECIFIED` | 2 | Good till expired order |
| `SUB_ORDER_TIME_SPECIFIED_DAY` | 3 | The order will be effective till 23:59:59 of the specified day. |

## SUB_ENUM_POSITION_REASON

| Value | Number | Description |
|---|---|---|
| `SUB_POSITION_REASON_CLIENT` | 0 |  |
| `SUB_POSITION_REASON_MOBILE` | 2 |  |
| `SUB_POSITION_REASON_WEB` | 3 |  |
| `SUB_POSITION_REASON_EXPERT` | 4 |  |

## SUB_ENUM_POSITION_TYPE

| Value | Number | Description |
|---|---|---|
| `SUB_POSITION_TYPE_BUY` | 0 | Buy |
| `SUB_POSITION_TYPE_SELL` | 1 | Sell |

## SUB_ENUM_TRADE_REQUEST_ACTIONS

| Value | Number | Description |
|---|---|---|
| `SUB_TRADE_ACTION_UNDEFINED` | 0 |  |
| `SUB_TRADE_ACTION_DEAL` | 1 | Place a trade order for an immediate execution with the specified parameters (market order) |
| `SUB_TRADE_ACTION_PENDING` | 2 | Place a trade order for the execution under specified conditions (pending order) |
| `SUB_TRADE_ACTION_SLTP` | 3 | Modify Stop Loss and Take Profit values of an opened position |
| `SUB_TRADE_ACTION_MODIFY` | 4 | Modify the parameters of the order placed previously |
| `SUB_TRADE_ACTION_REMOVE` | 5 | Delete the pending order placed previously |
| `SUB_TRADE_ACTION_CLOSE_BY` | 6 | Close a position by an opposite one |

## SUB_ENUM_TRADE_TRANSACTION_TYPE

| Value | Number | Description |
|---|---|---|
| `SUB_TRADE_TRANSACTION_ORDER_ADD` | 0 | Adding a new open order. |
| `SUB_TRADE_TRANSACTION_ORDER_UPDATE` | 1 | Updating an open order. The updates include not only evident changes from the client terminal or a trade server sides but also changes of an order state when setting it (for example, transition from ORDER_STATE_STARTED to ORDER_STATE_PLACED or from ORDER_STATE_PLACED to ORDER_STATE_PARTIAL, etc.). |
| `SUB_TRADE_TRANSACTION_ORDER_DELETE` | 2 | Removing an order from the list of the open ones. An order can be deleted from the open ones as a result of setting an appropriate request or execution (filling) and moving to the history. |
| `SUB_TRADE_TRANSACTION_DEAL_ADD` | 3 | Adding a deal to the history. The action is performed as a result of an order execution or performing operations with an account balance. |
| `SUB_TRADE_TRANSACTION_DEAL_UPDATE` | 4 | Updating a deal in the history. There may be cases when a previously executed deal is changed on a server. For example, a deal has been changed in an external trading system (exchange) where it was previously transferred by a broker. |
| `SUB_TRADE_TRANSACTION_DEAL_DELETE` | 5 | Deleting a deal from the history. There may be cases when a previously executed deal is deleted from a server. For example, a deal has been deleted in an external trading system (exchange) where it was previously transferred by a broker. |
| `SUB_TRADE_TRANSACTION_HISTORY_ADD` | 6 | Adding an order to the history as a result of execution or cancellation. |
| `SUB_TRADE_TRANSACTION_HISTORY_UPDATE` | 7 | Changing an order located in the orders history. This type is provided for enhancing functionality on a trade server side. |
| `SUB_TRADE_TRANSACTION_HISTORY_DELETE` | 8 | Deleting an order from the orders history. This type is provided for enhancing functionality on a trade server side. |
| `SUB_TRADE_TRANSACTION_POSITION` | 9 | Changing a position not related to a deal execution. This type of transaction shows that a position has been changed on a trade server side. Position volume, open price, Stop Loss and Take Profit levels can be changed. Data on changes are submitted in MqlTradeTransaction structure via OnTradeTransaction handler. Position change (adding, changing or closing), as a result of a deal execution, does not lead to the occurrence of TRADE_TRANSACTION_POSITION transaction. |
| `SUB_TRADE_TRANSACTION_REQUEST` | 10 | Notification of the fact that a trade request has been processed by a server and processing result has been received. Only type field (trade transaction type) must be analyzed for such transactions in MqlTradeTransaction structure. The second and third parameters of OnTradeTransaction (request and result) must be analyzed for additional data. |

## SymbolInfoDoubleProperty

| Value | Number | Description |
|---|---|---|
| `SYMBOL_BID` | 0 | Bid - best sell offer |
| `SYMBOL_BIDHIGH` | 1 | Maximal Bid of the day |
| `SYMBOL_BIDLOW` | 2 | Minimal Bid of the day |
| `SYMBOL_ASK` | 3 | Ask - best buy offer |
| `SYMBOL_ASKHIGH` | 4 | Maximal Ask of the day |
| `SYMBOL_ASKLOW` | 5 | Minimal Ask of the day |
| `SYMBOL_LAST` | 6 | Price of the last deal |
| `SYMBOL_LASTHIGH` | 7 | Maximal Last of the day |
| `SYMBOL_LASTLOW` | 8 | Minimal Last of the day |
| `SYMBOL_VOLUME_REAL` | 9 | Volume of the last deal |
| `SYMBOL_VOLUMEHIGH_REAL` | 10 | Maximum Volume of the day |
| `SYMBOL_VOLUMELOW_REAL` | 11 | Minimum Volume of the day |
| `SYMBOL_OPTION_STRIKE` | 12 | The strike price of an option. The price at which an option buyer can buy (in a Call option) or sell (in a Put option) the underlying asset, and the option seller is obliged to sell or buy the appropriate amount of the underlying asset. |
| `SYMBOL_POINT` | 13 | Symbol point value |
| `SYMBOL_TRADE_TICK_VALUE` | 14 | Value of SYMBOL_TRADE_TICK_VALUE_PROFIT |
| `SYMBOL_TRADE_TICK_VALUE_PROFIT` | 15 | Calculated tick price for a profitable position |
| `SYMBOL_TRADE_TICK_VALUE_LOSS` | 16 | Calculated tick price for a losing position |
| `SYMBOL_TRADE_TICK_SIZE` | 17 | Minimal price change |
| `SYMBOL_TRADE_CONTRACT_SIZE` | 18 | Trade contract size |
| `SYMBOL_TRADE_ACCRUED_INTEREST` | 19 | Accrued interest – accumulated coupon interest, i.e. part of the coupon interest calculated in proportion to the number of days since the coupon bond issuance, or the last coupon interest payment |
| `SYMBOL_TRADE_FACE_VALUE` | 20 | Face value – initial bond value set by the issuer |
| `SYMBOL_TRADE_LIQUIDITY_RATE` | 21 | Liquidity Rate is the share of the asset that can be used for the margin. |
| `SYMBOL_VOLUME_MIN` | 22 | Minimal volume for a deal |
| `SYMBOL_VOLUME_MAX` | 23 | Maximal volume for a deal |
| `SYMBOL_VOLUME_STEP` | 24 | Minimal volume change step for deal execution |
| `SYMBOL_VOLUME_LIMIT` | 25 | Maximum allowed aggregate volume of an open position and pending orders in one direction (buy or sell) for the symbol. For example, with the limitation of 5 lots, you can have an open buy position with the volume of 5 lots and place a pending order Sell Limit with the volume of 5 lots. But in this case you cannot place a Buy Limit pending order (since the total volume in one direction will exceed the limitation) or place Sell Limit with the volume more than 5 lots. |
| `SYMBOL_SWAP_LONG` | 26 | Long swap value |
| `SYMBOL_SWAP_SHORT` | 27 | Short swap value |
| `SYMBOL_SWAP_SUNDAY` | 28 | Swap calculation ratio (SYMBOL_SWAP_LONG or SYMBOL_SWAP_SHORT) for overnight positions rolled over from SUNDAY to the next day. There following values are supported: 0 – no swap is charged, 1 – single swap, 3 – triple swap |
| `SYMBOL_SWAP_MONDAY` | 29 | Swap calculation ratio (SYMBOL_SWAP_LONG or SYMBOL_SWAP_SHORT) for overnight positions rolled over from Monday to Tuesday |
| `SYMBOL_SWAP_TUESDAY` | 30 | Swap calculation ratio (SYMBOL_SWAP_LONG or SYMBOL_SWAP_SHORT) for overnight positions rolled over from Tuesday to Wednesday |
| `SYMBOL_SWAP_WEDNESDAY` | 31 | Swap calculation ratio (SYMBOL_SWAP_LONG or SYMBOL_SWAP_SHORT) for overnight positions rolled over from Wednesday to Thursday |
| `SYMBOL_SWAP_THURSDAY` | 32 | Swap calculation ratio (SYMBOL_SWAP_LONG or SYMBOL_SWAP_SHORT) for overnight positions rolled over from Thursday to Friday |
| `SYMBOL_SWAP_FRIDAY` | 33 | Swap calculation ratio (SYMBOL_SWAP_LONG or SYMBOL_SWAP_SHORT) for overnight positions rolled over from Friday to Saturday |
| `SYMBOL_SWAP_SATURDAY` | 34 | Swap calculation ratio (SYMBOL_SWAP_LONG or SYMBOL_SWAP_SHORT) for overnight positions rolled over from Saturday to Sunday |
| `SYMBOL_MARGIN_INITIAL` | 35 | Initial margin means the amount in the margin currency required for opening a position with the volume of one lot. It is used for checking a client's assets when he or she enters the market. |
| `SYMBOL_MARGIN_MAINTENANCE` | 36 | The maintenance margin. If it is set, it sets the margin amount in the margin currency of the symbol, charged from one lot. It is used for checking a client's assets when his/her account state changes. If the maintenance margin is equal to 0, the initial margin is used. |
| `SYMBOL_SESSION_VOLUME` | 37 | Summary volume of current session deals |
| `SYMBOL_SESSION_TURNOVER` | 38 | Summary turnover of the current session |
| `SYMBOL_SESSION_INTEREST` | 39 | Summary open interest |
| `SYMBOL_SESSION_BUY_ORDERS_VOLUME` | 40 | Current volume of Buy orders |
| `SYMBOL_SESSION_SELL_ORDERS_VOLUME` | 41 | Current volume of Sell orders |
| `SYMBOL_SESSION_OPEN` | 42 | Open price of the current session |
| `SYMBOL_SESSION_CLOSE` | 43 | Close price of the current session |
| `SYMBOL_SESSION_AW` | 44 | Average weighted price of the current session |
| `SYMBOL_SESSION_PRICE_SETTLEMENT` | 45 | Settlement price of the current session |
| `SYMBOL_SESSION_PRICE_LIMIT_MIN` | 46 | Minimal price of the current session |
| `SYMBOL_SESSION_PRICE_LIMIT_MAX` | 47 | Maximal price of the current session |
| `SYMBOL_MARGIN_HEDGED` | 48 | Contract size or margin value per one lot of hedged positions (oppositely directed positions of one symbol). Two margin calculation methods are possible for hedged positions. The calculation method is defined by the broker. Basic calculation: If the initial margin (SYMBOL_MARGIN_INITIAL) is specified for a symbol, the hedged margin is specified as an absolute value (in monetary terms). If the initial margin is not specified (equal to 0), SYMBOL_MARGIN_HEDGED is equal to the size of the contract, that will be used to calculate the margin by the appropriate formula in accordance with the type of the financial instrument (SYMBOL_TRADE_CALC_MODE). Calculation for the largest position: The SYMBOL_MARGIN_HEDGED value is not taken into account. The volume of all short and all long positions of a symbol is calculated. For each direction, a weighted average open price and a weighted average rate of conversion to the deposit currency is calculated. Next, using the appropriate formula chosen in accordance with the symbol type (SYMBOL_TRADE_CALC_MODE) the margin is calculated for the short and the long part. The largest one of the values is used as the margin. |
| `SYMBOL_PRICE_CHANGE` | 49 | Change of the current price relative to the end of the previous trading day in % |
| `SYMBOL_PRICE_VOLATILITY` | 50 | Price volatility in % |
| `SYMBOL_PRICE_THEORETICAL` | 51 | Theoretical option price |
| `SYMBOL_PRICE_DELTA` | 52 | Option/warrant delta shows the value the option price changes by, when the underlying asset price changes by 1 |
| `SYMBOL_PRICE_THETA` | 53 | Option/warrant theta shows the number of points the option price is to lose every day due to a temporary breakup, i.e. when the expiration date approaches |
| `SYMBOL_PRICE_GAMMA` | 54 | Option/warrant gamma shows the change rate of delta – how quickly or slowly the option premium changes |
| `SYMBOL_PRICE_VEGA` | 55 | Option/warrant vega shows the number of points the option price changes by when the volatility changes by 1% |
| `SYMBOL_PRICE_RHO` | 56 | Option/warrant rho reflects the sensitivity of the theoretical option price to the interest rate changing by 1% |
| `SYMBOL_PRICE_OMEGA` | 57 | Option/warrant omega. Option elasticity shows a relative percentage change of the option price by the percentage change of the underlying asset price |
| `SYMBOL_PRICE_SENSITIVITY` | 58 | Option/warrant sensitivity shows by how many points the price of the option's underlying asset should change so that the price of the option changes by one point |
| `SYMBOL_COUNT` | 59 | The count of symbol properties |

## SymbolInfoIntegerProperty

| Value | Number | Description |
|---|---|---|
| `SYMBOL_SUBSCRIPTION_DELAY` | 0 | Symbol data arrives with a delay. The property can be requested only for symbols selected in MarketWatch (SYMBOL_SELECT = true). The ERR_MARKET_NOT_SELECTED (4302) error is generated for other symbols |
| `SYMBOL_SECTOR` | 1 | The sector of the economy to which the asset belongs |
| `SYMBOL_INDUSTRY` | 2 | The industry or the economy branch to which the symbol belongs |
| `SYMBOL_CUSTOM` | 3 | It is a custom symbol – the symbol has been created synthetically based on other symbols from the Market Watch and/or external data sources |
| `SYMBOL_BACKGROUND_COLOR` | 4 | The color of the background used for the symbol in Market Watch |
| `SYMBOL_CHART_MODE` | 5 | The price type used for generating symbols bars, i.e. Bid or Last |
| `SYMBOL_EXIST` | 6 | Symbol with this name exists |
| `SYMBOL_SELECT` | 7 | Symbol is selected in Market Watch |
| `SYMBOL_VISIBLE` | 8 | Symbol is visible in Market Watch. Some symbols (mostly, these are cross rates required for calculation of margin requirements or profits in deposit currency) are selected automatically, but may not be visible in Market Watch. To be displayed such symbols have to be explicitly selected. |
| `SYMBOL_SESSION_DEALS` | 9 | Number of deals in the current session |
| `SYMBOL_SESSION_BUY_ORDERS` | 10 | Number of Buy orders at the moment |
| `SYMBOL_SESSION_SELL_ORDERS` | 11 | Number of Sell orders at the moment |
| `SYMBOL_VOLUME` | 12 | Volume of the last deal |
| `SYMBOL_VOLUMEHIGH` | 13 | Maximal day volume |
| `SYMBOL_VOLUMELOW` | 14 | Minimal day volume |
| `SYMBOL_TIME` | 15 | Time of the last quote |
| `SYMBOL_TIME_MSC` | 16 | Time of the last quote in milliseconds since 1970.01.01 |
| `SYMBOL_DIGITS` | 17 | Digits after a decimal point |
| `SYMBOL_SPREAD_FLOAT` | 18 | Indication of a floating spread |
| `SYMBOL_SPREAD` | 19 | Spread value in points |
| `SYMBOL_TICKS_BOOKDEPTH` | 20 | Maximal number of requests shown in Depth of Market. For symbols that have no queue of requests, the value is equal to zero. |
| `SYMBOL_TRADE_CALC_MODE` | 21 | Contract price calculation mode |
| `SYMBOL_TRADE_MODE` | 22 | Order execution type |
| `SYMBOL_START_TIME` | 23 | Date of the symbol trade beginning (usually used for futures) |
| `SYMBOL_EXPIRATION_TIME` | 24 | Date of the symbol trade end (usually used for futures) |
| `SYMBOL_TRADE_STOPS_LEVEL` | 25 | Minimal indention in points from the current close price to place Stop orders |
| `SYMBOL_TRADE_FREEZE_LEVEL` | 26 | Distance to freeze trade operations in points |
| `SYMBOL_TRADE_EXEMODE` | 27 | Deal execution mode |
| `SYMBOL_SWAP_MODE` | 28 | Swap calculation model |
| `SYMBOL_SWAP_ROLLOVER3DAYS` | 29 | The day of week to charge 3-day swap rollover |
| `SYMBOL_MARGIN_HEDGED_USE_LEG` | 30 | Calculating hedging margin using the larger leg (Buy or Sell) |
| `SYMBOL_EXPIRATION_MODE` | 31 | Flags of allowed order expiration modes |
| `SYMBOL_FILLING_MODE` | 32 | Flags of allowed order filling modes |
| `SYMBOL_ORDER_MODE` | 33 | Flags of allowed order types |
| `SYMBOL_ORDER_GTC_MODE` | 34 | Expiration of Stop Loss and Take Profit orders, if SYMBOL_EXPIRATION_MODE=SYMBOL_EXPIRATION_GTC (Good till canceled) |
| `SYMBOL_OPTION_MODE` | 35 | Option type |
| `SYMBOL_OPTION_RIGHT` | 36 | Option right (Call/Put) |

## SymbolInfoStringProperty

| Value | Number | Description |
|---|---|---|
| `SYMBOL_BASIS` | 0 | The underlying asset of a derivative |
| `SYMBOL_CATEGORY` | 1 | The name of the sector or category to which the financial symbol belongs |
| `SYMBOL_COUNTRY` | 2 | The country to which the financial symbol belongs |
| `SYMBOL_SECTOR_NAME` | 3 | The sector of the economy to which the financial symbol belongs |
| `SYMBOL_INDUSTRY_NAME` | 4 | The industry branch or the industry to which the financial symbol belongs |
| `SYMBOL_CURRENCY_BASE` | 5 | Basic currency of a symbol |
| `SYMBOL_CURRENCY_PROFIT` | 6 | Profit currency |
| `SYMBOL_CURRENCY_MARGIN` | 7 | Margin currency |
| `SYMBOL_BANK` | 8 | Feeder of the current quote |
| `SYMBOL_DESCRIPTION` | 9 | Symbol description |
| `SYMBOL_EXCHANGE` | 10 | The name of the exchange in which the financial symbol is traded |
| `SYMBOL_FORMULA` | 11 | The formula used for the custom symbol pricing. If the name of a financial symbol used in the formula starts with a digit or contains a special character (">" ", ".", "-", "&", "#" and so on) quotation marks should be used around this symbol name. Synthetic symbol: "@ESU19"/EURCAD Calendar spread: "Si-9.13"-"Si-6.13" Euro index: 34.38805726 * pow(EURUSD,0.3155) * pow(EURGBP,0.3056) * pow(EURJPY,0.1891) * pow(EURCHF,0.1113) * pow(EURSEK,0.0785) |
| `SYMBOL_ISIN` | 12 | The name of a symbol in the ISIN system (International Securities Identification Number). The International Securities Identification Number is a 12-digit alphanumeric code that uniquely identifies a security. The presence of this symbol property is determined on the side of a trade server. |
| `SYMBOL_PAGE` | 13 | The address of the web page containing symbol information. This address will be displayed as a link when viewing symbol properties in the terminal |
| `SYMBOL_PATH` | 14 | Path in the symbol tree |

## TMT5_ENUM_ORDER_TYPE

| Value | Number | Description |
|---|---|---|
| `TMT5_ORDER_TYPE_BUY` | 0 | Market Buy order |
| `TMT5_ORDER_TYPE_SELL` | 1 | Market Sell order |
| `TMT5_ORDER_TYPE_BUY_LIMIT` | 2 | Buy Limit pending order |
| `TMT5_ORDER_TYPE_SELL_LIMIT` | 3 | Sell Limit pending order |
| `TMT5_ORDER_TYPE_BUY_STOP` | 4 | Buy Stop pending order |
| `TMT5_ORDER_TYPE_SELL_STOP` | 5 | Sell Stop pending order |
| `TMT5_ORDER_TYPE_BUY_STOP_LIMIT` | 6 | Upon reaching the order price, a pending Buy Limit order is placed at the StopLimit price |
| `TMT5_ORDER_TYPE_SELL_STOP_LIMIT` | 7 | Upon reaching the order price, a pending Sell Limit order is placed at the StopLimit price |
| `TMT5_ORDER_TYPE_CLOSE_BY` | 8 | Order to close a position by an opposite one |

## TMT5_ENUM_ORDER_TYPE_TIME

| Value | Number | Description |
|---|---|---|
| `TMT5_ORDER_TIME_GTC` | 0 | Good till cancel order |
| `TMT5_ORDER_TIME_DAY` | 1 | Good till current trade day order |
| `TMT5_ORDER_TIME_SPECIFIED` | 2 | Good till expired order |
| `TMT5_ORDER_TIME_SPECIFIED_DAY` | 3 | The order will be effective till 23:59:59 of the specified day. If this time is outside a trading session, the order expires in the nearest trading time. |

## TerminalType

| Value | Number | Description |
|---|---|---|
| `MT4` | 0 |  |
| `MT5` | 1 |  |
