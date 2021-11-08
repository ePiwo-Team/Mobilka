package com.epiwo.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterToNet extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {

        String returnCode = "err";
        JSONObject jsonRegbject = new JSONObject();
        try {
            jsonRegbject.put("email", strings[1]);
            jsonRegbject.put("password", strings[2]);
            jsonRegbject.put("name",strings[3]);
            jsonRegbject.put("phoneNumber",strings[4]);
            jsonRegbject.put("birthDate",strings[5]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("JSON",jsonRegbject.toString());

        try {
            Siec.url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) Siec.url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
            os.writeBytes(jsonRegbject.toString());
            os.flush();
            os.close();


            Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
            Log.i("MSG" , urlConnection.getResponseMessage());

            returnCode = Integer.toString(urlConnection.getResponseCode());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return returnCode;
        }

    }
}
