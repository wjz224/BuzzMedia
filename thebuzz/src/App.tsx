import HomePage from './components/homePage/HomePage';
import AddMessage from './components/addMessage/AddMessage';
import EditMsg from './components/editMessage/EditMsg';
import LoginPage from './components/loginPage/LoginPage';
import {
  BrowserRouter,
  Routes,
  Route,
  Link
} from "react-router-dom";
import ProfilePage from './components/profilePage/ProfilePage';

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
        </Routes>
      </BrowserRouter>
      // <div>
        
      //   {/* <Router>
			// 		<Switch>
			// 			<Route exact path="/homePage" component={HomePage} />
			// 			<Route exact path="/Demo" component={AddMessage} />
			// 		</Switch>
			// 	</Router> */}
      //   {/* <HomePage />
      //   <AddMessage /> */}
      //   <LoginPage/>
      //   {/* <EditMsg />*/}
      // </div>
    );
  }
export default App;
