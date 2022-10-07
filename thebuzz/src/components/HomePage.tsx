import { useState, useEffect } from 'react';

//Homepage component of App.tsx

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

    //Standalone function for updating the number of likes. Called on button click
    function LikeMsg(id: number, likes: number){
        //PUT request
        fetch("https://thebuzzomega.herokuapp.com/messages/" + id + '/3',
            {
                method: "PUT",
            })
        
        //reloads page
        window.location.reload()
    }

    //Standalone function for deleting a message. Called on button click
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
                        {item['mId']} | {item['mTitle']} | {item['mContent']} | Likes: {item['mLikes']} <button data-testid='likebtn' onClick={() => LikeMsg(item['mId'], item['mLikes'])}> Like</button> <button data-testid = 'deletebtn' onClick={() => DeleteMsg(item['mId'])}> Delete</button> 
                    </li>
                ))
                : messages 
            }
        </div>
    )
}

export default HomePage