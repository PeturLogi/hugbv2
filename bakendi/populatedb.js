#! /usr/bin/env node

console.log('This script populates some test books, authors, genres and bookinstances to your database. Specified database as argument - e.g.: populatedb mongodb://your_username:your_password@your_dabase_url');

// Get arguments passed on command line
var userArgs = process.argv.slice(2);
if (!userArgs[0].startsWith('mongodb://')) {
    console.log('ERROR: You need to specify a valid mongodb URL as the first argument');
    return
}

var async = require('async')
var Account = require('./models/account')

var mongoose = require('mongoose');
var mongoDB = userArgs[0];
mongoose.connect(mongoDB);
mongoose.Promise = global.Promise;
var db = mongoose.connection;
mongoose.connection.on('error', console.error.bind(console, 'MongoDB connection error:'));

var accounts = [];

function accountCreate(email_string, name_string, address_string, mobileNr_string, password_string, cb) {
	accountdetail = {email_string:email_string, name_string:name_string, address_string:address_string, 
						mobileNr_string:mobileNr_string, password_string:password_string}

	var account = new Account(accountdetail);

	account.save(function (err) {
    	if (err) {
      		cb(err, null)
      		return
    	}
    	console.log('New Account: ' + account);
    	accounts.push(account)
    	cb(null, account)
  	}  );
}


function createAccounts(cb) {
	async.parallel([
		function(callback) {
			accountCreate('hopur6@hi.is', 'hopur6', 'Studentakjallarinn', '1234567', 'hallo', callback);
		},
		function(callback) {
			accountCreate('test@hi.is', 'test', 'Haskolatorg', '8691234', 'testpass', callback);
		},
		function(callback) {
			accountCreate('souschef@souschef.com', 'Sous Chef', 'Taeknigardur', '0987654', '1234', callback);
		},
		],
		// optional callback
		cb);
}


async.series([
	createAccounts
	],
	// optional callback
	function(err, results) {
		if (err) {
			console.log('Final ERR: ' + err);
		} else {
			console.log('Accounts: ' + accounts);
		}
		//All done, disconnect from database
		mongoose.connection.close();
	})

