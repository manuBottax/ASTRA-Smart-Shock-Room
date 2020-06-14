// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import java.io.IOException;

import org.json.JSONObject;

import cartago.*;
import utils.NetworkManager;

public class DisplayArtifact extends Artifact {
	
	private static final String DISPLAY_SERVICE_URL = "http://192.168.1.120:3001/api/display";
	
	void init() {
		System.out.println("Display Artifact created");
	}

	@OPERATION
	void printData(String type, String data, String position) {
		
		try {
			
			String path = DISPLAY_SERVICE_URL + "/" + position;
						
			System.out.println(path);
			
			JSONObject body = new JSONObject();
			
			body.put("type", type);
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : Cannot update command");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
    
	}
		
}

