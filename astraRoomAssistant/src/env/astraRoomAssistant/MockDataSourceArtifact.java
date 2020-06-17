// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import java.io.IOException;

import org.json.JSONObject;

import cartago.*;
import javafx.util.Pair;
import utils.NetworkManager;

public class MockDataSourceArtifact extends Artifact {
	
	private static final String SERVICE_URL = "http://192.168.1.120:3007/api/mock_data";
	
	void init(int initialValue) {
		System.out.println("Mock Data Source Artifact created");
	}

	@OPERATION
	void getMockData(OpFeedbackParam<String> value) {	
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(SERVICE_URL);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				
				String val = json.getString("value");
				
				System.out.println(val);
				
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Mock Data");
				failed("Mock Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("Mock Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}
	}
	
	@OPERATION
	void getMockImage(OpFeedbackParam<String> path) {	
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(SERVICE_URL + "/image");
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				
				String val = json.getString("path");
				
				System.out.println(val);
				
				path.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Mock Image");
				failed("Mock Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("Mock Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}
	}	
}

