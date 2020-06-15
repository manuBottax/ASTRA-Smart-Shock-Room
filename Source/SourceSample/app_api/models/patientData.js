var mongoose = require( 'mongoose' );

var dataSchema = new mongoose.Schema({ 
    patient_id: {type: String, required: true},
    data_type: {type: String, required: true},
    value: {type: Number, "default": 0, required: true}
});

mongoose.model('PatientData', dataSchema);