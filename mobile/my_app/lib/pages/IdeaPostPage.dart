import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/net/post_items_api.dart';
import 'package:file_picker/file_picker.dart';
import 'package:path/path.dart';
import 'package:my_app/provider/UploadFiles.Dart';
import 'package:flutter_linkify/flutter_linkify.dart';
import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'dart:io' as Io;
import 'package:my_app/net/put_files_api.dart';

class PostPage extends StatelessWidget {
  /// The main view of the post screen on the app containing a blank form with title and message fields and an add and cancel button
  const PostPage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: const MyCustomForm(),
    );
  }
}

class MyCustomForm extends StatefulWidget {
  /// Creates a blank form for user to fill out title and message to post
  const MyCustomForm({super.key});
  @override
  State<MyCustomForm> createState() => _MyCustomFormState();
}

class _MyCustomFormState extends State<MyCustomForm> {
  /// Corresponding State class which holds data related to MyCustomForm

  // Create a text controller and use it to retrieve the current value of the text field
  final myController = TextEditingController();
  final myController2 = TextEditingController();
  final drive = GoogleDrive();
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
    final fileNameDisplay = file != null ? basename(file!.path) : "No File Selected";
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
              hintText: 'Enter a Title',
            ),
          ),
        ),
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 8, vertical: 16),
          child: TextField(
            controller: myController2,
            decoration: InputDecoration(
              border: OutlineInputBorder(),
              hintText: 'Enter a Message',
            ),
          ),
        ),
        // text button for selecting a file
        TextButton(
          style: ButtonStyle(
            foregroundColor: MaterialStateProperty.all<Color>(Colors.blue),
          ),
          onPressed: () {
              selectFile();
          },
          child: Text('Select File'),
        ),
        Text(
          fileNameDisplay,
          style: TextStyle(fontSize: 16),
        ),
        TextButton(
          style: ButtonStyle(
            foregroundColor: MaterialStateProperty.all<Color>(Colors.blue),
          ),
          onPressed: () {
            String title = myController.text;
            String message = myController2.text;
            if(file !=null){
                String file64 = uploadFile(file);
                createPost(context,title,message,fileName,file64);
            }
            else{
                print("in other thing");
                createPost(context,title,message, "","");
            }
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

  void createPost(BuildContext context, String title, String message, String fileName, String file64) {
    print(title);
    print(message);
    if(fileName != Null){
        print("made post request");
        makePostRequest(title, message, fileName, file64, "1569641334");
    }
    //switches page back to home page
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return const MyHomePage();
    }));
  }
  // selecting a file
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
  void cancelPost(BuildContext context) {
    //Switch to screen homepage when cancel is pressed
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      
      return const MyHomePage();
    }));
  }
}
