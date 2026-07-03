# Global FX Trading Platform - Project Directory Structure Specification

## 1. Backend Directory Structure

### 1.1 Java Trading Core Service (fx-trade-core)

```
fx-trade-core/
├── src/main/java/com/globalfx/trade/
│   ├── GlobalFxTradeApplication.java          # Application startup class
│   │
│   ├── common/                                # Common module
│   │   ├── config/                          # Configuration classes
│   │   │   ├── AppConfig.java               # General configuration
│   │   │   ├── RedisConfig.java             # Redis configuration
│   │   │   ├── KafkaConfig.java             # Kafka configuration
│   │   │   └── WebConfig.java               # Web configuration
│   │   │
│   │   ├── constant/                        # Constant definitions
│   │   │   ├── OrderConstant.java           # Order-related constants
│   │   │   ├── TradeConstant.java           # Trade-related constants
│   │   │   └── SystemConstant.java          # System constants
│   │   │
│   │   ├── enums/                           # Enum classes
│   │   │   ├── OrderTypeEnum.java          # Order type
│   │   │   ├── OrderSideEnum.java           # Order side
│   │   │   ├── OrderStatusEnum.java         # Order status
│   │   │   ├── PositionStatusEnum.java      # Position status
│   │   │   └── RiskActionEnum.java         # Risk control action
│   │   │
│   │   ├── exception/                       # Exception classes
│   │   │   ├── BusinessException.java      # Business exception
│   │   │   ├── TradeException.java          # Trading exception
│   │   │   ├── RiskException.java           # Risk control exception
│   │   │   └── GlobalExceptionHandler.java # Global exception handler
│   │   │
│   │   ├── result/                          # Unified response
│   │   │   ├── Result.java                  # Response wrapper
│   │   │   ├── ResultCode.java             # Response code definition
│   │   │   └── PageResult.java             # Paginated response
│   │   │
│   │   └── util/                           # Utility classes
│   │       ├── IdGenerator.java            # ID generator
│   │       ├── DateUtil.java               # Date utility
│   │       ├── MoneyUtil.java              # Money utility
│   │       └── ValidationUtil.java         # Validation utility
│   │
│   ├── module/                              # Business modules
│   │   ├── order/                          # Order module
│   │   │   ├── controller/
│   │   │   │   └── OrderController.java
│   │   │   ├── service/
│   │   │   │   ├── OrderService.java
│   │   │   │   └── impl/
│   │   │   │       └── OrderServiceImpl.java
│   │   │   ├── mapper/
│   │   │   │   └── OrderMapper.java
│   │   │   ├── entity/
│   │   │   │   └── Order.java
│   │   │   ├── dto/
│   │   │   │   ├── CreateOrderDTO.java
│   │   │   │   └── OrderQueryDTO.java
│   │   │   └── vo/
│   │   │       └── OrderVO.java
│   │   │
│   │   ├── position/                       # Position module
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── mapper/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   └── vo/
│   │   │
│   │   ├── account/                        # Account module
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── mapper/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   └── vo/
│   │   │
│   │   ├── wallet/                         # Wallet module
│   │   │   └── (same structure as above)
│   │   │
│   │   ├── risk/                           # Risk control module
│   │   │   └── (same structure as above)
│   │   │
│   │   └── clearing/                       # Clearing module
│   │       └── (same structure as above)
│   │
│   └── security/                            # Security module
│       ├── JwtUtil.java                   # JWT utility
│       ├── JwtFilter.java                 # JWT filter
│       └── SecurityConfig.java             # Security configuration
│
├── src/main/resources/
│   ├── application.yml                     # Main configuration file
│   ├── application-dev.yml                 # Development environment
│   ├── application-test.yml               # Test environment
│   ├── application-prod.yml               # Production environment
│   └── mapper/                            # MyBatis XML
│       ├── OrderMapper.xml
│       ├── PositionMapper.xml
│       └── AccountMapper.xml
│
├── src/test/java/                         # Test code
│
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md
```

### 1.2 Go Market Data Service (fx-quote)

```
fx-quote/
├── cmd/
│   └── server/
│       └── main.go                        # Entry file
│
├── internal/
│   ├── config/
│   │   └── config.go                      # Configuration loading
│   │
│   ├── handler/                          # HTTP handler layer
│   │   ├── quote_handler.go
│   │   └── kline_handler.go
│   │
│   ├── service/                          # Business logic layer
│   │   ├── quote_service.go
│   │   ├── kline_service.go
│   │   └── aggregator_service.go
│   │
│   ├── repository/                       # Data access layer
│   │   ├── quote_repo.go
│   │   └── kline_repo.go
│   │
│   ├── model/                           # Data models
│   │   ├── quote.go
│   │   ├── kline.go
│   │   └── tick.go
│   │
│   ├── ws/                              # WebSocket handling
│   │   ├── server.go
│   │   └── client.go
│   │
│   └── proto/                           # Proto definitions
│       └── quote.proto
│
├── pkg/
│   ├── logger/                          # Logging wrapper
│   │   └── logger.go
│   ├── kafka/                           # Kafka wrapper
│   │   └── producer.go
│   └── redis/                           # Redis wrapper
│       └── client.go
│
├── go.mod
├── go.sum
├── Dockerfile
└── README.md
```

### 1.3 Go Risk Control Service (fx-risk)

```
fx-risk/
├── cmd/
│   └── server/
│       └── main.go
│
├── internal/
│   ├── config/
│   ├── handler/
│   ├── service/
│   │   ├── risk_service.go
│   │   ├── rule_engine.go              # Rule engine
│   │   └── anomaly_detector.go          # Anomaly detection
│   ├── repository/
│   ├── model/
│   └── engine/                          # Rule engine core
│       ├── context.go
│       ├── evaluator.go
│       └── rules/
│           ├── position_rule.go
│           ├── order_rule.go
│           └── margin_rule.go
│
├── pkg/
│   └── (common packages)
│
├── go.mod
└── Dockerfile
```

## 2. Frontend Directory Structure

### 2.1 React Trading Terminal (fx-trader-web)

```
fx-trader-web/
├── public/
│   ├── index.html
│   └── favicon.ico
│
├── src/
│   ├── api/                             # API request module
│   │   ├── client.ts                    # Axios wrapper
│   │   ├── quote.ts                     # Market data API
│   │   ├── order.ts                     # Order API
│   │   ├── position.ts                  # Position API
│   │   ├── account.ts                   # Account API
│   │   └── index.ts                     # Unified export
│   │
│   ├── assets/                          # Static resources
│   │   ├── images/                      # Images
│   │   │   ├── logo.png
│   │   │   └── icons/
│   │   └── styles/                      # Global styles
│   │       ├── variables.scss           # Variables
│   │       ├── reset.scss              # Reset styles
│   │       └── common.scss              # Common styles
│   │
│   ├── components/                      # Public components
│   │   ├── common/                      # Common components
│   │   │   ├── Loading.tsx
│   │   │   ├── ErrorBoundary.tsx
│   │   │   └── ProfitLossBadge.tsx
│   │   │
│   │   ├── chart/                       # Chart components
│   │   │   ├── CandlestickChart.tsx
│   │   │   ├── DepthChart.tsx
│   │   │   ├── LineChart.tsx
│   │   │   └── VolumeChart.tsx
│   │   │
│   │   ├── quote/                       # Market data components
│   │   │   ├── SymbolList.tsx
│   │   │   ├── SymbolTicker.tsx
│   │   │   └── PriceDisplay.tsx
│   │   │
│   │   ├── trade/                       # Trading components
│   │   │   ├── OrderForm.tsx
│   │   │   ├── QuickOrder.tsx
│   │   │   └── OrderHistory.tsx
│   │   │
│   │   └── account/                     # Account components
│   │       ├── AccountBalance.tsx
│   │       ├── PositionList.tsx
│   │       └── DepositForm.tsx
│   │
│   ├── composables/                      # Composable functions
│   │   ├── useWebSocket.ts              # WebSocket Hook
│   │   ├── useOrder.ts                  # Order Hook
│   │   ├── useQuote.ts                  # Market data Hook
│   │   └── useAuth.ts                   # Authentication Hook
│   │
│   ├── hooks/                            # Custom Hooks
│   │   └── (extended Hooks)
│   │
│   ├── layouts/                          # Layout components
│   │   ├── MainLayout.tsx               # Main layout
│   │   ├── TradingLayout.tsx             # Trading layout
│   │   └── components/
│   │       ├── Header.tsx
│   │       ├── Sidebar.tsx
│   │       └── Footer.tsx
│   │
│   ├── pages/                            # Page components
│   │   ├── login/
│   │   │   └── LoginPage.tsx
│   │   ├── trading/
│   │   │   ├── TradingPage.tsx           # Trading main page
│   │   │   └── ChartPage.tsx            # Chart page
│   │   ├── positions/
│   │   │   └── PositionsPage.tsx        # Position management
│   │   ├── orders/
│   │   │   └── OrdersPage.tsx           # Order history
│   │   ├── account/
│   │   │   ├── AccountPage.tsx          # Account overview
│   │   │   ├── DepositPage.tsx          # Deposit
│   │   │   └── WithdrawPage.tsx        # Withdrawal
│   │   └── profile/
│   │       ├── ProfilePage.tsx          # Personal center
│   │       └── KycPage.tsx             # KYC verification
│   │
│   ├── router/                           # Route configuration
│   │   ├── index.tsx                    # Route entry
│   │   ├── routes.ts                    # Route definitions
│   │   └── guards.ts                    # Route guards
│   │
│   ├── store/                            # State management (Zustand)
│   │   ├── quoteStore.ts                # Market data state
│   │   ├── orderStore.ts                # Order state
│   │   ├── positionStore.ts             # Position state
│   │   ├── accountStore.ts              # Account state
│   │   ├── userStore.ts                 # User state
│   │   └── index.ts                     # Store entry
│   │
│   ├── types/                            # TypeScript type definitions
│   │   ├── api.d.ts                     # API types
│   │   ├── order.d.ts                   # Order types
│   │   ├── quote.d.ts                   # Market data types
│   │   ├── account.d.ts                 # Account types
│   │   └── common.d.ts                  # Common types
│   │
│   ├── utils/                            # Utility functions
│   │   ├── format.ts                    # Formatting utilities
│   │   │   ├── formatMoney.ts           # Money formatting
│   │   │   ├── formatDate.ts           # Date formatting
│   │   │   └── formatNumber.ts         # Number formatting
│   │   ├── validation.ts                # Validation utilities
│   │   ├── storage.ts                    # Storage utilities
│   │   ├── crypto.ts                    # Cryptography utilities
│   │   └── request.ts                   # Request wrapper
│   │
│   ├── i18n/                             # Internationalization
│   │   ├── index.ts
│   │   ├── zh-CN.ts
│   │   └── en-US.ts
│   │
│   ├── App.tsx                           # Application entry
│   ├── main.tsx                          # Main entry
│   └── index.scss                        # Global style entry
│
├── .env.development                       # Development environment variables
├── .env.production                        # Production environment variables
├── .env.test                             # Test environment variables
├── vite.config.ts                        # Vite configuration
├── tsconfig.json                         # TypeScript configuration
├── tsconfig.node.json                    # Node TypeScript configuration
├── package.json
└── README.md
```

### 2.2 Vue Admin Backend (fx-admin-web)

```
fx-admin-web/
├── public/
│   └── index.html
│
├── src/
│   ├── api/                              # API requests
│   │   ├── axios.ts                      # Axios wrapper
│   │   ├── system/                       # System management
│   │   ├── order/                        # Order management
│   │   ├── customer/                     # Customer management
│   │   ├── risk/                         # Risk control management
│   │   └── index.ts
│   │
│   ├── assets/
│   │   ├── images/
│   │   └── styles/
│   │       ├── variables.scss
│   │       └── index.scss
│   │
│   ├── components/                       # Public components
│   │   ├── common/
│   │   │   ├── Pagination.vue
│   │   │   ├── ConfirmDialog.vue
│   │   │   ├── StatusTag.vue
│   │   │   ├── ActionButtons.vue
│   │   │   └── ExportButton.vue
│   │   │
│   │   ├── form/
│   │   │   ├── SearchForm.vue
│   │   │   ├── DictSelect.vue
│   │   │   ├── DateRangePicker.vue
│   │   │   └── RichEditor.vue
│   │   │
│   │   └── table/
│   │       └── DataTable.vue
│   │
│   ├── composables/                       # Composable functions
│   │   ├── useTable.ts
│   │   ├── useDialog.ts
│   │   ├── usePermission.ts
│   │   └── useDict.ts
│   │
│   ├── directives/                        # Custom directives
│   │   ├── permission.ts
│   │   └── loading.ts
│   │
│   ├── layouts/                           # Layout components
│   │   ├── index.vue                     # Main layout
│   │   ├── Sidebar.vue
│   │   ├── Header.vue
│   │   ├── TagsView.vue
│   │   └── AppMain.vue
│   │
│   ├── router/                            # Routes
│   │   ├── index.ts
│   │   ├── routes/
│   │   │   ├── static.ts
│   │   │   └── async.ts
│   │   └── permission.ts
│   │
│   ├── store/                             # State management (Pinia)
│   │   ├── index.ts
│   │   ├── user.ts
│   │   ├── permission.ts
│   │   └── settings.ts
│   │
│   ├── types/                             # Type definitions
│   │
│   ├── utils/                             # Utility functions
│   │   ├── validate.ts
│   │   ├── format.ts
│   │   ├── auth.ts
│   │   └── common.ts
│   │
│   ├── views/                             # Pages
│   │   ├── dashboard/
│   │   │   └── index.vue
│   │   ├── login/
│   │   │   └── index.vue
│   │   ├── order/
│   │   │   ├── list.vue
│   │   │   └── pending.vue
│   │   ├── customer/
│   │   │   ├── list.vue
│   │   │   ├── detail.vue
│   │   │   └── kyc.vue
│   │   ├── risk/
│   │   │   ├── rules.vue
│   │   │   ├── events.vue
│   │   │   └── blacklist.vue
│   │   ├── system/
│   │   │   ├── user/
│   │   │   │   ├── list.vue
│   │   │   │   └── form.vue
│   │   │   ├── role/
│   │   │   └── menu/
│   │   └── error/
│   │       ├── 404.vue
│   │       └── 403.vue
│   │
│   ├── App.vue
│   ├── main.ts
│   └── permission.ts
│
├── .env.development
├── .env.production
├── vite.config.ts
├── tsconfig.json
├── package.json
└── README.md
```

## 3. Naming Conventions

### 3.1 Java Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Package name | All lowercase | com.globalfx.trade.module.order |
| Class name | PascalCase | OrderController, OrderServiceImpl |
| Interface name | PascalCase | OrderService |
| Method name | camelCase | getOrderById, createOrder |
| Constants | UPPER_SNAKE_CASE | ORDER_STATUS_PENDING |
| Enum values | UPPER_SNAKE_CASE | PENDING("1", "Pending") |
| Variables | camelCase | orderList, accountBalance |
| Boolean variables | is/has/can prefix | isActive, hasPermission |
| DTO suffix | DTO | CreateOrderDTO, OrderQueryDTO |
| VO suffix | VO | OrderVO, AccountVO |
| Collection variables | plural or List suffix | orders, orderList |

### 3.2 Go Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Package name | All lowercase, short | quote, risk, trade |
| Struct name | PascalCase | QuoteService, OrderHandler |
| Interface name | PascalCase | Service, Repository |
| Variables | camelCase | orderList, accountBalance |
| Constants | PascalCase | HTTPStatusOK |
| Error variables | Err prefix | ErrOrderNotFound |
| Function name | PascalCase or camelCase | GetOrder, getOrder |

### 3.3 Frontend Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Directory name | kebab-case | order-module, user-management |
| File name (JS/TS) | kebab-case | order-service.ts, user-api.ts |
| File name (Vue/Svelte) | PascalCase | OrderList.vue, UserForm.vue |
| Component name | PascalCase | OrderList, UserForm |
| Variable name | camelCase | orderList, userName |
| Constants | UPPER_SNAKE_CASE | MAX_PAGE_SIZE |
| CSS class name | kebab-case | .order-list, .user-info |
| Database field | snake_case | order_no, create_time |
| API path | kebab-case | /order/list, /user-info |
| Vuex/Pinia Store | camelCase | useOrderStore, useUserStore |

### 3.4 Flutter Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| File name | snake_case | order_service.dart, user_model.dart |
| Class name | PascalCase | OrderService, UserModel |
| Method name | camelCase | getOrder(), createOrder() |
| Variable name | camelCase | orderList, userName |
| Constants | UPPER_SNAKE_CASE | MAX_PAGE_SIZE |
| Widget name | PascalCase | OrderForm, PositionCard |

## 4. File Organization Standards

### 4.1 Module Division Principles

- High cohesion, low coupling: related functions in the same module
- Single responsibility: each file has a single responsibility
- Clear layering: organized by Controller/Service/Mapper layers

### 4.2 Directory Depth Limit

- Directory nesting should not exceed 4 levels
- Single file code lines should not exceed 500 lines
- Split if exceeded