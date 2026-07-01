import 'package:equatable/equatable.dart';
import 'package:fx_trader_app/models/account_model.dart';

abstract class AccountState extends Equatable {
  const AccountState();
  @override
  List<Object?> get props => [];
}

class AccountInitial extends AccountState {}
class AccountLoading extends AccountState {}
class AccountSubmitting extends AccountState {}

class AccountLoaded extends AccountState {
  final AccountInfo info;
  const AccountLoaded({required this.info});
  @override
  List<Object?> get props => [info];
}

class AccountSuccess extends AccountState {
  final String message;
  const AccountSuccess({required this.message});
  @override
  List<Object?> get props => [message];
}

class AccountError extends AccountState {
  final String message;
  const AccountError({required this.message});
  @override
  List<Object?> get props => [message];
}
