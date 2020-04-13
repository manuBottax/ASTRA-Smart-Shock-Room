var express = require('express');
var router = express.Router();

var displayController = require('../controller/displayController');

router.post('/data', displayController.addPatientData);
router.get('/events', displayController.eventsHandler);
router.get('/status', displayController.getStatus); 

module.exports = router;