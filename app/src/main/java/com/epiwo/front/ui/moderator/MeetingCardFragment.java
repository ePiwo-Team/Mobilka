package com.epiwo.front.ui.moderator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.epiwo.front.R;
import com.epiwo.logic.Meeting;

public class MeetingCardFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_meeting_card, container, false);


        EditText name = root.findViewById(R.id.editTextMeetingCardName);
        EditText desc = root.findViewById(R.id.editTextMeetingCardDesc);
        EditText place = root.findViewById(R.id.editTextMeetingCardPlace);
        EditText date = root.findViewById(R.id.editTextMeetingCardDate);

        name.setText(Meeting.current.getName());
        desc.setText(Meeting.current.getDesc());
        place.setText(Meeting.current.getPlace());
        date.setText(Meeting.current.getDateAndTime());
        return root;
    }
}