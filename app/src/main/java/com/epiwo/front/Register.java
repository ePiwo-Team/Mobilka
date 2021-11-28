package com.epiwo.front;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.epiwo.logic.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {


    EditText date_in;
    final Calendar calendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        date_in=findViewById(R.id.editTextDate);
        date_in.setInputType(InputType.TYPE_NULL);
        date_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date_in);
            }
        });


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

    private void showDateDialog(final EditText date_in) {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(Register.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }



}

