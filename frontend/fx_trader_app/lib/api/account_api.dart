import 'package:fx_trader_app/api/api_client.dart';
import 'package:fx_trader_app/models/account_model.dart';

class AccountApi {
  final _client = ApiClient();

  Future<AccountInfo> getAccountInfo() async {
    final res = await _client.dio.get('/account/info');
    return AccountInfo.fromJson(res.data['data']);
  }

  Future<void> deposit(DepositParams params) async {
    await _client.dio.post('/account/deposit', data: params.toJson());
  }

  Future<void> withdraw(WithdrawParams params) async {
    await _client.dio.post('/account/withdraw', data: params.toJson());
  }

  Future<List<Transaction>> getTransactions({int page = 1, int pageSize = 20}) async {
    final res = await _client.dio.get('/account/transactions', queryParameters: {'page': page, 'pageSize': pageSize});
    return (res.data['data']['list'] as List).map((e) => Transaction.fromJson(e)).toList();
  }
}
