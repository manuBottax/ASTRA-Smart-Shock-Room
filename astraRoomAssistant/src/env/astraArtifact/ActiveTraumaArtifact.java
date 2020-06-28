// CArtAgO artifact code for project astraRoomAssistant

package astraArtifact;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cartago.*;
import javafx.util.Pair;
import utils.ArtifactStatus;
import utils.NetworkManager;
import utils.WebSocketHandler;

public class ActiveTraumaArtifact extends Artifact {
	
	private static final String BASE_SERVICE_URL = "http://192.168.1.120:3005/api/trauma/";
	private static final int POLLING_TIME = 10000;
	
	private boolean handlingTrauma = false;
	
	private String currentTraumaID = "";
	
	void init() {
		
	    WebSocketHandler ws;
		
		ws = new WebSocketHandler("new_trauma" , "http://192.168.1.120:3005");
		
		ws.addMessageHandler(new WebSocketHandler.MessageHandler() {

			public void receivedMessage(JSONObject message) {

				if (! handlingTrauma) {
					System.out.println("New Trauma Created");
					System.out.println("trauma id : " +  message.getString("trauma_id"));
					handlingTrauma = true;
					currentTraumaID = message.getString("trauma_id");
					execInternalOp("monitorTraumaStatus");
				}
			}

		});
		
		defineObsProperty("trauma_status", "unavailable");
		defineObsProperty("trauma_artifact_status", ArtifactStatus.SERVICE_CONNECTED.getStatus());
		
	}
	
	@OPERATION
	void getTraumaTeam(OpFeedbackParam<String> traumaLeader, OpFeedbackParam<ArrayList<String>> traumaTeam) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/trauma_team";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
			
			if (res.getKey() == 200) {	
				    				
				JSONObject json = new JSONObject(res.getValue());
				
				//System.out.println(json);
				
				String teamLeader = json.getString("traumaLeader") ;
				
				JSONArray t = json.getJSONArray("traumaTeamMembers");
				
				ArrayList<String> team = new ArrayList<String>();
				
				for(int i = 0; i < t.length(); i++) {
					team.add((String) t.get(i));
					//System.out.println("Team Members : " + team);
				}
				
				//System.out.println("Team Leader : " + teamLeader);
				
				//System.out.println("Team Members : " + team);
                
				traumaLeader.set(teamLeader);
				traumaTeam.set(team);
				
			} else {
				System.out.println("Error : Cannot GET Trauma Team Info");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Trauma Data retrieve failed", "service error", "failed_trauma_data_retrieve" );
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Trauma Data retrieve failed", "unavailable service", "failed_trauma_data_retrieve" );
        }
		
	}
	
	@OPERATION 
	void getPreHInfo(OpFeedbackParam<JSONObject> preHInfo) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/preH";
		
		try { 
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
			
			if (res.getKey() == 200) {
				    				
				JSONObject json = new JSONObject(res.getValue());
				
				preHInfo.set(json);
				
			} else {
				System.out.println("Error : Cannot GET Patient preH");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Trauma Data retrieve failed", "service error", "failed_trauma_data_retrieve" );
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Trauma Data retrieve failed", "unavailable service", "failed_trauma_data_retrieve" );
        }
	}
	
	@OPERATION
	void getTraumaInfo(OpFeedbackParam<JSONObject> traumaInfo) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/trauma_info";
		
		try { 
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
			
			if (res.getKey() == 200) {
				    				
				JSONObject json = new JSONObject(res.getValue());
				
				traumaInfo.set(json);
				
			} else {
				System.out.println("Error : Cannot GET Trauma Info");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Trauma Data retrieve failed", "service error", "failed_trauma_data_retrieve" );
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Trauma Data retrieve failed", "unavailable service", "failed_trauma_data_retrieve" );
        }
	}
	
	@OPERATION
	void getPatientInitialCondition(OpFeedbackParam<JSONObject> patientCondition) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/patient_initial_condition";
		
		try { 
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
			
			if (res.getKey() == 200) {
				    				
				JSONObject json = new JSONObject(res.getValue());
				
				patientCondition.set(json);
				
			} else {
				System.out.println("Error : Cannot GET Patient Initial Condition");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Trauma Data retrieve failed", "service error", "failed_trauma_data_retrieve" );
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Trauma Data retrieve failed", "unavailable service", "failed_trauma_data_retrieve" );
        }
	}
	
	@OPERATION
	void getEventList(OpFeedbackParam<JSONArray> eventList) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/events";
		
		try { 
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
			
			if (res.getKey() == 200) {
				    				
				JSONArray json = new JSONArray(res.getValue());
				
				eventList.set(json);
				
			} else {
				System.out.println("Error : Cannot GET Trauma Event list");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Trauma Data retrieve failed", "service error", "failed_trauma_data_retrieve" );
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Trauma Data retrieve failed", "unavailable service", "failed_trauma_data_retrieve" );
        }
	}
	
	@OPERATION
	void getEvent(String eventID, OpFeedbackParam<JSONObject> event) {
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/events/" + eventID;
		
		try { 
			
			Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
			
			if (res.getKey() == 200) {
				    				
				JSONObject json = new JSONObject(res.getValue());
				
				event.set(json);
				
			} else {
				System.out.println("Error : Cannot GET Trauma Event # " + eventID );
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Trauma Data retrieve failed", "service error", "failed_trauma_data_retrieve" );
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Trauma Data retrieve failed", "unavailable service", "failed_trauma_data_retrieve" );
        }
	}
	
	@OPERATION
	void completeTraumaHandling() {
		this.handlingTrauma = false;
		this.currentTraumaID = "";
		System.out.println("Trauma Handling Completed. Waiting for new Trauma");
	}
	
	@INTERNAL_OPERATION
	void monitorTraumaStatus() {
			
		ObsProperty status = getObsProperty("trauma_status");
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/trauma_current_status";
		
		while(this.handlingTrauma) {

	        try { 
				
				Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
				
				if (res.getKey() == 200) {	
					    				
					JSONObject json = new JSONObject(res.getValue());
					
					String st = json.getString("trauma_current_status");
					
					String old = (String) status.getValue();
					
					if (! old.equals(st)) {
						status.updateValue(st);
					}
					
				} else {
					System.out.println("Error : Cannot GET Patient STATUS");
					status.updateValue("unavailable");
					getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
					failed("Trauma Data retrieve failed", "service error", "failed_trauma_data_retrieve" );
				} 
	        	
	        } catch (IOException ex){
	            //ex.printStackTrace();
	            System.out.println("Error : Cannot Reach Trauma Service");
				status.updateValue("unavailable");
				getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
				failed("Trauma Data retrieve failed", "unavailable service", "failed_trauma_data_retrieve" );
	        }
	        
	        await_time(POLLING_TIME);
	        
	    }
	}
}