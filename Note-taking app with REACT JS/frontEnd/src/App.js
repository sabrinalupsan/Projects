import React from 'react';
import './App.css';
import { Switch, Route } from "react-router-dom";
import Login from './pages/Login'
import Notes from './pages/Notes';
import Home from './pages/Home'
// import NewNote from './pages/NewNote'

//test sad 1 1 1 1 1 1 1 
class App extends React.Component {
  constructor() {
    super();
    this.state = {
      user:{}
    };
  }
    render() {
      return(
        <div className="app" >
          <Switch>
            <Route exact path="/"
            render={(props) => <Login 
              // props-urile de aici sunt props-urile referitoare la router(match, history...)
              {...props} 
              
              loginFunc={(event,name,email,userId,password,groupId)=>this.loginFunc(event,name,email,userId,password,groupId)}
            />}/>
            
            {
              this.state.user.userId?
              <div>
              <Route path="/home" render={(props) => <Home 
                // props-urile de aici sunt props-urile referitoare la router(match, history...)
                {...props} 
                user={this.state.user}
                />}></Route>
  
                <Route path="/notes/:noteid" render={(props) => <Notes 
                // props-urile de aici sunt props-urile referitoare la router(match, history...)
                {...props} 
                user={this.state.user}
                />}></Route>
                </div>
              :<Route  path="*"
              render={(props) => <Login 
                // props-urile de aici sunt props-urile referitoare la router(match, history...)
                {...props} 
                
                loginFunc={(event,name,email,userId,password,groupId)=>this.loginFunc(event,name,email,userId,password,groupId)}
              />}/>
            }

           
            {/* <Route path="/notes/:noteid" component={Notes}/>
            {/* <Route path="/notes" component={NewNote}></Route> */}
            {/* <Route  path="/" component={Login}/> */}

          </Switch>
        </div>
      );
    }
    
    loginFunc(event, name, email, userId, password,groupId) {
      event.preventDefault();
      this.setState({
          user:
            {
              userId,
              name,
              email,
              groupId,
              password
            }
          
        }
      );
      console.log(this.state)
    }
      /* if(dbemail==email && dbpass==password)
        window.alert("Succesful login")
      else
        window.alert("Failed login") */
        // }
    
    /* function App() {
      return(
        <div className="app">
          <Switch>
            <Route path="/login" component={Login}/>
            <Route exact path="/" component={Home}/>
            <Route path="/notes" component={Notes}/>
            <Route path="/about" component={About}/>
            <Route path="/category/:categoryName" component={Category}/>
            <Route path="/student/:studentId" component={Student}/>
            <Route path="*" component={Page404}/>
          </Switch>
        </div>
      );
    } */


  }


export default App;