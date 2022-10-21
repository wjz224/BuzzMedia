import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router';
import { useLocation } from 'react-router-dom';
import './HeaderBar.css';

function HeaderBar() {

    let navigate = useNavigate();

    function goHome(){
        navigate("/home"); 
       
    };

    function goPost(){
        navigate("/add"); 
        
    };

    function goProfile(){
        navigate("/profile"); 
    };

    return (
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
    )
}

export default HeaderBar