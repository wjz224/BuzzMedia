

import 'package:flutter/material.dart';
import 'package:my_app/model/user_other.dart';
import 'package:my_app/net/put_change_profile.dart';

class TextFieldWidget extends StatefulWidget {
  final String label;
  final String text;
  final int maxLines;
  UserOther user;



  TextFieldWidget({
    Key? key,
    required this.label,
    required this.text,
    this.maxLines = 1,
    required this.user,
  }) : super(key: key);


  @override 
  _TextFieldWidgetState createState() => _TextFieldWidgetState();

}

class _TextFieldWidgetState extends State<TextFieldWidget>{
  late final TextEditingController controller;

  @override
  void initState(){
    super.initState();
    controller = TextEditingController(text: widget.text);
  }

  @override
  void dispose(){
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => Column(crossAxisAlignment: CrossAxisAlignment.start,
   children: [
    Text(
      widget.label,
      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 13),
    ),
    const SizedBox(height: 8),
    TextField(
      
    controller: controller,
    maxLines: widget.maxLines,
    onChanged: (text){
      setState(() {
        putChangeProfile(widget.user.gender,widget.user.sexualOrientation, text ,widget.user.email, widget.user.sessionID);
      });
    },
    decoration: InputDecoration(border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)))),
   ]);
}