// Agent roomMonitoringAgent in project astraRoomAssistant

/* Initial beliefs and rules */

current_patient("123459").

/* Initial goals */

!observe.

/* Plans */

+! observe : true 
	<-	?find_queue(Queue)
		?find_display(Display)
		?find_active_trauma(TraumaSource)
		?find_tac_source(TacSource)
		?find_mock_source(MockSource)
		?find_timer_artifact(TimeMonitor)
	
		focus(Queue)
		focus(TraumaSource)
		focus(TimeMonitor)
		focus(Display).

+ last_pending_command(Command) 
	<-	-+current_command(Command);
		acceptCommand [artifact_id(QueueId)].
	
+ accepted_work(Command, Id, Type, DataType, Target, Position) 
	<-	.println("Command Accepted : " , Id);
		!processCommand(Id, Type, DataType, Target, Position).

+ failed_update(Id, Error) : current_command(Command)
	<-	.println("Error ( " , Error , " ) on command update");
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
+! processCommand(CommandId, "monitoring", DataType, Target, Position)
	<-	.println("Working on Command ", CommandId);
		.println("Want to monitor ", DataType);
		!monitorData(CommandId, DataType, Target, Position).
		
/* ---------- Biometric Data Monitoring ------------------------ */

+! monitorData(CommandId, "blood_pressure", Target, Position) 
	<-  .println("Monitoring")
		monitorBloodPressure(CommandId, Target, Position) [artifact_id(TraumaSourceId)]
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").

+! monitorData(CommandId, "spO2", Target, Position) 
	<-  .println("Monitoring")
		monitorSaturation(CommandId, Target, Position) [artifact_id(TraumaSourceId)]
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		
+! monitorData(CommandId, "heart_rate", Target, Position) 
	<-  .println("Monitoring")
		monitorHeartRate(CommandId, Target, Position) [artifact_id(TraumaSourceId)]
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		
+! monitorData(CommandId, "temperature", Target, Position) 
	<-  .println("Monitoring")
		monitorTemperature(CommandId, Target, Position) [artifact_id(TraumaSourceId)]
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		
// TODO: è da implementare il monitoraggio di CO2, EGA e ROTEM che al momento sono disabilitati per motivi di test
		
/* ---------- Time Monitoring ------------------------ */
		
+! monitorData(CommandId, "eta", Target, Position) 
	<-  .println("Monitoring")
		monitorETA(CommandId, Target, Position) [artifact_id(TimeMonitor)]
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		
+! monitorData(CommandId, "total_time", Target, Position) 
	<-  .println("Monitoring")
		monitorTotalTime(CommandId, Target, Position) [artifact_id(TimeMonitor)]
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		

		
-! monitorData(CommandId, DataType, Target, Position) : current_command(Command)
	<-  .println("Cannot complete Monitoring")
		/* TODO : Stop monitoring in base al tipo */
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
/* -------------------------------------------------- */

+ new_monitoring_value(CommandId, Value, DataType, Target, Position) : DataType = "blood_pressure" | DataType = "spO2" | DataType = "heart_rate" | DataType = "temperature"
	<-  showBiometricData(Value, DataType, Position) [artifact_id(Target)].
	
+ new_monitoring_value(CommandId, Value, DataType, Target, Position) : DataType = "eta" | DataType = "total_time"
	<-  showTemporalData(Value, DataType, Position) [artifact_id(Target)].
		
+? find_queue(QueueId) 
	<- lookupArtifact("roomMonitorCommands", QueueId).
	
-? find_queue(QueueId) 
	<- .wait(200);
	   ?find_queue(QueueId).
	
+? find_display(DisplayId)
	<-	lookupArtifact("display_sr", DisplayId).
	
-? find_display(DisplayId) 
	<-	.wait(200);
		?find_display(DisplayId).
	
+? find_active_trauma(TraumaSourceId) 
	<-	lookupArtifact("activeTraumaService", TraumaSourceId).

-? find_active_trauma(TraumaSourceId) 
	<-	.wait(200);
		?find_active_trauma(TraumaSourceId).
	
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
