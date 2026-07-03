# Interglobal FX Trading Platform - Common Component Design Document

## 1. Component Inventory

### 1.1 React Trading Terminal Components

| Component Name | Path | Purpose | Usage Scenario |
|--------|------|------|----------|
| CandlestickChart | components/chart/CandlestickChart | Candlestick chart | Trading page |
| DepthChart | components/chart/DepthChart | Market depth chart | Trading page |
| SymbolTicker | components/quote/SymbolTicker | Real-time price ticker | Global |
| OrderForm | components/trade/OrderForm | Order form | Trading page |
| PositionCard | components/trade/PositionCard | Position card | Position page |
| OrderFormModal | components/trade/OrderFormModal | Order form modal | Trading page |
| AccountBalance | components/account/AccountBalance | Account balance display | Account page |
| ProfitLossBadge | components/common/ProfitLossBadge | Profit/Loss badge | Multiple locations |
| PriceDisplay | components/common/PriceDisplay | Price display | Multiple locations |
| CountdownTimer | components/common/CountdownTimer | Countdown timer | Countdown scenarios |

### 1.2 Vue Admin Panel Components

| Component Name | Path | Purpose | Usage Scenario |
|--------|------|------|----------|
| Pagination | components/common/Pagination.vue | Pagination component | List pages |
| SearchForm | components/form/SearchForm.vue | Search form | List pages |
| ConfirmDialog | components/common/ConfirmDialog.vue | Confirmation dialog | Delete operations, etc. |
| DictSelect | components/form/DictSelect.vue | Dictionary selector | Form components |
| DateRangePicker | components/form/DateRangePicker.vue | Date range picker | Search forms |
| StatusTag | components/common/StatusTag.vue | Status tag | Table columns |
| ActionButtons | components/common/ActionButtons.vue | Action button group | Table columns |
| ImportDialog | components/common/ImportDialog.vue | Import dialog | Data import |
| ExportButton | components/common/ExportButton.vue | Export button | List pages |
| PermissionButton | components/common/PermissionButton.vue | Permission button | Action buttons |

### 1.3 Flutter Mobile Components

| Component Name | Path | Purpose | Usage Scenario |
|--------|------|------|----------|
| CandlestickChartWidget | widgets/chart/candlestick_chart.dart | Candlestick chart | Trading page |
| OrderFormWidget | widgets/order/order_form.dart | Order form | Trading page |
| PositionCard | widgets/order/position_card.dart | Position card | Position page |
| PriceTicker | widgets/quote/price_ticker.dart | Real-time price | Global |
| AccountSummary | widgets/account/account_summary.dart | Account summary | Account page |
| LoadingIndicator | widgets/common/loading_indicator.dart | Loading indicator | Global |
| EmptyState | widgets/common/empty_state.dart | Empty state | List empty |
| PullToRefresh | widgets/common/pull_to_refresh.dart | Pull to refresh | List pages |

---

## 2. React Component Detailed Documentation

### 2.1 CandlestickChart (Candlestick Chart)

**Purpose**: Display forex candlestick charts with multi-timeframe switching support
**Path**: components/chart/CandlestickChart.tsx

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| symbol | string | Yes | - | Symbol code |
| period | string | No | '1h' | Candlestick period: 1m/5m/15m/1h/4h/1d |
| height | number | No | 400 | Chart height |
| showVolume | boolean | No | true | Whether to display volume |
| onPeriodChange | (period: string) => void | No | - | Period change callback |

**Events:**

| Event | Parameters | Description |
|--------|------|------|
| onCandleClick | (candle: CandleData) => void | Click on candlestick |
| onCrosshairMove | (data: CrosshairData) => void | Crosshair movement |

**Slots:**

| Slot Name | Description |
|--------|------|
| toolbar | Toolbar area |
| legend | Legend area |

**Usage Example:**
```tsx
import { CandlestickChart } from '@/components/chart';

<CandlestickChart
  symbol="EURUSD"
  period="1h"
  height={500}
  showVolume={true}
  onPeriodChange={(p) => setPeriod(p)}
  onCandleClick={(candle) => console.log(candle)}
/>
```

---

### 2.2 DepthChart (Market Depth Chart)

**Purpose**: Display order book depth, bid/ask visualization
**Path**: components/chart/DepthChart.tsx

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| bids | Array<{price: string, volume: string}> | Yes | - | Bid data |
| asks | Array<{price: string, volume: string}> | Yes | - | Ask data |
| height | number | No | 200 | Chart height |
| precision | number | No | 5 | Price precision |

**Usage Example:**
```tsx
<DepthChart
  bids={depthData.bids}
  asks={depthData.asks}
  height={200}
/>
```

---

### 2.3 SymbolTicker (Real-time Price Ticker)

**Purpose**: Display real-time buy/sell prices for currency pairs
**Path**: components/quote/SymbolTicker.tsx

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| symbol | string | Yes | - | Symbol code |
| size | 'small' \| 'medium' \| 'large' | No | 'medium' | Size |
| showChange | boolean | No | true | Whether to display price change |
| onClick | () => void | No | - | Click callback |

**Usage Example:**
```tsx
<SymbolTicker
  symbol="EURUSD"
  size="large"
  showChange={true}
  onClick={() => selectSymbol('EURUSD')}
/>
```

---

### 2.4 OrderForm (Order Form)

**Purpose**: Trading order form, supports market orders, limit orders, stop-loss orders
**Path**: components/trade/OrderForm.tsx

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| symbol | string | Yes | - | Symbol code |
| currentPrice | {bid: string, ask: string} | Yes | - | Current price |
| minLot | string | Yes | - | Minimum lot size |
| maxLot | string | Yes | - | Maximum lot size |
| lotStep | string | Yes | - | Lot step increment |
| leverage | number | Yes | - | Current leverage |
| onSubmit | (params: OrderParams) => Promise<void> | Yes | - | Submit callback |

**Events:**

| Event | Parameters | Description |
|--------|------|------|
| onSuccess | (order: Order) => void | Order placed successfully |
| onError | (error: Error) => void | Order placement failed |

**Usage Example:**
```tsx
<OrderForm
  symbol="EURUSD"
  currentPrice={{ bid: '1.08500', ask: '1.08520' }}
  minLot="0.01"
  maxLot="100"
  lotStep="0.01"
  leverage={30}
  onSubmit={handleSubmit}
/>
```

---

### 2.5 PositionCard (Position Card)

**Purpose**: Display individual position information, supports closing position, setting take-profit/stop-loss
**Path**: components/trade/PositionCard.tsx

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| position | Position | Yes | - | Position data |
| currentPrice | string | Yes | - | Current price |
| onClose | (positionId: number) => void | No | - | Close position callback |
| onSetSLTP | (positionId: number, sltp: SLTPData) => void | No | - | Set take-profit/stop-loss |
| onReverse | (positionId: number) => void | No | - | Reverse position callback |

**Usage Example:**
```tsx
<PositionCard
  position={positionData}
  currentPrice="1.08600"
  onClose={(id) => handleClose(id)}
  onSetSLTP={(id, data) => handleSLTP(id, data)}
/>
```

---

## 3. Vue Admin Panel Component Detailed Documentation

### 3.1 Pagination (Pagination Component)

**Purpose**: Unified pagination component with multiple style support
**Path**: components/common/Pagination.vue

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| pageNum | number | Yes | - | Current page number |
| pageSize | number | Yes | - | Items per page |
| total | number | Yes | - | Total items |
| sizes | array | No | [10,20,50,100] | Page size options |

**Events:**

| Event | Parameters | Description |
|--------|------|------|
| update:pageNum | (val: number) => void | Page number change |
| update:pageSize | (val: number) => void | Page size change |
| change | ({pageNum, pageSize}) => void | Any change |

**Usage Example:**
```vue
<template>
  <Pagination
    v-model:pageNum="queryParams.pageNum"
    v-model:pageSize="queryParams.pageSize"
    :total="total"
    @change="handlePageChange"
  />
</template>
```

---

### 3.2 SearchForm (Search Form)

**Purpose**: Unified search area layout with collapse/expand support
**Path**: components/form/SearchForm.vue

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| items | SearchItem[] | Yes | - | Form item configuration |
| collapsed | boolean | No | false | Whether collapsed |
| collapsedRows | number | No | 1 | Number of rows when collapsed |

**SearchItem Type:**

| Property | Type | Description |
|------|------|------|
| prop | string | Field name |
| label | string | Label text |
| component | string | Component type: Input/Select/DatePicker, etc. |
| props | object | Component properties |
| options | array | Dropdown options (for Select) |

**Events:**

| Event | Parameters | Description |
|--------|------|------|
| search | (values: object) => void | Click search |
| reset | () => void | Click reset |

**Usage Example:**
```vue
<template>
  <SearchForm
    :items="searchItems"
    @search="handleSearch"
    @reset="handleReset"
  />
</template>

<script setup>
const searchItems = [
  { prop: 'orderNo', label: 'Order Number', component: 'Input' },
  { prop: 'status', label: 'Status', component: 'Select', options: statusOptions },
  { prop: 'dateRange', label: 'Time', component: 'DatePicker' },
];
</script>
```

---

### 3.3 ConfirmDialog (Confirmation Dialog)

**Purpose**: Secondary confirmation for dangerous operations like deletion
**Path**: components/common/ConfirmDialog.vue

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| v-model | boolean | Yes | - | Dialog visibility |
| title | string | No | 'Prompt' | Title |
| content | string | Yes | - | Content |
| type | 'warning' \| 'danger' \| 'info' | No | 'warning' | Type |
| confirmText | string | No | 'Confirm' | Confirm button text |
| cancelText | string | No | 'Cancel' | Cancel button text |

**Events:**

| Event | Parameters | Description |
|--------|------|------|
| confirm | () => void | Click confirm |
| cancel | () => void | Click cancel |

**Usage Example:**
```vue
<template>
  <ConfirmDialog
    v-model="dialogVisible"
    title="Confirm Delete"
    content="Data cannot be recovered after deletion. Are you sure you want to delete?"
    type="danger"
    @confirm="handleConfirm"
  />
</template>
```

---

### 3.4 DictSelect (Dictionary Selector)

**Purpose**: Dropdown component that automatically loads options based on dictionary type
**Path**: components/form/DictSelect.vue

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| v-model | string \| number | Yes | - | Bound value |
| dictType | string | Yes | - | Dictionary type |
| placeholder | string | No | 'Please select' | Placeholder text |
| clearable | boolean | No | true | Whether clearable |
| filterable | boolean | No | true | Whether searchable |

**Events:**

| Event | Parameters | Description |
|--------|------|------|
| change | (val) => void | Value change |

**Usage Example:**
```vue
<template>
  <DictSelect
    v-model="form.status"
    dict-type="order_status"
    placeholder="Please select order status"
  />
</template>
```

---

### 3.5 StatusTag (Status Tag)

**Purpose**: Display tags with different colors based on status value
**Path**: components/common/StatusTag.vue

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| status | string \| number | Yes | - | Status value |
| dictType | string | No | - | Dictionary type |
| mappings | Record<string, string> | No | - | Custom mappings |

**Usage Example:**
```vue
<template>
  <StatusTag
    :status="row.status"
    :mappings="{
      1: { label: 'Pending', type: 'warning' },
      2: { label: 'Processing', type: 'primary' },
      3: { label: 'Completed', type: 'success' },
      4: { label: 'Failed', type: 'danger' },
    }"
  />
</template>
```

---

### 3.6 PermissionButton (Permission Button)

**Purpose**: Control button visibility based on permission identifier
**Path**: components/common/PermissionButton.vue

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| permission | string | Yes | - | Permission identifier |
| type | string | No | 'primary' | Button type |
| size | string | No | 'default' | Button size |

**Slots:**

| Slot Name | Description |
|--------|------|
| default | Button text |

**Usage Example:**
```vue
<template>
  <PermissionButton
    permission="user:add"
    type="primary"
    @click="handleAdd"
  >
    Add New
  </PermissionButton>
</template>
```

---

## 4. Flutter Mobile Component Detailed Documentation

### 4.1 CandlestickChartWidget (Candlestick Chart)

**Purpose**: Display forex candlestick charts
**Path**: widgets/chart/candlestick_chart.dart

**Props (Parameters):**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| candles | List<Candle> | Yes | - | Candlestick data |
| period | String | No | '1h' | Candlestick period |
| height | double | No | 300 | Chart height |

**Events (Callbacks):**

| Event | Description |
|--------|------|
| onPeriodChanged | Period change callback |
| onCandleTap | Candlestick tap callback |

**Usage Example:**
```dart
CandlestickChartWidget(
  candles: candles,
  period: '1h',
  height: 300,
  onPeriodChanged: (p) => setState(() => period = p),
)
```

---

### 4.2 OrderFormWidget (Order Form)

**Purpose**: Mobile order form
**Path**: widgets/order/order_form.dart

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| symbol | String | Yes | - | Symbol code |
| currentBid | String | Yes | - | Current bid price |
| currentAsk | String | Yes | - | Current ask price |
| onSubmit | Function | Yes | - | Submit callback |

**Usage Example:**
```dart
OrderFormWidget(
  symbol: 'EURUSD',
  currentBid: '1.08500',
  currentAsk: '1.08520',
  onSubmit: (params) => orderBloc.add(CreateOrder(params)),
)
```

---

### 4.3 PositionCard (Position Card)

**Purpose**: Mobile position card
**Path**: widgets/order/position_card.dart

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| position | Position | Yes | - | Position data |
| currentPrice | String | Yes | - | Current price |
| onClose | Function | No | - | Close position callback |
| onSetSLTP | Function | No | - | Set take-profit/stop-loss |

**Usage Example:**
```dart
PositionCard(
  position: position,
  currentPrice: '1.08600',
  onClose: () => closePosition(position.id),
  onSetSLTP: (sl, tp) => setSLTP(position.id, sl, tp),
)
```

---

### 4.4 PriceTicker (Real-time Price)

**Purpose**: Display real-time buy/sell prices
**Path**: widgets/quote/price_ticker.dart

**Props:**

| Property | Type | Required | Default | Description |
|--------|------|------|--------|------|
| symbol | String | Yes | - | Symbol code |
| bid | String | Yes | - | Bid price |
| ask | String | Yes | - | Ask price |
| spread | String | No | - | Spread |

**Usage Example:**
```dart
PriceTicker(
  symbol: 'EURUSD',
  bid: '1.08500',
  ask: '1.08520',
  spread: '2.0',
)
```

---

## 5. Component Development Standards

### 5.1 React Component Standards

- Use function components with Hooks
- Define Props interfaces using TypeScript
- Place components in the components directory, organized by functional modules
- Provide complete JSDoc comments for common components
- Use CSS Modules or styled-components for styling

### 5.2 Vue Component Standards

- Use Composition API (setup syntax sugar)
- Define Props using defineProps
- Place components in components/common or components/form directories
- Support v-model two-way binding for common components
- Use SCSS for styling with BEM naming convention

### 5.3 Flutter Component Standards

- Use StatelessWidget or StatefulWidget
- Use const constructors for immutable components
- Place components in the widgets directory
- Use Provider or BLoC for state management
- Follow Material Design specifications

---

## 6. Third-party Component Dependencies

### 6.1 React Ecosystem

| Library | Version | Purpose |
|--------|------|------|
| antd | ^5.x | UI component library |
| echarts | ^5.x | Charts |
| echarts-for-react | ^3.x | React wrapper |
| react-router-dom | ^6.x | Routing |
| zustand | ^4.x | State management |
| axios | ^1.x | HTTP requests |
| socket.io-client | ^4.x | WebSocket |
| dayjs | ^1.x | Date handling |
| lodash | ^4.x | Utility functions |

### 6.2 Vue Ecosystem

| Library | Version | Purpose |
|--------|------|------|
| element-plus | ^2.x | UI component library |
| echarts | ^5.x | Charts |
| vue-echarts | ^6.x | Vue wrapper |
| vue-router | ^4.x | Routing |
| pinia | ^2.x | State management |
| axios | ^1.x | HTTP requests |
| dayjs | ^1.x | Date handling |

### 6.3 Flutter Ecosystem

| Library | Version | Purpose |
|--------|------|------|
| fl_chart | ^0.68.x | Charts |
| dio | ^5.x | HTTP requests |
| go_router | ^14.x | Routing |
| flutter_bloc | ^8.x | State management |
| get_it | ^7.x | Dependency injection |
| shared_preferences | ^2.x | Local storage |
| intl | ^0.19.x | Internationalization |
