package com.epiwo.logic;

import android.os.Handler;
import android.widget.Toast;

import com.epiwo.front.MainPage;
import com.epiwo.front.ui.chat.ChatAdapter;
import com.epiwo.network.Siec;

import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Chat {

    public static final int bufforLen = 20;
    public static Chat current=null;
    public static boolean watchChat=false;

    public List<Balloon> talk = new LinkedList<>();
    public boolean onScreen = false;
    public ChatAdapter myAdapter;

    private long    sleepTime=1000;
    private long    minId=0;
    private long    maxId=0;
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
        watchChat = true;
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
        if (messageId>maxId) maxId = messageId;
    }


    public void sendBalloon(String text) {

        Siec.postChatMessage(meeting.getId(),text);
        if (sleepTime>1) sleepTime = sleepTime/2;
    }


    public void runBackGround () {
        final Handler checker = new Handler();

        checker.post(new Runnable() {
            @Override
            public void run() {
                // stop looking out for new messages
                if (!watchChat)
                    return;

                if ((maxId!=0)&&(Siec.getLastChatMessages(meeting)>maxId)) {
                    if (sleepTime>1) sleepTime = sleepTime/2;
                    if (onScreen) {
                        getAllBalloons();
                        myAdapter.notifyDataSetChanged();
                    }
                    else
                        getAllBalloons();
                    Toast.makeText(MainPage.page, "New message in " + name, Toast.LENGTH_SHORT).show();
                }
                else
                    sleepTime = sleepTime+100;

                // activity control
                if (onScreen) {
                    if (sleepTime>1000) sleepTime = sleepTime-1;
                }
                else
                    sleepTime = sleepTime+1;
                checker.postDelayed(this,sleepTime);
            }
        });
    }

}
