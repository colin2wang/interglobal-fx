import request from '../axios';

export interface RiskRule { id: number; name: string; type: string; condition: string; action: string; status: number; }

export const riskApi = {
  getRules: (params: any) => request.get('/admin/risk/rules', { params }),
  createRule: (data: any) => request.post('/admin/risk/rules', data),
  updateRule: (id: number, data: any) => request.put(`/admin/risk/rules/${id}`, data),
  deleteRule: (id: number) => request.delete(`/admin/risk/rules/${id}`),
  getEvents: (params: any) => request.get('/admin/risk/events', { params }),
  getBlacklist: (params: any) => request.get('/admin/risk/blacklist', { params }),
  addToBlacklist: (data: any) => request.post('/admin/risk/blacklist', data),
  removeFromBlacklist: (id: number) => request.delete(`/admin/risk/blacklist/${id}`),
};
