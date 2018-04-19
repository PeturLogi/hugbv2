// Require Mongoose 
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var AccountSchema = new Schema( {
	email_string	: {type: String, unique: true},
	name_string		: String,
	address_string	: String,
	mobileNr_string	: String,
	password_string	: String,
	hashed_password : String
});

// Virtual for Account email   ttt
AccountSchema
.virtual('email')
.get(function() {
	return this.email_string;
});

// Virtual for Account name
AccountSchema
.virtual('name')
.get(function() {
	return this.name_string;
});

// Virtual for Account address
AccountSchema
.virtual('address')
.get(function() {
	return this.address_string;
});

// Virtual for Account mobile number
AccountSchema
.virtual('mobile')
.get(function() {
	return this.mobileNr_string;
});

// Virtual for Account password
AccountSchema
.virtual('password')
.get(function() {
	return this.password_string;
});

// Virtual for Account url
AccountSchema
.virtual('url')
.get(function() {
	return '/api/account/' + this._id;
});

// Export model.
module.exports = mongoose.model('Account', AccountSchema);