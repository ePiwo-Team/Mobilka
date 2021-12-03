package com.epiwo.network;

import android.util.Log;
import com.epiwo.logic.Food;
import com.epiwo.logic.Meeting;
import com.epiwo.logic.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Siec {
    public static String address = "http://51.83.130.232:8080";

    //userzy
    public static String loginURL = address+ "/api/auth/login";
    public static String registerURL = address+ "/api/user/register";
    public static String echoURL = address+ "/api/user/hellouser";
    public static String selfURL = address+ "/api/user/getself";
    public static String updateUserURL = address+ "/api/user/modify";
    public static String deleteSelfURL = address+ "/api/user/delete";

    //spotkania
    public static String getSelfMeetingURL = address+ "/api/meeting/get_own";
    public static String createMeetingURL = address+ "/api/meeting/new_meeting";
    public static String getFoodListURL = address+ "/api/food";
    public static String destroyMeetingURL = address+ "/api/meeting/delete_meeting?meetingId="; //dodać id meetingu do usuwania
    public static String searchMeetingURL = address+ "/api/meeting/filter";

    public static URL url;
    public static String jwt;

    final public static String POST = "POST";
    final public static String GET = "GET";
    final public static String PUT = "PUT";
    final public static String DELETE ="DELETE";
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

    public static String deleteSelfUser() {
        RequestToNet backGroundEcho = new RequestToNet();
        String output = null;
        String input = null;
        try {
            output = backGroundEcho.execute(Siec.deleteSelfURL,Siec.DELETE, input).get();
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
        List<Food> foods = new LinkedList<>();
        Meeting newElement;

        try {
            output = backgroundSelf.execute(Siec.getSelfMeetingURL, Siec.GET, input).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Siec.httpRc == 200) {

            Log.i("userData", output);

            try {
                JSONArray jsonLista = new JSONArray(output);

                for (int i=0; i<jsonLista.length(); ++i) {
                    JSONObject jsonMeeting = jsonLista.getJSONObject(i);
                    JSONArray jsonMeetingFoods = jsonMeeting.getJSONArray("foods");
                    for(int j=0; j<jsonMeetingFoods.length();++j)
                        foods.add(Food.foods.get(Food.findFood(jsonMeetingFoods.getLong(j))));

                    newElement = new Meeting(
                            jsonMeeting.getInt("id"),
                            jsonMeeting.getString("name"),
                            jsonMeeting.getString("description"),
                            foods,
                            jsonMeeting.getString("place"),
                            jsonMeeting.getString("dateAndTime"),
                            jsonMeeting.getBoolean("currentUserHost")
                    );
                    Meeting.addMeeting(Meeting.meetings,newElement);
                    if (newElement.isModerator()) Meeting.addMeeting(Meeting.myMeetings,newElement);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getFoodList(List<Food> lista) {
        RequestToNet backgroundSelf = new RequestToNet();
        String output = null;
        String input = null;
        try {
            output = backgroundSelf.execute(Siec.getFoodListURL, Siec.GET, input).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Siec.httpRc == 200) {
            try {

                JSONArray jsonFoods = new JSONArray(output);

                for(int i=0; i<jsonFoods.length(); ++i) {
                    JSONObject jsonFood = jsonFoods.getJSONObject(i);
                    lista.add( new Food(jsonFood.getInt("id"),
                       jsonFood.getString("name"),
                       jsonFood.getString("description")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean postMeeting( Meeting item){

        RequestToNet backgroundRegister = new RequestToNet();
        String output = null;
        String input = null;

        JSONObject jsonSentObject = new JSONObject();
        try {
            jsonSentObject.put("name", item.getName());
            jsonSentObject.put("dateAndTime", item.getDateAndTime());
            jsonSentObject.put("place", item.getPlace());
            jsonSentObject.put("description", item.getDesc());
            JSONArray foods = new JSONArray();
            for (int i=0; i<item.getFoods().size(); ++i) foods.put(item.getFoods().get(i).getId());
            jsonSentObject.put("foods", foods );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            input = backgroundRegister.execute(Siec.createMeetingURL, Siec.POST, jsonSentObject.toString()).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if((Siec.httpRc == 200) || (Siec.httpRc == 201) )
            return true;

        return false;
    }

    public static boolean postFilterMeeting( String name, String place, String minDate, String maxDate, List<Food> filterFoods){

        RequestToNet backgroundRegister = new RequestToNet();
        String output = null;
        String input = null;

        JSONObject jsonSentObject = new JSONObject();
        try {

            JSONArray foods = new JSONArray();
            for (int i = 0; i < filterFoods.size(); ++i)
                foods.put(filterFoods.get(i).getId());
            jsonSentObject.put("foods", foods);
            jsonSentObject.put("place",place);
            jsonSentObject.put("name", name);
            jsonSentObject.put("minDate", minDate);
            jsonSentObject.put("maxDate", maxDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            output = backgroundRegister.execute(Siec.searchMeetingURL, Siec.POST, jsonSentObject.toString()).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Siec.httpRc == 200) {

                try {
                JSONArray jsonLista = new JSONArray(output);

                for (int i = 0; i < jsonLista.length(); ++i) {
                    JSONObject jsonMeeting = jsonLista.getJSONObject(i);
                    List<Food> foods = new LinkedList<>();
                    JSONArray jsonMeetingFoods = jsonMeeting.getJSONArray("foods");
                    for (int j = 0; j < jsonMeetingFoods.length(); ++j)
                        foods.add(Food.foods.get(Food.findFood(jsonMeetingFoods.getLong(j))));

                    Meeting.addMeeting(Meeting.foundMeetings,new Meeting(
                            jsonMeeting.getInt("id"),
                            jsonMeeting.getString("name"),
                            jsonMeeting.getString("description"),
                            foods,
                            jsonMeeting.getString("place"),
                            jsonMeeting.getString("dateAndTime"),
                            jsonMeeting.getBoolean("currentUserHost")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


}
