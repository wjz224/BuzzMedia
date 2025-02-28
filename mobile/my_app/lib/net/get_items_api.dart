import 'dart:collection';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:my_app/model/item_model.dart';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'dart:io';
//import 'package';

//the server url
const urlPrefix = 'https://thebuzzomega.herokuapp.com';
const String posts = "posts";

Box? box;

Future<List<Post>>fetchMessage(String sessionID) async {
  // open local cache for posts
  box = await Hive.openBox(posts);
  /// Gets the posts from the database
  // Get request to /messages route
	final response = await http.get(Uri.parse('$urlPrefix/1569641334/posts'));

  // Variable to contain the data from the get request
	var returnData;

  // If get requet is succesful parse through the data and return as a list of strings
    var res = jsonDecode(response.body);
    
		var resData = res['mData'] as List;
    print(resData.toString());
    List<Post> postObjs = resData.map((tagJson) => Post.fromJson(tagJson)).toList();
    Hive.box(posts).put("posts",postObjs);
    print(postObjs.toString());
    //String cleanup = response.body.substring(25,);
    //String cleanup2 = cleanup.substring(0, cleanup.length - 2);
    //print("Cleanup" + cleanup2);

		
    /*
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
		}*/
	
  
  // Return all posts as a list of strings
	return postObjs;

}

/// Simple get request without response parsing for unit testing purposes
Future<http.Response> makeGetRequest(String sessionID) {
	
	return http.get(Uri.parse('$urlPrefix/$sessionID/posts'));
	
}


