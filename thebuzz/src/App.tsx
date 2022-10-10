import HomePage from './components/HomePage';
import AddMessage from './components/AddMessage';
import EditMsg from './components/EditMsg';

//MAIN FILE FOR FRONT END. 
//This funtion calls all of the individual components for the app
//Homepage, AddMessage, EditMessage (probably more as project goes on)

function App() {
    return (
      <div>
        <HomePage />
        <AddMessage />
        <EditMsg />
      </div>
    );
  }
export default App;
