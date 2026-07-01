import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:fx_trader_app/bloc/order/order_event.dart';
import 'package:fx_trader_app/bloc/order/order_state.dart';
import 'package:fx_trader_app/repositories/order_repository.dart';

class OrderBloc extends Bloc<OrderEvent, OrderState> {
  final OrderRepository repository;

  OrderBloc({required this.repository}) : super(OrderInitial()) {
    on<CreateOrder>(_onCreateOrder);
    on<CancelOrder>(_onCancelOrder);
    on<LoadOrders>(_onLoadOrders);
    on<LoadOrderHistory>(_onLoadOrderHistory);
  }

  Future<void> _onCreateOrder(CreateOrder event, Emitter<OrderState> emit) async {
    emit(OrderSubmitting());
    try { await repository.createOrder(event.params); emit(OrderSuccess(message: 'Order placed')); }
    catch (e) { emit(OrderError(message: e.toString())); }
  }

  Future<void> _onCancelOrder(CancelOrder event, Emitter<OrderState> emit) async {
    try { await repository.cancelOrder(event.orderId); emit(OrderSuccess(message: 'Cancelled')); }
    catch (e) { emit(OrderError(message: e.toString())); }
  }

  Future<void> _onLoadOrders(LoadOrders event, Emitter<OrderState> emit) async {
    emit(OrderLoading());
    try { final orders = await repository.getOrders(status: event.status); emit(OrdersLoaded(orders: orders)); }
    catch (e) { emit(OrderError(message: e.toString())); }
  }

  Future<void> _onLoadOrderHistory(LoadOrderHistory event, Emitter<OrderState> emit) async {
    emit(OrderLoading());
    try { final orders = await repository.getOrderHistory(); emit(OrdersLoaded(orders: orders)); }
    catch (e) { emit(OrderError(message: e.toString())); }
  }
}
