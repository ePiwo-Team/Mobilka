package com.epiwo.logic;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.epiwo.network.Siec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Meeting {

    private long id;
    private String name;
    private String desc;
    private String place;
    private List<Food> foods;
    private String dateAndTime;
    private Chat chat;
    private boolean moderator;

    //static Meeting[] meetings = null;
    static List<Meeting> meetings = new LinkedList<>();
    static List<Meeting> myMeetings = new LinkedList<>();

    public static long getID(int poz) { return meetings.get(poz).id; }

    public static String getName(int pos) {
        return meetings.get(pos).name;
    }

    public static String getDesc(int pos) {
        return meetings.get(pos).desc;
    }

    public static String getPlace(int pos) {
        return meetings.get(pos).place;
    }

    public static String getMeetingDate(int pos) {
        return meetings.get(pos).dateAndTime;
    }

    public static Chat getChat(int poz) { return meetings.get(poz).chat; }

    public static boolean getModerator(int poz) { return meetings.get(poz).moderator; }


//    public static long getID(int poz) { return meetings[poz].id; }
//
//    public static String getName(int pos) {
//        return meetings[pos].name;
//    }
//
//    public static String getDesc(int pos) {
//        return meetings[pos].desc;
//    }
//
//    public static String getPlace(int pos) {
//        return meetings[pos].place;
//    }
//
//    public static String getMeetingDate(int pos) {
//        return meetings[pos].dateAndTime;
//    }
//
//    public static Chat getChat(int poz) { return meetings[poz].chat; }


    public String getName() {
        return name;
    }

    public long getId() { return id; }

    public String getDesc() {
        return desc;
    }

    public String getPlace() {
        return place;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public static int count(){
//        if (meetings==null)
//            return 0;
        return meetings.size();
    }


    public static void downloadMeetings(){
        meetings=new LinkedList<>();

        Siec.getSelfMeetings();

        Log.i("#spotkan: ", String.valueOf(meetings.size()));
    }

    public static void addMeeting(Meeting item){

        meetings.add(item);


    }


    public static String uploadMeeting(Meeting item){
        if(Siec.postMeeting(item))
            return "Założono spotkanie";
        else
            return "Błąd tworzenia: "+Siec.httpRc;
    }


    public Meeting(long id, String name, String desc, List<Food> foods, String place, String dateAndTime, boolean moderator) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.foods = foods;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.chat = new Chat(name,id);
        this.moderator = moderator;
    }

}
