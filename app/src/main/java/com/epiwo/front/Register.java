package com.epiwo.front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epiwo.logic.User;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
    }

    public void sendRegistration(View view){

        EditText editTextDate = (EditText) findViewById(R.id.editTextDate);
        EditText editTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditText editTextPasswordCheck = (EditText) findViewById(R.id.editTextTextPasswordCheck);
        User nowyUser = new User( editTextPersonName.getText().toString(),
                                editTextPassword.getText().toString(),
                                editTextPhone.getText().toString(),
                                editTextDate.getText().toString(),
                                editTextEmail.getText().toString());

        if( nowyUser.checkPassword(editTextPasswordCheck.getText().toString())) {
            if (nowyUser.register()) {
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
            } else{
                    String textFail = getResources().getString(R.string.rejestracjafail);
                    Toast toast = Toast.makeText(this, textFail, Toast.LENGTH_SHORT);
                    toast.show();
                  }
        }else{
            String text = getResources().getString(R.string.niezgodnehasla);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
        }

    }


}

