import 'package:http/http.dart';
import 'dart:convert';

Future<void> postComment(
  	String comment,
    String postID,
    String sessionID,

) async {
  /// Adds dislike to a post specified by the messageId variable
  
  // Makes put request with url specifying messageId and /dislikes route
	final url = Uri.parse('https://thebuzzomega.herokuapp.com/$sessionID/comments/$postID');
  final headers = {"Content-type": "application/json"};
  final json = jsonEncode({"mMessage": "$comment"});
	final response = await post(url, headers: headers, body: json);

  //Print statement to confirm success of dislike
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
}