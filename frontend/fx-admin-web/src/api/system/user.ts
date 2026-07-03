import request from '../axios';

export interface UserInfo {
  id: number;
  username: string;
  nickname: string;
  avatar: string;
  email: string;
  phone: string;
  status: number;
  roles: string[];
  permissions: string[];
}
export interface LoginForm {
  username: string;
  password: string;
}

export const userApi = {
  login: (data: LoginForm) => request.post('/auth/login', data),
  logout: () => request.post('/auth/logout'),
  getInfo: () => request.get<any, UserInfo>('/system/user/info'),
  getList: (params: any) => request.get('/system/user/list', { params }),
  create: (data: any) => request.post('/system/user', data),
  update: (id: number, data: any) => request.put(`/system/user/${id}`, data),
  delete: (id: number) => request.delete(`/system/user/${id}`),
  resetPassword: (id: number) => request.put(`/system/user/${id}/reset-password`),
};
