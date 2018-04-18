var express = require('express');
var router = express.Router();

var account_controller = require('../controllers/accountController');


/// ACCOUNT ROUTES ///

// GET souschef home page.
router.get('/', account_controller.index);

// GET request for creating Account. NOTE This must come before route for id (i.e. display account).
//router.get('/account/create', account_controller.account_create_get);

//router.post('/authenticate', account_controller.authenticate);

// POST request for creating Account.
router.post('/account/create', account_controller.account_create_post);

// DELETE request to delete Account.
router.delete('/account/:id/delete', account_controller.account_delete);

// POST request to delete Account.
//router.post('/account/:id/delete', account_controller.account_delete_post);

// GET request to update Account.
//router.get('/account/:id/update', account_controller.account_update_get);

// PUT request to update Account.
router.put('/account/:id/update', account_controller.account_update_put);

// GET request for one Account.
router.get('/account/:id', account_controller.account_get_one);

// Change password of specific account
router.post('/account/:id/password', account_controller.account_change_pass);

// GET request for all Accounts.
router.get('/accounts', account_controller.account_list);


module.exports = router;