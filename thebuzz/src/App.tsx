import * as React from 'react';
import { HashRouter as Router, Route, Link} from 'react-router-dom';
import HomePage from './components/HomePage';
import AddMessage from './components/AddMessage';
import EditMsg from './components/EditMsg';

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
