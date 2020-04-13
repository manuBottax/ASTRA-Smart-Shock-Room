package pubsub;

import com.rabbitmq.client.*;
import model.DataRequest;
import model.DisplayAction;
import network.RESTRequestHandler;
import org.json.JSONObject;

import java.io.IOException;

public class CommandReceiver extends Thread {

    private static final String EXCHANGE_NAME = "command_exchange";

    private static final String SOURCE_BASE_PATH = "http://localhost:3002/api/data/";
    private static final String DISPLAY_BASE_PATH = "http://localhost:3001/data";

    private String agentName = "";
    private String topic;

    private RESTRequestHandler requestHandler;

    private String queueName= "";

    public CommandReceiver(String agentName, String topic){
        super();
        this.agentName = agentName;
        this.topic = topic;
        this.queueName = agentName + "_commands";

        this.requestHandler = new RESTRequestHandler();
    }

    @Override
    public void run() {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Channel channel = null;

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            channel.queueDeclare(this.queueName, true, false, false, null);

            // if handle multiple topics use a foreach
            //for (String bindingKey : topics) {
            channel.queueBind(this.queueName, EXCHANGE_NAME, this.topic);
            //}

            System.out.println(" [ " + this.agentName + " ] Waiting for messages. To exit press CTRL+C");

            while(true) {

                //Channel finalChannel = channel;
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [ " + this.agentName + " ] Received "  + delivery.getEnvelope().getRoutingKey() + " : " + message + " ");
                    try {
                        doWork(message);
                    } finally {
                        System.out.println(" [ " + this.agentName + " ] Done");
                        System.out.println(" [ " + this.agentName + " ] Waiting for messages. To exit press CTRL+C");
                        //finalChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    }

                };

                try {
                    channel.basicConsume(this.queueName, true, deliverCallback, consumerTag -> { });
                    /*channel.addShutdownListener(new ShutdownListener() {
                        @Override
                        public void shutdownCompleted(ShutdownSignalException cause) {
                            cause.printStackTrace();
                        }
                    });*/
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doWork(String message){
        System.out.println(" [ " + this.agentName + " ] Working on message  '" + message + "'");

        JSONObject req = new JSONObject(message);

        String patient_id = req.getString("patient_id");
        String msg = "";
        JSONObject res;

        switch((String) req.get("data_type")){
            case "all":
                System.out.println(" [ " + this.agentName + " ] Requesting data ... ");
                //String path = SOURCE_BASE_PATH + patient_id;
                //System.out.println("GET Request at " + path);
                //res = this.requestHandler.getRequest(path);
                DataRequest request = new DataRequest(SOURCE_BASE_PATH, patient_id);
                res = request.performRequest();
                System.out.println(" Patient data found : " + res.get("data_type") + " : " + res.get("value"));
                msg = "{ \"patient_id\" : \"123456\" , \"data_type\" : \"" + res.get("data_type") + "\" , \"value\" : \""+ res.get("value") + "\"}";
                DisplayAction act = new DisplayAction(msg, DISPLAY_BASE_PATH);
                act.executeAction();
                //requestHandler.postRequest(DISPLAY_BASE_PATH, msg);
                break;

            case "bpm" :
                System.out.println(" [ " + this.agentName + " ] Requesting bpm data ... ");
                //path = SOURCE_BASE_PATH + patient_id + "/bpm";
                //System.out.println("GET Request at " + path);
                //res = this.requestHandler.getRequest(path);
                request = new DataRequest(SOURCE_BASE_PATH, patient_id + "/bpm");
                res = request.performRequest();
                System.out.println(" Patient bpm found : " + res.get("data_type") + " : " + res.get("value"));
                msg = "{ \"patient_id\" : \"123456\" , \"data_type\" : \"" + res.get("data_type") + "\" , \"value\" : \""+ res.get("value") + "\"}";
                act = new DisplayAction(msg, DISPLAY_BASE_PATH);
                act.executeAction();
                //requestHandler.postRequest(DISPLAY_BASE_PATH, msg);
                break;

            case "blood_pressure_max":
                System.out.println(" [ " + this.agentName + " ] Requesting pressure data ... ");
                //path = SOURCE_BASE_PATH + patient_id + "/blood_pressure_max";
                //System.out.println("GET Request at " + path);
                //res = this.requestHandler.getRequest(path);
                request = new DataRequest(SOURCE_BASE_PATH, patient_id + "/blood_pressure_max");
                res = request.performRequest();
                System.out.println(" Patient data found : " + res.get("data_type") + " : " + res.get("value"));
                msg = "{ \"patient_id\" : \"123456\" , \"data_type\" : \"" + res.get("data_type") + "\" , \"value\" : \""+ res.get("value") + "\"}";
                act = new DisplayAction(msg, DISPLAY_BASE_PATH);
                act.executeAction();
                //requestHandler.postRequest(DISPLAY_BASE_PATH, msg);
                break;
        }
    }
}
