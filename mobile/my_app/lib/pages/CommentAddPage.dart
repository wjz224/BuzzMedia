import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/pages/CommentPage.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/net/post_items_api.dart';
import 'package:my_app/net/put_comment.dart';
import 'package:my_app/net/post_Comment.dart';

class CommentAddPage extends StatelessWidget {
  /// The main view of the post screen on the app containing a blank form with title and message fields and an add and cancel button
  final String postId;
  const CommentAddPage({Key? key, required this.postId}): super(key: key);
  

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Edit Comment"),
      ),
      body:  MyCustomForm(postId: this.postId,));
  }
}

class MyCustomForm extends StatefulWidget {
  /// Creates a blank form for user to fill out title and message to post
  final String postId;

  const MyCustomForm({super.key, required this.postId});
  @override
  State<MyCustomForm> createState() => _MyCustomFormState();
}

class _MyCustomFormState extends State<MyCustomForm> {
  /// Corresponding State class which holds data related to MyCustomForm

  // Create a text controller and use it to retrieve the current value of the text field
  final myController = TextEditingController();
  //final myController2 = TextEditingController();

  @override
  void dispose() {
    // Clean up the controller when the widget is disposed.
    myController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      // ignore: prefer_const_literals_to_create_immutables
      children: <Widget>[
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 8, vertical: 16),
          child: TextField(
            controller: myController,
            decoration: InputDecoration(
              border: OutlineInputBorder(),
              hintText: 'Add Comment',
            ),
          ),
        ),
        
        TextButton(
          style: ButtonStyle(
            foregroundColor: MaterialStateProperty.all<Color>(Colors.blue),
          ),
          onPressed: () {
            String title = myController.text;
            //String message = myController2.text;
            //print('title: $title');
            createPost(context, title);
          },
          child: Text('Add'),
        ),
        TextButton(
          style: ButtonStyle(
            foregroundColor: MaterialStateProperty.all<Color>(Colors.blue),
          ),
          onPressed: () {
            cancelPost(context);
          },
          child: Text('Cancel'),
        )
      ],
    );
  }

  void createPost(BuildContext context, String title) {
    print(title);
    
    postComment(title, widget.postId, "-1909482473");
    //switches page back to home page
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      
      return CommentPage(postID: widget.postId);
    }));
    
  }

  void cancelPost(BuildContext context) {
    //Switch to screen homepage when cancel is pressed
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      
      return const MyHomePage();
    }));
  }
}
