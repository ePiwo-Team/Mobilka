package com.epiwo.front;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.epiwo.logic.User;
import com.epiwo.network.Siec;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    public void loguj(View view) {

        EditText wpiszLoginEditText =  (EditText) findViewById(R.id.wpiszLoginEditText);
        EditText wpiszHasloEditText = (EditText) findViewById(R.id.wpiszHasloEditText);



        User user = new User(wpiszLoginEditText.getText().toString(), wpiszHasloEditText.getText().toString());
        CharSequence text = "slabo";

        if(user.test()) {
            text = Siec.jwt;

        }

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

    }

}