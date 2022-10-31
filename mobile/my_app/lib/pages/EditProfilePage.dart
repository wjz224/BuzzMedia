import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:my_app/model/user_other.dart';
import 'package:my_app/model/user_preferances.dart';
import 'package:my_app/net/verify_api.dart';
import 'package:my_app/pages/ProfilePage.dart';
import 'package:my_app/net/put_change_profile.dart';

import 'package:my_app/widgets/TextFieldWidget.dart';

import 'LoginPage.dart';

import 'package:flutter/cupertino.dart';

import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';

class EditProfilePage extends StatefulWidget {
  @override
  _EditProfilePageState createState() => _EditProfilePageState();
}

class _EditProfilePageState extends State<EditProfilePage> {
  UserOther user = UserPreferences.myUser;

  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(),
        body: ListView(
          padding: EdgeInsets.symmetric(horizontal: 32),
          physics: BouncingScrollPhysics(),
          children: [
            const SizedBox(height: 24),
            TextFieldWidget(
                label: 'Gender',
                text: user.gender,
                onChanged: (gender) {
                  putChangeProfile(gender, user.sexualOrientation, user.bio,
                      "Id session", "Session Key");
                }),
            const SizedBox(height: 24),
            TextFieldWidget(
                label: 'Sexual Orientation',
                text: user.sexualOrientation,
                onChanged: (sexualOrientation) {
                  putChangeProfile(user.gender, sexualOrientation, user.bio,
                      "Id session", "Session Key");
                }),
            const SizedBox(height: 24),
            TextFieldWidget(
              label: 'Bio',
              text: user.bio,
              onChanged: (bio) {
                putChangeProfile(user.gender, user.sexualOrientation, bio,
                    "Id session", "Session Key");
              },
              maxLines: 5,
            ),
            ElevatedButton(
                child: Text('Save',
                    style: TextStyle(color: Colors.white, fontSize: 16)),
                style: ElevatedButton.styleFrom(
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(50))),
                onPressed: () {
                  Navigator.of(context).pop();
                })
          ],
        ),
      );
}
