import client from './client';

export interface AccountInfo {
  id: string;
  accountNo: string;
  balance: number;
  equity: number;
  margin: number;
  freeMargin: number;
  marginLevel: number;
  unrealizedPnl: number;
  currency: string;
  leverage: string;
}

export interface Transaction {
  id: string;
  type: 'deposit' | 'withdrawal' | 'commission' | 'swap' | 'profit' | 'adjustment';
  amount: number;
  balance: number;
  status: 'pending' | 'completed' | 'rejected';
  description: string;
  createdAt: string;
}

export const accountApi = {
  getAccountInfo: () => client.get<any, AccountInfo>('/account/info'),
  getBalance: () => client.get('/account/balance'),
  deposit: (params: { amount: number; method: string; remark?: string }) =>
    client.post('/account/deposit', params),
  withdraw: (params: { amount: number; method: string; accountNo: string; remark?: string }) =>
    client.post('/account/withdraw', params),
  getTransactions: (params?: { type?: string; page?: number; pageSize?: number }) =>
    client.get<any, { list: Transaction[]; total: number }>('/account/transactions', { params }),
  getDepositMethods: () => client.get('/account/deposit-methods'),
};
