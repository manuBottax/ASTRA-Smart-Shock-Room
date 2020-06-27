var jsonUtils = require("../../jsonUtilities");
var mongoose = require( 'mongoose');
var sender = require('../rabbitMQ/publisher');

var DB = mongoose.model('UserCommand');

// Command Sample Format {
//     _id : String,
//     type : String,
//     category : String,
//     target : String,
//     issuer : String,
//     status : String,
//     timestamp : Date,
//     completed_on : Date,
//     params : {
//         par1 : String,
//         par2 : String,
//         ...
//     }
//  }

const CommandStatus = {
    "pending" : 1,
    "in_processing" : 2,
    "completed" : 3,
    "error" : 4
}

// router.post('/commands/', commandController.postCommand);
module.exports.postCommand = function(req,res) {

    // fill out possibly empty data
    var target = req.body.target;
    if(!target || target === ''){
        target = "display_SR"
    }

    var command_data = {
        type : req.body.type,
        category : req.body.category,
        target : target,
        issuer : req.body.issuer,
        status : CommandStatus.pending,
        timestamp : Date.now(),
        completed_on : '',
        params : req.body.params
    }

    DB.create(command_data , function(err, command) {
        if (err) {
            jsonUtils.sendJsonResponse(res, 400, err);
        } else {
            publishCommand(command);
            jsonUtils.sendJsonResponse(res, 201, command);
        }
    }); 

}

const publishCommand = function (commandData) {

    // var params = JSON.parse(commandData.params);
    var priority;

    switch(commandData.type) {
        case 'visualisation' : 
            priority = 3;
            break;

        case 'monitoring' : 
            priority = 3;
            break;

        case 'annotation' : 
            priority = 2;
            break;

        case 'action' : 
            priority = 1;
            break;

        case 'undefined' : 
            priority = 1;
            break;
    }

    // command_data.category = "room"

    var ASTRACommand = {
        command_id : commandData._id,
        type : commandData.type,
        status : commandData.status,
        target : commandData.target,
        priority : priority,
        params : commandData.params
    }

    var router_key = commandData.category + "." + commandData.type

    sender.publishActivity(ASTRACommand, router_key);

}

// router.get('/commands/:command_id', commandController.getCommand);
module.exports.getCommand = function(req,res) {

    if (req.params && req.params.command_id) {

        var command_id = req.params.command_id;

        DB
        .findById(command_id)
        .exec(function(err, data) {
            if (!data){
                jsonUtils.sendJsonResponse(res, 404, "Command not found");
                return;
            } else if (err){
                jsonUtils.sendJsonResponse(res, 404, err);
                return;
            } else {
                jsonUtils.sendJsonResponse(res, 200, data);
            }
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "No Command id in request");
    }  
}


// router.get('/commands/pending', commandController.getPendingCommand);
module.exports.getPendingCommand = function(req,res) {

    DB
    .find({"status" : CommandStatus.pending})
    .exec(function(err, data) {
        if (err){
            jsonUtils.sendJsonResponse(res, 404, err);
            return;
        } else {
            jsonUtils.sendJsonResponse(res, 200, data);
        }
    }); 

}

// router.get('/commands/in_processing', commandController.getProcessingCommand);
module.exports.getProcessingCommand = function(req,res) {

    DB
    .find({"status" : CommandStatus.in_processing})
    .exec(function(err, data) {
        if (err){
            jsonUtils.sendJsonResponse(res, 404, err);
            return;
        } else {
            jsonUtils.sendJsonResponse(res, 200, data);
        }
    }); 


}

// router.get('/commands/completed', commandController.getCompletedCommand);
module.exports.getCompletedCommand = function(req,res) {

    DB
    .find({"status" : CommandStatus.completed})
    .exec(function(err, data) {
        if (err){
            jsonUtils.sendJsonResponse(res, 404, err);
            return;
        } else {
            jsonUtils.sendJsonResponse(res, 200, data);
        }
    }); 


}

// router.get('/commands/failed', commandController.getFailedCommand);
module.exports.getFailedCommand = function(req,res) {

    DB
    .find({"status" : CommandStatus.error})
    .exec(function(err, data) {
        if (err){
            jsonUtils.sendJsonResponse(res, 404, err);
            return;
        } else {
            jsonUtils.sendJsonResponse(res, 200, data);
        }
    }); 


}

// router.put('/commands/:command_id', commandController.updateCommand);
module.exports.updateCommand = function(req,res) {

    if (req.params && req.params.command_id) {

        var command_id = req.params.command_id;

        var completion_timestamp = ''

        if (req.body.status === CommandStatus.completed){
            completion_timestamp = Date.now();
        }

        var command_data = {
            type : req.body.type,
            target : req.body.target,
            category : req.body.category,
            issuer : req.body.issuer,
            status : req.body.status,
            timestamp : req.body.timestamp,
            completed_on : completion_timestamp,
            params : req.body.params
        }

        DB
        .updateOne({"_id" : command_id}, command_data)
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, err);
                return;
            } else {
                jsonUtils.sendJsonResponse(res, 200, data);
            }
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "No Command id in request");
    }
}

// router.put('/commands/:command_id/status', commandController.updateStatus);
module.exports.updateStatus = function(req,res) {

    if (req.params && req.params.command_id) {

        var command_id = req.params.command_id;

        DB
        .findById(command_id)
        .exec(function(err, data) {
            if (!data){
                jsonUtils.sendJsonResponse(res, 404, "Command not found");
                return;
            } else if (err){
                jsonUtils.sendJsonResponse(res, 404, err);
                return;
            } else {

                var completion_timestamp = '';

                if (req.body.status === CommandStatus.completed || req.body.status === CommandStatus.error){
                    completion_timestamp = Date.now();
                }

                data.status = req.body.status;
                data.completed_on = completion_timestamp;

                DB
                .updateOne({"_id" : command_id}, data)
                .exec(function(err, data) {
                    if (err){
                        jsonUtils.sendJsonResponse(res, 404, err);
                        return;
                    } else {
                        jsonUtils.sendJsonResponse(res, 200, data);
                    }
                });
            }
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "No Command id in request");
    }  
}

// router.delete('/commands/:command_id', commandController.deleteCommand);
module.exports.deleteCommand = function(req,res) {

    if (req.params && req.params.command_id) {

        var command_id = req.params.command_id;

        DB
        .deleteOne({"_id" : command_id})
        .exec(function(err, data) {
            if (err){
                jsonUtils.sendJsonResponse(res, 404, err);
                return;
            } else {
                jsonUtils.sendJsonResponse(res, 200, data);
            }
        });
    } else {
        jsonUtils.sendJsonResponse(res, 404, "No Command id in request");
    }
}