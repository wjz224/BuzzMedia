import 'package:flutter/material.dart';
import 'package:my_app/model/user_other.dart';
import 'package:my_app/net/put_change_profile.dart';


class SexualOrientationDropDown extends StatefulWidget {
  
  final List<String> list;
  UserOther user;
  //final ValueChanged<String> onChanged;
  SexualOrientationDropDown({
  required this.list, required this.user});
  



  @override
  State<SexualOrientationDropDown> createState() => _SexualOrientationDropDownState();
}

class _SexualOrientationDropDownState extends State<SexualOrientationDropDown> {
  String dropdownValue = "change";
  void initState(){
    super.initState();
    dropdownValue = widget.user.sexualOrientation;
  }
  

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
      
      value: dropdownValue,
      icon: const Icon(Icons.arrow_downward),
      
      style: const TextStyle(color: Colors.black),
      underline: Container(
        height: 2,
      ),
      onChanged: (String? value) {
        Null;
        // This is called when the user selects an item.
        setState(() {
          dropdownValue = value!;
          putChangeProfile(widget.user.gender,value,widget.user.bio,widget.user.email, widget.user.sessionID);
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
