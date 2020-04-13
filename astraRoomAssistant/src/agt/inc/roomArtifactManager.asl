// Agent parameterRequestHandler in project astraRoomAssistant

/* Initial beliefs and rules */

/* Initial goals */

!create_artifact.

/* Plans */

+!create_artifact : true <- 
	!setup_display(Display_id);
	!setup_source_sample(Source_id).
	
+! setup_display(D) : true <-
	makeArtifact("shockRoomDisplay", "astraRoomAssistant.Display", [], D).
	
+!setup_source_sample(S) : true <-
	makeArtifact("sampleParameterSource", "astraRoomAssistant.Resource", [], S).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
