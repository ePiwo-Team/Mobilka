package com.epiwo.front.ui.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.epiwo.front.R;
import com.epiwo.logic.Food;
import com.epiwo.logic.Meeting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SearchFragment extends Fragment {

   ArrayAdapter<String> adapter;
   ListView listViewData;
   EditText dateTime;
   EditText name;
   EditText place;
   Context context;
   View root;
   final Calendar calendar=Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search, container, false);

        Button button = root.findViewById(R.id.button_search);
        context = root.getContext();

        Food.loadFoods();

        dateTime=root.findViewById(R.id.editTextSearchMeetingDate);
        dateTime.setInputType(InputType.TYPE_NULL);
        dateTime.setOnClickListener(v -> showDateTimeDialog(dateTime));

        listViewData = root.findViewById(R.id.listView_search_data);
        adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_multiple_choice, Food.listToArray());
        listViewData.setAdapter(adapter);






        button.setOnClickListener(v -> {

          name = root.findViewById(R.id.editTextSearchMeetingName);
          place = root.findViewById(R.id.editTextSearchMeetingPlace);

                searchMeeting(root);
            //        Meeting.downloadMeetings();
        });

        return root;
    }


    public void searchMeeting(View view){

        List<Food> foods = new LinkedList<>();
        for(int i = 0; i<listViewData.getCount();i++){
            if(listViewData.isItemChecked(i)){
                foods.add(Food.foods.get(Food.findFood(listViewData.getItemAtPosition(i).toString())));
            }
        }

        Meeting.findMeeting(name.getText().toString(),place.getText().toString(),calendar,foods);


        RecyclerView recyclerView = root.findViewById(R.id.found_meetings_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new SearchMeetingAdapter());

    }



    private void showDateTimeDialog(final EditText date_time_in) {

        DatePickerDialog.OnDateSetListener dateSetListener= (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            @SuppressLint("SimpleDateFormat") TimePickerDialog.OnTimeSetListener timeSetListener= (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);

                SimpleDateFormat simpleDateFormat;
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(context,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
        };

        new DatePickerDialog(context,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }



}