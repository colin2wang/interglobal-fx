# Deployment Guide

## Prerequisites

- Docker 24+ and Docker Compose v2
- Java 21 JDK (for local builds)
- Go 1.21+ (for Go services)
- Node.js 20+ with pnpm (for frontend builds)

## Quick Start with Docker Compose

```bash
# Clone and enter the project
git clone <repo-url> && cd interglobal-fx

# Start all services
docker compose up -d

# Check status
docker compose ps

# View logs
docker compose logs -f fx-trade
```

This starts:
- **API Gateway:** Nginx (80) - routes all API requests
- **Infrastructure:** PostgreSQL (5432), Redis (6379), Kafka (9092), Zookeeper (2181)
- **Java services:** fx-system (8089), fx-trade (8081), fx-risk (8083), fx-clearing (8084), fx-crm (8085), fx-report (8086)
- **Go services:** fx-quote (8082), fx-risk-engine (8087), fx-mt-bridge (8088), fx-fix-gateway (8091)
- **Frontend:** fx-admin-web (3001), fx-trader-web (3000)

## Service Ports

| Service | Port | Description |
|---------|------|-------------|
| **api-gateway** | **80** | **API Gateway (Nginx) - all traffic enters here** |
| fx-admin-web | 3001 | Admin panel (Vue3) |
| fx-trader-web | 3000 | Trading terminal (React) |
| fx-system | 8089 | Auth, users, roles, permissions |
| fx-trade | 8081 | Orders, positions, accounts |
| fx-risk | 8083 | Risk control rules |
| fx-clearing | 8084 | Daily settlement, swap |
| fx-crm | 8085 | Customers, IB, tickets |
| fx-report | 8086 | Reports and statistics |
| fx-quote | 8082 | Real-time market data |
| fx-risk-engine | 8087 | Go risk engine |
| fx-mt-bridge | 8088 | MT4/MT5 bridge |
| fx-fix-gateway | 8091 | FIX protocol (TCP: 9876) |
| PostgreSQL | 5432 | Database |
| Redis | 6379 | Cache |
| Kafka | 9092 | Message queue |

## API Gateway Routing

All API requests should go through the gateway at `http://localhost:80`.

| Path Prefix | Routes To | Service |
|-------------|-----------|---------|
| `/api/v1/auth/*` | fx-system | Authentication |
| `/api/v1/users/*` | fx-system | User management |
| `/api/v1/roles/*` | fx-system | Role management |
| `/api/v1/orders/*` | fx-trade | Order management |
| `/api/v1/positions/*` | fx-trade | Position management |
| `/api/v1/accounts/*` | fx-trade | Account management |
| `/api/v1/symbols/*` | fx-trade | Symbol management |
| `/api/v1/risk/*` | fx-risk | Risk control |
| `/api/v1/customers/*` | fx-crm | Customer management |
| `/api/v1/ib/*` | fx-crm | IB partner management |
| `/api/v1/tickets/*` | fx-crm | Support tickets |
| `/api/v1/reports/*` | fx-report | Reports |
| `/api/v1/market/*` | fx-quote | Market data (Go) |
| `/api/v1/mt/*` | fx-mt-bridge | MT4/MT5 bridge (Go) |
| `/api/v1/fix/*` | fx-fix-gateway | FIX gateway (Go) |
| `/socket.io/*` | fx-trade | WebSocket (real-time) |
| `/ws/*` | fx-quote | WebSocket (quotes) |

## Building Java Services

```bash
cd backend

# Build all modules
mvn clean package -DskipTests

# Build a specific service
mvn clean package -pl fx-trade -am -DskipTests

# Run a service locally
java -jar fx-trade/target/fx-trade-1.0.0.jar --spring.profiles.active=dev
```

## Building Go Services

```bash
cd go-services/fx-quote

# Build
go build -o fx-quote ./cmd/server

# Run
./fx-quote -config config.yaml
```

## Building Frontend

```bash
cd frontend/fx-admin-web
pnpm install
pnpm run build    # Output: dist/

cd ../fx-trader-web
pnpm install
pnpm run build    # Output: dist/
```

## Environment Variables

### Java Services

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Profile (dev/test/prod) | `dev` |
| `DB_HOST` | PostgreSQL host | `localhost` |
| `DB_PORT` | PostgreSQL port | `5432` |
| `DB_NAME` | Database name | `fx_trading` |
| `DB_USERNAME` | Database user | `fxuser` |
| `DB_PASSWORD` | Database password | `fxpass` |
| `REDIS_HOST` | Redis host | `localhost` |
| `REDIS_PORT` | Redis port | `6379` |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka brokers | `localhost:9092` |

### Frontend

| Variable | Description | Default |
|----------|-------------|---------|
| `VITE_API_BASE_URL` | Backend API URL | `http://localhost:8080/api` |
| `VITE_WS_URL` | WebSocket URL | `ws://localhost:8080` |

## Database Initialization

```bash
# Apply schema
psql -h localhost -U fxuser -d fx_trading -f backend/sql/01-schema.sql

# Insert initial data
psql -h localhost -U fxuser -d fx_trading -f backend/sql/03-init-data.sql
```

## Stopping Services

```bash
# Stop all
docker compose down

# Stop and remove volumes (data loss)
docker compose down -v
```

## Production Considerations

- Use environment-specific `.env` files
- Configure TLS termination at load balancer
- Set up database backups (pg_dump cron)
- Configure Redis persistence (AOF/RDB)
- Set up monitoring (Prometheus + Grafana)
- Configure log aggregation (ELK Stack)
- Use Kubernetes for horizontal scaling
