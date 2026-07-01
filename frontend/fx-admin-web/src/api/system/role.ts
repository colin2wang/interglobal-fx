import request from '../axios';

export interface Role { id: number; name: string; code: string; status: number; description: string; createdAt: string; }

export const roleApi = {
  getList: (params: any) => request.get('/system/role/list', { params }),
  getAll: () => request.get('/system/role/all'),
  create: (data: any) => request.post('/system/role', data),
  update: (id: number, data: any) => request.put(`/system/role/${id}`, data),
  delete: (id: number) => request.delete(`/system/role/${id}`),
  getMenus: (roleId: number) => request.get(`/system/role/${roleId}/menus`),
  assignMenus: (roleId: number, menuIds: number[]) => request.put(`/system/role/${roleId}/menus`, { menuIds }),
};
