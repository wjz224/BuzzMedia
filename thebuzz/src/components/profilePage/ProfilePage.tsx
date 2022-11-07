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
        <div
            style={{ backgroundImage: `url(${background})`,
            height:'100vh',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',}}
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
                    <img className="profileImg" alt='profile' style={{width:100}} src={imageUrl} referrerPolicy="no-referrer"/>
                    <text className='name'>{name}</text>
                    <text className='email'>{email}</text>
                        <text className="bio">Bio:  N/A</text>
                        <text className="gender">Gender:  N/A</text>
                        <text className="sex">Sexual orientation:  N/A</text>
                    <div className='logoutBtn'>
                        <button><a href='/'>Logout</a></button>
                        <Logout />
                        <button>edit Profile</button>
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