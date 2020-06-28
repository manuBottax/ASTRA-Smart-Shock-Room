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

public class DisplayArtifact extends Artifact {
	
	private static final String DISPLAY_SERVICE_URL = "http://192.168.1.120:3001/api/display";
	
	private static final int POLLING_TIME = 10000;
	
	void init() {
		
		defineObsProperty("display_artifact_status", ArtifactStatus.ARTIFACT_CREATED.getStatus());
		defineObsProperty("display_status", "idle");
		
		execInternalOp("monitorStatus");
		
		System.out.println("Display Artifact created");
	}
	
	@OPERATION
	void showTraumaTeamInfo (String traumaLeader, ArrayList<String> traumaTeam, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "tl_data";
		
		try {
			
			JSONObject teamData = new JSONObject();
			
			teamData.put("traumaLeader", traumaLeader);
			teamData.put("traumaTeam", traumaTeam);
			
			JSONObject data = new JSONObject();
			
			data.put("data", teamData);
			
			int res = NetworkManager.doPOST(path, data.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	} 
	
	@OPERATION
	void showPreHInfo (JSONObject data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "preH";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("name", "preH");
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showTraumaInfo (JSONObject data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "trauma_info";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("name", "Trauma Info");
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showPatientInitialConditionInfo (JSONObject data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "patient_initial_condition";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("name", "Patient Initial Condition");
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showEventList (JSONArray data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/events";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("name", "events");
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	
	@OPERATION
	void showPatientInfo (String data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "patient_data";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("name", "ID paziente");
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showBiometricData (String data, String type, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "biometric_data";
		
		try {
			
			JSONObject body = new JSONObject();
			
			String name = "";
			
			if (type.equals("blood_pressure")) {
				name = "Pressione";		
			} else if (type.equals("spO2")) {
				name = "Saturazione";
			} else if (type.equals("heart_rate")) {
				name = "Frequenza Cardiaca";
			} else if (type.equals("temperature")){
				name = "Temperatura";
			}
			
			body.put("name", name);
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
						
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showDiagnosticData (String data, String type, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "diagnostic_data";
		
		try {
			
			JSONObject body = new JSONObject();
			
			String name = "";
			
			if (type.equals("blood_tests")) {
				name = "Esami del sangue";		
			} else if (type.equals("ecg")) {
				name = "ECG";
			}
			
			body.put("name", name);
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	
	@OPERATION
	void showTAC (JSONObject data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "tac";
		
		try {

			JSONObject body = new JSONObject();
			
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showRX (String data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "rx";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showTemporalData (String data, String type, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "temporal_data";
		
		try {
			
			JSONObject body = new JSONObject();
			
			String name = "";
			
			if (type.equals("eta")) {
				name = "Tempo All'Arrivo";		
			} else if (type.equals("total_time")) {
				name = "Tempo totale";
			}
			
			body.put("name", name);
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showEnvironmentalData (String data, String type, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "env_data";
		
		try {
			
			JSONObject body = new JSONObject();
			
			String name = "";
			
			if (type.equals("used_blood_unit")) {
				name = "Sacche di sangue utilizzate";		
			}
			
			body.put("name", name);
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
						
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void showError (JSONObject error, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "error";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("name", "Errore");
			body.put("data", error);
			
			int res = NetworkManager.doPOST(path, body.toString());
						
			if (res != 201) {
				System.out.println("Error : cannot complete display");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	@OPERATION
	void clearSection (String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "clear";
		
		try {
						
			int res = NetworkManager.doPOST(path, new JSONObject().toString());
						
			if (res != 201) {
				System.out.println("Error : cannot complete display operation");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("command display failed", "service error", "failed_display_clear", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("command display failed", "I/O error", "failed_display_clear", "IOException");
		}
	}
	
	@OPERATION
	void setDisplayStatus (String status) {
		
		String path = DISPLAY_SERVICE_URL + "/status" ;
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("status", status);
			
			int res = NetworkManager.doPUT(path, body.toString());
						
			if (res != 200) {
				System.out.println("Error : cannot update Display Status");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("display status failed", "service error", "failed_status_update", res );
			}
			
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("display status failed", "I/O error", "failed_status_update", "IOException");
		}
	}
	
	@INTERNAL_OPERATION
	void monitorStatus() {
		
		ObsProperty status = getObsProperty("display_status");
		
		String path = DISPLAY_SERVICE_URL + "/status" ;
		
		while(true) {

            try { 
    			
    			Pair<Integer, String> res = NetworkManager.doGET(path);		
    			
    			if (res.getKey() == 200) {	
    				    				
    				JSONObject json = new JSONObject(res.getValue());    				
    				String st = json.getString("status");    				
    				status.updateValue(st);
    				
    			} else {
    				System.out.println("Error : Cannot GET DISPLAY STATUS");
    				status.updateValue("unavailable");
    				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
    			} 
            	
            } catch (IOException ex){
                //ex.printStackTrace();
                System.out.println("Error : Cannot GET DISPLAY STATUS");
				status.updateValue("unavailable");
				getObsProperty("display_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
            }
            
            await_time(POLLING_TIME);
            
        }
		
	}
}

