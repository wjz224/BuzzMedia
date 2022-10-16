import React, {useState} from 'react';

/**
 * Component for adding messages.
 * @component 
 */

function AddMessage(){

    //States for title and content
    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")

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
        
        //POST request
        fetch("https://thebuzzomega.herokuapp.com/messages",
            {
                method: "POST",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
                body: JSON.stringify(subData)
            })

            //reloads page
            window.location.reload()
    }

    //"Main" method. Whatever is in the div is run whenever the component is used in App.tsx
    //Puts title, then a form. The form has two textbboxes, one for title and one for content. 
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