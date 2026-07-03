# Global汇通 Forex Trading Platform - Permission Design Document

## 1. Permission Model

Adopts RBAC (Role-Based Access Control) model:

```
User(User) ---N:N--- Role(Role) ---N:N--- Permission(Permission)
                    |
                    v
              DataScope(DataScope)
```

- Users can be assigned multiple roles
- Roles can be associated with multiple permissions
- Permissions are divided into: menu permissions, button permissions, API permissions
- Supports data permission control (by tenant/department/individual)

## 2. Permission Table Structure

### 2.1 Role Table (usr_role)

| Field Name | Type | Description |
|--------|------|------|
| id | BIGSERIAL | Primary key |
| role_code | VARCHAR(50) | Role code (unique) |
| role_name | VARCHAR(100) | Role name |
| tenant_id | BIGINT | Tenant ID |
| data_scope | SMALLINT | Data scope: 1-All 2-This tenant 3-This department 4-Only self |
| dept_id | BIGINT | Department ID (used when data_scope is 3) |
| status | SMALLINT | Status: 1-Normal 2-Disabled |
| sort_order | INTEGER | Sort order |
| is_system | SMALLINT | Is system role: 0-No 1-Yes |
| created_by | BIGINT | Created by user ID |
| created_time | TIMESTAMPTZ | Creation time |
| updated_by | BIGINT | Updated by user ID |
| updated_time | TIMESTAMPTZ | Update time |
| is_deleted | SMALLINT | Logical deletion: 0-Not deleted 1-Deleted |
| remark | VARCHAR(500) | Remarks |

### 2.2 Permission/Menu Table (usr_permission)

| Field Name | Type | Description |
|--------|------|------|
| id | BIGSERIAL | Primary key |
| parent_id | BIGINT | Parent ID (0 for top-level) |
| permission_code | VARCHAR(100) | Permission identifier (unique) |
| permission_name | VARCHAR(100) | Permission name |
| permission_type | SMALLINT | Type: 1-Directory 2-Menu 3-Button 4-API |
| path | VARCHAR(200) | Route path |
| component | VARCHAR(200) | Component path (for frontend) |
| icon | VARCHAR(100) | Icon |
| sort_order | INTEGER | Sort order |
| visible | SMALLINT | Is visible: 0-Show 1-Hide |
| status | SMALLINT | Status: 1-Normal 2-Disabled |
| tenant_id | BIGINT | Tenant ID (0 for global) |
| created_by | BIGINT | Created by user ID |
| created_time | TIMESTAMPTZ | Creation time |
| updated_by | BIGINT | Updated by user ID |
| updated_time | TIMESTAMPTZ | Update time |
| is_deleted | SMALLINT | Logical deletion: 0-Not deleted 1-Deleted |
| remark | VARCHAR(500) | Remarks |

### 2.3 User-Role Association Table (usr_user_role)

| Field Name | Type | Description |
|--------|------|------|
| id | BIGSERIAL | Primary key |
| user_id | BIGINT | User ID |
| role_id | BIGINT | Role ID |
| tenant_id | BIGINT | Tenant ID |
| created_by | BIGINT | Created by user ID |
| created_time | TIMESTAMPTZ | Creation time |

### 2.4 Role-Permission Association Table (usr_role_permission)

| Field Name | Type | Description |
|--------|------|------|
| id | BIGSERIAL | Primary key |
| role_id | BIGINT | Role ID |
| permission_id | BIGINT | Permission ID |
| tenant_id | BIGINT | Tenant ID |

## 3. Permission Identifier Specification

### 3.1 Permission Identifier Format

Format: `{module}:{resource}:{operation}`

| Operation | Description |
|------|------|
| view | View/List |
| add | Add/Create |
| edit | Edit/Modify |
| delete | Delete |
| export | Export |
| import | Import |
| approve | Approve |
| execute | Execute |

### 3.2 Module Prefix

| Module Prefix | Module Name |
|----------|----------|
| system | System Management |
| order | Order Management |
| position | Position Management |
| account | Account Management |
| wallet | Wallet Management |
| customer | Customer Management |
| ib | IB Management |
| risk | Risk Management |
| quote | Quote Management |
| report | Report Management |
| compliance | Compliance Management |
| ticket | Ticket Management |
| audit | Audit Management |
| notice | Notice Management |

## 4. Role List

| Role Name | Role Identifier | Data Scope | Description |
|--------|----------|----------|------|
| Super Admin | super_admin | All data | Has all permissions |
| Admin | admin | All data | Manages all business within tenant |
| Operations Manager | ops_manager | This tenant data | Operations management |
| Operator | operator | This tenant data | Daily operations |
| Risk Manager | risk_manager | This tenant data | Risk management |
| Risk Operator | risk_operator | This tenant data | Risk monitoring |
| Compliance Manager | compliance_manager | This tenant data | Compliance management |
| Compliance Operator | compliance_operator | This tenant data | Compliance execution |
| Customer Manager | customer_manager | Only self data | Customer development and maintenance |
| IB Manager | ib_manager | Only self data | IB management |
| Trader | trader | Only self data | Customer trading permissions |
| Finance | finance | This tenant data | Finance related |
| Auditor | auditor | All data | Audit inspection |

## 5. Data Permission

| Data Scope | Description | SQL Condition |
|----------|------|----------|
| 1-All data | Can view all data | No additional conditions |
| 2-This tenant data | Only view this tenant's data | tenant_id = {current tenant ID} |
| 3-This department data | Only view this department's data | dept_id = {current department ID} |
| 4-Only self data | Only view data created by self | created_by = {current user ID} |

## 6. Menu and Permission List

### 6.1 System Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | System Management | M-Directory | /admin/system | system | 100 |
| Level 2 | User Management | C-Menu | /admin/system/user | user | 1 |
| Button | View User | F-Button | system:user:view | - | - |
| Button | Add User | F-Button | system:user:add | - | - |
| Button | Edit User | F-Button | system:user:edit | - | - |
| Button | Delete User | F-Button | system:user:delete | - | - |
| Button | Reset Password | F-Button | system:user:resetPwd | - | - |
| Level 2 | Role Management | C-Menu | /admin/system/role | role | 2 |
| Button | View Role | F-Button | system:role:view | - | - |
| Button | Add Role | F-Button | system:role:add | - | - |
| Button | Edit Role | F-Button | system:role:edit | - | - |
| Button | Delete Role | F-Button | system:role:delete | - | - |
| Button | Assign Permission | F-Button | system:role:assign | - | - |
| Level 2 | Menu Management | C-Menu | /admin/system/menu | menu | 3 |
| Level 2 | Dictionary Management | C-Menu | /admin/system/dict | dict | 4 |
| Level 2 | Tenant Management | C-Menu | /admin/system/tenant | tenant | 5 |
| Level 2 | Audit Log | C-Menu | /admin/system/audit | audit | 6 |

### 6.2 Order Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Order Management | M-Directory | /admin/order | order | 10 |
| Level 2 | Order List | C-Menu | /admin/order/list | list | 1 |
| Button | View Order | F-Button | order:view | - | - |
| Button | Cancel Order | F-Button | order:cancel | - | - |
| Button | Export Order | F-Button | order:export | - | - |
| Level 2 | Pending Orders | C-Menu | /admin/order/pending | pending | 2 |

### 6.3 Account Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Account Management | M-Directory | /admin/account | account | 20 |
| Level 2 | Account List | C-Menu | /admin/account/list | list | 1 |
| Button | View Account | F-Button | account:view | - | - |
| Button | Modify Leverage | F-Button | account:editLeverage | - | - |
| Button | Freeze Account | F-Button | account:freeze | - | - |
| Button | Export Account | F-Button | account:export | - | - |
| Level 2 | Deposit Review | C-Menu | /admin/account/deposit | deposit | 2 |
| Button | Approve Deposit | F-Button | account:approveDeposit | - | - |
| Level 2 | Withdrawal Review | C-Menu | /admin/account/withdraw | withdraw | 3 |
| Button | Approve Withdrawal | F-Button | account:approveWithdraw | - | - |
| Level 2 | Internal Transfer | C-Menu | /admin/account/transfer | transfer | 4 |

### 6.4 Customer Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Customer Management | M-Directory | /admin/customer | customer | 30 |
| Level 2 | Customer List | C-Menu | /admin/customer/list | list | 1 |
| Button | View Customer | F-Button | customer:view | - | - |
| Button | Add Customer | F-Button | customer:add | - | - |
| Button | Edit Customer | F-Button | customer:edit | - | - |
| Button | Delete Customer | F-Button | customer:delete | - | - |
| Button | Export Customer | F-Button | customer:export | - | - |
| Level 2 | KYC Review | C-Menu | /admin/customer/kyc | kyc | 2 |
| Button | Approve KYC | F-Button | customer:approveKyc | - | - |
| Level 2 | Customer Follow-up | C-Menu | /admin/customer/follow | follow | 3 |
| Level 2 | Customer Segmentation | C-Menu | /admin/customer/segment | segment | 4 |

### 6.5 IB Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | IB Management | M-Directory | /admin/ib | ib | 40 |
| Level 2 | IB List | C-Menu | /admin/ib/list | list | 1 |
| Button | View IB | F-Button | ib:view | - | - |
| Button | Approve IB | F-Button | ib:approve | - | - |
| Button | Edit IB | F-Button | ib:edit | - | - |
| Button | Export IB | F-Button | ib:export | - | - |
| Level 2 | Commission Management | C-Menu | /admin/ib/commission | commission | 2 |
| Button | View Commission | F-Button | ib:viewCommission | - | - |
| Button | Settle Commission | F-Button | ib:settleCommission | - | - |
| Level 2 | White Label Management | C-Menu | /admin/ib/whitelabel | whitelabel | 3 |

### 6.6 Risk Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Risk Management | M-Directory | /admin/risk | risk | 50 |
| Level 2 | Risk Rules | C-Menu | /admin/risk/rules | rule | 1 |
| Button | View Rule | F-Button | risk:viewRule | - | - |
| Button | Add Rule | F-Button | risk:addRule | - | - |
| Button | Edit Rule | F-Button | risk:editRule | - | - |
| Button | Delete Rule | F-Button | risk:deleteRule | - | - |
| Button | Enable Rule | F-Button | risk:enableRule | - | - |
| Level 2 | Risk Events | C-Menu | /admin/risk/events | event | 2 |
| Button | View Event | F-Button | risk:viewEvent | - | - |
| Button | Handle Event | F-Button | risk:handleEvent | - | - |
| Level 2 | Blacklist | C-Menu | /admin/risk/blacklist | blacklist | 3 |
| Button | Add to Blacklist | F-Button | risk:addBlacklist | - | - |
| Button | Remove from Blacklist | F-Button | risk:removeBlacklist | - | - |
| Level 2 | Liquidation Records | C-Menu | /admin/risk/liquidation | liquidation | 4 |

### 6.7 Quote Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Quote Management | M-Directory | /admin/quote | quote | 60 |
| Level 2 | Symbol Management | C-Menu | /admin/quote/symbols | symbols | 1 |
| Button | View Symbol | F-Button | quote:viewSymbol | - | - |
| Button | Add Symbol | F-Button | quote:addSymbol | - | - |
| Button | Edit Symbol | F-Button | quote:editSymbol | - | - |
| Button | Enable/Disable | F-Button | quote:toggleStatus | - | - |
| Level 2 | Spread Configuration | C-Menu | /admin/quote/spread | spread | 2 |
| Level 2 | Trading Hours | C-Menu | /admin/quote/session | session | 3 |
| Level 2 | LP Management | C-Menu | /admin/quote/lp | lp | 4 |

### 6.8 Report Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Report Management | M-Directory | /admin/report | report | 70 |
| Level 2 | Trade Report | C-Menu | /admin/report/trade | trade | 1 |
| Button | View Report | F-Button | report:viewTrade | - | - |
| Button | Export Report | F-Button | report:exportTrade | - | - |
| Level 2 | Customer Report | C-Menu | /admin/report/customer | customer | 2 |
| Level 2 | Risk Report | C-Menu | /admin/report/risk | risk | 3 |
| Level 2 | Finance Report | C-Menu | /admin/report/finance | finance | 4 |
| Level 2 | Regulatory Report | C-Menu | /admin/report/regulatory | regulatory | 5 |
| Level 2 | Custom Report | C-Menu | /admin/report/custom | custom | 6 |

### 6.9 Compliance Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Compliance Management | M-Directory | /admin/compliance | compliance | 80 |
| Level 2 | Compliance Rules | C-Menu | /admin/compliance/rules | rule | 1 |
| Level 2 | AML Monitoring | C-Menu | /admin/compliance/aml | aml | 2 |
| Level 2 | Sanctions List | C-Menu | /admin/compliance/sanctions | sanctions | 3 |
| Level 2 | Customer Suitability | C-Menu | /admin/compliance/suitability | suitability | 4 |

### 6.10 Ticket Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Ticket Management | M-Directory | /admin/ticket | ticket | 90 |
| Level 2 | Ticket List | C-Menu | /admin/ticket/list | list | 1 |
| Button | View Ticket | F-Button | ticket:view | - | - |
| Button | Reply Ticket | F-Button | ticket:reply | - | - |
| Button | Close Ticket | F-Button | ticket:close | - | - |
| Button | Export Ticket | F-Button | ticket:export | - | - |
| Level 2 | Ticket Categories | C-Menu | /admin/ticket/category | category | 2 |

### 6.11 Notice Management Module

| Menu Level | Menu Name | Type | Route/Permission Identifier | Icon | Order |
|----------|----------|------|---------------|------|------|
| Level 1 | Notice Management | M-Directory | /admin/notice | notice | 95 |
| Level 2 | Announcement Management | C-Menu | /admin/notice/list | list | 1 |
| Button | Publish Announcement | F-Button | notice:publish | - | - |
| Button | Edit Announcement | F-Button | notice:edit | - | - |
| Button | Delete Announcement | F-Button | notice:delete | - | - |
| Level 2 | Message Template | C-Menu | /admin/notice/template | template | 2 |
| Level 2 | Push Records | C-Menu | /admin/notice/push-log | push-log | 3 |

## 7. Special Permissions

### 7.1 Tenant-Level Permissions

| Permission Identifier | Description | Applicable Scope |
|----------|------|----------|
| tenant:create | Create tenant | Super Admin |
| tenant:manage | Manage tenant | Super Admin |
| tenant:view | View tenant | Super Admin |

### 7.2 Financial Special Permissions

| Permission Identifier | Description | Applicable Scope |
|----------|------|----------|
| trade:forceClose | Force close position | Risk Manager |
| trade:manualExecute | Manual execution | Risk Manager |
| account:negativeBalance | Allow negative balance | Compliance Manager |
| risk:override | Risk rule exemption | Risk Manager |
| risk:suspendTrading | Suspend trading | Risk Manager |

## 8. Permission Verification Flow

```
Request → Gateway → Parse Token → Get User ID → Query User Roles → Query Role Permissions → Verify Permission → Allow/Deny
```