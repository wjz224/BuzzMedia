import { useState, useEffect } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
// import './LoginPage.css';




function OtherProfile() {
    
    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, imageUrl } = state;

    const background = require('../loginPage/honeyCombBackground.webp');

    let params = useParams();

    const [user, setUser] = useState({
        mEmail: "email@lehigh.edu",
        mGender: "N/A",
        mNote: "N/A",
        mProfile: "url",
        mSex_orient: "N/A",
        mUser_id: 0,
        mUsername: "username"
    })

    function goHome() {
        navigate("/home", {
            state: {
                sessionId: sessionId, 
                email: email,
                imageUrl: imageUrl,
            }
        });

    };

    //console.log(params.username)
    fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/users/${params.username}`,
        {
            method: "GET"

        })
        .then((usrResponse) => usrResponse.json())
        .then((usrData) => {
            console.log(usrData)
            setUser(usrData);

        })


    return (
        <div
            style={{
                backgroundImage: `url(${background})`,
                height: '100vh',

                fontSize: '50px',
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
            }}
            className='container'
        >
            <div className='profileContainer'>
                <img src={user.mProfile}></img>
                <text>{user.mUsername}</text>
                <text>{user.mEmail}</text>
            </div>
            <text>{user.mEmail}</text>
            <button onClick={goHome}>back</button>


        </div>
    )
}

export default OtherProfile