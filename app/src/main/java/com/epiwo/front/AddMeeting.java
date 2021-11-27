package com.epiwo.front;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class AddMeeting extends AppCompatActivity {

    ListView listViewData;
    ArrayAdapter<String> adapter;
    String[] arrayPeliculas = {"poz 1","poz 2", "poz 3", "poz 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting_activity);

        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayPeliculas );
        listViewData.setAdapter(adapter);



    }


    public void createMeeting(View view){
        String itemsSelected = "Selected items: \n";
        for(int i = 0; i<listViewData.getCount();i++){
            if(listViewData.isItemChecked(i)){
                itemsSelected += listViewData.getItemAtPosition(i) + "\n";
            }
        }
        Toast.makeText(this,itemsSelected,Toast.LENGTH_SHORT).show();

        Intent mainPage = new Intent(this , MainPage.class);
        startActivity(mainPage);
    }
}
