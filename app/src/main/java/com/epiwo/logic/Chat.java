package com.epiwo.logic;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.core.app.NotificationCompat;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.front.ui.chat.ChatAdapter;
import com.epiwo.network.Siec;

import java.util.LinkedList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;



public class Chat {

    public static boolean notifyOn = true;
    public static final int bufforLen = 20;
    public static Chat current=null;


    public static boolean uninitNotification = true;

    public List<Balloon> talk = new LinkedList<>();
    public boolean onScreen = false;
    public ChatAdapter myAdapter;
    public boolean watchChat;

    private String  lastText;
    private String  lastAuthor;
    private int     notificationId = 0;
    private long    sleepTime=1000; // milisec
    private long    minId=0;
    private long    maxId=0;
    private String  name;
    private Meeting meeting;

    public class Balloon {
        long userId;
        long messageId;
        String messageText;
        String date;
        String userName = null;

        public Balloon(long userId, long messageId, String messageText, String date) {
            this.userId = userId;
            this.messageId = messageId;
            this.messageText = messageText;
            this.date = date;

            for (int i=0 ; i<meeting.getParticipants().size() ; ++i ) {
                if (meeting.getParticipants().get(i).id == userId)
                    userName = meeting.getParticipants().get(i).name;
            }
            if(userName == null)
                userName = MainPage.page.getString(R.string.kicked_user_tag);
        }

        public String getText() { return messageText; }
        public String getUsr()  {
            return userName;
        }

        public boolean itsMe () {
            return (userId == User.me.id);
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

        lastAuthor = null;
        lastText   = null;
        Siec.getChatMessages(meeting);
    }

    public int getOldBalloons() {
        return Siec.getOldChatMessages(meeting,minId);
    }

    public void addBalloon(long userId, long messageId, String messageText, String date){
        Balloon tmp = new Balloon(userId, messageId, messageText, date);
        talk.add(0,tmp);
        if ((minId==0)||(messageId<minId)) minId = messageId;
        if (messageId>maxId) maxId = messageId;
        if (lastAuthor==null) lastAuthor = tmp.getUsr();
        if (lastText==null) lastText = messageText;
    }


    public void sendBalloon(String text) {

        Siec.postChatMessage(meeting.getId(),text);
        if (sleepTime>2000) sleepTime = sleepTime/2;
    }


    public void runBackground() {
        final Handler checker = new Handler();

        checker.post(new Runnable() {
            @Override
            public void run() {
                // stop looking out for new messages
                if (!watchChat)
                    return;

                if ((maxId!=0)&&(Siec.getLastChatMessages(meeting)>maxId)&&(notifyOn)) {
                    if (sleepTime>2000) sleepTime = sleepTime/2;
                    if (onScreen) {
                        getAllBalloons();
                        myAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        getAllBalloons();
                        popup(meeting.getName(),lastAuthor+": "+lastText);
                    }
                }
                else
                    sleepTime = sleepTime+1000;

                // activity control
                if (onScreen) {
                    if (sleepTime>1000) sleepTime = sleepTime-100;
                }
                else
                    sleepTime = sleepTime+100;
                checker.postDelayed(this,sleepTime);
            }
        });
    }


    void popup (String meetingName,String text){
        if(!notifyOn){ return; }
        if (uninitNotification &&(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
            String name = MainPage.page.getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH; //Important for heads-up notification
            NotificationChannel channel = new NotificationChannel(name,name,importance);
            channel.setDescription(MainPage.page.getString(R.string.channel_description));
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = getSystemService(MainPage.page,NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            uninitNotification = false;
        }

        Intent intent = new Intent(MainPage.page, MainPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainPage.page,0,intent,0);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainPage.page,MainPage.page.getString(R.string.app_name))
                    .setSmallIcon(R.drawable.chat_teardrop_dots)
                    .setContentTitle(meetingName)
                    .setContentText(text)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE) //Important for heads-up notification
                    .setPriority(NotificationCompat.PRIORITY_MAX); //Important for heads-up notification

            Notification buildNotification = mBuilder.build();
            NotificationManager mNotifyMgr = (NotificationManager) MainPage.page.getSystemService(Activity.NOTIFICATION_SERVICE);
            mNotifyMgr.notify(++notificationId, buildNotification);


    }

}
