// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Display extends Artifact {
	
	private static final String DISPLAY_URL = "http://localhost:3001/data";
	
	void init() {
		System.out.println("Display Artifact created");
	}

	@OPERATION
	void requestDisplay(String data, String position) {
		
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
    }
		
}

