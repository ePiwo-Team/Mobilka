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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
   Context context;
   final Calendar calendar=Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        Button button = root.findViewById(R.id.button_search);
        context = root.getContext();

        Food.loadFoods();

        dateTime=root.findViewById(R.id.editTextSearchMeetingDate);
        dateTime.setInputType(InputType.TYPE_NULL);
        dateTime.setOnClickListener(v -> showDateTimeDialog(dateTime));

        listViewData = root.findViewById(R.id.listView_search_data);
        adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_multiple_choice, Food.listToArray());
        listViewData.setAdapter(adapter);


//
//        RecyclerView recyclerView = root.findViewById(R.id.meetings_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
//        recyclerView.setAdapter(new MeetingAdapter());
//        if (Meeting.count() == 0) {
//            recyclerView.setVisibility(View.GONE);
//        }
//        else {
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//

        button.setOnClickListener(v -> {

            EditText name = root.findViewById(R.id.editTextSearchMeetingName);
            EditText place = root.findViewById(R.id.editTextSearchMeetingPlace);


            //        Meeting.downloadMeetings();
        });

        return root;
    }


    public void searchMeeting(View view){
        Meeting newMeeting;
        String info;
        List<Food> foods = new LinkedList<>();
        for(int i = 0; i<listViewData.getCount();i++){
            if(listViewData.isItemChecked(i)){
                foods.add(Food.foods.get(Food.findFood(listViewData.getItemAtPosition(i).toString())));
            }
        }




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