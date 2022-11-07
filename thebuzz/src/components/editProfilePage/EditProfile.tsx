import { info } from 'console';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import './EditProfile.css';
import { useState } from 'react';
const background = require('../loginPage/honeyCombBackground.webp');

/**
 * Component for displaying messages and message information, as well as liking and deleting messages.
 * @component 
 */


function EditProfile(this: any) {

    let navigate = useNavigate();

    const { state } = useLocation(); // Read values passed on state
    const { sessionId, email, familyName, givenName, googleId, imageUrl, name } = state;

    const [gender, setGender] = useState("N/A");
    const [sex, setSex] = useState("N/A");
    const [note, setNote] = useState("N/A");

    const handleChangeGender = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setGender(e.target.value)

        
    }
    const handleChangeSex = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setSex(e.target.value);
    };
    const handleChangeNote = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setNote(e.target.value);
    };
    

    //!Put request function when submit button is pressed then navigate back to profile
    function submit() {
        //turn the new info into a json format
        var data = 
        {
            mGender: gender,
            mSex: sex,
            mNote: note
        }

        //put request to update profile
        fetch(`https://thebuzzomega.herokuapp.com/${sessionId}/users/${email}`,
            {
                method: "PUT",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                mode: "cors",
                body: JSON.stringify(data)
            })
            .then((response) => response.json())
            .then((data) => {
                console.log(data)
            });

        navigate('/profile', { state: { sessionId, email, familyName, givenName, googleId, imageUrl, name } })
    }
    function cancel() {
        navigate('/profile', { state: { sessionId, email, familyName, givenName, googleId, imageUrl, name } })
    }
    //displays a form where additional info can be added to someones profile
    return (

        <div
            style={{
                height: '100vh',
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
            }}
        >
            <div className='container'>


                <button onClick={submit}>Submit</button>
                <button onClick={cancel}>cancel</button>
                <form>
                    <label>
                        Note:
                        <input type="text" name="name" onChange={handleChangeNote}/>
                    </label>
                    
                </form>
                <div >


                    <FormControl fullWidth>
                        <InputLabel id="demo-simple-select-label">Gender</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={gender}
                            label="gender"
                            onChange={handleChangeGender}
                        >
                            <MenuItem value={"N/A"}>N/A</MenuItem>
                            <MenuItem value={"male"}>Male</MenuItem>
                            <MenuItem value={"female"}>Female</MenuItem>
                            <MenuItem value={"transm"}>Transgender man/trans man</MenuItem>
                            <MenuItem value={"transw"}>Transgender woman/trans woman</MenuItem>
                            <MenuItem value={"nonconf"}>Genderqueer/gender nonconforming neither exclusively male nor female</MenuItem>
                            <MenuItem value={"decline"}>Decline to answer</MenuItem>
                            <MenuItem value={"other"}>Other</MenuItem>
                        </Select>
                    </FormControl>
                </div>
                <div>
                    <FormControl fullWidth>
                        <InputLabel id="demo-simple-select-label">Sexual Orientation</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={sex}
                            label="sexuality"
                            onChange={handleChangeSex}
                        >
                            <MenuItem value={'N/A'}>N/A</MenuItem>
                            <MenuItem value={"straight"}>Straight or heterosexual</MenuItem>
                            <MenuItem value={"gay"}>Lesbian or gay</MenuItem>
                            <MenuItem value={"bi"}>Bisexual</MenuItem>
                            <MenuItem value={"queer"}>Queer, pansexual, and/or questioning</MenuItem>
                            <MenuItem value={"dk"}>Don’t know</MenuItem>
                            <MenuItem value={"decline"}>Decline to answer</MenuItem>
                            <MenuItem value={"other"}>Other</MenuItem>
                        </Select>
                    </FormControl>
                </div>

            </div>

        </div >
    )
}


export default EditProfile;

