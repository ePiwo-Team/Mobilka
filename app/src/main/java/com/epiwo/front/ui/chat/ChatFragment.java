package com.epiwo.front.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;

import com.epiwo.logic.Chat;
import com.epiwo.logic.Meeting;
import com.epiwo.network.Siec;


public class ChatFragment extends Fragment {

    RecyclerView recyclerView;

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

         if (Chat.current==null) return root;

      EditText message = root.findViewById(R.id.editTextMessage);
      Button buttonSend = root.findViewById(R.id.button_send_message);
      TextView meetingName = root.findViewById(R.id.chat_name);


        meetingName.setText(Chat.current.getName());

        recyclerView = root.findViewById(R.id.chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new ChatAdapter());
        recyclerView.scrollToPosition(Chat.current.talk.size()-1);


        buttonSend.setOnClickListener(v -> {

            Chat.current.sendBalloon(message.getText().toString());
            message.setText("");
            ChatAdapter.currentChat.notifyItemInserted(Chat.current.talk.size());
            recyclerView.scrollToPosition(Chat.current.talk.size()-1);


        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView.scrollToPosition(Chat.current.talk.size()-1);
    }

}