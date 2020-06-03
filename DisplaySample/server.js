const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

var router = require('./routes/index');

const app = express();



// Set cors and bodyParser middlewares
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.use('/', router);

const PORT = 3001;

// Start server on 3001 port
app.listen(PORT, () => console.log(`Swamp Events service listening on port ${PORT}`));