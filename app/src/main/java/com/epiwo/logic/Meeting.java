package com.epiwo.logic;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.epiwo.network.Siec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meeting {

    private long id;
    private String name;
    private String desc;
    private String place;
    private List<String> foods;
    private String dateAndTime;
    private Chat chat;

    static Meeting[] meetings = null;

    public static long getID(int poz) { return meetings[poz].id; }

    public static String getName(int pos) {
        return meetings[pos].name;
    }

    public static String getDesc(int pos) {
        return meetings[pos].desc;
    }

    public static String getPlace(int pos) {
        return meetings[pos].place;
    }

    public static String getMeetingDate(int pos) {
        return meetings[pos].dateAndTime;
    }

    public static Chat getChat(int poz) { return meetings[poz].chat; }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPlace() {
        return place;
    }

    public List<String> getFoods() {
        return foods;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public static int count(){
        if (meetings==null)
            return 0;
        return meetings.length;
    }


    public static void downloadMeetings(){
        meetings=null;


        Siec.getSelfMeetings();

        Log.i("#spotkan: ", String.valueOf(meetings.length));
    }

    public static void addMeeting(Meeting item){
        if (meetings == null) {
            meetings = new Meeting[1];
            meetings[0] = item;
        }
        else {
            List<Meeting> arrlist
                    = new ArrayList<Meeting>(
                    Arrays.asList(meetings));

            arrlist.add(item);
            meetings = arrlist.toArray(new Meeting[0]);
        }
    }


    public static String uploadMeeting(Meeting item){
        if(Siec.postMeeting(item))
            return "Założono spotkanie";
        else
            return "Błąd tworzenia: "+Siec.httpRc;
    }


    public Meeting(long id, String name, String desc, List<String> foods, String place, String dateAndTime) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.foods = foods;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.chat = new Chat(name,id);
    }

    
}
