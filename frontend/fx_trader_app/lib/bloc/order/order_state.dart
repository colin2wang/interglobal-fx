import 'package:equatable/equatable.dart';
import 'package:fx_trader_app/models/order_model.dart';

abstract class OrderState extends Equatable {
  const OrderState();
  @override
  List<Object?> get props => [];
}

class OrderInitial extends OrderState {}
class OrderLoading extends OrderState {}
class OrderSubmitting extends OrderState {}

class OrdersLoaded extends OrderState {
  final List<Order> orders;
  const OrdersLoaded({required this.orders});
  @override
  List<Object?> get props => [orders];
}

class OrderSuccess extends OrderState {
  final String message;
  const OrderSuccess({required this.message});
  @override
  List<Object?> get props => [message];
}

class OrderError extends OrderState {
  final String message;
  const OrderError({required this.message});
  @override
  List<Object?> get props => [message];
}
