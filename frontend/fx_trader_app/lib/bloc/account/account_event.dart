import 'package:equatable/equatable.dart';
import 'package:fx_trader_app/models/account_model.dart';

abstract class AccountEvent extends Equatable {
  const AccountEvent();
  @override
  List<Object?> get props => [];
}

class LoadAccountInfo extends AccountEvent {}

class DepositFunds extends AccountEvent {
  final DepositParams params;
  const DepositFunds(this.params);
  @override
  List<Object?> get props => [params];
}

class WithdrawFunds extends AccountEvent {
  final WithdrawParams params;
  const WithdrawFunds(this.params);
  @override
  List<Object?> get props => [params];
}
