package com.example.gadkiyabram.gyrodatalithium;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {

    public static <T> String saveData(JSONObject object, String requestUrl, String token) throws JSONException {
        URL url = createUrl(requestUrl);

        String response = null;
        response = makeHttpPostRequest(object, url, token);

        return response;
    }

    public static <T> JSONArray getData(String requestUrl, String token) throws JSONException {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpGetRequest(url, token);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        JSONArray jsonArray = extractData(jsonResponse);
        return jsonArray;
    }

    private static String makeHttpGetRequest(URL url, String token) throws IOException{
        String response = null;

        if (url == null){ return response; }

        HttpURLConnection conn = null;

        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Token", token);
            conn.setRequestMethod("GET");

            int result = conn.getResponseCode();
            if (result == 200){
                InputStream in = conn.getInputStream();
                response = readFromStream(in);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    private static <T> String makeHttpPostRequest(JSONObject object, URL url, String token) {
        String response = null;

        if (url == null) { return response; }

        HttpURLConnection conn = null;
        BufferedReader reader = null;

        try{
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setChunkedStreamingMode(0);

            conn.addRequestProperty("Content-type", "application/json; charset=utf-8");
            conn.setRequestProperty("Token", token);
            conn.setRequestMethod("POST");

            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(object.toString().getBytes());
            out.flush();
            out.close();

            int result = conn.getResponseCode();
            if (result == 200){
                InputStream in = conn.getInputStream();
                response = readFromStream(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static String readFromStream(InputStream in) throws IOException {
        StringBuilder output = new StringBuilder();
        if (in != null){
            InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        return url;
    }

    private static <T> JSONArray extractData(String jsonResponse) {

        JSONArray jsonArray = null;

        if (jsonResponse != null) {
            try{
                jsonArray = new JSONArray(jsonResponse);
            }catch (JSONException ex){
                ex.printStackTrace();
            }
        }
        return jsonArray;
    }
}
