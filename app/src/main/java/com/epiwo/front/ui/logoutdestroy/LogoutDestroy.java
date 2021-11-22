package com.epiwo.front.ui.logoutdestroy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.epiwo.front.MainActivity;
import com.epiwo.front.R;
import com.epiwo.front.Register;
import com.epiwo.logic.User;

public class LogoutDestroy extends Fragment {

    User user = User.me;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout_destroy, container, false);

        Button buttonLogout = root.findViewById(R.id.button_logout);
        Button buttonDestroy = root.findViewById(R.id.button_destroy);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.name = null;
                user.phone = null;
                user.email = null;
                user.password = null;

                Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(mainActivity);
            }
        });


        return root;
    }

}