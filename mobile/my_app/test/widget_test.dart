// This is a basic Flutter widget test.
//
// To perform an interaction with a widget in your test, use the WidgetTester
// utility in the flutter_test package. For example, you can send tap and scroll
// gestures. You can also use WidgetTester to find child widgets in the widget
// tree, read text, and verify that the values of widget properties are correct.

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_app/net/get_items_api.dart';
import 'package:my_app/net/post_items_api.dart';
import 'package:my_app/net/put_like_api.dart';
import 'package:my_app/net/put_like_api.dart';
import 'package:my_app/main.dart';
import 'package:http/http.dart' as http;
import 'package:my_app/net/verify_api.dart';


Future<void> main() async {

  //checks if the get messages function works
  test('get request messages from server',() async{
      expect(
          (await makeGetRequest()).statusCode,
          200,
        );
    });

  //checks if the post request function works
  test('make a post request',() async{
      expect(
          (await makePostRequest('Unit Test', 'Tested')),
          200,
        );
    });

  test('testing the verify route', () async{
    Future <String> test = "12345" as Future<String>;
    expect(
      (await verify(test)),
      200,
    );
  });

}

//class _MyHttpOverrides extends HttpOverrides {}