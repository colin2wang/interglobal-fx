import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:fx_trader_app/bloc/account/account_bloc.dart';
import 'package:fx_trader_app/bloc/account/account_event.dart';
import 'package:fx_trader_app/bloc/account/account_state.dart';
import 'package:fx_trader_app/models/account_model.dart';

class DepositScreen extends StatefulWidget {
  const DepositScreen({super.key});
  @override
  State<DepositScreen> createState() => _DepositScreenState();
}

class _DepositScreenState extends State<DepositScreen> {
  final _formKey = GlobalKey<FormState>();
  final _amountController = TextEditingController();
  String _method = 'bank';
  String? _remark;

  @override
  void dispose() { _amountController.dispose(); super.dispose(); }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Deposit')),
      body: BlocListener<AccountBloc, AccountState>(
        listener: (context, state) {
          if (state is AccountSuccess) { ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(state.message))); context.pop(); }
          else if (state is AccountError) { ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(state.message), backgroundColor: Colors.red)); }
        },
        child: SingleChildScrollView(padding: const EdgeInsets.all(16), child: Form(
          key: _formKey, child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            TextFormField(controller: _amountController, keyboardType: TextInputType.number,
              decoration: const InputDecoration labelText: 'Amount', prefixText: '\$ ', border: OutlineInputBorder()),
            const SizedBox(height: 16),
            DropdownButtonFormField<String>(value: _method,
              decoration: const InputDecoration labelText: 'Method', border: OutlineInputBorder(),
              items: const [DropdownMenuItem(value: 'bank', child: Text('Bank Transfer')), DropdownMenuItem(value: 'crypto', child: Text('Cryptocurrency'))],
              onChanged: (v) => setState(() => _method = v!)),
            const SizedBox(height: 16),
            TextFormField(decoration: const InputDecoration labelText: 'Remark', border: OutlineInputBorder()),
            const SizedBox(height: 24),
            BlocBuilder<AccountBloc, AccountState>(builder: (context, state) {
              return SizedBox(width: double.infinity, child: ElevatedButton(
                onPressed: state is AccountSubmitting ? null : () {
                  if (_formKey.currentState!.validate()) {
                    context.read<AccountBloc>().add(DepositFunds(DepositParams(amount: double.parse(_amountController.text), method: _method, remark: _remark)));
                  }
                }, child: state is AccountSubmitting ? const CircularProgressIndicator() : const Text('Submit Deposit'),
              ));
            }),
          ]),
        )),
      ),
    );
  }
}
