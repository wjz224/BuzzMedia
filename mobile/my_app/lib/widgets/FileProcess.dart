import 'package:file_picker/file_picker.dart';
import 'package:path_provider/path_provider.dart';
import 'package:open_file/open_file.dart';
import 'dart:convert';
import 'dart:typed_data';
import 'dart:io';

class FileProcess {
  static bool isFolderCreated = false;
  static Directory? directory;

  static checkDocumentFolder() async {
    try {
      if (!isFolderCreated) {
        directory = await getApplicationDocumentsDirectory();
        await directory!.exists().then((value) {
          if (value) directory!.create();
          isFolderCreated = true;
        });
      }
    } catch (e) {
      print(e.toString());
    }
  }

  static Future<File> downloadFile(String file64, String fileName) async {
    Uint8List bytes = base64.decode(file64);
    await checkDocumentFolder();
    String dir =
        directory!.path + "/" + fileName;
        print(dir);
    File file = new File(dir);
    if (!file.existsSync()) file.create();
    await file.writeAsBytes(bytes);
    return file;
  }
  static void openFile(String fileName) {
        String dir =
            directory!.path + "/${fileName}";
        print(dir); 
        OpenFile.open(dir);
      }
}