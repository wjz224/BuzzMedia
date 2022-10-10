import 'package:http/http.dart';


Future<void> addLike(
  	String messageId,
) async {
  /// Adds a like to a post specified by the messageId variable
  
  // Makes put request with url specifying messageId and /likes route
	final url = Uri.parse('https://thebuzzomega.herokuapp.com/messages/$messageId/likes');
	final response = await put(url);

  // Print statements to confirm success of like function
	print('Status code: ${response.statusCode}');
	print('Body: ${response.body}');
  
}