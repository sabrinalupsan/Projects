import React, { Component } from 'react'
import User from '../components/User'
import 'bootstrap/dist/css/bootstrap.css';
import '../pages/css/home.css'

export default class Group extends Component {
    constructor(props){
        super(props);
        // this.updateGroupId=props.updateGroupId;
        this.state={
            userId:this.props.user.userId,
            groupId:this.props.user.groupId,
            groupUsers:[],
            addForm:false
        }
        this.addPerson=this.addPerson.bind(this)
        this.cancel=this.cancel.bind(this)
        this.leaveGroup=this.leaveGroup.bind(this)
        console.log("user id: "+this.state.userId)

    
    }

  async  fetchUsers(){
        await fetch(`http://localhost:8080/usersGroup/${this.state.groupId}`)
    .then(data => data.json())
    .then(users=>{
        console.log(users)
        this.setState({groupUsers: users
        
    })
    
    })
}
 componentDidMount(){
    //  console.log("ID GROUP WTF"+this.state.groupId)
     this.fetchUsers();
     console.log(this.state.groupUsers)
 }   
    render() {
        return (
            <div>
               {this.state.groupUsers.map((user) =>
                            <User
                                key={user.id}
                                route={user.id}
                                name={user.name}
                                email={user.email}                                // image={products[category].image}
                            />
                        )} 
                        {this.state.addForm?
                        <div>
                        <form method="PUT" onSubmit={(event)=> this.handleSubmit(event)}>
                            <input type="text" name="email"  
                                onChange={(event) => this.updateEmail(event)} placeholder='Enter Email'/>
                            <input type="submit" value='Add'></input>

                        </form>
                        <button type="button" className="btn btn-light btn-cancel" onClick={this.cancel} >Cancel</button>
                        </div>
                        :<button type="button" className="btn btn-light btn-add-mate" onClick={this.addPerson} >Add a study mate</button>

                        }
                <button type="button" className="btn btn-light btn-leave" onClick={this.leaveGroup} value="Leave Group">Leave Group</button>        
            </div>
        )
    }
    addPerson(){
        this.setState({addForm:true})
    }

    async leaveGroup(){
        //de implementat
        const data={groupId:null}
        console.log("user id: "+this.state.userId)

        await fetch(`http://localhost:8080/users/${this.state.userId}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
          })
          .then(response => response.json())
          .then(data => {
            console.log('Success:', data);
           
              this.props.updateGroupId(data.groupId);
          });
        }

    cancel(){
        this.setState({addForm:false})

    }
    updateEmail(event) {
        this.setState({emailNewPerson: event.target.value});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const data={groupId:this.state.groupId}
        await fetch(`http://localhost:8080/usersEmail/${this.state.emailNewPerson}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
          })
          .then(response => response.json())
          .then(data => {
            console.log('Success:', data);
            if(!data.user){
                window.alert(`the user with the email ${this.state.emailNewPerson} was not found`);
                return;
            }
            // this.setState(prevState => {
            //     return {
            //       groupUsers: [
            //         ...prevState.groupUsers,
            //         data.user
            //       ]
            //     }
            //   });   
            this.fetchUsers(); 
          })
          .catch((error) => {
            console.error('Error:', error);
          });
    }

  
   
    
}
