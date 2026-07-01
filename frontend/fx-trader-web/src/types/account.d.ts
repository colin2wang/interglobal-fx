declare namespace Account {
  interface Info {
    id: string; accountNo: string; balance: number; equity: number;
    margin: number; freeMargin: number; marginLevel: number;
    unrealizedPnl: number; currency: string; leverage: string;
  }
  interface Transaction {
    id: string; type: 'deposit' | 'withdrawal' | 'commission' | 'swap' | 'profit';
    amount: number; balance: number; status: 'pending' | 'completed' | 'rejected';
    description: string; createdAt: string;
  }
}
