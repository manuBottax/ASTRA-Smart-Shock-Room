// Agent traumaEventManagerAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start 
	<- 	?find_display(Display);
		?find_timer_artifact(TimeMonitor);
		//?find_active_trauma(Trauma);
		
		makeArtifact("activeTraumaService", "astraArtifact.ActiveTraumaArtifact", ["5ef5bc4f0c479b291c25360b"], TraumaId);
		
		focus(TraumaId);
		focus(Display);
		
		setDisplayStatus("idle") [artifact_id(DisplayId)].
		
+trauma_status(Status) : Status = "arriving"
	<- .println("test : Arriving patient");
		
		getTraumaTeam(TL, TT) [artifact_id(TraumaId)];
		setDisplayStatus("preH") [artifact_id(DisplayId)];
		showTraumaTeamInfo(TL, TT, "1") [artifact_id(DisplayId)];
		!monitorPreH.
	
+trauma_status(Status) : Status = "active"
	<- .println("test : patient is arrived");
		setPatientArrived [artifact_id(TimeMonitorId)];
		setDisplayStatus("active") [artifact_id(DisplayId)];
		!showInitialData
		!monitorActiveTraumaTime
		!monitorActiveTraumaEvent.
	
+trauma_status(Status) : Status = "done"
	<- .println("test : Patient trauma handling completed");
		setDisplayStatus("idle") [artifact_id(DisplayId)].
		
+! monitorPreH : trauma_status("arriving")
	<- 	.wait(1000)
		getTimeToETA(Eta) [artifact_id(TimeMonitorId)];
		showTemporalData(Eta, "eta", "2") [artifact_id(DisplayId)];
		getPreHInfo(PreH) [artifact_id(TraumaId)];
		showPreHInfo(PreH, "3") [artifact_id(DisplayId)];
		!!monitorPreH.	
		
 -! monitorPreH 
	<- .println("Stop Monitoring PreH"). 
	
+! showInitialData : trauma_status("active")
	<-	.wait(1500);
		showPatientInfo("123459", "3") [artifact_id(DisplayId)];
		//clearSection("3") [artifact_id(DisplayId)];
		getTraumaInfo(TInfo) [artifact_id(TraumaId)];
		showTraumaInfo(TInfo, "4") [artifact_id(DisplayId)];
		getPatientInitialCondition(InitCond)[artifact_id(TraumaId)];
		showPatientInitialConditionInfo(InitCond, "5") [artifact_id(DisplayId)].

	
+! monitorActiveTraumaTime : trauma_status("active")
	<- 	.wait(1000);
		getTimeFromArrive(Time) [artifact_id(TimeMonitorId)];
		showTemporalData(Time, "total_time", "2") [artifact_id(DisplayId)];
		!!monitorActiveTraumaTime.
	
-! monitorActiveTraumaTime 
	<- .println("Stop Monitoring Active Trauma Time"). 
	
+! monitorActiveTraumaEvent : trauma_status("active")
	<- 	.wait(10000);
		getEventList(EventList) [artifact_id(TimeMonitorId)];
		showEventList(EventList, "5") [artifact_id(DisplayId)];
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
