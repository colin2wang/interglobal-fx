import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:fx_trader_app/bloc/quote/quote_bloc.dart';
import 'package:fx_trader_app/bloc/quote/quote_state.dart';
import 'package:fx_trader_app/bloc/quote/quote_event.dart';
import 'package:fx_trader_app/widgets/chart/price_ticker.dart';
import 'package:fx_trader_app/widgets/order/order_form.dart';

class TradingScreen extends StatefulWidget {
  const TradingScreen({super.key});
  @override
  State<TradingScreen> createState() => _TradingScreenState();
}

class _TradingScreenState extends State<TradingScreen> {
  @override
  void initState() {
    super.initState();
    context.read<QuoteBloc>().add(LoadQuotes());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Trading'), actions: [
        IconButton(icon: const Icon(Icons.refresh), onPressed: () => context.read<QuoteBloc>().add(LoadQuotes())),
      ]),
      body: BlocBuilder<QuoteBloc, QuoteState>(
        builder: (context, state) {
          if (state is QuoteLoading) return const Center(child: CircularProgressIndicator());
          if (state is QuoteLoaded) {
            return Column(children: [
              Expanded(flex: 3, child: ListView.builder(
                itemCount: state.symbols.length,
                itemBuilder: (context, i) {
                  final symbol = state.symbols[i];
                  return PriceTicker(quote: symbol, isSelected: state.selectedSymbol?.symbol == symbol.symbol,
                    onTap: () => context.read<QuoteBloc>().add(SelectSymbol(symbol.symbol)));
                },
              )),
              Expanded(flex: 2, child: OrderForm(selectedSymbol: state.selectedSymbol)),
            ]);
          }
          if (state is QuoteError) return Center(child: Text(state.message));
          return const SizedBox.shrink();
        },
      ),
    );
  }
}
