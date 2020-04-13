var jsonUtils = require('../jsonUtilities');

let clients = [];
let patientData = [];


// GET /events 
module.exports.eventsHandler = function (req, res) {

    // Mandatory headers and http status to keep connection open
    const headers = {
      'Content-Type': 'text/event-stream',
      'Connection': 'keep-alive',
      'Cache-Control': 'no-cache'
    };

    res.writeHead(200, headers);
  
    // After client opens connection send all patientData as string
    const data = `data: ${JSON.stringify(patientData)}\n\n`;

    res.write(data);
  
    // Generate an id based on timestamp and save res
    // object of client connection on clients list
    // Later we'll iterate it and send updates to each client
    const clientId = Date.now();

    const newClient = {
      id: clientId,
      res
    };

    clients.push(newClient);
  
    // When client closes connection we update the clients list
    // avoiding the disconnected one
    req.on('close', () => {
      console.log(`${clientId} Connection closed`);
      // TODO: Aggiornare qui la lista in qualche modo 
      clients = clients.filter(c => c.id !== clientId);
    });
  }
  
  // Iterate clients list and use write res object method to send new data
  function sendEventsToAllClient(patientData) {
    clients.forEach(c => c.res.write(`data: ${JSON.stringify(patientData)}\n\n`))
  }
  
  // POST /data 
  module.exports.addPatientData = async function(req, res) {

    const patData = req.body;
    console.log("New patient data received ! ");
    console.log(patData);
    patientData.push(patData);

    console.log("Now have " + patientData.length + " Patient Data request")
  
    // Send recently added data as POST result
    //res.json(patData);
    jsonUtils.sendJsonResponse(res,201,patData);
  
    // Invoke iterate and send function
    return sendEventsToAllClient(patData);
  }

  module.exports.getStatus = function(req, res) {
    res.json({clients: clients.length});
  }