import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  static const platform = MethodChannel('com.example.push_notification/channel');

  Future<void> startService() async {
    try {
      await platform.invokeMethod('startService');
    } on PlatformException catch (e) {
      print("Failed to start service: ${e.message}");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text("Push Notification Service")),
        body: Center(
          child: ElevatedButton(
            onPressed: startService,
            child: const Text("Start Foreground Service"),
          ),
        ),
      ),
    );
  }
}
