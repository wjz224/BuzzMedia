import { useState, useEffect } from 'react';
import './LoginPage.css';


function LoginPage() {
    const orangeHex = require('./Orange_hexagon.png');
    const background = require('./blueHexagons.jpg');


    return (
        <div style={{ backgroundImage: `url(${background})`,
            height:'100vh',
            
            fontSize:'50px',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',}}
        >
            <div className='hexContainer'>
                <text className='enter'>Enter The Hive</text>
                <button className='loginBtn'>Login using google</button>
                <img alt='logo' className='hex' style={{ width: 500 }} src={String(orangeHex)} />
                
            </div>
            
        </div>
    )
}

export default LoginPage