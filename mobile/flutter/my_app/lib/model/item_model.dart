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



/// Create object from data like: http://www.cse.lehigh.edu/~spear/5k.json
// class NumberWordPair {
//   /// The string representation of the number
//   final String str; 
//   /// The int representation of the number
//   final int num;

//   const NumberWordPair({
//     required this.str,
//     required this.num,
//   });

//   factory NumberWordPair.fromJson(Map<dynamic, dynamic> json) {
//     return NumberWordPair(
//       str: json['str'],
//       num: json['num'],
//     );
//   }
// }