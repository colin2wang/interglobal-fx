import 'package:flutter/material.dart';
import 'package:fx_trader_app/models/account_model.dart';
import 'package:fx_trader_app/config/theme.dart';
import 'package:intl/intl.dart';

class PositionCard extends StatelessWidget {
  final Position position;
  final VoidCallback? onClose;
  const PositionCard({super.key, required this.position, this.onClose});

  @override
  Widget build(BuildContext context) {
    final isProfit = position.profit >= 0;
    return Card(margin: const EdgeInsets.only(bottom: 8), child: Padding(
      padding: const EdgeInsets.all(12), child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Row(children: [
            Container(padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
              decoration: BoxDecoration(color: position.side == 'buy' ? AppTheme.successColor.withOpacity(0.1) : AppTheme.dangerColor.withOpacity(0.1), borderRadius: BorderRadius.circular(4)),
              child: Text(position.side.toUpperCase(), style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold, color: position.side == 'buy' ? AppTheme.successColor : AppTheme.dangerColor))),
            const SizedBox(width: 8),
            Text(position.symbol, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
          ]),
          Text(position.volume.toString(), style: const TextStyle(color: Colors.grey)),
        ]),
        const SizedBox(height: 12),
        Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          _info('Open', position.openPrice.toStringAsFixed(5)),
          _info('Current', position.currentPrice.toStringAsFixed(5)),
          _info('P/L', '\$${position.profit.toStringAsFixed(2)}', color: isProfit ? AppTheme.successColor : AppTheme.dangerColor),
        ]),
        const SizedBox(height: 8),
        Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Text(DateFormat('MM/dd HH:mm').format(position.openTime), style: const TextStyle(fontSize: 12, color: Colors.grey)),
          TextButton(onPressed: onClose, style: TextButton.styleFrom(foregroundColor: AppTheme.dangerColor, padding: const EdgeInsets.symmetric(horizontal: 12)), child: const Text('Close')),
        ]),
      ]),
    ));
  }

  Widget _info(String label, String value, {Color? color}) => Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
    Text(label, style: const TextStyle(fontSize: 12, color: Colors.grey)),
    Text(value, style: TextStyle(fontWeight: FontWeight.bold, color: color)),
  ]);
}
