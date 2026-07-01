class PositionModel {
  final String id, symbol, side, marginType;
  final double volume, openPrice, currentPrice, profit, swap, commission, margin;
  final int leverage;
  final DateTime openTime;

  PositionModel({required this.id, required this.symbol, required this.side, required this.volume,
    required this.openPrice, required this.currentPrice, required this.profit, required this.swap,
    required this.commission, required this.margin, required this.marginType, required this.leverage, required this.openTime});

  factory PositionModel.fromJson(Map<String, dynamic> json) => PositionModel(
    id: json['id'] ?? '', symbol: json['symbol'] ?? '', side: json['side'] ?? '',
    volume: (json['volume'] ?? 0).toDouble(), openPrice: (json['openPrice'] ?? 0).toDouble(),
    currentPrice: (json['currentPrice'] ?? 0).toDouble(), profit: (json['profit'] ?? 0).toDouble(),
    swap: (json['swap'] ?? 0).toDouble(), commission: (json['commission'] ?? 0).toDouble(),
    margin: (json['margin'] ?? 0).toDouble(), marginType: json['marginType'] ?? 'fixed',
    leverage: json['leverage'] ?? 100,
    openTime: DateTime.parse(json['openTime'] ?? DateTime.now().toIso8601String()),
  );

  bool get isProfit => profit >= 0;
}
