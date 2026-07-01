# fx-trade

Core trading service managing orders, positions, accounts, wallets, and market symbols.

**Port**: 8081

## Modules

| Component | Description |
|-----------|-------------|
| Order | Create, query, cancel orders (market, limit, stop, take-profit) |
| Position | Open/close positions, partial close, P&L tracking |
| Account | Account info, balance, equity, margin calculation |
| Wallet | Multi-currency wallets, deposits, withdrawals, transfers |
| Symbol | Trading instrument management (200+ pairs) |
| Quote | Real-time price queries |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/order` | Create order |
| GET | `/api/v1/order` | List orders |
| GET | `/api/v1/order/{orderNo}` | Get order detail |
| DELETE | `/api/v1/order/{orderNo}` | Cancel order |
| GET | `/api/v1/position` | List positions |
| POST | `/api/v1/position/close` | Close position |
| GET | `/api/v1/account` | List accounts |
| GET | `/api/v1/account/{id}` | Get account detail |
| GET | `/api/v1/wallet/balance` | Get wallet balance |
| POST | `/api/v1/wallet/deposit` | Deposit funds |
| POST | `/api/v1/wallet/withdraw` | Withdraw funds |
| GET | `/api/v1/symbol` | List symbols |
| GET | `/api/v1/quote/prices` | Get real-time prices |
| GET | `/api/v1/quote/klines` | Get K-line data |

## Order Lifecycle

```
Created → Pending → Partially Filled → Filled
                     ↓                    ↓
                  Cancelled            Closed (position)
                     ↑
                  Rejected (risk check failed)
```

## Configuration

```yaml
server:
  port: 8081
fx:
  trade:
    max-slippage-points: 50
    position-merge-enabled: true
```

## Run

```bash
mvn spring-boot:run -pl fx-trade
```
