import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:fx_trader_app/bloc/quote/quote_bloc.dart';
import 'package:fx_trader_app/bloc/quote/quote_state.dart';
import 'package:fx_trader_app/widgets/chart/candlestick_chart.dart';

class ChartScreen extends StatelessWidget {
  final String symbol;
  const ChartScreen({super.key, required this.symbol});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(symbol)),
      body: BlocBuilder<QuoteBloc, QuoteState>(
        builder: (context, state) {
          if (state is QuoteLoaded) {
            final quote = state.symbols.firstWhere((s) => s.symbol == symbol, orElse: () => state.symbols.first);
            return CandlestickChart(symbol: symbol, currentPrice: quote.midPrice);
          }
          return const Center(child: CircularProgressIndicator());
        },
      ),
    );
  }
}
