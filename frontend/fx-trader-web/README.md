# fx-trader-web

React-based trading terminal for retail and professional traders.

## Tech Stack

| Library | Version | Purpose |
|---------|---------|---------|
| React | 18.x | UI framework |
| TypeScript | 5.x | Type safety |
| Vite | 5.x | Build tool |
| Zustand | 4.x | State management |
| Ant Design | 5.x | UI components |
| ECharts | 5.x | Charts (K-line, depth) |
| socket.io-client | 4.x | Real-time quotes |
| React Router | 6.x | Routing |
| Axios | 1.x | HTTP client |
| i18next | 23.x | Internationalization |

## Project Structure

```
src/
├── api/              # API client and endpoints
├── assets/           # Images, SCSS styles
├── components/       # Reusable components
│   ├── chart/        # CandlestickChart, DepthChart
│   ├── quote/        # SymbolTicker, SymbolList
│   ├── trade/        # OrderForm, QuickOrder
│   └── account/      # AccountBalance, PositionList
├── composables/      # React hooks (useWebSocket, useOrder, etc.)
├── layouts/          # MainLayout, Header, Sidebar
├── pages/            # Page components
│   ├── login/        # Login page
│   ├── trading/      # Trading terminal
│   ├── positions/    # Position management
│   ├── orders/       # Order history
│   └── account/      # Account overview, deposit, withdraw
├── router/           # Route definitions and guards
├── store/            # Zustand stores
├── types/            # TypeScript type definitions
├── utils/            # Utility functions
└── i18n/             # Translations (zh-CN, en-US)
```

## Quick Start

```bash
npm install
npm run dev       # http://localhost:3000
npm run build     # Production build
npm run lint      # ESLint check
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `VITE_API_BASE_URL` | Backend API URL | `http://localhost:8080` |
| `VITE_WS_URL` | WebSocket URL | `ws://localhost:8082` |
| `VITE_APP_TITLE` | Browser title | `GlobalFX Trading` |

## Key Features

- Real-time quote streaming with <50ms latency
- K-line charts with multiple timeframes (1m–1d)
- One-click trading (market orders)
- Position management with P&L tracking
- Multi-language support (Chinese/English)
