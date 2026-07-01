import 'package:equatable/equatable.dart';
import 'package:fx_trader_app/models/order_model.dart';

abstract class OrderEvent extends Equatable {
  const OrderEvent();
  @override
  List<Object?> get props => [];
}

class CreateOrder extends OrderEvent {
  final CreateOrderParams params;
  const CreateOrder(this.params);
  @override
  List<Object?> get props => [params];
}

class CancelOrder extends OrderEvent {
  final String orderId;
  const CancelOrder(this.orderId);
  @override
  List<Object?> get props => [orderId];
}

class LoadOrders extends OrderEvent {
  final String? status;
  const LoadOrders({this.status});
  @override
  List<Object?> get props => [status];
}

class LoadOrderHistory extends OrderEvent {}
