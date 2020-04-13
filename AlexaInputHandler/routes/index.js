var express = require('express');
var router = express.Router();

var rabbitController = require('../controller/rabbitMessageController')

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/parameterRequest', rabbitController.parameterRequest);

module.exports = router;
