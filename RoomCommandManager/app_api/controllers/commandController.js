var jsonUtils = require("../../jsonUtilities");
var mongoose = require( 'mongoose');

var DB = mongoose.model('UserCommand');


// Command Sample Format {
//     command_id : String,
//     type : String,
//     target : String,
//     issuer : String,
//     status : String,
//     timestamp : Date,
//     completed_on : Date
//     params : {
//         par1 : String,
//         par2 : String,
//         ...
//     }
//  }

// router.post('/commands/', commandController.postCommand);
module.exports.postCommand = function(req,res) {

    // var patient_id = req.params.patient_id;

    // if (req.params && req.params.patient_id) {

    //     DB
    //     .findOne({"patient_id" : patient_id})
    //     .exec(function(err, data) {
    //         if (!data){
    //             jsonUtils.sendJsonResponse(res, 404, "patient id not found");
    //             return;
    //         } else if (err){
    //             jsonUtils.sendJsonResponse(res, 404, err);
    //             return;
    //         } else {
    //             jsonUtils.sendJsonResponse(res, 200, data);
    //         }
    //     });
    // } else {
    //     jsonUtils.sendJsonResponse(res, 404, "No patient id in request");
    // }  
}

// router.get('/commands/:command_id', commandController.getCommand);
module.exports.getCommand = function(req,res) {

    // var patient_id = req.params.patient_id;

    // if (req.params && req.params.patient_id) {

    //     DB
    //     .findOne({"patient_id" : patient_id})
    //     .exec(function(err, data) {
    //         if (!data){
    //             jsonUtils.sendJsonResponse(res, 404, "patient id not found");
    //             return;
    //         } else if (err){
    //             jsonUtils.sendJsonResponse(res, 404, err);
    //             return;
    //         } else {
    //             jsonUtils.sendJsonResponse(res, 200, data);
    //         }
    //     });
    // } else {
    //     jsonUtils.sendJsonResponse(res, 404, "No patient id in request");
    // }  
}


// router.get('/commands/pending', commandController.getPendingCommand);
module.exports.getPendingCommand = function(req,res) {


}

// router.get('/commands/completed', commandController.getCompletedCommand);
module.exports.getCompletedCommand = function(req,res) {


}

// router.get('/commands/failed', commandController.getFailedCommand);
module.exports.getFailedCommand = function(req,res) {


}

// router.put('/commands/:command_id', commandController.updateCommand);
module.exports.updateCommand = function(req,res) {


}

// router.delete('/commands/:command_id', commandController.deleteCommand);
module.exports.deleteCommand = function(req,res) {


}

// module.exports.getSinglePatientData = function(req,res) {

//     var patient_id = req.params.patient_id;

//     if (req.params && req.params.patient_id) {

//         DB
//         .findOne({"patient_id" : patient_id})
//         .exec(function(err, data) {
//             if (!data){
//                 jsonUtils.sendJsonResponse(res, 404, "patient id not found");
//                 return;
//             } else if (err){
//                 jsonUtils.sendJsonResponse(res, 404, err);
//                 return;
//             } else {
//                 jsonUtils.sendJsonResponse(res, 200, data);
//             }
//         });
//     } else {
//         jsonUtils.sendJsonResponse(res, 404, "No patient id in request");
//     }  
// }

// module.exports.getAllPatientData = function(req,res) {

//     var patient_id = req.params.patient_id;

//     if (req.params && req.params.patient_id) {

//         DB
//         .find({"patient_id" : patient_id})
//         .exec(function(err, data) {
//             if (!data){
//                 jsonUtils.sendJsonResponse(res, 404, "patient id not found");
//                 return;
//             } else if (err){
//                 jsonUtils.sendJsonResponse(res, 404, err);
//                 return;
//             } else {
//                 jsonUtils.sendJsonResponse(res, 200, data);
//             }
//         });
//     } else {
//         jsonUtils.sendJsonResponse(res, 404, "No patient id in request");
//     }  
// }

// module.exports.getPatientDataPerType = function(req,res) {

//     if (req.params && req.params.patient_id && req.params.data_type) {

//         var patient_id = req.params.patient_id;
//         var data_type = req.params.data_type;

//         DB
//         .findOne({"patient_id" : patient_id , "data_type" : data_type})
//         .exec(function(err, data) {
//             if (!data){
//                 jsonUtils.sendJsonResponse(res, 404, "patient id not found");
//                 return;
//             } else if (err){
//                 jsonUtils.sendJsonResponse(res, 404, err);
//                 return;
//             } else {
//                 jsonUtils.sendJsonResponse(res, 200, data);
//             }
//         });
//     } else {
//         jsonUtils.sendJsonResponse(res, 404, "No patient id in request");
//     }  
// }

// module.exports.savePatientData = function(req,res) {

//     DB.create({
//         patient_id : req.body.patient_id,
//         data_type: req.body.data_type,
//         value : req.body.value
//     } , function(err, patientData) {
//             if (err) {
//                 jsonUtils.sendJsonResponse(res, 400, err);
//             } else {
//                 jsonUtils.sendJsonResponse(res, 201, patientData);
//             }
//         }
//     );
  
// }