'use strict';

const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const opportunitiesSchema = mongoose.Schema({

	title 			: String,
	description     : String,
	addedby			: String,
	location 		: String
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

module.exports = mongoose.model('opportunities', opportunitiesSchema);
