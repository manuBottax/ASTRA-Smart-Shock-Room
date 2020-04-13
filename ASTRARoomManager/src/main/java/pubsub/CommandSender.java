package pubsub;

import com.rabbitmq.client.Channel;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import model.Command;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CommandSender {

    private static final String EXCHANGE_NAME = "command_exchange";

    private Channel channel;

    public CommandSender(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendCommand(Command cmd) {

        try {
            channel.basicPublish(EXCHANGE_NAME, cmd.getType(), MessageProperties.PERSISTENT_TEXT_PLAIN, cmd.getMessage().getBytes(StandardCharsets.UTF_8));
        } catch( IOException ex) {
            System.err.println("Error : " + ex.getMessage());
        }

    }

}
