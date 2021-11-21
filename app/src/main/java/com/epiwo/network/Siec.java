package com.epiwo.network;

import android.util.Log;

import com.epiwo.logic.Meeting;
import com.epiwo.logic.User;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Siec {
    public static String address = "http://51.83.130.232:8080";

    //userzy
    public static String loginURL = address+ "/api/auth/login";
    public static String registerURL = address+ "/api/user/register";
    public static String echoURL = address+ "/api/user/hellouser";
    public static String selfURL = address+ "/api/user/getself";
    public static String updateUserURL = address+ "/api/user/modify";

    //spotkania

    public static String getSelfMeetingURL = address+ "/uzupelnic";


    public static URL url;
    public static String jwt;
    final public static String POST = "POST";
    final public static String GET = "GET";
    final public static String PUT = "PUT";
    public static int httpRc = 418;

    public static boolean loginNet(String login, String password) {

            RequestToNet backgroundLogin = new RequestToNet();

            String output = null;
            jwt = null;

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
            jsonRegbject.put("name", registrationData.name);
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
        if((Siec.httpRc == 200) || (Siec.httpRc == 201) )
            return true;

        return false;
    }

    public static String echo() {
        RequestToNet backGroundEcho = new RequestToNet();
        String output = null;
        String input = null;
        try {
            output = backGroundEcho.execute(Siec.echoURL,Siec.GET, input).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;

    }


    public static void getSelf(User user) {
        RequestToNet backgroundSelf = new RequestToNet();
        String output = null;
        String input = null;
        try {
            output = backgroundSelf.execute(Siec.selfURL, Siec.GET, input).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Siec.httpRc == 200) {
            try {
                JSONObject jsonUser = new JSONObject(output);
                Log.i("userData", output);
                user.email = jsonUser.getString("email");
                user.name = jsonUser.getString("name");
                user.phone = jsonUser.getString("phoneNumber");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean updateUserData( User updateUserData){

        RequestToNet backgroundRegister = new RequestToNet();
        String output = null;
        String input = null;

        JSONObject jsonUpdateUserObj = new JSONObject();
        try {
            jsonUpdateUserObj.put("email", updateUserData.email);
            jsonUpdateUserObj.put("password", updateUserData.password);
            jsonUpdateUserObj.put("name", updateUserData.name);
            jsonUpdateUserObj.put("phoneNumber", updateUserData.phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            input = backgroundRegister.execute(Siec.updateUserURL, Siec.PUT, jsonUpdateUserObj.toString()).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Siec.httpRc == 200)
            return true;

        return false;
    }

    public static void getSelfMeetings() {
        RequestToNet backgroundSelf = new RequestToNet();
        String output = null;
        String input = null;
  /*      try {
            output = backgroundSelf.execute(Siec.getSelfMeetingURL, Siec.GET, input).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        //tymczasowe, podmienic
        httpRc= 200;
        if (Siec.httpRc == 200) {

            JSONObject jsonTestObj = new JSONObject();
            try {
                jsonTestObj.put("id",40);
                jsonTestObj.put("name", "nazwaraz");
                jsonTestObj.put("desc", "opis spotkania");
                jsonTestObj.put("place", "domek");
                jsonTestObj.put("meetingDate", "2021-12-12");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
//                JSONObject jsonMeeting = new JSONObject(output);
//                Log.i("userData", output);
                JSONObject jsonMeeting = jsonTestObj;
                Meeting.addMeeting(new Meeting(
                   jsonMeeting.getInt("id"),
                   jsonMeeting.getString("name"),
                   jsonMeeting.getString("desc"),
                   jsonMeeting.getString("place"),
                   jsonMeeting.getString("meetingDate")
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
