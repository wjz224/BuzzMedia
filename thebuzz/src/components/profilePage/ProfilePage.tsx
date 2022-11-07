import { Logout } from '@mui/icons-material';
import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../headerBar/HeaderBar.css';
import './ProfilePage.css';

const background = require('../loginPage/honeyCombBackground.webp');
const orangeHex = require('../loginPage/Orange_hexagon.png');

//!!add delete google cookies when logout button is clicked then logout will work :)
function ProfilePage(this: any) {

    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, familyName, givenName, googleId, imageUrl, name } = state;


    const [profile, setProfile] = useState({
        mEmail: "email",
        mGender: "gender",
        mNote: "note",
        mProfile: "url",
        mSex_orient: "note",
        mUser_id: 0,
        mUsername: "username"
    })

    if(profile == undefined){
        setProfile({mEmail: "email",
        mGender: "gender",
        mNote: "note",
        mProfile: "url",
        mSex_orient: "note",
        mUser_id: 0,
        mUsername: "username"})
    }



    //get the user profile using session id and email
    fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/users/${email}`,
        {
            method: "GET",
            headers: {
                'Content-type': 'application/json; charset=UTF-8'
            },
            mode: "cors",
        })
        .then((response) => response.json())
        .then((data) => {
            setProfile(data)
        });

    function goHome() {
        navigate("/home", {
            state: {
                sessionId: sessionId, email: email,
                familyName: familyName,
                givenName: givenName,
                googleId: googleId,
                imageUrl: imageUrl,
                name: name
            }
        });

    };

    function goPost() {
        navigate("/add", {
            state: {
                sessionId: sessionId, email: email,
                familyName: familyName,
                givenName: givenName,
                googleId: googleId,
                imageUrl: imageUrl,
                name: name
            }
        });

    };

    function goProfile() {
        console.log("alrady on profile")
    };

    function edit() {
        // navigate("/editprofile", { state: { sessionId: sessionId ,email: email,
        //     familyName: familyName,
        //     givenName: givenName,
        //     googleId: googleId,
        //     imageUrl: imageUrl,
        //     name: name } })
        navigate("/editprofile", {
            state: {
                sessionId: sessionId, email: email,
                googleId: googleId,
                imageUrl: imageUrl,
                name: name
            }
        })
    }

    const handleBioChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {

    }

    return (
        <div
            style={{
                backgroundImage: `url(${background})`,
                height: '100vh',
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
            }}
            className='container'
        >
            <div className='headerContainer'>
                <div>
                    <h1 className='header'>TheBuzz</h1>
                </div>
                <div className='btnContainer'>
                    <button className='btn' onClick={goHome}>Home</button>
                    <button className='btn' onClick={goPost}>Add post</button>
                    <button className='btn' onClick={goProfile}>Profile</button>
                </div>

            </div>
            <div className='hexContainer'>
                <div className='loginBtn'>
                </div>
                <img className="profileImg" alt='profile' style={{ width: 100 }} src={profile.mProfile} referrerPolicy="no-referrer" />
                {/* <input
                    type="text"
                    value={bio}
                    onChange={this.setBio}
                /> */}

                <text className='bioTitle'>bio:</text>


                {/* <text className='genTitle'>gender:</text>
                <textarea className='gender' onChange={handleBioChange}>
                    {gender}
                </textarea>
                <text className='sexTitle'>sexuality:</text> */}

                <text className='name'>{profile.mUsername}</text>
                <text className='email'>{profile.mEmail}</text>
                <text className="bio">{profile.mNote}</text>
                <text className="gender">Gender:  {profile.mGender}</text>
                <text className="sex">Sexual orientation: {profile.mSex_orient}</text>
                <div className='logoutBtn'>
                    <button><a href='/home'>Logout</a></button> 
                    <Logout />
                    <button onClick={edit}>edit Profile</button>
                </div>

                <div>



                </div>

                <img alt='logo' className='hex' style={{ width: 700 }} src={String(orangeHex)} />

            </div>

        </div>
        // <div className='container'
        // style={{ backgroundImage: `url(${background})`,
        //     height:'100vh',
        //     backgroundSize: 'cover',
        //     backgroundRepeat: 'no-repeat',}}
        // >
        //     {/* <div className='hex'>
        //         <img alt='logo'  style={{ width: 500 }} src={String(orangeHex)} />
        //     </div> */}


        //     <div className='headerContainer'>
        //     <div>
        //         <h1 className='header'>TheBuzz</h1>
        //     </div>
        //     <div className='btnContainer'>           
        //         <button className='btn' onClick={goHome}>Home</button>
        //         <button className='btn' onClick={goPost}>Add post</button>
        //         <button className='btn' onClick={goProfile}>Profile</button>
        //     </div>

        // </div>
        //     <div className='hexContainer'>
        //         <img alt='logo'  style={{ width: 500 }} src={String(orangeHex)} className='hex'/>
        //         <div className='info'>
        //             {/* <img className="profileImage" alt='profile' style={{width:40}} src={imageUrl} referrerPolicy="no-referrer"/>
        //             <text className='name'>{name}</text>
        //             <text className='email'>{email}</text> */}

        //             <text className='name'>First LastName</text>
        //             <text className='email'>email</text>
        //             <button><a href='/'>Logout</a></button>
        //             <Logout />
        //         </div>

        //         {/*  */}
        //     </div>

        // </div>
    )
}

export default ProfilePage