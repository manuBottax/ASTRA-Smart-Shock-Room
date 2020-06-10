const express = require('express');

var router = express.Router();
var jsonUtils = require("./jsonUtilities");

router.get('/', (req, res) => {
  res.send('<h1>ASTRA Shock Room Display Service</h1> <p> You should not be here. </p>');
});

router.post('/api/display/:position/:data',  (req, res) => {

  if (req.params){
      var position = req.params.position;
      var data = req.params.data;

      io.emit("display_data", {position : position, value : data});
      jsonUtils.sendJsonResponse(res, 201, "OK");
  } else {
    jsonUtils.sendJsonResponse(res, 400, "Invalid Params");
  }

});

app = express();

app.use('/', router);

const http = require('http').createServer(app);
const io = require('socket.io')(http);

io.on('connection', (socket) => {
    console.log('Display connected');
    
    socket.on('disconnect', () => {
      console.log('Display disconnected');
    });

    socket.on('display_message', (msg) => {
        console.log('message: ' + msg);
    });
});


http.listen(3001, () => {
    console.log('listening on *:3001');
});

