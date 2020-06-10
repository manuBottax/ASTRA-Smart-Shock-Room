// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;

public class TraumaTrackerArtifact extends Artifact {
	
	private String ecgSample;
	
	void init() {
			
		this.ecgSample = "42";
		
		System.out.println("TraumaTrackerService Artifact created");
		
	}

	@OPERATION
	void getDataValue(String type, OpFeedbackParam<String> result) {
				
		if (type.equals("ecg")) {
			result.set(this.ecgSample);
		} else {
			result.set("6523986519523689");
		}

	}
}

