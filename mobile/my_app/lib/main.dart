import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';
import 'dart:convert';
import 'pages/HomePage.dart';
import 'pages/LoginPage.dart';
import 'dart:io';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:firebase_core/firebase_core.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Hive.initFlutter();
  await Firebase.initializeApp();

  runApp(MyApp());
}
// Man this Yash guy is a clown huh
class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _MyAppState();
  }
}

class _MyAppState extends State<MyApp>{
  /// This widget is the root of the application
  /// 
  var path = Directory.current.path;
  
  @override
	Widget build(BuildContext context) => ChangeNotifierProvider(
    create: (context) =>  GoogleSignInProvider(),
      child: MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "The Buzz",
      theme: ThemeData.from (colorScheme: ColorScheme.fromSwatch(primarySwatch: Colors.deepOrange)),
      home: LoginPage(),
    ),
		);
}

