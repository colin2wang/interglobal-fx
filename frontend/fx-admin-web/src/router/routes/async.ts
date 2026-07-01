import type { RouteRecordRaw } from 'vue-router';
export const asyncRoutes: RouteRecordRaw[] = [
  { path: 'dashboard', name: 'Dashboard', component: () => import('@/views/dashboard/index.vue') },
];
