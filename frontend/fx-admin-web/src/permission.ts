import router from './router';
import { useUserStore } from './store/user';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';

NProgress.configure({ showSpinner: false });

const whiteList = ['/login', '/404', '/403'];

router.beforeEach(async (to, _from, next) => {
  NProgress.start();
  document.title = `${to.meta.title || 'FX Admin'} - FX Admin Panel`;
  const userStore = useUserStore();
  const hasToken = userStore.token;

  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' });
    } else {
      if (userStore.roles.length === 0) {
        try {
          await userStore.getUserInfo();
          next({ ...to, replace: true });
        } catch {
          await userStore.resetState();
          next(`/login?redirect=${to.path}`);
        }
      } else {
        next();
      }
    }
  } else {
    if (whiteList.includes(to.path)) next();
    else next(`/login?redirect=${to.path}`);
  }
});

router.afterEach(() => { NProgress.done(); });
