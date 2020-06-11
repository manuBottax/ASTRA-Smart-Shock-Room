
const Alexa = require('ask-sdk-core');

const requestHandler = require('then-request');
// const requestHandler = require('sync-request');

const COMMAND_SERVICE_PATH = 'http://151.61.151.43:3010/api/commands'

const LaunchRequestHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'LaunchRequest';
    },
    
    handle(handlerInput) {
        const speakOutput = 'Benvenuto in ASTRA Room Controller. Attendo istruzioni';
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

const VisualisationRequestIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'VisualisationRequestIntent';
    },
    
    async handle(handlerInput) {
        
       const slots = handlerInput
            .requestEnvelope
            .request
            .intent
            .slots;
           
        const data_type = slots.data_type.value;
        var position = slots.position.value;
        
        var id = slots.data_type.resolutions.resolutionsPerAuthority[0].values[0].value.id;
        
        //If position is not defined by the user use a default value;
        if (! isFinite(position)){
            position = 4;
        }
        
        //TODO: switch sul tipo per gestione category
        var data = {json: {
            type : "visualisation",
            category : "room",
            target : "display_sr",
            issuer : "alexa_ASTRA_controller",
            params : {
                value : id,
                position : position
            }
        }}
        
        var speechOutput = 'Richiedo la visualizzazione di ' + data_type + ' del paziente'
        
        await requestHandler('POST', COMMAND_SERVICE_PATH, data)
            .getBody('utf-8')
            .then(JSON.parse)
            .done(function (res) {
                console.log(res);
            });
            
        return handlerInput.responseBuilder
                .speak(speechOutput)
                .reprompt("Rimango in attesa di altri comandi")
                .getResponse();

    }
}

const DrugInfoVisualisationRequestIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'DrugInfoVisualisationRequestIntent';
    },
    
    
    async handle(handlerInput) {
        
       const slots = handlerInput
            .requestEnvelope
            .request
            .intent
            .slots;
           
        const drug_name = slots.drug_name.value;
        
        //TODO: va gestita position per questo tipo di dato visto che non posso chiederla insieme al nome del farmaco.
        //If position is not defined by the user use a default value;
        const position = 5;
        
        /////// SYNC [OK] ////////////////////////////////////////////////////////////////////////////////////
        
        //TODO: switch sul tipo per gestione category
        var data = {json: {
            type : "visualisation",
            category : "room",
            target : "display_sr",
            issuer : "alexa_ASTRA_controller",
            params : {
                value : "drug_quantity",
                drug_name : drug_name,
                position : position
            }
        }}
        
        var speechOutput = 'Richiedo la visualizzazione della quantità di farmaco  ' + drug_name + ' somministrata al paziente' 
        
        await requestHandler('POST', COMMAND_SERVICE_PATH, data)
            .getBody('utf-8')
            .then(JSON.parse)
            .done(function (res) {
                console.log(res);
            });
            
        return handlerInput.responseBuilder
                .speak(speechOutput)
                .reprompt("Rimango in attesa di altri comandi")
                .getResponse();
        
    }
}

const MonitorRequestIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'MonitorRequestIntent';
    },
    
    async handle(handlerInput) {
        
       const slots = handlerInput
            .requestEnvelope
            .request
            .intent
            .slots;
           
        const data_type = slots.data_type.id;
        var position = slots.position.value;
        
        //If position is not defined by the user use a default value;
        if (! isFinite(position)){
            position = 4;
        }
        
         
        var id = slots.data_type.resolutions.resolutionsPerAuthority[0].values[0].value.id;
        console.log(id);
        
        
        //TODO: switch sul tipo per gestione category
        var data = {json: {
            type : "monitoring",
            category : "room",
            target : "display_sr",
            issuer : "alexa_ASTRA_controller",
            params : {
                value : id,
                position : position
            }
        }}
        
        var speechOutput = 'Richiedo il monitoraggio di ' + data_type + ' del paziente'
        
        await requestHandler('POST', COMMAND_SERVICE_PATH, data)
            .getBody('utf-8')
            .then(JSON.parse)
            .done(function (res) {
                console.log(res);
            });
            
        return handlerInput.responseBuilder
                .speak(speechOutput)
                .reprompt("Rimango in attesa di altri comandi")
                .getResponse();
        
    }
}

const OperationAnnotationRequestIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'OperationAnnotationRequestIntent';
    },
    
    async handle(handlerInput) {
        
       const slots = handlerInput
            .requestEnvelope
            .request
            .intent
            .slots;
           
        const type = slots.type.value;
        const operation = slots.operation.value;
        
        var data = {json: {
            type : "annotation",
            category : "trauma",
            target : "display_sr",
            issuer : "alexa_ASTRA_controller",
            params : {
                type : "operation",
                status : type,
                operation : operation
            }
        }}
        
        var speechOutput = "Richiedo l'annotazione di " + type + " " + operation + " del paziente"

        await requestHandler('POST', COMMAND_SERVICE_PATH, data)
            .getBody('utf-8')
            .then(JSON.parse)
            .done(function (res) {
                console.log(res);
            });
            
        return handlerInput.responseBuilder
                .speak(speechOutput)
                .reprompt("Rimango in attesa di altri comandi")
                .getResponse();
    }
}

const DrugAnnotationRequestIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'DrugAnnotationRequestIntent';
    },
    
    async handle(handlerInput) {
        
       const slots = handlerInput
            .requestEnvelope
            .request
            .intent
            .slots;
           
        const drug_name = slots.drug_name.value;
        const drug_quantity = slots.drug_quantity.value;
        
        var data = {json: {
            type : "annotation",
            category : "trauma",
            target : "display_sr",
            issuer : "alexa_ASTRA_controller",
            params : {
                type : "drug_administration",
                drug_name : drug_name,
                drug_quantity : drug_quantity
            }
        }}
        
        var speechOutput = "Richiedo l'annotazione della somministrazione di " + drug_quantity + " di " + drug_name + " al paziente"

        await requestHandler('POST', COMMAND_SERVICE_PATH, data)
            .getBody('utf-8')
            .then(JSON.parse)
            .done(function (res) {
                console.log(res);
            });
            
        return handlerInput.responseBuilder
                .speak(speechOutput)
                .reprompt("Rimango in attesa di altri comandi")
                .getResponse();
    
    }
}
        
const StartActionIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'StartActionIntent';
    },
    
    async handle(handlerInput) {
        
        var data = {json: {
            type : "action",
            category : "room",
            target : "",
            issuer : "alexa_ASTRA_controller",
            params : {
                type : "start_trauma"
            }
        }}
        
        var speechOutput = "Richiedo l'inizio della gestione di un trauma per il paziente in arrivo";

        await requestHandler('POST', COMMAND_SERVICE_PATH, data)
            .getBody('utf-8')
            .then(JSON.parse)
            .done(function (res) {
                console.log(res);
            });
            
        return handlerInput.responseBuilder
                .speak(speechOutput)
                .reprompt("Rimango in attesa di altri comandi")
                .getResponse();
        
    }
}

const EndActionIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'EndActionIntent';
    },
    
    async handle(handlerInput) {
        
        var data = {json: {
            type : "action",
            category : "room",
            target : "",
            issuer : "alexa_ASTRA_controller",
            params : {
                type : "end_trauma"
            }
        }}
        
        var speechOutput = "Richiedo la fine della gestione del trauma del paziente";

        await requestHandler('POST', COMMAND_SERVICE_PATH, data)
            .getBody('utf-8')
            .then(JSON.parse)
            .done(function (res) {
                console.log(res);
            });
            
        return handlerInput.responseBuilder
                .speak(speechOutput)
                .reprompt("Rimango in attesa di altri comandi")
                .getResponse();
    }
}

const HelpIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.HelpIntent';
    },
    handle(handlerInput) {
        const speakOutput = 'Come posso aiutarti ?';

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
        // return handlerInput.responseBuilder.getResponse();
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
        const speakOutput = `Mi dispiace, un errore mi ha impedito di completare la tua richiesta.`;

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
        VisualisationRequestIntentHandler,
        MonitorRequestIntentHandler,
        OperationAnnotationRequestIntentHandler,
        DrugAnnotationRequestIntentHandler,
        DrugInfoVisualisationRequestIntentHandler,
        StartActionIntentHandler,
        EndActionIntentHandler,
        HelpIntentHandler,
        CancelAndStopIntentHandler,
        SessionEndedRequestHandler,
        IntentReflectorHandler, // make sure IntentReflectorHandler is last so it doesn't override your custom intent handlers
    )
    .addErrorHandlers(
        ErrorHandler,
    )
    .lambda();
    
    
 /////// SYNC VERSION -> Not suitable for production env ////////////////////////////////////////////////////////////////////////////////////
        
        // const res = requestHandler('POST', COMMAND_SERVICE_PATH, data).getBody('utf-8');
                
        // var status = JSON.parse(res)
        
        // console.log(status);
        
        // speechOutput = status;
    

        // return handlerInput.responseBuilder
        //     .speak(speechOutput)
        //     .reprompt("Rimango in attesa di altri comandi")
        //     .getResponse();