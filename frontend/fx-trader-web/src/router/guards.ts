import { useUserStore } from '@/store/userStore';

export const authGuard = () => {
  const { isLoggedIn } = useUserStore.getState();
  return isLoggedIn ? null : '/login';
};

export const guestGuard = () => {
  const { isLoggedIn } = useUserStore.getState();
  return isLoggedIn ? '/trading' : null;
};
