
var jsonUtils = require("./jsonUtilities");
var mongoose = require( 'mongoose');

var DB = mongoose.model('Trauma');

/*
    // Trauma {
    //     _id : String,
    //     current_trauma_status : String,
    //     startOperatorId : String,
    //     startOperatorDescription : String,
    //     delayedActivation : {},
    //     traumaTeamMembers : String[],
    //     preH : {},
    //     traumaInfo : {},
    //     patientInitialCondition: {}
    //     events : [{}]
    // }
*/

//router.post('/api/trauma', traumaController.postTrauma);
module.exports.postTrauma = async function (req, res) {

    var data = {
        startOperatorId : req.body.startOperatorId,
        startOperatorDescription : req.body.startOperatorDescription,
        delayedActivation : req.body.delayedActivation,
        traumaTeamMembers : req.body.traumaTeamMembers
    }

    DB.create(data , function(err, trauma) {
        if (err) {
            jsonUtils.sendJsonResponse(res, 400, err);
        } else {
            console.log("Trauma created successfully !")
            jsonUtils.sendJsonResponse(res, 201, trauma._id);
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
        .deleteOne({"_id" : trauma_id})
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


// router.get('/api/trauma/:trauma_id/trauma_current_status',traumaController.getTraumaStatus)
module.exports.getTraumaStatus = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                // var status = data.trauma_current_status;
                jsonUtils.sendJsonResponse(res, 200, {"trauma_current_status" : data.trauma_current_status });
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.put('/api/trauma/:trauma_id/trauma_current_status',traumaController.updateTraumaStatus)
module.exports.updateTraumaStatus = function(req, res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.trauma_current_status){
                    data.trauma_current_status = req.body.trauma_current_status;
                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during status update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, {"trauma_current_status" : data.trauma_current_status });
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

                jsonUtils.sendJsonResponse(res, 200, {
                    "traumaLeader" : data.startOperatorDescription, 
                    "traumaTeamMembers" : JSON.parse(data.traumaTeamMembers)
                });
                
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
    
}

// router.put ('/api/trauma/:trauma_id/trauma_team', traumaController.updateTraumaTeam);
// module.exports.updateTraumaTeam = function(req,res) {

//     if (req.params && req.params.trauma_id) {

//         var trauma_id = req.params.trauma_id

//         DB
//         .findById(trauma_id)
//         .exec(function(err, data) {
//             if (err){
//                 jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
//                 return;
//             } else {
                
//                 if (req.body.trauma_team){

//                     data.trauma_team = req.body.trauma_team;

//                     DB.updateOne({"_id" : trauma_id}, data)
//                       .exec(function(update_err, update_data){
//                           if (update_err) {
//                             jsonUtils.sendJsonResponse(res, 400, "Error during trauma_team update");
//                             return;
//                           } else {
//                             jsonUtils.sendJsonResponse(res, 200, {"trauma_team" : data.trauma_team });
//                           }
//                       });
//                 } else {
//                     jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_team in request body");
//                 } 
//             }
//         }); 

//     } else {
//         jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
//     }
// }


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
                if(data.preh){
                    jsonUtils.sendJsonResponse(res, 200, data.preh );
                } else {
                    jsonUtils.sendJsonResponse(res, 200, {} );
                }
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
                
                if (req.body.preh){

                    data.preh = JSON.parse(req.body.preh);

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during preh update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, update_data);
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid preh in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.get ('/api/trauma/:trauma_id/trauma_info', traumaController.getTraumaInfo);
module.exports.getTraumaInfo = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                // console.log(data);
                if(data.traumaInfo){
                    jsonUtils.sendJsonResponse(res, 200, data.traumaInfo );
                } else {
                    jsonUtils.sendJsonResponse(res, 200, {} );
                }
                // jsonUtils.sendJsonResponse(res, 200, data.traumaInfo );
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }   
}


// router.put ('/api/trauma/:trauma_id/trauma_info', traumaController.updateTraumaInfo);
module.exports.updateTraumaInfo = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.traumaInfo){

                    data.traumaInfo = JSON.parse(req.body.traumaInfo);

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during traumaInfo update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, data.traumaInfo);
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid traumaInfo in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.get ('/api/trauma/:trauma_id/patient_initial_condition', traumaController.getPatientInitialCondition);
module.exports.getPatientInitialCondition = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                // console.log(data);
                if(data.patientInitialCondition){
                    jsonUtils.sendJsonResponse(res, 200, data.patientInitialCondition );
                } else {
                    jsonUtils.sendJsonResponse(res, 200, {} );
                }
                // jsonUtils.sendJsonResponse(res, 200, data.patientInitialCondition );
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }   
}

// router.put ('/api/trauma/:trauma_id/patient_initial_condition', traumaController.updatePatientInitialCondition);
module.exports.updatePatientInitialCondition = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.patientInitialCondition){

                    data.patientInitialCondition = JSON.parse(req.body.patientInitialCondition);

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during Patient Initial Condition update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, data.patientInitialCondition);
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid patientInitialCondition in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.post('/api/trauma/:trauma_id/events', traumaController.addEvent);
module.exports.addEvent = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                if (req.body.event){

                    data.events.push(JSON.parse(req.body.event));

                    DB.updateOne({"_id" : trauma_id}, data)
                      .exec(function(update_err, update_data){
                          if (update_err) {
                            jsonUtils.sendJsonResponse(res, 400, "Error during events update");
                            return;
                          } else {
                            jsonUtils.sendJsonResponse(res, 200, data.events);
                          }
                      });
                } else {
                    jsonUtils.sendJsonResponse(res, 404, "Invalid event in request body");
                } 
            }
        }); 

    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id params");
    }
}

// router.get('/api/trauma/:trauma_id/events', traumaController.getEventList);
module.exports.getEventList = function(req,res) {

    if (req.params && req.params.trauma_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {

                var events = data.events;

                jsonUtils.sendJsonResponse(res, 200, events );
                
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id or event_id params");
    }   
}

// router.get ('/api/trauma/:trauma_id/events/:event_id'', traumaController.getEvent);
module.exports.getEvent = function(req,res) {

    if (req.params && req.params.trauma_id && req.params.event_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {
                
                var ev = data.events.find((event) => {console.log(event.eventId) ; console.log(req.params.event_id) ; console.log(event.eventId == req.params.event_id) ; return event.eventId == req.params.event_id});
                if (ev){
                    jsonUtils.sendJsonResponse(res, 200,  ev );
                } else {
                    jsonUtils.sendJsonResponse(res, 404,  "not found" );
                }
                
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id or event_id params");
    }   
}

// router.put ('/api/trauma/:trauma_id/events/:event_id', traumaController.updateEvent);
module.exports.updateEvent = function(req,res) {

    if (req.params && req.params.trauma_id && req.params.event_id) {

        var trauma_id = req.params.trauma_id

        DB
        .findById(trauma_id)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, "Trauma not found");
                return;
            } else {

                for (let i = 0; i < data.events.length; i++) {
                    const e = data.events[i];

                    if (e.eventId == req.params.event_id){
                        found = true;
                        data.events[i] = JSON.parse(req.body.event);
                        DB.updateOne({"_id" : trauma_id}, data)
                          .exec(function(update_err, update_data){
                                if (update_err) {
                                    jsonUtils.sendJsonResponse(res, 400, "Error during events update");
                                    return;
                                } else {
                                    jsonUtils.sendJsonResponse(res, 200, data.events);
                                    return;
                                }
                          });
                    }
                }
            } 
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "Invalid trauma_id or event_id params");
    }   
}

