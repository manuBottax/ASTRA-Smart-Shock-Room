// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

current_patient("123459").

/* Initial goals */

!observe.


/* Plans */

+! observe : true 
	<-	?find_queue(Queue)
		?find_display(Display)
		?find_tt_source(TTSource)
		?find_tac_source(TacSource)
		?find_mock_source(MockSource)
	
		focus(Queue)
		focus(Display).
	
+ last_pending_command(Command) 
	<-	-+current_command(Command);
		acceptCommand [artifact_id(QueueId)].
	
+ accepted_work(Command, Id, Type, DataType, Target, Position) 
	<-	.println("Command Accepted : " , Id);
		!processCommand(Id, Type, DataType, Target, Position).

+ failed_update(Id, Error) : current_command(Command)
	<-	.println("Error ( " , Error , " ) on command update");
		setErrorOnCommand(Command) [artifact_id(QueueId)].
	
+! processCommand(CommandId, "visualisation", DataType, Target, Position) 
	<-	.println("Working on Command ", CommandId);
		.println("Want to visualise ", DataType);
		!requestData(DataType, Value);
		!displayData(CommandId, DataType, Value, Target, Position).
	
	/* TODO : Gestione Azioni */
	
/* ----------- DATA REQUEST  ----------- */
	
+! requestData("patient_details", Value) :  current_patient(P_ID)
	<-	.println("Searching for Patient Personal Data ");
		/*getMockData(Value) [artifact_id(MockSourceId)];*/
		Value = P_ID;
		+available_data(true).
	
/* Vital Parameters request */
+! requestData("blood_pressure", Value)  
	<-	.println("Searching for blood_pressure data");
		getBloodPressureValue(Value) [artifact_id(TTSourceId)];
		+available_data(true).
		
+! requestData("spO2", Value)
	<-	.println("Searching for saturation data");
		getSaturationValue(Value) [artifact_id(TTSourceId)];
		+available_data(true).
		
+! requestData("heart_rate", Value)
	<-	.println("Searching for heart rate data");
		getHeartRateValue(Value) [artifact_id(TTSourceId)];
		+available_data(true).
		
+! requestData("temperature", Value)
	<-	.println("Searching for temperature data");
		getTemperatureValue(Value) [artifact_id(TTSourceId)];
		+available_data(true).
		
// this is removed for testing behaviour with unsupported data type
/*
+! requestData(DataType, Value) : DataType = "CO2_level" | DataType = "ega" | DataType = "rotem" 
	<-	.println("Searching for Biometrical Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true). */

/* Diagnostic data */
+! requestData(DataType, Value) : DataType = "blood_tests" | DataType = "ecg" 
	<-	.println("Searching for Diagnostic Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).
		
+! requestData("chest_rx", Value)
	<-	.println("Searching for RX Data");
		getMockImage(Value) [artifact_id(MockSourceId)];
		+available_data(true).
	
+! requestData("tac", Value) : current_patient(P_ID)
	<-	.println("Searching for TAC data ");
		getTACData(P_ID, Value) [artifact_id(TacSourceId)];
		+available_data(true). 

/* Temporal data */
+! requestData(DataType, Value) : DataType = "eta" | DataType = "total_time" 
	<-	.println("Searching for Temporal Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).
		/* TODO: gestione degli aspetti temporali */
	
/* Environment data */
+! requestData("used_blood_unit", Value) 
	<-	.println("Searching for Blood Unit Data");
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).
	
/* All other Data */
+! requestData(DataType, Value) : current_command(Command)
	<-	.println("Comandi non gestiti")
		refuseCommand(Command) [artifact_id(QueueId)];
		+available_data(false).

-! requestData(DataType, Value)
	<- .println("Error, data unavailable").

+ data_type_unsopported : current_command(Command)
	<- .println("Unsupported type")
		refuseCommand(Command) [artifact_id(QueueId)];
		+available_data(false).
		
/* -------------------------------------------------------------------------------------- */
	
/* Display data in Shock Room Display using related artifact*/
+! displayData(CommandId, "patient_details", Value, Target, Position) : available_data(true)
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying patient data " , Value, " in ", Target, " on ", Position);
		-available_data(_)
		showPatientInfo(Value, Position) [artifact_id(Target)].
		
+! displayData(CommandId, DataType, Value, Target, Position) : available_data(true) & (DataType = "blood_pressure" | DataType = "spO2" | DataType = "heart_rate" | DataType = "temperature")
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position);
		-available_data(_)
		showBiometricData(Value, DataType, Position) [artifact_id(Target)].	
		
+! displayData(CommandId, DataType, Value, Target, Position) : available_data(true) & (DataType = "blood_tests" | DataType = "ecg")
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position);
		-available_data(_)
		showDiagnosticData(Value, DataType, Position) [artifact_id(Target)].
		
+! displayData(CommandId, "tac", Value, Target, Position) : available_data(true)
	<-	.println("Got TAC value");
		.println("Displaying TAC in ", Target, " on ", Position);
		-available_data(_)
		showTAC(Value, Position) [artifact_id(Target)].
		
+! displayData(CommandId, "chest_rx", Value, Target, Position) : available_data(true)
	<-	.println("Got RX value");
		.println("Displaying RX in ", Target, " on ", Position);
		-available_data(_)
		showRX(Value, Position) [artifact_id(Target)].
		
+! displayData(CommandId, DataType, Value, Target, Position) : available_data(true) & (DataType = "eta" | DataType = "total_time")
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position);
		-available_data(_)
		showTemporalData(Value, DataType, Position) [artifact_id(Target)].
		
+! displayData(CommandId, "used_blood_unit", Value, Target, Position) : available_data(true)
	<-	.println("Got blood unit value : ", Value);
		.println("Displaying blood unit data " , Value, " in ", Target, " on ", Position);
		-available_data(_)
		showEnvironmentalData(Value, DataType, Position) [artifact_id(Target)].

+! displayData(CommandId, DataType, Value, Target, Position) : available_data(false)
	<-	.println("invalid data, not print");
		-available_data(_).
		
-! displayData(CommandId, DataType, Value, Target, Position) : current_command(Command)
	<-	.println("Error on command display");
		/*.println("ID : ", CommandId , " type : ", DataType, " value : ", Value, " Target :", Target, " Position : ", Position);*/
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
+ completed_display : source(self)
	<-	.println("Display request completed successfully");
		! completeCommand.
	
+! completeCommand : current_command(Command)
	<- completeCommand(Command) [artifact_id(QueueId)].

+ command_handle_completed
	<- .println("Command Handling completed, waiting for a new command.").
	
/* -------------------------------------------------------------------------------------------------------- */
	
+? find_queue(QueueId) 
	<- lookupArtifact("roomVisualisationCommands", QueueId).
	
-? find_queue(QueueId) 
	<- .wait(200);
	   ?find_queue(QueueId).
	
+? find_display(DisplayId)
	<-	lookupArtifact("display_sr", DisplayId).
	
-? find_display(DisplayId) 
	<-	.wait(200);
		?find_display(DisplayId).
	
+? find_tt_source(TTSourceId) 
	<-	lookupArtifact("traumaTrackerService", TTSourceId).

-? find_tt_source(TTSourceId) 
	<-	.wait(200);
		?find_tt_source(TTSourceId).
	
+? find_tac_source(TacSourceId) 
	<-	lookupArtifact("tacPS", TacSourceId).

-? find_tac_source(TacSourceId) 
	<-	.wait(200);
		?find_tac_source(TacSourceId).
	
+? find_mock_source(MockSourceId) 
	<-	lookupArtifact("mockSource", MockSourceId).
 
-? find_mock_source(MockSourceId) 
	<-	.wait(200);
		?find_mock_source(MockSourceId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
