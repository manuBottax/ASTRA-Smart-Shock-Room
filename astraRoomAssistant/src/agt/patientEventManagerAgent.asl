// Agent patientEventManagerAgent in project astraRoomAssistant

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
		
+patient_status(Status) : Status = "arriving"
	<- .println("test : Arriving patient");
		addTraumaLeader("Emiliano Gamberini") [artifact_id(TraumaId)];
		setDisplayStatus("preH") [artifact_id(DisplayId)];
		showTraumaLeaderInfo("Emiliano Gamberini", "1") [artifact_id(DisplayId)];
		!monitorPreH.
	
+patient_status(Status) : Status = "active"
	<- .println("test : patient is arrived");
		setArrivalTime [artifact_id(TimeMonitorId)];
		setDisplayStatus("active") [artifact_id(DisplayId)];
		!showInitialData
		!monitorActiveTrauma.
	
+patient_status(Status) : Status = "done"
	<- .println("test : Patient trauma handling completed");
		setDisplayStatus("idle") [artifact_id(DisplayId)].
		
+! monitorPreH : patient_status("arriving")
	<- 	.wait(1000)
		getETA("system", "display_sr", "2") [artifact_id(TimeMonitorId)];
		getPreHInfo(PreH) [artifact_id(TraumaId)];
		showPreHInfo(PreH, "3") [artifact_id(DisplayId)];
		!monitorPreH.	
		
-! monitorPreH 
	<- .println("Stop Monitoring PreH").
	
+! showInitialData
	<-	.wait(2000);
		showPatientInfo("123459", "3") [artifact_id(DisplayId)];
		getPreHInfo(PreH) [artifact_id(TraumaId)];
		showPreHInfo(PreH, "4") [artifact_id(DisplayId)].
	
+! monitorActiveTrauma : patient_status("active")
	<- 	.wait(1000);
		getTotalTime("system", "display_sr", "2") [artifact_id(TimeMonitorId)];
		!monitorActiveTrauma.
	
-! monitorActiveTrauma 
	<- .println("Stop Monitoring Active Trauma"). 
	
+ new_monitoring_value(CommandId, Value, DataType, Target, Position) : DataType = "eta" | DataType = "total_time"
	<-  showTemporalData(Value, DataType, Position) [artifact_id(Target)].
		
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
