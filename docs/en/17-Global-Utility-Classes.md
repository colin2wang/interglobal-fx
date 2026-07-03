# Global FX Trading Platform - Global Utility Classes

## 1. Utility Class List

| Utility Class | Path | Purpose |
|--------|------|------|
| SecurityUtils | common/util/SecurityUtils.java | Security utility |
| DateUtil | common/util/DateUtil.java | Date utility |
| MoneyUtil | common/util/MoneyUtil.java | Money utility |
| IdGenerator | common/util/IdGenerator.java | ID generator |
| ValidationUtil | common/util/ValidationUtil.java | Validation utility |
| StringUtil | common/util/StringUtil.java | String utility |
| JsonUtil | common/util/JsonUtil.java | JSON utility |
| Result | common/result/Result.java | Unified response wrapper |
| ResultCode | common/result/ResultCode.java | Response code definition |
| PageResult | common/result/PageResult.java | Paginated response |
| BusinessException | common/exception/BusinessException.java | Business exception |
| GlobalExceptionHandler | common/exception/GlobalExceptionHandler.java | Global exception handler |

---

## 2. Detailed Utility Class Descriptions

### 2.1 SecurityUtils - Security Utility

**Purpose**: User authentication, password encryption, Token parsing, and other security-related operations
**Path**: common/util/SecurityUtils.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| getCurrentUserId | () | Long | Get current logged-in user ID |
| getCurrentUsername | () | String | Get current logged-in username |
| getCurrentTenantId | () | Long | Get current tenant ID |
| getCurrentAccountId | () | Long | Get current trading account ID |
| encryptPassword | String password | String | BCrypt password encryption |
| matchesPassword | String rawPassword, String encodedPassword | boolean | Password verification |
| generateToken | Long userId, String username | String | Generate JWT Token |
| parseToken | String token | Claims | Parse Token |
| getUserIdFromToken | String token | Long | Get user ID from Token |
| isTokenExpired | String token | boolean | Check if Token is expired |
| getPermissions | () | Set<String> | Get current user permission set |
| hasPermission | String permission | boolean | Check if user has a specific permission |
| hasAnyPermission | String... permissions | boolean | Check if user has any of the permissions |

**Usage Example:**

```java
// Get current user ID
Long userId = SecurityUtils.getCurrentUserId();

// Password encryption
String encodedPassword = SecurityUtils.encryptPassword("Password123");

// Password verification
boolean matches = SecurityUtils.matchesPassword("Password123", encodedPassword);

// Permission check
if (SecurityUtils.hasPermission("order:create")) {
    // Has order creation permission
}

// Generate Token
String token = SecurityUtils.generateToken(userId, username);
```

---

### 2.2 DateUtil - Date Utility

**Purpose**: Date/time processing, formatting, and calculation
**Path**: common/util/DateUtil.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| now | () | LocalDateTime | Current time |
| nowDate | () | LocalDate | Current date |
| nowTimestamp | () | Long | Current timestamp (milliseconds) |
| nowTimestampSeconds | () | Long | Current timestamp (seconds) |
| format | LocalDateTime date, String pattern | String | Date formatting |
| format | LocalDate date, String pattern | String | Date formatting |
| parse | String dateStr, String pattern | LocalDateTime | String to date conversion |
| parseDate | String dateStr, String pattern | LocalDate | String to Date conversion |
| formatDateTime | LocalDateTime date | String | Format to yyyy-MM-dd HH:mm:ss |
| formatDate | LocalDate date | String | Format to yyyy-MM-dd |
| formatTime | LocalDateTime date | String | Format to HH:mm:ss |
| formatTimestamp | Long timestamp | String | Timestamp to string conversion |
| parseTimestamp | String timestamp | Long | String to timestamp conversion |
| addDays | LocalDateTime date, long days | LocalDateTime | Add/subtract days |
| addHours | LocalDateTime date, long hours | LocalDateTime | Add/subtract hours |
| addMinutes | LocalDateTime date, long minutes | LocalDateTime | Add/subtract minutes |
| addSeconds | LocalDateTime date, long seconds | LocalDateTime | Add/subtract seconds |
| betweenDays | LocalDateTime start, LocalDateTime end | long | Calculate day difference |
| betweenHours | LocalDateTime start, LocalDateTime end | long | Calculate hour difference |
| betweenMinutes | LocalDateTime start, LocalDateTime end | long | Calculate minute difference |
| isBetween | LocalDateTime date, LocalDateTime start, LocalDateTime end | boolean | Check if date is within range |
| isToday | LocalDateTime date | boolean | Check if date is today |
| startOfDay | LocalDateTime date | LocalDateTime | Get start of day |
| endOfDay | LocalDateTime date | LocalDateTime | Get end of day |
| toUtc | LocalDateTime date | LocalDateTime | Convert to UTC time |
| toShanghai | LocalDateTime date | LocalDateTime | Convert to Shanghai time |
| toTimestamp | LocalDateTime date | Long | LocalDateTime to timestamp |
| of | Long timestamp | LocalDateTime | Timestamp to LocalDateTime |

**Constant Definitions:**

```java
String DATE_PATTERN = "yyyy-MM-dd";
String TIME_PATTERN = "HH:mm:ss";
String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
String DATETIME_PATTERN_MS = "yyyy-MM-dd HH:mm:ss.SSS";
String MONTH_PATTERN = "yyyy-MM";
String YEAR_PATTERN = "yyyy";
```

**Usage Example:**

```java
// Get current time
LocalDateTime now = DateUtil.now();

// Formatting
String dateStr = DateUtil.formatDateTime(now);
String dateOnly = DateUtil.formatDate(now.toLocalDate());

// Parsing
LocalDateTime date = DateUtil.parse("2024-01-01 10:00:00", DateUtil.DATETIME_PATTERN);

// Date calculation
LocalDateTime tomorrow = DateUtil.addDays(now, 1);
LocalDateTime nextWeek = DateUtil.addDays(now, 7);

// Comparison
boolean isToday = DateUtil.isToday(date);
boolean isBetween = DateUtil.isBetween(now, start, end);

// UTC conversion
LocalDateTime utcTime = DateUtil.toUtc(shanghaiTime);
```

---

### 2.3 MoneyUtil - Money Utility

**Purpose**: Precise calculation and formatting of foreign exchange amounts
**Path**: common/util/MoneyUtil.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| add | BigDecimal a, BigDecimal b | BigDecimal | Amount addition |
| subtract | BigDecimal a, BigDecimal b | BigDecimal | Amount subtraction |
| multiply | BigDecimal amount, BigDecimal price | BigDecimal | Amount multiplication |
| divide | BigDecimal a, BigDecimal b, int scale | BigDecimal | Amount division |
| compare | BigDecimal a, BigDecimal b | int | Compare values |
| isPositive | BigDecimal amount | boolean | Check if positive |
| isNegative | BigDecimal amount | boolean | Check if negative |
| isZero | BigDecimal amount | boolean | Check if zero |
| abs | BigDecimal amount | BigDecimal | Absolute value |
| negate | BigDecimal amount | BigDecimal | Negate value |
| max | BigDecimal a, BigDecimal b | BigDecimal | Get maximum value |
| min | BigDecimal a, BigDecimal b | BigDecimal | Get minimum value |
| format | BigDecimal amount, int scale | String | Format amount |
| formatDefault | BigDecimal amount | String | Default format (2 decimal places) |
| parse | String amount | BigDecimal | String to amount conversion |
| round | BigDecimal amount, int scale | BigDecimal | Round to nearest |
| floor | BigDecimal amount, int scale | BigDecimal | Round down |
| ceil | BigDecimal amount, int scale | BigDecimal | Round up |
| calculatePnL | BigDecimal openPrice, BigDecimal closePrice, BigDecimal lotSize, BigDecimal contractSize | BigDecimal | Calculate profit/loss |
| calculateMargin | BigDecimal price, BigDecimal lotSize, BigDecimal contractSize, BigDecimal leverage | BigDecimal | Calculate margin |
| calculateSwap | BigDecimal lotSize, BigDecimal swapRate, int days | BigDecimal | Calculate overnight interest |
| calculateSpread | BigDecimal ask, BigDecimal bid | BigDecimal | Calculate spread |
| pointsToPrice | int points, int precision | BigDecimal | Points to price conversion |
| priceToPoints | BigDecimal price, BigDecimal referencePrice, int precision | int | Price to points conversion |

**Usage Example:**

```java
// Basic operations
BigDecimal balance = MoneyUtil.add(amount1, amount2);
BigDecimal profit = MoneyUtil.subtract(closePrice, openPrice);

// Comparison
if (MoneyUtil.compare(margin, required) < 0) {
    throw new BusinessException("Insufficient margin");
}

// Formatting
String displayAmount = MoneyUtil.formatDefault(profit); // "1,234.56"
String preciseAmount = MoneyUtil.format(amount, 8); // "1234.56789000"

// Trading calculations
BigDecimal pnl = MoneyUtil.calculatePnL(openPrice, closePrice, lotSize, contractSize);
BigDecimal margin = MoneyUtil.calculateMargin(price, lotSize, contractSize, leverage);
BigDecimal swap = MoneyUtil.calculateSwap(lotSize, swapRate, 1); // 1-day interest
```

---

### 2.4 IdGenerator - ID Generator

**Purpose**: Distributed ID generation
**Path**: common/util/IdGenerator.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| nextId | () | Long | Generate Snowflake ID |
| nextUuid | () | String | Generate UUID |
| nextOrderNo | () | String | Generate order number (format: OR+timestamp+random) |
| nextPositionNo | () | String | Generate position number |
| nextDepositNo | () | String | Generate deposit number |
| nextWithdrawalNo | () | String | Generate withdrawal number |
| nextTicketNo | () | String | Generate ticket number |
| nextCustomerNo | () | String | Generate customer number |
| nextAccountNo | () | String | Generate account number |

**ID Format Description:**

| ID Type | Format | Example |
|--------|------|------|
| Snowflake ID | Long | 4874653423456789123 |
| UUID | UUID | 550e8400-e29b-41d4-a716-446655440000 |
| Order Number | OR+yyyyMMddHHmmss+6-digit random | OR20240101100000123456 |
| Position Number | PS+yyyyMMddHHmmss+6-digit random | PS20240101100000123456 |
| Deposit Number | DP+yyyyMMddHHmmss+6-digit random | DP20240101100000123456 |
| Withdrawal Number | WD+yyyyMMddHHmmss+6-digit random | WD20240101100000123456 |
| Ticket Number | TK+yyyyMMdd+6-digit sequence | TK20240101000001 |
| Customer Number | CU+yyyyMMdd+6-digit sequence | CU20240101000001 |
| Account Number | GF+6-digit sequence | GF000001 |

**Usage Example:**

```java
// Generate Snowflake ID
Long id = IdGenerator.nextId();

// Generate order number
String orderNo = IdGenerator.nextOrderNo(); // "OR20240101100000123456"

// Generate UUID
String uuid = IdGenerator.nextUuid();
```

---

### 2.5 ValidationUtil - Validation Utility

**Purpose**: Parameter validation
**Path**: common/util/ValidationUtil.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| notNull | Object obj, String message | void | Null check |
| notEmpty | String str, String message | void | Non-empty string check |
| notBlank | String str, String message | void | Non-blank string check |
| isTrue | boolean expression, String message | void | Condition must be true |
| isFalse | boolean expression, String message | void | Condition must be false |
| isNull | Object obj, String message | void | Must be null check |
| isEmail | String email | boolean | Email format validation |
| isPhone | String phone | boolean | Phone number format validation |
| isIdCard | String idCard | boolean | ID card format validation |
| isUrl | String url | boolean | URL format validation |
| isIp | String ip | boolean | IP address format validation |
| isPositive | BigDecimal num, String message | void | Positive number check |
| isNonNegative | BigDecimal num, String message | void | Non-negative number check |
| isBetween | BigDecimal num, BigDecimal min, BigDecimal max, String message | void | Range validation |
| minLength | String str, int length, String message | void | Minimum length check |
| maxLength | String str, int length, String message | void | Maximum length check |
| matches | String str, String regex, String message | void | Regex match validation |

**Usage Example:**

```java
// Basic validation
ValidationUtil.notNull(account, "Account cannot be empty");
ValidationUtil.notBlank(symbol, "Symbol code cannot be empty");
ValidationUtil.isTrue(balance.compareTo(amount) >= 0, "Insufficient balance");

// Format validation
ValidationUtil.isTrue(ValidationUtil.isEmail(email), "Invalid email format");
ValidationUtil.isTrue(ValidationUtil.isPhone(phone), "Invalid phone number format");

// Range validation
ValidationUtil.isBetween(lotSize, minLot, maxLot, "Trading volume out of range");

// Length validation
ValidationUtil.minLength(username, 3, "Username must be at least 3 characters");
ValidationUtil.maxLength(remark, 500, "Remark cannot exceed 500 characters");

// Compound validation
ValidationUtil.notNull(symbol, "Symbol cannot be empty");
ValidationUtil.isPositive(lotSize, "Trading volume must be greater than 0");
ValidationUtil.isBetween(lotSize, minLot, maxLot, "Trading volume out of range");
```

---

### 2.6 StringUtil - String Utility

**Purpose**: String processing
**Path**: common/util/StringUtil.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| isEmpty | String str | boolean | Check if empty (includes null and empty string) |
| isNotEmpty | String str | boolean | Check if not empty |
| isBlank | String str | boolean | Check if blank (includes null, spaces, tabs) |
| isNotBlank | String str | boolean | Check if not blank |
| defaultIfEmpty | String str, String defaultStr | String | Return default if empty |
| defaultIfBlank | String str, String defaultStr | String | Return default if blank |
| trim | String str | String | Trim leading/trailing whitespace |
| trimToNull | String str | String | Trim whitespace, return null if empty |
| substring | String str, int start, int end | String | Substring extraction |
| left | String str, int length | String | Extract left portion of specified length |
| right | String str, int length | String | Extract right portion of specified length |
| repeat | String str, int count | String | Repeat string |
| join | String[] array, String separator | String | Array concatenation |
| join | Collection coll, String separator | String | Collection concatenation |
| split | String str, String separator | String[] | String splitting |
| capitalize | String str | String | Capitalize first letter |
| uncapitalize | String str | String | Lowercase first letter |
| upperCase | String str | String | Convert to uppercase |
| lowerCase | String str | String | Convert to lowercase |
| contains | String str, String searchStr | boolean | Check if contains |
| containsIgnoreCase | String str, String searchStr | boolean | Check if contains (case-insensitive) |
| startsWith | String str, String prefix | boolean | Check if starts with |
| endsWith | String str, String suffix | boolean | Check if ends with |
| indexOf | String str, String searchStr | int | Find position |
| replace | String str, String target, String replacement | String | Replace |
| replaceAll | String str, String regex, String replacement | String | Regex replacement |
| mask | String str, int start, int end, char maskChar | String | String masking/desensitization |

**Usage Example:**

```java
// Check if empty
if (StringUtil.isEmpty(username)) {
    throw new BusinessException("Username cannot be empty");
}

// Default value
String displayName = StringUtil.defaultIfBlank(nickname, username);

// Masking
String maskedPhone = StringUtil.mask(phone, 3, 7, '*'); // "138****8000"
String maskedIdCard = StringUtil.mask(idCard, 4, 14, '*'); // "410****1234"

// String processing
String upperName = StringUtil.upperCase(name);
String trimmed = StringUtil.trim(str);
String joined = StringUtil.join(list, ",");
```

---

### 2.7 JsonUtil - JSON Utility

**Purpose**: JSON serialization/deserialization
**Path**: common/util/JsonUtil.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| toJson | Object obj | String | Object to JSON string |
| toJsonPretty | Object obj | String | Formatted JSON string |
| toJsonBytes | Object obj | byte[] | Object to JSON byte array |
| fromJson | String json, Class<T> clazz | T | JSON string to object |
| fromJson | String json, TypeReference<T> typeRef | T | JSON to complex type |
| fromJson | byte[] bytes, Class<T> clazz | T | JSON byte array to object |
| parseArray | String json, Class<T> clazz | List<T> | JSON array to List |
| parseObject | String json | Map<String, Object> | JSON to Map |
| toMap | Object obj | Map<String, Object> | Object to Map |

**Usage Example:**

```java
// Object to JSON
String json = JsonUtil.toJson(order);

// JSON to object
Order order = JsonUtil.fromJson(json, Order.class);

// Complex type
TypeReference<List<Order>> typeRef = new TypeReference<List<Order>>() {};
List<Order> orders = JsonUtil.fromJson(json, typeRef);

// JSON array
List<Order> orders = JsonUtil.parseArray(jsonArray, Order.class);

// Object to Map
Map<String, Object> map = JsonUtil.toMap(obj);
```

---

### 2.8 Result - Unified Response Wrapper

**Purpose**: Unified API response format
**Path**: common/result/Result.java

**Method List:**

| Method Name | Parameters | Return Value | Description |
|--------|------|--------|------|
| success | () | Result<Void> | Success response (no data) |
| success | T data | Result<T> | Success response (with data) |
| success | T data, String message | Result<T> | Success response (custom message) |
| fail | ResultCode code | Result<Void> | Failure response |
| fail | String message | Result<Void> | Failure response (custom message) |
| fail | int code, String message | Result<Void> | Failure response (custom code) |
| page | List<T> records, long total | Result<PageResult<T>> | Paginated response |
| getCode | () | int | Get response code |
| getMessage | () | String | Get message |
| getData | () | T | Get data |
| isSuccess | () | boolean | Check if successful |

**Usage Example:**

```java
// Success response
return Result.success();
return Result.success(order);
return Result.success("Operation successful");

// Failure response
return Result.fail(ResultCode.PARAM_ERROR);
return Result.fail("Order not found");

// Paginated response
PageResult<Order> pageResult = new PageResult<>(orders, total);
return Result.page(orders, total);
```

---

### 2.9 ResultCode - Response Code Definition

**Purpose**: Define business error codes
**Path**: common/result/ResultCode.java

**Predefined Response Codes:**

| Response Code | Description |
|--------|------|
| SUCCESS | Operation successful |
| PARAM_ERROR | Parameter validation failed |
| TOKEN_EXPIRED | Token has expired |
| TOKEN_INVALID | Token is invalid |
| PERMISSION_DENIED | No permission to access |
| NOT_FOUND | Resource not found |
| ACCOUNT_NOT_FOUND | Account not found |
| ORDER_NOT_FOUND | Order not found |
| POSITION_NOT_FOUND | Position not found |
| BALANCE_NOT_SUFFICIENT | Insufficient balance |
| MARGIN_NOT_SUFFICIENT | Insufficient margin |
| RISK_REJECTED | Risk control rejection |
| DUPLICATE_ORDER | Duplicate order |
| ORDER_EXPIRED | Order has expired |
| SYSTEM_ERROR | System error |

**Usage Example:**

```java
throw new BusinessException(ResultCode.PARAM_ERROR);
throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
throw new BusinessException(40001, "Custom error message");
```

---

### 2.10 PageResult - Paginated Response

**Purpose**: Paginated query response
**Path**: common/result/PageResult.java

**Properties:**

| Property | Type | Description |
|------|------|------|
| records | List<T> | Data list |
| total | long | Total count |
| pageNum | int | Current page number |
| pageSize | int | Items per page |
| pages | long | Total pages |

**Usage Example:**

```java
// Construct paginated result
PageResult<Order> pageResult = new PageResult<>();
pageResult.setRecords(orders);
pageResult.setTotal(total);
pageResult.setPageNum(pageNum);
pageResult.setPageSize(pageSize);
pageResult.setPages((total + pageSize - 1) / pageSize);

return Result.success(pageResult);
```

---

### 2.11 BusinessException - Business Exception

**Purpose**: Business logic exception
**Path**: common/exception/BusinessException.java

**Constructors:**

```java
// Use predefined response code
public BusinessException(ResultCode resultCode)

// Use custom message
public BusinessException(String message)

// Use response code and custom message
public BusinessException(ResultCode resultCode, String message)

// Use custom code and message
public BusinessException(int code, String message)
```

**Usage Example:**

```java
throw new BusinessException("Insufficient balance");
throw new BusinessException(ResultCode.BALANCE_NOT_SUFFICIENT);
throw new BusinessException(ResultCode.RISK_REJECTED, "Exceeds maximum position limit");
```

---

### 2.12 GlobalExceptionHandler - Global Exception Handler

**Purpose**: Unified handling of all exceptions
**Path**: common/exception/GlobalExceptionHandler.java

**Handled Exception Types:**

| Exception Type | Handling Method |
|----------|----------|
| BusinessException | Return business error response |
| ValidationException | Return parameter validation error |
| MethodArgumentNotValidException | Return field validation error |
| BindException | Return binding error |
| ConstraintViolationException | Return constraint violation error |
| MissingServletRequestParameterException | Return missing request parameter error |
| HttpMessageNotReadableException | Return message parsing error |
| NoHandlerFoundException | Return 404 error |
| AccessDeniedException | Return permission denied error |
| Exception | Return system error |

**Response Format:**

```json
{
  "code": 40001,
  "message": "Parameter validation failed",
  "data": null,
  "errors": [
    {
      "field": "symbol",
      "message": "Symbol code cannot be empty"
    }
  ],
  "timestamp": 1704067200000
}
```

---

## 3. Go Utility Packages

### 3.1 pkg/logger - Logger Utility

**Path**: pkg/logger/logger.go

**Function List:**

| Function Name | Description |
|--------|------|
| Debug(msg string, fields ...Field) | Debug log |
| Info(msg string, fields ...Field) | Info log |
| Warn(msg string, fields ...Field) | Warning log |
| Error(msg string, fields ...Field) | Error log |
| Fatal(msg string, fields ...Field) | Fatal log |
| WithField(key string, value interface{}) *Entry | Add field |
| WithTraceID(traceID string) *Entry | Add TraceID |

**Field Types:**

```go
func String(key string, val string) Field
func Int(key string, val int) Field
func Int64(key string, val int64) Field
func Float64(key string, val float64) Field
func Bool(key string, val bool) Field
func Err(err error) Field
func Any(key string, val interface{}) Field
```

**Usage Example:**

```go
import "fx-quote/pkg/logger"

logger.Info("order created", 
    logger.String("order_no", orderNo),
    logger.String("symbol", symbol),
    logger.Float64("lot_size", lotSize),
)
```

### 3.2 pkg/response - Unified Response

**Path**: pkg/response/response.go

**Function List:**

```go
func Success(c *gin.Context, data interface{})
func Fail(c *gin.Context, code int, message string)
func FailWithError(c *gin.Context, err error)
func Page(c *gin.Context, list interface{}, total int64)
```

**Usage Example:**

```go
import "fx-quote/pkg/response"

response.Success(c, order)
response.Fail(c, 40001, "Parameter error")
response.Page(c, orders, total)
```