# Interglobal FX Trading Platform - Dictionary Documentation

## 1. Dictionary List

| No. | Dictionary Type | Dictionary Name | Description |
|------|----------|----------|------|
| 1 | order_type | Order Type | Order type enumeration |
| 2 | order_side | Order Side | Buy/Sell |
| 3 | order_status | Order Status | Order status enumeration |
| 4 | order_source | Order Source | Web/App/API/MT4/MT5/FIX |
| 5 | time_in_force | Order Validity | GTC/GFD/GTD |
| 6 | execution_mode | Execution Mode | A-Book/B-Book/Hybrid |
| 7 | position_side | Position Side | Long/Short |
| 8 | position_status | Position Status | Open/Closed/Partially Closed/Force Closing |
| 9 | account_type | Account Type | Real/Demo/VIP |
| 10 | account_status | Account Status | Active/Frozen/Closed/Force Closing |
| 11 | account_platform | Account Platform | Proprietary/MT4/MT5 |
| 12 | risk_level | Customer Risk Level | Retail/Professional/Institutional |
| 13 | customer_type | Customer Type | Individual/Corporate |
| 14 | customer_status | Customer Status | Potential/Active/Dormant/Churned |
| 15 | customer_source | Customer Source | Organic Traffic/IB Referral/Advertising/Partner Channels |
| 16 | kyc_status | KYC Status | Not Submitted/Pending Review/Under Review/Approved/Rejected |
| 17 | kyc_level | KYC Level | Basic Verification/Advanced Verification/Corporate Verification |
| 18 | txn_type | Transaction Type | Deposit/Withdrawal/Transfer/Trade/Settlement/Commission/Interest/Fee |
| 19 | deposit_status | Deposit Status | Pending Payment/Processing/Completed/Cancelled/Failed |
| 20 | withdrawal_status | Withdrawal Status | Pending Review/Under Review/Approved/Rejected/Processing/Completed |
| 21 | ib_level | IB Level | IB/White Label/Strategic Partner |
| 22 | ib_commission_type | IB Commission Type | Fixed Commission/Percentage Commission/Tiered Commission |
| 23 | ib_status | IB Status | Pending Review/Active/Suspended/Terminated |
| 24 | symbol_type | Symbol Type | Major Pairs/Minor Pairs/Exotic/Precious Metals/Indices/Cryptocurrencies |
| 25 | symbol_status | Symbol Status | Active/Disabled |
| 26 | trade_mode | Trade Mode | Tradable/View Only/Disabled |
| 27 | swap_mode | Swap Calculation Mode | Points/Percentage |
| 28 | risk_rule_type | Risk Rule Type | Position Limit/Single Order Limit/Daily Trading Limit/Margin Ratio/Anomaly Detection |
| 29 | risk_action | Risk Action | Reject/Warning/Delay/Manual Review |
| 30 | risk_action_level | Risk Action Level | Low/Medium/High |
| 31 | risk_event_type | Risk Event Type | Order Rejection/Warning/Force Liquidation/Account Freeze/Anomaly Detection |
| 32 | risk_event_status | Risk Event Status | Pending/Processing/Processed/Ignored |
| 33 | ticket_category | Ticket Category | Trading Issue/Withdrawal Issue/Account Issue/System Issue/Complaint/Suggestion/Other |
| 34 | ticket_priority | Ticket Priority | Urgent/High/Medium/Low |
| 35 | ticket_status | Ticket Status | Pending Assignment/In Progress/Awaiting Reply/Resolved/Closed |
| 36 | aml_event_type | AML Event Type | Large Transaction/Suspicious Transaction/Sanctions List Hit |
| 37 | aml_event_status | AML Event Status | Pending/Processing/Processed/Ignored |
| 38 | user_status | User Status | Active/Disabled/Locked |
| 39 | role_status | Role Status | Active/Disabled |
| 40 | tenant_status | Tenant Status | Active/Trial/Suspended/Terminated |
| 41 | yes_no | Yes/No | Yes/No |
| 42 | gender | Gender | Male/Female/Unknown |
| 43 | data_scope | Data Scope | All Data/This Tenant/This Department/Self Only |
| 44 | lp_type | LP Type | Bank/ECN/LP/Exchange |
| 45 | lp_status | LP Status | Enabled/Disabled/Maintenance |
| 46 | region_code | Regulatory Region | ESMA/FCA/ASIC/NFA/CIMA/Other |
| 47 | notice_type | Notice Type | System Notice/Event Notification/Risk Alert |
| 48 | notice_status | Notice Status | Draft/Published/Unpublished |

## 2. Dictionary Details

### 2.1 Order Type (order_type)

**Purpose**: trd_order.order_type

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Market Order | 1 | 1 | primary | Market Order |
| Limit Order | 2 | 2 | primary | Limit Order |
| Stop Order | 3 | 3 | warning | Stop Order |
| Take Profit Order | 4 | 4 | warning | Take Profit Order |
| Stop Limit Order | 5 | 5 | warning | Stop Limit Order |
| Pending Order | 6 | 6 | info | Pending Order |

### 2.2 Order Side (order_side)

**Purpose**: trd_order.order_side

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Buy | 1 | 1 | danger | Buy/Long |
| Sell | 2 | 2 | success | Sell/Short |

### 2.3 Order Status (order_status)

**Purpose**: trd_order.status

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Pending | 1 | 1 | warning | Pending |
| Partially Filled | 2 | 2 | primary | Partially Filled |
| Filled | 3 | 3 | success | Filled |
| Cancelled | 4 | 4 | info | Cancelled |
| Rejected | 5 | 5 | danger | Rejected |
| Expired | 6 | 6 | default | Expired |

### 2.4 Order Source (order_source)

**Purpose**: trd_order.channel

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Web | 1 | 1 | primary | Trading Terminal Web |
| App | 2 | 2 | success | Mobile App |
| API | 3 | 3 | info | REST API |
| MT4 | 4 | 4 | warning | MetaTrader 4 |
| MT5 | 5 | 5 | warning | MetaTrader 5 |
| FIX Protocol | 6 | 6 | danger | FIX Protocol |

### 2.5 Position Status (position_status)

**Purpose**: pos_position.status

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Open | 1 | 1 | primary | Open |
| Closed | 2 | 2 | success | Closed |
| Partially Closed | 3 | 3 | warning | Partially Closed |
| Force Closing | 4 | 4 | danger | Force Closing |

### 2.6 Account Type (account_type)

**Purpose**: acc_account.account_type

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Real Account | 1 | 1 | primary | Real Account |
| Demo Account | 2 | 2 | info | Demo Account |
| VIP Account | 3 | 3 | warning | VIP Account |

### 2.7 Account Status (account_status)

**Purpose**: acc_account.status

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Active | 1 | 1 | success | Active |
| Frozen | 2 | 2 | warning | Frozen |
| Closed | 3 | 3 | default | Closed |
| Force Closing | 4 | 4 | danger | Force Closing |

### 2.8 Customer Risk Level (risk_level)

**Purpose**: acc_account.risk_level, crm_customer.risk_level

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Retail | 1 | 1 | primary | Retail |
| Professional | 2 | 2 | warning | Professional |
| Institutional | 3 | 3 | success | Institutional |

### 2.9 KYC Status (kyc_status)

**Purpose**: kyc_application.status, acc_account.kyc_status

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Not Submitted | 0 | 0 | default | Not Submitted |
| Pending Review | 1 | 1 | warning | Pending Review |
| Under Review | 2 | 2 | primary | Under Review |
| Approved | 3 | 3 | success | Approved |
| Rejected | 4 | 4 | danger | Rejected |

### 2.10 Transaction Type (txn_type)

**Purpose**: wlt_transaction.txn_type

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Deposit | 1 | 1 | success | Deposit |
| Withdrawal | 2 | 2 | danger | Withdrawal |
| Transfer | 3 | 3 | primary | Transfer |
| Trade | 4 | 4 | info | Trade |
| Settlement | 5 | 5 | warning | Settlement |
| Commission | 6 | 6 | success | Commission |
| Interest/Swap | 7 | 7 | info | Interest/Swap |
| Fee | 8 | 8 | danger | Fee |
| Others | 9 | 9 | default | Others |

### 2.11 Deposit Status (deposit_status)

**Purpose**: txn_deposit.status

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Pending Payment | 1 | 1 | warning | Pending Payment |
| Processing | 2 | 2 | primary | Processing |
| Completed | 3 | 3 | success | Completed |
| Cancelled | 4 | 4 | default | Cancelled |
| Failed | 5 | 5 | danger | Failed |

### 2.12 Withdrawal Status (withdrawal_status)

**Purpose**: txn_withdrawal.status

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Pending Review | 1 | 1 | warning | Pending Review |
| Under Review | 2 | 2 | primary | Under Review |
| Approved | 3 | 3 | success | Approved |
| Rejected | 4 | 4 | danger | Rejected |
| Processing | 5 | 5 | primary | Processing |
| Completed | 6 | 6 | success | Completed |

### 2.13 Symbol Type (symbol_type)

**Purpose**: sym_symbol.symbol_type

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Major Pairs | 1 | 1 | success | Major Pairs |
| Minor Pairs | 2 | 2 | primary | Minor Pairs |
| Exotic Pairs | 3 | 3 | warning | Exotic Pairs |
| Precious Metals | 4 | 4 | gold | Precious Metals |
| Indices | 5 | 5 | info | Indices |
| Cryptocurrencies | 6 | 6 | orange | Cryptocurrencies |

### 2.14 Risk Event Type (risk_event_type)

**Purpose**: rsk_event.event_type

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Order Rejected | 1 | 1 | danger | Order Rejected |
| Risk Warning | 2 | 2 | warning | Risk Warning |
| Force Liquidation | 3 | 3 | danger | Force Liquidation |
| Account Frozen | 4 | 4 | danger | Account Frozen |
| Anomaly Detected | 5 | 5 | warning | Anomaly Detected |

### 2.15 Risk Action (risk_action)

**Purpose**: rsk_rule.action

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Reject | 1 | 1 | danger | Reject |
| Warn | 2 | 2 | warning | Warn |
| Delay | 3 | 3 | primary | Delay |
| Manual Review | 4 | 4 | info | Manual Review |

### 2.16 Ticket Category (ticket_category)

**Purpose**: tic_ticket.category

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Trading Issue | 1 | 1 | primary | Trading Issue |
| Withdrawal Issue | 2 | 2 | danger | Withdrawal Issue |
| Account Issue | 3 | 3 | warning | Account Issue |
| System Issue | 4 | 4 | info | System Issue |
| Complaint | 5 | 5 | danger | Complaint |
| Suggestion | 6 | 6 | success | Suggestion |
| Others | 7 | 7 | default | Others |

### 2.17 Ticket Status (ticket_status)

**Purpose**: tic_ticket.status

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Pending Assignment | 1 | 1 | warning | Pending Assignment |
| In Progress | 2 | 2 | primary | In Progress |
| Awaiting Reply | 3 | 3 | info | Awaiting Reply |
| Resolved | 4 | 4 | success | Resolved |
| Closed | 5 | 5 | default | Closed |

### 2.18 Ticket Priority (ticket_priority)

**Purpose**: tic_ticket.priority

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| Urgent | 1 | 1 | danger | Urgent |
| High | 2 | 2 | warning | High |
| Medium | 3 | 3 | primary | Medium |
| Low | 4 | 4 | default | Low |

### 2.19 Yes/No (yes_no)

**Purpose**: General boolean field

| Dictionary Label | Dictionary Value | Sort | Style | Description |
|----------|--------|------|------|------|
| No | 0 | 0 | default | No |
| Yes | 1 | 1 | success | Yes |

## 3. Dictionary Data Initialization SQL

```sql
-- Dictionary type initialization
INSERT INTO dic_type (dict_type, dict_name, tenant_id, status) VALUES
('order_type', 'Order Type', 0, 1),
('order_side', 'Order Side', 0, 1),
('order_status', 'Order Status', 0, 1),
('position_status', 'Position Status', 0, 1),
('account_type', 'Account Type', 0, 1),
('account_status', 'Account Status', 0, 1),
('risk_level', 'Customer Risk Level', 0, 1),
('kyc_status', 'KYC Status', 0, 1),
('txn_type', 'Transaction Type', 0, 1),
('symbol_type', 'Symbol Type', 0, 1),
('risk_action', 'Risk Action', 0, 1),
('risk_event_type', 'Risk Event Type', 0, 1),
('ticket_category', 'Ticket Category', 0, 1),
('ticket_status', 'Ticket Status', 0, 1),
('yes_no', 'Yes/No', 0, 1);

-- Order Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('order_type', 'Market Order', '1', 1, 1, 0),
('order_type', 'Limit Order', '2', 2, 1, 0),
('order_type', 'Stop Order', '3', 3, 1, 0),
('order_type', 'Take Profit Order', '4', 4, 1, 0),
('order_type', 'Stop Limit Order', '5', 5, 1, 0),
('order_type', 'Pending Order', '6', 6, 1, 0);

-- Order Side
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('order_side', 'Buy', '1', 1, 1, 0),
('order_side', 'Sell', '2', 2, 1, 0);

-- Order Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('order_status', 'Pending', '1', 1, 1, 0),
('order_status', 'Partially Filled', '2', 2, 1, 0),
('order_status', 'Filled', '3', 3, 1, 0),
('order_status', 'Cancelled', '4', 4, 1, 0),
('order_status', 'Rejected', '5', 5, 1, 0),
('order_status', 'Expired', '6', 6, 1, 0);

-- Position Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('position_status', 'Open', '1', 1, 1, 0),
('position_status', 'Closed', '2', 2, 1, 0),
('position_status', 'Partially Closed', '3', 3, 1, 0),
('position_status', 'Force Closing', '4', 4, 1, 0);

-- Account Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('account_type', 'Real Account', '1', 1, 1, 0),
('account_type', 'Demo Account', '2', 2, 1, 0),
('account_type', 'VIP Account', '3', 3, 1, 0);

-- Account Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('account_status', 'Active', '1', 1, 1, 0),
('account_status', 'Frozen', '2', 2, 1, 0),
('account_status', 'Closed', '3', 3, 1, 0),
('account_status', 'Force Closing', '4', 4, 1, 0);

-- Customer Risk Level
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('risk_level', 'Retail', '1', 1, 1, 0),
('risk_level', 'Professional', '2', 2, 1, 0),
('risk_level', 'Institutional', '3', 3, 1, 0);

-- KYC Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('kyc_status', 'Not Submitted', '0', 0, 1, 0),
('kyc_status', 'Pending Review', '1', 1, 1, 0),
('kyc_status', 'Under Review', '2', 2, 1, 0),
('kyc_status', 'Approved', '3', 3, 1, 0),
('kyc_status', 'Rejected', '4', 4, 1, 0);

-- Transaction Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('txn_type', 'Deposit', '1', 1, 1, 0),
('txn_type', 'Withdrawal', '2', 2, 1, 0),
('txn_type', 'Transfer', '3', 3, 1, 0),
('txn_type', 'Trade', '4', 4, 1, 0),
('txn_type', 'Settlement', '5', 5, 1, 0),
('txn_type', 'Commission', '6', 6, 1, 0),
('txn_type', 'Interest/Swap', '7', 7, 1, 0),
('txn_type', 'Fee', '8', 8, 1, 0),
('txn_type', 'Others', '9', 9, 1, 0);

-- Symbol Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('symbol_type', 'Major Pairs', '1', 1, 1, 0),
('symbol_type', 'Minor Pairs', '2', 2, 1, 0),
('symbol_type', 'Exotic Pairs', '3', 3, 1, 0),
('symbol_type', 'Precious Metals', '4', 4, 1, 0),
('symbol_type', 'Indices', '5', 5, 1, 0),
('symbol_type', 'Cryptocurrencies', '6', 6, 1, 0);

-- Risk Action
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('risk_action', 'Reject', '1', 1, 1, 0),
('risk_action', 'Warn', '2', 2, 1, 0),
('risk_action', 'Delay', '3', 3, 1, 0),
('risk_action', 'Manual Review', '4', 4, 1, 0);

-- Risk Event Type
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('risk_event_type', 'Order Rejected', '1', 1, 1, 0),
('risk_event_type', 'Risk Warning', '2', 2, 1, 0),
('risk_event_type', 'Force Liquidation', '3', 3, 1, 0),
('risk_event_type', 'Account Frozen', '4', 4, 1, 0),
('risk_event_type', 'Anomaly Detected', '5', 5, 1, 0);

-- Ticket Category
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('ticket_category', 'Trading Issue', '1', 1, 1, 0),
('ticket_category', 'Withdrawal Issue', '2', 2, 1, 0),
('ticket_category', 'Account Issue', '3', 3, 1, 0),
('ticket_category', 'System Issue', '4', 4, 1, 0),
('ticket_category', 'Complaint', '5', 5, 1, 0),
('ticket_category', 'Suggestion', '6', 6, 1, 0),
('ticket_category', 'Others', '7', 7, 1, 0);

-- Ticket Status
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('ticket_status', 'Pending Assignment', '1', 1, 1, 0),
('ticket_status', 'In Progress', '2', 2, 1, 0),
('ticket_status', 'Awaiting Reply', '3', 3, 1, 0),
('ticket_status', 'Resolved', '4', 4, 1, 0),
('ticket_status', 'Closed', '5', 5, 1, 0);

-- Yes/No
INSERT INTO dic_data (dict_type, dict_label, dict_value, sort_order, status, tenant_id) VALUES
('yes_no', 'No', '0', 0, 1, 0),
('yes_no', 'Yes', '1', 1, 1, 0);
```
