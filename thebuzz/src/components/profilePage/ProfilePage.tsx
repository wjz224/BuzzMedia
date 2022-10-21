import { Logout } from '@mui/icons-material';
import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../headerBar/HeaderBar.css';
import './ProfilePage.css';

const background = require('../loginPage/honeyCombBackground.webp');
const orangeHex = require('../loginPage/Orange_hexagon.png');

function ProfilePage() {

    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const { email, familyName, givenName, googleId, imageUrl, name} = state;

    function goHome(){
        navigate("/home", {  state: {email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}}); 
       
    };

    function goPost(){
        navigate("/add", {  state: {email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}}); 
        
    };

    function goProfile(){
        console.log("alrady on profile")
    };
    
    return (
        <div className='container'
        style={{ backgroundImage: `url(${background})`,
            height:'100vh',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',}}
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
            <div className='infoContainer'>
                <text>{name}</text>
                <button><a href='/'>Logout</a></button>
                {/* <Logout /> */}
            </div>
            
        </div>
    )
}

export default ProfilePage