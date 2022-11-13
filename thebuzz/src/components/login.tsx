import { GoogleLogin } from 'react-google-login'
import { useNavigate } from 'react-router-dom';

const clientId = (process.env.REACT_APP_CLIENT_ID as string)

function Login() {

    let navigate = useNavigate();


    const onSuccess = (res: any) => {
         console.log("Login Success", res.tokenId)
         

        fetch(`https://thebuzzomega.herokuapp.com/verify/${res.tokenId}`,
            {
                method: "POST",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
            })
            .then((response) => response.json())
            .then((data) => {
                
                console.log(Response)
                console.log(data)
                
                navigate('/profile', 
                    { state: {
                        sessionId: data.mData,
                        email: res.profileObj.email,
                        familyName: res.profileObj.familyName,
                        givenName: res.profileObj.givenName,
                        googleId: res.profileObj.googleId,
                        imageUrl: res.profileObj.imageUrl,
                        name: res.profileObj.name } 
                    })

            });

         

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