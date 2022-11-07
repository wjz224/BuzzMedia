TARGETFOLDER=../backend/src/main/resources
WEBFOLDERNAME=web
rm -rf $TARGETFOLDER
mkdir $TARGETFOLDER
mkdir $TARGETFOLDER/$WEBFOLDERNAME
npm run build
<<<<<<< HEAD
cp -r build/* $TARGETFOLDER/$WEBFOLDERNAME
=======
cp -r build/* $TARGETFOLDER/$WEBFOLDERNAME
>>>>>>> backend
