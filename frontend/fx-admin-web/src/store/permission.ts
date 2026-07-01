import { defineStore } from 'pinia';
import type { RouteRecordRaw } from 'vue-router';

interface PermissionState { routes: RouteRecordRaw[]; }

export const usePermissionStore = defineStore('permission', {
  state: (): PermissionState => ({ routes: [] }),
  actions: { setRoutes(routes: RouteRecordRaw[]) { this.routes = routes; } },
});
