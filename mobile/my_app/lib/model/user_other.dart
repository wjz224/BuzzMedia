import 'package:flutter/cupertino.dart';

class UserOther{
  String userName;
  String gender;
  String sexualOrientation;
  String bio;
  String sessionID;



  UserOther({
    required this.userName,
    required this.gender,
    required this.sexualOrientation,
    required this.bio,
    required this.sessionID,
  });

  void setSessionID(String sessionID){
      this.sessionID = sessionID;
  }
  

}