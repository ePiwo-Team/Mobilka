package com.epiwo.front.ui.chat;

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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BalloonViewHolder> {

    @NonNull
    @Override
    public BalloonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new BalloonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BalloonViewHolder holder, int position) {
        holder.tekst.setText(Chat.current.talk.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return Chat.current.talk.size();
    }

    static class BalloonViewHolder extends RecyclerView.ViewHolder{
        TextView tekst;

        public BalloonViewHolder(@NonNull View itemView) {
            super(itemView);
            tekst =itemView.findViewById(R.id.balloon);
        }
    }
}
