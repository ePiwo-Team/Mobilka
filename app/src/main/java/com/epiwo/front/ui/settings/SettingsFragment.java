package com.epiwo.front.ui.settings;

import android.annotation.SuppressLint;
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
import androidx.navigation.Navigation;

import com.epiwo.front.MainActivity;
import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.logic.Chat;
import com.epiwo.logic.User;
import com.epiwo.network.Siec;

public class SettingsFragment extends Fragment {

    User user = User.me;

    @SuppressLint("CutPasteId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Button buttonDestroy = root.findViewById(R.id.button_destroy);
        Button updateButton = root.findViewById(R.id.buttonUpdate);
        Button goToChange = root.findViewById(R.id.button_goto_change_password);
        Button notification = root.findViewById(R.id.button_notifications);
        EditText editEmail = root.findViewById(R.id.editEmail);
        EditText editName = root.findViewById(R.id.editName);
        EditText editPhoneNumber = root.findViewById(R.id.editPhone);


        editEmail.setText(user.email);
        editName.setText(user.name);
        editPhoneNumber.setText(user.phone);

        updateButton.setOnClickListener(v -> {

            EditText editEmail1 = root.findViewById(R.id.editEmail);
            EditText editName1 = root.findViewById(R.id.editName);
            EditText editPhoneNumber1 = root.findViewById(R.id.editPhone);



            user.email = editEmail1.getText().toString();
            user.name = editName1.getText().toString();
            user.phone = editPhoneNumber1.getText().toString();


            Siec.updateUserData(user);
            Toast.makeText(getActivity(),"Dane zostały zaktualizowane", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.nav_home);



        });

        goToChange.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_change_password);
        });

        notification.setOnClickListener(v -> {
            Chat.notifyOn = !Chat.notifyOn;
            if(Chat.notifyOn)
                notification.setText(MainPage.page.getString(R.string.notification_on));
            else
                notification.setText(MainPage.page.getString(R.string.notification_off));
        });

        buttonDestroy.setOnClickListener(v -> {
            Siec.deleteSelfUser();
            user.nullUserData();
            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
            startActivity(mainActivity);
        });




        return root;
    }




}