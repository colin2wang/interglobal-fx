import 'package:flutter/material.dart';

class EmptyState extends StatelessWidget {
  final IconData icon;
  final String title;
  final String? message;
  const EmptyState({super.key, this.icon = Icons.inbox, required this.title, this.message});

  @override
  Widget build(BuildContext context) {
    return Center(child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
      Icon(icon, size: 64, color: Colors.grey[400]),
      const SizedBox(height: 16),
      Text(title, style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.grey[600])),
      if (message != null) ...[const SizedBox(height: 8), Text(message!, style: TextStyle(color: Colors.grey[500]))],
    ]));
  }
}
