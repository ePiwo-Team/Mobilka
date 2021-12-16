package com.epiwo.front;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.epiwo.logic.User;
import com.epiwo.network.Siec;

import java.util.Objects;

import static androidx.core.content.ContextCompat.getSystemService;


public class MainActivity extends AppCompatActivity {

    public static NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    public void loguj(View view) {
        Siec.jwt = null;
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

    public void adminLoguj(View view){
        User user = new User("admin@admin.pl","admin1");

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

