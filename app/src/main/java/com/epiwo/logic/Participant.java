package com.epiwo.logic;

import android.widget.Toast;

import com.epiwo.front.MainPage;
import com.epiwo.network.Siec;

import java.util.LinkedList;
import java.util.List;

public class Participant {

    long id;
    public   String name;
    public boolean isHost;

    public Participant(long id, String name, boolean isHost) {
        this.id = id;
        this.name = name;
        this.isHost = isHost;
    }


    public void removeParticipant (Meeting meeting) {

        String resp = "Wyrzucony za drzwi!";


        meeting.getParticipants().remove(this);

        if(Siec.postMeetingKickParticipants(meeting.getId(), this.id))
            Toast.makeText(MainPage.page, resp, Toast.LENGTH_SHORT ).show();


    }


    public static List<Participant> getParticipants(long meetingID){

        List<Participant> list = new LinkedList<>();
        Siec.getMeetingParticipants(meetingID,list,User.me.id);

        return list;
    }
}
