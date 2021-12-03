package com.epiwo.front;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epiwo.logic.User;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    TextView textDate_in;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        textDate_in = findViewById(R.id.editTextDate);
        textDate_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String monthWithZero = "0";
                if(month < 10)
                    monthWithZero = monthWithZero + String.valueOf(month);
                else
                    monthWithZero = String.valueOf(month);

                String dayWithZero = "0";
                if(dayOfMonth < 10)
                    dayWithZero = dayWithZero + String.valueOf(dayOfMonth);
                else
                    dayWithZero = String.valueOf(dayOfMonth);

                String date = year + "-" + monthWithZero + "-" + dayWithZero;
                textDate_in.setText(date);
            }
        };

    }

    public void sendRegistration(View view) {

        TextView textViewDate = (TextView) findViewById(R.id.editTextDate);
        EditText editTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditText editTextPasswordCheck = (EditText) findViewById(R.id.editTextTextPasswordCheck);

        User nowyUser = new User(editTextPersonName.getText().toString(),
                editTextPassword.getText().toString(),
                editTextPhone.getText().toString(),
                textViewDate.getText().toString(),
                editTextEmail.getText().toString());

        if (nowyUser.checkPassword(editTextPasswordCheck.getText().toString())) {
            if (nowyUser.register()) {
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
            } else {
                String textFail = getResources().getString(R.string.rejestracjafail);
                Toast toast = Toast.makeText(this, textFail, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            String text = getResources().getString(R.string.niezgodnehasla);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}


