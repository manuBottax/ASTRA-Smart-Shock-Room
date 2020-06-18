// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!createArtifact.

/* Plans */

+!createArtifact
	<-  !setupCommandQueue(QueueVisualisationId);
		!setupMonitorQueue(QueueMonitorId);
		!setupActionQueue(QueueActionId);
		!setupDisplay(Display_id);
		!setupTraumaSource(Source_TT_id);
		!setupTac(Source_TAC_id);
		!setupTimer(Timer_id);
		!setupMockSource(Source_id).
	
+! setupCommandQueue(Q)
	<- makeArtifact("roomVisualisationCommands", "astraRoomAssistant.RoomCommandQueueArtifact", ["room.visualisation", "room_visualisation_commands_queue"], Q).	
	
+! setupMonitorQueue(M)
	<- makeArtifact("roomMonitorCommands", "astraRoomAssistant.RoomCommandQueueArtifact", ["room.monitoring", "room_monitor_commands_queue"], M).

+! setupActionQueue(A)
	<- makeArtifact("roomActionCommands", "astraRoomAssistant.RoomCommandQueueArtifact", ["room.action", "room_action_commands_queue"], A).

+! setupDisplay(D)
	<- makeArtifact("display_sr", "astraRoomAssistant.DisplayArtifact", [], D).
	
+! setupTraumaSource(S)
	<- makeArtifact("traumaTrackerService", "astraRoomAssistant.TraumaArtifact", [], S).
	
+! setupTac(T) 
	<- makeArtifact("tacPS", "astraRoomAssistant.TACArtifact", [], T).
	
+! setupTimer(Timer)
	<-	makeArtifact("roomTimeMonitor", "astraRoomAssistant.TimeMonitorArtifact", [], Timer).
	
+! setupMockSource(Mock)
	<- makeArtifact("mockSource", "astraRoomAssistant.MockDataSourceArtifact", [], Mock). 

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
