# Global Huitong FX Trading Platform - Environment Configuration Document

## 1. Environment List

| Environment | Purpose | Web Domain | API Domain | Notes |
|-------------|---------|------------|------------|-------|
| Development (dev) | Daily development and debugging | localhost:3000 | localhost:8080 | Local startup |
| Test (test) | Functional/integration testing | test.fx-platform.com | api-test.fx-platform.com | Automated testing |
| Pre-release (staging) | Pre-launch verification | staging.fx-platform.com | api-staging.fx-platform.com | Production mirror |
| Production (prod) | Formal operation | fx-platform.com | api.fx-platform.com | High availability deployment |

## 2. Middleware Configuration

### 2.1 PostgreSQL Database

| Environment | Address | Port | Database Name | Username | Connection Pool |
|-------------|---------|------|---------------|----------|-----------------|
| dev | localhost | 5432 | fx_trading | fxuser | 5-20 |
| test | 10.0.1.101 | 5432 | fx_trading | fxuser | 10-50 |
| staging | 10.1.1.101 | 5432 | fx_trading | fxuser | 20-100 |
| prod | 10.2.1.101 | 5432 | fx_trading | fxuser | 50-200 |

**Database Partitioning Configuration:**
- fx_trading: Trading core database
- fx_account: Account database
- fx_quote: Market data database (TimescaleDB)
- fx_crm: CRM database
- fx_compliance: Compliance database
- fx_report: Report database
- fx_config: Configuration database

### 2.2 Redis Cache

| Environment | Address | Port | Password | DB Count |
|-------------|---------|------|----------|----------|
| dev | localhost | 6379 | - | 16 |
| test | 10.0.1.102 | 6379 | test_redis_pwd | 16 |
| staging | 10.1.1.102 | 6379 | staging_redis_pwd | 16 |
| prod | 10.2.1.102 | 6379 | prod_redis_pwd | 16 |

**Cluster Mode (Production):**
- 3 master, 3 slave architecture
- 2 replicas per master node
- Automatic failover

### 2.3 Kafka Message Queue

| Environment | Address | Port | Username | Password | Topic Count |
|-------------|---------|------|----------|----------|-------------|
| dev | localhost:9092 | 9092 | - | - | 20 |
| test | 10.0.1.103:9092 | 9092 | kafka | kafka_pwd | 50 |
| staging | 10.1.1.103:9092 | 9092 | kafka | kafka_pwd | 100 |
| prod | 10.2.1.103:9092 | 9092 | kafka | kafka_pwd | 200 |

**Core Topics:**
- fx.order.created: Order creation event
- fx.order.executed: Order execution event
- fx.position.updated: Position update event
- fx.quote.tick: Market data tick event
- fx.risk.event: Risk control event
- fx.audit.log: Audit log

### 2.4 Elasticsearch

| Environment | Address | Port | Purpose |
|-------------|---------|------|---------|
| dev | localhost:9200 | 9200 | Logs/Reports |
| test | 10.0.1.104:9200 | 9200 | Logs/Reports |
| staging | 10.1.1.104:9200 | 9200 | Logs/Reports/Search |
| prod | 10.2.1.104:9200 | 9200 | Logs/Reports/Search |

### 2.5 MinIO Object Storage

| Environment | Address | Port | AccessKey | SecretKey |
|-------------|---------|------|-----------|-----------|
| dev | localhost:9000 | 9000 | minioadmin | minioadmin |
| test | 10.0.1.105:9000 | 9000 | test_minio_ak | test_minio_sk |
| staging | 10.1.1.105:9000 | 9000 | staging_minio_ak | staging_minio_sk |
| prod | 10.2.1.105:9000 | 9000 | prod_minio_ak | prod_minio_sk |

**Bucket Planning:**
- fx-reports: Report files
- fx-contracts: Contract files
- fx-kyc: KYC materials
- fx-logs: System log archives

## 3. Third-party Service Configuration

### 3.1 SMS Service (Alibaba Cloud)

| Configuration Item | Value |
|--------------------|-------|
| AppKey | Obtain from Alibaba Cloud console |
| AppSecret | Obtain from Alibaba Cloud console |
| Signature | Global Huitong |
| Template ID | SMS_XXXXX |

### 3.2 Email Service

| Configuration Item | Value |
|--------------------|-------|
| SMTP Server | smtp.exmail.qq.com |
| Port | 465 (SSL) |
| Username | noreply@globalfx.com |
| Password | Obtain from email service provider |

### 3.3 KYC Service Provider

| Service Provider | Purpose | Configuration |
|------------------|---------|---------------|
| Jumio | ID recognition/facial verification | API Key + Secret |
| Sumsub | Backup KYC service | API Key + Secret |

### 3.4 Payment Channels

| Channel | Purpose | Merchant ID | Key |
|---------|---------|-------------|-----|
| Bank Transfer | Large deposits/withdrawals | - | - |
| Third-party Payment | Online payments | - | - |

### 3.5 LP Liquidity Providers

| LP Name | Connection Type | Configuration |
|---------|-----------------|---------------|
| LP-A | REST API | API Endpoint + Key |
| LP-B | WebSocket | WS Endpoint + Key |
| LP-C | FIX 4.4 | FIX Session Configuration |

## 4. Service Port Planning

### 4.1 Backend Service Ports

| Service | Port | Protocol | Description |
|---------|------|----------|-------------|
| fx-gateway | 8080 | HTTP | API Gateway |
| fx-trade-core | 8081 | gRPC | Trading Core |
| fx-quote | 8082 | gRPC | Market Data Service |
| fx-risk | 8083 | gRPC | Risk Control Service |
| fx-clearing | 8084 | gRPC | Clearing Service |
| fx-lp-bridge | 8085 | gRPC | LP Integration |
| fx-report | 8086 | HTTP | Report Service |
| fx-notify | 8087 | HTTP | Notification Service |
| fx-crm | 8088 | HTTP | CRM Service |
| fx-system | 8089 | HTTP | System Service |
| fx-mt-bridge | 8090 | Proprietary Protocol | MT Bridge |
| fx-fix-gateway | 8091 | FIX | FIX Gateway |

### 4.2 Frontend Service Ports

| Service | Port | Description |
|---------|------|-------------|
| fx-trader-web | 3000 | Trading terminal development |
| fx-admin-web | 3100 | Admin backend development |
| fx-admin-web | 80/443 | Production Nginx |

### 4.3 Middleware Ports

| Service | Port | Description |
|---------|------|-------------|
| PostgreSQL | 5432 | Master |
| PostgreSQL | 5433 | Replica 1 |
| PostgreSQL | 5434 | Replica 2 |
| Redis | 6379 | Standalone/Cluster |
| Kafka | 9092 | Broker |
| Kafka | 9093 | Replica |
| Zookeeper | 2181 | Kafka dependency |
| Elasticsearch | 9200 | HTTP |
| Kibana | 5601 | Visualization |
| MinIO | 9000 | API |
| MinIO Console | 9001 | Console |

## 5. Kubernetes Deployment Configuration

### 5.1 Production Cluster Planning

| Cluster | Purpose | Node Count | Specifications |
|---------|---------|------------|----------------|
| K8s-Dev | Development/Testing | 3 | 4C8G |
| K8s-Staging | Pre-release | 5 | 8C16G |
| K8s-Prod-AZ1 | Production Availability Zone 1 | 10 | 16C32G |
| K8s-Prod-AZ2 | Production Availability Zone 2 | 10 | 16C32G |
| K8s-Prod-AZ3 | Production Availability Zone 3 | 10 | 16C32G |

### 5.2 Production Environment Resource Configuration

| Service | CPU Request | CPU Limit | Memory Request | Memory Limit | Replicas |
|---------|-------------|-----------|----------------|--------------|----------|
| fx-gateway | 500m | 2000m | 512Mi | 2Gi | 3 |
| fx-trade-core | 1000m | 4000m | 1Gi | 4Gi | 5 |
| fx-quote | 1000m | 4000m | 1Gi | 4Gi | 3 |
| fx-risk | 500m | 2000m | 512Mi | 2Gi | 3 |
| fx-clearing | 500m | 2000m | 512Mi | 2Gi | 2 |
| fx-mt-bridge | 500m | 2000m | 512Mi | 2Gi | 2 |
| fx-fix-gateway | 500m | 2000m | 512Mi | 2Gi | 2 |

## 6. DNS Resolution Configuration

| Domain | Type | Target | Description |
|--------|------|--------|-------------|
| fx-platform.com | A | Load Balancer IP | Primary domain |
| api.fx-platform.com | CNAME | ALB | API Gateway |
| test.fx-platform.com | CNAME | ALB | Test environment |
| staging.fx-platform.com | CNAME | ALB | Pre-release |
| m.fx-platform.com | CNAME | CDN | Mobile |
| ws.fx-platform.com | CNAME | ALB | WebSocket |
| cdn.fx-platform.com | CNAME | CDN | Static resources |

## 7. SSL Certificate Configuration

| Domain | Certificate Type | Validity | Issuing Authority |
|--------|------------------|----------|-------------------|
| *.fx-platform.com | Wildcard | 1 year | DigiCert/Let's Encrypt |

## 8. Monitoring and Alerting Configuration

| Monitoring Item | Tool | Port | Description |
|-----------------|------|------|-------------|
| Application Monitoring | Prometheus | 9090 | Metrics Collection |
| Visualization | Grafana | 3000 | Chart Display |
| Distributed Tracing | Jaeger | 16686 | Request Tracing |
| Log Query | Kibana | 5601 | Log Analysis |
| Alert Notification | AlertManager | 9093 | Alert Aggregation |
