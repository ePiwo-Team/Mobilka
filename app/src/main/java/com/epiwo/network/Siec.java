package com.epiwo.network;

import android.util.Log;
import android.widget.Toast;

import com.epiwo.logic.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Siec {
    public static String address = "http://51.83.130.232:8080";
    public static String loginURL = address+ "/api/auth/login";
    public static String registerURL = address+ "/api/user/register";
    public static String echoURL = address+ "/api/user/hellouser";
    public static URL url;
    public static String jwt;
    final public static String POST = "POST";
    final public static String GET = "GET";
    final public static String PUT = "PUT";
    public static int httpRc = 418;

    public static boolean loginNet(String login, String password) {

            RequestToNet backgroundLogin = new RequestToNet();

            String output = null;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", login);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                output = backgroundLogin.execute(Siec.loginURL, Siec.POST, jsonObject.toString()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Siec.httpRc == 200) {
                try {
                    JSONObject jsonJWT = new JSONObject(output);
                    Log.i("JWT", jsonJWT.getString("jwt"));
                    Siec.jwt = jsonJWT.getString("jwt");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
    }

    public static boolean register( User registrationData){

        RequestToNet backgroundRegister = new RequestToNet();
        String output = null;
        String input = null;

        JSONObject jsonRegbject = new JSONObject();
        try {
            jsonRegbject.put("email", registrationData.email);
            jsonRegbject.put("password", registrationData.password);
            jsonRegbject.put("name", registrationData.login);
            jsonRegbject.put("phoneNumber", registrationData.phone);
            jsonRegbject.put("birthDate", registrationData.birthDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            input = backgroundRegister.execute(Siec.registerURL, Siec.POST, jsonRegbject.toString()).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Siec.httpRc == 200)
            return true;

        return false;
    }

    public static String echo() {
        RequestToNet backgroundecho = new RequestToNet();
        String output = null;
        String input = null;
        try {
            output = backgroundecho.execute(Siec.echoURL,Siec.GET, input).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;

    }


}
