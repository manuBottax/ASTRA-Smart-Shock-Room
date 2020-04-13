package pubsub;


import model.Command;
import pubsub.CommandSender;


import java.util.ArrayDeque;
import java.util.Queue;

public class InputHandler extends Thread {

    private String agentName;
    private CommandSender sender;

    private Queue<String> requestList;

    public InputHandler (String name) {
        this.sender = new CommandSender();
        this.agentName = name;
        this.requestList = new ArrayDeque<>();
    }

    public void postRequest(String request) {
        this.requestList.add(request);
    }


    @Override
    public void run() {
        while(true){

            String request = requestList.poll();

            if ( request != null) {

                String type = "";
                String name = "";
                String message = "" ;

                switch (request){
                    case "all" :
                        name = "patient_data_request";
                        type = "data_retriever.example";
                        message = "{ \"patient_id\" : \"123456\" , \"data_type\" : \"all\" }";
                        break;
                    case "bpm" :
                        name = "patient_bpm_request";
                        type = "data_retriever.example";
                        message = "{ \"patient_id\" : \"123456\" , \"data_type\" : \"bpm\" }";
                        break;
                    case"pressure_max":
                        name = "patient_pressure_max_request";
                        type = "data_retriever.example";
                        message = "{ \"patient_id\" : \"123456\" , \"data_type\" : \"blood_pressure_max\" }";
                        break;
                    default:
                        type = "unsupported";
                        message = "{ \"error\" : \"Unsupported Request\" }";
                        break;
                }

                Command command = new Command(name, type, this.agentName, message);

                this.sender.sendCommand(command);

            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
