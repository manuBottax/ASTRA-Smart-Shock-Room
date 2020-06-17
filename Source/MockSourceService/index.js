const express = require('express');

var router = express.Router();
var jsonUtils = require("./jsonUtilities");

app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Mock Data Source Service</h1> <p> You should not be here. </p>');
});

router.get('/api/mock_data',  (req, res) => {

  var mockData = 1.0 + ( Math.random() * 250 )

  jsonUtils.sendJsonResponse(res, 200, {value : "" + mockData});

});

router.get('/api/mock_data/image',  (req, res) => {

  var p = "https://drive.google.com/uc?id=1CdblsZT_bOAV7SAwC7-y8XVup0CKBrAg"

  jsonUtils.sendJsonResponse(res, 200, {path : p});

});

app.use('/', router);

const http = require('http').createServer(app);
// const io = require('socket.io')(http);

// io.on('connection', (socket) => {
//     console.log('data connected');
    
//     socket.on('disconnect', () => {
//       console.log('data disconnected');
//     });

//     socket.on('mock_source_message', (msg) => {
//         console.log('message: ' + msg);
//     });
// });


http.listen(3007, () => {
  console.log(' --------------------------------------------');
  console.log(' -----  ASTRA SHOCK ROOM MOCK SERVICE   -----');
  console.log(' -----     listening on *:3007          -----');
  console.log(' --------------------------------------------');
  console.log();
});

