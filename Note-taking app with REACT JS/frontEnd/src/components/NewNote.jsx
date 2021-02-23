import React from 'react'
import 'bootstrap/dist/css/bootstrap.css';

class NewNote extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // note_id: '',
            course_name: '',
            // content: '',
            course_number: '',
            userId: this.props.user.userId
        };
        console.log(this.state)
    }
    componentDidMount(){
    }

    async handleSubmit(event) {
        event.preventDefault();
        // const {courseName, content}=this.state;
        // const data = { course_name: 'mate' };

        await fetch('http://localhost:8080/notes', {
          method: 'POST', // or 'PUT'
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.state),
        })
        .then(response => response.json())
        .then(data => {
          console.log('Success:', data);
          this.props.updateNotes(data.body.id,data.body.userId,data.body["course_name"],
          data.body["course_number"]);

        })
        .catch((error) => {
          console.error('Error:', error);
        });
}


updateCourseName(event) {
    this.setState({course_name: event.target.value});
}


updateNumber(event) {
    this.setState({course_number: event.target.value});
}

    render() {
        const {variable1}= this.props;
        console.log(variable1)

        return (
           
      <div className="newNoteDiv">
      <header className="newNoteHeader">
        <form method="POST" onSubmit={(event)=> this.handleSubmit(event)}>
            <table>
                <tbody>
                    <tr>
                        <td>&#10024; Add a note &#10024;</td>
                    </tr>
                    <tr>
                        <td><label>Course name: </label></td>
                        <td><input type="text" name="courseName" onChange={(event) => this.updateCourseName(event)}></input></td>
                    </tr>
                    <tr>
                        <td><label>Course number: </label></td>
                        <td><input type="text" name="courseNumber" onChange={(event) => this.updateNumber(event)}></input></td>
                    </tr>
                    <tr>
                        <td colSpan="2"><input type="button" className="btn btn-light" type="submit"></input></td>
                    </tr>
                </tbody>
            </table>
        </form>
      </header>
    </div>

        )
    }
}

export default (NewNote)