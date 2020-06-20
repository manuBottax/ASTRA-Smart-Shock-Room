// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;
import utils.CommandStatus;
import utils.NetworkManager;
import utils.PriorityComparator;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;

import com.rabbitmq.client.*;

public class RoomCommandQueueArtifact extends Artifact {
	
	private final static long TICK_TIME = 1000;
	private final static String EXCHANGE_NAME = "room_command_exchange";
	
	private final static String COMMAND_SERVICE_URL = "http://192.168.1.120:3010/api/commands";
	
    private String topic;
    private String queueName;
    private Channel channel = null;
		
	private Queue<JSONObject> pendingQueue;
	private Queue<JSONObject> refusedQueue;
	private Queue<JSONObject> wrongQueue;
	
	void init(String topic, String queueName) {
		
		this.pendingQueue = new PriorityQueue<JSONObject>(10, new PriorityComparator());
		this.refusedQueue = new PriorityQueue<JSONObject>(10, new PriorityComparator());
		this.wrongQueue = new PriorityQueue<JSONObject>(10, new PriorityComparator());
		
		JSONObject initCommand = new JSONObject();
		initCommand.put("command_id", "-1");
		
		defineObsProperty("last_pending_command", initCommand);
		defineObsProperty("last_refused_command", initCommand, "-1");
		defineObsProperty("last_wrong_command", initCommand, "-1");
		
		this.topic = topic;
		this.queueName = queueName;

		execInternalOp("connect");
		
		System.out.println("Room Command Queue Artifact created");
	}
	
	/**
	 * Request the handling of the last pending command and return the command parameter.
	 */
	@OPERATION
	void acceptCommand() {
		
		
		ObsProperty last = getObsProperty("last_pending_command");
		JSONObject cmd = (JSONObject) last.getValue();
		String commandID = cmd.getString("command_id");
		
		//update the pending queue
		this.pendingQueue.poll();
		JSONObject nextCommand = this.pendingQueue.peek();
		if ( nextCommand != null) {
			last.updateValue(nextCommand);
		}
		
		if (! commandID.equals("-1")) {
							
			try {
				
				String path = COMMAND_SERVICE_URL + "/" + commandID + "/status";
				
				cmd.put("status", CommandStatus.in_processing.getStatusCode());
				
				JSONObject status = new JSONObject();
				status.put("status", CommandStatus.in_processing.getStatusCode());
				
				int res = NetworkManager.doPUT(path, status.toString());
								
				if (res == 200) {	
					
					System.out.println("Command Accepted !");
					System.out.println("Left pending commands : " + this.pendingQueue.size());
					
					JSONObject params = cmd.getJSONObject("params");
					
					signal("accepted_work", cmd, cmd.getString("command_id"), cmd.getString("type"), params.getString("value"), cmd.getString("target"), "" + params.getInt("position") );
					
				} else {
					System.out.println("Error : Cannot update command");
					failed("command acceptance failed", "service error", "failed_update", res, commandID );
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				failed("command acceptance failed", "I/O error", "failed_update", "IOException", commandID);
			}
		}
	}
	
	/**
	 * Set the specified command as completed and update it on the service. 
	 * 
	 * @param command - the completed command.
	 */
	@OPERATION
	void completeCommand(JSONObject command) {
				
		String commandID = command.getString("command_id");
	
		try {			
			
			String path = COMMAND_SERVICE_URL + "/" + commandID + "/status";
			
			JSONObject status = new JSONObject();
			status.put("status", CommandStatus.completed.getStatusCode());
			
			int res = NetworkManager.doPUT(path, status.toString());
						
			if (res == 200) {
				System.out.println("Command updated successfully");
			} else {
				System.out.println("Error : Cannot update command");
				failed("command acceptance failed", "service error", "failed_update", res, commandID);
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("command acceptance failed", "I/O error", "failed_update", "IOException", commandID);
		}	
	}
	
	/**
	 * Set a command as refused. This means that the system cannot handle this type of command. 
	 * 
	 * @param command - The command that is refused by the system
	 */
	@OPERATION
	void refuseCommand(JSONObject command) {
		
		String commandID = command.getString("command_id");
		
		try {
							
			String path = COMMAND_SERVICE_URL + "/" + commandID + "/status";
							
			JSONObject status = new JSONObject();
			status.put("status", CommandStatus.error.getStatusCode());
			
			int res = NetworkManager.doPUT(path, status.toString());
			
			if (res == 200) {
				
				ObsProperty refused = getObsProperty("last_refused_command");
				refused.updateValues(command, commandID);
				this.refusedQueue.add(command);
			
				System.out.println("Command updated successfully");
				
			} else {
				System.out.println("Error : Cannot update command");
				failed("command acceptance failed", "service error", "failed_update", res, commandID);
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			failed("command acceptance failed", "I/O error", "failed_update", "IOException", commandID);
		}	
	}
	
	/**
	 * Set a command as wrong. 
	 * This is used when the handling of that command generate some sort of error.
	 * Actions may be taken by agents to remove errors and complete the command handling. 
	 * 
	 * @param command - The command that generate the error.
	 */
	@OPERATION
	void setErrorOnCommand(JSONObject command) {
				
		this.wrongQueue.add(command);
		
		ObsProperty erroneous = getObsProperty("last_wrong_command");
		erroneous.updateValues(command, command.getString("command_id"));
		
		System.out.println("Error on " + erroneous.getValues()[1] + " - " + erroneous.getValues()[0]);
					
	}
	
	/**
	 * Add a command to the pending command queue.
	 * @param command - the command to be added. 
	 */
	@OPERATION
	void addPendingCommand(JSONObject command) {
		
		ObsProperty last = getObsProperty("last_pending_command");
		
		System.out.println("Command ID : " +  command.getString("command_id"));
		
		if (! command.getString("command_id").equals("-1")) {
			
			System.out.println("Adding new pending command : " + command.getString("command_id"));
		
		    if (this.pendingQueue.size() > 0) {
			    if (command.getInt("priority") > ((JSONObject) last.getValue()).getInt("priority")) {
			    	this.pendingQueue.add((JSONObject) last.getValue());
			    	last.updateValue(command);
			    } else {
			    	this.pendingQueue.add(command);
			    }
		    } else {
		    	this.pendingQueue.add(command);
		    	last.updateValue(command);
		    }
		}
	}
	
	/**
	 * Remove a command from the wrong command queue.
	 */
	@OPERATION
	void handledError() {
		
		ObsProperty last = getObsProperty("last_wrong_command");
			
		JSONObject c = this.wrongQueue.poll();
		
		System.out.println("Error handled. Error left : " + this.wrongQueue.size());
		
		if ( c != null) {
			last.updateValues(c, c.getString("command_id"));
		}
			
	}
		
	
	@INTERNAL_OPERATION
	void connect() {
		
        DeliverCallback deliverCallback = new DeliverCallback() {
			public void handle(String consumerTag, Delivery delivery) throws IOException {
			     String message = new String(delivery.getBody(), "UTF-8");
			     JSONObject req = new JSONObject(message);
			     System.out.println(" [ " + queueName + " Queue ] Received "  + delivery.getEnvelope().getRoutingKey() + " : " + message + " ");

			     ObsProperty last = getObsProperty("last_pending_command");
			     
			     // save message for future elaboration;
			     if (pendingQueue.size() > 0) {
				     if (req.getInt("priority") > ((JSONObject) last.getValue()).getInt("priority")) {
				    	 pendingQueue.add((JSONObject) last.getValue());
				    	 last.updateValue(req);
				     } else {
				    	 pendingQueue.add(req);
				     }
			     } else {
			    	 pendingQueue.add(req);
			    	 last.updateValue(req);
			     }
			 }
		};
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
        
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);

			channel.queueDeclare(queueName, true, false, false, null);
			
			channel.queueBind(queueName, EXCHANGE_NAME, this.topic);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
        
        System.out.println(" [ " + queueName + " Queue ] Waiting for messages. To exit press CTRL+C");
        
        while(true) {

            try {
            	await_time(TICK_TIME);
                channel.basicConsume(queueName, true, deliverCallback, new CancelCallback() {
					public void handle(String consumerTag) throws IOException { }
				});
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
	}

}

