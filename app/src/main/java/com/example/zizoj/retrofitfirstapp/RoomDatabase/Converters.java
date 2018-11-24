package com.example.zizoj.retrofitfirstapp.RoomDatabase;

import android.arch.persistence.room.TypeConverter;

import com.example.zizoj.retrofitfirstapp.Network.model.Forecastday;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {


    @TypeConverter
    public static ArrayList<Forecastday> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Forecastday>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisr(ArrayList<Forecastday> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
