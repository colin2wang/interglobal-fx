import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:fx_trader_app/config/env_config.dart';

class SettingsScreen extends StatelessWidget {
  const SettingsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Settings')),
      body: ListView(children: [
        const SizedBox(height: 16),
        ListTile(leading: const Icon(Icons.person), title: const Text('Profile'), trailing: const Icon(Icons.chevron_right), onTap: () {}),
        const Divider(),
        ListTile(leading: const Icon(Icons.language), title: const Text('Language'), trailing: const Icon(Icons.chevron_right), onTap: () {}),
        const Divider(),
        ListTile(leading: const Icon(Icons.notifications), title: const Text('Notifications'),
          trailing: Switch(value: true, onChanged: (v) {})),
        const Divider(),
        const SizedBox(height: 32),
        Padding(padding: const EdgeInsets.symmetric(horizontal: 16), child: OutlinedButton(
          onPressed: () async { await EnvConfig.clearToken(); if (context.mounted) context.go('/login'); },
          style: OutlinedButton.styleFrom(foregroundColor: Colors.red, side: const BorderSide(color: Colors.red)),
          child: const Text('Logout'),
        )),
        const SizedBox(height: 16),
      ]),
    );
  }
}
