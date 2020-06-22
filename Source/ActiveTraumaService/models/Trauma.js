var mongoose = require( 'mongoose' );

    // Trauma {
    //     _id : String,
    //     patient_status : String,
    //     trauma_team : [{}],
    //     preH : [{}],
    //     trauma_info : [{}],
    //     MTC : [{}],
    //     anamnesis : [{}],
    //     vital_sign : [{}],
    //     report : [{}],
    //     events : [{}]
    // }

var traumaSchema = new mongoose.Schema({ 
    patient_status: {type: String, required: true, "default" : "unavailable"},
    trauma_team: {type: Array, "default" : []},
    preH: {type: Array, "default" : [] },
    trauma_info: {type: Array, "default" : [] },
    MTC: {type: Array, "default" : [] },
    anamnesis: {type: Array, "default" : [] },
    vital_sign: {type: Array, "default" : [] },
    report: {type: Array, "default" : [] },
    events: {type: Array, "default" : [] },
});

mongoose.model('Trauma', traumaSchema);