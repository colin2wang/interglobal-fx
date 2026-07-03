import { lazy } from 'react';
import type { RouteObject } from 'react-router-dom';
import { MainLayout } from '@/layouts/MainLayout';

const LoginPage = lazy(() =>
  import('@/pages/login/LoginPage').then((m) => ({ default: m.LoginPage })),
);
const TradingPage = lazy(() =>
  import('@/pages/trading/TradingPage').then((m) => ({ default: m.TradingPage })),
);
const PositionsPage = lazy(() =>
  import('@/pages/positions/PositionsPage').then((m) => ({ default: m.PositionsPage })),
);
const OrdersPage = lazy(() =>
  import('@/pages/orders/OrdersPage').then((m) => ({ default: m.OrdersPage })),
);
const AccountPage = lazy(() =>
  import('@/pages/account/AccountPage').then((m) => ({ default: m.AccountPage })),
);
const DepositPage = lazy(() =>
  import('@/pages/account/DepositPage').then((m) => ({ default: m.DepositPage })),
);
const WithdrawPage = lazy(() =>
  import('@/pages/account/WithdrawPage').then((m) => ({ default: m.WithdrawPage })),
);
const ProfilePage = lazy(() =>
  import('@/pages/profile/ProfilePage').then((m) => ({ default: m.ProfilePage })),
);

export const routes: RouteObject[] = [
  { path: '/login', element: <LoginPage /> },
  {
    path: '/',
    element: <MainLayout />,
    children: [
      { index: true, element: <TradingPage /> },
      { path: 'trading', element: <TradingPage /> },
      { path: 'positions', element: <PositionsPage /> },
      { path: 'orders', element: <OrdersPage /> },
      { path: 'account', element: <AccountPage /> },
      { path: 'account/deposit', element: <DepositPage /> },
      { path: 'account/withdraw', element: <WithdrawPage /> },
      { path: 'profile', element: <ProfilePage /> },
    ],
  },
];
