# Global FX Trading Platform - API Detailed Documentation

## 1. Authentication Module (Auth)

### 1.1 User Login

- **API Endpoint**: POST /api/v1/auth/login
- **Description**: User login to obtain access token
- **Permission Required**: Public endpoint
- **Authentication Required**: No

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| username | body | string | Yes | Username/email/phone | "trader001" |
| password | body | string | Yes | Password | "Pass1234" |
| captchaKey | body | string | No | Captcha key | "xxx" |
| captchaCode | body | string | No | Captcha code | "1234" |

**Request Body Example:**
```json
{
  "username": "trader001",
  "password": "Pass1234"
}
```

**Response Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| accessToken | string | Access token |
| refreshToken | string | Refresh token |
| expiresIn | int | Expiration time (seconds) |
| tokenType | string | Token type (Bearer) |
| accountId | long | Account ID |
| accountNo | string | Account number |
| tenantId | long | Tenant ID |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 1800,
    "tokenType": "Bearer",
    "accountId": 12345,
    "accountNo": "GFX0012345",
    "tenantId": 1
  },
  "timestamp": 1704067200000,
  "requestId": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Error Codes:**

| Error Code | Description | Recommended Action |
|------------|-------------|-------------------|
| 40001 | Parameter validation failed | Check username and password format |
| 40101 | Login failed | Incorrect username or password |
| 40102 | Account has been frozen | Contact customer service |
| 42901 | Too many login attempts | Retry after 15 minutes |

---

### 1.2 Refresh Token

- **API Endpoint**: POST /api/v1/auth/refresh
- **Description**: Use refresh token to obtain a new access token
- **Permission Required**: Public endpoint
- **Authentication Required**: No

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| refreshToken | body | string | Yes | Refresh token | "xxx" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 1800,
    "tokenType": "Bearer"
  }
}
```

---

### 1.3 Logout

- **API Endpoint**: POST /api/v1/auth/logout
- **Description**: User logout, invalidate token
- **Permission Required**: Logged-in user
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

## 2. Quote Module (Quote)

### 2.1 Query All Symbols

- **API Endpoint**: GET /api/v1/quote/symbols
- **Description**: Get list of all tradable symbols
- **Permission Required**: Public endpoint
- **Authentication Required**: No

**Response Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| symbol | string | Symbol code |
| symbolName | string | Symbol name |
| symbolType | int | Symbol type |
| precision | int | Price precision |
| minLot | string | Minimum trading volume |
| maxLot | string | Maximum trading volume |
| tradeMode | int | Trading mode |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "symbol": "EURUSD",
      "symbolName": "EUR/USD",
      "symbolType": 1,
      "precision": 5,
      "minLot": "0.01",
      "maxLot": "100.00",
      "tradeMode": 1
    }
  ]
}
```

---

### 2.2 Get Real-time Quotes

- **API Endpoint**: GET /api/v1/quote/prices
- **Description**: Get real-time quotes for multiple symbols
- **Permission Required**: Logged-in user
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| symbols | query | string | No | Symbol codes, comma-separated | "EURUSD,GBPUSD" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "symbol": "EURUSD",
      "bid": "1.08500",
      "ask": "1.08520",
      "spread": "2.0",
      "spreadType": "fixed",
      "timestamp": 1704067200000,
      "trend": "up"
    }
  ]
}
```

---

### 2.3 Get K-line Data

- **API Endpoint**: GET /api/v1/quote/klines
- **Description**: Get K-line (candlestick) data for a specified symbol
- **Permission Required**: Logged-in user
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| symbol | query | string | Yes | Symbol code | "EURUSD" |
| period | query | string | Yes | K-line period: 1m/5m/15m/1h/4h/1d | "1h" |
| startTime | query | long | No | Start timestamp | 1703980800000 |
| endTime | query | long | No | End timestamp | 1704067200000 |
| limit | query | int | No | Number of records to return (max 1000) | 100 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "time": 1704067200000,
      "open": "1.08500",
      "high": "1.08600",
      "low": "1.08450",
      "close": "1.08550",
      "volume": "12500"
    }
  ]
}
```

---

### 2.4 Get Market Depth

- **API Endpoint**: GET /api/v1/quote/depth
- **Description**: Get market depth (order book) for a specified symbol
- **Permission Required**: Logged-in user
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| symbol | query | string | Yes | Symbol code | "EURUSD" |
| limit | query | int | No | Number of levels (default 10) | 10 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "symbol": "EURUSD",
    "timestamp": 1704067200000,
    "bids": [
      {"price": "1.08499", "volume": "100"},
      {"price": "1.08498", "volume": "250"}
    ],
    "asks": [
      {"price": "1.08520", "volume": "150"},
      {"price": "1.08521", "volume": "300"}
    ]
  }
}
```

---

## 3. Order Module (Order)

### 3.1 Create Order

- **API Endpoint**: POST /api/v1/order
- **Description**: Create a new trading order (Market/Limit/Stop order)
- **Permission Required**: order:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| symbol | body | string | Yes | Symbol code | "EURUSD" |
| orderType | body | int | Yes | Order type: 1-Market 2-Limit 3-Stop 4-Take Profit 6-Pending | 1 |
| orderSide | body | int | Yes | Side: 1-Buy 2-Sell | 1 |
| lotSize | body | string | Yes | Trading volume (lots) | "0.10" |
| orderPrice | body | string | No | Order price (for limit/stop orders) | "1.08600" |
| slippagePoints | body | int | No | Slippage (points), default 0 | 10 |
| timeInForce | body | int | No | Validity: 1-GTC 2-GFD 3-GTD, default 1 | 1 |
| expireTime | body | string | No | Expiration time (required for GTD) | "2024-01-01 12:00:00" |
| positionId | body | long | No | Associated position ID (for close orders) | 12345 |
| orderText | body | string | No | Order remark | "EA signal order" |

**Request Body Example:**
```json
{
  "symbol": "EURUSD",
  "orderType": 1,
  "orderSide": 1,
  "lotSize": "0.10",
  "slippagePoints": 10,
  "timeInForce": 1,
  "orderText": "EA signal order"
}
```

**Response Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| orderNo | string | Order number (UUID) |
| symbol | string | Symbol code |
| orderType | int | Order type |
| orderSide | int | Order side |
| lotSize | string | Order quantity |
| filledLot | string | Filled quantity |
| avgPrice | string | Average fill price |
| status | int | Order status |
| createdTime | string | Creation time |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderNo": "550e8400-e29b-41d4-a716-446655440000",
    "symbol": "EURUSD",
    "orderType": 1,
    "orderSide": 1,
    "lotSize": "0.10",
    "filledLot": "0.10",
    "avgPrice": "1.08515",
    "status": 3,
    "createdTime": "2024-01-01 10:00:00"
  },
  "timestamp": 1704067200000,
  "requestId": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Error Codes:**

| Error Code | Description | Recommended Action |
|------------|-------------|-------------------|
| 40003 | Insufficient margin | Increase account balance |
| 40004 | Exceeds trading limit | Check limit configuration |
| 40006 | Symbol not tradable | Check symbol status |
| 42201 | Risk control rule rejected | Review rejection reason |
| 40904 | Duplicate order | Wait for previous order to complete |

---

### 3.2 Cancel Order

- **API Endpoint**: DELETE /api/v1/order/{orderNo}
- **Description**: Cancel a pending order
- **Permission Required**: order:cancel
- **Authentication Required**: Yes

**Path Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| orderNo | string | Yes | Order number |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderNo": "550e8400-e29b-41d4-a716-446655440000",
    "status": 4
  }
}
```

**Error Codes:**

| Error Code | Description | Recommended Action |
|------------|-------------|-------------------|
| 40403 | Order not found | Check order number |
| 40902 | Order already filled | Cannot cancel a filled order |

---

### 3.3 Query Order List

- **API Endpoint**: GET /api/v1/order
- **Description**: Query order list for the current account
- **Permission Required**: order:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| pageNum | query | int | No | Page number | 1 |
| pageSize | query | int | No | Records per page | 20 |
| status | query | int | No | Order status | 1 |
| symbol | query | string | No | Symbol code | "EURUSD" |
| orderType | query | int | No | Order type | 1 |
| startDate | query | string | No | Start date | "2024-01-01" |
| endDate | query | string | No | End date | "2024-01-31" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "orderNo": "550e8400-e29b-41d4-a716-446655440000",
        "symbol": "EURUSD",
        "orderType": 1,
        "orderSide": 1,
        "lotSize": "0.10",
        "filledLot": "0.10",
        "avgPrice": "1.08515",
        "status": 3,
        "createdTime": "2024-01-01 10:00:00"
      }
    ],
    "total": 100,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 5
  }
}
```

---

### 3.4 Query Order Details

- **API Endpoint**: GET /api/v1/order/{orderNo}
- **Description**: Query detailed information of a specified order
- **Permission Required**: order:view
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderNo": "550e8400-e29b-41d4-a716-446655440000",
    "accountId": 12345,
    "symbol": "EURUSD",
    "orderType": 1,
    "orderSide": 1,
    "lotSize": "0.10",
    "filledLot": "0.10",
    "avgPrice": "1.08515",
    "orderPrice": null,
    "slippagePoints": 10,
    "status": 3,
    "positionId": 67890,
    "lpId": 1,
    "executionMode": 1,
    "channel": 1,
    "riskCheckStatus": 1,
    "createdTime": "2024-01-01 10:00:00",
    "updatedTime": "2024-01-01 10:00:01"
  }
}
```

---

### 3.5 Batch Close Positions

- **API Endpoint**: POST /api/v1/order/close-batch
- **Description**: Batch close multiple positions
- **Permission Required**: order:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| positionIds | body | array | Yes | List of position IDs | [123, 456] |
| orderType | body | int | No | Close order type, default market order | 1 |

**Request Body Example:**
```json
{
  "positionIds": [123, 456],
  "orderType": 1
}
```

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "results": [
      {"positionId": 123, "orderNo": "xxx", "success": true},
      {"positionId": 456, "orderNo": "yyy", "success": true, "error": null},
      {"positionId": 789, "success": false, "error": "Insufficient margin"}
    ]
  }
}
```

---

### 3.6 Set Stop Loss / Take Profit

- **API Endpoint**: POST /api/v1/order/sl-tp
- **Description**: Set or modify stop loss and take profit for a position
- **Permission Required**: order:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| positionId | body | long | Yes | Position ID | 12345 |
| stopLoss | body | string | No | Stop loss price | "1.08000" |
| takeProfit | body | string | No | Take profit price | "1.09000" |
| trailingStop | body | string | No | Trailing stop distance (points) | "50" |

**Request Body Example:**
```json
{
  "positionId": 12345,
  "stopLoss": "1.08000",
  "takeProfit": "1.09000",
  "trailingStop": "50"
}
```

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "positionId": 12345,
    "stopLoss": "1.08000",
    "takeProfit": "1.09000",
    "trailingStop": "50"
  }
}
```

---

## 4. Position Module (Position)

### 4.1 Query Position List

- **API Endpoint**: GET /api/v1/position
- **Description**: Query position list for the current account
- **Permission Required**: account:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| pageNum | query | int | No | Page number | 1 |
| pageSize | query | int | No | Records per page | 20 |
| symbol | query | string | No | Symbol code | "EURUSD" |
| status | query | int | No | Position status | 1 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "positionNo": "660e8400-e29b-41d4-a716-446655440000",
        "symbol": "EURUSD",
        "positionSide": 1,
        "openLot": "0.10",
        "currentLot": "0.10",
        "openPrice": "1.08500",
        "unrealizedPnl": "15.50",
        "unrealizedPnlPct": "5.12",
        "swapAmount": "-2.50",
        "usedMargin": "300.00",
        "openTime": "2024-01-01 10:00:00"
      }
    ],
    "total": 5,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 1
  }
}
```

---

### 4.2 Query Position Details

- **API Endpoint**: GET /api/v1/position/{positionId}
- **Description**: Query detailed information of a specified position
- **Permission Required**: account:view
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "positionNo": "660e8400-e29b-41d4-a716-446655440000",
    "accountId": 12345,
    "symbol": "EURUSD",
    "positionSide": 1,
    "openLot": "0.10",
    "currentLot": "0.10",
    "openPrice": "1.08500",
    "closeLot": "0.00",
    "closePrice": null,
    "realizedPnl": "0.00",
    "unrealizedPnl": "15.50",
    "swapAmount": "-2.50",
    "commission": "-2.00",
    "usedMargin": "300.00",
    "openOrderId": 11111,
    "openTime": "2024-01-01 10:00:00",
    "updateTime": "2024-01-01 12:00:00",
    "isLocked": 0,
    "status": 1
  }
}
```

---

### 4.3 Close Position

- **API Endpoint**: POST /api/v1/position/{positionId}/close
- **Description**: Close a specified position
- **Permission Required**: order:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| positionId | path | long | Yes | Position ID | 12345 |
| lotSize | body | string | No | Close volume, default all | "0.05" |
| orderType | body | int | No | Close order type, default 1 (market) | 1 |
| slippagePoints | body | int | No | Slippage points | 10 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "positionId": 12345,
    "orderNo": "770e8400-e29b-41d4-a716-446655440000",
    "closeLot": "0.10",
    "closePrice": "1.08650",
    "realizedPnl": "15.00",
    "status": 2
  }
}
```

---

### 4.4 Reverse Position

- **API Endpoint**: POST /api/v1/position/{positionId}/reverse
- **Description**: Close existing position and open a new position in the opposite direction
- **Permission Required**: order:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| positionId | path | long | Yes | Position ID | 12345 |
| lotSize | body | string | No | Reverse volume, default same as position | "0.10" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "closeOrderNo": "770e8400-e29b-41d4-a716-446655440000",
    "openOrderNo": "880e8400-e29b-41d4-a716-446655440000",
    "realizedPnl": "15.00",
    "newPositionId": 67890,
    "newPositionSide": 2
  }
}
```

---

## 5. Account Module (Account)

### 5.1 Query Account Information

- **API Endpoint**: GET /api/v1/account
- **Description**: Get current account details
- **Permission Required**: account:view
- **Authentication Required**: Yes

**Response Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| accountId | long | Account ID |
| accountNo | string | Account number |
| accountType | int | Account type |
| baseCurrency | string | Base currency |
| balance | string | Account balance |
| equity | string | Account equity |
| margin | string | Used margin |
| freeMargin | string | Free margin |
| marginLevel | string | Margin level (%) |
| unrealizedPnl | string | Unrealized P&L |
| leverage | decimal | Leverage ratio |
| status | int | Account status |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "accountId": 12345,
    "accountNo": "GFX0012345",
    "accountType": 1,
    "baseCurrency": "USD",
    "balance": "10000.00",
    "equity": "10015.50",
    "margin": "300.00",
    "freeMargin": "9715.50",
    "marginLevel": "333.85",
    "unrealizedPnl": "15.50",
    "leverage": 30,
    "status": 1
  }
}
```

---

### 5.2 Modify Leverage

- **API Endpoint**: PATCH /api/v1/account/leverage
- **Description**: Modify account leverage
- **Permission Required**: account:manage
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| leverage | body | decimal | Yes | Leverage ratio | 100 |

**Request Body Example:**
```json
{
  "leverage": 100
}
```

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "oldLeverage": 30,
    "newLeverage": 100
  }
}
```

**Error Codes:**

| Error Code | Description | Recommended Action |
|------------|-------------|-------------------|
| 40001 | Leverage out of allowed range | Leverage must be between 1-500 |
| 40302 | Account has been frozen | Cannot modify |
| 42201 | Reducing leverage with open positions may trigger liquidation | Confirm to proceed |

---

## 6. Wallet Module (Wallet)

### 6.1 Query Wallet List

- **API Endpoint**: GET /api/v1/wallet
- **Description**: Get all wallets for the current account
- **Permission Required**: account:view
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "walletId": 123,
      "currency": "USD",
      "balance": "10000.00",
      "frozenBalance": "0.00"
    },
    {
      "walletId": 124,
      "currency": "EUR",
      "balance": "500.00",
      "frozenBalance": "0.00"
    }
  ]
}
```

---

### 6.2 Request Deposit

- **API Endpoint**: POST /api/v1/wallet/deposit
- **Description**: Request a deposit
- **Permission Required**: account:deposit
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| currency | body | string | Yes | Currency | "USD" |
| amount | body | string | Yes | Deposit amount | "1000.00" |
| paymentMethod | body | int | Yes | Payment method: 1-Bank card 2-E-wallet 3-Cryptocurrency | 1 |
| bankId | body | long | No | Bank ID | 1 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "depositNo": "DEP20240101100000001",
    "amount": "1000.00",
    "currency": "USD",
    "paymentMethod": 1,
    "status": 1,
    "paymentUrl": "https://payment.example.com/xxx",
    "expireTime": "2024-01-01 12:00:00"
  }
}
```

---

### 6.3 Request Withdrawal

- **API Endpoint**: POST /api/v1/wallet/withdraw
- **Description**: Request a withdrawal
- **Permission Required**: account:withdraw
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| currency | body | string | Yes | Currency | "USD" |
| amount | body | string | Yes | Withdrawal amount | "500.00" |
| withdrawalMethod | body | int | Yes | Withdrawal method: 1-Bank card 2-E-wallet | 1 |
| bankCardId | body | long | No | Bank card ID | 1 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "withdrawalNo": "WDR20240101100000001",
    "amount": "500.00",
    "currency": "USD",
    "status": 1,
    "estimatedTime": "2024-01-02 12:00:00",
    "fee": "5.00"
  }
}
```

**Error Codes:**

| Error Code | Description | Recommended Action |
|------------|-------------|-------------------|
| 40002 | Insufficient balance | Check account balance |
| 40303 | KYC not passed | Complete KYC verification |

---

### 6.4 Query Transaction History

- **API Endpoint**: GET /api/v1/wallet/transactions
- **Description**: Query wallet transaction history
- **Permission Required**: account:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| currency | query | string | No | Currency | "USD" |
| txnType | query | int | No | Transaction type | 1 |
| startDate | query | string | No | Start date | "2024-01-01" |
| endDate | query | string | No | End date | "2024-01-31" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "txnNo": "TXN20240101100000001",
        "currency": "USD",
        "txnType": 1,
        "direction": 1,
        "amount": "1000.00",
        "balanceBefore": "9000.00",
        "balanceAfter": "10000.00",
        "relatedOrderNo": null,
        "status": 1,
        "createdTime": "2024-01-01 10:00:00"
      }
    ],
    "total": 50,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 3
  }
}
```

---

## 7. Customer Module (CRM)

### 7.1 Create Customer

- **API Endpoint**: POST /api/v1/customer
- **Description**: Create a new customer
- **Permission Required**: crm:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| customerType | body | int | Yes | Customer type: 1-Individual 2-Corporate | 1 |
| fullName | body | string | Yes | Name/Company name | "John Doe" |
| email | body | string | Yes | Email | "test@example.com" |
| phone | body | string | No | Phone | "+8613800138000" |
| country | body | string | Yes | Country code | "CN" |
| riskLevel | body | int | No | Risk level, default 1 | 1 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "customerId": 12345,
    "customerNo": "CUS20240101001"
  }
}
```

---

### 7.2 Query Customer List

- **API Endpoint**: GET /api/v1/customer
- **Description**: Query customer list
- **Permission Required**: crm:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| pageNum | query | int | No | Page number | 1 |
| pageSize | query | int | No | Records per page | 20 |
| status | query | int | No | Customer status | 2 |
| riskLevel | query | int | No | Risk level | 1 |
| keyword | query | string | No | Search keyword | "John" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "customerId": 12345,
        "customerNo": "CUS20240101001",
        "fullName": "John Doe",
        "email": "test@example.com",
        "status": 2,
        "riskLevel": 1,
        "totalDeposit": "10000.00",
        "totalTrades": 150,
        "createdTime": "2024-01-01"
      }
    ],
    "total": 100,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 5
  }
}
```

---

### 7.3 Add Follow-up Record

- **API Endpoint**: POST /api/v1/customer/{customerId}/follow
- **Description**: Add a follow-up record for a customer
- **Permission Required**: crm:manage
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| followType | body | int | Yes | Follow-up method: 1-Phone 2-Email 3-Meeting 4-Other | 1 |
| followContent | body | string | Yes | Follow-up content | "Discussed trading strategy..." |
| nextFollowTime | body | string | No | Next follow-up time | "2024-01-08" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "followId": 123
  }
}
```

---

## 8. IB Module (IB)

### 8.1 Register as IB

- **API Endpoint**: POST /api/v1/ib/register
- **Description**: Apply to become an IB (Introducing Broker)
- **Permission Required**: ib:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| partnerLevel | body | int | Yes | Partner level: 1-IB 2-White Label | 1 |
| commissionType | body | int | Yes | Commission type: 1-Fixed 2-Ratio 3-Tiered | 2 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "partnerId": 123,
    "partnerNo": "IB20240101001",
    "status": 1
  }
}
```

---

### 8.2 Query IB Commission

- **API Endpoint**: GET /api/v1/ib/commission
- **Description**: Query IB commission report
- **Permission Required**: ib:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| startDate | query | string | No | Start date | "2024-01-01" |
| endDate | query | string | No | End date | "2024-01-31" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalCommission": "1500.00",
    "pendingCommission": "300.00",
    "paidCommission": "1200.00",
    "records": [
      {
        "commissionNo": "CMM20240101001",
        "customerId": 12345,
        "customerName": "John Doe",
        "symbol": "EURUSD",
        "lotSize": "10.00",
        "commission": "15.00",
        "settleStatus": 1,
        "createdTime": "2024-01-01"
      }
    ]
  }
}
```

---

### 8.3 Query Sub-customers

- **API Endpoint**: GET /api/v1/ib/customers
- **Description**: Query sub-customers referred by the IB
- **Permission Required**: ib:view
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalCustomers": 50,
    "activeCustomers": 30,
    "records": [
      {
        "customerId": 12345,
        "customerName": "John Doe",
        "registeredTime": "2024-01-01",
        "totalDeposit": "5000.00",
        "totalTrades": 100,
        "commission": "150.00"
      }
    ]
  }
}
```

---

## 9. Risk Control Module (Risk)

### 9.1 Query Risk Control Rules

- **API Endpoint**: GET /api/v1/risk/rules
- **Description**: Query current risk control rules
- **Permission Required**: risk:view
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "ruleId": 1,
      "ruleCode": "MAX_POSITION",
      "ruleName": "Max Position Per Symbol",
      "ruleType": 1,
      "minValue": "0",
      "maxValue": "50",
      "action": 1,
      "status": 1
    }
  ]
}
```

---

### 9.2 Configure Risk Control Rules

- **API Endpoint**: POST /api/v1/risk/rules
- **Description**: Create a new risk control rule
- **Permission Required**: risk:manage
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| ruleCode | body | string | Yes | Rule code | "MAX_POSITION" |
| ruleName | body | string | Yes | Rule name | "Max Position Per Symbol" |
| ruleType | body | int | Yes | Rule type | 1 |
| symbolId | body | long | No | Applicable symbol | 1 |
| minValue | body | string | No | Minimum value | "0" |
| maxValue | body | string | Yes | Maximum value | "50" |
| action | body | int | Yes | Trigger action | 1 |
| priority | body | int | No | Priority, default 100 | 100 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "ruleId": 10
  }
}
```

---

### 9.3 Query Risk Control Events

- **API Endpoint**: GET /api/v1/risk/events
- **Description**: Query risk control event records
- **Permission Required**: risk:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| pageNum | query | int | No | Page number | 1 |
| pageSize | query | int | No | Records per page | 20 |
| eventType | query | int | No | Event type | 1 |
| status | query | int | No | Processing status | 1 |
| startDate | query | string | No | Start date | "2024-01-01" |
| endDate | query | string | No | End date | "2024-01-31" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "eventNo": "EVT20240101100000001",
        "ruleCode": "SINGLE_ORDER_LIMIT",
        "eventType": 1,
        "accountId": 12345,
        "symbol": "EURUSD",
        "orderNo": "xxx",
        "actionTaken": 1,
        "status": 3,
        "createdTime": "2024-01-01 10:00:00"
      }
    ],
    "total": 50,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 3
  }
}
```

---

## 10. System Module (System)

### 10.1 Query Current User Information

- **API Endpoint**: GET /api/v1/system/user/current
- **Description**: Get current logged-in user information
- **Permission Required**: Logged-in user
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 1,
    "userNo": "ADMIN001",
    "username": "admin",
    "nickname": "System Administrator",
    "email": "admin@globalfx.com",
    "tenantId": 1,
    "roles": ["SUPER_ADMIN"],
    "permissions": ["*"]
  }
}
```

---

### 10.2 Query Role List

- **API Endpoint**: GET /api/v1/system/roles
- **Description**: Query system role list
- **Permission Required**: system:view
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "roleId": 1,
      "roleCode": "SUPER_ADMIN",
      "roleName": "Super Administrator",
      "dataScope": 1,
      "status": 1
    }
  ]
}
```

---

### 10.3 Create System User

- **API Endpoint**: POST /api/v1/system/users
- **Description**: Create a new system user
- **Permission Required**: system:manage
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| username | body | string | Yes | Username | "operator01" |
| password | body | string | Yes | Password | "Pass1234" |
| nickname | body | string | Yes | Nickname | "Operator Wang" |
| email | body | string | No | Email | "op@globalfx.com" |
| phone | body | string | No | Phone | "13800138000" |
| roleIds | body | array | Yes | List of role IDs | [2, 3] |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 10,
    "userNo": "OP001"
  }
}
```

---

### 10.4 Query Dictionary Data

- **API Endpoint**: GET /api/v1/system/dicts/{dictType}
- **Description**: Get data for a specified dictionary type
- **Permission Required**: Logged-in user
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "dictValue": "1",
      "dictLabel": "Active",
      "sortOrder": 1
    },
    {
      "dictValue": "2",
      "dictLabel": "Disabled",
      "sortOrder": 2
    }
  ]
}
```

---

### 10.5 Query Audit Logs

- **API Endpoint**: GET /api/v1/system/audit-logs
- **Description**: Query audit logs
- **Permission Required**: audit:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| module | query | string | No | Module | "order" |
| action | query | string | No | Action | "create" |
| operatorId | query | long | No | Operator ID | 1 |
| startDate | query | string | No | Start date | "2024-01-01" |
| endDate | query | string | No | End date | "2024-01-31" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "logNo": "LOG20240101100000001",
        "module": "order",
        "action": "create",
        "operatorId": 12345,
        "operatorName": "John Doe",
        "ipAddress": "192.168.1.100",
        "requestParams": "{\"symbol\":\"EURUSD\"}",
        "status": 1,
        "createdTime": "2024-01-01 10:00:00"
      }
    ],
    "total": 1000,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 50
  }
}
```

---

## 11. Ticket Module (Ticket)

### 11.1 Create Ticket

- **API Endpoint**: POST /api/v1/ticket
- **Description**: Customer submits a ticket
- **Permission Required**: ticket:create
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| category | body | int | Yes | Ticket category | 5 |
| priority | body | int | Yes | Priority | 2 |
| title | body | string | Yes | Ticket title | "Order execution issue" |
| content | body | string | Yes | Ticket content | "The order with number xxx..." |
| attachments | body | array | No | List of attachment URLs | ["url1", "url2"] |
| relatedOrderNo | body | string | No | Related order number | "xxx" |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "ticketId": 123,
    "ticketNo": "TIC20240101001"
  }
}
```

---

### 11.2 Query Ticket List

- **API Endpoint**: GET /api/v1/ticket
- **Description**: Query ticket list
- **Permission Required**: ticket:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| pageNum | query | int | No | Page number | 1 |
| pageSize | query | int | No | Records per page | 20 |
| status | query | int | No | Ticket status | 2 |
| category | query | int | No | Ticket category | 5 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "ticketId": 123,
        "ticketNo": "TIC20240101001",
        "category": 5,
        "title": "Order execution issue",
        "priority": 2,
        "status": 2,
        "customerName": "John Doe",
        "createdTime": "2024-01-01 10:00:00"
      }
    ],
    "total": 50,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 3
  }
}
```

---

### 11.3 Reply to Ticket

- **API Endpoint**: POST /api/v1/ticket/{ticketId}/reply
- **Description**: Reply to a ticket
- **Permission Required**: ticket:reply
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| content | body | string | Yes | Reply content | "Issue resolved, please check..." |
| isInternal | body | int | No | Is internal reply (1-Yes, 0-No) | 0 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "replyId": 456
  }
}
```

---

### 11.4 Close Ticket

- **API Endpoint**: POST /api/v1/ticket/{ticketId}/close
- **Description**: Close a ticket
- **Permission Required**: ticket:manage
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| satisfaction | body | int | No | Customer satisfaction | 1 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "ticketId": 123,
    "status": 5,
    "closedTime": "2024-01-01 12:00:00"
  }
}
```

---

## 12. Report Module (Report)

### 12.1 Trading Report

- **API Endpoint**: GET /api/v1/report/trades
- **Description**: Get trading statistics report
- **Permission Required**: report:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| startDate | query | string | Yes | Start date | "2024-01-01" |
| endDate | query | string | Yes | End date | "2024-01-31" |
| accountId | query | long | No | Account ID | 12345 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalTrades": 150,
    "totalLots": "1500.00",
    "totalPnl": "2500.00",
    "totalCommission": "150.00",
    "totalSwap": "-50.00",
    "winRate": "55.5",
    "records": [
      {
        "date": "2024-01-01",
        "trades": 10,
        "lots": "100.00",
        "pnl": "150.00"
      }
    ]
  }
}
```

---

### 12.2 Export Report

- **API Endpoint**: GET /api/v1/report/export
- **Description**: Export report as Excel
- **Permission Required**: report:export
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| reportType | query | string | Yes | Report type: trade/customer/risk | "trade" |
| startDate | query | string | Yes | Start date | "2024-01-01" |
| endDate | query | string | Yes | End date | "2024-01-31" |
| format | query | string | No | Format, default xlsx | "xlsx" |

**Response Description:** Returns a file stream, Content-Type is application/octet-stream

---

## 13. Compliance Module (Compliance)

### 13.1 Query Compliance Rules

- **API Endpoint**: GET /api/v1/compliance/rules
- **Description**: Query regional compliance rules
- **Permission Required**: compliance:view
- **Authentication Required**: Yes

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "ruleId": 1,
      "regionCode": "ESMA",
      "regionName": "EU ESMA",
      "maxLeverage": 30,
      "marginCallLevel": 100,
      "stopOutLevel": 50,
      "negativeBalanceProtection": 1,
      "status": 1
    }
  ]
}
```

---

### 13.2 Query AML Events

- **API Endpoint**: GET /api/v1/compliance/aml-events
- **Description**: Query AML (Anti-Money Laundering) suspicious transaction events
- **Permission Required**: compliance:view
- **Authentication Required**: Yes

**Request Parameters:**

| Parameter | Location | Type | Required | Description | Example |
|-----------|----------|------|----------|-------------|---------|
| pageNum | query | int | No | Page number | 1 |
| pageSize | query | int | No | Records per page | 20 |
| status | query | int | No | Processing status | 1 |

**Response Example:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "eventId": 123,
        "eventNo": "AML20240101001",
        "customerId": 12345,
        "eventType": 1,
        "amount": "50000.00",
        "description": "Large transaction",
        "status": 1,
        "createdTime": "2024-01-01 10:00:00"
      }
    ],
    "total": 20,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 1
  }
}
```

---

## 14. API Summary

| No. | Module | API Name | Method | Endpoint | Permission |
|-----|--------|----------|--------|----------|------------|
| 1 | Auth | User Login | POST | /api/v1/auth/login | Public |
| 2 | Auth | Refresh Token | POST | /api/v1/auth/refresh | Public |
| 3 | Auth | Logout | POST | /api/v1/auth/logout | Login |
| 4 | Quote | Query All Symbols | GET | /api/v1/quote/symbols | Public |
| 5 | Quote | Get Real-time Quotes | GET | /api/v1/quote/prices | Login |
| 6 | Quote | Get K-line Data | GET | /api/v1/quote/klines | Login |
| 7 | Quote | Get Market Depth | GET | /api/v1/quote/depth | Login |
| 8 | Order | Create Order | POST | /api/v1/order | order:create |
| 9 | Order | Cancel Order | DELETE | /api/v1/order/{orderNo} | order:cancel |
| 10 | Order | Query Order List | GET | /api/v1/order | order:view |
| 11 | Order | Query Order Details | GET | /api/v1/order/{orderNo} | order:view |
| 12 | Order | Batch Close Positions | POST | /api/v1/order/close-batch | order:create |
| 13 | Order | Set SL/TP | POST | /api/v1/order/sl-tp | order:create |
| 14 | Position | Query Position List | GET | /api/v1/position | account:view |
| 15 | Position | Query Position Details | GET | /api/v1/position/{positionId} | account:view |
| 16 | Position | Close Position | POST | /api/v1/position/{positionId}/close | order:create |
| 17 | Position | Reverse Position | POST | /api/v1/position/{positionId}/reverse | order:create |
| 18 | Account | Query Account Info | GET | /api/v1/account | account:view |
| 19 | Account | Modify Leverage | PATCH | /api/v1/account/leverage | account:manage |
| 20 | Wallet | Query Wallet List | GET | /api/v1/wallet | account:view |
| 21 | Wallet | Request Deposit | POST | /api/v1/wallet/deposit | account:deposit |
| 22 | Wallet | Request Withdrawal | POST | /api/v1/wallet/withdraw | account:withdraw |
| 23 | Wallet | Query Transactions | GET | /api/v1/wallet/transactions | account:view |
| 24 | Customer | Create Customer | POST | /api/v1/customer | crm:create |
| 25 | Customer | Query Customer List | GET | /api/v1/customer | crm:view |
| 26 | Customer | Add Follow-up | POST | /api/v1/customer/{customerId}/follow | crm:manage |
| 27 | IB | Register as IB | POST | /api/v1/ib/register | ib:create |
| 28 | IB | Query IB Commission | GET | /api/v1/ib/commission | ib:view |
| 29 | IB | Query Sub-customers | GET | /api/v1/ib/customers | ib:view |
| 30 | Risk | Query Risk Rules | GET | /api/v1/risk/rules | risk:view |
| 31 | Risk | Configure Risk Rules | POST | /api/v1/risk/rules | risk:manage |
| 32 | Risk | Query Risk Events | GET | /api/v1/risk/events | risk:view |
| 33 | System | Query Current User | GET | /api/v1/system/user/current | Login |
| 34 | System | Query Role List | GET | /api/v1/system/roles | system:view |
| 35 | System | Create System User | POST | /api/v1/system/users | system:manage |
| 36 | System | Query Dictionary Data | GET | /api/v1/system/dicts/{dictType} | Login |
| 37 | System | Query Audit Logs | GET | /api/v1/system/audit-logs | audit:view |
| 38 | Ticket | Create Ticket | POST | /api/v1/ticket | ticket:create |
| 39 | Ticket | Query Ticket List | GET | /api/v1/ticket | ticket:view |
| 40 | Ticket | Reply to Ticket | POST | /api/v1/ticket/{ticketId}/reply | ticket:reply |
| 41 | Ticket | Close Ticket | POST | /api/v1/ticket/{ticketId}/close | ticket:manage |
| 42 | Report | Trading Report | GET | /api/v1/report/trades | report:view |
| 43 | Report | Export Report | GET | /api/v1/report/export | report:export |
| 44 | Compliance | Query Compliance Rules | GET | /api/v1/compliance/rules | compliance:view |
| 45 | Compliance | Query AML Events | GET | /api/v1/compliance/aml-events | compliance:view |
