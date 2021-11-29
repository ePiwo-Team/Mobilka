package com.epiwo.front.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.epiwo.front.R;


public class SearchFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

//        Meeting.downloadMeetings();
//
//        RecyclerView recyclerView = root.findViewById(R.id.meetings_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
//        recyclerView.setAdapter(new MeetingAdapter());
//        if (Meeting.count() == 0) {
//            recyclerView.setVisibility(View.GONE);
//        }
//        else {
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//
//        MainPage.fab.show();

        return root;
    }



}