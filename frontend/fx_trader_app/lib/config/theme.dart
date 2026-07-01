import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class AppTheme {
  static const Color primaryColor = Color(0xFF1890FF);
  static const Color successColor = Color(0xFF52C41A);
  static const Color dangerColor = Color(0xFFFF4D4F);

  static ThemeData get lightTheme {
    return ThemeData(
      useMaterial3: true,
      colorSchemeSeed: primaryColor,
      brightness: Brightness.light,
      scaffoldBackgroundColor: const Color(0xFFF5F5F5),
      textTheme: GoogleFonts.interTextTheme(),
      appBarTheme: const AppBarTheme(backgroundColor: Colors.white, foregroundColor: Color(0xFF333333), elevation: 0, centerTitle: true),
      cardTheme: CardTheme(color: Colors.white, elevation: 1, shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12))),
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(backgroundColor: primaryColor, foregroundColor: Colors.white, minimumSize: const Size(double.infinity, 48), shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8))),
      ),
    );
  }

  static ThemeData get darkTheme {
    return ThemeData(useMaterial3: true, colorSchemeSeed: primaryColor, brightness: Brightness.dark, scaffoldBackgroundColor: const Color(0xFF1A1A1A));
  }
}
