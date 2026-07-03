# Global Connect FX Trading Platform - API Specification

## 1. Basic Conventions

| Configuration Item | Standard |
|--------|------|
| Protocol | HTTPS + HTTP/2 |
| Base Path | /api/v1 |
| Data Format | JSON |
| Character Encoding | UTF-8 |
| Time Format | yyyy-MM-dd HH:mm:ss (ISO 8601 compatible) |
| Date Format | yyyy-MM-dd |
| Timestamp Format | Millisecond Unix timestamp (13 digits) |
| Amount Format | String (8 decimal places, e.g., "100.12345678") |
| Precision Handling | All amounts precise to 8 decimal places |

## 2. Request Standards

### 2.1 Request Headers

| Header | Required | Description |
|--------|------|------|
| Content-Type | Yes | application/json; charset=UTF-8 |
| Authorization | Yes | Bearer {access_token} |
| X-Request-Id | No | Request tracking ID (UUID), recommended for distributed tracing |
| X-Tenant-Id | Yes | Tenant ID (except for super admin/system APIs) |
| X-Locale | No | Language setting, e.g., zh-CN/en-US, default zh-CN |
| X-Time-Zone | No | Timezone setting, e.g., Asia/Shanghai, default UTC |
| User-Agent | No | Client identifier |
| Accept-Encoding | No | gzip, deflate |

### 2.2 Request Methods

| Method | Usage | Example |
|------|------|------|
| GET | Query resources | GET /api/v1/accounts/{id} |
| POST | Create resources | POST /api/v1/orders |
| PUT | Full update | PUT /api/v1/accounts/{id} |
| PATCH | Partial update | PATCH /api/v1/accounts/{id}/leverage |
| DELETE | Delete resources | DELETE /api/v1/pending-orders/{id} |

### 2.3 Path Parameters

- Path parameters use {param} format
- Parameter names use camelCase
- Example: /api/v1/accounts/{accountId}/positions

### 2.4 Query Parameters

| Parameter | Type | Default | Description |
|------|------|--------|------|
| pageNum | int | 1 | Page number (starting from 1) |
| pageSize | int | 20 | Records per page (max 100) |
| orderBy | string | - | Sort field (database field name) |
| orderDir | string | desc | Sort direction (asc/desc) |
| keyword | string | - | Fuzzy search keyword |
| startDate | string | - | Start date (yyyy-MM-dd) |
| endDate | string | - | End date (yyyy-MM-dd) |

### 2.5 Request Body

- Request body uses JSON format
- Field names use camelCase
- Enum values use numbers instead of strings
- Amount fields use strings to avoid precision loss

## 3. Unified Response Format

### 3.1 Success Response

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1704067200000,
  "requestId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 3.2 Paginated Response

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [],
    "total": 100,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 5
  },
  "timestamp": 1704067200000,
  "requestId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 3.3 Error Response

```json
{
  "code": 40001,
  "message": "Parameter validation failed: order quantity cannot be empty",
  "data": null,
  "errors": [
    {
      "field": "lotSize",
      "message": "lotSize cannot be empty"
    }
  ],
  "timestamp": 1704067200000,
  "requestId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 3.4 File Download Response

```
HTTP/1.1 200 OK
Content-Type: application/octet-stream
Content-Disposition: attachment; filename="report.xlsx"
X-Request-Id: 550e8400-e29b-41d4-a716-446655440000
```

## 4. Exception Code Definitions

### 4.1 Error Code Structure

Error Code = HTTP Status Code (4 digits) + Business Error Code (2 digits)

### 4.2 Business Error Code Definitions

| Error Code | HTTP Status Code | Description |
|--------|-----------|------|
| 200 | 200 | Success |
| 40001 | 400 | Parameter validation failed |
| 40002 | 400 | Insufficient balance |
| 40003 | 400 | Insufficient margin |
| 40004 | 400 | Exceeds trading limit |
| 40005 | 400 | Price out of range |
| 40006 | 400 | Symbol not tradeable |
| 40007 | 400 | Order type not supported |
| 40101 | 401 | Not logged in / Token expired |
| 40102 | 401 | Invalid token |
| 40103 | 401 | Refresh token invalid |
| 40301 | 403 | No access permission |
| 40302 | 403 | Account frozen |
| 40303 | 403 | KYC verification failed |
| 40304 | 403 | Demo account not allowed for this operation |
| 40401 | 404 | Resource not found |
| 40402 | 404 | Account not found |
| 40403 | 404 | Order not found |
| 40404 | 404 | Position not found |
| 40405 | 404 | Symbol not found |
| 40901 | 409 | Data conflict / duplicate |
| 40902 | 409 | Order already executed |
| 40903 | 409 | Position already locked |
| 40904 | 409 | Duplicate order submission |
| 42201 | 422 | Risk control rule rejection |
| 42202 | 422 | Abnormal trading detection |
| 42203 | 422 | Sanctions list match |
| 50001 | 500 | Internal server error |
| 50002 | 500 | Database error |
| 50003 | 500 | Cache error |
| 50004 | 500 | Message queue error |
| 50301 | 503 | Service temporarily unavailable |
| 50302 | 503 | System under maintenance |

## 5. Authentication Rules

### 5.1 Authentication Method

- **Authentication Method**: JWT (JSON Web Token)
- **Token Type**: Access Token + Refresh Token
- **Token Location**: Header Authorization: Bearer {access_token}

### 5.2 Token Structure

**Access Token Payload:**
```json
{
  "sub": "12345",
  "accountId": 12345,
  "tenantId": 1,
  "role": "TRADER",
  "permissions": ["order:create", "order:view"],
  "exp": 1704070800,
  "iat": 1704067200
}
```

### 5.3 Token Validity Period

| Token Type | Validity Period | Description |
|-----------|--------|------|
| Access Token | 30 minutes | Short-term access token |
| Refresh Token | 7 days | For refreshing Access Token |

### 5.4 Public APIs (No Authentication Required)

| API | Method | Description |
|------|------|------|
| /api/v1/auth/login | POST | User login |
| /api/v1/auth/refresh | POST | Refresh token |
| /api/v1/auth/logout | POST | Logout |
| /api/v1/auth/register | POST | User registration |
| /api/v1/public/symbols | GET | Public market data query |
| /api/v1/public/config | GET | Public configuration query |
| /api/v1/captcha | GET | Captcha image |
| /api/v1/sms/code | POST | Send SMS verification code |
| /ws/quote | WebSocket | Market data WebSocket |

### 5.5 Permission Identifier Rules

| Permission Prefix | Meaning |
|----------|------|
| order:* | Order-related permissions |
| order:create | Create order |
| order:view | View order |
| order:cancel | Cancel order |
| account:* | Account-related permissions |
| account:view | View account |
| account:withdraw | Withdrawal permission |
| risk:* | Risk control-related permissions |
| risk:view | View risk control |
| risk:manage | Risk control configuration |
| admin:* | Administrator permissions |

## 6. WebSocket Standards

### 6.1 Market Data WebSocket

- **Address**: wss://api.globalfx.com/ws/quote
- **Authentication**: Pass token parameter during connection
- **Subscription Format**:
```json
{
  "type": "subscribe",
  "symbols": ["EURUSD", "GBPUSD"]
}
```
- **Push Format**:
```json
{
  "type": "quote",
  "symbol": "EURUSD",
  "bid": "1.08500",
  "ask": "1.08520",
  "timestamp": 1704067200000
}
```

### 6.2 Trading WebSocket

- **Address**: wss://api.globalfx.com/ws/trade
- **Authentication**: Pass token parameter during connection
- **Usage**: Order status push, position change push

## 7. Rate Limiting Rules

| Rate Limit Dimension | Limit | Window |
|----------|------|------|
| IP Rate Limit | 1000 requests/minute | Sliding window |
| User Rate Limit | 100 requests/minute | Sliding window |
| Order Submission | 10 requests/second | Token bucket |
| Market Data Query | 100 requests/second | Token bucket |

## 8. Version Management

### 8.1 Versioning Strategy

- URL path contains version number: /api/v1/
- Deprecated APIs must be marked with @Deprecated
- Maintain at least 2 version cycles (approximately 6 months)
- Major changes release new version

### 8.2 Current Version

| Version | Status | Deprecation Time |
|------|------|----------|
| v1 | Current Stable | - |

## 9. Module Division

| Module Prefix | Module Name | Description |
|----------|----------|------|
| /api/v1/quote | Market Data Module | Currency pairs, real-time quotes, K-line |
| /api/v1/order | Order Module | Trading orders, order management |
| /api/v1/position | Position Module | Position query, close position |
| /api/v1/account | Account Module | Account, funds |
| /api/v1/wallet | Wallet Module | Wallet, deposit, withdrawal |
| /api/v1/customer | CRM Module | Customer management |
| /api/v1/ib | IB Module | Broker management |
| /api/v1/risk | Risk Control Module | Risk control configuration, monitoring |
| /api/v1/settlement | Settlement Module | Daily settlement, commission |
| /api/v1/compliance | Compliance Module | Compliance configuration, reports |
| /api/v1/system | System Module | Users, permissions, configuration |
| /api/v1/ticket | Ticket Module | Ticket management |
| /api/v1/report | Report Module | Report query |
| /api/v1/auth | Authentication Module | Login, registration |
| /public | Public APIs | No authentication required |

## 10. Special Constraints

### 10.1 Trading API Special Requirements

- Trading APIs require X-Request-Id for idempotency verification
- Order submission must return complete order information
- Order status changes must be pushed in real-time via WebSocket
- Order rejection must return specific risk control rule ID

### 10.2 Sensitive Data Handling

- Passwords are never returned
- ID numbers are masked (show first 4 and last 4 characters)
- Bank card numbers are masked (show last 4 digits)
- API keys are only returned once during creation

### 10.3 Audit Requirements

- All write operations are recorded in audit logs
- Logs include: operator, operation time, request parameters, response results
- Audit logs cannot be deleted or modified
