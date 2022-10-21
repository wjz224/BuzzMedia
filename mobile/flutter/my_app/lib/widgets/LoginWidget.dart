import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_app/provider/google_sign_in.dart';
import 'package:my_app/pages/HomePage.dart';
import 'package:my_app/net/post_items_api.dart';
import 'package:provider/provider.dart';

class LoginWidget extends StatelessWidget {
  
 @override
  Widget build(BuildContext context) {
    final ButtonStyle style =
        ElevatedButton.styleFrom(primary: Colors.white, 
                                 onPrimary: Colors.black, 
                                 minimumSize: Size(double.infinity, 50));

    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          ElevatedButton(
            style: style,
            onPressed: () {
              //function to open up google OAuth when button is pressed
              final provider = Provider.of<GoogleSignInProvider>(context, listen: false);
              provider.googleLogin();
            },
            child: const Text('Sign in with Google'),
          ),
        ],
      ),
    );
  }

 
}






