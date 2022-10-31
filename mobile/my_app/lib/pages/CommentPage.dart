// ignore_for_file: prefer_const_constructors

import 'package:flutter/material.dart';
import 'package:my_app/net/get_comments_api.dart';
import 'package:my_app/net/get_items_api.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/pages/IdeaPostPage.dart';
import 'package:my_app/net/put_like_api.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';
import 'package:my_app/pages/CommentPostPage.dart';

class CommentPage extends StatefulWidget {
  /// This stateful widget is the home page of the application

  const CommentPage({super.key, required this.title});

  final String title;

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
        title: Text(widget.title),
        actions: <Widget>[
          Padding(
              padding: EdgeInsets.only(right: 20.0),
              // A button in the top right used to add a post, looks like "+"
              child: GestureDetector(
                onTap: () {
                  /// When the "+" button is clicked, navigate to the post page (post_view.dart)
                  Navigator.push(context, MaterialPageRoute(builder: (context) {
                    return const CommentPostPage(title: 'The Buzz');
                  }));
                },
                child: Text(
                  '+',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 40),
                ),
              )),
          TextButton(
              child: Text('Go Back to Home',style: TextStyle(color: Colors.white) ),
              
              onPressed: () {
                Navigator.of(context).pop();
              }),
        ],
      ),

      body: const Center(
        // Center is a layout widget. It takes a single child and positions it in the middle of the parent
        // Displays a list of posts data
        child: HttpReqPosts(),
      ),
    );
  }
}

class HttpReqPosts extends StatefulWidget {
  /// Stateful widget get and display the posts from the database
  const HttpReqPosts({Key? key}) : super(key: key);

  @override
  State<HttpReqPosts> createState() => _HttpReqPostsState();
}

class _HttpReqPostsState extends State<HttpReqPosts> {
  /// Method for HttpReqPosts setState

  late Future<List<String>> _future_list_message;

  final _biggerFont = const TextStyle(fontSize: 18);

  @override
  void initState() {
    super.initState();
    print(fetchComments(1).runtimeType);
    _future_list_message = fetchComments(1);
  }

  List<bool> _selections = List.generate(2, (_) => false);
  @override
  Widget build(BuildContext context) {
    /// The main view of the home screen on the app containing a list of posts, like, and dislike buttons

    var fb = FutureBuilder<List<String>>(
      future: _future_list_message,
      builder: (BuildContext context, AsyncSnapshot<List<String>> snapshot) {
        Widget child;

        if (snapshot.hasData) {
          // Create  listview to show one row per array element of json response
          child = ListView.builder(
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.length,
              itemBuilder: /*1*/ (context, i) {
                // DataStr is the string of data for each post in json format !This is Tech Debt!
                var dataStr = snapshot.data?[i];
                // Split the components of dataString up
                var dataArr = dataStr?.split(',');
                var postID = dataArr?[0]?.split(':');
                var userID = dataArr?[1]?.split(':');
                var titleID = dataArr?[2]?.split(':');
                var textID = dataArr?[3]?.split(':');
                //seperate out the title, message, id, and numLikes into their own variables
                var title = postID?[1];
                var message = userID?[1];
                var likes = titleID?[1];
                var id = textID?[6];

                return Column(
                  children: <Widget>[
                    // One item in the list
                    ListTile(
                      // Displays the text and message of a post
                      //leading: Image.network(user.photoURL!),
                      title: RichText(
                        text: TextSpan(
                            text: ' $title ',
                            style: TextStyle(
                              color: Colors.black,
                              fontWeight: FontWeight.bold,
                              fontSize: 36,
                            ),
                            children: [
                              TextSpan(
                                text: "\n" + "$message",
                                style: TextStyle(
                                  color: Colors.white,
                                  fontWeight: FontWeight.normal,
                                  fontSize: 20,
                                ),
                              )
                            ]),
                      ),

                      subtitle:
                          Text("by " + "Some dude" + "\n" + "This email"),
                    ),
                    // Row widget puts the like count and buttons in one horizontal row together
                    Row(
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
                                addLike('$id');
                                _selections[1] = false;
                              } else if (index == 0 && !_selections[index]) {
                                dislike('$id');
                              }

                              //dislike Code
                              if (index == 1 && _selections[index]) {
                                _selections[0] = false;
                                dislike('$id');
                              } else if (index == 1 && !_selections[index]) {
                                addLike('$id');
                              }
                            });
                          },
                          color: Colors.grey,
                          fillColor: Colors.yellow,
                        ),

                        // Spacing between button and text
                        SizedBox(width: 20),
                        // Number of likes displayed
                        Text(
                          '10',
                          style: _biggerFont,
                        ),
                        //Edit button for comments
                        SizedBox(width: 20),
                        IconButton(onPressed: (){
                              editComment();
                            }, icon: Icon(Icons.mode))
                            ,
                        // The like button display and functionality

                        
                      ],
                    ),
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
  
  void editComment() {}
}
