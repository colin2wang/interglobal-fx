import { lazy } from 'react';
import type { RouteObject } from 'react-router-dom';
import { MainLayout } from '@/layouts/MainLayout';

const LoginPage = lazy(() =>
  import('@/pages/login/LoginPage').then((m) => ({ default: m.LoginPage })),
);
const RegisterPage = lazy(() =>
  import('@/pages/login/RegisterPage').then((m) => ({ default: m.RegisterPage })),
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
const HistoryPage = lazy(() =>
  import('@/pages/account/HistoryPage').then((m) => ({ default: m.HistoryPage })),
);
const ProfilePage = lazy(() =>
  import('@/pages/profile/ProfilePage').then((m) => ({ default: m.ProfilePage })),
);
const KycPage = lazy(() =>
  import('@/pages/profile/KycPage').then((m) => ({ default: m.KycPage })),
);
const MessagesPage = lazy(() =>
  import('@/pages/messages/MessagesPage').then((m) => ({ default: m.MessagesPage })),
);

export const routes: RouteObject[] = [
  { path: '/login', element: <LoginPage /> },
  { path: '/register', element: <RegisterPage /> },
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
      { path: 'account/history', element: <HistoryPage /> },
      { path: 'profile', element: <ProfilePage /> },
      { path: 'profile/kyc', element: <KycPage /> },
      { path: 'messages', element: <MessagesPage /> },
    ],
  },
];
