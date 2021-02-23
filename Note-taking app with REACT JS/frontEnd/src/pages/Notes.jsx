import React, { Component } from 'react'
import ReactQuill, { Quill } from 'react-quill'; // ES6
import 'react-quill/dist/quill.snow.css'; // ES6
import Image from '../components/Image'
// #1 import quill-image-uploader
// import { ReactComponent as Document } from '../assets/document.svg'
import Document from '../components/Document'
import ImageUploader from "quill-image-uploader";
// import dogege from '../uploads/dogege.png'
import './css/notes.css'
import YouTube from 'react-youtube';
import { 
  TwitterShareButton,
  TelegramShareButton,
  
  TwitterIcon,
  EmailShareButton,
  EmailIcon,
  TelegramIcon
} from 'react-share'
import ReactPlayer from "react-player"
import Layout from '../components/Layout';


// #2 register module
Quill.register("modules/imageUploader", ImageUploader);

export default class Notes extends Component {
    constructor(props){
        super(props);
        const {match}=this.props;
        console.log("props: ");
        console.log(match)
        this.state={
          userId:this.props.user.userId,
            groupId:this.props.user.groupId,
            noteId:match.params.noteid,
            course_name:"",
            course_number:"",
            content:"",
            noteGroup:"",
            url: "",
            youtube:false, 
            urlSoundCloud:"", 
            soundcloud:false,
            // images:[],
            files:[]

        }
        this.contentHandler=this.contentHandler.bind(this)
    }

async fetchNote(){
  await fetch(`http://localhost:8080/notes/${this.state.noteId}`)
    .then(data => data.json())
    .then(note=>{
        console.log(note)
        this.setState({content: note.content,
        course_number:note["course_number"],
        course_name:note["course_name"],
        noteGroup:note.groupId}
   )
   console.log(this.state)
  })
}

async fetchFiles(){
  await fetch(`http://localhost:8080/filesByNote/${this.state.noteId}`)
  .then(data => data.json())
  .then(files=>{
      console.log(files)
      this.setState({files:files})
        console.log(this.state.files)
      });   
}
      
//  )}
//  console.log(this.state)
// })
// }

  componentDidMount(){
  this.fetchNote();    
  this.fetchFiles();
   
  }

    

    contentHandler(value){
        // event.preventDefault();
        this.setState({content: value})
        console.log(this.state.content)
    }

    modules = {
        // #3 Add "image" to the toolbar
        toolbar: [["bold", "italic"]],
    
      };
    
      formats = [
        "header",
        "bold",
        "italic",
        "underline",
        "strike",
        "blockquote",
        "list",
        "bullet",
        "indent",
        "link",
        "image",
        "imageBlot" // #5 Optinal if using custom formats
      ];
    
      async addNoteToGroup(event) {
        event.preventDefault();
        // this.setState({groupId:this.state.user.groupId})
        // event.preventDefault();
        if(this.state.groupId===null || this.state.groupId===""){
          window.alert("You are not in a group!");
          return;
        }
        const data={groupId:this.state.groupId}
        await fetch(`http://localhost:8080/notes/${this.state.noteId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
          console.log('Success:', data);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
      }

      async Save(event){
        event.preventDefault();
        const data={content:this.state.content}
        await fetch(`http://localhost:8080/notes/${this.state.noteId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
          console.log('Success:', data);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
      }

    async  PostFile(data){
        await fetch(`http://localhost:8080/files`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(fileState => {
          console.log('Success:', fileState);
          this.setState(prevState => {
            return {
              files: [
                ...prevState.files,
                fileState.file
             
              ]
            }
          });                  console.log(this.state.files)
        })
        .catch((error) => {
          console.error('Error:', error);
        });
      }

    //   getID()
    // {
    //   var URL = document.getElementById("theURL").value;
    //   return URL.substring(17)
    // }

    handleSubmit(event){
      event.preventDefault();
      // this.updateUrl();
      this.setState({youtube: true })
    }

    handleSubmitSoundCloud(event){
      event.preventDefault();
      // this.updateUrl();
      this.setState({soundcloud: true })
    }

    updateUrl(event){
      event.preventDefault();
      this.setState({url:event.target.value.replaceAll("https://www.youtube.com/watch?v=","").replaceAll("https://youtu.be/","")})
    }

    updateSoundCloudUrl(event){
      event.preventDefault();
      this.setState({urlSoundCloud:event.target.value})
    }
    onChangeFile(e){
        let file =e.target.files[0];
        let reader = new FileReader();
        console.log(file);
        if(file ){
          let type;
          if(file.type.substr(0,5)==="image"){
              type="image"
          }
          else type="document"

          reader.onloadend=()=>{
            console.log(reader.result);
            // let img=reader.result as string;
            let fileSend=   {file:reader.result ,
              name:file.name,
              noteId:this.state.noteId,
            type:type}

            const fileState=this.PostFile(fileSend)
            // this.setState({image:reader.result})
          
   
          }
        }
        // else {
        //   reader.onloadend=()=>{
        //     console.log(reader.result);
        //     // let img=reader.result as string;
        //     this.setState(prevState => {
        //       return {
        //         files: [
        //           ...prevState.files,
        //           {file:reader.result,
        //             name:file.name,
        //             noteId:this.state.noteId,
        //           type:"document"}
        //         ]
        //       }
        //     });
            // this.setState({file:{data:reader.result, name:file.name}})
        // }}
        reader.readAsDataURL(file)
    }


    render() {
        return (
          <div className="row">
            <div className="col-md-8" align="center">
                
                <div className="note-content">
                <ReactQuill className="text-editor"
                  ref={el => {
                    this.quill = el;
                }}
                value={this.state.content} onChange={this.contentHandler}
                theme="snow"
        modules={this.modules}
        formats={this.formats}
        
                ></ReactQuill>
                <h3 align="left">Add your files!</h3>
                <div className="add-files" align="left">
                <input type="file"  name="file" onChange={(e)=>{this.onChangeFile(e)}}/>
                <button type="button" className="btn-save-file" onClick={(event)=> this.Save(event)}>Save</button>
                </div>
                {this.state.files.filter(file=>file.type==="image").length>0?
                 <div> 
                 <h3>Images:</h3>
                 <div className = "images-list">
                 {
  this.state.files.filter(file=>file.type==="image").map((image) =>
  
  <Image
  file={image.file}
  id={image.id}
  name={image.name}
  fetchFiles={()=>this.fetchFiles()}
  />
  )
  }</div></div>
                  :null               
}

                {this.state.files.filter(file=>file.type==="document").length>0?
              // <div>
              //   <Document>

              //   </Document>
              //   <a href={this.state.file}>THE FILE</a>

  
  <div> 
<h3>Files:</h3>
<div className="file-list">
{
  // let docs=this.state.files.filter(file=>file.type==="document");
  this.state.files.filter(file=>file.type==="document").map((file) =>
                
                    <Document file={file.file} 
                              name={file.name}
                              id={file.id}
                              fetchFiles={()=>this.fetchFiles()}

                             />)}</div> </div>
                  :null}
                {/* <img src={require(`../uploads/dogege.png`)}/> */}
                {/* <img src={dogege}/> */}
                {
                  this.state.groupId&&(!this.state.noteGroup) ?
                  <button type="button" className="btn btn-light btn-sort" onClick={(event)=> this.addNoteToGroup(event)}>Share this note with the group</button>
                  : null
                }
               
                </div></div>
                <div className="col-md-4 fill" align="center">
                <p>Share the note with your colleagues</p>
                <div className="share-options">
                <li className="network">
                <TwitterShareButton
                  className="network__share-button"
                  url={String(window.location)}
                  title={this.state["course_name"]}>
                  <TwitterIcon
                    size={50}
                  />
                </TwitterShareButton>
              </li>
              <li className="network">
                <EmailShareButton 
                  className="network__share-button"
                  url=""
                  subject="My note"
                  separator=" "
                  body={
                    this.state.content?
                    this.state.content.replaceAll("<p>", "").replaceAll("</p>", "")
                  :null
                }
                  ><EmailIcon
                     size={50}
                  />
                  </EmailShareButton>
              </li>
              </div>
              <p>Integrate a Youtube video!</p>
              <p>Paste the URL here: </p>
              <form onSubmit={(event)=> this.handleSubmit(event)}>
              <input id="theURL" type="text" name="url" onChange={(event) => this.updateUrl(event)}></input>
              <br></br>
              <input type="submit" className="btn btn-light btn-options" value="Add"></input>
              
              </form>
            {

              this.state.youtube? 
              <YouTube videoId={this.state.url}  opts={ {height: '250',
              width: '350'
              }} onReady={this._onReady} />
              : null
            
            }
            <p>Listen to music while you're writing your note!</p>
            <p>Paste the URL here: </p>
            <form onSubmit={(event)=> this.handleSubmitSoundCloud(event)}>
              <input id="theURLSoundCloud" type="text" name="url" onChange={(event) => this.updateSoundCloudUrl(event)}></input>
              <br></br>
              <input type="submit" className="btn btn-light btn-options" value="Add"></input>
              
              </form>
            {
              this.state.soundcloud?
            <ReactPlayer url={this.state.urlSoundCloud} /> :null
            }
            </div>
            </div>
            
        )
    }
    
}