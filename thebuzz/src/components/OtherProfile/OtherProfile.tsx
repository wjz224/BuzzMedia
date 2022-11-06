import { useState, useEffect } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import './OtherProfile.css';




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
                justifyContent: 'center',
                fontSize: '50px',
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
            }}
            className='container'
        >
            <div className='profileContainer'>
                <div>
                    <img src={user.mProfile}></img>
                </div>
                <div>
                    <text>{user.mUsername}</text>
                </div>
                <div>
                    <text>{user.mEmail}</text>
                </div>
                

                <button onClick={goHome}>back</button>
            </div>
            
            


        </div>
    )
}

export default OtherProfile