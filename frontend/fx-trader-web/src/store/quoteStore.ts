import { create } from 'zustand';
import type { SymbolQuote } from '@/api/quote';

interface QuoteState {
  quotes: Record<string, SymbolQuote>;
  selectedSymbol: string;
  watchList: string[];
  setQuote: (quote: SymbolQuote) => void;
  setSelectedSymbol: (symbol: string) => void;
  addToWatchList: (symbol: string) => void;
  removeFromWatchList: (symbol: string) => void;
}

export const useQuoteStore = create<QuoteState>((set) => ({
  quotes: {},
  selectedSymbol: 'EUR/USD',
  watchList: ['EUR/USD', 'GBP/USD', 'USD/JPY', 'XAU/USD', 'BTC/USD'],
  setQuote: (quote) => set((state) => ({ quotes: { ...state.quotes, [quote.symbol]: quote } })),
  setSelectedSymbol: (symbol) => set({ selectedSymbol: symbol }),
  addToWatchList: (symbol) => set((state) => ({ watchList: [...state.watchList, symbol] })),
  removeFromWatchList: (symbol) =>
    set((state) => ({ watchList: state.watchList.filter((s) => s !== symbol) })),
}));
