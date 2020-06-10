// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;
import utils.CommandStatus;
import utils.NetworkManager;

import java.io.IOException;
import java.util.Comparator;
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
	
	void init() {
		
		this.pendingQueue = new PriorityQueue<JSONObject>(10, new Comparator<JSONObject>() {

			public int compare(JSONObject o1, JSONObject o2) {
				if (o1.getInt("priority") < o1.getInt("priority"))
					return -1;
				else if (o1.getInt("priority") == o2.getInt("priority"))
					return 0; 
				else 
					return 1;
			}
		});
		
		//defineObsProperty("available_command", false);
		
		JSONObject initCommand = new JSONObject();
		initCommand.put("command_id", "-1");
		
		defineObsProperty("last_available_command", initCommand);
		
		System.out.println("Room Command Queue Artifact created");
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
		
		ObsProperty last = getObsProperty("last_available_command");
		
		JSONObject cmd = (JSONObject) last.getValue();
		
		String commandId = cmd.getString("command_id");
		
		if (! commandId.equals("-1")) {
		
			//TODO: sospendi il piano dell'agente finchè non riceve risposta -> usare multistep op
					
			try {
				
				String path = COMMAND_SERVICE_URL + "/" + commandId + "/status";
				
				cmd.put("status", CommandStatus.in_processing.getStatusCode());
				
				int res = NetworkManager.doPUT(path, cmd.toString());
				
				if (res == 200) {
					// l'agente può proseguire
					
					this.pendingQueue.poll();
					
					JSONObject nextCommand = this.pendingQueue.peek();
					
					if ( nextCommand != null) {
						last.updateValue(nextCommand);
					} /*else {
						available.updateValue(false);
					}*/
					
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
	
	/*
	@OPERATION
	void completeCommand(String id) {
	
		
		
	}
	
	*/
	
	@INTERNAL_OPERATION
	void connect() {
		
        DeliverCallback deliverCallback = new DeliverCallback() {
			public void handle(String consumerTag, Delivery delivery) throws IOException {
			     String message = new String(delivery.getBody(), "UTF-8");
			     JSONObject req = new JSONObject(message);
			     System.out.println(" [ " + queueName + " Queue ] Received "  + delivery.getEnvelope().getRoutingKey() + " : " + message + " ");

			     //ObsProperty available = getObsProperty("available_command");
			     ObsProperty last = getObsProperty("last_available_command");
			     
			     //System.out.println(" [ " + queueName + " Queue ] Obs Retrieved");
			     
			     //System.out.println(" [ " + queueName + " Queue ] Req :" + req.getInt("priority"));
			    // System.out.println(" [ " + queueName + " Queue ] last :" + ((JSONObject) last.getValue()).getInt("priority"));
			     
			     //System.out.println(" [ " + queueName + " Queue ] Checking Priority");
			     
			     // save message for future elaboration;
			     if (pendingQueue.size() > 0) {
				     if (req.getInt("priority") > ((JSONObject) last.getValue()).getInt("priority")) {
				    	 //System.out.println(" [ " + queueName + " Queue ] Updating last");
				    	 pendingQueue.add((JSONObject) last.getValue());
				    	 //available.updateValue(true);
				    	 last.updateValue(req);
				     } else {
				    	 //System.out.println(" [ " + queueName + " Queue ] In coda");
				    	 pendingQueue.add(req);
				     }
			     } else {
			    	 pendingQueue.add(req);
			    	 //available.updateValue(true);
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

