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
import 'package:my_app/pages/OtherProfilePages.dart';

class ShowProfile extends StatefulWidget {
  String mUser_ID;
  

  ShowProfile(
    this.mUser_ID,
  
  );
  @override
  _ShowProfileState createState() => _ShowProfileState();
}




class _ShowProfileState extends State<ShowProfile> {
  //List<bool> _selections = List.generate(2, (_) => false);
  late Future<UserOther> userPosted;
  //int likes = 0;
  //int dislikes = 0;
  void initState()  {
    super.initState(); 
    userPosted = fetchUserInfo("-1909482473", widget.mUser_ID);
    
  }

  @override
  Widget build(BuildContext context){
    var fb = FutureBuilder<UserOther>(
      future: userPosted,
      builder: (BuildContext context, AsyncSnapshot<UserOther> snapshot) {
        Widget child;
        if (snapshot.hasData) {
          UserOther? dataStr = snapshot.data;
          String userName = dataStr!.userName;
          String email = dataStr!.email;
          // Create  listview to show one row per array element of json response
          child = TextButton(
            onPressed: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) =>
                                  OtherProfile(widget.mUser_ID),
                            ));
                      },
            child: Text(userName + "\n" + email),
          );
        } else if (snapshot.hasError) {
          // newly added
          child = Text('${snapshot.error}');
        } else {
          child = const CircularProgressIndicator(); //show a loading spinner.
        }
        return child;
      },
    );

    return fb;
  }
        
      
}


