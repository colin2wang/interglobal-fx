import { Input, Tabs } from 'antd';
import { useState } from 'react';
import { useQuoteStore } from '@/store/quoteStore';
import { SymbolTicker } from './SymbolTicker';

export const SymbolList = () => {
  const { quotes, watchList, selectedSymbol, setSelectedSymbol } = useQuoteStore();
  const [search, setSearch] = useState('');
  const [activeTab, setActiveTab] = useState('watchlist');

  const filterSymbols = (symbols: string[]) =>
    symbols.filter((s) => s.toLowerCase().includes(search.toLowerCase()));
  const displaySymbols =
    activeTab === 'watchlist' ? filterSymbols(watchList) : filterSymbols(Object.keys(quotes));

  return (
    <div>
      <Input.Search
        placeholder="Search symbols..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        style={{ marginBottom: 8 }}
      />
      <Tabs
        activeKey={activeTab}
        onChange={setActiveTab}
        size="small"
        items={[
          { key: 'watchlist', label: 'Watchlist' },
          { key: 'all', label: 'All' },
        ]}
      />
      <div style={{ maxHeight: 'calc(100vh - 200px)', overflow: 'auto' }}>
        {displaySymbols.map((symbol) => (
          <SymbolTicker
            key={symbol}
            quote={quotes[symbol]}
            onClick={() => setSelectedSymbol(symbol)}
            selected={selectedSymbol === symbol}
          />
        ))}
      </div>
    </div>
  );
};
