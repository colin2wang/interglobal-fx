# fx-common

Shared library for all Java backend services. Contains common utilities, base classes, exception handling, and configuration.

## Contents

| Package | Description |
|---------|-------------|
| `base` | `BaseEntity` with auto-filled audit fields (id, createdBy, createdTime, etc.) |
| `result` | Unified API response: `Result<T>`, `ResultCode`, `PageResult<T>` |
| `exception` | `BusinessException`, `GlobalExceptionHandler` (REST error handling) |
| `enums` | 25+ business enums: `OrderTypeEnum`, `OrderStatusEnum`, `AccountStatusEnum`, etc. |
| `util` | `IdGenerator`, `DateUtil`, `MoneyUtil`, `ValidationUtil`, `StringUtil`, `JsonUtil`, `SecurityUtils` |
| `config` | `MyBatisPlusConfig`, `RedisConfig`, `JacksonConfig`, `SwaggerConfig` |
| `handler` | `MyMetaObjectHandler` (auto-fill createdTime/updatedTime) |
| `constant` | `TradeConstant`, `OrderConstant`, `SystemConstant` |

## Dependencies

```xml
<dependency>
    <groupId>com.globalfx</groupId>
    <artifactId>fx-common</artifactId>
</dependency>
```

## Usage

```java
// Unified response
return Result.success(orderVO);
return Result.fail(ResultCode.BALANCE_NOT_SUFFICIENT);

// ID generation
String orderNo = IdGenerator.nextOrderNo(); // "OR20240101100000123456"

// Financial calculations
BigDecimal pnl = MoneyUtil.calculatePnL(openPrice, closePrice, lotSize, contractSize);
BigDecimal margin = MoneyUtil.calculateMargin(price, lotSize, contractSize, leverage);

// Current user context
Long userId = SecurityUtils.getCurrentUserId();
Long tenantId = SecurityUtils.getCurrentTenantId();
```
