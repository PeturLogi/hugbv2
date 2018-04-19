var Account = require('../models/account');

exports.index = function(req, res) {
    res.send('NOT IMPLEMENTED: Site Home Page');
};

// Get all accounts
exports.account_list = function(req, res) {
	Account.find(function(err, accounts) {
		if (err) {
			return res.send(err);
		}

		res.json(accounts);
	});
};

// Get a specific Account.
exports.account_get_one = function(req, res) {
    Account.findOne({_id: req.params.id}, function(err, account) {
    	if (err) {
    		return res.send(err);
    	}

    	res.json(account);
    });
};

// Display Account create form on GET.
//exports.account_create_get = function(req, res) {
//    res.send('NOT IMPLEMENTED: Account create GET');
//};

// Handle Account create on POST.
exports.account_create_post = function(req, res) {
    var account = new Account(req.body);

    account.save(function(err) {
    	if (err) {
    		return res.send(err);
    	}

    	res.send({ message: 'Account Added'});
    });
};

// Display Account delete form on GET.
exports.account_delete = function(req, res) {
    Account.remove({
    	_id: req.params.id
    }, function(err, account) {
    	if (err) {
    		return res.send(err);
    	}

    	res.json({ message: 'Successfully deleted'});
    });
};

// Handle Account delete on POST.
//exports.account_delete_post = function(req, res) {
//    res.send('NOT IMPLEMENTED: Account delete POST');
//};

// Display Account update form on GET.
//exports.account_update_get = function(req, res) {
//    res.send('NOT IMPLEMENTED: Account update GET');
//};

// Handle Account update on PUT.
exports.account_update_put = function(req, res) {
    Account.findOne({_id: req.params.id }, function(err, account) {
    	if (err) {
    		return res.send(err);
    	}

    	for (prop in req.body) {
    		account[prop] = req.body[prop];
    	}

    	// save account
    	account.save(function(err) {
    		if (err) {
    			return res.send(err);
    		}

    		res.json({ message: 'Account updated!'});
    	});
    });
};