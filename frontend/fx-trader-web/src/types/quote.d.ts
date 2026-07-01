declare namespace Quote {
  interface Symbol { symbol: string; name: string; category: string; digits: number; contractSize: number; spread: number; }
  interface Tick { bid: number; ask: number; high: number; low: number; volume: number; timestamp: number; }
  interface Kline { time: number; open: number; high: number; low: number; close: number; volume: number; }
}
