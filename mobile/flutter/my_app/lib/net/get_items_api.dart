import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'dart:convert';
import 'package:my_app/model/item_model.dart';

//the server url
const urlPrefix = 'https://thebuzzomega.herokuapp.com';

Future<http.Response> makeGetRequest() {
  //final url =
  return http.get(Uri.parse('$urlPrefix/messages'));
  //   Response response = await
  // get(url);
  //   print('Status code: ${response.statusCode}');
  //   print('Headers: ${response.headers}');
  //   print('Body: ${response.body}');
}

Future<List<String>> fetchMessage() async {
  final response = await http.get(Uri.parse('$urlPrefix/messages'));

  var returnData;

  if (response.statusCode == 200) {
    var res = jsonDecode(response.body);
    var resData = res['mData'];

    print(response.body);
    print(resData);
    //return Message.fromJson(jsonDecode(response.body));
    if (resData is List) {
      returnData = (resData).map((x) => x.toString()).toList();
    } else if (resData is Map) {
      returnData = <String>[(resData as Map<String, dynamic>).toString()];
      print('map');
      print('$returnData');
    } else {
      developer
          .log('ERROR: Unexpected json response type (was not a List or Map).');
      returnData = List.empty();
      throw Exception(
          'Failed to retrieve web data (server returned ${response.statusCode})');
    }
  }

  return returnData;

//   return returnData;
  // } else {
  //   throw Exception('Failed to load album');
  // }
}

// Future<List<String>> doSomeLongRunningCalculation() async {
//   //return simpleLongRunningCalculation();  // we tried this, it worked

//   return getWebData(); 
// }

// Future<List<String>> simpleLongRunningCalculation() async {
//   await Future.delayed(Duration(seconds: 5)); // wait 5 sec
//   return ['x', 'y', 'z'];
// }


// Future<List<String>> getWebData() async {
//   developer.log('Making web request...');
//   // var url = Uri.http('www.cse.lehigh.edu', '~spear/courses.json');
//   // var url = Uri.parse('http://www.cse.lehigh.edu/~spear/courses.json'); // list of strings
//   var url = Uri.parse('https://thebuzzomega.herokuapp.com/messages');      // list of maps
//   // var url = Uri.parse('https://jsonplaceholder.typicode.com/albums/1'); // single map
//   var headers = {"Accept": "application/json"};  // <String,String>{};

//   var response = await http.get(url, headers: headers);

//   developer.log('Response status: ${response.statusCode}');
//   developer.log('Response headers: ${response.headers}');
//   developer.log('Response body: ${response.body}');

//   final List<String> returnData;
//   if (response.statusCode == 200) {
//     // If the server did return a 200 OK response, then parse the JSON.
//     var res = jsonDecode(response.body);
//     print('json decode: $res');

//     if( res is List ){
//       returnData = (res as List<dynamic>).map( (x) => x.toString() ).toList();
//     }else if( res is Map ){
//       returnData = <String>[(res as Map<String,dynamic>).toString()];
//     }else{
//       developer.log('ERROR: Unexpected json response type (was not a List or Map).');
//       returnData = List.empty();
//     }
//   }else{
//     throw Exception('Failed to retrieve web data (server returned ${response.statusCode})');
//   }

//   return returnData;
// }



// Future<List<NumberWordPair>> fetchNumberWordPairs() async {
//   final response = await http
//       .get(Uri.parse('https://thebuzzomega.herokuapp.com/messages'));

//   if (response.statusCode == 200) {
//     // If the server did return a 200 OK response, then parse the JSON.
//     final List<NumberWordPair> returnData;
//     var res = jsonDecode(response.body);
//     print('json decode: $res');

//     if( res is List ){
//       print('list statement');
//       returnData = (res as List<dynamic>).map( (x) => NumberWordPair.fromJson(x) ).toList();
//     }else if( res is Map ){
//       print('Map statement');
//       returnData = <NumberWordPair>[NumberWordPair.fromJson(res as Map<dynamic,dynamic>)];
//       print('done');
//     }else{
//       developer.log('ERROR: Unexpected json response type (was not a List or Map).');
//       returnData = List.empty();
//     }
//     return returnData;
//   } else {
//     // If the server did not return a 200 OK response,
//     // then throw an exception.
//     throw Exception('Did not receive success status code from request.');
//   }
// }