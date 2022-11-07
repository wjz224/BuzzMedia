import 'package:http/http.dart';


Future<void> dislike(
    String postID,
  	String sessionKey,
    
) async {
  /// Adds dislike to a post specified by the messageId variable
  
  // Makes put request with url specifying messageId and /dislikes route
	final url = Uri.parse('https://thebuzzomega.herokuapp.com/$sessionKey/$postID/dislikes');
	final response = await put(url);

  //Print statement to confirm success of dislike
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
}