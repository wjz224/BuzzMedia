import { GoogleLogout } from 'react-google-login'

const clientId = (process.env.CLIENT_ID as string);

function Logout() {

    const onSuccess = () => {
        console.log("Logout succesfull")
    }

    return (
        <div id="signOutButton">
            <GoogleLogout
                clientId={clientId}
                buttonText="Logout"
                onLogoutSuccess={onSuccess}
            />
        </div>
    )
    
}

export default Logout