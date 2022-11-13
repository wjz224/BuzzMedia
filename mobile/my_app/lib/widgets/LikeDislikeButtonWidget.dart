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
  UserOther userOther;


  LikeWidget(
    this.mPost_ID,
    this.userOther,
  );
  @override
  _LikeWidgetState createState() => _LikeWidgetState();
}

class _LikeWidgetState extends State<LikeWidget> {
  List<bool> _selections = List.generate(2, (_) => false);
  int likes = 0;
  void initState()  {
    super.initState(); 
    
  }

  @override
  Widget build(BuildContext context) => Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          // The dislike button display and functionality
          ToggleButtons(
            children: <Widget>[
              Icon(Icons.thumb_down),
              Icon(Icons.thumb_up),
            ],
            isSelected: _selections,
            onPressed: (int index) {
              setState(() {
                _selections[index] = !_selections[index];
                //likeCode
                if (index == 0 && _selections[index]) {
                  addLike(widget.mPost_ID!, widget.userOther.sessionID);
                  _selections[1] = false;
                } else if (index == 0 && !_selections[index]) {
                  dislike(widget.mPost_ID!, widget.userOther.sessionID);
                }

                //dislike Code
                if (index == 1 && _selections[index]) {
                  _selections[0] = false;
                  dislike(widget.mPost_ID!, widget.userOther.sessionID);
                } else if (index == 1 && !_selections[index]) {
                  addLike(widget.mPost_ID!, widget.userOther.sessionID);
                }               
                
              });
            },
            color: Colors.grey,
            fillColor: Colors.yellow,
          ),

          // Spacing between button and text
          SizedBox(width: 20),
          // Number of likes displayed
          
          //Spacing between button and text
          SizedBox(width: 20),

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
                  MaterialPageRoute(builder: (context) => CommentPage(postID: widget.mPost_ID,)),
                );
              })
        ],
      );
      
}


