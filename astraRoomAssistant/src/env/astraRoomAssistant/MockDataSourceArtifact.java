// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;

public class MockDataSourceArtifact extends Artifact {
	
	void init(int initialValue) {
		System.out.println("Mock Data Source Artifact created");
	}

	@OPERATION
	void getMockData(OpFeedbackParam<String> value) {	
		value.set("Sample Data");
	}
}

