import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';


//Create object from a post in the database
//each post should have a unique id(int), a title(String), and a message(String)
class Comment {
	int commentID;
  int postID;
	int userID;
	String text;
  String filename;
  String file;

  Comment(
    this.commentID,
    this.postID,
    this.userID,
    this.text,
    this.filename,
    this.file,
  );

	
	factory Comment.fromJson(dynamic json) {
		return Comment(json['mComment_id'] as int, json['mPost_id'] as int, json['mUser_id'] as int, json['mComment'] as String, json['mFilename'] as String, json['mFile'] as String);
	}


  @override
  String toString() {
    return '{ ${this.commentID}, ${this.postID}, ${this.userID},${this.text}, ${this.filename}, ${this.file}}';
  }
}

