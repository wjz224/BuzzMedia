import 'package:http/http.dart';
import 'dart:convert';

// the server url prefix
const urlPrefix = 'https://thebuzzomega.herokuapp.com';


Future<int> verify(
  Future<String> id_token,
) async {
  /// Pass in variables mTitle and mContent to make a post to the database

  // Creates final url, headers, and a json containing mTitle and mContent variable and passes them through the post request
	final url = Uri.parse('$urlPrefix/verify/$id_token'); 
	final headers = {"Content-type": "application/json"};
	final json = jsonEncode({"id_token": "$id_token"});
	final response = await post(url, headers: headers, body: json);
  
  // Print statements to confirm post success
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
  // Return the response status code for testing purposes
  return response.statusCode;
}
