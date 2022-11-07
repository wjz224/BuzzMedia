// ignore_for_file: unnecessary_new

import 'dart:collection';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
//import 'package';

//the server url
const urlPrefix = 'https://thebuzzomega.herokuapp.com';


Future<String> fetchUserInfo(String sessionID, String userID) async {
  /// Gets the posts from the database 

  // Get request to /messages route
	final response = await http.get(Uri.parse('$urlPrefix/$sessionID/users/$userID'));

  Future<String> returnData;

  // If get requet is succesful parse through the data and return as a list of strings
	if (response.statusCode == 200) {
    Future<String> resData = jsonDecode(response.body);
    
		//var resData = res['mData'];
    //String cleanup = response.body.substring(25,);
    //String cleanup2 = cleanup.substring(0, cleanup.length - 2);
    //print("Cleanup" + cleanup2);

		returnData = resData;
    return returnData;
	}else{
    returnData = " invalid" as Future<String>;
    return returnData;
  }
  
  
  // Return all posts as a list of strings
	


}

/// Simple get request without response parsing for unit testing purposes
Future<http.Response> makeGetRequest(String sessionID) {
	
	return http.get(Uri.parse('$urlPrefix/$sessionID/posts'));
	
}


