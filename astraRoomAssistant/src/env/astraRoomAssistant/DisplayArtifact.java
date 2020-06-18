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
	void showPatientInfo (String data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "patient_data";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("name", "ID paziente");
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : cannot complete display");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
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
						
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : cannot complete display");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
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
			
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : cannot complete display");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}
	
	
	@OPERATION
	void showTAC (String data, String position) {
		
		String path = DISPLAY_SERVICE_URL + "/" + position + "/" + "tac";
		
		try {
			
			JSONObject body = new JSONObject();
			
			body.put("data", data);
			
			int res = NetworkManager.doPOST(path, body.toString());
			
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : cannot complete display");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
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
			
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : cannot complete display");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
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
			
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : cannot complete display");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
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
			
			System.out.println(res);
			
			if (res == 201) {	
				
				signal("completed_display");
				
			} else {
				System.out.println("Error : cannot complete display");
				failed("command display failed", "service error", "failed_display", res );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("command display failed", "I/O error", "failed_display", "IOException");
		}
	}		
}

