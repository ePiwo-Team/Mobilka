package com.epiwo.front.ui.logoutdestroy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.epiwo.front.MainActivity;
import com.epiwo.front.R;
import com.epiwo.logic.User;
import com.epiwo.network.Siec;

public class LogoutFragment extends Fragment {

    User user = User.me;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        user.nullUserData();
        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
        startActivity(mainActivity);

        return root;
    }

}