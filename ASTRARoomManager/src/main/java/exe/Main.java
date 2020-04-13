package exe;

import pubsub.CommandReceiver;
import pubsub.CommandSender;

public class Main {

    public static void main(String[] argv) throws Exception {

        //pubsub.InputHandler handler = new pubsub.InputHandler("patient_data_handler");

        // send message to both receiver 1 & 2
        //CommandSender sender1 = new CommandSender("model.Command Sender", "command.example");

        //send message only to receiver 1
        //CommandSender sender2 = new CommandSender("Test Sender", "test.example");

        // send message to no one
        //CommandSender sender3 = new CommandSender("Example Sender", "test.command");

        CommandReceiver receiver1 = new CommandReceiver("Receiver1", "data_retriever.*");
        //CommandReceiver receiver2 = new CommandReceiver("Receiver2", "command.*");

        //sender1.start();
        //sender2.start();
        //sender3.start();

        //handler.start();
        receiver1.start();
        //receiver2.start();
    }
}
