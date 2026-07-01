import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:fx_trader_app/bloc/order/order_bloc.dart';
import 'package:fx_trader_app/bloc/order/order_event.dart';
import 'package:fx_trader_app/bloc/order/order_state.dart';
import 'package:fx_trader_app/models/order_model.dart';
import 'package:fx_trader_app/models/quote_model.dart';
import 'package:fx_trader_app/config/theme.dart';

class OrderForm extends StatefulWidget {
  final SymbolQuote? selectedSymbol;
  const OrderForm({super.key, this.selectedSymbol});
  @override
  State<OrderForm> createState() => _OrderFormState();
}

class _OrderFormState extends State<OrderForm> {
  OrderSide _side = OrderSide.buy;
  OrderType _type = OrderType.market;
  final _volumeController = TextEditingController(text: '0.01');
  final _priceController = TextEditingController();
  final _tpController = TextEditingController();
  final _slController = TextEditingController();

  @override
  void dispose() { _volumeController.dispose(); _priceController.dispose(); _tpController.dispose(); _slController.dispose(); super.dispose(); }

  void _placeOrder() {
    if (widget.selectedSymbol == null) return;
    context.read<OrderBloc>().add(CreateOrder(CreateOrderParams(
      symbol: widget.selectedSymbol!.symbol, side: _side, type: _type,
      volume: double.parse(_volumeController.text),
      price: _type != OrderType.market ? double.tryParse(_priceController.text) : null,
      takeProfit: double.tryParse(_tpController.text), stopLoss: double.tryParse(_slController.text),
    )));
  }

  @override
  Widget build(BuildContext context) {
    return BlocListener<OrderBloc, OrderState>(
      listener: (context, state) {
        if (state is OrderSuccess) { ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(state.message))); _volumeController.text = '0.01'; _priceController.clear(); _tpController.clear(); _slController.clear(); }
        else if (state is OrderError) { ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(state.message), backgroundColor: Colors.red)); }
      },
      child: Container(padding: const EdgeInsets.all(16), child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Row(children: [
          Expanded(child: ElevatedButton(onPressed: () => setState(() => _side = OrderSide.buy),
            style: ElevatedButton.styleFrom(backgroundColor: _side == OrderSide.buy ? AppTheme.successColor : Colors.grey, minimumSize: const Size(0, 48)),
            child: Text('BUY ${widget.selectedSymbol?.bid.toStringAsFixed(5) ?? '--'}', style: const TextStyle(color: Colors.white)))),
          const SizedBox(width: 8),
          Expanded(child: ElevatedButton(onPressed: () => setState(() => _side = OrderSide.sell),
            style: ElevatedButton.styleFrom(backgroundColor: _side == OrderSide.sell ? AppTheme.dangerColor : Colors.grey, minimumSize: const Size(0, 48)),
            child: Text('SELL ${widget.selectedSymbol?.ask.toStringAsFixed(5) ?? '--'}', style: const TextStyle(color: Colors.white)))),
        ]),
        const SizedBox(height: 16),
        DropdownButtonFormField<OrderType>(value: _type, decoration: const InputDecoration(labelText: 'Order Type', border: OutlineInputBorder(), isDense: true),
          items: const [DropdownMenuItem(value: OrderType.market, child: Text('Market')), DropdownMenuItem(value: OrderType.limit, child: Text('Limit')), DropdownMenuItem(value: OrderType.stop, child: Text('Stop'))],
          onChanged: (v) => setState(() => _type = v!)),
        const SizedBox(height: 12),
        TextField(controller: _volumeController, keyboardType: TextInputType.number, decoration: const InputDecoration(labelText: 'Volume (Lots)', border: OutlineInputBorder(), isDense: true)),
        if (_type != OrderType.market) ...[const SizedBox(height: 12),
          TextField(controller: _priceController, keyboardType: TextInputType.number, decoration: const InputDecoration(labelText: 'Price', border: OutlineInputBorder(), isDense: true))],
        const SizedBox(height: 12),
        Row(children: [
          Expanded(child: TextField(controller: _tpController, keyboardType: TextInputType.number, decoration: const InputDecoration(labelText: 'Take Profit', border: OutlineInputBorder(), isDense: true))),
          const SizedBox(width: 8),
          Expanded(child: TextField(controller: _slController, keyboardType: TextInputType.number, decoration: const InputDecoration(labelText: 'Stop Loss', border: OutlineInputBorder(), isDense: true))),
        ]),
        const SizedBox(height: 16),
        BlocBuilder<OrderBloc, OrderState>(builder: (context, state) {
          return SizedBox(width: double.infinity, child: ElevatedButton(
            onPressed: state is OrderSubmitting ? null : _placeOrder,
            style: ElevatedButton.styleFrom(backgroundColor: _side == OrderSide.buy ? AppTheme.successColor : AppTheme.dangerColor, minimumSize: const Size(0, 48)),
            child: state is OrderSubmitting ? const SizedBox(height: 20, width: 20, child: CircularProgressIndicator(strokeWidth: 2, color: Colors.white))
              : Text('Place ${_side == OrderSide.buy ? 'Buy' : 'Sell'} Order', style: const TextStyle(color: Colors.white, fontSize: 16)),
          ));
        }),
      ])),
    );
  }
}
