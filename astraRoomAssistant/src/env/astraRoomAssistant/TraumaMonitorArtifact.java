// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import java.io.IOException;

import org.json.JSONObject;

import cartago.*;
import javafx.util.Pair;
import utils.NetworkManager;

public class TraumaMonitorArtifact extends Artifact {
	
	private static final String BASE_SERVICE_URL = "http://192.168.1.120:3005/api/data/";
	
	private boolean pressureMonitoring = false;
	private boolean saturationMonitoring = false;
	private boolean heartRateMonitoring = false;
	private boolean temperatureMonitoring = false;
	
	
	void init() {
		defineObsProperty("blood_pressure", "0");
		defineObsProperty("spO2", "0");
		defineObsProperty("heart_rate", "0");
		defineObsProperty("temperature", "0");
	}

	@OPERATION
	void monitorBloodPressure(String id, String target, String position) {
		
		this.pressureMonitoring = true;
		int tickTime = 5000;
		
		while(this.pressureMonitoring) {
			
			String path = BASE_SERVICE_URL + "blood_pressure";
			
			try {
				
				await_time(tickTime);
				
				Pair<Integer, String> res = NetworkManager.doGET(path);
				
				if (res.getKey() == 200) {	
					
					
					JSONObject json = new JSONObject(res.getValue());
					
					String val = json.getString("value");
					
					signal("new_monitoring_value", id, val, "blood_pressure", target, position);
					
				} else {
					System.out.println("Error : Cannot GET Blood Pressure Data");
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
			}
        }
	}
	
	@OPERATION
	void monitorSaturation(String id, String target, String position) {
		
		this.saturationMonitoring = true;
		int tickTime = 5000;
		
		while(this.saturationMonitoring) {
			
			String path = BASE_SERVICE_URL + "spO2";
			
			try {
				
				await_time(tickTime);
				
				Pair<Integer, String> res = NetworkManager.doGET(path);
				
				if (res.getKey() == 200) {	
					
					
					JSONObject json = new JSONObject(res.getValue());
					
					String val = json.getString("value");
					
					signal("new_monitoring_value", id, val, "spO2", target, position);
					
				} else {
					System.out.println("Error : Cannot GET Saturation Data");
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
			}
        }
	}

	
	@OPERATION
	void monitorHeartRate(String id, String target, String position) {
		
		this.heartRateMonitoring = true;
		int tickTime = 1000;
		
		while(this.heartRateMonitoring) {
			
			String path = BASE_SERVICE_URL + "heart_rate";
			
			try {
				
				await_time(tickTime);
				
				Pair<Integer, String> res = NetworkManager.doGET(path);
				
				if (res.getKey() == 200) {	
					
					
					JSONObject json = new JSONObject(res.getValue());
					
					String val = json.getString("value");
					
					signal("new_monitoring_value", id, val, "heart_rate", target, position);
					
				} else {
					System.out.println("Error : Cannot GET Saturation Data");
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
			}
        }
	}
	
	@OPERATION
	void monitorTemperature(String id, String target, String position) {
		
		this.temperatureMonitoring = true;
		int tickTime = 1000;
		
		while(this.temperatureMonitoring) {
			
			String path = BASE_SERVICE_URL + "temperature";
			
			try {
				
				await_time(tickTime);
				
				Pair<Integer, String> res = NetworkManager.doGET(path);
				
				if (res.getKey() == 200) {	
					
					
					JSONObject json = new JSONObject(res.getValue());
					
					String val = json.getString("value");
					
					signal("new_monitoring_value", id, val, "temperature", target, position);
					
				} else {
					System.out.println("Error : Cannot GET Saturation Data");
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
			}
        }
	}
	
	@OPERATION 
	void stopTemperatureMonitoring() {
		this.temperatureMonitoring = false;
	}
	
	@OPERATION 
	void stopHeartRateMonitoring() {
		this.heartRateMonitoring = false;
	}
	
	@OPERATION 
	void stopSaturationMonitoring() {
		this.saturationMonitoring = false;
	}
	
	@OPERATION 
	void stopBloodPressureMonitoring() {
		this.pressureMonitoring = false;
	}
}

