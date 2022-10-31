import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';

//the server url
const urlPrefix = 'https://thebuzzomega.herokuapp.com';


Future<List<String>> fetchComments(id) async {
  /// Gets the posts from the database 

  // Get request to /messages route
	final response = await http.get(Uri.parse('$urlPrefix/posts/$id'));

  // Variable to contain the data from the get request
	var returnData;

  // If get requet is succesful parse through the data and return as a list of strings
	if (response.statusCode == 200) {
		var res = jsonDecode(response.body);
		var resData = res['mData'];

		if (resData is List) {
			returnData = (resData).map((x) => x.toString()).toList();
		} else if (resData is Map) {
			returnData = jsonDecode((resData as Map<String, dynamic>).toString());
			print('map');
			print('$returnData');
		} else {
			developer
				.log('ERROR: Unexpected json response type (was not a List or Map).');
			returnData = List.empty();
			throw Exception(
				'Failed to retrieve web data (server returned ${response.statusCode})');
		}
	}

  // Return all posts as a list of strings
	return returnData;

}

/// Simple get request without response parsing for unit testing purposes
Future<http.Response> makeCommentRequest() {
	
	return http.get(Uri.parse('$urlPrefix/posts'));
	
}


