// Agent roomActionAgent in project astraRoomAssistant

/* Initial beliefs and rules */

current_patient("123459").

/* Initial goals */

!observe.

/* Plans */

+! observe : true 
	<-	?find_queue(Queue)
		?find_timer_artifact(TimeMonitor)
	
		focus(Queue).


+ last_pending_command(Command) 
	<-	-+current_command(Command);
		acceptCommand [artifact_id(QueueId)].
	
+ accepted_work(Command, Id, Type, DataType, Target, Position) 
	<-	.println("Command Accepted : " , Id);
		!processCommand(Id, Type, DataType, Target, Position).

+ failed_update(Id, Error) : current_command(Command)
	<-	.println("Error ( " , Error , " ) on command update");
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
/* ---------- Action on Time Monitoring ------------------------ */
		
+! processCommand(CommandId, "action", DataType, Target, Position)
	<-	.println("Working on Command ", CommandId)
		.println("Want an action on ", Target);
		!doAction(CommandID, DataType, Target).

+! doAction(CommandID, "start_trauma", Target) 
	<-  .println("Inizio Gestione Trauma");
		completeCommand(CommandId) [artifact_id(QueueId)].
		
+! doAction(CommandID, "patient_arrived", Target) 
	<-  .println("Inizio Gestione Trauma")
		setArrivalTime(CommandID) [artifact_id(Target)];
		completeCommand(CommandId) [artifact_id(QueueId)].
		
+! doAction(CommandID, "end_trauma", Target) 
	<-  .println("Termine Gestione Trauma");
		completeCommand(CommandId) [artifact_id(QueueId)].
		
-! doAction(CommandId, DataType, Target) : current_command(Command)
	<-  .println("Cannot complete Action");
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
/* -------------------------------------------------- */	
		
+? find_queue(QueueId) 
	<- lookupArtifact("roomActionCommands", QueueId).
	
-? find_queue(QueueId) 
	<- .wait(200);
	   ?find_queue(QueueId).

+? find_timer_artifact(TimeMonitorId)
	<-  lookupArtifact("roomTimeMonitor", TimeMonitorId).
	
-? find_timer_artifact(TimeMonitorId)
	<-  .wait(200);
		?find_timer_artifact(TimeMonitorId).


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
