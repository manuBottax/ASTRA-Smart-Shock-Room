// Agent roomMonitoringAgent in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+! observe : true 
	<-	?find_queue(Queue)
		?find_display(Display)
		?find_vital_parameter_source(VitalParameterSource)
		?find_tac_source(TacSource)
		?find_mock_source(MockSource)
		?find_timer_artifact(TimeMonitor)
	
		focus(Queue)
		focus(VitalParameterSource)
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
	
+! processCommand(CommandId, "monitoring", DataType, Target, Position) : current_command(Command) & use_position (Position, Type)
	<-	.println("Working on Command ", CommandId);
		.println("Want to monitor ", DataType);
		!freePosition(Position, Type);
		+use_position(Position, DataType);
		!monitorData(Command, DataType, Target, Position).
		
+! processCommand(CommandId, "monitoring", DataType, Target, Position) : current_command(Command)
	<-	.println("Working on Command ", CommandId);
		.println("Want to monitor ", DataType);
		+use_position(Position, DataType);
		!monitorData(Command, DataType, Target, Position).
/* ---------- Biometric Data Monitoring ------------------------ */

+! monitorData(Command, "blood_pressure", Target, Position) 
	<-  .println("Monitoring Blood Pressure");
		+monitoring("blood_pressure");
		!monitorBloodPressure(Target, Position);
		completeCommand(Command) [artifact_id(QueueId)];
		.println("Command Handling completed, waiting for a new command.").
		
+! monitorData(Command, "spO2", Target, Position) 
	<-  .println("Monitoring spO2");
		+monitoring("spO2");
		!monitorSaturation(Target, Position);
		completeCommand(Command) [artifact_id(QueueId)];
		.println("Command Handling completed, waiting for a new command.").
		
+! monitorData(Command, "heart_rate", Target, Position) 
	<-  .println("Monitoring Heart Rate");
		+monitoring("heart_rate");
		!monitorHeartRate(Target, Position)
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		
+! monitorData(Command, "temperature", Target, Position) 
	<-  .println("Monitoring");
		+monitoring("temperature");
		!monitorTemperature(Target, Position)
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").

// ---------- Blood Pressure
	
+! monitorBloodPressure(Target, Position) : monitoring("blood_pressure")
	<- 	getBloodPressureValue(Value) [artifact_id(VitalParameterId)];
		showBiometricData(Value, "blood_pressure", Position) [artifact_id(Target)];
		.wait(1000);
		!! monitorBloodPressure (Target, Position).
		
+! stopMonitoringBloodPressure(Position)
	<-  -monitoring("blood_pressure");
		-use_position(Position, _);
		.println("Stop monitoring blood pressure").
	
-! monitorBloodPressure(Target, Position)
	<- 	!stopMonitoringBloodPressure(Position).
	
// ----------

// ---------- spO2
		
+! monitorSaturation(Target, Position) : monitoring("spO2")
	<- 	getSaturationValue(Value) [artifact_id(VitalParameterId)];
		showBiometricData(Value, "spO2", Position) [artifact_id(Target)];
		.wait(1000);
		!! monitorSaturation (Target, Position).
		
+! stopMonitoringSaturation(Position)
	<- 	-monitoring("spO2");
		-use_position(Position, _);
		.println("Stop monitoring saturation").
	
-! monitorSaturation(Target, Position)
	<- 	!stopMonitoringSaturation(Position).
	
// ----------

// ---------- heart rate
		
+! monitorHeartRate(Target, Position) : monitoring("heart_rate")
	<- 	getHeartRateValue(Value) [artifact_id(VitalParameterId)];
		showBiometricData(Value, "heart_rate", Position) [artifact_id(Target)];
		.wait(1000);
		!! monitorHeartRate (Target, Position).
		
+! stopMonitoringHeartRate(Position)
	<- 	-monitoring("heart_rate");
		-use_position(Position, _);
		.println("Stop monitoring heart rate").
	
-! monitorHeartRate(Target, Position)
	<- 	!stopMonitoringHeartRate(Position).
	
// ----------

// ---------- temperature
		
+! monitorTemperature(Target, Position) : monitoring("temperature")
	<- 	getTemperatureValue(Value) [artifact_id(VitalParameterId)];
		showBiometricData(Value, "temperature", Position) [artifact_id(Target)];
		.wait(1000);
		!! monitorTemperature (Target, Position).
		
+! stopMonitoringTemperature(Position)
	<- 	-monitoring("temperature");
		-use_position(Position, _);
		.println("Stop monitoring temperature").
	
-! monitorTemperature(Target, Position)
	<- 	!stopMonitoringTemperature(Position).
	
// ----------
		
// TODO: � da implementare il monitoraggio di CO2, EGA e ROTEM che al momento sono disabilitati per motivi di test
		
/* ---------- Time Monitoring ------------------------ */
		
+! monitorData(Command, "eta", Target, Position) 
	<-  .println("Monitoring ETA");
		+monitoring("eta");
		! monitorETA(Target, Position)
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		
+! monitorData(CommandId, "total_time", Target, Position) 
	<-  .println("Monitoring elapsed time");
		+monitoring("total_time");
		! monitorTotalTime(CommandId, Target, Position) [artifact_id(TimeMonitor)]
		completeCommand(Command) [artifact_id(QueueId)]
		.println("Command Handling completed, waiting for a new command.").
		
// ---------- eta
		
+! monitorETA(Target, Position) : monitoring("eta")
	<- 	getTimeToETA(Value) [artifact_id(TimeMonitorId)];
		showTemporalData(Value, "eta", Position) [artifact_id(Target)];
		.wait(1000);
		!! monitorETA (Target, Position).
		
+! stopMonitoringETA(Position)
	<- 	-monitoring("eta");
		-use_position(Position, _);
		.println("Stop monitoring ETA").
	
-! monitorETA(Target, Position)
	<- 	!stopMonitoringETA(Position).
	
// ----------

// ---------- total_time
		
+! monitorTotalTime(Target, Position) : monitoring("total_time")
	<- 	getTimeFromArrive(Value) [artifact_id(VitalParameterId)];
		showTemporalData(Value, "total_time", Position) [artifact_id(Target)];
		.wait(1000);
		!! monitorTotalTime (Target, Position).
		
+! stopMonitoringTotalTime(Position)
	<- 	-monitoring("total_time");
		-use_position(Position, _);
		.println("Stop monitoring total time").
	
-! monitorTotalTime(Target, Position)
	<- 	!stopMonitoringTotalTime(Position).
	
// ----------
		
-! monitorData(CommandId, DataType, Target, Position) : current_command(Command)
	<-  .println("Cannot complete Monitoring")
		setErrorOnCommand(Command) [artifact_id(QueueId)].
		
/* -------------------------------------------------- */

+ visualise_on(Position) : use_position (Position, Type)
	<-	.println("Received message from Visualisation Agent. Want to free position " , Position);
		.println("Position ", Position , " used by " , Type)
		! freePosition(Position, Type);
		-visualise_on(Position).
		
+ visualise_on(Position)
	<-	.println("Received message from Visualisation Agent. Want to free position " , Position);
		.println("Position is free");
		-visualise_on(Position).
		
+! freePosition(Position, "blood_pressure")
	<- 	! stopMonitoringBloodPressure(Position).
	
+! freePosition(Position, "spO2")
	<- 	! stopMonitoringSaturation(Position).
	
+! freePosition(Position, "heart_rate")
	<- 	! stopMonitoringHeartRate(Position).
	
+! freePosition(Position, "temperature")
	<- 	! stopMonitoringTemperature(Position).
	
+! freePosition(Position, "eta")
	<- 	! stopMonitorinETA(Position).
	
+! freePosition(Position, "total_time")
	<- 	! stopMonitoringTotalTime(Position).
	
-! freePosition(Position, DataType)
	<- 	.println("Position not in use").

/* -------------------------------------------------- */
		
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
	
+? find_vital_parameter_source(VitalParameterId) 
	<-	lookupArtifact("vitalParameterMonitor", VitalParameterId).

-? find_vital_parameter_source(VitalParameterId) 
	<-	.wait(200);
		?find_vital_parameter_source(VitalParameterId).
	
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
