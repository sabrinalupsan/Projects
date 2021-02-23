import React, { Component } from 'react'
import { withRouter } from 'react-router-dom'
import './css/login.css'
// import Layout from '../components/Layout'


 class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            password: '',
            email: '',
        };
        console.log(this.state)
    }
    componentDidMount(){
    }

    updatePassword(event) {
        this.setState({password: event.target.value});
    }

    updateEmail(event) {
        this.setState({email: event.target.value});
    }

 
 async handleSubmit(event) {
    event.preventDefault();
   const {email, password}=this.state;

    if(!(email.includes("@stud.ase.ro"))){
        
        window.alert("You need to log in with the institutional email")
        return;
    }
    // if(password.length < 3){

    //     window.alert("Your password should have at least 3 characters")
    //     return;
    // }
    
    await fetch("http://localhost:8080/users")
      .then(data => data.json())
      .then(data=>data.forEach((element)=>{
        //   console.log(element.email)
        if(element.email === this.state.email && element["user_pass"]===this.state.password){
               const history=this.props.history;
            //    console.log(element.groupId)
               this.props.loginFunc(event,element.name,element.email,element.id,element["user_pass"],element.groupId)
            history.push('/home');
            console.log("done login fetch")
        }
        

      }
      )
      )
      
    }
    render() {
       

        return (
            
        <div className="container-login">
        <div className="login-div">
        
        <form method="POST" onSubmit={(event)=> this.handleSubmit(event)}>
            <div className='components'>
            <img src="https://www.freelogoservices.com/api/main/images/1j+ojFVDOMkX9Wytexe43D6kif+AqhZGmxfIwXs1M3EMoAJtliEsh...Ro8fk4 " class = "logo-image"/>
            <h2>	&#10031; Login 	&#10031;</h2>
                    <div className="email">
                        
                        <input className="input-login" type="email" name="email"
                                            onChange={(event) => this.updateEmail(event)} placeholder='Enter Email'></input>
                    </div>  
                        
                    <div className="password">
                        <input className="input-login" type="password" name="password" 
                            onChange={(event) => this.updatePassword(event)} placeholder ='Enter Password' ></input>
                    </div>
                    <div type="button" className="btn btn-light"value='Login'>
                        <input className="btn btn-light" type="submit" value='Submit'></input>
                    </div>
            </div>
                    
        </form>
        
    </div>
    </div>
    
   
        )
    }
}


export default (Login)
