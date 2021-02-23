const express = require('express');
const bodyParser = require('body-parser')
const Sequelize = require('sequelize')
const multer = require('multer');
const upload = multer({ dest: 'uploads/' })
const cors = require('cors');
const app = express();
const fs = require('fs');
const { group } = require('console');


// app.use(bodyParser.json());
app.use(bodyParser.json({limit: '200mb'}));
app.use(bodyParser.urlencoded({limit: '200mb', extended: true}));
app.use(bodyParser.text({ limit: '200mb',extended: true }));



app.use(
	cors({
		origin:true,
		credentials:true
	})
)

//DB CREDENTIALS
const sequelize = new Sequelize('webdev','root','tepuppapa',{
	dialect : 'mysql',
	define : {
		timestamps : false
	}
})

sequelize.authenticate()
.then(()=>{
    console.log('connected');
})
.catch((err)=>{
    console.warn(err)
})

const storage = multer.diskStorage({
	destination: (req, file, cb) => {
	  cb(null, '/uploads')
	},
	filename: (req, file, cb) => {
	  cb(null, file.originalname)
	}
  })
 
const User= sequelize.define('user', {
	name: Sequelize.STRING,
	// : Sequelize.STRING,

	email: Sequelize.STRING,
    user_pass: Sequelize.STRING
}, {
	underscored : true
})


const Note= sequelize.define('note', {
	course_name: Sequelize.STRING,
	content	: Sequelize.TEXT,
    course_number: Sequelize.INTEGER
}, {
	underscored : true
})

const UserGroup= sequelize.define('group', {
	name: Sequelize.STRING},
 {
	underscored : true
})

const NoteGroup= sequelize.define('noteGroup', {
}, {
	underscored : true
})

const File= sequelize.define('file', {
	file	: Sequelize.TEXT,
	type: Sequelize.STRING,
	name: Sequelize.STRING
}, {
	underscored : true
})

User.hasMany(Note);
Note.belongsTo(User);
UserGroup.hasMany(User);
// User.belongsTo(UserGroup);
UserGroup.hasMany(Note);
// Note.belongsTo(UserGroup);
File.belongsTo(Note);
Note.hasMany(File);

module.exports=sequelize;


//CREATE TABLES
app.get('/create', async (req, res) => {
	try{
		await sequelize.sync({force : true})
		res.status(201).json({message : 'created'})
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})



//USERS API METHODS - GET (ALL), GET BY ID, POST

//USERS GET 
app.get('/users',async (req,res)=>{
    try{
		let users= await User.findAll()
		res.json(users)
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}

})

//USERS POST
app.post('/users', async (req, res) => {
	try{
		if (req.query.bulk && req.query.bulk == 'on'){
			await User.bulkCreate(req.body)
			res.status(201).json({message : 'created'})
		}
		else{
			await User.create(req.body)
			res.status(201).json({message : 'created'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

//USER GET BY ID
app.get('/users/:id', async (req, res) => {
	try{
		let user = await User.findByPk(req.params.id)
	
		if (user){
			res.status(200).json(user)
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

//get by groupId
app.get('/usersGroup/:groupId',async (req,res)=>{
    try{
		let users= await User.findAll({
			where :{groupId :req.params.groupId}
		})
		res.json(users)
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}

})



app.put('/usersEmail/:email', async (req, res) => {
	try{
		let user = await User.findAll({
			where: {
				email: req.params.email
			}
		})
		if (user){
			await user[0].update(req.body)
			res.status(202).json({message : 'accepted',user:user[0]})
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})


app.put('/users/:id', async (req, res) => {
	try{
		let user = await User.findByPk(req.params.id)
		if (user){
			await user.update(req.body)
			res.status(202).json({message : 'accepted'})
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})


app.delete('/users/:id', async (req, res) => {
	try{
		let user = await User.findByPk(req.params.id)
		if (user){
			await user.destroy()
			res.status(202).json({message : 'accepted'})
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})



// NOTE API METHODS - GET (ALL), POST

//NOTES GET
app.get('/notes',async (req,res)=>{
    try{
		let notes= await Note.findAll()
		res.json(notes)
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}

})

//NOTE GET BY ID

app.get('/notes/:id', async (req, res) => {
	try{
		let note = await Note.findByPk(req.params.id)
	
		if (note){
			res.status(200).json(note)
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

//NOTE POST
app.post('/notes', async (req, res) => {
	try{
		if (req.query.bulk && req.query.bulk == 'on'){
			await Note.bulkCreate(req.body)
			res.status(201).json({message : 'created'})
		}
		else{ const note= await Note.create(req.body)
			res.status(201).json({message : 'created',body:note})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

//GET USER'S NOTES BY USER_ID
app.get('/usersNotes/:userid', async (req, res) => {
	try{
		let notes = await Note.findAll({
			where: {
				userId: req.params.userid
			}
		})
	
		if (notes){
			res.status(200).json(notes)
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

//GET NOTES BY GROUP_ID
app.get('/groupNotes/:groupid', async (req, res) => {
	try{
		let notes = await Note.findAll({
			where: {
				groupId: req.params.groupid
			}
		})
	
		if (notes){
			res.status(200).json(notes)
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

app.put('/notes/:id', async (req, res) => {
	try{
		let note = await Note.findByPk(req.params.id)
		if (note){
			await note.update(req.body)
			res.status(202).json({message : 'accepted'})
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

app.delete('/notes/:id', async (req, res) => {
	try{
		let note = await Note.findByPk(req.params.id)
		if (note){
			await note.destroy()
			res.status(202).json({message : 'accepted'})
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})



//IMAGES API GET, POST METHODS

//IMAGES GET
app.get('/files',async (req,res)=>{
    try{
		let files= await File.findAll()
		res.json(files)
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})


app.get('/fileById/:id', async (req, res) => {
	try{
		let file = await File.findByPk(req.params.id)
		if (file){
			res.status(200).json(file)
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})


//IMAGE GET BY NOTE_ID
app.get('/filesByNote/:noteId',async (req,res)=>{
    try{
		let files= await File.findAll({
			where: {
				noteId: req.params.noteId
			}
		})
		// files.forEach(file => {
		// 	file.file=file.file.text()	
		// });
		res.json(files)
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})


app.put('/files/:id', async (req, res) => {
	try{
		let file = await File.findByPk(req.params.id)
		if (file){
			await file.update(req.body)
			res.status(202).json({message : 'accepted'})
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

app.delete('/files/:id', async (req, res) => {
	try{
		let file = await File.findByPk(req.params.id)
		if (file){
			await file.destroy()
			res.status(202).json({message : 'accepted'})
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

// try{
	
	// 	// var img = fs.readFileSync(req.file.path);
	// 	// var encode_file = img.toString('base64');
	// 	// Define a JSONobject for the file attributes for saving to database
	// 		//  contentType: req.file.mimetype,
		 
	// 	// var finalImg = {
	// 	// 	 file:  new Buffer(encode_file, 'base64'),
	// 	// 	 note_id:req.body.noteId
	// 	//   };

	// 		// const filePath = `uploads/${req.file.filename}`

	// 		// (finalImg)
	// 		const file = await File.create 
	// 		({ file: `http://localhost:8080/uploads/${req.file.filename}`,
	// 		noteId: req.body.noteId })
				
	// 		//post with form-data:
	// 			// {"file": *upload file*,
	// 			// 	"noteId": "value"	}

	// 		res.status(201).json({message : 'created',
	// 	file:file})
//IMAGE POST 
app.post('/files', async (req, res) => {
	try{
	if (req.query.bulk && req.query.bulk == 'on'){
					await File.bulkCreate(req.body)
					res.status(201).json({message : 'created'})
				}
				else{ const file= await File.create(req.body)
					res.status(201).json({message : 'created',file:file}) //WHAT IS THIS body:note ???
				}
			}
			catch(e){
				console.warn(e)
				res.status(500).json({message : 'server error',e:e})
			}
	
})

//USERGROUP METHODS GET, POST

//GROUPS GET
app.get('/groups',async (req,res)=>{
    try{
		let groups= await UserGroup.findAll()
		res.json(groups)
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}

})

//GROUPS GET BY ID
app.get('/groups/:id', async (req, res) => {
	try{
		let groups = await UserGroup.findByPk(req.params.id)
	
		if (groups){
			res.status(200).json(groups)
		}
		else{
			res.status(404).json({message : 'not found'})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

// //GROUPS POST
// app.post('/groups', async (req, res) => {
// 	try{
// 		if (req.query.bulk && req.query.bulk == 'on'){
// 			await UserGroup.bulkCreate(req.body)
// 			res.status(201).json({message : 'created'})
// 		}
// 		else{ const UserGroup= await UserGroup.create(req.body)
// 			res.status(201).json({message : 'created'}) //WHAT IS THIS body:note ???
// 		}
// 	}
// 	catch(e){
// 		console.warn(e)
// 		res.status(500).json({message : 'server error'})
// 	}
// })

app.post('/groups', async (req, res) => {
	try{
		if (req.query.bulk && req.query.bulk == 'on'){
			const group=await UserGroup.bulkCreate(req.body)
			res.status(201).json({message : 'created',group:group})
		}
		else{ const group= await UserGroup.create(req.body)
			res.status(201).json({message : 'created',group:group})
		}
	}
	catch(e){
		console.warn(e)
		res.status(500).json({message : 'server error'})
	}
})

// //NOTEGROUP METHODS GET, POST

// //NOTEGROUP GET
// app.get('/notegroups',async (req,res)=>{
//     try{
// 		let groups= await NoteGroup.findAll()
// 		res.json(groups)
// 	}
// 	catch(e){
// 		console.warn(e)
// 		res.status(500).json({message : 'server error'})
// 	}

// })

// //NOTEGROUP GET BY ID
// app.get('/notegroups/:id', async (req, res) => {
// 	try{
// 		let groups = await NoteGroup.findByPk(req.params.id)
	
// 		if (groups){
// 			res.status(200).json(groups)
// 		}
// 		else{
// 			res.status(404).json({message : 'not found'})
// 		}
// 	}
// 	catch(e){
// 		console.warn(e)
// 		res.status(500).json({message : 'server error'})
// 	}
// })

// //NOTEGROUP POST
// app.post('/notegroups', async (req, res) => {
// 	try{
// 		if (req.query.bulk && req.query.bulk == 'on'){
// 			await NoteGroup.bulkCreate(req.body)
// 			res.status(201).json({message : 'created'})
// 		}
// 		else{ const NoteGroup= await NoteGroup.create(req.body)
// 			res.status(201).json({message : 'created'}) //WHAT IS THIS body:note ???
// 		}
// 	}
// 	catch(e){
// 		console.warn(e)
// 		res.status(500).json({message : 'server error'})
// 	}
// })

app.listen(8080,()=>{
    console.log("server is up");
})