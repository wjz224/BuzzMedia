import { useState, useEffect } from 'react';
import './LoginPage.css';


function LoginPage() {
    const orangeHex = require('./Orange_hexagon.png');
    const background = require('./blueHexagons4.jpg');

    //!add this to first div to add background image
    // style={{ backgroundImage: `url(${background})`,
    // height:'100vh',
    
    // fontSize:'50px',
    // backgroundSize: 'cover',
    // backgroundRepeat: 'no-repeat',}}
    return (
        <div
            className='container'
        >
            <div className='titleContainer'>
                <text className='title'>The Buzz</text>
            </div>
           
            <div className='hexContainer'>
                <text className='enter'>Enter<br></br>The Hive</text>
                <button className='loginBtn'>
                    <a href="/home">Login using google</a>
                </button>
                <img alt='logo' className='hex' style={{ width: 500 }} src={String(orangeHex)} />
                
            </div>
            
        </div>
    )
}

export default LoginPage