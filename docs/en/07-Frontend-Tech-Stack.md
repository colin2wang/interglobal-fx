# Global FX Trading Platform - Frontend Tech Stack Specification

## 1. Technology Selection Overview

### 1.1 Trading Terminal (Web - React)

| Category | Technology | Version | Description |
|----------|------------|---------|-------------|
| Core Framework | React | 18.x | UI Framework |
| Language | TypeScript | 5.x | Type Safety |
| UI Component Library | Ant Design Pro | 5.x | Enterprise-grade Component Library |
| Build Tool | Vite | 5.x | Fast Build |
| State Management | Zustand | 4.x | Lightweight State Management |
| Router | React Router | 6.x | SPA Router |
| HTTP Client | Axios | 1.x | Request Encapsulation |
| WebSocket | socket.io-client | 4.x | Real-time Market Data Push |
| Chart Library | ECharts | 5.x | K-line Chart, Depth Chart |
| Internationalization | i18next | 23.x | Multi-language Support |
| Styling Solution | CSS Modules + SCSS | - | Modular Styling |
| Code Standards | ESLint + Prettier | - | Code Quality |

### 1.2 Admin Backend (Web - Vue3)

| Category | Technology | Version | Description |
|----------|------------|---------|-------------|
| Core Framework | Vue | 3.x | UI Framework |
| Language | TypeScript | 5.x | Type Safety |
| UI Component Library | Element Plus | 2.x | Enterprise-grade Component Library |
| Build Tool | Vite | 5.x | Fast Build |
| State Management | Pinia | 2.x | Vue State Management |
| Router | Vue Router | 4.x | SPA Router |
| HTTP Client | Axios | 1.x | Request Encapsulation |
| Chart Library | ECharts | 5.x | Data Visualization |
| Internationalization | vue-i18n | 9.x | Multi-language Support |
| Styling Solution | SCSS | - | Style Preprocessing |
| Table | Element Plus Table | - | Data Table |
| Form | Element Plus Form | - | Form Components |
| Code Standards | ESLint + Prettier | - | Code Quality |

### 1.3 Mobile (Flutter)

| Category | Technology | Version | Description |
|----------|------------|---------|-------------|
| Framework | Flutter | 3.x | Cross-platform Framework |
| Language | Dart | 3.x | Development Language |
| State Management | flutter_bloc | 8.x | BLoC Pattern |
| Router | go_router | 12.x | Declarative Routing |
| HTTP | dio | 5.x | Network Requests |
| WebSocket | web_socket_channel | 2.x | Real-time Communication |
| Local Storage | shared_preferences | 2.x | Local Data |
| Charts | fl_chart | 0.65.x | K-line Chart |
| UI Components | Material Design 3 | - | Google Design Language |

## 2. Project Structure

### 2.1 React Trading Terminal Structure

```
fx-trader-web/
├── public/                 # Static Assets
├── src/
│   ├── api/               # API Request Modules
│   │   ├── quote.ts      # Quote API
│   │   ├── order.ts      # Order API
│   │   ├── account.ts     # Account API
│   │   └── index.ts      # Unified Export
│   ├── assets/            # Static Assets
│   │   ├── images/        # Images
│   │   └── styles/        # Global Styles
│   ├── components/         # Common Components
│   │   ├── common/        # General Components
│   │   │   ├── PageLoading.tsx
│   │   │   └── ErrorBoundary.tsx
│   │   ├── chart/         # Chart Components
│   │   │   ├── CandlestickChart.tsx
│   │   │   └── DepthChart.tsx
│   │   ├── quote/         # Quote Components
│   │   │   ├── SymbolTicker.tsx
│   │   │   └── PriceBoard.tsx
│   │   └── trade/         # Trade Components
│   │       ├── OrderForm.tsx
│   │       └── PositionCard.tsx
│   ├── composables/       # Composition Functions
│   │   ├── useWebSocket.ts
│   │   ├── useOrder.ts
│   │   └── useAccount.ts
│   ├── hooks/             # Custom Hooks
│   ├── layouts/           # Layout Components
│   │   ├── MainLayout.tsx
│   │   └── TradeLayout.tsx
│   ├── pages/             # Page Components
│   │   ├── trading/       # Trading Pages
│   │   │   ├── TradingPage.tsx
│   │   │   └── OrderHistory.tsx
│   │   ├── account/       # Account Pages
│   │   │   └── AccountPage.tsx
│   │   └── report/        # Report Pages
│   │       └── ReportPage.tsx
│   ├── router/            # Router Configuration
│   │   └── index.tsx
│   ├── store/             # State Management
│   │   ├── quoteStore.ts
│   │   ├── orderStore.ts
│   │   ├── accountStore.ts
│   │   └── userStore.ts
│   ├── types/             # TypeScript Type Definitions
│   │   ├── api.d.ts
│   │   ├── order.d.ts
│   │   └── quote.d.ts
│   ├── utils/             # Utility Functions
│   │   ├── format.ts      # Formatting Utilities
│   │   ├── validation.ts  # Validation Utilities
│   │   └── storage.ts     # Storage Utilities
│   ├── App.tsx
│   ├── main.tsx
│   └── index.scss
├── .env.development       # Development Environment Variables
├── .env.production        # Production Environment Variables
├── vite.config.ts
├── tsconfig.json
├── package.json
└── README.md
```

### 2.2 Vue3 Admin Backend Structure

```
fx-admin-web/
├── public/                 # Static Assets
├── src/
│   ├── api/               # API Request Modules
│   │   ├── system/        # System Management
│   │   ├── order/         # Order Management
│   │   ├── customer/      # Customer Management
│   │   ├── risk/          # Risk Management
│   │   └── index.ts       # Unified Export
│   ├── assets/            # Static Assets
│   │   ├── images/
│   │   └── styles/
│   ├── components/         # Common Components
│   │   ├── common/        # General Components
│   │   │   ├── Pagination.vue
│   │   │   ├── SearchForm.vue
│   │   │   └── ConfirmDialog.vue
│   │   ├── form/          # Form Components
│   │   │   ├── DictSelect.vue
│   │   │   └── DateRangePicker.vue
│   │   └── table/         # Table Components
│   │       └── DataTable.vue
│   ├── composables/        # Composition Functions
│   │   ├── useTable.ts
│   │   ├── useDialog.ts
│   │   └── usePermission.ts
│   ├── directives/         # Custom Directives
│   │   └── permission.ts
│   ├── layouts/            # Layout Components
│   │   ├── index.vue
│   │   ├── Sidebar.vue
│   │   ├── Header.vue
│   │   └── TagsView.vue
│   ├── router/            # Router Configuration
│   │   └── index.ts
│   ├── store/             # State Management
│   │   ├── user.ts
│   │   ├── permission.ts
│   │   └── settings.ts
│   ├── types/             # TypeScript Type Definitions
│   ├── utils/             # Utility Functions
│   │   ├── validate.ts
│   │   ├── format.ts
│   │   └── auth.ts
│   ├── views/             # Page Components
│   │   ├── dashboard/
│   │   ├── system/        # System Management
│   │   ├── order/         # Order Management
│   │   ├── customer/      # Customer Management
│   │   ├── risk/          # Risk Management
│   │   ├── report/        # Report Management
│   │   └── compliance/    # Compliance Management
│   ├── App.vue
│   ├── main.ts
│   └── styles/
│       ├── variables.scss
│       └── common.scss
├── .env.development
├── .env.production
├── vite.config.ts
├── tsconfig.json
├── package.json
└── README.md
```

### 2.3 Flutter Mobile Structure

```
fx_trader_app/
├── lib/
│   ├── api/               # API Requests
│   │   ├── api_client.dart
│   │   ├── quote_api.dart
│   │   ├── order_api.dart
│   │   └── account_api.dart
│   ├── app/               # App Configuration
│   │   ├── app.dart
│   │   ├── routes.dart
│   │   └── theme.dart
│   ├── bloc/              # BLoC State Management
│   │   ├── quote/
│   │   │   ├── quote_bloc.dart
│   │   │   ├── quote_event.dart
│   │   │   └── quote_state.dart
│   │   ├── order/
│   │   └── account/
│   ├── models/             # Data Models
│   │   ├── quote_model.dart
│   │   ├── order_model.dart
│   │   └── account_model.dart
│   ├── repositories/       # Data Repositories
│   │   ├── quote_repository.dart
│   │   └── order_repository.dart
│   ├── screens/            # Pages
│   │   ├── trading/
│   │   │   ├── trading_screen.dart
│   │   │   └── chart_screen.dart
│   │   ├── account/
│   │   │   └── account_screen.dart
│   │   └── settings/
│   │       └── settings_screen.dart
│   ├── widgets/            # Components
│   │   ├── chart/
│   │   │   └── candlestick_chart.dart
│   │   ├── order/
│   │   │   └── order_form.dart
│   │   └── common/
│   │       └── loading_indicator.dart
│   └── utils/              # Utility Functions
│       ├── formatters.dart
│       └── validators.dart
├── pubspec.yaml
└── README.md
```

## 3. Request Encapsulation Specification

### 3.1 React/Axios Instance Configuration

```typescript
// api/client.ts
import axios from 'axios';
import { message } from 'antd';

const client = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
});

// Request Interceptor
client.interceptors.request.use((config) => {
  const token = localStorage.getItem('access_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  config.headers['X-Tenant-Id'] = localStorage.getItem('tenant_id');
  return config;
});

// Response Interceptor
client.interceptors.response.use(
  (response) => response.data,
  (error) => {
    const { code, message: msg } = error.response?.data || {};
    
    if (code === 40101) {
      // Token expired, redirect to login
      localStorage.clear();
      window.location.href = '/login';
    } else {
      message.error(msg || 'Request failed');
    }
    return Promise.reject(error);
  }
);

export default client;
```

### 3.2 Flutter/Dio Instance Configuration

```dart
// api/api_client.dart
class ApiClient {
  static final Dio _dio = Dio(
    BaseOptions(
      baseUrl: EnvConfig.apiBaseUrl,
      connectTimeout: const Duration(seconds: 30),
      receiveTimeout: const Duration(seconds: 30),
      headers: {
        'Content-Type': 'application/json',
      },
    ),
  )..interceptors.add(
      InterceptorsWrapper(
        onRequest: (options, handler) {
          final token = StorageUtil.getToken();
          if (token != null) {
            options.headers['Authorization'] = 'Bearer $token';
          }
          return handler.next(options);
        },
        onError: (error, handler) {
          if (error.response?.statusCode == 401) {
            // Token expired handling
            Get.offAllNamed('/login');
          }
          return handler.next(error);
        },
      ),
    );
}
```

## 4. Router Specification

### 4.1 React Router Configuration

```typescript
// router/index.tsx
const router = createBrowserRouter([
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/',
    element: <MainLayout />,
    children: [
      {
        path: '',
        element: <Navigate to="/trading" replace />,
      },
      {
        path: 'trading',
        element: <TradingPage />,
      },
      {
        path: 'positions',
        element: <PositionsPage />,
      },
      {
        path: 'orders',
        element: <OrdersPage />,
      },
      {
        path: 'account',
        element: <AccountPage />,
      },
    ],
  },
]);
```

### 4.2 Vue Router Configuration

```typescript
// router/index.ts
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: 'Login', hidden: true }
  },
  {
    path: '/',
    component: () => import('@/layouts/index.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: 'Workspace', icon: 'dashboard' }
      }
    ]
  }
];

export const asyncRoutes: RouteRecordRaw[] = [
  {
    path: '/order',
    component: () => import('@/layouts/index.vue'),
    meta: { title: 'Order Management', icon: 'order', permission: 'order:view' },
    children: [
      {
        path: 'list',
        component: () => import('@/views/order/list.vue'),
        meta: { title: 'Order List', permission: 'order:view' }
      }
    ]
  }
];
```

## 5. State Management Specification

### 5.1 React/Zustand Store

```typescript
// store/quoteStore.ts
import { create } from 'zustand';

interface QuoteState {
  symbols: Map<string, QuoteData>;
  subscribe: (symbols: string[]) => void;
  unsubscribe: (symbols: string[]) => void;
}

export const useQuoteStore = create<QuoteState>((set, get) => ({
  symbols: new Map(),
  
  subscribe: (symbols) => {
    // WebSocket subscription logic
    const ws = getWebSocket();
    ws.emit('subscribe', { symbols });
  },
  
  unsubscribe: (symbols) => {
    const ws = getWebSocket();
    ws.emit('unsubscribe', { symbols });
  },
}));
```

### 5.2 Vue/Pinia Store

```typescript
// store/user.ts
import { defineStore } from 'pinia';
import { getUserInfo, login, logout } from '@/api/system/user';

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userId: '',
    username: '',
    roles: [],
    permissions: [],
  }),
  
  actions: {
    async login(userInfo: { username: string; password: string }) {
      const res = await login(userInfo);
      this.token = res.accessToken;
      localStorage.setItem('token', res.accessToken);
    },
    
    async getUserInfo() {
      const res = await getUserInfo();
      this.userId = res.userId;
      this.username = res.username;
      this.roles = res.roles;
      this.permissions = res.permissions;
    },
    
    async logout() {
      await logout();
      this.token = '';
      this.roles = [];
      this.permissions = [];
      localStorage.removeItem('token');
    },
  },
});
```

### 5.3 Flutter/BLoC

```dart
// bloc/order/order_bloc.dart
class OrderBloc extends Bloc<OrderEvent, OrderState> {
  final OrderRepository repository;
  
  OrderBloc({required this.repository}) : super(OrderInitial()) {
    on<CreateOrder>(_onCreateOrder);
    on<LoadOrders>(_onLoadOrders);
  }
  
  Future<void> _onCreateOrder(
    CreateOrder event,
    Emitter<OrderState> emit,
  ) async {
    emit(OrderLoading());
    try {
      final order = await repository.createOrder(event.params);
      emit(OrderCreated(order));
    } catch (e) {
      emit(OrderError(e.toString()));
    }
  }
}
```

## 6. Environment Variables

### 6.1 React Environment Variables

```env
# .env.development
VITE_API_BASE_URL=http://localhost:8080/api
VITE_WS_URL=ws://localhost:8080/ws
VITE_APP_TITLE=Global FX(Development)

# .env.production
VITE_API_BASE_URL=https://api.globalfx.com/api
VITE_WS_URL=wss://api.globalfx.com/ws
VITE_APP_TITLE=Global FX
```

### 6.2 Vue Environment Variables

```env
# .env.development
VITE_API_BASE_URL=http://localhost:8081/api
VITE_APP_TITLE=Global FX Admin(Development)

# .env.production
VITE_API_BASE_URL=https://admin.globalfx.com/api
VITE_APP_TITLE=Global FX Admin
```

### 6.3 Flutter Environment Variables

```dart
// lib/config/env_config.dart
class EnvConfig {
  static const String apiBaseUrl = String.fromEnvironment(
    'API_BASE_URL',
    defaultValue: 'https://api.globalfx.com',
  );
  
  static const String wsUrl = String.fromEnvironment(
    'WS_URL',
    defaultValue: 'wss://api.globalfx.com/ws',
  );
}
```

## 7. Code Standards

### 7.1 TypeScript Standards

- Enable strict mode
- Interface naming: PascalCase, e.g., `IUserInfo`, `OrderParams`
- Type naming: PascalCase, e.g., `OrderStatus`
- File naming: kebab-case, e.g., `order-service.ts`
- Use `type` for simple types, use `interface` for object types

### 7.2 Component Standards

- Component files: PascalCase, e.g., `OrderForm.tsx`
- Props interface: `XxxProps`
- Event handling: `handleXxx`
- Business logic: Extract to hooks/composables

### 7.3 CSS/SCSS Standards

- Use CSS Modules to avoid style conflicts
- Variable naming: kebab-case
- Use CSS variables for colors, facilitating theme switching
- Responsive breakpoints: mobile(768px), tablet(1024px), desktop(1280px)

## 8. Performance Optimization

### 8.1 React Optimization

- Use `React.memo` to optimize component re-rendering
- Use `useMemo` and `useCallback` to cache computation results
- Use virtual scrolling for lists (react-window)
- Lazy loading for images
- Code splitting (React.lazy + Suspense)

### 8.2 Vue Optimization

- Use `v-once` to optimize static content
- Use `keep-alive` to cache components
- Router lazy loading
- Image lazy loading
- On-demand component importing

### 8.3 Flutter Optimization

- Use `const` constructors for immutable components
- Use `ListView.builder` for lists
- Use image caching (cached_network_image)
- Code compression (--release)