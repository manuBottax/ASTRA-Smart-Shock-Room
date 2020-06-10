// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+! observe : true <- 
	?init_queue(Queue)
	?find_display(Display)
	?find_tt_source(TTSource)
	?find_tac_source(TacSource)
	?find_mock_source(MockSource)
	
	focus(Queue)
	focus(Display)
	focus(TTSource).
	
+last_available_command(Command) : true <-
	.println("A new command is received : " , Command);
	acceptCommand(Result, Id, Type, DataType, Target, Position) [artifact_id(QueueId)];
	/* .println(Result, " | " , Id , " | ", Type , " | ", DataType , " | ", Target , " | ", Position); */
	+accepted_work(Result, Id, Type, DataType, Target, Position).
	
+accepted_work(Result, Id, Type, DataType, Target, Position) : Result = "OK" <-
	.println("Command Taken");
	!processCommand(Id, Type, DataType, Target, Position).
	
+accepted_work(Result, Id, Type, DataType, Target, Position) : Result = "Error" <-
	.println("Error on command Acceptance").
	
+accepted_work(Result, Id, Type, DataType, Target, Position) : Result = "Init OK" <-
	.println("Queue Initialization Completed").
	
+! processCommand(CommandId, "visualisation", DataType, Target, Position) <- 
	.println("Working on Command ", CommandId)
	.println("Want to visualise ", DataType)
	!requestData(DataType, Value)
	.println("Got ", DataType, " value : ", Value)
	!displayData(CommandId, DataType, Value, Target, Position).
	
+! processCommand(CommandId, "monitoring", DataType, Target, Position)  <- 
	.println("Working on Command ", CommandId)
	.println("Want to monitor ", DataType).
	/* TODO : gestione monitoraggio */
	
+! processCommand(CommandId, "action", DataType, Target, Position) <- 
	.println("Working on Command ", CommandId)
	.println("Want an action on ", Target).
	/* TODO : Gestione Azioni */
	
/* ----------- DATA REQUEST  ----------- */
	
+! requestData("patient_details", Value) <- 
	.println("Searching for Patient Personal Data ");
	getMockData(Value) [artifact_id(MockSourceId)]. 
	
/* Vital Parameters request */
+! requestData(DataType, Value) 
	: DataType = "blood_pressure" | DataType = "spO2" | 
	  DataType = "heart_rate" | DataType = "temperature" <- 
	.println("Searching for Vital Parameter data : ", DataType);
	getDataValue(DataType, Value) [artifact_id(TTSourceId)].
	
/* Biometrical Data request */
+! requestData(DataType, Value) 
	: DataType = "CO2_level" | DataType = "ega" | DataType = "rotem" <- 
	.println("Searching for Biometrical Data : ", DataType);
	getMockData(Value) [artifact_id(MockSourceId)].

/* Diagnostic data */
+! requestData(DataType, Value) 
	: DataType = "chest_rx" | DataType = "blood_tests" | DataType = "ecg" <- 
	.println("Searching for Diagnostic Data : ", DataType);
	getMockData(Value) [artifact_id(MockSourceId)].	
	
+! requestData("tac", Value) <- 
	.println("Searching for TAC data ");
	getTACData(Value) [artifact_id(TacSourceId)]. 

/* Temporal data */
/* TODO: gestione degli aspetti temporali */
+! requestData(DataType, Value) 
	: DataType = "eta" | DataType = "total_time" <- 
	.println("Searching for Temporal Data : ", DataType);
	getMockData(Value) [artifact_id(MockSourceId)].	
	
/* Environment data */
+! requestData(DataType, Value) 
	: DataType = "used_blood_unit" <- 
	.println("Searching for Environmental Data : ", DataType);
	getMockData(Value) [artifact_id(MockSourceId)].	
	
/* All other Data */
/* TODO : Gestire tipi unhandled */
+! requestData(DataType, Value) : true <- 
	.println("non dovrei essere qui")
	.println("Searching for data ", DataType);
	getMockData(Value) [artifact_id(MockSourceId)].

/* -------------------------------------------------------------------------------------- */
	
/* Display data in Shock Room Display using related artifact*/
+! displayData(CommandId, DataType, Value, Target, Position) : true <-
	.println("Displaying ", DataType, " data " , Value, " in ", Target, " on ", Position)
	printData(DataType, Value, Position, Result) [artifact_id(Target)]
	!completeCommand(Result, CommandId).
	
+! completeCommand(Result, CommandId) : Result = "OK" <-
	println("Display request completed successfully ")
	completeCommand(CommandId) [artifact_id(QueueId)].
	
+! completeCommand(Result, CommandId) : Result = "Error" <-
	println("Display request failed")
	setCommandError(CommandId) [artifact_id(QueueId)].
	
+ command_completed(CommandId) : true <-
	println("Command processing " , CommandId , " completed.").

+ command_status_error(CommandId) : true <-
	println("Cannot update " , CommandId , " status.").
	
	
	
	
	
	
	
	
	
+? init_queue(QueueId) <-
	makeArtifact("roomCommands", "astraRoomAssistant.RoomCommandQueueArtifact", [], QueueId)
	subscribeQueue("room.*", "room_commands_queue")[artifact_id(QueueId)].
	
-? find_queue(QueueId) <-
	.wait(200);
	?find_queue(QueueId).
	
+? find_display(DisplayId) <-
	lookupArtifact("display_sr", DisplayId).
	
-? find_display(DisplayId) <-
	.wait(200);
	?find_display(DisplayId).
	
+? find_tt_source(TTSourceId) <-
	lookupArtifact("traumaTrackerService", TTSourceId).

-? find_tt_source(TTSourceId) <-
	.wait(200);
	?find_tt_source(TTSourceId).
	
+? find_tac_source(TacSourceId) <-
	lookupArtifact("tacPS", TacSourceId).

-? find_tac_source(TacSourceId) <-
	.wait(200);
	?find_tac_source(TacSourceId).
	
+? find_mock_source(MockSourceId) <- 
   	lookupArtifact("mockSource", MockSourceId).
 
-? find_mock_source(MockSourceId) <-
	.wait(200);
	?find_mock_source(MockSourceId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
