import request from '../axios';

export interface Order {
  id: string;
  accountNo: string;
  symbol: string;
  side: 'buy' | 'sell';
  type: 'market' | 'limit' | 'stop';
  volume: number;
  price: number;
  filledVolume: number;
  avgPrice: number;
  status: string;
  profit: number;
  openTime: string;
  closeTime: string;
}

export const orderApi = {
  getList: (params: any) => request.get('/admin/orders', { params }),
  getDetail: (id: string) => request.get(`/admin/orders/${id}`),
  cancel: (id: string) => request.post(`/admin/orders/${id}/cancel`),
};
