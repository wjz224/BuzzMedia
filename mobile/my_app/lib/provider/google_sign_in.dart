import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';

//Logic to for the google sign in button that will direct to a google login page
class GoogleSignInProvider extends ChangeNotifier{
  final googleSignIn = GoogleSignIn();

  GoogleSignInAccount? _user; //Stores profileInformation about the user

  GoogleSignInAccount get user => _user!;

  /**
   * Code block to go to the google sign in page when button is clicked
   */

  Future googleLogin() async{
    try{
      final googleUser = await googleSignIn.signIn(); //shows the pop up to show account
    if (googleUser == null) return;

    _user = googleUser; //save the user inside the account we made in line 9

    final googleAuth = await googleUser.authentication;

    final credential = GoogleAuthProvider.credential( //add credentials
      accessToken: googleAuth.accessToken,
      idToken: googleAuth.idToken,
    );

    await FirebaseAuth.instance.signInWithCredential(credential);

    notifyListeners();
    }catch (e){
      print(e.toString());
    }
    

  }
  Future logout() async{
    await googleSignIn.disconnect();
    FirebaseAuth.instance.signOut();
  }

}
