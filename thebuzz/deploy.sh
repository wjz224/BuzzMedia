<<<<<<< HEAD
+TARGETFOLDER=../backend/src/main/resources
 WEBFOLDERNAME=web
 rm -rf $TARGETFOLDER
 mkdir $TARGETFOLDER
 mkdir $TARGETFOLDER/$WEBFOLDERNAME
 npm run build
 cp -r build/* $TARGETFOLDER/$WEBFOLDERNAME
=======
TARGETFOLDER=../backend/src/main/resources
WEBFOLDERNAME=web
rm -rf $TARGETFOLDER
mkdir $TARGETFOLDER
mkdir $TARGETFOLDER/$WEBFOLDERNAME
npm run build
cp -r build/* $TARGETFOLDER/$WEBFOLDERNAME
>>>>>>> origin/react
