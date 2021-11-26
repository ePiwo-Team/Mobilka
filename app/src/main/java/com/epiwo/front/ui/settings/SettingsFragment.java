package com.epiwo.front.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.epiwo.front.MainActivity;
import com.epiwo.front.R;
import com.epiwo.logic.User;
import com.epiwo.network.Siec;

public class SettingsFragment extends Fragment {

    User user = User.me;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Button buttonDestroy = root.findViewById(R.id.button_destroy);
        Button updateButton = root.findViewById(R.id.buttonUpdate);
        EditText editEmail = root.findViewById(R.id.editEmail);
        EditText editName = root.findViewById(R.id.editName);
        EditText editPhoneNumber = root.findViewById(R.id.editPhone);


        editEmail.setText(user.email);
        editName.setText(user.name);
        editPhoneNumber.setText(user.phone);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editEmail = root.findViewById(R.id.editEmail);
                EditText editName = root.findViewById(R.id.editName);
                EditText editPhoneNumber = root.findViewById(R.id.editPhone);
                EditText editPassword = root.findViewById(R.id.editPassword);
                EditText repeatPassword = root.findViewById(R.id.editPasswordRepeat);

                user.email = editEmail.getText().toString();
                user.name = editName.getText().toString();
                user.phone = editPhoneNumber.getText().toString();
                user.password = editPassword.getText().toString();


                if( user.checkPassword(repeatPassword.getText().toString()))
                Siec.updateUserData(user);
                else{
                    String text = getResources().getString(R.string.niezgodnehasla);
                    Toast.makeText(getActivity(),text, Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Siec.deleteSelfUser();
                user.nullUserData();
                Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(mainActivity);
            }
        });




        return root;
    }




}