
var jsonUtils = require("./jsonUtilities");
var mongoose = require( 'mongoose');

var DB = mongoose.model('TACData');

/*
    TACData {
        id : String,
        patient_id : String,
        path : String,
        timestamp : Date
    }
*/

module.exports.postTACData = async function (req, res) {

    var data = {
        patient_id : req.body.patient_id,
        path : req.body.path
    }

    DB.create(data , function(err, tac_data) {
        if (err) {
            jsonUtils.sendJsonResponse(res, 400, err);
        } else {
            console.log("TAC Data saved successfully !")
            jsonUtils.sendJsonResponse(res, 201, tac_data);
        }
    }); 

}


// router.get('/api/tac_data/:patient_id', tacController.getTACByPatient);
module.exports.getTACByPatient = function(req,res) {

    if (req.params && req.params.patient_id) {

        var patient_id = req.params.patient_id

        DB
        .find({"patient_id" : patient_id})
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, err);
                return;
            } else {
                console.log("Patient data retrieved !")
                jsonUtils.sendJsonResponse(res, 200, data);
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid patient_id params");
    }

}

