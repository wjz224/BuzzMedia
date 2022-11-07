import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:my_app/model/user_other.dart';
import 'package:my_app/model/user_preferances.dart';
import 'package:my_app/net/verify_api.dart';
import 'package:my_app/pages/ProfilePage.dart';
import 'package:my_app/net/put_change_profile.dart';
import 'package:my_app/widgets/DropDownWidget.dart';

import 'package:my_app/widgets/TextFieldWidget.dart';

import 'LoginPage.dart';

import 'package:flutter/cupertino.dart';

import 'package:my_app/provider/google_sign_in.dart';
import 'package:provider/provider.dart';

class EditProfilePage extends StatefulWidget {
  @override
   UserOther user;

   EditProfilePage({
    required this.user
   });

  _EditProfilePageState createState() => _EditProfilePageState();
}

class _EditProfilePageState extends State<EditProfilePage> {

  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(),
        body: ListView(
          padding: EdgeInsets.symmetric(horizontal: 32),
          physics: BouncingScrollPhysics(),
          children: [
            const SizedBox(height: 24),
            Text("Sexual Orientation", style: TextStyle(fontWeight: FontWeight.bold)),
            DropdownButtonField(list: ["N/A","striaght", "gay", "bi", "queer", "dk", "decline", "other"], dropdownValue: widget.user.gender,
            onChanged: (gender){
              putChangeProfile(gender, widget.user.sexualOrientation, widget.user.bio,
                    "yap224@lehigh.edu", widget.user.sessionID);
            },),
            const SizedBox(height: 24),
            Text("Gender", style: TextStyle(fontWeight: FontWeight.bold)),
            DropdownButtonField(list: ["N/A","male", "female", "transm", "transw", "nonconf", "decline", "other"], dropdownValue: widget.user.sexualOrientation,
            onChanged: (so){
              putChangeProfile(widget.user.gender, so, widget.user.bio,
                    "yap224@lehigh.edu", widget.user.sessionID);
            },),
            const SizedBox(height: 24),
            TextFieldWidget(
              label: 'Bio',
              text: widget.user.bio,
              onChanged: (bio) {
                putChangeProfile(widget.user.gender, widget.user.sexualOrientation, bio,
                    "yap224@lehigh.edu", widget.user.sessionID);
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

