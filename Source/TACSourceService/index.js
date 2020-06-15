const express = require('express');

var cors = require('cors')

var router = express.Router();
var jsonUtils = require("./jsonUtilities");

require('./models/db');

var status = "unavailable";


app = express();

app.use(cors());

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

var tacController = require("./tacController");

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room TAC Sample Service</h1> <p> You should not be here. </p>');
});

router.get('/api/status/',  (req, res) => {
  jsonUtils.sendJsonResponse(res, 200, {"status" : status});
});

router.post('/api/tac_data/', tacController.postTACData);

router.get ('/api/tac_data/:patient_id', tacController.getTACByPatient);

app.use('/', router);

const http = require('http').createServer(app);
const io = require('socket.io')(http);

io.on('connection', (socket) => {
    console.log('TAC connected');
    status = "available";
    
    socket.on('disconnect', () => {
      console.log('TAC disconnected');
      status = "unavailable";
    });

    socket.on('update_status', stat => {
      console.log('update status: ' + stat);
      status = stat;
    });

    socket.on('tac_message', (msg) => {
        console.log('message: ' + msg);
    });
});


http.listen(3003, () => {
  console.log(' --------------------------------------------');
  console.log(' -----  ASTRA SHOCK ROOM TAC SERVICE    -----');
  console.log(' -----     listening on *:3003          -----');
  console.log(' --------------------------------------------');
  console.log();
});

