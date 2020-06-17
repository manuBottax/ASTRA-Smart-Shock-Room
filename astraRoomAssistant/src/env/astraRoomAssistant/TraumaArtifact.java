// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import java.io.IOException;

import org.json.JSONObject;

import cartago.*;
import javafx.util.Pair;
import utils.NetworkManager;

public class TraumaArtifact extends Artifact {
	
	private static final String BASE_SERVICE_URL = "http://192.168.1.120:3005/api/data/";
	
	void init() {	
		System.out.println("TraumaService Artifact created");	
	}
	
	@OPERATION
	void getBloodPressureValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "blood_pressure";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				
				String val = json.getString("value");
				
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Blood Pressure Data");
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}		
	}
	
	@OPERATION
	void getSaturationValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "spO2";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				
				String val = json.getString("value");
				
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Saturation Data");
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}			
	}
	
	@OPERATION
	void getHeartRateValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "heart_rate";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				
				String val = json.getString("value");
				
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Heart Rate Data");
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}			
	}
	
	@OPERATION
	void getTemperatureValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "temperature";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				
				String val = json.getString("value");
				
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Temperature Data");
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}			
	}
	
}

