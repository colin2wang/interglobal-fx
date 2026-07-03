import { defineStore } from 'pinia';
import { userApi, type UserInfo } from '@/api/system/user';
import { getToken, setToken, removeToken } from '@/utils/auth';

interface UserState {
  token: string;
  userInfo: UserInfo | null;
  roles: string[];
  permissions: string[];
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({ token: getToken() || '', userInfo: null, roles: [], permissions: [] }),
  actions: {
    async login(username: string, password: string) {
      const res = await userApi.login({ username, password });
      this.token = res.data.token;
      setToken(res.data.token);
    },
    async getUserInfo() {
      const res = (await userApi.getInfo()) as unknown as UserInfo;
      this.userInfo = res;
      this.roles = res.roles;
      this.permissions = res.permissions;
    },
    async logout() {
      await userApi.logout();
      this.resetState();
    },
    resetState() {
      this.token = '';
      this.userInfo = null;
      this.roles = [];
      this.permissions = [];
      removeToken();
    },
  },
});
