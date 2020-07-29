package com.example.hcaptcha_android;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

//Tutorial how use HttpURLConnection https://www.baeldung.com/httpurlconnection-post

public class CallAPI extends AsyncTask<String, String, String> {

    public static void doExecute(String params) {
        CallAPI callAPI = new CallAPI();
        callAPI.execute("https://hcaptcha.com/siteverify", params,
                "YOUR-SECRET");
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection client = null;
        try {
            URL object = new URL(params[0]);
            client = (HttpURLConnection) object.openConnection();

            client.setChunkedStreamingMode(0);
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            client.setDoOutput(true);

            OutputStream os = client.getOutputStream();
            byte[] input = ("response=" + params[1] + "&secret=" + params[2]).getBytes("utf-8");
            os.write(input, 0, input.length);


            BufferedReader br = new BufferedReader(
                    new InputStreamReader(client.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();


        } catch (ProtocolException e) {
            e.printStackTrace();
            //return "{\"success\":false,\"error-codes\":[\"" + e.getMessage() + "\"]}";

        } catch (IOException e) {
            e.printStackTrace();
            //return "{\"success\":false,\"error-codes\":[\"" + e.getMessage() + "\"]}";
        }

        return "";

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // get s parameter with json like Gson
        Toast.makeText(MainActivity.context, s, Toast.LENGTH_SHORT).show();
    }
}