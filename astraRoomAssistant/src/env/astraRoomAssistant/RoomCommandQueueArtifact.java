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
	
	void init() {
		
		this.pendingQueue = new PriorityQueue<JSONObject>(10, new PriorityComparator());
		this.refusedQueue = new PriorityQueue<JSONObject>(10, new PriorityComparator());
		this.wrongQueue = new PriorityQueue<JSONObject>(10, new PriorityComparator());
		
		JSONObject initCommand = new JSONObject();
		initCommand.put("command_id", "-1");
		
		defineObsProperty("last_pending_command", initCommand);
		defineObsProperty("last_refused_command", initCommand);
		defineObsProperty("last_wrong_command", initCommand);
		
		System.out.println("Room Command Queue Artifact created");
	}
	
	/**
	 * Get the list of pending command that are waiting to be handled
	 */
	@OPERATION
	void getPendingQueue(OpFeedbackParam<Queue<JSONObject>> list) {
		list.set(this.pendingQueue);
	}
	
	/**
	 * Get the list of command that the ASTRA System cannot handle
	 */
	@OPERATION
	void getRefusedQueue(OpFeedbackParam<Queue<JSONObject>> list) {
		list.set(this.refusedQueue);
	}
	
	/**
	 * Get the list of command with an error
	 */
	@OPERATION
	void getWrongQueue(OpFeedbackParam<Queue<JSONObject>> list) {
		list.set(this.wrongQueue);
	}
	
	/**
	 * Set a command as refused and remove it from the pending list. 
	 * This mean that the system cannot handle this type of command. 
	 * 
	 * @param id - The id of the command that is refused by the system
	 */
	@OPERATION
	void refuseCommand(String id) {
		ObsProperty last = getObsProperty("last_pending_command");
		JSONObject cmd = (JSONObject) last.getValue();
		
		if (id.equals(cmd.getString("command_id"))) {
			JSONObject c = this.pendingQueue.poll();
			this.refusedQueue.add(c);
			
			//TODO: Aggiornare lo stato del comando nel servizio
			
			ObsProperty refused = getObsProperty("last_refused_command");
			refused.updateValue(c);
			
			JSONObject l = this.pendingQueue.peek();
			
			if ( l != null) {
				last.updateValue(l);
			}
			
		}
	}
	
	/**
	 * Set a command as wrong and remove it from the pending list. 
	 * This is used when the handling of that command generate some sort of error.
	 * Actions may be taken by agents to remove errors and complete the command handling. 
	 * 
	 * @param id - The id of the command that generate the error.
	 */
	@OPERATION
	void setErrorOnCommand(String id) {
		ObsProperty last = getObsProperty("last_pending_command");
		JSONObject cmd = (JSONObject) last.getValue();
		
		String commandID = cmd.getString("command_id");
		
		if (! commandID.equals("-1")) {
		
			if (id.equals(cmd.getString("command_id"))) {
				JSONObject c = this.pendingQueue.poll();
				
				System.out.println(c);
				
				if (c != null) {
				
					this.wrongQueue.add(c);
					
					ObsProperty erroneous = getObsProperty("last_wrong_command");
					erroneous.updateValue(c);
					
					JSONObject l = this.pendingQueue.peek();
					
					if ( l != null) {
						last.updateValue(l);
					}
				
				}
				
				//TODO: Aggiornare lo stato del comando nel servizio
				
				
			}
		}
	}
	
	/**
	 * Add a command to the pending command queue.
	 * @param command - the command to be added. 
	 */
	@OPERATION
	void addPendingCommand(JSONObject command) {
		
		ObsProperty last = getObsProperty("last_pending_command");
		
		if (! command.getString("command_id").equals("-1")) {
		
		     if (this.pendingQueue.size() > 0 ) {
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
	void handledError(JSONObject command) {
		ObsProperty last = getObsProperty("last_wrong_command");
		JSONObject cmd = (JSONObject) last.getValue();
		
		if (command.getString("command_id").equals(cmd.getString("command_id"))) {
			
			JSONObject c = this.wrongQueue.poll();
			
			if ( c != null) {
				last.updateValue(c);
			}
			
		}
	}
	
	@OPERATION
	void subscribeQueue(String topic, String queueName) {
		
		this.topic = topic;
		this.queueName = queueName;
		
		execInternalOp("connect");
	}
	
	@OPERATION
	void acceptCommand(OpFeedbackParam<String> result, 
			OpFeedbackParam<String> id,
			OpFeedbackParam<String> type,
			OpFeedbackParam<String> value,
			OpFeedbackParam<String> target,
			OpFeedbackParam<String> position) {
		
		ObsProperty last = getObsProperty("last_pending_command");
		JSONObject cmd = (JSONObject) last.getValue();
		String commandID = cmd.getString("command_id");
		
		System.out.println("Accepting command.");
		System.out.println("Left pending command : " + this.pendingQueue.size());
		
		if (! commandID.equals("-1")) {
		
			//TODO: sospendi il piano dell'agente finchè non riceve risposta -> multistep op ?
					
			try {
				
				String path = COMMAND_SERVICE_URL + "/" + commandID + "/status";
				
				cmd.put("status", CommandStatus.in_processing.getStatusCode());
				
				JSONObject status = new JSONObject();
				status.put("status", CommandStatus.in_processing.getStatusCode());
				
				int res = NetworkManager.doPUT(path, status.toString());
				
				if (res == 200) {
					// l'agente può proseguire
					
					System.out.println("Comando accettato");
					
					JSONObject params = cmd.getJSONObject("params");
					
					result.set("OK");
					id.set(cmd.getString("command_id"));
					type.set(cmd.getString("type"));
					value.set(params.getString("value"));
					target.set(cmd.getString("target"));
					position.set("" + params.getInt("position"));
					
				} else {
					System.out.println("Error : Cannot update command");
					result.set("Error");
				}
			} catch (IOException e) {
				System.out.println("Error : IOException [ " + e.getMessage() + " ]");
				result.set("Error");
			}
		} else {
			result.set("Init OK");
		}
	}
	
	@OPERATION
	void completeCommand(String commandID) {
		
		ObsProperty last = getObsProperty("last_pending_command");
	
		try {
			
			this.pendingQueue.poll();
			
			JSONObject nextCommand = this.pendingQueue.peek();
			
			if ( nextCommand != null) {
				last.updateValue(nextCommand);
			}
			
			String path = COMMAND_SERVICE_URL + "/" + commandID + "/status";
			
			JSONObject status = new JSONObject();
			status.put("status", CommandStatus.completed.getStatusCode());
			
			int res = NetworkManager.doPUT(path, status.toString());
			
			if (res == 200) {
				signal("command_completed", commandID);
			} else {
				System.out.println("Error : Cannot complete command");
				signal("command_status_error", commandID);
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			signal("command_status_error", commandID);
		}
		
	}

	/*
	@OPERATION
	void setCommandError(String commandID) {
				
		try {
			
			String path = COMMAND_SERVICE_URL + "/" + commandID + "/status";
			
			JSONObject status = new JSONObject();
			status.put("status", CommandStatus.error.getStatusCode());
			
			int res = NetworkManager.doPUT(path, status.toString());
			
			if (res == 200) {
				signal("command_status_error", commandID);
			} else {
				System.out.println("Error : Cannot complete command");
				signal("command_status_error", commandID);
			}
		} catch (IOException e) {
			System.out.println("Error : IOException [ " + e.getMessage() + " ]");
			signal("command_status_error", commandID);
		}
		
	} */
		
	
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

