import { useState, useEffect } from 'react';
import HeaderBar from '../headerBar/HeaderBar';
import './ProfilePage.css';


function ProfilePage() {

    return (
        <div className='container'>
            <HeaderBar />
            <div className='infoContainer'>
                <text>User Profile</text>
            </div>
            
        </div>
    )
}

export default ProfilePage