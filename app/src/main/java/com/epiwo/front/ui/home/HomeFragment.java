package com.epiwo.front.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.front.ui.chat.ChatFragment;
import com.epiwo.logic.Meeting;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.meetings_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new MeetingAdapter());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Meeting.update();
        MainPage.fab.show();
    }

    @Override
    public void onStop() {
        super.onStop();

        MainPage.fab.hide();
    }

}