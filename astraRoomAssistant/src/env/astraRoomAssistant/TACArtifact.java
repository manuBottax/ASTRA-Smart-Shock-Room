// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import cartago.*;
import javafx.util.Pair;
import utils.NetworkManager;

public class TACArtifact extends Artifact {
	
	private static final String BASE_TAC_SERVICE_URL = "http://192.168.1.120:3003/api";
	private static final int POLLING_TIME = 1000;
	
	void init() {
		System.out.println("TAC Artifact created");
		defineObsProperty("tac_status", "unavailable");
		
		execInternalOp("monitorStatus");
	}
	
	/**
	 * Get the current status of the TAC Machine. 
	 * 
	 * @param status - the status of the TAC. (output value) 
	 */
	
	@OPERATION
	void getTACStatus(OpFeedbackParam<String> status) {	
		ObsProperty tac = getObsProperty("tac_status");
		
		status.set((String) tac.getValue());
	}
	
	/**
	 * Get the TAC result for the specified patient. return the path to retrieve the result. 
	 * 
	 * @param patient_id - the id of the patient that take the exam. 
	 * @param path - the path where the exam result can be retrieved. (output value) 
	 */
	@OPERATION
	void getTACData(String patient_id, OpFeedbackParam<String> path) {	
		
		try {
			
			String requestPath = BASE_TAC_SERVICE_URL + "/tac_data/" + patient_id;
			
			System.out.println("Searching data at : " + requestPath);
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);
			
			if (res.getKey() == 200) {	
				
				System.out.println(res.getValue());
				
				JSONArray json = new JSONArray(res.getValue());
				
				if (! json.isEmpty()) {
				
					String tacPath = json.getJSONObject(0).getString("path");
					path.set(tacPath);
				
				} else {
					System.out.println("Error : TAC unavailable for that patient");
					failed("TAC retrieve failed", "not found error", "failed_data_retrieve" );
				}
				
			} else {
				System.out.println("Error : Cannot GET TAC");
				failed("TAC retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("TAC retrieve failed", "I/O error", "failed_data_retrieve" );
		}
	}
	
	@INTERNAL_OPERATION
	void monitorStatus() {
		
		ObsProperty status = getObsProperty("tac_status");
		String requestPath = BASE_TAC_SERVICE_URL + "/status";
		
		//System.out.println("path : " + requestPath);
		
		while(true) {

            try { 
    			
    			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
    			
    			if (res.getKey() == 200) {	
    				    				
    				JSONObject json = new JSONObject(res.getValue());
    				
    				String st = json.getString("status");
    				
    				//System.out.println("Polling for status -> OK ( " + st + " )");
    				
    				status.updateValue(st);
    				
    			} else {
    				System.out.println("Error : Cannot GET TAC STATUS");
    				status.updateValue("error");
    			} 
            	
            } catch (IOException ex){
                //ex.printStackTrace();
                System.out.println("Error : Cannot GET TAC STATUS");
				status.updateValue("error");
            }
            
            await_time(POLLING_TIME);
            
        }
		
	}
	
}

