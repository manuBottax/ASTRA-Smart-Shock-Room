package exe;

import pubsub.InputHandler;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputSample {



    public static void main(String[] argv) {

        InputHandler handler = new InputHandler("patient_data_handler");
        handler.start();

        Scanner scanner = new Scanner(System.in);

        System.out.println(" --- Simulatore di richieste ASTRA --- ");
        try {
            while (true) {
                System.out.println("Per favore inserisci una richiesta : ");
                String line = scanner.nextLine();

                handler.postRequest(line);
            }
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println("System.in was closed; exiting");
        }
    }

}
