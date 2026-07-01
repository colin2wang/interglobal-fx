import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:fx_trader_app/bloc/account/account_event.dart';
import 'package:fx_trader_app/bloc/account/account_state.dart';
import 'package:fx_trader_app/repositories/account_repository.dart';

class AccountBloc extends Bloc<AccountEvent, AccountState> {
  final AccountRepository repository;

  AccountBloc({required this.repository}) : super(AccountInitial()) {
    on<LoadAccountInfo>(_onLoadAccountInfo);
    on<DepositFunds>(_onDeposit);
    on<WithdrawFunds>(_onWithdraw);
  }

  Future<void> _onLoadAccountInfo(LoadAccountInfo event, Emitter<AccountState> emit) async {
    emit(AccountLoading());
    try { final info = await repository.getAccountInfo(); emit(AccountLoaded(info: info)); }
    catch (e) { emit(AccountError(message: e.toString())); }
  }

  Future<void> _onDeposit(DepositFunds event, Emitter<AccountState> emit) async {
    emit(AccountSubmitting());
    try { await repository.deposit(event.params); emit(AccountSuccess(message: 'Deposit submitted')); }
    catch (e) { emit(AccountError(message: e.toString())); }
  }

  Future<void> _onWithdraw(WithdrawFunds event, Emitter<AccountState> emit) async {
    emit(AccountSubmitting());
    try { await repository.withdraw(event.params); emit(AccountSuccess(message: 'Withdrawal submitted')); }
    catch (e) { emit(AccountError(message: e.toString())); }
  }
}
