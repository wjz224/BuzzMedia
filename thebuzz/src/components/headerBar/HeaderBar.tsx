import { useState, useEffect } from 'react';
import '../homePage/HomePage.css';


function HeaderBar() {

    return (
        <div className='headerContainer'>
                <h1 className='header'>TheBuzz</h1>
                <button className='addBtnNav'><a href="/add">add post</a></button>
                <button className='profileBtn'><a href="/profile">profile</a></button>
        </div>
    )
}

export default HeaderBar