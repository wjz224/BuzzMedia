import 'package:flutter/cupertino.dart';

class UserOther{
  int user_ID;
  String userName;
  String email;
  String gender;
  String sexualOrientation;
  String bio;
  String profile;
  String sessionID = "1569641334";
  



  UserOther(
    this.user_ID,
    this.userName,
    this.email,
    this.sexualOrientation,
    this.gender,
    this.bio,
    this.profile
  );

  void setSessionID(String sessionID){
      this.sessionID = sessionID;
  }
  
  factory UserOther.fromJson(dynamic json) {
		return UserOther(json['mUser_id'] as int, json['mUsername'] as String, json['mEmail'] as String, json['mSex_orient'] as String, json['mGender'] as String, json['mNote'] as String,json['mProfile'] as String);
	}


  @override
  String toString() {
    return '{ ${this.userName}, ${this.gender}, ${this.sexualOrientation},${this.bio},${this.sessionID}}';
  }

}