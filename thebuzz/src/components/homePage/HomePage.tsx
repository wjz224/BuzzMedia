import { useState, useEffect } from 'react';
import '../headerBar/HeaderBar.css';
import './HomePage.css';
import {IconButton, List, ListItem, ListItemText } from '@mui/material';
import CommentIcon from '@mui/icons-material/Comment';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import ThumbDownIcon from '@mui/icons-material/ThumbDown';
import Divider from '@mui/material/Divider';
import { Navigate, useLocation, useNavigate } from 'react-router-dom';
const background = require('../loginPage/honeyCombBackground.webp');

/**
 * Component for displaying messages and message information, as well as liking and deleting messages.
 * @component 
 */


function HomePage() {

    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const {sessionId, email, familyName, givenName, googleId, imageUrl, name} = state;
    function goHome(){
     console.log('already home')
       
    };

    function goPost(){
        navigate("/add", {  state: {sessionId:sessionId, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}}); 
        
    };

    function goProfile(){
        navigate("/profile", {  state: {sessionId: sessionId, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}}); 
    };
    

    //State for the list of messages
    const [messages, setMessages] = useState<any[]>([]);

    //useEffect runs inside code whenever the page is loaded
    useEffect(() => {
        //GET request. Gets messages from database
        fetch("https://thebuzzomega.herokuapp.com/posts",
            {
                method: "GET"
            })
            .then((response) => response.json())
            .then((data) => {
                setMessages(data.mData)
                console.log(data.mData)
            });

    }, []
    );

    function comment(id: number){
        navigate('/comments', {  state: {sessionId: sessionId, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}})
    }

    /**
     * Updates the number of likes. Called on button click.
     * @param id 
     */
    function LikeMsg(id: number){
        //PUT request
        fetch("https://thebuzzomega.herokuapp.com/messages/" + id + '/likes',
            {
                method: "PUT",
            })
        
        //reloads page
        window.location.reload()
    }

    /**
     * Updates the number of likes. Called on button click.
     * @param id 
     */
     function UnLikeMsg(id: number){
        //PUT request
        fetch("https://thebuzzomega.herokuapp.com/messages/" + id + '/dislikes',
            {
                method: "PUT",
            })
        
        //reloads page
        window.location.reload()
    }

    //Standalone function for deleting a message. Called on button click

    /**
     * Deletes a message. Called on button click.
     * @param id 
     */
    function DeleteMsg(id: number){
        //DELETE request
        fetch("https://thebuzzomega.herokuapp.com/messages/" + id,
            {
                method: "DELETE",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                }
            })
            
        //reloads page
        window.location.reload()
    }

    //This is like the main function. Whatever is in div gets run when the component is used in App.tsx
    //Prints a title, and maps the messages json to a list format
    return (
        <div   
            style={{ backgroundImage: `url(${background})`,
            height:'100vh',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',}}
        >
            
            {/* <div className='postsContainer'>
                {Array.isArray(messages)
                    ? messages.map((item) => (
                        <li key={item['mId']}>
                            {item['mId']} | {item['mTitle']} | {item['mContent']} | Likes: {item['mLikes']} <button data-testid='likebtn' onClick={() => LikeMsg(item['mId'])}> Like</button> <button data-testid='likebtn' onClick={() => UnLikeMsg(item['mId'])}> UnLike</button> <button data-testid = 'deletebtn' onClick={() => DeleteMsg(item['mId'])}> Delete</button> 
                        </li>
                    ))
                    : messages 
                }
            </div> */}
        <div className='postsContainer'>
            <List sx={{ width: '100%', maxWidth: 900, bgcolor: '#fff9d0' }}>
            <div className='postTextContainer'>
                {messages.map((value) => (
                    <><ListItem
                        key={value}
                        disableGutters
                        secondaryAction={<div>
                            <IconButton aria-label="thumbsUp" data-testid='likebtn' onClick={() => LikeMsg(value['mId'])}>
                                <ThumbUpIcon />
                            </IconButton>
                            <text>{value['mLikes']}</text>
                            <IconButton aria-label="thumbsDown" data-testid='likebtn' onClick={() => UnLikeMsg(value['mId'])}>
                                <ThumbDownIcon />
                            </IconButton>
                            <IconButton aria-label="comment" onClick={() => comment(value['mId'])}>
                                <CommentIcon />
                            </IconButton>
                        </div>}
                    >

                        <ListItemText 
                            primary={`User ${value['mPost_id']}'s post: ${value['mTitle']}`}
                            secondary={`${value['mText']}`}
                            className={'postText'}
                        />
                    </ListItem>
                    <Divider  component="li" /></>
                    
                ))}
                
                </div>
            </List>
        </div>
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
            
        </div>
    )
}



export default HomePage;

