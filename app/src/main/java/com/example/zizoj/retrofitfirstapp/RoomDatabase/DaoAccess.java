package com.example.zizoj.retrofitfirstapp.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Query("SELECT city_name FROM weathercity WHERE city_name = :ID")
    String getCityname(String ID);

    @Insert()
    void insertWeather(WeatherDatabaseModel model);

    @Query("SELECT * FROM weathercity Where city_name = :cityname")
    List<WeatherDatabaseModel> fetchOneMoviesbyMovieId(String cityname);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOnExistsWeather(WeatherDatabaseModel model);

    @Update
    void updateweather(WeatherDatabaseModel model);


}
