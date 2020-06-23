// CArtAgO artifact code for project astraRoomAssistant

package astraArtifact;

import java.io.IOException;

import org.json.JSONObject;

import cartago.*;
import javafx.util.Pair;
import utils.ArtifactStatus;
import utils.NetworkManager;

public class ActiveTraumaArtifact extends Artifact {
	
	private static final String BASE_SERVICE_URL = "http://192.168.1.120:3005/api/trauma/";
	private static final int POLLING_TIME = 10000;
	
	private String currentTraumaID = "";
	
	void init() {
		defineObsProperty("patient_status", "unavailable");
		defineObsProperty("trauma_artifact_status", ArtifactStatus.SERVICE_CONNECTED.getStatus());
		
		try { 
			
			Pair<Integer, String> res = NetworkManager.doPOSTWithResponse(BASE_SERVICE_URL, "");		
			
			if (res.getKey() == 201) {	
				System.out.println("Trauma Created !");
				System.out.println(res.getValue());
				    				
				JSONObject json = new JSONObject(res.getValue());
				
				this.currentTraumaID = json.getString("_id");
				
			} else {
				System.out.println("Error : Trauma Service Error");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
        }
		
		System.out.println("Active Trauma Artifact Created !");
		
		execInternalOp("monitorPatientStatus");
	}
	
	@OPERATION
	void addTraumaLeader(String name) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/trauma_team/element";
		
		try { 
			
			JSONObject data = new JSONObject();
			data.put("name", name);
			data.put("role", "team leader");
			
			Integer res = NetworkManager.doPOST(requestPath, "{\"trauma_team_element\" : " + data  + "}".toString());		
			
			if (res != 201) {	
				System.out.println("Error : Trauma Service Error");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
        }
	}
	
	@OPERATION
	void addTraumaTeamMember(String name) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/trauma_team/element";
		
		try { 
			
			JSONObject data = new JSONObject();
			data.put("name", name);
			data.put("role", "team member");
			
			Integer res = NetworkManager.doPOST(requestPath, "{\"trauma_team_element\" : " + data  + "}".toString());		
			
			if (res != 201) {	
				System.out.println("Error : Trauma Service Error");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
        }
	}
	
	@OPERATION 
	void getPreHInfo(OpFeedbackParam<String> preHInfo) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/preH";
		
		try { 
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
			
			if (res.getKey() == 200) {	
				    				
				JSONObject json = new JSONObject(res.getValue());
				
				preHInfo.set(json.toString());
				
			} else {
				System.out.println("Error : Cannot GET Patient preH");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
        }
	}

	@INTERNAL_OPERATION
	void monitorPatientStatus() {
			
		ObsProperty status = getObsProperty("patient_status");
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/patient_status";
		
		//System.out.println("path : " + requestPath);
		
		while(true) {
	
	        try { 
				
				Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
				
				if (res.getKey() == 200) {	
					    				
					JSONObject json = new JSONObject(res.getValue());
					
					String st = json.getString("patient_status");
					
					String old = (String) status.getValue();
					
					if (! old.equals(st)) {
						status.updateValue(st);
					}
					
				} else {
					System.out.println("Error : Cannot GET Patient STATUS");
					status.updateValue("unavailable");
					getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				} 
	        	
	        } catch (IOException ex){
	            //ex.printStackTrace();
	            System.out.println("Error : Cannot Reach Trauma Service");
				status.updateValue("unavailable");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
	        }
	        
	        await_time(POLLING_TIME);
	        
	    }
	}
}

