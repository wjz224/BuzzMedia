import 'package:flutter/material.dart';
import 'package:my_app/net/put_change_profile.dart';


class DropdownButtonField extends StatefulWidget {
  
  final List<String> list;
  String dropdownValue;
  final ValueChanged<String> onChanged;
  DropdownButtonField({
  required this.list, required this.dropdownValue, required this.onChanged});
  



  @override
  State<DropdownButtonField> createState() => _DropdownButtonFieldState();
}

class _DropdownButtonFieldState extends State<DropdownButtonField> {
  void initState(){
    super.initState();
    String dropdownValue = widget.list.first;
  }
  

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
      
      value: widget.dropdownValue,
      icon: const Icon(Icons.arrow_downward),
      
      style: const TextStyle(color: Colors.black),
      underline: Container(
        height: 2,
      ),
      onChanged: (String? value) {
        // This is called when the user selects an item.
        setState(() {
          widget.dropdownValue = value!;
          putChangeProfile(value, "random", "testing",
                    "yap224@lehigh.edu", "-1909482473");
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
