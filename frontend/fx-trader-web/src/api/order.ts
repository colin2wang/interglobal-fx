import client from './client';

export interface CreateOrderParams {
  symbol: string;
  side: 'buy' | 'sell';
  type: 'market' | 'limit' | 'stop';
  volume: number;
  price?: number;
  stopPrice?: number;
  takeProfit?: number;
  stopLoss?: number;
}

export interface Order {
  id: string;
  symbol: string;
  side: 'buy' | 'sell';
  type: 'market' | 'limit' | 'stop';
  volume: number;
  price?: number;
  filledVolume: number;
  avgPrice: number;
  status: 'pending' | 'filled' | 'partial' | 'cancelled';
  profit?: number;
  swap?: number;
  commission?: number;
  openTime: string;
  closeTime?: string;
}

export const orderApi = {
  createOrder: (params: CreateOrderParams) => client.post<any, Order>('/orders', params),
  cancelOrder: (orderId: string) => client.post(`/orders/${orderId}/cancel`),
  getOrders: (params?: { status?: string; page?: number; pageSize?: number }) =>
    client.get<any, { list: Order[]; total: number }>('/orders', { params }),
  getOrderHistory: (params?: { page?: number; pageSize?: number }) =>
    client.get<any, { list: Order[]; total: number }>('/orders/history', { params }),
  modifyOrder: (orderId: string, params: Partial<CreateOrderParams>) =>
    client.put(`/orders/${orderId}`, params),
};
