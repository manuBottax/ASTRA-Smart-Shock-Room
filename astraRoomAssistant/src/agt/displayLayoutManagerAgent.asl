// Agent displayLayoutManagerAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start 
	<- 	?find_display(Display);
		?find_timer_artifact(TimeMonitor);
		?find_active_trauma(Trauma);
		
		focus(Display);
		focus(TimeMonitor);
		focus(Trauma);
		
		setDisplayStatus("idle") [artifact_id(DisplayId)].
		//.wait(30000);
		/*setDisplayStatus("preH") [artifact_id(DisplayId)];
		showTraumaLeaderInfo("Emiliano Gamberini", "1") [artifact_id(DisplayId)];
		monitorETAWithTimer("preH", "display_sr", "2", 120) [artifact_id(TimeMonitorId)]*/
		//.wait(30000);
		/*setArrivalTime [artifact_id(TimeMonitorId)];
		setDisplayStatus("active") [artifact_id(DisplayId)];
		showPatientInfo("123459", "3") [artifact_id(DisplayId)];
		monitorTotalTime("preH", "display_sr", "2") [artifact_id(TimeMonitorId)].*/
		
+patient_status(Status) : Status = "arriving"
	<- .println("test : Arriving patient");
		setDisplayStatus("preH") [artifact_id(DisplayId)];
		showTraumaLeaderInfo("Emiliano Gamberini", "1") [artifact_id(DisplayId)];
		monitorETA("system", "display_sr", "2") [artifact_id(TimeMonitorId)].
	
+patient_status(Status) : Status = "active"
	<- .println("test : patient is arrived");
		setArrivalTime [artifact_id(TimeMonitorId)];
		setDisplayStatus("active") [artifact_id(DisplayId)];
		showPatientInfo("123459", "3") [artifact_id(DisplayId)];
		monitorTotalTime("system", "display_sr", "2") [artifact_id(TimeMonitorId)].
	
+patient_status(Status) : Status = "done"
	<- .println("test : Patient trauma handling completed");
		setDisplayStatus("idle") [artifact_id(DisplayId)].
		
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
		
+? find_active_trauma(TraumaId)
	<-  lookupArtifact("activeTraumaService", TraumaId).
	
-? find_active_trauma(TraumaId)
	<-  .wait(200);
		?find_active_trauma(TraumaId).
		
		
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
