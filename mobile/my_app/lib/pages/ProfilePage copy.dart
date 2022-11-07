/*// ignore_for_file: prefer_const_constructors
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
import 'package:my_app/model/user_preferances.dart';
import 'package:my_app/model/item_model.dart';
import 'dart:convert';
import 'package:my_app/widgets/LikeDislikeButtonWidget.dart';
import 'package:my_app/pages/EditProfilePage.dart';

class ProfilePage extends StatefulWidget {
  /// This stateful widget is the home page of the application

  const ProfilePage({super.key});

  final String title = "The Buzz";

  @override
  State<ProfilePage> createState() => ProfilePageState();
}

class ProfilePageState extends State<ProfilePage> {
  /// This method is rerun every time setState is called

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // Set appBar title to "The Buzz"
     appBar: AppBar(
          title: Text('Logged In'),
          centerTitle: true,
          actions: [
            IconButton(
                onPressed: () {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => EditProfilePage(user: userOther),
                      ));
                },
                icon: Icon(Icons.mode)),
            TextButton(
              child: Text('Abandon the Hive',
                  style: TextStyle(color: Colors.white)),
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
        child: ProfileContent(),
      ),
    );
  }
}

class ProfileContent extends StatefulWidget {
  /// Stateful widget get and display the posts from the database
  const ProfileContent({Key? key}) : super(key: key);

  @override
  State<ProfileContent> createState() => _ProfileContentState();
}

class _ProfileContentState extends State<ProfileContent> {
  /// Method for ProfileContent setState
  final user = FirebaseAuth.instance.currentUser!;

  late Future<UserOther> _future_list_message;

  late Future<List<String>> userInformartion;

  final _biggerFont = const TextStyle(fontSize: 18);

  final sizeButton = 50.0;

  final userOther = UserPreferences.myUser;
  List<bool> _selections = List.generate(2, (_) => false);


  @override
  void initState() {
    super.initState();
    print(fetchMessage(userOther.sessionID).runtimeType);
    _future_list_message = fetchMessage(userOther.sessionID);
  }

  
  @override
  Widget build(BuildContext context) {
    /// The main view of the home screen on the app containing a list of posts, like, and dislike buttons
    
    



    var fb = FutureBuilder<List<Post>>(
      future: _future_list_message,
      builder: (BuildContext context, AsyncSnapshot<List<Post>> snapshot) {
        Widget child;
        if (snapshot.hasData) {
          // Create  listview to show one row per array element of json response
          child = ListView.builder(
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.length,
              itemBuilder: /*1*/ (context, i) {
                // DataStr is the string of data for each post in json format !This is Tech Debt!
                Post? dataStr = snapshot.data?[i];
                // Split the components of dataString up
   
                // DataStr is the string of data for each post in json format !This is Tech Debt!
                // Split the components of dataString up
                

                //seperate out the title, message, id, and numLikes into their own variables
                String mPost_ID = dataStr!.postId.toString();
                String mUser_ID = dataStr!.userID.toString();
                String mTitle = dataStr!.title;
                String mText = dataStr!.text;
                String mLikes = dataStr!.likes.toString();
                


                //var userInformartion = fetchUserInfo(userOther.sessionID, mUser_ID!);

                
                
                //String userName = userInformartion[0];
                //String email = userInformartion[1];

                

                
              });
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
*/