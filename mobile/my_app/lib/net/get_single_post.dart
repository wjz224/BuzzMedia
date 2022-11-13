import 'dart:collection';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:my_app/model/item_model.dart';
//import 'package';

//the server url
const urlPrefix = 'https://thebuzzomega.herokuapp.com';


Future<Post>fetchSinglePost(String sessionID, String post_id) async {
  /// Gets the posts from the database 

  // Get request to /messages route
	final response = await http.get(Uri.parse('$urlPrefix/$sessionID/posts/$post_id'));


  var res = jsonDecode(response.body);
    
	print("Post" + res.toString()); 
  Post postObjs = res['mData'];
  
    
	return postObjs;

}

/// Simple get request without response parsing for unit testing purposes
Future<http.Response> makeGetRequest(String sessionID) {
	
	return http.get(Uri.parse('$urlPrefix/$sessionID/posts'));
	
}


