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
	void printData(String type, String data, String position, OpFeedbackParam<String> result) {
		
		try {
			
			String path = DISPLAY_SERVICE_URL + "/" + position;
			
			/* String path = DISPLAY_SERVICE_URL + "/" + position + "/" + data; */
			
			System.out.println(path);
			
			JSONObject body = new JSONObject();
			
			body.put("type", type);
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res == 201) {	
				
				result.set("OK");
				
			} else {
				System.out.println("Error : Cannot visualise data on display");
				result.set("Error");
			}
		} catch (IOException ex) {
            ex.printStackTrace();
            signal("display_request_failed", ex.getCause().getMessage());
        }
    
	}
		
		/*
		
        try {

        URL url = new URL(DISPLAY_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
           System.out.println("Some Error Occurred ! ");
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output = "";
        String s;
        while (( s = br.readLine()) != null) {
            output = output.concat(s);
        }

        try {
        	signal("display_request_completed", output);
            conn.disconnect();

        } catch (Exception ex){
            System.err.println(ex.getCause().getMessage());
            signal("display_request_failed", ex.getCause().getMessage());
            conn.disconnect();
        }

    } catch (IOException ex) {
            ex.printStackTrace();
            signal("display_request_failed", ex.getCause().getMessage());
        }
    } */
		
}

