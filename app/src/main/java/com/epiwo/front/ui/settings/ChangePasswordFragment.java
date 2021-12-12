package com.epiwo.front.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epiwo.front.MainPage;
import com.epiwo.front.R;
import com.epiwo.logic.User;
import com.epiwo.network.Siec;


public class ChangePasswordFragment extends Fragment {

    User user = User.me;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        EditText editPassword = root.findViewById(R.id.editPassword);
        EditText repeatPassword = root.findViewById(R.id.editPasswordRepeat);
        Button changePassword = root.findViewById(R.id.button_change_password);


        changePassword.setOnClickListener(v -> {

            user.password = editPassword.getText().toString();

            if( user.checkPassword(repeatPassword.getText().toString())) {
                Siec.updateUserData(user);
                Toast.makeText(getActivity(), MainPage.page.getString(R.string.password_has_been_changed), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
            else{
                String text = getResources().getString(R.string.niezgodnehasla);
                Toast.makeText(getActivity(),text, Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

}