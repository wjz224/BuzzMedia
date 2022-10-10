import { useState, useEffect } from 'react';

/**
 * Component for displaying messages and message information, as well as liking and deleting messages.
 * @component 
 */

function HomePage() {

    //State for the list of messages
    const [messages, setMessages] = useState<any[]>([]);

    //useEffect runs inside code whenever the page is loaded
    useEffect(() => {
        //GET request. Gets messages from database
        fetch("https://thebuzzomega.herokuapp.com/messages",
            {
                method: "GET"
            })
            //Load messages into a json file so we can display
            .then((response) => response.json())
            .then((data) => {
                setMessages(data.mData)
                console.log(data.mData)
            });

    }, []
    );

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
        <div>
            <h1>TheBuzz: Home page</h1>
            {Array.isArray(messages)
                ? messages.map((item) => (
                    <li key={item['mId']}>
                        {item['mId']} | {item['mTitle']} | {item['mContent']} | Likes: {item['mLikes']} <button data-testid='likebtn' onClick={() => LikeMsg(item['mId'])}> Like</button> <button data-testid='likebtn' onClick={() => UnLikeMsg(item['mId'])}> UnLike</button> <button data-testid = 'deletebtn' onClick={() => DeleteMsg(item['mId'])}> Delete</button> 
                    </li>
                ))
                : messages 
            }
        </div>
    )
}

export default HomePage