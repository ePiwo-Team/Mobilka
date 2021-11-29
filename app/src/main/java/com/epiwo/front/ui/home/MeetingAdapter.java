package com.epiwo.front.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.epiwo.front.R;
import com.epiwo.logic.Chat;
import com.epiwo.logic.Meeting;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyMeetingViewHolder> {

    @NonNull
    @Override
    public MyMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyMeetingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyMeetingViewHolder holder, int position) {
        holder.meetingName.setText(Meeting.getName(Meeting.meetings,position));
        holder.meetingPlace.setText(Meeting.getPlace(Meeting.meetings,position));
        holder.meetingDesc.setText(Meeting.getDesc(Meeting.meetings,position));


        // Osluga kliku na meetingu
        holder.itemView.setOnClickListener(v -> {

            Chat.current = Meeting.getChat(Meeting.meetings,position);
            Navigation.findNavController(v).navigate(R.id.nav_chat);
        });
    }

    @Override
    public int getItemCount() {
/*
        if(Meeting.meetings == null)
            return 0;
        else
 */
        return Meeting.count(Meeting.meetings);
    }

    static class MyMeetingViewHolder extends RecyclerView.ViewHolder{
        TextView meetingName;
        TextView meetingDesc;
        TextView meetingPlace;

        public MyMeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingDesc =itemView.findViewById(R.id.meeting_desc);
            meetingPlace =itemView.findViewById(R.id.meeting_place);
            meetingName =itemView.findViewById(R.id.meeting_name);
        }
    }
}
