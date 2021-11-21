package com.epiwo.logic;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.OffsetDateTime;
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
    private OffsetDateTime meetingDate;

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

    public OffsetDateTime getMeetingDate() {
        return meetingDate;
    }

    public static int count(){
        if (meetings==null)
            return 0;
        return meetings.length;
    }




    public static void update(){

        Meeting[] tmp = {new Meeting("nazwa"),new Meeting("nazwa1"),new Meeting("nazwa2")};

        meetings = tmp;

        Log.i("#spotkan: ", String.valueOf(meetings.length));
    }


    public Meeting(String name) {
        this.name = name;
    }


    public Meeting(long id, String name, String desc, /*List<String> foods,*/ String localization, OffsetDateTime meetingDate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
       // this.foods = foods;
        this.place = localization;
        this.meetingDate = meetingDate;
    }

}
