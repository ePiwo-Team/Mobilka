package com.epiwo.front.ui.moderator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.logic.Chat;
import com.epiwo.logic.Meeting;
import com.epiwo.logic.Participant;

import static android.view.View.VISIBLE;

public class MeetingCardFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_meeting_card, container, false);

        Meeting.current.setParticipants(Participant.getParticipants(Meeting.current.getId()));

        Button chat = root.findViewById(R.id.button_go_to_chat);
        Button modify = root.findViewById(R.id.button_edit_meeting);
        Button leave = root.findViewById(R.id.button_leave_meeting);

        EditText name = root.findViewById(R.id.editTextMeetingCardName);
        EditText desc = root.findViewById(R.id.editTextMeetingCardDesc);
        EditText place = root.findViewById(R.id.editTextMeetingCardPlace);
        EditText date = root.findViewById(R.id.editTextMeetingCardDate);
        name.setText(Meeting.current.getName());
        desc.setText(Meeting.current.getDesc());
        place.setText(Meeting.current.getPlace());
        date.setText(Meeting.current.getDateAndTime());

        RecyclerView recyclerView = root.findViewById(R.id.participant_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new ParticipantAdapter());

        chat.setOnClickListener(v -> {
            Chat.current = Meeting.current.getChat();
            Navigation.findNavController(v).navigate(R.id.nav_chat);
        });

        if(Meeting.current.isModerator()) {

            name.setFocusableInTouchMode(true);
            desc.setFocusableInTouchMode(true);
            place.setFocusableInTouchMode(true);
            date.setFocusableInTouchMode(true);
            modify.setVisibility(VISIBLE);

            modify.setOnClickListener(v ->{
                Meeting.current.setName(name.getText().toString());
                Meeting.current.setDesc(desc.getText().toString());
                Meeting.current.setPlace(place.getText().toString());
                Meeting.current.setDateAndTime(date.getText().toString());

                if (Meeting.current.updateMeeting())
                    Toast.makeText(MainPage.page, "Zaktualizowano spotkanie", Toast.LENGTH_SHORT).show();
            });

            leave.setText(R.string.destroy_meeting);

            leave.setOnClickListener(v ->{
                Meeting.current.destroyMeeting();
                Toast.makeText(MainPage.page, "Anulowano spotkanie", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.nav_home);
            });
        }else{
            leave.setText(R.string.leave_meeting);

            leave.setOnClickListener(v -> {
                Meeting.current.leaveMeeting();
                Toast.makeText(MainPage.page, "Opuszczono spotkanie", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.nav_home);
            });
            }




        return root;

    }
}