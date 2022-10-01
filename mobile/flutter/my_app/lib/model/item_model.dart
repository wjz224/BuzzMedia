import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';


//Create object from a post in the database
//each post should have a unique id(int), a title(String), and a message(String)
class Message {
	final int? mId;
	final String? mTitle;
	final String? mContent;
	final String? mLikes;
	final String? mCreated;

	const Message({
		required this.mId,
		required this.mTitle,
		required this.mContent,
		required this.mLikes,
		required this.mCreated,
	
	});

	factory Message.fromJson(Map<String, dynamic> json) {
		return Message(
			mId: json['mId'],
			mTitle: json['mTitle'],
			mContent: json['mContent'],
			mLikes: json['mLikes'],
			mCreated: json['mCreated'],
		);
	}
}

