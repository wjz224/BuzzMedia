import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';
import 'dart:convert';
import 'pages/HomePage.dart';
import 'pages/LoginPage.dart';

import 'package:firebase_core/firebase_core.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();

  runApp(const MyApp());
}
// Man this Yash guy is a clown huh
class MyApp extends StatelessWidget {
  
  const MyApp({super.key});

  /// This widget is the root of the application.
  
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

