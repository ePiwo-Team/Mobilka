package com.epiwo.front.ui.moderator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.logic.Meeting;
import com.epiwo.logic.Participant;


public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ParticipantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {

        String text = Meeting.current.getParticipants().get(position).name;
        if (Meeting.current.getParticipants().get(position).isHost)
            text = text+ " (" + MainPage.page.getString(R.string.host) + ")";
        holder.participantName.setText(text);

        if(!Meeting.current.isModerator() || Meeting.current.getParticipants().get(position).isHost)
            holder.participantKick.setVisibility(View.GONE);

        holder.participantKick.setOnClickListener(v -> {
            Meeting.current.getParticipants().get(position).removeParticipant(Meeting.current);
            notifyItemRemoved(position);
        });

    }

    @Override
    public int getItemCount() {
        if(Meeting.current.getParticipants() == null)
            return 0;
        return Meeting.current.getParticipants().size();
    }

    static class ParticipantViewHolder extends RecyclerView.ViewHolder{
        TextView participantName;
        Button participantKick;
        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            participantName =itemView.findViewById(R.id.participant_name);
            participantKick =itemView.findViewById(R.id.button_kick);
        }
    }
}
