class SymbolQuote {
  final String symbol;
  final double bid, ask, spread, high24h, low24h, change24h, changePercent24h;
  final int timestamp;

  SymbolQuote({required this.symbol, required this.bid, required this.ask, required this.spread,
    required this.high24h, required this.low24h, required this.change24h, required this.changePercent24h, required this.timestamp});

  factory SymbolQuote.fromJson(Map<String, dynamic> json) => SymbolQuote(
    symbol: json['symbol'] ?? '', bid: (json['bid'] ?? 0).toDouble(), ask: (json['ask'] ?? 0).toDouble(),
    spread: (json['spread'] ?? 0).toDouble(), high24h: (json['high24h'] ?? 0).toDouble(),
    low24h: (json['low24h'] ?? 0).toDouble(), change24h: (json['change24h'] ?? 0).toDouble(),
    changePercent24h: (json['changePercent24h'] ?? 0).toDouble(), timestamp: json['timestamp'] ?? 0,
  );

  double get midPrice => (bid + ask) / 2;
}

class KlineData {
  final DateTime time;
  final double open, high, low, close, volume;

  KlineData({required this.time, required this.open, required this.high, required this.low, required this.close, required this.volume});

  factory KlineData.fromJson(Map<String, dynamic> json) => KlineData(
    time: DateTime.fromMillisecondsSinceEpoch(json['time']),
    open: (json['open'] ?? 0).toDouble(), high: (json['high'] ?? 0).toDouble(),
    low: (json['low'] ?? 0).toDouble(), close: (json['close'] ?? 0).toDouble(),
    volume: (json['volume'] ?? 0).toDouble(),
  );
}
