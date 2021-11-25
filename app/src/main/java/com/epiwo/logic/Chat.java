package com.epiwo.logic;

import java.util.LinkedList;
import java.util.List;

public class Chat {

    static int test_gen=1;

    public static Chat current=null;

    public class Balloon {
        String usr;  // Teraz jest nazwa ale moze trzeba zrobic obiekt User?
        String text; // Na poczatek tekst. Rozwojowo moze tu byc wszystko.

        public Balloon(String usr, String text) {
            this.usr = usr;
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public List<Balloon> talk = new LinkedList<>();
    private String name;
    private long meetingID;

    public Chat (String name, long meetingID) {
        this.name = name;
        this.meetingID = meetingID;

        getAllBalloons();
    }

    public String getName() {
        return name;
    }

    public void getAllBalloons() {

        // Testowe budowanie gadki
        for (int i=0 ; i<test_gen ; ++i) {
            Balloon tmp = new Balloon(((i%2==0)?"Jozef":"Rys"),"powiedzial "+Integer.toString(i));
            talk.add(tmp);
        }
        ++ test_gen;
    }

    public void getNewBalloons() {

        // Testowe budowanie gadki
        Balloon tmp = new Balloon(((test_gen%2==0)?"Jozef":"Rys"),"dodal "+Integer.toString(test_gen));
        talk.add(tmp);
        ++ test_gen;
    }

    public void sendBalloon(String text, String usr) {
        talk.add(new Balloon(usr,text));
    }
}
