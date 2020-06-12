// Agent refusedMessageHandlerAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+! observe : true <- 
	?find_room_queue(RoomQueue);
	focus(RoomQueue).
	
+ last_refused_command(Command) [source(RoomQueue)] : true  <-
	.println("A room command was refused : " , Command).

+ last_refused_command(Command) [source(TraumaQueue)] : true  <-
	.println("A trauma command was refused : " , Command).
	
+ last_wrong_command(Command) [source(RoomQueue)] : true  <-
	.println("A room command has an error : " , Command);
	.wait(10000);
	addPendingCommand(Command) [artifact_id(RoomQueue)];
	handledError(Command) [artifact_id(RoomQueue)].

+ last_wrong_command(Command) [source(TraumaQueue)] : true  <-
	.println("A trauma command has an error : " , Command);
	.wait(10000);
	addPendingCommand(Command) [artifact_id(TraumaQueue)];
	handledError(Command) [artifact_id(TraumaQueue)].
	
	
	
	
+? find_room_queue(RoomQueueId) <-
	lookupArtifact("roomCommands", RoomQueueId).
	
-? find_room_queue(RoomQueueId) <-
	.wait(200);
	?find_room_queue(RoomQueueId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
