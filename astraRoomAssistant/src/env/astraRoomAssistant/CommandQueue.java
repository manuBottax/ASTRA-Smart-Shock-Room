// CArtAgO artifact code for project astraRoomAssistant

package astraRoomAssistant;

import cartago.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;

import com.rabbitmq.client.*;

public class CommandQueue extends Artifact {
	
	private final static long TICK_TIME = 1000;
	private final static String EXCHANGE_NAME = "command_exchange";
	
	private boolean enabled;
	
	//private String agentName = "Receiver1";
    private String topic;
    private String queueName;
    
    private Channel channel = null;
	
	void init(String topic, String queueName) {
		this.enabled = false;
		this.topic = topic;
		this.queueName = queueName;
		System.out.println("Command Queue Artifact created for data_request");
	}
	
	@OPERATION
	void enableCommand() {
		if (!enabled) {
			enabled = true;
			execInternalOp("connect");
		} else {
			failed("already_enabled");
		}
	}
	
	@OPERATION
	void disableCommand() {
		enabled = false;
	}
	
	@INTERNAL_OPERATION
	void connect() {
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
        
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			channel.queueDeclare(queueName, true, false, false, null);
	        // if handle multiple topics use a foreach
	        //for (String bindingKey : topics) {
	        channel.queueBind(queueName, EXCHANGE_NAME, topic);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
        
        System.out.println(" [ " + this.queueName + " Queue ] Waiting for messages. To exit press CTRL+C");
        
        while(this.enabled) {

            //Channel finalChannel = channel;
            DeliverCallback deliverCallback = new DeliverCallback() {
				public void handle(String consumerTag, Delivery delivery) throws IOException {
				     String message = new String(delivery.getBody(), "UTF-8");
				     JSONObject req = new JSONObject(message);
				     System.out.println(" [ " + queueName + " Queue ] Received "  + delivery.getEnvelope().getRoutingKey() + " : " + message + " ");
				     signal("new_data_request", req.get("data_type"));
				 }
			};

            try {
            	await_time(TICK_TIME);
                channel.basicConsume(this.queueName, true, deliverCallback, new CancelCallback() {
					public void handle(String consumerTag) throws IOException { }
				});
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        
        //execInternalOp("checkCommand");
	}

	/*@INTERNAL_OPERATION
	void checkCommand() {
		
		 while(true) {

             //Channel finalChannel = channel;
             DeliverCallback deliverCallback = new DeliverCallback() {
				public void handle(String consumerTag, Delivery delivery) throws IOException {
				     String message = new String(delivery.getBody(), "UTF-8");
				     System.out.println(" [ " + CommandQueue.this.agentName + " ] Received "  + delivery.getEnvelope().getRoutingKey() + " : " + message + " ");
				     try {
				    	 signal("new_data_request", "bpm");
				     } finally {
				         System.out.println(" [ " + CommandQueue.this.agentName + " ] Done");
				         System.out.println(" [ " + CommandQueue.this.agentName + " ] Waiting for messages. To exit press CTRL+C");
				     }

				 }
			};

             try {
                 channel.basicConsume(this.queueName, true, deliverCallback, new CancelCallback() {
					public void handle(String consumerTag) throws IOException { }
				});
             } catch (IOException ex){
                 ex.printStackTrace();
             }
         }
		

	}*/
	
	/*String data;
	int i = 0;
	
	while(enabled) {
		if (i % 3 == 1) {
			data = "bpm";
			i++;
			signal("new_data_request", data);
		} else if (i % 3 == 2 ) {
			data = "pressure_max";
			i++;
			signal("new_data_request", data);
		} else {
			data = "all";
			i++;
			signal("new_data_request", data);
		}
		
		await_time(TICK_TIME);
	}*/
}

