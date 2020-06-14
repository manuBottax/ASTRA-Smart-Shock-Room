// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;

public class TACArtifact extends Artifact {
	
	void init() {
		System.out.println("TAC Artifact created");
	}
	
	@OPERATION
	void getTACData(OpFeedbackParam<String> value) {			
		value.set("This is a TAC Sample");
	}
	
}

