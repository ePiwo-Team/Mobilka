package com.epiwo.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EchoToNet extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {

        String returnCode = "err";
        StringBuilder stringBuilder = new StringBuilder();

        try {
            Siec.url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) Siec.url.openConnection();
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Authorization","Bearer "+strings[1]);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
            Log.i("MSG" , urlConnection.getResponseMessage());

            returnCode = Integer.toString(urlConnection.getResponseCode());


            if (urlConnection.getResponseCode() == 200) {
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
            }


            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return returnCode + stringBuilder.toString();
        }

    }
}

