var mongoose = require( 'mongoose' );

// TACData {
//     id : String,
//     patient_id : String,
//     path : String,
//     timestamp : Date
// }

var tacSchema = new mongoose.Schema({ 
    patient_id: {type: String, required: true},
    path: {type: String, required: true},
    name : {type: String, required: true},
    description : {type: String, required: true},
    timestamp: {type: String, "default": Date.now(), required: true}
});

mongoose.model('TACData', tacSchema);