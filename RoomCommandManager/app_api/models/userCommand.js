var mongoose = require( 'mongoose' );

// Command Sample Format {
//     _id : String,
//     type : String,
//     target : String,
//     issuer : String,
//     status : String,
//     timestamp : Date,
//     completed_on : Date
//     params : {
//         par1 : String,
//         par2 : String,
//         ...
//     }
//  }

var commandSchema = new mongoose.Schema({ 
    type: {type: String, required: true},
    category: {type: String, required: true},
    target: {type: String, required: true},
    issuer: {type: String, required: true},
    status: {type: String, required: true},
    timestamp: {type: String, "default": Date.now(), required: true},
    accepted_on : {type: String},
    completed_on : {type: String}, 
    params : {type: Object}
});

mongoose.model('UserCommand', commandSchema);