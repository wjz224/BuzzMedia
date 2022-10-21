// ignore_for_file: prefer_const_constructors

import 'package:flutter/material.dart';
import 'package:my_app/net/get_items_api.dart';
import 'package:my_app/net/put_dislike_api.dart';
import 'package:my_app/pages/PostPage.dart';
import 'package:my_app/net/put_like_api.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';

class MyHomePage extends StatefulWidget {
  /// This stateful widget is the home page of the application

  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
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
                    return const PostPage(title: 'The Buzz');
                  }));
                },
                child: Text(
                  '+',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 40),
                ),
              )),
          TextButton(
              child: Text('Logout'),
              onPressed: () {
                final provider =
                    Provider.of<GoogleSignInProvider>(context, listen: false);
                provider.logout();
              })
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
    print(fetchMessage().runtimeType);
    _future_list_message = fetchMessage();
  }

  void _retry() {
    setState(() {
      _future_list_message = fetchMessage();
    });
  }

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
                var titleArr = dataArr?[1]?.split(':');
                var messageArr = dataArr?[2]?.split(':');
                var likesArr = dataArr?[3]?.split(':');
                //seperate out the title, message, id, and numLikes into their own variables
                var title = titleArr?[1];
                var message = messageArr?[1];
                var likes = likesArr?[1];
                var id = dataStr?[6];

                return Column(
                  children: <Widget>[
                    // One item in the list
                    ListTile(
                      // Displays the text and message of a post
                      title: Text(
                        " $title:  $message   	",
                        style: _biggerFont,
                      ),
                    ),

                    // Row widget puts the like count and buttons in one horizontal row together
                    Row(
                      children: <Widget>[
                        // The dislike button display and functionality
                        GestureDetector(
                          // When clicked run the dislike method
                          onTap: () {
                            dislike('$id');
                          },
                          // Thumbs down icon
                          child: Icon(Icons.thumb_down),
                        ),
                        // Spacing between button and text
                        SizedBox(width: 20),
                        // Number of likes displayed
                        Text(
                          '$likes',
                          style: _biggerFont,
                        ),
                        //Spacing between button and text
                        SizedBox(width: 20),

                        // The like button display and functionality
                        GestureDetector(
                          //function that adds a like from post with matching id
                          onTap: () {
                            addLike('$id');
                          },
                          // Thumbs up icon
                          child: Icon(Icons
                                  .thumb_up //added thumbs up icon for like button
                              ),
                        ),
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
}
