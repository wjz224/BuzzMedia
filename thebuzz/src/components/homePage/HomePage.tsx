import { useState, useEffect } from 'react';
import '../headerBar/HeaderBar.css';
import './HomePage.css';
import {Box, IconButton, List, ListItem, ListItemText, Modal, Typography } from '@mui/material';
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
    const {sessionId, email, imageUrl} = state;

    const [open, setOpen] = useState(false);
    const handleOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };
    function goHome(){
     console.log('already home')
       
    };

    function goPost(){
        navigate("/add", {  state: {sessionId:sessionId, email: email, imageUrl: imageUrl}}); 
        
    };

    function goProfile(){
        navigate("/profile", {  state: {sessionId: sessionId, email: email, imageUrl: imageUrl }}); 
    };
    

    //State for the list of messages
    const [messages, setMessages] = useState<any[]>([]);
    const [isLiked, setisliked] = useState(false);
    const [isDisliked, setisDisliked] = useState(false);
    const [fakeLikes, setFakeLikes] = useState(2);
    const [comments, setComments] = useState('');

    //useEffect runs inside code whenever the page is loaded
    useEffect(() => {
        //GET request. Gets messages from database
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/posts`,
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
        
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/comments/${id}`,
        {
            method: "GET"
            
        })
        .then((response) => response.json())
        .then((data) => {
                // setComments(data.mComment)
            console.log(data)
        });
        if(id == 1){
            setComments('???? what')
        }
        else{
            setComments('trueeeeee')
        }
        setOpen(true)
        //navigate('/comments', {  state: {sessionId: sessionId, email: email, imageUrl: imageUrl }})
    }

    /**
     * Updates the number of likes. Called on button click.
     * @param id 
     */
    function LikeMsg(id: any){
        console.log(id)
        
        if(isLiked == false){
            setisliked(true);
            if(isDisliked == true){
                setisDisliked(false)
                setFakeLikes(fakeLikes + 2)
            }
            else{
                setFakeLikes(fakeLikes+1);
            }
            
             //PUT request
            fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/${id}/likes`,
            {
                method: "PUT",
            })

        //      //reloads page
            window.location.reload()
    
        }
        else{
            setisliked(false);
            setFakeLikes(fakeLikes - 1)
        }
       
       
    }

    /**
     * Updates the number of likes. Called on button click.
     * @param id 
     */
     function UnLikeMsg(id: number){

        if(isDisliked==false){
            setisDisliked(true)
            if(isLiked == true){
                setisliked(false)
                setFakeLikes(fakeLikes - 2)
            }
            else{
                setFakeLikes(fakeLikes - 1);
            }
            
             //PUT request
            fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/${id}/dislikes`,
            {
                method: "PUT",
            })
            //reloads page
            window.location.reload()
        }
        else{
            setisDisliked(false);
            setFakeLikes(fakeLikes + 1)
        }
       
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
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box className="modalBox">
                    <Typography id="modal-modal-title" variant="h3" component="h2">
                    Comments:
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        {comments}
                    </Typography>
                    {/* <List sx={{ width: '100%', maxWidth: 900, bgcolor: '#fff9d0' }}>
            <div className='postTextContainer'>
                {comments.map((value) => (
                    <><ListItem
                        key={value}
                        disableGutters
                        secondaryAction={<div>
                           
                        </div>}
                    >
                        <ListItemText 
                            primary={`${value['mComment']}`}
                            // secondary={`${value['mText']}`}
                            // className={'postText'}
                        />
                    </ListItem>
                    <Divider  component="li" /></>
                    
                ))}
                
                </div>
            </List> */}
                </Box>
            </Modal>

            <List sx={{ width: '100%', maxWidth: 900, bgcolor: '#fff9d0' }}>
            <div className='postTextContainer'>
                {messages.map((value) => (
                    <><ListItem
                        key={value}
                        disableGutters
                        secondaryAction={<div>
                            <IconButton color={isLiked ? "primary" : "default"} aria-label="thumbsUp" data-testid='likebtn' onClick={() => LikeMsg(value['mPost_id'])}>
                                <ThumbUpIcon />
                            </IconButton>
                            {/* <text>{value['mLikes']}</text> */}
                            <text>{value['mLikes'] - value['mDislikes']}</text>
                            <IconButton color={isDisliked ? "primary" : "default"} aria-label="thumbsDown" data-testid='likebtn' onClick={() => UnLikeMsg(value['mPost_id'])}>
                                <ThumbDownIcon />
                            </IconButton>
                            <IconButton aria-label="comment" onClick={() => comment(value['mPost_id'])}>
                                <CommentIcon />
                            </IconButton>
                            {/* <IconButton aria-label="comment" onClick={comment}>
                                <CommentIcon />
                            </IconButton> */}
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

