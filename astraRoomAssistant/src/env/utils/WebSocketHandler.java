package utils;

import java.net.URISyntaxException;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;



public class WebSocketHandler {
	
	private String path; 
	private String message;
	
    private MessageHandler messageHandler;
    
    private Socket socket;

    public WebSocketHandler(String message, String servicePath) {

    	this.path = servicePath;
    	this.message = message;
    	
    	try {
            this.socket = IO.socket(path);
            
            this.socket
            	.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

					public void call(Object... args) {
						System.out.println("Connected to WS");
					}})
            	
	        	.on(this.message, new Emitter.Listener() {
	
					public void call(Object... args) {
						System.out.println("Message Received !");
						JSONObject obj = (JSONObject)args[0];
						
						messageHandler.receivedMessage(obj);
						
					}})
	            	  
        	    .on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

					public void call(Object... args) {
						System.out.println("Disconnected from WS");
					}});
            
            this.socket.connect();
            
            System.out.println("Socket connected !");
        } catch (URISyntaxException e) {
        	e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
    	
    	this.socket.emit("test", message); 
    }
    
    public void addMessageHandler(MessageHandler handler) {
    	this.messageHandler = handler;
    }

    
    public static interface MessageHandler {

        public void receivedMessage(JSONObject message);
    }
 
}
