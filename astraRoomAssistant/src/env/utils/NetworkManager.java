package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.util.Pair;

public class NetworkManager {

    private static final int TIMEOUT = 10000; //ms
    private static final int SERVER_OK_RESULT = 200;

    public static Pair<Integer, String> doGET(String url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(TIMEOUT);
        urlConnection.setReadTimeout(TIMEOUT);

        int res = urlConnection.getResponseCode();
        StringBuilder response = new StringBuilder();

        if (res == SERVER_OK_RESULT) {
            BufferedReader buff = new BufferedReader(new InputStreamReader(new BufferedInputStream(urlConnection.getInputStream())));

            String chunks;

            while ((chunks = buff.readLine()) != null) {
                response.append(chunks);
            }
        }

        return new Pair<Integer, String>(res, response.toString());
    }

    public static Integer doPOST(String url, String content) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setConnectTimeout(TIMEOUT);
        urlConnection.setReadTimeout(TIMEOUT);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.getOutputStream().write(content.getBytes(StandardCharsets.UTF_8));

        return urlConnection.getResponseCode();
    }
    
    public static Integer doPUT(String url, String content) throws IOException {
    	
    	HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("PUT");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setConnectTimeout(TIMEOUT);
        urlConnection.setReadTimeout(TIMEOUT);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.getOutputStream().write(content.getBytes(StandardCharsets.UTF_8));

        return urlConnection.getResponseCode();
    }
}
