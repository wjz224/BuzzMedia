import React, { useState, useEffect } from 'react';

function EditMsg(){

    const [id, setId] = useState("")
    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")

    const handleChangeId = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setId(e.target.value)
    }

    const handleChangeContent = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setContent(e.target.value)
    }

    function submit(){
        var subData = 
        {
            mMessage: content
        }
        
        fetch("https://thebuzzomega.herokuapp.com/messages/" + id,
            {
                method: "Put",
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
            <p>Update Message:</p>
            <form>
                <label>
                    Message Id: <input type="text" value={id} onChange={handleChangeId}/>
                    Content: <input type="text" value={content} onChange={handleChangeContent}/>
                </label>
            </form>
            Updated Message: {id} | {title} | {content}   
            <button data-testid = 'updatebtn' onClick={submit}> Update</button>
        </div>
    )

}

export default EditMsg