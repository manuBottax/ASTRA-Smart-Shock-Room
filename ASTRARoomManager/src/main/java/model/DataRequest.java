package model;

import network.RESTRequestHandler;
import org.json.JSONObject;

public class DataRequest extends  Request {

    private static final String TYPE_REQUEST_DATA = "request_data";

    private RESTRequestHandler requestHandler;

    public DataRequest(String targetPath, String content){
        this.setType(TYPE_REQUEST_DATA);
        this.setContent(content);
        this.setTargetPath(targetPath);

        this.requestHandler = new RESTRequestHandler();
    }


    @Override
    public JSONObject performRequest() {

        String request = this.getTargetPath() + this.getContent();

        JSONObject res = this.requestHandler.getRequest(request);

        System.out.println("Request completed with result : " + res);
        return res;
    }
}
