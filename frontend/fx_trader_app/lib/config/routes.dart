import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:fx_trader_app/screens/login/login_screen.dart';
import 'package:fx_trader_app/screens/trading/trading_screen.dart';
import 'package:fx_trader_app/screens/trading/chart_screen.dart';
import 'package:fx_trader_app/screens/account/account_screen.dart';
import 'package:fx_trader_app/screens/account/deposit_screen.dart';
import 'package:fx_trader_app/screens/positions/positions_screen.dart';
import 'package:fx_trader_app/screens/settings/settings_screen.dart';
import 'package:fx_trader_app/config/env_config.dart';

final GlobalKey<NavigatorState> _rootNavigatorKey = GlobalKey<NavigatorState>();

final appRouter = GoRouter(
  navigatorKey: _rootNavigatorKey,
  initialLocation: '/trading',
  redirect: (context, state) {
    final token = EnvConfig.getToken();
    final isLoginRoute = state.matchedLocation == '/login';
    if (token == null && !isLoginRoute) return '/login';
    if (token != null && isLoginRoute) return '/trading';
    return null;
  },
  routes: [
    GoRoute(path: '/login', builder: (context, state) => const LoginScreen()),
    ShellRoute(
      builder: (context, state, child) => MainShell(child: child),
      routes: [
        GoRoute(path: '/trading', builder: (context, state) => const TradingScreen()),
        GoRoute(path: '/trading/chart/:symbol', builder: (context, state) => ChartScreen(symbol: state.pathParameters['symbol']!)),
        GoRoute(path: '/positions', builder: (context, state) => const PositionsScreen()),
        GoRoute(path: '/account', builder: (context, state) => const AccountScreen()),
        GoRoute(path: '/account/deposit', builder: (context, state) => const DepositScreen()),
        GoRoute(path: '/settings', builder: (context, state) => const SettingsScreen()),
      ],
    ),
  ],
);

class MainShell extends StatelessWidget {
  final Widget child;
  const MainShell({super.key, required this.child});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: child,
      bottomNavigationBar: NavigationBar(
        selectedIndex: _getCurrentIndex(context),
        onDestinationSelected: (i) => _onTap(context, i),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.candlestick_chart_outlined), selectedIcon: Icon(Icons.candlestick_chart), label: 'Trade'),
          NavigationDestination(icon: Icon(Icons.account_balance_wallet_outlined), selectedIcon: Icon(Icons.account_balance_wallet), label: 'Positions'),
          NavigationDestination(icon: Icon(Icons.account_circle_outlined), selectedIcon: Icon(Icons.account_circle), label: 'Account'),
          NavigationDestination(icon: Icon(Icons.settings_outlined), selectedIcon: Icon(Icons.settings), label: 'Settings'),
        ],
      ),
    );
  }

  int _getCurrentIndex(BuildContext context) {
    final loc = GoRouterState.of(context).matchedLocation;
    if (loc.startsWith('/trading')) return 0;
    if (loc.startsWith('/positions')) return 1;
    if (loc.startsWith('/account')) return 2;
    if (loc.startsWith('/settings')) return 3;
    return 0;
  }

  void _onTap(BuildContext context, int index) {
    switch (index) {
      case 0: context.go('/trading');
      case 1: context.go('/positions');
      case 2: context.go('/account');
      case 3: context.go('/settings');
    }
  }
}
