// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

current_patient("123459").

/* Initial goals */

!observe.

/* Plans */

+! observe 
	<-	?find_queue(Queue)
		?find_display(Display)
		?find_vital_parameter_source(VitalParameterSource)
		?find_tac_source(TacSource)
		?find_mock_source(MockSource)
		?find_timer_artifact(TimeMonitor)
	
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
		!requestData(CommandId, DataType, Target, Position).

-! processCommand(CommandId, "visualisation", DataType, Target, Position) 
	<-	.println("FAILED COMMAND PROCESSING", CommandId).
	
/* ----------- DATA REQUEST  ----------- */
	
+! requestData(CommandId, "patient_details", Target, Position) :  current_patient(P_ID)
	<-	.println("Searching for Patient Personal Data ");
		!displayData(CommandId, DataType, P_ID, Target, Position).
	
/* Vital Parameters request */
+! requestData(CommandId, "blood_pressure", Target, Position)  
	<-	.println("Searching for blood_pressure data");
		getBloodPressureValue(Value) [artifact_id(VitalParameterSourceId)];
		!displayData(CommandId, "blood_pressure", Value, Target, Position).
		
+! requestData(CommandId, "spO2", Target, Position)
	<-	.println("Searching for saturation data");
		getSaturationValue(Value) [artifact_id(VitalParameterSourceId)];
		!displayData(CommandId, "spO2", Value, Target, Position).
		
+! requestData(CommandId, "heart_rate", Target, Position)
	<-	.println("Searching for heart rate data");
		getHeartRateValue(Value) [artifact_id(VitalParameterSourceId)];
		!displayData(CommandId, "heart_rate", Value, Target, Position).
		
+! requestData(CommandId, "temperature", Target, Position)
	<-	.println("Searching for temperature data");
		getTemperatureValue(Value) [artifact_id(VitalParameterSourceId)];
		!displayData(CommandId, "temperature", Value, Target, Position).
		
// this is removed for testing behaviour with unsupported data type
/*
+! requestData(CommandId, DataType, Target, Position) : DataType = "CO2_level" | DataType = "ega" | DataType = "rotem" 
	<-	.println("Searching for Biometrical Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		!displayData(CommandId, DataType, Value, Target, Position). */

/* Diagnostic data */
+! requestData(CommandId, DataType, Target, Position) : DataType = "blood_tests" | DataType = "ecg" 
	<-	.println("Searching for Diagnostic Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		!displayData(CommandId, DataType, Value, Target, Position).
		
+! requestData(CommandId, "chest_rx", Target, Position)
	<-	.println("Searching for RX Data");
		getMockImage(Value) [artifact_id(MockSourceId)];
		!displayData(CommandId, "chest_rx", Value, Target, Position).
	
+! requestData(CommandId, "tac", Target, Position) : current_patient(P_ID)
	<-	.println("Searching for TAC data ");
		getTACData(P_ID, Value) [artifact_id(TacSourceId)];
		!displayData(CommandId, "tac", Value, Target, Position). 

/* Temporal data */
+! requestData(CommandId, "eta", Target, Position)
	<-	.println("Searching for Temporal Data : ", DataType);
		getETA(Value) [artifact_id(TimeMonitorId)];
		!displayData(CommandId, DataType, Value, Target, Position).
		
+! requestData(CommandId, "total_time", Target, Position)
	<-	.println("Searching for Temporal Data : ", DataType);
		getArrivalTime(Value) [artifact_id(TimeMonitorId)];
		!displayData(CommandId, DataType, Value, Target, Position).
	
/* Environment data */
+! requestData(CommandId, "used_blood_unit", Target, Position) 
	<-	.println("Searching for Blood Unit Data");
		getMockData(Value) [artifact_id(MockSourceId)];
		!displayData(CommandId, "used_blood_unit", Value, Target, Position).
	
/* All other Data */
+! requestData(CommandId, DataType, Target, Position) : current_command(Command)
	<-	.println("Command not handled, refusing ...")
		refuseCommand(Command) [artifact_id(QueueId)].

-! requestData(CommandId, DataType, Target, Position) : current_command(Command)
	<- 	.println("Error ! Data unavailable")
		setErrorOnCommand(Command) [artifact_id(QueueId)].

/* + data_type_unsopported : current_command(Command)
	<- .println("Unsupported type")
		refuseCommand(Command) [artifact_id(QueueId)]. */
		
/* -------------------------------------------------------------------------------------- */
	
/* Display data in Shock Room Display using related artifact*/
+! displayData(CommandId, "patient_details", Value, Target, Position)
	<-	.println("Got Patient ID value : ", Value);
		.println("Displaying patient data " , Value, " in ", Target, " on ", Position);
		showPatientInfo(Value, Position) [artifact_id(Target)]
		.println("Display request completed successfully");
		! completeCommand.
		
+! displayData(CommandId, DataType, Value, Target, Position) : DataType = "blood_pressure" | DataType = "spO2" | DataType = "heart_rate" | DataType = "temperature"
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position);
		showBiometricData(Value, DataType, Position) [artifact_id(Target)]
		.println("Display request completed successfully");
		! completeCommand.	
		
+! displayData(CommandId, DataType, Value, Target, Position) : DataType = "blood_tests" | DataType = "ecg"
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position);
		showDiagnosticData(Value, DataType, Position) [artifact_id(Target)]
		.println("Display request completed successfully");
		! completeCommand.
		
+! displayData(CommandId, "tac", Value, Target, Position)
	<-	.println("Got TAC value");
		.println("Displaying TAC in ", Target, " on ", Position);
		showTAC(Value, Position) [artifact_id(Target)]
		.println("Display request completed successfully");
		! completeCommand.
		
+! displayData(CommandId, "chest_rx", Value, Target, Position)
	<-	.println("Got RX value");
		.println("Displaying RX in ", Target, " on ", Position);
		showRX(Value, Position) [artifact_id(Target)]
		.println("Display request completed successfully");
		! completeCommand.
		
+! displayData(CommandId, DataType, Value, Target, Position) : DataType = "eta" | DataType = "total_time"
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position);
		showTemporalData(Value, DataType, Position) [artifact_id(Target)]
		.println("Display request completed successfully");
		! completeCommand.
		
+! displayData(CommandId, "used_blood_unit", Value, Target, Position)
	<-	.println("Got blood unit value : ", Value);
		.println("Displaying blood unit data " , Value, " in ", Target, " on ", Position);
		showEnvironmentalData(Value, DataType, Position) [artifact_id(Target)]
		.println("Display request completed successfully");
		! completeCommand.
		
-! displayData(CommandId, DataType, Value, Target, Position) : current_command(Command)
	<-	.println("Error on command display");
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
	
+! completeCommand : current_command(Command)
	<- 	completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").

	
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
	
+? find_vital_parameter_source(VitalParameterSourceId) 
	<-	lookupArtifact("activeTraumaService", VitalParameterSourceId).

-? find_vital_parameter_source(VitalParameterSourceId) 
	<-	.wait(200);
		?find_vital_parameter_source(VitalParameterSourceId).
	
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
		
+? find_timer_artifact(TimeMonitorId)
	<-  lookupArtifact("roomTimeMonitor", TimeMonitorId).
	
-? find_timer_artifact(TimeMonitorId)
	<-  .wait(200);
		?find_timer_artifact(TimeMonitorId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
