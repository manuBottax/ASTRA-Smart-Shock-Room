// Agent systemInitAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!createArtifact.

/* Plans */

+!createArtifact
	<-  !setupCommandQueue(QueueVisualisationId);
		!setupMonitorQueue(QueueMonitorId);
		!setupActionQueue(QueueActionId);
		!setupDisplay(DisplayIdd);
		! setupVitalParameterSource(VitalParameterId);
		!setupTraumaSource(SourceTraumaId);
		!setupTac(SourceTACId);
		!setupTimer(TimerId);
		!setupMockSource(SourceId).
	
+! setupCommandQueue(Q)
	<- makeArtifact("roomVisualisationCommands", "astraArtifact.RoomCommandQueueArtifact", ["room.visualisation", "room_visualisation_commands_queue"], Q).	
	
+! setupMonitorQueue(M)
	<- makeArtifact("roomMonitorCommands", "astraArtifact.RoomCommandQueueArtifact", ["room.monitoring", "room_monitor_commands_queue"], M).

+! setupActionQueue(A)
	<- makeArtifact("roomActionCommands", "astraArtifact.RoomCommandQueueArtifact", ["room.action", "room_action_commands_queue"], A).

+! setupDisplay(D)
	<- makeArtifact("display_sr", "astraArtifact.DisplayArtifact", [], D).
	
+! setupTraumaSource(S)
	<- makeArtifact("activeTraumaService", "astraArtifact.ActiveTraumaArtifact", [], S).

+! setupVitalParameterSource(V)
	<- makeArtifact("vitalParameterMonitor", "astraArtifact.VitalParameterArtifact", [], V).	
	
+! setupTac(T) 
	<- makeArtifact("tacPS", "astraArtifact.TACArtifact", [], T).
	
+! setupTimer(Timer)
	<-	makeArtifact("roomTimeMonitor", "astraArtifact.TimeMonitorArtifact", [], Timer).
	
+! setupMockSource(Mock)
	<- makeArtifact("mockSource", "astraArtifact.MockDataSourceArtifact", [], Mock). 

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
