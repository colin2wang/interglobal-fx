import type { App, Directive } from 'vue';
import { useUserStore } from '@/store/user';

const permissionDirective: Directive = {
  mounted(el, binding) {
    const userStore = useUserStore();
    if (binding.value && Array.isArray(binding.value)) {
      const has = userStore.permissions.includes('*') || binding.value.some((p: string) => userStore.permissions.includes(p));
      if (!has) el.parentNode?.removeChild(el);
    }
  },
};

export const setupPermissionDirective = (app: App) => { app.directive('permission', permissionDirective); };
