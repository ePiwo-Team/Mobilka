package com.epiwo.logic;

import android.widget.ArrayAdapter;

import com.epiwo.network.Siec;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Food {


    public static List<Food> foods = null;

    public static void loadFoods(){
        foods = new LinkedList<>();
        Siec.getFoodList(foods);
    }

    public   long id;
    public   String name;
    public   String description;

    public Food(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public static String[] listToArray() {
        String [] array = new String[foods.size()];
        for (int i = 0; i < array.length; i++)
            array[i] = foods.get(i).getName();
        return array;
    }

    public static int  findFood(String name){
        for(int i=0; i<foods.size();i++)
        {
            if(Objects.equals(name,foods.get(i).getName()))
                return i;
        }
        return 0;
    }

    public static int  findFood(long id){
        for(int i=0; i<foods.size();i++)
        {
            if(id == foods.get(i).getId())
                return i;
        }
        return 0;
    }
}
