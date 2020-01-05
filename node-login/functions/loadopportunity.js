'use strict';

const opportunity = require('../models/opportunities');

exports.getOpportunity = email =>

	new Promise((resolve,reject) => {

		opportunity.find({  }, { title: 1, description: 1, email: 1, addedby: 1, location: 1, _id: 0 })

		.then(opportunity => resolve(opportunity))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }))

	});
