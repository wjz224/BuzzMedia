import HomePage from './components/homePage/HomePage';
import AddMessage from './components/addMessage/AddMessage';
import EditMsg from './components/editMessage/EditMsg';
import LoginPage from './components/loginPage/LoginPage';
import {
  BrowserRouter,
  Routes,
  Route,
  Link,
  useParams 
} from "react-router-dom";
import ProfilePage from './components/profilePage/ProfilePage';
import CommentPage from './components/commentPage/CommentPage';
import EditProfile from './components/editProfilePage/EditProfile';
import OtherProfile from './components/OtherProfile/OtherProfile';


//MAIN FILE FOR FRONT END. 
//This funtion calls all of the individual components for the app
//Homepage, AddMessage, EditMessage (probably more as project goes on)

function App() {
  
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/add" element={<AddMessage />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/editprofile" element={<EditProfile />} />
          <Route path="/comments/:postId" element={<CommentPage />} />
          <Route path="/users/:username" element={<OtherProfile />} />
        </Routes>
      </BrowserRouter>
    );
  }
export default App;
