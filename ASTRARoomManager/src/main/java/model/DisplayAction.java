package model;

import network.RESTRequestHandler;
import org.json.JSONObject;

public class DisplayAction extends Action {

    private static final String TYPE_ACTION_DISPLAY = "action_display";

    private RESTRequestHandler requestHandler;

    public DisplayAction(String content, String receiverPath){

        super();
        this.setType(TYPE_ACTION_DISPLAY);
        this.setContent(content);
        this.setReceiverPath(receiverPath);

        this.requestHandler = new RESTRequestHandler();



    }

    @Override
    public void executeAction() {

        JSONObject res = this.requestHandler.postRequest(this.getReceiverPath(), this.getContent());

        System.out.println("Action completed with result : " + res);

    }
}
