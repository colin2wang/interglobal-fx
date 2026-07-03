# Testing Strategy

## Overview

The project follows a multi-layer testing approach:

| Layer | Tool | Coverage Target | Priority |
|-------|------|-----------------|----------|
| Unit Tests | JUnit 5 + Mockito (Java) | 80%+ | P0 |
| Unit Tests | Go testing package | 80%+ | P0 |
| Unit Tests | Vitest (Frontend) | 70%+ | P1 |
| Integration Tests | Spring Boot Test | Critical paths | P1 |
| E2E Tests | Playwright / Cypress | Core flows | P2 |

## Backend Testing (Java)

### Unit Tests

Location: `backend/<module>/src/test/java/`

```bash
# Run all tests
mvn test

# Run tests for a specific module
mvn test -pl fx-trade

# Run a specific test class
mvn test -pl fx-trade -Dtest=OrderServiceTest
```

### Test Structure

```
src/test/java/com/globalfx/trade/
├── service/
│   └── OrderServiceTest.java
├── controller/
│   └── OrderControllerTest.java
└── mapper/
    └── OrderMapperTest.java
```

### Writing Unit Tests

```java
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldCreateOrder() {
        // Arrange
        CreateOrderDTO dto = new CreateOrderDTO();
        dto.setSymbol("EURUSD");
        dto.setLotSize(new BigDecimal("0.10"));

        when(orderMapper.insert(any())).thenReturn(1);

        // Act
        OrderVO result = orderService.createOrder(dto);

        // Assert
        assertNotNull(result);
        verify(orderMapper).insert(any());
    }
}
```

### Integration Tests

```java
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOrderList() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
```

## Backend Testing (Go)

### Unit Tests

Location: `go-services/<service>/internal/`

```bash
cd go-services/fx-quote

# Run all tests
go test ./...

# Run with verbose output
go test -v ./...

# Run with coverage
go test -cover ./...

# Run specific test
go test -run TestQuoteService -v ./internal/service/
```

### Writing Go Tests

```go
func TestQuoteService_GetLatest(t *testing.T) {
    repo := &mockQuoteRepo{}
    svc := NewQuoteService(repo)

    quote := svc.GetLatest("EURUSD")

    assert.NotNil(t, quote)
    assert.Equal(t, "EURUSD", quote.Symbol)
    assert.True(t, quote.Price > 0)
}
```

## Frontend Testing

### Setup

```bash
cd frontend/fx-trader-web

# Install test dependencies
pnpm add -D vitest @testing-library/react @testing-library/jest-dom
```

### Configuration

```typescript
// vite.config.ts
export default defineConfig({
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './src/test/setup.ts',
  },
});
```

### Writing Frontend Tests

```typescript
import { render, screen, fireEvent } from '@testing-library/react';
import { OrderForm } from '@/components/trade/OrderForm';

describe('OrderForm', () => {
  it('renders buy and sell buttons', () => {
    render(<OrderForm symbol="EURUSD" />);
    expect(screen.getByText('Buy')).toBeInTheDocument();
    expect(screen.getByText('Sell')).toBeInTheDocument();
  });

  it('submits order on button click', async () => {
    const onSubmit = vi.fn();
    render(<OrderForm symbol="EURUSD" onSubmit={onSubmit} />);
    fireEvent.click(screen.getByText('Buy'));
    expect(onSubmit).toHaveBeenCalled();
  });
});
```

### Running Frontend Tests

```bash
pnpm run test          # Run once
pnpm run test:watch    # Watch mode
pnpm run test:coverage # Coverage report
```

## Test Data Management

### Database Fixtures

```sql
-- backend/sql/03-init-data.sql
INSERT INTO sym_symbol (symbol, symbol_name, symbol_type) VALUES
('EURUSD', 'Euro/US Dollar', 1),
('GBPUSD', 'British Pound/US Dollar', 1),
('USDJPY', 'US Dollar/Japanese Yen', 1);
```

### Mock Data (Frontend)

```typescript
// src/test/mocks/handlers.ts
import { http, HttpResponse } from 'msw';

export const handlers = [
  http.get('/api/v1/quotes', () => {
    return HttpResponse.json({
      code: 200,
      data: [{ symbol: 'EURUSD', bid: 1.0850, ask: 1.0852 }],
    });
  }),
];
```

## CI Testing

Tests run automatically on every push/PR:

```yaml
# .github/workflows/test.yml
jobs:
  java-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '21'
      - run: mvn test

  go-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-go@v5
        with:
          go-version: '1.21'
      - run: go test ./...

  frontend-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: pnpm/action-setup@v2
      - run: pnpm install && pnpm run test
```

## Coverage Reports

```bash
# Java (JaCoCo)
mvn jacoco:report
# Open: backend/fx-trade/target/site/jacoco/index.html

# Go
go test -coverprofile=coverage.out ./...
go tool cover -html=coverage.out

# Frontend (Vitest)
pnpm run test:coverage
# Open: coverage/index.html
```
