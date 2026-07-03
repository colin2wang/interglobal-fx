# Interglobal FX Trading Platform - Database High-Level Design

## 1. Database Selection

| Configuration Item | Value |
|--------|-----|
| Database Type | PostgreSQL 15 + TimescaleDB (time-series data) |
| Character Set | UTF-8 |
| Collation | en_US.UTF-8 |
| Timezone Support | UTC (all timestamps stored as UTC) |
| Maximum Connections | 500 |
| Connection Pool | HikariCP, core 20, max 100 |
| Master-Slave Replication | Streaming replication (1 master, 2 slaves) |
| Read-Write Separation | Yes (read requests distributed to slave nodes) |

## 2. Database Inventory

| Database Name | Purpose | Included Modules | Storage Engine |
|----------|------|----------|----------|
| fx_trading | Trading core database | Orders, positions, clearing, risk control | PostgreSQL |
| fx_account | Account database | Accounts, wallets, KYC, fund transactions | PostgreSQL |
| fx_quote | Quote database | Currency pairs, K-line, real-time quotes | TimescaleDB |
| fx_crm | CRM database | Customers, IBs, tickets, follow-up records | PostgreSQL |
| fx_compliance | Compliance database | Compliance rules, audit logs, AML | PostgreSQL |
| fx_report | Report database | Reports, statistics, analytics data | PostgreSQL |
| fx_config | Configuration database | System parameters, dictionaries, permissions | PostgreSQL |

## 3. Naming Conventions

| Object | Convention | Example |
|------|------|------|
| Database name | lowercase + underscore | fx_trading |
| Table name | lowercase + underscore, module prefix | trd_order, acc_account |
| Field name | lowercase + underscore | create_time, user_id |
| Index name | idx_{table_name}_{field_name} | idx_trd_order_user_id |
| Unique index | uk_{table_name}_{field_name} | uk_acc_user_phone |
| Foreign key | No foreign key constraints (maintained at application layer) | - |
| Sequence | seq_{table_name} | seq_trd_order |
| View | v_{business_description} | v_active_positions |
| Function | fn_{function_description} | fn_calc_margin |
| Trigger | trg_{table_name}_{event} | trg_order_audit |

## 4. Table Naming Conventions

| Module Prefix | Database | Purpose |
|----------|------------|------|
| trd_ | fx_trading | Trading related |
| pos_ | fx_trading | Position related |
| rsk_ | fx_trading | Risk control related |
| clr_ | fx_trading | Clearing related |
| lp_ | fx_trading | Liquidity related |
| acc_ | fx_account | Account related |
| wlt_ | fx_account | Wallet related |
| kyc_ | fx_account | KYC related |
| txn_ | fx_account | Fund transactions |
| sym_ | fx_quote | Currency pairs/instruments |
| qte_ | fx_quote | Real-time quotes |
| knx_ | fx_quote | K-line data |
| crm_ | fx_crm | Customer management |
| ib_ | fx_crm | IB brokers |
| tic_ | fx_crm | Tickets |
| fll_ | fx_crm | Follow-up records |
| cmp_ | fx_compliance | Compliance module |
| aud_ | fx_compliance | Audit logs |
| rpt_ | fx_report | Reports related |
| cfg_ | fx_config | Configuration related |
| dic_ | fx_config | Dictionary related |
| usr_ | fx_config | User permissions |

## 5. Global Field Specifications

All business tables must include the following global fields:

| Field Name | Type | Required | Default Value | Description |
|--------|------|------|--------|------|
| id | BIGSERIAL | Yes | Auto-increment | Primary key |
| tenant_id | BIGINT | Yes | - | Tenant ID |
| created_by | BIGINT | Yes | - | Creator ID |
| created_time | TIMESTAMPTZ | Yes | NOW() | Creation time (UTC) |
| updated_by | BIGINT | No | NULL | Modifier ID |
| updated_time | TIMESTAMPTZ | No | NULL | Modification time (UTC) |
| is_deleted | SMALLINT | Yes | 0 | Soft delete flag (0-not deleted, 1-deleted) |
| remark | VARCHAR(500) | No | NULL | Remarks |

## 6. Field Type Specifications

| Business Type | PostgreSQL Type | Description |
|----------|----------------|------|
| Primary key ID | BIGSERIAL | Auto-increment 8-byte integer |
| Snowflake ID | BIGINT | Generated using sequence + node ID |
| Amount (precise) | DECIMAL(20,8) | Forex trading precision to 8 decimal places |
| Amount (display) | DECIMAL(15,2) | Page display amount |
| Price | DECIMAL(20,10) | Exchange rate precision up to 10 decimal places |
| Quantity/Lots | DECIMAL(15,5) | Trading volume precision 5 decimal places |
| Percentage | DECIMAL(10,6) | Such as margin rate, yield rate |
| Status | SMALLINT | Enumerated status values |
| Boolean | SMALLINT | 0-false 1-true |
| Timestamp | TIMESTAMPTZ | UTC timezone timestamp |
| Date | DATE | Date part only |
| Large text | TEXT | JSON/rich text/long descriptions |
| UUID | UUID | External system IDs, order numbers, etc. |
| IP address | INET | User IP, server IP |
| JSON | JSONB | Configuration parameters, extension fields |

## 7. Sharding Strategy

| Strategy | Description |
|------|------|
| Database sharding | Sharding by business module (7 databases) |
| Table sharding | Core trading tables sharded by time (monthly tables) |
| Sharding key | Trading tables: tenant_id + created_time |
| Routing algorithm | Hash modulo (tenant_id % 4) |
| Historical data | Data older than 1 year archived to cold storage |
| TimescaleDB | K-line/quote data using TimescaleDB hypertables |

## 8. Index Strategy

| Index Type | Use Case | Example |
|----------|----------|------|
| Primary key index | id field | PRIMARY KEY |
| Unique index | Unique fields such as account numbers, order numbers | UNIQUE(order_no) |
| Regular index | Query condition fields | CREATE INDEX idx_order_user ON trd_order(user_id) |
| Composite index | Multi-field combination queries | CREATE INDEX idx_order_user_status ON trd_order(user_id, status) |
| Partial index | Data with specific conditions | CREATE INDEX idx_active_orders ON trd_order(user_id) WHERE status = 1 |
| Expression index | Function queries | CREATE INDEX idx_order_date ON trd_order(DATE(created_time)) |
| GIN index | JSON field search | Full-text search for JSONB fields |

## 9. High Availability and Disaster Recovery

| Configuration Item | Description |
|--------|------|
| Streaming replication | 1 master, 2 slaves asynchronous streaming replication |
| Connection pool | PgBouncer connection pool proxy |
| Backup strategy | Daily full backup + real-time incremental WAL |
| Backup retention | 30 days |
| RPO | < 1 second |
| RTO | < 30 seconds |

## 10. Detailed Database Inventory

### fx_trading (Trading Core Database)

| Table Name | Chinese Name | Description |
|------|--------|------|
| trd_order | Order Table | All order lifecycle records |
| trd_order_his | Order History Table (Monthly) | Monthly archived order records |
| pos_position | Position Table | Current active positions |
| pos_position_his | Position History Table | Closed position records |
| rsk_rule | Risk Control Rule Table | Risk control rule configuration |
| rsk_event | Risk Control Event Table | Risk control trigger events |
| rsk_blacklist | Blacklist Table | Blacklist management |
| clr_daily_settlement | Daily Settlement Table | End-of-day clearing results |
| clr_trade_fee | Trading Fee Table | Commission/fee records |
| lp_provider | LP Provider Table | Liquidity provider configuration |
| lp_quote | LP Quote Records | LP quote logs |
| lp_hedge | Hedging Record Table | Hedging operation records |

### fx_account (Account Database)

| Table Name | Chinese Name | Description |
|------|--------|------|
| acc_account | Account Table | Customer main account |
| acc_sub_account | Sub-account Table | Sub-accounts (signals/EA, etc.) |
| wlt_wallet | Wallet Table | Multi-currency wallet |
| wlt_transaction | Wallet Transaction Table | Fund change records |
| kyc_application | KYC Application Table | KYC application records |
| kyc_document | KYC Document Table | ID/certification documents |
| txn_deposit | Deposit Record Table | Deposit applications and transactions |
| txn_withdrawal | Withdrawal Record Table | Withdrawal applications and transactions |
| txn_transfer | Transfer Record Table | Internal transfer records |

### fx_quote (Quote Database - TimescaleDB)

| Table Name | Chinese Name | Description |
|------|--------|------|
| sym_symbol | Currency Pair Table | Trading instrument configuration |
| sym_session | Trading Session Table | Instrument trading time configuration |
| qte_tick | Tick Quote Table | Real-time tick data |
| qte_book | Order Book Table | Market depth data |
| knx_1m | 1-Minute K-line Table | 1-minute K-line data |
| knx_5m | 5-Minute K-line Table | 5-minute K-line data |
| knx_15m | 15-Minute K-line Table | 15-minute K-line data |
| knx_1h | 1-Hour K-line Table | 1-hour K-line data |
| knx_4h | 4-Hour K-line Table | 4-hour K-line data |
| knx_1d | Daily K-line Table | Daily K-line data |

### fx_crm (CRM Database)

| Table Name | Chinese Name | Description |
|------|--------|------|
| crm_customer | Customer Table | Customer basic information |
| crm_contact | Contact Record Table | Customer contact history |
| crm_segment | Customer Segmentation Table | Customer segmentation configuration |
| ib_partner | IB Partner Table | IB broker information |
| ib_commission | Commission Table | IB commission records |
| ib_hierarchy | IB Hierarchy Table | IB superior-subordinate relationships |
| tic_ticket | Ticket Table | Complaint/inquiry tickets |
| tic_reply | Ticket Reply Table | Ticket processing records |
| fll_follow | Follow-up Record Table | Customer follow-up records |

### fx_compliance (Compliance Database)

| Table Name | Chinese Name | Description |
|------|--------|------|
| cmp_region | Regulatory Region Table | Regulatory region configuration |
| cmp_rule | Compliance Rule Table | Regional compliance rules |
| cmp_report | Regulatory Report Table | Generated regulatory reports |
| aud_log | Audit Log Table | All-operation audit logs |
| aml_transaction | AML Transaction Table | Large/suspicious transaction records |
| aml_sanction | Sanctions List Table | AML sanctions list |

### fx_report (Report Database)

| Table Name | Chinese Name | Description |
|------|--------|------|
| rpt_trade_daily | Daily Trading Report | Daily trading statistics |
| rpt_customer | Customer Analysis Table | Customer analytics data |
| rpt_risk | Risk Report Table | Risk indicator data |
| rpt_ib_commission | IB Commission Report | IB commission summary |

### fx_config (Configuration Database)

| Table Name | Chinese Name | Description |
|------|--------|------|
| cfg_tenant | Tenant Table | Multi-tenant configuration |
| cfg_parameter | System Parameter Table | System parameter configuration |
| cfg_region_setting | Region Setting Table | Regional differentiation configuration |
| dic_type | Dictionary Type Table | Enumeration type definitions |
| dic_data | Dictionary Data Table | Enumeration value data |
| usr_user | User Table | System users |
| usr_role | Role Table | Role definitions |
| usr_permission | Permission Table | Permission definitions |
| usr_user_role | User-Role Table | User-role associations |
| usr_role_permission | Role-Permission Table | Role-permission associations |
| usr_login_log | Login Log Table | Login history |