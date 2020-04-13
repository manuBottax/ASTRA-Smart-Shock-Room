package model;

import java.util.Date;

public class Command {

    private String id;
    private String name;
    private String type;
    private String senderId;
    private String message;
    private Date timestamp;

    public Command (String name, String type, String senderId, String message) {

        this.id =  "" + (int) (Math.random() * 100000);
        this.name = name;
        this.type = type;
        this.senderId = senderId;
        this.message = message;

        this.timestamp = new Date();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
