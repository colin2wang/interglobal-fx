# fx-quote

High-performance real-time quote engine built with Go. Provides WebSocket streaming, K-line aggregation, and market data caching.

**Port**: 8082 (HTTP + WebSocket)

## Features

- Real-time bid/ask streaming via WebSocket
- Multi-period K-line generation (1m, 5m, 15m, 1h, 4h, 1d)
- Redis caching for latest quotes (5s TTL)
- Kafka producer for tick event distribution
- Multi-source quote aggregation

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/quote/symbols` | List all symbols |
| GET | `/api/v1/quote/prices` | Get real-time prices |
| GET | `/api/v1/quote/klines` | Get K-line data |
| WS | `/ws/quote` | Real-time quote stream |

## WebSocket Protocol

### Subscribe
```json
{
  "type": "subscribe",
  "symbols": ["EURUSD", "GBPUSD"]
}
```

### Quote Push
```json
{
  "type": "quote",
  "symbol": "EURUSD",
  "bid": "1.08500",
  "ask": "1.08520",
  "timestamp": 1704067200000
}
```

## Architecture

```
LP Feed → Aggregator → Redis Cache → WebSocket Push
                   ↓
              Kafka (fx.quote.tick)
                   ↓
              K-line Generator → TimescaleDB
```

## Configuration

```yaml
app:
  name: fx-quote
  port: 8082
redis:
  host: localhost
  port: 6379
kafka:
  brokers: [localhost:9092]
quote:
  cache:
    tick_ttl_seconds: 5
  kline:
    periods: [1m, 5m, 15m, 1h, 4h, 1d]
```

## Run

```bash
cd go-services/fx-quote
go mod tidy
go run cmd/server/main.go
```
