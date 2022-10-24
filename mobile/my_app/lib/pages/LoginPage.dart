import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/pages/ProfilePage.dart';
import 'package:my_app/widgets/LoginWidget.dart';
import 'package:provider/provider.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:my_app/net/verify_api.dart';


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
                verify(user.getIdToken());

                return ProfilePage();/*Center( child: 
                TextButton(
                    child: Text('Logout'),
                    onPressed: () {
                      final provider = Provider.of<GoogleSignInProvider>(
                          context,
                          listen: false);
                      provider.logout();
                    }));*/
              } else if (snapshot.hasError) {
                return Center(child: Text('Something Went Wrong!'));
              } else {
                return LoginWidget();
              }
            }),
      );
}
