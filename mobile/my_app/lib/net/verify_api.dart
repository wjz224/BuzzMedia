import 'package:http/http.dart';
import 'dart:convert';

// the server url prefix
const urlPrefix = 'https://thebuzzomega.herokuapp.com';


Future<int> verify(
  Future<String> id_tokenFuture,
) async {
  /// Pass in variables mTitle and mContent to make a post to the database
  /// 
  // Creates final url, headers, and a json containing mTitle and mContent variable and passes them through the post request
  String id_token = await id_tokenFuture;
  
	final url = Uri.parse('$urlPrefix/verify/$id_token'); 
	final headers = {"Content-type": "application/json"};
	//final json = jsonEncode({"id_token": "$id_token"});
	final response = await post(url, headers: headers);
  
  // Print statements to confirm post success
  print('$id_token');
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
  // Return the response status code for testing purposes
  return response.statusCode;
}
