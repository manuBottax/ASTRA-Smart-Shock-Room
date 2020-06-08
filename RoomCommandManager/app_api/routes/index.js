var express = require('express');
var router = express.Router();

var commandController = require('../controllers/commandController');

router.post('/commands', commandController.postCommand);

router.get('/commands/pending', commandController.getPendingCommand);
router.get('/commands/in_processing', commandController.getProcessingCommand);
router.get('/commands/completed', commandController.getCompletedCommand);
router.get('/commands/failed', commandController.getFailedCommand);
router.get('/commands/:command_id', commandController.getCommand);

router.put('/commands/:command_id', commandController.updateCommand);
router.put('/commands/:command_id/status', commandController.updateStatus);

router.delete('/commands/:command_id', commandController.deleteCommand);

module.exports = router;

