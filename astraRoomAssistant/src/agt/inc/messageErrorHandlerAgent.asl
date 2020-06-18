// Agent messageErrorHandlerAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+! observe : true 
	<-  ? find_room_visualisation_queue(RoomVisualQueue);
		? find_room_monitor_queue(RoomMonitorQueue);
		? find_room_action_queue(RoomActionQueue);
		
		focus(RoomVisualQueue);
		focus(RoomMonitorQueue);
		focus(RoomActionQueue);.
	
+ last_refused_command(Command, CommandID) [source(RoomQueue)]
	<-	.println("A room command was refused : " , Command).

+ last_refused_command(Command, CommandID) [source(TraumaQueue)]
	<-	.println("A trauma command was refused : " , Command).
	
/* ---------------- Handling room related command error ---------------- */
	
+ last_wrong_command(Command, CommandID) [source(RoomQueue)] : retry_attempt(CommandID, N) & N < 5 
	<-	M = N + 1;
		.println("A room command with id " , CommandID, " has an error for the ", M , " time");
		-+ retry_attempt(CommandID, M );
		.wait(10000);
		addPendingCommand(Command) [artifact_id(RoomQueue)];
		handledError [artifact_id(RoomQueue)].
	
+ last_wrong_command(Command, CommandID) [source(RoomQueue)] : retry_attempt(CommandID, N) & N >= 5 
	<-	.println("A room command with id " , CommandID, " reach error limit. It will be refused.");
		refuseCommand(Command) [artifact_id(RoomQueue)];
		handledError [artifact_id(RoomQueue)].
	
+ last_wrong_command(Command, CommandID) [source(RoomQueue)]  
	<-	.println("A room command with id " , CommandID, " has an error for the 1st time");
		+ retry_attempt(CommandID, 1);
		.wait(10000);
		addPendingCommand(Command) [artifact_id(RoomQueue)];
		handledError [artifact_id(RoomQueue)].
		
/* ---------------- Handling trauma related command error ---------------- */

+ last_wrong_command(Command, CommandID) [source(TraumaQueue)] : true  <-
	.println("A trauma command has an error : " , Command);
	.wait(10000);
	addPendingCommand(Command) [artifact_id(TraumaQueue)];
	handledError [artifact_id(TraumaQueue)].
	
+? find_room_visualisation_queue(RoomVisualQueue) 
	<-	lookupArtifact("roomVisualisationCommands", RoomVisualQueue).
	
-? find_room_visualisation_queue(RoomVisualQueue) 
	<-	.wait(200);
		?find_room_visualisation_queue(RoomVisualQueue).
	
+? find_room_monitor_queue(RoomMonitorQueue) 
	<-	lookupArtifact("roomMonitorCommands", RoomMonitorQueue).
	
-? find_room_monitor_queue(RoomMonitorQueue) 
	<-	.wait(200);
		?find_room_monitor_queue(RoomMonitorQueue).
		
+? find_room_action_queue(RoomActionQueue) 
	<-	lookupArtifact("roomActionCommands", RoomActionQueue).
	
-? find_room_action_queue(RoomActionQueue) 
	<-	.wait(200);
		?find_room_action_queue(RoomActionQueue).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
