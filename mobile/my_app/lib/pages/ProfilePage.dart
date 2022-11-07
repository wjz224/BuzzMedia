import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:my_app/model/user_preferances.dart';
import 'package:my_app/net/verify_api.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/pages/EditProfilePage.dart';
import 'package:my_app/model/user_other.dart';

import 'LoginPage.dart';
import 'dart:convert';
import 'package:flutter/cupertino.dart';
import 'package:my_app/net/get_userID.dart';

import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';

class ProfilePage extends StatefulWidget {
  ProfilePage();


  

  _ProfilePageState createState() => _ProfilePageState();

}

class _ProfilePageState extends State<ProfilePage> {
  final user = FirebaseAuth.instance.currentUser!;
  UserOther userOther = UserPreferences.myUser;


  
  
  @override
  Widget build(BuildContext context) => Scaffold(
        
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
        body: Container(
            alignment: Alignment.center,
            color: Color.fromARGB(255, 228, 166, 8),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  'Profile',
                  style: TextStyle(fontSize: 24),
                ),
                SizedBox(height: 32),
                CircleAvatar(
                  radius: 40,
                  backgroundImage: NetworkImage(user.photoURL!),
                ),
                SizedBox(height: 8),
                Text(
                  'Name: ' + user.displayName!,
                  style: TextStyle(color: Colors.black, fontSize: 24),
                ),
                SizedBox(height: 8),
                Text(
                  'Email: ' + user.email!,
                  style: TextStyle(color: Colors.black, fontSize: 24),
                ),
                SizedBox(height: 8),
                Text(
                  'Bio:' + userOther.bio,
                  style: TextStyle(color: Colors.black, fontSize: 24),
                ),
                SizedBox(height: 8),
                Text(
                  'Gender: ' + userOther.gender,
                  style: TextStyle(color: Colors.black, fontSize: 24),
                ),
                SizedBox(height: 8),
                Text(
                  'Sexual Orientation: ' + userOther.sexualOrientation,
                  style: TextStyle(color: Colors.black, fontSize: 24),
                ),
                TextButton(
                    child: Text('Go to Colony Center',
                        style: TextStyle(color: Colors.white, fontSize: 40)),
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => const MyHomePage()),
                      );
                    })
              ],
            )),
      );

      void addingSessionID(UserOther userOther) async {
  final user = FirebaseAuth.instance.currentUser!;
  //print(user.getIdToken());
  String responseBody = await verify(user.getIdToken(true));
  var tmp = jsonDecode(responseBody);
  responseBody = tmp["mData"].toString();
  print(responseBody);
  userOther.setSessionID(responseBody);
  //return responseBody;
}

}


/*

String userInformation =
       fetchUserInfo(userOther.sessionID, user.email!);
  var tmp = jsonDecode(userInformation);
  userOther.gender = tmp["mGender"].toString();
  userOther.sexualOrientation = tmp["mSex"].toString();
  userOther.bio = tmp["mNote"].toString();
  */