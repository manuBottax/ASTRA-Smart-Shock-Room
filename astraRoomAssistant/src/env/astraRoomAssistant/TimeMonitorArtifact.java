// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cartago.*;

public class TimeMonitorArtifact extends Artifact {
	
	private Date eta = new Date();
	private Date arrivalTime; 
	
	private boolean etaMonitor = false;
	private boolean totalTimeMonitor = false;
	
	private boolean isPatientArrived = false;
	
	void init() {
		
		long nowTime = new Date().getTime() ;				
		this.eta.setTime(nowTime + 1234567L);
		
		System.out.println(" Time monitor initialized !");
	}
	
	@OPERATION
	void setArrivalTime(String id) {			
		this.arrivalTime = new Date();
		this.isPatientArrived = true;
		
		//signal("completed_action", id);
	}
	
	@OPERATION
	void monitorETA(String commandId, String target, String position) {
		
		if (! this.isPatientArrived) {
			
			this.etaMonitor = true;
			int tickTime = 1000;
		
			while(this.etaMonitor) {	
				
				Map<TimeUnit,Long> time = getDateDiff(new Date(), this.eta);
				
				long hours = time.get(TimeUnit.HOURS);
				long minute = time.get(TimeUnit.MINUTES);
				long seconds = time.get(TimeUnit.SECONDS);
				
				if (hours >= 0 && minute >= 0 && seconds >= 0) {
				
					String etaTime = hours + ":" + minute + ":" + seconds;
					
					signal("new_monitoring_value", commandId, etaTime, "total_time", target, position);
					
					await_time(tickTime);
				} else {
					this.etaMonitor = false;
				}
				
	        }
		} else {
			this.etaMonitor = false;
		}
	}
	
	@OPERATION
	void monitorTotalTime(String commandId, String target, String position) {
		
		if (this.isPatientArrived) {
				
			this.totalTimeMonitor = true;
			int tickTime = 1000;
			
			while(this.totalTimeMonitor) {
				
				Map<TimeUnit,Long> time = getDateDiff(this.arrivalTime, new Date());
				
				long hours = time.get(TimeUnit.HOURS);
				long minute = time.get(TimeUnit.MINUTES);
				long seconds = time.get(TimeUnit.SECONDS);
				
				if (hours >= 0 && minute >= 0 && seconds >= 0) {
				
					String elapsedTime = hours + ":" + minute + ":" + seconds;
					
					signal("new_monitoring_value", commandId, elapsedTime, "total_time", target, position);
					
					await_time(tickTime);
				} else {
					this.totalTimeMonitor = false;
				}
				
	        }
		} else {
			this.totalTimeMonitor = false;
		}
	}
	

	private static Map<TimeUnit,Long> getDateDiff(Date date1, Date date2) {
		
		long diffInMillies = date2.getTime() - date1.getTime();
		
		//create the list
	    List<TimeUnit> units = new ArrayList<TimeUnit> (EnumSet.allOf(TimeUnit.class));
	    Collections.reverse(units);

	    //create the result map of TimeUnit and difference
	    Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
	    long milliesRest = diffInMillies;

	    for ( TimeUnit unit : units ) {

	        //calculate difference in millisecond 
	        long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
	        long diffInMilliesForUnit = unit.toMillis(diff);
	        milliesRest = milliesRest - diffInMilliesForUnit;

	        //put the result in the map
	        result.put(unit,diff);
	    }

	    return result;		
	}
	
	
	/*@OPERATION
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
	} */
}

