// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+! observe : true <- 
	?find_queue(Queue)
	?find_display(Display)
	?find_tt_source(TTSource)
	?find_tac_source(Tac)
	
	focus(Queue)
	focus(Display)
	focus(TTSource).
	
+last_available_command(Command) : true <-
	.println("A new command is received : " , Command);
	acceptCommand(Result, Id, Type, Value, Target, Position) [artifact_id(QueueId)];
	/* .println(Result, " | " , Id , " | ", Type , " | ", Value , " | ", Target , " | ", Position); */
	+accepted_work(Result, Command, Id, Type, Value, Target, Position).
	
+accepted_work(Result, Command, Id, Type, Value, Target, Position) : Result = "OK" <-
	.println("Command Taken");
	!processCommand(Command, Id, Type, Value, Target, Position).
	
+accepted_work(Result, Command, Id, Type, Value, Target, Position) : Result = "Error" <-
	.println("Error on command Acceptance").
	
+accepted_work(Result, Command, Id, Type, Value, Target, Position) : Result = "Init OK" <-
	.println("Initialization Completed").
	
+! processCommand(Command, Id, Type, Value, Target, Position) : Type = "visualisation" <- 
	.println("Working on Command ", Id)
	.println("Want to visualise ", Value)
	!requestData(Value, Data)
	.println("Got ", Value, " value : ", Data)
	!displayData(Data, Target, Position).
	
+! processCommand(Command, Id, Type, Value, Target, Position) : Type = "monitoring" <- 
	.println("Working on Command ", Id)
	.println("Want to monitor ", Value).
	
+! processCommand(Command, Id, Type, Value, Target, Position) : Type = "action" <- 
	.println("Working on Command ", Id)
	.println("Want an action on ", Target).

+! requestData(Value, Data) : true <- 
	.println("Searching for data ", Value);
	getDataValue(Value, Data) [artifact_id(SourceId)].
	
/* Display data in Shock Room Display using related artifact*/
+! displayData(Data, Target, Position) : true <-
	.println("Displaying data " , Data, " in ", Target, " on ", Position).
	/* requestDisplay(Data, Position) [artifact_id(Target)]. */
	
+display_request_completed(Output) : true <-
	println("Display request completed : " , Output)
	completeCommand(Id) [artifact_id(QueueId)].

+display_request_failed(Error) : true <-
	println("Display request failed : " , Error).	
	
	
	
	
	
	
	
	
	
+? find_queue(QueueId) : true <-
	lookupArtifact("roomCommands", QueueId);
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
