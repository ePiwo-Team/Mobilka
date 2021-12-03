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
    static public Meeting current = null;

    //static Meeting[] meetings = null;
    static public List<Meeting> meetings = new LinkedList<>();
    static public List<Meeting> myMeetings = new LinkedList<>();

    public static long getID(List<Meeting> lista, int pos) { return lista.get(pos).id; }

    public static String getName(List<Meeting> lista, int pos) {
        return lista.get(pos).name;
    }

    public static String getDesc(List<Meeting> lista, int pos) {
        return lista.get(pos).desc;
    }

    public static String getPlace(List<Meeting> lista, int pos) {
        return lista.get(pos).place;
    }

    public static String getMeetingDate(List<Meeting> lista, int pos) {
        return lista.get(pos).dateAndTime;
    }

    public static Chat getChat(List<Meeting> lista, int pos) { return lista.get(pos).chat; }

    public static boolean getModerator(List<Meeting> lista, int pos) { return lista.get(pos).moderator; }




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

    public boolean isModerator() {
        return moderator;
    }

    public static int count(List<Meeting> lista){
        return lista.size();
    }


    public static void downloadMeetings(){
        meetings=new LinkedList<>();
        myMeetings=new LinkedList<>();

        Siec.getSelfMeetings();

        Log.i("#spotkan: ", String.valueOf(meetings.size()));
    }

    public static void addMeeting(Meeting item){

        meetings.add(item);

        if (item.isModerator())
            myMeetings.add(item);
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

    public static void searchMeeting(Meeting item){

    }

}
