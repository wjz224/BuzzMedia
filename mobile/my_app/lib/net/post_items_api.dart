import 'package:http/http.dart';
import 'dart:convert';

// the server url prefix
const urlPrefix = 'https://thebuzzomega.herokuapp.com';


Future<int> makePostRequest(
	String mTitle,
	String mContent,
  String mFilename,
  String mFile,
  String sessionID,
) async {
  /// Pass in variables mTitle and mContent to make a post to the database

  // Creates final url, headers, and a json containing mTitle and mContent variable and passes them through the post request
	final url = Uri.parse('$urlPrefix/1569641334/posts'); 
	final headers = {"Content-type": "application/json"};
	final json = jsonEncode({"mTitle": "$mTitle", "mMessage": "$mContent", "mFileName": "$mFilename", "mFile": "$mFile"});
	final response = await post(url, headers: headers, body: json);
  print(json);
  // Print statements to confirm post success
	print('Status code adding: ${response.statusCode}');
	print('Body: ${response.body}');
  
  // Return the response status code for testing purposes
  return response.statusCode;
}
