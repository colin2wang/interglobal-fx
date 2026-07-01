# fx-risk

High-performance risk engine built with Go. Provides real-time rule evaluation, anomaly detection, and risk event management.

**Port**: 8083

## Features

- Configurable rule engine with priority-based evaluation
- Position limit, order limit, margin level rules
- Anomaly detection (frequency trading, wash trading, latency arbitrage)
- Real-time risk event recording
- Sub-millisecond rule evaluation

## Rules

| Rule | Condition | Action |
|------|-----------|--------|
| Position Limit | Position > max_value | Reject |
| Order Limit | Order size > max_value | Reject |
| Daily Limit | Daily volume > max_value | Reject |
| Margin Level | Margin level < min_value | Warning / Force close |
| Anomaly | >10 orders in 5s | Reject + Alert |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/risk/check` | Check order against rules |
| GET | `/api/v1/risk/events` | List risk events |
| POST | `/api/v1/risk/events/{id}/handle` | Handle risk event |

## Architecture

```
Order Request → Rule Evaluator → Decision (Pass/Reject/Warn)
                   ↓
            Risk Event Logger → Kafka (fx.risk.event)
```

## Configuration

```yaml
app:
  name: fx-risk
  port: 8083
risk:
  rules:
    max_position_lots: 50
    max_order_lots: 10
    max_daily_volume: 500
    margin_call_level: 100
    stop_out_level: 50
```

## Run

```bash
cd go-services/fx-risk
go mod tidy
go run cmd/server/main.go
```
