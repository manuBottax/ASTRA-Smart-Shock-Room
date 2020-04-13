// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!observe.

/* Plans */

+!observe : true <- 
	?find_queue(Queue)
	?find_display(Display)
	?find_source(Source)
	
	focus(Queue)
	focus(Display)
	focus(Source).
	
+new_data_request(Data) : Data = "bpm" <- 
	println("perceived a bpm request");
	requestData("bpm") [artifact_id(SourceId)].
	
+new_data_request(Data) : Data = "blood_pressure_max" <- 
	println("perceived a pressure_max request");
	requestData("blood_pressure_max") [artifact_id(SourceId)].
	
+new_data_request(Data) : Data = "all" <- 
	println("perceived a all parameter request")
	requestData("") [artifact_id(SourceId)].

+resource_request_completed(Output) : true <-
	!displayData(Output);
	println("Data request completed : " , Output).

+resource_request_failed(Error) : true <-
	println("Data request failed : " , Error).
	
+! displayData(M) : true <-
	requestDisplay(M) [artifact_id(DisplayId)].
	
+display_request_completed(Output) : true <-
	println("Display request completed : " , Output).

+display_request_failed(Error) : true <-
	println("Display request failed : " , Error).	
	
	//this agent handle command on data parameter
+? find_queue(QueueId) : true <-
	lookupArtifact("dataCommand", QueueId).
	
	//if the queue for this type of command did not already exist the agent will create a new one
-? find_queue(QueueId) : true <-
	.wait(200);
	!setup_queue(QueueId).
	
+! setup_queue(Q) : true <-
	makeArtifact("dataCommand", "astraRoomAssistant.CommandQueue", ["data_retriever.*", "data_retriever_commands"], Q);
	enableCommand[artifact_id(Q)];
	?find_queue(QueueId).
	
+? find_display(DisplayId) : true <-
	lookupArtifact("shockRoomDisplay", DisplayId).
	
-? find_display(DisplayId) : true <-
	.wait(200);
	?find_display(DisplayId).
	
+?find_source(SourceId) : true <-
	lookupArtifact("sampleParameterSource", SourceId).

-?find_source(SourceId) : true <-
	.wait(200);
	?find_source(SourceId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
