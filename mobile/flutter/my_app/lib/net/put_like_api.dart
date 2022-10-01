import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'dart:convert';
import 'package:my_app/model/item_model.dart';

const urlPrefix = 'https://thebuzzomega.herokuapp.com';

Future<void> addLike(
  	String messageId,
) async {
  
	final url = Uri.parse('$urlPrefix/messages/$messageId/3');
	final headers = {"Content-type": "application/json"};
	final response = await put(url);
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
}