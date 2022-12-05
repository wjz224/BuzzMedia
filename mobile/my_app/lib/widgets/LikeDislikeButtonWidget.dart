import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';

import 'package:flutter/material.dart';
import 'package:like_button/like_button.dart';
import 'package:my_app/net/get_items_api.dart';
import 'package:my_app/net/get_single_post.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/net/get_single_post.dart';
import 'package:my_app/pages/IdeaPostPage.dart';
import 'package:my_app/pages/CommentPage.dart';
import 'package:my_app/net/get_userID.dart';

import 'package:my_app/net/put_like_api.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';

import 'package:my_app/model/user_other.dart';
import 'package:my_app/model/user_preferances.dart';
import 'package:my_app/model/item_model.dart';
import 'dart:convert';

class LikeWidget extends StatefulWidget {
  String mPost_ID;
  int likes;
  UserOther userOther;
  int dislikes;

  LikeWidget(
    this.mPost_ID,
    this.likes,
    this.userOther,
    this.dislikes,
  );
  @override
  _LikeWidgetState createState() => _LikeWidgetState();
}

class _LikeWidgetState extends State<LikeWidget> {
  //List<bool> _selections = List.generate(2, (_) => false);
  late Future<Post> currentPost;
  bool liked = false;
  bool disliked = false;
  int redLiked = 60;
  int greenLiked = 60;
  int blueLiked = 60;
  int redDisLiked = 60;
  int greenDisLiked = 60;
  int blueDisLiked = 60;

  //int likes = 0;
  //int dislikes = 0;
  void initState() {
    super.initState();
  }
  
  
  

  @override
  Widget build(BuildContext context) => Container(
      padding: const EdgeInsets.all(10),
      alignment: Alignment.center,
      color: Color.fromARGB(255, 228, 166, 8),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(widget.likes.toString()),
          SizedBox(width: 10),
          ElevatedButton(
              style: ElevatedButton.styleFrom(backgroundColor: Color.fromRGBO(redLiked, greenLiked, blueLiked, 1.0)),
              child: Text("Likes"),
              onPressed: () {
                if(!liked & disliked){
                  setState(() {
                  redDisLiked = 60;
                  greenLiked = 255;
                  widget.likes++;
                  liked = true;
                  disliked = false;
                  addLike(widget.mPost_ID, widget.userOther.sessionID);

                });
                }else if(liked){
                  setState(() {
                  greenLiked = 60;
                  widget.likes--;
                  liked = false;
                  dislike(widget.mPost_ID, widget.userOther.sessionID);

                });
                }else{
                  setState(() {
                  greenLiked = 255;
                  liked = true; 
                  widget.likes++;
                  addLike(widget.mPost_ID, widget.userOther.sessionID);
                });
                }
              }),

          // Spacing between button and text
          SizedBox(width: 10),
          // Number of likes displayed
          ElevatedButton(
              style: ElevatedButton.styleFrom(backgroundColor: Color.fromRGBO(redDisLiked, greenDisLiked, blueDisLiked, 1.0)),
              child: Text("DisLikes"),
              onPressed: () {
                if(!disliked & liked){
                  setState(() {
                  greenLiked = 60;
                  redDisLiked = 255;
                   widget.likes--;
                   dislike(widget.mPost_ID, widget.userOther.sessionID);
                  liked = false;
                  disliked = true;
                });}else if(disliked){
                  setState(() {
                  redDisLiked = 60;
                  disliked = false;
                  widget.likes++;
                  addLike(widget.mPost_ID, widget.userOther.sessionID);
                });
                }else{
                  setState(() {
                  redDisLiked = 255;
                  disliked = true;
                  widget.likes--;
                  dislike(widget.mPost_ID, widget.userOther.sessionID);
                });
                }
              }),
          //Spacing between button and text
          SizedBox(width: 10),

          // The like button display and functionality

          ElevatedButton(
              child: Text('View Comments',
                  style: TextStyle(color: Colors.white, fontSize: 16)),
              style: ElevatedButton.styleFrom(
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(50))),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => CommentPage(
                            postID: widget.mPost_ID,
                          )),
                );
              })
        ],
      ));
}
