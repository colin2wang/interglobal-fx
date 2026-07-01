import 'package:fx_trader_app/api/quote_api.dart';
import 'package:fx_trader_app/models/quote_model.dart';

class QuoteRepository {
  final QuoteApi _api = QuoteApi();
  Future<List<SymbolQuote>> getSymbols() => _api.getSymbols();
  Future<SymbolQuote> getSymbolQuote(String symbol) => _api.getSymbolQuote(symbol);
  Future<List<KlineData>> getKlines(String symbol, String period, {int limit = 100}) => _api.getKlines(symbol, period, limit: limit);
}
