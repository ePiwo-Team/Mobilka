package com.epiwo.front.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.logic.Chat;
import com.epiwo.logic.Meeting;

public class SearchMeetingAdapter extends RecyclerView.Adapter<SearchMeetingAdapter.MyMeetingViewHolder> {

    @NonNull
    @Override
    public MyMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyMeetingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_join_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyMeetingViewHolder holder, int position) {
        holder.meetingName.setText(Meeting.getName(Meeting.foundMeetings,position));
        holder.meetingPlace.setText(Meeting.getPlace(Meeting.foundMeetings,position));
        holder.meetingDesc.setText(Meeting.getDesc(Meeting.foundMeetings,position));

        if(Meeting.isMeetingOnList(Meeting.meetings,Meeting.getId(Meeting.foundMeetings, position)))
            holder.buttonJoin.setVisibility(View.GONE);
        // Osluga kliku na meetingu
        holder.buttonJoin.setOnClickListener(v -> {
            holder.buttonJoin.setVisibility(View.GONE);
            Meeting.addMeeting(Meeting.meetings, Meeting.foundMeetings.get(position));
            String resp = Meeting.joinMeeting(Meeting.foundMeetings.get(position));
            Toast.makeText(MainPage.page, resp, Toast.LENGTH_SHORT ).show();
        });
    }

    @Override
    public int getItemCount() {
        return Meeting.count(Meeting.foundMeetings);
    }

    static class MyMeetingViewHolder extends RecyclerView.ViewHolder{
        TextView meetingName;
        TextView meetingDesc;
        TextView meetingPlace;
        ImageView buttonJoin;
        public MyMeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonJoin = itemView.findViewById(R.id.button_join);
            meetingDesc =itemView.findViewById(R.id.meeting_desc);
            meetingPlace =itemView.findViewById(R.id.meeting_place);
            meetingName =itemView.findViewById(R.id.meeting_name);
        }
    }
}
