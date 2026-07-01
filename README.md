# GlobalFX Trading Platform

A comprehensive B2B2C foreign exchange trading management platform built with microservices architecture, supporting MT4/MT5 integration, LP liquidity connectivity, multi-wallet systems, and 200+ currency pairs.

## Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│                          Client Layer                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │ React Web   │  │  Vue3 Admin │  │ Flutter App │              │
│  │  (Trading)  │  │ (Back Office)│  │  (Mobile)   │              │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘              │
└─────────┼────────────────┼────────────────┼──────────────────────┘
          └────────────────┴────────────────┘
                           │
                  ┌────────▼────────┐
                  │   API Gateway   │
                  │  (Spring Boot)  │
                  └────────┬────────┘
                           │
┌──────────────────────────┼──────────────────────────────────────┐
│                     Application Layer                            │
│  ┌──────────────┐  ┌────▼─────┐  ┌──────────────┐             │
│  │  fx-trade    │  │ fx-quote │  │  fx-risk     │             │
│  │  (Java)      │  │  (Go)    │  │  (Go)        │             │
│  └──────┬───────┘  └────┬─────┘  └──────┬───────┘             │
│         └───────────────┬┴───────────────┘                      │
│                    Kafka MQ                                      │
│  ┌──────────────┐  ┌────┴─────┐  ┌──────────────┐             │
│  │  fx-clearing │  │  fx-crm  │  │  fx-report   │             │
│  │  (Java)      │  │  (Java)  │  │  (Java)      │             │
│  └──────────────┘  └──────────┘  └──────────────┘             │
│  ┌──────────────┐  ┌──────────┐  ┌──────────────┐             │
│  │ fx-mt-bridge │  │fx-fix-gw │  │  fx-system   │             │
│  │  (Go)        │  │  (Go)    │  │  (Java)      │             │
│  └──────────────┘  └──────────┘  └──────────────┘             │
└─────────────────────────────────────────────────────────────────┘
                           │
┌──────────────────────────┼──────────────────────────────────────┐
│                     Infrastructure Layer                         │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐          │
│  │PostgreSQL│ │  Redis   │ │  Kafka   │ │  MinIO   │          │
│  │  + TSDB  │ │ Cluster  │ │          │ │          │          │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

## Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Trading Core | Java + Spring Boot | 21 / 3.2 |
| Quote Engine | Go + Gin | 1.21+ |
| Risk Engine | Go | 1.21+ |
| Trading Web | React + TypeScript + Vite | 18 / 5.x |
| Admin Web | Vue 3 + TypeScript + Vite | 3 / 5.x |
| Mobile App | Flutter + Dart | 3.x |
| Database | PostgreSQL + TimescaleDB | 15 |
| Cache | Redis | 7 |
| Message Queue | Apache Kafka | 3.x |
| Search | Elasticsearch | 8.x |
| Object Storage | MinIO | Latest |

## Project Structure

```
interglobal-fx/
├── docs/                          # Design documents (Chinese)
├── backend/                       # Java Spring Boot microservices
│   ├── pom.xml                    # Parent POM
│   ├── fx-common/                 # Shared utilities, exceptions, enums
│   ├── fx-system/                 # Auth, RBAC, users, roles, dict
│   ├── fx-trade/                  # Orders, positions, accounts, wallets
│   ├── fx-risk/                   # Risk rules and events
│   ├── fx-clearing/               # Daily settlement, swap, commission
│   ├── fx-crm/                    # Customers, KYC, IB partners, tickets
│   ├── fx-report/                 # Trade/customer/risk reports
│   └── sql/                       # Database DDL and seed data
├── go-services/                   # Go microservices
│   ├── fx-quote/                  # Real-time quote engine (WebSocket)
│   ├── fx-risk/                   # Risk rule engine
│   ├── fx-mt-bridge/              # MT4/MT5 bridge
│   └── fx-fix-gateway/            # FIX 4.2/4.4 protocol gateway
└── frontend/                      # Frontend applications
    ├── fx-trader-web/             # React trading terminal
    ├── fx-admin-web/              # Vue3 admin panel
    └── fx_trader_app/             # Flutter mobile app
```

## Quick Start

### Prerequisites

- Java 21+
- Go 1.21+
- Node.js 18+
- Flutter 3.x
- Docker & Docker Compose

### Database Setup

```bash
# Start PostgreSQL, Redis, Kafka
docker-compose -f docker/docker-compose-dev.yml up -d

# Initialize database
psql -U fxuser -d fx_trading -f backend/sql/01-schema.sql
psql -U fxuser -d fx_trading -f backend/sql/03-init-data.sql
```

### Backend (Java)

```bash
cd backend
mvn clean install -DskipTests

# Start individual services
java -jar fx-system/target/fx-system-1.0.0.jar
java -jar fx-trade/target/fx-trade-1.0.0.jar
```

### Backend (Go)

```bash
cd go-services/fx-quote
go mod tidy
go run cmd/server/main.go

cd go-services/fx-risk
go mod tidy
go run cmd/server/main.go
```

### Frontend (React)

```bash
cd frontend/fx-trader-web
npm install
npm run dev      # http://localhost:3000
```

### Frontend (Vue3)

```bash
cd frontend/fx-admin-web
npm install
npm run dev      # http://localhost:3100
```

### Frontend (Flutter)

```bash
cd frontend/fx_trader_app
flutter pub get
flutter run
```

## Service Endpoints

| Service | Port | Protocol | Description |
|---------|------|----------|-------------|
| fx-trade | 8081 | gRPC/HTTP | Trading core |
| fx-quote | 8082 | HTTP + WebSocket | Quote streaming |
| fx-risk | 8083 | gRPC/HTTP | Risk engine |
| fx-clearing | 8084 | HTTP | Settlement |
| fx-crm | 8088 | HTTP | Customer management |
| fx-report | 8086 | HTTP | Reports |
| fx-system | 8089 | HTTP | System management |
| fx-mt-bridge | 8090 | TCP | MT4/MT5 bridge |
| fx-fix-gateway | 8091 | FIX | FIX protocol |

## API Documentation

All REST APIs follow a unified response format:

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1704067200000,
  "requestId": "uuid"
}
```

Base path: `/api/v1`

| Module | Path | Description |
|--------|------|-------------|
| Auth | `/api/v1/auth/*` | Login, logout, token refresh |
| Quote | `/api/v1/quote/*` | Symbols, prices, K-lines |
| Order | `/api/v1/order/*` | Create, query, cancel orders |
| Position | `/api/v1/position/*` | Position management |
| Account | `/api/v1/account/*` | Account info, leverage |
| Wallet | `/api/v1/wallet/*` | Deposit, withdraw, transfer |
| Risk | `/api/v1/risk/*` | Risk rules and events |
| CRM | `/api/v1/customer/*` | Customer management |
| System | `/api/v1/system/*` | Users, roles, menus, dict |

## Key Features

- **Multi-asset Trading**: 200+ currency pairs, metals, indices, crypto
- **Real-time Quotes**: WebSocket streaming with <50ms latency
- **Risk Management**: Real-time margin monitoring, auto-liquidation, rule engine
- **Multi-tenant**: Support for multiple brokers and white-label partners
- **IB System**: Multi-level commission, sub-client management
- **KYC/AML**: Identity verification, sanctions screening
- **Compliance**: ESMA/FCA/ASIC/NFA/CIMA regulatory support
- **MT4/MT5 Bridge**: MetaTrader platform integration
- **FIX Gateway**: Institutional client connectivity

## Database Schema

27 tables covering:

- `cfg_tenant` - Multi-tenant configuration
- `usr_user`, `usr_role`, `usr_permission` - RBAC
- `sym_symbol` - Trading instruments
- `trd_order` - Order lifecycle
- `pos_position` - Open positions
- `acc_account` - Trading accounts
- `wlt_wallet`, `wlt_transaction` - Wallet system
- `rsk_rule`, `rsk_event` - Risk management
- `clr_daily_settlement` - Daily settlement
- `crm_customer`, `kyc_application` - Customer management
- `ib_partner` - IB partner system
- `tic_ticket`, `tic_reply` - Ticket system
- `aud_log` - Audit trail

## Environment Configuration

| Environment | API URL | Web URL |
|------------|---------|---------|
| Development | localhost:8089 | localhost:3000 |
| Staging | api-staging.globalfx.com | staging.globalfx.com |
| Production | api.globalfx.com | globalfx.com |

## Module Documentation

Each sub-project has its own README with detailed setup and API documentation.

### Java Backend

| Module | Path | Description |
|--------|------|-------------|
| [fx-common](backend/fx-common/README.md) | `backend/fx-common/` | Shared utilities, exceptions, enums, configs |
| [fx-system](backend/fx-system/README.md) | `backend/fx-system/` | Authentication, RBAC, users, roles, dict |
| [fx-trade](backend/fx-trade/README.md) | `backend/fx-trade/` | Orders, positions, accounts, wallets, quotes |
| [fx-risk](backend/fx-risk/README.md) | `backend/fx-risk/` | Risk rules, events, rule engine |
| [fx-clearing](backend/fx-clearing/README.md) | `backend/fx-clearing/` | Daily settlement, swap, commission |
| [fx-crm](backend/fx-crm/README.md) | `backend/fx-crm/` | Customers, KYC, IB partners, tickets |
| [fx-report](backend/fx-report/README.md) | `backend/fx-report/` | Trade/customer/risk reports, dashboard |

### Go Microservices

| Service | Path | Description |
|---------|------|-------------|
| [fx-quote](go-services/fx-quote/README.md) | `go-services/fx-quote/` | Real-time quote engine, WebSocket, K-lines |
| [fx-risk](go-services/fx-risk/README.md) | `go-services/fx-risk/` | High-performance risk rule engine |
| [fx-mt-bridge](go-services/fx-mt-bridge/README.md) | `go-services/fx-mt-bridge/` | MT4/MT5 platform bridge |
| [fx-fix-gateway](go-services/fx-fix-gateway/README.md) | `go-services/fx-fix-gateway/` | FIX 4.2/4.4 protocol gateway |

### Frontend Applications

| Project | Path | Description |
|---------|------|-------------|
| [fx-trader-web](frontend/fx-trader-web/README.md) | `frontend/fx-trader-web/` | React 18 trading terminal |
| [fx-admin-web](frontend/fx-admin-web/README.md) | `frontend/fx-admin-web/` | Vue 3 admin panel |
| [fx_trader_app](frontend/fx_trader_app/README.md) | `frontend/fx_trader_app/` | Flutter mobile app |

## License

Proprietary - GlobalFX Trading Platform
