# fx-report

Reporting and analytics service generating trade reports, customer analytics, risk reports, and dashboard summaries.

**Port**: 8086

## Modules

| Component | Description |
|-----------|-------------|
| TradeReport | Daily/monthly/yearly trade statistics |
| CustomerReport | Client analysis, retention, P&L distribution |
| RiskReport | Exposure analysis, VaR, stress testing |
| Dashboard | Real-time operational metrics |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/report/trade` | Trade report |
| GET | `/api/v1/report/customer` | Customer report |
| GET | `/api/v1/report/risk` | Risk report |
| GET | `/api/v1/dashboard/summary` | Dashboard summary |

## Dashboard Metrics

| Metric | Description |
|--------|-------------|
| Today's Volume | Total trading volume |
| Today's Orders | Order count and success rate |
| Active Clients | Clients trading today |
| Pending Events | Unhandled risk events |

## Report Retention

| Report Type | Retention |
|-------------|-----------|
| Trade reports | 5 years |
| Audit logs | 5 years |
| Regulatory reports | 10 years |

## Run

```bash
mvn spring-boot:run -pl fx-report
```
