import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router';
import './CommentPage.css';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { IconButton } from '@mui/material';
import { green } from '@mui/material/colors';

function CommentPage() {
    let navigate = useNavigate();
    
    function goBack(){
        navigate('/home');
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