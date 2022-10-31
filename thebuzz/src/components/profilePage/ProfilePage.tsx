import { Logout } from '@mui/icons-material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../headerBar/HeaderBar.css';
import './ProfilePage.css';

const background = require('../loginPage/honeyCombBackground.webp');
const orangeHex = require('../loginPage/Orange_hexagon.png');

function ProfilePage(this: any) {

    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, familyName, givenName, googleId, imageUrl, name} = state;

    // const [name, setName] = useState('name');
    // const [email, setEmail] = useState('email');
    // const [imageUrl, setImageUrl] = useState('emptyString');
    const [gender, setGender] = useState('N/A')
    const [sexuality, setSexuality] = useState('N/A');
    const [bio, setBio] = useState('N/A');
    


    //TODO call to the database and get the profile based on session ID
    // fetch(`https://thebuzzomega.herokuapp.com/users/${sessionId}`,
    //     {
    //         method: "GET",
    //         headers: {
    //             'Content-type': 'application/json; charset=UTF-8'
    //         },
    //         mode: "cors",
    //     })
    //     .then((response) => response.json())
    //     .then((data) => {
    //         // setMessages(data.mData)
    //         console.log(data)
    //         setName(data.mName)
    //     });

    function goHome() {
        navigate("/home", { state: { sessionId: sessionId ,email: email,
            familyName: familyName,
            givenName: givenName,
            googleId: googleId,
            imageUrl: imageUrl,
            name: name
        }});

    };

    function goPost() {
        navigate("/add", { state: { sessionId: sessionId ,email: email,
            familyName: familyName,
            givenName: givenName,
            googleId: googleId,
            imageUrl: imageUrl,
            name: name } });

    };

    function goProfile() {
        console.log("alrady on profile")
    };

    function edit() {
        // navigate("/editprofile", { state: { sessionId: sessionId ,email: email,
        //     familyName: familyName,
        //     givenName: givenName,
        //     googleId: googleId,
        //     imageUrl: imageUrl,
        //     name: name } })
        navigate("/editprofile", { state: { sessionId: sessionId ,email: email,
            bio: bio,
            gender: gender,
            sexuality: sexuality,
            googleId: googleId,
            imageUrl: imageUrl,
            name: name } })
    }

    const handleBioChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setBio(e.target.value)
    }

    return (
        <div
            style={{
                backgroundImage: `url(${background})`,
                height: '100vh',
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
            }}
            className='container'
        >
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
            <div className='hexContainer'>
                <div className='loginBtn'>
                </div>
                <img className="profileImg" alt='profile' style={{ width: 100 }} src={imageUrl} referrerPolicy="no-referrer" />
                {/* <input
                    type="text"
                    value={bio}
                    onChange={this.setBio}
                /> */}

                <text className='bioTitle'>bio:</text>
                <textarea className='bio' onChange={handleBioChange}>
                    {bio}
                </textarea>
                <div className='gender'>
                    <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">Gender</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        // value={age}
                        label="gender"
                        // onChange={}
                    >
                        <MenuItem value={10}>Male</MenuItem>
                        <MenuItem value={20}>Female</MenuItem>
                        <MenuItem value={30}>Transgender man/trans man</MenuItem>
                        <MenuItem value={40}>Transgender woman/trans woman</MenuItem>
                        <MenuItem value={50}>Genderqueer/gender nonconforming neither exclusively male nor female</MenuItem>
                        <MenuItem value={60}>Decline to answer</MenuItem>
                        <MenuItem value={70}>Other</MenuItem>
                    </Select>
                    </FormControl>
                </div>
                <div className='sex'>
                    <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">Sexual Orientation</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        // value={age}
                        label="gender"
                        // onChange={}
                    >
                        <MenuItem value={10}>Straight or heterosexual</MenuItem>
                        <MenuItem value={20}>Lesbian or gay</MenuItem>
                        <MenuItem value={30}>Bisexual</MenuItem>
                        <MenuItem value={40}>Queer, pansexual, and/or questioning</MenuItem>
                        <MenuItem value={50}>Don’t know</MenuItem>
                        <MenuItem value={60}>Decline to answer</MenuItem>
                        <MenuItem value={70}>Other</MenuItem>
                    </Select>
                    </FormControl>
                </div>
                
                {/* <text className='genTitle'>gender:</text>
                <textarea className='gender' onChange={handleBioChange}>
                    {gender}
                </textarea>
                <text className='sexTitle'>sexuality:</text> */}
                
                <text className='name'>{name}</text>
                <text className='email'>{email}</text>
                {/* <text className="bio">Bio:  N/A</text> */}
                {/* <text className="gender">Gender:  N/A</text>    
                <text className="sex">Sexual orientation:  N/A</text> */}
                <div className='logoutBtn'>
                    <button><a href='/home'>Logout</a></button>
                    <Logout />
                    {/* <button onClick={edit}>edit Profile</button> */}
                </div>



                <div>



                </div>

                <img alt='logo' className='hex' style={{ width: 700 }} src={String(orangeHex)} />

            </div>

        </div>
        // <div className='container'
        // style={{ backgroundImage: `url(${background})`,
        //     height:'100vh',
        //     backgroundSize: 'cover',
        //     backgroundRepeat: 'no-repeat',}}
        // >
        //     {/* <div className='hex'>
        //         <img alt='logo'  style={{ width: 500 }} src={String(orangeHex)} />
        //     </div> */}


        //     <div className='headerContainer'>
        //     <div>
        //         <h1 className='header'>TheBuzz</h1>
        //     </div>
        //     <div className='btnContainer'>           
        //         <button className='btn' onClick={goHome}>Home</button>
        //         <button className='btn' onClick={goPost}>Add post</button>
        //         <button className='btn' onClick={goProfile}>Profile</button>
        //     </div>

        // </div>
        //     <div className='hexContainer'>
        //         <img alt='logo'  style={{ width: 500 }} src={String(orangeHex)} className='hex'/>
        //         <div className='info'>
        //             {/* <img className="profileImage" alt='profile' style={{width:40}} src={imageUrl} referrerPolicy="no-referrer"/>
        //             <text className='name'>{name}</text>
        //             <text className='email'>{email}</text> */}

        //             <text className='name'>First LastName</text>
        //             <text className='email'>email</text>
        //             <button><a href='/'>Logout</a></button>
        //             <Logout />
        //         </div>

        //         {/*  */}
        //     </div>

        // </div>
    )
}

export default ProfilePage