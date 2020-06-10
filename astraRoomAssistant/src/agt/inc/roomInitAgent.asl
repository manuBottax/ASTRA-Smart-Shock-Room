// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!create_artifact.

/* Plans */

+!create_artifact : true <- 
	!setup_display(Display_id);
	!setup_tt_source(Source_TT_id);
	!setup_tac(Source_TAC_id);
	!setup_mock_source(Source_id).
	
+! setup_display(D) : true <-
	makeArtifact("display_sr", "astraRoomAssistant.DisplayArtifact", [], D).
	
+! setup_tt_source(S) : true <-
	makeArtifact("traumaTrackerService", "astraRoomAssistant.TraumaArtifact", [], S).
	
+! setup_tac(T) : true <-
	makeArtifact("tacPS", "astraRoomAssistant.TACArtifact", [], T).
	
+! setup_mock_source(U) : true <-
	makeArtifact("mockSource", "astraRoomAssistant.MockDataSourceArtifact", [], U). 

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
