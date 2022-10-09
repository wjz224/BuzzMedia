import React, {useState} from 'react';

/**
 * Component for editing messages.
 * @component 
 */

function EditMsg(){

    //States for id and content
    const [id, setId] = useState("")
    const [content, setContent] = useState("")

    //Handler method for when the id state changes. Allows for "live typing"
    const handleChangeId = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setId(e.target.value)
    }

    //Handler method for when the content state changes. Allows for "live typing"
    const handleChangeContent = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setContent(e.target.value)
    }

    /**
     * Submits form data to the database for editing a message. Called on button click.
     */
    function submit(){
        //New json for the updated content
        var subData = 
        {
            mMessage: content
        }
        
        //PUT request
        fetch("https://thebuzzomega.herokuapp.com/messages/" + id,
            {
                method: "Put",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
                body: JSON.stringify(subData)
            })

            //refreshed page
            window.location.reload()
    }

    //"Main" method. Whatever is in the div is run when the component is used in App.tsx
    return(
        <div>
            <p>Update Message:</p>
            <form>
                <label>
                    Message Id: <input type="text" value={id} onChange={handleChangeId}/>
                    Content: <input type="text" value={content} onChange={handleChangeContent}/>
                </label>
            </form>
            Updated Message: {id} | {content}   
            <button data-testid = 'updatebtn' onClick={submit}> Update</button>
        </div>
    )

}

export default EditMsg