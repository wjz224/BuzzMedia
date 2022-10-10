import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'home_view.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  /// This widget is the root of the application.
  
  @override
	Widget build(BuildContext context) {
		return MaterialApp(
			title: 'Flutter Demo',
			theme: ThemeData(
			// This is the theme of your application.
			primarySwatch: Colors.yellow,
			),
      // This is the home page which displays the posts and 'The Buzz' title
			home: const MyHomePage(title: 'The Buzz'),
		);
	}
}
