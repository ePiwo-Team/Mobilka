package com.epiwo.front.ui.moderator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epiwo.front.R;
import com.epiwo.front.ui.home.MeetingAdapter;
import com.epiwo.logic.Meeting;


public class ModeratorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_moderator, container, false);

        Meeting.downloadMeetings();

        RecyclerView recyclerView = root.findViewById(R.id.moderated_meetings_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new ModeratorMeetingAdapter());

        if (Meeting.count(Meeting.myMeetings) == 0) {
            recyclerView.setVisibility(View.GONE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
        }

        return root;
    }
}