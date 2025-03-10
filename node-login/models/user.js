'use strict';

const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const userSchema = mongoose.Schema({

	name 			: String,
	email			: String,
	hashed_password	: String,
	created_at		: String,
	temp_password	: String,
	temp_password_time: String,
	county			: String,
	frequency		: String,
	skills: String
	// ,preferencesList	: [
	// 	{
	// 		provider: String,
	// 		receiver: String,
	// 		both: String
	// 	}
	// ]

});

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/node-login');

module.exports = mongoose.model('user', userSchema);
