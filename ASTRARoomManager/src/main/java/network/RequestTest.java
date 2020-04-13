package network;

import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class RequestTest {



    public static void main(String[] argv)  {

        RESTRequestHandler requestHandler = new RESTRequestHandler();

        System.out.println(" ---------------------------------------- ");
        System.out.println(" |        ASTRA REST SERVER TEST        | ");
        System.out.println("");
        System.out.println(" |  Generating some POST requests ... ");
        System.out.println("");

        /*

        for (int i = 0; i < 15; i ++){

            String type = "";

            if ( i % 3 == 0) {
                type = "blood_pressure_max";
            } else if ( i % 3 == 1) {
                type = "blood_pressure_min";
            } else if ( i % 3 == 2) {
                type = "bpm";
            }

            String input = "{\"patient_id\" : \"123456\" , \"data_type\" : \"" + type + "\" , \"value\" : " + (120 + i) + " }";

            JSONObject res = requestHandler.postRequest("http://localhost:3000/api/data/", input);

            System.out.println(res);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex){
                System.err.println(ex.getCause().getMessage());
            }
        }*/

        /*

        String input = "{\"patient_id\" : \"123456\" , \"data_type\" : \"" + "blood_pressure_max" + "\" , \"value\" : " + 120 + " }";

        JSONObject r = requestHandler.postRequest("http://localhost:3001/data", input);

        System.out.println(r);

        System.out.println("");
        System.out.println(" ----------------------------------- ");
        System.out.println("");

        */

        System.out.println(" |  Generating GET request ... ");

        JSONObject res = requestHandler.getRequest("http://localhost:3002/api/data/123456/bpm");

        System.out.println(res);

        System.out.println("");
        System.out.println(" |   ASTRA REST SERVER TEST COMPLETED   | ");
        System.out.println(" ---------------------------------------- ");
        System.out.println("");
    }

}
