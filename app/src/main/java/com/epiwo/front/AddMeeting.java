package com.epiwo.front;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.os.Bundle;

import android.text.InputType;

import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.epiwo.logic.Food;
import com.epiwo.logic.Meeting;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class AddMeeting extends AppCompatActivity {

    ListView listViewData;
    ArrayAdapter<String> adapter;
    AddMeeting context;
    EditText dateTime;
    final Calendar calendar=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting_activity);
        context = this;
        Food.loadFoods();

        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, Food.listToArray());
        listViewData.setAdapter(adapter);

        dateTime=findViewById(R.id.editTextMeetingDate);
        dateTime.setInputType(InputType.TYPE_NULL);
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateTime);
            }
        });
    }


    public void createMeeting(View view){

        Meeting newMeeting;
        String info;
        List<String> foods = new LinkedList<>();
        for(int i = 0; i<listViewData.getCount();i++){
            if(listViewData.isItemChecked(i)){
                foods.add(listViewData.getItemAtPosition(i).toString());
            }
        }


        EditText name = findViewById(R.id.editTextMeetingName);
        EditText desc = findViewById(R.id.editTextMeetingDescription);
        EditText date = findViewById(R.id.editTextMeetingDate);
        EditText place = findViewById(R.id.editTextMeetingPlace);

        newMeeting = new Meeting(0,name.getText().toString(),
                desc.getText().toString(),
                foods,
                place.getText().toString(),
                calendar.toInstant().toString());
                //date.getText().toString());
        info = Meeting.uploadMeeting(newMeeting);
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();

        Intent mainPage = new Intent(this , MainPage.class);
        startActivity(mainPage);
    }

    private void showDateTimeDialog(final EditText date_time_in) {

        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(AddMeeting.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(AddMeeting.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}
