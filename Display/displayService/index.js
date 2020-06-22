const express = require('express');

var router = express.Router();

var jsonUtils = require("./jsonUtilities");

app = express();
app.use(express.json());
app.use(express.urlencoded( { extended: true } ));

var displayStatus = "idle";

var displayTextData = function (req, res) {

  if (req.params){

    //console.log("req.body.name" + req.body.name);

      io.emit("display_data", {position : req.params.position, name : req.body.name, type: "text", value : req.body.data});
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

var displayImageData =  function (req, res) {

  if (req.params){

    io.emit("display_data", {position : req.params.position, type: "image", value : req.body.data});
    jsonUtils.sendJsonResponse(res, 201, "OK");

  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }
}

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Display Service</h1> <p> You should not be here. </p>');
});

router.post('/api/display/:position/patient_data', displayTextData)

router.post('/api/display/:position/tl_data', displayTextData)

router.post('/api/display/:position/biometric_data', displayTextData)

router.post('/api/display/:position/diagnostic_data', displayTextData)

router.post('/api/display/:position/tac', displayImageData)
router.post('/api/display/:position/rx', displayImageData)

router.post('/api/display/:position/temporal_data', displayTextData)

router.post('/api/display/:position/env_data', displayTextData)

router.post('/api/display/:position/error', displayTextData)

router.post('/api/display/:position/clear', clearData)

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Display Service</h1> <p> You should not be here. </p>');
});

router.get('/api/display/status', (req, res) => {
  // console.log("Requested Status !")
  // console.log(displayStatus)
  jsonUtils.sendJsonResponse(res,"200", {"status" : displayStatus});
});

router.put('/api/display/status', (req, res) => {
  displayStatus = req.body.status;
  io.emit("update_status", {"status" : displayStatus});
  jsonUtils.sendJsonResponse(res,"200", {"status" : displayStatus});
});

// router.post('/api/display/:position',  (req, res) => {

//   if (req.params){

//       var position = req.params.position;

//       var data_type = (req.body.type  === "tac" || req.body.type  === "chest_rx") ? "image" : "text"

//       io.emit("display_data", {position : position, name : req.body.type, type: data_type, value : req.body.data});
//       jsonUtils.sendJsonResponse(res, 201, "OK");
//   } else {
//     jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
//   }

// });

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

