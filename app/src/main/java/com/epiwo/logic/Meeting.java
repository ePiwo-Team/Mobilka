package com.epiwo.logic;

import android.util.Log;

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
    static Meeting[] meetings = null;

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

    public static int count(){
        if (meetings==null)
            return 0;
        return meetings.length;
    }


    public static void update(){
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


    public Meeting(String name) {
        this.name = name;
    }


    public Meeting(long id, String name, String desc, /*List<String> foods,*/ String place, String dateAndTime) {
        this.id = id;
        this.name = name;
        this.desc = desc;
       // this.foods = foods;
        this.place = place;
        this.dateAndTime = dateAndTime;
    }

}
