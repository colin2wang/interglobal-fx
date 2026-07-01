import 'package:fx_trader_app/api/api_client.dart';
import 'package:fx_trader_app/models/order_model.dart';

class OrderApi {
  final _client = ApiClient();

  Future<Order> createOrder(CreateOrderParams params) async {
    final res = await _client.dio.post('/orders', data: params.toJson());
    return Order.fromJson(res.data['data']);
  }

  Future<void> cancelOrder(String orderId) async {
    await _client.dio.post('/orders/$orderId/cancel');
  }

  Future<List<Order>> getOrders({String? status, int page = 1, int pageSize = 20}) async {
    final res = await _client.dio.get('/orders', queryParameters: {if (status != null) 'status': status, 'page': page, 'pageSize': pageSize});
    return (res.data['data']['list'] as List).map((e) => Order.fromJson(e)).toList();
  }

  Future<List<Order>> getOrderHistory({int page = 1, int pageSize = 20}) async {
    final res = await _client.dio.get('/orders/history', queryParameters: {'page': page, 'pageSize': pageSize});
    return (res.data['data']['list'] as List).map((e) => Order.fromJson(e)).toList();
  }
}
