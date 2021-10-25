package com.epiwo.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class LoginToNet extends android.os.AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String returnCode = "err";





            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", strings[1]);
                jsonObject.put("password", strings[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Siec.url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) Siec.url.openConnection();
 //               urlConnection.setRequestMethod("GET");
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.connect();

                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                os.writeBytes(jsonObject.toString());
                os.flush();
                os.close();


                Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
                Log.i("MSG" , urlConnection.getResponseMessage());

                returnCode = Integer.toString(urlConnection.getResponseCode());

                StringBuilder stringBuilder = new StringBuilder();

                if (urlConnection.getResponseCode() == 200) {
                    InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    String response = null;
                    while ((response = bufferedReader.readLine()) != null) {
                        stringBuilder.append(response + "\n");
                    }
                    bufferedReader.close();
                    JSONObject jsonJWT = new JSONObject(stringBuilder.toString());
                    Log.i("JWT", jsonJWT.getString("jwt"));
                    Siec.jwt = jsonJWT.getString("jwt");
                }


                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return returnCode;
            }

        }
    }

