package model;

public abstract class Action {

    private String id;
    private String type;
    private String content;
    private String receiverPath;

    public Action(){
        this.id = "" + (int)(Math.random() * 1000000);
    }

    public Action(String type, String content, String receiverPath){
        this.id = "" + (int)(Math.random() * 1000000);
        this.type = type;
        this.content = content;
        this.receiverPath = receiverPath;
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

    public String getReceiverPath() {
        return this.receiverPath;
    }

    public void setReceiverPath(String receiver_path) {
        this.receiverPath = receiver_path;
    }

    abstract public void executeAction();
}
