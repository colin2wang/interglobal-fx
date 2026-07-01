import 'package:flutter/material.dart';
import 'package:fx_trader_app/models/quote_model.dart';
import 'package:fx_trader_app/config/theme.dart';

class PriceTicker extends StatelessWidget {
  final SymbolQuote quote;
  final bool isSelected;
  final VoidCallback? onTap;

  const PriceTicker({super.key, required this.quote, this.isSelected = false, this.onTap});

  @override
  Widget build(BuildContext context) {
    final isPositive = quote.changePercent24h >= 0;
    return InkWell(onTap: onTap, child: Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
      decoration: BoxDecoration(
        color: isSelected ? Theme.of(context).colorScheme.primary.withOpacity(0.1) : null,
        border: Border(bottom: BorderSide(color: Theme.of(context).dividerColor, width: 0.5)),
      ),
      child: Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
        Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(quote.symbol, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
          const SizedBox(height: 4),
          Text('Spread: ${quote.spread.toStringAsFixed(1)}', style: const TextStyle(color: Colors.grey, fontSize: 12)),
        ])),
        Column(crossAxisAlignment: CrossAxisAlignment.end, children: [
          Row(children: [
            Text(quote.bid.toStringAsFixed(5), style: const TextStyle(fontWeight: FontWeight.bold, color: AppTheme.successColor)),
            const Text(' / '),
            Text(quote.ask.toStringAsFixed(5), style: const TextStyle(fontWeight: FontWeight.bold, color: AppTheme.dangerColor)),
          ]),
          const SizedBox(height: 4),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
            decoration: BoxDecoration(
              color: isPositive ? AppTheme.successColor.withOpacity(0.1) : AppTheme.dangerColor.withOpacity(0.1),
              borderRadius: BorderRadius.circular(4),
            ),
            child: Text('${isPositive ? '+' : ''}${quote.changePercent24h.toStringAsFixed(2)}%',
              style: TextStyle(fontSize: 12, color: isPositive ? AppTheme.successColor : AppTheme.dangerColor)),
          ),
        ]),
      ]),
    ));
  }
}
