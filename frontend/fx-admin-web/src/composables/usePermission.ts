import { useUserStore } from '@/store/user';

export function usePermission() {
  const userStore = useUserStore();
  const hasPermission = (p: string) => userStore.permissions.includes('*') || userStore.permissions.includes(p);
  const hasRole = (r: string) => userStore.roles.includes(r);
  return { hasPermission, hasRole };
}
