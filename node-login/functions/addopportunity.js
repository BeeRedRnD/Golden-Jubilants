'use strict';

const opportunities = require('../models/opportunities');
//const bcrypt = require('bcryptjs');

exports.addOpportunity = (title, description, addedby, location) => {

    return new Promise((resolve,reject) => {

        console.log("Inside new promise");
        const newOpportunity = new opportunities({
            title: title,
            description: description,
			addedby: addedby,
			location: location
        });

        console.log("about to save");
        newOpportunity.save()
        .then(() => {
            console.log("calling then");
            resolve({ status: 201, message: 'New Opportunity Added Sucessfully !' });
        })
        .catch(err => {
            console.log("calling catch with error: " + err);

            if (err.code === 11000) {
                reject({ status: 409, message: 'Opportunity already saved !' });

            } else {
                reject({ status: 500, message: 'Internal Server Error !' });
            }
        });
    });
};
