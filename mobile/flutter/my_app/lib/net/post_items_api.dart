import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'dart:convert';
import 'package:my_app/model/item_model.dart';

//the server url
const urlPrefix = 'https://thebuzzomega.herokuapp.com';

//a function where you pass in the title and content of a post
//this should add a post to the database
Future<void> makePostRequest(
	String mTitle,
	String mContent,
) async {
	final url = Uri.parse('$urlPrefix/messages');
	final headers = {"Content-type": "application/json"};
	final json = jsonEncode({"mTitle": "$mTitle", "mMessage": "$mContent"});
	final response = await post(url, headers: headers, body: json);
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
}
