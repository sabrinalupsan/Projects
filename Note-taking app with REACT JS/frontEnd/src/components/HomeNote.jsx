import React from 'react';
import {Link} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
// import React, { Component } from 'react'

// export default class HomeNote extends Component {
//     render() {
//         return (
//             <div>
                
//             </div>
//         )
//     }
// }


const NoteInList = (props) => {
    const {route, courseName, courseNumber,fetchNotes,fetchGroupNotes} = props;

    return(
        <div className="row mb-3">
            <Link to={`/notes/${route}`}>
                {/* <div className="w-100">
                    <img src={image} alt={name} className="w-100"/>
                </div> */}
                <h2 className="h4 my-1"><strong>Course: {courseName}</strong></h2>
                <p className="m-0">Course number: {courseNumber}</p>
                {
                    props.group?
                    null
                    :<button type="button" className="btn btn-light" onClick={(event)=>deleteNote(event,route,fetchNotes,fetchGroupNotes)}>Delete</button>

                }
            </Link>
        </div>
    );
}

async function deleteNote(event,route,fetchNotes,fetchGroupNotes){
    event.preventDefault();
    await fetch(`http://localhost:8080/notes/${route}`, {
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
        fetchNotes();
        fetchGroupNotes();
      })
      .catch((error) => {
        console.error('Error:', error);
      });
}

export default NoteInList;