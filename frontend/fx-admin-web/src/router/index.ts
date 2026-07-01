import { createRouter, createWebHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';
import MainLayout from '@/layouts/index.vue';

const routes: RouteRecordRaw[] = [
  { path: '/login', name: 'Login', component: () => import('@/views/login/index.vue'), meta: { title: 'Login' } },
  { path: '/404', name: 'NotFound', component: () => import('@/views/error/404.vue'), meta: { title: '404' } },
  { path: '/403', name: 'Forbidden', component: () => import('@/views/error/403.vue'), meta: { title: '403' } },
  {
    path: '/', component: MainLayout, redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('@/views/dashboard/index.vue'), meta: { title: 'Dashboard' } },
      { path: 'order', name: 'Order', component: () => import('@/views/order/list.vue'), meta: { title: 'Orders' } },
      { path: 'order/pending', name: 'PendingOrders', component: () => import('@/views/order/pending.vue'), meta: { title: 'Pending Orders' } },
      { path: 'position', name: 'Position', component: () => import('@/views/position/list.vue'), meta: { title: 'Positions' } },
      { path: 'account', name: 'Account', component: () => import('@/views/account/list.vue'), meta: { title: 'Accounts' } },
      { path: 'account/deposit', name: 'Deposit', component: () => import('@/views/account/deposit.vue'), meta: { title: 'Deposit' } },
      { path: 'account/withdraw', name: 'Withdraw', component: () => import('@/views/account/withdraw.vue'), meta: { title: 'Withdraw' } },
      { path: 'customer', name: 'Customer', component: () => import('@/views/customer/list.vue'), meta: { title: 'Customers' } },
      { path: 'customer/:id', name: 'CustomerDetail', component: () => import('@/views/customer/detail.vue'), meta: { title: 'Customer Detail' } },
      { path: 'customer/:id/kyc', name: 'CustomerKyc', component: () => import('@/views/customer/kyc.vue'), meta: { title: 'KYC Review' } },
      { path: 'ib', name: 'IB', component: () => import('@/views/ib/list.vue'), meta: { title: 'IBs' } },
      { path: 'ib/commission', name: 'IBCommission', component: () => import('@/views/ib/commission.vue'), meta: { title: 'IB Commission' } },
      { path: 'risk', name: 'Risk', component: () => import('@/views/risk/rules.vue'), meta: { title: 'Risk Rules' } },
      { path: 'risk/events', name: 'RiskEvents', component: () => import('@/views/risk/events.vue'), meta: { title: 'Risk Events' } },
      { path: 'risk/blacklist', name: 'Blacklist', component: () => import('@/views/risk/blacklist.vue'), meta: { title: 'Blacklist' } },
      { path: 'quote/symbols', name: 'QuoteSymbols', component: () => import('@/views/quote/symbols.vue'), meta: { title: 'Quote Symbols' } },
      { path: 'report/trade', name: 'TradeReport', component: () => import('@/views/report/trade.vue'), meta: { title: 'Trade Report' } },
      { path: 'ticket', name: 'Ticket', component: () => import('@/views/ticket/list.vue'), meta: { title: 'Tickets' } },
      { path: 'system/user', name: 'SystemUser', component: () => import('@/views/system/user/list.vue'), meta: { title: 'Users' } },
      { path: 'system/user/form/:id?', name: 'UserForm', component: () => import('@/views/system/user/form.vue'), meta: { title: 'User Form' } },
      { path: 'system/role', name: 'SystemRole', component: () => import('@/views/system/role/list.vue'), meta: { title: 'Roles' } },
      { path: 'system/menu', name: 'SystemMenu', component: () => import('@/views/system/menu/list.vue'), meta: { title: 'Menus' } },
      { path: 'system/dict', name: 'SystemDict', component: () => import('@/views/system/dict/list.vue'), meta: { title: 'Dictionary' } },
      { path: 'system/tenant', name: 'SystemTenant', component: () => import('@/views/system/tenant/list.vue'), meta: { title: 'Tenants' } },
      { path: 'system/audit', name: 'SystemAudit', component: () => import('@/views/system/audit/list.vue'), meta: { title: 'Audit Log' } },
    ],
  },
  { path: '/:pathMatch(.*)*', redirect: '/404' },
];

const router = createRouter({ history: createWebHistory(), routes });
export default router;
