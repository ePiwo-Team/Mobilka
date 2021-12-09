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
import android.widget.Toast;

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
      LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
      recyclerView.setLayoutManager(layoutManager);
      ChatAdapter chatAdapter = new ChatAdapter();
      Chat.current.myAdapter = chatAdapter;
      recyclerView.setAdapter(chatAdapter);
      recyclerView.scrollToPosition(Chat.current.talk.size()-1);

      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

          @Override
          public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
              int x = layoutManager.findFirstCompletelyVisibleItemPosition();
              if (x == 0) {
                  if (Chat.current.getOldBalloons()>0) chatAdapter.notifyDataSetChanged();
              }
          }
        });


        buttonSend.setOnClickListener(v -> {

            Chat.current.sendBalloon(message.getText().toString());
            Chat.current.getAllBalloons();
            message.setText("");
            chatAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(Chat.current.talk.size()-1);

        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Chat.current.onScreen = true;

        recyclerView.scrollToPosition(Chat.current.talk.size()-1);
    }

    @Override
    public void onPause() {
        super.onPause();

        Chat.current.onScreen = false;
    }
}