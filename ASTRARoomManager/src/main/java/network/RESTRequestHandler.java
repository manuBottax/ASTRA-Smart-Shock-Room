package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RESTRequestHandler {

    public JSONObject getRequest(String urlRequest){

        JSONObject res;

        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = "";
            String s;
            System.out.println("   ---> Output from Server .... \n");
            while (( s = br.readLine()) != null) {
                output = output.concat(s);
            }

            try {
                res = new JSONObject(output);
                conn.disconnect();
                return res;
            //todo: Throw this exception when receive an array, this need to be fixed
            }catch (JSONException ex){
                //ex.printStackTrace();
                JSONArray jArray = (JSONArray) new JSONTokener(output).nextValue();
                //System.err.println(ex.getCause().getMessage());
                conn.disconnect();
                //TODO: how to send back an array in result ?
                return jArray.getJSONObject(2);
            }

            //JSONParser parser = new JSONParser();
            //res = (JSONObject) parser.parse(output);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public JSONObject postRequest (String urlRequest, String data){

        JSONObject res;

        try {

        URL url = new URL(urlRequest);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
           System.out.println("Some Error Occurred ! ");
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output = "";
        String s;
        while (( s = br.readLine()) != null) {
            output = output.concat(s);
        }

        try {
            res = new JSONObject(output);
            conn.disconnect();
            return res;

        } catch (JSONException ex){
            System.err.println(ex.getCause().getMessage());
            conn.disconnect();
            return null;
        }

    } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
