import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:fx_trader_app/bloc/account/account_bloc.dart';
import 'package:fx_trader_app/bloc/account/account_event.dart';
import 'package:fx_trader_app/bloc/account/account_state.dart';

class AccountScreen extends StatefulWidget {
  const AccountScreen({super.key});
  @override
  State<AccountScreen> createState() => _AccountScreenState();
}

class _AccountScreenState extends State<AccountScreen> {
  @override
  void initState() {
    super.initState();
    context.read<AccountBloc>().add(LoadAccountInfo());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Account')),
      body: BlocBuilder<AccountBloc, AccountState>(
        builder: (context, state) {
          if (state is AccountLoading) return const Center(child: CircularProgressIndicator());
          if (state is AccountLoaded) {
            final info = state.info;
            return SingleChildScrollView(padding: const EdgeInsets.all(16), child: Column(
              crossAxisAlignment: CrossAxisAlignment.start, children: [
                Card(child: Padding(padding: const EdgeInsets.all(16), child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start, children: [
                    Text('Account: ${info.accountNo}', style: Theme.of(context).textTheme.titleMedium),
                    const SizedBox(height: 16),
                    _row('Balance', '\$${info.balance.toStringAsFixed(2)}'),
                    _row('Equity', '\$${info.equity.toStringAsFixed(2)}'),
                    _row('Margin', '\$${info.margin.toStringAsFixed(2)}'),
                    _row('Free Margin', '\$${info.freeMargin.toStringAsFixed(2)}'),
                    _row('Leverage', info.leverage),
                  ],
                ))),
                const SizedBox(height: 16),
                Row(children: [
                  Expanded(child: ElevatedButton(onPressed: () => context.push('/account/deposit'), child: const Text('Deposit'))),
                  const SizedBox(width: 16),
                  Expanded(child: OutlinedButton(onPressed: () {}, child: const Text('Withdraw'))),
                ]),
              ],
            ));
          }
          if (state is AccountError) return Center(child: Text(state.message));
          return const SizedBox.shrink();
        },
      ),
    );
  }

  Widget _row(String label, String value) => Padding(
    padding: const EdgeInsets.symmetric(vertical: 8),
    child: Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
      Text(label, style: const TextStyle(color: Colors.grey)),
      Text(value, style: const TextStyle(fontWeight: FontWeight.bold)),
    ]),
  );
}
