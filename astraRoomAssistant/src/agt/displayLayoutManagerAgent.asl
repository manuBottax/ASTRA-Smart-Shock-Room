// Agent displayLayoutManagerAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start 
	<- 	?find_display(Display);
		?find_timer_artifact(TimeMonitor);
		focus(Display);
		focus(TimeMonitor);
		
		setDisplayStatus("idle") [artifact_id(DisplayId)];
		.wait(30000);
		setDisplayStatus("preH") [artifact_id(DisplayId)];
		showTraumaLeaderInfo("Emiliano Gamberini", "1") [artifact_id(DisplayId)];
		monitorETAWithTimer("preH", "display_sr", "2", 120) [artifact_id(TimeMonitorId)]
		//.wait(30000);
		setDisplayStatus("active") [artifact_id(DisplayId)];
		setArrivalTime [artifact_id(TimeMonitorId)];
		showPatientInfo("123459", "3") [artifact_id(DisplayId)];
		monitorTotalTime("preH", "display_sr", "2") [artifact_id(TimeMonitorId)].
		
+? find_display(DisplayId)
	<-	lookupArtifact("display_sr", DisplayId).
	
-? find_display(DisplayId) 
	<-	.wait(200);
		?find_display(DisplayId).

+? find_timer_artifact(TimeMonitorId)
	<-  lookupArtifact("roomTimeMonitor", TimeMonitorId).
	
-? find_timer_artifact(TimeMonitorId)
	<-  .wait(200);
		?find_timer_artifact(TimeMonitorId).
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
