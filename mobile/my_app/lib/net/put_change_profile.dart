import 'package:http/http.dart';


Future<void> putChangeProfile(
  	String gender,
    String sexualOrientation,
    String bio,
    String id,
    String sessionKey

) async {
  /// Adds dislike to a post specified by the messageId variable
  
  // Makes put request with url specifying messageId and /dislikes route
	final url = Uri.parse('https://thebuzzomega.herokuapp.com/users/$id/$sessionKey');
	final response = await put(url);

  //Print statement to confirm success of dislike
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
}