# fx-admin-web

Vue 3 admin panel for operations, compliance, and management staff.

## Tech Stack

| Library | Version | Purpose |
|---------|---------|---------|
| Vue | 3.x | UI framework |
| TypeScript | 5.x | Type safety |
| Vite | 5.x | Build tool |
| Pinia | 2.x | State management |
| Element Plus | 2.x | UI components |
| ECharts | 5.x | Data visualization |
| Vue Router | 4.x | Routing |
| Axios | 1.x | HTTP client |
| vue-i18n | 9.x | Internationalization |

## Project Structure

```
src/
├── api/              # API modules (system, order, customer, risk)
├── assets/           # Images, SCSS styles
├── components/       # Reusable components
│   ├── common/       # Pagination, ConfirmDialog, StatusTag
│   ├── form/         # SearchForm, DictSelect, DateRangePicker
│   └── table/        # DataTable
├── composables/      # Vue composables (useTable, useDialog, usePermission)
├── directives/       # Custom directives (permission, loading)
├── layouts/          # Sidebar, Header, TagsView, AppMain
├── router/           # Route definitions (static + async)
├── store/            # Pinia stores (user, permission, settings)
├── types/            # TypeScript type definitions
├── utils/            # Utility functions
└── views/            # Page components
    ├── dashboard/    # Dashboard
    ├── order/        # Order management
    ├── position/     # Position management
    ├── account/      # Account management
    ├── customer/     # Customer management
    ├── ib/           # IB partner management
    ├── risk/         # Risk management
    ├── quote/        # Symbol management
    ├── report/       # Reports
    ├── compliance/   # Compliance
    ├── ticket/       # Support tickets
    ├── system/       # Users, roles, menus, dict, tenants
    └── error/        # 404, 403 pages
```

## Quick Start

```bash
npm install
npm run dev       # http://localhost:3100
npm run build     # Production build
npm run lint      # ESLint check
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `VITE_API_BASE_URL` | Backend API URL | `http://localhost:8089` |
| `VITE_APP_TITLE` | Browser title | `GlobalFX Admin` |

## Key Features

- RBAC permission control with dynamic menu rendering
- Data tables with search, pagination, export
- Dictionary-driven dropdowns and status tags
- Responsive sidebar with multi-level menus
- Dashboard with real-time operational metrics
