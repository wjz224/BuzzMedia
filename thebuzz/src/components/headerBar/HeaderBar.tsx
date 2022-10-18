import { useState, useEffect } from 'react';
import './HeaderBar.css';


function HeaderBar() {

    return (
        <div className='headerContainer'>
            <div>
                <h1 className='header'>TheBuzz</h1>
            </div>
            <div className='btnContainer'>
                
                <button className='btn'><a href="/home">home</a></button>
                <button className='btn'><a href="/add">add post</a></button>
                <button className='btn'><a href="/profile">profile</a></button>
            </div>
                
        </div>
    )
}

export default HeaderBar