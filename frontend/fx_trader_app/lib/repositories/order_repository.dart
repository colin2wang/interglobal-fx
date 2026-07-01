import 'package:fx_trader_app/api/order_api.dart';
import 'package:fx_trader_app/models/order_model.dart';

class OrderRepository {
  final OrderApi _api = OrderApi();
  Future<Order> createOrder(CreateOrderParams params) => _api.createOrder(params);
  Future<void> cancelOrder(String orderId) => _api.cancelOrder(orderId);
  Future<List<Order>> getOrders({String? status, int page = 1}) => _api.getOrders(status: status, page: page);
  Future<List<Order>> getOrderHistory({int page = 1}) => _api.getOrderHistory(page: page);
}
