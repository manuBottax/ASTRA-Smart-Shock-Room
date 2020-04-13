package model;

import org.json.JSONObject;

public abstract class Request {

    private String id;
    private String type;
    private String content;
    private String targetPath;

    public Request(){
        this.id = "" + (int)(Math.random() * 1000000);
    }

    public Request(String type,String targetPath, String content ){
        this.id = "" + (int)(Math.random() * 1000000);
        this.type = type;
        this.content = content;
        this.targetPath = targetPath;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetPath() {
        return this.targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    abstract public JSONObject performRequest();
}
