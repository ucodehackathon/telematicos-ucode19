'use strict';

const auth = require('basic-auth');
const jwt = require('jsonwebtoken');

const register = require('./functions/register');
const login = require('./functions/login');
const profile = require('./functions/profile');
const password = require('./functions/password');
const config = require('./config/config.json');
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

module.exports = router => {

	router.get('/', (req, res) => res.end('Welcome to our App !'));

	router.post('/authenticate', (req, res) => {

		const credentials = auth(req);

		if (!credentials) {

			res.status(400).json({ message: 'Invalid Request !' });

		} else {

			login.loginUser(credentials.name, credentials.pass)

				.then(result => {
					MongoClient.connect(url, function (err, db) {
						if (err) throw err;
						var dbo = db.db("node-login");
						dbo.collection("users").findOne({}, function (err, result1) {

							const token = jwt.sign(result, config.secret, { expiresIn: 1440 });
							res.status(result.status).json({ users: result1, token: token });
							if (err) throw err;
							console.log(result.name);
							db.close();
						});
					});
				})

				.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	router.post('/users', (req, res) => {

		const name = req.body.name;
		const email = req.body.email;
		const password = req.body.password;

		if (!name || !email || !password || !name.trim() || !email.trim() || !password.trim()) {

			res.status(400).json({ message: 'Invalid Request !' });

		} else {

			register.registerUser(name, email, password)

				.then(result => {

					res.setHeader('Location', '/users/' + email);
					res.status(result.status).json({ message: result.message })
				})

				.catch(err => res.status(err.status).json({ message: err.message }));
		}
	});

	router.get('/users/:id', (req, res) => {

		if (checkToken(req)) {

			profile.getProfile(req.params.id)

				.then(result => res.json(result))

				.catch(err => res.status(err.status).json({ message: err.message }));

		} else {

			res.status(401).json({ message: 'Invalid Token !' });
		}
	});

	
	router.put('/users/:id', (req, res) => {

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

	router.post('/users/:id/password', (req, res) => {

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

			} catch (err) {

				return false;
			}

		} else {

			return false;
		}
	}
}