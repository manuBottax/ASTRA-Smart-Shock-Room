// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

//free_agent(true).

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
	<-	.println("Working on Command ", CommandId)
		.println("Want to visualise ", DataType)
		!requestData(DataType, Value)
		!displayData(CommandId, DataType, Value, Target, Position).
	
+! processCommand(CommandId, "monitoring", DataType, Target, Position)
	<-	.println("Working on Command ", CommandId)
		.println("Want to monitor ", DataType).
	/* TODO : gestione monitoraggio */
	
+! processCommand(CommandId, "action", DataType, Target, Position)
	<-	.println("Working on Command ", CommandId)
		.println("Want an action on ", Target).
	/* TODO : Gestione Azioni */
	
/* ----------- DATA REQUEST  ----------- */
	
+! requestData("patient_details", Value)
	<-	.println("Searching for Patient Personal Data ");
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).
	
/* Vital Parameters request */
+! requestData(DataType, Value) 
	: DataType = "blood_pressure" |/* DataType = "spO2" | 
	  DataType = "heart_rate" |*/ DataType = "temperature" 
	<-	.println("Searching for Vital Parameter data : ", DataType);
		getDataValue(DataType, Value) [artifact_id(TTSourceId)];
		+available_data(true).
	
/* Biometrical Data request */
+! requestData(DataType, Value) : DataType = "CO2_level" | DataType = "ega" | DataType = "rotem" 
	<-	.println("Searching for Biometrical Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).

/* Diagnostic data */
+! requestData(DataType, Value) : DataType = "chest_rx" | DataType = "blood_tests" | DataType = "ecg" 
	<-	.println("Searching for Diagnostic Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).
	
+! requestData("tac", Value) 
	<-	.println("Searching for TAC data ");
		getTACData(Value) [artifact_id(TacSourceId)];
		+available_data(true). 

/* Temporal data */
+! requestData(DataType, Value) : DataType = "eta" | DataType = "total_time" 
	<-	.println("Searching for Temporal Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).
		/* TODO: gestione degli aspetti temporali */
	
/* Environment data */
+! requestData(DataType, Value) : DataType = "used_blood_unit" 
	<-	.println("Searching for Environmental Data : ", DataType);
		getMockData(Value) [artifact_id(MockSourceId)];
		+available_data(true).
	
/* All other Data */
+! requestData(DataType, Value) : current_command(Command)
	<-	.println("Comandi non gestiti")
		refuseCommand(Command) [artifact_id(QueueId)];
		+available_data(false).

+ data_type_unsopported : current_command(Command)
	<- .println("Unsupported type")
		refuseCommand(Command) [artifact_id(QueueId)];
		+available_data(false).
		
/* -------------------------------------------------------------------------------------- */
	
/* Display data in Shock Room Display using related artifact*/
+! displayData(CommandId, DataType, Value, Target, Position) : available_data(true)
	<-	.println("Got ", DataType, " value : ", Value);
		.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position);
		-available_data(_)
		printData(DataType, Value, Position) [artifact_id(Target)].
		

+! displayData(CommandId, DataType, Value, Target, Position) : available_data(false)
	<-	.println("invalid data, not print");
		-available_data(_).
		
-! displayData(CommandId, DataType, Value, Target, Position) : current_command(Command)
	<-	.println("Error on command display");
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
+ completed_display
	<-	.println("Display request completed successfully");
		! completeCommand.
	
+! completeCommand : current_command(Command)
	<- completeCommand(Command) [artifact_id(QueueId)].


+ command_handle_completed
	<- .println("Command Handling completed, waiting for a new command.").
	
	
+? find_queue(QueueId) 
	<- lookupArtifact("roomCommands", QueueId).
	
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
