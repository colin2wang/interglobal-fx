import 'package:fx_trader_app/api/account_api.dart';
import 'package:fx_trader_app/models/account_model.dart';

class AccountRepository {
  final AccountApi _api = AccountApi();
  Future<AccountInfo> getAccountInfo() => _api.getAccountInfo();
  Future<void> deposit(DepositParams params) => _api.deposit(params);
  Future<void> withdraw(WithdrawParams params) => _api.withdraw(params);
  Future<List<Transaction>> getTransactions({int page = 1}) => _api.getTransactions(page: page);
}
