/*jshint esversion: 6 */

var express = require('express');
var router = express.Router();
var dao = require('../utils/dao');
var bcrypt = require("bcrypt");
var logger = require("../utils/logger");


var accounts_bo = require('../bos/accounts_bo');
var profile_bo = require('../bos/profile_bo');

// Dummy Homepage GET route
router.get('/', function(req, res, next) {
	res.send({});
});


// Account Related Routes
router.post('/register', function(req, res, next) {
	if (exists(req.body.password) &&
		exists(req.body.email) &&
		req.body.password.match(password_validator) !== null &&
		req.body.email.match(email_validator) !== null) {
		accounts_bo.register(req.body.email, req.body.password, req.body.fname, req.body.lname, req.body.screenname, res);
	} else {
		res.send({
			'status_code': 400,
			'message': 'Bad Details'
		});
	}
});

router.post('/verifyAccount', function(req, res, next) {
	if (exists(req.body.code) && isNum(req.body.code) &&
		exists(req.body.email) &&
		req.body.email.match(email_validator) !== null) {
		accounts_bo.verifyAccount(req.body.email, req.body.code, res);
	} else {
		res.send({
			'status_code': 400,
			'message': 'Bad Details'
		});
	}
});

router.post('/signin', function(req, res, next) {
	if (exists(req.body.password) &&
		exists(req.body.email) &&
		req.body.password.match(password_validator) !== null &&
		req.body.email.match(email_validator) !== null) {
		accounts_bo.signin(req.body.email, req.body.password, req, res);
	} else {
		res.send({
			'status_code': 400,
			'message': 'Bad Credentials'
		});
	}

});

router.post('/emailAvailable', function(req, res, next) {
	accounts_bo.checkEmailAvailability(req.body.email, res);
});

router.post('/forgot', function(req, res, next) {
	if (exists(req.body.email) &&
		req.body.email.match(email_validator) !== null) {
		accounts_bo.handleForgotRequest(req.body.email, res);
	} else {
		res.send({
			'status_code': 400,
			'message': 'Bad Email'
		});
	}
});


// Account Related Routes
router.post('/updateProfile', function(req, res, next) {
	if ((exists(req.body.profile_id) && isNum(req.body.profile_id)) ||
		(exists(req.body.account_id) && isNum(req.body.account_id))) {
		profile_bo.updateProfile({
			'profile_id': req.body.profile_id,
			'account': req.body.account_id,
			'f_name': req.body.f_name,
			'l_name': req.body.l_name,
			'profile_pic': req.body.profile_pic,
			'location': req.body.location,
			'profession': req.body.profession,
			'about_me': req.body.about_me,
			'screen_name': req.body.screen_name
		}, res);
	} else {
		res.send({
			'status_code': 400,
			'message': 'Bad Request'
		});
	}
});

router.post('/fetchProfile', function(req, res, next) {
	if ((exists(req.body.profile_id) && isNum(req.body.profile_id)) ||
		(exists(req.body.account_id) && isNum(req.body.account_id))) {
		profile_bo.fetchProfile({
			'profile_id': req.body.profile_id,
			'account': req.body.account_id
		}, res);
	} else {
		res.send({
			'status_code': 400,
			'message': 'Bad Request'
		})
	}
});

module.exports = router;