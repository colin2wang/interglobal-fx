enum OrderSide { buy, sell }
enum OrderType { market, limit, stop }
enum OrderStatus { pending, filled, partial, cancelled }

class Order {
  final String id, symbol;
  final OrderSide side;
  final OrderType type;
  final double volume, avgPrice, filledVolume;
  final double? price, profit;
  final OrderStatus status;
  final DateTime openTime;
  final DateTime? closeTime;

  Order({required this.id, required this.symbol, required this.side, required this.type,
    required this.volume, this.price, required this.filledVolume, required this.avgPrice,
    required this.status, this.profit, required this.openTime, this.closeTime});

  factory Order.fromJson(Map<String, dynamic> json) => Order(
    id: json['id'] ?? '', symbol: json['symbol'] ?? '',
    side: OrderSide.values.firstWhere((e) => e.name == json['side'], orElse: () => OrderSide.buy),
    type: OrderType.values.firstWhere((e) => e.name == json['type'], orElse: () => OrderType.market),
    volume: (json['volume'] ?? 0).toDouble(), price: json['price']?.toDouble(),
    filledVolume: (json['filledVolume'] ?? 0).toDouble(), avgPrice: (json['avgPrice'] ?? 0).toDouble(),
    status: OrderStatus.values.firstWhere((e) => e.name == json['status'], orElse: () => OrderStatus.pending),
    profit: json['profit']?.toDouble(),
    openTime: DateTime.parse(json['openTime'] ?? DateTime.now().toIso8601String()),
    closeTime: json['closeTime'] != null ? DateTime.parse(json['closeTime']) : null,
  );
}

class CreateOrderParams {
  final String symbol;
  final OrderSide side;
  final OrderType type;
  final double volume;
  final double? price, stopPrice, takeProfit, stopLoss;

  CreateOrderParams({required this.symbol, required this.side, required this.type, required this.volume,
    this.price, this.stopPrice, this.takeProfit, this.stopLoss});

  Map<String, dynamic> toJson() => {
    'symbol': symbol, 'side': side.name, 'type': type.name, 'volume': volume,
    if (price != null) 'price': price, if (stopPrice != null) 'stopPrice': stopPrice,
    if (takeProfit != null) 'takeProfit': takeProfit, if (stopLoss != null) 'stopLoss': stopLoss,
  };
}
