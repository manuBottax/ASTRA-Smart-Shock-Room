var express = require('express');
var router = express.Router();

var dataController = require('../controllers/dataController');

router.get('/data/:patient_id', dataController.getAllPatientData);
router.get('/data/:patient_id/:data_type', dataController.getPatientDataPerType);
router.get('/data/:patient_id/test', dataController.getSinglePatientData);

router.post('/data', dataController.savePatientData);

module.exports = router;

