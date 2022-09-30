import * as React from 'react';
import { HashRouter as Router, Route, Link} from 'react-router-dom';
import HomePage from './components/HomePage';
import AddMessage from './components/AddMessage';

function App() {



    return (
      <div>
        <HomePage />
        <AddMessage />
      </div>
    );
  }
export default App;
