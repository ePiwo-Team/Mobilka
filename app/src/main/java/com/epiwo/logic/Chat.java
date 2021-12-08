package com.epiwo.logic;

import com.epiwo.network.Siec;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Chat {

    public static Chat current=null;
    public static final int bufforLen = 20;

    public List<Balloon> talk = new LinkedList<>();
    private long    minId=0;
    private String  name;
    private Meeting meeting;

    public class Balloon {
        long userId;
        long messageId;
        String messageText;
        String date;
        String userName;

        public Balloon(long userId, long messageId, String messageText, String date) {
            this.userId = userId;
            this.messageId = messageId;
            this.messageText = messageText;
            this.date = date;

            for (int i=0 ; i<meeting.getParticipants().size() ; ++i )
                if (meeting.getParticipants().get(i).id==userId)
                     userName = meeting.getParticipants().get(i).name;
        }

        public String getText() { return messageText; }
        public String getUsr()  {
            return userName;
        }

        public boolean itsMe () {
            if(userId == User.me.id)
                return true;
            return false;
        }

    }

    public Chat (Meeting meeting) {
        this.name = meeting.getName();
        this.meeting = meeting;
    }

    public String getName() {
        return name;
    }

    public void getAllBalloons() {

        talk = new LinkedList<>();
        minId=0;
        Siec.getChatMessages(meeting);
    }

    public int getOldBalloons() {
        return Siec.getOldChatMessages(meeting,minId);
    }

    public void addBalloon(long userId, long messageId, String messageText, String date){

        talk.add(0,new Balloon(userId, messageId, messageText, date));
        if ((minId==0)||(messageId<minId)) minId = messageId;
    }


    public void sendBalloon(String text) {
        Siec.postChatMessage(meeting.getId(),text);
    }

}
