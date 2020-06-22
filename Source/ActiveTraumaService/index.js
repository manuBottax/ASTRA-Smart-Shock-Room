const express = require('express');

var router = express.Router();
var jsonUtils = require("./jsonUtilities");

require('./models/db')

var patientStatus = "unavailable";

app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

var traumaController = require("./traumaControllers");

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Trauma Data Service</h1> <p> You should not be here. </p>');
});

router.get('/api/vital_parameter/blood_pressure',  (req, res) => {


  var minPressure = parseInt(10 + ( Math.random() * 100 ))

  var maxPressure = parseInt( (70 + ( Math.random() * 150 )) )

  var data = {
    value :  maxPressure + " | " +  minPressure
  }

  jsonUtils.sendJsonResponse(res, 200, data);

});

router.get('/api/vital_parameter/spO2',  (req, res) => {

  var saturation = parseInt( (80 + ( Math.random() * 20 )) )

  var data = {
    value : "" +  saturation
  }

  jsonUtils.sendJsonResponse(res, 200, data);
})

router.get('/api/vital_parameter/heart_rate',  (req, res) => {

  var rate = parseInt( ( Math.random() * 250 ))

  var data = {
    value : "" + rate
  }

  jsonUtils.sendJsonResponse(res, 200, data);
})

router.get('/api/vital_parameter/temperature',  (req, res) => {

  var temp = 35.0 +  ( Math.random() * 9.5)

  var data = {
    value :  temp.toFixed(1)
  }

  jsonUtils.sendJsonResponse(res, 200, data);

})

//// --- Active Trauma API --- 

router.post('/api/trauma', traumaController.postTrauma);
router.get ('/api/trauma/:trauma_id', traumaController.getTrauma);
router.delete('/api/trauma/:trauma_id', traumaController.deleteTrauma);

router.get('/api/trauma/:trauma_id/patient_status',traumaController.getPatientStatus)
router.put('/api/trauma/:trauma_id/patient_status',traumaController.updatePatientStatus)

// ActiveTrauma

router.post('/api/trauma/:trauma_id/trauma_team/element', traumaController.addTraumaTeamElement);
router.get ('/api/trauma/:trauma_id/trauma_team', traumaController.getTraumaTeam);
router.put ('/api/trauma/:trauma_id/trauma_team', traumaController.updateTraumaTeam);

router.post('/api/trauma/:trauma_id/preH/element', traumaController.addPreHInfoElement);
router.get ('/api/trauma/:trauma_id/preH', traumaController.getPreHInfo);
router.put ('/api/trauma/:trauma_id/preH', traumaController.updatePreHInfo);

// TODO: 

// router.post('/api/trauma/:trauma_id/trauma_info', traumaController.addTraumaInfo);
// router.get ('/api/trauma/:trauma_id/trauma_info', traumaController.getTraumaInfo);
// router.put ('/api/trauma/:trauma_id/trauma_info', traumaController.updateTraumaInfo);

// router.post('/api/trauma/:trauma_id/MTC', traumaController.addMajorTraumaCriteria);
// router.get ('/api/trauma/:trauma_id/MTC', traumaController.getMajorTraumaCriteria);
// router.put ('/api/trauma/:trauma_id/MTC', traumaController.updateMajorTraumaCriteria);

// router.post('/api/trauma/:trauma_id/anamnesis', traumaController.addAnamnesis);
// router.get ('/api/trauma/:trauma_id/anamnesis', traumaController.getAnamnesis);
// router.put ('/api/trauma/:trauma_id/anamnesis', traumaController.updateAnamnesis);

// router.post('/api/trauma/:trauma_id/vital_sign', traumaController.addVitalSign);
// router.get ('/api/trauma/:trauma_id/vital_sign', traumaController.getVitalSign);
// router.put ('/api/trauma/:trauma_id/vital_sign', traumaController.updateVitalSign);

// // TraumaTracker 

// router.post('/api/trauma/:trauma_id/report', traumaController.addReport);
// router.get ('/api/trauma/:trauma_id/report', traumaController.getReport);
// router.put ('/api/trauma/:trauma_id/report', traumaController.updateReport);

// router.post('/api/trauma/:trauma_id/events', traumaController.addEvent);
// router.get ('/api/trauma/:trauma_id/events', traumaController.getEvent);
// router.put ('/api/trauma/:trauma_id/events', traumaController.updateEvent);

app.use('/', router);

const http = require('http').createServer(app);

//The vital parameter monitor isn't actually connected.
const io = require('socket.io')(http);

io.on('connection', (socket) => {
    console.log('Vital Parameter monitor connected');
    
    socket.on('disconnect', () => {
      console.log('Vital Parameter monitor disconnected');
    });

    socket.on('vp_monitor_message', (msg) => {
        console.log('[ Vital Paramenter Monitor ] message: ' + msg);
    });

    socket.on('vp_update_blooed_pressure', (msg) => {
      console.log('[ Vital Paramenter Monitor ] new blood pressure: ' + msg);
      // update value 
    });

    //...
});


http.listen(3005, () => {
  console.log(' --------------------------------------------');
  console.log(' ----- ASTRA SHOCK ROOM TRAUMA SERVICE  -----');
  console.log(' -----     listening on *:3005          -----');
  console.log(' --------------------------------------------');
  console.log();
});

