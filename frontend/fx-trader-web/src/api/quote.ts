import client from './client';

export interface SymbolQuote {
  symbol: string;
  bid: number;
  ask: number;
  spread: number;
  high24h: number;
  low24h: number;
  change24h: number;
  changePercent24h: number;
  volume24h: number;
  timestamp: number;
}

export interface KlineData {
  time: number;
  open: number;
  high: number;
  low: number;
  close: number;
  volume: number;
}

export const quoteApi = {
  getSymbols: () => client.get<any, SymbolQuote[]>('/quote/symbols'),
  getSymbolQuote: (symbol: string) => client.get<any, SymbolQuote>(`/quote/symbols/${symbol}`),
  getKlines: (symbol: string, period: string, limit?: number) =>
    client.get<any, KlineData[]>(`/quote/klines/${symbol}`, { params: { period, limit } }),
  getDepth: (symbol: string) => client.get(`/quote/depth/${symbol}`),
};
