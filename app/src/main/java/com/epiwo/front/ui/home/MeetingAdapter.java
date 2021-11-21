package com.epiwo.front.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epiwo.front.R;
import com.epiwo.logic.Meeting;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyMeetingViewHolder> {



    @NonNull
    @Override
    public MyMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyMeetingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyMeetingViewHolder holder, int position) {
        holder.textView.setText(Meeting.meetings[position].toString());
    }

    @Override
    public int getItemCount() {
        return Meeting.meetings.length;
    }

    static class MyMeetingViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public MyMeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.meeting_name);
        }
    }
}
