import 'package:flutter/material.dart';
import 'package:fx_trader_app/models/account_model.dart';
import 'package:fx_trader_app/widgets/order/position_card.dart';

class PositionsScreen extends StatefulWidget {
  const PositionsScreen({super.key});
  @override
  State<PositionsScreen> createState() => _PositionsScreenState();
}

class _PositionsScreenState extends State<PositionsScreen> {
  final List<Position> _positions = [
    Position(id: '1', symbol: 'EUR/USD', side: 'buy', volume: 0.1, openPrice: 1.08500, currentPrice: 1.08750,
      profit: 25.0, swap: -0.5, commission: -0.7, margin: 108.50, openTime: DateTime.now().subtract(const Duration(hours: 2))),
  ];

  @override
  Widget build(BuildContext context) {
    final totalProfit = _positions.fold(0.0, (sum, p) => sum + p.profit);
    final totalMargin = _positions.fold(0.0, (sum, p) => sum + p.margin);

    return Scaffold(
      appBar: AppBar(title: const Text('Positions')),
      body: Column(children: [
        Container(padding: const EdgeInsets.all(16), child: Row(mainAxisAlignment: MainAxisAlignment.spaceAround, children: [
          Column(children: [
            const Text('Total P/L', style: TextStyle(color: Colors.grey)),
            Text('\$${totalProfit.toStringAsFixed(2)}', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: totalProfit >= 0 ? Colors.green : Colors.red)),
          ]),
          Column(children: [
            const Text('Total Margin', style: TextStyle(color: Colors.grey)),
            Text('\$${totalMargin.toStringAsFixed(2)}', style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
          ]),
        ])),
        Expanded(child: _positions.isEmpty
          ? const Center(child: Text('No open positions'))
          : ListView.builder(padding: const EdgeInsets.symmetric(horizontal: 16),
              itemCount: _positions.length,
              itemBuilder: (context, i) => PositionCard(position: _positions[i], onClose: () => setState(() => _positions.removeAt(i)))),
        ),
      ]),
    );
  }
}
