// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+! observe : true <- 
	?init_queue(Queue)
	?find_display(Display)
	?find_tt_source(TTSource)
	?find_tac_source(Tac)
	
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
	
+! processCommand(CommandId, Type, DataType, Target, Position) : Type = "visualisation" <- 
	.println("Working on Command ", Id)
	.println("Want to visualise ", DataType)
	!requestData(DataType, Value)
	.println("Got ", DataType, " value : ", Value)
	!displayData(CommandId, DataType, Value, Target, Position).
	
+! processCommand(Command, Id, Type, DataType, Target, Position) : Type = "monitoring" <- 
	.println("Working on Command ", Id)
	.println("Want to monitor ", DataType).
	
+! processCommand(Command, Id, Type, DataType, Target, Position) : Type = "action" <- 
	.println("Working on Command ", Id)
	.println("Want an action on ", Target).

+! requestData(DataType, Value) : true <- 
	.println("Searching for data ", DataType);
	getDataValue(DataType, Value) [artifact_id(TTSourceId)].
	
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
	
	
	
	
	
	
	
	
	
+? init_queue(QueueId) : true <-
	makeArtifact("roomCommands", "astraRoomAssistant.RoomCommandQueueArtifact", [], QueueId)
	/*lookupArtifact("roomCommands", QueueId);*/
	subscribeQueue("room.*", "room_commands_queue")[artifact_id(QueueId)].
	/* +queue(QueueId). */
	
-? find_queue(QueueId) : true <-
	.wait(200);
	?find_queue(QueueId).
	
+? find_display(DisplayId) : true <-
	lookupArtifact("display_sr", DisplayId).
	/* +display("display_sr", DisplayId).  */
	
-? find_display(DisplayId) : true <-
	.wait(200);
	?find_display(DisplayId).
	
+? find_tt_source(TTSourceId) : true <-
	lookupArtifact("traumaTrackerService", TTSourceId).

-? find_tt_source(TTSourceId) : true <-
	.wait(200);
	?find_source(TTSourceId).
	
+? find_tac_source(TacSourceId) : true <-
	lookupArtifact("tacPS", TacSourceId).

-? find_tac_source(TacSourceId) : true <-
	.wait(200);
	?find_source(TacSourceId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
