# InterGlobal FX Trading Platform - Page Prototypes and Description

## 1. Route List

### 1.1 Trading Terminal (React - /trader)

| Route Path | Page Name | Component Path | Permission | Menu |
|------------|-----------|----------------|------------|------|
| /login | Login Page | pages/login/index | No login required | No |
| /register | Register Page | pages/register/index | No login required | No |
| / | Trading Home | pages/trading/index | Logged-in user | Yes |
| /trading | Trading Page | pages/trading/main | Logged-in user | Yes |
| /positions | Position Management | pages/positions/index | Logged-in user | Yes |
| /orders | Order History | pages/orders/index | Logged-in user | Yes |
| /account | Account Overview | pages/account/index | Logged-in user | Yes |
| /deposit | Deposit Page | pages/account/deposit | Logged-in user | Yes |
| /withdraw | Withdrawal Page | pages/account/withdraw | Logged-in user | Yes |
| /history | Fund History | pages/account/history | Logged-in user | Yes |
| /profile | Profile Center | pages/profile/index | Logged-in user | Yes |
| /kyc | KYC Verification | pages/profile/kyc | Logged-in user | Yes |
| /messages | Message Center | pages/messages/index | Logged-in user | Yes |

### 1.2 Admin Backend (Vue - /admin)

| Route Path | Page Name | Component Path | Permission | Menu |
|------------|-----------|----------------|------------|------|
| /admin/login | Login Page | views/login/index | No login required | No |
| /admin | Dashboard | views/dashboard/index | Logged-in user | Yes |
| /admin/order | Order Management | - | - | Yes |
| /admin/order/list | Order List | views/order/list | order:view | Yes |
| /admin/order/pending | Pending Orders | views/order/pending | order:view | Yes |
| /admin/position | Position Management | - | - | Yes |
| /admin/position/list | Position List | views/position/list | account:view | Yes |
| /admin/account | Account Management | - | - | Yes |
| /admin/account/list | Account List | views/account/list | account:view | Yes |
| /admin/account/deposit | Deposit Review | views/account/deposit | account:manage | Yes |
| /admin/account/withdraw | Withdrawal Review | views/account/withdraw | account:manage | Yes |
| /admin/customer | Customer Management | - | - | Yes |
| /admin/customer/list | Customer List | views/customer/list | crm:view | Yes |
| /admin/customer/kyc | KYC Review | views/customer/kyc | crm:manage | Yes |
| /admin/customer/follow | Customer Follow-up | views/customer/follow | crm:manage | Yes |
| /admin/ib | IB Management | - | - | Yes |
| /admin/ib/list | IB List | views/ib/list | ib:view | Yes |
| /admin/ib/commission | Commission Management | views/ib/commission | ib:manage | Yes |
| /admin/risk | Risk Management | - | - | Yes |
| /admin/risk/rules | Risk Rules | views/risk/rules | risk:view | Yes |
| /admin/risk/events | Risk Events | views/risk/events | risk:view | Yes |
| /admin/risk/blacklist | Blacklist | views/risk/blacklist | risk:manage | Yes |
| /admin/quote | Market Data Management | - | - | Yes |
| /admin/quote/symbols | Symbol Management | views/quote/symbols | risk:manage | Yes |
| /admin/quote/spread | Spread Configuration | views/quote/spread | risk:manage | Yes |
| /admin/report | Report Management | - | - | Yes |
| /admin/report/trade | Trade Report | views/report/trade | report:view | Yes |
| /admin/report/customer | Customer Report | views/report/customer | report:view | Yes |
| /admin/report/risk | Risk Report | views/report/risk | report:view | Yes |
| /admin/compliance | Compliance Management | - | - | Yes |
| /admin/compliance/rules | Compliance Rules | views/compliance/rules | compliance:view | Yes |
| /admin/compliance/aml | AML Monitoring | views/compliance/aml | compliance:view | Yes |
| /admin/ticket | Ticket Management | - | - | Yes |
| /admin/ticket/list | Ticket List | views/ticket/list | ticket:view | Yes |
| /admin/system | System Management | - | - | Yes |
| /admin/system/user | User Management | views/system/user | system:view | Yes |
| /admin/system/role | Role Management | views/system/role | system:manage | Yes |
| /admin/system/menu | Menu Management | views/system/menu | system:manage | Yes |
| /admin/system/dict | Dictionary Management | views/system/dict | system:manage | Yes |
| /admin/system/tenant | Tenant Management | views/system/tenant | system:manage | Yes |
| /admin/system/audit | Audit Log | views/system/audit | audit:view | Yes |
| /admin/notice | Notice Management | - | - | Yes |
| /admin/notice/list | Announcement Management | views/notice/list | admin:notice | Yes |

---

## 2. Trading Terminal Page Description

### 2.1 Trading Main Page

**Route**: /trading
**Component**: pages/trading/main.tsx
**Permission**: Logged-in user

**Page Layout:**
```
+--------------------------------------------------------------------------+
|  [Logo]  InterGlobal FX   [EURUSD][GBPUSD][USDJPY][...]   [Account] [Messages] [Settings] |
+--------------------------------------------------------------------------+
|                    |                              |                      |
|   Currency Pair List |       Candlestick Chart Area |    Trading Panel      |
|  +--------------+  |  +------------------------+  |  +----------------+  |
|  | EURUSD  1.08 |  |  |                        |  |  |  Symbol       |  |
|  | GBPUSD  1.27 |  |  |    [Candlestick Chart] |  |  |  EURUSD       |  |
|  | USDJPY 149.5 |  |  |                        |  |  +----------------+  |
|  | ...         |  |  |                        |  |  | [Buy] [Sell]  |  |
|  +--------------+  |  +------------------------+  |  |  Lots: [0.10] |  |
|                    |  [1M][5M][15M][1H][4H][1D] |  |  Market Order  |  |
|   Market News      |                              |  +----------------+  |
|  +--------------+  |  Market Depth                |                      |
|  | News Title...|  |  Ask: 1.08530 (100)          |    Quick Order        |
|  | Financial... |  |  Ask: 1.08525 (250)          |  +----------------+  |
|  +--------------+  |  --------------------------  |  | Buy 0.10 EURUSD|  |
|                    |  Bid: 1.08520 (150)          |  | Sell 0.10 EURUSD| |
|                    |  Bid: 1.08515 (300)          |  +----------------+  |
+--------------------------------------------------------------------------+
```

**Functional Description:**
1. Left-side currency pair list: Click to switch trading symbols
2. Center candlestick chart: Supports multi-period switching, manual zoom, crosshair
3. Right-side trading panel: Order form, quick trading buttons
4. Real-time quotes: WebSocket push, millisecond-level updates

---

### 2.2 Position Management Page

**Route**: /positions
**Component**: pages/positions/index.tsx
**Permission**: Logged-in user

**Page Layout:**
```
+--------------------------------------------------------------------------+
|  Position Management                               [+Close All] [Export] |
+--------------------------------------------------------------------------+
|  [Search]: Symbol [EURUSD ▼]  Direction [All ▼]  Status [Open ▼]  [Search] [Reset] |
+--------------------------------------------------------------------------+
|  Select All | Ticket | Symbol | Direction | Lots | Open Price | Current Price | P/L | P/L% | ... |
|  +---------------------------------------------------------------+      |
|  | ☐ | 660e84... | EURUSD | Buy  | 0.10 | 1.08500  | 1.08600    | +$15.50 | |
|  | ☐ | 661e85... | GBPUSD | Sell | 0.05 | 1.27000  | 1.26800    | +$10.00 | |
|  +---------------------------------------------------------------+      |
+--------------------------------------------------------------------------+
|  Summary: Total Positions 2 | Total Margin $350.00 | Floating P/L +$25.50 | P/L% +7.29% |
+--------------------------------------------------------------------------+
|  [Pagination] Total 2 records  < 1 >  20 per page                       |
+--------------------------------------------------------------------------+
```

**Position Detail Card:**
```
+------------------------------------------+
|  EURUSD Long  0.10 lots                   |
+------------------------------------------+
|  Open Price: 1.08500     Current Price: 1.08600 |
|  P/L: +$15.50 ( +5.12% )                 |
|  Margin: $30.00         Swap: -$0.50      |
|  Open Time: 2024-01-01 10:00:00          |
+------------------------------------------+
|  [Close] [TP/SL] [Edit] [Trailing Stop]   |
+------------------------------------------+
```

**Interaction Rules:**
1. Click a position row to expand the detail card
2. Close button executes market close immediately
3. TP/SL settings open a modal dialog
4. Close All requires secondary confirmation

---

### 2.3 Account Overview Page

**Route**: /account
**Component**: pages/account/index.tsx
**Permission**: Logged-in user

**Page Layout:**
```
+--------------------------------------------------------------------------+
|  Account Overview                                    [Demo Account] [Switch] |
+--------------------------------------------------------------------------+
|  +----------------------+  +----------------------+                     |
|  | Account Balance      |  | Equity               |                     |
|  | $10,000.00           |  | $10,015.50           |                     |
|  +----------------------+  +----------------------+                     |
|  +----------------------+  +----------------------+                     |
|  | Free Margin          |  | Used Margin          |                     |
|  | $9,685.50            |  | $330.00              |                     |
|  +----------------------+  +----------------------+                     |
|  +----------------------+  +----------------------+                     |
|  | Margin Level         |  | Today's P/L          |                     |
|  | 303.50%              |  | +$15.50              |                     |
|  +----------------------+  +----------------------+                     |
+--------------------------------------------------------------------------+
|  Account Info:                                                          |
|  Account No: GFX0012345  |  Leverage: 1:30  |  Type: Real Account  |  Status: Active |
+--------------------------------------------------------------------------+
|  [+Deposit]  [+Withdrawal]  [+Internal Transfer]                        |
+--------------------------------------------------------------------------+
```

---

## 3. Admin Backend Page Description

### 3.1 Order List Page

**Route**: /admin/order/list
**Component**: views/order/list.vue
**Permission**: order:view

**Page Layout:**
```
+----------------------------------------------------------------------+
|  Order Management > Order List                    [+Export] [Refresh] |
+----------------------------------------------------------------------+
|  [Search Area]                                                      |
|  Order No: [________________]  Symbol: [____________▼]  Status: [____▼] |
|  Account:  [________________]  Type:   [____________▼]  Time:   [__~__] |
|                                                     [Search] [Reset] |
+----------------------------------------------------------------------+
|  [Action Bar]  Batch Cancel                                          |
+----------------------------------------------------------------------+
|  [Data Table]                                                       |
| +--------------------------------------------------------------+   |
| |  □ | Order No | Account | Symbol | Type | Direction | Lots | Price | Status |   |
| |----|----------|---------|--------|------|-----------|------|-------|--------|   |
| | □ | 550e84  | Zhang San | EURUSD | Market | Buy  | 0.10 |1.0850| Filled |   |
| | □ | 551e85  | Li Si    | GBPUSD | Limit  | Sell | 0.05 |1.2700| Pending |   |
| +--------------------------------------------------------------+   |
+----------------------------------------------------------------------+
|  [Pagination]  Total 1,234 records  < 1 2 3 ... 10 >  Per page [20▼] records |
+----------------------------------------------------------------------+
```

**Search Conditions:**

| Field | Component Type | Dictionary/Options | Default |
|-------|----------------|-------------------|---------|
| Order No | Input | - | - |
| Symbol | Dropdown Select | symbol_dict | All |
| Status | Dropdown Select | order_status | All |
| Account | Input | - | - |
| Type | Dropdown Select | order_type | All |
| Time Range | Date Range | - | Last 7 days |

**Table Column Definitions:**

| Column Name | Field | Width | Alignment | Format | Sortable |
|-------------|-------|-------|-----------|--------|----------|
| No. | index | 60px | center | - | No |
| Order No | orderNo | 180px | left | Copy button | No |
| Account | accountNo | 120px | left | Link to detail | No |
| Symbol | symbol | 100px | center | Currency pair | No |
| Type | orderType | 80px | center | Dictionary | No |
| Direction | orderSide | 60px | center | Buy (Red) / Sell (Green) | No |
| Lots | lotSize | 80px | right | 2 decimal places | Yes |
| Price | price | 100px | right | Precision follows symbol | Yes |
| Status | status | 80px | center | Tag colors | No |
| Time | createdTime | 160px | center | yyyy-MM-dd HH:mm:ss | Yes |
| Actions | action | 120px | center | Button group | No |

**Dictionary Data Mapping:**

| Field | Dictionary Type | Display |
|-------|----------------|---------|
| orderType | order_type | Tag |
| orderSide | order_side | Buy (Red) / Sell (Green) |
| status | order_status | Tag, different colors for different statuses |

**Action Buttons:**

| Button | Permission | Interaction | Description |
|--------|------------|-------------|-------------|
| Detail | order:view | Drawer/Modal | Display full order information |
| Cancel | order:cancel | Confirmation dialog | Cancel unfilled orders |
| Retry | order:manage | Direct execution | Retry failed orders |

**Interaction Rules:**
1. Automatically query list on page load
2. After search conditions change, click search to reset to page 1
3. Click table row to expand and view order details
4. Order status updates in real-time (WebSocket push)
5. Export supports Excel format

---

### 3.2 Customer List Page

**Route**: /admin/customer/list
**Component**: views/customer/list.vue
**Permission**: crm:view

**Page Layout:**
```
+----------------------------------------------------------------------+
|  Customer Management > Customer List              [+New] [Export]     |
+----------------------------------------------------------------------+
|  [Search Area]                                                      |
|  Customer No: [________________]  Name: [________________]  Status: [____▼] |
|  Risk Level: [____▼]  Source: [____▼]  IB Manager: [____▼]  Registration Time: [__~__] |
|                                                     [Search] [Reset] |
+----------------------------------------------------------------------+
|  [Data Table]                                                       |
| +--------------------------------------------------------------+   |
| | Customer No | Name | Email | Risk Level | Accounts | Deposit | Status | Actions |   |
| |-------------|------|-------|------------|----------|---------|--------|---------|   |
| | CUS...      | Zhang San | t@.. | Retail | 2  | $10K | Active | [Detail] |   |
| | CUS...      | Li Si    | l@.. | Professional | 1 | $50K | Dormant | [Detail] |   |
| +--------------------------------------------------------------+   |
+----------------------------------------------------------------------+
```

**Customer Detail Modal:**
```
+------------------------------------------------------------------+
|  Customer Detail                                     [X]          |
+------------------------------------------------------------------+
|  Basic Information                                                |
|  +--------------------------------------------------------------+ |
|  | Customer No: CUS20240101001                                  | |
|  | Name: Zhang San                                               | |
|  | Email: test@example.com                                       | |
|  | Phone: +86 138****8000                                        | |
|  | Country: China                                                | |
|  | Risk Level: Retail                                            | |
|  +--------------------------------------------------------------+ |
|  Account Information                                              |
|  +--------------------------------------------------------------+ |
|  | Account No | Type   | Equity | Status |                      | |
|  | GFX00..    | Real   | $10K   | Active |                      | |
|  +--------------------------------------------------------------+ |
|  Trading Statistics                                               |
|  +--------------------------------------------------------------+ |
|  | Total Trades: 150 | Total Deposits: $10,000 | Total Withdrawals: $5,000 | |
|  | P/L Distribution: Profitable 60% Losing 40%                   | |
|  +--------------------------------------------------------------+ |
+------------------------------------------------------------------+
```

---

### 3.3 Risk Rules Page

**Route**: /admin/risk/rules
**Component**: views/risk/rules.vue
**Permission**: risk:manage

**Page Layout:**
```
+----------------------------------------------------------------------+
|  Risk Management > Risk Rules                     [+New Rule] [Export] |
+----------------------------------------------------------------------+
|  [Search]: Rule Type [____▼]  Status [____▼]  [Search] [Reset]       |
+----------------------------------------------------------------------+
|  [Data Table]                                                       |
| +--------------------------------------------------------------+   |
| | Rule Code | Rule Name | Type | Min Value | Max Value | Action | Status | Actions | |
| |-----------|-----------|------|-----------|-----------|--------|--------|---------| |
| | MAX_POS.. | Max Position per Symbol | Position Limit | 0 | 50 | Reject | Active | [Edit] | |
| | SINGLE_.. | Max Single Trade Volume | Order Limit | 0 | 10 | Reject | Active | [Edit] | |
| +--------------------------------------------------------------+   |
+----------------------------------------------------------------------+
```

**Add/Edit Rule Modal:**
```
+------------------------------------------------------------------+
|  Add Risk Rule                                       [X]          |
+------------------------------------------------------------------+
|  Basic Information                                                |
|  Rule Code: [____________] *                                       |
|  Rule Name: [____________] *                                       |
|  Rule Type: [____________▼] *                                      |
|                                                                  |
|  Rule Configuration                                               |
|  Applicable Symbols: [All ▼] * or select specific symbols         |
|  Min Value:   [____________]                                       |
|  Max Value:   [____________] *                                     |
|                                                                  |
|  Trigger Action: [Reject ▼] *                                      |
|  Priority:   [100___] (lower number = higher priority)             |
|                                                                  |
|  Effective Time: [Immediate ▼] or specify time                    |
|                                                                  |
|                              [Cancel]            [Confirm]         |
+------------------------------------------------------------------+
```

---

### 3.4 Dashboard Page

**Route**: /admin
**Component**: views/dashboard/index.vue
**Permission**: Logged-in user

**Page Layout:**
```
+----------------------------------------------------------------------+
|  Dashboard                                        2024-01-01 10:00  |
+----------------------------------------------------------------------+
|  +------------+  +------------+  +------------+  +------------+    |
|  | Today's    |  | Today's    |  | Active     |  | Pending    |    |
|  | Trade Vol  |  | Orders     |  | Customers  |  | Events     |    |
|  |  $1.2M    |  |   1,234    |  |    567     |  |     23     |    |
|  |  ↑12.5%   |  |   ↑8.2%   |  |   ↑3.1%   |  |  ↓15.2%   |    |
|  +------------+  +------------+  +------------+  +------------+    |
+----------------------------------------------------------------------+
|  +--------------------------------+  +----------------------------+ |
|  | Trade Volume Trend (7 Days)    |  | Customer P/L Distribution | |
|  |                                |  |                            | |
|  |  [Line Chart]                 |  |  [Pie Chart]               | |
|  |                                |  |                            | |
|  +--------------------------------+  +----------------------------+ |
+----------------------------------------------------------------------+
|  +--------------------------------+  +----------------------------+ |
|  | Pending Items                  |  | Latest Orders              | |
|  |  □ 3 withdrawals pending review |  |  550e84... EURUSD Market Buy | |
|  |  □ 5 KYC pending review        |  |  551e85... GBPUSD Limit Sell | |
|  |  □ 2 tickets awaiting reply    |  |  552e86... XAUUSD Market Buy | |
|  |  □ 1 risk event pending        |  |                            | |
|  +--------------------------------+  +----------------------------+ |
+----------------------------------------------------------------------+
```

---

## 4. Mobile Page Description

### 4.1 Trading Home Page

**Route**: / (Home Tab)
**Component**: screens/trading/trading_screen.dart
**Permission**: Logged-in user

**Page Layout:**
```
+------------------------+
|  [≡]  EURUSD  [≡]     |
+------------------------+
|                        |
|  1.08520   1.08530    |
|  (Bid)    (Ask)       |
|  Spread: 1.0           |
|                        |
+------------------------+
|  [Candlestick Chart]   |
|                        |
|  [1M][5M][15M][1H]     |
+------------------------+
|                        |
|  +--------------------+|
|  | Buy                ||
|  | Lots: [0.10]  ▼   ||
|  | [Market Buy]       ||
|  +--------------------+|
|  +--------------------+|
|  | Sell               ||
|  | Lots: [0.10]  ▼   ||
|  | [Market Sell]      ||
|  +--------------------+|
+------------------------+
|  [Quotes] [Trade] [Positions] |
|         [Positions]    |
+------------------------+
```

---

### 4.2 Position List

**Route**: /positions (Tab)
**Component**: screens/trading/positions_screen.dart

**Page Layout:**
```
+------------------------+
|  Position Management [Clear] |
+------------------------+
|  Equity: $10,015.50     |
|  Floating P/L: +$25.50  |
+------------------------+
|  +--------------------+|
|  | EURUSD  Long  0.10 ||
|  | Open: 1.08500       ||
|  | Current: 1.08600    ||
|  | P/L: +$15.50       ||
|  |           [Close]   ||
|  +--------------------+|
|  +--------------------+|
|  | GBPUSD  Short 0.05 ||
|  | Open: 1.27000       ||
|  | Current: 1.26800    ||
|  | P/L: +$10.00       ||
|  |           [Close]   ||
|  +--------------------+|
+------------------------+
|  [Quotes] [Trade] [Positions] |
+------------------------+
```

---

## 5. Common Page Description

### 5.1 Login Page

**Route**: /login (trader) / /admin/login (admin)
**Component**: pages/login/index.tsx / views/login/index.vue

**Page Layout:**
```
+------------------------+
|                        |
|     [Logo]             |
|    InterGlobal FX      |
|                        |
|  Username: [__________] |
|  Password: [__________] |
|                        |
|  [      Login      ]   |
|                        |
|  Don't have an account? [Register] |
|  Forgot Password?      |
|                        |
+------------------------+
```

---

### 5.2 KYC Verification Page

**Route**: /kyc (trader)
**Component**: pages/profile/kyc.tsx

**Verification Steps:**
1. Personal information form
2. ID upload (front and back of ID card)
3. Facial recognition verification
4. Address proof upload
5. Submit for review

**Page Layout:**
```
+------------------------+
|  ← Identity Verification |
+------------------------+
|  Verification Level: Basic Verification |
|                        |
|  [===-----] 40%        |
|                        |
|  Current Step: Upload ID |
|                        |
|  +--------------------+|
|  |                    ||
|  |  [Upload ID Front] ||
|  |                    ||
|  +--------------------+|
|  +--------------------+|
|  |                    ||
|  |  [Upload ID Back]  ||
|  |                    ||
|  +--------------------+|
|                        |
|  [     Next Step     ]  |
|                        |
+------------------------+
```
