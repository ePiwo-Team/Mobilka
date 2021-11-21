package com.epiwo.logic;

public class Meeting {

    private int id;
    private String desc;
    private String type;
    private String localization;
    private String meetingDate;

    public static Meeting[] meetings = {
            new Meeting("nazwa"),
            new Meeting("nazwa2"),
            new Meeting("nazwa3")
    };

    public Meeting(String desc) {
        this.desc = desc;
    }

    public Meeting(int id, String desc, String type, String localization, String meetingDate) {
        this.id = id;
        this.desc = desc;
        this.type = type;
        this.localization = localization;
        this.meetingDate = meetingDate;
    }

    public String toString(){
     return this.desc;
    }





}
