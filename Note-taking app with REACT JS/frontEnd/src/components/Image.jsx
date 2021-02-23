import React, { Component } from 'react'
import { ReactComponent as FileIcon } from '../assets/document.svg'
import { ReactComponent as DeleteIcon } from '../assets/delete.svg'
import { Button } from 'primereact/button'
import '../pages/css/notes.css'
import 'bootstrap/dist/css/bootstrap.css';


export default class Image extends Component {
    constructor(props){
        super(props);
        // const url=URL.createObjectURL(new Blob([this.props.file.buffer],{type: 'text/plain'}))

        this.state={
            id:props.id,
            file:props.file,
            name:props.name, 
            

        }
    }
    componentDidMount(){
        console.log(this.state)

    }

    render() {

        return (
            <div className="image-container"> 
            <img src={this.state.file} alt="image"/>
            <a href={this.state.file}>{this.state.name}</a>
            <button type="button" className="btn btn-light" value="Delete" onClick={()=>this.deleteFile(this.state.id,this.props.fetchFiles)}>Delete</button>
            </div>
        )
    }
    async deleteFile(id,fetchFiles){
        // event.preventDefault();
        await fetch(`http://localhost:8080/files/${id}`, {
            method: 'DELETE', // or 'PUT'
            headers: {
              'Content-Type': 'application/json',
            },
            // body: JSON.stringify(this.state),
          })
          .then(response => response.json())
          .then(data => {
            console.log('Success:', data);
            // this.props.updateNotes(data.body.id,data.body.userId,data.body["course_name"],
            // data.body["course_number"]);
            // fetchNotes();
            // fetchGroupNotes();
            fetchFiles()
          })
          .catch((error) => {
            console.error('Error:', error);
          });
    }
}
