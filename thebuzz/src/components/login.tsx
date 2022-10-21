import { GoogleLogin } from 'react-google-login'
import { useNavigate } from 'react-router-dom';

const clientId = (process.env.REACT_APP_CLIENT_ID as string)

function Login() {

    console.log(clientId);
    
    let navigate = useNavigate();

    const onSuccess = (res: any) => {
         console.log("Login Success", res.profileObj)
         navigate('/profile', 
            { state: {
                email: res.profileObj.email,
                familyName: res.profileObj.familyName,
                givenName: res.profileObj.givenName,
                googleId: res.profileObj.googleId,
                imageUrl: res.profileObj.imageUrl,
                name: res.profileObj.name } 
            });
         navigate('/home', 
            { state: {
                email: res.profileObj.email,
                familyName: res.profileObj.familyName,
                givenName: res.profileObj.givenName,
                googleId: res.profileObj.googleId,
                imageUrl: res.profileObj.imageUrl,
                name: res.profileObj.name } 
            }
        )
    }

    const onFailure = (res: any) => {
        console.log("Login Failed", res)
   }
    
    return(
        <div id="SignInButton">
            <GoogleLogin
                clientId={clientId}
                buttonText="Login"
                onSuccess={onSuccess}
                onFailure={onFailure}
                cookiePolicy={'single_host_origin'} 
                isSignedIn={true}

            />
        </div>
    )
}

export default Login