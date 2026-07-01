import 'package:shared_preferences/shared_preferences.dart';

class EnvConfig {
  static late SharedPreferences _prefs;
  static String apiBaseUrl = 'http://localhost:8080/api';

  static Future<void> init() async {
    _prefs = await SharedPreferences.getInstance();
  }

  static String? getToken() => _prefs.getString('token');
  static Future<void> setToken(String token) => _prefs.setString('token', token);
  static Future<void> clearToken() => _prefs.remove('token');
}
