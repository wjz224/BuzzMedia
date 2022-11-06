import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router';
import './CommentPage.css';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { IconButton, ListItem, ListItemAvatar, ListItemText } from '@mui/material';
import Divider from '@mui/material/Divider';
import { green } from '@mui/material/colors';
import { useLocation, useParams } from 'react-router-dom';
import EditIcon from '@mui/icons-material/Edit';


const background = require('../loginPage/honeyCombBackground.webp');

// import CommentIcon from '@mui/icons-material/Comment;

function CommentPage() {
    let navigate = useNavigate();
    let params = useParams();

    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, imageUrl } = state;

    const [comments, setComments] = useState<any[]>([]);
    // const [addData, setAddData] = useState({mComment_id": 0
    // "mPost_id" = int
    // "mUser_id" = int
    // "mComment" = String})
    const [newComment, setNewComment] = useState("test Comment");
    const [userId, setUserId] = useState(0)
    // const [idVar, setIdVar] = useState(0)

    // setIdVar(parseInt(params.postId, 10))

    //fetch userId using the email and set state userId
    function getUserId() {
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/users/${email}`,
            {
                method: "GET",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
            })
            .then((response) => response.json())
            .then((data) => {
                //console.log(data.mUser_id)
                setUserId(data.mUser_id)
            });

    }

    const handleChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setNewComment(e.target.value)
    }

    function goBack() {
        navigate('/home', { state: { sessionId: sessionId, email: email, imageUrl: imageUrl } });
    };

    function editComment(id: any) {
        navigate(`/edit/${id}`, { state: { sessionId: sessionId, email: email, imageUrl: imageUrl } });
    };

    function comment(id: any) {
        getUserId();
        var subData =
        {
            // mComment_id: newComment,
            //mPost_id: id,
            //mUser_id: userId,
            mMessage: newComment
        }
        //console.log('hitcommentbutton')
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/comments/${id}`,
            {
                method: "POST",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
                body: JSON.stringify(subData)
            })
            .then((response) => response.json())
            .then((data) => {
                console.log(data)
            });
    }


    getComments();

    function getComments() {
        getUserId();
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/comments/${params.postId}`,
            {
                method: "GET"

            })
            .then((response) => response.json())
            .then((data) => {
                setComments(data)
                console.log()
                console.log(data)
                //!! store user Id and info in local storage: if userId matches on comment set com.editable=true
                comments.forEach((com) => {
                    if (com.mUser_id == userId) {
                        com.editable = true;
                        //console.log(com)
                    }
                    else {
                        com.editable = false;
                        //console.log(com);
                        
                    }
                })

            });

    }






    return (

        <div className='container'
            style={{
                backgroundImage: `url(${background})`,
                height: '100vh',
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
            }}>
            {/*!!TODO add a back button which goes to home */}
            <div className='headerContainer'>
                <div>
                    <IconButton aria-label="comment" onClick={() => goBack()} >
                        <ArrowBackIcon fontSize="large" className="header" />
                    </IconButton>
                    <text className='commentTitle'>Comments</text>
                </div>
            </div>
            <div className='commentContainer'>
                {comments.map((value) => {
                    
                    if (value.mUser_id == userId) {
                        //console.log('editable == true')
                        return (
                            <><ListItem
                                key={value}
                                disableGutters
                                secondaryAction={<div>

                                    <IconButton aria-label="comment" onClick={() => editComment(value['mComment_id'])}>
                                        <EditIcon/>
                                    </IconButton>

                                </div>}
                            >

                                <ListItemText
                                    primary={`${value['mComment']}`}
                                    // secondary={`${value['mText']}`}
                                    className={'postText'}
                                />
                            </ListItem>
                                <Divider component="li" /></>
                        )
                    }
                    else {
                        //console.log(value)
                        return (
                            <><ListItem
                                key={value}
                                disableGutters
                                secondaryAction={<div>

                                    {/* <IconButton aria-label="comment" onClick={() => comment(value['mPost_id'])}>
                                <text> + </text>
                            </IconButton> */}

                                </div>}
                            >

                                <ListItemText
                                    primary={`${value['mComment']}`}
                                    // secondary={`${value['mText']}`}
                                    className={'postText'}
                                />
                            </ListItem>
                                <Divider component="li" /></>
                        )
                    }




                })}
            </div>
            <div className="inputContainer">
                <input className="input" placeholder="Add a comment..." onChange={handleChange}></input>

            </div>
            <button onClick={() => comment(params.postId)}>submit</button>

        </div>
    )
}

export default CommentPage