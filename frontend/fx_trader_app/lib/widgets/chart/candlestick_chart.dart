import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:fx_trader_app/config/theme.dart';

class CandlestickChart extends StatelessWidget {
  final String symbol;
  final double currentPrice;

  const CandlestickChart({super.key, required this.symbol, required this.currentPrice});

  @override
  Widget build(BuildContext context) {
    final spots = List.generate(30, (i) {
      final change = (i * 17 % 100 - 50) / 10000;
      return FlSpot(i.toDouble(), currentPrice + change);
    });

    final isUp = spots.last.y >= spots.first.y;

    return Padding(padding: const EdgeInsets.all(16), child: Column(
      crossAxisAlignment: CrossAxisAlignment.start, children: [
        Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Text(symbol, style: Theme.of(context).textTheme.titleLarge),
          Text(currentPrice.toStringAsFixed(5), style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: isUp ? AppTheme.successColor : AppTheme.dangerColor)),
        ]),
        const SizedBox(height: 16),
        Expanded(child: LineChart(LineChartData(
          gridData: const FlGridData(show: true),
          titlesData: const FlTitlesData(show: false),
          borderData: FlBorderData(show: false),
          lineBarsData: [LineChartBarData(spots: spots, isCurved: true,
            color: isUp ? AppTheme.successColor : AppTheme.dangerColor,
            barWidth: 2, dotData: const FlDotData(show: false),
            belowBarData: BarAreaData(show: true, color: (isUp ? AppTheme.successColor : AppTheme.dangerColor).withOpacity(0.1)))],
        ))),
      ],
    ));
  }
}
