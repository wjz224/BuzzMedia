// ignore_for_file: prefer_const_constructors

import 'package:firebase_auth/firebase_auth.dart';

import 'package:flutter/material.dart';
import 'package:like_button/like_button.dart';
import 'package:my_app/net/get_items_api.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/pages/IdeaPostPage.dart';
import 'package:my_app/pages/CommentPage.dart';
import 'package:my_app/net/get_userID.dart';

import 'package:my_app/net/put_like_api.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';

import 'package:my_app/model/user_other.dart';

import 'package:my_app/model/item_model.dart';
import 'dart:convert';
import 'package:my_app/widgets/LikeDislikeButtonWidget.dart';
import 'package:my_app/pages/HomePage.dart';

/*class OtherProfile extends StatefulWidget {
  /// This stateful widget is the home page of the application

  const OtherProfile({super.key});

  //final String title = "The Buzz";

  @override
  State<OtherProfile> createState() => OtherProfileState();
}

class OtherProfileState extends State<OtherProfile> {
  /// This method is rerun every time setState is called

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // Set appBar title to "The Buzz"
      appBar: AppBar(
        title: Text('Logged In'),
        centerTitle: true,
        actions: [
          TextButton(
            child:
                Text('Abandon the Hive', style: TextStyle(color: Colors.white)),
            onPressed: () {
              final provider =
                  Provider.of<GoogleSignInProvider>(context, listen: false);
              provider.logout();
            },
          )
        ],
      ),

      body: const Center(
        // Center is a layout widget. It takes a single child and positions it in the middle of the parent
        // Displays a list of posts data
        child: OtherProfile(),
      ),
    );
  }
}*/

class OtherProfile extends StatefulWidget {
  /// Stateful widget get and display the posts from the database
 String mUser_ID;
  

  OtherProfile(
    this.mUser_ID,
  
  );

  @override
  State<OtherProfile> createState() => _OtherProfileState();
}

class _OtherProfileState extends State<OtherProfile> {
  /// Method for OtherProfile setState
  final user = FirebaseAuth.instance.currentUser!;

  late Future<UserOther> _future_Person;

  //late Future<List<String>> userInformartion;

  final _biggerFont = const TextStyle(fontSize: 18);

  final sizeButton = 50.0;


  @override
  void initState() {
    super.initState();
    //print(fetchMessage(userOther.sessionID).runtimeType);
    _future_Person = fetchUserInfo("-1909482473", widget.mUser_ID);
  }

  @override
  Widget build(BuildContext context) {
    /// The main view of the home screen on the app containing a list of posts, like, and dislike buttons

    var fb = FutureBuilder<UserOther>(
      future: _future_Person,
      builder: (BuildContext context, AsyncSnapshot<UserOther> snapshot) {
        Widget child;
        if (snapshot.hasData) {
          UserOther? dataStr = snapshot.data;
          String bio = dataStr!.bio;
          String userName = dataStr!.userName;
          String likes = dataStr!.sessionID;
          
          // Create  listview to show one row per array element of json response
          child = Container(
              padding: const EdgeInsets.all(16.0),
              alignment: Alignment.center,
              color: Color.fromARGB(255, 228, 166, 8),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  
                  Text(
                    'Profile',
                    style: TextStyle(fontSize: 24),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Name: ' + user.displayName!,
                    style: TextStyle(color: Colors.black, fontSize: 24),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Email: ' + user.email!,
                    style: TextStyle(color: Colors.black, fontSize: 24),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Bio:' + bio,
                    style: TextStyle(color: Colors.black, fontSize: 24),
                  ),
                  
                  SizedBox(height: 8),
                  
                  TextButton(
                      child: Text('Go Back',
                          style: TextStyle(color: Colors.white, fontSize: 40)),
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => const MyHomePage()),
                        );
                      })
                ],
              ));
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
