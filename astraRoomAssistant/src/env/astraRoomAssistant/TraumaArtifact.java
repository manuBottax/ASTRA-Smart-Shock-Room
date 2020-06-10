// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;

public class TraumaArtifact extends Artifact {
	
	private String pressureSample;
	private String saturationSample;
	private String rateSample;
	private String temperatureSample;
	
	void init() {
			
		this.pressureSample = " 80 | 120 ";
		this.saturationSample = " 98 ";
		this.rateSample = " 75 ";
		this.temperatureSample = " 36.7 °C ";
		
		System.out.println("TraumaTrackerService Artifact created");
		
	}

	@OPERATION
	void getDataValue(String type, OpFeedbackParam<String> result) {
				
		if (type.equals("blood_pressure")) {
			result.set(this.pressureSample);
		} else if (type.equals("spO2")){
			result.set(this.saturationSample);
		} else if (type.equals("heart_rate")){
			result.set(this.rateSample);
		} else if (type.equals("temperature")){
			result.set(this.temperatureSample);
		} else {
			result.set("Error");
		}

	}
}

