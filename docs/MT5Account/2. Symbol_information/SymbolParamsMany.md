# ✅ Getting Comprehensive Symbol Parameters

> **Request:** retrieve all parameters for one or many symbols in a single call (RECOMMENDED for reading many properties efficiently). Each symbol is returned as a 112-field `SymbolParameters` record.

**API Information:**

* **SDK wrapper:** `MT5Account.symbolParamsMany(...)` (from package `pro.mrpc.mt5`)
* **gRPC service:** `mt5_term_api.AccountHelper`
* **Proto definition:** `SymbolParamsMany` (defined in `mt5-term-api-account-helper.proto`)

### RPC

* **Service:** `mt5_term_api.AccountHelper`
* **Method:** `SymbolParamsMany(SymbolParamsManyRequest) → SymbolParamsManyReply`
* **Low‑level client (generated):** `AccountHelperGrpc.AccountHelperBlockingStub.symbolParamsMany(request)`
* **SDK wrapper (high-level):**

```java
package pro.mrpc.mt5;

public class MT5Account {
    /**
     * Gets comprehensive parameter details for one or more symbols with pagination support.
     * Returns extensive symbol information including contract specifications, trading conditions, and current state.
     * Use this when you need complete symbol details beyond basic tick data, especially for multiple symbols.
     *
     * @param symbolName Symbol name to filter (null to retrieve all symbols)
     * @param sortType Sort order for results (by name, MQL index, etc.)
     * @param pageNumber Page number for pagination (0-based index)
     * @param itemsPerPage Number of symbols per page (0 to retrieve all without pagination)
     * @return Detailed symbol parameters including specs, margins, session times, and current state
     * @throws ApiExceptionMT5 if the call fails or connection is lost
     */
    public Mt5TermApiAccountHelper.SymbolParamsManyReply symbolParamsMany(
        String symbolName,
        Mt5TermApiAccountHelper.AH_SYMBOL_PARAMS_MANY_SORT_TYPE sortType,
        int pageNumber,
        int itemsPerPage) throws ApiExceptionMT5;
}
```

**Request message:**

```protobuf
message SymbolParamsManyRequest {
  optional string symbol_name = 1;
  optional AH_SYMBOL_PARAMS_MANY_SORT_TYPE sort_type = 2;
  optional int32 page_number = 3;
  optional int32 items_per_page = 4;
}
```

**Reply message:**

```protobuf
message SymbolParamsManyReply {
  oneof response {
    SymbolParamsManyData data = 1;
    Error error = 2;
  }
}

message SymbolParamsManyData {
  repeated SymbolParameters symbol_infos = 1;
  int32 symbols_total = 2;
  optional int32 page_number = 3;
  optional int32 items_per_page = 4;
}
```

---

## 🔽 Input

| Parameter      | Type                                | Required | Description                                                       |
| -------------- | ----------------------------------- | -------- | ----------------------------------------------------------------- |
| `symbolName`   | `String`                            | optional | Filter by symbol name. Pass `null` to retrieve **all** symbols.   |
| `sortType`     | `AH_SYMBOL_PARAMS_MANY_SORT_TYPE`   | optional | Sort order for results. Pass `null` for server default.           |
| `pageNumber`   | `int`                               | ✅       | Page number for pagination (0-based).                             |
| `itemsPerPage` | `int`                               | ✅       | Items per page. Pass `0` to retrieve all items without paging.    |

---

## ⬆️ Output - `SymbolParamsManyData`

| Field            | Type                        | Java Type                 | Description                       |
| ---------------- | --------------------------- | ------------------------- | --------------------------------- |
| `symbol_infos`   | `repeated SymbolParameters` | `List<SymbolParameters>`  | List of symbol parameter records  |
| `symbols_total`  | `int32`                     | `int`                     | Total number of symbols           |
| `page_number`    | `int32` (optional)          | `int`                     | Current page number               |
| `items_per_page` | `int32` (optional)          | `int`                     | Items per page                    |

Access using `reply.getData().getSymbolInfosList()`, `reply.getData().getSymbolsTotal()`, etc.

### `SymbolParameters` structure

Each record contains **112 fields** — prices, volumes, spreads, contract specs, margins, swaps, session statistics, option data, Greeks, timestamps, trading modes, identifiers and classification. Access any field with its generated getter (e.g. `info.getBid()`, `info.getSpread()`, `info.getTradeContractSize()`).

**Enum-typed fields (13):**

| Field (getter)                          | Enum type                          |
| --------------------------------------- | ---------------------------------- |
| `sector` (`getSector()`)                | `BMT5_ENUM_SYMBOL_SECTOR`          |
| `industry` (`getIndustry()`)            | `BMT5_ENUM_SYMBOL_INDUSTRY`        |
| `chart_mode` (`getChartMode()`)         | `BMT5_ENUM_SYMBOL_CHART_MODE`      |
| `trade_calc_mode` (`getTradeCalcMode()`)| `BMT5_ENUM_SYMBOL_CALC_MODE`       |
| `trade_mode` (`getTradeMode()`)         | `BMT5_ENUM_SYMBOL_TRADE_MODE`      |
| `trade_exe_mode` (`getTradeExeMode()`)  | `BMT5_ENUM_SYMBOL_TRADE_EXECUTION` |
| `swap_mode` (`getSwapMode()`)           | `BMT5_ENUM_SYMBOL_SWAP_MODE`       |
| `swap_rollover_3days` (`getSwapRollover3Days()`) | `BMT5_ENUM_DAY_OF_WEEK`    |
| `filling_mode` (`getFillingModeList()`) | `repeated BMT5_ENUM_ORDER_TYPE_FILLING` |
| `order_mode` (`getOrderMode()`)         | `BMT5_ENUM_ORDER_TYPE`             |
| `order_gtc_mode` (`getOrderGtcMode()`)  | `BMT5_ENUM_SYMBOL_ORDER_GTC_MODE`  |
| `option_mode` (`getOptionMode()`)       | `BMT5_ENUM_SYMBOL_OPTION_MODE`     |
| `option_right` (`getOptionRight()`)     | `BMT5_ENUM_SYMBOL_OPTION_RIGHT`    |

---

## 🧱 Related enums (from proto)

> This method uses **14 enums** with **231 total constants** — 1 request-parameter enum plus 13 enum-typed fields on `SymbolParameters`. All are accessed as nested types on `Mt5TermApiAccountHelper` (e.g. `Mt5TermApiAccountHelper.BMT5_ENUM_SYMBOL_SECTOR.BMT5_SECTOR_ENERGY`).

### `AH_SYMBOL_PARAMS_MANY_SORT_TYPE` (request parameter `sortType`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `AH_PARAMS_MANY_SORT_TYPE_SYMBOL_NAME_ASC` | 0 | Symbol name ascending (A→Z) |
| `AH_PARAMS_MANY_SORT_TYPE_SYMBOL_NAME_DESC` | 1 | Symbol name descending (Z→A) |
| `AH_PARAMS_MANY_SORT_TYPE_MQL_INDEX_ASC` | 2 | MQL index ascending |
| `AH_PARAMS_MANY_SORT_TYPE_MQL_INDEX_DESC` | 3 | MQL index descending |

### `BMT5_ENUM_SYMBOL_SECTOR` (field `sector`)

| Enum Value | Value | Description |
|------------|-------|-------------|
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

### `BMT5_ENUM_SYMBOL_INDUSTRY` (field `industry`)

| Enum Value | Value | Description |
|------------|-------|-------------|
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

### `BMT5_ENUM_SYMBOL_CHART_MODE` (field `chart_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_SYMBOL_CHART_MODE_BID` | 0 |  |
| `BMT5_SYMBOL_CHART_MODE_LAST` | 1 |  |

### `BMT5_ENUM_SYMBOL_CALC_MODE` (field `trade_calc_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
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

### `BMT5_ENUM_SYMBOL_TRADE_MODE` (field `trade_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_SYMBOL_TRADE_MODE_DISABLED` | 0 | Trade is disabled for the symbol |
| `BMT5_SYMBOL_TRADE_MODE_LONGONLY` | 1 | Allowed only long positions |
| `BMT5_SYMBOL_TRADE_MODE_SHORTONLY` | 2 | Allowed only short positions |
| `BMT5_SYMBOL_TRADE_MODE_CLOSEONLY` | 3 | Allowed only position close operations |
| `BMT5_SYMBOL_TRADE_MODE_FULL` | 4 | No trade restrictions |

### `BMT5_ENUM_SYMBOL_TRADE_EXECUTION` (field `trade_exe_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_SYMBOL_TRADE_EXECUTION_REQUEST` | 0 | Execution by request |
| `BMT5_SYMBOL_TRADE_EXECUTION_INSTANT` | 1 | Instant execution |
| `BMT5_SYMBOL_TRADE_EXECUTION_MARKET` | 2 | Market execution |
| `BMT5_SYMBOL_TRADE_EXECUTION_EXCHANGE` | 3 | Exchange execution |

### `BMT5_ENUM_SYMBOL_SWAP_MODE` (field `swap_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
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

### `BMT5_ENUM_DAY_OF_WEEK` (field `swap_rollover_3days`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_SUNDAY` | 0 | Sunday |
| `BMT5_MONDAY` | 1 | Monday |
| `BMT5_TUESDAY` | 2 | Tuesday |
| `BMT5_WEDNESDAY` | 3 | Wednesday |
| `BMT5_THURSDAY` | 4 | Thursday |
| `BMT5_FRIDAY` | 5 | Friday |
| `BMT5_SATURDAY` | 6 | Saturday |

### `BMT5_ENUM_ORDER_TYPE_FILLING` (field `filling_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_ORDER_FILLING_FOK` | 0 |  |
| `BMT5_ORDER_FILLING_IOC` | 1 |  |
| `BMT5_ORDER_FILLING_BOC` | 3 |  |
| `BMT5_ORDER_FILLING_RETURN` | 2 |  |

### `BMT5_ENUM_ORDER_TYPE` (field `order_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_ORDER_TYPE_BUY` | 0 | Market Buy order |
| `BMT5_ORDER_TYPE_SELL` | 1 | Market Sell order |
| `BMT5_ORDER_TYPE_BUY_LIMIT` | 2 | Buy Limit pending order |
| `BMT5_ORDER_TYPE_SELL_LIMIT` | 3 | Sell Limit pending order |
| `BMT5_ORDER_TYPE_BUY_STOP` | 4 | Buy Stop pending order |
| `BMT5_ORDER_TYPE_SELL_STOP` | 5 | Sell Stop pending order |
| `BMT5_ORDER_TYPE_BUY_STOP_LIMIT` | 6 | Upon reaching the order price, a pending Buy Limit order is placed at the StopLimit price |
| `BMT5_ORDER_TYPE_SELL_STOP_LIMIT` | 7 | Upon reaching the order price, a pending Sell Limit order is placed at the StopLimit price |
| `BMT5_ORDER_TYPE_CLOSE_BY` | 8 | Order to close a position by an opposite one |

### `BMT5_ENUM_SYMBOL_ORDER_GTC_MODE` (field `order_gtc_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_SYMBOL_ORDERS_GTC` | 0 | Pending orders and Stop Loss/Take Profit levels are valid for an unlimited period until their explicit cancellation |
| `BMT5_SYMBOL_ORDERS_DAILY` | 1 | Orders are valid during one trading day. At the end of the day, all Stop Loss and Take Profit levels, as well as pending orders are deleted. |
| `BMT5_SYMBOL_ORDERS_DAILY_EXCLUDING_STOPS` | 2 | When a trade day changes, only pending orders are deleted, while Stop Loss and Take Profit levels are preserved. |

### `BMT5_ENUM_SYMBOL_OPTION_MODE` (field `option_mode`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_SYMBOL_OPTION_MODE_EUROPEAN` | 0 | European option may only be exercised on a specified date (expiration, execution date, delivery date) |
| `BMT5_SYMBOL_OPTION_MODE_AMERICAN` | 1 | American option may be exercised on any trading day or before expiry. The period within which a buyer can exercise the option is specified for it |

### `BMT5_ENUM_SYMBOL_OPTION_RIGHT` (field `option_right`)

| Enum Value | Value | Description |
|------------|-------|-------------|
| `BMT5_SYMBOL_OPTION_RIGHT_CALL` | 0 | A call option gives you the right to buy an asset at a specified price |
| `BMT5_SYMBOL_OPTION_RIGHT_PUT` | 1 | A put option gives you the right to sell an asset at a specified price |

---

## 💬 Just the essentials

* **What it is.** One RPC returning 112 fields per symbol for one symbol or the whole Market Watch.
* **Why you need it.** The most efficient way to read many symbol properties — a single call instead of dozens of `symbolInfo*` calls.
* **When to use.** Use this for complete symbol data; use `symbolInfoDouble/Integer/String` only for a single specific property.

---

## 🎯 Purpose

* Get all parameters for one symbol in a single call.
* Retrieve and paginate the full symbol list.
* Build comparison tables (spreads, margins, contract sizes).
* Classify symbols by sector/industry/calc mode.

---

## 🧩 Notes & Tips

* The method uses automatic reconnection via `executeWithReconnect()` to handle transient errors.
* All gRPC exceptions are converted to `ApiExceptionMT5` for consistent error handling.
* **Single symbol:** pass `symbolName` to fetch just that symbol.
* **All symbols:** pass `null` for `symbolName`.
* **Pagination:** use `pageNumber` + `itemsPerPage` for large lists; `itemsPerPage = 0` returns everything.
* `filling_mode` is a **repeated** enum — read it with `getFillingModeList()`.
* Some fields are zero/empty when not applicable to the symbol type.

---

## 🔗 Usage Examples

### 1) All parameters for one symbol (RECOMMENDED)

```java
import pro.mrpc.mt5.MT5Account;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import mt5_term_api.Mt5TermApiAccountHelper;

public class Example {
    public static void main(String[] args) {
        MT5Account account = new MT5Account(12345678, "password");
        try {
            account.connect("demo.mt5server.com", 443, "EURUSD");

            // symbolName="EURUSD", no sort, page 0, all items
            var reply = account.symbolParamsMany("EURUSD", null, 0, 0);
            var list  = reply.getData().getSymbolInfosList();

            if (!list.isEmpty()) {
                var info = list.get(0);
                System.out.printf("Symbol:        %s%n", info.getName());
                System.out.printf("Description:   %s%n", info.getSymDescription());
                System.out.printf("Bid / Ask:     %.5f / %.5f%n", info.getBid(), info.getAsk());
                System.out.printf("Spread:        %d points%n", info.getSpread());
                System.out.printf("Digits:        %d%n", info.getDigits());
                System.out.printf("Contract size: %.2f%n", info.getTradeContractSize());
                System.out.printf("Volume min/max/step: %.2f / %.2f / %.2f%n",
                    info.getVolumeMin(), info.getVolumeMax(), info.getVolumeStep());
                System.out.printf("Trade mode:    %s%n", info.getTradeMode().name());
                System.out.printf("Calc mode:     %s%n", info.getTradeCalcMode().name());
            }
        } catch (ApiExceptionMT5 e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            account.close();
        }
    }
}
```

### 2) First page of all symbols, sorted by name

```java
var reply = account.symbolParamsMany(
        null,
        Mt5TermApiAccountHelper.AH_SYMBOL_PARAMS_MANY_SORT_TYPE.AH_PARAMS_MANY_SORT_TYPE_SYMBOL_NAME_ASC,
        0,    // page number
        100); // items per page

var data = reply.getData();
System.out.printf("Total symbols: %d%n", data.getSymbolsTotal());
System.out.printf("On this page:  %d%n", data.getSymbolInfosList().size());

for (var info : data.getSymbolInfosList()) {
    System.out.printf("%-12s bid=%.5f ask=%.5f spread=%d%n",
        info.getName(), info.getBid(), info.getAsk(), info.getSpread());
}
```

### 3) Iterate through all pages

```java
java.util.List<Mt5TermApiAccountHelper.SymbolParameters> all = new java.util.ArrayList<>();
int page = 0, perPage = 100;

while (true) {
    var data = account.symbolParamsMany(null, null, page, perPage).getData();
    all.addAll(data.getSymbolInfosList());
    if (data.getSymbolInfosList().isEmpty() || all.size() >= data.getSymbolsTotal()) break;
    page++;
}
System.out.println("Total retrieved: " + all.size());
```

### 4) Compare spreads across symbols

```java
var list = account.symbolParamsMany(null, null, 0, 0).getData().getSymbolInfosList();

list.stream()
    .sorted(java.util.Comparator.comparingInt(Mt5TermApiAccountHelper.SymbolParameters::getSpread))
    .limit(20)
    .forEach(i -> System.out.printf("%-12s spread=%d bid=%.5f ask=%.5f%n",
        i.getName(), i.getSpread(), i.getBid(), i.getAsk()));
```

### 5) Classify symbols by calc mode

```java
var list = account.symbolParamsMany(null, null, 0, 0).getData().getSymbolInfosList();

java.util.Map<String, Long> byCalcMode = list.stream()
    .collect(java.util.stream.Collectors.groupingBy(
        i -> i.getTradeCalcMode().name(),
        java.util.stream.Collectors.counting()));

byCalcMode.forEach((mode, count) -> System.out.printf("%-40s %d%n", mode, count));
```

---

## 🔄 Low-level gRPC call (for reference)

```java
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import mt5_term_api.*;

// Create request (single symbol)
Mt5TermApiAccountHelper.SymbolParamsManyRequest request =
    Mt5TermApiAccountHelper.SymbolParamsManyRequest.newBuilder()
        .setSymbolName("EURUSD")
        .setPageNumber(0)
        .setItemsPerPage(0)
        .build();

Metadata headers = new Metadata();
Metadata.Key<String> idKey = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
headers.put(idKey, instanceId.toString());

Mt5TermApiAccountHelper.SymbolParamsManyReply reply = accountClient
    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
    .symbolParamsMany(request);

if (reply.hasError()) {
    throw new ApiExceptionMT5(reply.getError());
}

Mt5TermApiAccountHelper.SymbolParamsManyData data = reply.getData();
```

---

## 📚 See also

* [SymbolInfoDouble.md](./SymbolInfoDouble.md) - Get a single double property
* [SymbolInfoInteger.md](./SymbolInfoInteger.md) - Get a single integer property
* [SymbolInfoString.md](./SymbolInfoString.md) - Get a single string property
* [SymbolInfoTick.md](./SymbolInfoTick.md) - Get current tick data
* [SymbolInfoMarginRate.md](./SymbolInfoMarginRate.md) - Margin rate multipliers per order type
