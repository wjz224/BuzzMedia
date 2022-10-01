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

}

