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

public class ActiveTraumaArtifact extends Artifact {
	
	private static final String BASE_SERVICE_URL = "http://192.168.1.120:3005/api/trauma/";
	private static final int POLLING_TIME = 10000;
	
	private String currentTraumaID = "";
	
	void init(String traumaID) {
		
		this.currentTraumaID = traumaID;
		
		
		defineObsProperty("trauma_status", "unavailable");
		defineObsProperty("trauma_artifact_status", ArtifactStatus.SERVICE_CONNECTED.getStatus());
		
		execInternalOp("monitorTraumaStatus");
	}
	
	/* //POST Trauma
	@OPERATION
	void createNewTrauma() {
		
		try { 
			
			JSONObject data = new JSONObject();
			JSONObject delayedActivationData = new JSONObject();
			
			delayedActivationData.put("isDelayedActivation", true);
			delayedActivationData.put("originalAccessDate", "2020-06-24");
			delayedActivationData.put("originalAccessTime", "18:38:27");
			
			String[] ttMembers = new String[] {"anestesista","medico ps", "neurochirurgo"};
			
			data.put("startOperatorId", "m_1234");
			data.put("startOperatorDescription" , "Emiliano Gamberini");
			data.put("traumaTeamMembers", ttMembers);
			data.put("delayedActivation", delayedActivationData);
			
			Pair<Integer, String> res = NetworkManager.doPOSTWithResponse(BASE_SERVICE_URL, data.toString());		
			
			if (res.getKey() == 201) {	
				System.out.println("Trauma Created !");
				System.out.println(res.getValue());
				    				
				//JSONObject json = new JSONObject(res.getValue());
				
				//this.currentTraumaID = json.getString("_id");
				
				this.currentTraumaID = res.getValue();
				
				System.out.println("Trauma ID setted : " + this.currentTraumaID);
				
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
		
	} */
	
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
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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
			} 
        	
        } catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Error : Cannot Reach Trauma Service");
			getObsProperty("trauma_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
        }
	}
	
	@INTERNAL_OPERATION
	void monitorTraumaStatus() {
			
		ObsProperty status = getObsProperty("trauma_status");
		
		String requestPath = BASE_SERVICE_URL + this.currentTraumaID + "/trauma_current_status";
		
		//System.out.println("path : " + requestPath);
		
		while(true) {
	
	        try { 
				
				Pair<Integer, String> res = NetworkManager.doGET(requestPath);		
				
				if (res.getKey() == 200) {	
					    				
					JSONObject json = new JSONObject(res.getValue());
					
					String st = json.getString("trauma_current_status");
					
					//System.out.println("Trauma Status : " + st) ;
					
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


/*
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
*/

