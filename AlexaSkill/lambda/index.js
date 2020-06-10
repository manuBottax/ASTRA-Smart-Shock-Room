
const Alexa = require('ask-sdk-core');

//TODO: Usare le richieste asincrone in produzione
// const requestHandler = require('then-request');
const requestHandler = require('sync-request');

const LaunchRequestHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'LaunchRequest';
    },
    
    handle(handlerInput) {
        const speakOutput = 'Ciao Bottax ! Cosa posso fare per te ?';
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};
const HelloWorldIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'HelloWorldIntent';
    },
    handle(handlerInput) {
        const speakOutput = 'Certo ! Funziono benissimo e so rispondere ad ogni tua richiesta';


        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt('Vuoi sapere altro ?')
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

const SlotAnswerTestIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'SlotAnswerTestIntent';
    },
    
    handle(handlerInput) {
       const slots = handlerInput
            .requestEnvelope
            .request
            .intent
            .slots;
           
        const name = slots.name.value;
        
        const speakOutput = 'Ciao ' + name;
        
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt("Chi devo salutare adesso ? ")
            .getResponse();
    }
}

const PatientParameterRequestIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'PatientParameterRequestIntent';
    },
    
    // async handle(handlerInput) {
    handle(handlerInput) {
        
       const slots = handlerInput
            .requestEnvelope
            .request
            .intent
            .slots;
           
        const data_type = slots.data_type.value;
        
        var speechOutput = 'Richiedo ' + data_type + ' del paziente'
        
        const path = 'http://151.61.210.34:3005/parameterRequest'
        
        /////// SYNC [OK] ////////////////////////////////////////////////////////////////////////////////////
        
        const res = requestHandler('POST', path, 
            {json:{
                data_type : data_type,
                patient_id : '123456'
            }}).getBody('utf-8');
                
        var status = JSON.parse(res)
        
        console.log(status);
        
        speechOutput = status;

        return handlerInput.responseBuilder
            .speak(speechOutput)
            .reprompt("Vuoi sapere altro ?")
            .getResponse();
        
        /////// SYNC ////////////////////////////////////////////////////////////////////////////////////
    }
    
    
    /////// ASYNC [NON FUNZIONA] ////////////////////////////////////////////////////////////////////////////////////
        
    // var result = await requestHandler('GET', path)
    //             .getBody('utf-8')
    //             .then(JSON.parse)
    //             .done(function (res) {
    //                 console.log(res);
    //                 speechOutput = res.box_id;
    //                 return handlerInput.responseBuilder
    //                     .speak(speechOutput)
    //                     .reprompt("Vuoi sapere altro ?")
    //                     .getResponse();
    //             });
                
    // return result;
        
    ///////////////////////////////////////////////////////////////////////////////////////////
        
    //     try {
            
    //      requestHandler('POST', path, {json:{
    //             data_type : data_type,
    //             patient_id : '123456'
    //             }}) .getBody('utf-8')
    //                 .then(JSON.parse)
    //                 .done(function (res) {
    //                     console.log(res);
    //                     return handlerInput.responseBuilder
    //                         .speak(speechOutput)
    //                         .reprompt("Vuoi sapere altro ?")
    //                         .getResponse();
    //                 });
    //         // const response = await postHttp('parameterRequest',postData) 
    //         // speechOutput = 'Richiedo ' + data_type + ' del paziente';

            
    //     } catch (error){
    //         console.log(error)
    //         return handlerInput.responseBuilder
    //             .speak(' Errore, non ho trovato niente')
    //             .reprompt("Vuoi sapere altro ?")
    //             .getResponse();
    //     }
        
    //     // return handlerInput.responseBuilder
    //     //     .getResponse();
        
    // }
    
}



const HelpIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.HelpIntent';
    },
    handle(handlerInput) {
        const speakOutput = 'You can say hello to me! How can I help?';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

const CancelAndStopIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && (Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.CancelIntent'
                || Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.StopIntent');
    },
    handle(handlerInput) {
        const speakOutput = 'Ho finito, chiudo la skill !';
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const SessionEndedRequestHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'SessionEndedRequest';
    },
    handle(handlerInput) {
        // Any cleanup logic goes here.
        return handlerInput.responseBuilder.getResponse();
    }
};

// The intent reflector is used for interaction model testing and debugging.
// It will simply repeat the intent the user said. You can create custom handlers
// for your intents by defining them above, then also adding them to the request
// handler chain below.
const IntentReflectorHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest';
    },
    handle(handlerInput) {
        const intentName = Alexa.getIntentName(handlerInput.requestEnvelope);
        const speakOutput = `You just triggered ${intentName}`;

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

// Generic error handling to capture any syntax or routing errors. If you receive an error
// stating the request handler chain is not found, you have not implemented a handler for
// the intent being invoked or included it in the skill builder below.
const ErrorHandler = {
    canHandle() {
        return true;
    },
    handle(handlerInput, error) {
        console.log(`~~~~ Error handled: ${error.stack}`);
        const speakOutput = `Sorry, I had trouble doing what you asked. Please try again.`;

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

// The SkillBuilder acts as the entry point for your skill, routing all request and response
// payloads to the handlers above. Make sure any new handlers or interceptors you've
// defined are included below. The order matters - they're processed top to bottom.
exports.handler = Alexa.SkillBuilders.custom()
    .addRequestHandlers(
        LaunchRequestHandler,
        HelloWorldIntentHandler,
        SlotAnswerTestIntentHandler,
        PatientParameterRequestIntentHandler,
        HelpIntentHandler,
        CancelAndStopIntentHandler,
        SessionEndedRequestHandler,
        IntentReflectorHandler, // make sure IntentReflectorHandler is last so it doesn't override your custom intent handlers
    )
    .addErrorHandlers(
        ErrorHandler,
    )
    .lambda();
