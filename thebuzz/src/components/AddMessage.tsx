import React, { useState, useEffect } from 'react';

function AddMessage(){

    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")

    const handleChangeTitle = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setTitle(e.target.value)
    }

    const handleChangeContent = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setContent(e.target.value)
    }

    function submit(){
        var subData = 
        {
            mTitle: title,
            mMessage: content
        }
        
        fetch("https://thebuzzomega.herokuapp.com/messages",
            {
                method: "POST",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
                body: JSON.stringify(subData)
            })
            window.location.reload()
    }

    return(
        <div>
            <form>
                <p>Add Message:</p>
                <label>
                    Title: <input type="text" value={title} onChange={handleChangeTitle}/>
                    Content: <input type="text" value={content} onChange={handleChangeContent}/>
                </label>
            </form>
            New Message: {title} | {content}   
            <button data-testid = 'addbtn' onClick={submit}> Add</button>
        </div>
    )
    

    
}

export default AddMessage