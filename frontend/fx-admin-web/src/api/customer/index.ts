import request from '../axios';

export interface Customer { id: number; username: string; email: string; phone: string; status: number; kycStatus: string; createdAt: string; }

export const customerApi = {
  getList: (params: any) => request.get('/admin/customers', { params }),
  getDetail: (id: number) => request.get(`/admin/customers/${id}`),
  updateStatus: (id: number, status: number) => request.put(`/admin/customers/${id}/status`, { status }),
  reviewKyc: (id: number, data: { status: string; remark?: string }) => request.put(`/admin/customers/${id}/kyc/review`, data),
};
