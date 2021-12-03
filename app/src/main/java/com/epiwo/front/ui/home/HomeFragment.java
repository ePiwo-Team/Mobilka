package com.epiwo.front.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.logic.Meeting;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Meeting.downloadMeetings();

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Meeting.downloadMeetings();

        RecyclerView recyclerView = root.findViewById(R.id.meetings_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new MeetingAdapter());

        if (Meeting.count(Meeting.meetings) == 0) {
            recyclerView.setVisibility(View.GONE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
        }

        MainPage.fab.show();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Meeting.downloadMeetings();
        MainPage.fab.show();
    }

    @Override
    public void onStop() {
        super.onStop();

        MainPage.fab.hide();
    }

}