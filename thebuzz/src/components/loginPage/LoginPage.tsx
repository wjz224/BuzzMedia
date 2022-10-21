import { useState, useEffect } from 'react';
import './LoginPage.css';
import {
    GoogleButton,
    IAuthorizationOptions,
    isLoggedIn,
    createOAuthHeaders,
    logOutOAuthUser,
    GoogleAuth,
} from "react-google-oauth2";
import Login from '../login';
import { gapi } from 'gapi-script'



function LoginPage() {
    const clientId = process.env.CLIENT_ID;

    const orangeHex = require('./Orange_hexagon.png');
    const background = require('./honeyCombBackground.webp');

    useEffect(() => {
        function start() {
            gapi.client.init({
                clientId: clientId,
                scope: ""
            })
        };
        gapi.load('client:auth2', start);
    });

    

   

    //!add this to first div to add background image
    // style={{ backgroundImage: `url(${background})`,
    // height:'100vh',
    
    // fontSize:'50px',
    // backgroundSize: 'cover',
    // backgroundRepeat: 'no-repeat',}}
    return (
        <div
            style={{ backgroundImage: `url(${background})`,
            height:'100vh',
            
            fontSize:'50px',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',}}
            className='container'
        >
            <div className='titleContainer'>
                <text className='title'>The Buzz</text>
            </div>
           
            <div className='hexContainer'>
                <text className='enter'>Enter<br></br>The Hive</text>
                <div className='loginBtn'>
                    <Login />
                </div>
                
                {/* <button className='loginBtn'>
                    <a href="/home">Login using google</a>
                </button> */}
            <div>
                    {/* <GoogleButton
                        placeholder="demo/search.png" // Optional
                        options={options}
                        apiUrl="http://localhost:5000/google_login"
                        defaultStyle={true} // Optional
                    /> */}
                    

                </div>
                
                <img alt='logo' className='hex' style={{ width: 500 }} src={String(orangeHex)} />
                
            </div>
            
        </div>
    )
}

export default LoginPage