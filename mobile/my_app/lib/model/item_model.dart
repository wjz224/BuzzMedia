import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';


//Create object from a post in the database
//each post should have a unique id(int), a title(String), and a message(String)
class Post {
	int postId;
  int userID;
	String title;
	String text;
	int likes;
  //int? dislikes;

  Post(
    this.postId,
    this.userID,
    this.title,
    this.text,
    this.likes,
    //this.dislikes,
  );

	
	factory Post.fromJson(dynamic json) {
		return Post(json['mPost_id'] as int, json['mUser_id'] as int, json['mTitle'] as String, json['mText'] as String,  json['mLikes'] as int);
	}


  @override
  String toString() {
    return '{ ${this.postId}, ${this.userID}, ${this.title},${this.text},${this.likes}}';
  }
}

