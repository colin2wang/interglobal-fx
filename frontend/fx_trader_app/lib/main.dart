import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:fx_trader_app/config/env_config.dart';
import 'package:fx_trader_app/config/routes.dart';
import 'package:fx_trader_app/config/theme.dart';
import 'package:fx_trader_app/bloc/quote/quote_bloc.dart';
import 'package:fx_trader_app/bloc/order/order_bloc.dart';
import 'package:fx_trader_app/bloc/account/account_bloc.dart';
import 'package:fx_trader_app/repositories/quote_repository.dart';
import 'package:fx_trader_app/repositories/order_repository.dart';
import 'package:fx_trader_app/repositories/account_repository.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await EnvConfig.init();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider(create: (_) => QuoteRepository()),
        RepositoryProvider(create: (_) => OrderRepository()),
        RepositoryProvider(create: (_) => AccountRepository()),
      ],
      child: MultiBlocProvider(
        providers: [
          BlocProvider(create: (_) => QuoteBloc(repository: QuoteRepository())),
          BlocProvider(create: (_) => OrderBloc(repository: OrderRepository())),
          BlocProvider(create: (_) => AccountBloc(repository: AccountRepository())),
        ],
        child: MaterialApp.router(
          title: 'FX Trader',
          theme: AppTheme.lightTheme,
          darkTheme: AppTheme.darkTheme,
          themeMode: ThemeMode.system,
          routerConfig: appRouter,
          debugShowCheckedModeBanner: false,
        ),
      ),
    );
  }
}
