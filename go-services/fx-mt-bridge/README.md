# fx-mt-bridge

MT4/MT5 bridge service for integrating with MetaTrader platforms. Handles account synchronization, order forwarding, and position reporting.

**Port**: 8090 (TCP)

## Features

- MT4/MT5 Manager API integration
- Account creation and synchronization
- Order forwarding (new, modify, cancel)
- Execution report generation
- Position synchronization
- Balance and equity sync

## Protocol Flow

```
GlobalFX Core ←→ MT-Bridge ←→ MT4/MT5 Server
                     ↓
              Account Sync (balance, equity, margin)
              Order Sync (new orders, executions)
              Position Sync (open positions)
```

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/mt/accounts/sync` | Sync accounts from MT |
| POST | `/api/v1/mt/orders/forward` | Forward order to MT |
| POST | `/api/v1/mt/orders/execute` | Report execution to MT |
| GET | `/api/v1/mt/positions/sync` | Sync positions from MT |

## Configuration

```yaml
app:
  name: fx-mt-bridge
  port: 8090
mt:
  server:
    host: mt4-server
    port: 443
    login: manager_login
    password: manager_password
  sync:
    interval_seconds: 5
    retry_count: 3
```

## Run

```bash
cd go-services/fx-mt-bridge
go mod tidy
go run cmd/server/main.go
```
