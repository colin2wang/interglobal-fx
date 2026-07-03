# Development Setup Guide

## System Requirements

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 21+ | Backend services |
| Maven | 3.9+ | Java build tool |
| Go | 1.21+ | Go microservices |
| Node.js | 20+ | Frontend runtime |
| pnpm | 9+ | Frontend package manager |
| Docker | 24+ | Container runtime |
| Docker Compose | v2 | Multi-container orchestration |
| Git | 2.40+ | Version control |

## Repository Structure

```
interglobal-fx/
├── backend/              # Java Spring Boot services (Maven multi-module)
├── go-services/          # Go microservices
├── frontend/
│   ├── fx-admin-web/     # Vue3 admin panel
│   ├── fx-trader-web/    # React trading terminal
│   └── fx_trader_app/    # Flutter mobile app
├── docs/                 # Project documentation
│   ├── cn/               # Chinese docs
│   └── en/               # English docs
├── docker-compose.yml    # Full stack orchestration
└── README.md
```

## Step 1: Start Infrastructure

```bash
docker compose up -d postgres redis kafka zookeeper
```

Wait for healthy status:
```bash
docker compose ps
```

## Step 2: Initialize Database

```bash
# Create all databases
psql -h localhost -U fxuser -d postgres -c "
CREATE DATABASE fx_trading;
CREATE DATABASE fx_config;
CREATE DATABASE fx_crm;
CREATE DATABASE fx_report;
"

# Apply schema and data
psql -h localhost -U fxuser -d fx_trading -f backend/sql/01-schema.sql
psql -h localhost -U fxuser -d fx_trading -f backend/sql/03-init-data.sql
```

## Step 3: Build and Run Java Services

```bash
cd backend

# Install dependencies
mvn clean install -DskipTests

# Run individual services (in separate terminals)
java -jar fx-system/target/fx-system-1.0.0.jar
java -jar fx-trade/target/fx-trade-1.0.0.jar
java -jar fx-risk/target/fx-risk-1.0.0.jar
java -jar fx-clearing/target/fx-clearing-1.0.0.jar
java -jar fx-crm/target/fx-crm-1.0.0.jar
java -jar fx-report/target/fx-report-1.0.0.jar
```

## Step 4: Build and Run Go Services

```bash
cd go-services/fx-quote
go mod download
go run ./cmd/server

# Repeat for other Go services
cd ../fx-risk && go run ./cmd/server
cd ../fx-mt-bridge && go run ./cmd/server
cd ../fx-fix-gateway && go run ./cmd/server
```

## Step 5: Run Frontend

### Admin Panel (Vue3)
```bash
cd frontend/fx-admin-web
pnpm install
pnpm run dev    # http://localhost:3001
```

### Trading Terminal (React)
```bash
cd frontend/fx-trader-web
pnpm install
pnpm run dev    # http://localhost:3000
```

### Mobile App (Flutter)
```bash
cd frontend/fx_trader_app
flutter pub get
flutter run
```

## IDE Setup

### IntelliJ IDEA
- Import as Maven project
- Set JDK to Java 21
- Enable Lombok plugin
- Set up Spring Boot run configurations

### VS Code
- Install extensions: Volar, ESLint, Prettier, Tailwind CSS
- Open `frontend/fx-admin-web` or `frontend/fx-trader-web` as workspace

## API Documentation

Once fx-system is running:
- Swagger UI: http://localhost:8089/doc.html (Knife4j)
- OpenAPI JSON: http://localhost:8089/v3/api-docs

## Useful Commands

```bash
# Maven
mvn clean install -DskipTests    # Build all
mvn test                         # Run tests
mvn spring-boot:run -pl fx-trade # Run single service

# Go
go test ./...                    # Run tests
go vet ./...                     # Static analysis
golangci-lint run                # Lint

# Frontend
pnpm run dev                     # Dev server
pnpm run build                   # Production build
pnpm run lint                    # ESLint check
pnpm run format                  # Prettier format
```

## Troubleshooting

| Problem | Solution |
|---------|----------|
| Port already in use | Check with `lsof -i :PORT` and kill the process |
| Database connection refused | Ensure PostgreSQL is running: `docker compose ps postgres` |
| Kafka connection timeout | Wait for Kafka to be ready: `docker compose logs kafka` |
| Node modules not found | Run `pnpm install` in the frontend directory |
| Maven build fails | Run `mvn clean install -DskipTests` from backend root |
