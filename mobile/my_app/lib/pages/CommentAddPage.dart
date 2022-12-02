import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/pages/CommentPage.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/net/post_items_api.dart';
import 'package:my_app/net/put_comment.dart';
import 'package:my_app/net/post_Comment.dart';
import 'package:my_app/provider/UploadFiles.Dart';
import 'package:file_picker/file_picker.dart';
import 'package:path/path.dart';
import 'dart:convert';
import 'dart:io';
class CommentAddPage extends StatelessWidget {
  /// The main view of the post screen on the app containing a blank form with title and message fields and an add and cancel button
  final String postId;
  const CommentAddPage({Key? key, required this.postId}): super(key: key);
  

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Add Comment"),
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
  File? file = null;
  String fileName = "";

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
              selectFile();
          },
          child: Text('Select File'),
        ),
        TextButton(
          style: ButtonStyle(
            foregroundColor: MaterialStateProperty.all<Color>(Colors.blue),
          ),
          onPressed: () {
            String title = myController.text;
            if(file !=null){
                 String file64 = uploadFile(file);
                createPost(context, title, fileName, file64);
            }
            else{
                createPost(context,title, "","");
            }
            //String message = myController2.text;
            //print('title: $title');

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

  void createPost(BuildContext context, String title, String fileName, String file64) {
    print(title);
    
    postComment(title, widget.postId, fileName, file64, "1569641334");
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
  Future selectFile() async{
      // get file
      final result = await FilePicker.platform.pickFiles(allowMultiple: false);
      // check if result from file picked is valid or not
      if(result == null) return;
      // if result exists, get path of file
      final path = result.files.single.path!;
      // Create file object with path
      setState(() => file = File(path));
  }
  String uploadFile(File? file)  {
    // file is null, return empty string for file
    if (file == null) return "";
    // Set file name to basename of path.
    fileName = basename(file.path);
    // if file is not null, than store the file's bytes
    final bytes =  file.readAsBytesSync();
    // base64 encode the bytes of the file.
    String base64Encode = base64.encode(bytes);
    // return the base64 encoded version of the bytes.
    return base64Encode;
  }
}
