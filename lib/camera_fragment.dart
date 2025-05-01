import 'package:flutter/material.dart';
import 'package:flutter_vlc_player/flutter_vlc_player.dart';

class IpCameraStreamScreen extends StatefulWidget {
  @override
  _IpCameraStreamScreenState createState() => _IpCameraStreamScreenState();
}

class _IpCameraStreamScreenState extends State<IpCameraStreamScreen> {
  late VlcPlayerController _vlcController;

  @override
  void initState() {
    super.initState();
    _vlcController = VlcPlayerController.network(
      'rtsp://YOUR_CAMERA_IP:554/stream',
      autoPlay: true,
      options: VlcPlayerOptions(),
    );
  }

  @override
  void dispose() {
    _vlcController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("IP Camera Stream")),
      body: VlcPlayer(
        controller: _vlcController,
        aspectRatio: 16 / 9,
        placeholder: Center(child: CircularProgressIndicator()),
      ),
    );
  }
}
