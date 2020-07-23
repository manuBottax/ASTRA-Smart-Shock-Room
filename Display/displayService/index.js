const express = require('express');

var router = express.Router();

var jsonUtils = require("./jsonUtilities");

app = express();
app.use(express.json());
app.use(express.urlencoded( { extended: true } ));

var displayStatus = "idle";

// --- Handler for the different type of data that the display can visualise ---
var displayTraumaTeamData = function (req, res) {

  if (req.params){
    
      io.emit("display_data", {position : req.params.position, name : "", type: "trauma_team", value : req.body.data});
      jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayPreHData = function (req, res) {

  if (req.params){

    io.emit("display_data", {position : req.params.position, name : req.body.name, type: "preh", value : req.body.data});
    jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayTraumaInfoData = function (req, res) {

  if (req.params){

    io.emit("display_data", {position : req.params.position, name : req.body.name, type: "trauma_info", value : req.body.data});
    jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayInitialConditionInfoData = function (req, res) {

  if (req.params){

    io.emit("display_data", {position : req.params.position, name : req.body.name, type: "patient_initial_condition", value : req.body.data});
    jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayEventListData = function (req, res) {

  if (req.params){

    io.emit("display_data", {position : req.params.position, name : req.body.name, type: "event_list", value : req.body.data});
    jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayTextData = function (req, res) {

  if (req.params){

      io.emit("display_data", {position : req.params.position, name : req.body.name, type: "text", value : req.body.data});
      jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayImageData =  function (req, res) {

  if (req.params){

    io.emit("display_data", {position : req.params.position, type: "image", value : req.body.data});
    jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayTACData =  function (req, res) {

  if (req.params){

    console.log("TAC : ");
    console.log(req.body.data);

    io.emit("display_data", {position : req.params.position, type: "tac", value : req.body.data});
    jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

var displayErrorData = function (req, res) {

  if (req.params){

    var type = 'visualizzare'

    if (req.body.data.type == 'monitoring'){
      type = 'monitorare'
    } else if (req.body.data.type == 'annotation'){
      type = 'annotare'
    } else if (req.body.data.type == 'action'){
      type = 'completare'
    }

    var errorMessage = "Impossibile " + type + " " + req.body.data.params.value + " (Comando # " + req.body.data.command_id + ")"; 

      io.emit("display_data", {position : req.params.position, name : req.body.name, type: "text", value : errorMessage});
      jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}


var clearData = function (req, res) {

  if (req.params){

      io.emit("display_data", {position : req.params.position, name : "", type: "text", value : ""});
      jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

// --- REST Service Routing paths ---

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Display Service</h1> <p> You should not be here. </p>');
});

router.post('/api/display/:position/tl_data', displayTraumaTeamData)

router.post('/api/display/:position/preH', displayPreHData)

router.post('/api/display/:position/trauma_info', displayTraumaInfoData)

router.post('/api/display/:position/patient_initial_condition', displayInitialConditionInfoData)

router.post('/api/display/:position/events', displayEventListData)

router.post('/api/display/:position/patient_data', displayTextData)

router.post('/api/display/:position/biometric_data', displayTextData)

router.post('/api/display/:position/diagnostic_data', displayTextData)

router.post('/api/display/:position/tac', displayTACData)

router.post('/api/display/:position/rx', displayImageData)

router.post('/api/display/:position/temporal_data', displayTextData)

router.post('/api/display/:position/env_data', displayTextData)

router.post('/api/display/:position/error', displayErrorData)

router.post('/api/display/:position/clear', clearData)

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Display Service</h1> <p> You should not be here. </p>');
});

router.get('/api/display/status', (req, res) => {
  jsonUtils.sendJsonResponse(res,"200", {"status" : displayStatus});
});

router.put('/api/display/status', (req, res) => {
  displayStatus = req.body.status;
  io.emit("update_status", {"status" : displayStatus});
  jsonUtils.sendJsonResponse(res,"200", {"status" : displayStatus});
});

app.use('/', router);

const http = require('http').createServer(app);
const io = require('socket.io')(http);

io.on('connection', (socket) => {
    console.log('Display connected');
    
    socket.on('disconnect', () => {
      console.log('Display disconnected');
    });

    socket.on('update_status', stat => {
      console.log('update status: ' + stat);
      displayStatus = stat;
    });

    socket.on('display_message', (msg) => {
        console.log('message: ' + msg);
    });
});


http.listen(3001, () => {
    console.log(' --------------------------------------------');
    console.log(' ----- ASTRA SHOCK ROOM DISPLAY SERVICE -----');
    console.log(' -----        listening on *:3001       -----');
    console.log(' --------------------------------------------');
    console.log();
});

