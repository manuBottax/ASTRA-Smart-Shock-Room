// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cartago.*;

public class Resource extends Artifact {
	
	private static final String SOURCE_BASE_PATH = "http://localhost:3002/api/data/123456/";
	
	void init() {
		System.out.println("Patient Parameter Sample Artifact created");
	}

	@OPERATION
	void requestData(String dataRequest) {
		
        try {
        	String path = SOURCE_BASE_PATH + dataRequest;
        	URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = "";
            String s;
            System.out.println("   ---> Output from Server .... \n");
            while (( s = br.readLine()) != null) {
                output = output.concat(s);
            }

            try {
                conn.disconnect();
                signal("resource_request_completed", output);
            } catch (Exception ex) {
                signal("resource_request_failed", ex.getCause().getMessage());
            }
	    } catch (IOException ex) {
	    	signal("resource_request_failed", ex.getCause().getMessage());
	    }
	}
}

