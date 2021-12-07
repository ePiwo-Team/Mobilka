package com.epiwo.logic;

import com.epiwo.network.Siec;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Chat {

   public static Chat current=null;

    public List<Balloon> talk = new LinkedList<>();
    private String  name;
    private Meeting meeting;
    long test = 1000;
    public class Balloon {
        Long userId;
        Long messageId;
        String messageText;
        String date;
        String userName;

        public Balloon(Long userId, Long messageId, String messageText, String date) {
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
        Siec.getChatMessages(meeting);
    }

    public void addBalloon(Long userId, Long messageId, String messageText, String date){
        talk.add(new Balloon(userId, messageId, messageText, date));
    }


    public void sendBalloon(String text) {
        talk.add(new Balloon(User.me.id, test++,text, Calendar.getInstance().toInstant().toString()));
        Siec.postChatMessage(meeting.getId(),text);

    }

}
