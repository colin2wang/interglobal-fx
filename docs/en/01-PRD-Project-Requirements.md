# Global Exchange FX Trading Platform - Project Requirements Document (PRD)

## 1. Project Overview

### 1.1 Project Background
As the global forex market continues to develop, the demand for forex trading services from individual and institutional investors is growing. The current market suffers from numerous pain points including fragmented trading systems, high market data latency, weak risk control capabilities, and low clearing efficiency. Global Exchange aims to build a secure, efficient, and compliant B2B2C forex trading management platform that integrates high-quality liquidity resources to provide one-stop forex trading services for retail traders, professional investors, and institutional clients.

### 1.2 Project Goals
- Build a complete forex trading ecosystem supporting MT4/MT5 integration and LP liquidity access
- Implement multi-wallet/multi-ledger system supporting 200+ currency pair trading
- Meet major global financial regulatory compliance requirements (ESMA/FCA/ASIC/NFA/CIMA)
- Trading latency below 10ms, market data latency below 50ms
- System availability reaching 99.99% (financial-grade SLA)

### 1.3 Project Scope
**In Scope:**
- Real-time market data system: multi-source market data integration, K-line data, depth chart
- Trading order system: market orders/limit orders/stop-loss/take-profit/pending orders/trailing stops
- Intelligent risk control system: real-time risk control, rule engine, anomaly detection
- Account and wallet system: multi-currency accounts, KYC/AML, fund management
- Clearing and settlement system: end-of-day clearing, P&L calculation, commission sharing
- Leverage and margin system: dynamic leverage, margin calculation, negative balance protection
- Liquidity management system: multi-LP access, routing strategies, B/A-Book hybrid
- Reporting and analytics system: trading reports, risk reports, regulatory reports
- Compliance and regulatory system: multi-region compliance configuration, client suitability
- Customer management system (CRM): customer profiles, tiered management, ticket system
- Broker/IB system: multi-level commissions, sub-client management, white-label support
- Notification and messaging system: multi-channel push, template management
- Operations management system: instrument management, parameter configuration, activity management
- Integration and connectivity system: MT4/MT5 bridging, FIX protocol, payment gateway
- System administration: multi-tenancy, RBAC, monitoring, disaster recovery

**Out of Scope:**
- Cryptocurrency trading (non-forex business)
- Stocks/futures/options and other financial products
- Manual trading/copy trading services (technology platform only)
- Offline branch/in-person account opening services

## 2. Target Users

### 2.1 User Profiles

| User Type | Characteristics | Core Needs | Usage Frequency |
|-----------|----------------|------------|-----------------|
| Retail Trader | Small capital (<$10,000), limited technical analysis skills, seeks convenience | Easy to use, convenient deposits, basic risk control | High frequency, 10-50 trades daily |
| Professional Trader | Medium capital ($10K-$500K), professional skills, needs API trading | High leverage, low spreads, API access, fast execution | Very high frequency, 100-500 trades daily |
| Institutional Investor | Large capital ($500K+), high compliance requirements, needs dedicated service | FIX connectivity, block trades, dedicated account manager | Medium-high frequency, on demand |
| Broker/IB | Holds client resources, seeks maximum profit sharing | Multi-level commissions, clear reports, fast withdrawals | 1-3 views daily |
| Account Manager | Responsible for client acquisition and retention, high KPI pressure | Client list, follow-up reminders, convenient approvals | Around the clock |
| Risk Control Staff | Responsible for market risk monitoring, abnormal trade handling | Real-time market monitoring, timely alerts, convenient handling | Around the clock |
| Operations Staff | Responsible for daily operations, maintaining system stability | Instrument management, activity configuration, parameter adjustment | Business hours |
| Compliance Staff | Responsible for compliance checks, regulatory report submission | Report generation, audit logs, compliance checks | Daily/weekly/quarterly reporting cycles |
| System Administrator | Responsible for system operations, permission management | System stability, clear permissions, convenient operations | On demand |

### 2.2 User Roles and Permissions

| Role | Description | Core Permissions |
|------|-------------|-----------------|
| Retail Trader | Regular individual client | View market data, place trades, deposit/withdraw, view positions and reports |
| Professional Trader | Qualified professional client | Higher leverage, lower spreads, API trading, large-volume trades |
| Institutional Investor | Corporate or high-net-worth client | FIX connectivity, white-label customization, dedicated service |
| Broker/IB | Client referral/distributor | Client management, commission viewing, sub-IB management |
| Account Manager | Responsible for client development and maintenance | Client assignment, follow-up records, submit for approval |
| Risk Control Specialist | Risk control monitoring execution | Risk control rule viewing, alert confirmation, limit adjustment requests |
| Risk Control Supervisor | Risk control decision management | Risk control rule configuration, forced liquidation execution, exemption approval |
| Operations Specialist | Daily operations execution | Instrument parameter configuration, activity creation, announcement publishing |
| Operations Supervisor | Operations management decision-making | Parameter approval, activity approval, system configuration |
| Compliance Specialist | Compliance execution checks | Report generation, compliance checks, audit queries |
| Compliance Supervisor | Compliance management approval | Compliance rule configuration, regulatory liaison, report review |
| System Administrator | System operations management | System monitoring, log viewing, service management |
| Super Administrator | Platform-wide management | Multi-tenant management, global configuration, role and permission management |

## 3. Functional Modules

### 3.1 Functional Module Overview

| Module ID | Module Name | Priority | Description |
|-----------|-------------|----------|-------------|
| M01 | Real-time Market Data System | P0 | Multi-source market data integration, real-time quotes, K-line data, depth chart |
| M02 | Trading Order System | P0 | All order types, position management, order lifecycle |
| M03 | Intelligent Risk Control System | P0 | Real-time risk control, rule engine, anomaly detection, forced liquidation |
| M04 | Account and Wallet System | P0 | Multi-currency accounts, fund management, KYC/AML |
| M05 | Clearing and Settlement System | P0 | End-of-day clearing, P&L calculation, commission sharing |
| M06 | Leverage and Margin System | P0 | Dynamic leverage, margin calculation, negative balance protection |
| M07 | Liquidity Management (LP) System | P0 | Multi-LP access, routing strategies, B/A-Book mode |
| M08 | Reporting and Analytics System | P1 | Trading reports, risk reports, regulatory reports |
| M09 | Compliance and Regulatory System | P1 | Multi-region compliance configuration, client suitability, audit logs |
| M10 | Customer Management System (CRM) | P1 | Customer profiles, tiered management, ticket system |
| M11 | Broker/IB System | P1 | Multi-level commissions, sub-client management, white-label support |
| M12 | Notification and Messaging System | P2 | Multi-channel push, message templates, notification management |
| M13 | Operations Management System | P1 | Instrument management, parameter configuration, activity management |
| M14 | Integration and Connectivity System | P0 | MT4/MT5 bridging, FIX protocol, payment gateway |
| M15 | System Administration | P0 | Multi-tenancy, RBAC, monitoring, disaster recovery |

## 4. Detailed Functional Module Design

### M01 - Real-time Market Data System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M01-F01 | Multi-source Market Data Integration | P0 | Support LP/bank/exchange feed multi-path access, auto-failover |
| M01-F02 | Currency Pair Management | P0 | 200+ currency pair maintenance, major/minor/exotic classification |
| M01-F03 | Real-time Quote Engine | P0 | Bid/Ask spread calculation, spread management, quote validity |
| M01-F04 | K-line Data Service | P0 | 1M/5M/15M/1H/4H/1D multi-period K-line generation and storage |
| M01-F05 | Depth Chart/Market Depth | P1 | Order book depth visualization, bid/ask display |
| M01-F06 | Market Data WebSocket Push | P0 | <50ms latency real-time push, support subscribe/unsubscribe |
| M01-F07 | Historical Market Data Query | P1 | Support time range queries, data export, playback function |

**Business Rules:**
1. Market data validity: normal quotes valid for 5 seconds, re-fetch required if expired
2. Spread limits: min/max spread thresholds set for each currency pair
3. Trading hours: based on exchange hours, support DST/winter time switching
4. Holiday configuration: support individual instrument non-trading period settings
5. Quote priority: proprietary LP > interbank > exchange

**Business Flow:**
1. Multi-source market data simultaneously feeds into the market data aggregation engine
2. Aggregation engine performs price quality validation, deduplication, and merging
3. Generates optimal Bid/Ask quotes, writes to Redis cache
4. Kafka message queue distributes to all subscription services
5. WebSocket service pushes to end users

---

### M02 - Trading Order System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M02-F01 | Market Order | P0 | Execute immediately at current best price |
| M02-F02 | Limit Order | P0 | Execute at specified price, support buy/sell limits |
| M02-F03 | Stop Loss Order | P0 | Trigger market order when price hits stop loss level |
| M02-F04 | Take Profit Order | P0 | Trigger position close when price hits take profit level |
| M02-F05 | Trailing Stop | P1 | Move stop loss price in favorable direction with price |
| M02-F06 | Pending Orders | P0 | Limit pending, stop pending, OCO pending |
| M02-F07 | Position Management | P0 | Open/close/partial close/reverse positions |
| M02-F08 | Batch Trading | P1 | Batch order placement, one-click close all positions |
| M02-F09 | Slippage Control | P0 | Set max slippage range, reject execution if exceeded |
| M02-F10 | Order History | P0 | Complete order lifecycle record query |
| M02-F11 | Electronic Signature | P1 | Trade confirmation electronic signature archiving |

**Business Rules:**
1. Order ID generation rule: timestamp + random number, unique and non-repeating
2. Order validity: GTC (good til canceled) / GFD (good for day) / GTD (good til date)
3. Minimum trade volume: independently set for each currency pair (typically starting from 0.01 lots)
4. Maximum trade volume: separate limits for per-order and per-account
5. Execution priority: price priority > time priority
6. Position aggregation: same currency pair and direction combined for display

**Business Flow:**
1. User submits order request (including instrument/direction/quantity/price)
2. Trading engine validation: sufficient funds/instrument tradable/limit check
3. Real-time risk control review: margin/exposure/anomaly detection
4. Order routing: select execution path based on B-Book/A-Book configuration
5. Execution processing: generate fill records, update positions, update balance
6. Event notification: Kafka push fill events, WebSocket push

---

### M03 - Intelligent Risk Control System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M03-F01 | Real-time Risk Monitoring | P0 | Margin rate/floating P&L/exposure real-time calculation |
| M03-F02 | Forced Liquidation Mechanism | P0 | Margin Call → partial forced close → full forced close three-level trigger |
| M03-F03 | Risk Control Rule Engine | P0 | Configurable rules: position limit/single order limit/daily limit |
| M03-F04 | Abnormal Trading Detection | P0 | Wash trading/latency arbitrage/multi-account correlation detection |
| M03-F05 | Blacklist/Graylist | P1 | Client/IP/device fingerprint blacklist management |
| M03-F06 | Risk Control Threshold Adjustment | P1 | Support dynamic adjustment by client/instrument/time period |
| M03-F07 | Risk Control Event Audit | P0 | All risk control actions recorded, traceable |

**Business Rules:**
1. Forced liquidation trigger conditions:
   - Margin Call: margin rate ≤ 100% (warning)
   - Partial forced close: margin rate ≤ 80%
   - Full forced close: margin rate ≤ 50%
2. Abnormal trading determination: more than 10 trades within 5 seconds by a single account is considered wash trading
3. Exposure limit: net exposure on a single currency pair must not exceed 200% of account equity
4. Risk control exemption: risk control supervisor can temporarily exempt specific accounts

**Business Flow:**
1. Real-time reception of order/position change events
2. Rule engine executes all risk control rules in parallel
3. Calculate comprehensive risk score, determine pass/fail
4. Exceeding-limit orders automatically rejected or flagged for review
5. Forced liquidation triggers automatic close position operation
6. Record complete risk control decision logs

---

### M04 - Account and Wallet System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M04-F01 | Multi-currency Account | P0 | USD/EUR/CNY/JPY/GBP and other major currencies |
| M04-F02 | Account Types | P0 | Trading account/demo account/VIP account classification |
| M04-F03 | Deposit/Withdrawal Management | P0 | Bank transfer/e-wallet/cryptocurrency deposits |
| M04-F04 | Internal Transfer | P0 | Fund transfer between sub-accounts |
| M04-F05 | KYC Identity Verification | P0 | Document upload/facial recognition/address proof |
| M04-F06 | AML Screening | P0 | Sanctions list screening/large suspicious transaction monitoring |
| M04-F07 | Account Freeze | P1 | Compliance/risk control account freeze/unfreeze |

**Business Rules:**
1. Account balance precision: 8 decimal places
2. Deposit review: deposits exceeding $10,000 require manual review
3. Withdrawal review: first withdrawal requires KYC approval
4. Transfer limit: single transfer up to $500,000, no daily cumulative limit
5. Demo account: funds are not real, for practice only

**Business Flow:**
1. User submits account opening application, uploads KYC materials
2. KYC service provider performs identity verification and facial comparison
3. AML system screens sanctions lists and high-risk lists
4. Account activated after compliance staff approval
5. Account balance updated after funds are received

---

### M05 - Clearing and Settlement System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M05-F01 | End-of-Day Clearing | P0 | Daily automatic execution of position clearing, P&L settlement |
| M05-F02 | P&L Calculation | P0 | Realized P&L/unrealized P&L real-time calculation |
| M05-F03 | Fee Calculation | P0 | Trading commissions/overnight interest (Swap) calculation |
| M05-F04 | Commission Sharing | P0 | IB/white-label profit sharing automatic calculation and settlement |
| M05-F05 | Reconciliation System | P0 | Internal ledger vs LP reconciliation vs bank reconciliation |
| M05-F06 | Settlement Report | P1 | Generate client/platform settlement reports |
| M05-F07 | Tax Reports | P1 | Support various countries' tax filing formats |

**Business Rules:**
1. Clearing time: daily automatic execution at UTC 00:00
2. Overnight interest: Wednesday overnight positions charged 3 days of interest
3. Commission settlement: T+1 settlement, support weekly/monthly settlement
4. Reconciliation discrepancies: amounts exceeding $0.01 flagged for resolution

**Business Flow:**
1. End-of-day clearing task triggered
2. Iterate all positions to calculate unrealized P&L
3. Closed positions calculate realized P&L
4. Calculate and deduct commissions and overnight interest
5. Generate IB commission sharing records
6. Reconcile with LP, generate reconciliation discrepancy report

---

### M06 - Leverage and Margin System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M06-F01 | Dynamic Leverage | P0 | Configurable 1:1 to 1:500, auto-adjust based on rules |
| M06-F02 | Margin Calculation | P0 | Used/available/margin level precise calculation |
| M06-F03 | Negative Balance Protection | P0 | Retail client losses do not exceed principal |
| M06-F04 | Regulatory Leverage Limits | P0 | ESMA/ASIC/FCA/NFA differentiated configuration |

**Business Rules:**
1. Major currency pair default leverage: retail 1:30, professional 1:100
2. Gold default leverage: retail 1:20, professional 1:50
3. Index product leverage: retail 1:5, professional 1:20
4. Dynamic adjustment: leverage automatically reduced when equity decreases
5. Negative balance protection: retail account minimum balance is 0

**Business Flow:**
1. Calculate required margin when user opens a position
2. Verify if account has sufficient available margin
3. Real-time update of used margin and margin level
4. Trigger leverage recalculation when equity changes
5. Margin insufficient triggers forced liquidation process

---

### M07 - Liquidity Management (LP) System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M07-F01 | Multi-LP Access | P0 | Unified access for banks/ECNs/liquidity providers |
| M07-F02 | LP Routing Strategy | P0 | Best price/round-robin/weighted allocation strategies |
| M07-F03 | B-Book Mode | P0 | Platform proprietary trading, market maker mode |
| M07-F04 | A-Book Mode | P0 | Orders directly hedged with LP, STP mode |
| M07-F05 | Hybrid Mode | P0 | B-Book + A-Book hybrid, automatic routing |
| M07-F06 | Hedging Management | P1 | Automatic hedging/manual hedging configuration |
| M07-F07 | LP Reconciliation | P0 | LP quotes/fills reconciled with platform |

**Business Rules:**
1. LP quote validity: 500ms
2. A-Book order execution: latency < 5ms
3. B-Book position hedging: intraday/end-of-day hedging optional
4. Routing strategy: weighted by spread/latency/trading volume

**Business Flow:**
1. LP continuously pushes quote stream
2. Quote aggregation engine calculates optimal price
3. Orders routed to B-Book or A-Book based on configuration
4. B-Book orders matched against platform positions
5. A-Book orders forwarded to LP for execution
6. End-of-day aggregation of all LP positions for hedging

---

### M08 - Reporting and Analytics System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M08-F01 | Trading Reports | P1 | Daily/monthly/yearly reports, support PDF/Excel export |
| M08-F02 | Client Analytics | P1 | Activity/retention/P&L distribution analysis |
| M08-F03 | Operations Metrics | P1 | Trading volume/revenue/spread income/overnight interest |
| M08-F04 | Risk Reports | P1 | Exposure analysis/VaR/stress testing |
| M08-F05 | Regulatory Reports | P0 | MiFID II/CFTC/ASIC compliance reports |
| M08-F06 | Custom Reports | P2 | Drag-and-drop report designer |

**Business Rules:**
1. Report retention period: 5 years (regulatory requirement)
2. Data masking: complete account information not displayed unless necessary
3. Report permissions: visibility controlled by role and client assignment

**Business Flow:**
1. User initiates report query request
2. System validates permissions and data scope
3. Generate aggregated data from ES/PG
4. Format output as PDF/Excel
5. Store in MinIO for download

---

### M09 - Compliance and Regulatory System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M09-F01 | Multi-region Compliance | P0 | ESMA/FCA/ASIC/NFA/CIMA differentiated configuration |
| M09-F02 | Client Suitability | P0 | Risk questionnaire/KID disclosure documents |
| M09-F03 | MiFIR Reporting | P1 | EU trade reporting real-time submission |
| M09-F04 | EMIR Reporting | P1 | Derivative trade reporting |
| M09-F05 | AML Monitoring | P0 | Large transaction/suspicious transaction monitoring |
| M09-F06 | Audit Logs | P0 | All operations recorded, tamper-proof |
| M09-F07 | GDPR Compliance | P1 | Data encryption/privacy protection/right to deletion |

**Business Rules:**
1. Client suitability: retail clients must complete risk assessment
2. Negative balance disclosure: must confirm awareness of risks before account opening
3. Trade reporting: real-time or T+1 submission to regulators
4. Data retention: audit logs retained for 5 years

**Business Flow:**
1. New client registration triggers suitability assessment process
2. Risk control events pushed to regulatory system in real-time
3. End-of-day batch generation of regulatory reports
4. Periodic audit log integrity verification

---

### M10 - Customer Management System (CRM)

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M10-F01 | Customer Profile | P1 | Personal information/trading preferences/risk assessment |
| M10-F02 | Customer Tiering | P1 | Retail/professional/institutional tiered management |
| M10-F03 | Account Manager Assignment | P1 | Client and account manager binding |
| M10-F04 | Ticket System | P1 | Complaint/appeal/inquiry ticket workflow |
| M10-F05 | Customer Notifications | P1 | Email/SMS/App push |
| M10-F06 | Lifecycle Management | P2 | Potential/active/dormant/churned stages |

**Business Rules:**
1. Client assignment: one client corresponds to one account manager
2. Public pool: clients inactive for 30 days enter public pool
3. Upgrade criteria: equity exceeding $100,000 can apply for VIP

---

### M11 - Broker/IB System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M11-F01 | IB Registration Review | P1 | Broker registration generation and qualification review |
| M11-F02 | Multi-level Commissions | P1 | Fixed/percentage/tiered commission configuration |
| M11-F03 | Sub-client Management | P1 | Management of clients developed by IBs |
| M11-F04 | IB Reports | P1 | Commission details/client reports |
| M11-F05 | White-label Support | P2 | Brand customization/domain customization/logo customization |

**Business Rules:**
1. Commission tiers: support up to 5-level IB structure
2. Commission types: trading commission/spread rebate/P&L sharing
3. Minimum withdrawal amount: $100

---

### M12 - Notification and Messaging System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M12-F01 | Trading Notifications | P0 | Order fills/forced liquidation alerts/insufficient margin |
| M12-F02 | Market News | P2 | Financial news push/calendar |
| M12-F03 | System Announcements | P1 | Platform announcements/activity notifications |
| M12-F04 | Multi-channel Push | P1 | App/email/SMS/in-app message |
| M12-F05 | Message Templates | P1 | Configurable message templates |

---

### M13 - Operations Management System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M13-F01 | Instrument Management | P1 | Enable/disable/trading hours/spread configuration |
| M13-F02 | Trading Hours | P1 | Winter/summer time/holiday adjustments |
| M13-F03 | Deposit/Withdrawal Approval | P1 | Large deposit/withdrawal manual approval |
| M13-F04 | Activity Management | P2 | Deposit bonuses/trading competitions/cashback |
| M13-F05 | Parameter Configuration | P1 | System-level parameter adjustment |
| M13-F06 | Operation Logs | P1 | All configuration changes recorded |

---

### M14 - Integration and Connectivity System

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M14-F01 | MT4/MT5 Bridging | P0 | Integration with MetaTrader platform |
| M14-F02 | FIX Protocol | P0 | Institutional client FIX 4.2/4.4 integration |
| M14-F03 | Payment Gateway | P1 | Multiple payment channel integration |
| M14-F04 | KYC Integration | P1 | Jumio/SumSub/Onfido integration |
| M14-F05 | Data Source Integration | P2 | News/economic calendar/analysis tools |

---

### M15 - System Administration

**Feature List:**

| Feature ID | Feature Name | Priority | Description |
|------------|--------------|----------|-------------|
| M15-F01 | Multi-tenancy | P0 | Multiple brokers independent operation |
| M15-F02 | Role and Permissions | P0 | RBAC permission system |
| M15-F03 | System Monitoring | P0 | Service health/trading latency/load |
| M15-F04 | Log Management | P0 | Structured logging/distributed tracing |
| M15-F05 | Disaster Recovery | P0 | RPO<1s, RTO<30s |
| M15-F06 | Canary Release | P1 | Version management/canary strategy |

---

## 5. Non-functional Requirements

### 5.1 Performance Requirements

| Metric | Requirement |
|--------|-------------|
| Core trading path latency | < 10ms (P99) |
| Market data push latency | < 50ms (end-to-end) |
| Market data query response | < 100ms |
| Order query response | < 200ms |
| Page initial load | < 3 seconds |
| System concurrent trading | >= 10,000 TPS |
| Simultaneous online users | >= 100,000 |

### 5.2 Security Requirements

| Metric | Requirement |
|--------|-------------|
| Authentication method | JWT + OAuth2 |
| Transport encryption | TLS 1.3 |
| Storage encryption | AES-256 |
| Password policy | Uppercase + lowercase + numbers + special characters, minimum 8 characters |
| Session timeout | Auto-logout after 30 minutes of inactivity |
| Audit logs | All operations recorded, non-deletable, tamper-proof |
| Data retention | 5-year retention for audit logs |

### 5.3 Availability Requirements

| Metric | Requirement |
|--------|-------------|
| System availability | 99.99% (financial-grade SLA) |
| Maximum annual downtime | < 52 minutes |
| Data backup | Real-time incremental backup + daily full backup |
| Disaster recovery | Same-city dual-active + off-site disaster recovery |
| RPO | < 1 second (zero data loss) |
| RTO | < 30 seconds (rapid recovery) |

### 5.4 Compliance Requirements

| Regulatory Region | Compliance Requirements |
|-------------------|------------------------|
| EU ESMA | Retail leverage limits, negative balance protection, standardized risk warnings |
| UK FCA | Client fund segregation, MiFID II reporting, FCA authorization |
| Australia ASIC | ASIC licensing, retail client classification, dispute resolution |
| US NFA | NFA membership qualification, CFTC reporting, 2% rule |
| Cayman CIMA | CIMA registration, CIMA reporting, anti-money laundering compliance |

---

## 6. Business Constraints

### 6.1 Technical Constraints
- Trading core uses Java 21 + Spring Boot 3
- Market data service uses Go 1.21+
- Frontend uses React 18 + TypeScript
- Database uses PostgreSQL 15 + TimescaleDB
- Message queue uses Kafka
- Cache uses Redis Cluster 7

### 6.2 Compliance Constraints
- All trades must be recorded for post-trade audit
- Client funds must be held in segregated accounts
- Cross-border transactions must comply with local regulatory requirements
- Cross-border data transfer must comply with GDPR requirements

### 6.3 Security Constraints
- Must pass Level 3 Security Protection certification
- Core trading data must be encrypted at rest
- DDoS attack protection capability required
- Replay attack prevention mechanism required
