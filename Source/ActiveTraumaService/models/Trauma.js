var mongoose = require( 'mongoose' );

    // Trauma {
    //     _id : String,
    //     current_trauma_status : String,
    //     startOperatorId : String,
    //     startOperatorDescription : String,
    //     delayedActivation : {},
    //     traumaTeamMembers : String[],
    //     preH : {},
    //     traumaInfo : {},
    //     patientInitialCondition: {}
    //     events : [{}]
    // }

var traumaSchema = new mongoose.Schema({ 
    current_trauma_status: {type: String, required: true, "default" : "unavailable"},
    startOperatorId : {type: String, required: true},
    startOperatorDescription :  {type: String, required: true},
    delayedActivation : { type: Object} ,
    traumaTeamMembers: {type: Array, "default" : []},
    preh: {type: Object },
    traumaInfo: {type: Object },
    patientInitialCondition: {type: Object},
    events: {type: Array, "default" : [] },
});

mongoose.model('Trauma', traumaSchema);