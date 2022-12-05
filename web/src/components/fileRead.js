import { GoogleLogin } from "react-google-login";
import { Navigate } from "react-router-dom";
import { gapi } from "gapi-script";

const googleDiscover = ['https://www.googleapis.com/discovery/v1/apis/drive/v3/rest'];
const userDrive = 'https://www.googleapis.com/auth/drive.metadata.readonly';

const handleClientLoad = () => {
  gapi.load('client:auth2', initClient);
};

const initClient = () => {
    setIsLoadingGoogleDriveApi(true);
    gapi.client
      .init({
        googleDiscover: googleDiscover,
        userDrive: userDrive,
        apiKey: API_KEY,
        clientId: CLIENT_ID,
      })
      .then(
        function () {
          gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

          updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        },
        function (error) {}
      );
  };