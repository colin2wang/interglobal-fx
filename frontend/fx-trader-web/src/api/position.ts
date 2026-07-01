import client from './client';

export interface Position {
  id: string;
  symbol: string;
  side: 'buy' | 'sell';
  volume: number;
  openPrice: number;
  currentPrice: number;
  profit: number;
  swap: number;
  commission: number;
  margin: number;
  openTime: string;
  marginType: 'fixed' | 'dynamic';
  leverage: number;
}

export const positionApi = {
  getPositions: () => client.get<any, Position[]>('/positions'),
  closePosition: (positionId: string) => client.post(`/positions/${positionId}/close`),
  closePartial: (positionId: string, volume: number) =>
    client.post(`/positions/${positionId}/close-partial`, { volume }),
  modifyPosition: (positionId: string, params: { takeProfit?: number; stopLoss?: number }) =>
    client.put(`/positions/${positionId}`, params),
};
