# fx-risk

Risk management service providing real-time order validation, risk rule engine, and event tracking.

**Port**: 8083

## Modules

| Component | Description |
|-----------|-------------|
| RiskRule | CRUD for configurable risk rules |
| RiskEvent | Risk event logging and tracking |
| RiskEngine | Real-time order/position risk checking |

## Risk Rules

| Rule Type | Description | Example |
|-----------|-------------|---------|
| Position Limit | Max position per symbol | ≤ 50 lots per symbol |
| Order Limit | Max single order size | ≤ 10 lots per order |
| Daily Limit | Max daily trading volume | ≤ 500 lots per day |
| Margin Level | Margin call threshold | ≤ 100% triggers warning |
| Anomaly Detection | Unusual trading patterns | >10 orders in 5 seconds |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/risk/rules` | List risk rules |
| POST | `/api/v1/risk/rules` | Create risk rule |
| PUT | `/api/v1/risk/rules/{id}` | Update risk rule |
| DELETE | `/api/v1/risk/rules/{id}` | Delete risk rule |
| GET | `/api/v1/risk/events` | List risk events |
| POST | `/api/v1/risk/events/{id}/handle` | Handle risk event |

## Risk Check Flow

```
Order Request → Rule Engine → Risk Evaluation → Pass/Reject
                          ↓
                   Check Rules (by priority):
                   1. Position limits
                   2. Order size limits
                   3. Daily volume limits
                   4. Margin requirements
                   5. Anomaly detection
```

## Run

```bash
mvn spring-boot:run -pl fx-risk
```
