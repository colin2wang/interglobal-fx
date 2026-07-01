import { create } from 'zustand';
import { getToken, setToken, removeToken } from '@/utils/storage';

interface UserInfo {
  id: string;
  username: string;
  nickname: string;
  email: string;
  phone: string;
  avatar: string;
}

interface UserState {
  token: string | null;
  userInfo: UserInfo | null;
  isLoggedIn: boolean;
  setToken: (token: string) => void;
  setUserInfo: (info: UserInfo) => void;
  logout: () => void;
}

export const useUserStore = create<UserState>((set) => ({
  token: getToken(),
  userInfo: null,
  isLoggedIn: !!getToken(),
  setToken: (token) => { setToken(token); set({ token, isLoggedIn: true }); },
  setUserInfo: (info) => set({ userInfo: info }),
  logout: () => { removeToken(); set({ token: null, userInfo: null, isLoggedIn: false }); window.location.href = '/login'; },
}));
