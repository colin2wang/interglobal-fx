declare namespace Order {
  type Side = 'buy' | 'sell';
  type Type = 'market' | 'limit' | 'stop';
  type Status = 'pending' | 'filled' | 'partial' | 'cancelled';
  interface CreateParams {
    symbol: string; side: Side; type: Type; volume: number;
    price?: number; stopPrice?: number; takeProfit?: number; stopLoss?: number;
  }
  interface Record {
    id: string; symbol: string; side: Side; type: Type; volume: number;
    price?: number; filledVolume: number; avgPrice: number; status: Status;
    profit?: number; swap?: number; commission?: number; openTime: string; closeTime?: string;
  }
}
