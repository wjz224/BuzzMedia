import { info } from 'console';
import { useLocation, useNavigate } from 'react-router-dom';
import './EditProfile.css';
const background = require('../loginPage/honeyCombBackground.webp');

/**
 * Component for displaying messages and message information, as well as liking and deleting messages.
 * @component 
 */


function EditProfile() {

    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, bio, gender, sexuality, googleId, imageUrl, name} = state;
    
    console.log(imageUrl);
    //!Put request function when submit button is pressed then navigate back to profile
    function submit () {
        navigate('/profile', {  state: { sessionId: sessionId ,email: email,
            bio: bio,
            gender: gender,
            sexuality: sexuality,
            googleId: googleId,
            imageUrl: imageUrl,
            name: name }})
    }
    function cancel () {
        navigate('/profile', {  state: { sessionId: sessionId ,email: email,
            bio: bio,
            gender: gender,
            sexuality: sexuality,
            googleId: googleId,
            imageUrl: imageUrl,
            name: name }})
    }
    //displays a form where additional info can be added to someones profile
    return (
        <div   
            style={{ backgroundImage: `url(${background})`,
            height:'100vh',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',}}
        >

            <button onClick={cancel}>cancel</button>
            <button onClick={submit}>cancel</button>


        </div>
    )
}


export default EditProfile;

