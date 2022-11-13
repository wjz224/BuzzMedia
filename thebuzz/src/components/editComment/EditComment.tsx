import React, {useState} from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

/**
 * Component for editing messages.
 * @component 
 */

function EditComment(){

    let navigate = useNavigate();

    let params = useParams();

    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, imageUrl } = state;

    //States for id and content
    const [content, setContent] = useState("")

    
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
            mComment: content
        }
        
        //PUT request
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/comments/${email}/${params.id}`,
            {
                method: "Put",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
                body: JSON.stringify(subData)
            }).then((response) => response.json())
            .then((data) => {
                console.log(data)
                
            })
            navigate("/home", {
                state: {
                    sessionId: sessionId, email: email,
                    imageUrl: imageUrl,
                }
            });  
    }

    //"Main" method. Whatever is in the div is run when the component is used in App.tsx
    return(
        <div>
            <p>Update Comment:</p>
            <form>
                <label>
                    Content: <input type="text" value={content} onChange={handleChangeContent}/>
                </label>
            </form>
            <button data-testid = 'updatebtn' onClick={submit}> Update</button>
        </div>
    )

}

export default EditComment