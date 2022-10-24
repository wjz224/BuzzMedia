import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/net/post_items_api.dart';
import 'package:provider/provider.dart';
import 'dart:math' as math;

class LoginWidget extends StatelessWidget {
  @override

  Widget build(BuildContext context) => Container(
    decoration: const BoxDecoration(
      image:  DecorationImage(
        image: AssetImage(
          'assets/Comb.JPG'
        ),
        fit: BoxFit.cover,
      ),
    ),
  
  
  child: Scaffold(
    backgroundColor: Colors.transparent,
    
    body: Container(
      alignment: Alignment.center,
      padding: const EdgeInsets.all(32),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          ElevatedButton(
              style: ElevatedButton.styleFrom(
                primary: Color.fromARGB(223, 224, 77, 8),
                textStyle: TextStyle(fontSize:30, fontWeight: FontWeight.bold),
              ),
              
              onPressed: () {
                //function to open up google OAuth when button is pressed
                final provider =
                    Provider.of<GoogleSignInProvider>(context, listen: false);
                provider.googleLogin();
              },
              child: CustomPaint(
                painter: HexagonPainter(Offset(100, 20),150),
                child: Text("Enter The Hive"),
                
              )),
        ],
      ),
      )
    ), 

  );
  
}

class HexagonPainter extends CustomPainter {
  static const int SIDES_OF_HEXAGON = 6;
  final double radius;
  final Offset center;
  HexagonPainter(this.center, this.radius);

  @override
  void paint(Canvas canvas, Size size) {
    Paint paint = Paint()..color = Color.fromARGB(255, 224, 76, 8);
    Path path = createHexagonPath();
    canvas.drawPath(path, paint);
  }

  Path createHexagonPath() {
    final path = Path();
    var angle = (math.pi * 2) / SIDES_OF_HEXAGON;
    Offset firstPoint = Offset(radius * math.cos(0.0), radius * math.sin(0.0));
    path.moveTo(firstPoint.dx + center.dx, firstPoint.dy + center.dy);
    for (int i = 1; i <= SIDES_OF_HEXAGON; i++) {
      double x = radius * math.cos(angle * i) + center.dx;
      double y = radius * math.sin(angle * i) + center.dy;
      path.lineTo(x, y);
    }
    path.close();
    return path;
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => false;
}
