# fx-crm

Customer relationship management service handling customers, KYC verification, IB partners, and support tickets.

**Port**: 8088

## Modules

| Component | Description |
|-----------|-------------|
| Customer | Customer profiles, segmentation, lifecycle |
| KYC | Identity verification workflow |
| IB Partner | Introducing Broker management, commission tiers |
| Ticket | Support ticket system with SLA tracking |

## API Endpoints

### Customer

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/customer` | List customers |
| POST | `/api/v1/customer` | Create customer |
| GET | `/api/v1/customer/{id}` | Get customer detail |
| PUT | `/api/v1/customer/{id}` | Update customer |

### KYC

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/kyc` | List KYC applications |
| POST | `/api/v1/kyc/{id}/review` | Review KYC application |

### IB Partner

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/ib` | List IB partners |
| POST | `/api/v1/ib` | Create IB partner |
| GET | `/api/v1/ib/{id}` | Get IB detail |

### Ticket

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/ticket` | List tickets |
| POST | `/api/v1/ticket` | Create ticket |
| POST | `/api/v1/ticket/{id}/reply` | Reply to ticket |

## Customer Lifecycle

```
Potential → Active → Silent → Lost
              ↓
         VIP Upgrade (equity > $100K)
```

## KYC Workflow

```
Submitted → Under Review → Approved / Rejected
                ↓
         Additional Documents Required
```

## Run

```bash
mvn spring-boot:run -pl fx-crm
```
