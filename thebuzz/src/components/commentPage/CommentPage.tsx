import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router';
import './CommentPage.css';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { IconButton } from '@mui/material';
import { green } from '@mui/material/colors';
import { useLocation } from 'react-router-dom';

function CommentPage() {
    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const {sessionId, email, familyName, givenName, googleId, imageUrl, name} = state;
    
    function goBack(){
        navigate('/home', {  state: {sessionId: sessionId, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}});
    };

    return (

        <div className='container'>
            {/*!!TODO add a back button which goes to home */}
            <div className='headerContainer'>
            <div>
                <IconButton aria-label="comment" onClick={() => goBack()} >
                    <ArrowBackIcon fontSize="large" className="header"/>
                </IconButton>
                
            </div>
                
        </div>
            
            {/*!!TODO Display the original post at the top distinct form comments */}
            {/*!!TODO Display list of comments */}
            {/*!!TODO Display text box where user can add their own comment at the end */}
                
        </div>
    )
}

export default CommentPage