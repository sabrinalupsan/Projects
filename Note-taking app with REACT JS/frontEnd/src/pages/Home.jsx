import React from 'react'
// import Layout from '../components/Layout';
// import products from '../utils/products.json';
import HomeNote from '../components/HomeNote';
import NewNote from '../components/NewNote'
import Group from '../components/Group'
import './css/home.css'
import 'bootstrap/dist/css/bootstrap.css';

class Home extends React.Component{
    constructor(props) {
        super(props);
        this.updateNotes=this.updateNotes.bind(this);
        this.fetchNotes=this.fetchNotes.bind(this);
        this.fetchGroupNotes=this.fetchGroupNotes.bind(this);
        this.updateGroupId=this.updateGroupId.bind(this);
        this.createGroup=this.createGroup.bind(this);
        this.showSortedNotes=this.showSortedNotes.bind(this);
        this.compareByNumber=this.compareByNumber.bind(this);
        this.compareByName=this.compareByName.bind(this);


        this.state = {
            notes: [],
            user: this.props.user,
            groupId:this.props.user.groupId,
            createGroup:false
        };
        console.log(this.state.user)
        
    }

    updateGroupId(data){
      console.log("DATA"+data)
        this.setState({user:{groupId:data}})
        console.log("GROUP ID IS NOW: "+this.state.user.groupId)
    }
  //FETCH NOTES METHOD  

    async fetchNotes(){
        // event.preventDefault();
        await fetch(`http://localhost:8080/usersNotes/${this.props.user.userId}`)
        .then(data => data.json())
        .then(data=>this.setState({notes: data}))
              console.log(this.state.notes)
    }

   

    // deleteNotes(notes,groupNotes){
    //     this.setState({notes:notes,
    //     groupNotes:groupNotes})
    // }

    updateNotes(id,
        user_id,
        course_name,
        course_number){
        this.setState(prevState => {
            return {
              notes: [
                ...prevState.notes,
                {
                  id,
                  user_id,
                  course_name,
                  course_number
                }
              ]
            }
          });    
        }
      
    
    componentDidMount() {
       console.log("CREATE GR"+ this.state.createGroup)
        // const notes = [];
        this.fetchNotes();
        console.log(this.state.groupId)
        if(this.state.user.groupId)
        {this.fetchGroupData()
        this.fetchGroupNotes()
        }
        // fetch()
        // this.setState({notes});
    }

    async updateGroup(data){
        await fetch(`http://localhost:8080/users/${this.state.user.userId}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({groupId: data}),
          })
          .then(response => response.json())
          .then(group => {
            console.log('Success:', group);
           
              this.updateGroupId(data);
              
          });
    }

   async createGroup(event){
     event.preventDefault();
     const data={name:this.state.groupName}
        //de implementat
        await fetch('http://localhost:8080/groups', {
            method: 'POST', // or 'PUT'
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
          })
          .then(response => response.json())
          .then(data => {
            console.log('Success:', data.group["id"]);
            // this.setState({groupId:data.group.id})
            
                this.updateGroup(data.group["id"])
  
          })
          .catch((error) => {
            console.error('Error:', error);
          });
    }

    async fetchGroupNotes(){
        if(!this.state.user.groupId)
        {
        return;
        }
       
        await fetch(`http://localhost:8080/groupNotes/${this.props.user.groupId}`)
        .then(data => data.json())
        .then(data=>this.setState({groupNotes: data}))
              console.log(this.state.groupNotes)
    }

    async fetchGroupData(){
      if(!this.state.user.groupId)
      {
      return;
      }
     
      // await fetch(`http://localhost:8080/groups/${this.props.user.groupId}`)
      // .then(data => data.json())
      // .then(data=>this.setState({groupName: data.groups.name}))
            // console.log(this.state.groupNotes)
  }

  compareByNumber(a, b)
  {
    const courseNumberA = a.course_number
    const courseNumberB = b.course_number
    let comparison = 0;
    if (courseNumberA > courseNumberB) {
      comparison = 1;
    } 
    else if (courseNumberA < courseNumberB) {
      comparison = -1;
    }
    return comparison;
  }

  compareByName(a, b)
  {
    const courseNameA = a.course_name
    const courseNameB = b.course_name
    return courseNameA.localeCompare(courseNameB);
  }

  showSortedNotes()
  {
    const array= this.state.notes.sort(this.compareByNumber).sort(this.compareByName)
    this.setState({notes: array});
  }

    render() {

        return(
          <body>
            <header className = "col-md-12" align="center">
                <img align="middle" src="https://www.freelogoservices.com/api/main/images/1j+ojFVDOMkX9Wytexe43D6kif+AqhZGmxfIwXs1M3EMoAJtliEsh...Ro8fk4 " class = "logo-image"/>
            </header>

            <div className="container-fluid container-min-max-width">
            { this.state.user.groupId
                ? <div className="row">
                  <div className="col-md-6" align="center"> <h3>Your group: {this.state.groupName}</h3>
                  
                    <Group user={this.state.user}
                    updateGroupId={this.updateGroupId}></Group>
                    
                    </div>
                  <div className="col-md-6" align="center">
                  <h3>Group Notes:</h3>

                    { this.state.groupNotes
                    ?
                      this.state.groupNotes.map((note) =>
                      <HomeNote 
                      // fetchNotes={this.fetchNotes}
                        group={true}
                          key={note.id}
                          route={note.id}
                          courseName={note["course_name"]}
                          courseNumber={note["course_number"]}
                          // image={products[category].image}
                      />
                        )
                    : <h4>You don't have any notes!</h4>  
                    }
                    
                      </div>
                     </div>
                : this.state.createGroup?
                     <div>
                   <form onSubmit={(event)=> this.createGroup(event)}>
                     <label htmlFor="name">Group name</label>
                   <input id="name" type="text" name="url" onChange={(event) => this.updateGroupName(event)}></input>
                   <input type="submit" value="Create group"></input>
                   </form>
                   <button type="button" className="btn btn-light" onClick={()=>this.setState({createGroup:false})}>Cancel</button>
                    </div>
                   :<button type="button" className="btn btn-light btn-study-group" onClick={()=>this.setState({createGroup:true})}>Make a study group!</button>
                 
                  
                }
                <div className="row">
                  <div className="col-md-12 " align="center">
                    <h3>Your notes:</h3>
                    <div className="row">
                        {this.state.notes.map((note) =>
                            <HomeNote
                                fetchNotes={this.fetchNotes}
                                fetchGroupNotes={this.fetchGroupNotes}
                                key={note.id}
                                route={note.id}
                                courseName={note["course_name"]}
                                courseNumber={note["course_number"]}
                                // image={products[category].image}
                            />
                        )}
                    </div>
                    <button type="button" className="btn btn-light btn-sort" onClick={this.showSortedNotes}>Sort my notes by course</button>
                   <NewNote user={this.state.user} updateNotes={this.updateNotes} >New Note</NewNote>
                   
                   </div>
                </div>
                </div>
                </body>
            // </Layout>
        );
    }
    updateGroupName(event){
        event.preventDefault();
        this.setState({groupName:event.target.value})
    }
}

export default Home;