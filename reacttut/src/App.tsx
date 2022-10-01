import * as React from "react";
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { Hello } from "./Hello";
import {Url} from "./Url";
import {Counter} from "./Counter";

export class App extends React.Component {
    render() {
        return (
            <Router>
                <div>
                    <nav>
                        <Link to="/">Hello (1)</Link>
                        &nbsp;|&nbsp;
                        <Link to="/hello">Hello (2)</Link>
                        &nbsp;|&nbsp;
                        <Link to="/url/1">Url (1)</Link>
                        &nbsp;|&nbsp;
                        <Link to="/url/2">Url (2)</Link>
                        &nbsp;|&nbsp;
                        <Link to="/counter">Counter</Link>
                    </nav>
                    <Switch>
                        <Route exact path="/" component={Hello} />
                        <Route exact path="/hello" render={() => <Hello message={"There"} />} />
                        <Route path="/url/:num" component={Url} />
                        <Route exact path="/counter" component={Counter} />
                    </Switch>
                    <div>
                        &copy; 2022
                    </div>
                </div>
            </Router>
        );
    }
}

