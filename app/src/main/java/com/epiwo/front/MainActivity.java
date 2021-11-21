package com.epiwo.front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.epiwo.logic.User;
import com.epiwo.network.Siec;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void loguj(View view) {

        EditText wpiszLoginEditText = findViewById(R.id.wpiszLoginEditText);
        EditText wpiszHasloEditText = findViewById(R.id.wpiszHasloEditText);

        User user = new User(wpiszLoginEditText.getText().toString(), wpiszHasloEditText.getText().toString());
        CharSequence text = "slabo";

        if (user.test()) {
            text = Siec.echo();

            Intent meetings = new Intent(this, MainPage.class);
            startActivity(meetings);

        }else {
            text  = "http: " + Integer.toString(Siec.httpRc);
        }

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void rejestruj(View view) {
        Intent rejestracja = new Intent(this, Register.class);
        startActivity(rejestracja);
    }
}