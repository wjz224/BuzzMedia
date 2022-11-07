import React, {useState} from 'react';
import { useNavigate } from 'react-router';
import { useLocation } from 'react-router-dom';
import '../headerBar/HeaderBar.css';
import './AddMessage.css';

/**
 * Component for adding messages.
 * @component 
 */

function AddMessage(){

    let navigate = useNavigate();
    //States for title and content
    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")
    
    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, familyName, givenName, googleId, imageUrl, name} = state;

    function goHome(){
        navigate("/home", {  state: {sessionId: sessionId, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}} ); 
       
    };

    function goPost(){ 
        console.log('already on post')
    };

    function goProfile(){
        navigate("/profile", {  state: {sessionId: sessionId, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}} ); 
    };

    //Handler method for when the title state changes. Allows for "live typing"
    const handleChangeTitle = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setTitle(e.target.value)
    }

    //Handler method for when the content state changes. Allows for "live typing"
    const handleChangeContent = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setContent(e.target.value)
    }

    /**
     * Submits form data to the database for adding a message. Called on button click.
     */
    function submit(){
        //New json for added title and content
        var subData = 
        {
            mTitle: title,
            mMessage: content
        }
        console.log(sessionId);
        //POST request
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/posts`,
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

            navigate("/home", {state: {sessionId: sessionId, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}}); 
    }

    //"Main" method. Whatever is in the div is run whenever the component is used in App.tsx
    //Puts title, then a form. The form has two textbboxes, one for title and one for content. 
    return(
        <div>
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
            <div className='container'>
                <form>
                    <p>Add Message:</p>
                    <label>
                        Title: <input type="text" value={title} onChange={handleChangeTitle}/>
                        Content: <input type="text" value={content} onChange={handleChangeContent}/>
                    </label>
                </form>
                New Message: {title} | {content}   
                <button data-testid = 'addbtn' onClick={submit}>add</button>
            </div>
            
        </div>
    )
    

    
}

export default AddMessage