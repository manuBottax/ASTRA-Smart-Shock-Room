// CArtAgO artifact code for project astraRoomAssistant

package astraArtifact;

import java.io.IOException;

import org.json.JSONObject;

import cartago.*;
import javafx.util.Pair;
import utils.ArtifactStatus;
import utils.NetworkManager;

public class VitalParameterArtifact extends Artifact {
	
	private static final String BASE_SERVICE_URL = "http://192.168.1.120:3005/api/vital_parameter/";
	
	private boolean pressureMonitoring = false;
	private boolean saturationMonitoring = false;
	private boolean heartRateMonitoring = false;
	private boolean temperatureMonitoring = false; 
	
	void init() {	
		
		defineObsProperty("vital_parameter_artifact_status", ArtifactStatus.SERVICE_CONNECTED.getStatus());
		
		System.out.println("TraumaService Artifact created");	
	}
	
	@OPERATION
	void getBloodPressureValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "blood_pressure";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				String val = json.getString("value");
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Blood Pressure Data");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}		
	}
	
	@OPERATION
	void getSaturationValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "spO2";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				String val = json.getString("value");
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Saturation Data");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}			
	}
	
	@OPERATION
	void getHeartRateValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "heart_rate";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				String val = json.getString("value");
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Heart Rate Data");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}			
	}
	
	@OPERATION
	void getTemperatureValue(OpFeedbackParam<String> value) {
		
		String path = BASE_SERVICE_URL + "temperature";
		
		try {
			
			Pair<Integer, String> res = NetworkManager.doGET(path);
			
			if (res.getKey() == 200) {	
				
				
				JSONObject json = new JSONObject(res.getValue());
				String val = json.getString("value");
				value.set(val);
				
			} else {
				System.out.println("Error : Cannot GET Temperature Data");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
				failed("Data retrieve failed", "service error", "failed_data_retrieve" );
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
			failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
		}			
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
					getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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
					getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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
					getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
				failed("Data retrieve failed", "I/O error", "failed_data_retrieve" );
			}
        }
	}
	
	@OPERATION
	void monitorTemperature(String id, String target, String position) {
		
		this.temperatureMonitoring = true;
		int tickTime = 30000;
		
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
					getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_ERROR.getStatus());
					failed("Data retrieve failed", "service error", "failed_data_retrieve" );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				getObsProperty("vital_parameter_artifact_status").updateValue(ArtifactStatus.SERVICE_UNREACHABLE.getStatus());
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

