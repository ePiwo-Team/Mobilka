package com.epiwo.logic;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.epiwo.network.Siec;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Meeting {

    private long id;
    private String name;
    private String desc;
    private String place;
    private List<String> foods;
    private String meetingDate;
    static Meeting[] meetings = null;

    public static String getName(int pos) {
        return meetings[pos].name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPlace() {
        return place;
    }

    public String getMeetingDate() {
        return meetingDate;
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


    public Meeting(long id, String name, String desc, /*List<String> foods,*/ String place, String meetingDate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
       // this.foods = foods;
        this.place = place;
        this.meetingDate = meetingDate;
    }

}
