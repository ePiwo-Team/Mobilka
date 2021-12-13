package com.epiwo.logic;


import com.epiwo.network.Siec;

import java.util.LinkedList;
import java.util.Objects;

public class User {

    public static User me;

    Boolean activeAuth = false;
    public String name;
    public String password;
    public String phone;
    public String birthDate;
    public String email;
    public long id = 1;

    public User(String name, String password, String phone, String birthDate, String email) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        me=this;
    }

    public User(String name, String password) {
        activeAuth = Siec.loginNet(name, password);
        me = this;
        me.password = password;
    }

    public Boolean test(){
        return activeAuth;
    }

    public Boolean checkPassword(String passwordCheck){
        return(Objects.equals(passwordCheck, password));
    }

    public Boolean register(){
        Siec.jwt = null;
        return Siec.register(this);


    }

    public void refreshUser(){
        Siec.getSelf(this);

    }

    public void nullUserData(){

//        Meeting.meetings = new LinkedList<>();

        Meeting.meetings = Meeting.clearMeetingList(Meeting.meetings);
        Meeting.myMeetings = Meeting.clearMeetingList(Meeting.myMeetings);

        name = null;
        phone = null;
        email = null;
        password = null;
        Siec.jwt = null;

    }

}
