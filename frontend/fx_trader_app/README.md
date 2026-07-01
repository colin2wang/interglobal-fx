# fx_trader_app

Flutter mobile trading application for iOS and Android.

## Tech Stack

| Library | Version | Purpose |
|---------|---------|---------|
| Flutter | 3.x | Cross-platform framework |
| Dart | 3.x | Programming language |
| flutter_bloc | 8.x | State management (BLoC pattern) |
| go_router | 12.x | Declarative routing |
| dio | 5.x | HTTP client |
| fl_chart | 0.65.x | K-line and price charts |
| shared_preferences | 2.x | Local storage |
| intl | 0.18.x | Internationalization |

## Project Structure

```
lib/
├── api/              # API clients (Dio-based)
│   ├── api_client.dart
│   ├── quote_api.dart
│   ├── order_api.dart
│   └── account_api.dart
├── app/              # App configuration
│   ├── app.dart
│   ├── routes.dart
│   └── theme.dart
├── bloc/             # BLoC state management
│   ├── quote/        # QuoteBloc, QuoteEvent, QuoteState
│   ├── order/        # OrderBloc, OrderEvent, OrderState
│   └── account/      # AccountBloc, AccountEvent, AccountState
├── models/           # Data models
│   ├── quote_model.dart
│   ├── order_model.dart
│   ├── account_model.dart
│   └── position_model.dart
├── repositories/     # Data repositories
│   ├── quote_repository.dart
│   ├── order_repository.dart
│   └── account_repository.dart
├── screens/          # Screen pages
│   ├── trading/      # TradingScreen, ChartScreen
│   ├── positions/    # PositionsScreen
│   ├── account/      # AccountScreen, DepositScreen
│   ├── login/        # LoginScreen
│   └── settings/     # SettingsScreen
├── widgets/          # Reusable widgets
│   ├── chart/        # CandlestickChart, PriceTicker
│   ├── order/        # OrderForm, PositionCard
│   └── common/       # LoadingIndicator, EmptyState
└── main.dart         # App entry point
```

## Quick Start

```bash
flutter pub get
flutter run              # Debug mode
flutter run --release    # Release mode
flutter build apk        # Build Android APK
flutter build ios        # Build iOS
```

## Configuration

```dart
// lib/config/env_config.dart
class EnvConfig {
  static const String apiBaseUrl = String.fromEnvironment(
    'API_BASE_URL',
    defaultValue: 'https://api.globalfx.com',
  );
}
```

## Key Features

- Real-time quote streaming
- K-line charts with multiple timeframes
- One-tap trading (buy/sell)
- Position management with P&L tracking
- Account overview and fund management
- Material Design 3 UI
