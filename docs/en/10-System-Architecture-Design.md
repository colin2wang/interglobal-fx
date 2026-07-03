# Interglobal FX Trading Platform - System Architecture Design

## 1. Architecture Overview

| Configuration | Value |
|--------|-----|
| Architecture Pattern | Microservices Architecture + Frontend-Backend Separation |
| Trading Core | Java 17 + Spring Boot 3 (Strong Consistency) |
| Market Data Service | Go 1.21+ (Low-latency Real-time Market Data) |
| Risk Control Engine | Go + Rule Engine (Millisecond-level Risk Control) |
| Web Frontend | React 18 + TypeScript + Ant Design Pro |
| Admin Backend | Vue3 + Element Plus |
| Mobile | Flutter (iOS/Android) |
| Database | PostgreSQL 15 + TimescaleDB (Time-series Data) |
| Cache | Redis Cluster 7 |
| Message Queue | Apache Kafka |
| Search Engine | Elasticsearch 8.x |
| File Storage | MinIO (Object Storage) |
| Service Mesh | Kubernetes (Container Orchestration) |
| Service Registry | Consul (Service Discovery) |
| Distributed Tracing | Jaeger + SkyWalking |
| Log Collection | ELK Stack (Elasticsearch + Logstash + Kibana) |

## 2. System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              Client Layer                                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │  Web Trading │  │  Web Admin  │  │  Mobile App │  │  MT4/MT5    │        │
│  │  (React)    │  │   (Vue3)    │  │  (Flutter)  │  │   Bridge    │        │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘        │
└─────────┼────────────────┼────────────────┼────────────────┼────────────────┘
          │                │                │                │
          └────────────────┴────────────────┴────────────────┘
                                   │
                          ┌────────▼────────┐
                          │    API Gateway   │
                          │   (Kong/Nginx)   │
                          │ Routing/Auth/Rate Limiting │
                          └────────┬────────┘
                                   │
┌──────────────────────────────────┼──────────────────────────────────────────┐
│                                  │              Application Service Layer    │
│  ┌───────────────┐  ┌────────────▼───────┐  ┌───────────────┐              │
│  │  Trading Core  │  │  Market Data (Go)  │  │  Risk Control │              │
│  │  Java/Spring   │  │  Real-time/WebSocket│  │  Go/Rule Engine│             │
│  │  Orders/Positions│ │  K-line Generation/Aggregation│  │  Real-time Monitoring│  │
│  └───────┬───────┘  └─────────┬─────────┘  └───────┬───────┘              │
│          │                    │                    │                        │
│  ┌───────▼───────────────────▼────────────────────▼───────┐               │
│  │                      Kafka Message Bus                  │               │
│  │  Order Events/Market Events/Risk Events/Audit Events   │               │
│  └──────────────────────────┬─────────────────────────────┘               │
│                             │                                              │
│  ┌───────────────┐  ┌──────▼───────┐  ┌───────────────┐  ┌────────────┐  │
│  │  Clearing     │  │  LP Bridge   │  │  Report       │  │  Notify    │  │
│  │  EOD Clearing │  │  Multi-LP    │  │  Statistics   │  │  Multi-channel│ │
│  │              │  │  Routing     │  │  Reports      │  │  Push      │  │
│  └───────────────┘  └──────────────┘  └───────────────┘  └────────────┘  │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                        Infrastructure Layer                         │   │
│  │  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐   │   │
│  │  │PostgreSQL│ │TimescaleDB│ │  Redis   │ │Elasticsearch│ │  MinIO  │   │   │
│  │  │ Trading  │ │ K-line   │ │ Cache/Lock│ │ Logs/Reports│ │ File   │   │   │
│  │  │ Core     │ │ Data     │ │          │ │           │ │ Storage │   │   │
│  │  └──────────┘ └──────────┘ └──────────┘ └──────────┘ └──────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────────────────────┘
                                   │
┌──────────────────────────────────┼──────────────────────────────────────────┐
│                                  │              Operations Management Layer │
│  ┌───────────────┐  ┌────────────▼───────┐  ┌───────────────┐              │
│  │   K8s Cluster │  │   Monitoring &     │  │   Logging     │              │
│  │  Pod/Service  │  │   Alerting System  │  │   System      │              │
│  │  HPA/Rolling  │  │   Prometheus+Grafana│  │   ELK Stack  │              │
│  │  Upgrade      │  │   Tracing Jaeger   │  │   Loki       │              │
│  └───────────────┘  └────────────────────┘  └───────────────┘              │
└──────────────────────────────────────────────────────────────────────────────┘
```

## 3. Layered Architecture

### 3.1 Service Layering

```
┌────────────────────────────────────────────┐
│           Gateway Layer                    │  Routing, Authentication, Rate Limiting, Monitoring
├────────────────────────────────────────────┤
│           BFF Layer (Aggregation)          │  Frontend-Backend Data Adaptation, Aggregation
├────────────────────────────────────────────┤
│           Service Layer                    │  Business Logic Processing
├────────────────────────────────────────────┤
│           Infrastructure Layer             │  Database, Cache, Message Queue
└────────────────────────────────────────────┘
```

### 3.2 Core Service Modules

| Service Name | Tech Stack | Responsibility | Deployment Instances |
|----------|--------|------|----------|
| fx-gateway | Kong/Nginx | API Gateway, Routing, Authentication | 3+ |
| fx-trade-core | Java17/SpringBoot3 | Trading Core (Orders/Positions) | 5+ |
| fx-quote | Go1.21 | Market Data Service | 3+ |
| fx-risk | Go1.21 | Risk Control Engine | 3+ |
| fx-clearing | Java17/SpringBoot3 | Clearing Service | 2+ |
| fx-lp-bridge | Go1.21 | LP Bridge Service | 2+ |
| fx-report | Java17/SpringBoot3 | Report Service | 2+ |
| fx-notify | Java17/SpringBoot3 | Notification Service | 2+ |
| fx-crm | Java17/SpringBoot3 | CRM Service | 2+ |
| fx-system | Java17/SpringBoot3 | System Management Service | 2+ |
| fx-mt-bridge | Go1.21 | MT4/MT5 Bridge | 2+ |
| fx-fix-gateway | Go1.21 | FIX Protocol Gateway | 2+ |

### 3.3 Layer Responsibilities

| Layer | Responsibility | Technical Constraints |
|------|------|----------|
| Controller/BFF | Parameter Validation, Permission Check, Service Invocation, Response Wrapping | Must not contain business logic |
| Service | Business Logic, Transaction Control, Domain Model | Must not directly handle HTTP requests |
| Repository/Mapper | Data Persistence Operations | Must not contain business logic |
| Entity/Domain | Domain Model Definition | Must not be directly returned to external consumers |

## 4. Middleware Usage Rules

### 4.1 Redis Usage Standards

| Purpose | Key Naming Convention | Expiration Strategy |
|------|-------------|----------|
| Session Token | session:{tenantId}:{userId} | 30 minutes |
| Market Data Cache | quote:{symbol}:latest | Real-time update, 5s expiration |
| Position Cache | position:{accountId}:{positionId} | Real-time update |
| Account Balance Cache | account:{accountId}:balance | Real-time update |
| Rate Limiter | ratelimit:{ip}:{interface} | Sliding window 1 minute |
| Distributed Lock | lock:{resource}:{id} | 30s timeout |
| Leaderboard | leaderboard:{symbol}:daily | Cleared after daily settlement |

### 4.2 Kafka Usage Standards

| Topic | Partitions | Message Format | Consumer Group | Retention Period |
|-------|--------|----------|----------|----------|
| fx.order.created | 16 | JSON | order-service | 7 days |
| fx.order.executed | 16 | JSON | risk-service, clearing-service | 7 days |
| fx.position.updated | 8 | JSON | report-service | 30 days |
| fx.quote.tick | 32 | JSON | quote-service, risk-service | 1 day |
| fx.risk.event | 8 | JSON | notify-service | 30 days |
| fx.settlement.daily | 4 | JSON | report-service | 90 days |
| fx.audit.log | 16 | JSON | audit-service, es-sync | 5 years |

### 4.3 Database Usage Standards

| Database | Purpose | Connection Pool | Master-Slave Strategy |
|--------|------|--------|----------|
| fx_trading | Trading Core | HikariCP 20-100 | 1 Master, 2 Slaves |
| fx_account | Account Funds | HikariCP 20-100 | 1 Master, 2 Slaves |
| fx_quote | Market Data K-lines | HikariCP 10-50 | 1 Master, 2 Slaves |
| fx_crm | Customer CRM | HikariCP 10-50 | 1 Master, 2 Slaves |
| fx_compliance | Compliance Audit | HikariCP 10-50 | 1 Master, 2 Slaves |
| fx_report | Report Data | HikariCP 10-50 | Read-only Slave |

## 5. Security Architecture

### 5.1 Authentication & Authorization

| Security Item | Solution |
|--------|------|
| Authentication Method | JWT + OAuth2 |
| Token Storage | Redis Cluster (supports active invalidation) |
| Password Encryption | BCrypt (strength 12) |
| Sensitive Data | AES-256 Encrypted Storage |
| Transport Encryption | TLS 1.3 |
| API Signature | HMAC-SHA256 |

### 5.2 Protection Measures

| Threat Type | Protection Solution |
|----------|----------|
| SQL Injection | Parameterized Queries + MyBatis #{} |
| XSS Attack | Input Filtering + HTML Escaping |
| CSRF Attack | JWT Token Verification |
| Replay Attack | Request Sequence Number + Timestamp Validation |
| DDoS Attack | API Gateway Rate Limiting + CDN Protection |
| Brute Force Attack | Login Failure Count Limit + CAPTCHA |

### 5.3 Financial-grade Security

- Customer funds stored in segregated accounts
- Full operation audit logs (tamper-proof)
- Multi-level approval for sensitive operations
- Encrypted data backups
- Key separation management (HSM)

## 6. Performance Architecture

### 6.1 Performance Metrics

| Metric | Target | Description |
|------|--------|------|
| Trading Core Latency | < 10ms (P99) | Order Processing Time |
| Market Data Push Latency | < 50ms | End-to-end Latency |
| API Response Time | < 200ms | Non-trading Interfaces |
| System Throughput | >= 10,000 TPS | Order Processing Capacity |
| Concurrent Users | >= 100,000 | Peak Concurrency |
| System Availability | 99.99% | Annual Downtime < 52 minutes |

### 6.2 Performance Optimization Strategies

| Optimization Point | Strategy |
|--------|------|
| Database | Read-Write Splitting + Sharding + Index Optimization |
| Cache | Multi-level Caching (Local + Redis) + Cache Warming |
| Asynchronous | Kafka Async Messaging + Batch Processing |
| Connection Pool | Database Connection Pool + HTTP Connection Pool Reuse |
| Code | Hot Data Local Caching + Lock-free Algorithms |

## 7. High Availability Architecture

### 7.1 Deployment Architecture

```
                    ┌─────────────────┐
                    │   Load Balancer  │
                    │   (SLB/ALB)     │
                    └────────┬────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
┌───────▼───────┐    ┌───────▼───────┐    ┌───────▼───────┐
│  AZ A         │    │  AZ B         │    │  AZ C         │
│  ┌─────────┐  │    │  ┌─────────┐  │    │  ┌─────────┐  │
│  │ Trade   │  │    │  │ Trade   │  │    │  │ Trade   │  │
│  │ Service │  │    │  │ Service │  │    │  │ Service │  │
│  └─────────┘  │    │  └─────────┘  │    │  └─────────┘  │
│  ┌─────────┐  │    │  ┌─────────┐  │    │  ┌─────────┐  │
│  │  Quote  │  │    │  │  Quote  │  │    │  │  Quote  │  │
│  │ Service │  │    │  │ Service │  │    │  │ Service │  │
│  └─────────┘  │    │  └─────────┘  │    │  └─────────┘  │
└───────────────┘    └───────────────┘    └───────────────┘
```

### 7.2 Disaster Recovery Metrics

| Metric | Target | Description |
|------|--------|------|
| RPO | < 1 second | Recovery Point Objective, Zero Data Loss |
| RTO | < 30 seconds | Recovery Time Objective |
| Failover | Automatic | Master-Slave Switching Without Manual Intervention |
| Data Synchronization | Real-time | Cross-AZ Data Synchronization |

## 8. Monitoring & Alerting

### 8.1 Monitoring Metrics

| Category | Metric | Alert Threshold |
|------|------|----------|
| Trading Performance | Order Processing Latency P99 | > 10ms |
| Trading Performance | Market Data Push Latency | > 50ms |
| System Health | CPU Usage | > 80% |
| System Health | Memory Usage | > 85% |
| System Health | Disk Usage | > 90% |
| Business Metrics | Order Failure Rate | > 1% |
| Business Metrics | Account Balance Anomaly | > 0 |
| Risk Metrics | Risk Rejection Rate | > 5% |
| Risk Metrics | Forced Liquidation Trigger | Alert on Trigger |

### 8.2 Alert Notifications

| Level | Trigger Condition | Notification Method |
|------|----------|----------|
| P1 - Critical | System Down, Forced Liquidation Trigger | Phone + SMS + DingTalk |
| P2 - Severe | Latency Exceeded, Error Rate Rising | SMS + DingTalk |
| P3 - Normal | High Resource Usage | DingTalk/Email |
| P4 - Advisory | Routine Check | Email |

## 9. Logging Standards

### 9.1 Log Levels

| Level | Usage Scenario |
|------|----------|
| ERROR | System Exceptions, Uncaught Errors, Trading Failures |
| WARN | Business Exceptions, Risk Rejections, Recoverable Errors |
| INFO | Order Creation/Execution, Login/Logout, Batch Operations |
| DEBUG | Development Debugging, API Parameters, SQL Execution |
| TRACE | Detailed Debug Information |

### 9.2 Log Format

```
[2024-01-01 10:00:00.123] [INFO] [trace-id:550e8400-e29b-41d4-a716] 
[class:com.fx.trade.service.OrderService] - 
[orderNo:550e8400, accountId:12345, symbol:EURUSD, action:ORDER_CREATED]
```

### 9.3 Log Collection

- All service logs output to stdout uniformly
- Filebeat collects logs to Logstash
- Logstash parses and stores into Elasticsearch
- Kibana for visualization and querying
- Audit logs in separate index, retained for 5 years

## 10. Scalability Design

### 10.1 Horizontal Scaling

- Stateless services: Trading core, market data, risk control, etc. deployed statelessly
- Auto scaling: K8s HPA scales based on CPU/memory/custom metrics
- Database scaling: Sharding + Read-write splitting

### 10.2 Plugin-based Design

- LP integration: Plugin-style LP bridging, supporting rapid integration of new LPs
- Instrument support: Configuration-based instrument management, supporting new trading instruments
- Regulatory compliance: Region-specific compliance rule configuration

### 10.3 Technical Debt

- Core and non-core pathways separated
- Multi-level degradation at critical nodes
- Asynchronous non-critical processes
