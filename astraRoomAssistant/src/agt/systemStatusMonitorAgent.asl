// Agent systemStatusMonitorAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!monitorArtifact.
		
/* Plans */


+! monitorArtifact
	<-	?find_visualisation_queue(VisualisationQueue)
		?find_monitor_queue(MonitorQueue)
		?find_action_queue(ActionQueue)
		?find_display(Display)
		?find_vital_parameter_source(VitalParameterSource)
		?find_tac_source(TacSource)
		?find_mock_source(MockSource)
		?find_timer_artifact(TimeMonitor);
		
		focus(VisualisationQueue);
		focus(MonitorQueue);
		focus(ActionQueue);
		focus(Display);
		focus(VitalParameterSource);
		focus(TacSource);
		focus(MockSource);
		focus(TimeMonitor).

+ trauma_artifact_status (Status) 
	<- .println("Artifact 'Active Trauma' Status changed : " , Status).
	
+ time_artifact_status (Status) 
	<- .println("Artifact 'Time monitor' Status changed : " , Status).
	
+ tac_artifact_status (Status) 
	<- .println("Artifact 'TAC' Status changed : " , Status).
	
+ mock_source_artifact_status (Status) 
	<- .println("Artifact 'Mock Source' Status changed : " , Status).
	
+ display_artifact_status (Status) 
	<- .println("Artifact 'Display' Status changed : " , Status).
	
+ queue_artifact_status (Status) [artifact_id(QueueVisualisationId)]
	<- .println("Artifact 'Visualisation Command Queue' Status changed : " , Status).
	
+ queue_artifact_status (Status) [artifact_id(QueueMonitorId)]
	<- .println("Artifact 'Monitoring Command Queue' Status changed : " , Status).
	
+ queue_artifact_status (Status) [artifact_id(QueueActionId)]
	<- .println("Artifact 'Action Command Queue' Status changed : " , Status).
	


+? find_visualisation_queue(VisualisationQueueId) 
	<- lookupArtifact("roomVisualisationCommands", VisualisationQueueId).
	
-? find_visualisation_queue(VisualisationQueueId) 
	<- .wait(200);
	   ?find_visualisation_queue(VisualisationQueueId).
	   
+? find_monitor_queue(MonitorQueueId) 
	<- lookupArtifact("roomMonitorCommands", MonitorQueueId).
	
-? find_monitor_queue(MonitorQueueId) 
	<- .wait(200);
	   ?find_monitor_queue(MonitorQueueId).
	   
+? find_action_queue(ActionQueueId) 
	<- lookupArtifact("roomActionCommands", ActionQueueId).
	
-? find_action_queue(ActionQueueId) 
	<- .wait(200);
	   ?find_action_queue(ActionQueueId).
	
+? find_display(DisplayId)
	<-	lookupArtifact("display_sr", DisplayId).
	
-? find_display(DisplayId) 
	<-	.wait(200);
		?find_display(DisplayId).
	
+? find_vital_parameter_source(VitalParameterSourceId) 
	<-	lookupArtifact("activeTraumaService", VitalParameterSourceId).

-? find_vital_parameter_source(VitalParameterSourceId) 
	<-	.wait(200);
		?find_vital_parameter_source(VitalParameterSourceId).
	
+? find_tac_source(TacSourceId) 
	<-	lookupArtifact("tacPS", TacSourceId).

-? find_tac_source(TacSourceId) 
	<-	.wait(200);
		?find_tac_source(TacSourceId).
	
+? find_mock_source(MockSourceId) 
	<-	lookupArtifact("mockSource", MockSourceId).
 
-? find_mock_source(MockSourceId) 
	<-	.wait(200);
		?find_mock_source(MockSourceId).
		
+? find_timer_artifact(TimeMonitorId)
	<-  lookupArtifact("roomTimeMonitor", TimeMonitorId).
	
-? find_timer_artifact(TimeMonitorId)
	<-  .wait(200);
		?find_timer_artifact(TimeMonitorId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
