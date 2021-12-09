package com.epiwo.logic;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.epiwo.network.Siec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Meeting {

    private long id;
    private String name;
    private String desc;
    private String place;
    private List<Food> foods;

    private List<Participant> participants;

    private String dateAndTime;
    private Chat chat;
    private boolean moderator;
    static public Meeting current = null;

    //static Meeting[] meetings = null;
    static public List<Meeting> meetings = new LinkedList<>();
    static public List<Meeting> myMeetings = new LinkedList<>();
    static public List<Meeting> foundMeetings = new LinkedList<>();


    public Meeting(long id, String name, String desc, List<Food> foods, String place, String dateAndTime, boolean moderator) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.moderator = moderator;
        this.foods = foods;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.participants = null;
        this.setParticipants(Participant.getParticipants(id));
        this.chat = new Chat(this);
        this.chat.getAllBalloons();
        this.chat.runBackGround();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public static long getId(List<Meeting> lista, int pos) { return lista.get(pos).id; }

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


    public Chat getChat() {
        return chat;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

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

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
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

    public static void addMeeting(List<Meeting> target, Meeting item) {
        target.add(item);
    }


    public static String uploadMeeting(Meeting item){
        if(Siec.postMeeting(item))
            return "Założono spotkanie";
        else
            return "Błąd tworzenia: "+Siec.httpRc;
    }

    public boolean updateMeeting(){
        return  Siec.putMeetingEditMeeting(this);
    }

    public boolean leaveMeeting(){
        return Siec.postMeetingLeave(this.getId());
    }
    public boolean destroyMeeting(){
        return Siec.deleteMeeting(this.getId());
    }


    public static void findMeeting(String name, String place, Calendar date, List<Food> foods){
        Meeting.foundMeetings = new LinkedList<>();
        String minDate;
        String maxDate;
        final int offset = 3;       // odległość wyszukiwania w dniach
        date.add(Calendar.DATE, offset);
        maxDate = date.toInstant().toString();
        date.add(Calendar.DATE, 2*(-offset));
        minDate = date.toInstant().toString();
        minDate = "";
        maxDate = "";
        Siec.postFilterMeeting(name,place, minDate,maxDate,foods);

    }

    public static boolean isMeetingOnList(List<Meeting> meetings, long id){
        for(int i=0; i<meetings.size(); i++)
        {
            if(getId(meetings,i) == id)
                return true;
        }
        return false;
    }

    public static String joinMeeting(Meeting item){
        return Siec.joinMeeting(item.getId());
    }

}
