import React, { Component } from 'react'

export default class User extends Component {
    constructor(props){
        super(props);
        this.state={
            userId:props.key,
            name:props.name,
            email:props.email
        }
    }
    deleteUser(){
        console.log("delete user"+this.state.userId);
        //de implementat
    }
    render() {
        return (
            <div>
                <h4>{this.state.name}</h4>
                <p>email: {this.state.email} </p>
                {/* <button onClick={this.deleteUser}>Delete</button> */}
            </div>
        )
    }
}
