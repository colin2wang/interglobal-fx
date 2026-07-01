# fx-clearing

Settlement and clearing service handling daily settlement, swap calculations, and commission distribution.

**Port**: 8084

## Modules

| Component | Description |
|-----------|-------------|
| Settlement | Daily settlement execution for all accounts |
| SwapCalculation | Overnight interest calculation (triple on Wednesday) |
| CommissionCalculation | IB commission calculation and distribution |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/settlement` | List settlement records |
| GET | `/api/v1/settlement/{id}` | Get settlement detail |
| POST | `/api/v1/settlement/execute` | Execute daily settlement |
| GET | `/api/v1/settlement/summary` | Get settlement summary |

## Settlement Process

```
1. Fetch all open positions
2. Calculate unrealized P&L per position
3. Apply swap (overnight interest)
   - Wednesday positions × 3 days
4. Calculate realized P&L for closed trades
5. Deduct commissions
6. Update account balance
7. Generate settlement record
8. Calculate IB commission splits
```

## Swap Calculation

| Day | Multiplier |
|-----|-----------|
| Monday | 1x |
| Tuesday | 1x |
| Wednesday | 3x (covers weekend) |
| Thursday | 1x |
| Friday | 1x |

## Run

```bash
mvn spring-boot:run -pl fx-clearing
```
