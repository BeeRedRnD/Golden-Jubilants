'use strict';

const auth = require('basic-auth');
const jwt = require('jsonwebtoken');

const register = require('./functions/register');
const login = require('./functions/login');
const profile = require('./functions/profile');

const opportunity= require('./functions/loadopportunity');


const password = require('./functions/password');
const config = require('./config/config.json');

const addOpportunity= require('./functions/addopportunity')

module.exports = router => {

	router.get('/', (req, res) => res.end('Welcome to Learn2Crack !'));

	router.post('/authenticate', (req, res) => {

		const credentials = auth(req);

		if (!credentials) {

			res.status(400).json({ message: 'Invalid Request !' });

		} else {

			login.loginUser(credentials.name, credentials.pass)

			.then(result => {

				const token = jwt.sign(result, config.secret, { expiresIn: 1440 });

				res.status(result.status).json({ message: result.message, token: token });

			})

			.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	router.post('/users', (req, res) => {

		const name = req.body.name;
		const email = req.body.email;
		const password = req.body.password;
		const county= req.body.county;
		const frequency= req.body.frequency;
		const skills= req.body.skills;
		// const preferencesList= req.body.preferencesList;

		if (!name || !email || !password || !name.trim() || !email.trim() || !password.trim()) {

			res.status(400).json({message: 'Invalid Request !'});

		} else {

			register.registerUser(name, email, password, county, frequency, skills)

			.then(result => {

				res.setHeader('Location', '/users/'+email);
				res.status(result.status).json({ message: result.message })
			})

			.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	/////bilal insertion starts
	router.post('/opportunities', (req, res) => {

		const title = req.body.title;
		const description = req.body.description;
		const addedby = req.body.addedby;
		const location= req.body.location;

		console.log("ZZZZZZZZZZ"+title)
		// const password = req.body.password;
		// const county= req.body.county;
		// const frequency= req.body.frequency;
		// const skills= req.body.skills;
		// const preferencesList= req.body.preferencesList;

		if (!title || !description || !title.trim() || !description.trim() || !addedby.trim() || !location.trim()) {

			res.status(400).json({message: 'Invalid Request !'});

		} else {

			addOpportunity.addOpportunity(title, description, addedby, location)

			.then(result => {

				console.log("New opportunity addddddded");
				//res.setHeader('Location', '/users/'+email);
				//res.status(result.status).json({ message: result.message })
			})

			.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});



	///bilal inserttion ends

	router.get('/users/:id', (req,res) => {

		if (checkToken(req)) {

			profile.getProfile(req.params.id)

			.then(result => res.json(result))

			.catch(err => res.status(err.status).json({ message: err.message }));

		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});



// bilal second insertion starts
router.get('/opportunities/:id', (req,res) => {

	if (checkToken(req)) {

		opportunity.getOpportunity(req.params.id)

		.then(result => res.json(result))

		.catch(err => res.status(err.status).json({ message: err.message }));

	} else {

		res.status(401).json({ message: 'Invalid Token !' });
	}
});


//bilal second insertion ends





	router.put('/users/:id', (req,res) => {

		if (checkToken(req)) {

			const oldPassword = req.body.password;
			const newPassword = req.body.newPassword;

			if (!oldPassword || !newPassword || !oldPassword.trim() || !newPassword.trim()) {

				res.status(400).json({ message: 'Invalid Request !' });

			} else {

				password.changePassword(req.params.id, oldPassword, newPassword)

				.then(result => res.status(result.status).json({ message: result.message }))

				.catch(err => res.status(err.status).json({ message: err.message }));

			}
		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	router.post('/users/:id/password', (req,res) => {

		const email = req.params.id;
		const token = req.body.token;
		const newPassword = req.body.password;

		if (!token || !newPassword || !token.trim() || !newPassword.trim()) {

			password.resetPasswordInit(email)

			.then(result => res.status(result.status).json({ message: result.message }))

			.catch(err => res.status(err.status).json({ message: err.message }));

		} else {

			password.resetPasswordFinish(email, token, newPassword)

			.then(result => res.status(result.status).json({ message: result.message }))

			.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	function checkToken(req) {

		const token = req.headers['x-access-token'];

		if (token) {

			try {

  				var decoded = jwt.verify(token, config.secret);

  				return decoded.message === req.params.id;

			} catch(err) {

				return false;
			}

		} else {

			return false;
		}
	}
}
