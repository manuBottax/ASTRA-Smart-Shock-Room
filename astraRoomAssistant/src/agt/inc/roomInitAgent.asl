// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!createArtifact.

/* Plans */

+!createArtifact
	<-  !setupCommandQueue(Queue_id);
		!setupDisplay(Display_id);
		!setupTraumaSource(Source_TT_id);
		!setupTac(Source_TAC_id);
		!setupMockSource(Source_id).
	
+! setupCommandQueue(Q)
	<- makeArtifact("roomCommands", "astraRoomAssistant.RoomCommandQueueArtifact", ["room.*", "room_commands_queue"], QueueId).	
	
+! setupDisplay(D)
	<- makeArtifact("display_sr", "astraRoomAssistant.DisplayArtifact", [], D).
	
+! setupTraumaSource(S)
	<- makeArtifact("traumaTrackerService", "astraRoomAssistant.TraumaArtifact", [], S).
	
+! setupTac(T) 
	<- makeArtifact("tacPS", "astraRoomAssistant.TACArtifact", [], T).
	
+! setupMockSource(M)
	<- makeArtifact("mockSource", "astraRoomAssistant.MockDataSourceArtifact", [], M). 

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
