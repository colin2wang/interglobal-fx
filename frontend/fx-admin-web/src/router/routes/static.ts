import type { RouteRecordRaw } from 'vue-router';
export const staticRoutes: RouteRecordRaw[] = [
  { path: '/login', name: 'Login', component: () => import('@/views/login/index.vue') },
];
