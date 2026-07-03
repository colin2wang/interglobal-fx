# Interglobal FX Trading Platform - Project Directory Structure

## 1. Backend Directory Structure

### 1.1 Project Overview

The backend uses a Maven multi-module architecture, with each service deployed independently:

```
backend/
├── pom.xml                          # Parent POM (Spring Boot 3.2.4, Java 21)
├── sql/                             # Database initialization scripts
│   ├── 01-schema.sql
│   └── 03-init-data.sql
│
├── fx-common/                       # Common module (not independently deployable)
├── fx-system/                       # System management service (auth, users, roles, permissions)
├── fx-trade/                        # Trading core service (orders, positions, accounts)
├── fx-risk/                         # Risk control service (rule engine, risk events)
├── fx-clearing/                     # Clearing service (daily settlement, swap calculation)
├── fx-crm/                          # CRM service (customers, IB, tickets)
└── fx-report/                       # Report service (statistics and reports)
```

### 1.2 Common Module (fx-common)

Shared library containing utility classes, base entities, and unified response wrappers, depended upon by all other modules.

```
fx-common/src/main/java/com/globalfx/common/
├── base/                            # Base entities (BaseEntity, etc.)
├── config/                          # Common configuration classes
├── constant/                        # Constants
├── enums/                           # Enumerations (order types, statuses, etc.)
├── exception/                       # Exception classes
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ...
├── handler/                         # Global handlers
├── result/                          # Unified response wrappers
│   ├── Result.java
│   ├── ResultCode.java
│   └── PageResult.java
└── util/                            # Utility classes
    ├── DateUtil.java
    ├── MoneyUtil.java
    ├── IdGenerator.java
    └── ValidationUtil.java
```

### 1.3 Trading Core Service (fx-trade)

Port: 8081 | Redis DB: 1

```
fx-trade/src/main/java/com/globalfx/trade/
├── FxTradeApplication.java          # Application entry point
│
├── controller/                      # Controller layer
│   ├── AccountController.java
│   ├── OrderController.java
│   ├── PositionController.java
│   ├── SymbolController.java
│   ├── QuoteController.java
│   └── WalletController.java
│
├── service/                         # Business logic layer
│   ├── AccountService.java
│   ├── OrderService.java
│   ├── PositionService.java
│   ├── SymbolService.java
│   ├── QuoteService.java
│   ├── WalletService.java
│   └── impl/                        # Implementations
│       ├── AccountServiceImpl.java
│       ├── OrderServiceImpl.java
│       └── ...
│
├── mapper/                          # Data access layer (MyBatis-Plus)
│   ├── AccountMapper.java
│   ├── OrderMapper.java
│   └── PositionMapper.java
│
├── entity/                          # Database entities
│   ├── Account.java
│   ├── Order.java
│   ├── Position.java
│   ├── Symbol.java
│   └── Wallet.java
│
├── dto/                             # Data Transfer Objects
│   ├── CreateOrderDTO.java
│   └── OrderQueryDTO.java
│
└── vo/                              # View Objects
    ├── AccountVO.java
    ├── OrderVO.java
    └── PositionVO.java

fx-trade/src/main/resources/
├── application.yml
└── mapper/                          # MyBatis XML mappings
    ├── AccountMapper.xml
    ├── OrderMapper.xml
    └── PositionMapper.xml
```

### 1.4 System Management Service (fx-system)

Port: 8089 | Redis DB: 0 | Includes Spring Security

```
fx-system/src/main/java/com/globalfx/system/
├── FxSystemApplication.java
│
├── controller/
│   ├── AuthController.java          # Login, logout, token refresh
│   ├── UserController.java
│   ├── RoleController.java
│   ├── MenuController.java
│   ├── DictController.java
│   └── TenantController.java
│
├── service/
│   ├── AuthService.java
│   ├── UserService.java
│   ├── RoleService.java
│   ├── MenuService.java
│   ├── DictService.java
│   └── impl/
│
├── security/                        # Security module
│   ├── JwtUtil.java
│   ├── JwtFilter.java
│   └── SecurityConfig.java
│
├── mapper/
├── entity/
├── dto/
└── vo/
```

### 1.5 Risk Control Service (fx-risk)

Port: 8083 | Redis DB: 2

```
fx-risk/src/main/java/com/globalfx/risk/
├── FxRiskApplication.java
│
├── controller/
│   ├── RiskEventController.java
│   └── RiskRuleController.java
│
├── service/
│   ├── RiskEngineService.java       # Risk engine core
│   ├── RiskEventService.java
│   ├── RiskRuleService.java
│   └── impl/
│
├── mapper/
│   ├── RiskEventMapper.java
│   └── RiskRuleMapper.java
│
├── entity/
│   ├── RiskEvent.java
│   └── RiskRule.java
│
├── dto/
│   ├── HandleEventDTO.java
│   ├── OrderCheckDTO.java
│   └── RiskRuleDTO.java
│
└── vo/
    ├── RiskCheckResult.java
    ├── RiskEventVO.java
    └── RiskRuleVO.java
```

### 1.6 Clearing Service (fx-clearing)

Port: 8084 | Redis DB: 3

```
fx-clearing/src/main/java/com/globalfx/clearing/
├── FxClearingApplication.java
│
├── controller/
│   └── SettlementController.java
│
├── service/
│   ├── SettlementService.java       # Daily settlement
│   ├── SwapCalculationService.java  # Swap (overnight interest) calculation
│   └── impl/
│
├── mapper/
│   └── DailySettlementMapper.java
│
├── entity/
│   └── DailySettlement.java
│
├── dto/
│   ├── ExecuteSettlementDTO.java
│   └── SettlementQueryDTO.java
│
└── vo/
    ├── SettlementResultVO.java
    └── SettlementVO.java
```

### 1.7 CRM Service (fx-crm)

```
fx-crm/src/main/java/com/globalfx/crm/
├── FxCrmApplication.java
│
├── controller/
│   ├── CustomerController.java
│   ├── IbController.java           # IB partner management
│   ├── KycController.java          # KYC review
│   └── TicketController.java       # Ticket system
│
├── service/
│   ├── CustomerService.java
│   ├── IbPartnerService.java
│   ├── KycService.java
│   ├── TicketService.java
│   └── impl/
│
├── mapper/
│   ├── CustomerMapper.java
│   ├── IbPartnerMapper.java
│   ├── KycApplicationMapper.java
│   ├── TicketMapper.java
│   └── TicketReplyMapper.java
│
├── entity/
│   ├── Customer.java
│   ├── IbPartner.java
│   ├── KycApplication.java
│   ├── Ticket.java
│   └── TicketReply.java
│
├── dto/
│   ├── CustomerDTO.java
│   ├── IbPartnerDTO.java
│   ├── KycReviewDTO.java
│   ├── TicketDTO.java
│   └── TicketReplyDTO.java
│
└── vo/
    ├── CustomerVO.java
    ├── IbPartnerVO.java
    ├── KycApplicationVO.java
    └── TicketVO.java
```

### 1.8 Report Service (fx-report)

```
fx-report/src/main/java/com/globalfx/report/
├── FxReportApplication.java
│
├── controller/
├── service/
├── mapper/
├── dto/
└── vo/
```

## 2. Go Microservice Directory Structure

All Go services are located under the `go-services/` directory with a consistent structure.

### 2.1 Go Services Overview

| Service | Directory | HTTP Port | Purpose |
|---------|-----------|-----------|---------|
| fx-quote | go-services/fx-quote | - | Real-time market data service |
| fx-risk | go-services/fx-risk | - | Risk control engine |
| fx-mt-bridge | go-services/fx-mt-bridge | - | MT4/MT5 bridge |
| fx-fix-gateway | go-services/fx-fix-gateway | 8091 | FIX protocol gateway (TCP: 9876) |

### 2.2 Market Data Service (fx-quote)

```
fx-quote/
├── cmd/server/main.go              # Entry point
├── config.yaml                     # Configuration
├── Dockerfile
│
├── internal/
│   ├── config/config.go            # Config loading
│   ├── handler/                    # HTTP handler layer
│   │   ├── quote_handler.go
│   │   └── kline_handler.go
│   ├── service/                    # Business logic layer
│   │   ├── quote_service.go
│   │   ├── kline_service.go
│   │   └── aggregator_service.go
│   ├── repository/                 # Data access layer
│   │   ├── quote_repo.go
│   │   └── kline_repo.go
│   ├── model/                      # Data models
│   │   ├── quote.go
│   │   ├── kline.go
│   │   └── tick.go
│   └── ws/                         # WebSocket handling
│       ├── server.go
│       └── client.go
│
├── pkg/                            # Shared packages
│   ├── logger/logger.go
│   ├── kafka/producer.go
│   └── redis/client.go
│
├── go.mod
├── go.sum
└── README.md
```

### 2.3 Risk Control Service (fx-risk)

```
fx-risk/
├── cmd/server/main.go
├── config.yaml
├── Dockerfile
│
├── internal/
│   ├── config/
│   ├── handler/
│   ├── service/
│   │   ├── risk_service.go
│   │   ├── rule_engine.go          # Rule engine
│   │   └── anomaly_detector.go     # Anomaly detection
│   ├── repository/
│   ├── model/
│   └── engine/                     # Rule engine core
│       ├── context.go
│       ├── evaluator.go
│       └── rules/
│           ├── position_rule.go
│           ├── order_rule.go
│           └── margin_rule.go
│
├── pkg/
├── go.mod
└── Dockerfile
```

### 2.4 MT4/MT5 Bridge Service (fx-mt-bridge)

```
fx-mt-bridge/
├── cmd/server/main.go
├── config.yaml
├── Dockerfile
│
├── internal/
│   ├── config/
│   ├── handler/
│   ├── protocol/                   # MT protocol parsing
│   └── service/
│
├── pkg/
├── go.mod
└── README.md
```

### 2.5 FIX Protocol Gateway (fx-fix-gateway)

```
fx-fix-gateway/
├── cmd/server/main.go
├── config.yaml
├── Dockerfile
│
├── internal/
│   ├── config/
│   ├── handler/
│   │   ├── fix_handler.go
│   │   └── order_handler.go
│   ├── protocol/                   # FIX 4.4 protocol implementation
│   └── service/
│
├── pkg/
├── go.mod
└── README.md
```

## 3. Frontend Directory Structure

### 3.1 React Trading Terminal (fx-trader-web)

```
fx-trader-web/
├── src/
│   ├── api/                        # API request modules
│   │   ├── client.ts               # Axios wrapper
│   │   ├── quote.ts
│   │   ├── order.ts
│   │   ├── position.ts
│   │   ├── account.ts
│   │   └── index.ts
│   │
│   ├── assets/styles/              # Global styles
│   │   ├── reset.scss
│   │   └── common.scss
│   │
│   ├── components/
│   │   ├── common/                 # ErrorBoundary, PageLoading, PriceDisplay, ProfitLossBadge
│   │   ├── chart/                  # CandlestickChart, DepthChart
│   │   ├── quote/                  # SymbolTicker, SymbolList
│   │   ├── trade/                  # OrderForm, QuickOrder, OrderHistory
│   │   └── account/                # AccountBalance, PositionList
│   │
│   ├── composables/                # React Hooks
│   │   ├── useAuth.ts
│   │   ├── useOrder.ts
│   │   ├── useQuote.ts
│   │   └── useWebSocket.ts
│   │
│   ├── i18n/                       # Internationalization
│   │   ├── index.ts
│   │   ├── zh-CN.ts
│   │   └── en-US.ts
│   │
│   ├── layouts/
│   │   ├── MainLayout.tsx
│   │   └── components/
│   │       ├── Header.tsx
│   │       └── Sidebar.tsx
│   │
│   ├── pages/
│   │   ├── login/LoginPage.tsx
│   │   ├── trading/TradingPage.tsx
│   │   ├── positions/PositionsPage.tsx
│   │   ├── orders/OrdersPage.tsx
│   │   ├── account/
│   │   │   ├── AccountPage.tsx
│   │   │   ├── DepositPage.tsx
│   │   │   └── WithdrawPage.tsx
│   │   └── profile/ProfilePage.tsx
│   │
│   ├── router/
│   │   ├── index.tsx
│   │   ├── routes.tsx
│   │   └── guards.ts
│   │
│   ├── store/                      # Zustand state management
│   │   ├── userStore.ts
│   │   ├── quoteStore.ts
│   │   ├── orderStore.ts
│   │   ├── positionStore.ts
│   │   └── accountStore.ts
│   │
│   ├── types/                      # TypeScript type definitions
│   │   ├── api.d.ts
│   │   ├── order.d.ts
│   │   ├── quote.d.ts
│   │   ├── account.d.ts
│   │   └── common.d.ts
│   │
│   ├── utils/
│   │   ├── format.ts
│   │   ├── validation.ts
│   │   ├── storage.ts
│   │   └── request.ts
│   │
│   ├── App.tsx
│   ├── main.tsx
│   └── index.scss
│
├── .env.development
├── .env.production
├── vite.config.ts
├── tsconfig.json
├── package.json
└── pnpm-lock.yaml
```

### 3.2 Vue Admin Backend (fx-admin-web)

```
fx-admin-web/
├── src/
│   ├── api/
│   │   ├── axios.ts                # Axios wrapper
│   │   ├── system/user.ts, role.ts
│   │   ├── order/index.ts
│   │   ├── customer/index.ts
│   │   ├── risk/index.ts
│   │   └── index.ts
│   │
│   ├── components/
│   │   ├── common/                 # Pagination, ConfirmDialog, StatusTag, ActionButtons, ExportButton
│   │   ├── form/                   # SearchForm, DictSelect, DateRangePicker
│   │   └── table/                  # DataTable
│   │
│   ├── composables/
│   │   ├── useTable.ts
│   │   ├── useDialog.ts
│   │   ├── usePermission.ts
│   │   └── useDict.ts
│   │
│   ├── directives/
│   │   ├── permission.ts           # v-permission directive
│   │   └── loading.ts              # v-loading directive
│   │
│   ├── layouts/
│   │   ├── index.vue               # Main layout
│   │   ├── Sidebar.vue
│   │   ├── Header.vue
│   │   ├── TagsView.vue
│   │   └── AppMain.vue
│   │
│   ├── router/
│   │   ├── index.ts                # Main route definitions
│   │   └── routes/
│   │       ├── static.ts           # Login, 404, 403
│   │       └── async.ts            # Dynamic routes
│   │
│   ├── store/                      # Pinia state management
│   │   ├── user.ts
│   │   ├── permission.ts
│   │   └── settings.ts
│   │
│   ├── utils/
│   │   ├── auth.ts
│   │   ├── format.ts
│   │   ├── validate.ts
│   │   └── common.ts
│   │
│   ├── views/
│   │   ├── login/index.vue
│   │   ├── dashboard/index.vue
│   │   ├── order/list.vue, pending.vue
│   │   ├── position/list.vue
│   │   ├── account/list.vue, deposit.vue, withdraw.vue
│   │   ├── customer/list.vue, detail.vue, kyc.vue
│   │   ├── ib/list.vue, commission.vue
│   │   ├── risk/rules.vue, events.vue, blacklist.vue
│   │   ├── quote/symbols.vue
│   │   ├── report/trade.vue
│   │   ├── ticket/list.vue
│   │   ├── system/
│   │   │   ├── user/list.vue, form.vue
│   │   │   ├── role/list.vue
│   │   │   ├── menu/list.vue
│   │   │   ├── dict/list.vue
│   │   │   ├── tenant/list.vue
│   │   │   └── audit/list.vue
│   │   └── error/404.vue, 403.vue
│   │
│   ├── App.vue
│   ├── main.ts
│   └── permission.ts               # Route guard
│
├── .env.development                # VITE_API_BASE_URL=http://localhost:8080/api
├── .env.production
├── vite.config.ts                  # port: 3001
├── tsconfig.json
├── package.json
└── pnpm-lock.yaml
```

### 3.3 Flutter Mobile App (fx_trader_app)

```
fx_trader_app/
├── lib/
│   ├── main.dart                   # Entry: MultiBlocProvider + MaterialApp.router
│   │
│   ├── config/
│   │   ├── env_config.dart         # Environment config, token storage
│   │   ├── routes.dart             # GoRouter route definitions
│   │   └── theme.dart              # Material 3 themes
│   │
│   ├── api/
│   │   ├── api_client.dart         # Dio singleton + Bearer token interceptor
│   │   ├── quote_api.dart
│   │   ├── order_api.dart
│   │   └── account_api.dart
│   │
│   ├── bloc/                       # BLoC state management
│   │   ├── quote/                  # QuoteBloc, QuoteEvent, QuoteState
│   │   ├── order/                  # OrderBloc, OrderEvent, OrderState
│   │   └── account/                # AccountBloc, AccountEvent, AccountState
│   │
│   ├── models/
│   │   ├── quote_model.dart
│   │   ├── order_model.dart
│   │   ├── account_model.dart
│   │   └── position_model.dart
│   │
│   ├── repositories/
│   │   ├── quote_repository.dart
│   │   ├── order_repository.dart
│   │   └── account_repository.dart
│   │
│   ├── screens/
│   │   ├── login/LoginScreen.dart
│   │   ├── trading/TradingScreen.dart, ChartScreen.dart
│   │   ├── positions/PositionsScreen.dart
│   │   ├── account/AccountScreen.dart, DepositScreen.dart
│   │   └── settings/SettingsScreen.dart
│   │
│   └── widgets/
│       ├── chart/                  # CandlestickChart, PriceTicker
│       ├── order/                  # OrderForm, PositionCard
│       └── common/                 # LoadingIndicator, EmptyState
│
├── pubspec.yaml
└── pubspec.lock
```

## 4. Naming Conventions

### 4.1 Java Naming Conventions

| Type | Convention | Example |
|------|-----------|---------|
| Package | All lowercase | com.globalfx.trade.controller |
| Class | PascalCase | OrderController, OrderServiceImpl |
| Interface | PascalCase | OrderService |
| Method | camelCase | getOrderById, createOrder |
| Constant | UPPER_SNAKE_CASE | ORDER_STATUS_PENDING |
| Enum value | UPPER_SNAKE_CASE | PENDING("1", "Pending") |
| Variable | camelCase | orderList, accountBalance |
| Boolean | is/has/can prefix | isActive, hasPermission |
| DTO suffix | DTO | CreateOrderDTO, OrderQueryDTO |
| VO suffix | VO | OrderVO, AccountVO |
| Collection | Plural or List suffix | orders, orderList |

### 4.2 Go Naming Conventions

| Type | Convention | Example |
|------|-----------|---------|
| Package | All lowercase, short | quote, risk, trade |
| Struct | PascalCase | QuoteService, OrderHandler |
| Interface | PascalCase | Service, Repository |
| Variable | camelCase | orderList, accountBalance |
| Constant | PascalCase | HTTPStatusOK |
| Error variable | Err prefix | ErrOrderNotFound |

### 4.3 Frontend Naming Conventions

| Type | Convention | Example |
|------|-----------|---------|
| Directory | kebab-case | order-module, user-management |
| File (JS/TS) | kebab-case or PascalCase | order-service.ts or OrderService.ts |
| File (Vue) | PascalCase | OrderList.vue |
| Component | PascalCase | OrderList, UserForm |
| Variable | camelCase | orderList, userName |
| Constant | UPPER_SNAKE_CASE | MAX_PAGE_SIZE |
| CSS class | kebab-case | .order-list, .user-info |
| Store | camelCase | useOrderStore, useUserStore |

### 4.4 Flutter Naming Conventions

| Type | Convention | Example |
|------|-----------|---------|
| File | snake_case | order_service.dart |
| Class | PascalCase | OrderService, UserModel |
| Method | camelCase | getOrder(), createOrder() |
| Widget | PascalCase | OrderForm, PositionCard |

## 5. File Organization Standards

### 5.1 Module Division Principles

- High cohesion, low coupling: related functionality in the same module
- Single responsibility: each file has a single purpose
- Clear layering: organized by Controller / Service / Mapper

### 5.2 Directory Depth Limits

- Maximum directory nesting: 4 levels
- Maximum lines per file: 500 lines
- Split when exceeded
