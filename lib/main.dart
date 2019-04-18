import 'package:flutter/material.dart';
// import 'package:geolocator/geolocator.dart';
// import 'package:permission_handler/permission_handler.dart';
// import 'package:flutter_background_geolocation/flutter_background_geolocation.dart'
//     as bg;

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String _positionString = "";
  // bg.ActivityChangeEvent _event;

  @override
  void initState() {
    super.initState();
    // ////
    // // 1.  Listen to events (See docs for all 12 available events).
    // //

    // // Fired whenever a location is recorded
    // bg.BackgroundGeolocation.onLocation((bg.Location location) {
    //   // print('>>>>>>>>>>> [location] - $location');
    // });

    // // Fired whenever the plugin changes motion-state (stationary->moving and vice-versa)
    // bg.BackgroundGeolocation.onMotionChange((bg.Location location) {
    //   // print('>>>>>>>>>>> [motionchange] - $location');
    // });

    // // Fired whenever the state of location-services changes.  Always fired at boot
    // bg.BackgroundGeolocation.onProviderChange((bg.ProviderChangeEvent event) {
    //   // print('>>>>>>>>>>> [providerchange] - $event');
    // });

    // bg.BackgroundGeolocation.onActivityChange((bg.ActivityChangeEvent event) {
    //   print('>>>>>>>>>>> [onActivityChange] - $event');
    //   setState(() {
    //     _positionString += event.activity + "\n";
    //   });
    // });

    // ////
    // // 2.  Configure the plugin
    // //

    // bg.BackgroundGeolocation.ready(bg.Config(
    //         desiredAccuracy: bg.Config.DESIRED_ACCURACY_HIGH,
    //         distanceFilter: 10.0,
    //         stopOnTerminate: false,
    //         startOnBoot: true,
    //         debug: true,
    //         logLevel: bg.Config.LOG_LEVEL_VERBOSE,
    //         reset: true))
    //     .then((bg.State state) {
    //   if (!state.enabled) {
    //     ////
    //     // 3.  Start the plugin.
    //     //
    //     bg.BackgroundGeolocation.start();
    //   }
    // });
  }

  Future _incrementCounter() async {
    setState(() {
      _counter++;
      // _positionString += _event.activity + _event.confidence.toString();
      _positionString += TimeOfDay.now().toString() + "\n";
    });

    // Map<PermissionGroup, PermissionStatus> permissions =
    //     await PermissionHandler()
    //         .requestPermissions([PermissionGroup.location]);

    // Geolocator geolocator = Geolocator()..forceAndroidLocationManager = true;
    // GeolocationStatus geolocationStatus =
    //     await geolocator.checkGeolocationPermissionStatus();
    // var _position = await Geolocator()
    //     .getCurrentPosition(desiredAccuracy: LocationAccuracy.high);
    // setState(() {
    //   _positionString = _position.latitude.toString();
    //   // _positionString = geolocationStatus.toString();
    // });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: LayoutBuilder(
        builder: (BuildContext context, BoxConstraints viewportConstraints) {
          return SingleChildScrollView(
            child: ConstrainedBox(
              constraints: BoxConstraints(
                minHeight: viewportConstraints.maxHeight,
                maxWidth: viewportConstraints.maxWidth,
              ),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
                  Container(
                    // A fixed-height child.
                    color: Colors.yellow,
                    child: Text(
                      '$_counter  -> ' + ' $_positionString',
                      style: Theme.of(context).textTheme.overline,
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
