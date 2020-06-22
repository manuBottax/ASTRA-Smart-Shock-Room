

var jsonUtils = require("./jsonUtilities");
var mongoose = require( 'mongoose');

var DB = mongoose.model('Trauma');

/*
    Trauma {
        _id : String,
        patient_status : String,
        trauma_team : [{}],
        preH : [{}],
        trauma_info : [{}],
        MTC : [{}],
        anamnesis : [{}],
        vital_sign : [{}],
        report : [{}],
        events : [{}]
    }
*/

//router.post('/api/trauma', traumaController.postTrauma);
module.exports.postTrauma = async function (req, res) {

    DB.create({} , function(err, trauma) {
        if (err) {
            jsonUtils.sendJsonResponse(res, 400, err);
        } else {
            console.log("Trauma created successfully !")
            jsonUtils.sendJsonResponse(res, 201, trauma);
        }
    }); 
}

//router.get ('/api/trauma/:trauma_id', traumaController.getTrauma);
module.exports.getTrauma = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, err);
                return;
            } else {
                console.log("Trauma data retrieved !")
                jsonUtils.sendJsonResponse(res, 200, data);
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

//router.delete('/api/trauma/:trauma_id', traumaController.deleteTrauma);
module.exports.deleteTrauma = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .deleteOne({"_id " : trauma_id})
        .exec(function(err, data) {
            if (err){
                console.log("Error during trauma deletion !")
                jsonUtils.sendJsonResponse(res, 404, err);
                return;
            } else {
                console.log("Trauma deleted successfully !")
                jsonUtils.sendJsonResponse(res, 200, data);
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}


// router.get('/api/trauma/:trauma_id/patient_status',traumaController.getPatientStatus)
module.exports.getPatientStatus = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                var status = data.patient_status;
                jsonUtils.sendJsonResponse(res, 200, {"patient_status" : status });
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.put('/api/trauma/:trauma_id/patient_status',traumaController.updatePatientStatus)
module.exports.updatePatientStatus = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.patient_status){
                    data.patient_status = req.body.patient_status;
                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during status update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, {"patient_status" : update_data });
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid status in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.post('/api/trauma/:trauma_id/trauma_team/element', traumaController.addTraumaTeamElement);
module.exports.addTraumaTeamElement = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.trauma_team_element){

                    data.trauma_team.push(req.body.trauma_team_element);

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during trauma_team element creation");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 201, {"trauma_team" : data.trauma_team });
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_team_element in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.get ('/api/trauma/:trauma_id/trauma_team', traumaController.getTraumaTeam);
module.exports.getTraumaTeam = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                jsonUtils.sendJsonResponse(res, 200, {"trauma_team" : data.trauma_team });
                
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
    
}

// router.put ('/api/trauma/:trauma_id/trauma_team', traumaController.updateTraumaTeam);
module.exports.updateTraumaTeam = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.trauma_team){

                    data.trauma_team = req.body.trauma_team;

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during trauma_team update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, {"trauma_team" : data.trauma_team });
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_team in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.post('/api/trauma/:trauma_id/preH/element', traumaController.addPreHInfoElement);
module.exports.addPreHInfoElement = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.preH_element){

                    data.preH.push(req.body.preH_element);

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during preH element creation");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 201, {"preH" : data.preH });
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid preH_element in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.get ('/api/trauma/:trauma_id/preH', traumaController.getPreHInfo);
module.exports.getPreHInfo = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                jsonUtils.sendJsonResponse(res, 200, {"preH" : data.preH });
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
    
}


// router.put ('/api/trauma/:trauma_id/preH', traumaController.updatePreHInfo);
module.exports.updatePreHInfo = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.preH){

                    data.preH = req.body.preH;

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during preH update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, {"preH" : data.preH });
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid preH in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}



/* TODO : 

// ActiveTrauma

router.post('/api/trauma/:trauma_id/trauma_info', traumaController.addTraumaInfo);
router.get ('/api/trauma/:trauma_id/trauma_info', traumaController.getTraumaInfo);
router.put ('/api/trauma/:trauma_id/trauma_info', traumaController.updateTraumaInfo);

router.post('/api/trauma/:trauma_id/MTC', traumaController.addMajorTraumaCriteria);
router.get ('/api/trauma/:trauma_id/MTC', traumaController.getMajorTraumaCriteria);
router.put ('/api/trauma/:trauma_id/MTC', traumaController.updateMajorTraumaCriteria);

router.post('/api/trauma/:trauma_id/anamnesis', traumaController.addAnamnesis);
router.get ('/api/trauma/:trauma_id/anamnesis', traumaController.getAnamnesis);
router.put ('/api/trauma/:trauma_id/anamnesis', traumaController.updateAnamnesis);

router.post('/api/trauma/:trauma_id/vital_sign', traumaController.addVitalSign);
router.get ('/api/trauma/:trauma_id/vital_sign', traumaController.getVitalSign);
router.put ('/api/trauma/:trauma_id/vital_sign', traumaController.updateVitalSign);

// TraumaTracker 

router.post('/api/trauma/:trauma_id/report', traumaController.addReport);
router.get ('/api/trauma/:trauma_id/report', traumaController.getReport);
router.put ('/api/trauma/:trauma_id/report', traumaController.updateReport);

router.post('/api/trauma/:trauma_id/events', traumaController.addEvent);
router.get ('/api/trauma/:trauma_id/events', traumaController.getEvent);
router.put ('/api/trauma/:trauma_id/events', traumaController.updateEvent); */







// router.get('/api/tac_data/:patient_id', tacController.getTACByPatient);
// module.exports.getTACByPatient = function(req,res) {

//     if (req.params && req.params.patient_id) {

//         var patient_id = req.params.patient_id

//         DB
//         .find({"patient_id" : patient_id})
//         .exec(function(err, data) {
//             if (err){
//                 jsonUtils.sendJsonResponse(res, 404, err);
//                 return;
//             } else {
//                 console.log("Patient data retrieved !")
//                 jsonUtils.sendJsonResponse(res, 200, data);
//             }
//         }); 

//     } else {
//         jsonUtils.sendJsonResponse(res, 404, "Invalid patient_id params");
//     }

// }