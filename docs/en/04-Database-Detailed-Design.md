# Global FX Trading Platform - Database Detailed Design

## 1. ER Relationship Description

### 1.1 Core Entity Relationships

```
Tenant(cfg_tenant) 1---N Account(acc_account) 1---N Wallet(wlt_wallet)
Account(acc_account) 1---N Order(trd_order) 1---N Position(pos_position)
Position(pos_position) N---1 Symbol(sym_symbol)
Order(trd_order) N---1 Symbol(sym_symbol)
Symbol(sym_symbol) N---1 Tick(qte_tick)
Account(acc_account) 1---N Customer(crm_customer) 1---N KYC(kyc_application)
Customer(crm_customer) N---1 IB Partner(ib_partner)
IB Partner(ib_partner) 1---N IB Partner(ib_partner) (self-referencing hierarchy)
Customer(crm_customer) 1---N Ticket(tic_ticket) 1---N Ticket Reply(tic_reply)
Customer(crm_customer) 1---N Follow-up Record(fll_follow)
Account(acc_account) 1---N Deposit Record(txn_deposit)
Account(acc_account) 1---N Withdrawal Record(txn_withdrawal)
Position(pos_position) 1---1 Daily Settlement(clr_daily_settlement)
Account(acc_account) 1---N Risk Event(rsk_event)
Risk Rule(rsk_rule) N---1 Symbol(sym_symbol)
```

### 1.2 ER Diagram (Text Description)

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  cfg_tenant │────<│  acc_account│────<│  wlt_wallet │
│   Tenant    │     │   Account   │     │    Wallet   │
└─────────────┘     └─────────────┘     └─────────────┘
                           │
                           ├────<┬────< txn_deposit | txn_withdrawal
                           │
                           ▼
                    ┌─────────────┐
                    │  trd_order  │
                    │    Order    │
                    └─────────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │ pos_position│
                    │  Position   │
                    └─────────────┘
                           │
                    ┌──────┴──────┐
                    ▼             ▼
             ┌─────────────┐ ┌─────────────┐
             │sym_symbol   │ │clr_daily    │
             │   Symbol    │ │   Daily     │
             └─────────────┘ └─────────────┘
                    ▲
                    │
             ┌─────────────┐
             │  qte_tick   │
             │    Tick     │
             └─────────────┘
```

## 2. Detailed Table Design

### 2.1 Symbol Table (sym_symbol)

**Table Description:** Defines all tradeable forex currency pairs, cryptocurrencies, precious metals, indices, and other instruments

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| symbol | VARCHAR | 20 | Yes | - | Symbol code, e.g. EURUSD |
| symbol_name | VARCHAR | 100 | Yes | - | Symbol name, e.g. Euro/US Dollar |
| symbol_type | SMALLINT | - | Yes | - | Instrument type: 1-Major Pair 2-Minor Pair 3-Exotic 4-Precious Metal 5-Index 6-Cryptocurrency |
| base_currency | VARCHAR | 10 | Yes | - | Base currency |
| quote_currency | VARCHAR | 10 | Yes | - | Quote currency |
| precision | SMALLINT | - | Yes | 5 | Price precision (decimal places) |
| lot_size | DECIMAL | 15,5 | Yes | 100000 | Standard lot size |
| min_lot | DECIMAL | 15,5 | Yes | 0.01 | Minimum trading volume (lots) |
| max_lot | DECIMAL | 15,5 | Yes | 100 | Maximum trading volume (lots) |
| lot_step | DECIMAL | 15,5 | Yes | 0.01 | Trading volume step |
| min_distance_points | INTEGER | - | Yes | 10 | Minimum distance for take profit/stop loss (points) |
| trade_mode | SMALLINT | - | Yes | 1 | Trading mode: 1-Tradable 2-View Only 3-Disabled |
| contract_size | DECIMAL | 15,5 | Yes | 100000 | Contract size |
| swap_long | DECIMAL | 10,5 | Yes | - | Long swap overnight interest (points) |
| swap_short | DECIMAL | 10,5 | Yes | - | Short swap overnight interest (points) |
| swap_mode | SMALLINT | - | Yes | 1 | Interest calculation mode: 1-Points 2-Percentage |
| trading_hours | VARCHAR | 100 | Yes | - | Trading hours configuration |
| status | SMALLINT | - | Yes | 1 | Status: 1-Enabled 2-Disabled |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_sym_symbol | Primary Key | id | Primary Key Index |
| uk_sym_symbol_code | Unique | symbol, tenant_id | Unique Symbol Code |
| idx_sym_type | Normal | symbol_type | Query by instrument type |

**DDL Script:**
```sql
CREATE TABLE sym_symbol (
    id BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    symbol_name VARCHAR(100) NOT NULL,
    symbol_type SMALLINT NOT NULL DEFAULT 1,
    base_currency VARCHAR(10) NOT NULL,
    quote_currency VARCHAR(10) NOT NULL,
    precision SMALLINT NOT NULL DEFAULT 5,
    lot_size DECIMAL(15,5) NOT NULL DEFAULT 100000,
    min_lot DECIMAL(15,5) NOT NULL DEFAULT 0.01,
    max_lot DECIMAL(15,5) NOT NULL DEFAULT 100,
    lot_step DECIMAL(15,5) NOT NULL DEFAULT 0.01,
    min_distance_points INTEGER NOT NULL DEFAULT 10,
    trade_mode SMALLINT NOT NULL DEFAULT 1,
    contract_size DECIMAL(15,5) NOT NULL DEFAULT 100000,
    swap_long DECIMAL(10,5) NOT NULL DEFAULT 0,
    swap_short DECIMAL(10,5) NOT NULL DEFAULT 0,
    swap_mode SMALLINT NOT NULL DEFAULT 1,
    trading_hours VARCHAR(100) NOT NULL DEFAULT '00:00-24:00',
    status SMALLINT NOT NULL DEFAULT 1,
    tenant_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(symbol, tenant_id)
);

CREATE INDEX idx_sym_type ON sym_symbol(symbol_type);
```

---

### 2.2 Order Table (trd_order)

**Table Description:** Records the complete lifecycle of all trading orders

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| order_no | UUID | - | Yes | - | Order Number (UUID) |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| account_id | BIGINT | - | Yes | - | Account ID |
| symbol_id | BIGINT | - | Yes | - | Symbol ID |
| order_type | SMALLINT | - | Yes | - | Order Type: 1-Market Order 2-Limit Order 3-Stop Order 4-Take Profit Order 5-Stop Limit Order 6-Pending Order |
| order_side | SMALLINT | - | Yes | - | Order Side: 1-Buy 2-Sell |
| order_price | DECIMAL | 20,10 | No | NULL | Order Price (for limit orders) |
| stop_price | DECIMAL | 20,10 | No | NULL | Trigger Price (for stop/pending orders) |
| lot_size | DECIMAL | 15,5 | Yes | - | Order Quantity (lots) |
| filled_lot | DECIMAL | 15,5 | Yes | 0 | Filled Quantity |
| avg_price | DECIMAL | 20,10 | No | NULL | Average Execution Price |
| order_price_show | DECIMAL | 20,10 | No | NULL | Display Price When Order Placed |
| slippage_points | INTEGER | - | Yes | 0 | Slippage (points) |
| slippage_type | SMALLINT | - | Yes | 1 | Slippage Type: 1-Fixed 2-Percentage |
| status | SMALLINT | - | Yes | 1 | Status: 1-Pending 2-Partially Filled 3-Fully Filled 4-Cancelled 5-Rejected 6-Expired |
| execution_type | SMALLINT | - | Yes | 1 | Execution Type: 1-Full Order Execution 2-Per Trade Execution |
| time_in_force | SMALLINT | - | Yes | 1 | Validity: 1-GTC (Good Till Cancelled) 2-GFD (Good For Day) 3-GTD (Good Till Date) |
| expire_time | TIMESTAMPTZ | - | No | NULL | Expiration Time |
| position_id | BIGINT | - | No | NULL | Associated Position ID (for close orders) |
| parent_order_id | BIGINT | - | No | NULL | Parent Order ID (e.g., stop order linked to market order) |
| lp_id | BIGINT | - | No | NULL | Execution LP ID |
| execution_mode | SMALLINT | - | Yes | 1 | Execution Mode: 1-A-Book 2-B-Book 3-Hybrid |
| client_order_id | VARCHAR | 100 | No | NULL | Client Order ID (FIX/MetaTrader) |
| channel | SMALLINT | - | Yes | 1 | Source Channel: 1-Web 2-App 3-API 4-MT4 5-MT5 6-FIX |
| ip_address | INET | - | No | NULL | Order IP Address |
| order_text | VARCHAR | 500 | No | NULL | Order Remark |
| reason | VARCHAR | 500 | No | NULL | Rejection/Cancellation Reason |
| risk_check_status | SMALLINT | - | Yes | 1 | Risk Check Status: 1-Passed 2-Pending Review 3-Rejected |
| risk_check_time | TIMESTAMPTZ | - | No | NULL | Risk Check Time |
| risk_rule_ids | VARCHAR | 200 | No | NULL | Triggered Risk Rule ID List |
| source_order_no | UUID | - | No | NULL | Source Order Number (for reversal/modification) |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_trd_order | Primary Key | id | Primary Key Index |
| uk_trd_order_no | Unique | order_no | Unique Order Number |
| uk_trd_client_order | Unique | client_order_id, channel, tenant_id | Client Order Number |
| idx_trd_account | Normal | account_id | Query by Account |
| idx_trd_symbol | Normal | symbol_id | Query by Symbol |
| idx_trd_status | Normal | status | Query by Status |
| idx_trd_created | Normal | created_time | Query by Time |
| idx_trd_account_symbol | Composite | account_id, symbol_id, created_time | Account + Symbol + Time Query |

**DDL Script:**
```sql
CREATE TABLE trd_order (
    id BIGSERIAL PRIMARY KEY,
    order_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    symbol_id BIGINT NOT NULL,
    order_type SMALLINT NOT NULL,
    order_side SMALLINT NOT NULL,
    order_price DECIMAL(20,10) DEFAULT NULL,
    stop_price DECIMAL(20,10) DEFAULT NULL,
    lot_size DECIMAL(15,5) NOT NULL,
    filled_lot DECIMAL(15,5) NOT NULL DEFAULT 0,
    avg_price DECIMAL(20,10) DEFAULT NULL,
    order_price_show DECIMAL(20,10) DEFAULT NULL,
    slippage_points INTEGER NOT NULL DEFAULT 0,
    slippage_type SMALLINT NOT NULL DEFAULT 1,
    status SMALLINT NOT NULL DEFAULT 1,
    execution_type SMALLINT NOT NULL DEFAULT 1,
    time_in_force SMALLINT NOT NULL DEFAULT 1,
    expire_time TIMESTAMPTZ DEFAULT NULL,
    position_id BIGINT DEFAULT NULL,
    parent_order_id BIGINT DEFAULT NULL,
    lp_id BIGINT DEFAULT NULL,
    execution_mode SMALLINT NOT NULL DEFAULT 1,
    client_order_id VARCHAR(100) DEFAULT NULL,
    channel SMALLINT NOT NULL DEFAULT 1,
    ip_address INET DEFAULT NULL,
    order_text VARCHAR(500) DEFAULT NULL,
    reason VARCHAR(500) DEFAULT NULL,
    risk_check_status SMALLINT NOT NULL DEFAULT 1,
    risk_check_time TIMESTAMPTZ DEFAULT NULL,
    risk_rule_ids VARCHAR(200) DEFAULT NULL,
    source_order_no UUID DEFAULT NULL,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(order_no)
);

CREATE INDEX idx_trd_account ON trd_order(account_id);
CREATE INDEX idx_trd_symbol ON trd_order(symbol_id);
CREATE INDEX idx_trd_status ON trd_order(status);
CREATE INDEX idx_trd_created ON trd_order(created_time);
CREATE INDEX idx_trd_account_symbol ON trd_order(account_id, symbol_id, created_time);
```

---

### 2.3 Position Table (pos_position)

**Table Description:** Records currently active trading positions for accounts

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| position_no | UUID | - | Yes | - | Position Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| account_id | BIGINT | - | Yes | - | Account ID |
| symbol_id | BIGINT | - | Yes | - | Symbol ID |
| position_side | SMALLINT | - | Yes | - | Position Side: 1-Long (Buy) 2-Short (Sell) |
| open_lot | DECIMAL | 15,5 | Yes | - | Open Quantity (lots) |
| current_lot | DECIMAL | 15,5 | Yes | - | Current Quantity (lots) |
| open_price | DECIMAL | 20,10 | Yes | - | Average Open Price |
| open_order_id | BIGINT | - | Yes | - | Open Order ID |
| close_lot | DECIMAL | 15,5 | Yes | 0 | Closed Quantity |
| close_price | DECIMAL | 20,10 | No | NULL | Average Close Price |
| realized_pnl | DECIMAL | 20,8 | Yes | 0 | Realized Profit/Loss |
| unrealized_pnl | DECIMAL | 20,8 | Yes | 0 | Unrealized Profit/Loss |
| swap_amount | DECIMAL | 20,8 | Yes | 0 | Cumulative Swap Overnight Interest |
| commission | DECIMAL | 20,8 | Yes | 0 | Cumulative Commission |
| used_margin | DECIMAL | 20,8 | Yes | 0 | Used Margin |
| open_time | TIMESTAMPTZ | - | Yes | - | Open Time |
| update_time | TIMESTAMPTZ | - | Yes | NOW() | Update Time |
| is_locked | SMALLINT | - | Yes | 0 | Is Locked: 0-No 1-Yes |
| lock_reason | VARCHAR | 200 | No | NULL | Lock Reason |
| status | SMALLINT | - | Yes | 1 | Status: 1-Open 2-Closed 3-Partially Closed 4-Force Closing |
| version | INTEGER | - | Yes | 1 | Optimistic Lock Version |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_pos_position | Primary Key | id | Primary Key Index |
| uk_pos_no | Unique | position_no | Unique Position Number |
| uk_pos_account_symbol | Unique | account_id, symbol_id, position_side, status | Account + Symbol + Side Unique |
| idx_pos_account | Normal | account_id | Query by Account |
| idx_pos_status | Normal | status | Query by Status |

**DDL Script:**
```sql
CREATE TABLE pos_position (
    id BIGSERIAL PRIMARY KEY,
    position_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    symbol_id BIGINT NOT NULL,
    position_side SMALLINT NOT NULL,
    open_lot DECIMAL(15,5) NOT NULL,
    current_lot DECIMAL(15,5) NOT NULL,
    open_price DECIMAL(20,10) NOT NULL,
    open_order_id BIGINT NOT NULL,
    close_lot DECIMAL(15,5) NOT NULL DEFAULT 0,
    close_price DECIMAL(20,10) DEFAULT NULL,
    realized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    unrealized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    swap_amount DECIMAL(20,8) NOT NULL DEFAULT 0,
    commission DECIMAL(20,8) NOT NULL DEFAULT 0,
    used_margin DECIMAL(20,8) NOT NULL DEFAULT 0,
    open_time TIMESTAMPTZ NOT NULL,
    update_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_locked SMALLINT NOT NULL DEFAULT 0,
    lock_reason VARCHAR(200) DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    version INTEGER NOT NULL DEFAULT 1,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(position_no)
);

CREATE INDEX idx_pos_account ON pos_position(account_id);
CREATE INDEX idx_pos_status ON pos_position(status);
```

---

### 2.4 Account Table (acc_account)

**Table Description:** Customer main account information

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| account_no | VARCHAR | 30 | Yes | - | Account Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| customer_id | BIGINT | - | Yes | - | Customer ID |
| account_type | SMALLINT | - | Yes | - | Account Type: 1-Real Account 2-Demo Account 3-VIP Account |
| account_group | VARCHAR | 50 | No | NULL | Account Group (e.g., VIP Group, ECN Group) |
| base_currency | VARCHAR | 10 | Yes | USD | Account Base Currency |
| leverage | DECIMAL | 10,2 | Yes | 30 | Leverage Multiplier |
| balance | DECIMAL | 20,8 | Yes | 0 | Account Balance |
| equity | DECIMAL | 20,8 | Yes | 0 | Account Equity |
| margin | DECIMAL | 20,8 | Yes | 0 | Used Margin |
| free_margin | DECIMAL | 20,8 | Yes | 0 | Free Margin |
| margin_level | DECIMAL | 10,2 | Yes | 0 | Margin Level (%) |
| credit | DECIMAL | 20,8 | Yes | 0 | Credit Limit |
| unrealized_pnl | DECIMAL | 20,8 | Yes | 0 | Unrealized Profit/Loss |
| realized_pnl | DECIMAL | 20,8 | Yes | 0 | Realized Profit/Loss (Today) |
| deposit_amount | DECIMAL | 20,8 | Yes | 0 | Cumulative Deposits |
| withdraw_amount | DECIMAL | 20,8 | Yes | 0 | Cumulative Withdrawals |
| status | SMALLINT | - | Yes | 1 | Status: 1-Active 2-Frozen 3-Closed 4-Force Closing |
| freeze_reason | VARCHAR | 500 | No | NULL | Freeze Reason |
| ib_id | BIGINT | - | No | NULL | Associated IB ID |
| manager_id | BIGINT | - | No | NULL | Account Manager ID |
| platform | SMALLINT | - | Yes | 1 | Platform Type: 1-Proprietary 2-MT4 3-MT5 |
| platform_account_id | VARCHAR | 100 | No | NULL | MT4/MT5 Account ID |
| region_code | VARCHAR | 10 | - | - | Regulatory Region Code |
| risk_level | SMALLINT | - | Yes | 1 | Risk Level: 1-Retail 2-Professional 3-Institutional |
| kyc_status | SMALLINT | - | Yes | 0 | KYC Status: 0-Not Submitted 1-Pending Review 2-Under Review 3-Approved 4-Rejected |
| last_trade_time | TIMESTAMPTZ | - | No | NULL | Last Trade Time |
| last_login_time | TIMESTAMPTZ | - | No | NULL | Last Login Time |
| version | INTEGER | - | Yes | 1 | Optimistic Lock Version |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_acc_account | Primary Key | id | Primary Key Index |
| uk_acc_no | Unique | account_no | Unique Account Number |
| uk_acc_platform | Unique | platform, platform_account_id | Unique Platform Account |
| idx_acc_customer | Normal | customer_id | Query by Customer |
| idx_acc_ib | Normal | ib_id | Query by IB |
| idx_acc_status | Normal | status | Query by Status |

**DDL Script:**
```sql
CREATE TABLE acc_account (
    id BIGSERIAL PRIMARY KEY,
    account_no VARCHAR(30) NOT NULL,
    tenant_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    account_type SMALLINT NOT NULL DEFAULT 1,
    account_group VARCHAR(50) DEFAULT NULL,
    base_currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    leverage DECIMAL(10,2) NOT NULL DEFAULT 30,
    balance DECIMAL(20,8) NOT NULL DEFAULT 0,
    equity DECIMAL(20,8) NOT NULL DEFAULT 0,
    margin DECIMAL(20,8) NOT NULL DEFAULT 0,
    free_margin DECIMAL(20,8) NOT NULL DEFAULT 0,
    margin_level DECIMAL(10,2) NOT NULL DEFAULT 0,
    credit DECIMAL(20,8) NOT NULL DEFAULT 0,
    unrealized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    realized_pnl DECIMAL(20,8) NOT NULL DEFAULT 0,
    deposit_amount DECIMAL(20,8) NOT NULL DEFAULT 0,
    withdraw_amount DECIMAL(20,8) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 1,
    freeze_reason VARCHAR(500) DEFAULT NULL,
    ib_id BIGINT DEFAULT NULL,
    manager_id BIGINT DEFAULT NULL,
    platform SMALLINT NOT NULL DEFAULT 1,
    platform_account_id VARCHAR(100) DEFAULT NULL,
    region_code VARCHAR(10) DEFAULT NULL,
    risk_level SMALLINT NOT NULL DEFAULT 1,
    kyc_status SMALLINT NOT NULL DEFAULT 0,
    last_trade_time TIMESTAMPTZ DEFAULT NULL,
    last_login_time TIMESTAMPTZ DEFAULT NULL,
    version INTEGER NOT NULL DEFAULT 1,
    created_by BIGINT NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by BIGINT DEFAULT NULL,
    updated_time TIMESTAMPTZ DEFAULT NULL,
    is_deleted SMALLINT NOT NULL DEFAULT 0,
    remark VARCHAR(500) DEFAULT NULL,
    UNIQUE(account_no)
);

CREATE INDEX idx_acc_customer ON acc_account(customer_id);
CREATE INDEX idx_acc_ib ON acc_account(ib_id);
CREATE INDEX idx_acc_status ON acc_account(status);
```

---

### 2.5 Wallet Table (wlt_wallet)

**Table Description:** Multi-currency wallet, an account can have multiple currency wallets

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| wallet_no | UUID | - | Yes | - | Wallet Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| account_id | BIGINT | - | Yes | - | Account ID |
| currency | VARCHAR | 10 | Yes | - | Currency Code |
| balance | DECIMAL | 20,8 | Yes | 0 | Available Balance |
| frozen_balance | DECIMAL | 20,8 | Yes | 0 | Frozen Amount |
| total_in | DECIMAL | 20,8 | Yes | 0 | Cumulative Inflow |
| total_out | DECIMAL | 20,8 | Yes | 0 | Cumulative Outflow |
| status | SMALLINT | - | Yes | 1 | Status: 1-Active 2-Frozen |
| version | INTEGER | - | Yes | 1 | Optimistic Lock Version |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_wlt_wallet | Primary Key | id | Primary Key Index |
| uk_wlt_no | Unique | wallet_no | Unique Wallet Number |
| uk_wlt_account_currency | Unique | account_id, currency | Account + Currency Unique |
| idx_wlt_account | Normal | account_id | Query by Account |

---

### 2.6 Wallet Transaction Table (wlt_transaction)

**Table Description:** All fund movement transactions for wallets

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| txn_no | UUID | - | Yes | - | Transaction Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| wallet_id | BIGINT | - | Yes | - | Wallet ID |
| account_id | BIGINT | - | Yes | - | Account ID |
| currency | VARCHAR | 10 | Yes | - | Currency |
| txn_type | SMALLINT | - | Yes | - | Transaction Type: 1-Deposit 2-Withdrawal 3-Transfer 4-Trade 5-Settlement 6-Commission 7-Interest 8-Fee 9-Other |
| direction | SMALLINT | - | Yes | - | Direction: 1-Inflow 2-Outflow |
| amount | DECIMAL | 20,8 | Yes | - | Transaction Amount |
| balance_before | DECIMAL | 20,8 | Yes | - | Balance Before Transaction |
| balance_after | DECIMAL | 20,8 | Yes | - | Balance After Transaction |
| frozen_before | DECIMAL | 20,8 | Yes | - | Frozen Before Transaction |
| frozen_after | DECIMAL | 20,8 | Yes | - | Frozen After Transaction |
| related_order_no | UUID | - | No | NULL | Associated Order Number |
| related_txn_no | UUID | - | No | NULL | Associated Transaction Number (for transfers) |
| status | SMALLINT | - | Yes | 1 | Status: 1-Success 2-Failed 3-Reversed |
| reference_id | VARCHAR | 100 | No | NULL | External Reference Number |
| remark | VARCHAR | 500 | No | NULL | Remark |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_wlt_transaction | Primary Key | id | Primary Key Index |
| uk_wlt_txn_no | Unique | txn_no | Unique Transaction Number |
| uk_wlt_ref | Unique | reference_id | Unique External Reference |
| idx_wlt_wallet | Normal | wallet_id | Query by Wallet |
| idx_wlt_account | Normal | account_id | Query by Account |
| idx_wlt_created | Normal | created_time | Query by Time |
| idx_wlt_type | Normal | txn_type | Query by Type |

---

### 2.7 Risk Rule Table (rsk_rule)

**Table Description:** Risk control rule configuration

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| rule_code | VARCHAR | 50 | Yes | - | Rule Code |
| rule_name | VARCHAR | 100 | Yes | - | Rule Name |
| rule_type | SMALLINT | - | Yes | - | Rule Type: 1-Position Limit 2-Per Order Limit 3-Daily Trading Limit 4-Margin Level 5-Anomaly Detection 6-Symbol Limit |
| symbol_id | BIGINT | - | No | NULL | Applicable Symbol (NULL means all) |
| account_group | VARCHAR | 50 | No | NULL | Applicable Account Group |
| risk_level | SMALLINT | - | No | NULL | Applicable Risk Level |
| min_value | DECIMAL | 20,8 | No | NULL | Minimum Value |
| max_value | DECIMAL | 20,8 | No | NULL | Maximum Value |
| action | SMALLINT | - | Yes | - | Trigger Action: 1-Reject 2-Warning 3-Delay 4-Manual Review |
| action_level | SMALLINT | - | Yes | 1 | Action Level: 1-Low 2-Medium 3-High |
| priority | INTEGER | - | Yes | 100 | Priority (lower number = higher priority) |
| status | SMALLINT | - | Yes | 1 | Status: 1-Enabled 2-Disabled |
| effective_start | TIMESTAMPTZ | - | No | NULL | Effective Start Time |
| effective_end | TIMESTAMPTZ | - | No | NULL | Effective End Time |
| rule_expression | TEXT | - | No | NULL | Rule Expression (JSON format) |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_rsk_rule | Primary Key | id | Primary Key Index |
| uk_rsk_code | Unique | rule_code, tenant_id | Unique Rule Code |
| idx_rsk_type | Normal | rule_type | Query by Rule Type |
| idx_rsk_status | Normal | status | Query by Status |

---

### 2.8 Risk Event Table (rsk_event)

**Table Description:** Risk control triggered event records

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| event_no | UUID | - | Yes | - | Event Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| rule_id | BIGINT | - | Yes | - | Triggered Rule ID |
| rule_code | VARCHAR | 50 | - | - | Rule Code |
| event_type | SMALLINT | - | Yes | - | Event Type: 1-Order Rejected 2-Warning 3-Force Liquidation 4-Account Frozen 5-Anomaly Detected |
| account_id | BIGINT | - | Yes | - | Account ID |
| symbol_id | BIGINT | - | No | NULL | Symbol ID |
| order_no | UUID | - | No | NULL | Order Number |
| position_no | UUID | - | No | NULL | Position Number |
| event_data | JSONB | - | No | NULL | Event Details (JSON) |
| action_taken | SMALLINT | - | Yes | - | Action Taken: 1-Rejected 2-Warned 3-Delayed 4-Pending Manual 5-Force Liquidated |
| operator_id | BIGINT | - | No | NULL | Handler ID |
| operate_time | TIMESTAMPTZ | - | No | NULL | Handling Time |
| operate_result | VARCHAR | 500 | - | NULL | Handling Result |
| status | SMALLINT | - | Yes | 1 | Status: 1-Pending 2-In Progress 3-Processed 4-Ignored |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_rsk_event | Primary Key | id | Primary Key Index |
| uk_rsk_event_no | Unique | event_no | Unique Event Number |
| idx_rsk_account | Normal | account_id | Query by Account |
| idx_rsk_status | Normal | status | Query by Status |
| idx_rsk_created | Normal | created_time | Query by Time |

---

### 2.9 LP Provider Table (lp_provider)

**Table Description:** Liquidity Provider configuration

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| provider_code | VARCHAR | 50 | Yes | - | Provider Code |
| provider_name | VARCHAR | 100 | Yes | - | Provider Name |
| provider_type | SMALLINT | - | Yes | - | Provider Type: 1-Bank 2-ECN 3-LP 4-Exchange |
| connection_type | SMALLINT | - | Yes | 1 | Connection Type: 1-REST 2-WebSocket 3-FIX 4-Proprietary Protocol |
| api_endpoint | VARCHAR | 500 | - | - | API Endpoint |
| api_key | VARCHAR | 200 | - | - | API Key (Encrypted Storage) |
| api_secret | VARCHAR | 500 | - | - | API Secret (Encrypted Storage) |
| supported_symbols | TEXT | - | - | - | Supported Symbols List (JSON Array) |
| priority | INTEGER | - | Yes | 100 | Priority (lower = higher) |
| status | SMALLINT | - | Yes | 1 | Status: 1-Enabled 2-Disabled 3-Maintenance |
| min_lot | DECIMAL | 15,5 | - | 0.01 | Minimum Trading Volume |
| max_lot | DECIMAL | 15,5 | - | 100 | Maximum Trading Volume |
| timeout_ms | INTEGER | - | Yes | 5000 | Timeout (milliseconds) |
| retry_count | SMALLINT | - | Yes | 3 | Retry Count |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

---

### 2.10 Daily Settlement Table (clr_daily_settlement)

**Table Description:** End-of-day settlement result records

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| settlement_no | VARCHAR | 30 | Yes | - | Settlement Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| account_id | BIGINT | - | Yes | - | Account ID |
| settlement_date | DATE | - | Yes | - | Settlement Date |
| open_equity | DECIMAL | 20,8 | Yes | - | Opening Equity |
| close_equity | DECIMAL | 20,8 | Yes | - | Closing Equity |
| realized_pnl | DECIMAL | 20,8 | Yes | 0 | Realized Profit/Loss |
| unrealized_pnl | DECIMAL | 20,8 | Yes | 0 | Unrealized Profit/Loss |
| swap_amount | DECIMAL | 20,8 | Yes | 0 | Swap Overnight Interest |
| commission | DECIMAL | 20,8 | Yes | 0 | Commission |
| deposit | DECIMAL | 20,8 | Yes | 0 | Deposits |
| withdrawal | DECIMAL | 20,8 | Yes | 0 | Withdrawals |
| ib_commission | DECIMAL | 20,8 | Yes | 0 | IB Commission |
| balance_before | DECIMAL | 20,8 | Yes | - | Balance Before Settlement |
| balance_after | DECIMAL | 20,8 | Yes | - | Balance After Settlement |
| margin_before | DECIMAL | 20,8 | - | - | Margin Before Settlement |
| margin_after | DECIMAL | 20,8 | - | - | Margin After Settlement |
| status | SMALLINT | - | Yes | 1 | Status: 1-Pending 2-In Progress 3-Completed 4-Failed |
| error_message | VARCHAR | 500 | - | - | Error Message |
| processed_time | TIMESTAMPTZ | - | No | NULL | Processing Completion Time |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_clr_daily | Primary Key | id | Primary Key Index |
| uk_clr_no | Unique | settlement_no | Unique Settlement Number |
| uk_clr_account_date | Unique | account_id, settlement_date | Account + Date Unique |
| idx_clr_date | Normal | settlement_date | Query by Date |
| idx_clr_status | Normal | status | Query by Status |

---

### 2.11 Customer Table (crm_customer)

**Table Description:** Customer basic information

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| customer_no | VARCHAR | 30 | Yes | - | Customer Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| customer_type | SMALLINT | - | Yes | 1 | Customer Type: 1-Individual 2-Corporate |
| first_name | VARCHAR | 100 | No | NULL | First Name |
| last_name | VARCHAR | 100 | No | NULL | Last Name |
| full_name | VARCHAR | 200 | Yes | - | Full Name |
| email | VARCHAR | 100 | Yes | - | Email |
| phone | VARCHAR | 30 | No | NULL | Phone |
| country | VARCHAR | 10 | Yes | - | Country Code |
| city | VARCHAR | 100 | No | NULL | City |
| address | VARCHAR | 500 | No | NULL | Address |
| birth_date | DATE | - | NULL | - | Date of Birth |
| id_card_type | SMALLINT | - | - | ID Document Type: 1-National ID 2-Passport 3-Driver's License |
| id_card_no | VARCHAR | 100 | - | - | Document Number (Encrypted) |
| risk_score | INTEGER | - | - | 0 | Risk Score |
| risk_level | SMALLINT | - | Yes | 1 | Risk Level: 1-Retail 2-Professional 3-Institutional |
| segment | VARCHAR | 50 | - | - | Customer Segmentation |
| source | SMALLINT | - | - | 1 | Customer Source: 1-Organic Traffic 2-IB Referral 3-Advertising 4-Partner Channel |
| ib_id | BIGINT | - | NULL | - | Referring IB ID |
| manager_id | BIGINT | - | NULL | - | Account Manager ID |
| status | SMALLINT | - | Yes | 1 | Status: 1-Lead 2-Active 3-Inactive 4-Churned |
| last_active_time | TIMESTAMPTZ | - | - | Last Active Time |
| total_deposit | DECIMAL | 20,8 | - | 0 | Cumulative Deposits |
| total_withdrawal | DECIMAL | 20,8 | - | 0 | Cumulative Withdrawals |
| total_trades | INTEGER | - | - | 0 | Cumulative Trade Count |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_crm_customer | Primary Key | id | Primary Key Index |
| uk_crm_no | Unique | customer_no | Unique Customer Number |
| uk_crm_email | Unique | email, tenant_id | Unique Email |
| idx_crm_ib | Normal | ib_id | Query by IB |
| idx_crm_manager | Normal | manager_id | Query by Account Manager |
| idx_crm_status | Normal | status | Query by Status |

---

### 2.12 KYC Application Table (kyc_application)

**Table Description:** KYC verification application records

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| application_no | UUID | - | Yes | - | Application Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| customer_id | BIGINT | - | Yes | - | Customer ID |
| kyc_level | SMALLINT | - | Yes | 1 | KYC Level: 1-Basic Verification 2-Advanced Verification 3-Corporate Verification |
| kyc_provider | VARCHAR | 50 | - | - | KYC Service Provider |
| provider_ref_id | VARCHAR | 100 | - | - | Provider Reference ID |
| status | SMALLINT | - | Yes | 1 | Status: 1-Not Submitted 2-Under Review 3-Approved 4-Rejected 5-Additional Info Required |
| reject_reason | VARCHAR | 500 | - | - | Rejection Reason |
| verify_result | JSONB | - | - | - | Verification Result Details |
| risk_score | INTEGER | - | - | 0 | Risk Score |
| aml_check_result | SMALLINT | - | - | AML Check Result: 1-Passed 2-Rejected 3-Manual Review |
| sanctions_hit | SMALLINT | - | - | 0 | Sanctions List Hit: 0-No 1-Yes |
| operator_id | BIGINT | - | NULL | - | Review Operator ID |
| operate_time | TIMESTAMPTZ | - | - | - | Review Time |
| operate_remark | VARCHAR | 500 | - | - | Review Remark |
| expire_time | TIMESTAMPTZ | - | - | - | Verification Expiration Time |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

---

### 2.13 IB Partner Table (ib_partner)

**Table Description:** Broker/IB Partner information

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| partner_no | VARCHAR | 30 | Yes | - | Partner Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| customer_id | BIGINT | - | Yes | - | Associated Customer ID |
| partner_level | SMALLINT | - | Yes | 1 | Partner Level: 1-IB 2-White Label 3-Strategic Partner |
| parent_id | BIGINT | - | NULL | - | Parent Partner ID |
| hierarchy_path | VARCHAR | 500 | - | - | Hierarchy Path (e.g., /1/2/3/) |
| commission_type | SMALLINT | - | Yes | 1 | Commission Type: 1-Fixed Commission 2-Percentage Commission 3-Tiered Commission |
| commission_config | JSONB | - | - | - | Commission Configuration (JSON) |
| rebate_ratio | DECIMAL | 10,4 | - | 0 | Rebate Ratio |
| status | SMALLINT | - | Yes | 1 | Status: 1-Pending Review 2-Active 3-Suspended 4-Terminated |
| total_customers | INTEGER | - | - | 0 | Cumulative Customer Count |
| active_customers | INTEGER | - | - | 0 | Active Customer Count |
| total_commission | DECIMAL | 20,8 | - | 0 | Cumulative Commission |
| pending_commission | DECIMAL | 20,8 | - | 0 | Pending Commission |
| paid_commission | DECIMAL | 20,8 | - | 0 | Paid Commission |
| brand_name | VARCHAR | 100 | - | - | White Label Brand Name |
| domain | VARCHAR | 100 | - | - | White Label Domain |
| logo_url | VARCHAR | 500 | - | - | White Label LOGO |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_ib_partner | Primary Key | id | Primary Key Index |
| uk_ib_no | Unique | partner_no | Unique Partner Number |
| idx_ib_parent | Normal | parent_id | Query by Parent |
| idx_ib_customer | Normal | customer_id | Query by Customer |
| idx_ib_status | Normal | status | Query by Status |

---

### 2.14 Ticket Table (tic_ticket)

**Table Description:** Customer complaint/inquiry tickets

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| ticket_no | VARCHAR | 30 | Yes | - | Ticket Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| customer_id | BIGINT | - | Yes | - | Customer ID |
| account_id | BIGINT | - | NULL | - | Associated Account ID |
| category | SMALLINT | - | Yes | - | Ticket Category: 1-Trading Issue 2-Withdrawal Issue 3-Account Issue 4-System Issue 5-Complaint 6-Suggestion 7-Other |
| priority | SMALLINT | - | Yes | 2 | Priority: 1-Urgent 2-High 3-Medium 4-Low |
| title | VARCHAR | 200 | Yes | - | Ticket Title |
| content | TEXT | - | Yes | - | Ticket Content |
| attachments | JSONB | - | - | - | Attachment List |
| related_order_no | UUID | - | NULL | - | Associated Order Number |
| status | SMALLINT | - | Yes | 1 | Status: 1-Pending Assignment 2-In Progress 3-Awaiting Reply 4-Resolved 5-Closed |
| assigned_to | BIGINT | - | NULL | - | Handler ID |
| assigned_time | TIMESTAMPTZ | - | NULL | - | Assignment Time |
| first_reply_time | TIMESTAMPTZ | - | NULL | - | First Reply Time |
| resolved_time | TIMESTAMPTZ | - | NULL | - | Resolution Time |
| closed_time | TIMESTAMPTZ | - | NULL | - | Closure Time |
| satisfaction | SMALLINT | - | - | Customer Satisfaction: 1-Very Satisfied 2-Satisfied 3-Neutral 4-Dissatisfied |
| sla_deadline | TIMESTAMPTZ | - | - | SLA Deadline |
| sla_breached | SMALLINT | - | 0 | SLA Breached: 0-No 1-Yes |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_tic_ticket | Primary Key | id | Primary Key Index |
| uk_tic_no | Unique | ticket_no | Unique Ticket Number |
| idx_tic_customer | Normal | customer_id | Query by Customer |
| idx_tic_assigned | Normal | assigned_to | Query by Handler |
| idx_tic_status | Normal | status | Query by Status |
| idx_tic_priority | Normal | priority | Query by Priority |

---

### 2.15 Audit Log Table (aud_log)

**Table Description:** All operation audit logs

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| log_no | UUID | - | Yes | - | Log Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| module | VARCHAR | 50 | Yes | - | Module |
| action | VARCHAR | 100 | Yes | - | Operation Type |
| operator_type | SMALLINT | - | Yes | - | Operator Type: 1-System 2-User 3-Administrator |
| operator_id | BIGINT | - | NULL | - | Operator ID |
| operator_name | VARCHAR | 100 | - | - | Operator Name |
| ip_address | INET | - | NULL | - | IP Address |
| user_agent | VARCHAR | 500 | - | NULL | User Agent |
| request_id | UUID | - | NULL | - | Request Tracking ID |
| request_method | VARCHAR | 10 | - | NULL | Request Method |
| request_url | VARCHAR | 500 | - | NULL | Request URL |
| request_params | JSONB | - | NULL | - | Request Parameters |
| request_body | TEXT | - | NULL | - | Request Body |
| response_code | VARCHAR | 20 | - | NULL | Response Code |
| response_body | TEXT | - | NULL | - | Response Body |
| execution_time | INTEGER | - | NULL | - | Execution Time (milliseconds) |
| error_message | TEXT | - | NULL | - | Error Message |
| status | SMALLINT | - | Yes | 1 | Status: 1-Success 2-Failed |
| extra_data | JSONB | - | NULL | - | Extended Data |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_aud_log | Primary Key | id | Primary Key Index |
| uk_aud_log_no | Unique | log_no | Unique Log Number |
| uk_aud_request_id | Normal | request_id | Request Tracking ID |
| idx_aud_operator | Normal | operator_id | Query by Operator |
| idx_aud_module | Normal | module | Query by Module |
| idx_aud_action | Normal | action | Query by Operation |
| idx_aud_created | Normal | created_time | Query by Time |

**DDL Script:**
```sql
CREATE TABLE aud_log (
    id BIGSERIAL PRIMARY KEY,
    log_no UUID NOT NULL DEFAULT gen_random_uuid(),
    tenant_id BIGINT NOT NULL,
    module VARCHAR(50) NOT NULL,
    action VARCHAR(100) NOT NULL,
    operator_type SMALLINT NOT NULL DEFAULT 1,
    operator_id BIGINT DEFAULT NULL,
    operator_name VARCHAR(100) DEFAULT NULL,
    ip_address INET DEFAULT NULL,
    user_agent VARCHAR(500) DEFAULT NULL,
    request_id UUID DEFAULT NULL,
    request_method VARCHAR(10) DEFAULT NULL,
    request_url VARCHAR(500) DEFAULT NULL,
    request_params JSONB DEFAULT NULL,
    request_body TEXT DEFAULT NULL,
    response_code VARCHAR(20) DEFAULT NULL,
    response_body TEXT DEFAULT NULL,
    execution_time INTEGER DEFAULT NULL,
    error_message TEXT DEFAULT NULL,
    status SMALLINT NOT NULL DEFAULT 1,
    extra_data JSONB DEFAULT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_aud_operator ON aud_log(operator_id);
CREATE INDEX idx_aud_module ON aud_log(module);
CREATE INDEX idx_aud_created ON aud_log(created_time);
```

---

### 2.16 Tenant Table (cfg_tenant)

**Table Description:** Multi-tenant configuration

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| tenant_code | VARCHAR | 50 | Yes | - | Tenant Code |
| tenant_name | VARCHAR | 100 | Yes | - | Tenant Name |
| tenant_type | SMALLINT | - | Yes | 1 | Tenant Type: 1-Broker 2-White Label 3-Institutional |
| logo_url | VARCHAR | 500 | - | - | LOGO URL |
| domain | VARCHAR | 100 | - | - | Domain |
| region_code | VARCHAR | 10 | - | - | Regulatory Region |
| license_no | VARCHAR | 100 | - | - | Regulatory License Number |
| contact_email | VARCHAR | 100 | - | - | Contact Email |
| contact_phone | VARCHAR | 30 | - | - | Contact Phone |
| config | JSONB | - | NULL | - | Tenant Configuration |
| default_leverage | DECIMAL | 10,2 | - | 30 | Default Leverage |
| default_currency | VARCHAR | 10 | - | USD | Default Currency |
| status | SMALLINT | - | Yes | 1 | Status: 1-Active 2-Trial 3-Suspended 4-Terminated |
| expire_time | TIMESTAMPTZ | - | NULL | - | Expiration Time |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

---

### 2.17 System User Table (usr_user)

**Table Description:** System users (employees/administrators)

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| user_no | VARCHAR | 30 | Yes | - | Employee Number |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| username | VARCHAR | 50 | Yes | - | Username |
| password | VARCHAR | 200 | Yes | - | Password (BCrypt Encrypted) |
| nickname | VARCHAR | 100 | - | - | Nickname |
| email | VARCHAR | 100 | - | - | Email |
| phone | VARCHAR | 30 | - | - | Phone |
| avatar_url | VARCHAR | 500 | - | - | Avatar URL |
| dept_id | BIGINT | - | NULL | - | Department ID |
| post | VARCHAR | 100 | - | - | Position |
| status | SMALLINT | - | Yes | 1 | Status: 1-Active 2-Disabled 3-Locked |
| last_login_ip | INET | - | NULL | - | Last Login IP |
| last_login_time | TIMESTAMPTZ | - | NULL | - | Last Login Time |
| login_fail_count | SMALLINT | - | 0 | - | Consecutive Login Failure Count |
| lock_expire_time | TIMESTAMPTZ | - | NULL | - | Lock Expiration Time |
| password_expire_time | TIMESTAMPTZ | - | NULL | - | Password Expiration Time |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_usr_user | Primary Key | id | Primary Key Index |
| uk_usr_no | Unique | user_no | Unique Employee Number |
| uk_usr_username | Unique | username, tenant_id | Unique Username |
| idx_usr_tenant | Normal | tenant_id | Query by Tenant |
| idx_usr_status | Normal | status | Query by Status |

---

### 2.18 Role Table (usr_role)

**Table Description:** Role definitions

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| role_code | VARCHAR | 50 | Yes | - | Role Code |
| role_name | VARCHAR | 100 | Yes | - | Role Name |
| tenant_id | BIGINT | - | Yes | - | Tenant ID |
| data_scope | SMALLINT | - | Yes | 1 | Data Scope: 1-All Data 2-Own Tenant Data 3-Own Department Data 4-Own Data Only |
| dept_id | BIGINT | - | NULL | - | Department ID (when data scope is department) |
| status | SMALLINT | - | Yes | 1 | Status: 1-Active 2-Disabled |
| sort_order | INTEGER | - | 0 | - | Sort Order |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

---

### 2.19 Dictionary Type Table (dic_type)

**Table Description:** Enumeration type definitions

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| dict_type | VARCHAR | 100 | Yes | - | Dictionary Type Code |
| dict_name | VARCHAR | 100 | Yes | - | Dictionary Type Name |
| tenant_id | BIGINT | - | Yes | - | Tenant ID (0 for global) |
| status | SMALLINT | - | Yes | 1 | Status: 1-Active 2-Disabled |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

---

### 2.20 Dictionary Data Table (dic_data)

**Table Description:** Enumeration value data

| Field Name | Type | Length | Required | Default | Description |
|------------|------|--------|----------|---------|-------------|
| id | BIGSERIAL | - | Yes | Auto-increment | Primary Key |
| dict_type | VARCHAR | 100 | Yes | - | Parent Dictionary Type |
| dict_label | VARCHAR | 100 | Yes | - | Dictionary Label |
| dict_value | VARCHAR | 100 | Yes | - | Dictionary Key Value |
| sort_order | INTEGER | - | 0 | - | Sort Order |
| css_class | VARCHAR | 100 | - | - | CSS Style Class |
| list_class | VARCHAR | 100 | - | - | Display Style |
| is_default | SMALLINT | - | 0 | - | Is Default: 0-No 1-Yes |
| status | SMALLINT | - | Yes | 1 | Status: 1-Active 2-Disabled |
| tenant_id | BIGINT | - | Yes | - | Tenant ID (0 for global) |
| created_by | BIGINT | - | Yes | - | Created By ID |
| created_time | TIMESTAMPTZ | - | Yes | NOW() | Created Time |
| updated_by | BIGINT | - | No | NULL | Updated By ID |
| updated_time | TIMESTAMPTZ | - | No | NULL | Updated Time |
| is_deleted | SMALLINT | - | Yes | 0 | Logical Deletion |
| remark | VARCHAR | 500 | No | NULL | Remark |

**Index Design:**

| Index Name | Type | Fields | Description |
|------------|------|--------|-------------|
| pk_dic_data | Primary Key | id | Primary Key Index |
| idx_dic_type | Normal | dict_type | Query by Dictionary Type |

---

## 3. Enumeration Dictionary Tables (Business Enumeration Values)

### 3.1 Order Type (order_type)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Market Order | Market Order |
| 2 | Limit Order | Limit Order |
| 3 | Stop Order | Stop Order |
| 4 | Take Profit Order | Take Profit Order |
| 5 | Stop Limit Order | Stop Limit Order |
| 6 | Pending Order | Pending Order |

### 3.2 Order Side (order_side)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Buy | Buy/Long |
| 2 | Sell | Sell/Short |

### 3.3 Order Status (order_status)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Pending | Pending |
| 2 | Partially Filled | Partially Filled |
| 3 | Filled | Filled |
| 4 | Cancelled | Cancelled |
| 5 | Rejected | Rejected |
| 6 | Expired | Expired |

### 3.4 Position Status (position_status)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Open | Open |
| 2 | Closed | Closed |
| 3 | Partially Closed | Partially Closed |
| 4 | Force Closing | Force Closing |

### 3.5 Account Status (account_status)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Active | Active |
| 2 | Frozen | Frozen |
| 3 | Closed | Closed |
| 4 | Force Closing | Force Closing |

### 3.6 Account Type (account_type)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Real Account | Real Account |
| 2 | Demo Account | Demo Account |
| 3 | VIP Account | VIP Account |

### 3.7 Risk Level (risk_level)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Retail | Retail |
| 2 | Professional | Professional |
| 3 | Institutional | Institutional |

### 3.8 Customer Status (customer_status)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Lead | Lead |
| 2 | Active | Active |
| 3 | Inactive | Inactive |
| 4 | Churned | Churned |

### 3.9 KYC Status (kyc_status)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 0 | Not Submitted | Not Submitted |
| 1 | Pending Review | Pending Review |
| 2 | Under Review | Under Review |
| 3 | Approved | Approved |
| 4 | Rejected | Rejected |

### 3.10 Transaction Type (txn_type)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Deposit | Deposit |
| 2 | Withdrawal | Withdrawal |
| 3 | Transfer | Transfer |
| 4 | Trade | Trade |
| 5 | Settlement | Settlement |
| 6 | Commission | Commission |
| 7 | Interest | Interest/Swap |
| 8 | Fee | Fee |
| 9 | Others | Others |

### 3.11 Risk Event Type (rsk_event_type)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Order Rejected | Order Rejected |
| 2 | Risk Warning | Risk Warning |
| 3 | Force Liquidation | Force Liquidation |
| 4 | Account Frozen | Account Frozen |
| 5 | Anomaly Detected | Anomaly Detected |

### 3.12 Symbol Type (symbol_type)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Major Pairs | Major Pairs |
| 2 | Minor Pairs | Minor Pairs |
| 3 | Exotic Pairs | Exotic Pairs |
| 4 | Precious Metals | Precious Metals |
| 5 | Indices | Indices |
| 6 | Cryptocurrencies | Cryptocurrencies |

### 3.13 Ticket Category (ticket_category)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Trading Issue | Trading Issue |
| 2 | Withdrawal Issue | Withdrawal Issue |
| 3 | Account Issue | Account Issue |
| 4 | System Issue | System Issue |
| 5 | Complaint | Complaint |
| 6 | Suggestion | Suggestion |
| 7 | Others | Others |

### 3.14 Ticket Status (ticket_status)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Pending Assignment | Pending Assignment |
| 2 | In Progress | In Progress |
| 3 | Awaiting Reply | Awaiting Reply |
| 4 | Resolved | Resolved |
| 5 | Closed | Closed |

### 3.15 Source Channel (channel)

| dict_value | dict_label | Description |
|------------|------------|-------------|
| 1 | Web | Web |
| 2 | Mobile App | Mobile App |
| 3 | API | API |
| 4 | MT4 | MetaTrader 4 |
| 5 | MT5 | MetaTrader 5 |
| 6 | FIX Protocol | FIX Protocol |

## 4. Initialization Data Scripts

### 4.1 Global Dictionary Type Initialization

```sql
-- Order Related
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status) VALUES
('order_type', 'Order Type', 0, 1),
('order_side', 'Order Side', 0, 1),
('order_status', 'Order Status', 0, 1),
('position_status', 'Position Status', 0, 1),
('time_in_force', 'Order Validity', 0, 1),
('execution_mode', 'Execution Mode', 0, 1),
('channel', 'Source Channel', 0, 1);

-- Account Related
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status) VALUES
('account_type', 'Account Type', 0, 1),
('account_status', 'Account Status', 0, 1),
('risk_level', 'Risk Level', 0, 1),
('customer_status', 'Customer Status', 0, 1),
('kyc_status', 'KYC Status', 0, 1),
('txn_type', 'Transaction Type', 0, 1),
('deposit_status', 'Deposit Status', 0, 1),
('withdrawal_status', 'Withdrawal Status', 0, 1);

-- Symbol Related
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status) VALUES
('symbol_type', 'Symbol Type', 0, 1),
('symbol_status', 'Symbol Status', 0, 1),
('trade_mode', 'Trading Mode', 0, 1);

-- Risk Control Related
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status) VALUES
('rsk_rule_type', 'Risk Rule Type', 0, 1),
('rsk_event_type', 'Risk Event Type', 0, 1),
('rsk_action', 'Risk Action', 0, 1),
('rsk_action_level', 'Action Level', 0, 1);

-- CRM Related
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status) VALUES
('customer_type', 'Customer Type', 0, 1),
('customer_source', 'Customer Source', 0, 1),
('ib_level', 'IB Level', 0, 1),
('ib_commission_type', 'Commission Type', 0, 1),
('ticket_category', 'Ticket Category', 0, 1),
('ticket_status', 'Ticket Status', 0, 1),
('ticket_priority', 'Ticket Priority', 0, 1);

-- System Related
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status) VALUES
('user_status', 'User Status', 0, 1),
('role_status', 'Role Status', 0, 1),
('tenant_status', 'Tenant Status', 0, 1),
('yes_no', 'Yes/No', 0, 1),
('data_scope', 'Data Scope', 0, 1);
```

### 4.2 Global Dictionary Data Initialization

```sql
-- Order Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('order_type', 'Market Order', '1', 1, 1),
('order_type', 'Limit Order', '2', 2, 1),
('order_type', 'Stop Order', '3', 3, 1),
('order_type', 'Take Profit Order', '4', 4, 1),
('order_type', 'Stop Limit Order', '5', 5, 1),
('order_type', 'Pending Order', '6', 6, 1);

-- Order Side
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('order_side', 'Buy', '1', 1, 1),
('order_side', 'Sell', '2', 2, 1);

-- Order Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('order_status', 'Pending', '1', 1, 1),
('order_status', 'Partially Filled', '2', 2, 1),
('order_status', 'Filled', '3', 3, 1),
('order_status', 'Cancelled', '4', 4, 1),
('order_status', 'Rejected', '5', 5, 1),
('order_status', 'Expired', '6', 6, 1);

-- Position Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('position_status', 'Open', '1', 1, 1),
('position_status', 'Closed', '2', 2, 1),
('position_status', 'Partially Closed', '3', 3, 1),
('position_status', 'Force Closing', '4', 4, 1);

-- Account Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('account_type', 'Real Account', '1', 1, 1),
('account_type', 'Demo Account', '2', 2, 1),
('account_type', 'VIP Account', '3', 3, 1);

-- Account Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('account_status', 'Active', '1', 1, 1),
('account_status', 'Frozen', '2', 2, 1),
('account_status', 'Closed', '3', 3, 1),
('account_status', 'Force Closing', '4', 4, 1);

-- Risk Level
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('risk_level', 'Retail', '1', 1, 1),
('risk_level', 'Professional', '2', 2, 1),
('risk_level', 'Institutional', '3', 3, 1);

-- KYC Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('kyc_status', 'Not Submitted', '0', 0, 1),
('kyc_status', 'Pending Review', '1', 1, 1),
('kyc_status', 'Under Review', '2', 2, 1),
('kyc_status', 'Approved', '3', 3, 1),
('kyc_status', 'Rejected', '4', 4, 1);

-- Transaction Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('txn_type', 'Deposit', '1', 1, 1),
('txn_type', 'Withdrawal', '2', 2, 1),
('txn_type', 'Transfer', '3', 3, 1),
('txn_type', 'Trade', '4', 4, 1),
('txn_type', 'Settlement', '5', 5, 1),
('txn_type', 'Commission', '6', 6, 1),
('txn_type', 'Interest', '7', 7, 1),
('txn_type', 'Fee', '8', 8, 1),
('txn_type', 'Others', '9', 9, 1);

-- Symbol Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('symbol_type', 'Major Pairs', '1', 1, 1),
('symbol_type', 'Minor Pairs', '2', 2, 1),
('symbol_type', 'Exotic Pairs', '3', 3, 1),
('symbol_type', 'Precious Metals', '4', 4, 1),
('symbol_type', 'Indices', '5', 5, 1),
('symbol_type', 'Cryptocurrencies', '6', 6, 1);

-- Yes/No
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('yes_no', 'No', '0', 0, 1),
('yes_no', 'Yes', '1', 1, 1);
```

### 4.3 Super Admin Initialization

```sql
-- Create Super Admin
INSERT INTO cfg_tenant (tenant_code, tenant_name, tenant_type, status, created_by) VALUES
('SYSTEM', 'System', 1, 1, 0);

-- Create System Admin Roles
INSERT INTO usr_role (role_code, role_name, tenant_id, data_scope, status, sort_order, created_by) VALUES
('SUPER_ADMIN', 'Super Admin', 1, 1, 1, 1, 0),
('ADMIN', 'Admin', 1, 1, 1, 2, 0),
('OPERATOR', 'Operator', 1, 1, 1, 3, 0),
('RISK_MANAGER', 'Risk Manager', 1, 1, 1, 4, 0),
('COMPLIANCE', 'Compliance', 1, 1, 1, 5, 0),
('TRADER', 'Trader', 1, 4, 1, 6, 0);

-- Create System Admin User (Password: Admin@123)
INSERT INTO usr_user (user_no, tenant_id, username, password, nickname, email, status, created_by) VALUES
('SUPER001', 1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'System Admin', 'admin@globalfx.com', 1, 0);

-- Associate Admin Role
INSERT INTO usr_user_role (user_id, role_id) VALUES (1, 1);
```

### 4.4 Default Symbol Initialization

```sql
-- Major Pairs
INSERT INTO sym_symbol (symbol, symbol_name, symbol_type, base_currency, quote_currency, precision, status, tenant_id, created_by) VALUES
('EURUSD', 'EUR/USD', 1, 'EUR', 'USD', 5, 1, 1, 0),
('GBPUSD', 'GBP/USD', 1, 'GBP', 'USD', 5, 1, 1, 0),
('USDJPY', 'USD/JPY', 1, 'USD', 'JPY', 3, 1, 1, 0),
('USDCHF', 'USD/CHF', 1, 'USD', 'CHF', 5, 1, 1, 0),
('AUDUSD', 'AUD/USD', 1, 'AUD', 'USD', 5, 1, 1, 0),
('USDCAD', 'USD/CAD', 1, 'USD', 'CAD', 5, 1, 1, 0),
('NZDUSD', 'NZD/USD', 1, 'NZD', 'USD', 5, 1, 1, 0);

-- Precious Metals
INSERT INTO sym_symbol (symbol, symbol_name, symbol_type, base_currency, quote_currency, precision, status, tenant_id, created_by) VALUES
('XAUUSD', 'XAU/USD', 4, 'XAU', 'USD', 2, 1, 1, 0),
('XAGUSD', 'XAG/USD', 4, 'XAG', 'USD', 3, 1, 1, 0);

-- Default Risk Rules
INSERT INTO rsk_rule (rule_code, rule_name, rule_type, min_value, max_value, action, priority, status, tenant_id, created_by) VALUES
('MAX_POSITION', 'Max Position Per Symbol', 1, 0, 50, 1, 10, 1, 1, 0),
('SINGLE_ORDER_LIMIT', 'Max Single Order Volume', 2, 0, 10, 1, 20, 1, 1, 0),
('DAILY_TRADE_LIMIT', 'Daily Trading Limit', 3, 0, 1000000, 1, 30, 1, 1, 0),
('MARGIN_CALL_LEVEL', 'Margin Call Level', 4, 0, 100, 2, 5, 1, 1, 0),
('STOP_OUT_LEVEL', 'Stop Out Level', 4, 0, 50, 1, 5, 1, 1, 0);
```

## 5. DDL Script Specifications

- All table names use lowercase with underscores, prefixed by module name
- Field comments (COMMENT) must be filled in, describing the business meaning of the field
- Enumeration type fields use SMALLINT, not ENUM type
- Amount fields use DECIMAL(20,8), FLOAT/DOUBLE is prohibited
- Price fields use DECIMAL(20,10) for higher precision
- Time fields use TIMESTAMPTZ (UTC timezone)
- All foreign key relationships are maintained at the application layer; no foreign key constraints are created at the database layer
- Index naming: Normal index idx_, Unique index uk_, Composite index idx_ (multiple fields joined by underscores)
- Important business tables use optimistic locking (version field) to prevent concurrency issues
- TimescaleDB hypertables need to be created separately