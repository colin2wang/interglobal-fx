import type { App } from 'vue';
import { ElLoading } from 'element-plus';

const loadingDirective = {
  mounted(el: any, binding: any) {
    el.instance = ElLoading.service({
      lock: true,
      text: 'Loading...',
      background: 'rgba(0,0,0,0.7)',
    });
    if (!binding.value) el.instance.close();
  },
  updated(el: any, binding: any) {
    if (binding.value) {
      el.instance?.open();
    } else {
      el.instance?.close();
    }
  },
  unmounted(el: any) {
    el.instance?.close();
  },
};

export const setupLoadingDirective = (app: App) => {
  app.directive('loading', loadingDirective);
};
