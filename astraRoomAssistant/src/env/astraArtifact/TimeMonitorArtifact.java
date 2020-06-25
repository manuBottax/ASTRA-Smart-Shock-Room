// CArtAgO artifact code for project astraRoomAssistant

package astraArtifact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cartago.*;
import utils.ArtifactStatus;

public class TimeMonitorArtifact extends Artifact {
	
	private Date eta;
	private Date arrivalTime; 
	
	//private boolean isPatientArrived = false;
	
	void init() {
		long nowTime = new Date().getTime() ;				
		this.eta = new Date(nowTime + 1234567L);
		
		defineObsProperty("time_artifact_status", ArtifactStatus.ARTIFACT_CREATED.getStatus());
		
		System.out.println(" Time monitor initialized !");
	}
	
	@OPERATION
	void getETA(OpFeedbackParam<Date> eta) {	
		eta.set(this.eta);
	}
	
	@OPERATION
	void setETA(long eta) {
		this.eta = new Date(eta);
	}
	
	@OPERATION
	void getArrivalTime(OpFeedbackParam<Date> arrivalTime) {
		arrivalTime.set(this.arrivalTime);
	}
	
	@OPERATION
	void setPatientArrived() {			
		this.arrivalTime = new Date();
		//this.isPatientArrived = true;
	}
	
	@OPERATION
	void getTimeToETA(OpFeedbackParam<String> etaTime) {
		
		//if (! this.isPatientArrived) {
				
				Map<TimeUnit,Long> time = getDateDiff(new Date(), this.eta);
				
				long hours = time.get(TimeUnit.HOURS);
				long minute = time.get(TimeUnit.MINUTES);
				long seconds = time.get(TimeUnit.SECONDS);
				
				//if (hours >= 0 && minute >= 0 && seconds >= 0) {
				
					String t = hours + ":" + minute + ":" + seconds;
					
					etaTime.set(t);
					
					//signal("new_time_value", commandId, etaTime, "eta", target, position);
					
				//} 
		//}
	}
	
	@OPERATION
	void getTimeFromArrive(OpFeedbackParam<String> totalTime) {
		
		//if (this.isPatientArrived) {
				
			Map<TimeUnit,Long> time = getDateDiff(this.arrivalTime, new Date());
			
			long hours = time.get(TimeUnit.HOURS);
			long minute = time.get(TimeUnit.MINUTES);
			long seconds = time.get(TimeUnit.SECONDS);
			
			//if (hours >= 0 && minute >= 0 && seconds >= 0) {
			
				String elapsedTime = hours + ":" + minute + ":" + seconds;
				
				totalTime.set(elapsedTime);
				
				//signal("new_time_value", commandId, elapsedTime, "total_time", target, position);
		//	}
		//}
	}
	
	/*
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
					
					signal("new_monitoring_value", commandId, etaTime, "eta", target, position);
					
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
	
	*/
	

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
}

