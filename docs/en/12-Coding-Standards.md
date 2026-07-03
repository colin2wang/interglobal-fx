# Global FX Trading Platform - Coding Standards

## 1. General Standards

### 1.1 Naming Conventions

- **No Pinyin naming**: Use English words or common technical terms
- **Boolean variables**: Use is/has/can/will prefixes
  - Good: `isActive`, `hasPermission`, `canEdit`, `willExecute`
  - Bad: `active`, `permission`, `edit`, `execute`
- **Collection variables**: Use plural or List/Map/Set suffixes
  - Good: `users`, `orderList`, `userMap`
  - Bad: `userList` (but this is also acceptable)
- **Constant naming**: Java uses UPPER_SNAKE_CASE, other languages use conventional constant naming
- **Interfaces and implementations**: Java interfaces do not add I prefix, implementation classes add Impl suffix

### 1.2 Comment Standards

| Comment Type | Standard | Example |
|--------------|----------|---------|
| Class comments | Use JSDoc/JavaDoc, describe class responsibilities | `/** Order service, responsible for full lifecycle management of orders */` |
| Method comments | Parameter, return value, exception description | `/** Create order @param dto order information @return order number */` |
| Inline comments | Explain complex logic, prohibit meaningless comments | `// Use binary search to optimize performance` |
| TODO comments | Mark pending items | `// TODO: Optimize slippage calculation algorithm` |

**Prohibited behaviors:**
- Comments inconsistent with code
- Meaningless repetitive comments (e.g., `// i++; i increments`)
- Outdated comments not updated timely

### 1.3 Code Formatting

| Language | Indentation | Line Width | Line Break |
|----------|-------------|------------|------------|
| Java | 4 spaces | 120 characters | LF |
| Go | Tab | 120 characters | LF |
| TypeScript | 2 spaces | 100 characters | LF |
| Vue | 2 spaces | 120 characters | LF |
| Dart | 2 spaces | 80 characters | LF |

### 1.4 Code Complexity

| Metric | Limit | Description |
|--------|-------|-------------|
| Method lines | ≤80 lines | Must split if exceeded |
| Class lines | ≤500 lines | Must split class if exceeded |
| Method parameters | ≤5 | Wrap in object if exceeded |
| Cyclomatic complexity | ≤10 | Must refactor if exceeded |
| Nesting levels | ≤4 levels | Must refactor if exceeded |

## 2. Java Coding Standards

### 2.1 Controller Layer

```java
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    
    private final OrderService orderService;
    
    /**
     * Create order
     */
    @PostMapping
    public Result<OrderVO> createOrder(@Valid @RequestBody CreateOrderDTO dto) {
        log.info("Create order request: symbol={}, lotSize={}", dto.getSymbol(), dto.getLotSize());
        
        // Parameters already validated via annotations in DTO
        OrderVO orderVO = orderService.createOrder(dto);
        
        return Result.success(orderVO);
    }
    
    /**
     * Query order list
     */
    @GetMapping
    public Result<PageResult<OrderVO>> queryOrders(OrderQueryDTO dto) {
        PageResult<OrderVO> result = orderService.queryOrders(dto);
        return Result.success(result);
    }
    
    /**
     * Cancel order
     */
    @DeleteMapping("/{orderNo}")
    public Result<Void> cancelOrder(@PathVariable String orderNo) {
        orderService.cancelOrder(orderNo);
        return Result.success();
    }
}
```

**Key standards:**
- Use `@Valid` for parameter validation
- Uniformly return `Result<T>`
- Log key operations
- Prohibit business logic in Controller

### 2.2 Service Layer

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final OrderMapper orderMapper;
    private final PositionService positionService;
    private final RiskService riskService;
    private final AccountService accountService;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderVO createOrder(CreateOrderDTO dto) {
        // 1. Parameter validation (business level)
        validateOrderParams(dto);
        
        // 2. Query account information
        Account account = accountService.getById(dto.getAccountId());
        if (account == null) {
            throw new BusinessException(ResultCode.ACCOUNT_NOT_FOUND);
        }
        
        // 3. Risk control check
        RiskCheckResult riskResult = riskService.checkOrder(dto, account);
        if (!riskResult.isPassed()) {
            log.warn("Order risk control rejected: accountId={}, reason={}", 
                     dto.getAccountId(), riskResult.getMessage());
            throw new RiskException(riskResult.getMessage());
        }
        
        // 4. Create order
        Order order = buildOrder(dto);
        orderMapper.insert(order);
        
        // 5. Execute order (market orders are executed directly)
        if (dto.getOrderType() == OrderTypeEnum.MARKET.getCode()) {
            executeMarketOrder(order, dto);
        }
        
        return convertToVO(order);
    }
    
    private void validateOrderParams(CreateOrderDTO dto) {
        if (dto.getLotSize().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Order quantity must be greater than 0");
        }
    }
    
    private void executeMarketOrder(Order order, CreateOrderDTO dto) {
        // Execution logic
    }
}
```

**Key standards:**
- Use `@Transactional` for transaction management
- Use `BusinessException` or custom exceptions for business errors
- Prohibit direct operation of `HttpServletRequest/Response`
- Split complex logic into private methods

### 2.3 Mapper Layer

```java
@Mapper
public interface OrderMapper {
    
    @Insert("INSERT INTO trd_order (order_no, account_id, symbol, order_type, " +
            "order_side, lot_size, status, created_time) " +
            "VALUES (#{orderNo}, #{accountId}, #{symbol}, #{orderType}, " +
            "#{orderSide}, #{lotSize}, #{status}, #{createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);
    
    @Select("<script>" +
            "SELECT * FROM trd_order WHERE is_deleted = 0 " +
            "<if test='accountId != null'> AND account_id = #{accountId} </if>" +
            "<if test='status != null'> AND status = #{status} </if>" +
            "<if test='symbol != null'> AND symbol = #{symbol} </if>" +
            "ORDER BY created_time DESC" +
            "</script>")
    List<Order> selectByCondition(OrderQueryDTO dto);
    
    @Select("SELECT * FROM trd_order WHERE order_no = #{orderNo} AND is_deleted = 0")
    Order selectByOrderNo(@Param("orderNo") String orderNo);
    
    @Update("<script>" +
            "UPDATE trd_order SET " +
            "<if test='status != null'>status = #{status},</if>" +
            "<if test='filledLot != null'>filled_lot = #{filledLot},</if>" +
            "<if test='avgPrice != null'>avg_price = #{avgPrice},</if>" +
            "updated_time = NOW() " +
            "WHERE id = #{id} AND is_deleted = 0" +
            "</script>")
    int updateById(Order order);
}
```

**Key standards:**
- Prohibit `SELECT *`, explicitly list fields
- Use `<script>` tags to wrap dynamic SQL
- Simple queries use annotations, complex ones use XML
- Use `@Param` for explicit parameter naming

### 2.4 DTO/VO Standards

```java
// Create order DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    
    @NotBlank(message = "Symbol code cannot be empty")
    private String symbol;
    
    @NotNull(message = "Order type cannot be empty")
    private Integer orderType;
    
    @NotNull(message = "Order side cannot be empty")
    private Integer orderSide;
    
    @NotNull(message = "Order quantity cannot be empty")
    @DecimalMin(value = "0.01", message = "Minimum trading volume is 0.01 lots")
    @DecimalMax(value = "100.00", message = "Maximum trading volume is 100 lots")
    private BigDecimal lotSize;
    
    // Limit order price
    private BigDecimal orderPrice;
    
    // Slippage
    private Integer slippagePoints = 0;
}

// Order view object
@Data
@Builder
public class OrderVO {
    
    private String orderNo;
    private String symbol;
    private String symbolName;
    private Integer orderType;
    private String orderTypeName;
    private Integer orderSide;
    private String orderSideName;
    private BigDecimal lotSize;
    private BigDecimal filledLot;
    private BigDecimal avgPrice;
    private Integer status;
    private String statusName;
    private String createdTime;
}
```

**Key standards:**
- DTO for receiving request parameters, add validation annotations
- VO for returning to frontend, filter sensitive fields
- Use `@Builder` to simplify construction
- Use `BigDecimal` for amounts, prohibit `Double`

## 3. Go Coding Standards

### 3.1 Handler Layer

```go
type OrderHandler struct {
    orderSvc *service.OrderService
}

func (h *OrderHandler) CreateOrder(c *gin.Context) {
    var req dto.CreateOrderReq
    if err := c.ShouldBindJSON(&req); err != nil {
        result.FailWithError(c, err)
        return
    }
    
    log.Printf("Create order request: symbol=%s, lotSize=%s", req.Symbol, req.LotSize)
    
    resp, err := h.orderSvc.CreateOrder(c.Request.Context(), &req)
    if err != nil {
        log.Printf("Failed to create order: %v", err)
        result.FailWithError(c, err)
        return
    }
    
    result.Success(c, resp)
}
```

### 3.2 Service Layer

```go
type OrderService struct {
    orderRepo   *repository.OrderRepository
    positionSvc *PositionService
    riskSvc     *RiskService
}

func (s *OrderService) CreateOrder(ctx context.Context, req *dto.CreateOrderReq) (*dto.OrderResp, error) {
    // Parameter validation
    if req.LotSize.Sign() <= 0 {
        return nil, errors.New("Order quantity must be greater than 0")
    }
    
    // Risk control check
    riskResult, err := s.riskSvc.CheckOrder(ctx, req)
    if err != nil {
        return nil, err
    }
    if !riskResult.Passed {
        return nil, errors.New("Risk control rejected: " + riskResult.Message)
    }
    
    // Create order
    order := &model.Order{
        OrderNo:    uuid.New().String(),
        Symbol:     req.Symbol,
        OrderType:  req.OrderType,
        OrderSide:  req.OrderSide,
        LotSize:    req.LotSize,
        Status:     model.OrderStatusPending,
    }
    
    if err := s.orderRepo.Create(ctx, order); err != nil {
        return nil, err
    }
    
    return &dto.OrderResp{
        OrderNo: order.OrderNo,
        Status:  order.Status,
    }, nil
}
```

**Key standards:**
- Use `context.Context` to pass request context
- Use `errors.New()` or custom error types for errors
- Log key operations
- Do not use global variables

## 4. TypeScript Coding Standards

### 4.1 API Module

```typescript
// api/order.ts
import client from './client';
import type { ApiResponse, PageResponse } from '@/types/api';

// Create order
export function createOrder(data: CreateOrderDTO): Promise<ApiResponse<OrderVO>> {
  return client.post('/api/v1/order', data);
}

// Query order list
export function getOrderList(params: OrderQueryDTO): Promise<ApiResponse<PageResponse<OrderVO>>> {
  return client.get('/api/v1/order', { params });
}

// Cancel order
export function cancelOrder(orderNo: string): Promise<ApiResponse<void>> {
  return client.delete(`/api/v1/order/${orderNo}`);
}
```

### 4.2 Store Module

```typescript
// store/orderStore.ts
import { create } from 'zustand';
import { createOrder, getOrderList, cancelOrder } from '@/api/order';
import type { Order, CreateOrderDTO } from '@/types/order';

interface OrderState {
  orders: Order[];
  total: number;
  loading: boolean;
  
  // Actions
  fetchOrders: (params?: OrderQueryDTO) => Promise<void>;
  submitOrder: (data: CreateOrderDTO) => Promise<Order>;
  cancelOrderByNo: (orderNo: string) => Promise<void>;
}

export const useOrderStore = create<OrderState>((set, get) => ({
  orders: [],
  total: 0,
  loading: false,
  
  fetchOrders: async (params) => {
    set({ loading: true });
    try {
      const res = await getOrderList(params);
      set({ 
        orders: res.data.records, 
        total: res.data.total,
        loading: false 
      });
    } catch (error) {
      set({ loading: false });
      throw error;
    }
  },
  
  submitOrder: async (data) => {
    const res = await createOrder(data);
    await get().fetchOrders();
    return res.data;
  },
  
  cancelOrderByNo: async (orderNo) => {
    await cancelOrder(orderNo);
    await get().fetchOrders();
  },
}));
```

### 4.3 React Components

```tsx
// components/trade/OrderForm.tsx
import { useState } from 'react';
import { useOrderStore } from '@/store/orderStore';
import { formatMoney } from '@/utils/format';
import type { CreateOrderDTO } from '@/types/order';

interface OrderFormProps {
  symbol: string;
  currentPrice: { bid: string; ask: string };
}

export function OrderForm({ symbol, currentPrice }: OrderFormProps) {
  const [lotSize, setLotSize] = useState('0.10');
  const [orderSide, setOrderSide] = useState<1 | 2>(1);
  
  const { submitOrder, loading } = useOrderStore();
  
  const handleSubmit = async () => {
    const data: CreateOrderDTO = {
      symbol,
      orderType: 1, // Market order
      orderSide,
      lotSize,
    };
    
    try {
      await submitOrder(data);
      message.success('Order submitted successfully');
    } catch (error) {
      message.error('Failed to submit order');
    }
  };
  
  return (
    <div className="order-form">
      <div className="price-display">
        <span className="bid">{currentPrice.bid}</span>
        <span className="ask">{currentPrice.ask}</span>
      </div>
      
      <input
        type="number"
        value={lotSize}
        onChange={(e) => setLotSize(e.target.value)}
        className="lot-input"
      />
      
      <button 
        className="buy-btn" 
        onClick={() => setOrderSide(1)}
        disabled={loading}
      >
        Buy
      </button>
      
      <button 
        className="sell-btn" 
        onClick={() => setOrderSide(2)}
        disabled={loading}
      >
        Sell
      </button>
      
      <button onClick={handleSubmit} disabled={loading}>
        Confirm Order
      </button>
    </div>
  );
}
```

## 5. Vue Coding Standards

### 5.1 Component Standards

```vue
<template>
  <div class="order-list">
    <!-- Search area -->
    <SearchForm :items="searchItems" @search="handleSearch" @reset="handleReset" />
    
    <!-- Toolbar -->
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">Add New</el-button>
      <el-button @click="handleExport">Export</el-button>
    </div>
    
    <!-- Data table -->
    <el-table :data="tableData" v-loading="loading" @selection-change="handleSelection">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="orderNo" label="Order No" width="180" />
      <el-table-column prop="symbol" label="Symbol" width="100" />
      <el-table-column prop="lotSize" label="Quantity" width="100" align="right" />
      <el-table-column prop="status" label="Status">
        <template #default="{ row }">
          <StatusTag :status="row.status" :mappings="statusMappings" />
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleView(row)">Details</el-button>
          <el-button link type="danger" @click="handleDelete(row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- Pagination -->
    <Pagination
      v-model:pageNum="queryParams.pageNum"
      v-model:pageSize="queryParams.pageSize"
      :total="total"
      @change="fetchData"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { getOrderList, deleteOrder } from '@/api/order';
import { ElMessage, ElMessageBox } from 'element-plus';

const loading = ref(false);
const tableData = ref([]);
const total = ref(0);

const queryParams = reactive({
  pageNum: 1,
  pageSize: 20,
  symbol: '',
  status: null,
});

const searchItems = [
  { prop: 'symbol', label: 'Symbol', component: 'Input' },
  { prop: 'status', label: 'Status', component: 'Select', options: [] },
];

const statusMappings = {
  1: { label: 'Pending', type: 'warning' },
  3: { label: 'Filled', type: 'success' },
  4: { label: 'Cancelled', type: 'info' },
};

const fetchData = async () => {
  loading.value = true;
  try {
    const res = await getOrderList(queryParams);
    tableData.value = res.data.records;
    total.value = res.data.total;
  } finally {
    loading.value = false;
  }
};

const handleSearch = () => {
  queryParams.pageNum = 1;
  fetchData();
};

const handleReset = () => {
  queryParams.pageNum = 1;
  queryParams.symbol = '';
  queryParams.status = null;
  fetchData();
};

const handleDelete = async (row: any) => {
  await ElMessageBox.confirm('Confirm delete this order?', 'Prompt', { type: 'warning' });
  await deleteOrder(row.orderNo);
  ElMessage.success('Delete successful');
  fetchData();
};

onMounted(() => {
  fetchData();
});
</script>

<style scoped lang="scss">
.order-list {
  padding: 20px;
}

.toolbar {
  margin: 16px 0;
}
</style>
```

## 6. Security Standards

### 6.1 Password Security

- Passwords use BCrypt encrypted storage (strength 12)
- Prohibit plaintext password transmission (HTTPS)
- Prohibit logging passwords
- Password complexity requirements: uppercase + lowercase + numbers + special characters, minimum 8 characters

### 6.2 SQL Injection Prevention

- Java: Use MyBatis `#{}` parameterized queries
- Go: Use database driver parameterized queries
- Prohibit string concatenation SQL

### 6.3 XSS Prevention

- Input filtering: Whitelist filtering special characters
- Output escaping: HTML special character escaping
- Use mature frameworks for automatic protection

### 6.4 API Security

- All APIs require authentication (except whitelist)
- Sensitive operations require secondary verification
- API rate limiting to prevent abuse
- Request body size limits

### 6.5 Financial Transaction Security

- Order numbers use UUID/Snowflake ID, unpredictable
- Order amounts use high-precision Decimal
- Balance changes use optimistic lock/distributed lock
- All transaction operations record audit logs
- Critical operations support reversal/correction