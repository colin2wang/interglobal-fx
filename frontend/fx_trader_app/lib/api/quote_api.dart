import 'package:fx_trader_app/api/api_client.dart';
import 'package:fx_trader_app/models/quote_model.dart';

class QuoteApi {
  final _client = ApiClient();

  Future<List<SymbolQuote>> getSymbols() async {
    final res = await _client.dio.get('/quote/symbols');
    return (res.data['data'] as List).map((e) => SymbolQuote.fromJson(e)).toList();
  }

  Future<SymbolQuote> getSymbolQuote(String symbol) async {
    final res = await _client.dio.get('/quote/symbols/$symbol');
    return SymbolQuote.fromJson(res.data['data']);
  }

  Future<List<KlineData>> getKlines(String symbol, String period, {int limit = 100}) async {
    final res = await _client.dio.get('/quote/klines/$symbol', queryParameters: {'period': period, 'limit': limit});
    return (res.data['data'] as List).map((e) => KlineData.fromJson(e)).toList();
  }
}
