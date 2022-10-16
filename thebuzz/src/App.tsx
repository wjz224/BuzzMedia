import HomePage from './components/homePage/HomePage';
import AddMessage from './components/addMessage/AddMessage';
import EditMsg from './components/editMessage/EditMsg';
import { BrowserRouter as Router, Route } from 'react-router-dom';

//MAIN FILE FOR FRONT END. 
//This funtion calls all of the individual components for the app
//Homepage, AddMessage, EditMessage (probably more as project goes on)

function App() {
    return (
      <div>
        {/* <Router>
					<Switch>
						<Route exact path="/homePage" component={HomePage} />
						<Route exact path="/Demo" component={AddMessage} />
					</Switch>
				</Router> */}
        <HomePage />
        <AddMessage />
        {/* <EditMsg />*/}
      </div>
    );
  }
export default App;
