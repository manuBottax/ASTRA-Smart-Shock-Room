var express = require('express');
var router = express.Router();

var commandController = require('../controllers/commandController');

router.post('/commands/', commandController.postCommand);
router.get('/commands/:command_id', commandController.getCommand);
router.get('/commands/pending', commandController.getPendingCommand);
router.get('/commands/completed', commandController.getCompletedCommand);
router.get('/commands/failed', commandController.getFailedCommand);
router.put('/commands/:command_id', commandController.updateCommand);
router.delete('/commands/:command_id', commandController.deleteCommand);

module.exports = router;

