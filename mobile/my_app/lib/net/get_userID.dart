// ignore_for_file: unnecessary_new

import 'dart:collection';
import 'dart:developer' as developer;
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:my_app/model/user_other.dart';
import 'dart:io';
//import 'package';

//the server url
const urlPrefix = 'https://thebuzzomega.herokuapp.com';
const String user = "user";

Future<UserOther> fetchUserInfo(String sessionID, String email) async {
  // local caching for user info
   await Hive.openBox(user);
  /// Gets the posts from the database 

  // Get request to /messages route
	final response = await http.get(Uri.parse('$urlPrefix/1569641334/users/$email'));

  var res = jsonDecode(response.body);
    
		print(res.toString());
    print("im getting user id");
    UserOther postObjs = UserOther.fromJson(res);
    Hive.box(user).put("posts",postObjs);
    print(postObjs.toString());
    
	return postObjs;
  // If get requet is succesful parse through the data and return as a list of strings
	
    
		//var resData = res['mData'];
    //String cleanup = response.body.substring(25,);
    //String cleanup2 = cleanup.substring(0, cleanup.length - 2);
    //print("Cleanup" + cleanup2); 
  // Return all posts as a list of strings
}

/// Simple get request without response parsing for unit testing purposes
Future<http.Response> makeGetRequest(String sessionID) {
	
	return http.get(Uri.parse('$urlPrefix/$sessionID/posts'));
	
}


