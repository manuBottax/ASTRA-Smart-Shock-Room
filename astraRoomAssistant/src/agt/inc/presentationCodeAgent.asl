	
+ last_pending_command(Command) 
	<-	-+current_command(Command);
		acceptCommand [artifact_id(QueueId)].
	
+ work_accepted(Command, Id, Type, DataType, Target, Position) 
	<-	!processCommand(Id, Type, DataType, Target, Position).

+ work_refused(Id, Error) : current_command(Command)
	<-	setErrorOnCommand(Command) [artifact_id(QueueId)].
	
+! processCommand(CommandId, "visualisation", DataType, Target, Position) 
	<-	.send(roomMonitoringAgent, tell, visualise_on(Position));
		!requestData(CommandId, DataType, Target, Position).
	
+! requestData(CommandId, "blood_pressure", Target, Position)  
	<-	getBloodPressureValue(Value) [artifact_id(VitalParameterSourceId)];
		!displayData(CommandId, "blood_pressure", Value, Target, Position).
		
	// ....
		
+! displayData(CommandId, DataType, Value, Target, Position) : DataType = "blood_pressure" | DataType = "spO2" 
			| DataType = "heart_rate" | DataType = "temperature"
			
	<-	showBiometricData(Value, DataType, Position) [artifact_id(Target)]
		! completeCommand.		
		
	// ....	
	
+! completeCommand : current_command(Command)
	<- 	completeCommand(Command) [artifact_id(QueueId)].