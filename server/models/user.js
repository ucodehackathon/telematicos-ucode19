'use strict';

const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const userSchema = mongoose.Schema({ 

	user_name 		: String,
	full_name		: String,
	email			: String, 
	hashed_password	: String,
	gender			: String,
	age				: Number,
	weight			: Number,
	created_at		: String,
	temp_password	: String,
	temp_password_time: String
	
});

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/node-login');

module.exports = mongoose.model('user', userSchema);        