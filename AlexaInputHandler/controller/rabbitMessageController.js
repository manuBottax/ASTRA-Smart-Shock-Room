var jsonUtils = require("../jsonUtilities");

var sender = require('../rabbitMQ/sender');



module.exports.parameterRequest = function(req,res) {

    var patient_id = req.body.patient_id;
    var data_type = 'unsupported';

    switch (req.body.data_type) {
        case 'battito':
            data_type = 'bpm';
            break;

        case 'pressione':
            data_type = 'blood_pressure_max';
            break;

        case 'tutto':
            data_type = 'all';
            break;
    
        default:
            data_type = 'unsupported';
            break;
    }

    var msg = '{ \"patient_id\" : \"' + patient_id + '\" , \"data_type\" : \"' + data_type +'\" }';

    sender.sendMessage(msg);
    jsonUtils.sendJsonResponse(res, 200, "OK");
}