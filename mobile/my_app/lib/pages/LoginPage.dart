import 'package:firebase_auth/firebase_auth.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/pages/ProfilePage.dart';
import 'package:my_app/widgets/LoginWidget.dart';
import 'package:provider/provider.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:my_app/net/verify_api.dart';
import 'package:my_app/model/user_other.dart';
import 'package:my_app/model/user_preferances.dart';
import 'dart:convert';


class LoginPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) => Scaffold(
        body: StreamBuilder(
            stream: FirebaseAuth.instance.authStateChanges(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(child: CircularProgressIndicator());
              } else if (snapshot.hasData) {
                final user = FirebaseAuth.instance.currentUser!;
                //verify(user.getIdToken(true));
                addingSessionID();
                /*Center( child: 
                TextButton(
                    child: Text('Logout', style: TextStyle(fontSize: 24, color: Colors.black)),
                    
                    onPressed: () {
                      final provider = Provider.of<GoogleSignInProvider>(
                          context,
                          listen: false);
                      provider.logout();
                    }));*/
                
                return ProfilePage();

              } else if (snapshot.hasError) {
                return Center(child: Text('Something Went Wrong!'));
              } else {
                return LoginWidget();
              }
            }),
      );
}

Future<String> addingSessionID() async {
  final userOther = UserPreferences.myUser;
  final user = FirebaseAuth.instance.currentUser!;
  //print(user.getIdToken());
  String responseBody = await verify(user.getIdToken(true));
  var tmp = jsonDecode(responseBody);
  responseBody = tmp["mData"].toString();
  print(responseBody);
  userOther.setSessionID(responseBody);
  return responseBody;
}