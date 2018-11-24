package com.example.zizoj.retrofitfirstapp.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {WeatherDatabaseModel.class}, version = 8, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess() ;
}
