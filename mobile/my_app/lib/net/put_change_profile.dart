import 'package:http/http.dart';
import 'dart:convert';

Future<void> putChangeProfile(
  	String gender,
    String sexualOrientation,
    String bio,
    String email,
    String sessionID,

) async {
  /// Adds dislike to a post specified by the messageId variable
  
  // Makes put request with url specifying messageId and /dislikes route
	final url = Uri.parse('https://thebuzzomega.herokuapp.com/1569641334/users/$email');
  final headers = {"Content-type": "application/json"};
  final json = jsonEncode({"mGender": "$gender", "mSex": "$sexualOrientation", "mNote": "$bio"});
	final response = await put(url, headers: headers, body: json);

  //Print statement to confirm success of dislike
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
}