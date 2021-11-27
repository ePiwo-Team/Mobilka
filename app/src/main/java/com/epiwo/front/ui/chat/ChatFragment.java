package com.epiwo.front.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.epiwo.front.R;

import com.epiwo.logic.Chat;


public class ChatFragment extends Fragment {
  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

         if (Chat.current==null) return root;

        TextView test = root.findViewById(R.id.test_tekst);
        test.setText(Chat.current.getName());

        RecyclerView recyclerView = root.findViewById(R.id.chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new ChatAdapter());
        recyclerView.scrollToPosition(Chat.current.talk.size());

        Button buttonSend = root.findViewById(R.id.button_send_message);
        buttonSend.setOnClickListener(v -> {

        });

        return root;
    }


}