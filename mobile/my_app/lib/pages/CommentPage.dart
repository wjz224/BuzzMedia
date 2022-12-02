// ignore_for_file: prefer_const_constructors
import 'package:firebase_auth/firebase_auth.dart';

import 'package:flutter/material.dart';
import 'package:like_button/like_button.dart';
import 'package:my_app/net/get_items_api.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/pages/CommentPutPage.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/pages/IdeaPostPage.dart';
import 'package:my_app/pages/CommentPage.dart';
import 'package:my_app/net/get_userID.dart';
import 'package:my_app/net/get_comments_api.dart';

import 'package:my_app/net/put_like_api.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';
import 'package:my_app/pages/CommentAddPage.dart';
import 'package:my_app/model/user_other.dart';
import 'package:my_app/model/user_preferances.dart';
import 'package:my_app/model/item_model.dart';
import 'package:my_app/model/comment_model.dart';
import 'dart:convert';
import 'package:my_app/widgets/LikeDislikeButtonWidget.dart';
import 'package:my_app/widgets/FileProcess.dart';
class CommentPage extends StatefulWidget {
  /// This stateful widget is the home page of the application
  
  final String postID;
  const CommentPage({super.key, required this.postID});

  

  @override
  State<CommentPage> createState() => _CommentPageState();
}

class _CommentPageState extends State<CommentPage> {
  /// This method is rerun every time setState is called

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // Set appBar title to "The Buzz"
      appBar: AppBar(
        title: Text("Comments"),
        actions: <Widget>[
          Padding(
              padding: EdgeInsets.only(right: 20.0),
              // A button in the top right used to add a post, looks like "+"
              child: GestureDetector(
                onTap: () {
                  /// When the "+" button is clicked, navigate to the post page (post_view.dart)
                  Navigator.push(context, MaterialPageRoute(builder: (context) {
                    return CommentAddPage(postId: widget.postID);
                  }));
                },
                child: Text(
                  '+',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
                ),
              )
              ),TextButton(
              child: Text('Go Back',
                  style: TextStyle(color: Colors.white)),
              onPressed: () {
                final provider =
                    Provider.of<GoogleSignInProvider>(context, listen: false);
                provider.logout();
                Navigator.of(context).pop();
              }),
          
        ],
      ),

      body: Center(
        // Center is a layout widget. It takes a single child and positions it in the middle of the parent
        // Displays a list of posts data
        child: HttpReqComment(postID: widget.postID),
      ),
    );
  }
}

class HttpReqComment extends StatefulWidget {
  /// Stateful widget get and display the posts from the database
  final String postID;
  HttpReqComment({Key? key, required this.postID}) : super(key: key);

  @override
  State<HttpReqComment> createState() => _HttpReqCommentState();
}

class _HttpReqCommentState extends State<HttpReqComment> {
  /// Method for HttpReqComment setState
  final user = FirebaseAuth.instance.currentUser!;

  late Future<List<Comment>> _future_list_message;

  late Future<List<String>> userInformartion;

  final _biggerFont = const TextStyle(fontSize: 18);

  final sizeButton = 50.0;

  final userOther = UserPreferences.myUser;


  @override
  void initState() {
    super.initState();
    print(fetchMessage(userOther.sessionID).runtimeType);
    _future_list_message = fetchComments(userOther.sessionID, widget.postID);
  }

  
  @override
  Widget build(BuildContext context) {
    /// The main view of the home screen on the app containing a list of posts, like, and dislike buttons
    
    



    var fb = FutureBuilder<List<Comment>>(
      future: _future_list_message,
      builder: (BuildContext context, AsyncSnapshot<List<Comment>> snapshot) {
        Widget child;
        if (snapshot.hasData) {
          // Create  listview to show one row per array element of json response
          child = ListView.builder(
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.length,
              itemBuilder: /*1*/ (context, i) {
                // DataStr is the string of data for each post in json format !This is Tech Debt!
                Comment? dataStr = snapshot.data?[i];
                // Split the components of dataString up
   
                // DataStr is the string of data for each post in json format !This is Tech Debt!
                // Split the components of dataString up
                

                //seperate out the title, message, id, and numLikes into their own variables
                String commentID = dataStr!.commentID.toString();
                String mUser_ID = dataStr!.userID.toString();
                String postID = dataStr!.postID.toString();
                String mText = dataStr!.text;
                String mFileName = dataStr!.filename;
                String mFile = dataStr!.file;
                
                


                //var userInformartion = fetchUserInfo(userOther.sessionID, mUser_ID!);

                
                
                //String userName = userInformartion[0];
                //String email = userInformartion[1];

                

                return Column(
                  children: <Widget>[
                    // One item in the list
                    ListTile(
                      // Displays the text and message of a post
                      title: RichText(
                        text: TextSpan(
                            text: mText,
                            style: TextStyle(
                              color: Colors.black,
                              fontWeight: FontWeight.bold,
                              fontSize: 24,
                            ),                            
                          ),
                      ),
                    trailing: IconButton(onPressed: (){
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => CommentPutPage(commentID: commentID, postID: postID),
                              ));
                            }, icon: Icon(Icons.mode)),
                      
                    ),
                    TextButton(
                      child: Text(mFileName,
                          style: TextStyle(color: Colors.green)),
                      onPressed: () {
                        FileProcess.downloadFile(mFile,mFileName);
                        FileProcess.openFile(mFileName);
                      }),
                    // Row widget puts the like count and buttons in one horizontal row together
                    
                    // Space between each post item
                    Divider(height: 1.0),
                  ],
                );
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
