// Agent errorHandlerAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+! observe : true 
	<-  ? find_room_visualisation_queue(VisualQueue);
		? find_room_monitor_queue(MonitorQueue);
		? find_room_action_queue(ActionQueue);
		
		?find_display(D);
		
		focus(VisualQueue);
		focus(MonitorQueue);
		focus(ActionQueue);
		
		focus(D).
		
/* ---------------- Handling room related command error ---------------- */
	
+ last_refused_command(Command, CommandID) [artifact_id(RoomVisualQueue)] : ( not CommandID == "-1")
	<-	.println("A room visualisation command was refused : " , CommandID);
		showError(Command, "8") [artifact_id(Display)].
	
+ last_refused_command(Command, CommandID) [artifact_id(RoomMonitorQueue)] : ( not CommandID == "-1")
	<-	.println("A room monitoring command was refused : " , CommandID);
		showError(Command, "8") [artifact_id(Display)].
	
+ last_refused_command(Command, CommandID) [artifact_id(RoomActionQueue)] :  ( not CommandID == "-1")
	<-	.println("A room action command was refused : " , CommandID);
		showError(Command, "8") [artifact_id(Display)].	

/* ERROR on Visualisation  */
+ last_wrong_command(Command, CommandID) [artifact_id(RoomVisualQueue)] : ( not CommandID == "-1") & retry_attempt(CommandID, N) & N < 5 
	<-	M = N + 1;
		.println("A room visulisation command with id " , CommandID, " has an error for the ", M , " time");
		-+ retry_attempt(CommandID, M );
		.wait(2000);
		addPendingCommand(Command) [artifact_id(RoomVisualQueue)];
		handledError [artifact_id(RoomVisualQueue)].
	
+ last_wrong_command(Command, CommandID) [artifact_id(RoomVisualQueue)] : ( not CommandID == "-1") & retry_attempt(CommandID, N) & N >= 5 
	<-	.println("A room visulisation command with id " , CommandID, " reach error limit. It will be refused.");
		refuseCommand(Command) [artifact_id(RoomVisualQueue)];
		handledError [artifact_id(RoomVisualQueue)].
	
+ last_wrong_command(Command, CommandID) [artifact_id(RoomVisualQueue)] : ( not CommandID == "-1")
	<-	.println("A room visulisation command with id " , CommandID, " has an error for the 1st time");
		+ retry_attempt(CommandID, 1);
		.wait(2000);
		addPendingCommand(Command) [artifact_id(RoomVisualQueue)];
		handledError [artifact_id(RoomVisualQueue)].
		
/* ERROR on Monitor  */
+ last_wrong_command(Command, CommandID) [artifact_id(RoomMonitorQueue)] : ( not CommandID == "-1") & retry_attempt(CommandID, N) & N < 5 
	<-	M = N + 1;
		.println("A room monitor command with id " , CommandID, " has an error for the ", M , " time");
		-+ retry_attempt(CommandID, M );
		.wait(2000);
		addPendingCommand(Command) [artifact_id(RoomMonitorQueue)];
		handledError [artifact_id(RoomMonitorQueue)].
	
+ last_wrong_command(Command, CommandID) [artifact_id(RoomMonitorQueue)] : ( not CommandID == "-1") & retry_attempt(CommandID, N) & N >= 5 
	<-	.println("A room monitor command with id " , CommandID, " reach error limit. It will be refused.");
		refuseCommand(Command) [artifact_id(RoomMonitorQueue)];
		handledError [artifact_id(RoomMonitorQueue)].
	
+ last_wrong_command(Command, CommandID) [artifact_id(RoomMonitorQueue)]  : ( not CommandID == "-1")
	<-	.println("A room monitor command with id " , CommandID, " has an error for the 1st time");
		+ retry_attempt(CommandID, 1);
		.wait(2000);
		addPendingCommand(Command) [artifact_id(RoomMonitorQueue)];
		handledError [artifact_id(RoomMonitorQueue)].
		
/* ERROR on Action  */
+ last_wrong_command(Command, CommandID) [artifact_id(RoomActionQueue)] : ( not CommandID == "-1") & retry_attempt(CommandID, N) & N < 5 
	<-	M = N + 1;
		.println("A room action command with id " , CommandID, " has an error for the ", M , " time");
		-+ retry_attempt(CommandID, M );
		.wait(2000);
		addPendingCommand(Command) [artifact_id(RoomActionQueue)];
		handledError [artifact_id(RoomActionQueue)].
	
+ last_wrong_command(Command, CommandID) [artifact_id(RoomActionQueue)] : ( not CommandID == "-1") & retry_attempt(CommandID, N) & N >= 5 
	<-	.println("A room action command with id " , CommandID, " reach error limit. It will be refused.");
		refuseCommand(Command) [artifact_id(RoomActionQueue)];
		handledError [artifact_id(RoomActionQueue)].
	
+ last_wrong_command(Command, CommandID) [artifact_id(RoomActionQueue)] : ( not CommandID == "-1")
	<-	.println("A room action command with id " , CommandID, " has an error for the 1st time");
		+ retry_attempt(CommandID, 1);
		.wait(2000);
		addPendingCommand(Command) [artifact_id(RoomActionQueue)];
		handledError [artifact_id(RoomActionQueue)].
		
/* ---------------- Handling trauma related command error ---------------- */

// TODO : Implementare quando viene aggiunto il TraumaAssistantAgent

/* + last_refused_command(Command, CommandID) [source(TraumaQueue)]
	<-	.println("A trauma command was refused : " , Command). */

	
	
	
	
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
		
+? find_display(Display) 
	<-	lookupArtifact("display_sr", Display).
	
-? find_display(Display) 
	<-	.wait(200);
		?find_display(Display).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
