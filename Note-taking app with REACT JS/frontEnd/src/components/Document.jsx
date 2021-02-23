import React, { Component } from 'react'
import { ReactComponent as FileIcon } from '../assets/document.svg'
import { ReactComponent as DeleteIcon } from '../assets/delete.svg'
import { Button } from 'primereact/button'
//import '../pages/css/notes.css'
import 'bootstrap/dist/css/bootstrap.css';

export default class Document extends Component {
    constructor(props){
        super(props);
        this.state={
            id:props.id,
            file:props.file,
            name:props.name
        }
    }
    render() {
        return (
            <div className="file-container"> 
                <FileIcon></FileIcon>
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
