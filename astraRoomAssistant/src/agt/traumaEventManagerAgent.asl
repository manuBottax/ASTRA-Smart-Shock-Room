// Agent traumaEventManagerAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start 
	<- 	?find_display(Display);
		?find_timer_artifact(TimeMonitor);
		//?find_active_trauma(Trauma);
		
		makeArtifact("activeTraumaService", "astraArtifact.ActiveTraumaArtifact",[], TraumaId);
		
		focus(TraumaId);
		focus(Display);
		
		setDisplayStatus("idle") [artifact_id(DisplayId)].
		
+trauma_status(Status) : Status = "arriving"
	<- .println("Trauma Status : Arriving patient");
		setDisplayStatus("preH") [artifact_id(DisplayId)];
		!monitorPreHETA
		!monitorPreHInfo.
	
+trauma_status(Status) : Status = "active"
	<- .println("Trauma Status : patient is arrived");
		setDisplayStatus("active") [artifact_id(DisplayId)];
		setPatientArrived [artifact_id(TimeMonitorId)];
		!showInitialData
		!monitorActiveTraumaTime
		!monitorActiveTraumaEvent.
	
+trauma_status(Status) : Status = "done"
	<- .println("Trauma Status : Patient trauma handling completed");
		setDisplayStatus("idle") [artifact_id(DisplayId)];
		completeTraumaHandling [artifact_id(TraumaId)].
		
		
+! monitorPreHETA : trauma_status("arriving")
	<- 	getTimeToETA(Eta) [artifact_id(TimeMonitorId)];
		showTemporalData(Eta, "eta", "2") [artifact_id(DisplayId)];
		.wait(1000);
		!!monitorPreHETA.	
		
 -! monitorPreHETA
	<- .println("Stop Monitoring PreH ETA"). 
	
+! monitorPreHInfo: trauma_status("arriving")
	<- 	getPreHInfo(PreH) [artifact_id(TraumaId)];
		showPreHInfo(PreH, "3") [artifact_id(DisplayId)];
		getTraumaTeam(TL, TT) [artifact_id(TraumaId)];
		showTraumaTeamInfo(TL, TT, "1") [artifact_id(DisplayId)];
		.wait(5000);
		!!monitorPreHInfo.	
		
 -! monitorPreHInfo
	<- .println("Stop Monitoring PreH INFO"). 
	
	
// -----------------------------------------------------------------------------
	
+! showInitialData : trauma_status("active")
	<-	showPatientInfo("654321", "3") [artifact_id(DisplayId)].

	
+! monitorActiveTraumaTime : trauma_status("active")
	<- 	getTimeFromArrive(Time) [artifact_id(TimeMonitorId)];
		showTemporalData(Time, "total_time", "2") [artifact_id(DisplayId)];
		.wait(1000);
		!!monitorActiveTraumaTime.
	
-! monitorActiveTraumaTime 
	<- .println("Stop Monitoring Active Trauma Time"). 
	
+! monitorActiveTraumaEvent : trauma_status("active")
	<- 	getTraumaInfo(TInfo) [artifact_id(TraumaId)];
		showTraumaInfo(TInfo, "4") [artifact_id(DisplayId)];
		getEventList(EventList) [artifact_id(TimeMonitorId)];
		showEventList(EventList, "5") [artifact_id(DisplayId)];
		.wait(5000);
		!!monitorActiveTraumaEvent.
	
-! monitorActiveTraumaEvent 
	<- .println("Stop Monitoring Active TraumaEvent").
	
// --------------------------------------------------------------------------------------------------
		
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
		
/* +? find_active_trauma(TraumaId)
	<-  lookupArtifact("activeTraumaService", TraumaId).
	
-? find_active_trauma(TraumaId)
	<-  .wait(200);
		?find_active_trauma(TraumaId). */
		
		
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
