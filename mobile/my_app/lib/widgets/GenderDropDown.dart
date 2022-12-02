import 'package:flutter/material.dart';
import 'package:my_app/model/user_other.dart';
import 'package:my_app/net/put_change_profile.dart';


class GenderDropDown extends StatefulWidget {
  
  final List<String> list;
  UserOther user;
  
  //final ValueChanged<String> onChanged;
  GenderDropDown({
  required this.list, required this.user});
  



  @override
  State<GenderDropDown> createState() => _GenderDropDownState();
}

class _GenderDropDownState extends State<GenderDropDown> {
  String dropdownGender = "change";
  void initState(){
    super.initState();
    dropdownGender = widget.user.gender;
  }
  

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
      
      value: dropdownGender,
      icon: const Icon(Icons.arrow_downward),
      
      style: const TextStyle(color: Colors.black),
      underline: Container(
        height: 2,
      ),
      onChanged: (String? value) {
        Null;
        // This is called when the user selects an item.
        setState(() {
          dropdownGender = value!;
          putChangeProfile(value,widget.user.sexualOrientation,widget.user.bio,widget.user.email, widget.user.sessionID);
            },
        );
      },
      items: widget.list.map<DropdownMenuItem<String>>((String value) {
        return DropdownMenuItem<String>(
          value: value,
          child: Text(value),
        );
      }).toList(),
    );
  }
}
