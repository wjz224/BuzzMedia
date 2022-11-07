import 'package:http/http.dart';
import 'dart:convert';

Future<void> putComment(
  	String comment,
    String email,
    String sessionID,
    String commentID,

) async {
  /// Adds dislike to a post specified by the messageId variable
  
  // Makes put request with url specifying messageId and /dislikes route
	final url = Uri.parse('https://thebuzzomega.herokuapp.com/$sessionID/comments/$email/$commentID');
  final headers = {"Content-type": "application/json"};
  final json = jsonEncode({"mComment": "$comment"});
	final response = await put(url, headers: headers, body: json);

  //Print statement to confirm success of dislike
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
}