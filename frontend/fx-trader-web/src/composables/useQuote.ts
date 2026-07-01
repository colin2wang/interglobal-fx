import { useEffect, useCallback } from 'react';
import { useWebSocket } from './useWebSocket';
import { useQuoteStore } from '@/store/quoteStore';
import type { SymbolQuote } from '@/api/quote';

export const useQuote = () => {
  const { setQuote } = useQuoteStore();
  const { emit, on } = useWebSocket({ namespace: '/quote' });

  useEffect(() => {
    const unsub = on('quote', (quote: SymbolQuote) => setQuote(quote));
    return unsub;
  }, [on, setQuote]);

  const subscribe = useCallback((symbols: string[]) => emit('subscribe', { symbols }), [emit]);
  const unsubscribe = useCallback((symbols: string[]) => emit('unsubscribe', { symbols }), [emit]);

  return { subscribe, unsubscribe };
};
