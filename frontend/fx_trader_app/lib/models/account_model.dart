class AccountInfo {
  final String id, accountNo, currency, leverage;
  final double balance, equity, margin, freeMargin, marginLevel, unrealizedPnl;

  AccountInfo({required this.id, required this.accountNo, required this.balance, required this.equity,
    required this.margin, required this.freeMargin, required this.marginLevel, required this.unrealizedPnl,
    required this.currency, required this.leverage});

  factory AccountInfo.fromJson(Map<String, dynamic> json) => AccountInfo(
    id: json['id'] ?? '', accountNo: json['accountNo'] ?? '',
    balance: (json['balance'] ?? 0).toDouble(), equity: (json['equity'] ?? 0).toDouble(),
    margin: (json['margin'] ?? 0).toDouble(), freeMargin: (json['freeMargin'] ?? 0).toDouble(),
    marginLevel: (json['marginLevel'] ?? 0).toDouble(), unrealizedPnl: (json['unrealizedPnl'] ?? 0).toDouble(),
    currency: json['currency'] ?? 'USD', leverage: json['leverage'] ?? '1:100',
  );
}

class Transaction {
  final String id, type, status, description;
  final double amount, balance;
  final DateTime createdAt;

  Transaction({required this.id, required this.type, required this.amount, required this.balance,
    required this.status, required this.description, required this.createdAt});

  factory Transaction.fromJson(Map<String, dynamic> json) => Transaction(
    id: json['id'] ?? '', type: json['type'] ?? '', amount: (json['amount'] ?? 0).toDouble(),
    balance: (json['balance'] ?? 0).toDouble(), status: json['status'] ?? '',
    description: json['description'] ?? '', createdAt: DateTime.parse(json['createdAt'] ?? DateTime.now().toIso8601String()),
  );
}

class Position {
  final String id, symbol, side;
  final double volume, openPrice, currentPrice, profit, swap, commission, margin;
  final DateTime openTime;

  Position({required this.id, required this.symbol, required this.side, required this.volume,
    required this.openPrice, required this.currentPrice, required this.profit, required this.swap,
    required this.commission, required this.margin, required this.openTime});

  factory Position.fromJson(Map<String, dynamic> json) => Position(
    id: json['id'] ?? '', symbol: json['symbol'] ?? '', side: json['side'] ?? '',
    volume: (json['volume'] ?? 0).toDouble(), openPrice: (json['openPrice'] ?? 0).toDouble(),
    currentPrice: (json['currentPrice'] ?? 0).toDouble(), profit: (json['profit'] ?? 0).toDouble(),
    swap: (json['swap'] ?? 0).toDouble(), commission: (json['commission'] ?? 0).toDouble(),
    margin: (json['margin'] ?? 0).toDouble(),
    openTime: DateTime.parse(json['openTime'] ?? DateTime.now().toIso8601String()),
  );

  bool get isProfit => profit >= 0;
}

class DepositParams {
  final double amount;
  final String method;
  final String? remark;
  DepositParams({required this.amount, required this.method, this.remark});
  Map<String, dynamic> toJson() => {'amount': amount, 'method': method, if (remark != null) 'remark': remark};
}

class WithdrawParams {
  final double amount;
  final String method, accountNo;
  final String? remark;
  WithdrawParams({required this.amount, required this.method, required this.accountNo, this.remark});
  Map<String, dynamic> toJson() => {'amount': amount, 'method': method, 'accountNo': accountNo, if (remark != null) 'remark': remark};
}
