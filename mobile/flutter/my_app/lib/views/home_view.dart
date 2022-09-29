import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'dart:convert';
import 'package:my_app/net/get_items_api.dart';
import 'package:my_app/model/item_model.dart';
import 'package:my_app/views/post_view.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
        //TODO: OnPress the switchScreen function is called
         actions: <Widget>[
    Padding(
      padding: EdgeInsets.only(right: 20.0),
      child: GestureDetector(
        onTap: () {
          Navigator.push(context, MaterialPageRoute(builder: (context) {
          return const PostPage(title: 'The Buzz');
        }));},
        child: Text('add'),
      )
    ),
   
  ],
      ),

      //TODO: display a lit of posts from the database using a column widget
      body: const Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: HttpReqPosts(),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

// class _MyAppState extends State<MyApp> {
//   late Future<message> futureMessage;

//   @override
//   void initState() {
//     super.initState();
//     futureMessage = fetchMessage();
//   }
// }

class HttpReqPosts extends StatefulWidget {
  const HttpReqPosts({Key? key}) : super(key: key);

  @override
  State<HttpReqPosts> createState() => _HttpReqPostsState();
}

class _HttpReqPostsState extends State<HttpReqPosts> {
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
    var fb = FutureBuilder<List<String>>(
      future: _future_list_message,
      builder:
          (BuildContext context, AsyncSnapshot<List<String>> snapshot) {
        Widget child;

        if (snapshot.hasData) {
          // developer.log('`using` ${snapshot.data}', name: 'my.app.category');
          // create  listview to show one row per array element of json response
          child = ListView.builder(
              //shrinkWrap: true, //expensive! consider refactoring. https://api.flutter.dev/flutter/widgets/ScrollView/shrinkWrap.html
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.length,
              itemBuilder: /*1*/ (context, i) {
                return Column(
                  children: <Widget>[
                    ListTile(
                      title: Text(
                        " ${snapshot.data?[i]}",
                        // snapshot.data![i].str,
                        style: _biggerFont,
                      ),
                    ),
                    Divider(height: 1.0),
                  ],
                );
              });
        } else if (snapshot.hasError) {
          // newly added
          child = Text('${snapshot.error}');
        } else {
          // awaiting snapshot data, return simple text widget
          // child = Text('Calculating answer...');
          child = const CircularProgressIndicator(); //show a loading spinner.
        }
        return child;
      },
    );

    return fb;
  }
}

// class HttpReqPosts extends StatefulWidget {
//   const HttpReqPosts({Key? key}) : super(key: key);

//   @override
//   State<HttpReqPosts> createState() => _HttpReqPostsState();
// }

// class _HttpReqPostsState extends State<HttpReqPosts> {
//   late Future<List<NumberWordPair>> _future_list_numword_pairs;

//   final _biggerFont = const TextStyle(fontSize: 18);

//   @override
//   void initState() {
//     super.initState();
//     _future_list_numword_pairs = fetchNumberWordPairs();
//   }

//   void _retry() {
//     setState(() {
//       _future_list_numword_pairs = fetchNumberWordPairs();
//     });
//   }

//   @override
//   Widget build(BuildContext context) {
//     var fb = FutureBuilder<List<NumberWordPair>>(
//       future: _future_list_numword_pairs,
//       builder:
//           (BuildContext context, AsyncSnapshot<List<NumberWordPair>> snapshot) {
//         Widget child;

//         if (snapshot.hasData) {
//           // developer.log('`using` ${snapshot.data}', name: 'my.app.category');
//           // create  listview to show one row per array element of json response
//           child = ListView.builder(
//               //shrinkWrap: true, //expensive! consider refactoring. https://api.flutter.dev/flutter/widgets/ScrollView/shrinkWrap.html
//               padding: const EdgeInsets.all(16.0),
//               itemCount: snapshot.data!.length,
//               itemBuilder: /*1*/ (context, i) {
//                 return Column(
//                   children: <Widget>[
//                     ListTile(
//                       title: Text(
//                         "row ${i}: num=${snapshot.data![i].num} str=${snapshot.data![i].str}",
//                         // snapshot.data![i].str,
//                         style: _biggerFont,
//                       ),
//                     ),
//                     Divider(height: 1.0),
//                   ],
//                 );
//               });
//         } else if (snapshot.hasError) {
//           // newly added
//           child = Text('${snapshot.error}');
//         } else {
//           // awaiting snapshot data, return simple text widget
//           // child = Text('Calculating answer...');
//           child = const CircularProgressIndicator(); //show a loading spinner.
//         }
//         return child;
//       },
//     );

//     return fb;
//   }
// }
