package com.epiwo.network;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestToNet extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        /*
        strings[0] adres
        strings[1] metoda http (post/get/put/cokolwiek)
        strings[2] output
        */

        StringBuilder stringBuilder = new StringBuilder();
        URL url;
        Siec.httpRc = 418;

        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(strings[1]);
            urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            if(Siec.jwt != null){
                urlConnection.setRequestProperty("Authorization","Bearer "+Siec.jwt);
            }
            if((strings[1] == Siec.POST) || (strings[1] == Siec.PUT)) {
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
            }

            urlConnection.connect();


            if(((strings[1] == Siec.POST) || (strings[1] == Siec.PUT)) && (strings[2] != null) ) {
                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());

                os.writeBytes(strings[2]);
                os.flush();
                os.close();
            }

            Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
            Log.i("MSG", urlConnection.getResponseMessage());

            Siec.httpRc = urlConnection.getResponseCode();

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
            return stringBuilder.toString();
        }
    }
}
