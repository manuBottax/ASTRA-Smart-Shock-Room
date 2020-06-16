const express = require('express');

var router = express.Router();
var jsonUtils = require("./jsonUtilities");

app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Trauma Data Service</h1> <p> You should not be here. </p>');
});

router.get('/api/data/blood_pressure',  (req, res) => {


  var minPressure = parseInt(10 + ( Math.random() * 100 ))

  var maxPressure = parseInt( (70 + ( Math.random() * 150 )) )

  var data = {
    value :  minPressure + " | " + maxPressure 
  }

  jsonUtils.sendJsonResponse(res, 200, data);

});

router.get('/api/data/spO2',  (req, res) => {

  var saturation = parseInt( (80 + ( Math.random() * 20 )) )

  var data = {
    value : "" +  saturation
  }

  jsonUtils.sendJsonResponse(res, 200, data);
})

router.get('/api/data/heart_rate',  (req, res) => {

  var rate = parseInt( ( Math.random() * 250 ))

  var data = {
    value : "" + rate
  }

  jsonUtils.sendJsonResponse(res, 200, data);
})

router.get('/api/data/temperature',  (req, res) => {

  var temp = 35.0 +  ( Math.random() * 9.5)

  var data = {
    value :  temp.toFixed(1)
  }

  jsonUtils.sendJsonResponse(res, 200, data);

})

app.use('/', router);

const http = require('http').createServer(app);
// const io = require('socket.io')(http);

// io.on('connection', (socket) => {
//     console.log('Display connected');
    
//     socket.on('disconnect', () => {
//       console.log('Display disconnected');
//     });

//     socket.on('display_message', (msg) => {
//         console.log('message: ' + msg);
//     });
// });


http.listen(3005, () => {
  console.log(' --------------------------------------------');
  console.log(' ----- ASTRA SHOCK ROOM TRAUMA SERVICE  -----');
  console.log(' -----     listening on *:3005          -----');
  console.log(' --------------------------------------------');
  console.log();
});

