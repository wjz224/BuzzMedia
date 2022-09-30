import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/views/home_view.dart';
import 'package:my_app/net/post_items_api.dart';

class PostPage extends StatelessWidget {
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

class MyCustomForm extends StatelessWidget {
  const MyCustomForm({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      // ignore: prefer_const_literals_to_create_immutables
      children: <Widget>[
        const Padding(
          padding: EdgeInsets.symmetric(horizontal: 8, vertical: 16),
          child: TextField(
            decoration: InputDecoration(
              border: OutlineInputBorder(),
              hintText: 'Enter a Title',
            ),
          ),
        ),
        const Padding(
          padding: EdgeInsets.symmetric(horizontal: 8, vertical: 16),
          child: TextField(
            decoration: InputDecoration(
              border: OutlineInputBorder(),
              hintText: 'Enter a Message',
            ),
          ),
        ),
        TextButton(
          style: ButtonStyle(
            foregroundColor: MaterialStateProperty.all<Color>(Colors.blue),
          ),
          onPressed: () {createPost(context);},
          child: Text('Add'),
        ),
        TextButton(
          style: ButtonStyle(
            foregroundColor: MaterialStateProperty.all<Color>(Colors.blue),
          ),
          onPressed: () {createPost(context); },
          child: Text('Cancel'),
        )
      ],
    );
  }
  
void createPost(BuildContext context) {
    //TODO add post to the database
    makePostRequest('test','#1');
    //switches page back to home page
      Navigator.push(context, MaterialPageRoute(builder: (context) {
        return const MyHomePage(title: 'The Buzz');
}));
}


    
}



