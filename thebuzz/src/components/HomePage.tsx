import React, { useState, useEffect } from 'react';

function HomePage() {

    const [messages, setMessages] = useState<any[]>([]);

    useEffect(() => {
        fetch("https://thebuzzomega.herokuapp.com/messages",
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

    function LikeMsg(id: number, likes: number){

        fetch("https://thebuzzomega.herokuapp.com/messages/" + id,
            {
                method: "PUT",
                body: JSON.stringify({mLikes: ++likes})
            })

        window.location.reload()
    }

    function DeleteMsg(id: number){

        fetch("https://thebuzzomega.herokuapp.com/messages/" + id,
            {
                method: "DELETE",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                }
            })

        window.location.reload()
    }

    function UpdateMsg(){

        //need to find way to update whatever thing the user wants to update
        //maybe display ned component or reuse old text boxes

        window.location.reload()
    }

    return (
        <div>
            <h1>TheBuzz: Home page</h1>
            {Array.isArray(messages)
                ? messages.map((item) => (
                    <li key={item['mId']}>
                        {item['mTitle']} | {item['mContent']} | Likes: {item['mLikes']} <button onClick={() => LikeMsg(item['mId'], item['mLikes'])}> Like</button> <button onClick={() => DeleteMsg(item['mId'])}> Delete</button> <button onClick={UpdateMsg}> Update</button>
                    </li>
                ))
                : messages 
            }
        </div>
    )
}

export default HomePage